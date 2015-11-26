package jp.ac.u_ryukyu.phys.tool3D;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.opengles.GL10;

import jp.ac.u_ryukyu.phys.lib.Vec3;

public class Plane extends MovingObject3D {
	Vec3 ptNW,ptNE,ptSE,ptSW;
	Vec3 org_ptNW,org_ptNE,org_ptSE,org_ptSW;
	public Plane(Vec3 nw,Vec3 ne,Vec3 se,Vec3 sw,float r,float g,float b,float a) {
		super(r,g,b,a);
		
		org_ptNW=nw;
		org_ptNE=ne;
		org_ptSE=se;
		org_ptSW=sw;
		
		makePts();
		makeVertices();
	}
	
	protected void makePts() {
		copyFromOrg();
	}
	@Override
	public void copyFromOrg(){
		ptNW=org_ptNW;
		ptNE=org_ptNE;
		ptSE=org_ptSE;
		ptSW=org_ptSW;
	}
	protected void makeVertices() {
		vertices=new float[12];
	
		vertices[0]=ptNE.X();
		vertices[1]=ptNE.Y();
		vertices[2]=ptNE.Z();
		vertices[3]=ptSE.X();
		vertices[4]=ptSE.Y();
		vertices[5]=ptSE.Z();
		
		vertices[6]=ptNW.X();
		vertices[7]=ptNW.Y();
		vertices[8]=ptNW.Z();
		vertices[9]=ptSW.X();
		vertices[10]=ptSW.Y();
		vertices[11]=ptSW.Z();
		
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		mVertexBuffer = vbb.asFloatBuffer();
		mVertexBuffer.put(vertices);
		mVertexBuffer.position(0);
		
		
	}
	@Override
	public void translatePts(Vec3 p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setThetaPts(float theta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPhiPts(float phi) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setThetaPhiPts(float theta, float phi) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawHontai(GL10 gl) {	
		Vec3 n;
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
		gl.glColor4f(rr,gg,bb,aa);
		gl.glDisable(GL10.GL_CULL_FACE); // 両面を描く。

	
		n=ptSW.Diff(ptSE).Cross(ptSW.Diff(ptNW));
		gl.glNormal3f(n.X(), n.Y(),n.Z());
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	}

}
