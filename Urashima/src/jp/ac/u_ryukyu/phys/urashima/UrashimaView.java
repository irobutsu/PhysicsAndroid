package jp.ac.u_ryukyu.phys.urashima;


import jp.ac.u_ryukyu.phys.lib.DraggManageView;
import jp.ac.u_ryukyu.phys.lib.MovingObject;
import jp.ac.u_ryukyu.phys.lib.Vec2;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.FloatMath;


public class UrashimaView extends DraggManageView {
	Bitmap earth;
	    
	



	
	
	  public void Vchange(int a) { V=((float)a)/100; }
	  public void changecenterFlg(boolean f){ isEarthCenter=f;  }
	  
	  
	 
	

	  float V=0;
	  float futosa=0.1f;
	  float nagasa=0.5f;
	  float Lfactor=1.0f;

	  boolean isEarthCenter=false;
	  boolean isLorentz=false;
	  boolean repeatMode=true;
	  boolean naminamiMode=false;	    
	  float w,h;
	  float earthX;
	  private boolean ttFlg;
	  private boolean ptofFlg;

	  public UrashimaView(Context context){
		  super(context);
		  setBackgroundColor(Color.WHITE);
		  Resources r = getResources();
		  earth=BitmapFactory.decodeResource(r, R.drawable.earth);
		  setFocusable(true);
		  setStepT(0.01f);
	  }


	  // このプログラムでは、画面中心を(0,0)として、x=-1,x=1が画面の左右端になる
	  // という座標系を使っている。下は座標系の間の変換式
	  float X(float xx) { return w/2+(xx*w/2);}
	  float Y(float xx) { return h/2-(xx*w/2);}
	  
	  private void line(Canvas g,int c,float x,float y,float x1,float y1)
	  { 
		  Paint p=new Paint(); p.setColor(c);
		  p.setStyle(Style.STROKE);
	    
		  g.drawLine(w/2+(int)(x*w/2),h/4-(int)(y*w/2),
		       w/2+(int)(x1*w/2),h/4-(int)(y1*w/2),p);	    
	  }

	  private void circle(Canvas g,int c,float x,float y,float r)
	  {
		  Paint p=new Paint(); p.setColor(c);
		  p.setStyle(Style.STROKE);
		  g.drawCircle(w/2+((x)*w/2),h/2-((y)*w/2),r*w/2,p);
	  }
	  
	  private void fillcircle(Canvas g,int c,float x,float y,float r)
	  {
	    Paint p=new Paint(); p.setColor(c);
	    p.setStyle(Style.FILL);
	    g.drawCircle(w/2+x*w/2,h/4-y*w/2,r*w/2,p);
	  }
	  
	  private void fillellipse(Canvas g,int c,float x,float y,float r,float rr)
	  {
		  Paint p=new Paint(); p.setColor(c);
	    p.setStyle(Style.FILL);
	    g.drawOval(new RectF(w/2+((x-rr*r)*w/2),h/4-((y+r)*w/2),w/2+((x+rr*r)*w/2),h/4-(((y-r)*w/2))),p);
	  }
	  
	  
	  public void setV(float a) { V=a; }
	  public void setEFlg(boolean f){ isEarthCenter=f;}
	  public void setLFlg(boolean f){isLorentz=f; }
	  public void changeRepeatFlg(boolean f){repeatMode=f; }
	  public void setWFlg(boolean f){naminamiMode=f; }

	  public void writeLight(Canvas g,int c, float t1,float Vv){
		  if( Vv*t1 > nagasa*2 ) {
			  // fillcircle(g,c,earthX,-nagasa/2,futosa/6);
	      } else {
	    	  if( Vv*t1 > nagasa ) {
	    		  fillcircle(g,c,earthX+futosa/8,3*nagasa/2-Vv*t1,futosa/8);
	    	  } else {
	    		  fillcircle(g,c,earthX,-nagasa/2+Vv*t1,futosa/8);
	    		  if( c== Color.RED ) {
	    			  circle(g,Color.MAGENTA,earthX-V*t1,-nagasa/2,t1);
	    		  } else {
	    			  circle(g,Color.CYAN,earthX-V*t1,-nagasa/2,t1);
	    		  }
	    	  }
	      }
	      if( isLorentz ) {
	    	  if( t1 > nagasa*2/Vv ) {
	    		  // fillcircle(g,c,earthX,-nagasa/2,futosa/6);
	    	  } else if ( t1 > nagasa*Vv/(1-V) ) {
	    		  fillcircle(g,c,earthX+nagasa*Vv+nagasa*Vv*(1+V)/(1-V)-(1+V)*t1,-nagasa/2+futosa/8,futosa/8);
	    	  } else {
	    		  fillcircle(g,c,earthX+(1-V)*t1,-nagasa/2,futosa/8);
	    	  }
	      } else {
	    	  if( t1 > nagasa*2/(1-V*V) ) {
	    		  // fillcircle(g,c,earthX,-nagasa/2,futosa/6);
	    	  } else if ( t1 > nagasa/(1-V) ) {
	    		  fillcircle(g,c,earthX+nagasa+nagasa*(1+V)/(1-V)-(1+V)*t1,-nagasa/2+futosa/8,futosa/8);
	    	  } else {
	    		  fillcircle(g,c,earthX+(1-V)*t1,-nagasa/2-futosa/8,futosa/8);
	    	  }
	      }
	  }

		@Override
		public boolean ClickOthers(Vec2 m) {
			// TODO Auto-generated method stub
			return false;
		}


		@Override
		protected void drawBackGround(Canvas g) {
			int i;
			float rocketX;
			float rocketL;
			float earthX;
			float earthL;

			int y,ny;


			w=getWidth();
			h=getHeight();

			Paint p=new Paint();
			p.setColor(Color.BLACK);
			p.setStyle(Style.FILL);
			g.drawRect(0,0,w,h,p);



			if( isEarthCenter){
				earthX=0;
				earthL=1;
				rocketX=-0.85f+V*t;
				rocketL=FloatMath.sqrt(1-V*V);
			} else {
				earthX=-V*t;
				earthL=FloatMath.sqrt(1-V*V);
				rocketX=-0.85f;
				rocketL=1f;
			}

			float rocketW=0.3f;

			//      fillellipse(g,Color.green,earthX,-0.5,0.3,earthL);
			g.drawBitmap(earth,null,new Rect( 
					(int)(w/2+(earthX-earthL*0.3f)*w/2),
					(int)(h/4-(-0.5+0.3)*w/2),
					(int)(w/2+(earthX+earthL*0.3f)*w/2),
					(int)(h/4-(-0.5-0.3)*w/2)),p);
			line(g,Color.WHITE,rocketX,rocketW/2,rocketX,-rocketW/2);
			line(g,Color.WHITE,rocketX,rocketW/2,rocketX+0.5f*rocketL,rocketW/2);
			line(g,Color.WHITE,rocketX,-rocketW/2,rocketX+0.5f*rocketL,-rocketW/2);
			line(g,Color.WHITE,rocketX+0.5f*rocketL,rocketW/2,rocketX+0.7f*rocketL,0);
			line(g,Color.WHITE,rocketX+0.5f*rocketL,-rocketW/2,rocketX+0.7f*rocketL,0);

			float t0 =t*earthL;
			float tt = t0 - 2*rocketW*FloatMath.floor(t0/rocketW/2);
			if( tt > rocketW ) {
				fillcircle(g,Color.RED,earthX,-tt+3*rocketW/2-0.4f,0.01f);
			} else {
				fillcircle(g,Color.RED,earthX,tt-rocketW/2-0.4f,0.01f);
			}
			line(g,Color.BLACK,earthX-0.05f*earthL,-rocketW/2-0.4f,earthX+0.05f*earthL,-rocketW/2-0.4f);
			line(g,Color.BLACK,earthX-0.05f*earthL,rocketW/2-0.4f,earthX+0.05f*earthL,rocketW/2-0.4f);


			fillellipse(g,Color.YELLOW,earthX+0.2f*earthL,-0.3f,0.03f,earthL);
			float phi=(float) (2*Math.PI*t0/rocketW/2);
			line(g,Color.BLACK,
				   earthX+0.2f*earthL,
				   -0.3f,
				   earthX+earthL*(0.2f+0.03f*FloatMath.sin(phi)),
				   -0.3f+0.03f*FloatMath.cos(phi));



			t0=t*rocketL;
			tt = t0 - 2*rocketW*FloatMath.floor(t0/rocketW/2);
			if( tt > rocketW ) {
				fillcircle(g,Color.RED,rocketX+rocketL/8,3*rocketW/2-tt,0.01f);
			} else {
				fillcircle(g,Color.RED,rocketX+rocketL/8,tt-rocketW/2,0.01f);
			}



			fillellipse(g,Color.YELLOW,rocketX+0.4f*rocketL,0.15f,0.03f,rocketL);
			fillellipse(g,Color.YELLOW,rocketX+0.4f*rocketL,0.15f,0.03f,rocketL);

			phi=(float) (2*Math.PI*t0/rocketW/2);
			      line(g,Color.BLACK,	
				   rocketX+rocketL*0.4f,	
				   0.15f,
				   rocketX+rocketL*(0.4f+0.03f*FloatMath.sin(phi)),
				   0.15f+0.03f*FloatMath.cos(phi));
			      
		}	
		
		
	

		@Override
		protected void writeSubView(Canvas canvas) {
			// TODO Auto-generated method stub
			
		}


		public void restart() {
			t=0;
		}


		public void setTTFlg(boolean b) {
			ttFlg=b;
		}


		public void setPtoF(boolean b) {
			ptofFlg=b;
		}


		@Override
		protected void calcNextStage() {
			// TODO Auto-generated method stub
			
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

