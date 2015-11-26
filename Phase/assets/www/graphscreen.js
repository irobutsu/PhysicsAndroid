// JavaScript Document
// グラフを描くキャンバスです。
var GraphScreen=function(canvas_string,hh,ww,adjustW) {
    RescaleScreen.call(this,canvas_string,hh,ww,adjustW);
}

GraphScreen.prototype=Object.create(RescaleScreen.prototype);
GraphScreen.prototype.constructor=GraphScreen;

GraphScreen.prototype.writeContents=function() {
    this.writeCoordinate();
    this.writeGraphs();
}
GraphScreen.prototype.writeCoordinate=function() {
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
    if( this.ylabel != null ) {
		ct.drawImage(this.ylabel,this.w*0.03,this.topy-this.w*0.1,this.w*0.08*this.ylabel.width/this.ylabel.height,this.w*0.08);	
    }
    ct.beginPath();
    ct.moveTo(this.rightwx,0);
    ct.lineTo(this.rightwx-this.w*0.1,this.w*0.03);
    ct.lineTo(this.rightwx-this.w*0.1,-this.w*0.03);
    ct.closePath();
    ct.fill();
    if( this.xlabel != null ) {
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
}
// 横軸、縦軸のラベル。
GraphScreen.prototype.xlabel=null;
GraphScreen.prototype.ylabel=null;
// ラベルになる画像をセットする。graphscreenは縦が反転しているので、画像も反転画像を用意する。
GraphScreen.prototype.setxlabel=function(f) { this.xlabel=new Image(); this.xlabel.src=f;}
GraphScreen.prototype.setylabel=function(f) { this.ylabel=new Image(); this.ylabel.src=f;}

// プロットする関数。
GraphScreen.prototype.func=function(x) { return x;}
GraphScreen.prototype.setFunction=function(f) {this.func=f;}

// 設定しておいた関数のグラフを描く。
GraphScreen.prototype.writeFunction=function(col,num){
	if( num == undefined ) {
		num=100;
	}
    var ct=this.ctx;
    ct.strokeStyle=col;
    ct.beginPath();
    ct.moveTo(this.leftwx,this.func(this.leftwx));
    var i;
    for(i=1; i< num ; i++ ) {
		var x=this.leftwx + i*this.w/num;
		ct.lineTo(x,this.func(x));
    }
    ct.lineTo(this.rightwx,this.func(this.rightwx));
    ct.stroke();
}

// 設定しておいた関数のグラフを描く。ただし、縦軸と横軸の関係が逆。
GraphScreen.prototype.writeFunctionYX=function(col,num){
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
GraphScreen.prototype.writeLinear=function(a,b,col){
    var ct=this.ctx;
    ct.strokeStyle=col;
    ct.beginPath();
    ct.moveTo(this.leftwx,a*this.leftwx+b);
    ct.lineTo(this.rightwx,a*this.rightwx+b);
    ct.stroke();
}
