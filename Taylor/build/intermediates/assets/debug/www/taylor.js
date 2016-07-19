var gs;
var order=0;
function marume6(x) {
	var xx=x*1000000;
	xx=Math.round(xx);
	xx  /= 1000000;
    if( x- xx === 0 ) {
        return xx;
    } else {
        return xx+"…";
    }
}
function marume4(x) {
	var xx=Math.round(10000*x);
	var pittari="";
	if( xx != x*10000 ) {
		pittari="...";
	}
	xx /= 10000;
    return xx + pittari;
}
function next(funcN,funcString) {
	if( order == 8 ) {
		return;
	}
	order++;
	writeFuncs(funcN,funcString);
}

function writeFuncs(funcN,funcString) {
	document.getElementById("now").innerHTML=order;
	document.getElementById("funcString").innerHTML=funcString(0);
	gs.clearFunction();
	gs.setFunction(function(x) { return funcN(x,0);},"rgba(0,0,255,1)");
	switch(order) {
	case 1:
		gs.setFunction(function(x) { return funcN(x,1);},"rgba(255,0,0,1)");
		break;
	case 2:
		gs.setFunction(function(x) { return funcN(x,1);},"rgba(255,35,20,0.5)");
		gs.setFunction(function(x) { return funcN(x,2);},"rgba(255,0,0,1)");
		break;
	case 3:
		gs.setFunction(function(x) { return funcN(x,1);},"rgba(255,70,40,0.5)");
		gs.setFunction(function(x) { return funcN(x,2);},"rgba(255,35,20,0.5)");
		gs.setFunction(function(x) { return funcN(x,3);},"rgba(255,0,0,1)");
		break;
	case 4:
		gs.setFunction(function(x) { return funcN(x,1);},"rgba(255,105,60,0.5)");
		gs.setFunction(function(x) { return funcN(x,2);},"rgba(255,70,40,0.5)");
		gs.setFunction(function(x) { return funcN(x,3);},"rgba(255,35,20,0.5)");
		gs.setFunction(function(x) { return funcN(x,4);},"rgba(255,0,0,1)");
		break;
	case 5:
		gs.setFunction(function(x) { return funcN(x,1);},"rgba(255,140,80,0.5)");
		gs.setFunction(function(x) { return funcN(x,2);},"rgba(255,105,60,0.5)");
		gs.setFunction(function(x) { return funcN(x,3);},"rgba(255,70,40,0.5)");
		gs.setFunction(function(x) { return funcN(x,4);},"rgba(255,35,20,0.5)");
		gs.setFunction(function(x) { return funcN(x,5);},"rgba(255,0,0,1)");
		break;
	case 6:
		gs.setFunction(function(x) { return funcN(x,1);},"rgba(255,175,100,0.5)");
		gs.setFunction(function(x) { return funcN(x,2);},"rgba(255,140,80,0.5)");
		gs.setFunction(function(x) { return funcN(x,3);},"rgba(255,105,60,0.5)");
		gs.setFunction(function(x) { return funcN(x,4);},"rgba(255,70,40,0.5)");
		gs.setFunction(function(x) { return funcN(x,5);},"rgba(255,35,20,0.5)");
		gs.setFunction(function(x) { return funcN(x,6);},"rgba(255,0,0,1)");
		break;
	case 7:
		gs.setFunction(function(x) { return funcN(x,1);},"rgba(255,210,120,0.5)");
		gs.setFunction(function(x) { return funcN(x,2);},"rgba(255,175,100,0.5)");
		gs.setFunction(function(x) { return funcN(x,3);},"rgba(255,140,80,0.5)");
		gs.setFunction(function(x) { return funcN(x,4);},"rgba(255,105,60,0.5)");
		gs.setFunction(function(x) { return funcN(x,5);},"rgba(255,70,40,0.5)");
		gs.setFunction(function(x) { return funcN(x,6);},"rgba(255,35,20,0.5)");
		gs.setFunction(function(x) { return funcN(x,7);},"rgba(255,0,0,1)");
		break;
	case 8:
		gs.setFunction(function(x) { return funcN(x,1);},"rgba(255,245,140,0.5)");
		gs.setFunction(function(x) { return funcN(x,2);},"rgba(255,210,120,0.5)");
		gs.setFunction(function(x) { return funcN(x,3);},"rgba(255,175,100,0.5)");
		gs.setFunction(function(x) { return funcN(x,4);},"rgba(255,140,80,0.5)");
		gs.setFunction(function(x) { return funcN(x,5);},"rgba(255,105,60,0.5)");
		gs.setFunction(function(x) { return funcN(x,6);},"rgba(255,70,40,0.5)");
		gs.setFunction(function(x) { return funcN(x,7);},"rgba(255,35,20,0.5)");
		gs.setFunction(function(x) { return funcN(x,8);},"rgba(255,0,0,1)");
		document.getElementById("nextbutton").disabled="disabled";
		break;
	}
	setFuncStrings(funcString);
	gs.writeGraph();
}
function setFuncStrings(funcString) {
	switch(order) {
	case 1:
		document.getElementById("funcString1").innerHTML=funcString(1);
		break;
	case 2:
		document.getElementById("funcString1").innerHTML=funcString(2);
		document.getElementById("funcString2").innerHTML=funcString(1);
		break;
	case 3:
		document.getElementById("funcString1").innerHTML=funcString(3);
		document.getElementById("funcString2").innerHTML=funcString(2);
		document.getElementById("funcString3").innerHTML=funcString(1);
		break;
	case 4:
		document.getElementById("funcString1").innerHTML=funcString(4);
		document.getElementById("funcString2").innerHTML=funcString(3);
		document.getElementById("funcString3").innerHTML=funcString(2);
		document.getElementById("funcString4").innerHTML=funcString(1);
		break;
	case 5:
		document.getElementById("funcString1").innerHTML=funcString(5);
		document.getElementById("funcString2").innerHTML=funcString(4);
		document.getElementById("funcString3").innerHTML=funcString(3);
		document.getElementById("funcString4").innerHTML=funcString(2);
		document.getElementById("funcString5").innerHTML=funcString(1);
		break;
	case 6:
		document.getElementById("funcString1").innerHTML=funcString(6);
		document.getElementById("funcString2").innerHTML=funcString(5);
		document.getElementById("funcString3").innerHTML=funcString(4);
		document.getElementById("funcString4").innerHTML=funcString(3);
		document.getElementById("funcString5").innerHTML=funcString(2);
		document.getElementById("funcString6").innerHTML=funcString(1);
		break;
	case 7:
		document.getElementById("funcString1").innerHTML=funcString(7);
		document.getElementById("funcString2").innerHTML=funcString(6);
		document.getElementById("funcString3").innerHTML=funcString(5);
		document.getElementById("funcString4").innerHTML=funcString(4);
		document.getElementById("funcString5").innerHTML=funcString(3);
		document.getElementById("funcString6").innerHTML=funcString(2);
		document.getElementById("funcString7").innerHTML=funcString(1);
		break;
	case 8:
		document.getElementById("funcString1").innerHTML=funcString(8);
		document.getElementById("funcString2").innerHTML=funcString(7);
		document.getElementById("funcString3").innerHTML=funcString(6);
		document.getElementById("funcString4").innerHTML=funcString(5);
		document.getElementById("funcString5").innerHTML=funcString(4);
		document.getElementById("funcString6").innerHTML=funcString(3);
		document.getElementById("funcString7").innerHTML=funcString(2);
		document.getElementById("funcString8").innerHTML=funcString(1);
		break;
	}
}

function clearf(funcN,funcString) {
	order=0;			
	writeFuncs(funcN,funcString);
	document.getElementById("nextbutton").disabled="";
	document.getElementById("funcString1").innerHTML="";
	document.getElementById("funcString2").innerHTML="";
	document.getElementById("funcString3").innerHTML="";
	document.getElementById("funcString4").innerHTML="";
	document.getElementById("funcString5").innerHTML="";
	document.getElementById("funcString6").innerHTML="";
	document.getElementById("funcString7").innerHTML="";
	document.getElementById("funcString8").innerHTML="";
}

var PointY=function(rr,cc) {
	this.r=rr;
	this.col=cc;
	this.write=function(ct) {
		ct.fillStyle=this.col;
		ct.beginPath();
		ct.moveTo(this.x,this.y+0.3*this.r);
		ct.lineTo(this.x+this.r,this.y);
		ct.lineTo(this.x,this.y-0.3*this.r);
		ct.lineTo(this.x-this.r,this.y);
		ct.closePath();
		ct.fill();
	};
	this.setXY=function(xx,yy) {
		this.x=xx;
		this.y=yy;
	};
};
var PointX=function(xx,yy,rr,c,x0,x1) {
	this.y=0;
	DraggablePoint.call(this,xx,yy,rr,c); // 親クラスのコンストラクタを呼ぶ。DraggablePointはプロトタイプを使わなかったのでこれでいい。
	this.setRange=function(xx0,xx1) {
		this.rangex0=xx0;
		this.rangex1=xx1;
	};
	this.setRange(x0,x1);
	this.setXY=function(xx,yy) {
		// y座標を動かす必要はないので上書き。
		if( xx < this.rangex0 ) {
			this.x =this.rangex0;
		} else if( xx > this.rangex1 ) {
			this.x = this.rangex1;
		} else {
			this.x=Math.round(xx*50)/50;
		}
	};
	this.write=function(ct) {
		ct.fillStyle=this.col;
		ct.beginPath();
		ct.moveTo(this.x,this.y+this.r);
		ct.lineTo(this.x+0.3*this.r,this.y);
		ct.lineTo(this.x,this.y-this.r);
		ct.lineTo(this.x-0.3*this.r,this.y);
		ct.closePath();
		ct.fill();
	};
};
// 親クラスタのプロトタイプを継承する。
PointX.prototype=Object.create(DraggablePoint.prototype);
PointX.prototype.constructor=PointX;
