package jp.ac.u_ryukyu.phys.lib;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

public abstract class DraggManageView extends View {

	protected float wx;
	protected float hy;
	protected float wallMg=0.02f;
	protected int wallColor=Color.rgb(200, 200, 200);
	protected int bgColor=Color.WHITE;


	protected boolean isAttached;
	protected Bitmap bitmap;
	public void setBitmap(Bitmap b){bitmap=b;} 
	// 背景などに使うビットマップ（使わない場合もあり）

	protected ArrayList<MovingObject> objs;
	// 物体のリスト。動く物体は全部ここに登録されている必要がある。
	// ドラッグされる物体は、DragManagerが別に管理している。

	public DraggManageView(Context context) {
		super(context);
		setFocusable(true);
		objs=new ArrayList<MovingObject>();
	}
	
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		wx=w;
		hy=h;
	}

//	protected int nowDrag = 0;
	protected boolean nowGo;



	protected void addObj(MovingObject p) {
		objs.add(p);
	}

	protected void delObj(MovingObject p) {
		objs.remove(p);
	}

	public abstract boolean ClickOthers(Vec2 m);
	// 管理している物体以外が押された時の処理ルーチン。


	protected void drawBackGround(Canvas c) {
		Paint paint=new Paint();
		paint.setColor(wallColor);
		c.drawRect(0, 0, wx, hy,paint); // 壁
		paint.setColor(bgColor);
		c.drawRect(wallMg*wx,wallMg*hy,(1f-wallMg)*wx,(1f-wallMg)*hy,paint);
		if( bitmap != null ) {
			c.drawBitmap(bitmap, null, new Rect((int)(wallMg*wx),(int)(wallMg*hy),(int)((1f-wallMg)*wx),(int)((1f-wallMg)*hy)), paint);
		}
	}

	protected abstract void setSituation();
	protected abstract void writeSubView(Canvas canvas);
	//abstract protected void calcNext();

	@Override
	protected void onDraw(Canvas canvas) {
		drawBackGround(canvas); // まず、背景（にあたるもの）を書く。
		setSituation();         // 状況の確認。たとえば働く力などを設定。
		calcNextStage();        // 時間変化を設定。ドラッグなどもここでなんとかする。
		writePrevious(canvas);  // 次を設定した後で「その前」を描く。
		writeSubView(canvas);   // 別窓などを描く。
		prepareForNext();       // 次の段階のための準備
	}


	protected void writePrevious(Canvas canvas) {
		int i;
		for(i=0; i<objs.size(); i++) {
			objs.get(i).writeP(canvas);
		}
	}
	protected void prepareForNext() {
		int i;
		for(i=0; i<objs.size(); i++) {
			objs.get(i).clearF();
			objs.get(i).savePrev();
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean handled = DraggManager.manageDragg(event, this,
				wallMg * wx, wallMg * hy,
				(1f - wallMg) * wx, (1f - wallMg) * hy);
		return handled || super.onTouchEvent(event);
	}

	protected void calcNextStage() {
		// それぞれの物体について、外力から加速度を計算する。
		int i;
		for(i=0; i<objs.size(); i++) {
			objs.get(i).setAFromF();  
		}
		// ただし、これ以外にも、衝突や床からの垂直抗力があるかもしれない。 
		// というわけで、後で計算し直すのだが、それでもここで計算しておく意味は、
		// この後加速度がどう変化したかから、垂直抗力を計算するため。

		for(i=0; i< objs.size(); i++ ) {
			if( objs.get(i).isDragged() ) { 
				objs.get(i).pushPath();
				// とりあえず、ドラッグされたポイントをストアする。
				// MOVEイベントの時にやってないのは、MOVEイベントが発生する時間は
				// 一定間隔ではないから。

				if( objs.get(i).tamatteru() ) {
					Vec2 pA=objs.get(i).A();
					objs.get(i).setPosVFromPaths(stepT);
					objs.get(i).setF(Color.argb(128,255,0,255),
							objs.get(i).PPos().Sum(objs.get(i).shift()),
							objs.get(i).A().Diff(pA).Prod(objs.get(i).M()));
				} else {
					objs.get(i).setDraggedPos();
					objs.get(i).adjustV(stepT);
				}
			} else {
				calcNextEach(objs.get(i)); // ドラッグされていない物体について「次の位置」を計算する。
				// いろんな運動の可能性があるので、abstractにしておいて後ではめる。
			}
		}
		calcNext(); // 「次の位置」を計算する。
		// 相互作用しながら動く物体の場合、上で呼んだcalcNextEach(objs.get(i))では
		// 正しい位置が計算できない場合があるので、ここで全体の計算を読んでおく。
		// 実装はどちらか片っ方でもよい。
		
		for(i=0; i<objs.size(); i++) {
			objs.get(i).setAFromF();  
		}
	}

	
	protected void calcNext() {
		// 「何もしない」のがデフォルト。_
	} 
	abstract protected void calcNextEach(MovingObject movingObject);

	public float getWx() { return wx; }
	public float getHy() { return hy; }

	@Override
	protected void onAttachedToWindow() {
		timerStart();
		// isAttached=trueは↑でやられているので不要。
		super.onAttachedToWindow();
	}
	@Override
	protected void onDetachedFromWindow() {
		isAttached = false;
		super.onDetachedFromWindow();
	}



	// 計算機内の時間ステップも50ミリ秒としておく。
	protected float t=0f;
	protected long delay = 50;
	// 50ミリ秒がデフォルトのアニメーション間隔。１秒20コマ（ちょっと多い？）
	protected float stepT=0.05f;

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}
	
	public float Time(){return t;}
	protected void setStepT(float tt) {stepT=tt;}

	public void timerStop() { nowGo=false; }
	public void timerStart() { nowGo=true; handler_start();}
	public void timeStepGO() { t += stepT;}
	
	protected void handler_start() {
		Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (isAttached) {
					if( nowGo ) {
						timeStepGO(); //計算機内の時間をstepTだけ進める。
						invalidate(); //書き直しを起こさせる。
						sendEmptyMessageDelayed(0, delay);
						// delay ミリ秒経ったら、自分自身をもう一度呼び出すようにしておく。
					}
				}
			}
		};
		isAttached = true;
		handler.sendEmptyMessageDelayed(0, delay);
	}
}