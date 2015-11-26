package jp.ac.u_ryukyu.phys.maeno.physlib;


public class Vec2 {
	private float x,y;

	public static final Vec2 zero=new Vec2(0f,0f);
	public Vec2(float xx,float yy){ x=xx; y=yy;}
	
	public Vec2(Vec2 b) {
		x=b.x; y=b.y;
	}

	// add,sub,mul,div���������������������������������������������������������������
	public void add(Vec2 b) {
		x += b.x;
		y += b.y;
	}
	public void sub(Vec2 b) {
		x -= b.x;
		y -= b.y;
	}
	public void mul(float b) {
		x *= b;
		y *= b;
	}
	public void div(float b) {
		x /= b;
		y /= b;
	}
	
	public void normalize(){
		float r=Norm();
		x /=r;
		y /=r;
	}
	
	// ������������������
	public void Rot1() {
		float tmp=y;
		y=x;
		x=-tmp;
	}
	
	public void rot(float th) {
		float tmp=y;
		float costh= (float) Math.cos(th);
		float sinth= (float) Math.sin(th);
		y=(y*costh+x*sinth);
		x=(x*costh-tmp*sinth);
	}
	
	// sum,diff,prod,quot������������������������������������������������������������������������������������������
	public Vec2 Sum(Vec2 b) {
		return new Vec2(x+b.x,y+b.y);
	}
	public Vec2 Sum(Vec2 b,Vec2 c) {
		return new Vec2(x+b.x+c.x,y+b.y+c.y);
	}
	public Vec2 Sum(Vec2 b,Vec2 c,Vec2 d) {
		return new Vec2(x+b.x+c.x+d.x,y+b.y+c.y+d.y);
	}
	public Vec2 Diff(Vec2 b) {
		return new Vec2(x-b.x,y-b.y);
	}
	public float Cross(Vec2 b) {
		return x*b.y-y*b.x;
	}
	public float Dot(Vec2 b) {
		return x*b.x+y*b.y;
	}
	public Vec2 Prod(float a) {
		return new Vec2(a*x,a*y);
	}
	public Vec2 Quot(float a) {
		return new Vec2(x/a,y/a);
	}
	
	public float NormSquare() {
		return x*x+y*y;
	}
	public float Norm() {
		return (float)(Math.sqrt(x*x+y*y));
	}
	public float X() { return x;}
	public float Y() { return y;}
	public void setX(float a){x=a;}
	public void setY(float a){y=a;}

}
