package jp.ac.u_ryukyu.phys.tool3D;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.opengles.GL10;

import jp.ac.u_ryukyu.phys.lib.Vec3;

public class Hexahedron extends MovingObject3D{
	Vec3 ptDNW,ptDNE,ptDSE,ptDSW,ptUSW,ptUSE,ptUNE,ptUNW;
	Vec3 org_ptDNW,org_ptDNE,org_ptDSE,org_ptDSW,org_ptUSW,org_ptUSE,org_ptUNE,org_ptUNW;

	
	public Hexahedron(Vec3 dnw,Vec3 dne,Vec3 dse,Vec3 dsw,Vec3 usw,Vec3 use,Vec3 une,Vec3 unw,float r,float g,float b,float a) {
		super(r,g,b,a);
		org_ptDNW=dnw;
		org_ptDNE=dne;
		org_ptDSE=dse;
		org_ptDSW=dsw;
		org_ptUSW=usw;
		org_ptUSE=use;
		org_ptUNE=une;
		org_ptUNW=unw;
		makePts();
		makeVertices();
	}
	protected void makePts() {
		copyFromOrg();
	}
	@Override
	public void copyFromOrg(){
		ptDNW=org_ptDNW;
		ptDNE=org_ptDNE;
		ptDSE=org_ptDSE;
		ptDSW=org_ptDSW;
		ptUSW=org_ptUSW;
		ptUSE=org_ptUSE;
		ptUNE=org_ptUNE;
		ptUNW=org_ptUNW;
	}
	@Override
	public void translatePts(Vec3 p) {
		ptDNW.add(p);
		ptDNE.add(p);
		ptDSE.add(p);
		ptDSW.add(p);
		ptUSW.add(p);
		ptUSE.add(p);
		ptUNE.add(p);
		ptUNW.add(p);
	}

	protected void makeVertices() {
		vertices=new float[54];
	
		vertices[0]=ptUNE.X();
		vertices[1]=ptUNE.Y();
		vertices[2]=ptUNE.Z();
		vertices[3]=ptUSE.X();
		vertices[4]=ptUSE.Y();
		vertices[5]=ptUSE.Z();
		
		vertices[6]=ptUNW.X();
		vertices[7]=ptUNW.Y();
		vertices[8]=ptUNW.Z();
		vertices[9]=ptUSW.X();
		vertices[10]=ptUSW.Y();
		vertices[11]=ptUSW.Z();
		
		vertices[12]=ptDNW.X();
		vertices[13]=ptDNW.Y();
		vertices[14]=ptDNW.Z();
		vertices[15]=ptDSW.X();
		vertices[16]=ptDSW.Y();
		vertices[17]=ptDSW.Z();
		vertices[18]=ptDNE.X();
		vertices[19]=ptDNE.Y();
		vertices[20]=ptDNE.Z();
		vertices[21]=ptDSE.X();
		vertices[22]=ptDSE.Y();
		vertices[23]=ptDSE.Z();
		vertices[24]=ptUNE.X();
		vertices[25]=ptUNE.Y();
		vertices[26]=ptUNE.Z();
		vertices[27]=ptUSE.X();
		vertices[28]=ptUSE.Y();
		vertices[29]=ptUSE.Z();
		
		vertices[30]=ptUSE.X();
		vertices[31]=ptUSE.Y();
		vertices[32]=ptUSE.Z();
		vertices[33]=ptDSE.X();
		vertices[34]=ptDSE.Y();
		vertices[35]=ptDSE.Z();
		vertices[36]=ptUSW.X();
		vertices[37]=ptUSW.Y();
		vertices[38]=ptUSW.Z();
		vertices[39]=ptDSW.X();
		vertices[40]=ptDSW.Y();
		vertices[41]=ptDSW.Z();
		
		vertices[42]=ptUNW.X();
		vertices[43]=ptUNW.Y();
		vertices[44]=ptUNW.Z();
		vertices[45]=ptDNW.X();
		vertices[46]=ptDNW.Y();
		vertices[47]=ptDNW.Z();
		vertices[48]=ptUNE.X();
		vertices[49]=ptUNE.Y();
		vertices[50]=ptUNE.Z();
		vertices[51]=ptDNE.X();
		vertices[52]=ptDNE.Y();
		vertices[53]=ptDNE.Z();
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		mVertexBuffer = vbb.asFloatBuffer();
		mVertexBuffer.put(vertices);
		mVertexBuffer.position(0);
	}

	public void drawHontai(GL10 gl){
		Vec3 n;
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
		gl.glColor4f(rr,gg,bb,aa);

		
		n=ptUSW.Diff(ptUSE).Cross(ptUSW.Diff(ptUNW));
		gl.glNormal3f(n.X(), n.Y(),n.Z());
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		n=ptUSW.Diff(ptUNW).Cross(ptUSW.Diff(ptDSW));
		gl.glNormal3f(n.X(), n.Y(),n.Z());
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 2, 4);
		n=ptDNW.Diff(ptDNE).Cross(ptDNW.Diff(ptDSW));
		gl.glNormal3f(n.X(), n.Y(),n.Z());
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 4, 4);
		n=ptDSE.Diff(ptUSE).Cross(ptDNW.Diff(ptDSE));
		gl.glNormal3f(n.X(), n.Y(),n.Z());
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 6, 4);
		n=ptDSW.Diff(ptDSE).Cross(ptDSW.Diff(ptUSW));
		gl.glNormal3f(n.X(), n.Y(),n.Z());
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 10, 4);
		n=ptDNE.Diff(ptUNE).Cross(ptDNW.Diff(ptDNE));
		gl.glNormal3f(n.X(), n.Y(),n.Z());
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 14, 4);
		
		
		
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
}
