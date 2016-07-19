package jp.ac.u_ryukyu.phys.emfromline;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.util.FloatMath;
import jp.ac.u_ryukyu.phys.lib.Vec3;
import jp.ac.u_ryukyu.phys.tool3D.Cylinder;
import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.Yajirushi3DFixedR;
import jp.ac.u_ryukyu.phys.tool3D.Yajirushi3DScaledR;

public class EMRenderer extends DraggableRenderer {
	final int gridSize=10;
	final int gridzSize=1;
	// 
	protected float t;
	Cylinder Dousen;
	Yajirushi3DFixedR I;
	Yajirushi3DScaledR vecE[][];
	Yajirushi3DScaledR vecB[][];

	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig cnf) {
		super.onSurfaceCreated(gl, cnf);
		makeworld();
	}

	protected void makeworld() {
		Dousen=new Cylinder(0.12f,gridSize,8,1f,1f,1f,0.4f);
		I=new Yajirushi3DFixedR(0.1f,0.1f,8,1f,1f,1f,1f);
		Dousen.setPos(new Vec3(0f,0f,-0.5f*(gridSize)));

		vecE=new Yajirushi3DScaledR[gridSize][gridSize];
		vecB=new Yajirushi3DScaledR[gridSize][gridSize];
	}
	boolean timerFlg=true;

	@Override
	protected void drawContent(GL10 gl) {
		int i, j, k;
		if( timerFlg ) {
			t += 0.05f;
		}
		float Len = -0.5f*FloatMath.sin(t);
		I=new Yajirushi3DFixedR(Len*5f,0.1f,8,0f,1f,1f,1f,true);


		float midPoint=0.5f*(gridSize);
		float midzPoint=0.5f*(gridzSize);


		I.draw(gl);

		if( inftyFlg ) {
			for (i = 0; i < gridSize ; i++) {
				float x = i - midPoint + 0.5f;
				for (j = 0; j < gridSize ; j++) {
					float y = j - midPoint + 0.5f;
					vecB[i][j] = new Yajirushi3DScaledR((float)(Math.abs(Len)/Math.sqrt(x * x + y * y)), 8, 0f, 0f, 1f, 1f,true);
					vecB[i][j].setThetaPhi((float) (0.5 * Math.PI), (float) (Math.atan2(x, -y) + (Len < 0 ? Math.PI : 0.0)));
					for (k = 0; k < gridzSize; k++) {
						float z = k - midzPoint + 0.5f;
						vecB[i][j].setPos(new Vec3(x, y, z));
						vecB[i][j].draw(gl);
					}
				}
			}
		} else {
			for (i = 0; i < gridSize ; i++) {
				float x = i - midPoint + 0.5f;
				for (j = 0; j < gridSize ; j++) {
					float y = j - midPoint + 0.5f;
					float r=(float)Math.sqrt(x*x+y*y);
					Len=-FloatMath.cos(t - 0.5f*r);
					vecB[i][j] = new Yajirushi3DScaledR((float)(Math.abs(Len)/r), 8, 0f, 0f, 1f, 1f,true);
					vecB[i][j].setThetaPhi((float) (0.5 * Math.PI), (float) (Math.atan2(x, -y) + (Len < 0 ? Math.PI : 0.0)));
					for (k = 0; k < gridzSize ; k++) {
						float z = k - midzPoint + 0.5f;
						vecB[i][j].setPos(new Vec3(x, y, z));
						vecB[i][j].draw(gl);
					}
					float xx =i-midPoint;
					float yy =j-midPoint;
					r=(float)Math.sqrt(xx*xx+yy*yy);
					if( r >0.001f ) {
						Len = -0.5f*FloatMath.sin(t - 0.5f*r);
						vecE[i][j] = new Yajirushi3DScaledR((float) (Math.abs(Len) / r), 8, 1f, 0f, 0f, 1f, true);
						vecE[i][j].setThetaPhi((float) (Len < 0 ? Math.PI : 0.0), 0f);
						for (k = 0; k < gridzSize; k++) {
							float z = k - midzPoint + 0.5f;
							vecE[i][j].setPos(new Vec3(xx, yy, z));
							vecE[i][j].draw(gl);
						}
					}
				}
			}
		}
		Dousen.draw(gl);
	}
	boolean rotBFlg=false;
	boolean inftyFlg =true;
	
	public void setT(float t2) {
		t=t2;		
	}
	
	public void setDelM(int i) {
		makeworld();
	}

	public void setInftyFlg(boolean checked) {
		inftyFlg =checked;
	}
	public void setrotBFlg(boolean checked) {
		rotBFlg=checked;
	}

	public void timerStop() {
		timerFlg=false;
	}

	public void timerStart() {
		timerFlg=true;
	}
}
