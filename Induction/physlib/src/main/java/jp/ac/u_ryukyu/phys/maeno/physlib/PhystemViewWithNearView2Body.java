package jp.ac.u_ryukyu.phys.maeno.physlib;

import android.content.Context;
import android.graphics.Canvas;

public abstract class PhystemViewWithNearView2Body extends PhystemViewWithNearView {
	protected MovingObject M;
	protected MovingObject m;
	
	public PhystemViewWithNearView2Body(Context context) {
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
		Vec2 savePP2=M.PPos();
		m.setPPos(vv);
		M.setPPos(savePP2.Sum((vv.Diff(savePP))));
		m.writeP(canvas);
		if( vFlg ) { m.writePV(canvas); }
		if( aFlg ) { m.writeA2(canvas); }
		if( fFlg ) { m.writeF(canvas); }
		m.setPPos(savePP);

		canvas.save();
		canvas.clipRect(Nvxs,Nvys,Nvxe,Nvye);
		M.writeP(canvas);
		if( vFlg ) { m.writePV(canvas); M.writePV(canvas); }
		if( aFlg ) { m.writeA2(canvas); M.writeA2(canvas);}
		if( fFlg ) { m.writeF(canvas); M.writeF(canvas);}
		M.setPPos(savePP2);
		canvas.restore();
	}

}