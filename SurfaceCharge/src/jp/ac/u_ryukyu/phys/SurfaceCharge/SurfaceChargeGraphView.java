package jp.ac.u_ryukyu.phys.SurfaceCharge;


import jp.ac.u_ryukyu.phys.lib.Vec3;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.os.Message;
import android.util.FloatMath;
import android.view.View;
import android.widget.LinearLayout;

public class SurfaceChargeGraphView extends View {
	float wx,hy;
	SurfaceChargeSurfaceView view3d;
	private float z=0f;
	float  R=1f;
	float factorB; // このfactorBは電場をグラフに書く時に何倍するかの因子。
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint p=new Paint();
		p.setColor(Color.WHITE);
		canvas.drawRect(0, 0, this.getWidth(),this.getHeight(),p);

		float basez=(hy)/2f;
		
		factorB=0.4f*hy;
		
		p.setColor(Color.BLACK);
		canvas.drawLine(0, basez, wx,basez, p);

		float ww=(wx-0.2f*hy)/80f;
		
		canvas.drawLine(20f, 0, 20f, hy, p);
		
		Path zp=new Path();
		

		float piOverN=(float)(Math.PI)/N;
		float twoPiOverN=2f*piOverN;
		float phi=(t+0.5f)*twoPiOverN;
		float tN=FloatMath.floor(t/N);
		float theta=(tN+0.5f)*piOverN;

		Vec3 vecR;
		float sint=FloatMath.sin(theta);
		float cost=FloatMath.cos(theta);
		float sinp=FloatMath.sin(theta);
		float cosp=FloatMath.cos(theta);
		float zz;
		
		
		zz=(0.5f)*R/40-R;
		vecR=new Vec3(-R*sint*cosp,-R*sint*sinp,z-R*cost);
		
			
		float rr=vecR.NormSquare();
		float rrsq=FloatMath.sqrt(rr);
		Vec3 vecB1=vecR;
	
		vecB1.div(rr*rrsq);
		
		float Bx=vecB1.X();
		float By=vecB1.Y();
		float Bz=vecB1.Z();

		float stz=basez-Bz*factorB;
		zp.moveTo(20f, stz);
		int i;
	

		int startN=1;
		int endN;
		endN=80;

		for( i=startN ; i<endN ; i++ ) {
		
			zz=(i+0.5f)*R/40-R;
			
			vecR=new Vec3(-R*cost,-R*sint,z);
			
			
			rr=vecR.NormSquare();
			rrsq=FloatMath.sqrt(rr);

			vecB1=vecR;
		
			vecB1.div(rr*rrsq);
			
			
			float deltaBz =vecB1.Z();
			Bz += deltaBz;
			zp.lineTo(i*ww+20f, basez-deltaBz*factorB);
		}
		
		zp.lineTo(80*ww+20f, stz);
		view3d.setTotalB(0f,0f,Bz); // view3dには、和を送る。
		Bx /=80;
		By /=80;
		Bz /=80;
		
		p.setStyle(Style.STROKE);
		p.setStrokeWidth(3f);
		p.setColor(Color.rgb(0, 200, 200));
		canvas.drawPath(zp, p);

		
		ww=(wx-0.2f*hy)/N;
		
		for( i=startN ; i<N ; i++ ) {
			zz=(2*i+1)*R/N-R;
			
				vecR=new Vec3(-R*cost,-R*sint,z);
			
			
			rr=vecR.NormSquare();
			rrsq=FloatMath.sqrt(rr);

			vecB1=vecR;
		
			vecB1.div(rr*rrsq);
			
			float deltaBx =vecB1.X();
			float deltaBy =vecB1.Y();
			float deltaBz =vecB1.Z();
			if( i== t) {
				p.setStyle(Style.FILL);
			} else {
				p.setStyle(Style.STROKE);
			}
			p.setStrokeWidth(1f);
			float top=basez-deltaBz*factorB;
			if( top > hy ) { top=hy;}
			p.setColor(Color.rgb(0, 200, 200));
			canvas.drawRect(i*ww+20f, top,(i+1)*ww+20f,basez, p);
		}

		p.setTextSize(24f);
		p.setStyle(Style.STROKE);
		p.setColor(Color.rgb(0, 200, 200));
		canvas.drawLine(20f,basez-Bz*factorB,5f,basez-Bz*factorB-10f,p);
		canvas.drawLine(20f,basez-Bz*factorB,5f,basez-Bz*factorB+10f,p);
		canvas.drawLine(5f,basez-Bz*factorB-10f,5f,basez-Bz*factorB+10f,p);
		p.setStyle(Style.FILL);
		canvas.drawText("ｚ成分", N*ww+20f, basez, p);

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		wx=w; hy=h;
		int ww=((View)getParent()).getWidth();
		int hh=((View)getParent()).getHeight();
		if( ww-hh != w ) {
			this.setLayoutParams(new LinearLayout.LayoutParams(ww-hh, hh));
			view3d.setLayoutParams(new LinearLayout.LayoutParams(hh, hh));
		}
	}

	public SurfaceChargeGraphView(Context context,SurfaceChargeSurfaceView view3) {
		super(context);
		// TODO Auto-generated constructor stub
		view3d=view3;
	}

	int N=10;
	int t=0;
	
	int delay=50;
	protected boolean isAttached;
	protected boolean nowGo;
	

	public void setN(int n) {
		N=n;
		view3d.setN(n);
		t=0;
	}
	public void setR(float n) {
		R=n;
		view3d.setR(n);
		t=0;
	}
	
	public void stop() { nowGo=false; }
	public void start() { nowGo=true; handler_start();}
	protected void handler_start() {
		Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (isAttached) {
					if( nowGo ) {
						t++;
						if( t>=N ) {
							t=0;
						}
						invalidate();
						view3d.setT(t);
						view3d.invalidate();
						sendEmptyMessageDelayed(0, delay);
					}
				}
			}
		};
		isAttached = true;
		handler.sendEmptyMessageDelayed(0, delay);
	}
	@Override
	protected void onAttachedToWindow() {
		start();
		super.onAttachedToWindow();
	}
	@Override
	protected void onDetachedFromWindow() {
		isAttached = false;
		super.onDetachedFromWindow();
	}

	public void setZ(float f) {
		z=f;
		view3d.setZ(z);
	}
}
