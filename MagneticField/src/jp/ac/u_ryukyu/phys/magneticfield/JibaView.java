package jp.ac.u_ryukyu.phys.magneticfield;


import jp.ac.u_ryukyu.phys.lib.DraggManageView;
import jp.ac.u_ryukyu.phys.lib.MovingObject;
import jp.ac.u_ryukyu.phys.lib.Vec2;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;


public class JibaView extends DraggManageView {
	Charge Q1,Q2;
	float EE=0f;
	float N=5f;
	int w,h;
	boolean showEFlg=false;
	boolean showYFlg=true;
	boolean showy1Flg=false;
	boolean showy2Flg=false;
	boolean showFFlg=true;
	boolean showSFlg=true;
	int xv,yv;
	int www=64;
	float Vstep=4f;
	float V[][];
	float XX1[];
	float XX2[];
	float YY1[];
	float YY2[];
	@Override
	protected void writePrevious(Canvas canvas) {
		super.writePrevious(canvas);
		// ↑これで物体が描かれる。
	}
	@Override
	protected void onSizeChanged(int ww, int hh, int oldw, int oldh) {
		super.onSizeChanged(ww, hh, oldw, oldh);
		w=ww;
		h=hh;
		XX1=new float[(w/2)+1];
		XX2=new float[(w/2)+1];
		YY1=new float[(h/2)+1];
		YY2=new float[(h/2)+1];
		
		Q1.setX(w/2);
		Q1.setY(h/2);
		Q2.setX(w/4);
		Q2.setY(h/2);
	}

	public JibaView(Context context) {
		super(context);
		wallMg=0f;
		setFocusable(true); 

		Q1=new Charge(3,30,Color.RED,new Vec2(100f,100f));
		Q2=new Charge(-3,30,Color.BLUE,new Vec2(200f,140f));
		addObj(Q1);
		addObj(Q2);
		
		Q1.setDraggManager(0);
		Q2.setDraggManager(0);
//		timerStop();
	}
	
	Bitmap bgBitmap;
	protected void rewriteBackGround() {
		bgBitmap=Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bgBitmap);

		int x1i=(int)Q1.X();
		int x2i=(int)Q2.X();
		int y1i=(int)Q1.Y();
		int y2i=(int)Q2.Y();
		int x,y;
		Paint p=new Paint();

		if( showEFlg ) {
			if( Q1.Q()*Q2.Q() < 0 ) {
				if( Math.abs(Q1.X()-Q2.X())>Math.abs(Q1.Y()-Q2.Y()) ) {
					if( xv == x1i || xv == x2i ) {
						// 二つの電荷が同じ場所にあるなら何もしなくていい。
						xv =-1;
						yv =-1;
					} else {
						yv =-1;
                    }
				} else {
					if( yv == y1i || yv == y2i ) {
						xv =-1;
						yv =-1;
					} else {
						xv=-1;
                    }
                }
			} else {
				xv =-1;
				yv =-1;
            }
			for( x=0 ; x<w ; x++ ) {	
				writeRikisenFrom(0,x,0,canvas);
            }
			for( y=0 ; y<h ; y++ ) {
				writeRikisenFrom(1,w-1,y,canvas);
            }
			for( x=w-1 ; x>=0 ; x-- ) {
				writeRikisenFrom(2,x,h-1,canvas);
            }
			for( y=h-1 ; y>1 ; y-- ) {
				writeRikisenFrom(3,0,y,canvas);
            }
			if( xv > 0 ) {
				for( y=0 ; y<h-1 ; y++ ) {
					writeRikisenFrom(1,xv,y,canvas);
					writeRikisenFrom(3,xv,y,canvas);
                }
            }
			if( yv > 0 ) {
				for( x=w-1 ; x > 0 ; x-- ) {
					writeRikisenFrom(0,x,yv,canvas);
					writeRikisenFrom(2,x,yv,canvas);
                }
			}
        }
       p.setColor(Color.BLACK);
	}

	@Override
	protected void drawBackGround(Canvas canvas) {
		int x,y;
		
		canvas.drawColor(Color.WHITE);



		xv= ((int)(Q1.X()+Q2.X()))/2;
		yv= ((int)(Q1.Y()+Q2.Y()))/2;

		if( rewriteBGFlg ) {
			rewriteBackGround();
			rewriteBGFlg=false;
		}
		canvas.drawBitmap(bgBitmap, 0, 0, new Paint());
		
		if( showYFlg || showy1Flg || showy2Flg ) {
			for( x=www; x < w ; x+=www ) {
				for( y=www; y< h ; y+=www ) {
					Vec2 np=new Vec2(x,y);
					float r1 =1.0f/((x-Q1.X())*(x-Q1.X())+(y-Q1.Y())*(y-Q1.Y()));
					Vec2 xxx=new Vec2(x-Q1.X(),y-Q1.Y());
					xxx.mul(Q1.Q()*1500f*r1);

					if( showy1Flg ) {
						drawYajirusi(canvas,Color.argb(128,255,130,130),np,xxx);
                    }

					float r2 =1.0f/((x-Q2.X())*(x-Q2.X())+(y-Q2.Y())*(y-Q2.Y()));
					Vec2 xxx2=new Vec2(x-Q2.X(),y-Q2.Y());
					xxx2.mul(Q2.Q()*1500f*r2);

					if( showy2Flg ) {
						drawYajirusi(canvas,Color.argb(128,130,130,255),np,xxx2);
                    }


					if( showYFlg ) {
						if( showSFlg ) {
							drawYajirusi(canvas,Color.argb(128,128,128,128),np,xxx.Sum(xxx2));
						} else {
							xxx.add(xxx2);
							xxx.normalize();
							xxx.mul(50f);
							drawYajirusi(canvas,Color.argb(128,128,128,128),np,xxx);
						}
					}

                }
            }
        }
		//Q1.write(canvas);
		//Q2.write(canvas);

       	if( showFFlg ) {
       		float f=(4000f*Q1.Q()*Q2.Q()/(((Q1.Pos().Diff(Q2.Pos())).Norm())));
       		
       		Vec2 r= Q1.Pos().Diff(Q2.Pos());
       		r.normalize();
       		
       		drawYajirusiF(canvas,Color.argb(128,0,128,255),Q1.Pos(),r.Prod(f));
       		drawYajirusiF(canvas,Color.argb(128,255,128,0),Q2.Pos(),r.Prod(-f));
        }
    }

	
	
	int segments[][];
	boolean calced[][];
	private boolean rewriteBGFlg=true;

    boolean SameSegment(double xx,double yy,double xxx,double yyy)
    {
        double angle1,angle2,angle3,angle4;
        if( Math.abs(xx-Q1.X())< 2.0 && Q1.Y()>yy) {
               // 角度がπや-πになる可能性のある、危険なところ。
            angle1=Math.atan2(Q1.X()-xx,Q1.Y()-yy)+Math.PI;
            angle3=Math.atan2(Q1.X()-xxx,Q1.Y()-yyy)+Math.PI;
        } else {
            angle1=Math.atan2(xx-Q1.X(),yy-Q1.Y());
            angle3=Math.atan2(xxx-Q1.X(),yyy-Q1.Y());
        }
        if( Math.abs(xx-Q2.X()) < 2.0 && Q2.Y()>yy) {
            angle2=Math.atan2(Q2.X()-xx,Q2.Y()-yy)+Math.PI;
            angle4=Math.atan2(Q2.X()-xxx,Q2.Y()-yyy)+Math.PI;
        } else {
            angle2=Math.atan2(xx-Q2.X(),yy-Q2.Y());
            angle4=Math.atan2(xxx-Q2.X(),yyy-Q2.Y());
        }
        int inte = (int)Math.floor((Q1.Q()*angle1+Q2.Q()*angle2-EE*xx)/(2.0*Math.PI)*N);
        int inte2 = (int)Math.floor((Q1.Q()*angle3+Q2.Q()*angle4-EE*xxx)/(2.0*Math.PI)*N);
        return(inte == inte2);
    } 

    void writeRikisenFrom(int ffrom, int xx,int yy,Canvas c)
    {
    	int from=ffrom;
    	int y=yy;
    	int x=xx;
		Paint p=new Paint();
		p.setColor(Color.RED);
    	// 0:上から 1:右から 2:下から 3:左から

		while(true) {
    		double xs=x;
    		double xe=x+1;
    		double ys=y;
    		double ye=y+1;

    		if( (Q1.Q() != 0 && Q1.isNear(x,y) )
					||(Q2.Q() !=0 && Q2.isNear(x,y) ) ) {
				// 電荷から近いところに来たら終了。
				return;
			}

    		switch(from) {
    		case 0:
    			// 上から来た。
    			if( SameSegment(xs,ys,xe,ys) ) {
    				// 上から電気力線は通ってこなかった。
    				return;
    			}
    			c.drawRect(x,y,x+1,y+1,p);
    			if( !SameSegment(xs,ye,xe,ye) ) {
    				// 下に通った。
    				y ++; from=0;
    				if( y > h ) {
    					return;
    				}
    			} else 	if( !SameSegment(xs,ys,xs,ye) ) {
    				// 左に通った。
    				x--; from=1;
    				if( x < 0 ) {
    					return;
    				}
    			} else if( !SameSegment(xe,ys,xe,ye) ) {
    				x++; from=3;
    				if( x > w ) {
    					return;
    				}
    			}
    			break;
    		case 1:
    	        if( SameSegment(xe,ys,xe,ye) ) {
    	            // 右から電気力線は通ってこなかった。
    	            return;
    	        }
    	       c.drawRect(x,y,x+1,y+1,p);
    	       if( !SameSegment(xs,ye,xe,ye) ) {
    	            // 下に通った。
    	    	   y++ ; from=0;
    	    	   if( y > h ) { return;}
    	        } else if( !SameSegment(xs,ys,xs,ye) ) {
    	            // 左に通った。
    	        	x--; from=1;
    	        	if( x < 0 ) { return;}
    	        }
    	       if( !SameSegment(xs,ys,xe,ys) ) {
    	    	   // 上に通った。
    	    	   y--; from=2;
    	    	   if( y < 0 ) { return; }
    	        }
    	       break;
    		case 2:
    			if( SameSegment(xs,ye,xe,ye) ) {
    				// 下から電気力線は通ってこなかった。
    				return;
    			}
    			c.drawRect(x,y,x+1,y+1,p);
    			if( !SameSegment(xs,ys,xe,ys) ) {
    				// 上に通った。
    				y--; from=2;
    				if( y < 0  ) { return; }
    			} else 	if( !SameSegment(xs,ys,xs,ye) ) {
    				// 左に通った。
    				x--; from=1;
    				if( x < 0 ) { 	return; }
    			} else if( !SameSegment(xe,ys,xe,ye) ) {
    				// 右に通った。
    				x++; from=3;
    				if( x > w ) { return; }
    			}
    			break;
    		case 3:
    			if( SameSegment(xs,ys,xs,ye) ) {
    				// 左から電気力線は通ってこなかった。
    				return;
    			}
    			c.drawRect(x,y,x+1,y+1,p);
    			if( !SameSegment(xs,ye,xe,ye) ) {
    				// 下に通った。
    				y++ ; from=0;
    				if( y > h ) { return;}
    			} else if( !SameSegment(xs,ys,xe,ys) ) {
    				// 上に通った。
    				y--; from=2;
    				if( y < 0  ) { return; }
    			} else if( !SameSegment(xe,ys,xe,ye) ) {
    				// 右に通った。
    				x++; from=3;
    				if( x > w ) { return; }
    			}
    			break;
    		}
        }
    }
   static void drawYajirusiF(Canvas c,int cc,Vec2 xxx,Vec2 vvx) {
		Paint pp=new Paint();
		pp.setColor(cc);
		float rr=vvx.Norm();
		float width=rr*0.05f;

		c.drawCircle(xxx.X(), xxx.Y(), width*2f, pp);
		drawYajirusi(c,cc,xxx,vvx);
	}
   static void drawYajirusi(Canvas c,int cc,Vec2 xxx,Vec2 vvx) {
		Paint pp=new Paint();
		pp.setColor(cc);
				
		Path p=new Path();

		float rr=vvx.Norm();
		float width=rr*0.05f;


		Vec2 xx=vvx.Prod(width/rr);
		xx.Rot1();

		 
		 
		 Vec2 soko1=xxx.Diff(xx);
		 Vec2 soko2=xxx.Sum(xx);
		 Vec2 noki3=soko2.Sum(vvx.Prod(0.8f));
		 Vec2 noki2=soko1.Sum(vvx.Prod(0.8f));
		 Vec2 noki1=noki2.Diff(xx);
		 Vec2 noki4=noki3.Sum(xx);
		 Vec2 teppen=xxx.Sum(vvx);
		 
		 
		 p.moveTo(soko1.X(), soko1.Y());
		 p.lineTo(soko2.X(), soko2.Y());
		 p.lineTo(noki3.X(), noki3.Y());
		 p.lineTo(noki4.X(), noki4.Y());
		 p.lineTo(teppen.X(),teppen.Y());
		 p.lineTo(noki1.X(), noki1.Y());
		 p.lineTo(noki2.X(), noki2.Y());
		 p.lineTo(soko1.X(), soko1.Y());
		 
		 c.drawPath(p,pp);
	 }

//	double V(int x,int y) {
//		float xx1=x-Q1.X();
//		float xx2=x-Q2.X();
//		float yy1=y-Q1.Y();
//		float yy2=y-Q2.Y();
//        return( Q1.Q()*Math.log(xx1*xx1+yy1*yy1)
//                +Q2.Q()*Math.log(xx2*xx2+yy2*yy2)
//                -EE*y
//                );
//    }

	public void setEFlg(boolean isChecked) {
		showEFlg=isChecked;
		rewriteBGFlg=true;
	}
	public void setYFlg(boolean isChecked) {
		showYFlg=isChecked;
	}
	public void setY1Flg(boolean isChecked) {
		showy1Flg=isChecked;
	}
	public void setY2Flg(boolean isChecked) {
		showy2Flg=isChecked;
	}
	public void setFFlg(boolean isChecked) {
		showFFlg=isChecked;
	}
	public void setSFlg(boolean isChecked) {
		showSFlg=isChecked;
	}

	public int addQ1() {
		int q1=Q1.Q();
		if( q1<5 ) { q1++; Q1.setQ(q1); }
		rewriteBGFlg=true;
		return q1;
	}
	public int subQ1() {
		int q1=Q1.Q();
		if( q1>-5 ) { q1--; Q1.setQ(q1); }
		rewriteBGFlg=true;
		return q1;
	}

	public int addQ2() {
		int q1=Q2.Q();
		if( q1<5 ) { q1++; Q2.setQ(q1); }
		rewriteBGFlg=true;

		return q1;
	}
	public int subQ2() {
		int q1=Q2.Q();
		if( q1>-5 ) { q1--; Q2.setQ(q1); }
		rewriteBGFlg=true;

		return q1;
	}


	@Override
	public boolean ClickOthers(Vec2 m) {
		
			return false;
		
	}

	@Override
	protected void writeSubView(Canvas canvas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setSituation() {
		if( Q1.isDragged() || Q2.isDragged() ) {
			rewriteBGFlg=true; // Q1かQ2がドラッグ中ならリライトする。
		}
		
	}

	
	@Override
	protected void calcNextEach(MovingObject movingObject) {
	}
	
	@Override
	protected void calcNext() {
	}
}
