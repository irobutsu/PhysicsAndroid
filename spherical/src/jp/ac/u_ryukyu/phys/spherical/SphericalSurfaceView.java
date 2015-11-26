package jp.ac.u_ryukyu.phys.spherical;

import android.content.Context;
import jp.ac.u_ryukyu.phys.lib.Vec3;
import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.DraggableSurfaceView;

public class SphericalSurfaceView extends DraggableSurfaceView {

	public SphericalSurfaceView(Context context) {
		super(context);
		setTranslationDefault(new Vec3(0f,0f,-12f));
		
	}

	@Override
	protected DraggableRenderer newRenderer() {
		return new SphericalRenderer();
	}
	public void setT(float t) {
		((SphericalRenderer) renderer).setT(t);
	}
	public int vrstateChange() {
		return ((SphericalRenderer) renderer).vrstateChange();
	}
	public int vthetastateChange() {
		return ((SphericalRenderer) renderer).vthetastateChange();
	}
	public int vphistateChange() {
		return ((SphericalRenderer) renderer).vphistateChange();
	}
	public void setReso(int t,int p,int r) {
		((SphericalRenderer) renderer).setReso(t,p,r);
	}
}
