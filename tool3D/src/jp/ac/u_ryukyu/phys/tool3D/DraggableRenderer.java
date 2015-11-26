package jp.ac.u_ryukyu.phys.tool3D;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import jp.ac.u_ryukyu.phys.lib.Vec2;
import jp.ac.u_ryukyu.phys.lib.Vec3;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;


abstract public class DraggableRenderer implements Renderer {
	protected FloatBuffer matrix; // 回転と並進の行列
	//protected float matrix[];
	protected Vec3 translationVec; 
	private Vec3 tmpTranslate = Vec3.zero;
	private Vec3 saveTranslate = Vec3.zero;
	
	
	protected Vec3 translationDefault=new Vec3(0,0,-3f);

	public void setTranslationDefault(Vec3 tDefault) {
		this.translationDefault = tDefault;
		resetView();
	}

	protected Matrix3x3 M;
	protected Matrix3x3 tmpmatrix;  // 現在の変換行列は、tmpmatrix*savematrix
	protected Matrix3x3 savematrix; // ドラッグが始まった時の変換行列
	protected Matrix3x3 rotmatrixDefault=new Matrix3x3(1,0,0,0,1,0,0,0,1);

	protected float tmpExpRate=1f;
	protected float saveExpRate=1f;
	protected float defaultExpRate=1f;
	
	public DraggableRenderer() {
		tmpmatrix=new Matrix3x3(1,0,0,0,1,0,0,0,1);
		savematrix=new Matrix3x3(1,0,0,0,1,0,0,0,1);
		matrix=FloatBuffer.allocate(16);
		//matrix=new float[16];
		M=new Matrix3x3(1,0,0,0,1,0,0,0,1);
	}
	public DraggableRenderer(float theta,float phi) {
		tmpmatrix=new Matrix3x3(rotmatrixDefault);
		savematrix=new Matrix3x3(theta,phi);
		matrix=FloatBuffer.allocate(16);
		//matrix=new float[16];
		M=new Matrix3x3(theta,phi);
	}
	
	public Vec3 xjiku() {
		return new Vec3(
				M.M11,M.M21,M.M31
				);
	}

	public Vec3 yjiku() {
		return new Vec3(
				M.M12,M.M22,M.M32
				);
	}

	public Vec3 zjiku() {
		return new Vec3(
				M.M13,M.M23,M.M33
				);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glMatrixMode(GL10.GL_MODELVIEW);	
		gl.glLoadIdentity();

		matrix.put(0,M.M11); matrix.put(4,M.M12); matrix.put(8,M.M13);  matrix.put(12,translationVec.X());
		matrix.put(1,M.M21); matrix.put(5,M.M22); matrix.put(9,M.M23);  matrix.put(13,translationVec.Y());
		matrix.put(2,M.M31); matrix.put(6,M.M32); matrix.put(10,M.M33); matrix.put(14,translationVec.Z());
		matrix.put(3,0f);    matrix.put(7,0f);    matrix.put(11,0f);    matrix.put(15,1f);
		
		gl.glMultMatrixf(matrix);
	
		GLU.gluLookAt(gl, 0f, 0f, 1f, 0f, 0f, 0f,0f,1f, 0f);
		float expRate=saveExpRate*tmpExpRate;
		
		gl.glScalef(expRate,expRate,expRate);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT 
				| GL10.GL_DEPTH_BUFFER_BIT);
		//gl.glMatrixMode(GL10.GL_PROJECTION);
		drawContent(gl);
	}
	abstract protected void drawContent(GL10 gl);
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
	    
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// gl.glMatrixMode(GL10.GL_MODELVIEW);
	    gl.glLoadIdentity();    
	    GLU.gluPerspective(gl, 45f,(float) width / height, 0.1f, 50f);
	
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig cnf) {
	    gl.glEnable(GL10.GL_DEPTH_TEST);
	    gl.glDepthFunc(GL10.GL_LEQUAL);
	    gl.glEnable(GL10.GL_COLOR_MATERIAL);
	    gl.glEnable(GL10.GL_BLEND);
	    gl.glBlendFunc(GL10.GL_SRC_ALPHA,GL10.GL_ONE_MINUS_SRC_ALPHA);
	    gl.glEnable(GL10.GL_LIGHTING);
	    gl.glEnable(GL10.GL_LIGHT0);
	    //gl.glClearColor(0.6f,0.8f,1,1);
	    gl.glDisable(GL10.GL_CULL_FACE); //カリングを無効
	//	gl.glCullFace(GL10.GL_BACK); 	//両面描画
	}

	protected boolean movingMode;


	public void setExpandRate(float f) {
		tmpExpRate=f;
	}

	public void saveExpandRate() {
		saveExpRate=tmpExpRate*saveExpRate;		
		tmpExpRate=1f;
	}
	
	public void saveMatrix() {
		// 次の段階では、現在の変換行列が基準になる。
		savematrix=tmpmatrix.Prod(savematrix);
		tmpmatrix=new Matrix3x3(1,0,0,0,1,0,0,0,1);
		// 基準が変更されたので、現在の変換行列を１に戻す。
	//	Log.d("M11",String.valueOf(savematrix.M11));
	}

	public void setRotationDefault(Matrix3x3 d) {
		rotmatrixDefault=new Matrix3x3(d);
		resetView();
	}
	
	public void setRotation(Vec2 a){
		float ph=(float)Math.atan2(a.Y(), -a.X());
		float th=a.Norm();
		
		
		Matrix3x3 k=new Matrix3x3(th,ph);
		Matrix3x3 b=new Matrix3x3(-ph);
		tmpmatrix=k.Prod(b);
		M=tmpmatrix.Prod(savematrix);
	}

	public void setTranslate(Vec2 p) {	
		tmpTranslate=saveTranslate.Sum(new Vec3(-p.X(),p.Y(),0f));
		translationVec=tmpTranslate;
	}


	public void setMovingMode(boolean yes) {
		movingMode=yes;
	}
	public void setTwist(float angle) {
		tmpmatrix=new Matrix3x3(angle);
		M=tmpmatrix.Prod(savematrix);
	}
	public void resetView() {
		saveExpRate=defaultExpRate;
		tmpExpRate=1f;
		savematrix=new Matrix3x3(rotmatrixDefault);
		tmpmatrix=new Matrix3x3(Matrix3x3.unitMatrix);
		M=tmpmatrix.Prod(savematrix);
		translationVec=new Vec3(translationDefault);
		saveTranslate=new Vec3(translationVec);
	}
	public void setSaveTranslate() {
		saveTranslate=new Vec3(translationVec);
	}
}