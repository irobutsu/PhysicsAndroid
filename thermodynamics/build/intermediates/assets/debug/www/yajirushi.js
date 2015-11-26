// 矢印のコンストラクタ。色と長さの初期値をセットするだけ。
var Yajirushi=function(cl) {
	this.color=cl;
	this.len=1;
	this.p=new Vector(0,0); // .pは根元の位置。
	this.v=new Vector(0,1); // .vは矢印の向きのベクトル
	this.theta=0; // θ=0は真下（y軸向き）。
}
// Yajirushiの使い方
// var y=new Yajirushi("rgb(255,0,0)");のようにして色決め。
// y.setXY(x,y);で根元を指定。
// y.setL(l);で長さ指定。
// y.setTheta(angle);で向きを指定。angleはx軸との角度。
// y.setV(v); または y.setVXY(vx,vy);で向きと長さをベクトル指定もできる。
// y.draw();で描く。
Yajirushi.prototype={	
	setXY:function(xx,yy) {this.p.x=xx; this.p.y=yy;},
	setL:function(l) { this.len=l; this.v.x=l*Math.cos(this.theta); this.v.y=l*Math.sin(this.theta);},
	setTheta:function(th) {this.theta=th; this.v.x=this.len*Math.cos(th); this.v.y=this.len*Math.sin(th);},
	setP:function(V) {
		this.setXY(V.x,V.y);
	},
	setV:function(V) {
		this.setVXY(V.x,V.y);
	},
	setVXY:function(vx,vy) {
		this.v.x=vx; this.v.y=vy;
		this.len=Math.sqrt(vy*vy+vx*vx);
		this.theta=Math.atan2(-vx,vy);
	},	
	setColor:function(c){ this.color=c;},
	draw:function(ct) {
		//ct.strokeStyle="rgb(0,0,0)";
		ct.fillStyle=this.color;
		ct.save();
		
		ct.translate(this.p.x,this.p.y);
		ct.rotate(this.theta);
		ct.beginPath();
		var ll=0.05*this.len;
		var lll=0.8*this.len;
		ct.moveTo(ll,0);
		ct.lineTo(-ll,0);
		ct.lineTo(-ll,lll);
		ct.lineTo(-2*ll,lll);
		ct.lineTo(0,this.len);
		ct.lineTo(2*ll,lll);
		ct.lineTo(ll,lll);
		ct.closePath();
		ct.fill();
		ct.restore();
	}
}
