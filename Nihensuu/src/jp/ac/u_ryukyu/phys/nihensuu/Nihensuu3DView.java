package jp.ac.u_ryukyu.phys.nihensuu;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.widget.LinearLayout;
import jp.ac.u_ryukyu.phys.lib.Vec3;
import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.DraggableSurfaceView;
import jp.ac.u_ryukyu.phys.tool3D.Matrix3x3;

public class Nihensuu3DView extends DraggableSurfaceView {
	int cxy,cx2,cx1,cy2,cy1,c0;
	int w,h;
	float V[][];
	float fac;
	public Nihensuu3DView(Context context) {
		super(context);
		setTranslationDefault(new Vec3(-0.3f,-1f,-3f));
		renderer.setRotationDefault(new Matrix3x3(1,0,0,0,1,0,0,0,1));
		cx2=2;
		cx1=0;
		cy2=2;
		cy1=0;
		cxy=0;
		c0=0;
		fac=0.05f;
		w=100;
		h=100;
		V=new float[w][h];
		reDraw();
	}
	@Override
	protected void onSizeChanged(int ww1, int hh1, int oldw, int oldh) {
		super.onSizeChanged(ww1, hh1, oldw, oldh);
		int ww=((View)getParent()).getWidth();
		int hh=((View)getParent()).getHeight();
			this.setLayoutParams(new LinearLayout.LayoutParams(ww, hh));
	}
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		drawV(V,w,h);
	}
	public void drawV(float[][] v, int W, int H) {
		((PotentialRenderer) renderer).setPotential(v,W,H);
		((PotentialRenderer) renderer).go();
	}
	public void erase() {
		((PotentialRenderer) renderer).stop();		
	}
	@Override
	protected DraggableRenderer newRenderer() {
		return new PotentialRenderer();
	}
	public void setMode(int i) {
		((PotentialRenderer)renderer).setMode(i);
	}	
	public void calcFunc() {
		int i,j;
		for(i=0; i<w; i++) {
			for(j=0; j<h; j++) {
				V[i][j]=(
					 cx2*(i-w/2)*(i-w/2)
					+cy2*(j-h/2)*(j-h/2)
					+cxy*(i-w/2)*(j-h/2)
					)*fac*fac
					+(cx1*(i-w/2)+cy1*(j-h/2))*fac
					+c0;
			}
		}
	}
	public void reDraw() {
		calcFunc();
		drawV(V,w,h);
	}
	public void downx2() {
		if( cx2 > -4) {
			cx2--;
			reDraw();
		}
	}
	public void upx2() {
		if( cx2 < 4) {
			cx2++;
			reDraw();
		}
	}
	public void downx1() {
		if( cx1 > -4) {
			cx1--;
			reDraw();
		}
	}
	public void upx1() {
		if( cx1 < 4) {
			cx1++;
			reDraw();
		}
	}
	public void downy2() {
		if( cy2 > -4) {
			cy2--;
			reDraw();
		}
	}
	public void upy2() {
		if( cy2 < 4) {
			cy2++;
			reDraw();
		}
	}
	public void downy1() {
		if( cy1 > -4) {
			cy1--;
			reDraw();
		}
	}
	public void upy1() {
		if( cy1 < 4) {
			cy1++;
			reDraw();
		}
	}
	public void downxy() {
		if( cxy > -4) {
			cxy--;
			reDraw();
		}
	}
	public void upxy() {
		if( cxy < 4) {
			cxy++;
			reDraw();
		}
	}
	public void down0() {
		if( c0 > -4) {
			c0--;
			reDraw();
		}
	}
	public void up0() {
		if( c0 < 4) {
			c0++;
			reDraw();
		}
	}
	public String getC0String() {
		return Integer.toString(c0);
	}
	public String getCX1String() {
		return Integer.toString(cx1);
	}
	public String getCX2String() {
		return Integer.toString(cx2);
	}
	public String getCY1String() {
		return Integer.toString(cy1);
	}
	public String getCY2String() {
		return Integer.toString(cy2);
	}
	public String getCXYString() {
		return Integer.toString(cxy);
	}

}
