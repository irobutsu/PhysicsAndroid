package jp.ac.phys.u_ryukyu.charges3D;


import javax.microedition.khronos.opengles.GL10;

import jp.ac.u_ryukyu.phys.lib.Vec2;
import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.PotentialGraph;

public class PotentialRenderer extends DraggableRenderer {
	Vec2 rotate=Vec2.zero;
	PotentialGraph potential;
	
	@Override
	public void drawContent(GL10 gl) {
		if(!stopFlg) {
			if( potential !=null) {
				potential.setMovingMode(movingMode);
				potential.draw(gl);
			}
		}
	}

	private boolean stopFlg;
	public void setMabiki(int i) {
		potential.setMabiki(i);
	}
	
	

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
