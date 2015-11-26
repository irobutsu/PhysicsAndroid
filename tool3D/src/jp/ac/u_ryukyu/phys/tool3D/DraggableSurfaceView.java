package jp.ac.u_ryukyu.phys.tool3D;
import jp.ac.u_ryukyu.phys.lib.Vec2;
import jp.ac.u_ryukyu.phys.lib.Vec3;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

abstract public class DraggableSurfaceView extends GLSurfaceView {
	protected DraggableRenderer renderer;

	private boolean secondTouchDone = false;
	private boolean firstTouchDone = false;
	private int firstTouchPointerID;
	private int secondTouchPointerID;

	Vec2 firstTouchPlace;
	Vec2 secondTouchPlace;

	float wx;
	float hy;

	public void setTranslationDefault(Vec3 tDefault) {
		renderer.setTranslationDefault(tDefault);
	}

	private float nowExpand = 1f;
	// private float expandRate = 1f;

	@Override
	protected void onSizeChanged(int w, int h, int oldw,int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		wx=w; hy=h;
	}

	protected abstract DraggableRenderer newRenderer();
	
	
	private boolean movingMode;

	protected boolean touchManageFlg=true;
	

	public DraggableSurfaceView(Context context) {
		super(context);
		//setEGLContextFactory(new ContextFactory());
		renderer=newRenderer();
		setRenderer(renderer);
		resetView();
	}

	public void manageFirstTouch(int ptID, Vec2 mouseP) {
		firstTouchDone=true;
		firstTouchPointerID=ptID;
		firstTouchPlace=mouseP;
		movingMode =( mouseP.X() > 0.4*wx && mouseP.X() <0.6*wx
		&&  mouseP.Y() > 0.4*hy && mouseP.Y() <0.6*hy);
		renderer.setMovingMode(movingMode);
	}

	public void endFirstTouch() {
		if( movingMode ) {
			renderer.setSaveTranslate();
		}
		renderer.setMovingMode(false); // ドラッグ終了。
		renderer.saveMatrix();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if( touchManageFlg ) {
		int index=(event.getAction()&MotionEvent.ACTION_POINTER_ID_MASK)>>
			MotionEvent.ACTION_POINTER_ID_SHIFT;
		int ptID=event.getPointerId(index);
		Vec2 mouseP=new Vec2(event.getX(index),event.getY(index));      
		switch ( event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			if( !firstTouchDone ) {
				manageFirstTouch(ptID,mouseP);
			} else if( !secondTouchDone ) {
				endFirstTouch(); // ２個目のタッチが来た時点で1stタッチの処理を終了。
				secondTouchDone=true;
				secondTouchPointerID=ptID;
				int firstindex=event.findPointerIndex(firstTouchPointerID);
				if( firstTouchPointerID == secondTouchPointerID || firstindex <0 ) {
	    			// 1stと2ndが同じだったり、1stがみつからん時
					// 1stタッチと同じ処理をする。
					secondTouchDone=false;
					firstTouchPointerID=ptID;
					manageFirstTouch(ptID,mouseP);
				} else {
	    			movingMode=false;
					renderer.setMovingMode(false); // ２個目のタッチがされた時点でmovingModeは抜ける。
					nowExpand=1f;
					firstTouchPlace=new Vec2(event.getX(firstindex),event.getY(firstindex));
					secondTouchPlace=mouseP;
				}
			}
			// 既にタッチが２個あったら、もう何もしない（３個以上のタッチは無視します）
			return true;
		case MotionEvent.ACTION_MOVE:
			if( firstTouchDone && !secondTouchDone) {
				Vec2 tvec=(firstTouchPlace.Diff(mouseP)).Prod((float)(Math.PI/hy));
				// tvecは、どれだけ「ずらしたか」を表す。
				// hyで割ってPIをかけたことで、端から端までで180度になる。
				if( movingMode ) {
					renderer.setTranslate(tvec.Prod(3f));					
				} else {
					renderer.setRotation(tvec);
				}
			} else if( secondTouchDone ) {
				int firstindex=event.findPointerIndex(firstTouchPointerID);
				int secondindex=event.findPointerIndex(secondTouchPointerID);
				if( firstindex <0 ) {
					// secondtouchがあるのにfirstTouchがいつの間にか外れている場合。
					secondTouchDone=false;
				} else {
					Vec2 firstTouchNow=new Vec2(event.getX(firstindex),event.getY(firstindex));
					Vec2 secondTouchNow=new Vec2(event.getX(secondindex),event.getY(secondindex));
	
					Vec2 firstShift=firstTouchPlace.Diff(secondTouchPlace);
					Vec2 nowShift=firstTouchNow.Diff(secondTouchNow);
					float firstLen=firstShift.Norm();
					float secondLen=nowShift.Norm();
					nowExpand=secondLen/firstLen;		
					renderer.setExpandRate(nowExpand);

					renderer.setTwist((float)(
							Math.atan2(firstShift.Y(),firstShift.X())
							-Math.atan2(nowShift.Y(),nowShift.X())							
							));
					
					Vec2 midPlace=(firstTouchPlace.Sum(secondTouchPlace)).Quot(2);
					Vec2 midNow=(firstTouchNow.Sum(secondTouchNow)).Quot(2);
					Vec2 tvec=(midPlace.Diff(midNow)).Quot(hy);
					
					
					renderer.setTranslate(tvec.Prod(3f));
				}
			}	
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			if( firstTouchDone && secondTouchDone) {
				// ２個のタッチのうちどっちかが外れたのか？
				
				if( ptID == firstTouchPointerID || 
					ptID == secondTouchPointerID) {
					
					if( ptID == firstTouchPointerID ) {
						// 1stタッチが外れたのなら、2ndタッチを1stに格上げ。
						firstTouchPointerID=secondTouchPointerID;					
					}	
					secondTouchDone=false;
					renderer.saveMatrix();
					renderer.saveExpandRate();
					renderer.setSaveTranslate();
					// ここからfirstTouchが始まったことにする。
					int firstindex=event.findPointerIndex(firstTouchPointerID);
					if( firstindex >=0 ) {
						manageFirstTouch(firstTouchPointerID,new Vec2(event.getX(firstindex),event.getY(firstindex)));
					} else {
						// 両方のタッチがいっせいにはずれたらしい。
						firstTouchDone=false;
					}
//					renderer.setExpandRate(nowExpand);
//					renderer.saveExpandRate();
//					nowExpand=1f;
//					renderer.setSaveTranslate();
				}
			}  else {
				firstTouchDone=false;
				secondTouchDone=false;
				endFirstTouch();
			}
				
		}
		}
		return super.onTouchEvent(event);
	}

	public void resetView() {
		renderer.resetView();
	
	}

}