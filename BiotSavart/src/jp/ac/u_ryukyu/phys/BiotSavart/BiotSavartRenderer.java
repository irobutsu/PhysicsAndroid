package jp.ac.u_ryukyu.phys.BiotSavart;



import javax.microedition.khronos.opengles.GL10;

import jp.ac.u_ryukyu.phys.lib.Vec3;

import jp.ac.u_ryukyu.phys.tool3D.Cone;
import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.Line3D;
import jp.ac.u_ryukyu.phys.tool3D.Torus;
import jp.ac.u_ryukyu.phys.tool3D.Yajirushi3DScaledR;
import jp.ac.u_ryukyu.phys.tool3D.Yajirushi3DFixedR;

import android.opengl.GLSurfaceView.Renderer;
import android.util.FloatMath;

public class BiotSavartRenderer extends DraggableRenderer implements Renderer {
	protected float t;
	
	float X=0;
	float Z=1f;
	final float  R=1f;
	final float factorB=0.4f;
	final float lengthJ=0.4f;

	private boolean xFlg;
	private boolean yFlg;
	private boolean zFlg;
	
	Cone cone=new Cone(1f,1f,20,1f,1f,1f,1f);
	Torus torus=new Torus(R,0.02f,40,10,1f,1f,1f,1f);
	Yajirushi3DScaledR vecj=new Yajirushi3DScaledR(lengthJ,20,0f,1f,0f,1f);
	Yajirushi3DScaledR vecB=new Yajirushi3DScaledR(1f,20,0f,0f,1f,1f);
	Yajirushi3DFixedR vecr;

	Yajirushi3DFixedR xjiku=new Yajirushi3DFixedR(10f,0.005f,4,1f,0f,0f,1f,true);
	Yajirushi3DFixedR yjiku=new Yajirushi3DFixedR(10f,0.005f,4,1f,0f,0f,1f,true);
	Yajirushi3DFixedR zjiku=new Yajirushi3DFixedR(10f,0.005f,4,1f,0f,0f,1f,true);

	public BiotSavartRenderer() {
		super(0f,(float)(Math.PI/4));
		xjiku.setThetaPhi((float)(Math.PI/2),0f);
		yjiku.setThetaPhi((float)(Math.PI/2),(float)(Math.PI/2));
	}
	@Override
	public void drawContent(GL10 gl) {
		torus.draw(gl);

		float omegat=t*((float)(Math.PI/4.0));
		float sin=FloatMath.sin(omegat);
		float cos=FloatMath.cos(omegat);
		
		Vec3 vecJ=new Vec3(-sin,cos,0f);

		vecj.setThetaPhi(3.141592f*0.5f,omegat+1.5708f);
		Vec3 vecR=new Vec3(X-R*cos,-R*sin,Z);
		float rr=vecR.NormSquare();
		float rrsq=FloatMath.sqrt(rr);

		if( movingMode) {
			vecr=new Yajirushi3DFixedR(rrsq,0.03f,5,1f,0f,0f,0.5f);
		} else {
			vecr=new Yajirushi3DFixedR(rrsq,0.03f,5,1f,1f,1f,0.4f);
		}
		vecr.setThetaPhi(vecR.Theta(),vecR.Phi());
		Vec3 n=new Vec3(R,0f,0f);
		n.rotz(omegat);
		vecr.setPos(n);
		vecj.setPos(n);
		vecj.draw(gl);
		vecr.draw(gl);

		Vec3 vecB1=vecJ.Cross(vecR);
		
		vecB1.div(rr*rrsq);
		vecB1.mul(factorB);

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
			float f=(float)(factorB*lengthJ*80/(2.0*Math.PI));
			Vec3 vecBa=new Vec3(f*Bx,f*By,f*Bz);
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



	public void setT(float t2) {
		t=t2;		
	}
}
