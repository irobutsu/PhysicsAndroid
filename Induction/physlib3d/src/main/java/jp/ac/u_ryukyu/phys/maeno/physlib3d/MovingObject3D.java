package jp.ac.u_ryukyu.phys.maeno.physlib3d;


import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import jp.ac.u_ryukyu.phys.maeno.physlib.Vec3;

public abstract class MovingObject3D {
	// MovingObject3Dに属する物体は、描画前に以下のパラメータで動かされる。
	// これはいわば、カメラの位置合わせ。

	
	

	// 以下は、この物体自体位置や回転のパラメータ
//	protected FloatBuffer matrix;
	// protected FloatBuffer tmatrix;
	float matrix[]={
		1,0,0,0,
		0,1,0,0,
		0,0,1,0,
		0,0,0,1};
	// matrixは4×4の行列だが、
	
	
	// pos;
	// rotAng=0f;
	//Vec3 rotVec=new Vec3(0f,0f,1f);
	
	protected FloatBuffer mVertexBuffer;
	protected float vertices[];
	protected float rr,gg,bb,aa;
	public MovingObject3D(float r,float g,float b,float a) {
		rr=r; gg=g; bb=b; aa=a;
		// pos=new Vec3(0f,0f,0f);
		//	matrix=FloatBuffer.allocate(16);
	
		//tmatrix=FloatBuffer.allocate(16);
//		float unit[]={1,0,0,0,
//					  0,1,0,0,
//					  0,0,1,0,
//					  0,0,0,1};
		//matrix.put(unit);
	//	tmatrix.put(unit);
	}
	protected void preDraw(GL10 gl){
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glPushMatrix(); 
//		gl.glMultMatrixf(tmatrix);
		gl.glMultMatrixf(matrix,0);
		// 以下の二つの替りを行列にやらせる。
		// gl.glTranslatef(pos.X(),pos.Y(),pos.Z());
		// gl.glRotatef(rotAng, rotVec.X(), rotVec.Y(),rotVec.Z());
	}
	@SuppressWarnings("static-method")
	protected void postDraw(GL10 gl) {
		gl.glPopMatrix();
		gl.glMatrixMode(GL10.GL_PROJECTION);
	}
	abstract public void copyFromOrg();
	public void setPos(Vec3 p) {
//		pos=new Vec3(p);
		//matrix.put(12,p.X());
		//matrix.put(13,p.Y());
		//matrix.put(14,p.Z());
		matrix[12]=p.X();
		matrix[13]=p.Y();
		matrix[14]=p.Z();
	}
	
	public void setThetaPhi(float th,float ph) {
	//	rotVec=new Vec3(0f,1f,0f); // y軸向き単位ベクトル
	//	rotVec.rotz(ph);
	//	rotAng=(float)(th*180.0/Math.PI);
		float cost=(float)Math.cos(th);
		float sint=(float)Math.sin(th);
		float cosp=(float)Math.cos(ph);
		float sinp=(float)Math.sin(ph);
		// 回転の行列はこんな感じ
		// cosθcosφ  -sinφ sinθcosφ
		// cosθsinφ  cosφ  sinθsinφ
		//   -sinθ      0      cosθ
		// この行列は、
		// cosφ -sinφ 0        cosθ  0   sinθ
		// sinφ  cosφ 0    と   0     1    0           の積
		//   0     0    1       -sinθ  0   cosθ
		
		//matrix.put(0,cost*cosp);  matrix.put(4,-sinp); matrix.put(8,sint*cosp); // matrix.put(12,0);
		//matrix.put(1,cost*sinp);  matrix.put(5,cosp);  matrix.put(9,sint*sinp); // matrix.put(13,0);
		//matrix.put(2,-sint);      matrix.put(6,0);     matrix.put(10,cost);     // matrix.put(14,0);
		// matrix.put(3,0);          matrix.put(7,0);     matrix.put(11,0);         matrix.put(15,1);
		matrix[0]=cost*cosp;  matrix[4]=-sinp; matrix[8]=sint*cosp; // matrix.put(12,0);
		matrix[1]=cost*sinp;  matrix[5]=cosp;  matrix[9]=sint*sinp; // matrix[13,0);
		matrix[2]=-sint;      matrix[6]=0;     matrix[10]=cost;     // matrix.put(14,0);

	}
	public void setAlphaThetaPhi(float al,float th,float ph) {
		// 上のに、さらに最初に「z軸周りのひねりα」を加えた行列。
		float cosa=(float)Math.cos(al);
		float sina=(float)Math.sin(al);
		float cost=(float)Math.cos(th);
		float sint=(float)Math.sin(th);
		float cosp=(float)Math.cos(ph);
		float sinp=(float)Math.sin(ph);
		// この行列は、
		// cosφ -sinφ 0        cosθ  0   sinθ     cosα  -sinα  0
		// sinφ  cosφ 0    と   0     1    0    と  sinα   cosα  0   の積で、
		//   0     0    1       -sinθ  0   cosθ      0       0     1

		// cosθcosφcosα-sinφsinα  -cosθcosφsinα-sinφcosα  sinθcosφ
		// cosθsinφcosα-cosφsinα  -cosθsinφsinα-cosφcosα  sinθsinφ
		// -sinθcosα                 sinθsinα                   cosθ
		matrix[0]=cost*cosp*cosa-sinp*sina; matrix[4]=-cost*cosp*sina-sinp*cosa; matrix[8]=sint*cosp; // matrix[12,0);
		matrix[1]=cost*sinp*cosa+cosp*sina; matrix[5]=-cost*sinp*sina+cosp*cosa; matrix[9]=sint*sinp; // matrix[13,0);
		matrix[2]=-sint*cosa;               matrix[6]=sint*sina;                 matrix[10]=cost;     // matrix.put(14,0);
//		matrix.put(3,0);          matrix.put(7,0);     matrix.put(11,0);        // matrix.put(15,1);
		
	}
	
	public void setPts(Vec3 p) {
		copyFromOrg();
		translatePts(p);
	}
	abstract public void translatePts(Vec3 p);
	// translatePtsは、位置座標そのものを並進。以下も同様。
	abstract public void setThetaPts(float theta);
	abstract public void setPhiPts(float phi);
	abstract public void setThetaPhiPts(float theta,float phi);
	abstract public void drawHontai(GL10 gl);
	public void draw(GL10 gl) {
		preDraw(gl);
		drawHontai(gl);
		postDraw(gl);
	}
	public void changeColor(float r,float g,float b,float a) {
		rr=r;
		gg=g;
		bb=b;
		aa=a;
	}
}