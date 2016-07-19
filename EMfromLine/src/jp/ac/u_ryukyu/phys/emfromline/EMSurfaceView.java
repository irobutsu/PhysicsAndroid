package jp.ac.u_ryukyu.phys.emfromline;

import android.content.Context;
import jp.ac.u_ryukyu.phys.lib.Vec3;
import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.DraggableSurfaceView;

public class EMSurfaceView extends DraggableSurfaceView {

	public EMSurfaceView(Context context) {
		super(context);
		setTranslationDefault(new Vec3(0f,3f,-8f));
	}

	@Override
	protected DraggableRenderer newRenderer() {
		return new EMRenderer();
	}
	public void setT(float t) {
		((EMRenderer) renderer).setT(t);
	}
	public void setDelM(int i) {
		((EMRenderer) renderer).setDelM(i);
	}

	public void setrotEFlg(boolean checked) {
		((EMRenderer)renderer).setInftyFlg(checked);
	}

	public void timerStart() {
		((EMRenderer)renderer).timerStart();
	}

	public void timerStop() {
		((EMRenderer)renderer).timerStop();
	}	
}
