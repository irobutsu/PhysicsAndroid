package jp.ac.u_ryukyu.phys.bhthru;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.FloatMath;
import android.view.View;
import android.widget.LinearLayout;

public class BHthruSubView extends View {
	float aa=1f;
	float dd=30f;
	float ll=50f;
	int w;
	int h;
	BHthruView mainview;
	
	@Override
	protected void onSizeChanged(int www, int hhh, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(www, hhh, oldw, oldh);
		w=www; h=hhh;
		int ww=((View)getParent()).getWidth();
		int hh=((View)getParent()).getHeight();
		if( ww-hh != www ) {
			this.setLayoutParams(new LinearLayout.LayoutParams(ww-hh, hh));
			mainview.setLayoutParams(new LinearLayout.LayoutParams(hh, hh));
		}
	}
	public BHthruSubView(Context context,BHthruView v) {
		super(context);
		// TODO Auto-generated constructor stub
		mainview=v;
	}
	int T=45;
	void setAngle(int a) {
		T=a;
		postInvalidate();
		invalidate();
	}
	public void setAA(float a) {
		aa=a;invalidate();
	}
	public void setDD(float a) {
		dd=a;invalidate();
	}
	public void setLL(float a) {
		ll=a;invalidate();
	}
	public void OnDraw(Canvas gc)
	{
		if( gc==null ) {
			return;
		}
		Paint p=new Paint();
		p.setColor(Color.BLUE);
		p.setStyle(Paint.Style.FILL);
		gc.drawRect(0,0,w,h,p);


		float ratio=h/200f;

		int aaa= ((int)(aa*ratio))/10;


		int ddd=((int)(dd*ratio))/10;

		int x1=w/2;
		int y1=h/2-ddd;
		int x2,y2;
		int i;
		float hh=0.003f;
		float xx=aa/dd;



		p.setColor(Color.RED);

		
		if( T==0 ) {
			gc.drawLine(x1,y1,x1,h/2,p);
		} else if( T==180 ) {
			gc.drawLine(x1,y1,x1,0,p);
		} else {
			float angle=(float)(T*Math.PI/180.0);
			float tanT=FloatMath.sqrt(1f-xx)*FloatMath.sin(angle)/FloatMath.cos(angle);
			float v=-xx*tanT;

			for(i=1 ; i<5000 ; i++ ) {
				float k1=hh*(1.5f*xx*xx-xx);
				float j1=hh*v;
				float k2=hh*(1.5f*(xx+j1*0.5f)*(xx+j1*0.5f)-xx-j1*0.5f);
				float j2=hh*(v+0.5f*k1);
				float k3=hh*(1.5f*(xx+j2*0.5f)*(xx+j2*0.5f)-xx-j2*0.5f);
				float j3=hh*(v+0.5f*k2);
				float k4=hh*(1.5f*(xx+j3)*(xx+j3)-xx-j3);
				float j4=hh*(v+k3);

				v += (k1+2*k2+2*k3+k4)/6.0f;
				xx += (j1+2*j2+2*j3+j4)/6.0f;
				if( xx >= 1.0 ) {
					break;
				}
				if(xx <= 0.0 ) {
					break;
				}

				float rrr=aa/xx*ratio;
				x2=w/2+(int)(rrr*FloatMath.sin(hh*i));
				y2=h/2-(int)(rrr*FloatMath.cos(hh*i));
				gc.drawLine(x1,y1,x2,y2,p);

				if( x2<0 || x2> w || y2<0 || y2>h ) {
					break;
				}

				x1=x2;
				y1=y2;
			}
		}

		int lll=((int)(ll*ratio))/10;

		p.setColor(Color.BLACK);
		gc.drawLine(w/2-(int)(50*ratio),h/2+lll-1,w/2+(int)(50*ratio),h/2+lll-1,p);
		gc.drawOval(new RectF(w/2-aaa,h/2-aaa,aaa*2,aaa*2),p);
	}

}
