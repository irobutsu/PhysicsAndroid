package jp.ac.u_ryukyu.phys.nihensuu;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import jp.ac.u_ryukyu.phys.lib.Vec3;
import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.DraggableSurfaceView;
import jp.ac.u_ryukyu.phys.tool3D.Matrix3x3;

public class Nihensuu3DView extends DraggableSurfaceView {
	float cxy,cx2,cx1,cy2,cy1,c0;
	int w,h;
	float V[][];
	float fac;
	public Nihensuu3DView(Context context) {
		super(context);
		setTranslationDefault(new Vec3(-0.3f,-1f,-3f));
		renderer.setRotationDefault(new Matrix3x3(1,0,0,0,1,0,0,0,1));
		cx2=0;
		cx1=-0.5f;
		cy2=0;
		cy1=0.25f;
		cxy=0;
		c0=0;
		fac=0.1f;
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
		drawV(V, w, h);
	}
	public void drawV(float[][] v, int W, int H) {
		((PotentialRenderer) renderer).setPotential(v, W, H);
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
		for(i=0; i<h; i++) {
			for(j=0; j<w; j++) {
				V[i][j]=25f*(
						(
					 cx2*(j-w/2)*(j-w/2)
					+cy2*(i-h/2)*(i-h/2)
					+cxy*(j-w/2)*(i-h/2)
					)*4/(h*h)
					+(cx1*(j-w/2)+cy1*(i-h/2))*2/h
					+c0
				);
			}
		}
	}
	public void reDraw() {
		calcFunc();
		drawV(V,w,h);
	}
	public void downx2() {
		if( cx2 > -2) {
			cx2-=0.25f;
			reDraw();
		}
	}
	public void upx2() {
		if( cx2 < 2) {
			cx2+=0.25f;
			reDraw();
		}
	}
	public void downx1() {
		if( cx1 > -2) {
			cx1-=0.25f;
			reDraw();
		}
	}
	public void upx1() {
		if( cx1 < 2) {
			cx1+=0.25f;
			reDraw();
		}
	}
	public void downy2() {
		if( cy2 > -2) {
			cy2-=0.25f;
			reDraw();
		}
	}
	public void upy2() {
		if( cy2 < 2) {
			cy2+=0.25f;
			reDraw();
		}
	}
	public void downy1() {
		if( cy1 > -2) {
			cy1-=0.25f;
			reDraw();
		}
	}
	public void upy1() {
		if( cy1 < 2) {
			cy1+=0.25f;
			reDraw();
		}
	}
	public void downxy() {
		if( cxy > -2) {
			cxy-=0.25f;
			reDraw();
		}
	}
	public void upxy() {
		if( cxy < 2) {
			cxy+=0.25f;
			reDraw();
		}
	}
	public void down0() {
		if( c0 > -2) {
			c0-=0.25f;
			reDraw();
		}
	}
	public void up0() {
		if( c0 < 2) {
			c0+=0.25f;
			reDraw();
		}
	}
	public String getC0String() {
		return Float.toString(c0);
	}
	public String getCX1String() {
		return Float.toString(cx1);
	}
	public String getCX2String() {
		return Float.toString(cx2);
	}
	public String getCY1String() {
		return Float.toString(cy1);
	}
	public String getCY2String() {
		return Float.toString(cy2);
	}
	public String getCXYString() {
		return Float.toString(cxy);
	}
	public CharSequence getFString() {
		String s="";
		boolean plusflg=false;
		if( cx2 !=0 ) {
			if( cx2 == 1) {
				s += "x<sup>2</sup>";
			} else if (cx2 == -1 ) {
				s += "-x<sup>2</sup>";
			} else {
				s += Float.toString(cx2) + "x<sup>2</sup>";
			}
			plusflg=true;
		}
		if( cx1 !=0 ) {
			if( cx1 == 1 ) {
				if( plusflg ) {
					s += "+x";
				} else {
					s="x";
				}
			} else if( cx1==-1 ) {
				s += "-x";
			} else {
				if( plusflg && cx1 >0 ) {
					s += "+" + Float.toString(cx1) + "x";
				} else {
					s += Float.toString(cx1)+"x";
				}
			}
			plusflg=true;
		}
		if( cy2 !=0 ) {
			if( cy2 == 1 ) {
				if( plusflg ) {
					s += "+y<sup>2</sup>";
				} else {
					s="y<sup>2</sup>";
				}
			} else {
				if (cy2 == -1) {
					s += "-y<sup>2</sup>";
				} else {
					if (plusflg && cy2 > 0) {
						s += "+" + Float.toString(cy2) + "y<sup>2</sup>";
					} else {
						s += Float.toString(cy2) + "y<sup>2</sup>";
					}
				}
			}
			plusflg=true;
		}
		if( cy1 !=0 ) {
			if( cy1 == 1 ) {
				if( plusflg ) {
					s += "+y";
				} else {
					s="y";
				}
			} else if( cy1==-1 ) {
				s += "-y";
			} else {
				if (plusflg && cy1 > 0) {
					s += "+" + Float.toString(cy1)+"y";
				} else {
					s += Float.toString(cy1) + "y";
				}
			}
			plusflg=true;
		}
		if( cxy !=0 ) {
			if( cxy == 1 ) {
				if( plusflg ) {
					s += "+xy";
				} else {
					s="xy";
				}
			} else if( cxy==-1 ) {
				s += "-xy";
			} else {
				if( plusflg && cxy >0 ) {
					s += "+"+Float.toString(cxy) + "xy";
				}else {
					s += Float.toString(cxy) + "xy";
				}
			}
			plusflg=true;
		}
		if( c0 !=0 ) {
			if( c0 > 0 && plusflg ) {
					s += "+"+Float.toString(c0);
				} else {
					s += Float.toString(c0);
				}

			plusflg=true;
		}
		if( !plusflg ) {
			s="0";
		}
		CharSequence source = Html.fromHtml(s);
		return source;
	}

}
