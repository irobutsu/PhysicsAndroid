package jp.ac.u_ryukyu.phys.gsense;



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

public class GsenseRenderer extends DraggableRenderer implements Renderer {
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
	Yajirushi3DScaledR vecj=new Yajirushi3DScaledR(lengthJ,20,0f,1f,0f,1f);
	Yajirushi3DScaledR vecB;
	Yajirushi3DFixedR vecr;

	Yajirushi3DFixedR xjiku=new Yajirushi3DFixedR(10f,0.005f,4,1f,0f,0f,1f,true);
	Yajirushi3DFixedR yjiku=new Yajirushi3DFixedR(10f,0.005f,4,1f,0f,0f,1f,true);
	Yajirushi3DFixedR zjiku=new Yajirushi3DFixedR(10f,0.005f,4,1f,0f,0f,1f,true);

	public GsenseRenderer() {
		super(0f,(float)(Math.PI/4));
		xjiku.setThetaPhi((float)(Math.PI/2),0f);
		yjiku.setThetaPhi((float)(Math.PI/2),(float)(Math.PI/2));
	}
	@Override
	public void drawContent(GL10 gl) {
		float k=-0.15f;
		
		float X;
		float Y;
		float Z=k*gz;
		
		
			X=k*gx;
			Y=k*gy;
	
		
		vecB=new Yajirushi3DScaledR(X,Y,Z,10,0f,0f,1f,1f,false);
		//vecB.setThetaPhi(vecB1.Theta(),vecB1.Phi());
		vecB.setPos(new Vec3(0,0,0));
		vecB.draw(gl);
		
		
		float a;
		if( zFlg ) {
			a=Z;
			Yajirushi3DScaledR vecBz;

			if( a<0 ) {
				vecBz=new Yajirushi3DScaledR(-a,10,0f,1f,1f,0.5f);
				vecBz.setThetaPhi((float)Math.PI,0f);
			} else {
				vecBz=new Yajirushi3DScaledR(a,10,0f,1f,1f,0.5f);
			}
			vecBz.setPos(new Vec3(0,0,0));
			vecBz.draw(gl);
		}
		if( xFlg) {
			a=X;
			Yajirushi3DScaledR vecBx;
			if( a<0 ) {
				vecBx=new Yajirushi3DScaledR(-a,10,1f,1f,0f,0.5f);
				vecBx.setThetaPhi((float)(Math.PI/2.0),(float)Math.PI);
			} else {
				vecBx=new Yajirushi3DScaledR(a,10,1f,1f,0f,0.5f);
				vecBx.setThetaPhi((float)(Math.PI/2.0),0f);
			}
			vecBx.setPos(new Vec3(0,0,0));
			vecBx.draw(gl);
		}
		if( yFlg ) {
			a=Y;
			Yajirushi3DScaledR vecBy;
			if( a<0 ) {
				vecBy=new Yajirushi3DScaledR(-a,10,1f,0f,2f,0.5f);
				vecBy.setThetaPhi((float)(Math.PI/2.0),(float)(-Math.PI/2.0));
			} else {
				vecBy=new Yajirushi3DScaledR(a,10,1f,0f,1f,0.5f);
				vecBy.setThetaPhi((float)(Math.PI/2.0),(float)(Math.PI/2.0));
			}
			vecBy.setPos(new Vec3(0,0,0));
			vecBy.draw(gl);
		}
		xjiku.draw(gl);
		yjiku.draw(gl);
		//zjiku.draw(gl);
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

	private boolean aFlg=true;

	private float gx;

	private float gy;

	private float gz;

	private float g;

	private int turn=0;

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
	public void setG(float x2, float y2, float z2, float gg) {
		gx=x2;
		gy=y2;
		gz=z2;
		g=gg;
	}
	public void setTurn(int t) {
		turn=t;
	}
}
