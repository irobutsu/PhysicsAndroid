package jp.ac.u_ryukyu.phys.gsense;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

public class GsenseGraphView extends View {
	float wx,hy;
	GsenseSurfaceView view3d;
	float gtable[];
	float oldgtable[];
	float gxtable[];
	float oldgxtable[];
	float gytable[];
	float oldgytable[];
	float gztable[];
	float oldgztable[];
	
	int gcnt=0;
	Path yp=new Path();
	Paint p=new Paint();
	private boolean xFlg=false;
	private boolean yFlg=false;
	private boolean zFlg=false;
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		int i;
		gxtable[gcnt]=gx;
		gytable[gcnt]=gy;
		gztable[gcnt]=gz;
		gtable[gcnt++]=g;
		if( gcnt >= dataN ) {
			gcnt=0;
			for(i=0; i<dataN ; i++) {
				oldgtable[i]=gtable[i];
				oldgxtable[i]=gxtable[i];
				oldgytable[i]=gytable[i];
				oldgztable[i]=gztable[i];
			}
		}
		
		p.setColor(Color.WHITE);
		canvas.drawRect(0, 0, this.getWidth(),this.getHeight(),p);

		float h1=hy/3f;
		float h2=2f*h1;
		
		float basex=h1/2f;
		float basey=(h1+h2)/2f;
		float basez=(h2+hy)/2f;
		
		
		p.setColor(Color.rgb(200,200,200));
		canvas.drawLine(0, basex, wx,basex, p);
		canvas.drawLine(0, basey, wx,basey, p);
		p.setColor(Color.BLACK);
		canvas.drawLine(0, basez, wx,basez, p);

		float ww=(wx-0.2f*hy)/dataN;
		
		canvas.drawLine(20f, 0, 20f, hy, p);
		
		p.setStyle(Style.STROKE);
		float oldgmax=-100000;
		float oldgmin=10000;
		float oldgxmax=-100000;
		float oldgxmin=10000;
		float oldgymax=-100000;
		float oldgymin=10000;
		float oldgzmax=-100000;
		float oldgzmin=10000;
		if( oldgtable[0]!= -10000) {
			yp.rewind();
			yp.moveTo(20f,basez-h1*0.1f*oldgtable[0]);
			for(i=1;i<dataN; i++) {
				yp.lineTo(20f+i*ww, basez-h1*0.1f*oldgtable[i]);
			}
			p.setColor(Color.rgb(200, 200,255));
			canvas.drawPath(yp, p);
			if( xFlg ) {
				yp.rewind();
				yp.moveTo(20f,basez-h1*0.1f*oldgxtable[0]);
				for(i=1;i<dataN; i++) {
					yp.lineTo(20f+i*ww, basez-h1*0.1f*oldgxtable[i]);
				}
				p.setColor(Color.rgb(255, 255,200));
				canvas.drawPath(yp, p);
			}
			if( yFlg ) {
				yp.rewind();
				yp.moveTo(20f,basez-h1*0.1f*oldgytable[0]);
				for(i=1;i<dataN; i++) {
					yp.lineTo(20f+i*ww, basez-h1*0.1f*oldgytable[i]);
				}
				p.setColor(Color.rgb(255, 200,255));
				canvas.drawPath(yp, p);
			}
			if( zFlg ) {
				yp.rewind();
				yp.moveTo(20f,basez-h1*0.1f*oldgztable[0]);
				for(i=1;i<dataN; i++) {
					yp.lineTo(20f+i*ww, basez-h1*0.1f*oldgztable[i]);
				}
				p.setColor(Color.rgb(200, 255,0));
				canvas.drawPath(yp, p);
			}
		}
		yp.rewind();
		


		
		float sty=basez-h1*0.1f*gtable[0];
	

		
		p.setStyle(Style.STROKE);
	
		p.setStrokeWidth(3f);

		yp.moveTo(20f, sty);
		for(i=1; i<gcnt; i++) {
			if( oldgmax < gtable[i]) {
				oldgmax=gtable[i];
			}
			if( oldgmin > gtable[i]) {
				oldgmin=gtable[i];
			}
			yp.lineTo(20f+i*ww, basez-h1*0.1f*gtable[i]);
		}
		
		p.setColor(Color.rgb(0, 0,200));
		canvas.drawPath(yp, p);
		
		if( yFlg ) {
			yp.rewind();
			yp.moveTo(20f,basez-h1*0.1f*gytable[0]);
			for(i=1; i<gcnt; i++) {
				if( oldgymax < gytable[i]) {
					oldgymax=gytable[i];
				}
				if( oldgymin > gytable[i]) {
					oldgymin=gytable[i];
				}
				yp.lineTo(20f+i*ww, basez-h1*0.1f*gytable[i]);
			}
			
			p.setColor(Color.rgb(200,0,200));
			canvas.drawPath(yp, p);
		}
		if( zFlg ) {
			yp.rewind();
			yp.moveTo(20f,basez-h1*0.1f*gztable[0]);
			for(i=1; i<gcnt; i++) {
				if( oldgzmax < gztable[i]) {
					oldgzmax=gztable[i];
				}
				if( oldgzmin > gztable[i]) {
					oldgzmin=gztable[i];
				}
				yp.lineTo(20f+i*ww, basez-h1*0.1f*gztable[i]);
			}
			
			p.setColor(Color.rgb(0,200,0));
			canvas.drawPath(yp, p);
		}
		if( xFlg ) {
			yp.rewind();
			yp.moveTo(20f,basez-h1*0.1f*gxtable[0]);
			for(i=1; i<gcnt; i++) {
				if( oldgxmax < gxtable[i]) {
					oldgxmax=gxtable[i];
				}
				if( oldgxmin > gxtable[i]) {
					oldgxmin=gxtable[i];
				}
				yp.lineTo(20f+i*ww, basez-h1*0.1f*gxtable[i]);
			}
			
			p.setColor(Color.rgb(200,200,0));
			canvas.drawPath(yp, p);
		}
		yp.rewind();

		p.setColor(Color.rgb(200, 200, 0));

		p.setTextSize(24f);
		p.setColor(Color.rgb(0, 0, 200));
		p.setStyle(Style.FILL);
		canvas.drawText(getContext().getString(R.string.g)+g+"m/s", 20f, basex, p);
		p.setColor(Color.rgb(200,200,255));
		canvas.drawText("Max="+oldgmax+"m/s", 40f, basex+40f, p);
		canvas.drawText("Min="+oldgmin+"m/s", 40f, basex+60f, p);
		p.setTextSize(20f);
		if( xFlg ) {
			p.setColor(Color.rgb(200,200, 0));
			canvas.drawText(getContext().getString(R.string.x_comp)+gx+"m/s", (dataN-20)*ww+20f, basex-60f, p);
			canvas.drawText("Max="+oldgxmax+"m/s", (dataN-20)*ww+20f, basex-40f, p);
			canvas.drawText("Min="+oldgxmin+"m/s", (dataN-20)*ww+20f, basex-20f, p);
		}
		if( yFlg ) {
			p.setColor(Color.rgb(200,0,200));
			canvas.drawText(getContext().getString(R.string.y_comp)+gy+"m/s", (dataN-20)*ww+20f, basex+20f, p);
			canvas.drawText("Max="+oldgymax+"m/s", (dataN-20)*ww+20f, basex+40f, p);
			canvas.drawText("Min="+oldgymin+"m/s", (dataN-20)*ww+20f, basex+60f, p);
		}
		if( zFlg ) {
			p.setColor(Color.rgb(0,200, 0));
			canvas.drawText(getContext().getString(R.string.z_comp)+gz+"m/s", (dataN-20)*ww+20f, basex+100f, p);
			canvas.drawText("Max="+oldgzmax+"m/s", (dataN-20)*ww+20f, basex+120f, p);
			canvas.drawText("Min="+oldgzmin+"m/s", (dataN-20)*ww+20f, basex+140f, p);
		}
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
	int dataN=120;
	public GsenseGraphView(Context context,GsenseSurfaceView view3) {
		super(context);
		// TODO Auto-generated constructor stub
		view3d=view3;
		gtable=new float[dataN];
		oldgtable=new float[dataN];
		gxtable=new float[dataN];
		oldgxtable=new float[dataN];
		gytable=new float[dataN];
		oldgytable=new float[dataN];
		gztable=new float[dataN];
		oldgztable=new float[dataN];
		oldgtable[0]=-10000;
	}

	float t;
	float stepT=0.05f;
	int delay=50;
	protected boolean isAttached;
	protected boolean nowGo;
	private float gx;
	private float gy;
	private float gz;
	private float g;
	private boolean aFlg=true;
	private int turn=1;
	
	
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
						view3d.setG(gx,gy,gz,g);
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


	public void setG(float x2, float y2, float z2) {
		switch( turn ) {
		case 0:
			gx=x2;
			gy=y2;
			break;
		case 1:
			gx=-y2;
			gy=x2;
			break;
		case 2:
			gx=-x2;
			gy=-y2;
			break;
		case 3:
			gx=y2;
			gy=-x2;
			break;
		default:
			gx=x2;
			gy=y2;
			break;
		}
		gz=z2;
		g=(float) Math.sqrt(gx*gx+gy*gy+gz*gz);
	}

	public void setXFlg(boolean isChecked) {
		xFlg=isChecked;
	}

	public void setYFlg(boolean isChecked) {
		yFlg=isChecked;
	}
	public void setZFlg(boolean isChecked) {
		zFlg=isChecked;
	}

	public void setAFlg(boolean isChecked) {
		aFlg=isChecked;
	}

	public void turn() {
		turn++;
		if( turn >3) {
			turn=0;
		}
		view3d.SetTurn(turn);
	}
}
