'use strict';
// m1とm2をつなぐバネ
var Bane = function (ps, m1, m2, k, l, c) {
	// バネはNonDynamicalObjectである。
	NonDynamicalObject.call(this, ps, 0, 0, c);
	this.m1 = m1;
	this.m2 = m2;
	// バネ定数（デフォルトは1）
	this.k = (k === undefined) ? 1 : k;
	// 自然長（デフォルトは0）
	this.l = (l === undefined) ? 0 : l;
};

Bane.prototype = Object.create(NonDynamicalObject.prototype);
Bane.prototype.constructor = Bane;
Bane.prototype.w = 0.2;

// バネは自分のつながっている相手に対してのみ力を加える。
Bane.prototype.interactionForce = function (to) {
	if (to === this.m1) {
		return this.force1();
	} else if (to === this.m2) {
		return this.force2();
	} else {
		return new Vector(0, 0);
	}
};

Bane.prototype.force2 = function () {
	var F = this.m1.displacementFrom(this.m2);
	return this.force(F);
};
Bane.prototype.force1 = function () {
	var F = this.m2.displacementFrom(this.m1);
	return this.force(F);
};

Bane.prototype.force = function (F) {
	var Fl = F.length();
	if (Math.abs(Fl) < 1e-10) {
		F = new Vector(0, 0);
	} else {
		F.mul(this.k * (Fl - this.l) / Fl);
	}
	return F;
};

Bane.prototype.draw = function () {
	var x1 = this.m1.pos.x;
	var y1 = this.m1.pos.y;
	var x2 = this.m2.pos.x;
	var y2 = this.m2.pos.y;

	var dx = (x2 - x1) * 0.05;
	var dy = (y2 - y1) * 0.05;
	var dxy = Math.sqrt(dx * dx + dy * dy);
	var ddx = this.w * dx / dxy;
	var ddy = this.w * dy / dxy;

	this.ps.writeLine(x1, y1, x1 + 4 * dx, y1 + 4 * dy, this.col, 2);
	this.ps.writeLine(x1 + 4 * dx, y1 + 4 * dy, x1 + 5 * dx - ddy, y1 + 5 * dy + ddx, this.col, 2);
	this.ps.writeLine(x1 + 5 * dx - ddy, y1 + 5 * dy + ddx, x1 + 7 * dx + ddy, y1 + 7 * dy - ddx, this.col, 2);
	this.ps.writeLine(x1 + 7 * dx + ddy, y1 + 7 * dy - ddx, x1 + 9 * dx - ddy, y1 + 9 * dy + ddx, this.col, 2);
	this.ps.writeLine(x1 + 9 * dx - ddy, y1 + 9 * dy + ddx, x1 + 11 * dx + ddy, y1 + 11 * dy - ddx, this.col, 2);
	this.ps.writeLine(x1 + 11 * dx + ddy, y1 + 11 * dy - ddx, x1 + 13 * dx - ddy, y1 + 13 * dy + ddx, this.col, 2);
	this.ps.writeLine(x1 + 13 * dx - ddy, y1 + 13 * dy + ddx, x1 + 15 * dx + ddy, y1 + 15 * dy - ddx, this.col, 2);
	this.ps.writeLine(x1 + 15 * dx + ddy, y1 + 15 * dy - ddx, x1 + 16 * dx, y1 + 16 * dy, this.col, 2);
	this.ps.writeLine(x2, y2, x1 + 16 * dx, y1 + 16 * dy, this.col, 2);
};
