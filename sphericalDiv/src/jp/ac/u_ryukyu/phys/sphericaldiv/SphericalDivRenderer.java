package jp.ac.u_ryukyu.phys.sphericaldiv;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.Matrix3x3;

public class SphericalDivRenderer extends DraggableRenderer {
	// ThreeVecE rotMxy[][];
	GridSphere sphere;
	// 
	protected float t;
	protected int Tn,Pn,Xn;
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig cnf) {
		Tn=14;
		Pn=16;
		Xn=5;
		super.onSurfaceCreated(gl, cnf);
		gl.glEnable(GL10.GL_LIGHT1);
		setRotationDefault(new Matrix3x3(1f,0f,0f,0f,0.8f,0.6f,0f,-0.6f,0.8f));
		makeworld();
	}
	protected void setReso(int tnum,int pnum,int xnum) {
		Tn=tnum;
		Pn=pnum;
		Xn=xnum;
		makeworld();
	}

	protected void makeworld() {
		sphere = new GridSphere(3, Tn, Pn, Xn,1f, 0f, 1f, 1f);
		//rotMxy=new ThreeVecE[gridSize][gridSize];
	}

	
	int vrState=0;
	int vthetaState=0;
	int vphiState=0;
	@Override
	protected void drawContent(GL10 gl) {
		if( movingMode) {
			sphere.changeColor(1f, 0f, 0f, 0.8f);
		} else {
			sphere.changeColor(1f, 1f, 1f, 0.8f);
		}
		sphere.draw(gl);
		if( vrState==2 ) {
			sphere.drawvecr(gl,t);
		} else if( vrState==1) {
			sphere.drawvecr(gl,-1f);
		}
		if( vthetaState==2 ) {
			sphere.drawvectheta(gl,t);
		} else if( vthetaState==1){
			sphere.drawvectheta(gl,0f);
		}
		if( vphiState==2 ) {
			sphere.drawvecphi(gl,t);
		} else if( vphiState==1) {
			sphere.drawvecphi(gl, 0f);
		}
		
		sphere.drawRoom(gl);
		
		t++;
	}
	
	
	public void setT(float t2) {
		t=t2;		
	}

	public int vrstateChange() {
		vrState++;
		if( vrState > 2) {
			vrState=0;
		}
		return vrState;
	}
	public int vthetastateChange() {
		vthetaState++;
		if( vthetaState > 2) {
			vthetaState=0;
		}
		return vthetaState;		
	}
	public int vphistateChange() {
		vphiState++;
		if( vphiState > 2) {
			vphiState=0;
		}
		return vphiState;		
	}
	public void toNorth() {
		sphere.downTPoint();
	}
	public void toSouth() {
		sphere.upTPoint();
	}
}
