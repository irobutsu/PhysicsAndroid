var nu;
var N;
var sqN;
var r;
var w1;

var ratio;
var startBq;

var nu2;
var r2;
var w2;

var ratio2;
var startBq2;

function restart() {
    time=0;
	gclear();
    setN(N);
}
function setBq(b) {
    startBq=b;
    ratio=b/N;
    var bqst=document.getElementById("bqst");
    if( bqst != null ) {
		bqst.innerHTML="最初は"+N+"個の粒子があり、"+b+"ベクレルでした。";
    }
}

var time;
function radGO() {
    var i;
    // 1/8秒の生存率は、1秒の生存率の1/8乗。つまりルート三回。
    var rat;
    if( ratio >1 ) {
		rat=2;
    } else {
		rat =1-Math.sqrt(Math.sqrt(Math.sqrt(1-ratio)));
    }
    var ikinokori=0;
    for(i=0 ; i<N ; i++) {
		if(nu[i].live) {
			if(Math.random()<rat ) {
				nu[i].live=false;
				nu[i].t=0;
			} else {
				ikinokori++;
			}
		}
    }
    var ctx = canvas.getContext("2d");
    drawALL(ctx,"rgb(255,50,50)","rgb(50,255,50)",canvas.width,canvas.height,
			N,sqN,w1,nu,r);
    var bqnow=document.getElementById("bqnow");
    if( bqnow != null ) {
		bqnow.innerHTML="今は"+ikinokori+"個の粒子があり、"+ikinokori*startBq/N+"ベクレルです。";
    }
    time+=1;
    if( time %8 == 0 ) {
		var ctx2=gcanvas.getContext("2d");
		ctx2.beginPath();
		ctx2.strokeStyle="rgb(255,0,0)";
		ctx2.moveTo(time/8,gcanvas.height);
		ctx2.lineTo(time/8,gcanvas.height*(1-ikinokori/N));
		ctx2.stroke();
    }
    setTimeout('radGO() ',125);
    // アニメーションは１秒８コマなので、125ミリ秒間隔で呼び出す。
}

function drawALL(ctx,liveStyle,deadStyle,wx,hy,NN,sqNN,wN,nuN,rN) {
    var i; 
    ctx.clearRect(0, 0, wx, hy);
    ctx.fillStyle="rgb(250,250,255)";
    ctx.fillRect(0,0,wx,hy);
    for(i=0 ; i<NN ; i++) {
		ctx.beginPath();
		var cx=0.2*wx+wN*(i%sqNN+0.5);
		var cy=0.2*wx+wN*(Math.floor(i/sqNN)+0.5);
		ctx.arc(cx,cy,rN,0,2*Math.PI,true);
		ctx.closePath();
		if( nuN[i].live ){
			ctx.fillStyle=liveStyle;
			ctx.fill();
		} else {
			ctx.fillStyle=deadStyle;
			ctx.fill();
		}	
    }
    for(i=0 ; i<NN ; i++) {
		if( nuN[i].live ){
		} else {
			var t1=nuN[i].t;
			if( t1>=0) {
				var cx=0.2*wx+wN*(i%sqNN+0.5);
				var cy=0.2*wx+wN*(Math.floor(i/sqNN)+0.5);
				var t0=t1-1;
				if( t0 <0 ) {
					t0=0;
				}
				ctx.beginPath();
				var ex=cx+nuN[i].vx*t0;
				var ey=cy+nuN[i].vy*t0;
				if( (ex < 0 || ex >wx ) && (ey<0 || ey>hy) ) {
					nuN[i].t=-1;
				} else {
					ctx.moveTo(ex,ey);
					ctx.lineTo(cx+nuN[i].vx*t1,cy+nuN[i].vy*t1);
					ctx.strokeWidth=10;
					ctx.strokeStyle="rgb(230,230,0)";
					ctx.stroke();
					nuN[i].t += 0.2;
				}
			}
		}
    }
}


function setN(n) {
    var wx=canvas.width;
    N=n;
    nu=[];
    var i;
    for(i=0 ; i<N ;i++ ) {
		nu[i]= new Object();
		nu[i].live=true;
		var theta=Math.random()*2*Math.PI;
		nu[i].vx=100*Math.cos(theta);
		nu[i].vy=100*Math.sin(theta);
		nu[i].t=-1;
    }
    sqN=Math.floor(Math.sqrt(N));
    if( sqN*sqN != N ) {
		sqN ++;
    }
    w1=0.6*wx/sqN;
    r=(w1-2)*0.5;
}

var canvas;
var gcanvas;
function init() {
    time=0;
    canvas = document.getElementById("maincanvas");
    if( canvas != null ) {
		setN(100);	
    }
	gcanvas = document.getElementById("subcanvas");
	gclear();
}
function gclear() {
	var ctx2=gcanvas.getContext("2d");
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
