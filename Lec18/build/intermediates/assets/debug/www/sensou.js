var A0=100;
var B0=50;

function restart() {
    time=0;
	A=A0;
	B=B0;
	$('#A0').html(A0);
	$('#B0').html(B0);
	$('#A0B0sq').html(A0*A0-B0*B0);
	gclear();
}

var time;

function writeNow(x,dx,startx,col,ct) {
	var i;
	var i10;
	var i1;
	var r=hy*0.05;
	ct.fillStyle=col;
	ct.strokeStyle=col;
	i10=Math.floor(x*0.1);
	i1=x-i10*10;
	ct.beginPath();
	for( i=0; i< i10; i++ ) {
		ct.rect(startx+(i*2+0.1)*r,0,0.8*r,hy);
	}
	ct.rect(startx+(i10*2+0.1)*r,hy-i1*2*r,0.8*r,i1*2*r);
	ct.fill();
	ct.beginPath();
	ct.fillStyle="rgb(100,100,100)";
	ct.rect(startx+(i10*2+0.1)*r,hy-(i1+dx)*2*r,0.8*r,dx*2*r);
	ct.fill();
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
function fightGO() {
	var dB;
	var dA;
	time++;
	if( B>0 && A>0 ) {
		dB=-A*0.005;
		dA=-B*0.005;
		A += dA;
		B += dB;
	}
	if( A<0 ) { A=0;}
	if( B<0 ) { B=0;}
    ctx.clearRect(0, 0, wx, hy);
    ctx.fillStyle="rgb(250,250,255)";
    ctx.fillRect(0,0,wx,hy);
	writeNow(A,-dA,0,"rgb(255,0,0)",ctx);
	writeNow(B,-dB,wx*0.5,"rgb(0,0,255)",ctx);
	$('#A').html(marume4(A));
	$('#B').html(marume4(B));
	$('#A0B0sqnow').html(marume4(A*A-B*B));

	ctx2.beginPath();
	ctx2.strokeStyle="rgba(255,0,0,0.5)";
	ctx2.moveTo(time,hy);
	ctx2.lineTo(time,hy-0.01*A*hy);
	ctx2.stroke();
	ctx2.beginPath();
	ctx2.strokeStyle="rgba(0,0,255,0.3)";
	ctx2.moveTo(time,hy);
	ctx2.lineTo(time,hy-0.01*B*hy);
	ctx2.stroke();


    setTimeout('fightGO() ',125);
    // アニメーションは１秒８コマなので、125ミリ秒間隔で呼び出す。
}
var A,B;

var canvas;
var gcanvas;
var ctx;
var wx,hy;
function init() {
    time=0;
	A=A0;
	B=B0;
    canvas = document.getElementById("maincanvas");
	ctx=canvas.getContext("2d");
	wx=document.body.clientWidth*0.9;
	canvas.width=wx;
	hy=wx*0.5;
	canvas.height=hy;
	gcanvas = document.getElementById("subcanvas");
	gcanvas.width=hy;
	gcanvas.height=hy;
	ctx2=gcanvas.getContext("2d");
	restart();
	gclear();
	fightGO();
}
var ctx2;
function gclear() {
	ctx2.fillStyle="rgb(100,255,255)";
	ctx2.fillRect(0,0,gcanvas.width,gcanvas.height);
	ctx2.strokeStyle="rgb(0,0,0)";
	ctx2.beginPath();
	ctx2.moveTo(0,gcanvas.height/2);
	ctx2.lineTo(gcanvas.width,gcanvas.height/2);
	ctx2.stroke();
	ctx2.strokeStyle="rgb(100,100,100)";
	ctx2.beginPath();
	ctx2.moveTo(0,gcanvas.height/4);
	ctx2.lineTo(gcanvas.width,gcanvas.height/4);
	ctx2.moveTo(0,3*gcanvas.height/4);
	ctx2.lineTo(gcanvas.width,3*gcanvas.height/4);
	ctx2.stroke();
	ctx2.strokeStyle="rgb(200,200,200)";
	ctx2.beginPath();
	ctx2.moveTo(0,gcanvas.height/8);
	ctx2.lineTo(gcanvas.width,gcanvas.height/8);
	ctx2.moveTo(0,3*gcanvas.height/8);
	ctx2.lineTo(gcanvas.width,3*gcanvas.height/8);
	ctx2.moveTo(0,5*gcanvas.height/8);
	ctx2.lineTo(gcanvas.width,5*gcanvas.height/8);
	ctx2.moveTo(0,7*gcanvas.height/8);
	ctx2.lineTo(gcanvas.width,7*gcanvas.height/8);

	ctx2.stroke();
}
