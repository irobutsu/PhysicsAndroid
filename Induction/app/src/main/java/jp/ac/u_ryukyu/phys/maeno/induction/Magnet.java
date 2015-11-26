package jp.ac.u_ryukyu.phys.maeno.induction;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import jp.ac.u_ryukyu.phys.maeno.physlib.MovingObject;
import jp.ac.u_ryukyu.phys.maeno.physlib.Vec2;


public class Magnet extends MovingObject {
	protected float r;
	private float centerX;
	public Magnet(float rr, Vec2 xx,float cx) {
		super(xx, Vec2.zero);
		r=rr;
		centerX=cx;
	}
	public float R() {return r;}
	
	protected void write_sub(Canvas cc,float xx,float yy) {
		Paint p=new Paint();
		p.setColor(Color.RED);
		cc.drawRect(xx - r * 0.2f, yy - r, xx + 0.2f*r, yy, p);
		p.setColor(Color.BLUE);
		cc.drawRect(xx - r * 0.2f, yy, xx + 0.2f*r, yy + r, p);
		if( isDragged()) {	
			p.setStyle(Paint.Style.STROKE);
			p.setColor(Color.argb(100, 255, 255, 100));
			p.setStrokeWidth(8f);
			cc.drawRect(xx-r*0.2f,yy-r,xx+0.2f*r,yy+r,p);
		}
	}
	
	@Override
	public void write(Canvas cc) {
		write_sub(cc,x.X(),x.Y());
	}
	@Override
	public void writeP(Canvas cc) {
		write_sub(cc,px.X(),px.Y());
	}
	@Override
	public boolean isCatched(Vec2 xx) {
		return (
				x.X()-r*0.2f < xx.X() && xx.X() < x.X()+0.2f*r
			&&  x.Y()-r      < xx.Y() && xx.Y() < x.Y()+r
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
