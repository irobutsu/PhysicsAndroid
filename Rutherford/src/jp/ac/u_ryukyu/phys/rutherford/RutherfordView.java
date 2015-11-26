package jp.ac.u_ryukyu.phys.rutherford;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.FloatMath;



import jp.ac.u_ryukyu.phys.lib.CircleObject;
import jp.ac.u_ryukyu.phys.lib.MovingObject;
import jp.ac.u_ryukyu.phys.lib.PhystemView;
import jp.ac.u_ryukyu.phys.lib.Vec2;

@SuppressWarnings("PointlessBooleanExpression")
public class RutherfordView extends PhystemView {
	private CircleObject Nuclear;
	private CircleObject m;
	private AlphaLauncher L;
	private boolean existFlg=true;
	

	private float Q; // 正電荷
	protected boolean crashFlg;	

	public RutherfordView(Context context){
		super(context);
		Q=500000f;
		Nuclear=new CircleObject(Color.argb(128,80,80,80), 80f,new Vec2(40f,20f),Vec2.zero);
		m=new CircleObject(Color.rgb(30,255,80), 15f,new Vec2(120f,400f),Vec2.zero);	
		L=new AlphaLauncher(Color.rgb(20, 20, 178),25f,new Vec2(620f,400f),Vec2.zero);
		L.setDraggManager();
		m.setDraggManager();	
		addObj(m);
		addObj(Nuclear);
		addObj(L);
		wallColor=Color.rgb(200, 200, 0);
		stepT=0.05f;
		
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// 画面サイズが決まった後で、原子核をその画面の左の方に置く。
		Nuclear.setPos(new Vec2(0.25f*wx,0.5f*hy));
		Nuclear.setPPos(Nuclear.Pos());
		L.setPos(new Vec2(0.8f*wx,0.5f*hy+1));
		startm();
	}
	public void startm() {
		existFlg=true;
		m.setPos(L.Pos());
		m.setV(new Vec2(-VVV,0f));
		if( pt == null ) {
			pt=new Path();
		}
		pt.moveTo(L.X(), L.Y());
	}

	public void setQ(float q) {
		Q=q;
	}
	public void setR(float r) {
		Nuclear.setR(r);
	}
	float VVV=100f;
	public void setV(float v) {
		VVV=v;
	}
	
	Path pt;
    @Override
    protected void drawBackGround(Canvas canvas) {
    	Paint paint = new Paint();	 
    	//paint.setColor(Color.WHITE);
    	//canvas.drawRect(0f,0f,wx,hy,paint);
   
    	paint.setColor(wallColor);
    	canvas.drawRect(0, 0, wx, hy,paint); // 床。	 
    	canvas.drawBitmap(bitmap, null, new Rect((int)(wallMg*wx),(int)(wallMg*hy),(int)((1f-wallMg)*wx),(int)((1f-wallMg)*hy)), paint);
    	Nuclear.writeP(canvas);
    	if( pt != null ) {
    		paint.setColor(Color.argb(120,255,255,255));
    		paint.setStyle(Paint.Style.STROKE);
    		paint.setStrokeWidth(4f);
    		canvas.drawPath(pt, paint);
    	}
    }


	@Override
	public boolean ClickOthers(Vec2 mp) {
		if(!existFlg) {
			delObj(m);
			m=new CircleObject(Color.rgb(30,255,80), 15f,mp,Vec2.zero);	
			m.setDraggManager();
			addObj(m);
			existFlg=true;
			return true;
		} 
		return false;
		
	}

	protected Vec2 ExternalForcetom(Vec2 mPos) {
		Vec2 mtoM=Nuclear.Pos().Diff(mPos);
		float r2=mtoM.NormSquare();
		float RR=Nuclear.R();
		if( r2 > RR*RR ) { 
			mtoM.mul(-Q/(r2*FloatMath.sqrt(r2)));
		} else {
			mtoM.mul(-Q/(RR*RR*RR));
		}
		return mtoM;
	}
	


    @Override
	protected void setSituation() {
    	m.setF(Color.argb(128, 255, 120, 120),m.Pos(),
    			ExternalForcetom(m.Pos()));
    	m.setAFromF();
    }
    
	@Override
	protected void calcNextEach(MovingObject movingObject) {
		//一体しかないので、引数を無視して全部mで書く。

		if(!existFlg) {
			return; // 電荷がいないのならあっさり帰る。
		}

		Vec2 externaltom;
		//=ExternalForcetom(m.Pos());

		//m.setA(externaltom);
		m.setAFromF();

		// 以下はルンゲ・クッタ法

		Vec2 kx1=m.V().Prod(stepT);
		Vec2 kvx1=m.A().Prod(stepT);

		Vec2 kx2=(m.V().Sum(kvx1.Quot(2f))).Prod(stepT);
		Vec2 m1p=m.Pos().Sum(kx1.Quot(2f));

		externaltom=ExternalForcetom(m1p);

		Vec2 kvx2=externaltom.Prod(stepT);
		Vec2 kx3=((m.V()).Sum(kvx2.Quot(2f))).Prod(stepT);

		m1p=m.Pos().Sum(kx2.Quot(2f));

		externaltom=ExternalForcetom(m1p);


		Vec2 kvx3=externaltom.Prod(stepT);

		Vec2 kx4=(m.V().Sum(kvx3)).Prod(stepT);


		m1p=m.Pos().Sum(kx3);



		externaltom=ExternalForcetom(m1p);


		Vec2 kvx4=externaltom.Prod(stepT);



		m.setPos((m.Pos()).Sum( (kx1.Sum(kx2.Prod(2f),kx3.Prod(2f),kx4)).Quot(6f)) );
		if( pt != null ) {
			pt.lineTo(m.X(), m.Y());
		}
		m.setV((m.V()).Sum( (kvx1.Sum(kvx2.Prod(2f),kvx3.Prod(2f),kvx4)).Quot(6f)) );		 
	}

	@Override
	protected void writePrevious(Canvas canvas) {
		super.writePrevious(canvas);
		// 書き終わった後で、mが画面の外に出ていないか、チェック。
		if( m.X()<-m.R() || m.X() > wx+m.R()
				|| (m.Y())<-m.R() || m.Y()>hy+m.R() ) {
			existFlg=false;
			// pt=null;
		}
	}

	@Override
	protected void writeSubView(Canvas canvas) {
		// TODO Auto-generated method stub
		
	}

	public void clearPath() {
		pt=null;
	}
}

