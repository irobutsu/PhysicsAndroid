package jp.ac.u_ryukyu.phys.currents;

import android.content.Context;
import jp.ac.u_ryukyu.phys.lib.Vec3;
import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.DraggableSurfaceView;
import jp.ac.u_ryukyu.phys.tool3D.PotentialGraph;
import jp.ac.u_ryukyu.phys.tool3D.Matrix3x3;

public class Charges3DView extends DraggableSurfaceView {

	public Charges3DView(Context context) {
		super(context);
		setTranslationDefault(new Vec3(-2f,0,-13f));
		renderer.setRotationDefault(new Matrix3x3(1,0,0,0,0,-1,0,1,0));
	}
	public void drawV(float[][] v, int W, int H) {
		((PotentialRenderer) renderer).setPotential(v,W,H);
		((PotentialRenderer) renderer).go();
	}
	public void erase() {
		((PotentialRenderer) renderer).stop();		
	}
	@Override
	protected DraggableRenderer newRenderer() {
		return new PotentialRenderer();
	}
	public void setMode(int i) {
		((PotentialRenderer)renderer).setMode(i);
	}	
}
