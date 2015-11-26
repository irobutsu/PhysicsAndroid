package jp.ac.u_ryukyu.phys.tool3D;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Line3D {
	float rr,gg,bb,aa;
	float lineVertices[];
	FloatBuffer line;
	public Line3D(float x1,float y1,float z1,float x2,float y2,float z2) {
		rr=1f;
		gg=1f;
		bb=1f;
		aa=1f;
		lineVertices=new float[6];
		lineVertices[0]=x1;
		lineVertices[1]=y1;
		lineVertices[2]=z1;
		lineVertices[3]=x2;
		lineVertices[4]=y2;
		lineVertices[5]=z2;
		
		line = makeFloatBuffer(lineVertices); 
	}
	public void setColor(float r,float g,float b,float a) {
		rr=r;
		gg=g;
		bb=b;
		aa=a;
	}
	public void draw(GL10 gl) { 
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		gl.glColor4f(rr,gg,bb,aa); 
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, line); 
		gl.glDrawArrays(GL10.GL_LINES, 0, 2); 
	} 
	public static FloatBuffer makeFloatBuffer(float[] arr) { 
		ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4); 
		bb.order(ByteOrder.nativeOrder()); 
		FloatBuffer fb = bb.asFloatBuffer(); 
		fb.put(arr); 
		fb.position(0); 
		return fb; 
	} 
}