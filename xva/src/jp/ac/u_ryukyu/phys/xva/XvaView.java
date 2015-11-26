package jp.ac.u_ryukyu.phys.xva;




import jp.ac.u_ryukyu.phys.lib.MovingObject;
import jp.ac.u_ryukyu.phys.lib.PhystemViewWithNearView1Body;
import jp.ac.u_ryukyu.phys.lib.Vec2;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;


public class XvaView extends PhystemViewWithNearView1Body {
	private float Ax,Ay,A;
	private boolean uFlg=false;
	private float mr=30f;


	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		if( mr > 0.1f*h ) {
			mr=0.1f*h;
			if( m != null ) {
				((CircleObjectKE)m).setR(mr);
			}
		}
	}

	public void setG(float g) {
		A=g;
	}
	public float getAx() {return Ax;}
	public float getAy() {return Ay;}
	public void setA(float x,float y) {
		Ax=-10f*x; Ay=10f*y;
		// このセットされた加速度を使うかどうかは後で決まる。
	}
	
	public XvaView(Context context){
		super(context);
		A=10f;
		wallColor=Color.rgb(200, 200, 0);
		m=new CircleObjectKE(Color.rgb(30,255,80), mr,new Vec2(120f,80f),new Vec2(50f, -40f));
		m.setDraggManager();
		addObj(m);
	}
	

    public void setUFlg(boolean a) { uFlg=a;}

    public void setE(float e) {
    	((CircleObjectKE)m).setE(e);
    }

	@Override
	protected void calcNextEach(MovingObject k) {
		// 物体は一個なので、実はk=mに決まっている。
		((CircleObjectKE)k).calcNext(stepT,wallMg*wx,wallMg*hy,(1f-wallMg)*wx,(1f-wallMg)*hy);
    	k.setAFromF(); // 働いている力を足して加速度を出す。
    	// ↑をもう一回呼んでいるのは、calcNextの中で「跳ね返り」が起こると、
    	// 力が増えているから。
	}

	@Override
	protected void setSituation() {
		Vec2 nowG;
   	 	if( uFlg ) {
   	 		nowG=new Vec2(Ax,Ay); // 加速度センサーによる重力を働かせることを再確認。
   	 	} else {
   	 		nowG=new Vec2(0f,A*10f);
   	 	}
   	 	m.setF(Color.MAGENTA,m.PPos(),nowG);
	}
	boolean yetNotCheck=true;
	public void checkDone() {
		yetNotCheck=false;
	}
	@Override
	public void onDraw(Canvas cc) {
		if( yetNotCheck ) {
			showDownWord(cc);
		} else {
			super.onDraw(cc);
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
}
