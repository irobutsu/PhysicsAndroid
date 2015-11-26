package jp.ac.u_ryukyu.phys.GMm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.FloatMath;



import jp.ac.u_ryukyu.phys.lib.CircleObject;
import jp.ac.u_ryukyu.phys.lib.MovingObject;
import jp.ac.u_ryukyu.phys.lib.PhystemViewWithNearView1Body;
import jp.ac.u_ryukyu.phys.lib.Vec2;

public class GMmView extends PhystemViewWithNearView1Body {
	private CircleObject M;
	private boolean sFlg=true;
	
	private float Mom; // 太陽は地球の何倍の質量を持っているか。
	private float G; // 万有引力定数
	protected boolean crashFlg;	

	public GMmView(Context context){
		super(context);
		G=50000f;
		Mom=200f;	
		M=new CircleObject(Color.rgb(255,180,80), 80f,new Vec2(40f,20f),new Vec2(0f,0f));
		m=new CircleObject(Color.rgb(30,255,80), 30f,new Vec2(120f,150f),new Vec2(60f, -110f));	
		
		// ドラッグできるのはmの方だけ。
		m.setDraggManager();	
		addObj(m);
		addObj(M);
		wallColor=Color.rgb(200, 200, 0);
		stepT=0.05f;
		this.openNv=false;
	}

	public void setMom(float g) {
		Mom=g;
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// 画面サイズが決まった後で、Mをその画面の中心に置く。
		M.setPos(new Vec2(0.5f*wx,0.5f*hy));
		M.setPPos(M.Pos());
		M.setR(0.14f*hy);
		((CircleObject)m).setR(0.06f*hy);
		setCircle();
	}

	public void setSFlg(boolean a) { sFlg=a;}

 
    @Override
    protected void drawBackGround(Canvas canvas) {
    	Paint paint = new Paint();	 
    	//paint.setColor(Color.WHITE);
    	//canvas.drawRect(0f,0f,wx,hy,paint);
   
    	paint.setColor(wallColor);
    	canvas.drawRect(0, 0, wx, hy,paint); // 床。	 
    	canvas.drawBitmap(bitmap, null, new Rect((int)(wallMg*wx),(int)(wallMg*hy),(int)((1f-wallMg)*wx),(int)((1f-wallMg)*hy)), paint);
    	M.writeP(canvas);
    }

	public void setCircle() {
		nowGo=true;
		crashFlg=false;
		m.setPos(new Vec2(0.25f*wx, 0.5f*hy));
		M.setPos(new Vec2(0.5f*wx,0.5f*hy));
		m.setV(new Vec2(0f, (float)Math.sqrt(G*Mom/(0.25f*wx))));
	}
	public void brake() {
		m.setV((m.V()).Prod(0.9f));
	}


	protected Vec2 ExternalForcetom(Vec2 mPos) {
		Vec2 mtoM=M.Pos().Diff(mPos);
		float r2=mtoM.NormSquare();
		mtoM.mul(G*Mom/(r2*FloatMath.sqrt(r2)));
		return mtoM;
	}
	@Override
	protected void writePrevious(Canvas canvas) {
		// TODO Auto-generated method stub
		super.writePrevious(canvas);
		if( sFlg ) {
			 Paint pp=new Paint();
			 pp.setColor(Color.argb(60, 0, 255, 255));
				
			 Path p=new Path();


			 p.moveTo(m.PPos().X(),m.PPos().Y());
			 p.lineTo(M.PPos().X(), M.PPos().Y());
			 p.lineTo(m.PPos().X()+m.PV().X(),m.Pos().Y()+m.PV().Y());
			 canvas.drawPath(p,pp);
		}
	}

	@Override
	protected void writeNearViewContent(Canvas canvas, Vec2 vv) {
		Vec2 shiftV=vv.Diff(m.PPos());
		Vec2 saveM=M.Pos();
		M.setPPos(M.Pos().Sum(shiftV));
		canvas.save();
		canvas.clipRect(Nvxs, Nvys, Nvxe, Nvye);
		M.writeP(canvas);
		canvas.restore();
		M.setPPos(saveM);
		super.writeNearViewContent(canvas, vv);
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
		if( !crashFlg) {

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
			m.setV((m.V()).Sum( (kvx1.Sum(kvx2.Prod(2f),kvx3.Prod(2f),kvx4)).Quot(6f)) );		 



		} else {
			// 以下はクラッシュ中。
			// 運動はストップ。
			if( (m.Pos().Diff(M.Pos())).Norm() < ((CircleObject) m).R()+M.R() ) {
				crashFlg =true; 
				m.setV(new Vec2(0f,0f));
				m.setA(new Vec2(0f,0f));
				m.setF(Color.argb(200,255,255,120), m.Pos(),ExternalForcetom(m.Pos()).Prod(-1f));
			} else {
				crashFlg=false;
			}
		}


		if( (m.Pos().Diff(M.Pos())).NormSquare() <(((CircleObject) m).R()+M.R())*(((CircleObject) m).R()+M.R()) 
				&& !m.isDragged() && !M.isDragged()) {
			crashFlg=true;
			// 止める力が働いたので、それを加える。
			Vec2 mM=(m.Pos()).Diff(M.Pos());
			mM.normalize();
			mM.mul(M.R());

			Vec2 newV=new Vec2(0f,0f);
			m.setF(Color.argb(200, 255, 255, 0), M.Pos().Sum(mM),((m.V().Diff(newV)).Quot(stepT)).Diff(m.getF()));
			m.setV(newV);
		}
	}
}

