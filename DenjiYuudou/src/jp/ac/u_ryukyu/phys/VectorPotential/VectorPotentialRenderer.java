package jp.ac.u_ryukyu.phys.VectorPotential;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.util.FloatMath;

import jp.ac.u_ryukyu.phys.lib.Vec3;
import jp.ac.u_ryukyu.phys.tool3D.Cone;
import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.Torus;
import jp.ac.u_ryukyu.phys.tool3D.Yajirushi3DScaledR;
import jp.ac.u_ryukyu.phys.tool3D.Yajirushi3DFixedR;

public class VectorPotentialRenderer extends DraggableRenderer {
	private Yajirushi3DScaledR[][][] A;
	private RotYajirushi3D[][][] rotAyz;
	private RotYajirushi3D[][][] rotAzx;
	private RotYajirushi3D[][][] rotAxy;
	private Yajirushi3DScaledR[][][] B;
	private Yajirushi3DFixedR I;
	Yajirushi3DFixedR I2;
	private Torus I3;
	private final int gridSize=6;
	// 
	protected float t;
	private boolean rotAxFlg=false;
	private boolean rotAyFlg=false;
	private boolean rotAzFlg=false;
	private boolean aFlg=false;
	private boolean bFlg=false;
	private int makeMode=0;
	private int writeMode=0;
	private Cone I3Cone;
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig cnf) {
		super.onSurfaceCreated(gl, cnf);

		A=new Yajirushi3DScaledR[gridSize][gridSize][gridSize];
		B=new Yajirushi3DScaledR[gridSize-1][gridSize-1][gridSize-1];
		rotAyz=new RotYajirushi3D[gridSize][gridSize-1][gridSize-1];
		rotAzx=new RotYajirushi3D[gridSize-1][gridSize][gridSize-1];
		rotAxy=new RotYajirushi3D[gridSize-1][gridSize-1][gridSize];
		makeworld();
	}

	protected void makeworld() {
		float midPoint=0.5f*(gridSize-1);
		float gg,bb;
		if( movingMode ) {
			gg=0f;bb=0f;
		} else {
			gg=1f; bb=1f;
		}
		switch( makeMode) {
		case 0:
			I=new Yajirushi3DFixedR(gridSize, 0.1f, 10, 1f,gg,bb,1f,true);
	//		I2=new Yajirushi3DFixedR(0f, 0f, 10, 1f,1f,1f,1f,true);
			break;
		case 1:
			I=new Yajirushi3DFixedR(gridSize, 0.1f, 10, 1f,gg,bb,1f,true);
			I.setPos(new Vec3(1,0,0));
			
			I2=new Yajirushi3DFixedR(gridSize, 0.1f, 10, 1f,gg,bb,1f,true);
			I2.setPos(new Vec3(-1,0,0));
			break;
		case 2:
			I=new Yajirushi3DFixedR(gridSize, 0.1f, 10, 1f,gg,bb,1f,true);
			I.setPos(new Vec3(1,0,0));
			
			I2=new Yajirushi3DFixedR(gridSize, 0.1f, 10, 1f,gg,bb,1f,true);
			I2.setPos(new Vec3(-1,0,0));
			I2.setThetaPhi((float)Math.PI, 0f);
			break;
		case 3:
			I3=new Torus(gridSize*0.3f,0.06f,25,6,1f,gg,bb,1f);
			I3Cone=new Cone(0.12f, 0.37f, 10, 1f,gg,bb,1f);
			I3Cone.setThetaPts((float) (Math.PI/2));
			I3Cone.setPos(new Vec3(0,-gridSize*0.3f,0));
			break;
		}

		int i,j,k;
		for(i=0; i<gridSize; i++) {
			float x=i-midPoint;
			for(j=0; j<gridSize ; j++) {
				float y=j-midPoint;
				float az=0f;
				switch( makeMode ) {
				case 0:
					az=1.3f-0.3f*((float)Math.log(4*(x*x+y*y)));
					break;
				case 1:
					az=1.5f-0.3f*((float)Math.log((x-1)*(x-1)+y*y))
						 -0.3f*((float)Math.log((x+1)*(x+1)+y*y));
					break;
				case 2:
					az=-0.3f*((float)Math.log((x-1)*(x-1)+y*y))
						 +0.3f*((float)Math.log((x+1)*(x+1)+y*y));
					break;
				}

				for(k=0; k<gridSize ; k++ ) {
					float z=k-midPoint;
					switch( makeMode) {
					case 0:
					case 1:
					case 2:
						A[i][j][k]=new Yajirushi3DScaledR(0f,0f,az,4, 0f,1f,0f,0.8f,true);
						break;
					case 3:
						{
							float ax=0f;
							float ay=0f;
							int m;
							for(m=0; m<20 ; m++) {
								float phi=m*(float)(Math.PI/10.0);
								float xx=gridSize*0.3f*FloatMath.cos(phi);
								float yy=gridSize*0.3f*FloatMath.sin(phi);
								float rr=FloatMath.sqrt((x-xx)*(x-xx)+(y-yy)*(y-yy)+z*z);
								
								ax += -0.05f*yy/rr;
								ay += 0.05f*xx/rr;
							}
							A[i][j][k]=new Yajirushi3DScaledR(ax,ay,0f,4, 0f,1f,0f,0.8f,true);
						}
						break;
					}
					A[i][j][k].setPos(new Vec3(x,y,z));
				}
			}
		}

		float rotaxy[][][]=new float[gridSize-1][gridSize-1][gridSize];
		float rotayz[][][]=new float[gridSize][gridSize-1][gridSize-1];
		float rotazx[][][]=new float[gridSize-1][gridSize][gridSize-1];
		for(i=0; i<gridSize ; i++) {
			float x=i-midPoint;
			for(j=0 ; j<gridSize-1; j++) {
				float y=j-midPoint+0.5f;
				for(k=0; k<gridSize-1; k++ ) {
					float z=k-midPoint+0.5f;
					float rota=(
							A[i][j+1][k].vec().Z()-A[i][j][k].vec().Z()
							+A[i][j+1][k+1].vec().Z()-A[i][j][k+1].vec().Z() // この２行は∂_y A_z
							+A[i][j][k].vec().Y()-A[i][j][k+1].vec().Y()
							+A[i][j+1][k].vec().Y()-A[i][j+1][k+1].vec().Y() // この２行は -∂_z A_y 
							);
					if( rota <0 ) {
						rotAyz[i][j][k]=new RotYajirushi3D(-rota,-0.5f*rota,-rota*0.04f,10,4,1f,0f,1f,1f);
						rotAyz[i][j][k].setThetaPhi((float)(Math.PI/2),(float)Math.PI);
					} else {
						rotAyz[i][j][k]=new RotYajirushi3D(rota,0.5f*rota,rota*0.04f,10,4,1f,0f,1f,1f);
						rotAyz[i][j][k].setThetaPhi((float)(Math.PI/2),0f);
					}
					rotayz[i][j][k]=rota;
					rotAyz[i][j][k].setPos(new Vec3(x,y,z));
				}
			}
		}


		for(i=0; i<gridSize-1 ; i++) {
			float x=i-midPoint+0.5f;
			for(j=0 ; j<gridSize; j++) {
				float y=j-midPoint;
				for(k=0; k<gridSize-1; k++ ) {
					float z=k-midPoint+0.5f;
					float rota=(
							A[i][j][k+1].vec().X()-A[i][j][k].vec().X()
							+A[i+1][j][k+1].vec().X()-A[i+1][j][k].vec().X() // この２行は ∂_z A_x
							+A[i][j][k].vec().Z()-A[i+1][j][k].vec().Z()
							+A[i][j][k+1].vec().Z()-A[i+1][j][k+1].vec().Z() // この２行は -∂_x A_z
							);
					if( rota <0 ) {
						rotAzx[i][j][k]=new RotYajirushi3D(-rota,-0.5f*rota,-rota*0.04f,10,4,1f,0f,1f,1f);
						rotAzx[i][j][k].setThetaPhi((float)(Math.PI/2),(float)(-Math.PI/2));
					} else {
						rotAzx[i][j][k]=new RotYajirushi3D(rota,0.5f*rota,rota*0.04f,10,4,1f,0f,1f,1f);
						rotAzx[i][j][k].setThetaPhi((float)(Math.PI/2),(float)(Math.PI/2));
					}
					rotazx[i][j][k]=rota;
					rotAzx[i][j][k].setPos(new Vec3(x,y,z));
				}
			}
		}
		
				
		for(i=0; i<gridSize-1 ; i++) {
			float x=i-midPoint+0.5f;
			for(j=0 ; j<gridSize-1; j++) {
				float y=j-midPoint+0.5f;
				for(k=0; k<gridSize; k++ ) {
					float z=k-midPoint;
					float rota=(
							A[i+1][j][k].vec().Y()-A[i][j][k].vec().Y()
							+A[i+1][j+1][k].vec().Y()-A[i][j+1][k].vec().Y() // この２行は ∂_x A_y
							+A[i][j][k].vec().X()-A[i][j+1][k].vec().X()
							+A[i+1][j][k].vec().X()-A[i+1][j+1][k].vec().X() // この２行は -∂_y A_x
							);
					if( rota <0 ) {
						rotAxy[i][j][k]=new RotYajirushi3D(-rota,-0.5f*rota,-rota*0.04f,10,4,1f,0f,1f,1f);
						rotAxy[i][j][k].setThetaPhi((float)(Math.PI),0);
					} else {
						rotAxy[i][j][k]=new RotYajirushi3D(rota,0.5f*rota,rota*0.04f,10,4,1f,0f,1f,1f);
					}
					rotaxy[i][j][k]=rota;
					rotAxy[i][j][k].setPos(new Vec3(x,y,z));
				}
			}
		}
		float bx=0f;
		float by=0f;
		float bz=0f;
		for(i=0; i<gridSize-1 ; i++) {
			float x=i-midPoint+0.5f;
			for(j=0 ; j<gridSize-1; j++) {
				float y=j-midPoint+0.5f;
				
				for(k=0; k<gridSize-1; k++ ) {
					float z=k-midPoint+0.5f;
					bx=0.5f*(rotayz[i][j][k]+rotayz[i+1][j][k]);
					by=0.5f*(rotazx[i][j][k]+rotazx[i][j+1][k]);
					bz=0.5f*(rotaxy[i][j][k]+rotaxy[i][j][k+1]);
					B[i][j][k]=new Yajirushi3DScaledR(bx,by,bz,4,0f,1f,1f,0.5f,true);
					B[i][j][k].setPos(new Vec3(x,y,z));
				}
			}
		}
	}


	@Override
	protected void drawContent(GL10 gl) {
		// TODO Auto-generated method stub
		int i,j,k;
		
		switch( writeMode) {
		case 1:
		case 2:
			if( movingMode) {
				I2.changeColor(1f,0f,0f,1f);
			} else {
				I2.changeColor(1f,1f,1f,1f);
			}
			I2.draw(gl);
			//$FALL-THROUGH$
		case 0:
			if( movingMode) {
				I.changeColor(1f,0f,0f,1f);
			} else {
				I.changeColor(1f,1f,1f,1f);
			}
			I.draw(gl);
			break;
		case 3:
			if( movingMode) {
				I3.changeColor(1f,0f,0f,1f);
				I3Cone.changeColor(1f,0f,0f,1f);
			} else {
				I3.changeColor(1f,1f,1f,1f);
				I3Cone.changeColor(1f,1f,1f,1f);
			}
			I3.draw(gl);
			I3Cone.draw(gl);
		}
		if( aFlg ) {
		for(i=0; i<gridSize; i++) {
			for(j=0; j<gridSize ; j++) {
				for(k=0; k<gridSize ; k++ ) {
					A[i][j][k].draw(gl);		
				}
			}
		}
		}
		if( bFlg) {
		for(i=0; i<gridSize-1; i++) {
			for(j=0; j<gridSize-1 ; j++) {
				for(k=0; k<gridSize-1 ; k++ ) {
					B[i][j][k].draw(gl);	
				}
			}
		}
		}
		if( rotAxFlg ) {
			for(i=0; i<gridSize; i++) {
				for(j=0; j<gridSize-1 ; j++) {
					for(k=0; k<gridSize-1 ; k++ ) {
						rotAyz[i][j][k].draw(gl);	
					}
				}
			}
		}
		if( rotAyFlg) {
			for(i=0; i<gridSize-1; i++) {
				for(j=0; j<gridSize ; j++) {
					for(k=0; k<gridSize-1 ; k++ ) {
						rotAzx[i][j][k].draw(gl);	
					}
				}
			}
		}
		if( rotAzFlg ) {
			for(i=0; i<gridSize-1; i++) {
				for(j=0; j<gridSize-1 ; j++) {
					for(k=0; k<gridSize ; k++ ) {
						rotAxy[i][j][k].draw(gl);	
					}
				}
			}
		}
	}
	
	public void setMode(int k) {
		makeMode=k;
		makeworld();
		writeMode=k;
	}
	
	public void setT(float t2) {
		t=t2;		
	}


	public void setAFlg(boolean isChecked) {
		aFlg=isChecked;
	}
	public void setBFlg(boolean isChecked) {
		bFlg = isChecked;
	}
	public void setrotAxFlg(boolean isChecked) {
		rotAxFlg=isChecked;
	}
	public void setrotAyFlg(boolean isChecked) {
		rotAyFlg=isChecked;
	}
	public void setrotAzFlg(boolean isChecked) {
		rotAzFlg=isChecked;
	}
}
