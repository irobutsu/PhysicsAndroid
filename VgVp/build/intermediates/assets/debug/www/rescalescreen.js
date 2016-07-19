// JavaScript Document
// グラフを描くキャンバスです。
var RescaleScreen=function(canvas_string,ww,hh,adjustW) {
    this.canvas = document.getElementById(canvas_string);
    this.ctx=this.canvas.getContext("2d");
	
	this.o_h=hh;
	this.o_w=ww;
    this.setOriginalSize();

	if( adjustW > 0 ) {
		// 画面サイズにあわせてcanvasサイズを変える。
		// 画面横幅のadjustW倍になる。ただし、縦が苦しい
		// 場合はもっと縮む。
		// この機能を使わない場合はadjustWに負の値をセット。

		var wx=document.body.clientWidth*adjustW;
		var hy=document.body.clientHeight*0.7;
		
		if( hy > wx*this.h/this.w ) {
			this.canvas.width=wx;
			this.canvas.height=wx*this.h/this.w;
		} else {
			this.canvas.height=hy;
			this.canvas.width=hy*this.w/this.h;	
		}
	}
}

RescaleScreen.prototype={
	// 以下はオリジナルのパラメータ（標準に戻す時は以下の値に変える）
	o_w:20, // 画面の横サイズ（実際のピクセルとは違う）
	o_h:16, // 縦サイズ
	o_leftwx:-10, // 左壁の座標
	// 右壁の座標は、自動的に「左壁の座標＋横サイズ」になる。
	o_bottomy:-6, // 画面下の座標
	// 画面上の座標は、自動的に「画面下の座標＋縦サイズ」になる。
	// html5のcanvasの都合で、x軸は右向き、y軸は下向きであるが、
	// setScale()の中で普通のx,y軸になるようにしている。
	// 以下の描画は全てこの座標系で行います。
	ct:function() { return this.ctx;},
	setW:function(x) { this.o_w=x;},
	setH:function(x) { this.o_h=x;},
	setLeft:function(x) { this.o_leftwx=x; },
	setBottom:function(x) { this.o_bottomy=x; },
	writeGraphs:function() {},
    // これは後で上書き設定する。
	setOriginalSize:function() {
		this.w=this.o_w;
		this.h=this.o_h;
		this.leftwx=this.o_leftwx;
		this.rightwx=this.o_leftwx+this.o_w;
		this.bottomy=this.o_bottomy;
		this.topy=this.o_bottomy+this.o_h;
	},
	// ズームイン／アウトした後に元の状態に戻す。
	gobackOriginalSize:function() {
		this.setOriginalSize();   
		this.setScale();
	},
	changeScale:function(k) {
		this.w*=k; this.h*=k;
		this.leftwx= (this.leftwx+this.rightwx+ k*(this.leftwx-this.rightwx))/2;
		this.rightwx=this.leftwx+this.w;
		this.bottomy=(this.bottomy+this.topy + k*(this.bottomy-this.topy))/2;
		this.topy=this.bottomy+this.h;
		this.setScale();  
    },
    setScale:function() {
		var sc=this.canvas.width/this.w;
		//this.ctx.scale(sc,sc);
		//		this.ctx.translate(this.rightwx,-this.topy);
		this.ctx.setTransform(sc,0,0,-sc,0,0);
		this.ctx.translate(-this.leftwx,-this.topy);
		this.stdStrokeWidth=this.w/300;
		this.ctx.lineWidth=this.stdStrokeWidth;
    },
    saveScale:function() {
		this.ctx.save();
		this.ctx.setTransform(1,0,0,1,0,0);
		this.ctx.stdStrokeWidth=1;
    },
    restoreScale:function() {
		this.ctx.restore();
		this.ctx.stdStrokeWidth=this.w/300;
    },
    fromMxtoCx:function(mx) {
		return mx/this.canvas.width*this.w+this.leftwx;
    },
    fromMytoCy:function(my) {
		return -my/this.canvas.height*this.h+this.topy;
    },
    clearALL:function() {
		this.ctx.clearRect(this.leftwx,this.bottomy,this.w,this.h);
    },
	// (x,y)に半径rの点を打つ。
	writePoint:function(x,y,r,col) {
		var ct=this.ctx;
		ct.strokeStyle=col;
		ct.fillStyle=col;
		ct.beginPath();
		ct.arc(x,y,r,0,2*Math.PI,false);
		ct.fill();
	},
// (x1,y1)から(x2,y2)へ線分を引く。
	writeLine:function(x1,y1,x2,y2,col,w) {
		if( w==undefined ) w=1;
		var ct=this.ctx;
		ct.lineWidth=this.ctx.stdStrokeWidth*w;
		ct.strokeStyle=col;
		ct.beginPath();
		ct.moveTo(x1,y1);
		ct.lineTo(x2,y2);
		ct.stroke();
		ct.lineWidth=this.ctx.stdStrokeWidth;
	},
	writeRect:function(x1,y1,x2,y2,col,w) {
		if( w==undefined ) w=1;
		var ct=this.ctx;
		ct.lineWidth=this.ctx.stdStrokeWidth*w;
		ct.fillStyle=col;
		ct.beginPath();
		ct.moveTo(x1,y1);
		ct.lineTo(x1,y2);
		ct.lineTo(x2,y2);
		ct.lineTo(x2,y1);
		ct.closePath();
		ct.fill();
		ct.lineWidth=this.ctx.stdStrokeWidth;
	}
}
