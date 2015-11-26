package jp.ac.u_ryukyu.phys.BiotSavart;

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

public class BiotSavartGraphView extends View {
	float wx,hy;
	BiotSavartSurfaceView view3d;
	private float z=1f;
	private float x=0f;
	final float  RR=1f;
	float factorB; // このfactorBは磁場をグラフに書く時に何倍するかの因子。
	Paint p=new Paint();
	Path xp;
	Path yp;
	Path zp;
	Vec3 vecJ;
	Vec3 vecR;
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	
		p.setColor(Color.WHITE);
		canvas.drawRect(0, 0, this.getWidth(),this.getHeight(),p);

		float h1=hy/3f;
		float h2=2f*h1;
		
		float basex=h1/2f;
		float basey=(h1+h2)/2f;
		float basez=(h2+hy)/2f;
		
		factorB=0.4f*hy;
		
		p.setColor(Color.BLACK);
		canvas.drawLine(0, basex, wx,basex, p);
		canvas.drawLine(0, basey, wx,basey, p);
		canvas.drawLine(0, basez, wx,basez, p);

		float ww=(wx-0.2f*hy)/80f;
		
		canvas.drawLine(20f, 0, 20f, hy, p);
		

		


		vecJ = new Vec3(0f,1f,0f);


		vecR=new Vec3(x-RR,0f,z);
		
		float rr=vecR.NormSquare();
		float rrsq=FloatMath.sqrt(rr);
		Vec3 vecB1=vecJ.Cross(vecR);
	
		vecB1.div(rr*rrsq);
		
		float Bx=vecB1.X();
		float By=vecB1.Y();
		float Bz=vecB1.Z();

		float stx=basex-Bx*factorB;
		float sty=basey-By*factorB;
		float stz=basez-Bz*factorB;
		xp.moveTo(20f, stx);
		yp.moveTo(20f, sty);
		zp.moveTo(20f, stz);
		int i;
		float omegat;
		float cos;
		float sin;

		for( i=1 ; i<80 ; i++ ) {
			omegat=((float)(i*Math.PI/40.0));
			sin=FloatMath.sin(omegat);
			cos=FloatMath.cos(omegat);
		
			vecJ=new Vec3(-sin,cos,0f);
		
			vecR=new Vec3(x-RR*cos,-RR*sin,z);
			
			rr=vecR.NormSquare();
			rrsq=FloatMath.sqrt(rr);

			vecB1=vecJ.Cross(vecR);
		
			vecB1.div(rr*rrsq);
			
			float deltaBx =vecB1.X();
			float deltaBy =vecB1.Y();
			float deltaBz =vecB1.Z();
			Bx += deltaBx;
			By += deltaBy;
			Bz += deltaBz;
			xp.lineTo(i*ww+20f, basex-deltaBx*factorB);
			yp.lineTo(i*ww+20f, basey-deltaBy*factorB);
			zp.lineTo(i*ww+20f, basez-deltaBz*factorB);
		}
		xp.lineTo(80*ww+20f, stx);
		yp.lineTo(80*ww+20f, sty);
		zp.lineTo(80*ww+20f, stz);
		
		Bx /=80;
		By /=80;
		Bz /=80;
		// view3dに送られる(Bx,By,Bz)は、「磁場の平均」
		view3d.setTotalB(Bx,By,Bz);
		
		p.setStyle(Style.STROKE);
		p.setStrokeWidth(3f);
		
		p.setColor(Color.rgb(200, 200, 0));
		canvas.drawPath(xp, p);
		p.setColor(Color.rgb(200, 0,200));
		canvas.drawPath(yp, p);
		p.setColor(Color.rgb(0, 200, 200));
		canvas.drawPath(zp, p);
		
		omegat=t*((float)(Math.PI/4.0));
		sin=FloatMath.sin(omegat);
		cos=FloatMath.cos(omegat);
		
		vecJ=new Vec3(-sin,cos,0f);
		
		vecR=new Vec3(x-RR*cos,-RR*sin,z);
		
		rr=vecR.NormSquare();
		rrsq=FloatMath.sqrt(rr);

		vecB1=vecJ.Cross(vecR);
	
		vecB1.div(rr*rrsq);

		p.setColor(Color.rgb(200, 200, 0));

		i=(int) (t*10f);
		i= i%80;
		p.setTextSize(24f);
		p.setColor(Color.rgb(200, 200, 0));
		p.setStyle(Style.STROKE);
		canvas.drawLine(20f,basex-Bx*factorB,5f,basex-Bx*factorB-10f,p);
		canvas.drawLine(20f,basex-Bx*factorB,5f,basex-Bx*factorB+10f,p);
		canvas.drawLine(5f,basex-Bx*factorB-10f,5f,basex-Bx*factorB+10f,p);
		canvas.drawLine(i*ww+20f, basex-factorB*vecB1.X(), i*ww+20f, basex, p);
		p.setStyle(Style.FILL);
		canvas.drawText(getContext().getString(R.string.x_comp), 80*ww+20f, basex, p);
		p.setStyle(Style.STROKE);
		p.setColor(Color.rgb(200, 0,200));
		canvas.drawLine(20f,basey-By*factorB,5f,basey-By*factorB-10f,p);
		canvas.drawLine(20f,basey-By*factorB,5f,basey-By*factorB+10f,p);
		canvas.drawLine(5f,basey-By*factorB-10f,5f,basey-By*factorB+10f,p);
		canvas.drawLine(i*ww+20f, basey-factorB*vecB1.Y(), i*ww+20f, basey, p);
		p.setStyle(Style.FILL);
		canvas.drawText(getContext().getString(R.string.y_comp), 80*ww+20f, basey, p);
		p.setStyle(Style.STROKE);
		p.setColor(Color.rgb(0, 200, 200));
		canvas.drawLine(20f,basez-Bz*factorB,5f,basez-Bz*factorB-10f,p);
		canvas.drawLine(20f,basez-Bz*factorB,5f,basez-Bz*factorB+10f,p);
		canvas.drawLine(5f,basez-Bz*factorB-10f,5f,basez-Bz*factorB+10f,p);
		canvas.drawLine(i*ww+20f, basez-factorB*vecB1.Z(), i*ww+20f, basez, p);
		p.setStyle(Style.FILL);
		canvas.drawText(getContext().getString(R.string.z_comp), 80*ww+20f, basez, p);

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

	public BiotSavartGraphView(Context context,BiotSavartSurfaceView view3) {
		super(context);
		// TODO Auto-generated constructor stub
		view3d=view3;
		xp=new Path();
		yp=new Path();
		zp=new Path();
	}

	float t;
	float stepT=0.05f;
	int delay=50;
	protected boolean isAttached;
	protected boolean nowGo;
	
	
	public void stop() { nowGo=false; }
	public void start() { nowGo=true; handler_start();}
	protected void handler_start() {
		Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (isAttached) {
					if( nowGo ) {
						t += stepT;
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

	public void setX(float f) {
		x=f;
		view3d.setX(x);
	}
}
