package jp.ac.u_ryukyu.phys.Oscillator;

import jp.ac.u_ryukyu.phys.lib.CircleObject;
import jp.ac.u_ryukyu.phys.lib.Vec2;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

class CircleObjectWithSpring extends CircleObject {
	/**
	 * 
	 */
	private OscillatorView CircleObjectWithSpring;

	public CircleObjectWithSpring(OscillatorView oscillatorView, int cc, float rr, Vec2 xx, Vec2 vxx) {
		super(cc, rr, xx, vxx);
		// TODO Auto-generated constructor stub
		CircleObjectWithSpring = oscillatorView;
	}

	@Override
	public void writeP(Canvas cc) {
		// TODO Auto-generated method stub
		super.writeP(cc);
		Paint p=new Paint();
		p.setColor(Color.rgb(80,200,80));
		float s=0.03f*CircleObjectWithSpring.getWx();
		cc.drawLine(s,x.Y(),2f*s+CircleObjectWithSpring.Time()*20f,x.Y(),p);
		p.setColor(c);
		cc.drawCircle(2f*s+CircleObjectWithSpring.Time()*20f,x.Y(),3f,p);
		
		float bane1=0.8f*Y()+0.1f*CircleObjectWithSpring.getHy();
		float bane2=0.2f*Y()+0.4f*CircleObjectWithSpring.getHy();
		
		p.setColor(Color.BLACK);
		p.setStrokeWidth(2f);
		
		cc.drawLine(s, Y(), s, bane1, p);
		cc.drawLine(s, 0.5f*CircleObjectWithSpring.getHy(), s, bane2, p);
		
		
		int i;
		cc.drawLine(s, bane1, s+8f, (bane1*5+bane2)/6f, p);
		cc.drawLine(s, bane2, s+8f, (bane2*5+bane1)/6f, p);
		float bane3=0.3f*s;
		for(i=1 ; i<3 ; i++) {
			cc.drawLine(s+bane3, (bane1*(i*2f-1f) + bane2*(7f-i*2f))/6f, s-bane3,(bane1*(i*2f) + bane2*(6f-i*2f))/6f, p);
			cc.drawLine(s+bane3, (bane1*(i*2f+1f) + bane2*(5f-i*2f))/6f, s-bane3,(bane1*(i*2f) + bane2*(6f-i*2f))/6f, p);
		}	p.setColor(Color.BLACK);
		
		p.setColor(Color.argb(100,255,255,255));
		p.setStrokeWidth(6f);
		
		cc.drawLine(s, Y(), s, bane1, p);
		cc.drawLine(s, 0.5f*CircleObjectWithSpring.getHy(), s, bane2, p);
		
		
		cc.drawLine(s, bane1, s+bane3, (bane1*5+bane2)/6f, p);
		cc.drawLine(s, bane2, s+bane3, (bane2*5+bane1)/6f, p);
		for(i=1 ; i<3 ; i++) {
			cc.drawLine(s+bane3, (bane1*(i*2f-1f) + bane2*(7f-i*2f))/6f, s-bane3,(bane1*(i*2f) + bane2*(6f-i*2f))/6f, p);
			cc.drawLine(s+bane3, (bane1*(i*2f+1f) + bane2*(5f-i*2f))/6f, s-bane3,(bane1*(i*2f) + bane2*(6f-i*2f))/6f, p);
		}
		
	}

	@Override
	public Vec2 PositionInRect(float X1, float Y1, float X2, float Y2,
			Vec2 mp) {
		Vec2 xx=new Vec2(mp);
		xx.setX(0.03f*CircleObjectWithSpring.getWx()); // 横には動かない。
		if( xx.Y() < Y1+r  ) {
			xx.setY(Y1+r);
		}
		if( xx.Y() > Y2-r ) {
			xx.setY(Y2-r);
		}
		return xx;
	}
	
}