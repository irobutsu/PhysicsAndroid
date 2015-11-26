package jp.ac.u_ryukyu.phys.tool3D;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;


import javax.microedition.khronos.opengles.GL10;

import jp.ac.u_ryukyu.phys.lib.Vec3;

public class Cone extends MovingObject3D {
	Vec3 sokoC;
	Vec3 teppen;
	Vec3 org_sokoC;
	Vec3 org_teppen;
	Vec3 soko[];
	Vec3 org_soko[];
	float R;
	float h;
	int Rnum;
	
	public Cone(float RR,float hh,int RRnum,float r,float g,float b,float a) {
		super(r,g,b,a);
		
		R=RR; h=hh;
		Rnum=RRnum;
		makePts();
		makeVertices();
	}
	
	protected void makePts() {
		int i;
		org_teppen=new Vec3(0f,0f,h);
		org_sokoC=Vec3.zero;
		org_soko=new Vec3[Rnum];
		soko=new Vec3[Rnum];
		double omega=2.0*Math.PI/Rnum;
		for(i=0; i<Rnum; i++) {
			float x=(float)(R*Math.cos(i*omega));
			float y=(float)(R*Math.sin(i*omega));
						org_soko[i]=new Vec3(x,y,0);
		}
		copyFromOrg();
	}
	@Override
	public void translatePts(Vec3 p) {
		int i;
		teppen.add(p);
		sokoC.add(p);
		for(i=0; i<Rnum ; i++) {
			soko[i].add(p);
		}
		makeVertices();
	}
	
	protected void makeVertices() {
		// これでRnum+1個の点ができたので、面を作る。
		vertices=new float[18*Rnum];
		int i;
		//　側面部分
		for(i=0; i<Rnum; i++) {
			int ii=i+1;
			if( ii== Rnum) {ii=0;}
			// 側面部分
			vertices[18*(i)]=teppen.X();
			vertices[18*(i)+1]=teppen.Y();
			vertices[18*(i)+2]=teppen.Z();
			vertices[18*(i)+3]=soko[i].X();
			vertices[18*(i)+4]=soko[i].Y();
			vertices[18*(i)+5]=soko[i].Z();
			vertices[18*(i)+6]=soko[ii].X();
			vertices[18*(i)+7]=soko[ii].Y();
			vertices[18*(i)+8]=soko[ii].Z();
			// 底面部分
			vertices[18*(i)+9]=sokoC.X();
			vertices[18*(i)+10]=sokoC.Y();
			vertices[18*(i)+11]=sokoC.Z();
			vertices[18*(i)+12]=soko[ii].X();
			vertices[18*(i)+13]=soko[ii].Y();
			vertices[18*(i)+14]=soko[ii].Z();
			vertices[18*(i)+15]=soko[i].X();
			vertices[18*(i)+16]=soko[i].Y();
			vertices[18*(i)+17]=soko[i].Z();
		}

	
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		mVertexBuffer = vbb.asFloatBuffer();
		mVertexBuffer.put(vertices);
		mVertexBuffer.position(0);
	}
	@Override
	public void drawHontai(GL10 gl){
		gl.glPushMatrix();
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
		gl.glColor4f(rr,gg,bb,aa);
	
		int i;

		for(i=0; i<Rnum ; i++ ) {
			int ii=i+1;
			if( ii==Rnum ) {
				ii=0;
			}

			Vec3 n=Vec3.NormalizedNormal(teppen,soko[i],soko[ii]);
			gl.glNormal3f(n.X(), n.Y(),n.Z());
			gl.glDrawArrays(GL10.GL_TRIANGLES, i*6, 3);
		}
		for(i=0; i<Rnum ; i++ ) {
			int ii=i+1;
			if( ii==Rnum ) {
				ii=0;
			}

			Vec3 n=Vec3.NormalizedNormal(sokoC,soko[i],soko[ii]);
			gl.glNormal3f(n.X(), n.Y(),n.Z());
			gl.glDrawArrays(GL10.GL_TRIANGLES, i*6+3, 3);
		}
		gl.glPopMatrix();
	}
	@Override
	public void copyFromOrg() {
		// TODO Auto-generated method stub
		int i;
		for(i=0; i<Rnum ;i++) {
			teppen=new Vec3(org_teppen);
			sokoC=new Vec3(org_sokoC);
			soko[i]=new Vec3(org_soko[i]);
		}
	}	@Override
	public void setThetaPts(float theta) {
		copyFromOrg();
		int i;
		teppen.roty(theta);
		sokoC.roty(theta);
		for(i=0; i<Rnum ;i++) {
			soko[i].roty(theta);
		}
		makeVertices();
	}
	@Override
	public void setPhiPts(float phi) {
		copyFromOrg();
		int i;
		teppen.rotz(phi);
		sokoC.rotz(phi);
		for(i=0; i<Rnum ;i++) {
			
			soko[i].rotz(phi);
		}
		makeVertices();
	}
	@Override
	public void setThetaPhiPts(float theta,float phi) {
		copyFromOrg();
		int i;
		teppen.roty(theta);
		teppen.rotz(phi);
		sokoC.roty(theta);
		sokoC.rotz(phi);
		for(i=0; i<Rnum ;i++) {
			soko[i].roty(theta);
			soko[i].rotz(phi);
		}makeVertices();
	}
	
}