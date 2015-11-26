package jp.ac.u_ryukyu.phys.LineCharge;




import javax.microedition.khronos.opengles.GL10;

import jp.ac.u_ryukyu.phys.lib.Vec3;

import jp.ac.u_ryukyu.phys.tool3D.Cone;
import jp.ac.u_ryukyu.phys.tool3D.Cylinder;
import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.Line3D;
import jp.ac.u_ryukyu.phys.tool3D.Matrix3x3;
import jp.ac.u_ryukyu.phys.tool3D.Torus;
import jp.ac.u_ryukyu.phys.tool3D.Yajirushi3DScaledR;
import jp.ac.u_ryukyu.phys.tool3D.Yajirushi3DFixedR;

import android.opengl.GLSurfaceView.Renderer;
import android.util.FloatMath;

public class LineChargeRenderer extends DraggableRenderer implements Renderer {
	protected int t;
	
	float X=0;
	float Z=0f;
	float  R=1f;
	final float factorB=0.4f;
	float lengthJ=0.4f;

	private boolean xFlg;
	private boolean yFlg;
	private boolean zFlg;
	
	private boolean circleMode=false;
	
	Cylinder cylinder=new Cylinder(0.02f,2*R,10,1f, 1f, 1f, 1f);
	Cone cone=new Cone(1f,1f,20,1f,1f,1f,1f);
	Torus torus=new Torus(R,0.02f,10,10,1f,1f,1f,1f);
	Cylinder vecj=new Cylinder(0.022f,lengthJ,20,0f,1f,0f,1f);
	Yajirushi3DScaledR vecB=new Yajirushi3DScaledR(1f,20,0f,0f,1f,1f);
	Yajirushi3DFixedR vecr;

	Yajirushi3DFixedR xjiku=new Yajirushi3DFixedR(10f,0.005f,4,1f,0f,0f,1f,true);
	Yajirushi3DFixedR yjiku=new Yajirushi3DFixedR(10f,0.005f,4,1f,0f,0f,1f,true);
	Yajirushi3DFixedR zjiku=new Yajirushi3DFixedR(10f,0.005f,4,1f,0f,0f,1f,true);

	int N;
	public void setN(int N2) {
		N=N2;
		torus=new Torus(R,0.02f,N,10,1f,1f,1f,1f);
		if( circleMode ) {
			lengthJ=(float)(2.0*Math.PI*R/N);
		} else {
			lengthJ=2*R/N;
		}
		vecj=new Cylinder(0.022f,lengthJ,20,0f,1f,0f,1f);
	}
	public void setR(float r) {
		R=r;
		torus=new Torus(R,0.02f,N,10,1f,1f,1f,1f);
		cylinder=new Cylinder(0.02f,2*R,10,1f, 1f, 1f, 1f);
		cylinder.translatePts(new Vec3(0,0,-R));
	}
	
	public void setCircleMode(boolean t) {
		circleMode=t;
	}
	public LineChargeRenderer() {
		super(0f,(float)(Math.PI/4));
		xjiku.setThetaPhi((float)(Math.PI/2),0f);
		yjiku.setThetaPhi((float)(Math.PI/2),(float)(Math.PI/2));
		cylinder.translatePts(new Vec3(0,0,-R));
		
		float cos=FloatMath.cos(1f);
		float sin=FloatMath.sin(1f);
		setRotationDefault(new Matrix3x3(1,0,0,0,cos,sin,0,-sin,cos));
		this.setTranslationDefault(new Vec3(-0.9f,0,-3.5f));
	}
	@Override
	public void drawContent(GL10 gl) {
		if( circleMode ) {
			torus.draw(gl);
		} else {
			cylinder.draw(gl);
		}
		float omegat=(float) ((t+0.5f)*(2.0*Math.PI)/N);
		float omegat1=(float) ((t)*(2.0*Math.PI)/N);
		float omegat2=(float) ((t+1f)*(2.0*Math.PI)/N);
		float sin1=FloatMath.sin(omegat1);
		float cos1=FloatMath.cos(omegat1);
		float sin2=FloatMath.sin(omegat2);
		float cos2=FloatMath.cos(omegat2);
		// 以上はcircleMode=trueで必要。
		// 以下はcircleMode=falseで必要。
		float z=2*(t+0.5f)*R/N-R;
		
		Vec3 vecR;
		if( circleMode ) {
			vecR=new Vec3(X-0.5f*R*(cos1+cos2),-0.5f*R*(sin1+sin2),Z);
		} else {
			vecR=new Vec3(X,0f,Z-z);
		}
		float rr=vecR.NormSquare();
		float rrsq=FloatMath.sqrt(rr);

		if( movingMode) {
			vecr=new Yajirushi3DFixedR(rrsq,0.03f,5,1f,0f,0f,0.5f);
		} else {
			vecr=new Yajirushi3DFixedR(rrsq,0.03f,5,1f,1f,1f,0.4f);
		}
		vecr.setThetaPhi(vecR.Theta(),vecR.Phi());
		
		Vec3 n;
		if(circleMode ) {
			n=new Vec3(0.5f*R*(cos1+cos2),0.5f*R*(sin1+sin2),0f);
		} else {
			n=new Vec3(0,0f,z);
		}
		vecr.setPos(n);
		vecj.copyFromOrg();
		vecj.translatePts(new Vec3(0f,0f,-0.5f*lengthJ));
		// 円柱の重心がnに来るように。
		if( circleMode ) {
			vecj.setThetaPhi(3.141592f*0.5f,omegat+1.5708f);
		} else {
			vecj.setThetaPhi(0f,0f);
		}
		vecj.setPos(n);
		vecj.draw(gl);
		vecr.draw(gl);

		Vec3 vecB1=vecR;
		float B=20;
		
		vecB1.div(rr*rrsq*N);
		vecB1.mul(B);

		vecB=new Yajirushi3DScaledR(vecB1.Norm(),10,0f,0f,1f,1f);
		vecB.setThetaPhi(vecB1.Theta(),vecB1.Phi());
		vecB.setPos(new Vec3(X,0,Z));
		vecB.draw(gl);
		

		float a;
		if( zFlg ) {
			a=vecB1.Z();
			Yajirushi3DScaledR vecBz;

			if( a<0 ) {
				vecBz=new Yajirushi3DScaledR(-a,10,0f,1f,1f,0.5f);
				vecBz.setThetaPhi((float)Math.PI,0f);
			} else {
				vecBz=new Yajirushi3DScaledR(a,10,0f,1f,1f,0.5f);
			}
			vecBz.setPos(new Vec3(X,0,Z));
			vecBz.draw(gl);
		}
		if( xFlg) {
			a=vecB1.X();
			Yajirushi3DScaledR vecBx;
			if( a<0 ) {
				vecBx=new Yajirushi3DScaledR(-a,10,1f,1f,0f,0.5f);
				vecBx.setThetaPhi((float)(Math.PI/2.0),(float)Math.PI);
			} else {
				vecBx=new Yajirushi3DScaledR(a,10,1f,1f,0f,0.5f);
				vecBx.setThetaPhi((float)(Math.PI/2.0),0f);
			}
			vecBx.setPos(new Vec3(X,0,Z));
			vecBx.draw(gl);
		}
		if( yFlg ) {
			a=vecB1.Y();
			Yajirushi3DScaledR vecBy;
			if( a<0 ) {
				vecBy=new Yajirushi3DScaledR(-a,10,1f,0f,2f,0.5f);
				vecBy.setThetaPhi((float)(Math.PI/2.0),(float)(-Math.PI/2.0));
			} else {
				vecBy=new Yajirushi3DScaledR(a,10,1f,0f,1f,0.5f);
				vecBy.setThetaPhi((float)(Math.PI/2.0),(float)(Math.PI/2.0));
			}
			vecBy.setPos(new Vec3(X,0,Z));
			vecBy.draw(gl);
		}
		if( aFlg) {
			Vec3 vecBa=new Vec3(Bx,By,Bz);
			vecBa.div(80);
			vecBa.mul(B);
			vecB=new Yajirushi3DScaledR(vecBa.Norm(),10,0f,0f,1f,0.3f);
			vecB.setThetaPhi(vecBa.Theta(),vecBa.Phi());
			vecB.setPos(new Vec3(X,0,Z));
			vecB.draw(gl);
		}
		
		xjiku.draw(gl);
		yjiku.draw(gl);
		zjiku.draw(gl);
	}

	

	public void setZ(float z2) {
		Z=z2;		
	}

	public void setX(float x2) {
		// TODO Auto-generated method stub
		X=x2;
	}
	
	
	public void setXFlg(boolean isChecked) {
		xFlg=isChecked;
	}
	public void setYFlg(boolean isChecked) {
		yFlg=isChecked;
	}
	public void setZFlg(boolean isChecked) {
		zFlg=isChecked;
	}

	float Bx,By,Bz;

	private boolean aFlg;

	public void setTotalB(float bx, float by, float bz) {
		Bx=bx; By=by; Bz=bz;

	}

	public void setAFlg(boolean isChecked) {
		// TODO Auto-generated method stub
		aFlg=isChecked;
	}



	public void setT(int t2) {
		t=t2;		
	}
}
