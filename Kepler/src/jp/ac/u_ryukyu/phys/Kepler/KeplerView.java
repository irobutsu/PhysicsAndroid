package jp.ac.u_ryukyu.phys.Kepler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


import jp.ac.u_ryukyu.phys.lib.MovingObject;
import jp.ac.u_ryukyu.phys.lib.PhystemViewWithNearView;
import jp.ac.u_ryukyu.phys.lib.Vec2;
import jp.ac.u_ryukyu.phys.lib.CircleObject;

public class KeplerView extends PhystemViewWithNearView {
	CircleObject M;
	CircleObject m;
	private float Mom=1f; // 太陽は地球の何倍の質量を持っているか。
	private float G; // 万有引力定数
	private boolean showGFlg;
	private boolean showGVFlg;
	private boolean crashFlg;
	public KeplerView(Context context){
		super(context);
		// m=1,M=1とする。
		G=500000f;
		M=new CircleObject(Color.rgb(255,180,80), 30f,new Vec2(40f,10f),new Vec2(-30f,0f));
		m=new CircleObject(Color.rgb(30,255,80), 30f,new Vec2(120f,150f),new Vec2(30f, 0f));
		setMom(1f);
		addObj(M);
		addObj(m);
		// ドラッグをさせる場合、DraggManager
		M.setDraggManager(); 
		m.setDraggManager();	
	}
	public void stopGP() {
		Vec2 GV=(m.V().Prod(m.M())).Sum(M.V().Prod(M.M())).Quot(M.M()+m.M());
		m.setV(m.V().Diff(GV));
		M.setV(M.V().Diff(GV));
	}
	public void brake() {
		m.setV((m.V()).Prod(0.9f));
	}
	public void setMom(float g) {
		Mom=g;
		M.setM(g);
		M.setR(m.R()*((float)Math.pow(Mom,1.0/3.0)));
	}
  
    // 物体aから物体bへと働く相互作用力を求める。
	protected Vec2 InteractionFromMtom(Vec2 pM,Vec2 pm) {
		Vec2 inryoku;
    	Vec2 xxx= pM.Diff(pm); // m→M へのベクトル
    	float r2=xxx.NormSquare(); // その長さの自乗
    	float gg;
    	gg=(float)(G*M.M()*m.M()/(r2*Math.sqrt(r2)));
    	inryoku=xxx.Prod(gg);	
    	return inryoku;
	}
	
    @Override
	protected void setSituation() {
    	Vec2 inryoku=InteractionFromMtom(M.Pos(),m.Pos());
    	
    	m.setF(Color.argb(128, 255, 120, 120),m.Pos(),inryoku);
    	M.setF(Color.argb(128, 255, 120, 120),M.Pos(),inryoku.Prod(-1f));
    }
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		m.setR(0.025f*w);
		setMom(Mom);
	}
	public void setCircle() {
		if( M.isDragged() || m.isDragged()) {
			return;
		}
		nowGo=true;
		crashFlg=false;
		m.setPos(new Vec2(0.25f*wx, 0.5f*hy));
		m.setV(new Vec2(0f, (float)Math.sqrt(G*Mom*Mom/((1+Mom)*0.25f*wx))));
		M.setPos(new Vec2(0.5f*wx,0.5f*hy));
		M.setV(new Vec2(0f, -(float)Math.sqrt(G/((1+Mom)*0.25f*wx))));
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
		Vec2 shiftV=vv.Diff(m.PPos());
		Vec2 savePP=m.PPos();
		m.setPPos(vv);
		m.writeP(canvas);
		Vec2 saveM=M.Pos();
		M.setPPos(M.Pos().Sum(shiftV));
		canvas.save();
		canvas.clipRect(Nvxs, Nvys, Nvxe, Nvye);
		M.writeP(canvas);
		if( vFlg ) { M.writePV(canvas); }
		if( aFlg ) { M.writeA2(canvas); }
		if( fFlg ) { M.writeF(canvas); }
		canvas.restore();
		if( vFlg ) { m.writePV(canvas); }
		if( aFlg ) { m.writeA2(canvas); }
		if( fFlg ) { m.writeF(canvas); }
		m.setPPos(savePP);
		M.setPPos(saveM);
		
	}
	@Override
	protected void calcNextEach(MovingObject movingObject) {
		// このルーチンは「各物体ごとに」呼び出されるが、
		// 実際の計算はMとmをまとめてやらなくてはいけないので、
		// movingOjbectがmの時だけ動かすことにする。
		if( m==movingObject) {
			if( !crashFlg ){
				Vec2 interaction;

				float stepm=stepT/m.M();
				float mstepM=-stepT/M.M();
				
				interaction=InteractionFromMtom(M.Pos(),m.Pos());
				
				m.setA(interaction.Quot(m.M()));
				M.setA(interaction.Quot(-M.M()));


				// 以下はルンゲ・クッタ法

				Vec2 kx1=m.V().Prod(stepT);
				Vec2 kX1=M.V().Prod(stepT);

				Vec2 kvx1=m.A().Prod(stepT);
				Vec2 kvX1=M.A().Prod(stepT);
				// kvx1=万有引力*Δt

				Vec2 kx2=(m.V().Sum(kvx1.Quot(2f))).Prod(stepT);
				Vec2 kX2=(M.V().Sum(kvX1.Quot(2f))).Prod(stepT);
				// kx2=(v+0.5kvx1)Δt

				Vec2 m1p=m.Pos().Sum(kx1.Quot(2f));
				Vec2 M1p=M.Pos().Sum(kX1.Quot(2f));

				interaction=InteractionFromMtom(M1p,m1p);



				Vec2 kvx2=interaction.Prod(stepm);
				Vec2 kvX2=interaction.Prod(mstepM);


				Vec2 kx3=((m.V()).Sum(kvx2.Quot(2f))).Prod(stepT);
				Vec2 kX3=((M.V()).Sum(kvX2.Quot(2f))).Prod(stepT);

				m1p=m.Pos().Sum(kx2.Quot(2f));
				M1p=M.Pos().Sum(kX2.Quot(2f));

				interaction=InteractionFromMtom(M1p,m1p);


				Vec2 kvx3=interaction.Prod(stepm);
				Vec2 kvX3=interaction.Prod(mstepM);

				Vec2 kx4=(m.V().Sum(kvx3)).Prod(stepT);
				Vec2 kX4=(M.V().Sum(kvX3)).Prod(stepT);

				m1p=m.Pos().Sum(kx3);
				M1p=M.Pos().Sum(kX3);

				interaction=InteractionFromMtom(M1p,m1p);

				Vec2 kvx4=interaction.Prod(stepm);
				Vec2 kvX4=interaction.Prod(mstepM);


				m.setPos((m.Pos()).Sum( (kx1.Sum(kx2.Prod(2f),kx3.Prod(2f),kx4)).Quot(6f)) );
				M.setPos((M.Pos()).Sum( (kX1.Sum(kX2.Prod(2f),kX3.Prod(2f),kX4)).Quot(6f)) );
				m.setV((m.V()).Sum( (kvx1.Sum(kvx2.Prod(2f),kvx3.Prod(2f),kvx4)).Quot(6f)) );
				M.setV((M.V()).Sum( (kvX1.Sum(kvX2.Prod(2f),kvX3.Prod(2f),kvX4)).Quot(6f)) );
			} else {
				// 以下はクラッシュ中。２物体を一体で等速直線運動させる。
				m.setA(new Vec2(0,0));
				M.setA(new Vec2(0,0));
				(m.Pos()).add(m.V().Prod(stepT));
				(M.Pos()).add(M.V().Prod(stepT));
			}


			if((m.Pos().Diff(M.Pos())).NormSquare() <(m.R()+M.R())*(m.R()+M.R()) 
					&& !m.isDragged() && !M.isDragged()) {
				crashFlg=true;
				// 止める力が働いたので、それを加える。
				Vec2 mM=(m.Pos()).Diff(M.Pos());
				mM.normalize();
				mM.mul(M.R());
				Vec2 newV=(m.V().Prod(m.M()).Sum(M.V().Prod(M.M()))).Quot(m.M()+M.M());
				m.setF(Color.argb(200, 255, 255, 0), M.Pos().Sum(mM),((m.V().Diff(newV)).Quot(stepT)).Diff(m.getF()));
				M.setF(Color.argb(200, 255, 255, 0), M.Pos().Sum(mM),((M.V().Diff(newV)).Quot(stepT)).Diff(M.getF()));


				m.setV(newV);
				M.setV(newV);
			}
		}
	}
	@Override
	protected void writePrevious(Canvas canvas) {
		super.writePrevious(canvas);
		Vec2 GP=(m.Pos().Prod(m.M())).Sum(M.Pos().Prod(M.M()));
		GP.div(M.M()+m.M());
		if( showGFlg ) {
			Paint p=new Paint();
			p.setColor(Color.RED);
			canvas.drawLine(GP.X()+8f,GP.Y()+8f,GP.X()-8f,GP.Y()-8f,p);
			canvas.drawLine(GP.X()+8f,GP.Y()-8f,GP.X()-8f,GP.Y()+8f,p);
		}
		if( showGVFlg) {
			Vec2 GV=(m.V().Prod(m.M())).Sum(M.V().Prod(M.M()));
			GV.div(M.M()+m.M());
			MovingObject.drawYajirusi(canvas,Color.argb(100, 0, 128, 255), GP,GV, 3f);
		}
	}
	public void setGFlg(boolean isChecked) {
		showGFlg=isChecked;
	}
	public void setGVFlg(boolean isChecked) {
		showGVFlg=isChecked;
	}
}

