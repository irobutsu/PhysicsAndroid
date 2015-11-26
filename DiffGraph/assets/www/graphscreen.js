// JavaScript Document
// グラフを描くキャンバスです。
var GraphScreen=function(canvas_string,hh,ww,adjustW,syx) {
	this.funcList=new Array();
    RescaleScreen.call(this,canvas_string,hh,ww,adjustW,syx);
}


GraphScreen.prototype=Object.create(RescaleScreen.prototype);
GraphScreen.prototype.constructor=GraphScreen;

GraphScreen.prototype.clearFunction=function() {
	this.funcList=new Array();
}
GraphScreen.prototype.writeGraph=function() {
	this.clearALL();
	this.writeContents();
}
GraphScreen.prototype.writeContents=function() {
    this.writeCoordinate();
    this.writeFunction(100);
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
GraphScreen.prototype.setFunction=function(f,col) {
	if( col == undefined ) {
		col="rgba(255,0,0,0.8)";
	}
	var func=new Object();
	func.color=col;
	func.func=f;
	this.funcList.push(func);
}


// 設定しておいた関数のグラフを描く。
GraphScreen.prototype.writeFunction=function(num){
    var ct=this.ctx;
	var i;
	var x0=this.leftwx;
	var step=this.w/num;
	var f;
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
		ct.lineTo(this.rightwx,fnc(this.rightwx));
		ct.stroke();
	}
}
GraphScreen.prototype.writeLinear=function(a,x0,b,c) {
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
}

