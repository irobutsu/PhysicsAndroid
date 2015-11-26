package jp.ac.u_ryukyu.phys.rutherford;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import jp.ac.u_ryukyu.phys.lib.MovingObject;
import jp.ac.u_ryukyu.phys.lib.Vec2;

public class AlphaLauncher extends MovingObject {
	protected int color;
	protected float size;
	public AlphaLauncher(int cc, float rr, Vec2 xx, Vec2 vxx) {
		super(xx, vxx);
		color=cc;
		size=rr;
	}

	@Override
	public boolean releaseDragg() {
		// TODO Auto-generated method stub
		v=Vec2.zero;
		return super.releaseDragg();
	}

	@Override
	public void writeP(Canvas c) {
		write_sub(c,Px(),Py());
	}
	public void write_sub(Canvas c,float x1,float y1) {
		Paint p=new Paint();
		p.setColor(color);
		p.setStyle(Paint.Style.FILL);
		Path pt=new Path();
		pt.moveTo(x1-3*size, y1-size);
		pt.lineTo(x1-3*size, y1-0.5f*size);
		pt.lineTo(x1+0.5f*size, y1-0.5f*size);
		pt.lineTo(x1+0.5f*size, y1+0.5f*size);
		pt.lineTo(x1-3*size, y1+0.5f*size);
		pt.lineTo(x1-3*size, y1+size);
		pt.lineTo(x1+size, y1+size);
		pt.lineTo(x1+size, y1-size);
		pt.close();
		c.drawPath(pt, p);
		p.setColor(Color.argb(50, 255, 255, 255));
		c.drawRect(x1-3*size, y1-0.5f*size, x1+0.5f*size, y1+0.5f*size, p);
	}

	@Override
	public void write(Canvas c) {
		write_sub(c,this.X(),this.Y());		
	}

	@Override
	public boolean isCatched(Vec2 xxx) {
		return !(xxx.X() > X() + size || xxx.X() < X() - 3 * size
				|| xxx.Y() > Y() + size || xxx.Y() < Y() - size);
	}

	@Override
	public Vec2 PositionInRect(float X1, float Y1, float X2, float Y2, Vec2 mp) {
		Vec2 x1;
		x1 = new Vec2(mp);
		if( x1.X() < X1+3*size ) {
			x1.setX(X1+3*size);
		}
		if( x1.X() > X2-size ) {
			x1.setX(X2-size);
		}
		if( x1.Y() < Y1+size  ) {
			x1.setY(Y1+size);
		}
		if( x1.Y() > Y2-size ) {
			x1.setY(Y2-size);
		}
		return x1;
	}

}
