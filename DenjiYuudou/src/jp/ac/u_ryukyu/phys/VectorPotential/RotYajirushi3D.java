package jp.ac.u_ryukyu.phys.VectorPotential;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.util.FloatMath;

import jp.ac.u_ryukyu.phys.lib.Vec3;
import jp.ac.u_ryukyu.phys.tool3D.ConeWithoutBottom;
import jp.ac.u_ryukyu.phys.tool3D.MovingObject3D;
import jp.ac.u_ryukyu.phys.tool3D.Torus;

public class RotYajirushi3D extends MovingObject3D {	
	
	public RotYajirushi3D(float len,float R1,float R2,int RRnum,int R2num,float r, float g, float b, float a) {
		super(r, g, b, a);
		t=new Torus(R1, R2, (float)(1.5*Math.PI), RRnum, R2num, r, g, b, a*0.5f);
		ya=new ConeWithoutBottom(len*0.1f,len*0.5f, RRnum, r, g, b, a);
		ya.translatePts(new Vec3(0,0,-0.25f*len));
		c=new ConeWithoutBottom(R1*0.2f, R1*0.4f, RRnum, r, g, b, a*0.5f);
		c.setThetaPhiPts((float)(Math.PI/2), 0);
		c.translatePts(new Vec3(0,-R1,0));
		setMatrices();
	}
	private static float[][] matrices;
	int mCount=0;
	private Torus t;
	private ConeWithoutBottom ya;
	private ConeWithoutBottom c;
	
	@Override
	public void copyFromOrg() {
		// TODO Auto-generated method stub
		t.copyFromOrg();
		ya.copyFromOrg();
		c.copyFromOrg();
	}

	@Override
	public void translatePts(Vec3 p) {
		t.translatePts(p);
		ya.translatePts(p);
		c.translatePts(p);
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
		c.setThetaPhiPts(theta,phi);
	}

	// rotがくるくる回る行列は、先に計算しておいて、アニメーションする時の負担を下げる（つもり）
	static protected void setMatrices()	{
		if( matrices == null) {
			matrices=new float[20][];
			int i;
			for(i=0; i<20 ; i++ ) {
				matrices[i]=new float[16];
				float costh=FloatMath.cos((float)(i*Math.PI/10.0));
				float sinth=FloatMath.sin((float)(i*Math.PI/10.0));
				matrices[i][0]=costh;
				matrices[i][1]=sinth;
				matrices[i][2]=0;
				matrices[i][3]=0;
				matrices[i][4]=-sinth;
				matrices[i][5]=costh;
				matrices[i][6]=0;
				matrices[i][7]=0;
				matrices[i][8]=0;
				matrices[i][9]=0;
				matrices[i][10]=1;
				matrices[i][11]=0;
				matrices[i][12]=0;
				matrices[i][13]=0;
				matrices[i][14]=0;
				matrices[i][15]=1;
			}
		}
	}
	@Override
	public void drawHontai(GL10 gl) {
	//	setMatrices();
		gl.glMultMatrixf(matrices[mCount],0);
		mCount++;
		if( mCount >19 ) {
			mCount=0;
		}
		t.drawHontai(gl);
		c.drawHontai(gl);
		ya.drawHontai(gl);
	}

}
