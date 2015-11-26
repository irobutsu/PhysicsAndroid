package jp.ac.u_ryukyu.phys.maeno.physlib3d;

public class Yajirushi3DScaledR extends Yajirushi3D {
	int RR;
	boolean flg;
	public Yajirushi3DScaledR(float len,int RRnum,float r,float g,float b,float a) {
		super(len, len*0.08f, len*0.4f, RRnum, r, g, b, a);
		RR=RRnum;
		flg=false;
	}
	public Yajirushi3DScaledR(float len,int RRnum,float r,float g,float b,float a,boolean f) {
		super(len, len*0.08f, len*0.4f, RRnum, r, g, b, a,f);
		RR=RRnum;
		flg=f;
	}
	public Yajirushi3DScaledR(float bx, float by, float bz, int RRnum, float r,float g, float b, float a, boolean f) {
		super((float)Math.sqrt(bx*bx+by*by+bz*bz), (float)Math.sqrt(bx*bx+by*by+bz*bz)*0.08f, (float)Math.sqrt(bx*bx+by*by+bz*bz)*0.4f, RRnum, r, g, b, a,f);
		setThetaPhi((float)Math.atan2(Math.sqrt(bx*bx+by*by),bz), (float)Math.atan2(by,bx));
		RR=RRnum;
		flg=f;
	}
	public void setLength(float len) {
		construct_sub(len,len*0.08f,len*0.4f,RR,flg);
	}
}