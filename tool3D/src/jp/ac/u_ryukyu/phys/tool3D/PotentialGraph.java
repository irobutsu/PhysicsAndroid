package jp.ac.u_ryukyu.phys.tool3D;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.util.FloatMath;

import jp.ac.u_ryukyu.phys.lib.Vec3;

public class PotentialGraph extends MovingObject3D {
	protected Vec3 pts[][];
	protected Vec3 cls[][];
	protected float verticesCol[];
	protected float colorScale=0.2f;
	protected float latticeHeight=3f;
	protected float latticeMax=15f;
	protected float latticeMin=-15f;
	protected float verticesLattice[];
	protected Line3D latticeLines[][];
	protected int baseMode; // 0:書かない 1:面で 2:線で
	
	
	public void setBaseMode(int m) {
		if( m==1 || m==2 ) {
			baseMode=m;
			setLattice(latticeMin,latticeMax,latticeHeight);
		} else {
			baseMode=0;
		}
	}
	
	public void setLattice(float min,float max,float height) {
		latticeMax=max;
		latticeMin=min;
		latticeHeight=height;
		int k;
		int kmax=(int)((latticeMax-latticeMin)/latticeHeight);
		latticeLines=new Line3D[kmax][4];
		float y=latticeMin;
		verticesLattice=new float[(int)(12*(latticeMax-latticeMin)/latticeHeight)];

		switch(baseMode) {
		case 1:
			for( k=0 ; k<kmax ; k++) {
				verticesLattice[12*k]=-0.1f*w/2;
				verticesLattice[12*k+1]=0.002f*h*y;
				verticesLattice[12*k+2]=-0.1f*h/2;
				verticesLattice[12*k+3]=0.1f*w/2;
				verticesLattice[12*k+4]=0.002f*h*y;
				verticesLattice[12*k+5]=-0.1f*h/2;
				verticesLattice[12*k+6]=-0.1f*w/2;
				verticesLattice[12*k+7]=0.002f*h*y;
				verticesLattice[12*k+8]=0.1f*h/2;
				verticesLattice[12*k+9]=0.1f*w/2;
				verticesLattice[12*k+10]=0.002f*h*y;
				verticesLattice[12*k+11]=0.1f*h/2;
				y += latticeHeight;
			}
			ByteBuffer vbb3=ByteBuffer.allocateDirect(verticesLattice.length * 4);
			vbb3.order(ByteOrder.nativeOrder());
			latticeBuffer= vbb3.asFloatBuffer();
			latticeBuffer.put(verticesLattice);
			latticeBuffer.position(0);
			break;
		case 2:
			for( k=0; k<kmax; k++) {
			latticeLines[k][0]=new Line3D(
					-0.1f*w/2,0.002f*h*y,-0.1f*h/2,
					-0.1f*w/2,0.002f*h*y,0.1f*h/2
					);
			latticeLines[k][1]=new Line3D(
					-0.1f*w/2,0.002f*h*y,-0.1f*h/2,
					0.1f*w/2,0.002f*h*y,-0.1f*h/2
					);
			latticeLines[k][2]=new Line3D(
					0.1f*w/2,0.002f*h*y,-0.1f*h/2,
					0.1f*w/2,0.002f*h*y,0.1f*h/2
					);
			latticeLines[k][3]=new Line3D(
					-0.1f*w/2,0.002f*h*y,0.1f*h/2,
	    			   0.1f*w/2,0.002f*h*y,0.1f*h/2
	    			   );
	    		y += latticeHeight;
			}  
			break;
		}
	}
	
	
	protected int w,h;
	protected int mabiki=4;
	public int getMabiki() {
		return mabiki;
	}

	public void setMabiki(int mabiki) {
		this.mabiki = mabiki;
	}


	protected FloatBuffer latticeBuffer;
	protected FloatBuffer mColorBuffer;
	protected boolean movingMode=false;


	public PotentialGraph(int W,int H,float V[][],int mb) {
		super(1f,1f,1f,1f); // MovingObject3Dのコンストラクタは色を指定するが、
		// PotentialGraphaは別に色を計算するのでこれは実は意味のない指定。
		mabiki=mb;
		w=W/mabiki; h=H/mabiki;
		pts=new Vec3[w][h];
		cls=new Vec3[w][h];
		baseMode=2;

		int i,j;
		
		for(i=0; i<w ; i++) {
			for(j=0; j<h ;j++) {
				pts[i][j]=new Vec3(0.1f*(i-w/2),0.002f*h*V[mabiki*i][mabiki*j],0.1f*(j-h/2));
				cls[i][j]=setColor(V[mabiki*i][mabiki*j]);
			}
		}
		vertices=new float[12*(w-1)*(h-1)];
		verticesCol=new float[16*(w-1)*(h-1)];
		for(i=0; i<w-1 ; i++) {
			for(j=0; j<h-1 ;j++) {
				vertices[12*(i*(h-1)+j)]=pts[i][j].X();
				vertices[12*(i*(h-1)+j)+1]=pts[i][j].Y();
				vertices[12*(i*(h-1)+j)+2]=pts[i][j].Z();
				vertices[12*(i*(h-1)+j)+3]=pts[i+1][j].X();
				vertices[12*(i*(h-1)+j)+4]=pts[i+1][j].Y();
				vertices[12*(i*(h-1)+j)+5]=pts[i+1][j].Z();
				vertices[12*(i*(h-1)+j)+6]=pts[i][j+1].X();
				vertices[12*(i*(h-1)+j)+7]=pts[i][j+1].Y();
				vertices[12*(i*(h-1)+j)+8]=pts[i][j+1].Z();
				vertices[12*(i*(h-1)+j)+9]=pts[i+1][j+1].X();
				vertices[12*(i*(h-1)+j)+10]=pts[i+1][j+1].Y();
				vertices[12*(i*(h-1)+j)+11]=pts[i+1][j+1].Z();
				
				verticesCol[16*(i*(h-1)+j)]=cls[i][j].X();
				verticesCol[16*(i*(h-1)+j)+1]=cls[i][j].Y();
				verticesCol[16*(i*(h-1)+j)+2]=cls[i][j].Z();
				verticesCol[16*(i*(h-1)+j)+3]=1f;
				verticesCol[16*(i*(h-1)+j)+4]=cls[i+1][j].X();
				verticesCol[16*(i*(h-1)+j)+5]=cls[i+1][j].Y();
				verticesCol[16*(i*(h-1)+j)+6]=cls[i+1][j].Z();
				verticesCol[16*(i*(h-1)+j)+7]=1f;
				verticesCol[16*(i*(h-1)+j)+8]=cls[i][j+1].X();
				verticesCol[16*(i*(h-1)+j)+9]=cls[i][j+1].Y();
				verticesCol[16*(i*(h-1)+j)+10]=cls[i][j+1].Z();
				verticesCol[16*(i*(h-1)+j)+11]=1f;
				verticesCol[16*(i*(h-1)+j)+12]=cls[i+1][j+1].X();
				verticesCol[16*(i*(h-1)+j)+13]=cls[i+1][j+1].Y();
				verticesCol[16*(i*(h-1)+j)+14]=cls[i+1][j+1].Z();
				verticesCol[16*(i*(h-1)+j)+15]=1f;
			}
		}
		
	     

	
		
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		mVertexBuffer = vbb.asFloatBuffer();
		mVertexBuffer.put(vertices);
		mVertexBuffer.position(0);            
		ByteBuffer vbb2= ByteBuffer.allocateDirect(verticesCol.length * 4);
		vbb2.order(ByteOrder.nativeOrder());
		mColorBuffer = vbb2.asFloatBuffer();
        mColorBuffer.put(verticesCol);
        mColorBuffer.position(0);
 
        setLattice(latticeMin, latticeMax, latticeHeight);
	}
	@Override
	public void drawHontai(GL10 gl){    	
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
		// gl.glCullFace(GL10.GL_FRONT_AND_BACK);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);
		int i;
		for(i=0; i<(w-1)*(h-1) ; i++ ) {
			int ii=i/(h-1);
			int jj=i-ii*(h-1);
			Vec3 n=Vec3.NormalizedNormal(pts[ii][jj],pts[ii][jj+1],pts[ii+1][jj]);
			gl.glNormal3f(n.X(), n.Y(),n.Z());
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, i*4, 4);
		}
		if(baseMode == 1) {
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, latticeBuffer);
		}
		int k;
		int kmax=(int)((latticeMax-latticeMin)/latticeHeight);
		gl.glBlendFunc(GL10.GL_ALPHA_BITS, GL10.GL_ONE_MINUS_SRC_ALPHA);

		for(k=0; k<kmax; k++) {
			for(i=0; i<4 ;i++) {
				switch( baseMode ) {
				case 1:
					gl.glNormal3f(0f, 1f, 0f);
					if(movingMode ) {
						gl.glColor4f(1f,0f,0f,0.05f); 
					} else {
						gl.glColor4f(1f,1f,1f,0.05f);
					}
					gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,k*4,4);
					break;
				case 2:
					if( movingMode ) {
						latticeLines[k][i].setColor(1f, 0f, 0f, 1f);
					} else {
						latticeLines[k][i].setColor(1f, 1f, 1f, 1f);
					}
					latticeLines[k][i].draw(gl);
					break;
				}
			}
		}
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}

	protected Vec3 setColor(float v) {
		Vec3 c;
		float vv =v*colorScale;
		vv= vv-7f*FloatMath.floor(vv/7f);

		// これで、vvは0から7までになった。

		if( vv<1 ) {
			// 0〜1 白から黄色へ
			c=new Vec3(1, 1, 1-vv);
		} else if( vv<2) {
			// 1〜2 黄から緑へ
			c=new Vec3(2-vv, 1, 0);
		} else if( vv<3) {
			// 2〜3 緑から水色へ
			c=new Vec3(0, 1, vv-2);
		} else if( vv<4) {
			// 3〜4 水色から青へ
			c=new Vec3(0,4-vv,1);
		} else if( vv<5) {
			// 4〜5 青から紫へ
			c=new Vec3(vv-4,0,1);
		} else  if( vv<6){
			// 5〜6 紫から赤へ
			c=new Vec3(1,0,6-vv);
		} else {
			// 6〜7 赤から白へ
			c=new Vec3(1,vv-6,vv-6);
		}
		return c;
	}
	
	// 以下は使わないのでつけない。
	// 後でMovingObject3Dをもう少し階層化しよう。
	@Override
	public void copyFromOrg() {
		// TODO Auto-generated method stub
		
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
	public void setMovingMode(boolean mode) {
		movingMode=mode;		
	}
}