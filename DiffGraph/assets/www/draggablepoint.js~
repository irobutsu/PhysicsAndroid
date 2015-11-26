var DraggablePoint=function(xx,yy,rr,c) {
	this.x=xx; this.y=yy;
	this.r=rr; this.col=c;
	this.shiftx=0;
	this.shifty=0;
};
DraggablePoint.prototype={
	nowDrag:false,
	canDrag:true,
	ID:-1,
	inP:function(xx,yy) {
		if( this.canDrag ) {
			return this.r*this.r > (xx-this.x)*(xx-this.x) +(yy-this.y)*(yy-this.y);
		} else {
			return false;
		}
	},
	setNowDrag:function(x,y) { this.nowDrag=true; this.shiftx=x-this.x; this.shifty=y-this.y;},
	resetNowDrag:function() { this.nowDrag=false;},
	setID:function(j) { this.ID=j;},
	moveByDrag:function(xx,yy,x0,y0,x1,y1) {
		xx -= this.shiftx;
		yy -= this.shifty;
		this.setXY(xx,yy,x0,y0,x1,y1);
	},
	// x,y座標をセットする。軸上など、場所が特定される場合は、この関数をオーバーライドする。
	setXY:function(xx,yy,x0,y0,x1,y1) {
		if( xx < x0 ) {
			this.x=x0;
		} else if( xx > x1 ) {
			this.x=x1;
		} else {
			this.x=xx;
		}
		if( yy< y0 ) {
			this.y=y0;
		} else if( yy >y1 ) {
			this.y=y1;
		} else {
			this.y=yy;
		}
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
};
