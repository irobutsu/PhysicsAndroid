package jp.ac.u_ryukyu.phys.Divergence;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import jp.ac.u_ryukyu.phys.lib.CircleObject;
import jp.ac.u_ryukyu.phys.lib.DraggManageView;
import jp.ac.u_ryukyu.phys.lib.MovingObject;
import jp.ac.u_ryukyu.phys.lib.Vec2;

public class GaussView extends DraggManageView {
	TextView l1;
	GaussGraphView graphview;
	
	public GaussView(Context context,TextView ll1,GaussGraphView gv) {
		super(context);
		l1=ll1;
		graphview=gv;
		wallMg=0f;
	}
	int N=10;
	int pointGridx=0;
	int pointGridy=0;
	@Override
	public boolean ClickOthers(Vec2 m) {
		DivSource ds;
//		RotSource rs;
		switch( cMode) {
		case 0:
		float www=wx/N;
		pointGridx=(int)(m.X()/www);
		pointGridy=(int)(m.Y()/www);
			return true;
		case 1:
			ds=new DivSource(m,1);
			ds.setDraggManager();
			addObj(ds);
			return true;
		case 3:	
	//		rs=new RotSource(m,1);
	//		rs.setDraggManager();
	//		addObj(rs);
			return true;
		case 2:
			ds=new DivSource(m,-1);
			ds.setDraggManager();
			addObj(ds);
			return true;
		case 4:	
		//	rs=new RotSource(m,-1);
		//	rs.setDraggManager();
		//	addObj(rs);
			return true;
		}
		return false;
	}

	class Source extends CircleObject {
		boolean isDivergence;
		int charge;
		public Source(int cc, float rr, Vec2 xx, Vec2 vxx) {
			super(cc, rr, xx, vxx);
		}
		public boolean isDiv(){ return isDivergence;}
		public int Q(){return charge;}
	}
	class DivSource extends Source {
		public DivSource(Vec2 p,int q) {
			super(Color.RED, 20, p, Vec2.zero);
			isDivergence=true;
			charge=q;
			if( q<0) {
				c=Color.BLACK;
			}
		}
		@Override
		public void writeP(Canvas cv) {
			super.writeP(cv);
			Paint p=new Paint();
			p.setColor(Color.WHITE);
			cv.drawLine(x.X()-r*0.5f,x.Y(),x.X()+r*0.5f,x.Y(),p);
			if( charge >0) {
				cv.drawLine(x.X(),x.Y()-r*0.5f,x.X(),x.Y()+r*0.5f,p);
			}
		}
	}
//
//	class RotSource extends Source {
//		public RotSource(Vec2 p,int q) {
//			super(Color.BLUE, 20, p, Vec2.zero);
//			isDivergence=false;
//			charge=q;
//			if( q<0 ) {
//				c=Color.GREEN;
//			}
//		}
//		@Override
//		public void writeP(Canvas cv) {
//			super.writeP(cv);
//			Paint p=new Paint();
//			p.setStyle(Style.STROKE);
//			p.setColor(Color.WHITE);
//			cv.drawArc(new RectF(x.X()-r*0.5f,x.Y()-r*0.5f,x.X()+r*0.5f,x.Y()+r*0.5f), 0, 270, false, p);
//			if( charge>0) {
//				cv.drawLine(x.X()+0.4f*r, x.Y(), x.X()+0.6f*r, x.Y(), p);
//				cv.drawLine(x.X()+0.4f*r, x.Y(), x.X()+0.5f*r, x.Y()-0.4f*r, p);
//				cv.drawLine(x.X()+0.6f*r, x.Y(), x.X()+0.5f*r, x.Y()-0.4f*r, p);
//			} else {
//				cv.drawLine(x.X(), x.Y()-0.4f*r, x.X(), x.Y()-0.6f*r, p);
//				cv.drawLine(x.X(), x.Y()-0.4f*r, x.X()+0.4f*r, x.Y()-0.5f*r, p);
//				cv.drawLine(x.X(), x.Y()-0.6f*r, x.X()+0.4f*r, x.Y()-0.5f*r, p);
//			}
//		}
//	}

	@Override
	protected void writeSubView(Canvas canvas) {
		// TODO Auto-generated method stub

	}

	
	int Ex0,Ey0,Exx,Exy,Eyx,Eyy;

	
	public void setEx0(int i) {Ex0=i;}
	public void setEy0(int i) {Ey0=i;}
	public void setExy(int i) {Exy=i;}
	public void setEyx(int i) {Eyx=i;}
	public void setExx(int i) {Exx=i;}
	public void setEyy(int i) {Eyy=i;}
	public int getEx0() {return Ex0;}
	public int getExx() {return Exx;}
	public int getExy() {return Exy;}
	public int getEy0() {return Ey0;}
	public int getEyx() {return Eyx;}
	public int getEyy() {return Eyy;}

	final int maxE=5;
	public void upEx0(){ Ex0++;if( Ex0>maxE ) { Ex0=maxE;}}
	public void downEx0(){ Ex0--;if( Ex0<-maxE) { Ex0=-maxE;}}
	public void upExx(){ Exx++;if( Exx>maxE ) { Exx=maxE;}}
	public void downExx(){ Exx--;if( Exx<-maxE) { Exx=-maxE;}}
	public void upExy(){ Exy++;if( Exy>maxE ) { Exy=maxE;}}
	public void downExy(){ Exy--;if( Exy<-maxE) { Exy=-maxE;}}
	public void upEy0(){ Ey0++;if( Ey0>maxE ) { Ey0=maxE;}}
	public void downEy0(){ Ey0--;if( Ey0<-maxE) { Ey0=-maxE;}}
	public void upEyx(){ Eyx++;if( Eyx>maxE ) { Eyx=maxE;}}
	public void downEyx(){ Eyx--;if( Eyx<-maxE) { Eyx=-maxE;}}
	public void upEyy(){ Eyy++;if( Eyy>maxE ) { Eyy=maxE;}}
	public void downEyy(){ Eyy--;if( Eyy<-maxE) { Eyy=-maxE;}}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		wx=w;
		hy=h;
		int ph=((View)getParent()).getHeight();
		int pw=((View)getParent()).getWidth();
		if( ph-200 != h ) {
			this.setLayoutParams(new LinearLayout.LayoutParams(pw, ph-200));
			graphview.setLayoutParams(new LinearLayout.LayoutParams(pw, 200));
		}
		float ww=wx/N;
		gridx0=wx/2-ww;
		gridy0=hy/2-ww;
		gridx1=wx/2+ww;
		gridy1=hy/2+ww;
	}

	public static void drawYajirusi(Canvas g,float x,float y,Vec2 p,int cc) {
		drawYajirusi(g,x,y,p.X(),p.Y(),cc);
	}

	public static void drawYajirusi(Canvas g,float x,float y,float vx,float vy,int cc)
	{
		int xx=(int)(vx);			
		int yy=(int)(vy);

		Path p=new Path();
		Paint paint=new Paint();
		paint.setColor(cc);
		int vyy=(int)(0.08*vy);
		int vxx=(int)(0.08*vx);
		if( vyy==0 ) {vyy=1;}
		if( vxx==0 ) {vxx=1;}

		p.moveTo(x+vyy,y-vxx);
		p.lineTo(x-vyy,y+vxx);
		p.lineTo(x+(int)(0.75*vx-vyy),y+(int)(0.75*vy+vxx));
		p.lineTo(x+(int)(0.75*vx-0.25*vy),y+(int)(0.75*vy+0.25*vx));
		p.lineTo(x+xx,y+yy);
		p.lineTo(x+(int)(0.75*vx+0.25*vy),y+(int)(0.75*vy-0.25*vx));
		p.lineTo(x+(int)(0.75*vx+vyy),y+(int)(0.75*vy-vxx));

		g.drawPath(p,paint);
	}


	float bairitsu=0.1f;
	public void setbairitsu(int i){
		bairitsu=0.001f*(i+10); // i=0〜300で、初期値は90 
	}


	@Override
	public void drawBackGround(Canvas gc){
		Paint p=new Paint();

	

		p.setColor(Color.WHITE);
		gc.drawRect(0,0,wx,hy,p);


		float ww=wx/N;


		int i,j;

		p.setColor(Color.BLACK);
		gc.drawLine(wx/2,0,wx/2,hy,p);
		gc.drawLine(0,hy/2,wx,hy/2,p);

		p.setColor(Color.GRAY);
		for(i=1 ; i*ww < wx/2 ; i++ ) {
			gc.drawLine(wx/2+i*ww,0,wx/2+i*ww,hy,p);
			gc.drawLine(wx/2-i*ww,0,wx/2-i*ww,hy,p);
		}
		for(j=1; j*ww < hy/2 ; j++ ) {
			gc.drawLine(0,hy/2+j*ww,wx,hy/2+j*ww,p);
			gc.drawLine(0,hy/2-j*ww,wx,hy/2-j*ww,p);
		}



	//	drawYajirusi(gc,wx/2,hy/2,EX0,EY0,Color.argb(120,255,0,0));
//		for(i=0 ; i*ww < wx/2 ; i++ ) {
//			for(j=0; j*ww < hy/2 ; j++ ) {
//				if( i != 0 || j != 0 ) {
//					float EX1=bairitsu*(Exx*i*ww);
//					float EX2=bairitsu*Exy*j*ww;
//					float EY1=bairitsu*Eyx*i*ww;
//					float EY2=bairitsu*(Eyy*j*ww);
//
//					drawYajirusi(gc,wx/2+i*ww,hy/2-j*ww,
//							EX0+EX1+EX2,EY0-EY1-EY2,Color.argb(120,255,0,0));
//					drawYajirusi(gc,wx/2-i*ww,hy/2-j*ww,
//							EX0-EX1+EX2,EY0+EY1-EY2,Color.argb(120,255,0,0));
//					drawYajirusi(gc,wx/2+i*ww,hy/2+j*ww,
//							EX0+EX1-EX2,EY0-EY1+EY2,Color.argb(120,255,0,0));
//					drawYajirusi(gc,wx/2-i*ww,hy/2+j*ww,
//							EX0-EX1-EX2,EY0+EY1+EY2,Color.argb(120,255,0,0));
//				}
//			}
//		}
		float minimumx=wx/2-((int)(wx/2/ww))*ww;
		float minimumy=hy/2-((int)(hy/2/ww))*ww;
		for( i=0 ; i*ww < wx ; i++) {
			for( j=0; j*ww < hy; j++) {
				float px=minimumx+i*ww;
				float py=minimumy+j*ww;
				drawYajirusi(gc,px,py,E(px,py),Color.argb(120,255,0,0));
			}
		}
		
		
		int divE=Exx+Eyy;
		int rotE=-Exy+Eyx;
		
		p.setStyle(Style.STROKE);
		p.setStrokeWidth(3f);
		p.setColor(Color.argb(120,0,0,255));


		gridx0=minimumx+(pointGridx)*ww;
		gridy0=minimumy+(pointGridy-1)*ww;
		gridx1=minimumx+(pointGridx+2)*ww;
		gridy1=minimumy+(pointGridy+1)*ww;
		gc.drawRect(gridx0,gridy0,gridx1,gridy1,p);
		

		l1.setText("（源の場所以外の）div E="+Integer.toString(divE));
		//l2.setText("（源の場所以外の）rot E="+Integer.toString(rotE));

		float px,py;

		
		float t0=t-((int)(t/40))*40;
		if( t0>30) {
			px=gridx0;
			py=gridy0+(t0-30)*2*ww/10f;
		} else if (t0 >20) {
			px=gridx1-(t0-20)*2*ww/10f;
			py=gridy0;
		} else if( t0>10 ) {
			px=gridx1;
			py=gridy1-(t0-10)*2*ww/10f;
		} else {
			px=gridx0+t0*2*ww/10f;
			py=gridy1;
		}
		Vec2 EE=E(px,py);
	
		if( t0>30 || (t0>10 && t0<20) ) {
			drawYajirusi(gc,px,py,new Vec2(EE.X(),0),Color.argb(120, 255, 0, 255));
			// drawYajirusi(gc,px,py,new Vec2(0,EE.Y()),Color.argb(120, 200, 200, 0));
		} else {
			//drawYajirusi(gc,px,py,new Vec2(EE.X(),0),Color.argb(120, 200,200, 0));
			drawYajirusi(gc,px,py,new Vec2(0,EE.Y()),Color.argb(120, 255, 0, 255));	
		}
		
		
		drawYajirusi(gc,px,py,EE,Color.argb(120,0,255,0));

		// graphviewのwxは、こっちのwxと同じになっているはずなので、そういうことで計算する。
		// N=8以上でないとヤバイな。
		float wx4=wx/4;
		for(i=0; i<ww*2 ; i++ ) {
			px=gridx0+i;
			py=gridy1;
			Vec2 E=E(px,py);
			graphview.setDiv(i,E.Y());
			// graphview.setRot(i,E.X());
		}
		for(i=0; i<ww*2 ; i++ ) {
			px=gridx1;
			py=gridy1-i;
			Vec2 E=E(px,py);
			graphview.setDiv(i+ww*2,E.X());
		//	graphview.setRot(i+ww*2,-E.Y());
		}
		for(i=0; i<ww*2 ; i++ ) {
			px=gridx1-i;
			py=gridy0;
			Vec2 E=E(px,py);
			graphview.setDiv(i+ww*4,-E.Y());
		//	graphview.setRot(i+ww*4,-E.X());
		}
		for(i=0; i<ww*2 ; i++ ) {
			px=gridx0;
			py=gridy0+i;
			Vec2 E=E(px,py);
			graphview.setDiv(i+ww*6,-E.X());
	//		graphview.setRot(i+ww*6,E.Y());
		}
		graphview.setI(t0,ww);
	
	}

	protected Vec2 E(float px,float py) {
		float EX=bairitsu*100f*Ex0+bairitsu*Exx*(px-wx/2)-bairitsu*Exy*(py-hy/2);
		float EY=-bairitsu*100f*Ey0-bairitsu*Eyx*(px-wx/2)+bairitsu*Eyy*(py-hy/2);		
		int k;
		for(k=0; k<objs.size(); k++ ) {
			float dx=px-objs.get(k).X();
			float dy=py-objs.get(k).Y();
			float r2=dx*dx+dy*dy;
			if(r2==0) {
				r2=10000000000000f;
				// r2=0の時は、いっそ影響をなくす。
			}
			
			
			float Q=((Source)objs.get(k)).Q();
			
			if( ((Source)objs.get(k)).isDiv() ) {
				EX +=q*Q*bairitsu*dx/r2;
				EY +=q*Q*bairitsu*dy/r2;
			} else {
				EX +=q*Q*bairitsu*dy/r2;
				EY -=q*Q*bairitsu*dx/r2;
			}
		}	
		return new Vec2(EX,EY);
	}
	
	
	float q=50000f;
	float gridx0,gridx1,gridy0,gridy1;
	@Override
	protected void setSituation() {
		// TODO
	}

	@Override
	protected void calcNextEach(MovingObject movingObject) {
		// TODO Auto-generated method stub
		
	}

	int cMode=0;
	public void clickMode(int i) {
		cMode=i;
	}
	public void allErase() {
		
		int i;
		for( i=0 ; i<objs.size(); i++) {
			objs.get(i).removeDraggManager();
		}
		objs.clear();
	}
}
