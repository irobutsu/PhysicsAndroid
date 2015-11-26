package jp.ac.u_ryukyu.phys.Flux;

import jp.ac.u_ryukyu.phys.lib.Vec3;
import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.DraggableSurfaceView;
import android.content.Context;
import android.view.SurfaceView;

public class Flux3DView extends DraggableSurfaceView {
	Vec3 veca,vecb,vecc;
	
	public Flux3DView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void setT(float tt) {
		((FluxRenderer)renderer).setT(tt);
	}
	@Override
	protected DraggableRenderer newRenderer() {
		return new FluxRenderer();
	}
	public void setVecE(Vec3 p) {((FluxRenderer)renderer).setVecE(p);}
	public void setVecS(Vec3 p) {((FluxRenderer)renderer).setVecS(p);}

}
