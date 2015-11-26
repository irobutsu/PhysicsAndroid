package jp.ac.u_ryukyu.phys.tool3D;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Lines3D {
	float rr,gg,bb,aa;
	float width;
	float lineVertices[];
	FloatBuffer line;
	int lineCount;
	int mode;
	public Lines3D(float r,float g,float b,float a,float w) {
		rr=r;
		gg=g;
		bb=b;
		aa=a;
		lineCount=0;
		width=w;
		mode=GL10.GL_LINE_STRIP;
	}
	
	public void setLineStripMode() { mode=GL10.GL_LINE_STRIP;}
	public void setLinesMode() { mode=GL10.GL_LINES;}
	public void clear() { lineCount=0; lineVertices=null;}
	public void setPoint(float x1,float y1,float z1) {
		float newLineVertices[]=new float[lineCount*3+3];
		if( lineCount >0 ) {
			System.arraycopy(lineVertices, 0, newLineVertices, 0, lineCount*3);
		}
		newLineVertices[lineCount*3]=x1;
		newLineVertices[lineCount*3+1]=y1;
		newLineVertices[lineCount*3+2]=z1;
		line = makeFloatBuffer(newLineVertices); 
		lineCount++;
		lineVertices=newLineVertices;
	}
	public void setColor(float r,float g,float b,float a) {
		rr=r;
		gg=g;
		bb=b;
		aa=a;
	}
	public void draw(GL10 gl) { 
		if( lineCount < 2) {
			return;
		}
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		gl.glColor4f(rr,gg,bb,aa); 
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, line); 
		gl.glLineWidth(width);
		gl.glDrawArrays(mode, 0, lineCount); 
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