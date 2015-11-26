package jp.ac.u_ryukyu.phys.nihensuu;


import javax.microedition.khronos.opengles.GL10;

import android.util.FloatMath;
import jp.ac.u_ryukyu.phys.lib.Vec2;
import jp.ac.u_ryukyu.phys.lib.Vec3;
import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.PotentialGraph;
import jp.ac.u_ryukyu.phys.tool3D.Yajirushi3D;

public class PotentialRenderer extends DraggableRenderer {
	Vec2 rotate=Vec2.zero;
	PotentialGraph potential;
	Yajirushi3D xjiku;
	Yajirushi3D yjiku;
	Yajirushi3D zjiku;
	
	public PotentialRenderer(){
	
		xjiku=new Yajirushi3D(2f,0.05f,0.3f,10,1f,0f,0f,1f); 
		yjiku=new Yajirushi3D(2f,0.05f,0.3f,10,0f,0f,1f,1f);
		zjiku=new Yajirushi3D(2f,0.05f,0.3f,10,1f,0f,1f,1f);
		xjiku.setThetaPhi((float)(0.5f*Math.PI), 0f);
		zjiku.setThetaPhi((float)(0.5f*Math.PI), (float)(0.5f*Math.PI));
	}
	@Override
	public void drawContent(GL10 gl) {
		if(!stopFlg) {
			if( potential !=null) {
				potential.setMovingMode(movingMode);
				potential.draw(gl);
			}
		}
		xjiku.draw(gl);
		yjiku.draw(gl);
		zjiku.draw(gl);
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
