var DraggablePoint=function(xx,yy,rr,c) {
	this.x=xx; this.y=yy;
	this.r=rr; this.col=c;
}
DraggablePoint.prototype={
	nowDrag:false,
	canDrag:true,
	ID:-1,
	in:function(xx,yy) {
		if( this.canDrag ) {
			return this.r*this.r > (xx-this.x)*(xx-this.x) +(yy-this.y)*(yy-this.y);
		} else {
			return false;
		}
	},
	setNowDrag:function() { this.nowDrag=true;},
	resetNowDrag:function() { this.nowDrag=false;},
	setID:function(j) { this.ID=j;},
	// x,y座標をセットする。軸上など、場所が特定される場合は、この関数をオーバーライドする。
	setXY:function(xx,yy) {
		this.y=yy;
		this.x=xx;
	},
	// ●で書くのをスタブにしておく↓
	write:function(ct) {
		ct.fillStyle=this.col;
		ct.beginPath();
		ct.arc(this.x,this.y,this.r,0,2*Math.PI,false);
		ct.fill();
	},
	getID:function() { return this.ID;},
	disableDrag:function() { this.canDrag=false;}
}
