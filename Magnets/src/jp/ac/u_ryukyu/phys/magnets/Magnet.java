package jp.ac.u_ryukyu.phys.magnets;


import jp.ac.u_ryukyu.phys.lib.Vec2;
import jp.ac.u_ryukyu.phys.lib.MovingObject;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.FloatMath;


public class Magnet extends MovingObject {
	private float hankei;
	private int cl;
	private float va;
	private float v0;
	private float v1;
	private float angle;
	
	public Magnet(int R,Vec2 xx,float a) {
		super(xx, Vec2.zero);
		hankei=R;
		setPos(xx);
		angle=a;
		dragMode=-1;
	}

	public float R() { return hankei;}
	public float angle() {return angle;}
	public void setAngle(float a){ angle=a;}
	
	@Override
	public void write(Canvas canvas) {
		write_sub(canvas,x.X(),x.Y());
	}
	protected void write_sub(Canvas canvas,float xx,float yy) {
		Paint p=new Paint();
		p.setColor(Color.RED);
		Path path=new Path();
		
		float x1=x.X();
		float y1=x.Y();
		float hsin=hankei*FloatMath.cos(angle);
		float hcos=hankei*FloatMath.sin(angle);
		path.reset();
		path.moveTo(x1+hcos,y1-hsin);
		path.lineTo(x1-hcos, y1+hsin);
		path.lineTo(x1-hcos+hsin*4.5f, y1+hsin+hcos*4.5f);
		path.lineTo(x1+hcos+hsin*4.5f, y1-hsin+hcos*4.5f);
		canvas.drawPath(path, p);
		p.setColor(Color.BLACK);
		path.reset();
		path.moveTo(x1+hcos,y1-hsin);
		path.lineTo(x1-hcos, y1+hsin);
		path.lineTo(x1-hcos-hsin*4.5f, y1+hsin-hcos*4.5f);
		path.lineTo(x1+hcos-hsin*4.5f, y1-hsin-hcos*4.5f);
		canvas.drawPath(path, p);
		
	
		if( dragMan != null && dragMan.isDragged() ) {
			p.setStyle(Style.STROKE);
			p.setStrokeWidth(4f);
			p.setColor(Color.argb(100, 255, 255, 0));
			canvas.drawPath(path, p);
		}
	}
	int dragMode=-1;
	public void setDragMode(int m) {
		dragMode=m;
	}
	public int getDragMode() {
		return dragMode;
	}
	public boolean isNear(float xx,float yy) {
		return (x.X()-xx)*(x.X()-xx)+(x.Y()-yy)*(x.Y()-yy)< hankei*hankei*2;
		//　半径のルート２倍まで感知させることにする。
	}
	public void setColor(int cc){ cl=cc;}

	@Override
	public boolean isCatched(Vec2 mp) {
		// 既にドラッグ中なら偽を返す。
		if( dragMan != null && dragMan.isDragged() ) {
			return false;
		}
		// ドラッグ中でなく、かつポインタが上にあれば、真を返す。
		return ( isNear(mp.X(),mp.Y()) );
	}
	public boolean isCatchedN(Vec2 mp) {
		if( dragMan != null && dragMan.isDragged() ) {
			return false;
		}
		float hsin=hankei*FloatMath.cos(angle);
		float hcos=hankei*FloatMath.sin(angle);
		float xxx=x.X()+hsin*4.5f-mp.X();
		float yyy=x.Y()+hcos*4.5f-mp.Y();
		return (xxx*xxx+yyy*yyy<hankei*hankei*2);
	}
	public boolean isCatchedS(Vec2 mp) {
		if( dragMan != null && dragMan.isDragged() ) {
			return false;
		}
		float hsin=hankei*FloatMath.cos(angle);
		float hcos=hankei*FloatMath.sin(angle);
		float xxx=x.X()-hsin*4.5f-mp.X();
		float yyy=x.Y()-hcos*4.5f-mp.Y();
		return (xxx*xxx+yyy*yyy<hankei*hankei*2);
	}

	// 座標が常に偶数になるように細工。
	@Override
	public void setX(float xx) {
		int xxx=(int)xx;
		if( xxx%2 == 1 ) {
			xxx -= 1;
		}
		x.setX(xxx);
	}
	@Override
	public void setY(float xx) {
		int xxx=(int)xx;
		if( xxx%2 == 1 ) {
			xxx -= 1;
		}
		x.setY(xxx);
	}
	@Override
	public void setPos(Vec2 p) {
		setX(p.X());
		setY(p.Y());
	}
	
	public float V0() {
		return v0;
	}
	public float V1() {
		return v1;
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
