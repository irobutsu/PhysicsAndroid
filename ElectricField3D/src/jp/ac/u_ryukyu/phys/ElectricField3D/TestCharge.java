package jp.ac.u_ryukyu.phys.ElectricField3D;

import jp.ac.u_ryukyu.phys.lib.Vec2;
import android.graphics.Color;

public class TestCharge extends Charge {
	public TestCharge(Vec2 p,int h) {
		super(1,h,Color.rgb(100, 100, 100),p);
		setDraggManager();
	}
}
