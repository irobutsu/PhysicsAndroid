// キャンバスに描画するスクロールバー
var Scrollbar=function(canvas_string,textarea,min,max,val,func,c,step,adjustW,adjustH) {
	if( adjustW === undefined ) {adjustW=0.8;}
	if( adjustH === undefined ) {adjustH=0.1;}
	if( val === undefined ) { val=(min+max)*0.5; }
	if( c=== undefined ) { c="rgba(239,69,74,0.7)";}
	if( step === undefined ) { step=1; }
	this.step=step;
	this.func=func;
	this.col=c;
    this.canvas = document.getElementById(canvas_string);
	var nowgs=this;
	this.text = document.getElementById(textarea);	
    this.ctx=this.canvas.getContext("2d");

	this.val=val;
	this.w=max-min;
	this.leftwx=min;
	this.h=this.w*adjustH/adjustW;

	
	this.canvas.width=document.body.clientWidth*adjustW;
	this.canvas.height=document.body.clientWidth*adjustH;
	this.setScale();
	this.write();
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
	this.canvas.onmousedown = function(e) {
		e.preventDefault();
		var mouseX = e.clientX - e.target.getBoundingClientRect().left;
		var mouseY = e.clientY - e.target.getBoundingClientRect().top;
		nowgs.ptdownsub(0,nowgs.fromMxtoCx(mouseX),nowgs.fromMytoCy(mouseY));
	};
	this.canvas.onmousemove = function(e) {
		// e.preventDefault();
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

Scrollbar.prototype={
	setScale:function() {
		var sc=this.canvas.width/this.w;
		//this.ctx.scale(sc,sc);
		//		this.ctx.translate(this.rightwx,-this.topy);
		this.ctx.setTransform(sc,0,0,sc,0,0);
		this.ctx.translate(-this.leftwx,0);
		this.stdStrokeWidth=this.w/300;
		this.ctx.lineWidth=this.stdStrokeWidth;
	},
	fromMxtoCx:function(mx) {
		return mx/this.canvas.width*this.w+this.leftwx;
	},
	fromMytoCy:function(my) {
		return my/this.canvas.height*this.h;
	},
	write:function() {
		this.clearALL();
		this.writeMark();
	},
	writeMark:function() {
		this.ctx.fillStyle=this.col;
		this.ctx.moveTo(this.val,this.h);
		this.ctx.lineTo(this.val+0.25*this.h,0.5*this.h);
		this.ctx.lineTo(this.val,0);
		this.ctx.lineTo(this.val-0.25*this.h,0.5*this.h);
		this.ctx.closePath();
		this.ctx.fill();
	},
	clearALL:function() {	
		this.ctx.fillStyle="rgb(255,255,255)";
		this.ctx.fillRect(this.leftwx,0,this.w,this.h);
		this.ctx.beginPath();
		this.ctx.strokeStyle="rgb(80,80,80)";
		this.ctx.moveTo(this.leftwx,0.5*this.h);
		this.ctx.lineTo(this.leftwx+this.w,0.5*this.h);
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.strokeStyle="rgba(130,130,130,0.3)";
		this.ctx.moveTo(this.leftwx,0.5*this.h+this.stdStrokeWidth);
		this.ctx.lineTo(this.leftwx+this.w,0.5*this.h+this.stdStrokeWidth);
		this.ctx.stroke();
	},
	ptdownsub:function(j,x,y) {
		if( this.val +this.h*0.25 > x && this.val-this.h*0.25 < x ) {
			this.nowDrag=true;
			this.shiftx = x-this.val;
			this.ID=j;
		}
	},
	ptmovesub:function(j,x,y) {
		if( this.nowDrag && this.ID == j ) {
			this.moveByDrag(x,y);
			this.clearALL();
			this.writeMark();
		}
	},
	moveByDrag:function(xx,yy) {
		xx -= this.shiftx;
		this.setXY(xx,this.leftwx,this.leftwx+this.w);
	},// x,y                                         
	setXY:function(xx,x0,x1) {
		if( xx < x0 ) {
			this.val=x0;
		} else if( xx > x1 ) {
			this.val=x1;
		} else {
			this.val=this.step*Math.ceil(xx/this.step);
		}
		this.text.innerHTML=this.val;
		this.func(this.val);
	},
	ptendsub:function(j) {
		if( this.nowDrag && this.ID == j ) {
			this.nowDrag =false;
		}
	},
}

