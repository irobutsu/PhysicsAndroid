package jp.ac.u_ryukyu.phys.maeno.physlib3d;


public class Yajirushi3DFixedR extends Yajirushi3D {
	public Yajirushi3DFixedR(float len,float fixr, int RRnum,float r,float g,float b,float a) {
		super(len,fixr,len*0.3f,RRnum,r,g,b,a);
	}
	public Yajirushi3DFixedR(float len,float fixr, int RRnum,float r,float g,float b,float a,boolean f) {
		super(len,fixr,len*0.3f,RRnum,r,g,b,a,f);
	}
	public Yajirushi3DFixedR(float bx,float by,float bz,float fixr, int RRnum,float r,float g,float b,float a,boolean f) {
		super((float)Math.sqrt(bx*bx+by*by+bz*bz),fixr,(float)Math.sqrt(bx*bx+by*by+bz*bz)*0.3f,RRnum,r,g,b,a,f);
		setThetaPhi((float)Math.atan2(Math.sqrt(bx*bx+by*by),bz), (float)Math.atan2(by,bx));
		
	}
}