package jp.ac.u_ryukyu.phys.bohr;

import jp.ac.u_ryukyu.phys.lib.Vec2;
import android.graphics.Color;

public class TestCharge extends Charge {
	public TestCharge(Vec2 p) {
		super(-1,15,Color.rgb(100, 100, 100),p);
		setDraggManager();
	}
}
