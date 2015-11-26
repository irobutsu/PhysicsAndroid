package jp.ac.phys.u_ryukyu.charges3D;

import java.util.ArrayList;
import jp.ac.u_ryukyu.phys.lib.Vec2;
import android.graphics.Canvas;
import android.graphics.Color;



class Charges {
	int hankei=20;
	public void setHankei(int h) { hankei=h;}
	protected ArrayList<Charge> chargeList;
    Charges() {
    	chargeList=new ArrayList<Charge>();
    }

    void clear() {
    	if( chargeList.size() > 0 ) {
    		chargeList.clear();
    	}
    }

    public Charge addNew(float xx,float yy,int qq)
    {
    	int cc;
    	if( qq>0) {
    		cc=Color.RED;
    	} else {
    		cc=Color.BLACK;
    	}
    	Charge q=new Charge(qq,hankei,cc,new Vec2(xx,yy));
    	chargeList.add(q);
    	return q;    	
    }

    public int count() { 
    	return chargeList.size();
    }

    public Vec2 Pos(int i) {
    	return chargeList.get(i).Pos();
    }
    public float X(int i) {
        return chargeList.get(i).X();
    }
    public float Y(int i) {
        return chargeList.get(i).Y();
    }
    public int Q(int i) {
        return chargeList.get(i).Q();
    }

    public int totalQ() {
    	int q=0;
    	int i;
        for( i=0 ; i<chargeList.size() ;i++ ) {
        	q += chargeList.get(i).Q();
        }
        return q;
    }

    public float V(Vec2 p1) {
        float V=1.0f;
        int i;
        for( i=0 ; i<chargeList.size() ; i++ ) {
        	Vec2 p=chargeList.get(i).Pos();
        	if( Q(i) >0 ) {
        		V *= (p.Diff(p1)).NormSquare();
            } else {
            	V /= (p.Diff(p1)).NormSquare();
            }
        }
        return((float)(-Math.log(V)));
    }


    public float VV(Vec2 p1) {
        float V=0.0f;
        int i;
        for( i=0 ; i<chargeList.size() ; i++ ) {
        	V += chargeList.get(i).V(p1);
        }
        return(V);
    }

  

    public double Qangle(double xx,double yy)
    {
        double angle1;
        int i;
        int n=chargeList.size();
    
        if( n==0 ) {
            return 0.0;
        }
        double k1=0.0;

        for( i=0 ; i<n ; i++ ) {
        	angle1=Math.atan2(xx-X(i),yy-Y(i));
            k1 += Q(i)*angle1;
        }

        return(k1);
    } 


    public void drawMark(Canvas gc,int i) {
    	chargeList.get(i).write(gc);
    }

    public void drawMarks(Canvas gc) {
    	int i;
    	for(i=0; i<chargeList.size() ; i++ ) {
    		drawMark(gc,i);
    	}
    }

    public boolean isNear(int x1,int y1) {
    	int i;
    	for( i=0 ; i<chargeList.size() ;i++ ) {
    		float xx=chargeList.get(i).X();
    		float yy=chargeList.get(i).Y();

    		if( (xx-x1)*(xx-x1)+(yy-y1)*(yy-y1) <= hankei*hankei ) {
    			return true;
    		}
    	}
    	return false;
    }

    public boolean isNear(int x1,int y1,int i) {
    	float xx=chargeList.get(i).X();
    	float yy=chargeList.get(i).Y();
		return (xx - x1) * (xx - x1) + (yy - y1) * (yy - y1) <= hankei * hankei;
	}
    public void del(int i) {
    	chargeList.remove(i);
    }
    public void del(Charge q) {
    	int i;
    	for(i=0; i<chargeList.size(); i++) {
    		if( q==chargeList.get(i)) {
    			del(i);
    		}
    	}
    }

	public void allErase() {
		chargeList.clear();
	}

	public float V0(int i) {
		return chargeList.get(i).V0();
	}
	public float V1(int i) {
		return chargeList.get(i).V1();
	}
	public float VA(int i) {
		return chargeList.get(i).VA();
	}
	
	public float R() {
		// TODO Auto-generated method stub
		return hankei;
	}
}