// JavaScript Document
var PlotScreen=function(canvas_string,adjustFlg) {
    this.canvas = document.getElementById(canvas_string);
    this.ctx=this.canvas.getContext("2d");

    this.w=this.o_w; // 画面の横サイズ（実際のピクセルとは違う）
    this.h=this.o_h; // 縦サイズ
    this.leftwx=this.o_leftwx; // 左壁の座標
    this.rightwx=this.o_rightwx; // 右壁の座標
    this.bottomy=this.o_bottomy; // 画面下の座標
    this.topy=this.o_topy;    // 画面上の座標
    // w=rigthwx-leftwx, h=topy-bottomyでないとダメ（プログラマが自分で気をつける）
	this.tatenagaFlg=false;
    this.writeGraphs=function() {};
    // これは後で上書き設定する。

    if( adjustFlg != false ) {
		// 画面サイズにあわせてcanvasサイズを変える。
		var wx1=document.body.clientWidth;
		var hy1=document.body.clientHeight;
		var wx,hy;
		wx=wx1*0.9;
		hy=hy1*0.85;
		if( wx1 > hy1 ) {
			this.tatenagaFlg=false;
		} else {
			this.tatenagaFlg=true;
		}
		if( adjustFlg == "turn" && this.tatenagaFlg ) {
			this.turn();
		}
		if( hy > wx*this.h/this.w ) {
		    this.canvas.width=wx;
	    	this.canvas.height=wx*this.h/this.w;
		} else {
	    	this.canvas.height=hy;
	    	this.canvas.width=hy*this.w/this.h;	
		}
    }


    
    this.setScale();
	this.setMouseEvent();
}

PlotScreen.prototype={
    // 以下はオリジナルのパラメータ（標準に戻す時は以下の値に変える）
    o_w:20, // 画面の横サイズ（実際のピクセルとは違う）
    o_h:14, // 縦サイズ
    o_leftwx:-10, // 左壁の座標
    o_rightwx:10, // 右壁の座標
    o_bottomy:-7, // 画面下の座標
    o_topy:7,    // 画面上の座標
	// PlotScreenは、画面が横長であることを仮定している。
    // 以上４つは、w,hと整合が取れるように設定すること。rightwx-leftwx=w,topy-bottomy=h
    // html5のcanvasの都合で、x軸は右向き、y軸は下向きであるが、
    // setScale()の中で普通のx,y軸になるようにしている。
    // 以下の描画は全てこの座標系で行います。
    ct:function() { return this.ctx;},
    // ズームイン／アウトした後に元の状態に戻す。
    gobackOriginalSize:function() {
		if( tatenagaFlg ) {
			this.turn();
		} else {
			this.normal();
		}
	},
	setMouseEvent:function() {
		gsnow=this;
	    var touchdev = false;
    	if (navigator.userAgent.indexOf('iPhone') > 0
		|| navigator.userAgent.indexOf('iPod') > 0
		|| navigator.userAgent.indexOf('iPad') > 0
		|| navigator.userAgent.indexOf('Android') > 0) {
		touchdev = true;
    	}
    	if( touchdev ) {
			this.canvas.ontouchstart = ptdownt;	
			this.canvas.ontouchmove = ptmovet;
			this.canvas.ontouchend = ptupt;
    	} else {
			this.canvas.onmousedown = ptdown;	
			this.canvas.onmousemove = ptmove;
			this.canvas.onmouseup = ptup;
			this.canvas.onmouseout = ptup;
    	}
	},
		normal:function() {
			this.w=this.o_w;
			this.h=this.o_h;
			this.leftwx=this.o_leftwx;
			this.rightwx=this.o_rightwx;
			this.bottomy=this.o_bottomy;
			this.topy=this.o_topy;
			this.setScale();
		},
		turn:function() {
			this.w=this.o_h;
			this.h=this.o_w;
			this.leftwx=this.o_bottomy;
			this.rightwx=this.o_topy;
			this.bottomy=this.o_leftwx;
			this.topy=this.o_rightwx;
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
	this.stdStrokeWidth=this.w/400;
	this.ctx.lineWidth=this.stdStrokeWidth;
    },
    saveScale:function() {
	this.ctx.save();
	this.ctx.setTransform(1,0,0,1,0,0);
	this.ctx.stdWidth=1;
    },
    restoreScale:function() {
	this.ctx.restore();
	this.ctx.stdWidth=this.w/400;
    },
    fromMxtoCx:function(mx) {
	return mx/this.canvas.width*this.w+this.leftwx;
    },
    fromMytoCy:function(my) {
	return my/this.canvas.height*this.h+this.topy;
    },
    clearALL:function() {
	this.ctx.clearRect(this.leftwx,this.bottomy,this.w,this.h);
    },
    writeCoordinate:function() {
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
	ct.beginPath();
	ct.moveTo(this.rightwx,0);
	ct.lineTo(this.rightwx-this.w*0.1,this.w*0.03);
	ct.lineTo(this.rightwx-this.w*0.1,-this.w*0.03);
	ct.closePath();
	ct.fill();
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
	
    },
    writeGraph:function(a,b,col){
	var ct=this.ctx;
	ct.strokeStyle=col;
	ct.beginPath();
	ct.moveTo(this.leftwx,a*this.leftwx+b);
	ct.lineTo(this.rightwx,a*this.rightwx+b);
	ct.stroke();
    },
    ptdownt:function() {
	event.preventDefault();
	var i;
	for(i=0 ; i< event.touches.length; i++ ) {
	    var e = event.touches[i];
	    var rect = event.target.getBoundingClientRect();
    	    this.ptdownsub(e.identifier,e.clientX - rect.left,e.clientY - rect.top );
	}
    },
    ptmovet:function() {
	event.preventDefault();
	var i;
	for(i=0 ; i< event.touches.length; i++ ) {
	    var e = event.touches[i];
   	    var rect = event.target.getBoundingClientRect();
    	    this.ptmovesub(e.identifier,e.clientX - rect.left ,e.clientY-rect.top);
	}
    },
    ptupt:function() {
	var j;
	for(j=0; j<event.changedTouches.length; j++ ) {
    	    var e=event.changedTouches[j];
    	    downx=undefined;
	}
    },
    ptdownsub:function(j,x,y) {
	// マウスダウンが起こった場所を設定。
	this.downx=x,this.downy=y;
	// マウスダウンされた時のtopy,leftwxを設定。
	this.downtimetopy=new Object(this.topy);
	this.downtimeleftwx=new Object(this.leftwx);
    },
    ptmovesub:function(j,x,y) {
	if( this.downx == undefined ) {
	    return;
	}
	this.topy=this.downtimetopy　+this.h*(y-this.downy)/this.canvas.height;
	this.leftwx=this.downtimeleftwx -this.w*(x -this.downx)/this.canvas.width;
	this.bottomy=this.topy-this.h;
	this.rightwx=this.leftwx+this.w;
	this.setScale();
	this.clearALL();
	this.writeCoordinate();
	this.writeGraphs();
    },
    // 以下はマウスのためのもの（タッチではなく）
    // マウスは一個しかないので、identifierにあたる数字として0を送る。
    ptdown:function(e) {
	e.preventDefault();
	var mouseX = e.clientX - e.target.getBoundingClientRect().left;
	var mouseY = e.clientY - e.target.getBoundingClientRect().top;
	this.ptdownsub(0,mouseX,mouseY);
    },
    ptmove:function(e) {
	e.preventDefault();
	var mouseX = e.clientX - e.target.getBoundingClientRect().left;
	var mouseY = e.clientY - e.target.getBoundingClientRect().top;
	this.ptmovesub(0,(mouseX),(mouseY));
    },
    ptup:function() {
	this.downx=undefined;
    }    
}
var gsnow;
function ptdownt() {
    gsnow.ptdownt();
}
function ptmovet() {
    gsnow.ptmovet();
}
function ptupt() {
    gsnow.ptupt();
}
function ptdown(e) {
    gsnow.ptdown(e);
}
function ptmove(e) {
    gsnow.ptmove(e);
}
function ptup() {
    gsnow.ptup();
}
