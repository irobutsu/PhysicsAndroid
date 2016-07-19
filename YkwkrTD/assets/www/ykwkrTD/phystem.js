'use strict';
// 物理オブジェクト
// 持っている属性は以下の通り
// pos：現在位置
// v：速度
// npos:時間がΔt経過した後の位置
// r：半径（ここで考えている物理オブジェクトの多くは(回転を考えないという意味で)質点だが、すべて円形で表現するので、半径を持つ場合が多い）
// col：色（描画される時の色）
// ps：属している「系」（Phystem）
var PhysObject = function (ps, x, y, c, rr) {
	this.pos = new Vector(
		(x === undefined) ? 0 : x,
		(y === undefined) ? 0 : y
	);
	// 色をセット
	this.col = (c === undefined) ? "rgba(0,0,0,0.4)" : c;
	this.npos = this.pos.makeCopy();
	// npは、「次の位置」（シンプレクティック法で微分方程式を解いているのでこれが必要）
	this.r = (rr === undefined) ? 0.5 :rr;
	this.ps = ps; // 自分の属している系をセットしておく。
	// この「系」は描画用のキャンバスも含んでいる。
};
PhysObject.prototype = {
	// 描画：●を描く。
	draw: function () {
		this.ps.fillCircle(this.pos.x, this.pos.y, this.r, this.col);
	},
	// 相互作用力。「to」へと及ぼす力を返す。
	// 現在はスタブなので0ベクトルを返す。
	interactionForce: function (to) {
		return new Vector(0, 0);
	},
	// 変位べクトル。現在位置ではなく、△t後の位置で計算する。
	displacementFrom: function (from) {
		return this.npos.diff(from.npos);
	}
};
// 非力学オブジェクト
// 運動しないもしくは運動方程式によらない運動をするオブジェクト
var NonDynamicalObject = function (ps, x, y, c, r) {
	PhysObject.call(this, ps, x, y, c, r);
	if (ps !== undefined) {
		ps.ndObjs.push(this);
		// 「系」に、自分自身を登録する。
	}
};

NonDynamicalObject.prototype = Object.create(PhysObject.prototype);
NonDynamicalObject.prototype.constructor = NonDynamicalObject;

// NonDynamicalObjectの「次の場所」の計算。
// 基本的には動かない（か、動きが関係ない）ので、単に現在位置をコピーするだけ
// 自律的に（運動方程式に従わずに）動く物体の場合は、ここを変更すべし。
NonDynamicalObject.prototype.setNextPos  = function() {
	this.npos.copyFrom(this.pos);
};



// DynamicalObject:「動く物体」のクラス
// 親クラスはPhysObject（物理的物体？）
// mass（質量）と、fList（働いている力のリスト）という属性が追加される。
// yavというオブジェクトを持っているが、外部から使う必要は多分ない。
// 初期位置、初速度、質量、色、と「どの系に属すか」が呼び出しパラメータ（初速度以降は省略可）
var DynamicalObject = function (ps, x, y, vx, vy, m, c, r) {
	PhysObject.call(this, ps, x, y, c, r);
	if (ps !== undefined) {
		// 自分自身を系の「動くものリスト」に登録する。
		ps.dyObjs.push(this);
	}
	this.v = new Vector(
		(vx === undefined) ? 0 : vx,
		(vy === undefined) ? 0 : vy
	);
	this.mass = (m === undefined) ? 1 : m;
	this.makeV(); // 動くものは速度や力を表示するので、その為の矢印を先に作っておく。
	this.fList = []; // 力のリスト。
};
// 親クラスのプロトタイプを継承する
DynamicalObject.prototype = Object.create(PhysObject.prototype);
// 作成された時に上のコンストラクタが呼ばれるようにする。
DynamicalObject.prototype.constructor = DynamicalObject;

DynamicalObject.prototype.addF = function (f) {
	if (f.x !== 0 || f.y !== 0) {
		this.fList.push(f.makeCopy());
		// fのコピーを作って力のリストに登録
	}
};
DynamicalObject.prototype.fClear = function () {
	delete this.fList;
	this.fList = [];
};
DynamicalObject.prototype.makeV = function (c) {
	this.yav = new Yajirushi((c === undefined) ? "rgba(0,0,0,0.8)" : c);
};
DynamicalObject.prototype.drawV = function () {
	this.yav.setP(this.pos);
	this.yav.setV(this.v);
	this.yav.setColor("rgba(0,0,0,0.8)");
	this.yav.draw(this.ps.ctx);
};
DynamicalObject.prototype.drawF = function () {
	var i;
	this.yav.setP(this.pos);
	this.yav.setColor("rgba(238,69,74,0.5)");
	for (i = 0; i < this.fList.length; i++) {
		this.yav.setV(this.fList[i]);
		this.yav.draw(this.ps.ctx);
	}
};


// Phystem（Physical Systemの略）
// 座標系を設定して、その座標系に図を描くためのキャンバス
// 呼び出した方は　new Phystem(canvas_string, ww, hh, adjustW, left, bottom);
// canvas_stringはキャンバス要素のid
// ww, hhは縦横の幅（画面上の幅とは一致しない）
// 画面上の幅は、横幅*adjustWになる。
// 画面の左隅のx座標はleft、
// 下隅のy座標はbottomとなる。
// x, y座標は右、上が正方向となるので注意。

// Phystem
// 物理的「系」とその表示を司るオブジェクト
var Phystem = function (canvas_string, ww, hh, adjustW, left, bottom) {
	RescaleCanvas.call(this, canvas_string, ww, hh, adjustW, left, bottom);
	this.clearLists();
	this.stopFlg=false;
};
Phystem.prototype = Object.create(RescaleCanvas.prototype);
Phystem.prototype.constructor = Phystem;

// 以下、Phystemに追加されるプロトタイプ


Phystem.prototype.stepT = 0.01;
Phystem.prototype.stepTime = 10;
Phystem.prototype.drawStep = 4;
Phystem.prototype.edgew = 1;
Phystem.prototype.drawVFlg = true;

Phystem.prototype.stop = function () {
	this.stopFlg=true;
};
// DynamicalObjectのリストをクリアする。
Phystem.prototype.cleardyLists = function () {
	this.dyObjs = []; //動くオブジェクトのリスト
};

// DynamicalObjectとNonDynamicalObjectのリストをクリアする
Phystem.prototype.clearLists = function () {
	this.dyObjs = []; //動くオブジェクトのリスト
	this.ndObjs = []; //動かないオブジェクトのリスト
};
// 中身を描く。
Phystem.prototype.writeContents = function () {
	this.writeBackGround();
	var i;
	var N = this.dyObjs.length;
	for (i = 0; i < N; i++) {
		this.dyObjs[i].draw();
	}
	if (this.drawVFlg) {
		for (i = 0; i < N; i++) {
			this.dyObjs[i].drawV();
		}
	}
	if (this.drawFFlg) {
		for (i = 0; i < N; i++) {
			this.dyObjs[i].drawF();
		}
	}
	N = this.ndObjs.length;
	for (i = 0; i < N; i++) {
		this.ndObjs[i].draw();
	}
};


Phystem.prototype.writeXY = false; // x軸、y軸を描くか？
Phystem.prototype.writeX = false;  // x軸だけ描くか？
Phystem.prototype.writeY = false;  // y軸だけ描くか？


// 背景を描く。
Phystem.prototype.writeBackGround = function () {
	var ct = this.ctx;
	ct.fillStyle = "rgb(240,255,255)";
	ct.fillRect(this.leftx, this.bottomy, this.w, this.h);
	if (this.writeXY) {
		this.writeCoordinate();
	}
	if (this.writeX) {
		this.writeCoordinateX();
	}
	if (this.writeY) {
		this.writeCoordinateY();
	}
};
Phystem.prototype.start = function () { this.goStep(this); };
Phystem.prototype.count = 0;
Phystem.prototype.goStep = function (ps) {
	ps.count++;
	if (ps.count >= ps.drawStep) {
		ps.count = 0;
		ps.calcNext();
		ps.writeContents();
	} else {
		ps.calcNextNoRecord();
	}
	//	setTimeout(ps.goStep, ps.stepTime, ps);←この書き方はIEだと動かんそうな。
	this.report();
	if( !this.stopFlg ) {
		setTimeout(function () { ps.goStep(ps); }, ps.stepTime);
	}
};
Phystem.prototype.report=function() {};
// ↑アニメーションするごとに何かを報告する関数。もともとは、何もしない。

Phystem.prototype.initObjs = function() {
	var i,now;
	var N = this.dyObjs.length;
	var dt = this.stepT;
	for (i = 0; i < N; i++) {
		now = this.dyObjs[i];
		now.npos.copyFrom(now.pos);
		now.npos.addVt(now.v, dt);
		// npos = pos+vΔt
		now.fClear();
	}
	var N = this.ndObjs.length;
	for (i = 0; i < N; i++) {
		now = this.ndObjs[i];
		now.setNextPos();
	}
}

// this.stepT 秒後の状態を計算する。
Phystem.prototype.calcNext = function () {
	var i, now;
	var N = this.dyObjs.length;
	var dt = this.stepT;
	this.initObjs();
	var F, f, j;
	for (i = 0; i < N; i++) {
		now = this.dyObjs[i];
		F = new Vector(0, 0);
		for (j = 0; j < N; j++) {
			// j番目の動的オブジェクトからi番目の動的オブジェクトへの力
			if (i !=  j) {
				f = this.dyObjs[j].interactionForce(now);
				F.add(f);
				now.addF(f);
			}
		}
		for (j = 0; j < this.ndObjs.length; j++) {
			// j番目の【非】動的オブジェクトからi番目の動的オブジェクトへの力
			f = this.ndObjs[j].interactionForce(now);
			F.add(f);
			now.addF(f);
		}
		now.v.add(F.prod(dt / now.mass));  // v=v+(F(x+vΔt)/m)Δt
		now.pos.copyFrom(now.npos);
	}

};

// 物体の次の状態を計算するが、記録を残さない
// Phystemのループ内では、記録を残すステップはthis.drawStepごとに一回にしている。
Phystem.prototype.calcNextNoRecord = function () {
	var i;
	var N = this.dyObjs.length;
	var dt = this.stepT;
	var now, F, f, j;
	this.initObjs();

	for (i = 0; i < N; i++) {
		now = this.dyObjs[i];
		F = new Vector(0, 0);
		for (j = 0 ; j < N; j++) {
			// j番目の動的オブジェクトからi番目の動的オブジェクトへの力
			if (i !== j) {
				f = this.dyObjs[j].interactionForce(now);
				F.add(f);
			}
		}
		for (j = 0 ; j < this.ndObjs.length ; j++) {
			// j番目の【非】動的オブジェクトからi番目の動的オブジェクトへの力
			f = this.ndObjs[j].interactionForce(now);
			F.add(f);
		}
		now.v.add(F.prod(dt / now.mass));  // v = v+(F(x+vΔt)/m)Δt
		now.pos.copyFrom(now.npos);
	}
};

// 空気抵抗を追加する
Phystem.prototype.makeAir = function (k) {
	this.airResist = (k === undefined) ? 1 : k;
	var air = new NonDynamicalObject(this);
	air.interactionForce = function (to) {
		return new Vector(-to.v.x * this.ps.airResist, -to.v.y * to.ps.airResist);
	};
	air.draw = function () {};
};

// 重力を追加する
Phystem.prototype.makeGravity = function (g) {
	this.gravity = (g === undefined) ? 1 : g;
	var earth = new NonDynamicalObject(this);
	earth.interactionForce = function (to) {
		return new Vector(0, -to.mass * to.ps.gravity);
	};
	earth.draw = function () {};
};
Phystem.prototype.makeEdge = function (w) {
	// edgeは、この系の端。
	this.edgew = (w === undefined) ? 1 : w;
	var edge = new NonDynamicalObject(this); //境界を非力学的オブジェクトとして設置
	this.edgeStrength = 2000; // 境界でのはね返りの強さ
	edge.interactionForce = function (to) {
		var fx, fy, p = to.npos; // 力は「新しい位置」で計算する。
		fx = 0;
		fy = 0;
		var sys = this.ps;
		if (p.x < sys.leftx + sys.edgew + to.r) {
			fx =  sys.leftx + sys.edgew  + to.r - p.x;
		}
		if (p.x > sys.rightx - sys.edgew - to.r) {
			fx =  sys.rightx - sys.edgew - to.r - p.x;
		}
		if (p.y < sys.bottomy + sys.edgew + to.r) {
			fy =  sys.bottomy + sys.edgew + to.r  - p.y;
		}
		if (p.y > sys.topy - sys.edgew - to.r) {
			fy =  sys.topy - sys.edgew - to.r - p.y;
		}
		return new Vector(sys.edgeStrength * fx, sys.edgeStrength * fy);
	};
	edge.draw = function () {
		var sys = this.ps;
		var ct = this.ps.ctx;
		ct.fillStyle = "rgba(100,200,100,0.3)";
		ct.fillRect(sys.leftx, sys.bottomy, sys.w, sys.edgew);
		ct.fillRect(sys.leftx, sys.topy - sys.edgew, sys.w, sys.edgew);
		ct.fillRect(sys.leftx, sys.bottomy, sys.edgew, sys.h);
		ct.fillRect(sys.rightx - sys.edgew, sys.bottomy, sys.edgew, sys.h);
	};
};
