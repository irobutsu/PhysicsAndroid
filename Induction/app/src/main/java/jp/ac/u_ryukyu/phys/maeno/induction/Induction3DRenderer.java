package jp.ac.u_ryukyu.phys.maeno.induction;


import javax.microedition.khronos.opengles.GL10;

import jp.ac.u_ryukyu.phys.maeno.physlib.Vec3;
import jp.ac.u_ryukyu.phys.maeno.physlib3d.Cone;
import jp.ac.u_ryukyu.phys.maeno.physlib3d.Cylinder;
import jp.ac.u_ryukyu.phys.maeno.physlib3d.DraggableRenderer;
import jp.ac.u_ryukyu.phys.maeno.physlib3d.Matrix3x3;
import jp.ac.u_ryukyu.phys.maeno.physlib3d.Torus;
import jp.ac.u_ryukyu.phys.maeno.physlib3d.Yajirushi3DFixedR;
import jp.ac.u_ryukyu.phys.maeno.physlib3d.Yajirushi3DScaledR;

public class Induction3DRenderer extends DraggableRenderer {
	private float Cz=0;
	private float Mz=0;
	private float Cv=0;
	private float Mv=0;
	private final float  R=1f;
	private final int NUM=6;
	private final float HALFNUM=2.5f;
	private final int INUM=4;
	private final float IHALFNUM=1.5f;

	private final float magnetR=0.2f;
	private final float magnetL=1f;

	private Cylinder magnetN=new Cylinder(magnetR,magnetL,6,1f,0f,0f,1f);
	private Cylinder magnetS=new Cylinder(magnetR,magnetL,6,0f,0f,1f,1f);
	private Torus coil =new Torus(R,0.02f,40,10,1f,1f,1f,1f);
	private Yajirushi3DScaledR[] vecj=new Yajirushi3DScaledR[NUM];
	private Yajirushi3DScaledR[][][] vecB = new Yajirushi3DScaledR[NUM][NUM][NUM];
	private Yajirushi3DScaledR[][] vecBI = new Yajirushi3DScaledR[INUM][INUM];


	public Induction3DRenderer() {
		super(0f,(float)(Math.PI/4));
		int i,j,k;
		for(i=0;i<INUM ; i++) {
			for (j = 0; j < INUM; j++) {
				vecBI[i][j] = new Yajirushi3DScaledR(0.5f, 8, 1f, 1f, 0f, 0.3f, true);

			}
		}
		for(i=0; i<NUM; i++) {
			vecj[i]=new Yajirushi3DScaledR(0.4f,8,1f,1f,1f,0.5f);
			for(j=0; j<NUM; j++) {

				for(k=0; k<NUM; k++ ) {
					vecB[i][j][k]=new Yajirushi3DScaledR(0.5f,8,0f,1f,1f,0.3f,true);
					vecB[i][j][k].setPos(new Vec3((i-HALFNUM),(j-HALFNUM),(k-HALFNUM)));
				}
			}
		}
		setTranslationDefault(new Vec3(0f,0.5f,-5f));
		float cos=(float)Math.cos(1.0);
		float sin=(float)Math.sin(1.0);
		setRotationDefault(new Matrix3x3(1f,0f,0f,0f,cos,sin,0f,-sin,cos));
	}
	public void setZ(float mz,float cz,float mv,float cv) {
		Cz=cz;
		Mz=mz;
		Cv=cv;
		Mv=mv;
	}
	double I; // コイルに流れている電流
	public void setI(double i) {I=i;}
	public void calcB() {
		int i,j,k;
		for(i=0; i<INUM; i++) {
			float x=i-IHALFNUM;
			for(j=0; j<INUM; j++) {
				float y=j-IHALFNUM;
				double BIz=0;

				int m;
				for(m=0; m<10 ; m++ ) {
					double cos = Math.cos(0.2*Math.PI*m);
					double sin = Math.sin(0.2*Math.PI*m);
					double IPx=x-R*cos;
					double IPy=y-R*sin;

					double r3=Math.pow(IPx*IPx+IPy*IPy,-1.5);
					// 電流のいる場所から、今考えている場所までのシフトベクトル（IPx,IPy,0)
					// I=1;
					double Ix=I*sin;
					double Iy=-I*cos;
					BIz += 0.03*(Ix*IPy-Iy*IPx)*r3;
				}
				if( BIz<0 ) {
					BBI[i][j] = (float)(-BIz);
					BITheta[i][j] = (float) Math.PI;
					BIPhi[i][j] = 0f;
				} else {
					BBI[i][j]=(float)BIz;
					BITheta[i][j]=0f;
					BIPhi[i][j]=0f;
				}

			}
		}
		for(i=0; i<NUM; i++) {
			float x=i-HALFNUM;
			for (j = 0; j < NUM; j++) {
				float y=j-HALFNUM;

				for (k = 0; k < NUM; k++) {
					double BI;
					double Bx;
					double By;
					double Bz;
					float z=k-HALFNUM;



					//if( x<magnetR && x>-magnetR && y<magnetR && y>-magnetR &&
					//		 z<Mz+magnetL && z>Mz-magnetL) {

					//} else {
					double zz=z-Mz-magnetL;
					double xxyy=x*x+y*y;
					double rr = xxyy + zz* zz;
					double r = Math.sqrt(rr);
					double B = 0.6f / rr;
					Bx = (B * x) / r;
					By = (B * y) / r;
					Bz = (B * zz) / r;

					zz=z-Mz+magnetL;
					rr = xxyy + zz * zz;
					r = Math.sqrt(rr);
					B=0.6f/rr;
					Bx -= (B * x) / r;
					By -= (B * y) / r;
					Bz -= (B * zz) / r;

					B=Math.sqrt(Bx*Bx+By*By+Bz*Bz);


					BB[i][j][k]=((float) B);
					BTheta[i][j][k]=(float) Math.acos(Bz / B);
					BPhi[i][j][k]=(float) Math.atan2(By, Bx);

				}
			}
		}
	}
	float BB[][][]=new float[NUM][NUM][NUM];
	float BTheta[][][]=new float[NUM][NUM][NUM];
	float BPhi[][][]=new float[NUM][NUM][NUM];
	float BBI[][]=new float[INUM][INUM];
	float BITheta[][]=new float[INUM][INUM];
	float BIPhi[][]=new float[INUM][INUM];
	@Override
	public void drawContent(GL10 gl) {
		if( movingMode ) {
			coil.changeColor(1f,0f,0f,1f);
		} else {
			coil.changeColor(1f,1f,1f,1f);
		}
		coil.setPos(new Vec3(0f,0f,Cz));
		coil.draw(gl);
		magnetN.setPos(new Vec3(0f, 0f, Mz));
		magnetS.setPos(new Vec3(0f, 0f, Mz - magnetL));
		magnetN.draw(gl);
		magnetS.draw(gl);

		int i,j,k;

		for(i=0; i<NUM; i++) {
			double theta= ((2.0 * Math.PI) / NUM) * i;
			float sin = (float) Math.sin(theta);
			float cos = (float) Math.cos(theta);



			vecj[i].setPos(new Vec3(-sin, cos, Cz));
			if( I>0) {
				vecj[i].setLength((float)I);
				vecj[i].setThetaPhi((float)(Math.PI*0.5),(float)(theta));
			} else {
				vecj[i].setLength((float)(-I));
				vecj[i].setThetaPhi((float) (Math.PI * 0.5), (float) (theta + Math.PI));
			}
			vecj[i].draw(gl);
		}



		for(i=0; i<INUM; i++) {
			for (j = 0; j < INUM; j++) {
				vecBI[i][j].setPos(new Vec3((i - IHALFNUM), (j - IHALFNUM), Cz));
				vecBI[i][j].setLength(BBI[i][j]);
				vecBI[i][j].setThetaPhi(BITheta[i][j], BIPhi[i][j]);
				vecBI[i][j].draw(gl);
			}
		}
		for(i=0; i<NUM; i++) {
			for (j = 0; j < NUM; j++) {
				for (k = 0; k < NUM; k++) {
					vecB[i][j][k].setLength(BB[i][j][k]);
					vecB[i][j][k].setThetaPhi(BTheta[i][j][k], BPhi[i][j][k]);
					vecB[i][j][k].draw(gl);

				}
			}
		}
	}
}
