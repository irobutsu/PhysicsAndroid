package jp.ac.u_ryukyu.phys.tool3D;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;


import javax.microedition.khronos.opengles.GL10;

import jp.ac.u_ryukyu.phys.lib.Vec3;

public class Sphere extends MovingObject3D {
	Vec3 hokkyoku;
	Vec3 nankyoku;
	Vec3 org_hokkyoku;
	Vec3 org_nankyoku;
	Vec3 sokumen[][];
	Vec3 org_sokumen[][];
	float R;
	float h;
	int Tnum;
	int Pnum;
	
	public Sphere(float RR,int TTnum,int PPnum,float r,float g,float b,float a) {
		super(r,g,b,a);
		
		R=RR;
		Pnum=PPnum;
		Tnum=TTnum;
		makePts();
		makeVertices();
	}

	protected void makePts() {
		int i,j;
		org_hokkyoku=new Vec3(0f,0f,R);
		org_nankyoku=new Vec3(0f,0f,-R);
		org_sokumen=new Vec3[Tnum][Pnum];
		sokumen=new Vec3[Tnum][Pnum];
		double deltaTheta=Math.PI/(Tnum+1);
		double deltaPhi=2.0*Math.PI/Pnum;
		for(i=0; i<Tnum; i++) {
			double theta=(i+1)*deltaTheta;
			for(j=0; j<Pnum ; j++) {
				double phi=j*deltaPhi;
				float x=(float)(R*Math.sin(theta)*Math.cos(phi));
				float y=(float)(R*Math.sin(theta)*Math.sin(phi));
				float z=(float)(R*Math.cos(theta));
				org_sokumen[i][j]=new Vec3(x,y,z);
			}
		}
		copyFromOrg();
	}
	@Override
	public void translatePts(Vec3 p) {
		int i,j;
		hokkyoku.add(p);
		nankyoku.add(p);
		for(i=0; i<Tnum ; i++) {
			for(j=0; j<Pnum; j++) {
			sokumen[i][j].add(p);
			}
		}
		makeVertices();
	}
	@Override
	public void setPts(Vec3 p) {
		copyFromOrg();
		int i,j;
		hokkyoku.add(p);
		nankyoku.add(p);
		for(i=0; i<Tnum ; i++) {
			for(j=0; j<Pnum; j++) {
			sokumen[i][j].add(p);
			}
		}
		makeVertices();
	}
	protected void makeVertices() {
		// これでRnum+1個の点ができたので、面を作る。
		vertices=new float[18*Pnum+12*Pnum*Tnum];
		int i,j;
		//側面部分
		for(i=0; i<Pnum; i++) {
			int ii=i+1;
			if( ii== Pnum) {ii=0;}
			// 北極付近
			vertices[18*(i)]=hokkyoku.X();
			vertices[18*(i)+1]=hokkyoku.Y();
			vertices[18*(i)+2]=hokkyoku.Z();
			vertices[18*(i)+3]=sokumen[0][i].X();
			vertices[18*(i)+4]=sokumen[0][i].Y();
			vertices[18*(i)+5]=sokumen[0][i].Z();
			vertices[18*(i)+6]=sokumen[0][ii].X();
			vertices[18*(i)+7]=sokumen[0][ii].Y();
			vertices[18*(i)+8]=sokumen[0][ii].Z();
			// 南極付近
			vertices[18*(i)+9]=nankyoku.X();
			vertices[18*(i)+10]=nankyoku.Y();
			vertices[18*(i)+11]=nankyoku.Z();
			vertices[18*(i)+12]=sokumen[Tnum-1][ii].X();
			vertices[18*(i)+13]=sokumen[Tnum-1][ii].Y();
			vertices[18*(i)+14]=sokumen[Tnum-1][ii].Z();
			vertices[18*(i)+15]=sokumen[Tnum-1][i].X();
			vertices[18*(i)+16]=sokumen[Tnum-1][i].Y();
			vertices[18*(i)+17]=sokumen[Tnum-1][i].Z();
		}
		// それ以外の側面
		for(j=0; j<Tnum ; j++ ) {
			int jj=j+1;
			if( jj==Tnum) {jj=0;}
			int K=18*Pnum+12*Pnum*j;
			for(i=0; i<Pnum ;i++) {
				int ii=i+1;
				if( ii== Pnum) {ii=0;}				
				int KK=K+12*i;
				vertices[KK]=sokumen[j][i].X();
				vertices[KK+1]=sokumen[j][i].Y();
				vertices[KK+2]=sokumen[j][i].Z();
				vertices[KK+3]=sokumen[j][ii].X();
				vertices[KK+4]=sokumen[j][ii].Y();
				vertices[KK+5]=sokumen[j][ii].Z();
				vertices[KK+6]=sokumen[jj][i].X();
				vertices[KK+7]=sokumen[jj][i].Y();
				vertices[KK+8]=sokumen[jj][i].Z();
				vertices[KK+9]=sokumen[jj][ii].X();
				vertices[KK+10]=sokumen[jj][ii].Y();
				vertices[KK+11]=sokumen[jj][ii].Z();
			}
		}
	
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		mVertexBuffer = vbb.asFloatBuffer();
		mVertexBuffer.put(vertices);
		mVertexBuffer.position(0);
	}
	@Override
	public void drawHontai(GL10 gl){
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
		gl.glColor4f(rr,gg,bb,aa); 
		int i,j;

		for(i=0; i<Pnum ; i++ ) {
			int ii=i+1;
			if( ii==Pnum ) {
				ii=0;
			}

			Vec3 n=Vec3.NormalizedNormal(hokkyoku,sokumen[0][i],sokumen[0][ii]);
			gl.glNormal3f(n.X(), n.Y(),n.Z());
			gl.glDrawArrays(GL10.GL_TRIANGLES, i*6, 3);

			n=Vec3.NormalizedNormal(nankyoku,sokumen[Tnum-1][ii],sokumen[Tnum-1][i]);
			gl.glNormal3f(n.X(), n.Y(),n.Z());
			gl.glDrawArrays(GL10.GL_TRIANGLES, 3+i*6, 3);
		}
		for(i=0; i<Pnum ; i++ ) {
			int ii=i+1;
			if( ii==Pnum ) {
				ii=0;
			}
			for( j=0; j<Tnum; j++ ) {
				int jj=j+1;
				if( jj==Tnum ) {jj=0;}
				Vec3 n=Vec3.NormalizedNormal(sokumen[j][i],sokumen[jj][i],sokumen[jj][ii]);
				gl.glNormal3f(n.X(), n.Y(),n.Z());
				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, Pnum*6+(i+Pnum*j)*4, 4);
			}
		}
	}
	@Override
	public void copyFromOrg() {
		// TODO Auto-generated method stub
		int i,j;
		for(i=0; i<Tnum ;i++) {for(j=0 ; j<Pnum ;j++){
			hokkyoku=new Vec3(org_hokkyoku);
			nankyoku=new Vec3(org_nankyoku);
			sokumen[i][j]=new Vec3(org_sokumen[i][j]);
		}}
	}	
	
	@Override
	public void setThetaPts(float theta) {
		copyFromOrg();
		int i,j;
		hokkyoku.roty(theta);
		nankyoku.roty(theta);
		for(i=0; i<Tnum ;i++) {for(j=0 ; j<Pnum ;j++){
			sokumen[i][j].roty(theta);
		}}
		makeVertices();
	}
	@Override
	public void setPhiPts(float phi) {
		copyFromOrg();
		int i,j;
		hokkyoku.rotz(phi);
		nankyoku.rotz(phi);
		for(i=0; i<Tnum ;i++) {
			for(j=0 ; j<Pnum ;j++){
			sokumen[i][j].rotz(phi);
			}		}
		makeVertices();
	}
	@Override
	public void setThetaPhiPts(float theta,float phi) {
		copyFromOrg();
		int i,j;
		hokkyoku.roty(theta);
		hokkyoku.rotz(phi);
		nankyoku.roty(theta);
		nankyoku.rotz(phi);
		for(i=0; i<Tnum ;i++) {for(j=0 ; j<Pnum ;j++){
			sokumen[i][j].roty(theta);
			sokumen[i][j].rotz(phi);
		}}makeVertices();
	}
}
