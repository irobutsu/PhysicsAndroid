package jp.ac.u_ryukyu.phys.lib;

import android.util.FloatMath;

public class Vec3 {
	private float x,y,z;

	public static final Vec3 zero=new Vec3(0f,0f,0f);
	public Vec3(float xx,float yy,float zz){ x=xx; y=yy; z=zz;}
	
	public Vec3(Vec3 vec3) {
		x=vec3.x;
		y=vec3.y;
		z=vec3.z;
	}

	// add,sub,mul,divは「動詞」であり、クラスの変数を操作する。
	public void add(Vec3 b) {
		x += b.x;
		y += b.y;
		z += b.z;
	}
	public void sub(Vec3 b) {
		x -= b.x;
		y -= b.y;
		z -= b.z;
	}
	public void mul(float b) {
		x *= b;
		y *= b;
		z *= b;
	}
	public void div(float b) {
		x /= b;
		y /= b;
		z /= b;
	}
	
	public void normalize(){
		float r=Norm();
		if( r!=0f) {
			x /=r;
			y /=r;
			z /=r;
		}
	}
	
	public void rotx(float th) {
		float tmp=z;
		float costh=FloatMath.cos(th);
		float sinth=FloatMath.sin(th);
		z=(z*costh+y*sinth);
		y=(y*costh-tmp*sinth);
	}
	public void roty(float th) {
		float tmp=x;
		float costh=FloatMath.cos(th);
		float sinth=FloatMath.sin(th);
		x=(x*costh+z*sinth);
		z=(z*costh-tmp*sinth);
	}
	public void rotz(float th) {
		float tmp=y;
		float costh=FloatMath.cos(th);
		float sinth=FloatMath.sin(th);
		y=(y*costh+x*sinth);
		x=(x*costh-tmp*sinth);
	}
	
	// sum,diff,prod,quotは「名詞」であり、クラス変数から作られた別のベクトルを返す。
	public Vec3 Sum(Vec3 b) {
		return new Vec3(x+b.x,y+b.y,z+b.z);
	}
	public Vec3 Sum(Vec3 b,Vec3 c) {
		return new Vec3(x+b.x+c.x,y+b.y+c.y,z+b.z+c.z);
	}
	public Vec3 Sum(Vec3 b,Vec3 c,Vec3 d) { 
		return new Vec3(x+b.x+c.x+d.x,y+b.y+c.y+d.y,z+b.z+c.z+d.z);
	}
	public Vec3 Diff(Vec3 b) {
		return new Vec3(x-b.x,y-b.y,z-b.z);
	}
	public Vec3 Cross(Vec3 b) {
		return new Vec3(y*b.z-z*b.y, z*b.x-x*b.z, x*b.y-y*b.x);
	}

	public float Dot(Vec3 b) {
		return x*b.x+y*b.y +z*b.z;
	}
	public Vec3 Prod(float a) {
		return new Vec3(a*x,a*y,a*z);
	}
	public Vec3 Quot(float a) {
		return new Vec3(x/a,y/a,z/a);
	}

	public float NormSquare() {
		return x*x+y*y+z*z;
	}
	public float Norm() {
		return (float)(Math.sqrt(x*x+y*y+z*z));
	}
	public float X() { return x;}
	public float Y() { return y;}
	public float Z() { return z;}
	public void setX(float a){x=a;}
	public void setY(float a){y=a;}
	public void setZ(float a){z=a;}
	
	
	public float Theta() {
		return (float)Math.atan2(Math.sqrt(x*x+y*y),z);
	}
	public float Phi(){
		return (float)Math.atan2(y, x);
	}
	
	// a  b
	// c
	// という３つの位置ベクトルから法線ベクトルを作る。
	static public Vec3 Normal(Vec3 a,Vec3 b,Vec3 c) {
		Vec3 ab=b.Diff(a);
		Vec3 ac=c.Diff(a);
		
		return ab.Cross(ac);
	}
	static public Vec3 NormalizedNormal(Vec3 a,Vec3 b,Vec3 c) {
		Vec3 d=Normal(a,b,c);
		d.normalize();
		return d;
	}

	public void rotate(float f,Vec3 v) {
		float theta=v.Theta();
		float phi=v.Phi();
		
		rotz(-phi);
		roty(-theta); // これで回転軸がzになる。
		
		rotz(f);
		
		roty(theta);
		rotz(phi); // 回転軸を戻す。
	}
	public void rotate(Vec3 vec3) {
		rotate(vec3.Norm(),vec3);
	}

}
