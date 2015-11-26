package jp.ac.u_ryukyu.phys.bhthru;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.FloatMath;
import android.view.View;

public class BHthruView extends View {
	public BHthruView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		WW=-1; // WWは画像を作る時の横幅。これが負だということは、まだ描画が始まってない、ということ。
	}
	@Override
	protected void onSizeChanged(int www, int hhh, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(www, hhh, oldw, oldh);
		w=www;
		h=hhh;
		if(w == h ) {
			handler_start();
		}
	}
	protected Bitmap BackImage;
	int BackImageW,BackImageH;
	float aa=1f;
	float dd=30f;
	float ll=50f;
	int w;
	int h;

	int ph,pw;
	float R;
	
	public void setAA(float a) {
		aa=a;
		invalidate();
	}
	public void setDD(float a) {
		dd=a;
		invalidate();
	}
	public void setLL(float a) {
		ll=a;
		invalidate();
	}



	public void setBitmap(Bitmap b){BackImage=b;} 



	
	boolean changeFlg=false;



	protected long delay=1; // 1ミリ秒で一個計算できる？？
	protected boolean isAttached;
	protected void handler_start() {
		Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (isAttached) {
					boolean endFlg=false;
					endFlg=calcNext(); // 次の段階へ。
					invalidate(); //書き直しを起こさせる。
					if( !endFlg ) { 
						sendEmptyMessageDelayed(0, delay);
						// delay ミリ秒経ったら、自分自身をもう一度呼び出すようにしておく。
					}
				}
			}
		};
		isAttached = true;
		handler.sendEmptyMessageDelayed(0, delay);
	}

	
	Bitmap buf2; // 描画を残しておくためのバッファ
	Canvas gc2; // そのバッファに書き込むためのcanvas。
	Canvas gc;
	int WW;
	int nowX,nowY;

	public boolean calcNext() {
	
		int Iw=BackImage.getWidth()/2;
		int Ih=BackImage.getHeight()/2;

		float pixRatio= (BackImageW)/100f;

		if( w <= 0 || h<= 0 ) {
			return true; // これでは書けません。
		}
		float x=aa/dd;

	
		int w0=WW/2;
		if( WW <0 || changeFlg) {
			// これは初回起動のためのルーチン
			changeFlg=false;
			if( w> h ) {
				WW=h; 
			} else {
				WW=w;
			}
			if(WW<=0) { return false;}
			w0=WW/2; 

			buf2=Bitmap.createBitmap(WW, WW, Bitmap.Config.ARGB_8888);

			gc2= new Canvas(buf2); // gc2に対する描画は、buf2に対する描画。
			
			nowX=0;
			nowY=0;
			if( 3.0*x >= 2.0 ) {
				Paint p=new Paint();
				// シュワルツシュルト半径の1.5倍より近いところだと、前を向いてもBHしか見えない。
				// というわけでこの場合初回起動で黒く塗って終わり。
				p.setColor(Color.BLACK);
				p.setStyle(Paint.Style.FILL);
				if( gc != null ) {
					gc.drawRect(0,0,w,h,p);
				}
				gc2.drawRect(0,0,w,h,p);
				return true;
			}
			return false; // 次回に続く、というわけで帰る。
		}
		// 以下は２回目以降。

		float XX=FloatMath.sqrt(1-x);


		float xx=nowX-w0+0.5f;
		float yy=nowY-w0+0.5f;


		float rr=FloatMath.sqrt(xx*xx+yy*yy+w0*w0);
		float cosTx=xx/rr;
		float cosTy=yy/rr;
		float cosTz=-((float)(w0))/rr;


		float RR=FloatMath.sqrt(cosTx*cosTx+cosTy*cosTy);
		float tanTD=-cosTz/RR;
		float tanT=tanTD*XX;
		float v=x*tanT;

		int c1,c2,c3,c4,c5,c6,c7,c8;

		float cosX=cosTx/RR;
		float cosY=cosTy/RR;
		float hh=0.003f;

		float x1=x;

		float v2;
		float x2=x;
		float xr=0f;
		float yr=0f;
		float zr=0f;
		float phi=-1f;


		if( tanTD<=0.0 || -4.0/27.0 + v*v -x*x*x+x*x <=0.0 ) {
			int ii;
			for(ii=1 ; ii<3000 ; ii++ ) {
				float k1=hh*(1.5f*x1*x1-x1);
				float j1=hh*v;
				float x1j1=x1+j1*0.5f;
				float k2=hh*(1.5f*(x1j1)*(x1j1)-x1j1);
				float j2=hh*(v+0.5f*k1);
				float x1j2=x1+j2*0.5f;
				float k3=hh*(1.5f*(x1j2)*(x1j2)-x1j2);
				float j3=hh*(v+0.5f*k2);
				float x1j3=x1+j3;
				float k4=hh*(1.5f*(x1j3)*(x1j3)-x1j3);
				float j4=hh*(v+k3);

				v2 = v+ (k1+2*k2+2*k3+k4)/6.0f;
				x2 = x1+ (j1+2*j2+2*j3+j4)/6.0f;



				phi= hh*(ii);
				zr=aa/x2*FloatMath.cos(phi);
				if( zr <= -ll ) {
					zr=-ll;
					break;
				}
				v=v2;
				x1=x2;
			}

			if( zr== -ll ) {
				float rxy=aa/x2*FloatMath.sin(phi);
				xr=rxy*cosX;
				yr=rxy*cosY;

				int py= (int)(pixRatio*xr);
				int px= (int)(pixRatio*yr);

				if(px >=Iw || px <-Iw || py >=Ih || py < -Ih ) {
					c1=Color.BLUE;
					c2=Color.BLUE;
					c3=Color.BLUE;
					c4=Color.BLUE;
				} else {
					c1=BackImage.getPixel(Iw+px,Ih+py);
					c2=BackImage.getPixel(Iw-px-1,Ih+py);
					c3=BackImage.getPixel(Iw+px,Ih-py-1);
					c4=BackImage.getPixel(Iw-px-1,Ih-py-1);
				}
				if(py >=Iw || py <-Iw || px >=Ih || px < -Ih ) {
					c5=Color.BLUE;
					c6=Color.BLUE;
					c7=Color.BLUE;
					c8=Color.BLUE;
				} else {
					c5=BackImage.getPixel(Iw+py,Ih+px);
					c6=BackImage.getPixel(Iw-py-1,Ih+px);
					c7=BackImage.getPixel(Iw+py,Ih-px-1);
					c8=BackImage.getPixel(Iw-py-1,Ih-px-1);
				}
			} else {
				// ²¿¤Ë¤â¤Ö¤Ä¤«¤Ã¤Æ¤Ê¤¤¡£
				c1=Color.GRAY;
				c2=Color.GRAY;
				c3=Color.GRAY;
				c4=Color.GRAY;
				c5=Color.GRAY;
				c6=Color.GRAY;
				c7=Color.GRAY;
				c8=Color.GRAY;
			}
		} else {
			c1=Color.BLACK;
			c2=Color.BLACK;
			c3=Color.BLACK;
			c4=Color.BLACK;
			c5=Color.BLACK;
			c6=Color.BLACK;
			c7=Color.BLACK;
			c8=Color.BLACK;
		}
		Paint p=new Paint();
		p.setColor(c1);
		p.setStyle(Paint.Style.FILL);
		if( gc != null ) {
			gc.drawRect(nowY,nowX,1,1,p);
		}
		gc2.drawRect(nowY,nowX,1,1,p);
		p.setColor(c3);

		if( gc != null ) {
			gc.drawRect(nowY,WW-nowX-1,1,1,p);
		}
		gc2.drawRect(nowY,WW-nowX-1,1,1,p);

		p.setColor(c2);

		if( gc != null ) {
			gc.drawRect(WW-nowY-1,nowX,1,1,p);
		}
		gc2.drawRect(WW-nowY-1,nowX,1,1,p);
		p.setColor(c4);

		if( gc != null ) {
			gc.drawRect(WW-nowY-1,WW-nowX-1,1,1,p);
		}
		gc2.drawRect(WW-nowY-1,WW-nowX-1,1,1,p);

		p.setColor(c5);

		if( gc != null ) {
			gc.drawRect(nowX,nowY,1,1,p);
		}
		gc2.drawRect(nowX,nowY,1,1,p);
		p.setColor(c7);

		if( gc != null ) {
			gc.drawRect(nowX,WW-nowY-1,1,1,p);
		}
		gc2.drawRect(nowX,WW-nowY-1,1,1,p);

		p.setColor(c6);

		if( gc != null ) {
			gc.drawRect(WW-nowX-1,nowY,1,1,p);
		}
		gc2.drawRect(WW-nowX-1,nowY,1,1,p);
		p.setColor(c8);

		if( gc != null ) {
			gc.drawRect(WW-nowX-1,WW-nowY-1,1,1,p);
		}
		gc2.drawRect(WW-nowX-1,WW-nowY-1,1,1,p);

		nowY++;
		if( nowY >= w0 ) {
			nowX++;
			if( nowX >= w0 ) {
				return true; // 今回で終了〜
			}
			nowY=nowX;
		}
		return false; // まだ続く。
	}



	public void OnDraw(Canvas c){
		gc=c;
		if( gc == null ) {
			return;
		}




		if( w !=pw || h!= ph ) { 
			pw=w;
			ph=h;
			changeFlg=true;
		}

		Paint p=new Paint();
		p.setColor(Color.WHITE);
		p.setStyle(Paint.Style.FILL);
		gc.drawRect(0,0,w,h,p);



	
		
		
			gc.drawBitmap(buf2, 0, 0, p);
	
	}
}


