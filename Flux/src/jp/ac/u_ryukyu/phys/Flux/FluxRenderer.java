package jp.ac.u_ryukyu.phys.Flux;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.util.FloatMath;

import jp.ac.u_ryukyu.phys.lib.Vec3;
import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.Matrix3x3;
import jp.ac.u_ryukyu.phys.tool3D.Sphere;
import jp.ac.u_ryukyu.phys.tool3D.Yajirushi3DFixedR;

public class FluxRenderer extends DraggableRenderer implements Renderer {

	Yajirushi3DFixedR vecE;
	Yajirushi3DFixedR vecS;
	float t=0f;
	Sphere s[];
	
	public FluxRenderer() {
		setTranslationDefault(new Vec3(-2f,-3f,-12f));
		setRotationDefault(new Matrix3x3(0f,1f,0f,-1f,0f,0f,0f,0f,1f));
		// setRotationDefault(new Matrix3x3(0.8f,-0.6f,0f,0.6f,0.8f,0f,0f,0f,1f));
		setVecE(new Vec3(1,0,0));
		setVecS(new Vec3(0,1,0));
		this.defaultExpRate=0.05f;
		
		int i;
		s=new Sphere[200];
		for(i=0 ; i<200 ; i++ ) {
			s[i]=new Sphere(1f, 5, 5, 0f, 1f, 0f, 1f);
		//	s[i].setPts(new Vec3(50f,50f,50f));
			s[i].setPts(new Vec3((float)(400*Math.random()-200),(float)(400*Math.random()-200),(float)(400*Math.random()-200)));
		}
	}
	public void setT(float tt) {t=tt;}
	
	
	protected FloatBuffer vBuffer;
	float vertices[];
	float vertices2[];
	float vertices3[];
	@Override
	protected void drawContent(GL10 gl) {
		vecE.draw(gl);
		vecS.draw(gl);
		
		Vec3 vecSv=new Vec3(vecS.vec());

		float SS=vecSv.Norm();
		vecSv.normalize(); // とりあえず長さを1に。
		float vSt=vecSv.Theta()+(float)(Math.PI*0.5);
		float vSp=vecSv.Phi();
		
		Vec3 vecS1=new Vec3(
				FloatMath.sin(vSt)*FloatMath.cos(vSp),
				FloatMath.sin(vSt)*FloatMath.sin(vSp),
				FloatMath.cos(vSt)				
				);
		Vec3 vecS2=vecS1.Cross(vecSv);
		
		
		vecS1.mul(SS); // 長さを100に。
		vecS2.mul(SS); // 長さを100に。
		
		float tt=t-FloatMath.floor(t);
		
		
		float vEx=vecE.vec().X()*tt;
		float vEy=vecE.vec().Y()*tt;
		float vEz=vecE.vec().Z()*tt;	
		Vec3 vE=new Vec3(vEx,vEy,vEz);
		
		int i;
		for(i=0; i<200 ;i ++) {
			s[i].setPos(vE);
			s[i].draw(gl);
		}
		
		
		vertices=new float[12];
		vertices[0]=0f;
		vertices[1]=0f;
		vertices[2]=0f;
		vertices[3]=vecS1.X();
		vertices[4]=vecS1.Y();
		vertices[5]=vecS1.Z();
		vertices[6]=vecS2.X();
		vertices[7]=vecS2.Y();
		vertices[8]=vecS2.Z();
		vertices[9]=vecS1.X()+vecS2.X();
		vertices[10]=vecS1.Y()+vecS2.Y();
		vertices[11]=vecS1.Z()+vecS2.Z();
		ByteBuffer vbb3=ByteBuffer.allocateDirect(vertices.length * 4);
		vbb3.order(ByteOrder.nativeOrder());
		vBuffer= vbb3.asFloatBuffer();
		vBuffer.put(vertices);
		vBuffer.position(0);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuffer);
		gl.glBlendFunc(GL10.GL_ALPHA_BITS, GL10.GL_ONE_MINUS_SRC_ALPHA);

		gl.glColor4f(1f,1f,1f,0.8f);
		gl.glNormal3f(-vecSv.X(), -vecSv.Y(), -vecSv.Z());
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		
		
	
				
		vertices2=new float[12];
		vertices2[0] = vEx;
		vertices2[1] = vEy;
		vertices2[2] = vEz;
		vertices2[3] = vertices[3]+vEx;
		vertices2[4] = vertices[4]+vEy;
		vertices2[5] = vertices[5]+vEz;
		vertices2[6] = vertices[6]+vEx;
		vertices2[7] = vertices[7]+vEy;
		vertices2[8] = vertices[8]+vEz;
		vertices2[9] = vertices[9]+vEx;
		vertices2[10] = vertices[10]+vEy;
		vertices2[11] = vertices[11]+vEz;
		
		ByteBuffer vbb4=ByteBuffer.allocateDirect(vertices2.length * 4);
		vbb4.order(ByteOrder.nativeOrder());
		vBuffer= vbb4.asFloatBuffer();
		vBuffer.put(vertices2);
		vBuffer.position(0);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuffer);
		gl.glBlendFunc(GL10.GL_ALPHA_BITS, GL10.GL_ONE_MINUS_SRC_ALPHA);

		gl.glColor4f(1f,1f,1f,0.8f);
		gl.glNormal3f(vecSv.X(), vecSv.Y(), vecSv.Z());
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		
		vertices3=new float[12];
		vertices3[0] = vertices[0];
		vertices3[1] = vertices[1];
		vertices3[2] = vertices[2];
		vertices3[3] = vertices2[0];
		vertices3[4] = vertices2[1];
		vertices3[5] = vertices2[2];
		vertices3[6] = vertices[6];
		vertices3[7] = vertices[7];
		vertices3[8] = vertices[8];
		vertices3[9] = vertices2[6];
		vertices3[10] = vertices2[7];
		vertices3[11] = vertices2[8];
		
		ByteBuffer vbb5=ByteBuffer.allocateDirect(vertices3.length * 4);
		vbb5.order(ByteOrder.nativeOrder());
		vBuffer= vbb5.asFloatBuffer();
		vBuffer.put(vertices3);
		vBuffer.position(0);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuffer);
		gl.glBlendFunc(GL10.GL_ALPHA_BITS, GL10.GL_ONE_MINUS_SRC_ALPHA);

		gl.glColor4f(1f,0.9f,0f,0.4f);
		gl.glNormal3f(vecSv.X(), vecSv.Y(), vecSv.Z());
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		
		vertices3[0] = vertices[0];
		vertices3[1] = vertices[1];
		vertices3[2] = vertices[2];
		vertices3[3] = vertices2[0];
		vertices3[4] = vertices2[1];
		vertices3[5] = vertices2[2];
		vertices3[6] = vertices[3];
		vertices3[7] = vertices[4];
		vertices3[8] = vertices[5];
		vertices3[9] = vertices2[3];
		vertices3[10] = vertices2[4];
		vertices3[11] = vertices2[5];
		
		ByteBuffer vbb6=ByteBuffer.allocateDirect(vertices3.length * 4);
		vbb6.order(ByteOrder.nativeOrder());
		vBuffer= vbb6.asFloatBuffer();
		vBuffer.put(vertices3);
		vBuffer.position(0);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuffer);
		gl.glBlendFunc(GL10.GL_ALPHA_BITS, GL10.GL_ONE_MINUS_SRC_ALPHA);

		gl.glColor4f(0.9f,1f,0f,0.4f);
		gl.glNormal3f(vecSv.X(), vecSv.Y(), vecSv.Z());
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

		vertices3[0] = vertices[9];
		vertices3[1] = vertices[10];
		vertices3[2] = vertices[11];
		vertices3[3] = vertices2[9];
		vertices3[4] = vertices2[10];
		vertices3[5] = vertices2[11];
		vertices3[6] = vertices[3];
		vertices3[7] = vertices[4];
		vertices3[8] = vertices[5];
		vertices3[9] = vertices2[3];
		vertices3[10] = vertices2[4];
		vertices3[11] = vertices2[5];
		
		ByteBuffer vbb7=ByteBuffer.allocateDirect(vertices3.length * 4);
		vbb7.order(ByteOrder.nativeOrder());
		vBuffer= vbb7.asFloatBuffer();
		vBuffer.put(vertices3);
		vBuffer.position(0);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuffer);
		gl.glBlendFunc(GL10.GL_ALPHA_BITS, GL10.GL_ONE_MINUS_SRC_ALPHA);

		gl.glColor4f(1f,1f,0f,0.4f);
		gl.glNormal3f(vecSv.X(), vecSv.Y(), vecSv.Z());
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		
		vertices3[0] = vertices[9];
		vertices3[1] = vertices[10];
		vertices3[2] = vertices[11];
		vertices3[3] = vertices2[9];
		vertices3[4] = vertices2[10];
		vertices3[5] = vertices2[11];
		vertices3[6] = vertices[6];
		vertices3[7] = vertices[7];
		vertices3[8] = vertices[8];
		vertices3[9] = vertices2[6];
		vertices3[10] = vertices2[7];
		vertices3[11] = vertices2[8];
		
		ByteBuffer vbb8=ByteBuffer.allocateDirect(vertices3.length * 4);
		vbb8.order(ByteOrder.nativeOrder());
		vBuffer= vbb8.asFloatBuffer();
		vBuffer.put(vertices3);
		vBuffer.position(0);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuffer);
		gl.glBlendFunc(GL10.GL_ALPHA_BITS, GL10.GL_ONE_MINUS_SRC_ALPHA);

		gl.glColor4f(0.9f,0.9f,0f,0.4f);
		gl.glNormal3f(vecSv.X(), vecSv.Y(), vecSv.Z());
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	
	}
	
	public void setVecE(Vec3 p) {
		float th=p.Theta();
		float ph=p.Phi();
		float len=p.Norm();
		vecE=new Yajirushi3DFixedR(len,1.2f,10,1f,0f,0f,1f,true);
		vecE.translatePts(new Vec3(0,0,len*0.5f));
		vecE.setThetaPhi(th, ph);
	}
	public void setVecS(Vec3 p) {
		float th=p.Theta();
		float ph=p.Phi();
		float len=p.Norm();
		vecS=new Yajirushi3DFixedR(len,1.2f,10,0f,0f,1f,1f,true);
		vecS.translatePts(new Vec3(0,0,len*0.5f));
		vecS.setThetaPhi(th, ph);
	}	
}
