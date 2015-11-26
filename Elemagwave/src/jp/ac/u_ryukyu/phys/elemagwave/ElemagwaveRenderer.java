package jp.ac.u_ryukyu.phys.elemagwave;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.util.FloatMath;
import jp.ac.u_ryukyu.phys.lib.Vec3;
import jp.ac.u_ryukyu.phys.tool3D.DraggableRenderer;
import jp.ac.u_ryukyu.phys.tool3D.Yajirushi3DFixedR;

public class ElemagwaveRenderer extends DraggableRenderer {
	final int gridSize=20;
	// 
	protected float t;
	Yajirushi3DFixedR vecE[];
	Yajirushi3DFixedR vecB[];
	RotVec rotE[];
	RotVec rotB[];
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig cnf) {
		super.onSurfaceCreated(gl, cnf);
		makeworld();
	}

	protected void makeworld() {
		vecE=new Yajirushi3DFixedR[gridSize];
		vecB=new Yajirushi3DFixedR[gridSize];
		rotE=new RotVec[gridSize];
		rotB=new RotVec[gridSize];
	}
	boolean timerFlg=true;

	@Override
	protected void drawContent(GL10 gl) {
		if( timerFlg ) {
			t += 0.05f;
		}
		float midPoint=0.5f*(gridSize-1);
		int i,j,k;
		for(i=0; i<gridSize-1 ; i++) {
			float x=i-midPoint+0.5f;
			float z=0f;
			float y=0f;
			float Len=3f*FloatMath.sin(0.5f*x-t);
			if( Len > 0) {
				vecE[i]=new Yajirushi3DFixedR(Len,0.1f,8,1f,0f,0f,1f);
				vecB[i]=new Yajirushi3DFixedR(Len,0.1f,8,0f,0f,1f,1f);
				vecB[i].setThetaPhi((float)(Math.PI*0.5),(float)(-Math.PI*0.5));
			} else {
				vecE[i]=new Yajirushi3DFixedR(-Len,0.1f,8,1f,0f,0f,1f);
				vecB[i]=new Yajirushi3DFixedR(-Len,0.1f,8,0f,0f,1f,1f);
				vecE[i].setThetaPhi((float)Math.PI,0f);
				vecB[i].setThetaPhi((float)(Math.PI*0.5),(float)(Math.PI*0.5));
			}
			vecE[i].setPos(new Vec3(x,y,z));
			vecB[i].setPos(new Vec3(x,y,z));
			x += 0.5f;
			Len=-FloatMath.cos(0.5f*x-t);
			if( rotBFlg) {
				if(Len >0 ) {
					rotB[i]=new RotVec(Len, 0.05f, 9, 5, 0.5f, 0.5f, 1f, 1);
				} else {
					rotB[i]=new RotVec(-Len, 0.05f, 9, 5, 0.5f, 0.5f, 1f, 1f);
					rotB[i].setThetaPhi((float)(Math.PI),0f);
				}
				rotB[i].setPos(new Vec3(x,y,z));
			}
			if( rotEFlg) {
				if(Len >0 ) {
					rotE[i]=new RotVec(Len, 0.05f, 9, 5, 1f, 0.5f, 0.5f, 1);
					rotE[i].setAlphaThetaPhi((float)(0.5f*Math.PI),(float)(0.5*Math.PI),(float)(0.5f*Math.PI));
				} else {
					rotE[i]=new RotVec(-Len, 0.05f, 9, 5, 1f, 0.5f, 0.5f, 1f);
					rotE[i].setAlphaThetaPhi((float)(0.5f*Math.PI),(float)(0.5*Math.PI),(float)(-0.5*Math.PI));
				}
				rotE[i].setPos(new Vec3(x,y,z));
			}
		}
		for(i=0; i<gridSize-1; i++) {
				vecE[i].draw(gl);	
				vecB[i].draw(gl);
				if( rotBFlg ) {
					if( rotB[i] != null) {
						rotB[i].draw(gl);
					}
				}
				if( rotEFlg ) {
					if( rotE[i] != null ) {
					rotE[i].draw(gl);
					}
				}
		}
	}
	boolean rotBFlg=false;
	boolean rotEFlg=false;
	
	public void setT(float t2) {
		t=t2;		
	}
	
	public void setDelM(int i) {
		makeworld();
	}

	public void setrotEFlg(boolean checked) {
		rotEFlg=checked;
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
