package jp.ac.u_ryukyu.phys.funccomposite;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.util.FloatMath;
import jp.ac.u_ryukyu.phys.lib.Vec2;
import jp.ac.u_ryukyu.phys.lib.Vec3;
import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.Lines3D;
import jp.ac.u_ryukyu.phys.tool3D.Matrix3x3;
import jp.ac.u_ryukyu.phys.tool3D.Plane;
import jp.ac.u_ryukyu.phys.tool3D.Sphere;
import jp.ac.u_ryukyu.phys.tool3D.Yajirushi3DFixedR;

public class FuncCompositeRenderer extends DraggableRenderer implements Renderer {

	Lines3D xlabel1,xlabel2,ylabel1,ylabel2,zlabel1,zlabel2;
	
	Plane planexy;
	Plane planeyz;
	Plane planezx;
	Yajirushi3DFixedR xjikuy,xjikuz;
	Yajirushi3DFixedR yjikux,yjikuz;
	Yajirushi3DFixedR zjikux,zjikuy;
	Lines3D linexy;
	Lines3D lineyz;
	Lines3D linezx;
	Lines3D xyz;
	int fnoF=1;
	int fnoG=2;
	float a=1;
	float b=1;
	public void seta(float aa) { a=aa;}
	public void setb(float bb) { b=bb;}
	public void setfnox(int f,float aa) {
		fnoF=f;
		a=aa;
		funcsetXY();
		funcsetZX();
	
	}
	public void setfnoy(int f,float bb) {
		fnoG=f;
		b=bb;
		funcsetYZ();
		funcsetZX();
	
	}
	
	
	float t=0f;

	
	public FuncCompositeRenderer() {
		setTranslationDefault(new Vec3(-1f,1.3f,-9f));
		// setRotationDefault(new Matrix3x3(0f,1f,0f,-1f,0f,0f,0f,0f,1f));
		// setRotationDefault(new Matrix3x3(0.8f,-0.6f,0f,0.6f,0.8f,0f,0f,0f,1f));
		float cos=FloatMath.cos(-0.5f);
		float sin=FloatMath.sin(-0.5f);
		setRotationDefault(new Matrix3x3(0f,1f,0f,
										sin,0f,cos,
										cos,0f,-sin));
		this.defaultExpRate=1f;
		//			setRotation(new Vec2(2f,1f));
		//	saveMatrix();
		
		
		xlabel1=new Lines3D(1f,0f,0f,1f,4f);
		xlabel1.setLinesMode();
		xlabel1.setPoint(-0.3f, 1.9f, -1.98f);
		xlabel1.setPoint(-0.1f, 2, -1.98f);
		xlabel1.setPoint(-0.1f, 1.9f, -1.98f);
		xlabel1.setPoint(-0.3f, 2, -1.98f);
		
		xlabel2=new Lines3D(1f,0f,0f,1f,4f);
		xlabel2.setLinesMode();
		xlabel2.setPoint(-1.98f, 1.9f,0.3f);
		xlabel2.setPoint(-1.98f, 2f,0.1f);
		xlabel2.setPoint(-1.98f, 1.9f,0.1f);
		xlabel2.setPoint(-1.98f, 2f,0.3f);
		
		ylabel1=new Lines3D(0f,0f,1f,1f,4f);
		ylabel1.setLinesMode();
		ylabel1.setPoint(-1.9f, 0.1f, -1.98f);
		ylabel1.setPoint(-1.8f, 0.2f, -1.98f);
		ylabel1.setPoint(-1.9f, 0.3f, -1.98f);
		ylabel1.setPoint(-1.8f, 0.2f, -1.98f);
		ylabel1.setPoint(-1.8f, 0.2f, -1.98f);
		ylabel1.setPoint(-1.7f, 0.2f, -1.98f);
		
		ylabel2=new Lines3D(0f,0f,1f,1f,4f);
		ylabel2.setLinesMode();
		ylabel2.setPoint(-1.8f,-1.98f,0.3f);
		ylabel2.setPoint(-1.9f, -1.98f,0.2f);
		ylabel2.setPoint(-2f, -1.98f,0.3f);
		ylabel2.setPoint(-1.9f, -1.98f,0.2f);
		ylabel2.setPoint(-1.9f,-1.98f, 0.2f);
		ylabel2.setPoint(-1.9f, -1.98f,0.1f);
		
		zlabel1=new Lines3D(1f,0f,1f,1f,4f);
		zlabel1.setPoint(-1.98f, 0.1f, 2f);
		zlabel1.setPoint(-1.98f, 0.3f, 2f);
		zlabel1.setPoint(-1.98f, 0.1f, 1.8f);
		zlabel1.setPoint(-1.98f, 0.3f, 1.8f);
		
		zlabel2=new Lines3D(1f,0f,1f,1f,4f);
		zlabel2.setPoint(-0.1f,-1.98f, 2f);
		zlabel2.setPoint(-0.3f,-1.98f, 2f);
		zlabel2.setPoint(-0.1f,-1.98f, 1.8f);
		zlabel2.setPoint(-0.3f,-1.98f, 1.8f);

		
		
		linexy=new Lines3D(0.5f,0f,0f,1f,2f);
		lineyz=new Lines3D(0.5f,0.5f,0f,1f,2f);
		linezx=new Lines3D(0f,0.5f,0.5f,1f,2f);
		xyz=new Lines3D(1f,1f,1f,0.9f,5f);
		planexy=new Plane(
				new Vec3(-2,-2,-2),
				new Vec3(-2,2,-2),
				new Vec3(2,2,-2),
				new Vec3(2,-2,-2),
				0f,1f,1f,0.5f);
		
		planeyz=new Plane(
				new Vec3(-2,-2,-2),
				new Vec3(-2,-2,2),
				new Vec3(-2,2,2),
				new Vec3(-2,2,-2),
				0f,0f,1f,0.5f);
		
		planezx=new Plane(
				new Vec3(-2,-2,-2),
				new Vec3(2,-2,-2),
				new Vec3(2,-2,2),
				new Vec3(-2,-2,2),
				0f,1f,0f,0.5f);
		
		
		xjikuy=new Yajirushi3DFixedR(4f,0.02f,6,0.8f,0f,0f,1f);
		xjikuy.setThetaPhiPts((float)Math.PI*0.5f,(float)Math.PI*0.5f);
		xjikuy.translatePts(new Vec3(0f,-2f,-2f));
		xjikuz=new Yajirushi3DFixedR(4f,0.02f,6,0.8f,0f,0f,1f);
		xjikuz.setThetaPhiPts((float)Math.PI*0.5f,(float)Math.PI*0.5f);
		xjikuz.translatePts(new Vec3(-2f,-2f,0f));
		yjikux=new Yajirushi3DFixedR(4f,0.02f,6,0f,0f,0.8f,1f);
		yjikux.setThetaPts((float)Math.PI*(-0.5f));
		yjikux.translatePts(new Vec3(2f,0f,-2f));
		yjikuz=new Yajirushi3DFixedR(4f,0.02f,6,0f,0f,0.8f,1f);
		yjikuz.setThetaPts((float)Math.PI*(-0.5f));
		yjikuz.translatePts(new Vec3(2f,-2f,0f));
		zjikux=new Yajirushi3DFixedR(4f,0.02f,6,0.6f,0f,0.6f,1f);
		zjikux.translatePts(new Vec3(0f,-2f,-2f));
		zjikuy=new Yajirushi3DFixedR(4f,0.02f,6,0.6f,0f,0.6f,1f);
		zjikuy.translatePts(new Vec3(-2f,0f,-2f));
		funcsetXY();
		funcsetYZ();
		funcsetZX();
	}
	void funcsetXY() {
		linexy.clear();
		float i;
		float y;
		for( i=-2; i<= 2; i += 0.02f) {
			y=-Func(fnoF,a,i);
			if( !Float.isNaN(y) ) {
				linexy.setPoint(y,i,-1.98f);
			}
		}
	}
	void funcsetYZ() {
		lineyz.clear();
		float i;
		float z;
		for( i=-2; i<= 2; i += 0.02f) {
			z=Func(fnoG,b,i);
			if( !Float.isNaN(z)) {
				lineyz.setPoint(-i,-1.98f,z);
			}
		}
	}
	void funcsetZX() {
		linezx.clear();
		float i;
		float z;
		for( i=-2; i<= 2; i += 0.02f) {
			z=Func(fnoG,b,Func(fnoF,a,i));
			if( !Float.isNaN(z)) {
				linezx.setPoint(-1.98f,i,z);
			}
		}
	}
	float Func(int funcNo,float aa,float x) {
		float ans=0f;
		switch( funcNo ) {
		case 0:
			ans= aa*x;
			break;
		case 1:
			ans= aa/ x;
			break;
		case 2:
			ans= aa*x*x;
			break;
		case 3:
			ans= FloatMath.sqrt(Math.abs(x/ aa));
			break;
		case 4:
			ans= FloatMath.sin(aa*x);
			break;
		case 5:
			ans= (float)Math.asin(x)/aa;
			break;
		case 6:
			ans= (float)Math.exp(aa*x);
			break;
		case 7:
			ans= (float)Math.log(Math.abs(x))/ aa;
			break;
		}
		return ans;
	}
	
	public void setT(float tt) {
		t=tt;
		x= t-FloatMath.floor(t);
		x= 4*x -2;
	}
	
	float x=-2;
	protected FloatBuffer vBuffer;

	@Override
	protected void drawContent(GL10 gl) {
		xjikuy.draw(gl);
		xjikuz.draw(gl);
		yjikux.draw(gl);
		yjikuz.draw(gl);
		zjikux.draw(gl);
		zjikuy.draw(gl);
	
		xlabel1.draw(gl);
		xlabel2.draw(gl);
		ylabel1.draw(gl);
		ylabel2.draw(gl);
		zlabel1.draw(gl);
		zlabel2.draw(gl);
		
		linexy.draw(gl);
		lineyz.draw(gl);
		linezx.draw(gl);
		
		xyz.clear();
		
		xyz.setPoint(0,x,-2);
		float y=Func(fnoF,a,x);
		if( !Float.isNaN(y)) {
			xyz.setPoint(-y,x,-2);
			xyz.setPoint(-y,-2,-2);
			float z=Func(fnoG,b,y);
			if( !Float.isNaN(z)) {
				xyz.setPoint(-y,-2,z);
				xyz.setPoint(-2,-2,z);
				xyz.setPoint(-2,x,z);
				//xyz.setPoint(-2,0,z);
			} else {
				xyz.setPoint(-y, -2, 2);
			}
		} else {
			xyz.setPoint(-2, x, -2);
		}
		xyz.draw(gl);
		
		
		planexy.draw(gl);
		planeyz.draw(gl);
		planezx.draw(gl);
	}
}
