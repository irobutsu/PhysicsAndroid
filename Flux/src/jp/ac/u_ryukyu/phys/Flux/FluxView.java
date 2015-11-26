package jp.ac.u_ryukyu.phys.Flux;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import jp.ac.u_ryukyu.phys.lib.DraggManageView;
import jp.ac.u_ryukyu.phys.lib.DraggManager;
import jp.ac.u_ryukyu.phys.lib.MovingObject;
import jp.ac.u_ryukyu.phys.lib.Vec2;
import jp.ac.u_ryukyu.phys.lib.Vec3;

public class FluxView extends DraggManageView {
	DraggableVector vecE;
	DraggableVector vecS;
	Flux3DView view3d;
	Vec2 oxy;
//	Vec2 oyz;

	
	public FluxView(Context context,Flux3DView v3d) {
		super(context);	
		setStepT(0.01f);
		setFocusable(true);
		vecE=new DraggableVector(-80,20,0,Color.argb(200,255,0,0));
		vecS=new DraggableVector(-100,0,0,Color.argb(200,0,0,255));
		
		vecE.setDraggManager(0);
		vecS.setDraggManager(0);
		
		view3d=v3d;
		

	}
	@Override
	public void timeStepGO() {
		super.timeStepGO();
		if( view3d != null ) {
			view3d.setT(t);
		}
	}	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean handled=false;


		int index=(event.getAction()&MotionEvent.ACTION_POINTER_ID_MASK)>>
        	MotionEvent.ACTION_POINTER_ID_SHIFT;
		int ptID=event.getPointerId(index);
		Vec2 mouseP=new Vec2(event.getX(index),event.getY(index));


		switch ( event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			if( vecE.Catch(mouseP) != 0) {
				if( vecS.isDraggedBy(ptID) ) {
					return false;
				}
				vecE.startDragg(ptID,Vec2.zero);
				vecE.setTmpPos(mouseP);
				return true;
			}
			if( vecS.Catch(mouseP) !=0) {
				if( vecE.isDraggedBy(ptID) ) {
					return false;
				}
				vecS.startDragg(ptID,Vec2.zero);
				vecS.setTmpPos(mouseP);
				return true;
			}
			// どの物体も押されてない 
			ClickOthers(mouseP);
			break;
    	case MotionEvent.ACTION_MOVE:
    		// ACTION_MOVEはポインタそれぞれについて一個ずつではなく、一挙に来るみたい。
    		// というわけで、ACTION_MOVEが来た時は全てのドラッグ可能な物体について処理を実行。
    		int ID=vecE.whoDragg();
    		int idx=event.findPointerIndex(ID);
    		// idxが負の時は、今回来たデータの中にこの物体はない、ということ。
    		if(idx>=0) {
    			mouseP=new Vec2(event.getX(idx),event.getY(idx));
    			vecE.setMove(mouseP);
    			handled=true;
    		}
    		ID=vecS.whoDragg();
    		idx=event.findPointerIndex(ID);
    		// idxが負の時は、今回来たデータの中にこの物体はない、ということ。
    		if(idx>=0) {
    			mouseP=new Vec2(event.getX(idx),event.getY(idx));
    			vecS.setMove(mouseP);
    			handled=true;
    		}
    		break;
    	case MotionEvent.ACTION_UP:
    	case MotionEvent.ACTION_POINTER_UP:
    		//タッチが離されたときの動作
			if( vecE.isDraggedBy(ptID) ) {
				vecE.releaseDragg();
    			handled=true;
			}
			if( vecS.isDraggedBy(ptID) ) {
				vecS.releaseDragg();
    			handled=true;
			}
    		break;
    	}
		return handled;
		
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		wx=w;
		hy=h;
		
		
		int ww=((View)getParent()).getWidth();
		int hh=((View)getParent()).getHeight();
		if( ww-hh != wx ) {
			this.setLayoutParams(new LinearLayout.LayoutParams(ww-hh, hh));
			view3d.setLayoutParams(new LinearLayout.LayoutParams(hh, hh));
		}
		oxy=new Vec2(wx/2,hy/2);
	//	oyz=new Vec2(wx/2,3*hy/4);
		
		
		DraggableVector.setOrigin_xy(oxy);
	//	DraggableVector.setOrigin_yz(oyz);
	}

	@Override
	protected void drawBackGround(Canvas c) {
		Paint p=new Paint();
		p.setColor(Color.WHITE);
		c.drawRect(0,0,wx,hy,p);
		
		p.setColor(Color.BLACK);
		c.drawLine(wx/2, 0, wx/2, hy, p);
		
		c.drawLine(0,oxy.Y(), wx,oxy.Y(),p);
	//	c.drawLine(0,oyz.Y(), wx,oyz.Y(),p);
		
	}
	@Override
	protected void writePrevious(Canvas canvas) {
		vecE.writeP(canvas);
		vecS.writeP(canvas);
		view3d.setVecE(vecE.vec);
		view3d.setVecS(vecS.vec);
	}
	
	

	@Override
	public boolean ClickOthers(Vec2 m) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	protected void setSituation() {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void writeSubView(Canvas canvas) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void calcNextEach(MovingObject movingObject) {
		// TODO Auto-generated method stub
		
	}
}
