// javascript フェルマーの原理の説明

var Hanshaten=function(xx,yy,rr,c) {
	this.y=0;
	DraggablePoint.call(this,xx,yy,rr,c); // 親クラスのコンストラクタを呼ぶ。DraggablePointはプロトタイプを使わなかったのでこれでいい。
	this.setXY=function(xx,yy) {
		// 反射点はy座標を動かす必要はないので上書き。
		this.x=xx;
	}
	this.write=function(ct) {
		ct.fillStyle=this.col;
		ct.beginPath();
		ct.moveTo(this.x,this.y+this.r);
		ct.lineTo(this.x+this.r,this.y);
		ct.lineTo(this.x,this.y-this.r);
		ct.lineTo(this.x-this.r,this.y);
		ct.closePath();
		ct.fill();
	}
}
// 親クラスタのプロトタイプを継承する。
Hanshaten.prototype=Object.create(DraggablePoint.prototype);
Hanshaten.prototype.constructor=Hanshaten;

var Touchakuten=function(xx,yy,rr,c,cc) {
	DraggablePoint.call(this,xx,yy,rr,c); // 親クラスのコンストラクタを呼ぶ。
	this.col2=cc;
	this.setXY=function(xx,yy) {
		if( yy<this.r ) {
			this.y=this.r;
		} else {
			this.y=yy;
		}
		this.x=xx;
	}
	this.write=function(ct) {
		ct.fillStyle=this.col;
		ct.beginPath();
		ct.moveTo(this.x-this.r,this.y-this.r);
		ct.lineTo(this.x-this.r,this.y+this.r);
		ct.lineTo(this.x+this.r,this.y+this.r);
		ct.lineTo(this.x+this.r,this.y-this.r);
		ct.closePath();
		ct.fill();
		// 対称点も書く。
		ct.fillStyle=this.col2;
		ct.beginPath();
		ct.moveTo(this.x-this.r,-this.y-this.r);
		ct.lineTo(this.x-this.r,-this.y+this.r);
		ct.lineTo(this.x+this.r,-this.y+this.r);
		ct.lineTo(this.x+this.r,-this.y-this.r);
		ct.closePath();
		ct.fill();
	};
}
Touchakuten.prototype=Object.create(DraggablePoint.prototype);
Touchakuten.prototype.constructor=Hanshaten;


var Touchakuten2=function(xx,yy,rr,c) {
	DraggablePoint.call(this,xx,yy,rr,c); // 親クラスのコンストラクタを呼ぶ。
	this.setXY=function(xx,yy) {
		if( yy>-this.r ) {
			this.y=-this.r;
		} else {
			this.y=yy;
		}
		this.x=xx;
	}
	this.write=function(ct) {
		ct.fillStyle=this.col;
		ct.beginPath();
		ct.moveTo(this.x-this.r,this.y-this.r);
		ct.lineTo(this.x-this.r,this.y+this.r);
		ct.lineTo(this.x+this.r,this.y+this.r);
		ct.lineTo(this.x+this.r,this.y-this.r);
		ct.closePath();
		ct.fill();
	};
}
Touchakuten2.prototype=Object.create(DraggablePoint.prototype);
Touchakuten2.prototype.constructor=Hanshaten;

var siten;
var touchakuten;
var hanshaten;
$(document).delegate('#page1','pageinit',function(e) {
    gs=new DragmanScreen("canvas1",10,10,0.5);
    gs.setLeft(-1);
    gs.setBottom(-5);
    gs.gobackOriginalSize();
    gs2=new GraphScreen("canvas2",12,20,0.3);
    gs2.setLeft(-1);
    gs2.setBottom(-2);
    gs2.gobackOriginalSize();
    gs2.setylabel("t.png");
    gs2.setxlabel("x.png");
    gs.writeContents=function() {
		graphWrite();
    }    
    gs2.writeContents=function() {
		graphWrite();
    }
	siten=new DraggablePoint(0,4,0.2,"rgba(255,0,0,1)");
	touchakuten=new Touchakuten(8,3,0.2,"rgba(0,0,255,1)","rgba(0,0,255,0.5)");
	hanshaten=new Hanshaten(3,0,0.2,"rgba(0,0,0,1)");
	gs.pushDragObjList(siten);
	gs.pushDragObjList(hanshaten);
	gs.pushDragObjList(touchakuten);
});
$(document).delegate('#page1','pageshow',function(e) {
    graphWrite();
});
function graphWrite() {
    graph1Write();
	graph2Write();
}

function graph2Write() {
    gs2.setScale()
    gs2.clearALL();	
    gs2.writeCoordinate();
    graph2WriteContent();
}
function graph2WriteContent() {
	gs2.setFunction(fin);
	gs2.writeFunction("rgba(200,200,0,1)");
	gs2.setFunction(fout);
	gs2.writeFunction("rgba(100,0,100,1)");
	gs2.setFunction(f);
	gs2.writeFunction("rgba(0,0,255,1)");
	gs2.writeLine(hanshaten.x,0,hanshaten.x,fin(hanshaten.x),"rgba(200,200,0,1)");
	gs2.writeLine(hanshaten.x,fin(hanshaten.x),hanshaten.x,f(hanshaten.x),"rgba(100,0,100,1)");
	gs2.ctx.fillStyle="rbga(0,0,0,1)";
	gs2.ctx.beginPath();
	gs2.ctx.moveTo(hanshaten.x+0.2,0);
	gs2.ctx.lineTo(hanshaten.x,0.2);
	gs2.ctx.lineTo(hanshaten.x-0.2,0);
	gs2.ctx.lineTo(hanshaten.x,-0.2);
	gs2.ctx.closePath();
	gs2.ctx.fill();
}
function f(x) {
	return fin(x) + fout(x);
}

function fin(x) {
	return(Math.sqrt((siten.x-x)*(siten.x-x)+(siten.y)*(siten.y)));
}
function fout(x) {
	return(Math.sqrt((touchakuten.x-x)*(touchakuten.x-x)+(touchakuten.y)*(touchakuten.y)));
}
function graph1Write() {
    gs.setScale();
    gs.clearALL();	
	graph1WriteContent();
}

function graph1WriteContent() {
	var ct=gs.ctx;
	gs.writeRect(10,-10,-10,0,"rgba(200,200,200,1)");
	hanshaten.write(ct);
	siten.write(ct);
	touchakuten.write(ct);
	gs.writeLine(hanshaten.x,hanshaten.y,siten.x,siten.y,"rgba(200,200,0,1)");
	gs.writeLine(hanshaten.x,hanshaten.y,touchakuten.x,touchakuten.y,"rgba(200,0,200,1)");
	gs.writeLine(hanshaten.x,hanshaten.y,touchakuten.x,-touchakuten.y,"rgba(200,0,200,0.3)");
}

var siten2;
var touchakuten2;
var nyushaten;
$(document).delegate('#page2','pageinit',function(e) {
    gs3=new DragmanScreen("canvas3",10,10,0.5);
    gs3.setLeft(-1);
    gs3.setBottom(-5);
    gs3.gobackOriginalSize();
    gs4=new GraphScreen("canvas4",15,30,0.25);
    gs4.setLeft(-1);
    gs4.setBottom(-2);
    gs4.gobackOriginalSize();
    gs4.setylabel("t.png");
    gs4.setxlabel("x.png");
    gs3.writeContents=function() {
		graphWrite2();
    }    
	gs4.writeContents=function() {
		graph4Write();
    }    
	siten2=new DraggablePoint(0,2,0.2,"rgba(255,0,0,1)");
	touchakuten2=new Touchakuten2(8,-4.5,0.2,"rgba(0,0,255,1)","rgba(0,0,255,0.5)");
	nyushaten=new Hanshaten(3,0,0.2,"rgba(0,0,0,1)");
	gs3.pushDragObjList(siten2);
	gs3.pushDragObjList(nyushaten);
	gs3.pushDragObjList(touchakuten2);
});
$(document).delegate('#page2','pageshow',function(e) {
    graphWrite2();
});
function graphWrite2() {
    graph3Write();
	graph4Write();
}

function graph4Write() {
    gs4.setScale()
    gs4.clearALL();	
    gs4.writeCoordinate();
    graph4WriteContent();
}
function graph4WriteContent() {
	gs4.setFunction(f2);
	gs4.writeFunction("rgba(0,0,255,1)");
	gs4.setFunction(f3);
	gs4.writeFunction("rgba(0,0,0,1)");
	var i=0;
	var top2=f2(nyushaten.x);
	var top3=f3(nyushaten.x);
	while( i+1< top3) {
		gs4.writeLine(nyushaten.x,i,nyushaten.x,i+0.5,"rgba(160,160,60,1)");
		gs4.writeLine(nyushaten.x,i+0.5,nyushaten.x,i+1,"rgba(160,60,160,1)");
		i+=1;
	}
	if( i+0.5 < top3 ) {
		gs4.writeLine(nyushaten.x,i,nyushaten.x,i+0.5,"rgba(160,160,60,1)");
		gs4.writeLine(nyushaten.x,i+0.5,nyushaten.x,top3,"rgba(160,60,160,1)");
		if( top2 > i+1 ) {
			gs4.writeLine(nyushaten.x,top3,nyushaten.x,i+1,"rgba(255,0,255,1)");
		} else {
			gs4.writeLine(nyushaten.x,top3,nyushaten.x,top2,"rgba(255,0,255,1)");
		}
	} else {
		gs4.writeLine(nyushaten.x,i,nyushaten.x,top3,"rgba(160,160,60,1)");
		if( top2 > i+0.5 ) {
			gs4.writeLine(nyushaten.x,top3,nyushaten.x,i+0.5,"rgba(255,255,0,1)");
			if( top2 > i+1 ) {
				gs4.writeLine(nyushaten.x,i+0.5,nyushaten.x,i+1,"rgba(255,0,255,1)");
			} else {
				gs4.writeLine(nyushaten.x,i+0.5,nyushaten.x,top2,"rgba(255,0,255,1)");
			}
		} else {
			gs4.writeLine(nyushaten.x,top3,nyushaten.x,top2,"rgba(255,255,0,1)");
		}
	}
	i+=1;
	while( i+1 < top2 ) {
		gs4.writeLine(nyushaten.x,i,nyushaten.x,i+0.5,"rgba(255,255,0,1)");
		gs4.writeLine(nyushaten.x,i+0.5,nyushaten.x,i+1,"rgba(255,0,255,1)");
		i+=1;
	}
	if( i+0.5 < top2 ) {
		gs4.writeLine(nyushaten.x,i,nyushaten.x,i+0.5,"rgba(255,255,0,1)");
		gs4.writeLine(nyushaten.x,i+0.5,nyushaten.x,top2,"rgba(255,0,255,1)");
	} else if( i< top2 ) {
		gs4.writeLine(nyushaten.x,i,nyushaten.x,top2,"rgba(255,255,0,1)");
	}
	gs4.ctx.fillStyle="rbga(0,0,0,1)";
	gs4.ctx.beginPath();
	gs4.ctx.moveTo(nyushaten.x+0.2,0);
	gs4.ctx.lineTo(nyushaten.x,0.2);
	gs4.ctx.lineTo(nyushaten.x-0.2,0);
	gs4.ctx.lineTo(nyushaten.x,-0.2);
	gs4.ctx.closePath();
	gs4.ctx.fill();
}
function f3(x) {
	return(Math.sqrt((siten2.x-x)*(siten2.x-x)+(siten2.y)*(siten2.y)));
}
function f2(x) {
	return(
		Math.sqrt((siten2.x-x)*(siten2.x-x)+(siten2.y)*(siten2.y))
			+n*Math.sqrt((touchakuten2.x-x)*(touchakuten2.x-x)+(touchakuten2.y)*(touchakuten2.y))
	);
}
var n=2;
function graph3Write() {
    gs3.setScale();
    gs3.clearALL();	
	graph3WriteContent();
}

function graph3WriteContent() {
	var ct=gs3.ctx;
	gs3.writeRect(10,-10,-10,0,"rgba(100,245,245,1)");
	nyushaten.write(ct);
	siten2.write(ct);
	touchakuten2.write(ct);
	var amari;
	amari=simasimaLine(gs3.ctx,siten2.x,siten2.y,nyushaten.x,nyushaten.y,"rgba(160,160,60,1)","rgba(160,60,160,1)",0,1);
	simasimaLine(gs3.ctx,nyushaten.x,nyushaten.y,touchakuten2.x,touchakuten2.y,"rgba(255,255,0,1)","rgba(255,0,255,1)",amari,n);
}
function simasimaLine(ct,x1,y1,x2,y2,c1,c2,a,n) {
	var len=Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
	var vx=(x2-x1)/(2*len*n);
	var vy=(y2-y1)/(2*len*n);
	// (vx,vy)は半波長分のベクトル。
	var xx=x1;
	var yy=y1;
	var l2=len;
	var endflg=false;
	if( a>0 ) {
		if( a>0.5 ) {
			ct.strokeStyle=c2;
			ct.beginPath();
			ct.moveTo(x1,y1);
			if( (1-a) < len*n ) {
				xx=x1+vx*(1-a)*2;
				yy=y1+vy*(1-a)*2;
			} else {
				xx=x2;
				yy=y2;
				endflg=true;
			}
			ct.lineTo(xx,yy);
			ct.stroke();
			if( endflg ) {
				return 0;
			}
		} else {
			ct.strokeStyle=c1;
			ct.beginPath();
			ct.moveTo(x1,y1);
			if( (0.5-a) < len*n ) {
				xx=x1+vx*(0.5-a)*2;
				yy=y1+vy*(0.5-a)*2;
			} else {
				xx=x2;
				yy=y2;
				endflg=true;
			}
			ct.lineTo(xx,yy);
			ct.stroke();
			if( endflg ) {
				return 0;
			}
			ct.strokeStyle=c2;
			ct.beginPath();
			ct.moveTo(xx,yy);
			if( (1-a) <len*n ) {
				xx=xx+vx;
				yy=yy+vy;
			} else {
				xx=x2;
				yy=y2;
				endflg=true;
			}
			ct.lineTo(xx,yy);
			ct.stroke();
			if( endflg ) {
				return 0;
			}
		}
		l2 -= (1-a)/n;
	}

	while( l2 >= 0 ) {
		if( l2 < 0.5/n ) {
			xxx=xx+vx*l2*2*n;
			yyy=yy+vy*l2*2*n;
		} else {
			xxx=xx+vx;
			yyy=yy+vy;
		}
		ct.strokeStyle=c1;
		ct.beginPath();
		ct.moveTo(xx,yy);
		ct.lineTo(xxx,yyy);
		ct.stroke();
		l2 -=0.5/n;
		if( l2 <0 ) {
			return l2+0.5/n;
		} else if( l2 <0.5/n ) {
			xx=xxx+vx*l2*2*n;
			yy=yyy+vy*l2*2*n;
		} else {
			xx=xxx+vx;
			yy=yyy+vy;
		}
		ct.strokeStyle=c2;
		ct.beginPath();
		ct.moveTo(xx,yy);
		ct.lineTo(xxx,yyy);
		ct.stroke();
		l2 -=0.5/n;
	}
	return l2+1/n;
}

