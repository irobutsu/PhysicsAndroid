package jp.ac.u_ryukyu.phys.funccomposite;

import jp.ac.u_ryukyu.phys.lib.Vec3;
import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.DraggableSurfaceView;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceView;

class FuncComposite3DView extends DraggableSurfaceView {
	Vec3 veca,vecb,vecc;
	
	public FuncComposite3DView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void setT(float tt) {
		((FuncCompositeRenderer)renderer).setT(tt);
	}
	@Override
	protected DraggableRenderer newRenderer() {
		return new FuncCompositeRenderer();
	}
	
	public void setfnoF(int funcFx,float aa) {
		((FuncCompositeRenderer)renderer).setfnox(funcFx,aa);
	}
	public void setfnoG(int funcGy,float bb) {
		((FuncCompositeRenderer)renderer).setfnoy(funcGy,bb);
	}
	float t;
	float stepT=0.005f;
	int delay=50;
	protected boolean isAttached;
	protected boolean nowGo;
	
	
	public void stop() { nowGo=false; }
	public void start() { nowGo=true; handler_start();}
	protected void handler_start() {
		Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (isAttached) {
					if( nowGo ) {
						t += stepT;
						invalidate();
						setT(t);
						invalidate();
						sendEmptyMessageDelayed(0, delay);
					}
				}
			}
		};
		isAttached = true;
		handler.sendEmptyMessageDelayed(0, delay);
	}
	@Override
	protected void onAttachedToWindow() {
		start();
		super.onAttachedToWindow();
	}
	@Override
	protected void onDetachedFromWindow() {
		isAttached = false;
		super.onDetachedFromWindow();
	}

}
