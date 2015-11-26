package jp.ac.u_ryukyu.phys.SurfaceCharge;


import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.DraggableSurfaceView;
import android.content.Context;

public class SurfaceChargeSurfaceView extends DraggableSurfaceView {
	private float z;
	private float x;
	public SurfaceChargeSurfaceView(Context context) {
		super(context);
	
	}
	@Override
	protected DraggableRenderer newRenderer() {
		return new SurfaceChargeRenderer();
	}
	public void setZ(float f) {
		z=f;
		((SurfaceChargeRenderer) renderer).setZ(z);
	}
	public void setT(int t) {
		((SurfaceChargeRenderer) renderer).setT(t);
	}
	public void setN(int n) {
		((SurfaceChargeRenderer) renderer).setN(n);
	}
	public void setR(float n) {
		((SurfaceChargeRenderer) renderer).setR(n);
	}

	public void setX(float f) {
		// TODO Auto-generated method stub
		x=f;
		((SurfaceChargeRenderer) renderer).setX(x);
	}

	public void setZFlg(boolean isChecked) {
		// TODO Auto-generated method stub
		((SurfaceChargeRenderer) renderer).setZFlg(isChecked);
	}
	public void setYFlg(boolean isChecked) {
		// TODO Auto-generated method stub
		((SurfaceChargeRenderer) renderer).setYFlg(isChecked);
	}
	public void setXFlg(boolean isChecked) {
		// TODO Auto-generated method stub
		((SurfaceChargeRenderer) renderer).setXFlg(isChecked);
	}
	
	float Bx,By,Bz;
	public void setTotalB(float bx, float by, float bz) {
		// TODO Auto-generated method stub
		Bx=bx;
		By=by;
		Bz=bz;
		((SurfaceChargeRenderer) renderer).setTotalB(bx,by,bz);
	}

	public void setAFlg(boolean isChecked) {		
		((SurfaceChargeRenderer) renderer).setAFlg(isChecked);
	}
}
