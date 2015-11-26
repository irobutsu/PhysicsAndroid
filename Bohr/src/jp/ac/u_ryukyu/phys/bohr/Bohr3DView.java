package jp.ac.u_ryukyu.phys.bohr;

import jp.ac.u_ryukyu.phys.lib.Vec3;
import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.DraggableSurfaceView;
import jp.ac.u_ryukyu.phys.tool3D.Matrix3x3;
import android.content.Context;


public class Bohr3DView extends DraggableSurfaceView {

	private boolean Show3DFlg;
	private boolean Show3PFlg;
	private boolean Show2PFlg;
	private boolean Show2SFlg;
	private boolean ShowWaveFlg;
	private boolean ShowParticleFlg;
	
	public Bohr3DView(Context context) {
		super(context);
		setTranslationDefault(new Vec3(0,0,-10f));
		renderer.setRotationDefault(new Matrix3x3(1f,0f,0f,0f,0.5f,-0.86602540378444f,0f,0.86602540378444f,0.5f));
	}
	
	public void drawV(float[][] v, int W, int H) {
		((BohrRenderer) renderer).setPotential(v,W,H);
		((BohrRenderer) renderer).go();
	}
	public void go() {
		((BohrRenderer) renderer).go();		
	}

	public void erase() {
		((BohrRenderer) renderer).stop();		
	}
	@Override
	protected DraggableRenderer newRenderer() {
		return new BohrRenderer();
	}
	public void setMode(int i) {
		((BohrRenderer)renderer).setMode(i);
	}
	public void setTestChargePos(float x, float y,float z,int W,int H) {
		((BohrRenderer)renderer).setTestChargePos(new Vec3(x,y,z),W,H);
	}
	public void setTestChargeExist(boolean p) {
		((BohrRenderer)renderer).setTestChargeExist(p);
	}
	public void set1SFlg(boolean isChecked) {
		((BohrRenderer)renderer).set1SFlg(isChecked);
	}
	public void set2SFlg(boolean isChecked) {
		((BohrRenderer)renderer).set2SFlg(isChecked);
	}
	public void set3SFlg(boolean isChecked) {
		((BohrRenderer)renderer).set3SFlg(isChecked);
	}
	public void setWFlg(boolean isChecked) {
		ShowWaveFlg=isChecked;
	}
	public void setPFlg(boolean isChecked) {
		ShowParticleFlg=isChecked;
		// TODO Auto-generated method stub
		
	}
	
	
}

