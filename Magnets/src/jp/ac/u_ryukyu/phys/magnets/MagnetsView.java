package jp.ac.u_ryukyu.phys.magnets;


import jp.ac.u_ryukyu.phys.lib.DraggManageView;
import jp.ac.u_ryukyu.phys.lib.DraggManager;
import jp.ac.u_ryukyu.phys.lib.MovingObject;
import jp.ac.u_ryukyu.phys.lib.Vec2;

import android.app.ProgressDialog;
import android.app.SearchManager.OnCancelListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.widget.ProgressBar;

public class MagnetsView extends DraggManageView {
	int A[][];
	private ProgressBar pBar;

	public MagnetsView(Context context,ProgressBar pbar) {
		super(context);
		pBar=pbar;
//		this.timerStop();
		M=new Magnets();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int index=(event.getAction()&MotionEvent.ACTION_POINTER_ID_MASK)>>
    	MotionEvent.ACTION_POINTER_ID_SHIFT;
    	Vec2 mouseP=new Vec2(event.getX(index),event.getY(index));
    	int ptID=event.getPointerId(index);
    	int j;
		if( qGenerateFlg ) {
			switch ( event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_MOVE:
				for(j=0; j<DraggManager.dragObjList.size(); j++ ) {
					Magnet q=((Magnet)DraggManager.dragObjList.get(j));
					int ID=q.whoDragg();
					int idx=event.findPointerIndex(ID);
					if( idx>=0) {
						mouseP=new Vec2(event.getX(idx),event.getY(idx));
					
						if( q.getDragMode()==0) {
							Vec2 pt=mouseP.Diff(q.shift()); // shift分を引いて、「物体の中心位置」に調整する。
							q.setTmpPos(q.PositionInRect(0,0,w,h,pt));
							return true;
						} else if( q.getDragMode()==1 ) {
							float a=(float)Math.atan2(mouseP.Y()-q.Y(), mouseP.X()-q.X());
							q.setAngle(a);
							return true;
						} else if( q.getDragMode()==2 ) {
							float a=(float)Math.atan2(q.Y()-mouseP.Y(),q.X()-mouseP.X());
							q.setAngle(a);
							return true;
						} else {
						
						}
					}
				}
				break;
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN:
				if(DraggManager.dragObjList!=null ) {
				for(j=0; j<DraggManager.dragObjList.size(); j++ ) {
					Magnet q=((Magnet)DraggManager.dragObjList.get(j));
					if( q.isCatched(mouseP) ) {
						q.setDragMode(0);
						q.startDragg(ptID,mouseP.Diff(q.Pos()));
						return true;
					} else if(q.isCatchedN(mouseP)) {
						q.setDragMode(1);
						q.setTmpPos(q.Pos());
						q.startDragg(ptID,Vec2.zero);
						return true;
					} else if(q.isCatchedS(mouseP) ) {
						q.setDragMode(2);
						q.setTmpPos(q.Pos());
						q.startDragg(ptID,Vec2.zero);
						return true;
					}
				}
				}
				return ClickOthers(mouseP);
			case MotionEvent.ACTION_UP:
	    	case MotionEvent.ACTION_POINTER_UP:
	    		//タッチが離されたときの動作
	    		for(j=0;j<DraggManager.dragObjList.size(); j++) {
	    			if( DraggManager.dragObjList.get(j).isDraggedBy(ptID) ) {
	    				DraggManager.dragObjList.get(j).releaseDragg();
	    				((Magnet)DraggManager.dragObjList.get(j)).setDragMode(-1);
	    			}
	    		}
	    		break;
	    	}
			return false;
		}
		
		
		switch ( event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			
			for(j=0; j<DraggManager.dragObjList.size(); j++ ) {
				Magnet q=((Magnet)DraggManager.dragObjList.get(j));
				if( q.isCatched(mouseP) || q.isCatchedN(mouseP) || q.isCatchedS(mouseP) ) {
					M.del(q);
					q.removeDraggManager();
					delObj(q);
					recalc();
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected void onSizeChanged(int ww1, int hh1, int oldw, int oldh) {
		super.onSizeChanged(ww1, hh1, oldw, oldh);
		w=ww1;
		h=hh1;
		A = new int[(w/2+1)][(h/2+1)];
		len=w/10;
		pBar.setMax(w);
		M.setHankei(w/40);
	}
	// 磁石の長さは半径の８倍
	float len;

	Magnets M;

	float N=5.0f;

	boolean showFFlg=true;
	boolean showYFlg=false;
	boolean showEFlg=true;


	int w,h;




	boolean recalcFlg;
	private boolean rewriteBGFlg=true;
	Bitmap bgBitmap;
	protected void rewriteBackGround() {
		bgBitmap=Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas gc = new Canvas(bgBitmap);

		Paint p=new Paint();
		p.setColor(Color.WHITE);
		gc.drawRect(0,0,w,h,p);

		if( calcEV == null ) {
			return;
		}
		if( calcEV.getStatus()==AsyncTask.Status.FINISHED ) {
			writeRikisen(gc);
		}   
	}
	
	
	@Override
	public void drawBackGround(Canvas gc){
		if( rewriteBGFlg ) {
			rewriteBackGround();
		}
		gc.drawBitmap(bgBitmap, 0, 0, new Paint());
	}
	

	@Override
	protected void setSituation() {
		if( DraggManager.someIsDragged()) {
			recalc();
		}
	}

	@Override
	protected void writeSubView(Canvas canvas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void calcNextEach(MovingObject movingObject) {
		// TODO Auto-generated method stub
		
	}

	void stopCalc() {
		if( calcEV == null ) {
			return;
		}
		stopFlg=true;
		calcEV.cancel(true);
		stopFlg=false;
	}


	void writeRikisen(Canvas gc)
	{
		int x,y;
		Paint p=new Paint();

		if( !showEFlg ) {
			return;
		}
//		int cut[][];
//		cut =new int[w/2+1][h/2];
//
//		int i,j;
//		for(i=0; i< w/2+1; i++ ) {
//			for(j=0; j< h/2;j++ ) {
//				cut[i][j]=0;
//			}
//		}
//
//
//		for( i=0 ; i<Q.count() ; i++ ) {
//			for( j=0 ; j< Q.Y(i)/2 ; j++ ) {
//				cut[((int)Q.X(i))/2][j]+= Q.Q(i);
//			}
//		}


		p.setColor(Color.BLUE);
		for( x=0 ; x<w/2 ; x++ ) {
			for( y=0 ; y<h/2 ; y++ ) {
				if( !M.isNear(2*x,2*y) ) {
					if( A[x][y] != A[x][y+1] 
							|| A[x][y] != A[x+1][y+1]//-2*N*cut[x+1][y] 
									|| A[x][y] != A[x+1][y]// -2*N*cut[x+1][y]
							){
						gc.drawRect(2*x,2*y,2*x+2,2*y+2,p);
					}
				}
			}
		}
	}


	CalcEV calcEV=null;
	boolean stopFlg=false;


	ProgressDialog progressDialog;
	private boolean pChargeFlg=true;
	private boolean qGenerateFlg=true;


	class CalcEV extends AsyncTask<Void,Integer,Boolean>  implements OnCancelListener{
		ProgressBar progressBar;
		public CalcEV(ProgressBar pb){
			progressBar=pb;
		}
		@SuppressWarnings("boxing")
		@Override
		protected void onProgressUpdate(Integer... values) {
			progressBar.setProgress(values[0]);
		}

		@SuppressWarnings("boxing")
		@Override
		protected Boolean doInBackground(Void... params) {
			int x,y;
			int qn=M.count();
			
			float XXN[][];
			float XXS[][];
			float YYN[][];
			float YYS[][];
			float XXXN2[][];
			float YYYN2[][];
			float XXXS2[][];
			float YYYS2[][];
			int i;
			
			if( !showEFlg ) {
				publishProgress(w);
				return true;
			}
			XXN=new float[qn][w/2+1];
			XXXN2=new float[qn][w/2+1];
			YYN=new float[qn][h/2+1];
			YYYN2=new float[qn][h/2+1];
			XXS=new float[qn][w/2+1];
			XXXS2=new float[qn][w/2+1];
			YYS=new float[qn][h/2+1];
			YYYS2=new float[qn][h/2+1];
			for( i=0 ; i<qn ; i++) {
				float angle=M.angle(i);
				for(x=0 ; x<=w/2 ; x++) {
					float xx=2*x-M.X(i);
					float xl=len*FloatMath.cos(angle);
					XXN[i][x]=xx-xl;
					XXXN2[i][x]=(xx-xl)*(xx-xl);
					XXS[i][x]=xx+xl;
					XXXS2[i][x]=(xx+xl)*(xx+xl);
				}
				for(y=0; y<h/2 ; y++) {
					float yy=2*y-M.Y(i);
					float yl=len*FloatMath.sin(angle);
					YYN[i][y]=yy-yl;
					YYYN2[i][y]=(yy-yl)*(yy-yl);
					YYS[i][y]=yy+yl;
					YYYS2[i][y]=(yy+yl)*(yy+yl);
				}	
				if( isCancelled()) {
					return false;
				}
			}

			
			if( showEFlg ) {
				synchronized(A) {
					for( x=0 ; x<= w/2 ; x++ ) {
						if( isCancelled()) {
							return false;
						}
						
						// ここでは、角度の計算を複素数の掛算で行う。
						// ＋チャージはx+iyをかける。
						// (R+iI)(x+iy)=Rx-Iy  +i (Ix+Ry)
						// (R+iI)/(x+iy)=(R+iI)*(x-iy)/(x^2+y^2)=( (Rx+Iy)+i(Ix-Ry) )/(x^2+y^2)
						for( y=0 ; y<= h/2 ; y++ ) {
							float Im=0f;
							float Re=1f;
							for( i=0 ; i<qn; i++) {
								float tmpRe=Re*XXN[i][x]-Im*YYN[i][y];
								Im = Re*YYN[i][y]+Im*XXN[i][x];
								float r2=FloatMath.sqrt(XXXN2[i][x]+YYYN2[i][y]);
								Re=tmpRe/r2;
								Im=Im/r2;
								
								tmpRe=Re*XXS[i][x]+Im*YYS[i][y];
								Im = -Re*YYS[i][y]+Im*XXS[i][x];
								r2=FloatMath.sqrt(XXXS2[i][x]+YYYS2[i][y]);
								Re=tmpRe/r2;
								Im=Im/r2;
							}
							double angle=Math.atan2(Im, Re);
							A[x][y]=(int)FloatMath.floor((float)(N*angle/Math.PI));
						}
						publishProgress(x*2);
					}
				}
			}

			publishProgress(w);
			return true;
		}	
		@SuppressWarnings("boxing")
		@Override
		protected void onPostExecute(Boolean result) {
			if( result ) {
				// 特に何もしない。
			}
		}
		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			
		}
	}


	public void setEFlg(boolean isChecked) {
		showEFlg=isChecked;
		rewriteBGFlg=true;
		if( showEFlg ) {
			recalc();
		} else {
			stopCalc();
		}
	}

	public void setPFlg(boolean isChecked) {
		pChargeFlg=isChecked;
	}
	public void setQFlg(boolean isChecked) {
		qGenerateFlg=isChecked;
	}
	
	protected void recalc() {
		rewriteBGFlg=true;
		if( calcEV != null ) {
			while(!calcEV.isCancelled() && calcEV.getStatus()==AsyncTask.Status.RUNNING ) {
				calcEV.cancel(true);
			}
		}
		calcEV=new CalcEV(pBar);
		calcEV.execute();
	}

	@Override
	public boolean ClickOthers(Vec2 m) {
		if( qGenerateFlg ) {
			Magnet q=M.addNew(m.X(),m.Y());
			addObj(q);
			q.setDraggManager(0);
			
			recalc();
			return true;
		} 
		int i;
		for(i=0; i<objs.size(); i++ ) {
			Magnet q= (Magnet)objs.get(i);
			if( q.isCatched(m) ) {
				M.del(q);
				delObj(q);
				q.removeDraggManager();
				q=null;
				recalc();
				return true;
			}
		}

		return false;
	}



	public void allErase() {
		
		int i;
		for( i=0 ; i<M.count(); i++) {
			delObj(M.magnetList.get(i));
			M.magnetList.get(i).removeDraggManager();
		}
		M.allErase();
		recalc();
	}
}

