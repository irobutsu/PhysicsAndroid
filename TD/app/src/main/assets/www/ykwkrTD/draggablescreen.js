// JavaScript Document
// RescaleScreenに、画面全体をドラッグする機能を加える。
var DraggableScreen=function(canvas_string,ww,hh,adjustW) {
	RescaleScreen.call(this,canvas_string,ww,hh,adjustW); //親クラスのコンストラクタを呼ぶ。
	var touchdev = false;
	if (navigator.userAgent.indexOf('iPhone') > 0
		|| navigator.userAgent.indexOf('iPod') > 0
		|| navigator.userAgent.indexOf('iPad') > 0
		|| navigator.userAgent.indexOf('Android') > 0) {
		touchdev = true;
	}
	var nowgs=this; // この値は関数から抜けた後も残る。
	if( touchdev ) {
		this.canvas.ontouchstart = function(e) {
			event.preventDefault();
			var i;
			for(i=0 ; i< event.touches.length; i++ ) {
				var e = event.touches[i];
				var rect = event.target.getBoundingClientRect();
				nowgs.ptdownsub(e.identifier,e.clientX - rect.left,e.clientY - rect.top );
			}
		};
		this.canvas.ontouchmove = function(e) {
			event.preventDefault();
			var i;
			for(i=0 ; i< event.touches.length; i++ ) {
				var e = event.touches[i];
   				var rect = event.target.getBoundingClientRect();
				nowgs.ptmovesub(e.identifier,e.clientX - rect.left ,e.clientY-rect.top);
			}
		};
		this.canvas.ontouchend = function(e) {
			for(j=0; j<event.changedTouches.length; j++ ) {
				var e=event.changedTouches[j];
				downx=undefined;
			}
		};
	} else {
		this.canvas.onmousedown = function(e) {
			e.preventDefault();
			var mouseX = e.clientX - e.target.getBoundingClientRect().left;
			var mouseY = e.clientY - e.target.getBoundingClientRect().top;
			nowgs.ptdownsub(0,mouseX,mouseY);
		};
		this.canvas.onmousemove = function(e) {
			e.preventDefault();
			var mouseX = e.clientX - e.target.getBoundingClientRect().left;
			var mouseY = e.clientY - e.target.getBoundingClientRect().top;
			nowgs.ptmovesub(0,(mouseX),(mouseY));
		};
		this.canvas.onmouseup = function(e) {
			nowgs.downx=undefined;
		}
		this.canvas.onmouseout = function(e) {
			nowgs.downx=undefined;
		};
	}
}

// 親クラスタのプロトタイプを継承する。
DraggableScreen.prototype=Object.create(RescaleScreen.prototype);
DraggableScreen.prototype.constructor=DraggableScreen;


DraggableScreen.prototype.ptdownsub=function(j,x,y) {
	// マウスダウンが起こった場所を設定。
	this.downx=x,this.downy=y;
	// マウスダウンされた時のtopy,leftwxを設定。
	this.downtimetopy=new Object(this.topy);
	this.downtimeleftwx=new Object(this.leftwx);
}
DraggableScreen.prototype.ptmovesub=function(j,x,y) {
	if( this.downx == undefined ) {
		return;
	}
	this.topy=this.downtimetopy　+this.h*(y-this.downy)/this.canvas.height;
	this.leftwx=this.downtimeleftwx -this.w*(x -this.downx)/this.canvas.width;
	this.bottomy=this.topy-this.h;
	this.rightwx=this.leftwx+this.w;
	this.setScale();
	this.clearALL();
	this.writeContents();
}
DraggableScreen.prototype.writeContents=function() {}
