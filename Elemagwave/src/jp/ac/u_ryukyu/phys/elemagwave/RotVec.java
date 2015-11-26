package jp.ac.u_ryukyu.phys.elemagwave;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.util.FloatMath;

import jp.ac.u_ryukyu.phys.lib.Vec3;
import jp.ac.u_ryukyu.phys.tool3D.ConeWithoutBottom;
import jp.ac.u_ryukyu.phys.tool3D.MovingObject3D;
import jp.ac.u_ryukyu.phys.tool3D.Sphere;
import jp.ac.u_ryukyu.phys.tool3D.Torus;

// rotを表現する3Dオブジェクト
public class RotVec extends MovingObject3D {	
	float v;
	float R;
	public RotVec(float len,float R2,int RRnum,int R2num,float r, float g, float b, float a) {
		super(r, g, b, a);
		t=new Torus(0.3f*len, R2, (float)(2*Math.PI), RRnum, R2num, r, g, b, a*0.5f);
		ya=new ConeWithoutBottom(len*0.1f,len*0.5f, RRnum, r, g, b, a);
		ya.translatePts(new Vec3(0,0,-0.25f*len));
		tya=new ConeWithoutBottom(2*R2,4*R2,RRnum, r, g, b, a*0.5f);
		tya.setThetaPhiPts((float)(0.5*Math.PI),(float)(0.5*Math.PI));
		tya.translatePts(new Vec3(0.3f*len,0,0));
	}
	Torus t;
	ConeWithoutBottom ya;
	ConeWithoutBottom tya;
	
	@Override
	public void copyFromOrg() {
		// TODO Auto-generated method stub
		t.copyFromOrg();
		ya.copyFromOrg();
		tya.copyFromOrg();
	}

	@Override
	public void translatePts(Vec3 p) {
		t.translatePts(p);
		ya.translatePts(p);
		tya.translatePts(p);
	}

	@Override
	public void setThetaPts(float theta) {
		t.setThetaPts(theta);
		ya.setThetaPts(theta);
		tya.setThetaPts(theta);
	}

	@Override
	public void setPhiPts(float phi) {
		t.setPhiPts(phi);
		ya.setPhiPts(phi);
		tya.setPhiPts(phi);
	}

	@Override
	public void setThetaPhiPts(float theta, float phi) {
		t.setThetaPhiPts(theta, phi);
		ya.setThetaPhiPts(theta, phi);
		tya.setThetaPhiPts(theta, phi);
	}

	float rotCount;
	@Override
	public void drawHontai(GL10 gl) {
	
	
		t.drawHontai(gl);
	
		ya.drawHontai(gl);
		tya.drawHontai(gl);
	}

}
