package jp.ac.u_ryukyu.phys.LeastAction;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import jp.ac.u_ryukyu.phys.lib.CircleObject;
import jp.ac.u_ryukyu.phys.lib.DraggManager;
import jp.ac.u_ryukyu.phys.lib.MovingObject;
import jp.ac.u_ryukyu.phys.lib.DraggManageView;
import jp.ac.u_ryukyu.phys.lib.Vec2;

public class ActionPrincipleView extends DraggManageView {
	CircleObjectOnAxis m[];
	CircleObject M;
	final int N=12;
	float widthOne;
	float hankei;
	int whichMode=0;
	float g=1;
	
	public ActionPrincipleView(Context context) {
		super(context);
		m=new CircleObjectOnAxis[N];
		this.setStepT(4f);
	}
	
	public void setG(float gg) {g=gg;}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		int i;
		

		objs=new ArrayList<MovingObject>();

		widthOne=wx/(N+5);
		hankei=widthOne*0.3f;
		M=new CircleObject(Color.RED,hankei,new Vec2(widthOne*0.5f,0.5f*hy),Vec2.zero);
		addObj(M);
		
		for(i=0; i<N ; i++) {
			m[i]=new CircleObjectOnAxis(Color.argb(128,0,0,255), hankei, 
					new Vec2(widthOne+2*hankei+widthOne*(i),0.5f*hy));
			addObj(m[i]);
//			m[i].setDraggManager();
		}
		m[0].setY(hy-m[0].R());
		m[1].setY(hy-m[1].R()-widthOne);
		setMode(0);
	}

	
	public void setMode(int mode) {
		int i;
		t=0;
		whichMode=mode;
		switch(mode) {
		case 0:
			//	最初と２番目を動かすと残りが決まる。
			m[0].setColor(Color.argb(128, 255, 0, 0));
			m[0].canDrag();
			m[1].setColor(Color.argb(128, 255, 0, 0));
			m[1].canDrag();
			for(i=2; i<N ; i++) {
				m[i].setColor(Color.argb(128, 0, 0, 255));
				m[i].cantDrag();
			}
			if( m[1].Py()<m[N-1].R() ) {
				m[1].setPY(m[N-1].R());
			}
			if( m[1].Py()>hy-m[N-1].R() ) {
				m[1].setPY(hy-m[N-1].R());
			}
			break;
		case 1:
			// 最初と最後を動かすと残りが決まる。
			m[0].setColor(Color.argb(128, 255, 0, 0));
			m[0].canDrag();
			for(i=1; i<N-1 ; i++) {
				m[i].setColor(Color.argb(128, 0, 0, 255));
				m[i].cantDrag();
			}
			m[N-1].setColor(Color.argb(128, 255, 0, 0));
			m[N-1].canDrag();
			if( m[N-1].Py()<m[N-1].R() ) {
				m[N-1].setPY(m[N-1].R());
			}
			if( m[N-1].Py()>hy-m[N-1].R() ) {
				m[N-1].setPY(hy-m[N-1].R());
			}
			break;
		case 2:
			for(i=0; i<N ; i++) {
				m[i].setColor(Color.argb(128, 255, 0, 0));
				m[i].canDrag();
				if( m[i].Py()<m[N-1].R() ) {
					m[i].setPY(m[N-1].R());
				}
				if( m[i].Py()>hy-m[N-1].R() ) {
					m[i].setPY(hy-m[N-1].R());
				}
			}
			break;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean handled = DraggManager.manageDragg(event, this, 0f, 0f, wx, hy);
		return handled || super.onTouchEvent(event);
	}

	float x0;
	float v0;
	
	@Override
	protected void setSituation() {
		float a=-g/(5*widthOne); // 5*widthOneでスケールします。
		int i;
		switch(whichMode) {
		case 0:
			x0=m[0].Y();
			// x0+v0 t -at^2/2 = x1 より、
			// v0 = (x1-x0+at^2/2)/t
			v0=(m[1].Y()-x0+a*widthOne*widthOne*0.5f)/widthOne;
			for(i=2; i<N ; i++) {
				m[i].setY(x0+v0*widthOne*i-a*widthOne*widthOne*i*i*0.5f);
			}
			break;
		case 1:
			x0=m[0].Y();
			v0=(m[N-1].Y()-x0+0.5f*a*widthOne*widthOne*(N-1)*(N-1))/(widthOne*(N-1));
			for(i=1; i<N-1 ; i++) {
				m[i].setY(x0+v0*widthOne*i-a*widthOne*widthOne*i*i*0.5f);
			}
			break;
		}
		if( DraggManager.someIsDragged() ) {
			t=0;
		}
	}
	@Override
	protected void calcNextEach(MovingObject movingObject) {
		// M以外は動かないので、Mの時だけ実行。
		if( movingObject == M) {
			int i;
			float a=-g/(5*widthOne); // 5*widthOneでスケールします。

			if( whichMode==2) {
				i=(int)(t/widthOne);
				if( i>=N-1) {
					M.setY(hy*2); // 涅槃に送る。
				} else {
					M.setY(m[i].Y()+(m[i+1].Y()-m[i].Y())/widthOne*(t-i*widthOne));
				}
			} else {
				M.setY(x0+v0*t-a*t*t*0.5f);    
			}
		}
	}


	@Override
	public boolean ClickOthers(Vec2 mp) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void drawBackGround(Canvas c) {
		Paint p=new Paint();
		p.setColor(Color.WHITE);
		c.drawRect(0,0,wx,hy, p);
		p.setColor(Color.LTGRAY);
		int i;
		float y;
		for( y=hy/2+widthOne/2; y<hy ; y+=widthOne/2) {
			c.drawLine(widthOne+3*hankei+widthOne*N, y, wx, y, p);
		}
		for( y=hy/2+widthOne/2; y>0 ; y-=widthOne/2) {
			c.drawLine(widthOne+3*hankei+widthOne*N, y, wx, y, p);
		}
		p.setColor(Color.BLACK);
		c.drawLine(0,hy*0.5f,wx,0.5f*hy,p);
		Path pt=new Path();
		pt.moveTo(wx, 0.5f*hy);
		pt.lineTo(wx-2*hankei, 0.5f*(hy-hankei));
		pt.lineTo(wx-2*hankei, 0.5f*(hy+hankei));
		pt.close();
		c.drawPath(pt, p);
		
		p.setColor(Color.CYAN);
		c.drawRect(0,0,widthOne,hy,p);
		

		for(i=0; i<N-1 ;i++) {			
			p.setColor(Color.GRAY);
			c.drawLine(m[i].X(), 0, m[i].X(), hy, p);
			p.setColor(Color.RED);
			c.drawLine(m[i].X(), m[i].Py(), m[i+1].X(), m[i+1].Py(), p);
		}
		p.setColor(Color.GRAY);
		c.drawLine(m[N-1].X(), 0, m[N-1].X(), hy, p);
		
		p.setColor(Color.GREEN);
		p.setStrokeWidth(widthOne/N);
		float Ek=0;
		for(i=1; i<N ; i++) {
			float v=(m[i].Py()-m[i-1].Py())/widthOne;
			Ek += 0.5f*v*v*5*widthOne;
			c.drawLine(0.5f*(m[i].X()+m[i-1].X()),hy,0.5f*(m[i].X()+m[i-1].X()),hy-0.5f*v*v*5*widthOne,p);
		}
		p.setColor(Color.BLUE);
		p.setStrokeWidth(widthOne/N);
		float Eu=0;
		for(i=0; i<N ; i++) {
			c.drawLine(m[i].X(),hy,m[i].X(),hy-(hy-m[i].Py())*g,p);
			Eu += g*(hy-m[i].Py());
		}
		
		p.setStrokeWidth(0f);
		p.setColor(Color.argb(128,0,255,0));
		c.drawRect(wx-widthOne*3,hy/2-Ek/N,wx-widthOne*2,hy/2, p);
		p.setColor(Color.argb(128,0,0,255));
		c.drawRect(wx-widthOne*2,hy/2-Eu/N,wx-widthOne,hy/2, p);

		p.setColor(Color.argb(128,255,0,0));
		float S=hy/2-(Ek-Eu)/N;
		if( S> hy/2) {
			c.drawRect(wx-widthOne,hy/2,wx,S, p);
		} else {
			c.drawRect(wx-widthOne,S,wx,hy/2, p);
		}
		p.setColor(Color.BLACK);
		p.setTextSize(widthOne*0.3f);
		c.drawText(getContext().getString(R.string.int_U), wx-widthOne*4, 0.6f*hy+widthOne, p);
		c.drawText(getContext().getString(R.string.int_K), wx-widthOne*4, 0.6f*hy, p);
		c.drawText(getContext().getString(R.string.int_L), wx-widthOne*4, 0.6f*hy+2f*widthOne, p);

		p.setColor(Color.CYAN);
		p.setStrokeWidth(1f);
		c.drawLine(M.X(), M.Py(), t+widthOne+2*hankei, M.Py(),p);
	}

	
	@Override
	protected void writeSubView(Canvas canvas) {
		// TODO Auto-generated method stub

	}

	
	
	class CircleObjectOnAxis extends CircleObject {

		public CircleObjectOnAxis(int c, float f, Vec2 vec2) {
			super(c,f,vec2,Vec2.zero);
			this.setDraggManager(0);
		}

		@Override
		public Vec2 PositionInRect(float X1, float Y1, float X2, float Y2,Vec2 mp) {
			Vec2 xx=new Vec2(mp);
			xx.setX(this.X()); // 横には動かない。
			if( xx.Y() < Y1+r  ) {
				xx.setY(Y1+r);
			}
			if( xx.Y() > Y2-r ) {
				xx.setY(Y2-r);
			}
			return xx;
		}
			
	}



}