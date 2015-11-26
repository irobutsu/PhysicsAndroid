package jp.ac.u_ryukyu.phys.xva;

import android.graphics.Color;
import android.util.FloatMath;
import jp.ac.u_ryukyu.phys.lib.CircleObject;
import jp.ac.u_ryukyu.phys.lib.Vec2;

public class CircleObjectKE extends CircleObject {
	float ke; // 跳ね返り係数。
	public CircleObjectKE(int cc, float rr, Vec2 xx, Vec2 vxx) {
		super(cc, rr, xx, vxx);
		ke=0.5f;
	}
	public void setE(float ee){ ke=ee;}

	public void calcNext(float st,float x0,float y0,float x1,float y1) {
		super.calcNextConstA(st);
		if( x.X()<= x0+r) {
			// 世界の端に来た。
			if( a.X() == 0f ) {
				v.setX(-v.X()*ke);
				x.setX(x0+r +(x0+r-x.X()));
			} else {
				float tt=(-pv.X()-(float)Math.sqrt(pv.X()*pv.X()-2*a.X()*(px.X()-x0-r)))/a.X(); // 壁にぶつかるまでの時間
				float mvx=pv.X()+a.X()*tt; // 壁にぶつかった時の速度。
				mvx = -ke*mvx; //跳ね返り係数。
				if( Math.abs(2*mvx) < Math.abs(a.X()*st) ) {
					v.setX(0f);
					x.setX(x0+r);
					setF(Color.argb(200, 255, 255, 128),px.Sum(new Vec2(-r,0)),new Vec2(-a.X(),0));
				} else {
					v.setX(mvx +a.X()*(st-tt)); // その後の加速。計算すると、vy = -pvy+ay*(st-2*tt);
					x.setX(x0+r +mvx*(st-tt)+0.5f*a.X()*(st-tt)*(st-tt));
					Vec2 pa=a;
					adjustA(st);
					setF(Color.argb(200, 255, 255, 128),px.Sum(new Vec2(-r,0)),a.Diff(pa));
				}
			}
		}
		if( x.X()>= x1-r) {
			if( a.X() == 0f) {
				v.setX(-v.X()*ke);
				x.setX(x1-r+(x1-r-x.X()));
			} else {
				float tt=(-pv.X()+(float)Math.sqrt(pv.X()*pv.X()-2*a.X()*(px.X()-x1+r)))/a.X(); // 壁にぶつかるまでの時間
				float mvx=pv.X()+a.X()*tt; // 壁にぶつかった時の速度。
				mvx = -ke*mvx; //跳ね返り係数。
				if( Math.abs(2*mvx) < Math.abs(a.X()*st) ) {
					v.setX(0f);
					x.setX(x1-r);
					setF(Color.argb(200, 255, 255, 128),px.Sum(new Vec2(r,0)),new Vec2(-a.X(),0));
				} else {
					v.setX(mvx +a.X()*(st-tt)); // その後の加速。計算すると、vy = -pvy+ay*(st-2*tt);
					x.setX(x1-r +mvx*(st-tt)+0.5f*a.X()*(st-tt)*(st-tt));
					Vec2 pa=a;
					adjustA(st);
					setF(Color.argb(200, 255, 255, 128),px.Sum(new Vec2(r,0)),a.Diff(pa));
				}
			}
		} 
		if( x.Y()>= y1-r ) {
			if( a.Y() == 0f) {
				v.setY(-v.Y()*ke);
				x.setY(y1-r+(y1-r-x.Y()));
			} else {
				float tt=(-pv.Y()+FloatMath.sqrt(pv.Y()*pv.Y()-2*a.Y()*(px.Y()-y1+r)))/a.Y(); // 壁にぶつかるまでの時間
				float mvy=pv.Y()+a.Y()*tt; // 壁にぶつかった時の速度。
				mvy = -ke*mvy; //跳ね返り係数。
				if( Math.abs(2*mvy) < Math.abs(a.Y()*st) ) {
					v.setY(0f);
					x.setY(y1-r);
					setF(Color.argb(200, 255, 255, 128),px.Sum(new Vec2(0,r)),new Vec2(0,-a.Y()));
				} else {
					v.setY(mvy +a.Y()*(st-tt)); // その後の加速。計算すると、vy = -pvy+a.Y()*(st-2*tt);
					x.setY(y1-r +mvy*(st-tt)+0.5f*a.Y()*(st-tt)*(st-tt));
					Vec2 pa=a;
					adjustA(st);
					setF(Color.argb(200, 255, 255, 128),px.Sum(new Vec2(0,r)),a.Diff(pa));
				}
			}
		} 
		if( x.Y()<=y0+r) {
			if( a.Y() == 0f ) {
				v.setY(-v.Y()*ke);
				x.setY( y0+r +(y0+r-x.Y()) );
			} else {
				float tt=(-pv.Y()-FloatMath.sqrt(pv.Y()*pv.Y()-2*a.Y()*(px.Y()-y0-r)))/a.Y(); // 壁にぶつかるまでの時間
				float mvy=pv.Y()+a.Y()*tt; // 壁にぶつかった時の速度。
				mvy = -ke*mvy; //跳ね返り係数。
				if( Math.abs(2*mvy) < Math.abs(a.Y()*st) ) {
					v.setY(0f);
					x.setY(y0+r);
					setF(Color.argb(200, 255, 255, 128),px.Sum(new Vec2(0,-r)),new Vec2(0,-a.Y()));
				} else {
					v.setY(mvy +a.Y()*(st-tt)); // その後の加速。計算すると、vy = -pvy+a.Y()*(st-2*tt);
					x.setY(y0+r +mvy*(st-tt)+0.5f*a.Y()*(st-tt)*(st-tt));
					Vec2 pa=a;
					adjustA(st);
					setF(Color.argb(200, 255, 255, 128),px.Sum(new Vec2(0,-r)),a.Diff(pa));
				}
			}
		}
	}
}
