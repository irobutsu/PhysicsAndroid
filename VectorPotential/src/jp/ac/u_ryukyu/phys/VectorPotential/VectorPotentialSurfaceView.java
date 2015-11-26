package jp.ac.u_ryukyu.phys.VectorPotential;

import android.content.Context;
import jp.ac.u_ryukyu.phys.lib.Vec3;
import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.DraggableSurfaceView;

public class VectorPotentialSurfaceView extends DraggableSurfaceView {

	public VectorPotentialSurfaceView(Context context) {
		super(context);
		setTranslationDefault(new Vec3(0f,0f,-12f));
	}

	@Override
	protected DraggableRenderer newRenderer() {
		return new VectorPotentialRenderer();
	}
	public void setT(float t) {
		((VectorPotentialRenderer) renderer).setT(t);
	}	
//	float t;
//	float stepT=0.5f;
//	int delay=500;
//	protected boolean isAttached;
//	protected boolean nowGo;
//	
//	public void stop() { nowGo=false; }
//	public void start() { nowGo=true; handler_start();}
//	protected void handler_start() {
//		Handler handler = new Handler() {
//			@Override
//			public void handleMessage(Message msg) {
//				if (isAttached) {
//					if( nowGo ) {
//						t += stepT;
//						setT(t);
//						invalidate();
//						sendEmptyMessageDelayed(0, delay);
//					}
//				}
//			}
//		};
//		isAttached = true;
//		handler.sendEmptyMessageDelayed(0, delay);
//	}
//	@Override
//	protected void onAttachedToWindow() {
//		start();
//		super.onAttachedToWindow();
//	}
//	@Override
//	protected void onDetachedFromWindow() {
//		isAttached = false;
//		super.onDetachedFromWindow();
//	}

	public void setrotAxFlg(boolean isChecked) {
		((VectorPotentialRenderer)renderer).setrotAxFlg(isChecked);
	}
	public void setrotAyFlg(boolean isChecked) {
		((VectorPotentialRenderer)renderer).setrotAyFlg(isChecked);
	}
	public void setrotAzFlg(boolean isChecked) {
		((VectorPotentialRenderer)renderer).setrotAzFlg(isChecked);
	}
	public void setBFlg(boolean isChecked) {
		((VectorPotentialRenderer)renderer).setBFlg(isChecked);
	}
	public void setAFlg(boolean isChecked) {
		((VectorPotentialRenderer)renderer).setAFlg(isChecked);
	}

	public void setMode(int i) {
		((VectorPotentialRenderer)renderer).setMode(i);
	}
}
