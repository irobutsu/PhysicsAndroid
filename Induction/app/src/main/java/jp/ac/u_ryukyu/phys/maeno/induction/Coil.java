package jp.ac.u_ryukyu.phys.maeno.induction;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import jp.ac.u_ryukyu.phys.maeno.physlib.MovingObject;
import jp.ac.u_ryukyu.phys.maeno.physlib.Vec2;


public class Coil extends MovingObject {
	protected float r;
	private float centerX;
	public Coil(float rr, Vec2 xx, float cx) {
		super(xx, Vec2.zero);
		r=rr;
		centerX=cx;
	}
	public float R() {return r;}
	
	protected void write_sub(Canvas cc,float xx,float yy) {
		Paint p=new Paint();
		p.setColor(Color.argb(100, 100, 100, 100));
		cc.drawRect(xx - r, yy - 0.2f * r, xx + r, yy + 0.2f * r, p);
		if( isDragged()) {	
			p.setStyle(Paint.Style.STROKE);
			p.setColor(Color.argb(100, 255, 255, 100));
			p.setStrokeWidth(8f);
			cc.drawRect(xx - r, yy - 0.2f * r, xx + r, yy + 0.2f * r, p);
		}

	}
	
	@Override
	public void write(Canvas cc) {
		write_sub(cc, x.X(), x.Y());
	}
	@Override
	public void writeP(Canvas cc) {
		write_sub(cc,px.X(),px.Y());
	}
	public void writeI(Canvas cc,double I,float xx,float yy) {
		Paint p=new Paint();
		p.setColor(Color.argb(100, 100, 100, 100));
		cc.drawCircle(xx-r,yy, 0.2f*r, p);
		cc.drawCircle(xx+r,yy, 0.2f*r, p);
		p.setColor(Color.WHITE);
		p.setStrokeWidth(2f);
		if( I<0) {
			cc.drawCircle(xx-r,yy,r*0.1f,p);
			cc.drawLine(xx+r - 0.1f * r, yy - 0.1f * r, xx+r + 0.1f * r, yy + 0.1f * r, p);
			cc.drawLine(xx + r - 0.1f * r, yy + 0.1f * r, xx+r + 0.1f * r, yy - 0.1f * r, p);
		}
		if( I> 0 ) {
			cc.drawCircle(xx+r,yy,r*0.1f,p);
			cc.drawLine(xx-r - 0.1f * r, yy - 0.1f * r, xx-r + 0.1f * r, yy + 0.1f * r, p);
			cc.drawLine(xx-r - 0.1f * r, yy + 0.1f * r, xx-r + 0.1f * r, yy - 0.1f * r, p);
		}
		if( isDragged()) {
			p.setStyle(Paint.Style.STROKE);
			p.setColor(Color.argb(100, 255, 255, 100));
			p.setStrokeWidth(8f);
			cc.drawCircle(xx-r,yy, r*0.2f, p);
			cc.drawCircle(xx+r,yy, r*0.2f, p);
		}
	}
	@Override
	public boolean isCatched(Vec2 xx) {
		return (
					x.X()-r < xx.X() && xx.X() < x.X()+r
							&&  x.Y()-0.2f*r      < xx.Y() && xx.Y() < x.Y()+0.2f*r
		);
	}
	public void setR(float rr) {
		r=rr;
	}
	public void setCenterX(float r){ centerX=r;}

	void drawYajirusiFrom(Canvas canvas,int cc,Vec2 shift,Vec2 vx,float w){
		drawYajirusi(canvas,cc,x.Sum(shift),vx,w);
	}

	// (X1,Y1)-(X2,Y2)の範囲に入るように調整した位置座標を返す。
	@Override
	public Vec2 PositionInRect(float X1,float Y1,float X2,float Y2,Vec2 mp) {
		mp.setX(centerX);
		if( mp.Y() < Y1+r  ) {
			mp.setY(Y1 + r);
		}
		if( mp.Y() > Y2-r ) {
			mp.setY(Y2 - r);
		}
		return mp;
	}
}
