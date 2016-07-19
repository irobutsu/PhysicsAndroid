// RescaleCanvas
// 座標系を設定して、その座標系に図を描くためのキャンバス
// 呼び出し方は　new RescaleCanvas(canvas_string,ww,hh,adjustW,left,bottom);
// canvas_stringはキャンバス要素のid
// ww,hhは縦横の幅（画面上の幅とは一致しない）
// 画面上の幅は、横幅*adjustWになる。
// 画面の左隅のx座標はleft、
// 下隅のy座標はbottomとなる。
// x,y座標は右、上が正方向となるので注意。
var RescaleCanvas=function(canvas_string,ww,hh,adjustW,left,bottom) {
    this.canvas = document.getElementById(canvas_string);
    this.ctx=this.canvas.getContext("2d");

	// 以下、o_のついた量は「初期値」として使用。
	this.o_w= ( ww == undefined ) ? 20: ww;
	this.o_h= ( hh == undefined ) ? 20: hh;
	this.o_leftx= ( left == undefined ) ? -0.5*this.o_w : left;
	this.o_bottomy= ( bottom == undefined ) ? -0.5*this.o_h : bottom;
	this.w=this.o_w;
	this.h=this.o_h;
	this.leftx=this.o_leftx;
	this.rightx=this.o_leftx+this.o_w;
	this.bottomy=this.o_bottomy;
	this.topy=this.o_bottomy+this.o_h;

	if( adjustW > 0 ) {
		// 画面サイズにあわせてcanvasサイズを変える。
		// 画面横幅のadjustW倍になる。
		// この機能を使わない場合はadjustWに負の値をセット。
		var wx=document.body.clientWidth*adjustW;
		this.canvas.width=wx;
		this.canvas.height=wx*this.h/this.w;
	}
	this.setScale();
}


// 以下はプロトタイプ（後で上書きすることができる）
RescaleCanvas.prototype={
	// html5のcanvasの都合で、x軸は右向き、y軸は下向きであるが、
	// setScale()の中で普通のx,y軸になるようにしている。
	// 以下の描画は全てこの座標系で行います。
	// ズームイン／アウトした後に元の状態に戻す。
	gobackOriginalSize:function() {
		this.w=this.o_w;
		this.h=this.o_h;
		this.leftx=this.o_leftx;
		this.rightx=this.o_leftx+this.o_w;
		this.bottomy=this.o_bottomy;
		this.topy=this.o_bottomy+this.o_h;
		this.setScale();
	},
	changeScale:function(k) {
		this.w*=k; this.h*=k;
		this.leftx= (this.leftx+this.rightx+ k*(this.leftx-this.rightx))/2;
		this.rightx=this.leftx+this.w;
		this.bottomy=(this.bottomy+this.topy + k*(this.bottomy-this.topy))/2;
		this.topy=this.bottomy+this.h;
		this.setScale();  
    },
    setScale:function() {
		var sc=this.canvas.width/this.w;
		var scy=this.canvas.height/this.h;
		this.ctx.setTransform(sc,0,0,-scy,0,0);
		this.ctx.translate(-this.leftx,-this.topy);
		this.stdStrokeWidth= this.w/this.canvas.width;
		this.ctx.lineWidth=this.stdStrokeWidth;
    },
    saveScale:function() {
		this.ctx.save();
		this.ctx.setTransform(1,0,0,1,0,0);
		this.ctx.stdStrokeWidth=1;
    },
    restoreScale:function() {
		this.ctx.restore();
		this.ctx.stdStrokeWidth= this.w/this.canvas.width;
		this.ctx.lineWidth=this.stdStrokeWidth;
    },
	// 以下二つは、マウスカーソルからキャンバスの座標系への変換。
    fromMxtoCx:function(mx) {
		return mx/this.canvas.width*this.w+this.leftx;
    },
    fromMytoCy:function(my) {
		return -my/this.canvas.height*this.h+this.topy;
    },
    clearALL:function() {	
		this.ctx.fillStyle="rgb(255,255,255)";
		this.ctx.fillRect(this.leftx,this.bottomy,this.w,this.h);
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
	writeCircle:function(x1,y1,r,col,w) {
		if( w==undefined ) w=1;
		var ct=this.ctx;
		ct.lineWidth=this.ctx.stdStrokeWidth*w;
		ct.strokeStyle=col;
		ct.beginPath();
		ct.arc(x1,y1,r,0,2*Math.PI,true);
		ct.stroke();
		ct.lineWidth=this.ctx.stdStrokeWidth;
	},
	fillCircle:function(x1,y1,r,col) {
		var ct=this.ctx;
		ct.fillStyle=col;
		ct.beginPath();
		ct.arc(x1,y1,r,0,2*Math.PI,true);
		ct.fill();
	},
	writeRect:function(x1,y1,x2,y2,col,w) {
		if( w==undefined ) w=1;
		var ct=this.ctx;
		ct.lineWidth=this.ctx.stdStrokeWidth*w;
		ct.strokeStyle=col;
		ct.beginPath();
		ct.moveTo(x1,y1);
		ct.lineTo(x1,y2);
		ct.lineTo(x2,y2);
		ct.lineTo(x2,y1);
		ct.closePath();
		ct.stroke();
		ct.lineWidth=this.ctx.stdStrokeWidth;
	},
	fillRect:function(x1,y1,x2,y2,col,w) {
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
	},
	writeBackGround:function() {
		this.writeCoordinate();
	},
	writeCoordinate:function() {
		var ct=this.ctx;
		ct.strokeStyle="rgb(0,0,0)";
		ct.fillStyle="rgb(0,0,0)";
		ct.beginPath();
		ct.moveTo(this.leftx,0);
		ct.lineTo(this.rightx,0);
		ct.moveTo(0,this.topy);
		ct.lineTo(0,this.bottomy);
		ct.stroke();
		ct.beginPath();
		ct.moveTo(0,this.topy);
		ct.lineTo(this.w*0.03,this.topy-this.w*0.1);
		ct.lineTo(-this.w*0.03,this.topy-this.w*0.1);
		ct.closePath();
		ct.fill();
		ct.beginPath();
		ct.moveTo(this.rightx,0);
		ct.lineTo(this.rightx-this.w*0.1,this.w*0.03);
		ct.lineTo(this.rightx-this.w*0.1,-this.w*0.03);
		ct.closePath();
		ct.fill();
		var minx=Math.ceil(this.leftx+0.1);
		var maxx=Math.floor(this.rightx-0.1);
		var miny=Math.ceil(this.bottomy+0.1);
		var maxy=Math.floor(this.topy-0.1);
		var i;
		ct.strokeStyle="rgba(0,0,0,0.3)";
		for(i=minx ; i<=maxx ; i++ ) {
			ct.beginPath();
			ct.moveTo(i,this.bottomy);
			ct.lineTo(i,this.topy);
			ct.stroke();
		}
		for(i=miny ; i<=maxy ; i++ ) {
			ct.beginPath();
			ct.moveTo(this.leftx,i);
			ct.lineTo(this.rightx,i);
			ct.stroke();
		}
	},
	writeCoordinateX:function() {
		var ct=this.ctx;
		ct.strokeStyle="rgb(0,0,0)";
		ct.fillStyle="rgb(0,0,0)";
		ct.beginPath();
		ct.moveTo(this.leftx,0);
		ct.lineTo(this.rightx,0);
		ct.moveTo(0,this.topy);
		ct.lineTo(0,this.bottomy);
		ct.stroke();
		ct.beginPath();
		ct.moveTo(this.rightx,0);
		ct.lineTo(this.rightx-this.w*0.1,this.w*0.03);
		ct.lineTo(this.rightx-this.w*0.1,-this.w*0.03);
		ct.closePath();
		ct.fill();
		var minx=Math.ceil(this.leftx+0.1);
		var maxx=Math.floor(this.rightx-0.1);
		var i;
		ct.strokeStyle="rgba(0,0,0,0.3)";
		for(i=minx ; i<=maxx ; i++ ) {
			ct.beginPath();
			ct.moveTo(i,this.bottomy);
			ct.lineTo(i,this.topy);
			ct.stroke();
		}
	},
	writeCoordinateY:function() {
		var ct=this.ctx;
		ct.strokeStyle="rgb(0,0,0)";
		ct.fillStyle="rgb(0,0,0)";
		ct.beginPath();
		ct.moveTo(this.leftx,0);
		ct.lineTo(this.rightx,0);
		ct.moveTo(0,this.topy);
		ct.lineTo(0,this.bottomy);
		ct.stroke();
		ct.moveTo(0,this.topy);
		ct.lineTo(this.w*0.03,this.topy-this.w*0.1);
		ct.lineTo(-this.w*0.03,this.topy-this.w*0.1);
		ct.closePath();
		ct.fill();
		var miny=Math.ceil(this.bottomy+0.1);
		var maxy=Math.floor(this.topy-0.1);
		var i;
		ct.strokeStyle="rgba(0,0,0,0.3)";
		for(i=miny ; i<=maxy ; i++ ) {
			ct.beginPath();
			ct.moveTo(this.leftx,i);
			ct.lineTo(this.rightx,i);
			ct.stroke();
		}
	}
}
