// JavaScript Document
var wx;
var screenw;
var hy;
var N;
var canvas;

var x0,x1,y0,y1;


// xxとyyは、スクリーン座標からグラフのx,y座標を得る。
function xx(x) {
    return x0+x*(x1-x0)/wx;
}

function yy(y) {
    return y0+(hy-y)*(y1-y0)/hy;
}

// sxとsyは、グラフの座標からスクリーン座標を得る。
function sy(y) {
    return hy - hy*(y-y0)/(y1-y0);
// Y=hy - hy*(y-y0)/(y1-y0)
// hy -Y = hy*(y-y0)/(y1-y0)
// (hy -Y)*(y1-y0)/hy = y-y0
// y0+(hy-Y)*(y1-y0)/hy =y
}

function sx(x) {
    return wx*(x-x0)/(x1-x0);
// X=wx*(x-x0)/(x1-x0)
// X*(x1-x0)=wx*(x-x0)
// X*(x1-x0)/wx=x-x0
// x0+X*(x1-x0)/wx=x
}

var mode;

function drawCoordinate(ctx) {
    ctx.beginPath();
    ctx.moveTo(0, sy(0));
    ctx.lineTo(wx - 1, sy(0));
    ctx.lineTo(wx - 1, sy(0));
    ctx.lineTo(wx - 21, sy(0) - 10);
    ctx.moveTo(wx - 1, sy(0));
    ctx.lineTo(wx - 21, sy(0) + 10);
    ctx.moveTo(sx(0), 0);
    ctx.lineTo(sx(0), hy);
    ctx.moveTo(sx(0)- 10, 20);
    ctx.lineTo(sx(0), 0);
    ctx.lineTo(sx(0)+10, 20);
    ctx.strokeStyle = "black";
    ctx.stroke();
}

function drawGraph(ctx) {
    ctx.beginPath();
    ctx.moveTo(0, sy(f(xx(0))));
    for(var i = 1; i < wx; i++) {
	ctx.lineTo(i, sy(f(xx(i))));
    }
    ctx.strokeStyle = "red";
    ctx.stroke();


    var nowx=animeTimer+sx(0);
    var stepx=(wx-sx(0))/N;
    var i;
    var snowx,snowy;

    V=0;
	

    ctx.fillStyle="rgba(0,255,255,0.5)"
    for( i=0 ; (i+1)*stepx < animeTimer ; i++ ) {
	snowx=sx(0)+stepx*i;
	if( kaidan2Flg !=0 ) {
	    snowy=sy(f(xx(snowx+stepx)));
	} else {
    	    snowy=sy(f(xx(snowx)));
	}
	if( nuriFlg != 0 ) {
	    ctx.fillRect(snowx,snowy,stepx,sy(0)-snowy);	    
	}
	V += stepx*(sy(0)-snowy);
    }
    snowx=sx(0)+stepx*i;
    if( kaidan2Flg !=0 ) {
	F=f(xx(snowx+stepx));
    } else {
	F=f(xx(snowx));
    }
    snowy=sy(F);
    if( nuriFlg != 0 ) {
	ctx.fillRect(snowx,snowy,animeTimer-stepx*i,sy(0)-snowy);
    }
    V += (animeTimer-stepx*i)*(sy(0)-snowy);

    if( nuriFlg != 0 ) {
	for( i=0 ; i< N ; i++ ) {
	    snowx=sx(0)+stepx*i;
	    if( kaidan2Flg != 0 ) {
		snowy=sy(f(xx(snowx+stepx)))
	    } else {
		snowy=sy(f(xx(snowx)));
	    }
	    ctx.strokeStyle="rgba(0,0,0,0.5)"
	    ctx.strokeRect(snowx,snowy,stepx,sy(0)-snowy);
	}
    }
    ctx.beginPath();
    ctx.moveTo(nowx,0);
    ctx.lineTo(nowx,hy);
    ctx.strokeStyle="rgba(200,200,0,0.8)";
    ctx.stroke();
}
var F;
var V;

function drawimage(ctx,filename,x,y,w,h) {
    var image=new Image();
    image.src=filename;
    image.onload=function(){
	ctx.drawImage(image,x,y,w,h);
    };
}

var animeTimer;
var autoUpFlg;
function animationGO() {
    animeTimer += (wx-sx(0))*0.01;
    drawALL();
    if( animeTimer >= wx-sx(0) ) {
	animeTimer =0;
	if( autoUpFlg != 0) {
	    N++;
	    if( N>50 ) {
		N=5;
	    }
	}
    }
    setTimeout('animationGO()',100);	
}

var f;
function init() {
    canvas = document.getElementById("maincanvas");
    screenw=document.body.clientWidth*0.9;
    hy=document.body.clientHeight*0.9;
    if( hy > screenw*0.6666666 ) {
	hy=screenw*0.666666;
    } else {
	screenw=1.5*hy;
    }
    canvas.width=screenw;
    canvas.height=hy;
    wx=0.5*screenw;
    
    initCoordinate();
    nuriFlg=0;
    kaidan2Flg=0;
    autoUpFlg=0;
    N=5;
    a=0.3;
    b=0.8;
    c=3;
    furoFlg=1;
    f=linear;
    Intf=Intlinear;
    animeTimer=0;
}
var Intf;
var a;
var b;
var c;
function constant(x) {
    return c;
}

function Intconstant(x) {
    return c*x;
}

function linear(x) {
    return b*x+c;
}

function Intlinear(x) {
    return 0.5*b*x*x+c*x;
}

function houbutsu(x) {
    return a*x*x+b*x+c;
}

function Inthoubutsu(x) {
    return a*x*x*x/3+0.5*b*x*x+c*x;
}


function initCoordinate() {
    x0=-0.2;
    x1=1.8;
    y0=-0.2;
    y1=1.3;
    // グラフでの１が、canvasサイズの0.6倍になるようにする。
    //mx1 = 0.5;
    //mx2 = 1;
}

var furoFlg;

function drawALL() {
    var ct = canvas.getContext("2d");
    ct.clearRect(0, 0, screenw, hy);
    ct.fillStyle = "white";
    ct.fillRect(0, 0, screenw, hy);
    drawGraph(ct);
    drawCoordinate(ct);
    if( furoFlg !=0 ) {
	drawFuro(ct);
    }
}

function drawFuro(ctx) {
    ctx.fillStyle="black";
    ctx.beginPath();
    ctx.moveTo(1.2*wx,0.85*hy);
    ctx.lineTo(1.8*wx,0.85*hy);
    ctx.lineTo(1.8*wx,0.4*hy);
    ctx.lineTo(1.85*wx,0.4*hy);
    ctx.lineTo(1.85*wx,0.9*hy);
    ctx.lineTo(1.15*wx,0.9*hy);
    ctx.lineTo(1.15*wx,0.4*hy);
    ctx.lineTo(1.2*wx,0.4*hy);
    ctx.closePath();
    ctx.fill();

    ctx.fillStyle="gray";

    ctx.beginPath();
    ctx.moveTo(1.45*wx,0.2*hy);
    ctx.lineTo(1.55*wx,0.2*hy);
    ctx.lineTo(1.55*wx,0.15*hy);
    ctx.lineTo(1.8*wx,0.15*hy);
    ctx.lineTo(1.8*wx,0.1*hy);
    ctx.lineTo(1.45*wx,0.1*hy);
    ctx.closePath();
    ctx.fill();

    ctx.strokeStyle="black"
    ctx.strokeRect(1.8*wx,0.05*hy,0.18*wx,0.18*wx);
    ctx.fillStyle="red";
    ctx.fillRect(1.885*wx,0.05*hy,0.01*wx,0.02*wx);
    ctx.beginPath();
    var theta=F*5;
    var cos=Math.cos(theta);
    var sin=Math.sin(theta);
    ctx.fillStyle="rgb(150,150,200)"
    ctx.moveTo(1.89*wx-0.007*wx*cos,0.05*hy+0.09*wx-0.007*wx*sin);
    ctx.lineTo(1.89*wx+0.08*wx*sin-0.004*wx*cos,0.05*hy+0.09*wx-0.08*wx*cos-0.004*wx*sin);
    ctx.lineTo(1.89*wx+0.08*wx*sin+0.004*wx*cos,0.05*hy+0.09*wx-0.08*wx*cos+0.004*wx*sin);
    ctx.lineTo(1.89*wx+0.007*wx*cos,0.05*hy+0.09*wx+0.007*wx*sin);
    ctx.closePath();
    ctx.fill();
    ctx.beginPath();
    ctx.fillStyle="rgb(200,200,200)";
    ctx.arc(1.89*wx,0.05*hy+0.09*wx,0.01*wx,0,2*Math.PI,true);
    ctx.fill();

    ctx.fillStyle="cyan";

    var v=F*wx*0.03;
    if( v>0 ) {
	ctx.fillRect(1.5*wx-v,0.2*hy,2*v,0.65*hy);
    }
    var H=V/(0.6*wx);
    if( H< 0.47*hy ) {
	ctx.fillRect(1.2*wx,0.85*hy-H,0.6*wx,H);	
    } else {
	H=0.47*hy;
	ctx.fillRect(1.2*wx,0.38*hy,0.6*wx,H);	
	ctx.fillRect(1.15*wx,0.38*hy,0.7*wx,0.02*hy);	
	ctx.fillRect(1.15*wx-v,0.38*hy,v,0.62*hy);	
	ctx.fillRect(1.85*wx,0.38*hy,v,0.62*hy);	
    }
}

function reset() {
    animeTimer=0;
    drawALL();
}

function cdown() {
    if( c>0) {
	c -=0.05;
    }
    c*=100;
    c=Math.round(c);
    c/=100;
    reset();
}

function cup() {
    if( c<1) {
	c +=0.05;
    }
    c*=100;
    c=Math.round(c);
    c/=100;
    reset();
}

function bdown() {
    if( b>0) {
	b -=0.05;
    }
    b*=100;
    b=Math.round(b);
    b/=100;
    reset();
}

function bup() {
    if( b<1) {
	b +=0.05;
    }
    b*=100;
    b=Math.round(b);
    b/=100;
    reset();
}

function adown() {
    if( a>0) {
	a -=0.05;
    }
    a*=100;
    a=Math.round(a);
    a/=100;
    reset();
}

function aup() {
    if( a<1) {
	a +=0.05;
    }
    a*=100;
    a=Math.round(a);
    a/=100;
    reset();
}
var kaidan2Flg;
var nuriFlg;

function Ndown() {
    if( N>1) {
	N--;
    }
    reset();
}

function Nup() {
    if( N<500) {
	N++;
    }
    reset();
}