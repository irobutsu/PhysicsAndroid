package jp.ac.u_ryukyu.phys.Oscillator;

import jp.ac.u_ryukyu.phys.lib.DraggManager;
import jp.ac.u_ryukyu.phys.lib.MovingObject;
import jp.ac.u_ryukyu.phys.lib.PhystemView;
import jp.ac.u_ryukyu.phys.lib.Vec2;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.FloatMath;
import android.view.MotionEvent;


public class OscillatorView extends PhystemView {
	CircleObjectWithSpring m;

	public OscillatorView(Context context) {
		super(context);
		m=new CircleObjectWithSpring(this, Color.GREEN, 0.03f*getWx(), new Vec2(0.05f*getWx(),0.5f*getHy()), new Vec2(0f,0f));
		addObj(m);
		m.setDraggManager();
		
		k=1f;
		kappa=0f;
		mass=2f;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean handled=DraggManager.manageDragg(event,this,0f,0f,wx,getHy());
		if( !handled ) {
			return super.onTouchEvent(event);
		} 
		return true;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		wx=w;
		hy=h;
		if( m!= null) {
			m.setPos(m.PositionInRect(0, 0, w, h,m.Pos()));
			m.setR(0.03f*getWx());
			m.setXY(0.03f*getWx(), 0.3f*getHy());
			x0=0.5f*getHy()-50f;
			v0=0f;
			setInitialCondition();
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
		p.setColor(Color.rgb(220,228,228));
		c.drawRect(0f, 0f, 0.06f*getWx(), getHy(), p);
		p.setColor(Color.WHITE);
		c.drawRect(0.06f*getWx(),0f,getWx(),getHy(),p);
		int s=(int)(0.06f*getWx());
		
		p.setColor(Color.GRAY);
		
		c.drawLine(s,0.5f*getHy(),getWx(),0.5f*getHy(),p);
		
		
		p.setAntiAlias(true);
		p.setStyle(Paint.Style.STROKE);
		p.setColor(Color.BLACK);

		c.drawPath(pt,p);
		

		if( sFlg ) {
			p.setColor(Color.argb(128,255, 0, 0));
			
			c.drawPath(pt1, p);

			p.setColor(Color.argb(128,0, 0, 255));

			c.drawPath(pt2, p);
		}
		
		p.setColor(Color.RED);
		
		String s1;
		
		if(K==0f) {
			s1="臨界振動です";
		} else if( K<0f ) {
			s1="過減衰です";
		} else {
			if( kappa==0f) {
				s1="単振動です";
			} else {
				s1="減衰振動です";
			}
		}
		
//		Rect r = new Rect((int)(0.75f*wx),(int)(0.1f*hy),(int)(0.85f*wx),(int)(0.2f*hy));
//		p.setStyle(Paint.Style.STROKE);
//		p.setStrokeWidth(3f);
//		p.setColor(Color.GRAY);
//		c.drawRect(r,p);
		p.setColor(Color.BLACK);
		p.setStyle(Paint.Style.FILL);
		p.setTextSize(0.1f*getHy());
		c.drawText(s1, 0.6f*wx, 0.15f*getHy(), p);
	}
	Path pt;
	Path pt1;
	Path pt2;
	
	protected void setPath() {
		int i;
		int s=(int)(0.06f*getWx());
		pt=new Path();
		pt.moveTo(s,Pos(0f));

		for(i=s+2; i<(int)getWx(); i+=2) {
			pt.lineTo(i,Pos(0.05f*(i-s)));
		}
	}
	protected void setPath1() {
		int i;
		int s=(int)(0.06f*getWx());
		pt1=new Path();
		pt1.moveTo(s,Sol1(0f));

		for(i=s+2; i<(int)getWx(); i+=2) {
			pt1.lineTo(i,Sol1(0.05f*(i-s)));
		}
	}
	protected void setPath2() {
		int i;
		int s=(int)(0.06f*getWx());
		pt2=new Path();
		pt2.moveTo(s,Sol2(0f));

		for(i=s+2; i<(int)getWx(); i+=2) {
			pt2.lineTo(i,Sol2(0.05f*(i-s)));
		}
	}

	protected float Pos(float time) {
		if( K==0f) {
			return 0.5f*getHy()+(A1+A2*time)*((float)Math.exp(lambda*time));
			// hy/2 + (A1+A2 t)exp(λt)
		} else if(K>0) {
			return 0.5f*getHy()
					+A*((float)(Math.cos(omega*time+alpha)
					*Math.exp(lambda*time)));
			// hy/2 + A cos(ωt+α) exp(λt)
		} else {
			return 0.5f*getHy()
					+A1*((float)Math.exp((lambda+omega)*time))
					+A2*((float)Math.exp((lambda-omega)*time));
			// hy/2 + A1 exp((λ+ω)t) + A2 exp((λ-ω)t) 
		}
	}

	protected float Sol2(float time) {
		if( K==0f) {
			return 0.5f*getHy()+A2*time*((float)Math.exp(lambda*time));
			
			// hy/2 + (A1+A2 t)exp(λt)
		} else if(K>0) {
			return 0.5f*getHy()
					-A*((float)(Math.sin(omega*time)*Math.sin(alpha)
					*Math.exp(lambda*time)));
			// hy/2 + A cos(ωt+α) exp(λt)
		} else {
			return 0.5f*getHy()
					+A2*((float)Math.exp((lambda-omega)*time));
			// hy/2 + A1 exp((λ+ω)t) + A2 exp((λ-ω)t) 
		}
	}
	protected float Sol1(float time) {
		if( K==0f) {
			return 0.5f*getHy()+(A1)*((float)Math.exp(lambda*time));
		
			
			// hy/2 + (A1+A2 t)exp(λt)
		} else if(K>0) {
			return 0.5f*getHy()
					+A*((float)(Math.cos(omega*time)*Math.cos(alpha)
					*Math.exp(lambda*time)));
			// hy/2 + A cos(ωt+α) exp(λt)
		} else {
			return 0.5f*getHy()
					+A1*((float)Math.exp((lambda+omega)*time));
			// hy/2 + A1 exp((λ+ω)t) + A2 exp((λ-ω)t) 
		}
	}
	
	protected float Vel(float time) {
		if( K==0f) {
			return 
					lambda*(A1+A2*time)*((float)Math.exp(lambda*time))
					+A2*((float)Math.exp(lambda*time));
			// λ(A1+A2 t)exp(λt) +A2 exp(λt)
		} else if(K>0) {
			return
				A*(
						(-omega*FloatMath.sin(alpha+omega*time)+lambda*FloatMath.cos(alpha+omega*time))
						*((float)Math.exp(lambda*time))
							);
			// -A ωsin (ωt+α)exp(λt) +λ Acos (ωt+α)exp(λt)
		} else {
			return (lambda+omega)*A1*((float)Math.exp((lambda+omega)*time))
						+(lambda-omega)*A2*((float)Math.exp((lambda-omega)*time));
			// A1(λ+ω) exp((λ+ω)t) + A2 (λ-ω)exp((λ-ω)t) 
		}
	}
	protected float Acc(float time) {
		if( K==0f) {
			return 
					lambda*lambda*(A1+A2*time)*((float)Math.exp(lambda*time))
					+2*lambda*A2*((float)Math.exp(lambda*time));
			// λ^2(A1+A2 t)exp(λt) +2λA2 exp(λt)
		} else if(K>0) {
			return
				A*(
						-omega*omega*FloatMath.cos(alpha+omega*time)
						-2*lambda*omega*FloatMath.sin(alpha+omega*time)
						+lambda*lambda*FloatMath.cos(alpha+omega*time)
						)*((float)Math.exp(lambda*time));
			// -A ω^2cos (ωt+α)exp(λt) -2λ Aω sin (ωt+α)exp(λt) + lamda^2 A sin(ωt+α)exp(λt)
		} else {
			return (lambda+omega)*(lambda+omega)*A1*((float)Math.exp((lambda+omega)*time))
						+(lambda-omega)*(lambda-omega)*A2*((float)Math.exp((lambda-omega)*time));
			// A1(λ+ω^2 exp((λ+ω)t) + A2 (λ-ω)^2exp((λ-ω)t) 
		}
	}

	
	void setInitialCondition() {	
		K=mass*k-0.25f*kappa*kappa;
		if(K>0) {
			omega=FloatMath.sqrt(K)/mass;
		} else {
			omega=FloatMath.sqrt(-K)/mass;
		}
		lambda=-0.5f*kappa/mass;
		// 現在の位置・速度から将来の運動を予測。
 		if(K==0f) {
 			// Y=hy/2 + A1
 			// V=λA1+A2   より
 			// A1 = Y- hy/2
 			// A2 = V-λA1
 			A1=x0-0.5f*getHy();
 			A2=v0-lambda*A1;
 			
 		} else if(K>0) {
 		
 			// Y= hy/2 +A cos α
 			// V= -ωA sinα + λA cos α より
 			// Y-hy/2 = A cos α
 			// V= -ωA sinα + λ(Y-hy/2)
 			// (λ(Y-hy/2)-V)/ω = A sinα
 			A=FloatMath.sqrt(
 					(x0-0.5f*getHy())*(x0-0.5f*getHy())
 					+(lambda*(x0-0.5f*getHy())-v0)*(lambda*(x0-0.5f*getHy())-v0)/(omega*omega)
 					);
 			alpha=(float)Math.atan2(lambda*(x0-0.5f*getHy())-v0,omega*(x0-0.5f*getHy()));
 		} else {
 			// Y=hy/2 + A1 + A2 
 			// V = A1(λ+ω)+ A2 (λ-ω) より
 
 			// V/(λ+ω) = A1 + A2 *(λ-ω)/(λ+ω)
 			// V/(λ-ω) = A1*(λ+ω)/(λ-ω） + A2
 			
 			// Y-V/(λ+ω) = hy/2 +A2- A2 *(λ-ω)/(λ+ω) 
 			// Y-V/(λ-ω) = hy/2 +A1- A1 *(λ+ω)/(λ-ω) 

 			
 			// (λ-ω) (Y-hy/2) -V = (λ-ω)A1 -A1 *(λ+ω)=-2ωA1
 			// (λ+ω) (Y-hy/2) -V = (λ+ω)A2 -A2 *(λ-ω)=2ωA2

 			A1=((x0-0.5f*getHy())*(lambda-omega)-v0)/(-2f*omega);
 			A2=((x0-0.5f*getHy())*(lambda+omega)-v0)/(2f*omega);
 		}	
 		setPath();
 		setPath1();
 		setPath2();
 		
	}
	
	@Override
	protected void calcNext() {
		boolean Dragged=DraggManager.someIsDragged();
    	if( !Dragged ) {
    		float x=Pos(t);
    		m.setY(x);
    		float v=Vel(t);
    		m.setV(new Vec2(0f,v));
    		//float a=Acc(t);
    		//m.setF(Color.YELLOW,m.Pos(),new Vec2(0f,mass*a));
    		m.setF(Color.argb(200, 255, 255, 0),m.Pos(),new Vec2(0f,-k*(x-hy*0.5f)));
    		if( kappa != 0f ) {
    			if( v >0 ) {
    				m.setF(Color.argb(200,128,0,128), new Vec2(m.X()+0.5f*m.R(),m.Y()+0.86602540378444f*m.R()), new Vec2(0,-kappa*v*0.5f));
    				m.setF(Color.argb(200,128,0,128), new Vec2(m.X()-0.5f*m.R(),m.Y()+0.86602540378444f*m.R()), new Vec2(0,-kappa*v*0.5f));
    			} else {
    				m.setF(Color.argb(200,128,0,128), new Vec2(m.X()+0.5f*m.R(),m.Y()-0.86602540378444f*m.R()), new Vec2(0,-kappa*v*0.5f));
    				m.setF(Color.argb(200,128,0,128), new Vec2(m.X()-0.5f*m.R(),m.Y()-0.86602540378444f*m.R()), new Vec2(0,-kappa*v*0.5f));
    			}
    		}
     	} else {
     		// ドラッグしている間は時刻を0に戻す。
     		t=0;
     		x0=m.Y();
     		v0=m.Vy();
     		// 初期値をセット
     		setInitialCondition();
     		
     	}
    	m.setAFromF();
	}


	@Override
	protected void writeSubView(Canvas canvas) {
		// 何もしません。
	}
	float kappa;
	float lambda;
	float omega;
	float k;
	float K;
	float alpha;
	float A;
	float A1;
	float A2;
	float x0;
	float v0;
	protected boolean sFlg;
	public void setK(float kk) {
		k=kk;
	
		setInitialCondition();
	}
	float mass;
	public void setMass(float m1) {
		mass=m1;
		m.setM(m1);
		setInitialCondition();
	}
	public void setKappa(float k2) {
		kappa=k2;	
		setInitialCondition();
	}
	public void TimeRestart() {
		t=0;		
	}
	public void toggleSFlg() {
		sFlg=!sFlg;
		
	}

	@Override
	protected void setSituation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void calcNextEach(MovingObject movingObject) {
		// TODO Auto-generated method stub
		
	}
}
