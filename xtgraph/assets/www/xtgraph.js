// JavaScript Document
// x-tグラフを描いて運動を視る。
var Tama=function(xx,yy,rr,c) {
  DraggablePoint.call(this,xx,yy,rr,c);
  this.setXY=function(xx,yy) {
	this.y=yy; // このモデルではxは変化しないので、xxは無視する。
	if( this.y<this.r ) { this.y=this.r; }
	if( this.y>6-this.r ) { this.y = 6-this.r; }
  };
}
Tama.prototype=Object.create(DraggablePoint.prototype);
Tama.prototype.constructor=Tama;

var countT=12;
function init_xtgraph(canvas_string) {
  gs=new DragmanScreen(canvas_string,2+countT*0.5,6.5,0.8);
  gs.setLeft(-1.5);
  gs.setBottom(-0.5);
  gs.gobackOriginalSize();
  var i;
  gs.pushDragObjList( new Tama(0,0.5,0.15,"rgba(255,0,0,1)") );
  for( i=1; i<countT ; i++ ) {
	gs.pushDragObjList( new Tama(i*0.5,2,0.15,"rgba(130,130,130,1)") );
  }
  gs.pushDragObjList( new Tama(countT*0.5,3,0.15,"rgba(255,0,0,1)") );
  gs.writeContents=function() {
	graphWrite();
  }
  houbutsu();
}
var vFlg=false;
function graphWrite() {
  gs.clearALL();
  var i;
  gs.writeLine(0,0,0.5*countT,0,"rgb(200,0,0)");
  gs.ctx.fillStyle="rgb(200,0,0)";
  gs.ctx.moveTo(countT*0.5+0.5,0);
  gs.ctx.lineTo(countT*0.5-0.5,0.3);
  gs.ctx.lineTo(countT*0.5-0.5,-0.3);
  gs.ctx.fill();
  gs.writeLine(0,0,0,6,"rgba(0,0,200,0.5)");
  gs.ctx.fillStyle="rgba(0,0,200,0.5)";
  gs.ctx.moveTo(0,6);
  gs.ctx.lineTo(0.3,5);
  gs.ctx.lineTo(-0.3,5);
  gs.ctx.fill();
 
  for(i=0; i<countT ; i++ ) {
	gs.writeLine(i*0.5,gs.dragObjList[i].y,
				 i*0.5+0.5,gs.dragObjList[i+1].y,
				 "rgba(0,0,128,1)");
  }
  for(i=0; i<=countT ; i++ ) {
	gs.writeLine(i*0.5,0,i*0.5,6,"rgba(0,0,0,0.2)");
	gs.dragObjList[i].write(gs.ctx);
  }
  if( vFlg ) {
	gs.writeLine(0,3,0.5*countT+1,3,"rgba(0,200,0,0.5)");
	for( i=1; i<=countT ; i++ ) {
	  var vv=gs.dragObjList[i].y-gs.dragObjList[i-1].y;
	  gs.writeRect(-0.23+i*0.5,3,-0.27+i*0.5,3+vv,"rgba(0,200,0,0.5)");
	}
  }
}
function kasoku() {
	var b=gs.dragObjList[0].y;
	var a=2*(gs.dragObjList[countT].y-b)/ (countT*countT);
	for( i=1 ; i<countT ; i++ ) {
		gs.dragObjList[i].y=a*i*i*0.5+b;
	}
	graphWrite();
}

function makeLinear() {
  var b=gs.dragObjList[0].y;
  var a=(gs.dragObjList[countT].y-b)/ countT;
  var i;
  for( i=1 ; i<countT ; i++ ) {
	gs.dragObjList[i].y=a*i+b;
  }
  graphWrite();
}
function makeRandom() {
  var i;
  for( i=1 ; i<countT ; i++ ) {
	gs.dragObjList[i].y=0.5+Math.random()*5;
  }
  graphWrite();
}
function houbutsu() {
  var i;
  var y1=gs.dragObjList[0].y;
  var y2=gs.dragObjList[countT].y;
  var v=(y2-y1)/ countT;
  for( i=1 ; i<countT ; i++ ) {
	gs.dragObjList[i].y=y1+i*(countT-i)*0.1 + v*i;
  }
  graphWrite();
}

var t=0;
function animeGo() {
  t=0;
  anime();
}
function anime() {
  t+=0.1;
  if( t>= countT ) {
	t=countT;
  }
  var t1=Math.floor(t);
  var t2=t-t1;
  var v;
  if( t==countT ) {
	v=0;
  } else {
	v=gs.dragObjList[t1+1].y-gs.dragObjList[t1].y;
  }
  var yy=gs.dragObjList[t1].y+v*t2;
  graphWrite();
  gs.ctx.beginPath();
  gs.ctx.fillStyle="rgb(130,130,130)";
  gs.ctx.arc(-1,yy,0.15,0,2*Math.PI,false);
  gs.ctx.fill();
  gs.ctx.strokeStyle="rgba(0,120,120,0.5)";
  gs.ctx.beginPath();
  gs.ctx.moveTo(-1,yy);
  gs.ctx.lineTo(t*0.5,yy);
  gs.ctx.stroke();
  if( t== countT ) {
	t=0;
  } else {
	setTimeout("anime();",50);
  }
}
function checkCB() {
	vFlg=document.getElementById('checkV').checked;
	graphWrite();
}
