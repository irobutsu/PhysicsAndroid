package jp.ac.u_ryukyu.phys.RelativisiticTrain;

import jp.ac.u_ryukyu.phys.RelativisiticTrain.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.FloatMath;
import jp.ac.u_ryukyu.phys.lib.DraggManageView;
import jp.ac.u_ryukyu.phys.lib.MovingObject;
import jp.ac.u_ryukyu.phys.lib.Vec2;

public class RelativisticTrainView extends DraggManageView {
	  float V=0;
	  float futosa=0.07f;
	  float Lfactor=1.0f;
//	  float firstX=-0.35f;
	  Bitmap bitmapx,bitmapct,bitmapxd,bitmapctd;
	  
	  boolean isAnimeMode=true;
	  boolean isTimeShift=true;
	  
	  
	  float w,h;	
	  
	  public void setV(float a) { V=a;}
	  public RelativisticTrainView(Context context){
			super(context);
			setBackgroundColor(Color.WHITE);
			setFocusable(true); 
			setStepT(0.01f);
			Resources r = getResources();
			bitmapx=BitmapFactory.decodeResource(r, R.drawable.x);
			bitmapxd=BitmapFactory.decodeResource(r, R.drawable.xd);
			bitmapct=BitmapFactory.decodeResource(r, R.drawable.ct);
			bitmapctd=BitmapFactory.decodeResource(r, R.drawable.ctd);
			
		}  

	  // 以下は、
	 // (w/2,h/4)が原点で、
	  // w/2=1になるような座標系での楕円や線を書くルーチン。
	  private void fillellipse(Canvas g,int c,float x,float y,float r,float rr)
	  {
		Paint p=new Paint();
	    p.setColor(c);
	    g.drawOval(new RectF(w/2+((x-rr*r)*w/2),h/4-((y-r)*w/2),
	    		w/2+((x+rr*r)*w/2),h/4-((y+r)*w/2)),p);
	  }
	  
	  private void line(Canvas g,int c,float x,float y,float xe,float ye)
	  {
		  Paint p=new Paint();
	    p.setColor(c);
	    g.drawLine(w/2+(x*w/2),h/4-(y*w/2),
		       w/2+(xe*w/2),h/4-(ye*w/2),p);
	  }
	 
	  
	  private void fillcircle(Canvas g,int c,float x,float y,float r)
		{Paint p=new Paint();
		  p.setColor(c);
		  g.drawOval(new RectF(w/2+((x-r)*w/2),h/4-((y-r)*w/2),
				  w/2+((x+r)*w/2),h/4-((y+r)*w/2)),p);
		}
	  
	
	 

	    void writeGraph(Canvas g)
	    {
	        float gw=getWidth();
	        float gh=getHeight();
	        float ofsety=gh/6;
	        float denshalen=gw*0.4f;

	        
	     
	        
	        gh=gh-ofsety; // オフセット分高さを下げる。
	        
	        Lfactor=FloatMath.sqrt(1-V*V);

	        Paint p=new Paint();
	        p.setStrokeWidth(2f);


	        g.save();
	        p.setColor(Color.CYAN);
	        g.drawRect(0,0,gw,ofsety,p);

	        g.clipRect(new Rect(0,(int)ofsety,(int)gw,(int)(2*gh)));
	        p.setColor(Color.WHITE);
	        g.drawRect(0,ofsety,gw,ofsety+gh,p);

	        p.setColor(Color.GRAY);
	        g.drawLine(0,ofsety+7*gh/8,gw,ofsety+7*gh/8,p);
	        p.setTextSize(gw*0.05f);
	        Matrix m=new Matrix();
	        float xheight=bitmapx.getHeight();
	        float scale=gw*0.04f/xheight;
	        xheight=0.04f*gw;
	        m.setScale(scale,scale);
	        m.postTranslate(gw-0.07f*gw, ofsety+7*gh/8);
	        g.drawBitmap(bitmapx,m,p);
	        // g.drawText("x", gw-0.06f*gw, ofsety+7*gh/8, p);
	        g.drawLine(gh/8,ofsety+0,gh/8,ofsety+gh,p);

	        float ctheight=bitmapct.getHeight();
	        ctheight *= scale;
	        m.setScale(scale,scale);
	        m.postTranslate(gh/8-ctheight, ofsety+ctheight);
	        g.drawBitmap(bitmapct,m,p);
	        		
	        // g.drawText("ct", gh/8, ofsety+0.05f*gw, p);
	        

	        p.setColor(Color.BLACK);
	        g.drawLine(0,ofsety+7*gh/8+(V*gh/8),gw,ofsety+7*gh/8-(V*(gw-gh/8)),p);
	        float xdheight=bitmapxd.getHeight();
	        xdheight *=scale;
	        m.setScale(scale,scale);
	        m.postSkew(0,-V*Lfactor);
	        m.postTranslate(gw-0.07f*gw, ofsety+7*gh/8-(V*(gw-gh/8))-xdheight);
	        g.drawBitmap(bitmapxd, m, p);
	        // g.drawText("x'", gw-0.06f*gw, ofsety+7*gh/8-(V*(gw-gh/8)), p);
	        g.drawLine(0,ofsety+7*gh/8+(V*gh/8)-(denshalen*Lfactor),
	                   gw,ofsety+7*gh/8-(V*(gw-gh/8))-(denshalen*Lfactor),p);
	        float ctdheight=bitmapctd.getHeight();
	        ctdheight *=scale;
	        m.setScale(scale,scale);
	        m.postSkew(-V*Lfactor,0);
	        m.postTranslate(gh/8+(V*7*gh/8), ofsety+0.05f*gw+ctheight-ctdheight);
	        g.drawBitmap(bitmapctd, m, p);
	        // g.drawText("ct'", gh/8+(V*7*gh/8), ofsety+0.05f*gw, p);

	        g.drawLine(gh/8+(V*7*gh/8),ofsety+0,
	        		   gh/8-(V*gh/8),ofsety+gh,p);



	        g.drawLine(gh/8+(V*7*gh/8),ofsety+0,gh/8-(V*gh/8),ofsety+gh,p);

	        g.drawLine(gh/8+(V*7*gh/8+denshalen*Lfactor),ofsety+0,
	                   gh/8-(V*gh/8-denshalen*Lfactor),ofsety+gh,p);

	        p.setColor(Color.BLUE);
	        g.drawLine(gh/8+(V*7*gh/8+denshalen/2*Lfactor),ofsety+0,
	                   gh/8-(V*gh/8-denshalen/2*Lfactor),ofsety+gh,p);


	        p.setColor(Color.RED);
	        g.drawLine(0,ofsety+gh,gh,ofsety+0,p);

	        if( isTimeShift ) {
	        	g.drawLine(gh/4+(denshalen/Lfactor+V*denshalen/Lfactor),ofsety+gh,
	        			-3*gh/4+(denshalen/Lfactor+V*denshalen/Lfactor),ofsety+0,p);
	        } else {
	        	g.drawLine(gh/4+(denshalen*Lfactor),ofsety+gh,
	        			-3*gh/4+(denshalen*Lfactor),ofsety+0,p);
	        }
	        p.setColor(Color.GREEN);
	        float denshaX1=gh/8+(denshalen*V*t);
	        float denshaX2=gh/8+(denshalen*V*t)+(denshalen*Lfactor);
	        float denshaY=ofsety+7*gh/8-(denshalen*t);
	        
	        
	        g.drawLine(denshaX1,denshaY,
	                   denshaX2,denshaY,p);
	                   

	        p.setColor(Color.RED);

	        float lightX1;
	        if( isTimeShift ) {
	        	lightX1=gh/8+(denshalen/Lfactor+V*denshalen/Lfactor)-denshalen*t;
	        } else {
	        	lightX1=gh/8+(denshalen*Lfactor)-denshalen*t;
	        }
	        float lightX2=gh/8+denshalen*t;
	        
	        g.drawOval(new RectF(lightX1-5,denshaY-5,lightX1+5,denshaY+5),p);
	        g.drawOval(new RectF(lightX2-5,denshaY-5,lightX2+5,denshaY+5),p);
	        g.restore();
	        
	    
	        Path pt=new Path();
	        
	        
	        
	        pt.moveTo(denshaX1,ofsety/2-futosa*gw/2);
	        pt.lineTo(denshaX1,ofsety/2+futosa*gw/2);
	        pt.lineTo(denshaX2,ofsety/2+futosa*gw/2);
	        pt.lineTo(denshaX2,ofsety/2-futosa*gw/2);
	        pt.close();

	        p.setColor(Color.WHITE);
	        p.setStyle(Style.FILL);
	        g.drawPath(pt, p);
	        p.setColor(Color.GREEN);
	        p.setStyle(Style.STROKE);
	        g.drawPath(pt, p);
	        
	        float denshaM=(denshaX1+denshaX2)/2;
	        
	        p.setColor(Color.BLUE);
	        p.setStyle(Style.FILL);
	        float hankei=0.5f*futosa*gw/2;
	        g.drawOval(new RectF(denshaM-hankei*Lfactor,ofsety/2-futosa*gw/2,denshaM+hankei*Lfactor,ofsety/2-futosa*gw/2+2*hankei), p);
	        g.drawLine(denshaM,ofsety/2-futosa*gw/2+2*hankei,denshaM,ofsety/2-futosa*gw/2+3*hankei,p);
	        g.drawLine(denshaM+hankei*Lfactor,ofsety/2+futosa*gw/2,denshaM,ofsety/2-futosa*gw/2+3*hankei,p);
	        g.drawLine(denshaM-hankei*Lfactor,ofsety/2+futosa*gw/2,denshaM,ofsety/2-futosa*gw/2+3*hankei,p);

	        if( lightX2 < denshaM ) {
	        	g.drawLine(denshaM-2*hankei*Lfactor,ofsety/2+0.5f*hankei,denshaM,ofsety/2+0.5f*hankei,p);
	        } else {
	        	g.drawLine(denshaM-2*hankei*Lfactor,ofsety/2-futosa*gw*0.2f,denshaM,ofsety/2+0.5f*hankei,p);
	        }
	        if( lightX1 <= denshaM) {
	        	g.drawLine(denshaM+2*hankei*Lfactor,ofsety/2-futosa*gw*0.2f,denshaM,ofsety/2+0.5f*hankei,p);
	        } else {
	        	g.drawLine(denshaM+2*hankei*Lfactor,ofsety/2+0.5f*hankei,denshaM,ofsety/2+0.5f*hankei,p);
	        }


	        p.setColor(Color.RED);
	        if( lightX1 > denshaX1 && lightX1 < denshaX2 ) {
	        	g.drawOval(new RectF(lightX1-5,ofsety/2-5,lightX1+5,ofsety/2+5),p);
	        }
	        if( lightX2 > denshaX1 && lightX2 < denshaX2 ) {
	        	g.drawOval(new RectF(lightX2-5,ofsety/2-5,lightX2+5,ofsety/2+5),p);
	        }

	        if( denshaY > ofsety ) {
	        	p.setColor(Color.argb(50, 0, 256, 0));
	        	g.drawLine(denshaX1, ofsety/2, denshaX1, denshaY, p);
	        	g.drawLine(denshaX2, ofsety/2, denshaX2, denshaY, p);
	        }
	        if( denshaX1 > 1.05f*gw) {
	        	t=0; // 電車が消えてしばらくたったら最初に戻す。
	        }
	    }

	    void writeDensha(Canvas g, float tt,float height)
	    {
	    	float firstX=-0.35f;
			float denshaX=firstX+V*tt;

	        
	   
	        Lfactor=FloatMath.sqrt(1-V*V);
	    
	    
	        if( tt > Lfactor/(1-V) ) {
	            // 		fillcircle(g,Color.RED,denshaX+Lfactor/2,h,futosa/6);
	        } else {
	            fillcircle(g,Color.RED,firstX-Lfactor/2+ tt,height,futosa/6);
	        }
	        
	        if( isTimeShift ) {
	            if( tt > 1f/Lfactor ) {
	                // fillcircle(g,Color.RED,
	                //	       denshaX-Lfactor/2,h,futosa/6);
	            } else if ( tt < V/Lfactor ) {
	                // fillcircle(g,Color.RED,
	                //	       denshaX+Lfactor/2,h,futosa/6);
	            } else {
	                fillcircle(g,Color.RED,
	                           firstX+V*V/Lfactor
	                           +Lfactor/2-(tt -V/Lfactor),
	                           height,futosa/6);
	      }		
	    } else {
	      if( tt > Lfactor/(1+V) ) {
		// fillcircle(g,Color.RED,
		//       denshaX-Lfactor/2,h,futosa/6);
	      } else {
		fillcircle(g,Color.RED,
			   firstX+Lfactor/2- tt,height,futosa/6);
	      }
	    }
	    line(g,Color.GREEN,
		 denshaX-Lfactor/2,height-futosa/2,
		 denshaX-Lfactor/2,height+futosa/2);
	    line(g,Color.GREEN,
		 denshaX-Lfactor/2,height+futosa/2,
		 denshaX+Lfactor/2,height+futosa/2);
	    line(g,Color.GREEN,
		 denshaX+Lfactor/2,height-futosa/2,
		 denshaX+Lfactor/2,height+futosa/2);
	    line(g,Color.GREEN,
		 denshaX-Lfactor/2,height-futosa/2,
		 denshaX+Lfactor/2,height-futosa/2);
	    
	    fillellipse(g,Color.BLUE,denshaX,height+futosa/3,futosa/3,Lfactor);
	    
	    line(g,Color.BLUE,
		 denshaX+Lfactor*futosa/3,height-futosa/8,
			 denshaX-Lfactor*futosa/3,height-futosa/8);
	    line(g,Color.BLUE,
		 denshaX,height,
		 denshaX,height-futosa/4);
	    line(g,Color.BLUE,
		 denshaX,height-futosa/4,
		 denshaX+Lfactor*futosa/4,height-futosa/2);
	    line(g,Color.BLUE,
		 denshaX,height-futosa/4,
		 denshaX-Lfactor*futosa/4,height-futosa/2);
	  }
	  

	@Override
	public boolean ClickOthers(Vec2 m) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void drawBackGround(Canvas c) {
		
    Paint p=new Paint();
	    
	    w=getWidth();
	    h=getHeight();
	    
	    p.setColor(Color.WHITE);
	    c.drawRect(0,0,w,h,p);
	    

	    writeDensha(c,t,0);
	    writeGraph(c);
	}

	

	@Override
	protected void writeSubView(Canvas canvas) {
		// TODO Auto-generated method stub

	}
	public void restart() {
		t=0;
		
	}
	public void changeshiftFlg(boolean isChecked) {
		isTimeShift=isChecked;
	}
	
	@Override
	protected void setSituation() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void calcNextEach(MovingObject movingObject) {
		// TODO Auto-generated method stub
		
	}

}
