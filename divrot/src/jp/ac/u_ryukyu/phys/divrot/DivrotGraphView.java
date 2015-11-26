package jp.ac.u_ryukyu.phys.divrot;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.view.View;

public class DivrotGraphView extends View {
	float wx;
	float hy;
	public DivrotGraphView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		Paint p=new Paint();
		p.setColor(Color.WHITE);
		canvas.drawRect(0,0,wx,hy, p);
		p.setColor(Color.BLACK);
		canvas.drawLine(0,hy/2,wx,hy/2,p);
		if( div ==null) {return;}
		if( rot ==null) {return;} //念の為。
		int i;
		Path pDiv=new Path();
		Path pRot=new Path();
		pDiv.moveTo(0, div[0]);
		pRot.moveTo(0, rot[0]);
		for(i=1;i<wx; i++) {
			pDiv.lineTo(i, div[i]);
			pRot.lineTo(i, rot[i]);
		}
		p.setStyle(Style.STROKE);
		p.setStrokeWidth(3f);
		p.setColor(Color.argb(120,255,0,255));
		canvas.drawPath(pDiv,p);
		canvas.drawLine(k, hy/2, k, div[k], p);
		p.setColor(Color.argb(120,200,200,0));
		canvas.drawPath(pRot,p);
		canvas.drawLine(k, hy/2, k, rot[k], p);
		
	} 

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		wx=w;
		hy=h;
		div=new float[w];
		rot=new float[w];
		int i;
		for(i=0; i<w; i++) {
			div[i]=rot[i]=hy/2;
		}
	}

	
	float div[];
	float rot[];
	
	public void setDiv(float i, float f) {
		div[(int)i]=hy/2-f;
	}
	public void setRot(float i, float f) {
		rot[(int)i]=hy/2-f;
	}
	int k;
	float ww;
	public void setI(float t0,float w) {
		ww=w;
		k=(int)(t0*(2*ww)/10f);
		invalidate();
	}
}
