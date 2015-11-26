var gs;
var gs2;
var gs3;
var a=0;
var x=1;
var px0flg=false;
var px1flg=false;
var fimg=new Image();
var tildefimg=new Image("tildef.img");
var xpximg=new Image();
var animeCount=0;
$(document).delegate('#page1','pageinit',function(e) {
    gs=new GraphScreen("canvas1",6,5,0.45);
    gs.setLeft(-3);
    gs.setBottom(-1);
    gs.gobackOriginalSize();
    gs.setxlabel("x.png");
    gs.setylabel("f.png");
    gs2=new GraphScreen("canvas2",6,5,0.45);
    gs2.setLeft(-3);
    gs2.setBottom(-1);
    gs2.gobackOriginalSize();
    gs2.setylabel("f.png");
    gs2.setxlabel("px.png");
    gs.writeGraphs=function() {
		graph1WriteContent();
    }    
    gs2.writeGraphs=function() {
		graph2WriteContent();
    }
});
$(document).delegate('#page1','pageshow',function(e) {
    a=Number($('#slider').val());
    graphWrite();
});
function graphWrite() {
    graph1Write();
    graph2Write();
    if( animeCount >= 0 ) {
		animeCount++;
		if( animeCount >100 ) {
			animeCount=0;
		}
		setTimeout("graphWrite();",50);
    }
}

$(document).delegate('#page3','pageinit',function(e) {
    tildefimg.src="tildef.png";
    fimg.src="f.png";
    xpximg.src="xpx.png";
    gs3=new GraphScreen("canvas3",6,5,0.45);
    gs3.setLeft(-3);
    gs3.setBottom(-2);
    gs3.gobackOriginalSize();
    gs3.setxlabel("x.png");
    gs3.setylabel("f.png");
    gs3.writeGraphs=function() {
		graph3WriteContent();
    }
    gs4=new GraphScreen("canvas4",6,5,0.45);
    gs4.setW(6);
    gs4.setLeft(-3);
    gs4.setH(5);
    gs4.setBottom(-2);
    gs4.gobackOriginalSize();
    gs4.setxlabel("px.png");
    gs4.setylabel("ftildef.png");
    gs4.writeGraphs=function() {
		graph4Write();
    }
});

$(document).delegate('#page3','pageshow',function(e) {
    a=Number($('#slider_a').val());
    x=Number($('#slider_x').val());
    graphWrite3();
});
function graphWrite3() {
    graph3Write();
    graph4Write();
}
function graph4Write() {
	gs4.setScale();
	gs4.clearALL();	
	gs4.writeCoordinate();
	graph4WriteContent();
}
function graph4WriteContent() {
    gs4.setFunction(tildef);
    gs4.writeFunction("rgba(0,0,255,0.5)");
    gs4.setFunction(fp);
    gs4.writeFunction("rgba(255,0,0,0.5)");
    gs4.writePoint(2*(x-a),-(x-a)*(x+a),0.05,"rgba(0,0,255,1)");
}
function graph3Write() {
    gs3.setScale();
    gs3.clearALL();	
    gs3.writeCoordinate();
    graph3WriteContent();
}

function graph3WriteContent() {
    gs3.setFunction(f);
    gs3.writeFunction("rgba(255,0,0,0.5)");
    gs3.writeLinear(2*(x-a),-(x-a)*(x+a),"rgba(128,128,0,1)");
    gs3.writePoint(0,-(x-a)*(x+a),0.05,"rgba(0,0,255,1)");
    gs3.ctx.drawImage(tildefimg,-0.2,-(x-a)*(x+a),0.204,0.404);
    gs3.writeLine(0,(x-a)*(x-a),x,(x-a)*(x-a),"rgba(120,120,120,0.5)");
    gs3.writePoint(0,(x-a)*(x-a),0.05,"rgba(60,60,60,1)");
    gs3.ctx.drawImage(fimg,-0.2,(x-a)*(x-a),0.176,0.320);
    gs3.ctx.strokeStyle="rgba(120,120,120,0.5)";
    gs3.ctx.beginPath();
    gs3.ctx.moveTo(0,(x-a)*(x-a));
    gs3.ctx.quadraticCurveTo(-0.5,-(x-a)*a,0,-(x-a)*(x+a))
    gs3.ctx.stroke();
    gs3.ctx.drawImage(xpximg,-1.2,-(x-a)*a,0.768,0.224);
}
function graph2Write() {
    gs2.setScale()
    gs2.clearALL();	
    gs2.writeCoordinate();
    graph2WriteContent();
}
function graph2WriteContent() {
    gs2.setFunction(fp);
    gs2.writeFunction("rgba(255,0,0,0.5)");
    if(px0flg ) {
		gs2.writePoint(0,0,0.05,"rgba(128,0,128,1)");
    }
    if(px1flg ) {
		gs2.writePoint(1,0.25,0.05,"rgba(0,128,128,1)");
    }
    if( animeCount >= 0 ) {
		var xx;
		if( animeCount > 50 ) {
			xx=(75-animeCount)/12.5
		} else {
			xx=(animeCount-25)/12.5;
		}
		gs2.writePoint(xx,xx*xx*0.25,0.05,"rgba(128,128,0,1)");	
    }
}
function graph1Write() {
    gs.setScale();
    gs.clearALL();	
    gs.writeCoordinate();
    graph1WriteContent();
}

function graph1WriteContent() {
    gs.setFunction(f);
    gs.writeFunction("rgba(255,0,0,0.5)");
    if(px0flg ) {
		gs.writeLinear(0,0,"rgba(128,0,128,1)");	
		gs.writePoint(a,0,0.05,"rgba(128,0,128,1)");
    }
    if(px1flg ) {
		gs.writeLinear(1,-a-0.25,"rgba(0,128,128,1)");
		gs.writePoint(Number(a)+0.5,0.25,0.05,"rgba(0,128,128,1)");
    }
    if( animeCount >= 0 ) {
		var xx;
		if( animeCount > 50 ) {
			xx=(75-animeCount)/12.5;
		} else {
			xx=(animeCount-25)/12.5;
		}
		var yy=xx*xx*0.25;
		gs.writePoint(a+xx*0.5,yy,0.05,"rgba(128,128,0,1)");
		gs.writeLinear(xx,-xx*(a+xx*0.25),"rgba(128,128,0,1)");
    }
}


function tildef(x) {
    return -0.25*x*x-a*x;
}
function f(x) {
    return (x-a)*(x-a);
}
function fp(x) {
    return x*x*0.25;
}

function onanime() {
    if( $("#animeon").prop("checked") ) {
		animeCount=0;	
    } else {
		animeCount=-1;
    }
    graphWrite();
}
function  onpx0change() {
    px0flg=$("#checkboxpxzero_0").prop("checked");
    if( animeCount <0 ) {
		graphWrite();	
    }
}
function onpx1change() {
    px1flg=$("#checkboxpxone_0").prop("checked");
    if( animeCount <0 ) {
		graphWrite();
    }
}
var gsUF;
var gsUF2;

$(document).delegate('#UtoF','pageinit',function(e) {
    tildefimg.src="U.png";
    fimg.src="largeF.png";
    xpximg.src="TS.png";
    gsUF=new GraphScreen("canvasUF",4,6,0.45);
    gsUF.setLeft(-1);
    gsUF.setBottom(-3);
    gsUF.gobackOriginalSize();
    gsUF.setxlabel("S.png");
    gsUF.setylabel("U.png");
    gsUF.writeGraphs=function() {
		graphUFWriteContent();
    }
    gsUF2=new GraphScreen("canvasUF2",4,6,0.45);
    gsUF2.setLeft(-1);
    gsUF2.setBottom(-3);
    gsUF2.gobackOriginalSize();
    gsUF2.setxlabel("T.png");
    gsUF2.setylabel("largeF.png");
    gsUF2.writeGraphs=function() {
		graphUF2Write();
    }
});

var t0=1;
var v0=1;
$(document).delegate('#UtoF','pageshow',function(e) {
    t0=Number($('#slider_T').val());
    v0=Number($('#slider_V').val());
    graphUFWrite();
});


function graphUFWrite() {
    gsUF.setScale();
    gsUF.clearALL();	
    gsUF.writeCoordinate();
    graphUFWriteContent();
    graphUF2Write();
}

function U_T(t) {
	return t;
}
function U_S(s) {
    return Math.exp(s-1)/Math.pow(v0,1/1.5);
}

// U(s)の接線
// U'(s)=exp(s-1)/V^(1/c)
// U-exp(s0-1)=exp(s0-1)*(s-s0)
// U=exp(s0-1)*s -(s0-1)*exp(s0-1)

// U→Fというルジャンドル変換
// t=exp(s-1);
// s=log(t)+1;
// F(t)=U-TS=t-t*(log(t)+log(v)/1.5+1)
// F(s)=exp(s-1)*(1-s)/V^(1/1.5)
// F'(t)=1-(log(t)+log(v)/1.5+1)-1=-\log(t)-\log(v)/1.5-1
function F_T(t) {
    if( t==0 ) {
        return 0;
    }
    return t-t*(Math.log(t)+ Math.log(v0)/1.5 +1);
}
function F_S(s) {
	return Math.exp(s-1)*(1-s)/Math.pow(v0,1/1.5);
}

function graphUFWriteContent() {
    gsUF.setFunction(U_S);
    gsUF.writeFunction("rgba(255,0,0,0.5)");
    gsUF.setFunction(F_S);
    gsUF.writeFunction("rgba(0,0,255,0.1)");
	if( t0 == 0 ) {
		gsUF.writeLinear(0,0,"rgba(128,128,0,1)");
		gsUF.ctx.drawImage(fimg,-0.2,0,0.176,0.20);
		gsUF.ctx.drawImage(tildefimg,-0.4,0,0.204,0.204);
		gsUF.writePoint(0,0,0.05,"rgba(255,0,255,0.8)");
	} else {
		var vf=Math.pow(v0,1/1.5);
		var u0=t0;
		var f0=F_T(t0);
		var s0=(u0-f0)/t0;
		gsUF.writeLinear(u0,-(s0-1)*u0,"rgba(128,128,0,1)");
		gsUF.writePoint(0,-(s0-1)*u0,0.05,"rgba(0,0,255,0.5)");
		gsUF.ctx.drawImage(tildefimg,-0.2,u0,0.204,0.204);
		gsUF.writeLine(0,u0,s0,u0,"rgba(120,120,120,0.5)");
		gsUF.writeLine(s0,gsUF.topy,s0,gsUF.bottomy,"rgba(120,120,120,0.1)");
		gsUF.writeLine(s0,f0,0,f0,"rgba(120,120,120,0.5)");
		gsUF.writePoint(0,u0,0.05,"rgba(255,0,0,0.5)");
		gsUF.ctx.drawImage(fimg,-0.2,-(s0-1)*u0,0.176,0.20);
		gsUF.ctx.strokeStyle="rgba(120,120,120,0.5)";
		gsUF.ctx.beginPath();
		gsUF.ctx.moveTo(0,u0);
		gsUF.ctx.quadraticCurveTo(-0.5,(-0.5*s0+1)*u0,0,-(s0-1)*u0)
		gsUF.ctx.stroke();
		gsUF.ctx.drawImage(xpximg,-0.8,(-0.5*s0+1)*u0,0.5,0.224);
	}
}

function graphUF2Write() {
	gsUF2.setScale();
	gsUF2.clearALL();	
	gsUF2.writeCoordinate();
	graphUF2WriteContent();
}

function graphUF2WriteContent() {
    gsUF2.setFunction(F_T);
    gsUF2.writeFunction("rgba(0,0,255,0.5)");
    gsUF2.setFunction(U_T);
    gsUF2.writeFunction("rgba(255,0,0,0.1)");

	if( t0 == 0 ) {
		// 縦線を引く
		gsUF2.ctx.strokeStyle="rgba(128,128,0,1)";
		gsUF2.ctx.beginPath();
		gsUF2.ctx.moveTo(0,gsUF2.topy);
		gsUF2.ctx.lineTo(0,gsUF2.bottomy);
		gsUF2.ctx.stroke();
		gsUF2.writePoint(0,0,0.05,"rgba(255,0,255,0.8)");
		gsUF2.ctx.drawImage(fimg,-0.2,0,0.176,0.20);
		gsUF2.ctx.drawImage(tildefimg,-0.4,0,0.204,0.204);
	} else {
		var u0=t0;
		var f0=F_T(t0);
		var s0=(u0-f0)/t0;
		gsUF2.writePoint(0,f0,0.05,"rgba(0,0,255,0.5)");
		gsUF2.writeLinear(-Math.log(t0)-Math.log(v0)/1.5-1,t0,"rgba(128,128,0,1)");
		gsUF2.writeLine(0,f0,t0,f0,"rgba(120,120,120,0.5)");
		gsUF2.writeLine(t0,gsUF2.topy,t0,gsUF2.bottomy,"rgba(120,120,120,0.1)");
		gsUF2.writeLine(t0,t0,0,t0,"rgba(120,120,120,0.5)");
		gsUF2.writePoint(0,t0,0.05,"rgba(255,0,0,0.5)");
		gsUF2.ctx.strokeStyle="rgba(120,120,120,0.5)";
		gsUF2.ctx.beginPath();
		gsUF2.ctx.moveTo(0,t0);
		gsUF2.ctx.quadraticCurveTo(-0.5,(-0.5*s0+1)*t0,0,-(s0-1)*t0)
		gsUF2.ctx.stroke();
		gsUF2.ctx.drawImage(xpximg,-0.8,(-0.5*s0+1)*t0,0.5,0.224);
		gsUF2.ctx.drawImage(fimg,-0.2,-(s0-1)*t0,0.176,0.20);
		gsUF2.ctx.drawImage(tildefimg,-0.2,t0,0.204,0.204);
	}
}
