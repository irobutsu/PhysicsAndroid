package jp.ac.u_ryukyu.phys.elemagwave;

import android.content.Context;
import jp.ac.u_ryukyu.phys.lib.Vec3;
import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.DraggableSurfaceView;

public class ElemagwaveSurfaceView extends DraggableSurfaceView {

	public ElemagwaveSurfaceView(Context context) {
		super(context);
		setTranslationDefault(new Vec3(0f,0f,-12f));
	}

	@Override
	protected DraggableRenderer newRenderer() {
		return new ElemagwaveRenderer();
	}
	public void setT(float t) {
		((ElemagwaveRenderer) renderer).setT(t);
	}
	public void setDelM(int i) {
		((ElemagwaveRenderer) renderer).setDelM(i);
	}

	public void setrotEFlg(boolean checked) {
		((ElemagwaveRenderer)renderer).setrotEFlg(checked);
	}	
	public void setrotBFlg(boolean checked) {
		((ElemagwaveRenderer)renderer).setrotBFlg(checked);
	}

	public void timerStart() {
		((ElemagwaveRenderer)renderer).timerStart();
	}

	public void timerStop() {
		((ElemagwaveRenderer)renderer).timerStop();
	}	
}
