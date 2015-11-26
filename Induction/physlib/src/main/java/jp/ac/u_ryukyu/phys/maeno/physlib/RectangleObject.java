package jp.ac.u_ryukyu.phys.maeno.physlib;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class RectangleObject extends MovingObject {
	protected float tate;
	protected float yoko;
	protected int c;
	public RectangleObject(int cc,float t,float y,Vec2 xx,Vec2 vxx) {
		super(xx, vxx);
		tate=t;
		yoko=y;
		c=cc;
	}

	public void setColor(int cc) {c=cc;}
	public float Tate() {return tate;}
	public float Yoko() {return yoko;}

	
	protected void write_sub(Canvas cc,float xx,float yy) {
		Paint p=new Paint();
		p.setAntiAlias(true);
		p.setColor(c);
		cc.drawRect(xx-yoko/2,yy-tate/2,xx+yoko/2,yy+tate/2,p);
		if( isDragged()) {	
			p.setStyle(Paint.Style.STROKE);
			p.setColor(Color.argb(100,255,255,100));
			p.setStrokeWidth(8f);
			cc.drawRect(xx-yoko/2,yy-tate/2,xx+yoko/2,yy+tate/2,p);
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
		return !(x.X() + yoko / 2 < xx.X() || x.X() - yoko / 2 > xx.X()) && !(x.Y() + tate / 2 < xx.Y() || x.Y() - tate / 2 > xx.Y());
	}

	public void setTate(float rr) {
		tate=rr;
	}
	public void setYoko(float rr) {
		yoko=rr;
	}

	void drawYajirusiFrom(Canvas canvas,int cc,Vec2 shift,Vec2 vx,float w){
		drawYajirusi(canvas,cc,x.Sum(shift),vx,w);
	}
	
	@Override
	public boolean startDragg(int i,Vec2 s) {
		return super.startDragg(i, s);

	}
	@Override
	public boolean releaseDragg(){
		return super.releaseDragg();
	}
	// (X1,Y1)-(X2,Y2)の範囲に入るように調整した位置座標を返す。
	@Override
	public Vec2 PositionInRect(float X1,float Y1,float X2,float Y2,Vec2 mp) {
		if( mp.X() < X1+yoko/2 ) {
			mp.setX(X1 + yoko / 2);
		}
		if( mp.X() > X2-yoko/2 ) {
			mp.setX(X2 - yoko / 2);
		}
		if( mp.Y() < Y1+tate/2  ) {
			mp.setY(Y1 + tate / 2);
		}
		if( mp.Y() > Y2-tate/2 ) {
			mp.setY(Y2 - tate / 2);
		}
		return mp;
	}
}
