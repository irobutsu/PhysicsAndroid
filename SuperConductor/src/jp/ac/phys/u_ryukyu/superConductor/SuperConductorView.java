package jp.ac.phys.u_ryukyu.superConductor;

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
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class SuperConductorView extends View {
    int V[][];
    double phi0=0.0;
    double L=0.0;

    int hankei=10;
    float atsumi=1;
    float wakka=50;
    float x1=150;
    float y1=240;
    float x3=120;
    float y3=100;

    double angle1=0.0;
    double angle2=0.0;
    double len=30.0;
    double delta;


    int xmin,xmax,ymin,ymax,xv,yv;

    int nowDrag=0;

	Paint p;
	public SuperConductorView(Context context) {
		super(context);
//		this.timerStop();
		Q=new Charges();
		p=new Paint();
		L=0.0;
		int i;
		for( i=(int)atsumi ; i< wakka-atsumi ; i++ ) {
		    L -= Math.log((double)i) -  Math.log((double)(wakka-i));
		}
		L *= 2.0;
	}
	float shiftx=0;
	float shifty=0;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int index=(event.getAction()&MotionEvent.ACTION_POINTER_ID_MASK)>>
        	MotionEvent.ACTION_POINTER_ID_SHIFT;
		Vec2 mouseP=new Vec2(event.getX(index),event.getY(index));
		switch ( event.getActionMasked()) {
		case MotionEvent.ACTION_MOVE:
  			if( nowDrag == 1) {
				x1=mouseP.X();
				y1=mouseP.Y();
				if( x1 < hankei ) {
	                x1=hankei;
	            }
	            if( x1 > w-hankei ) {
	                x1= w-hankei;
	            }
	            if( y1 < hankei ) {
	                y1=hankei;
	            }
	            if( y1 > h-hankei ) {
	                y1= h-hankei;
	            }
	            Q.setXY(0, x1, y1);
	            invalidate();
	            return true;
			} else if( nowDrag == 3 ) {
				x3=mouseP.X()+shiftx;
				y3=mouseP.Y()+shifty;
				if( x3 < atsumi ) {
					x3=atsumi;
				}
				if( x3 > w-atsumi ) {
					x3= w-atsumi;
				}
				if( y3 < atsumi ) {
					y3=atsumi;
				}
				if( y3 > h-atsumi ) {
					y3= h-atsumi;
				}
				invalidate();
				return true;
			}
			break;
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			float xx=x1;
	        float yy=y1;
	        float mx=mouseP.X();
	        float my=mouseP.Y();
	        if( mx > xx-hankei && mx < xx+hankei && my > yy-hankei && my < yy+hankei ) {
	            nowDrag=1;
	            return true;
	        }

	        xx=x3;
	        yy=y3;
	        if( mx > xx-atsumi*4 && mx < xx+wakka+atsumi*4 && my > yy-atsumi*4 && my < yy+atsumi*4 ) {
	            nowDrag=3;
	            shiftx=xx-mx;
	            shifty=yy-my;
	            return true;
	        }
	        break;
		case MotionEvent.ACTION_POINTER_UP:
		case MotionEvent.ACTION_UP:
			nowDrag=0;
			return true;
		}
		return false;
	}
	double calcPhi() {
		double phi;
		phi = 2*Math.atan2((double)(-y3+y1),(double)(x3+atsumi-x1)-0.5);
		phi -= 2*Math.atan2((double)(-y3+y1),(double)(x3+wakka-atsumi-x1)+0.5);

		return phi;
	}
	boolean superFlg=false;

	@Override
	protected void onSizeChanged(int ww1, int hh1, int oldw, int oldh) {
		super.onSizeChanged(ww1, hh1, oldw, oldh);
		w=ww1;
		h=hh1;
		//int ww=((View)getParent()).getWidth();
		//int hh=((View)getParent()).getHeight();
		//if( hh/2 != h ) {
		//	this.setLayoutParams(new LinearLayout.LayoutParams(ww, hh/2));
		//	view3d.setLayoutParams(new LinearLayout.LayoutParams(ww, hh-hh/2));
		//}
		V = new int[w/3+2][h/3+2];
		hankei=w/20;
		Q.clear();
		Q.setHankei(hankei);
		Q.addNew(w*0.5f, h*0.75f,1);
		x1=w*0.5f;
		y1=h*0.75f;
		y3=h*0.5f;
		x3=w*0.35f;
		wakka=w*0.3f;
		atsumi=w*0.006f;
	}


	Charges Q;

	float N=5.0f;

	boolean showFFlg=true;
	boolean showYFlg=false;
	boolean showEFlg=true;
	boolean showVFlg=false;

	int w,h;




	@Override
	public void onDraw(Canvas gc){
		int i,j;
		 

		double denryu;
		if( superFlg ) {
		    denryu = (calcPhi()-phi0)/L;
		} else {
		    denryu=0.0;
		}
		int x,y;
		if( V==null) {
			V=new int[w/4+2][h/4+2];
		}
		double XX1[]=new double[(w+1)/4 +1];
		double XX3[]=new double[(w+1)/4 +1];
		double YY1[]=new double[(h+1)/4 +1];
		double YY32[]=new double[(h+1)/4 +1];
		for(i=0 ; i<= (w+1)/4 ; i++ ) {
			XX1[i]=4*(i-1)-x1;
			XX3[i]=4*(i-1)-x3;
		}
		for(j=0 ; j<= (h+1)/4 ; j++ ) {
			YY1[j]=4*(j-1)-y1;
			YY32[j]=(4*(j-1)-y3)*(4*(j-1)-y3);
		}
		
		for(i=0 ; i<= (w+1)/4 ; i++ ) {
			for(j=0 ; j<= (h+1)/4 ; j++ ) {
				double v=0;
				v += 2*Math.atan2(XX1[i],YY1[j]);
				double sq=1.0;
				if( XX3[i] != 0 || YY32[j] != 0 ) {
					sq  *= (((XX3[i])*(XX3[i])+(YY32[j])));
				}
				if( XX3[i] != wakka || YY32[j] != 0 ) { 
					sq /= (((XX3[i]-wakka)*(XX3[i]-wakka)+(YY32[j])));
				}
				if( sq != 1.0) {
					v += denryu*Math.log(sq);
				}
				v *= 10.0;
				v /= Math.PI;
				V[i][j] = (int)Math.floor(v);
			}
		}

		p.setColor(Color.WHITE);
		gc.drawRect(0,0,w,h,p);

		int diff;
		
		for( x=0 ; x< w ; x+=4 ) {
			for( y=0 ; y< h ; y+=4 ) {
				float xx1=x-x1;
				float yy1=y-y1;
				if( (xx1)*(xx1)+(yy1)*(yy1) > hankei*hankei 
						&&(x-x3)*(x-x3)+(y-y3)*(y-y3) > atsumi*atsumi 
						&&(x-x3-w)*(x-x3-w)+(y-y3)*(y-y3) > atsumi*atsumi 
						) {
					diff = V[x/4+1][y/4+1] - V[x/4][y/4+1];
				    if( (xx1 <= 4 && xx1 >= -4) && y<y1 ) {
                        if( diff == 2*20 ) {
                        	diff=0;
                        }
                    }
					if( diff != 0 ) {
						p.setColor(Color.BLUE);
						gc.drawRect(x-2,y-2,x+2,y+2,p);
					} else {
						
						diff = V[x/4+1][y/4+1] - V[x/4+1][y/4];
						
						if( diff != 0 ) {
							p.setColor(Color.BLUE);
							gc.drawRect(x-2,y-2,x+2,y+2,p);
						}
					}
				}
			}
		}
		Q.drawMarks(gc);

		if( superFlg ) {
			p.setColor(Color.GRAY);
		} else {
			p.setColor(Color.BLACK);
		}
		gc.drawRect(x3-2*atsumi,y3-4*atsumi,x3+wakka+4*atsumi,y3+8*atsumi,p);
	}
	public boolean toggleSuper() {
	   if( superFlg ) {
		   superFlg=false;
		   invalidate();
		   return false;
	   } else {
		   superFlg=true;
		   phi0=calcPhi();
		   invalidate();
		   return true;
	   }
	}
}

