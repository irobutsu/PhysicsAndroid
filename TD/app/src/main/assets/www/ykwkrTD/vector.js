// �������٥��ȥ�
var Vector=function(xx,yy) {
	this.x=xx;
	this.y=yy;
}
// C++�ʤɤθ���Ǥ�+-*/�ʤɤΡֱ黻�ҡפ�٥��ȥ��ʣ�ǿ������˺�����Ǥ���Τ�����
// javascript�ˤϤ����ޤǤε�ǽ�Ϥʤ��Τǡ��ؿ��ǽ񤯡�
Vector.prototype={
	copyFrom:function(V) {this.x=V.x; this.y=V.y;},
	// v.copyFrom(v2)�ǡ�v��v2�Υ��ԡ��Ȥʤ롣
	makeCopy:function() { return new Vector(this.x,this.y); },
	// v2=v.makeCopy()�ǡ�v��v2�Υ��ԡ��Ȥʤ롣
	setXY:function(xx,yy) { this.x=xx; this.y=yy;},
	// v.add(v1)�ǡ�v=v+v1
	add:function(V) {this.x += V.x; this.y += V.y;},
	// v.sub(v1)�ǡ�v=v-v1
	sub:function(V) {this.x -= V.x; this.y -= V.y;},
	// w=v.add(v1)�ǡ�w=v+v1
	sum:function(V) { return new Vector(this.x +V.x,this.y+V.y); },
	// w=v.diff(v1)�ǡ�w=v-v1
	diff:function(V) { return new Vector(this.x -V.x,this.y-V.y); },
	// v2=v.mul(a)�ǡ������ v2=v*a
	mul:function(a) { this.x *= a; this.y *=a; },
	// v2=v.div(a)�ǡ�����ǳ�ꡢv2=v/a
	div:function(a) { this.x /= a; this.y /=a; },
	// prod�ϥ����顼�ܤ��롣v.prod(a)�ǡ�v=v*a;
	prod:function(a) { return new Vector(this.x*a,this.y*a); },
	// irpod�����ѡ������֤���
	iprod:function(V) { return this.x*V.x+this.y*V.y;},
	// eprot�ϳ��ѡ��󼡸��ʤΤǤ��������֤���
	eprod:function(V) { return this.x*V.y-this.y*V.x;},
	// v2=v.quat(a)�ǡ�����ǳ�ä��٥��ȥ��������
	quot:function(a) { return new Vector(this.x/a,this.y/a); },
	// v.addVt(v2,t)�ǡ�v=v+v2*t
	addVt:function(V,t){ this.x += V.x*t,this.y += V.y*t; },
	length:function() {
		return Math.sqrt(this.x*this.x+this.y*this.y);
	}
};
