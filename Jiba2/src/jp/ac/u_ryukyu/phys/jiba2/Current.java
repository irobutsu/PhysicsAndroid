package jp.ac.u_ryukyu.phys.jiba2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import jp.ac.u_ryukyu.phys.lib.Vec2;


public class Current {
	private int q;
	private int hankei;
	private int cl;
	private Vec2 x;
	
	public Current(int Q,int R,int cc,Vec2 xx) {
		q=Q;
		hankei=R;
		cl=cc;
		x=xx;
	}

	public int Q() { return q;}
	public int R() { return hankei;}
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
		if( q<0) {
			canvas.drawCircle(x.X(),x.Y(),hankei*0.5f,p);
		}
		if( q> 0 ) {
			canvas.drawLine(x.X()-0.4f*hankei,x.Y()-0.4f*hankei,x.X()+0.4f*hankei,x.Y()+0.4f*hankei,p);
			canvas.drawLine(x.X()-0.4f*hankei,x.Y()+0.4f*hankei,x.X()+0.4f*hankei,x.Y()-0.4f*hankei,p);
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
	}
}