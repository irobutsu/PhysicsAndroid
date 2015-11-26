package jp.ac.u_ryukyu.phys.jiba2;


import jp.ac.u_ryukyu.phys.lib.Vec2;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

public class Jiba2View extends View {
	Current I1,I2;
	MonoPole M;
	float EE=0f;
	float N=5f;
	int w,h;
	boolean showEFlg=true;
	boolean showYFlg=false;
	boolean showy1Flg=false;
	boolean showy2Flg=false;
	boolean showVFlg=false;
	boolean showFFlg=true;
	boolean showSFlg=true;
	int www=128;
	float Vstep=4f;
	float XX1[];
	float XX2[];
	float YY1[];
	float YY2[];
	
	@Override
	protected void onSizeChanged(int ww, int hh, int oldw, int oldh) {
		w=ww;
		h=hh;
		V = new int[(int)((w+6)/3)][(int)((h+6)/3)];
		I1.setY(h/2);
		I2.setY(h/2);
		I1.setX(w/4);
		I2.setX(3*w/4);
		M.setY(h/4);
		M.setX(w/2);
	}

	public Jiba2View(Context context) {
		super(context);
		setFocusable(true); 
		I1=new Current(0,30,Color.RED,new Vec2(100f,100f));
		I2=new Current(0,30,Color.BLUE,new Vec2(200f,140f));
		M=new MonoPole(1,30,Color.CYAN,new Vec2(300f,100f),0);
	}
	float len=30f;
	float hankei=30f;
	int extB=0;
	int V[][];

	@Override
	protected void onDraw(Canvas canvas) {
		int x,y;
		
		
		canvas.drawColor(Color.WHITE);

		Paint p=new Paint();
		
	     int i,j;

	     double XXM[]=new double[(int)((w+3)/3)+1];
	     double YYM[]=new double[(int)((h+3)/3)+1];
	     double XXI1[]=new double[(int)((w+3)/3)+1];
	     double XXI2[]=new double[(int)((w+3)/3)+1];
	     double YYI1[]=new double[(int)((h+3)/3)+1];
	     double YYI2[]=new double[(int)((h+3)/3)+1];
	     for(i=0 ; i<= (w+3)/3 ; i++ ) {
	    	 XXM[i]=3*i-M.X();
	    	 XXI1[i]=(3*i-I1.X())*(3*i-I1.X());
	    	 XXI2[i]=(3*i-I2.X())*(3*i-I2.X());
	     }

	     for(j=0 ; j<= (h+3)/3 ; j++ ) {
		 	YYM[j]=3*j-M.Y();
		 	 YYI1[j]=(3*j-I1.Y())*(3*j-I1.Y());
	    	 YYI2[j]=(3*j-I2.Y())*(3*j-I2.Y());
	     }
	 	for(i=0 ; i<= (w+3)/3 ; i++ ) {
	 		x=3*i;
	 		for(j=0 ; j<= (h+3)/3 ; j++ ) {
	 			double v=extB*i/100.0;
	 			y=3*j;
	 			v += M.Q()*Math.atan2(XXM[i],YYM[j]);
	 			if( x != I1.X() || y != I1.Y() ) {
	 				v += I1.Q()*Math.log(XXI1[i]+YYI1[j])/5.0;
	 			}	
	 			if(	 x != I2.X() || y != I2.Y() ) { 
	 				v += I2.Q()*Math.log(XXI2[i]+YYI2[j])/5.0;
	 			}
	 			v *= 10.0;
	 			v /= Math.PI;
	 			V[i][j] = (int)Math.floor(v);
	 		}
	 	}

        int diff;
        for( i=0 ; i< w/3 ; i++ ) {
           	x=3*i;
            for( j=0 ; j< h/3 ; j++ ) {
            	y=3*j;
                if( (XXM[i])*(XXM[i])+(YYM[j])*(YYM[j]) > hankei*hankei 
                    &&XXI1[i]+YYI1[j] > hankei*hankei 
                    &&XXI2[i]+YYI2[j] > hankei*hankei 
                    ) {

                    diff = V[i+1][j+1] - V[i][j+1];
                    

                    if( (XXM[i] <= 3 && XXM[i] >= -3) && YYM[j]<0 ) {
                        if( diff == M.Q()*20 ) {
                        	diff=0;
                        }
                    }

                    if( diff != 0 ) {
                        p.setColor(Color.BLUE);
                        canvas.drawRect(x,y,x+3,y+3,p);
                    } else {
                        
                        diff = V[i+1][j+1] - V[i+1][j];
                        
                        if( diff != 0 ) {
                            p.setColor(Color.BLUE);
                            canvas.drawRect(x,y,x+3,y+3,p);
                        }
                    }
                }
            }
        }
        I1.write(canvas);
        I2.write(canvas);
        M.write(canvas);
    }

    boolean nowDrag1=false;
    boolean nowDrag2=false;
    int handled;
    Vec2 shift;
    @Override
	public boolean onTouchEvent(MotionEvent event) {
    	int pointerCount = event.getPointerCount();
    	handled=0;
    	Vec2 mp[]=new Vec2[pointerCount];
    	int i;
  
    	for(i=0; i<pointerCount ; i++ ) {
    		mp[i]=new Vec2(event.getX(i),event.getY(i));
    	
    		switch ( event.getAction() & MotionEvent.ACTION_MASK) {
    		case MotionEvent.ACTION_DOWN:
    		case MotionEvent.ACTION_POINTER_DOWN:
    			// 同じポインタで両方が動かないように。
    			if( !M.isDraggedBy(i) && !I2.isDraggedBy(i) && I1.isCatched(mp[i]) ) { 
    				I1.setDraggingPointer(i);
    				shift=mp[i].Diff(I1.P());
    				handled=1;
    			}
    			// 同じポインタで両方が動かないように。
    			if( !M.isDraggedBy(i) && !I1.isDraggedBy(i) && I2.isCatched(mp[i]) ) {
    				I2.setDraggingPointer(i);
    				shift=mp[i].Diff(I2.P());
    				handled=2;
    			}
    			if( !I1.isDraggedBy(i) && !I2.isDraggedBy(i) && M.isCatched(mp[i]) ) {
    				M.setDraggingPointer(i);
    				shift=mp[i].Diff(M.P());
    				handled=3;
    			}
    			break;
    		case MotionEvent.ACTION_MOVE:
    			if( I1.isDraggedBy(i)) {
    				I1.setPosition(mp[i]);
    				if(I1.X()> w-I1.R()) { I1.setX(w-I1.R());}
    				if(I1.X()< I1.R()) { I1.setX(I1.R());}
    				if(I1.Y()> h-I1.R()) { I1.setY(h-I1.R());}
    				if(I1.Y()< I1.R()) { I1.setY(I1.R());}
    				invalidate();
    			}
    			if( I2.isDraggedBy(i)) {
    				I2.setPosition(mp[i]);
    				if(I2.X()> w-I2.R()) { I2.setX(w-I2.R());}
    				if(I2.X()< I2.R()) { I2.setX(I2.R());}
    				if(I2.Y()> h-I2.R()) { I2.setY(h-I2.R());}
    				if(I2.Y()< I2.R()) { I2.setY(I2.R());}
    				invalidate();
    			}
    			if( M.isDraggedBy(i)) {
    				M.setPosition(mp[i]);
    				if(M.X()> w-M.R()) { M.setX(w-M.R());}
    				if(M.X()< M.R()) { M.setX(M.R());}
    				if(M.Y()> h-M.R()) { M.setY(h-M.R());}
    				if(M.Y()< M.R()) { M.setY(M.R());}
    				invalidate();
    			}
    			break;
    		case MotionEvent.ACTION_UP:
    		case MotionEvent.ACTION_POINTER_UP:
			//タッチが離されたときの動作
    			if( I1.isDraggedBy(i) ) {
    				I1.releaseDragg();
    				invalidate();
    			}
    			if( I2.isDraggedBy(i) ) {
    				I2.releaseDragg();
    				invalidate();
    			}
    			if( M.isDraggedBy(i) ) {
    				M.releaseDragg();
    				invalidate();
    			}
    			break;
    		}	
    	}
    	if( handled ==0 ) {
    		return super.onTouchEvent(event);
    	}
    	return true;
    }

	double V(int x,int y) {
		float xx1=x-I1.X();
		float xx2=x-I2.X();
		float yy1=y-I1.Y();
		float yy2=y-I2.Y();
        return( I1.Q()*Math.log(xx1*xx1+yy1*yy1)
                +I2.Q()*Math.log(xx2*xx2+yy2*yy2)
                -EE*y
                );
    }

	public void setEFlg(boolean isChecked) {
		showEFlg=isChecked;
	}
	public void setVFlg(boolean isChecked) {
		showVFlg=isChecked;
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
		int q=I1.Q();
		if( q<5 ) { q++; I1.setQ(q); }
		return q;
	}
	public int subQ1() {
		int q=I1.Q();
		if( q>-5 ) { q--; I1.setQ(q); }
		return q;
	}

	public int addQ2() {
		int q=I2.Q();
		if( q<5 ) { q++; I2.setQ(q); }
		return q;
	}
	public int subQ2() {
		int q=I2.Q();
		if( q>-5 ) { q--; I2.setQ(q); }
		return q;
	}

	public int addM() {
		int q=M.Q();
		if( q<5 ) { q++; M.setQ(q); }
		return q;
	}
	public int subM() {
		int q=M.Q();
		if( q>-5 ) { q--; M.setQ(q); }
		return q;
	}
	public int addB() {
		int q=extB;
		if( q<5 ) { q++; extB=q; }
		return q;
	}
	public int subB() {
		int q=extB;
		if( q>-5 ) { q--; extB=q; }
		return q;
	}
}
