package jp.ac.u_ryukyu.phys.SurfaceCharge;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;


import javax.microedition.khronos.opengles.GL10;

import android.util.FloatMath;

import jp.ac.u_ryukyu.phys.lib.Vec3;
import jp.ac.u_ryukyu.phys.tool3D.MovingObject3D;
import jp.ac.u_ryukyu.phys.tool3D.Yajirushi3D;

public class GridSphere extends MovingObject3D {
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
	float deltaTheta;
	float deltadeltaTheta;
	float deltaPhi;
	float deltadeltaPhi;
	Yajirushi3D yr,yt,yp;
	public GridSphere(float RR,int TTnum,int PPnum,float r,float g,float b,float a) {
		super(r,g,b,a);
		R=RR;
		Pnum=PPnum;
		Tnum=TTnum;
		makePts();
		makeVertices();
//		yr=new Yajirushi3D(0.4f, 0.03f, 0.2f, Xnum,1f,0f,1f,1f);
//		// yr.translatePts(new Vec3(0,0,R));
//		yt=new Yajirushi3D(0.4f, 0.03f, 0.2f, Xnum,1f,1f,0f,1f);
//		yt.setThetaPhiPts((float)(0.5*FPI), 0f);
//		yt.translatePts(new Vec3(0,0,R));
//		yp=new Yajirushi3D(0.4f, 0.03f, 0.2f, Xnum,0f,1f,1f,1f);
//		yp.setThetaPhiPts((float)(0.5*FPI), (float)(0.5*FPI));
//		yp.translatePts(new Vec3(0,0,R));
	}
	final float FPI=(float)Math.PI;
	final float FTPI=2f*FPI;
	final float FHPI=0.5f*FPI;
	protected void makePts() {
		int i,j;
		org_hokkyoku=new Vec3(0f,0f,R);
		org_nankyoku=new Vec3(0f,0f,-R);
		org_sokumen=new Vec3[2*(Tnum-1)][2*Pnum];
		sokumen=new Vec3[2*(Tnum-1)][2*Pnum];
		deltaTheta=FPI/Tnum;
		deltadeltaTheta=deltaTheta*0.05f;
		deltaPhi=2f*FPI/Pnum;
		deltadeltaPhi=deltaPhi*0.05f;
		float x,y,z;
		for(i=0; i<Tnum-1; i++) {
			float theta=(i+1)*deltaTheta;
			float theta2=theta+deltadeltaTheta;
			float sintheta=FloatMath.sin(theta);
			float costheta=FloatMath.cos(theta);
			float sintheta2=FloatMath.sin(theta2);
			float costheta2=FloatMath.cos(theta2);
			theta -= deltadeltaTheta;
			for(j=0; j<Pnum ; j++) {
				float phi=j*deltaPhi;
				float phi2=phi+deltadeltaPhi;
				float cosphi=FloatMath.cos(phi);
				float sinphi=FloatMath.sin(phi);
				float cosphi2=FloatMath.cos(phi2);
				float sinphi2=FloatMath.sin(phi2);
				
				x=R*sintheta*cosphi;
				y=R*sintheta*sinphi;
				z=R*costheta;
				org_sokumen[2*i][2*j]=new Vec3(x,y,z);
				x=R*sintheta*cosphi2;
				y=R*sintheta*sinphi2;
				org_sokumen[2*i][2*j+1]=new Vec3(x,y,z);
				x=R*sintheta2*cosphi;
				y=R*sintheta2*sinphi;
				z=R*costheta2;
				org_sokumen[2*i+1][2*j]=new Vec3(x,y,z);
				x=R*sintheta2*cosphi2;
				y=R*sintheta2*sinphi2;
				org_sokumen[2*i+1][2*j+1]=new Vec3(x,y,z);
			}
		}
		copyFromOrg();
	}
	@Override
	public void translatePts(Vec3 p) {
		int i,j;
		hokkyoku.add(p);
		nankyoku.add(p);
		for(i=0; i<Tnum-1 ; i++) {
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
		for(i=0; i<(Tnum-1)*2 ; i++) {
			for(j=0; j<Pnum*2; j++) {
				sokumen[i][j].add(p);
			}
		}
		makeVertices();
	}
	protected void makeVertices() {
		vertices=new float[24*Pnum*(Tnum-1)+24*Pnum*(Tnum-2)+18*Pnum];
		int i,j,k,jj,jjj;
		
		// for(i=0; i<Tnum-2; i++) {
		for(i=0; i<Tnum-1; i++) {
			k=i*Pnum*24;
			for( j=0 ; j<2*Pnum ; j++ ) {
				int kk=k+12*j;
				jj=j;
				jjj=j+1;
				if( jjj == 2*Pnum ) { jjj=0;}
				vertices[kk]=sokumen[2*i][jj].X();
				vertices[kk+1]=sokumen[2*i][jj].Y();
				vertices[kk+2]=sokumen[2*i][jj].Z();
				vertices[kk+3]=sokumen[2*i+1][jj].X();
				vertices[kk+4]=sokumen[2*i+1][jj].Y();
				vertices[kk+5]=sokumen[2*i+1][jj].Z();
				vertices[kk+6]=sokumen[2*i][jjj].X();
				vertices[kk+7]=sokumen[2*i][jjj].Y();
				vertices[kk+8]=sokumen[2*i][jjj].Z();
				vertices[kk+9]=sokumen[2*i+1][jjj].X();
				vertices[kk+10]=sokumen[2*i+1][jjj].Y();
				vertices[kk+11]=sokumen[2*i+1][jjj].Z();
			}
		}
		k=(Tnum-1)*Pnum*24;
		for(j=0; j<Pnum ; j++ ) {
			jj=j*2;
			jjj=j*2+1;
			int kk=k+j*9;
			if( jjj == 2*Pnum ) {jjj=0;}
			vertices[kk]=hokkyoku.X();
			vertices[kk+1]=hokkyoku.Y();
			vertices[kk+2]=hokkyoku.Z();
			vertices[kk+3]=sokumen[0][jj].X();
			vertices[kk+4]=sokumen[0][jj].Y();
			vertices[kk+5]=sokumen[0][jj].Z();
			vertices[kk+6]=sokumen[0][jjj].X();
			vertices[kk+7]=sokumen[0][jjj].Y();
			vertices[kk+8]=sokumen[0][jjj].Z();
		}
		k=(Tnum-1)*Pnum*24+Pnum*9;
		for(j=0; j<Pnum ; j++ ) {
			int kk=k+j*9;
			jj=j*2;
			jjj=j*2+1;
			if( jjj == 2*Pnum ) {jjj=0;}
			vertices[kk]=nankyoku.X();
			vertices[kk+1]=nankyoku.Y();
			vertices[kk+2]=nankyoku.Z();
			vertices[kk+3]=sokumen[2*Tnum-3][jj].X();
			vertices[kk+4]=sokumen[2*Tnum-3][jj].Y();
			vertices[kk+5]=sokumen[2*Tnum-3][jj].Z();
			vertices[kk+6]=sokumen[2*Tnum-3][jjj].X();
			vertices[kk+7]=sokumen[2*Tnum-3][jjj].Y();
			vertices[kk+8]=sokumen[2*Tnum-3][jjj].Z();
		}
		for(i=0; i<Tnum-2; i++) {
			k=(Tnum-1)*Pnum*24+Pnum*18+i*Pnum*24;
			for( j=0 ; j<Pnum ; j++ ) {
				jj=2*j;
				jjj=2*j+1;
				int kk=k+12*j;
				vertices[kk]=sokumen[2*i][jj].X();
				vertices[kk+1]=sokumen[2*i][jj].Y();
				vertices[kk+2]=sokumen[2*i][jj].Z();
				vertices[kk+3]=sokumen[2*i+2][jj].X();
				vertices[kk+4]=sokumen[2*i+2][jj].Y();
				vertices[kk+5]=sokumen[2*i+2][jj].Z();
				vertices[kk+6]=sokumen[2*i][jjj].X();
				vertices[kk+7]=sokumen[2*i][jjj].Y();
				vertices[kk+8]=sokumen[2*i][jjj].Z();
				vertices[kk+9]=sokumen[2*i+2][jjj].X();
				vertices[kk+10]=sokumen[2*i+2][jjj].Y();
				vertices[kk+11]=sokumen[2*i+2][jjj].Z();
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
		//gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
		gl.glColor4f(rr,gg,bb,aa); 

		gl.glPointSize(50);
	
		int i,j;
		//緯線を書く。
		for(i=0; i<Tnum-1 ; i++ ) {
			float theta=deltaTheta*(i+1);
			float sintheta=FloatMath.sin(theta);
			float costheta=FloatMath.cos(theta);
			for(j=0; j<2*Pnum ; j++ ) {
				float phi;
				if( j%2 ==0 ) {
					phi=j*deltaPhi*0.5f;
				} else {
					phi=(j-1)*deltaPhi*0.5f;
				}
				gl.glNormal3f(
						(float)(sintheta*Math.cos(phi)),
						(float)(sintheta*Math.sin(phi)),
						costheta
						);
				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, i*Pnum*8+j*4, 4);
			}
		}
		// 北極付近
		gl.glNormal3f(0f, 0f, 1f);
		for(j=0; j<Pnum ; j++) {
			gl.glDrawArrays(GL10.GL_TRIANGLES, 8*(Tnum-1)*Pnum+j*3, 3);
		}
		// 南極付近
		gl.glNormal3f(0f, 0f, -1f);
		for(j=0; j<Pnum ; j++) {
			gl.glDrawArrays(GL10.GL_TRIANGLES, 8*(Tnum-1)*Pnum+(Pnum+j)*3, 3);
		}
		// 経線
		for(i=0; i<Tnum-2;i++) {
			float theta=deltaTheta*(i+1);
			for(j=0; j<2*Pnum ; j++ ) {
				float phi=j*deltaPhi;
				gl.glNormal3f(
						FloatMath.sin(theta)*FloatMath.cos(phi),
						FloatMath.sin(theta)*FloatMath.sin(phi),
						FloatMath.cos(theta)
						);
				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 8*(Tnum-1)*Pnum+Pnum*6 +(2*i*Pnum+j)*4, 4);
			}
		}
	}
	public void drawOnePlane(GL10 gl,int p,int t) {
		
	}
	@Override
	public void copyFromOrg() {
		int i,j;
		hokkyoku=new Vec3(org_hokkyoku);
		nankyoku=new Vec3(org_nankyoku);
		for(i=0; i<(Tnum-1)*2 ;i++) {for(j=0 ; j<Pnum*2 ;j++){
			sokumen[i][j]=new Vec3(org_sokumen[i][j]);
		}}
	}	
	
	@Override
	public void setThetaPts(float theta) {
		copyFromOrg();
		int i,j;
		hokkyoku.roty(theta);
		nankyoku.roty(theta);
		for(i=0; i<(Tnum-1) ;i++) {for(j=0 ; j<Pnum ;j++){
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
		for(i=0; i<Tnum-1 ;i++) {
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
		for(i=0; i<Tnum-1 ;i++) {for(j=0 ; j<Pnum ;j++){
			sokumen[i][j].roty(theta);
			sokumen[i][j].rotz(phi);
		}}makeVertices();
	}
	
	public void drawvecr(GL10 gl,float tt) {
		float w=0.02f*tt;
		if( tt<0f ) {
			w=1f;
		} else if( w > 1f ) {
			w -= FloatMath.floor(w);
		}
		
		yr.copyFromOrg();
		yr.translatePts(new Vec3(0,0,w*R));
		yr.setThetaPhi(0f, 0f);
		yr.draw(gl);
		int i,j;
		for(i=0; 2*i<(Tnum-1)*2 ;i++) {
			float theta=deltaTheta*(2*i+1);
			for(j=0; 2*j<2*Pnum ; j++ ) {
				float phi=2*j*deltaPhi;
				yr.setThetaPhi(theta, phi);
				yr.draw(gl);
			}
		}
		yr.setThetaPhi(FPI, 0f);
		yr.draw(gl);
	}
	public void drawvectheta(GL10 gl,float t) {
		int i,j;
		float w=0.02f*t;
		for(i=0; 2*i<(Tnum-1) ;i++) {
			float theta=deltaTheta*(2*i+1)+w;
			while( theta > FPI ) { 
				theta /= FPI;
				theta -=FloatMath.floor(theta);
				theta *= FPI;
			}
			for(j=0; 2*j<2*Pnum ; j++ ) {
				float phi=2*j*deltaPhi;
				yt.setThetaPhi(theta, phi);
				yt.draw(gl);
			}
		}
	}
	public void drawvecphi(GL10 gl,float t) {
		int i,j;
		float w=0.02f*t;
		for(i=0; 2*i<(Tnum-1) ;i++) {
			float theta=deltaTheta*(2*i+1);
			for(j=0; 2*j<2*Pnum ; j++ ) {
				float phi=2*j*deltaPhi;
				yp.setThetaPhi(theta, phi+w);
				yp.draw(gl);
			}
		}
	}
}
