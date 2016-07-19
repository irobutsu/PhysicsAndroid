// JavaScript Document
// グラフを描くキャンバスです。
var RescaleScreen=function(canvas_string,ww,hh,adjustW,syx) {
    this.canvas = document.getElementById(canvas_string);
    this.ctx=this.canvas.getContext("2d");

	// scaleYX は、x座標とy座標の比率。デフォルトは1。
	if( syx == undefined ) {
		this.o_syx=1;
	}else {
		this.o_syx=syx;
	}
	
	this.o_h=hh;
	this.o_w=ww;
    this.setOriginalSize();

    this.writeGraphs=function() {};
    // これは後で上書き設定する。

	if( adjustW > 0 ) {
		// 画面サイズにあわせてcanvasサイズを変える。
		// 画面横幅のadjustW倍になる。ただし、縦が苦しい
		// 場合はもっと縮む。
		// この機能を使わない場合はadjustWに負の値をセット。

		var wx=document.body.clientWidth*adjustW;
		var hy=document.body.clientHeight*0.7;
		
		if( hy > wx*this.h/this.w ) {
			this.canvas.width=wx;
			this.canvas.height=wx*this.h/(this.w*this.o_syx);
		} else {
			this.canvas.height=hy;
			this.canvas.width=hy*this.w*this.o_syx/this.h;	
		}
	}
};

RescaleScreen.prototype={
	// 以下はオリジナルのパラメータ（標準に戻す時は以下の値に変える）
	o_w:20, // 画面の横サイズ（実際のピクセルとは違う）
	o_h:16, // 縦サイズ（実際の縦サイズはこれではない。これに次のo_syxを掛けたものが実際のサイズになる。
	o_syx:1, // 縦横比（1がデフォルト。y座標が何倍濃いかという指定）
	o_leftwx:-10, // 左壁の座標
	// 右壁の座標は、自動的に「左壁の座標＋横サイズ」になる。
	o_bottomy:-6, // 画面下の座標
	// 画面上の座標は、自動的に「画面下の座標＋縦サイズ*縦横比」になる。
	// html5のcanvasの都合で、x軸は右向き、y軸は下向きであるが、
	// setScale()の中で普通のx,y軸になるようにしている。
	// 以下の描画は全てこの座標系で行います。
	ct:function() { return this.ctx;},
	setW:function(x) { this.o_w=x;},
	setH:function(x) { this.o_h=x;},
	setLeft:function(x) { this.o_leftwx=x; },
	setBottom:function(x) { this.o_bottomy=x; },
	setScaleYX:function(x) {
		this.syx=x;
		this.h=this.o_h*this.syx/this.o_syx;
		this.topy=this.bottomy+this.h;
	},
	setOriginalSize:function() {
		this.w=this.o_w;
		this.h=this.o_h;
		this.leftwx=this.o_leftwx;
		this.rightwx=this.o_leftwx+this.o_w;
		this.bottomy=this.o_bottomy;
		this.topy=this.o_bottomy+this.o_h;
		this.syx=this.o_syx;
	},
	// ズームイン／アウトした後に元の状態に戻す。
	gobackOriginalSize:function() {
		this.setOriginalSize();   
		this.setScale();
	},
	// 全体のスケールをk倍する。
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
		this.ctx.setTransform(sc,0,0,-sc/this.syx,0,0);
		this.ctx.translate(-this.leftwx,-this.topy);
		this.stdStrokeWidth=this.w/300;
		this.ctx.lineWidth=this.stdStrokeWidth;
    },
	// スケール変換を保存して、いったんリセットする。
    saveScale:function() {
		this.ctx.save();
		this.ctx.setTransform(1,0,0,1,0,0);
		this.ctx.stdStrokeWidth=1;
    },
    restoreScale:function() {
		this.ctx.restore();
		this.ctx.stdStrokeWidth=this.w/300;
    },
	// fromC*toM*は、キャンバス座標からマウス座標への変換
	fromCxtoMx:function(cx) {
		return (cx-this.leftwx)/this.w*this.canvas.width;
	},
	fromCytoMy:function(cy) {
		return (this.topy-cy)/this.h*this.canvas.height;
	},
	// formM*toC*は、マウス座標からキャンバス座標への変換
    fromMxtoCx:function(mx) {
		return mx/this.canvas.width*this.w+this.leftwx;
    },
    fromMytoCy:function(my) {
		return -my/this.canvas.height*this.h+this.topy;
    },
    clearALL:function() {
    	this.ctx.fillStyle="rgb(255,255,255)";
		this.ctx.fillRect(this.leftwx,this.bottomy,this.w,this.h);
    }
};
