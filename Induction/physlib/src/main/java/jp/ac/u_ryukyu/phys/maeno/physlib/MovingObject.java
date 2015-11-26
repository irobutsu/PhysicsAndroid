package jp.ac.u_ryukyu.phys.maeno.physlib;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;


abstract public class MovingObject {
	protected Vec2 x; //������������
	protected Vec2 px;//������������
	protected Vec2 v; //������������
	protected Vec2 pv;//������������
	protected Vec2 a; //���������
	protected ArrayList<FPack> Forces;
	//������������������������������������
	protected float m;
	

	class FPack {
		public int cc;
		public Vec2 F;
		public Vec2 pF;
		public FPack(int c,Vec2 f,Vec2 pf) {
			cc=c; F=new Vec2(f);pF=new Vec2(pf);
		}
	}
	
	
	public MovingObject(Vec2 xx,Vec2 vv,float mass) {
		px=new Vec2(xx);
		x=new Vec2(xx);
		pv=new Vec2(vv);
		v=new Vec2(vv);
		a=new Vec2(0f,0f);
		setM(mass);
	}

	public void setM(float mass) {
		if( m>0) {
			m=mass;
		} else {
			m=1;
		}
	}
	public MovingObject(Vec2 xx,Vec2 vv) {
		this(xx,vv,1f);
	}
	
	public void calcNextConstA(float st) {
		// ������������������������
		v=pv.Sum(a.Prod(st)); // v=pv + a t
		x=px.Sum(pv.Prod(st),a.Prod(0.5f*st*st)); // x=px + pv t + 0.5 a t^2
	}

	public void calcA(float st) {
		// ������������������������������������������������������������������������
		a=v.Diff(pv);
		a.div(st);
	}

	public void savePrev() {
		// ���������������������������������������������������������
		pv=v; 
		px=x; 
	}
	
	public float X() {return x.X();}
	public float Y() {return x.Y();}
	public float Px() {return px.X();}
	public float Py() {return px.Y();}
	public float Vx() {return v.X();}
	public float Vy() {return v.Y();}
	public float PVx() {return pv.X();}
	public float PVy() {return pv.Y();}
	public float Ax(){ return a.X(); }
	public float Ay(){ return a.Y(); }
	public Vec2 V() {return v;}
	public Vec2 Pos() {return x;} // p������������������������
	public Vec2 A() {return a;}
	public Vec2 PPos(){return px;}
	public Vec2 PV(){return pv;}

	public void setA(Vec2 yy) {
		a=new Vec2(yy);
	}
	public void setXY(float xx,float d) {
		x=new Vec2(xx,d);
	}
	public void setX(float xx) {x.setX(xx);}
	public void setY(float xx) {x.setY(xx);}
	public void setPX(float xx) {px.setX(xx);}
	public void setPY(float xx) {px.setY(xx);}
	
	public void setPos(Vec2 xx) {
		x=xx; 
	}
	public void setPPos(Vec2 xx) {
		px=xx; 
	}
	public void setV(Vec2 xx) {
		v=xx; 
	}
	public void setF(int cc,Vec2 pp,Vec2 xx) {
		if( Forces==null) {
			Forces= new ArrayList<>();
		}
		Forces.add(new FPack(cc,xx,pp.Diff(px)));
	}
	public void clearF() {
		if( Forces != null ) {
			Forces.clear();
		}
	}
	
	
	public void adjustA(float st) {
		// ���������������������������������������������������������������������������������������������������������������
		a=v.Diff(pv);
		a.div(st);
		// a=(v-pv)/st
	}

	public void adjustV(float st) {
		v=x.Diff(px);
		v.div(st);
		// v=(x-px)/st
	}

	public void writeV(Canvas c){
		drawYajirusi(c,Color.argb(128,0,0,255),x,v,5);
	}
	public void writeV(Canvas c,int cl){
		drawYajirusi(c,cl,x,v,5);
	}
	public void writePV(Canvas c){
		drawYajirusi(c,Color.argb(128,0,0,255),px,pv,5);
	}
	public void writePV(Canvas c,int cl){
		drawYajirusi(c,cl,px,pv,5);
	}
	
	public void writeA(Canvas c){
		drawYajirusi(c,Color.argb(128,255,0,0),x,a,5);
	}
	public void writePA(Canvas c){
		drawYajirusi(c,Color.argb(128,255,0,0),px,a,5);
	}
	public void writeA2(Canvas c){
		drawYajirusi(c,Color.argb(128,255,0,0),px.Sum(v),a,5f);
	}
	
	public void writeF(Canvas c) {
		if( Forces == null) {return;}
		int i;
		for(i=0; i<Forces.size(); i++ ) {
			drawYajirusiF(c,Forces.get(i).cc,px.Sum(Forces.get(i).pF),Forces.get(i).F,3);
		}
	}
	public Vec2 getF() {
		Vec2 f=new Vec2(0f,0f);
		if( Forces!= null) {
			int i;
			for(i=0; i<Forces.size(); i++ ) {
				f.add(Forces.get(i).F);
			}
		}
		return f;
	}

	abstract public void writeP(Canvas c);
	abstract public void write(Canvas c);
	public void setAFromF() {
		a=getF().Quot(m);
	}
	
	protected void drawYajirusi(Canvas c,int cl,Vec2 vvx,float w){
		drawYajirusi(c,cl,x,vvx,w);
	}
	static void drawYajirusiF(Canvas c,int cc,Vec2 xxx,Vec2 vvx,float w) {
		Paint pp=new Paint();
		pp.setColor(cc);
		c.drawCircle(xxx.X(), xxx.Y(), w*2f, pp);
		drawYajirusi(c,cc,xxx,vvx,w);
	}
	public static void drawYajirusi(Canvas c,int cc,Vec2 xxx,Vec2 vvx,float w) {
		Paint pp=new Paint();
		pp.setColor(cc);
		
		Path p=new Path();

		 float rr=vvx.Norm();
		 Vec2 xx=vvx.Prod(w/rr);
		 xx.Rot1();

		 
		 
		 Vec2 soko1=xxx.Diff(xx);
		 Vec2 soko2=xxx.Sum(xx);
		 Vec2 noki3=soko2.Sum(vvx.Prod(0.6f));
		 Vec2 noki2=soko1.Sum(vvx.Prod(0.6f));
		 Vec2 noki1=noki2.Diff(xx);
		 Vec2 noki4=noki3.Sum(xx);
		 Vec2 teppen=xxx.Sum(vvx);
		 
		 
		 p.moveTo(soko1.X(), soko1.Y());
		 p.lineTo(soko2.X(), soko2.Y());
		 p.lineTo(noki3.X(), noki3.Y());
		 p.lineTo(noki4.X(), noki4.Y());
		 p.lineTo(teppen.X(),teppen.Y());
		 p.lineTo(noki1.X(), noki1.Y());
		 p.lineTo(noki2.X(), noki2.Y());
		 p.lineTo(soko1.X(), soko1.Y());
		 
		 c.drawPath(p,pp);
	 }

	// ���������������������������������������������������������
	// ������������������������������������������������������������
	// ������������������������dragMan������������null������������������
	protected DraggManager dragMan=null;
	
	protected boolean nowCanDrag=true; // ������������������������������������������������������������������������������������������

	public boolean isCanDrag() {
		return nowCanDrag;
	}
	public void canDrag() {
		nowCanDrag = true;
	}
	public void cantDrag() {
		nowCanDrag = false;
	}
	// ������������������������������������������������
	// setDraggManager���������������������������������������
	public void setDraggManager() {
		dragMan=new DraggManager(this);
	}

	public void setDraggManager(int i) {
		dragMan=new DraggManager(this,i);
	}

	// ������������������������������������������������������������������������������������������
	public void removeDraggManager() {
		DraggManager.remove(this);
	}
	
	
	public boolean tamatteru() {
		return dragMan != null && dragMan.tamatteru();
	}
	public boolean startDragg(int i) {
		return nowCanDrag && startDragg(i, Vec2.zero);
	}
	public boolean startDragg(int i,Vec2 s) {
		return dragMan != null && dragMan.startDragg(i, s);
	}
	public boolean releaseDragg() {
		return dragMan != null && dragMan.releaseDragg();
	}
	
	public void pushPath(Vec2 p){
		if( dragMan != null ) {
			dragMan.pushPath(p);
		}
	}
	
	public void pushPath(){
		if( dragMan != null ) {
			dragMan.pushPath();
		}
	}
	
	public void setDraggedPos(){
		setDraggedPos(dragMan.tmpPos);
	}
	public void setDraggedPos(Vec2 x){
		if( dragMan !=null) {
			setPos(x);
		}
	}
	public void setPosVFromPaths(float st) {
		if( dragMan != null) {
			setV(dragMan.calcMidV(st));
			setA(dragMan.calcMidA(st));
			setPos(dragMan.calcMid());
		}
	}

	public Vec2 shift(){
		if( dragMan != null ) {
			return dragMan.shift();
		}
		return Vec2.zero;
		
	}
	public void setTmpPos(Vec2 p) {
		if( dragMan != null ) {
			dragMan.setTmpPos(p);
		}
	}
	
	
	public boolean isDragged() {
		return dragMan != null && dragMan.isDragged();

	}
	public boolean isDraggedBy(int ptID) {
		return dragMan != null && dragMan.isDraggedBy(ptID);

	}
	// ������������������������������������������������������������������
	public int whoDragg(){
		if( dragMan != null ) {
			return dragMan.whoDragg();
		}
		return -1;
		
	}
	// ���������������������������������������������������������������������������
	// ���������������������������������������������������������abstract������������������
	abstract public boolean isCatched(Vec2 xxx);
	// ���������������������������������������������������������������������������������������������abstract
	abstract public Vec2 PositionInRect(float X1,float Y1,float X2,float Y2,Vec2 mp);
	public float M() {
		return m;
	}
}
