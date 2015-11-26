package jp.ac.u_ryukyu.phys.rotM;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.util.FloatMath;

import jp.ac.u_ryukyu.phys.lib.Vec3;
import jp.ac.u_ryukyu.phys.tool3D.ConeWithoutBottom;
import jp.ac.u_ryukyu.phys.tool3D.MovingObject3D;
import jp.ac.u_ryukyu.phys.tool3D.Sphere;
import jp.ac.u_ryukyu.phys.tool3D.Torus;

// 円電流を表現する3Dオブジェクト
public class RotCurrent3D extends MovingObject3D {	
	float v;
	float R;
	public RotCurrent3D(float len,float R1,float R2,int RRnum,int R2num,float r, float g, float b, float a) {
		super(r, g, b, a);
		t=new Torus(R1, R2, (float)(2.0*Math.PI), RRnum, R2num, 1f, 1f, 1f, a*0.5f);
		ya=new ConeWithoutBottom(len*0.1f,len*0.5f, RRnum, r, g, b, a);
		ya.translatePts(new Vec3(0,0,-0.25f*len));
	
		N=(int)(len*4f);
		if( N<1 ) { N=1;}
		int i;
		s=new Sphere[N];
		s[0]=new Sphere(R1*0.1f,4, 4,1f, 0f, 0f, a*0.5f);
		for(i=1 ; i<N ; i++) {
			s[i]=new Sphere(R1*0.1f,4, 4,0f, 1f, 0f, a*0.5f);
		}
		// s1.translatePts(new Vec3(R1,0f,0f));
		// s2.translatePts(new Vec3(0f,R1,0f));
		// s3.translatePts(new Vec3(-R1,0f,0f));
		// s4.translatePts(new Vec3(0f,-R1,0f));
		rotCount=0f;
		v=0.045f;
		R=R1;
	}
	Torus t;
	ConeWithoutBottom ya;
	Sphere s[];
	int N;
	
	@Override
	public void copyFromOrg() {
		// TODO Auto-generated method stub
		t.copyFromOrg();
		ya.copyFromOrg();
		int i;
		for(i=0 ; i<N ; i++) {
			s[i].copyFromOrg();
		}
	}

	@Override
	public void translatePts(Vec3 p) {
		t.translatePts(p);
		ya.translatePts(p);
		int i;
		for(i=0 ; i<N ; i++) {
			s[i].translatePts(p);
		}
	}

	@Override
	public void setThetaPts(float theta) {
		t.setThetaPts(theta);
	}

	@Override
	public void setPhiPts(float phi) {
		t.setPhiPts(phi);
		ya.setPhiPts(phi);
	}

	@Override
	public void setThetaPhiPts(float theta, float phi) {
		t.setThetaPhiPts(theta, phi);
		ya.setThetaPhiPts(theta, phi);
	}

	float rotCount;
	@Override
	public void drawHontai(GL10 gl) {
		rotCount +=v;
		int i;
		for(i=0 ; i<N ; i++) {
			s[i].copyFromOrg();
			float angle=((float)(2.0*Math.PI*i))/N+rotCount;
			s[i].translatePts(new Vec3(R*FloatMath.cos(angle),R*FloatMath.sin(angle),0f));
		}
		t.drawHontai(gl);
		for(i=0 ; i<N ; i++) {
			s[i].drawHontai(gl);
		}
		ya.drawHontai(gl);
	}

}
