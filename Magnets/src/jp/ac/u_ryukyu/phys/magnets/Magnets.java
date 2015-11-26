package jp.ac.u_ryukyu.phys.magnets;


import java.util.ArrayList;
import jp.ac.u_ryukyu.phys.lib.Vec2;
import android.graphics.Canvas;
import android.graphics.Color;



class Magnets {
	int hankei=20;
	public void setHankei(int h) {hankei=h;}
	protected ArrayList<Magnet> magnetList;
    Magnets() {
    	magnetList=new ArrayList<Magnet>();
    }

    void clear() {
    	if( magnetList.size() > 0 ) {
    		magnetList.clear();
    	}
    }

    public Magnet addNew(float xx,float yy)
    {
    	Magnet q=new Magnet(hankei,new Vec2(xx,yy),0f);
    	magnetList.add(q);
    	return q;    	
    }

    public int count() { 
    	return magnetList.size();
    }

    public Vec2 Pos(int i) {
    	return magnetList.get(i).Pos();
    }
    public float angle(int i) {
    	return magnetList.get(i).angle();
    }
    public float X(int i) {
        return magnetList.get(i).X();
    }
    public float Y(int i) {
        return magnetList.get(i).Y();
    }
   

    public void drawMark(Canvas gc,int i) {
    	magnetList.get(i).write(gc);
    }

    public void drawMarks(Canvas gc) {
    	int i;
    	for(i=0; i<magnetList.size() ; i++ ) {
    		drawMark(gc,i);
    	}
    }

    public boolean isNear(int x1,int y1) {
    	int i;
    	for( i=0 ; i<magnetList.size() ;i++ ) {
    		float xx=magnetList.get(i).X();
    		float yy=magnetList.get(i).Y();

    		if( (xx-x1)*(xx-x1)+(yy-y1)*(yy-y1) <= hankei*hankei ) {
    			return true;
    		}
    	}
    	return false;
    }

    public boolean isNear(int x1,int y1,int i) {
    	float xx=magnetList.get(i).X();
    	float yy=magnetList.get(i).Y();
		return (xx - x1) * (xx - x1) + (yy - y1) * (yy - y1) <= hankei * hankei;
	}
    public void del(int i) {
    	magnetList.remove(i);
    }
    public void del(Magnet q) {
    	int i;
    	for(i=0; i<magnetList.size(); i++) {
    		if( q==magnetList.get(i)) {
    			del(i);
    		}
    	}
    }

	public void allErase() {
		magnetList.clear();
	}

	public float V0(int i) {
		return magnetList.get(i).V0();
	}
	public float V1(int i) {
		return magnetList.get(i).V1();
	}
	public float VA(int i) {
		return magnetList.get(i).VA();
	}
	
	public float R() {
		// TODO Auto-generated method stub
		return hankei;
	}
}