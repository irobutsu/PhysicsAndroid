package jp.ac.u_ryukyu.phys.floatingball;




import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.FloatMath;
import android.view.View;


public class FloatingBallView extends View {
	private float Ax,Ay,Az;
	private float Vx,Vy,Vz;
	private float x,y,z;
	private int wx,hy;
	private long pTime,time;

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		wx=w;
		hy=h;
		p=new Paint();
	}
	Paint p;
	protected void reset() {
		x=y=z=Vx=Vy=Vz=0;
		pTime=System.currentTimeMillis();
	}

	public float getAx() {return Ax;}
	public float getAy() {return Ay;}
	public float getAz() {return Az;}
	public void setA(float xx,float yy,float zz) {
		time=System.currentTimeMillis();
		float t=(time-pTime)*0.001f;
		Ax=-xx; Ay=yy; Az=zz;	
		x=x+Vx*t+Ax*t*0.5f;
		y=y+Vy*t+Ay*t*0.5f;
		z=z+Vz*t+Az*t*0.5f;
		Vx=Vx+Ax*t;
		Vy=Vy+Ay*t;
		Vz=Vz+Az*t;
		pTime=time;
		invalidate();
	}
	
	public FloatingBallView(Context context){
		super(context);
		reset();
	}
	boolean yetNotCheck=true;
	@Override
	public void onDraw(Canvas cc) {
		if( yetNotCheck ) {
			showDownWord(cc);
		} else {
			p.setStyle(Paint.Style.FILL);
			p.setColor(Color.BLUE);
			cc.drawRect(0, 0,wx,hy,p);
			float r=FloatMath.sqrt(0.0025f);
			
			p.setColor(Color.WHITE);
			cc.drawCircle(wx*0.5f+x, hy*0.5f+y, 100f, p);
		}
	}
	public void showDownWord(Canvas cc) {
		int x;
		int y;
		Paint p=new Paint();
		p.setColor(Color.BLACK);
		cc.drawRect(0, 0, wx, hy, p);
		p.setColor(Color.WHITE);
		p.setStyle(Style.FILL);
		for(x=100; x< wx; x += 200) {
			for( y=100 ; y< hy; y +=200) {
				Path path=new Path();
				path.moveTo(x, y);
				path.lineTo(x+40, y-40);
				path.lineTo(x+20, y-40);
				path.lineTo(x+20, y-100);
				path.lineTo(x-20, y-100);
				path.lineTo(x-20, y-40);
				path.lineTo(x-40, y-40);
				path.close();
				cc.drawPath(path, p);
			}
		}
	}
	public void checkDone() {
		yetNotCheck=false;
	}
}
