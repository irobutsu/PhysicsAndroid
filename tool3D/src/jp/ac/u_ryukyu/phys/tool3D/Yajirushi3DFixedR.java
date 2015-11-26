package jp.ac.u_ryukyu.phys.tool3D;

import android.util.FloatMath;

public class Yajirushi3DFixedR extends Yajirushi3D {
	public Yajirushi3DFixedR(float len,float fixr, int RRnum,float r,float g,float b,float a) {
		super(len,fixr,len*0.3f,RRnum,r,g,b,a);
	}
	public Yajirushi3DFixedR(float len,float fixr, int RRnum,float r,float g,float b,float a,boolean f) {
		super(len,fixr,len*0.3f,RRnum,r,g,b,a,f);
	}
	public Yajirushi3DFixedR(float bx,float by,float bz,float fixr, int RRnum,float r,float g,float b,float a,boolean f) {
		super(FloatMath.sqrt(bx*bx+by*by+bz*bz),fixr,FloatMath.sqrt(bx*bx+by*by+bz*bz)*0.3f,RRnum,r,g,b,a,f);
		setThetaPhi((float)Math.atan2(Math.sqrt(bx*bx+by*by),bz), (float)Math.atan2(by,bx));
		
	}
}