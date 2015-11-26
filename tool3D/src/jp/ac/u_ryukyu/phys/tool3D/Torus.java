package jp.ac.u_ryukyu.phys.tool3D;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.opengles.GL10;

import jp.ac.u_ryukyu.phys.lib.Vec3;

public class Torus extends MovingObject3D {
	
	Vec3 pts[][];
	Vec3 org_pts[][];

	float R;
	float r;
	int Rnum;
	int rnum;
	float angle;
	boolean closeFlg;

	public Torus(float RR,float rrr,float an,int RRnum,int rrnum,float r1,float g,float b,float a) {
		super(r1,g,b,a);
		closeFlg=false;
		angle=an;
		R=RR; r=rrr;
		Rnum=RRnum; rnum=rrnum;
		makePts();
		makevertices();
	}

	public Torus(float RR,float rrr,int RRnum,int rrnum,float r1,float g,float b,float a) {
		super(r1,g,b,a);
		closeFlg=true;
		R=RR; r=rrr;
		Rnum=RRnum; rnum=rrnum;
		angle=(float)(2*Math.PI);
		makePts();
		makevertices();
	}
	protected void makePts() {
		int i,j;
		pts=new Vec3[Rnum][rnum];
		org_pts=new Vec3[Rnum][rnum];
		if(closeFlg) {
			for(i=0; i<Rnum; i++) {
				for(j=0; j<rnum ; j++) {
					org_pts[i][j]=new Vec3(
							(float)((R+r*Math.cos(angle*j/rnum))*Math.cos(angle*i/Rnum)),
							(float)((R+r*Math.cos(angle*j/rnum))*Math.sin(angle*i/Rnum)),
							(float)(r*Math.sin(angle*j/rnum))
							);
				}
			}
		} else {
			for(i=0; i<Rnum; i++) {
				for(j=0; j<rnum ; j++) {
					org_pts[i][j]=new Vec3(
							(float)((R+r*Math.cos(angle*j/rnum))*Math.cos(angle*i/(Rnum-1))),
							(float)((R+r*Math.cos(angle*j/rnum))*Math.sin(angle*i/(Rnum-1))),
							(float)(r*Math.sin(angle*j/rnum))
							);
				}
			}
		}
			
		copyFromOrg();
	}
	protected void makevertices() {
		int i,j;
		// これでRnum*rnum個の点ができたので、面を作る。
		if( closeFlg) {
			vertices=new float[12*Rnum*rnum];
			for(i=0; i<Rnum; i++) {
				int ii=i+1;
				if( ii==Rnum ) { ii=0; }
				for(j=0; j<rnum; j++) {
					int jj=j+1;
					if( jj==rnum) {
						jj=0;
					}
					vertices[12*(i*rnum+j)]=pts[i][j].X();
					vertices[12*(i*rnum+j)+1]=pts[i][j].Y();
					vertices[12*(i*rnum+j)+2]=pts[i][j].Z();
					vertices[12*(i*rnum+j)+3]=pts[ii][j].X();
					vertices[12*(i*rnum+j)+4]=pts[ii][j].Y();
					vertices[12*(i*rnum+j)+5]=pts[ii][j].Z();
					vertices[12*(i*rnum+j)+6]=pts[i][jj].X();
					vertices[12*(i*rnum+j)+7]=pts[i][jj].Y();
					vertices[12*(i*rnum+j)+8]=pts[i][jj].Z();
					vertices[12*(i*rnum+j)+9]=pts[ii][jj].X();
					vertices[12*(i*rnum+j)+10]=pts[ii][jj].Y();
					vertices[12*(i*rnum+j)+11]=pts[ii][jj].Z();
				}
			}
		} else {
			vertices=new float[12*(Rnum-1)*rnum];
			for(i=0; i<Rnum-1; i++) {
				int ii=i+1;
				for(j=0; j<rnum; j++) {
					int jj=j+1;
					if( jj == rnum ) {
						jj=0;
					}
					vertices[12*(i*(rnum)+j)]=pts[i][j].X();
					vertices[12*(i*(rnum)+j)+1]=pts[i][j].Y();
					vertices[12*(i*(rnum)+j)+2]=pts[i][j].Z();
					vertices[12*(i*(rnum)+j)+3]=pts[ii][j].X();
					vertices[12*(i*(rnum)+j)+4]=pts[ii][j].Y();
					vertices[12*(i*(rnum)+j)+5]=pts[ii][j].Z();
					vertices[12*(i*(rnum)+j)+6]=pts[i][jj].X();
					vertices[12*(i*(rnum)+j)+7]=pts[i][jj].Y();
					vertices[12*(i*(rnum)+j)+8]=pts[i][jj].Z();
					vertices[12*(i*(rnum)+j)+9]=pts[ii][jj].X();
					vertices[12*(i*(rnum)+j)+10]=pts[ii][jj].Y();
					vertices[12*(i*(rnum)+j)+11]=pts[ii][jj].Z();
				}
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
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
		gl.glColor4f(rr, gg, bb, aa);
		int i;
		int shuki;
		if( closeFlg ) {
			shuki=Rnum;
		} else {
			shuki=Rnum-1;
		}
		
		for(i=0; i<rnum*shuki ; i++ ) {
			int ii=i/rnum;
			int jj=i-ii*rnum;
			int iii;
			if( ii==Rnum-1 ) {
				iii=0;
			} else {
				iii=ii+1;
			}
			int jjj;
			if( jj==rnum-1 ) {
				jjj=0;
			} else {
				jjj=jj+1;
			}
			
			Vec3 n=Vec3.NormalizedNormal(pts[ii][jj],pts[iii][jj],pts[iii][jjj]);
			gl.glNormal3f(n.X(), n.Y(),n.Z());
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, i*4, 4);
		}
	}

	@Override
	public void copyFromOrg() {
		int i,j;
		for(i=0; i<Rnum; i++) {
			for(j=0; j<rnum; j++) {
				pts[i][j]=new Vec3(org_pts[i][j]);
			}
		}
	}

	@Override
	public void translatePts(Vec3 p) {
		int i,j;
		for(i=0; i<Rnum; i++) {
			for(j=0; j<rnum; j++) {
				pts[i][j].add(p);
			}
		}
		makevertices();
	}

	@Override
	public void setThetaPts(float theta) {
		copyFromOrg();
		int i,j;
		for(i=0; i<Rnum ;i++) {
			for(j=0; j<rnum; j++) {
			pts[i][j].roty(theta);
			}
		}
		makevertices();
	}

	@Override
	public void setPhiPts(float phi) {
		copyFromOrg();
		int i,j;
		for(i=0; i<Rnum ;i++) {
			for(j=0; j<rnum; j++) {
				pts[i][j].rotz(phi);
			}
		}
		makevertices();
	}

	@Override
	public void setThetaPhiPts(float theta, float phi) {
		copyFromOrg();
		int i,j;
		for(i=0; i<Rnum ;i++) {
			for(j=0; j<rnum; j++) {
				pts[i][j].roty(theta);
				pts[i][j].rotz(phi);
			}
		}
		makevertices();
	}
}