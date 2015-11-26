// ２次元ベクトル
var Vector=function(xx,yy) {
	this.x=xx;
	this.y=yy;
}
// C++などの言語では+-*/などの「演算子」もベクトルや複素数向けに再定義できるのだが、
// javascriptにはそこまでの機能はないので、関数で書く。
Vector.prototype={
	copyFrom:function(V) {this.x=V.x; this.y=V.y;},
	// v.copyFrom(v2)で、vがv2のコピーとなる。
	makeCopy:function() { return new Vector(this.x,this.y); },
	// v2=v.makeCopy()で、vがv2のコピーとなる。
	setXY:function(xx,yy) { this.x=xx; this.y=yy;},
	// v.add(v1)で、v=v+v1
	add:function(V) {this.x += V.x; this.y += V.y;},
	// v.sub(v1)で、v=v-v1
	sub:function(V) {this.x -= V.x; this.y -= V.y;},
	// w=v.add(v1)で、w=v+v1
	sum:function(V) { return new Vector(this.x +V.x,this.y+V.y); },
	// w=v.diff(v1)で、w=v-v1
	diff:function(V) { return new Vector(this.x -V.x,this.y-V.y); },
	// v2=v.mul(a)で、定数倍 v2=v*a
	mul:function(a) { this.x *= a; this.y *=a; },
	// v2=v.div(a)で、定数で割り、v2=v/a
	div:function(a) { this.x /= a; this.y /=a; },
	// prodはスカラー倍する。v.prod(a)で、v=v*a;
	prod:function(a) { return new Vector(this.x*a,this.y*a); },
	// irpodは内積。数を返す。
	iprod:function(V) { return this.x*V.x+this.y*V.y;},
	// eprotは外積。二次元なのでこれも数を返す。
	eprod:function(V) { return this.x*V.y-this.y*V.x;},
	// v2=v.quat(a)で、定数で割ったベクトルを代入。
	quot:function(a) { return new Vector(this.x/a,this.y/a); },
	// v.addVt(v2,t)で、v=v+v2*t
	addVt:function(V,t){ this.x += V.x*t,this.y += V.y*t; },
	length:function() {
		return Math.sqrt(this.x*this.x+this.y*this.y);
	}
};
