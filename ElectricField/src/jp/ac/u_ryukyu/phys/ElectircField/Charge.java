package jp.ac.u_ryukyu.phys.ElectircField;

import jp.ac.u_ryukyu.phys.lib.Vec2;
import jp.ac.u_ryukyu.phys.lib.MovingObject;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;


public class Charge extends MovingObject {
	private int q;
	private float hankei;
	private int cl;
	private float va;
	private float v0;
	
	public Charge(int Q,int R,int cc,Vec2 xx) {
		super(xx, Vec2.zero);
		hankei=R;
		cl=cc;
		setQ(Q);
	}

	public int Q() { return q;}
	public float R() { return hankei;}
	public void setHankei(int h) { hankei=h;}
	
	
	@Override
	public void write(Canvas canvas) {
		write_sub(canvas,x.X(),x.Y());
	}
	protected void write_sub(Canvas canvas,float xx,float yy) {
		Paint p=new Paint();
		p.setColor(cl);
		canvas.drawCircle(xx,yy, hankei, p);
		p.setColor(Color.WHITE);
		p.setStrokeWidth(2f);
		if( q>0) {
			canvas.drawLine(xx,yy-hankei*0.8f,xx,yy+hankei*0.8f,p);
		}
		if( q!= 0 ) {
			canvas.drawLine(xx-0.8f*hankei,yy,xx+0.8f*hankei,yy,p);
		}
		if( dragMan != null && dragMan.isDragged() ) {
			p.setStyle(Style.STROKE);
			p.setStrokeWidth(4f);
			p.setColor(Color.argb(100, 255, 255, 0));
			canvas.drawCircle(xx,yy, hankei, p);
		}
	}
	
	public boolean isNear(float xx,float yy) {
		return (x.X()-xx)*(x.X()-xx)+(x.Y()-yy)*(x.Y()-yy)< hankei*hankei;
	}
	public void setColor(int cc){ cl=cc;}

	@Override
	public boolean isCatched(Vec2 mp) {
		// 既にドラッグ中なら偽を返す。
		// ドラッグ中でなく、かつポインタが上にあれば、真を返す。
		return ( isNear(mp.X(),mp.Y()) );
	}

	public void setQ(int q2) {
		q=q2;
		va=q/(hankei*hankei);
		v0=q-q*((float)Math.log(hankei*hankei));
	}

	public float V0() {
		return v0;
	}
	public float VA() {
		return va;
	}

	@Override
	public void writeP(Canvas c) {
		write_sub(c,px.X(),px.Y());
	}

	@Override
	public Vec2 PositionInRect(float X1, float Y1, float X2, float Y2, Vec2 mp) {
		if( mp.X() < X1+hankei ) {
			mp.setX(X1+hankei);
		}
		if( mp.Y() < Y1+hankei ) {
			mp.setY(Y1+hankei);
		}	
		if( mp.X() > X2-hankei ) {
			mp.setX(X2-hankei);
		}
		if( mp.Y() > Y2-hankei ) {
			mp.setY(Y1-hankei);
		}
		return mp;
	}
}
