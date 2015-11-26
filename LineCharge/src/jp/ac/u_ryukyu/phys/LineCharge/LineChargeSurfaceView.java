package jp.ac.u_ryukyu.phys.LineCharge;


import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.DraggableSurfaceView;
import android.content.Context;

public class LineChargeSurfaceView extends DraggableSurfaceView {
	private float z;
	private float x;
	public LineChargeSurfaceView(Context context) {
		super(context);
	
	}
	@Override
	protected DraggableRenderer newRenderer() {
		return new LineChargeRenderer();
	}
	public void setZ(float f) {
		z=f;
		((LineChargeRenderer) renderer).setZ(z);
	}
	public void setT(int t) {
		((LineChargeRenderer) renderer).setT(t);
	}
	public void setN(int n) {
		((LineChargeRenderer) renderer).setN(n);
	}
	public void setR(float n) {
		((LineChargeRenderer) renderer).setR(n);
	}

	public void setX(float f) {
		// TODO Auto-generated method stub
		x=f;
		((LineChargeRenderer) renderer).setX(x);
	}
	
	public void setCircleMode(boolean t) {
		((LineChargeRenderer) renderer).setCircleMode(t);
	}

	public void setZFlg(boolean isChecked) {
		// TODO Auto-generated method stub
		((LineChargeRenderer) renderer).setZFlg(isChecked);
	}
	public void setYFlg(boolean isChecked) {
		// TODO Auto-generated method stub
		((LineChargeRenderer) renderer).setYFlg(isChecked);
	}
	public void setXFlg(boolean isChecked) {
		// TODO Auto-generated method stub
		((LineChargeRenderer) renderer).setXFlg(isChecked);
	}
	
	float Bx,By,Bz;
	public void setTotalB(float bx, float by, float bz) {
		// TODO Auto-generated method stub
		Bx=bx;
		By=by;
		Bz=bz;
		((LineChargeRenderer) renderer).setTotalB(bx,by,bz);
	}

	public void setAFlg(boolean isChecked) {		
		((LineChargeRenderer) renderer).setAFlg(isChecked);
	}
}
