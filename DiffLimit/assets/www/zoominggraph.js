// JavaScript Document
// グラフを描くキャンバスです。
// これは、アニメーションでズームするバージョン。
var ZoomingGraph=function(canvas_string,hh,ww,adjustW) {
	this.zf=1; // zoom factor
	this.funcList=new Array();
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
		
			this.canvas.width=wx;
			this.canvas.height=wx*this.h/this.w;
	}

	this.dragObjList=[];
	//    var touchdev = true;
	//    if (navigator.userAgent.indexOf('iPhone') > 0
	//	    || navigator.userAgent.indexOf('iPod') > 0
	//	    || navigator.userAgent.indexOf('iPad') > 0
	//	    || navigator.userAgent.indexOf('Android') > 0) {
	//	    touchdev = true;
	//    }
	var nowgs=this;
	//    if( touchdev ) {
	if( window.navigator.msPointerEnabled ) {
		this.canvas.addEventListener(
			"MSPointerDown",
			function(event) {
				event.preventManupulation();
				nowgs.ptdownsub(event.pointerID,event.pageX,event.pageY);
			},
			false
		);
		this.canvas.addEventListener(
			"MSPointerMove",
			function(event) {
				event.preventManupulation();
				nowgs.ptmovesub(event.pointerID,
								nowgs.fromMxtoCx(event.pageX),
								nowgs.fromMytoCy(event.pageY));
			},
			false);
		this.canvas.addEventListener(
			"MSPointerUp",
			function(event) {
				event.preventManupulation();
    	    	nowgs.ptendsub(e.pointerID);
			},
			false);
	} else {
		this.canvas.addEventListener(
			"touchstart",
			function(event) {
				event.preventDefault();
				var i;
				for(i=0 ; i< event.touches.length; i++ ) {
	    			var e = event.touches[i];
	    			var rect = event.target.getBoundingClientRect();
    				nowgs.ptdownsub(e.identifier,
									nowgs.fromMxtoCx(e.clientX - rect.left),
									nowgs.fromMytoCy(e.clientY - rect.top ));
				}
			}
			,false
		);
		this.canvas.addEventListener(
			"touchmove",
			function(event) {
				event.preventDefault();
				var i;
				for(i=0 ; i< event.touches.length; i++ ) {
	    			var e = event.touches[i];
   					var rect = event.target.getBoundingClientRect();
    	    		nowgs.ptmovesub(e.identifier,
									nowgs.fromMxtoCx(e.clientX - rect.left),
									nowgs.fromMytoCy(e.clientY - rect.top ));
				}
			},
			false);
		this.canvas.addEventListener(
			"touchend",
			function(event) {
				event.preventDefault();
				for(j=0; j<event.changedTouches.length; j++ ) {
    	    		var e=event.changedTouches[j];
    	    		nowgs.ptendsub(e.identifier);
				}
			},
			false);
	}
	//    } else {
	this.canvas.onmousedown = function(e) {
		e.preventDefault();
		var mouseX = e.clientX - e.target.getBoundingClientRect().left;
		var mouseY = e.clientY - e.target.getBoundingClientRect().top;
		nowgs.ptdownsub(0,nowgs.fromMxtoCx(mouseX),nowgs.fromMytoCy(mouseY));
	};
	this.canvas.onmousemove = function(e) {
		e.preventDefault();
		var mouseX = e.clientX - e.target.getBoundingClientRect().left;
		var mouseY = e.clientY - e.target.getBoundingClientRect().top;
		nowgs.ptmovesub(0,nowgs.fromMxtoCx(mouseX),nowgs.fromMytoCy(mouseY));
	};
	this.canvas.onmouseup = function(e) {
		nowgs.ptendsub(0);
	};
	this.canvas.onmouseout = function(e) {
		nowgs.ptendsub(0);
	};
}

ZoomingGraph.prototype={
	// 以下はオリジナルのパラメータ（標準に戻す時は以下の値に変える）
	o_w:4, // 画面の横サイズ（実際のピクセルとは違う）
	o_h:4, // 縦サイズ
	o_cx:1, // 中心のx座標
	o_cy:1, // 中心のy座標
	// 以下の描画は全てこの座標系で行います。
	ct:function() { return this.ctx;},
	setW:function(x) { this.o_w=x;},
	setH:function(x) { this.o_h=x;},
	zoomback:function() {
		this.zf =1;
		this.setScale();
	},
	zoom2:function() {
		this.zf *=0.5;
		this.setScale();
	},
	zoomout2:function() {
		this.zf *=2;
		this.setScale();
	},
	changeZoomFactor:function(z) {
		this.zf=z;
		this.setScale();
	},
	setCenter:function(x,y){ this.o_cx=x; this.o_cy=y;},
	moveCenter:function(x,y){
		this.cx=x;
		this.cy=y;
		this.setScale();
	},
	setOriginalSize:function() {
		this.w=this.o_w;
		this.h=this.o_h;
		this.cx=this.o_cx;
		this.cy=this.o_cy;
		this.zf=1;
	},
	leftx:function() { return this.cx-this.w*0.5*this.zf; },
	rightx:function() { return this.cx+this.w*0.5*this.zf; },
	topy:function() { return this.cy+this.h*0.5*this.zf; },
	bottomy:function() { return this.cy-this.h*0.5*this.zf; },
	// ズームイン／アウトした後に元の状態に戻す。
	gobackOriginalSize:function() {
		this.setOriginalSize();   
		this.setScale();
	},
    setScale:function() {
		var sc=this.canvas.width/(this.w*this.zf);
		//this.ctx.scale(sc,sc);
		//		this.ctx.translate(this.rightwx,-this.topy);
		this.ctx.setTransform(sc,0,0,-sc,0,0);
		this.ctx.translate(-this.leftx(),-this.topy());
		this.stdStrokeWidth=this.w*this.zf/300;
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
		return mx/this.canvas.width*this.w*this.zf+this.leftx();
    },
    fromMytoCy:function(my) {
		return -my/this.canvas.height*this.h*this.zf+this.topy();
    },
    clearALL:function() {
		this.ctx.fillStyle="rgb(255,255,255)";
		this.ctx.fillRect(this.leftx(),this.bottomy(),this.w*this.zf,this.h*this.zf);
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
	},
	writeCoordinate:function() {
		var ct=this.ctx;
		ct.strokeStyle="rgb(0,0,0)";
		ct.fillStyle="rgb(0,0,0)";
		ct.beginPath();
		ct.moveTo(this.leftx(),0);
		ct.lineTo(this.rightx(),0);
		ct.moveTo(0,this.topy());
		ct.lineTo(0,this.bottomy());
		ct.stroke();
		ct.beginPath();
		ct.moveTo(0,this.topy());
		ct.lineTo(this.w*0.03,this.topy()-this.w*0.1);
		ct.lineTo(-this.w*0.03,this.topy()-this.w*0.1);
		ct.closePath();
		ct.fill();
		if( this.ylabel != null ) {
			ct.drawImage(this.ylabel,this.w*0.03,this.topy()-this.w*0.1,this.w*0.08*this.ylabel.width/this.ylabel.height,this.w*0.08);	
		}
		ct.beginPath();
		ct.moveTo(this.rightx(),0);
		ct.lineTo(this.rightx()-this.w*0.1,this.w*0.03);
		ct.lineTo(this.rightx()-this.w*0.1,-this.w*0.03);
		ct.closePath();
		ct.fill();
		if( this.xlabel != null ) {
			ct.drawImage(this.xlabel,this.rightx()-this.w*0.15,this.w*0.03,this.w*0.05*this.xlabel.width/this.xlabel.height,this.w*0.05);
		}
		var minx=Math.ceil(this.leftx()+0.1);
		var maxx=Math.floor(this.rightx()-0.1);
		var miny=Math.ceil(this.bottomy()+0.1);
		var maxy=Math.floor(this.topy()-0.1);
		var i;
		ct.strokeStyle="rgba(0,0,0,0.3)";
		for(i=minx ; i<=maxx ; i++ ) {
			ct.beginPath();
			ct.moveTo(i,this.bottomy());
			ct.lineTo(i,this.topy());
			ct.stroke();
		}
		for(i=miny ; i<=maxy ; i++ ) {
			ct.beginPath();
			ct.moveTo(this.leftx(),i);
			ct.lineTo(this.rightx(),i);
			ct.stroke();
		}
	},
	writeCoordinate:function() {
		var ct=this.ctx;
		ct.strokeStyle="rgb(0,0,0)";
		ct.fillStyle="rgb(0,0,0)";
		ct.beginPath();
		ct.moveTo(this.leftx(),0);
		ct.lineTo(this.rightx(),0);
		ct.moveTo(0,this.topy());
		ct.lineTo(0,this.bottomy());
		ct.stroke();
		ct.beginPath();
		ct.moveTo(0,this.topy());
		ct.lineTo(this.w*this.zf*0.03,this.topy()-this.w*this.zf*0.1);
		ct.lineTo(-this.w*this.zf*0.03,this.topy()-this.w*this.zf*0.1);
		ct.closePath();
		ct.fill();
		ct.beginPath();
		ct.moveTo(this.rightx(),0);
		ct.lineTo(this.rightx()-this.w*this.zf*0.1,this.w*this.zf*0.03);
		ct.lineTo(this.rightx()-this.w*this.zf*0.1,-this.w*this.zf*0.03);
		ct.closePath();
		ct.fill();
		var minx=Math.ceil(this.leftx()+0.1);
		var maxx=Math.floor(this.rightx()-0.1);
		var miny=Math.ceil(this.bottomy()+0.1);
		var maxy=Math.floor(this.topy()-0.1);
		var i;
		ct.strokeStyle="rgba(0,0,0,0.3)";
		for(i=minx ; i<=maxx ; i++ ) {
			ct.beginPath();
			ct.moveTo(i,this.bottomy());
			ct.lineTo(i,this.topy());
			ct.stroke();
		}
		for(i=miny ; i<=maxy ; i++ ) {
			ct.beginPath();
			ct.moveTo(this.leftx(),i);
			ct.lineTo(this.rightx(),i);
			ct.stroke();
		}
	},
	func:function(x) { return x;},
	setFunction:function(f,col) {
		if( col == undefined ) {
			col="rgba(255,0,0,0.8)";
		}
		var func=new Object();
		func.color=col;
		func.func=f;
		this.funcList.push(func);
	},
	// 設定しておいた関数のグラフを描く。
	writeFunction:function(num){
		if( num == undefined ) {
			num=100;
		}
		var ct=this.ctx;
		var i;
		var x0=this.leftx();
		var step=this.w*this.zf/num;
		for(f=0; f< this.funcList.length ; f++ ) {
			ct.strokeStyle=this.funcList[f].color;
			ct.beginPath();
			var fnc=this.funcList[f].func;
			ct.moveTo(x0,fnc(x0));
			var i;
			var x;
			for(i=1; i< num ; i++ ) {
				x=x0 + i*step;
				ct.lineTo(x,fnc(x));
			}
			ct.stroke();
		}
	},
	// 傾きa、切片bの直線を引く。
	writeLinear:function(a,b,col){
		var ct=this.ctx;
		ct.strokeStyle=col;
		ct.beginPath();
		ct.moveTo(this.leftx(),a*this.leftx()+b);
		ct.lineTo(this.rightx(),a*this.rightx()+b);
		ct.stroke();
	},
	writeGraph:function() {
		this.clearALL();
		this.writeContents();
	},
	writeContents:function() {
		this.writeCoordinate();
		this.writeFunction(this.samples);
	},
	pushDragObjList:function(a) { this.dragObjList.push(a);},
	ptdownsub:function(j,x,y) {
		var i;
		for(i=0; i<this.dragObjList.length ; i++ ) {
			if(this.dragObjList[i].inP(x,y) ) {
				this.dragObjList[i].setNowDrag(x,y);
				this.dragObjList[i].setID(j);
				return;
			}
		}
	},
	ptmovesub:function(j,x,y) {
		var i=0;
		var rewriteflg=false;
		for( i=0; i< this.dragObjList.length; i++ ) {
			if( this.dragObjList[i].nowDrag 
				&& this.dragObjList[i].getID() == j ) {
				rewriteflg=true;
				this.dragObjList[i].moveByDrag(x,y,this.leftx(),this.rightx(),this.bottomy(),this.topy());
			}
		}
		if( rewriteflg ) {
			this.setScale();
			this.clearALL();
			this.writeContents();
		}
	},
	ptendsub:function(j) {
		var i=0;
		for( i=0; i< this.dragObjList.length; i++ ) {
			if( this.dragObjList[i].nowDrag 
				&& this.dragObjList[i].getID() == j ) {
				this.dragObjList[i].resetNowDrag();
			}
		}
	},
	fromMxtoCx:function(mx) {
		return mx/this.canvas.width*this.w*this.zf+this.leftx();
    },
    fromMytoCy:function(my) {
		return -my/this.canvas.height*this.h*this.zf+this.topy();
    },
}
