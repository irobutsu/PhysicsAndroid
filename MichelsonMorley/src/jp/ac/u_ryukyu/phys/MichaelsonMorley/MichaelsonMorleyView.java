package jp.ac.u_ryukyu.phys.MichaelsonMorley;

import jp.ac.u_ryukyu.phys.lib.DraggManageView;
import jp.ac.u_ryukyu.phys.lib.MovingObject;
import jp.ac.u_ryukyu.phys.lib.PhystemView;
import jp.ac.u_ryukyu.phys.lib.PhystemViewWithNearView;
import jp.ac.u_ryukyu.phys.lib.Vec2;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.FloatMath;


public class MichaelsonMorleyView extends DraggManageView {
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
	
	  public MichaelsonMorleyView(Context context){
			super(context);
			setBackgroundColor(Color.WHITE);
			setFocusable(true); 
			setStepT(0.01f);
		}
	    
	  
	  // このプログラムでは、画面中心を(0,0)として、x=-1,x=1が画面の左右端になる
	  // という座標系を使っている。下は座標系の間の変換式
	  float X(float xx) { return w/2+(xx*w/2);}
	  float Y(float xx) { return h/2-(xx*w/2);}
	  


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
	    g.drawCircle(w/2+x*w/2,h/2-y*w/2,r*w/2,p);
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
			int c;
			
			Paint p=new Paint();

			w=getWidth();
			h=getHeight();

	
			

			if( isLorentz ) {
				Lfactor=FloatMath.sqrt(1-V*V);
			} else {
				Lfactor=1;
			}
			
			if( repeatMode ) {
				if( V== 0 ) {
					if( t> nagasa*2+0.3 ) {
						t=0;
					}
				} else if( isLorentz ) {
					if( t > nagasa*2/Lfactor+0.3 ) {
						t=0;
					}
				} else {
					if( t > nagasa*2/(1-V*V)+0.3 ) {
						t=0;
					}
				}
			}

			p.setColor(Color.GRAY);
			g.drawRect(0,0,w,h,p);


			if( (!isEarthCenter) ){
				earthX=-0.85f+V*t;
			} else {
				earthX=-0.15f;
			}

			double ih=bitmap.getHeight();
			double iw=bitmap.getWidth();
			if( isEarthCenter ) {
				g.drawBitmap(bitmap,null,
						new Rect((int)(-w-(-0.15f+V*t)*w/2),0,
								 (int)(h*iw/ih-(-0.15f+V*t)*w/2),(int)h)
				,p);
			} else {
				g.drawBitmap(bitmap,null,
						new Rect((int)(-w-0.85f*w/2),0,
								 (int)(h*iw/ih-0.85f*w/2),(int)h)
				,p);
			}


			p.setColor(Color.GREEN);
			p.setStyle(Style.FILL);
			p.setAntiAlias(true);
			g.drawOval(new RectF(X(earthX-5*Lfactor),Y(0),X(earthX+5*Lfactor),Y(-10)),p);

			p.setColor(Color.WHITE);
			p.setStyle(Style.FILL);
			Path pt=new Path();
			pt.moveTo(X(earthX-Lfactor*(futosa/2)),Y(-nagasa/2-futosa/2));
			pt.lineTo(X(earthX-Lfactor*(futosa/2)),Y(nagasa/2));
			pt.lineTo(X(earthX+Lfactor*(futosa/2)),Y(nagasa/2));
			pt.lineTo(X(earthX+Lfactor*(futosa/2)),Y(-nagasa/2+futosa/2));
			pt.lineTo(X(earthX+Lfactor*nagasa),Y(-nagasa/2+futosa/2));
			pt.lineTo(X(earthX+Lfactor*nagasa),Y(-nagasa/2-futosa/2));
			pt.lineTo(X(earthX-Lfactor*(futosa/2)),Y(-nagasa/2-futosa/2));
//			pt.lineTo(X(earthX+Lfactor*(futosa/2)),Y(-nagasa/2+futosa/2));
			pt.close();
			g.drawPath(pt, p);
			p.setColor(Color.BLACK);
			p.setStyle(Style.STROKE);
			g.drawPath(pt, p);
			
			
			

			if( naminamiMode ) {
				float tt=t;
				c=Color.RED;
				do {
					writeLight(g,c,tt,FloatMath.sqrt(1-V*V));
					tt -= nagasa/4;
					if( c == Color.RED ) {
						c=Color.BLUE;
					} else {
						c=Color.RED;
					}
				} while (tt >0 );
			} else {
				writeLight(g,Color.RED,t,FloatMath.sqrt(1-V*V));
			}
			float deltaT=0.06f;
			if(ttFlg) { 
				writeDoor(g,earthX,nagasa/2,futosa/2,Lfactor);	
				if( !ptofFlg && Lfactor*t-deltaT > nagasa ) {
					writeDR(g,earthX-Lfactor*futosa/4,nagasa/2,futosa/4,Lfactor);
				}
				if( Lfactor*t < nagasa-deltaT && ptofFlg) {
					writeDR(g,earthX-Lfactor*futosa/4,nagasa/2,futosa/4,Lfactor);
				}
				if( Lfactor*t > nagasa-deltaT && Lfactor*t < nagasa+deltaT) {
					DoorOpen(g,earthX,nagasa/2,futosa/2,Lfactor);
				} 
				if( Lfactor*t > nagasa-deltaT && Lfactor*t < nagasa+deltaT ) {
					float ratio=(Lfactor*t-nagasa+deltaT)/(2*deltaT);
					float rsqrt=FloatMath.sqrt(ratio);
					float nsqrt=FloatMath.sqrt(1f-ratio);
					if( ptofFlg) {
						writeDR(g,earthX+Lfactor*(futosa/4-futosa/2*nsqrt),nagasa/2,futosa*nsqrt/4,Lfactor);
					} else {
						writeDR(g,earthX+Lfactor*(futosa/4-futosa/2*rsqrt),nagasa/2,futosa*rsqrt/4,Lfactor);
					}
				}
				
					
				
				writeDoor(g,earthX+(nagasa)*Lfactor,-nagasa/2,futosa/2,Lfactor);
				
				float td;
				if( isLorentz ) {
					td=nagasa*Lfactor/(1-V);
				} else {
					td=nagasa/(1-V);
				}
				if( ptofFlg && Lfactor*t > Lfactor*td+deltaT ) {
					writeDR(g,earthX+(nagasa-futosa/4)*Lfactor,-nagasa/2,futosa/4,Lfactor);
				}
				if( !ptofFlg && Lfactor*t < Lfactor*td-deltaT) {
					writeDR(g,earthX+(nagasa-futosa/4)*Lfactor,-nagasa/2,futosa/4,Lfactor);
				}
				if( Lfactor*t > Lfactor*td-deltaT && Lfactor*t < Lfactor*td+deltaT) {
					float ratio=(Lfactor*(t-td)+deltaT)/(2*deltaT);
					float rsqrt=FloatMath.sqrt(ratio);
					float nsqrt=FloatMath.sqrt(1f-ratio);
					DoorOpen(g,earthX+(nagasa)*Lfactor,-nagasa/2,futosa/2,Lfactor);
					if( ptofFlg ) {
						writeDR(g,earthX+(nagasa+futosa/4-futosa/2*rsqrt)*Lfactor,-nagasa/2,futosa*rsqrt/4,Lfactor);
					} else {
						writeDR(g,earthX+(nagasa+futosa/4-futosa/2*nsqrt)*Lfactor,-nagasa/2,futosa*nsqrt/4,Lfactor);
					}
				}
			}
		}
		protected void DoorOpen(Canvas g,float x,float y,float ww,float rr) {
			Paint p=new Paint(); 
			p.setColor(Color.rgb(128,0,0));
			Path pt=new Path();
			pt.moveTo(X(x+rr*0.6f*ww),Y(y+ww*0.45f));
			pt.lineTo(X(x+ww*rr),Y(y+ww*0.6f));
			pt.lineTo(X(x+ww*rr),Y(y-ww*0.6f));
			pt.lineTo(X(x+0.6f*ww*rr),Y(y-ww*0.45f));
			pt.close();
			g.drawPath(pt, p); 
			p.setColor(Color.BLACK);
			g.drawRect(X(x+ww*0.1f*rr),Y(y-ww*0.45f),X(x+ww*0.6f*rr),Y(y+0.4f*ww),p);
		}
		
		protected void writeDoor(Canvas g,float x,float y,float ww,float rr) {
			 Paint p=new Paint(); 
			 p.setColor(Color.CYAN);
			 g.drawRect(X(x),Y(y-ww/2),X(x+0.7f*ww*rr),Y(y+ww/2),p);
			 p.setColor(Color.rgb(0,200,200));
			 g.drawRect(X(x+ww*0.1f*rr),Y(y-ww*0.45f),X(x+ww*0.6f*rr),Y(y+0.4f*ww),p);
		}
		
		protected void writeDR(Canvas g,float x,float y,float r, float rr) {
			 Paint p=new Paint(); p.setColor(Color.BLUE);
			    p.setStyle(Style.FILL);
			    g.drawOval(new RectF(X(x)-rr*r*w/2,Y(y)-r*w/2,
			    		X(x)+rr*r*w/2,Y(y)+r*w/2),p);
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

