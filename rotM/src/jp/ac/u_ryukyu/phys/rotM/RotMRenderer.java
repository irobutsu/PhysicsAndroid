package jp.ac.u_ryukyu.phys.rotM;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


import jp.ac.u_ryukyu.phys.lib.Vec3;
import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;

public class RotMRenderer extends DraggableRenderer {
	RotCurrent3D rotMxy[][];
	final int gridSize=6;
	// 
	protected float t;

	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig cnf) {
		super.onSurfaceCreated(gl, cnf);
		delM=0;
		rotMxy=new RotCurrent3D[gridSize][gridSize];
		makeworld();
	}

	protected void makeworld() {
		float midPoint=0.5f*(gridSize-1);
		int i,j;
		for(i=0; i<gridSize-1 ; i++) {
			float x=i-midPoint+0.5f;
			for(j=0 ; j<gridSize-1; j++) {
				float y=j-midPoint+0.5f;
				
				float z=0f;
				float rota=((float)(gridSize*2+delM*i))/gridSize;
				rotMxy[i][j]=new RotCurrent3D(rota,0.7f,0.04f,20,4,1f,0f,1f,1f);
				rotMxy[i][j].setPos(new Vec3(x,y,z));
			}
		}
	}


	@Override
	protected void drawContent(GL10 gl) {
		// TODO Auto-generated method stub
		int i,j,k;

		for(i=0; i<gridSize-1; i++) {
			for(j=0; j<gridSize-1 ; j++) {
				rotMxy[i][j].draw(gl);	
			}
		}
	}
	
	
	public void setT(float t2) {
		t=t2;		
	}
	int delM;
	public void setDelM(int i) {
		delM=i;
		makeworld();
	}
}
