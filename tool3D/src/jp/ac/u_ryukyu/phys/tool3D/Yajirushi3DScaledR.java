package jp.ac.u_ryukyu.phys.tool3D;

import android.util.FloatMath;

public class Yajirushi3DScaledR extends Yajirushi3D {
	public Yajirushi3DScaledR(float len,int RRnum,float r,float g,float b,float a) {
		super(len, len*0.08f, len*0.4f, RRnum, r, g, b, a);
	}
	public Yajirushi3DScaledR(float len,int RRnum,float r,float g,float b,float a,boolean f) {
		super(len, len*0.08f, len*0.4f, RRnum, r, g, b, a,f);
	}
	public Yajirushi3DScaledR(float bx, float by, float bz, int RRnum, float r,float g, float b, float a, boolean f) {
		super(FloatMath.sqrt(bx*bx+by*by+bz*bz), FloatMath.sqrt(bx*bx+by*by+bz*bz)*0.08f, FloatMath.sqrt(bx*bx+by*by+bz*bz)*0.4f, RRnum, r, g, b, a,f);
		setThetaPhi((float)Math.atan2(Math.sqrt(bx*bx+by*by),bz), (float)Math.atan2(by,bx));
	}
}