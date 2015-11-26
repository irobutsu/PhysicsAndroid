package jp.ac.u_ryukyu.phys.charges;


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

class ChargesView extends DraggManageView {
	private int[][] A;
	private ProgressBar pBar;

	public ChargesView(Context context,ProgressBar pbar) {
		super(context);
		pBar=pbar;
//		this.timerStop();
		Q=new Charges();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if( qGenerateFlg ) {
			// ドラッグモードでは、普通のタッチイベント。
			return super.onTouchEvent(event);
		}

		int j;
		int index=(event.getAction()&MotionEvent.ACTION_POINTER_ID_MASK)>>
        	MotionEvent.ACTION_POINTER_ID_SHIFT;
		Vec2 mouseP=new Vec2(event.getX(index),event.getY(index));
		switch ( event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			
			for(j=0; j<DraggManager.dragObjList.size(); j++ ) {
				Charge q=((Charge)DraggManager.dragObjList.get(j));
				if( q.isCatched(mouseP) ) {
					Q.del(q);
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
		pBar.setMax(w);
		Q.setHankei(w/40);
	}

	private Charges Q;

	float N=5.0f;

	boolean showFFlg=true;
	boolean showYFlg=false;
	boolean showEFlg=true;


//	int xmin,xmax,ymin,ymax,xv,yv;


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
				if( !Q.isNear(2*x,2*y) ) {
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
			int qn=Q.count();
			
			float XX[][];
			float YY[][];
			float XXX2[][];
			float YYY2[][];
			int Ch[];
			int i;
			
			if( !showEFlg ) {
				publishProgress(w);
				return true;
			}
			Ch=new int[qn];
			XX=new float[qn][w/2+1];
			XXX2=new float[qn][w/2+1];
			YY=new float[qn][h/2+1];
			YYY2=new float[qn][h/2+1];
			for( i=0 ; i<qn ; i++) {
				Ch[i]=Q.Q(i);
				for(x=0 ; x<=w/2 ; x++) {
					float xx=2*x-Q.X(i);
					XX[i][x]=xx;
					XXX2[i][x]=xx*xx;
				}
				for(y=0; y<h/2 ; y++) {
					float yy=2*y-Q.Y(i);
					YY[i][y]=yy;
					YYY2[i][y]=yy*yy;
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
								if( Ch[i]>0 ) {
									float tmpRe=Re*XX[i][x]-Im*YY[i][y];
									Im = Re*YY[i][y]+Im*XX[i][x];
									float r2=FloatMath.sqrt(XXX2[i][x]+YYY2[i][y]);
									Re=tmpRe/r2;
									Im=Im/r2;
								} else {
									float tmpRe=Re*XX[i][x]+Im*YY[i][y];
									Im = -Re*YY[i][y]+Im*XX[i][x];
									float r2=FloatMath.sqrt(XXX2[i][x]+YYY2[i][y]);
									Re=tmpRe/r2;
									Im=Im/r2;
								}
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
			Charge q;
			if( pChargeFlg ) {
				q=Q.addNew(m.X(), m.Y(), 1);
			} else {
				q=Q.addNew(m.X(), m.Y(), -1);
			}
			addObj(q);
			q.setDraggManager(0);
			
			recalc();
			return true;
		} 
		int i;
		for(i=0; i<objs.size(); i++ ) {
			Charge q= (Charge)objs.get(i);
			if( q.isCatched(m) ) {
				Q.del(q);
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
		for( i=0 ; i<Q.count(); i++) {
			delObj(Q.chargeList.get(i));
			Q.chargeList.get(i).removeDraggManager();
		}
		Q.allErase();
		recalc();
	}
}

