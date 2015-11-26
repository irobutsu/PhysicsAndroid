// JavaScript Document
// 複数のグラフを描くキャンバスです。
var MultiGraphScreen=function(canvas_string,hh,ww,adjustW,syx) {
	this.funcList=new Array();
    RescaleScreen.call(this,canvas_string,hh,ww,adjustW,syx);	
	this.dragObjList=[];
	var nowgs=this; 
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
			},false
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
};

MultiGraphScreen.prototype=Object.create(RescaleScreen.prototype);
MultiGraphScreen.prototype.constructor=MultiGraphScreen;

MultiGraphScreen.prototype.clearFunction=function() {
	this.funcList=new Array();
};
MultiGraphScreen.prototype.writeGraph=function() {
	this.clearALL();
	this.writeContents();
};

MultiGraphScreen.prototype.writeContents=function() {
    this.writeCoordinate();
    this.writeFunction(this.samples);
};
MultiGraphScreen.prototype.writeCoordinate=function() {
    var ct=this.ctx;
    ct.strokeStyle="rgb(0,0,0)";
    ct.fillStyle="rgb(0,0,0)";
    ct.beginPath();
    ct.moveTo(this.leftwx,0);
    ct.lineTo(this.rightwx,0);
    ct.moveTo(0,this.topy);
    ct.lineTo(0,this.bottomy);
    ct.stroke();
    ct.beginPath();
    ct.moveTo(0,this.topy);
    ct.lineTo(this.w*0.03,this.topy-this.w*0.1);
    ct.lineTo(-this.w*0.03,this.topy-this.w*0.1);
    ct.closePath();
    ct.fill();
    if( this.ylabel !== null ) {
		ct.drawImage(this.ylabel,this.w*0.03,this.topy-this.w*0.1,this.w*0.08*this.ylabel.width/this.ylabel.height,this.w*0.08);	
    }
    ct.beginPath();
    ct.moveTo(this.rightwx,0);
    ct.lineTo(this.rightwx-this.w*0.1,this.w*0.03);
    ct.lineTo(this.rightwx-this.w*0.1,-this.w*0.03);
    ct.closePath();
    ct.fill();
    if( this.xlabel !== null ) {
		ct.drawImage(this.xlabel,this.rightwx-this.w*0.15,this.w*0.03,this.w*0.05*this.xlabel.width/this.xlabel.height,this.w*0.05);
    }
    var minx=Math.ceil(this.leftwx+0.1);
    var maxx=Math.floor(this.rightwx-0.1);
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
		ct.moveTo(this.leftwx,i);
		ct.lineTo(this.rightwx,i);
		ct.stroke();
    }
};
// 横軸、縦軸のラベル。
MultiGraphScreen.prototype.xlabel=null;
MultiGraphScreen.prototype.ylabel=null;
// ラベルになる画像をセットする。graphscreenは縦が反転しているので、画像も反転画像を用意する。
MultiGraphScreen.prototype.setxlabel=function(f) { this.xlabel=new Image(); this.xlabel.src=f;};
MultiGraphScreen.prototype.setylabel=function(f) { this.ylabel=new Image(); this.ylabel.src=f;};

// プロットする関数。
MultiGraphScreen.prototype.func=function(x) { return x;};
MultiGraphScreen.prototype.setFunction=function(f,col) {
	if( col === undefined ) {
		col="rgba(255,0,0,0.8)";
	}
	var func=new Object();
	func.color=col;
	func.func=f;
	this.funcList.push(func);
};


// 設定しておいた関数のグラフを描く。
MultiGraphScreen.prototype.writeFunction=function(num){
	if( num === undefined ) {
		num=100;
	}
    var ct=this.ctx;
	var i;
	var x0=this.leftwx;
	var step=this.w/num;
	for(f=0; f< this.funcList.length ; f++ ) {
		ct.strokeStyle=this.funcList[f].color;
		ct.beginPath();
		var fnc=this.funcList[f].func;
		var writeFlg=false;
		var px=x0;
		var py=fnc(x0);
		var x;
		var y;
		for(i=1; i< num ; i++ ) {
			x=x0 + i*step;
			y=fnc(x);
			if( py < this.bottomy || py > this.topy ) {
				if( y < this.bottomy || y > this.topy ) {
					// どちらも圏外
					writeFlg=false;
				} else {
					ct.moveTo(px,py);
					ct.lineTo(x,y);
					writeFlg=true;
				}
			} else {
				if( writeFlg === false ) {
					ct.moveTo(px,py);
					writeFlg=true;
				}
				ct.lineTo(x,y);
			}
			px=x;
			py=y;
		}
		//ct.lineTo(this.rightwx,fnc(this.rightwx));
		ct.stroke();
	}
};

MultiGraphScreen.prototype.writeLinear=function(a,x0,b,c) {
	var ct=this.ctx;
	var x1=this.leftwx;
	var y1=a*(x1-x0)+b;
	var x2=this.rightwx;
	var y2=a*(x2-x0)+b;
	ct.strokeStyle=c;
	ct.beginPath();
	ct.moveTo(x1,y1);
	ct.lineTo(x2,y2);
	ct.stroke();
};

MultiGraphScreen.prototype.pushDragObjList=function(a) { this.dragObjList.push(a);};
MultiGraphScreen.prototype.ptdownsub=function(j,x,y) {
	var i;
    for(i=0; i<this.dragObjList.length ; i++ ) {
		if(this.dragObjList[i].inP(x,y) ) {
			this.dragObjList[i].setNowDrag(x,y);
			this.dragObjList[i].setID(j);
			return;
		}
    }
};
//               
MultiGraphScreen.prototype.ptmovesub=function(j,x,y) {
	var i=0;
	var rewriteflg=false;
	for( i=0; i< this.dragObjList.length; i++ ) {
		if( this.dragObjList[i].nowDrag 
			&& this.dragObjList[i].getID() == j ) {
			rewriteflg=true;
			this.dragObjList[i].moveByDrag(x,y,this.leftwx,this.rightwx,this.bottomy,this.topy);
		}
	}
	if( rewriteflg ) {
		this.setScale();
		this.clearALL();
		this.writeContents();
	}
};

MultiGraphScreen.prototype.ptendsub=function(j) {
	var i=0;
	for( i=0; i< this.dragObjList.length; i++ ) {
		if( this.dragObjList[i].nowDrag 
			&& this.dragObjList[i].getID() == j ) {
			this.dragObjList[i].resetNowDrag();
		}
	}
};
