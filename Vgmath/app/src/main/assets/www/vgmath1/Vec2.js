var Vec2=function(xx,yy) {
	if( xx==undefined ) xx=0;
	if( yy==undefined ) yy=0;
	this.x=xx;
	this.y=yy;
}
Vec2.prototype={
	add:function(v) { this.x += v.x; this.y += v.y;},
	sub:function(v) { this.x -= v.x; this.y -= v.y;},
	copy:function(v) { this.x=v.x; this.y=v.y;},
	smultiply:function(a) { this.x *=a; this.y *=a;},
	normSquare:function() { return this.x*this.x+this.y*this.y;},
	norm:function() { return Math.sqrt(this.normSquare());},
	normalize:function() {
		var n=this.norm();
		this.x /= n; this.y /=n;
	},
};
function Vec2_parallelComponent(v,v1) {
	var v1n=new Vec2(v1.x,v1.y);
	v1n.normalize();
	var l=Vec2_dot(v,v1n);
	return new Vec2(v1n.x*l,v1n.y*l);
}

function Vec2_parallelComponentLength(v,v1) {
	var v1n=new Vec2(v1.x,v1.y);
	v1n.normalize();
	return Vec2_dot(v,v1n);
}

function Vec2_orthogonalComponent(v,v1) {
	var vp=Vec2_parallelComponent(v,v1);
	return new Vec2(v.x-vp.x,v.y-vp.y);
}
function Vec2_cross(v1,v2) {
	return v1.x*v2.y-v1.y*v2.x;
}
function Vec2_dot(v1,v2) {
	return v1.x*v2.x+v1.y*v2.y;
}
function Vec2_sproduct(v1,s) {
	return new Vec2(v1.x*s,v1.y*s);
}
function Vec2_sum(v1,v2) {
	return new Vec2(v1.x+v2.x,v1.y+v2.y);
}
function Vec2_diff(v1,v2) {
	return new Vec2(v1.x-v2.x,v1.y-v2.y);
}


