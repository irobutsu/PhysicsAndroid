var PhysObject=function(ps,x,y,c) {
	this.p=new Vector(
		(x==undefined) ? 0:x,
		(y==undefined) ? 0:y
	);
	// 色をセット（これもいらんといえばいらんのだが）
	this.col=(c==undefined) ? "rgba(0,0,0,0.4)":c;
	this.np=this.p.makeCopy();
	// npは、「次の位置」（シンプレクティック法で微分方程式を解いているのでこれが必要）
	this.r=0.5;	
	this.ps=ps; // 自分の属している系をセットしておく。
	// この「系」は描画用のキャンバスも含んでいる。
}
PhysObject.prototype={
	// 描画：●を描く。
	draw:function() {
		this.ps.fillCircle(this.p.x,this.p.y,this.r,this.col);
	},
	// 相互作用力。「to」へと及ぼす力を返す。
	// 現在はスタブなので0ベクトルを返す。
	interactionForce:function(to) {
		return new Vector(0,0);
	},
	displacementFrom:function(from) {
		return this.np.diff(from.np);
	}
}

var NonDynamicalObject=function(ps,x,y,c) {
	PhysObject.call(this,ps,x,y,c);
	if( ps!=undefined ) {
		ps.ndObjs.push(this);
	}
}

NonDynamicalObject.prototype=Object.create(PhysObject.prototype);
NonDynamicalObject.prototype.constructor=NonDynamicalObject;

// DynamicalObject:「動く物体」のクラス
// 親クラスはPhysObject（物理的物体？）
// 初期位置、初速度、質量、色、と「どの系に属すか」が呼び出しパラメータ（初速度以降は省略可）
var DynamicalObject=function(ps,x,y,vx,vy,m,c) {
	PhysObject.call(this,ps,x,y,c);
	if( ps!=undefined ) {
		// 自分自身を系の「動くものリスト」に登録する。
		ps.dyObjs.push(this);
	}
	this.v=new Vector(
		(vx==undefined) ? 0:vx,
		(vy==undefined) ? 0:vy
	);
	this.mass=(m==undefined) ? 1:m;
	this.makeV(); // 動くものは速度や力を表示するので、その為の矢印を先に作っておく。
	this.fClear();
}
// 親クラスのプロトタイプを継承する
DynamicalObject.prototype=Object.create(PhysObject.prototype);
// 作成された時に上のコンストラクタが呼ばれるようにする。
DynamicalObject.prototype.constructor=DynamicalObject;

DynamicalObject.prototype.addF=function(f) {
	if( f.x !=0 || f.y !=0 ) {
		this.fList.push(f.makeCopy());
	}
};
DynamicalObject.prototype.fClear=function() {
	delete this.fList;
	this.fList=new Array();
};
DynamicalObject.prototype.makeV=function(c) {
	this.yav=new Yajirushi(( c==undefined ) ? "rgba(0,0,255,0.3)":c );
};
DynamicalObject.prototype.drawV=function() {
	this.yav.setP(this.p);
	this.yav.setV(this.v);
	this.yav.setColor("rgba(0,0,255,0.5)");
	this.yav.draw(this.ps.ctx);
};
DynamicalObject.prototype.drawF=function() {
	var i;
	for(i=0; i<this.fList.length; i++ ) {
		this.yav.setP(this.p);
		this.yav.setV(this.fList[i]);
		this.yav.setColor("rgba(238,69,74,0.5)");
		this.yav.draw(this.ps.ctx);
	}
};


// Phystem（Physical Systemの略）
// 座標系を設定して、その座標系に図を描くためのキャンバス
// 呼び出した方は　new Phystem(canvas_string,ww,hh,adjustW,left,bottom);
// canvas_stringはキャンバス要素のid
// ww,hhは縦横の幅（画面上の幅とは一致しない）
// 画面上の幅は、横幅*adjustWになる。
// 画面の左隅のx座標はleft、
// 下隅のy座標はbottomとなる。
// x,y座標は右、上が正方向となるので注意。

// Phystem
// 物理的「系」とその表示を司るオブジェクト
var Phystem=function(canvas_string,ww,hh,adjustW,left,bottom) {
	RescaleCanvas.call(this,canvas_string,ww,hh,adjustW,left,bottom);
	this.clearLists();
};
Phystem.prototype=Object.create(RescaleCanvas.prototype);
Phystem.prototype.constructor=Phystem;

// 以下、Phystemに追加されるプロトタイプ


Phystem.prototype.stepT=0.01;
Phystem.prototype.stepTime=10;
Phystem.prototype.drawStep=4;
Phystem.prototype.edgew=1;
Phystem.prototype.drawVFlg=true;
Phystem.prototype.cleardyLists=function() {
	this.dyObjs=new Array(); //動くオブジェクトのリスト
};
Phystem.prototype.clearLists=function() {
	this.dyObjs=new Array(); //動くオブジェクトのリスト
	this.ndObjs=new Array(); //動かないオブジェクトのリスト
};
// 中身を描く。
Phystem.prototype.writeContents=function() {
	this.writeBackGround();
	var i;
	var N=this.dyObjs.length;
	for( i=0 ; i< N; i++ ) {
		this.dyObjs[i].draw();
	}
	if( this.drawVFlg ) {
		for( i=0 ; i< N; i++ ) {
			this.dyObjs[i].drawV();
		}
	}
	if( this.drawFFlg ) {
		for( i=0 ; i< N; i++ ) {
			this.dyObjs[i].drawF();
		}
	}
	N=this.ndObjs.length;
	for(i=0; i<N ; i++) {
		this.ndObjs[i].draw();
	}
};

Phystem.prototype.writeXY=false;
Phystem.prototype.writeX=false;
Phystem.prototype.writeX=false;


// 背景を描く。縁がある場合では周りに枠を描く。
Phystem.prototype.writeBackGround=function() {
	var ct=this.ctx;
	ct.fillStyle="rgb(240,255,255)";
	ct.fillRect(this.leftx,this.bottomy,this.w,this.h);
	if( this.writeXY ) {
		this.writeCoordinate();
	}
	if( this.writeX ) {
		this.writeCoordinateX();
	}
	if( this.writeY ) {
		this.writeCoordinateY();
	}
};
Phystem.prototype.start=function() { this.goStep(this);};
Phystem.prototype.count=0;
Phystem.prototype.goStep=function(ps) {
	ps.count++;
	if( ps.count >= ps.drawStep ) {
		ps.count=0;
		ps.calcNext();
		ps.writeContents();
	} else {
		ps.calcNextNoRecord();
	}
//	setTimeout(ps.goStep,25,ps);←この書き方はIEだと動かん。
	setTimeout(function() { ps.goStep(ps); },ps.stepTime);
};
Phystem.prototype.calcNext=function() {
	var i;
	var N=this.dyObjs.length;
	var dt=this.stepT;
	var now;
	for(i=0; i<N ; i++ ) {
		now=this.dyObjs[i];
		now.np.copyFrom(now.p);
		now.np.addVt(now.v,dt);
		// xN = x+vΔt
		now.fClear();
	}
	var F;
	for(i=0; i<N ; i++ ) {
		now=this.dyObjs[i];
		F=new Vector(0,0);
		for( j=0 ; j<N ; j++ ) {
			// j番目の動的オブジェクトからi番目の動的オブジェクトへの力
			if( i!= j) {
				f=this.dyObjs[j].interactionForce(now);
				F.add(f);
				now.addF(f);
			}
		}
		for( j=0 ; j<this.ndObjs.length ; j++ ) {
			// j番目の【非】動的オブジェクトからi番目の動的オブジェクトへの力
			f=this.ndObjs[j].interactionForce(now);
			F.add(f);
			now.addF(f);
		}
		now.v.add(F.prod(dt/now.mass));  // v=v+(F(x+vΔt)/m)Δt
		now.p.copyFrom(now.np);
	}

};
Phystem.prototype.calcNextNoRecord=function() {
	var i;
	var N=this.dyObjs.length;
	var dt=this.stepT;
	var now;
	for(i=0; i<N ; i++ ) {
		now=this.dyObjs[i];
		now.np.copyFrom(now.p);
		now.np.addVt(now.v,dt);
		now.fClear();
	}
	var F;
	for(i=0; i<N ; i++ ) {
		now=this.dyObjs[i];
		F=new Vector(0,0);
		for( j=0 ; j<N ; j++ ) {
			// j番目の動的オブジェクトからi番目の動的オブジェクトへの力
			if( i != j ) {
				f=this.dyObjs[j].interactionForce(now);
				F.add(f);
			}
		}
		for( j=0 ; j<this.ndObjs.length ; j++ ) {
			// j番目の【非】動的オブジェクトからi番目の動的オブジェクトへの力
			f=this.ndObjs[j].interactionForce(now);
			F.add(f);
		}
		now.v.add(F.prod(dt/now.mass));  // v=v+(F(x+vΔt)/m)Δt
		now.p.copyFrom(now.np);
	}
};
Phystem.prototype.externalForce=function(m) {
	return new Vector(0,0);
};
Phystem.prototype.fieldForce=function(m) {
	// デフォルトは端での反発のみ。
};
Phystem.prototype.hasNoEdge=false;
// 相互作用の力（これはスタブなので0を返す）
Phystem.prototype.interactionForce=function(ml,i) {
	return new Vector(0,0);
};
// 空気抵抗を追加する
Phystem.prototype.makeAir=function(k) {
	this.airResist=(k==undefined)? 1 :k;
	var air=new NonDynamicalObject(this);
	air.interactionForce=function(to) {
		return new Vector(-to.v.x*this.ps.airResist,-to.v.y*to.ps.airResist);
	};
	air.draw=function() {};
};
Phystem.prototype.makeGravity=function(g) {
	this.gravity=(g==undefined)? 9.8 :g;
	var earth=new NonDynamicalObject(this);
	earth.interactionForce=function(to) {
		return new Vector(0,-to.mass*to.ps.gravity);
	};
	earth.draw=function() {};
};
Phystem.prototype.makeEdge=function(w){
	// edgeは、この系の端。
	this.edgew=(w==undefined)? 1: w;
	var edge=new NonDynamicalObject(this); //境界を非力学的オブジェクトとして設置
	this.edgeStrength=2000; // 境界でのはね返りの強さ
	edge.interactionForce=function(to) {
		var p=to.np; // 力は「新しい位置」で計算する。
		var fx,fy;
		fx=0;
		fy=0;
		var sys=this.ps;
		if( p.x < sys.leftx+sys.edgew+to.r ) {
			fx= sys.leftx+sys.edgew +to.r -p.x;
		}
		if( p.x > sys.rightx-sys.edgew-to.r ) {
			fx= sys.rightx-sys.edgew-to.r-p.x;
		}
		if( p.y < sys.bottomy+sys.edgew+to.r ) {
			fy= sys.bottomy+sys.edgew+to.r -p.y;
		}
		if( p.y > sys.topy-sys.edgew-to.r ) {
			fy= sys.topy-sys.edgew-to.r-p.y;
		}
		return new Vector(sys.edgeStrength*fx,sys.edgeStrength*fy);
	};
	edge.draw=function() {
		var sys=this.ps;
		var ct=this.ps.ctx;
		ct.fillStyle="rgba(100,200,100,0.3)";
		ct.fillRect(sys.leftx,sys.bottomy,sys.w,sys.edgew);
		ct.fillRect(sys.leftx,sys.topy-sys.edgew,sys.w,sys.edgew);
		ct.fillRect(sys.leftx,sys.bottomy,sys.edgew,sys.h);
		ct.fillRect(sys.rightx-sys.edgew,sys.bottomy,sys.edgew,sys.h);
	};
};
