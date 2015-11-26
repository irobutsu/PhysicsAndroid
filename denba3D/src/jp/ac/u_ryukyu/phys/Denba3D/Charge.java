package jp.ac.u_ryukyu.phys.Denba3D;

import jp.ac.u_ryukyu.phys.lib.Vec2;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class Charge {
	private int q;
	private float hankei;
	private int cl;
	private Vec2 x;
	private float a;
	private float v0;
	
	public Charge(int Q,int R,int cc,Vec2 xx) {
		
		hankei=R;
		cl=cc;
		x=xx;
		setQ(Q);
	}

	public int Q() { return q;}
	public float R() { return hankei;}
	public Vec2 P() { return x;}
	public float X(){
		return x.X();
	}
	public float Y() {
		return x.Y();
	}

	public void setX(float a){ x.setX(a);}
	public void setY(float a){ x.setY(a);}
	public void setPosition(Vec2 p) {
		x=p;
	}
	
	
	
	protected void write(Canvas canvas) {
		Paint p=new Paint();
		p.setColor(cl);
		canvas.drawCircle(x.X(),x.Y(), hankei, p);
		p.setColor(Color.WHITE);
		p.setStrokeWidth(2f);
		if( q>0) {
			canvas.drawLine(x.X(),x.Y()-hankei*0.8f,x.X(),x.Y()+hankei*0.8f,p);
		}
		if( q!= 0 ) {
			canvas.drawLine(x.X()-0.8f*hankei,x.Y(),x.X()+0.8f*hankei,x.Y(),p);
		}
	}
	
	public boolean isNear(float xx,float yy) {
		return (x.X()-xx)*(x.X()-xx)+(x.Y()-yy)*(x.Y()-yy)< hankei*hankei;
	}
	public void setColor(int cc){ cl=cc;}
	
	int draggingPointer=-1;
	public boolean isCatched(Vec2 mp) {
		// 既にドラッグ中なら偽を返す。
		// ドラッグ中でなく、かつポインタが上にあれば、真を返す。
		return ( draggingPointer <0 && isNear(mp.X(),mp.Y()) );
	}
	public void setDraggingPointer(int i) { 
		draggingPointer=i;
		cl=cl-0x80000000;
	}
	public boolean isDragged() { return draggingPointer != -1;}
	public boolean isDraggedBy(int i) { return draggingPointer==i;}

	public void releaseDragg() {
		draggingPointer=-1;
		cl=cl+0x80000000;
	}

	public void setQ(int q2) {
		q=q2;
		a=q/(hankei*hankei);
		v0=q-q*((float)Math.log(hankei*hankei));
	}

	public float V0() {
		return v0;
	}
	public float A() {
		return a;
	}
}
