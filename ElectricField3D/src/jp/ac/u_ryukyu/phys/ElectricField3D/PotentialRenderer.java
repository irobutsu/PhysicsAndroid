package jp.ac.u_ryukyu.phys.ElectricField3D;


import javax.microedition.khronos.opengles.GL10;

import jp.ac.u_ryukyu.phys.lib.Vec2;
import jp.ac.u_ryukyu.phys.lib.Vec3;
import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.PotentialGraph;
import jp.ac.u_ryukyu.phys.tool3D.Sphere;

public class PotentialRenderer extends DraggableRenderer {
	Vec2 rotate=Vec2.zero;
	PotentialGraph potential;
	Sphere testCharge;
	boolean existTest=false;
	
	public PotentialRenderer() {
		testCharge=new Sphere(0.3f, 15, 15, 0.5f,0.5f,0.5f,1f);
	}
	
	public void setTestChargePos(Vec3 p,int w,int h) {
		int mabiki=4;
		// pts[i][j]=new Vec3(0.1f*(i-w/2),
		//                    0.002f*h*V[mabiki*i][mabiki*j],0.1f*(j-h/2));
		testCharge.setPos(new Vec3(
				0.05f*(p.X()-w/2)/mabiki,
				0.001f*h*p.Z()/mabiki,
				0.05f*(p.Y()-h/2)/mabiki
				));
	}
	public void setTestChargeExist(boolean p){
		existTest=p;
	}
	
	@Override
	public void drawContent(GL10 gl) {
		if(!stopFlg) {
			if( potential !=null) {
				potential.setMovingMode(movingMode);
				potential.draw(gl);
			}
		}
		if( existTest ) {
			testCharge.draw(gl);
		}
	}

	private boolean stopFlg;
	
	

	public void stop() {
		stopFlg=true;
	}

	public void setPotential(float[][] v, int w, int h) {
		potential=new PotentialGraph(w,h,v,4);
		potential.setBaseMode(baseMode);
	}

	public void go() {
		stopFlg=false;
	}
	int baseMode;
	public void setMode(int m){
		baseMode=m;
		potential.setBaseMode(m);
	}
}
