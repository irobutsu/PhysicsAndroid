package jp.ac.u_ryukyu.phys.lib;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;



public class CircleObject extends MovingObject {
	protected float r;
	protected int c;
	public CircleObject(int cc,float rr,Vec2 xx,Vec2 vxx) {
		super(xx, vxx);
		r=rr;
		c=cc;
	}
	public void setColor(int cc) {c=cc;}
	public float R() {return r;}

	
	protected void write_sub(Canvas cc,float xx,float yy) {
		Paint p=new Paint();
		p.setAntiAlias(true);
		p.setColor(c);
		cc.drawCircle(xx,yy,r,p);
		if( isDragged()) {	
			p.setStyle(Paint.Style.STROKE);
			p.setColor(Color.argb(100,255,255,100));
			p.setStrokeWidth(8f);
			cc.drawCircle(xx, yy, r, p);
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
		return (x.Diff(xx)).NormSquare() <= r * r;
	}
	public void setR(float rr) {
		r=rr;
	}

	void drawYajirusiFrom(Canvas canvas,int cc,Vec2 shift,Vec2 vx,float w){
		drawYajirusi(canvas,cc,x.Sum(shift),vx,w);
	}

	@Override
	public boolean releaseDragg(){
		return super.releaseDragg();
	}
	// (X1,Y1)-(X2,Y2)の範囲に入るように調整した位置座標を返す。
	@Override
	public Vec2 PositionInRect(float X1,float Y1,float X2,float Y2,Vec2 mp) {
		Vec2 x1=new Vec2(mp);
		if( x1.X() < X1+r ) {
			x1.setX(X1+r);
		}
		if( x1.X() > X2-r ) {
			x1.setX(X2-r);
		}
		if( x1.Y() < Y1+r  ) {
			x1.setY(Y1+r);
		}
		if( x1.Y() > Y2-r ) {
			x1.setY(Y2-r);
		}
		return x1;
	}
}
