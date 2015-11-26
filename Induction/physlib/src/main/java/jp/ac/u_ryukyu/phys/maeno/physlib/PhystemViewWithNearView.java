package jp.ac.u_ryukyu.phys.maeno.physlib;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;


public abstract class PhystemViewWithNearView extends PhystemView {
	
	protected boolean lFlg=false;
	protected boolean rFlg=false;
	protected boolean lrFlg=true;

	
	protected float Nvxs,Nvx1,Nvx2,Nvxe,Nvys,Nvy1,Nvye;
	protected boolean openNv=true;
	
	
	// protected View nearView;
	
	
	public PhystemViewWithNearView(Context context) {
		super(context);	
	}
	


	protected abstract Vec2 getCenterofNearView();
	// NearViewの中心となる座標を取ってくる。

	protected abstract float getCenterXofNearView();
	
	protected void writeNearView(Canvas canvas) {
		float W=canvas.getWidth();
		float H=canvas.getHeight();
		 if( !openNv || lFlg  || (lrFlg && getCenterXofNearView()>0.5*W)) {
			 	Nvxs=0f;
			 	Nvys=0f;
			 	Nvxe=0.4f*W;
			 	Nvye=0.4f*H;
		 } else if( rFlg || (lrFlg && getCenterXofNearView() <= 0.5*W) ) {
				Nvxs=0.6f*W;
				Nvys=0f;
				Nvxe=W;
				Nvye=0.4f*H;
		 }
		 Nvx1=Nvxs+(Nvxe-Nvxs)*0.1f;
		 Nvx2=Nvxs+(Nvxe-Nvxs)*0.2f;
		 Nvy1=Nvys+(Nvxe-Nvxs)*0.1f;

		Paint paint=new Paint();
		float fudesize=0.005f*(Nvxe-Nvxs);
		// 画面に応じて細い筆で。
		
		if(openNv) {			 
			Vec2 centerP=getCenterofNearView();
			// NearViewの中心となる座標を取ってくる。
	        	
			paint.setAntiAlias(true);
			paint.setStrokeWidth(fudesize*2f);
			paint.setColor(wallColor);
			paint.setStyle(Paint.Style.FILL);
			canvas.drawRect(Nvxs, Nvys, Nvxe, Nvye,paint);
			int dx0=(int)(wallMg*wx -centerP.X()+(Nvxs+Nvxe)/2);
			int dy0=(int)(wallMg*hy -centerP.Y()+(Nvys+Nvye)/2);
			int dx1=(int)((1-wallMg)*wx -centerP.X()+(Nvxs+Nvxe)/2);
			int dy1=(int)((1-wallMg)*hy -centerP.Y()+(Nvys+Nvye)/2);
			if( bitmap != null ) {
				double ih=bitmap.getHeight();
				double iw=bitmap.getWidth();
				canvas.save();
				canvas.clipRect(Nvxs,Nvys,Nvxe,Nvye);
				int X1=(int)(iw*((centerP.X()-wallMg*W)/((1.0-2.0*wallMg)*W)-0.2));
				int Y1=(int)(ih*((centerP.Y()-wallMg*H)/((1.0-2.0*wallMg)*H)-0.2));
				int X2=(int)(iw*((centerP.X()-wallMg*W)/((1.0-2.0*wallMg)*W)+0.2));
				int Y2=(int)(ih*((centerP.Y()-wallMg*H)/((1.0-2.0*wallMg)*H)+0.2));
				if( !(X2 <0||Y2<0 || X1 > iw || Y1 > ih) )	 {
					canvas.drawBitmap(bitmap, 
							new Rect(0,0,(int)iw,(int)ih),
							new Rect(dx0,dy0,dx1,dy1),
							paint);
				}
				canvas.restore();
			} else {
				paint.setColor(bgColor);
				canvas.save();
				canvas.clipRect(Nvxs,Nvys,Nvxe,Nvye);
				canvas.drawRect(dx0,dy0,dx1,dy1,paint);
				canvas.restore();
			}

			paint.setColor(Color.CYAN);
			paint.setStyle(Paint.Style.STROKE);
			canvas.drawRect(Nvxs,Nvys,Nvxe,Nvye,paint);
			paint.setColor(Color.LTGRAY);
			paint.setStyle(Paint.Style.FILL);
			canvas.drawRect(Nvxs,Nvys,Nvx2,Nvy1,paint);
						
	        	
			paint.setColor(Color.rgb(0,128,128));
						canvas.drawLine(Nvxs, Nvy1-fudesize, Nvx1, Nvy1-fudesize,paint);
			canvas.drawLine(Nvx1-fudesize, Nvy1-fudesize, Nvx1-fudesize, Nvys,paint);
			canvas.drawLine(Nvx1, Nvy1-fudesize, Nvx2, Nvy1-fudesize, paint);
			canvas.drawLine(Nvx1+(Nvxe-Nvxs)*0.1f-fudesize, Nvy1-fudesize, Nvx2-fudesize, Nvys, paint);
			paint.setColor(Color.rgb(80,80,80));
			float xx1=Nvxs*0.9f+(Nvx1)*0.1f-fudesize;
			float xx2=Nvxs*0.1f+(Nvx1)*0.9f-fudesize;
			float xy1=Nvys*0.9f+(Nvy1)*0.1f-fudesize;
			float xy2=Nvys*0.1f+(Nvy1)*0.9f-fudesize;
			
			
			canvas.drawLine(xx1,xy2,xx2,xy1,paint);
			canvas.drawLine(xx1,xy1,xx2,xy2,paint);

			xx1=Nvx1*0.2f+Nvx2*0.8f;	
			xx2=Nvx1*0.8f+Nvx2*0.2f;
			xy1=Nvys*0.35f+Nvy1*0.65f;
			
			float hh=(Nvy1-Nvys)*0.25f;
			
			if( rFlg ) {
				paint.setColor(Color.GRAY);
			} else {
				paint.setColor(Color.RED);
			}
			canvas.drawLine(xx1, xy1, xx2, xy1, paint);
			canvas.drawLine(xx2+hh, xy1-5f, xx2, xy1, paint);
			canvas.drawLine(xx2+hh, xy1+5f, xx2, xy1, paint);
			
			xy1=Nvys*0.65f+Nvy1*0.35f;
			
			if( lFlg ) {
				paint.setColor(Color.GRAY);
			} else {
				paint.setColor(Color.RED);
			}
			canvas.drawLine(xx1, xy1, xx2, xy1, paint);
			canvas.drawLine(xx1-hh, xy1-5f, xx1, xy1, paint);
			canvas.drawLine(xx1-hh, xy1+5f, xx1, xy1, paint);

			Vec2 vv=new Vec2(0.5f*(Nvxs+Nvxe),0.5f*(Nvys+Nvye));
			writeNearViewContent(canvas,vv);
		} else {
			paint.setColor(Color.LTGRAY);
			paint.setStyle(Paint.Style.FILL);
			canvas.drawRect(Nvxs,Nvys,Nvx1,Nvy1,paint);
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(Color.rgb(0,128,128));
			paint.setStrokeWidth(fudesize*2);
			canvas.drawLine(Nvxs, Nvy1-fudesize, Nvx1, Nvy1-fudesize,paint);
			canvas.drawLine(Nvx1-fudesize, Nvy1-fudesize, Nvx1-fudesize, Nvys,paint);
			paint.setColor(Color.rgb(60,60,60));
			canvas.drawLine(0.9f*Nvxs+0.1f*Nvx1,0.9f*Nvys+0.1f*Nvy1,0.2f*Nvxs+0.8f*Nvx1,0.2f*Nvys+0.8f*Nvy1,paint);
			canvas.drawLine(0.2f*Nvxs+0.8f*Nvx1,0.5f*Nvys+0.5f*Nvy1,0.2f*Nvxs+0.8f*Nvx1,0.2f*Nvys+0.8f*Nvy1,paint);
			canvas.drawLine(0.5f*Nvxs+0.5f*Nvx1,0.2f*Nvys+0.8f*Nvy1,0.2f*Nvxs+0.8f*Nvx1,0.2f*Nvys+0.8f*Nvy1,paint);
		}
	}

	protected abstract void writeNearViewContent(Canvas canvas,Vec2 vv); 

	@Override
	public boolean ClickOthers(Vec2 m) {
		float mx=m.X();
		float my=m.Y();
		if( mx>Nvxs && mx<Nvx1 && my>Nvys && my<Nvye) {
			openNv = !openNv;
			return true;
		} else if( openNv && mx>Nvx1 && mx<Nvx2 && my>Nvys && my<Nvye) {
			if( lFlg ) {
				rFlg=true;
				lFlg=false;
			} else if( rFlg ) {
				rFlg=false;
				lrFlg=true;
			} else if( lrFlg) {
				lrFlg=false;
				lFlg=true;
			}
			return true;
		}
		return false;
	}

	
	@Override
	protected void writeSubView(Canvas canvas) {
    	writeNearView(canvas);	// これが入ったのが違うところ。
    }
}
