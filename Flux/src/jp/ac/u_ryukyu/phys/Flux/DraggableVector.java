package jp.ac.u_ryukyu.phys.Flux;

import android.graphics.Canvas;
import jp.ac.u_ryukyu.phys.lib.MovingObject;
import jp.ac.u_ryukyu.phys.lib.Vec2;
import jp.ac.u_ryukyu.phys.lib.Vec3;

public class DraggableVector extends MovingObject {
	static Vec2 origin_xy; // xy平面で原点にしている点
	//static Vec2 origin_yz; // yz平面で原点にしている点
	Vec3 vec;
	int cl; // 色
	int whichCatch=0;
	
	public DraggableVector(float x,float y,float z,int col) {
		super(Vec2.zero,Vec2.zero);
		cl=col;
		vec=new Vec3(x,y,z);
	}
	
	static void setOrigin_xy(Vec2 p) {origin_xy=p;}
	//static void setOrigin_yz(Vec2 p) {origin_yz=p;}

	
	public void write_sub(float x1,float y1,Vec2 origin, Canvas c) {
		Vec2 p=new Vec2(x1,y1);
		drawYajirusi(c, cl,origin,p, 5f);
	}

	@Override
	public void writeP(Canvas c) {
		write_sub(vec.Y(),vec.X(),origin_xy,c);
	//	write_sub(vec.Y(),vec.Z(),origin_yz,c);
	}

	@Override
	public void write(Canvas c) {
		write_sub(vec.Y(),vec.X(),origin_xy,c);
	//	write_sub(vec.Y(),vec.Z(),origin_yz,c);
	}

	@Override
	public boolean isCatched(Vec2 xxx) {
		float dx=xxx.X()-vec.Y()+origin_xy.X();
		float dy=xxx.Y()-vec.X()+origin_xy.Y();
		
		if( dx*dx+dy*dy < 1000f ) {
			return true;
		}
	//	dx=xxx.X()-vec.Y()+origin_yz.X();
	//	dy=xxx.Y()-vec.Z()+origin_yz.Y();
	//	if( dx*dx+dy*dy < 1000f ) {
	//		return true;
	//	}		
		return false;
	}

	public void setMove(Vec2 p) {
		if( whichCatch==1)  {
			vec.setY(p.X()-origin_xy.X());
			vec.setX(p.Y()-origin_xy.Y());
	//	} else if( whichCatch==2) {
	//		vec.setY(p.X()-origin_yz.X());
	//		vec.setZ(p.Y()-origin_yz.Y());
		}
	}
	public int Catch(Vec2 xxx) {
		float dx=xxx.X()-vec.Y()-origin_xy.X();
		float dy=xxx.Y()-vec.X()-origin_xy.Y();
		
		if( dx*dx+dy*dy < 1000f ) {
			whichCatch=1;
			return 1; // xy面の方が押された。
		}
//		dx=xxx.X()-vec.Y()-origin_yz.X();
//		dy=xxx.Y()-vec.Z()-origin_yz.Y();
//		if( dx*dx+dy*dy < 1000f ) {
//			whichCatch=2;
//			return 2; // yz面の方が押された。
//		}		
		return 0; // 押されてない。
	}

	@Override
	public Vec2 PositionInRect(float X1, float Y1, float X2, float Y2, Vec2 mp) {
		return mp;
	}

}
