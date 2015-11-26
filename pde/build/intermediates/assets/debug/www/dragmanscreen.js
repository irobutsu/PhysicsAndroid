// JavaScript Document
// RescaleScreenに、中にある物体をドラッグする機能を加える。
var DragmanScreen=function(canvas_string,ww,hh,adjustW) {
    RescaleScreen.call(this,canvas_string,ww,hh,adjustW); //親クラスのコンストラクタを呼ぶ。
	this.dragObjList=[];
//    var touchdev = true;
//    if (navigator.userAgent.indexOf('iPhone') > 0
//	    || navigator.userAgent.indexOf('iPod') > 0
//	    || navigator.userAgent.indexOf('iPad') > 0
//	    || navigator.userAgent.indexOf('Android') > 0) {
//	    touchdev = true;
//    }
    var nowgs=this; // この値は関数から抜けた後も残る。
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
//    }
};

// 親クラスタのプロトタイプを継承する。
DragmanScreen.prototype=Object.create(RescaleScreen.prototype);
DragmanScreen.prototype.constructor=DragmanScreen;

DragmanScreen.prototype.pushDragObjList=function(a) { this.dragObjList.push(a);};
DragmanScreen.prototype.ptdownsub=function(j,x,y) {
	var i;
    for(i=0; i<this.dragObjList.length ; i++ ) {
		if(this.dragObjList[i].inP(x,y) ) {
			this.dragObjList[i].setNowDrag(x,y);
			this.dragObjList[i].setID(j);
			return;
		}
    }
};

// マウスやタッチのカーソル移動
DragmanScreen.prototype.ptmovesub=function(j,x,y) {
	var i=0;
	var rewriteflg=false;
	for( i=0; i< this.dragObjList.length; i++ ) {
		if( this.dragObjList[i].nowDrag 
			&& this.dragObjList[i].getID() == j ) {
			rewriteflg=true;
			this.dragObjList[i].moveByDrag(x,y,this.leftwx,this.bottomy,this.rightwx,this.topy);
		}
	}
	if( rewriteflg ) {
		this.setScale();
		this.clearALL();
		this.writeContents();
	}
};
DragmanScreen.prototype.ptendsub=function(j) {
	var i=0;
	for( i=0; i< this.dragObjList.length; i++ ) {
		if( this.dragObjList[i].nowDrag 
			&& this.dragObjList[i].getID() == j ) {
			this.dragObjList[i].resetNowDrag();
		}
	}
};

DragmanScreen.prototype.writeContents=function() {};
