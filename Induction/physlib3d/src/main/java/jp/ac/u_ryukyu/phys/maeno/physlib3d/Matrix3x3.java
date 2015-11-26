package jp.ac.u_ryukyu.phys.maeno.physlib3d;

public class Matrix3x3 {
	float M11,M12,M13;
	float M21,M22,M23;
	float M31,M32,M33;
	
	
	protected Matrix3x3() {

	}
	
	public Matrix3x3(Matrix3x3 m) {
		M11=m.M11; M12=m.M12; M13=m.M13;
		M21=m.M21; M22=m.M22; M23=m.M23;
		M31=m.M31; M32=m.M32; M33=m.M33;
	}

	public Matrix3x3(float m11,float m12,float m13,float m21,float m22,float m23,float m31,float m32,float m33 ) {
		M11=m11; M12=m12; M13=m13;
		M21=m21; M22=m22; M23=m23;
		M31=m31; M32=m32; M33=m33;
	}
	public Matrix3x3(float phi) {
		float cos=(float)Math.cos(phi);
		float sin=(float)Math.sin(phi);
		M11=cos; M12=-sin;
		M21=sin; M22=cos;
		M13=M23=M31=M32=0f;
		M33=1f;
	}
	public Matrix3x3(float th,float ph) {
		float cost=(float)Math.cos(th);
		float sint=(float)Math.sin(th);
		float cosp=(float)Math.cos(ph);
		float sinp=(float)Math.sin(ph);

		M11=cost*cosp; M12=-sinp;   M13=sint*cosp;
		M21=cost*sinp; M22=cosp;    M23=sint*sinp;
		M31=-sint;     M32=0;       M33=cost;    
	}
	public Matrix3x3 Prod(Matrix3x3 b) {
		Matrix3x3 M=new Matrix3x3();

		M.M11=M11*b.M11+M12*b.M21+M13*b.M31;
		M.M12=M11*b.M12+M12*b.M22+M13*b.M32;
		M.M13=M11*b.M13+M12*b.M23+M13*b.M33;
		M.M21=M21*b.M11+M22*b.M21+M23*b.M31;
		M.M22=M21*b.M12+M22*b.M22+M23*b.M32;
		M.M23=M21*b.M13+M22*b.M23+M23*b.M33;
		M.M31=M31*b.M11+M32*b.M21+M33*b.M31;
		M.M32=M31*b.M12+M32*b.M22+M33*b.M32;
		M.M33=M31*b.M13+M32*b.M23+M33*b.M33;
		
		return M;
	}
	public void copyFrom(Matrix3x3 b) {
		M11=b.M11;
		M12=b.M12;
		M13=b.M13;
		M21=b.M21;
		M22=b.M22;
		M23=b.M23;
		M31=b.M31;
		M32=b.M32;
		M33=b.M33;
	}
	
	static final Matrix3x3 zeroMatrix=new Matrix3x3(0,0,0,0,0,0,0,0,0);
	static final Matrix3x3 unitMatrix=new Matrix3x3(1,0,0,0,1,0,0,0,1);
}
