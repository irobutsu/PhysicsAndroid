// JavaScript Document
// グラフを描くキャンバスです。
// これはドラッグ不可にしてあるバージョン。
var GraphCanvas=function(canvas_string,hh,ww,adjustW,left,bottom) {
    RescaleCanvas.call(this,canvas_string,hh,ww,adjustW,left,bottom);
}

GraphCanvas.prototype=Object.create(RescaleCanvas.prototype);
GraphCanvas.prototype.constructor=GraphCanvas;


// プロットする関数。

GraphCanvas.prototype.func=function(x) { return x;}
GraphCanvas.prototype.setFunction=function(f) {this.func=f;}

GraphCanvas.prototype.clearAndWrite=function() {
	this.clearALL();
	this.writeBackGround();
};

// 設定しておいた関数のグラフを描く。
GraphCanvas.prototype.writeFunction=function(col,num){
	if( num == undefined ) {
		num=100;
	}
    var ct=this.ctx;
    ct.strokeStyle=col;
    ct.beginPath();
    ct.moveTo(this.leftx,this.func(this.leftx));
    var i;
    for(i=1; i< num ; i++ ) {
		var x=this.leftx + i*this.w/num;
		ct.lineTo(x,this.func(x));
    }
    ct.lineTo(this.rightx,this.func(this.rightx));
    ct.stroke();
}

// 設定しておいた関数のグラフを描く。ただし、縦軸と横軸の関係が逆。
GraphCanvas.prototype.writeFunctionYX=function(col,num){
	if( num == undefined ) {
		num=200;
	}
    var ct=this.ctx;
    ct.strokeStyle=col;
    ct.beginPath();
    ct.moveTo(this.func(this.bottomy),this.bottomy);
    var i;
    for(i=1; i< num ; i++ ) {
		var y=this.bottomy + i*this.h/num;
		ct.lineTo(this.func(y),y);
    }
    ct.lineTo(this.func(this.topy),this.topy);
    ct.stroke();
}
// 傾きa、切片bの直線を引く。
GraphCanvas.prototype.writeLinear=function(a,b,col){
    var ct=this.ctx;
    ct.strokeStyle=col;
    ct.beginPath();
    ct.moveTo(this.leftx,a*this.leftx+b);
    ct.lineTo(this.rightx,a*this.rightx+b);
    ct.stroke();
}
