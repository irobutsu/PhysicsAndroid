package jp.ac.u_ryukyu.phys.lib;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import jp.ac.u_ryukyu.phys.lib.MovingObject;
import jp.ac.u_ryukyu.phys.lib.PhystemViewWithNearView;
import jp.ac.u_ryukyu.phys.lib.Vec2;

public abstract class PhystemViewWithNearViewNBody extends PhystemViewWithNearView {
	protected ArrayList<MovingObject> MList;
	MovingObject pointedM;
	public PhystemViewWithNearViewNBody(Context context) {
		super(context);
	}

	@Override
	protected Vec2 getCenterofNearView() {
		return pointedM.PPos();
	}

	@Override
	protected float getCenterXofNearView() {
		return pointedM.PPos().X();
	}

	@Override
	protected void writeNearViewContent(Canvas canvas, Vec2 vv) {
		ArrayList<Vec2> savePPList=new ArrayList<Vec2>();
		
		int i;
		int pt=-1;
		for(i=0; i<MList.size(); i++) {
			if( MList.get(i) == pointedM ) {
				pt=i;
			}
			savePPList.add(MList.get(i).PPos());
		}
		if(pt < 0 )  {
			// pointしたObjectがないのなら、ここでやることはない。
			return;
		} 
		pointedM.setPPos(vv);
		for( i=0 ; i<MList.size(); i++ ) {
			MList.get(i).setPPos(savePPList.get(i).Sum((vv.Diff(savePPList.get(pt)))));
		}
		pointedM.writeP(canvas);
		if( vFlg ) { pointedM.writePV(canvas); }
		if( aFlg ) { pointedM.writeA2(canvas); }
		if( fFlg ) { pointedM.writeF(canvas); }
		pointedM.setPPos(savePPList.get(pt));

		canvas.save();
		canvas.clipRect(Nvxs,Nvys,Nvxe,Nvye);
		pointedM.writeP(canvas);
		if( vFlg ) { 
			for(i=0; i<MList.size(); i++) {
				if( MList.get(i) != pointedM ) {
					MList.get(i).writePV(canvas);
				}
			}
		}
		if( aFlg ) {
			for(i=0; i<MList.size(); i++) {
				if( MList.get(i) != pointedM ) {
					MList.get(i).writeA2(canvas);
				}
			}
		}
		if( fFlg ) { 
			for(i=0; i<MList.size(); i++) {
				if( MList.get(i) != pointedM ) {
					MList.get(i).writeF(canvas);
				}
			}
			pointedM.setPPos(savePPList.get(pt));
			canvas.restore();
		}
	}
}