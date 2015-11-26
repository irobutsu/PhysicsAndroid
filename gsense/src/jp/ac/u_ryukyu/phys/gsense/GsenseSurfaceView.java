package jp.ac.u_ryukyu.phys.gsense;

import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.DraggableSurfaceView;
import android.annotation.SuppressLint;
import android.content.Context;


@SuppressLint("Override")
public class GsenseSurfaceView extends DraggableSurfaceView {
	private float z;
	private float x;
	public GsenseSurfaceView(Context context) {
		super(context);
		touchManageFlg=false;
	
	}
	@Override
	protected DraggableRenderer newRenderer() {
		return new GsenseRenderer();
	}
	public void setZ(float f) {
		z=f;
		((GsenseRenderer) renderer).setZ(z);
	}
	@SuppressLint("Override")
	public void setT(float t) {
		((GsenseRenderer) renderer).setT(t);
	}

	public void setX(float f) {
		// TODO Auto-generated method stub
		x=f;
		((GsenseRenderer) renderer).setX(x);
	}

	public void setZFlg(boolean isChecked) {
		// TODO Auto-generated method stub
		((GsenseRenderer) renderer).setZFlg(isChecked);
	}
	public void setYFlg(boolean isChecked) {
		// TODO Auto-generated method stub
		((GsenseRenderer) renderer).setYFlg(isChecked);
	}
	public void setXFlg(boolean isChecked) {
		// TODO Auto-generated method stub
		((GsenseRenderer) renderer).setXFlg(isChecked);
	}
	
	float Bx,By,Bz;
	private float gx;
	private float gy;
	private float gz;
	private float g;
	public void setTotalB(float bx, float by, float bz) {
		// TODO Auto-generated method stub
		Bx=bx;
		By=by;
		Bz=bz;
		((GsenseRenderer) renderer).setTotalB(bx,by,bz);
	}

	public void setAFlg(boolean isChecked) {		
		((GsenseRenderer) renderer).setAFlg(isChecked);
	}
	public void setG(float x, float y, float z, float gg) {
		gx=x;
		gy=y;
		gz=z;
		g=gg;
		((GsenseRenderer) renderer).setG(x,y,z,gg);
	}
	int turn=1;
	public void SetTurn(int t) {
		turn=t;
		((GsenseRenderer) renderer).setTurn(turn);
	}
}
