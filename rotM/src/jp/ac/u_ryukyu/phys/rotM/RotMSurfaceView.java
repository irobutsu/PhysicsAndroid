package jp.ac.u_ryukyu.phys.rotM;

import android.content.Context;
import jp.ac.u_ryukyu.phys.lib.Vec3;
import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.DraggableSurfaceView;

public class RotMSurfaceView extends DraggableSurfaceView {

	public RotMSurfaceView(Context context) {
		super(context);
		setTranslationDefault(new Vec3(0f,0f,-12f));
	}

	@Override
	protected DraggableRenderer newRenderer() {
		return new RotMRenderer();
	}
	public void setT(float t) {
		((RotMRenderer) renderer).setT(t);
	}
	public void setDelM(int i) {
		((RotMRenderer) renderer).setDelM(i);
	}	
}
