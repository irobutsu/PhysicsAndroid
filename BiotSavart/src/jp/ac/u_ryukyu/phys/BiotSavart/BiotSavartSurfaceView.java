package jp.ac.u_ryukyu.phys.BiotSavart;

import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.DraggableSurfaceView;
import android.annotation.SuppressLint;
import android.content.Context;

@SuppressLint("Override")
public class BiotSavartSurfaceView extends DraggableSurfaceView {
	private float z;
	private float x;
	public BiotSavartSurfaceView(Context context) {
		super(context);
	
	}
	@Override
	protected DraggableRenderer newRenderer() {
		return new BiotSavartRenderer();
	}
	public void setZ(float f) {
		z=f;
		((BiotSavartRenderer) renderer).setZ(z);
	}
	@SuppressLint("Override")
	public void setT(float t) {
		((BiotSavartRenderer) renderer).setT(t);
	}

	public void setX(float f) {
		// TODO Auto-generated method stub
		x=f;
		((BiotSavartRenderer) renderer).setX(x);
	}

	public void setZFlg(boolean isChecked) {
		// TODO Auto-generated method stub
		((BiotSavartRenderer) renderer).setZFlg(isChecked);
	}
	public void setYFlg(boolean isChecked) {
		// TODO Auto-generated method stub
		((BiotSavartRenderer) renderer).setYFlg(isChecked);
	}
	public void setXFlg(boolean isChecked) {
		// TODO Auto-generated method stub
		((BiotSavartRenderer) renderer).setXFlg(isChecked);
	}
	
	float Bx,By,Bz;
	public void setTotalB(float bx, float by, float bz) {
		// TODO Auto-generated method stub
		Bx=bx;
		By=by;
		Bz=bz;
		((BiotSavartRenderer) renderer).setTotalB(bx,by,bz);
	}

	public void setAFlg(boolean isChecked) {		
		((BiotSavartRenderer) renderer).setAFlg(isChecked);
	}
}
