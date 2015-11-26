package jp.ac.u_ryukyu.phys.bohr;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.util.FloatMath;

import jp.ac.u_ryukyu.phys.lib.Vec2;
import jp.ac.u_ryukyu.phys.lib.Vec3;
import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.PotentialGraph;
import jp.ac.u_ryukyu.phys.tool3D.Sphere;
import jp.ac.u_ryukyu.phys.tool3D.Torus;

public class BohrRenderer extends DraggableRenderer {
	Vec2 rotate=Vec2.zero;
	PotentialGraph potential;
	Sphere testCharge;
	boolean existTest=false;
	Sphere electron;
	FunctionGraph f1s;
	
	public BohrRenderer() {
		testCharge=new Sphere(0.1f, 15, 15, 0.5f,0.5f,0.5f,1f);
	}
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig cnf) {
		super.onSurfaceCreated(gl, cnf);
		makeworld();
		electron=new Sphere(0.1f, 10,10,0.5f,0.5f,0.5f,1f);
	}
	float V[][];
	int gridSize=60;
	// ボーア半径を3とする単位を使う。
	private boolean show1SFlg=true;
	private Torus torus1s;
	private Torus torus2s;
	private Torus torus3s;
	protected void makeworld() {
		int i,j;
		V=new float[gridSize][gridSize];
		center=gridSize*0.5f;
		for(i=0; i<gridSize ; i++ ) {
			for(j=0; j<gridSize ; j++ ) {
				float r2=(i-center)*(i-center)+(j-center)*(j-center);
				if( r2 != 0f ) {
					V[i][j]=V0/FloatMath.sqrt(r2);
				} else {
					V[i][j]=Float.NEGATIVE_INFINITY;
				}
			}
		}
		torus1s=new Torus(0.3f, 0.03f, 20,10,1f,1f,1f,1f);
		torus1s.setThetaPhiPts((float)(Math.PI*0.5),(float)(Math.PI*0.5));
		torus1s.translatePts(new Vec3(0f,0.002f*gridSize*V0/3f,0f));
		torus2s=new Torus(1.2f, 0.03f, 20,10,1f,1f,1f,1f);
		torus2s.setThetaPhiPts((float)(Math.PI*0.5),(float)(Math.PI*0.5));
		torus2s.translatePts(new Vec3(0f,0.002f*gridSize*V0/12f,0f));
		torus3s=new Torus(2.7f, 0.03f, 20,10,1f,1f,1f,1f);
		torus3s.setThetaPhiPts((float)(Math.PI*0.5),(float)(Math.PI*0.5));
		torus3s.translatePts(new Vec3(0f,0.002f*gridSize*V0/27f,0f));
		setPotential(V,gridSize,gridSize);
		for(i=0; i<gridSize ; i++ ) {
			for(j=0; j<gridSize ; j++ ) {
				float r2=(i-center)*(i-center)+(j-center)*(j-center);
				if( r2 != 0f ) {
					V[i][j]=(float) (-V0*Math.exp((double)(-FloatMath.sqrt(r2)/3f)));
				}
			}
		}
		f1s=new FunctionGraph(gridSize,gridSize,V,1);
	}
	float V0=-20f;
	float center;
	public void setTestChargePos(Vec3 p,int w,int h) {
		int mabiki=4;
		testCharge.setPos(new Vec3(
				0.05f*(p.X()-w/2)/mabiki,
				0.001f*h*p.Z()/mabiki,
				0.05f*(p.Y()-h/2)/mabiki
				));
	}
	public void setTestChargeExist(boolean p){
		existTest=p;
	}
	float v;
	private boolean show3SFlg=true;
	private boolean show2SFlg=true;
	private boolean show2PFlg=false;
	private boolean show3PFlg=false;
	private boolean show3DFlg=false;
	private boolean showFFlg=false;
	@Override
	public void drawContent(GL10 gl) {
		v+=0.5f;
		if(!stopFlg) {
			if( potential !=null) {
				potential.setMovingMode(movingMode);
				potential.draw(gl);
			}
			float x,y,z;
			Vec3 epos;
			if( show1SFlg ) {
				z=V0/3;
				x=3*FloatMath.cos(v);
				y=3*FloatMath.sin(v);
				epos=new Vec3(0.1f*x,0.002f*gridSize*z,0.1f*y);
				electron.setPos(epos);
				electron.draw(gl);
				if(showFFlg ) {
					f1s.draw(gl);
				}
				torus1s.draw(gl);
			}
			if( show2SFlg ) {
				z=V0/12;
				x=center+12*FloatMath.cos(v/2f);
				y=center+12*FloatMath.sin(v/2f);
				epos=new Vec3(0.1f*(x-gridSize/2),0.002f*gridSize*z,0.1f*(y-gridSize/2));
				electron.setPos(epos);
				electron.draw(gl);
				torus2s.draw(gl);
			}
			
			if( show3SFlg ) {
				z=V0/27;
				x=center+27*FloatMath.cos(v/3f);
				y=center+27*FloatMath.sin(v/3f);
				epos=new Vec3(0.1f*(x-gridSize/2),0.002f*gridSize*z,0.1f*(y-gridSize/2));
				electron.setPos(epos);
				electron.draw(gl);
				torus3s.draw(gl);
			}
		
		}
		if( existTest ) {
			testCharge.draw(gl);
		}
	}

	public void set1SFlg(boolean isChecked) {
		show1SFlg=isChecked;
	}
	public void set2SFlg(boolean isChecked) {
		show2SFlg=isChecked;
	}
	public void set3SFlg(boolean isChecked) {
		show3SFlg=isChecked;
	}
	public void set2PFlg(boolean isChecked) {
		show2PFlg=isChecked;
	}
	public void set3PFlg(boolean isChecked) {
		show3PFlg=isChecked;
	}
	public void set3DFlg(boolean isChecked) {
		show3DFlg=isChecked;
	}

	private boolean stopFlg;
	
	

	public void stop() {
		stopFlg=true;
	}

	public void setPotential(float[][] v, int w, int h) {
		potential=new PotentialGraph(w,h,v,1);
		potential.setBaseMode(baseMode);
	}

	public void go() {
		stopFlg=false;
	}
	int baseMode;
	public void setMode(int m){
		baseMode=m;
		potential.setBaseMode(m);
	}

	
}
