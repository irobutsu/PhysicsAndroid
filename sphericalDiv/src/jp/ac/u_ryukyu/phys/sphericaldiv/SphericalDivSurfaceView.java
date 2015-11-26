package jp.ac.u_ryukyu.phys.sphericaldiv;

import android.content.Context;
import jp.ac.u_ryukyu.phys.lib.Vec3;
import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.DraggableSurfaceView;

public class SphericalDivSurfaceView extends DraggableSurfaceView {

	public SphericalDivSurfaceView(Context context) {
		super(context);
		setTranslationDefault(new Vec3(0f,0f,-12f));
		
	}

	@Override
	protected DraggableRenderer newRenderer() {
		return new SphericalDivRenderer();
	}
	public void setT(float t) {
		((SphericalDivRenderer) renderer).setT(t);
	}
	public int vrstateChange() {
		return ((SphericalDivRenderer) renderer).vrstateChange();
	}
	public int vthetastateChange() {
		return ((SphericalDivRenderer) renderer).vthetastateChange();
	}
	public int vphistateChange() {
		return ((SphericalDivRenderer) renderer).vphistateChange();
	}
	public void setReso(int t,int p,int r) {
		((SphericalDivRenderer) renderer).setReso(t,p,r);
	}

	
	public void toNorth() {
		((SphericalDivRenderer) renderer).toNorth();
	}
	public void toSouth() {
		((SphericalDivRenderer) renderer).toSouth();
	}
}
