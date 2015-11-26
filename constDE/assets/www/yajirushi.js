// ����Υ��󥹥ȥ饯��������Ĺ���ν���ͤ򥻥åȤ��������
var Yajirushi=function(cl) {
	this.color=cl;
	this.len=1;
	this.p=new Vector(0,0); // .p�Ϻ����ΰ��֡�
	this.v=new Vector(0,1); // .v������θ����Υ٥��ȥ�
	this.theta=0; // ��=0�Ͽ�����y�������ˡ�
}
// Yajirushi�λȤ���
// var y=new Yajirushi("rgb(255,0,0)");�Τ褦�ˤ��ƿ���ᡣ
// y.setXY(x,y);�Ǻ�������ꡣ
// y.setL(l);��Ĺ�����ꡣ
// y.setTheta(angle);�Ǹ�������ꡣangle��x���Ȥγ��١�
// y.setV(v); �ޤ��� y.setVXY(vx,vy);�Ǹ�����Ĺ����٥��ȥ�����Ǥ��롣
// y.draw();��������
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
