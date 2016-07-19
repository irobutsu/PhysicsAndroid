// コネクタは二つのオブジェクトを繋ぐオブジェクト
var Connector=function(ps,m1,m2,c) {
	PhysObject.call(this,ps,0,0,c);
	if( ps!=undefined ) {
		ps.ndObjs.push(this);
	}
	this.m1=m1;
	this.m2=m2;
}

Connector.prototype=Object.create(NonDynamicalObject.prototype);
Connector.prototype.constructor=Connector;

Connector.prototype.draw=function() {
	this.ps.writeLine(this.m1.p.x,this.m1.p.y,this.m2.p.x,this.m2.p.y,this.col,2);
}
