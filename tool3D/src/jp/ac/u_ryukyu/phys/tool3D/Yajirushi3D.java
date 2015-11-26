package jp.ac.u_ryukyu.phys.tool3D;

import javax.microedition.khronos.opengles.GL10;

import jp.ac.u_ryukyu.phys.lib.Vec3;

public class Yajirushi3D extends MovingObject3D{
	private Cylinder hontai;
	ConeWithoutBottom	saki;
	float length;
	float sakilength;
	Vec3 v;
	boolean midFlg; // Posが矢印の中心か？ defaultではPosは矢印の根元。
	//protected Yajirushi3D() {super(1f,1f,1f,1f);}
	public Yajirushi3D(float len,float w,float sakilen, int RRnum,float r,float g,float b,float a){
		super(r, g, b, a);
		construct_sub(len,w,sakilen,RRnum,false);
	}
	public Yajirushi3D(float len,float w,float sakilen, int RRnum,float r,float g,float b,float a,boolean f) {
		super(r,g,b,a);
		construct_sub(len,w,sakilen,RRnum,f);
	}

	private void construct_sub(float len,float w,float sakilen, int RRnum,boolean f) {
		midFlg=f;
		length=len;
		sakilength=sakilen;
		hontai=new Cylinder(w, len-sakilen, RRnum,rr,gg,bb,aa);
		saki=new ConeWithoutBottom(w*2f, sakilen, RRnum,rr,gg,bb,aa);
		if( midFlg) {
			hontai.translatePts(new Vec3(0f,0f,-0.5f*len));
			saki.translatePts(new Vec3(0f,0f,0.5f*len-sakilen));
		} else {
			saki.translatePts(new Vec3(0f,0f,len-sakilen));
		}
		v=new Vec3(0,0,len);
	}
	
	public Vec3 vec(){
		return new Vec3(
				matrix[0]*v.X()+matrix[4]*v.Y()+matrix[8]*v.Z(),
				matrix[1]*v.X()+matrix[5]*v.Y()+matrix[9]*v.Z(),
				matrix[2]*v.X()+matrix[6]*v.Y()+matrix[10]*v.Z()
				);
	}
	
	
	@Override
	public void translatePts(Vec3 p) {
		hontai.translatePts(p);
		saki.translatePts(p);
	}
	
	@Override
	public void drawHontai(GL10 gl) {
		hontai.drawHontai(gl);
		saki.drawHontai(gl);
	}
	@Override
	public void setThetaPhiPts(float th,float ph){
		hontai.setThetaPhiPts(th, ph);
		saki.setThetaPhiPts(th, ph);
		Vec3 n=new Vec3(0f,0f,length-sakilength);
		n.roty(th);
		n.rotz(ph);
		saki.translatePts(n);
		v=saki.teppen.Diff(hontai.sokoC);
	}
	@Override
	public void copyFromOrg() {
		hontai.copyFromOrg();
		saki.copyFromOrg();
		Vec3 n=new Vec3(0f,0f,length-sakilength);
		saki.translatePts(n);
	}
	@Override
	public void setThetaPts(float th) {
		hontai.setThetaPts(th);
		saki.setThetaPts(th);	
		Vec3 n=new Vec3(0f,0f,length-sakilength);
		n.roty(th);
		saki.translatePts(n);
		v=saki.teppen.Diff(hontai.sokoC);
	}
	@Override
	public void setPhiPts(float ph) {
		hontai.setPhiPts(ph);
		saki.setPhiPts(ph);
		v=saki.teppen.Diff(hontai.sokoC);
	}
	@Override
	public void changeColor(float r,float g,float b,float a) {
		hontai.changeColor(r, g, b, a);
		saki.changeColor(r, g, b, a);
	}
}