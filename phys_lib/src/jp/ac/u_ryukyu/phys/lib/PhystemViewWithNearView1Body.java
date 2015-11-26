package jp.ac.u_ryukyu.phys.lib;

import android.content.Context;
import android.graphics.Canvas;
import jp.ac.u_ryukyu.phys.lib.PhystemViewWithNearView;
import jp.ac.u_ryukyu.phys.lib.Vec2;

public abstract class PhystemViewWithNearView1Body extends PhystemViewWithNearView {
	protected MovingObject m;
	
	public PhystemViewWithNearView1Body(Context context) {
		super(context);
	}

	@Override
	protected Vec2 getCenterofNearView() {
		return m.PPos();
	}

	@Override
	protected float getCenterXofNearView() {
		return m.PPos().X();
	}

	@Override
	protected void writeNearViewContent(Canvas canvas, Vec2 vv) {
		Vec2 savePP=m.PPos();
		m.setPPos(vv);
		m.writeP(canvas);
		if( vFlg ) { m.writePV(canvas); }
		if( aFlg ) { m.writeA2(canvas); }
		if( fFlg ) { m.writeF(canvas); }
		m.setPPos(savePP);
	}

}