var ts;
var phi=0;
var theta=0;
var psi=0;
var planexy,planeyz,planezx;
var red=new THREE.MeshLambertMaterial({color: 0xCC0000});
var blue=new THREE.MeshLambertMaterial({color: 0x0000CC});
var yellow=new THREE.MeshLambertMaterial({color: 0x888800});
var cyan=new THREE.MeshLambertMaterial({color: 0x00EEEE});
var magenta=new THREE.MeshLambertMaterial({color: 0x550055});


$(document).delegate('#page_gousei','pageinit',function(e) {

});
var xlabel,ylabel,zlabel;
var gsikaku;
$(document).delegate('#page_gousei','pageshow',function(e) {
	ts=new ThreejsScreen("tcanvas",0.7,0.8);
	var planemxy=new THREE.MeshLambertMaterial(
		{color: 0xFF00FF,transparent:true,opacity:0.1,shading: THREE.SmoothShading}
	);
	var planemyz=new THREE.MeshLambertMaterial(
		{color: 0xFF8800,transparent:true,opacity:0.1,shading: THREE.SmoothShading}
	);
	var planemzx=new THREE.MeshLambertMaterial(
		{color: 0x88FF00,transparent:true,opacity:0.1,shading: THREE.SmoothShading}
	);
	planeg=new THREE.CubeGeometry(4,4,0.01);
	planexy = new THREE.Mesh(planeg,planemxy);
	planeyz = new THREE.Mesh(planeg,planemyz);
	planezx = new THREE.Mesh(planeg,planemzx);
	planexy.position.set(0,0,-2);
	planezx.position.set(0,2,0);
	planezx.rotation.x=Math.PI*0.5;
	planeyz.position.set(-2,0,0);
	planeyz.rotation.y=Math.PI*0.5;

	var shape=new THREE.Shape();
	shape.moveTo(0,0.2);
	shape.lineTo(0.1,0);
	shape.lineTo(0,-0.2);
	shape.lineTo(-0.1,0);
	gsikaku=new THREE.ShapeGeometry(shape);


	c=makeCoordinate(red,blue,magenta);
	ts.camera.position.set(3.5,-7,4);


	ts.scene.add(xlabel);
	ts.scene.add(c.xjikuxy);
	ts.scene.add(c.xjikuzx);
	ts.scene.add(c.yjikuxy);
	ts.scene.add(c.yjikuyz);
	ts.scene.add(c.zjikuyz);
	ts.scene.add(c.zjikuzx);
	ts.scene.add(planexy);
	ts.scene.add(planeyz);
	ts.scene.add(planezx);	
	ts.scene.remove(gxy);
	gxy=make_graph2D(function(x) { return x*x; });
	ts.scene.add(gxy);
	graphWrite();
});
var c;
var funcxy=function(x){ return Func(fnox,a,x);};
var funcyz=function(x){ return Func(fnoy,b,x); };
var funcxz=function(x){ return funcyz(funcxy(x)); };

var Yajirushi=function(len,w,ya,yaw) {
	if( len == undefined ) {
		len=1;
	}
	if( w==undefined ) {
		w=0.03;
	}
	if( yaw==undefined ) {
		yaw=1.6;
	}
	if( ya==undefined ) {
		ya=0.2;
	}
	THREE.CylinderGeometry.call(this,w*len,w*len,len*(1-ya),(ts.useWebGL() ? 8 : 3));
	var i;
	for(i=0; i<this.vertices.length; i++ ) {
		this.vertices[i].y += 0.5*len*(1-ya);
	}
	this.capg=new THREE.Mesh(new THREE.CylinderGeometry(0,yaw*w*len,len*ya,(ts.useWebGL() ? 8 : 3)));
	for(i=0; i<this.capg.geometry.vertices.length; i++ ) {
		this.capg.geometry.vertices[i].y += len*(1-0.5*ya);
	}
	THREE.GeometryUtils.merge(this,this.capg);
}
Yajirushi.prototype=Object.create(THREE.CylinderGeometry.prototype);
Yajirushi.prototype.constructor=Yajirushi;


function makeCoordinate(xm,ym,zm,b) {
	if( b== undefined ) { b=1;}
	var cood=new Object();

	cood.xjikuxy=new THREE.Mesh(new Yajirushi(4,b*0.01),xm);
	cood.xjikuzx=new THREE.Mesh(new Yajirushi(4,b*0.01),xm);
	cood.yjikuxy=new THREE.Mesh(new Yajirushi(4,b*0.01),ym);
	cood.yjikuyz=new THREE.Mesh(new Yajirushi(4,b*0.01),ym);
	cood.zjikuyz=new THREE.Mesh(new Yajirushi(4,b*0.01),zm);
	cood.zjikuzx=new THREE.Mesh(new Yajirushi(4,b*0.01),zm);
	cood.xjikuxy.rotation.set(0,0,-0.5*Math.PI);
	cood.xjikuzx.rotation.set(0,0,-0.5*Math.PI);
	cood.xjikuxy.position.set(-2,0,-2);
	cood.xjikuzx.position.set(-2,2,0);
	cood.yjikuxy.position.set(0,-2,-2);
	cood.yjikuyz.position.set(-2,-2,0);
	cood.zjikuyz.position.set(0,2,-2);
	cood.zjikuzx.position.set(-2,0,-2);
	cood.zjikuyz.rotation.set(0.5*Math.PI,0,0);
	cood.zjikuzx.rotation.set(0.5*Math.PI,0,0);
	return cood;
}

var gxy,gyz,gzx;
var line;
var px,py,pz;
var px2,py2,pz2;

function graphWrite() {	
	ts.scene.remove(gxy);
	ts.scene.remove(gyz);
	ts.scene.remove(gzx);
	ts.scene.remove(px);
	ts.scene.remove(py);
	ts.scene.remove(pz);
	ts.scene.remove(px2);
	ts.scene.remove(py2);
	ts.scene.remove(pz2);
	ts.scene.remove(line);
	gxy=make_graph2D(funcxy,{color:0x00AA00});
	gxy.position.set(0,0,-2);
	gyz=make_graph2D(funcyz,{color:0x888800});
	gyz.rotation.y=Math.PI*0.5;
	gyz.rotation.z=Math.PI*0.5;
	gyz.position.set(-2,0,0);
	gzx=make_graph2D(funcxz,{color:0x000000});
	gzx.rotation.x=Math.PI*0.5;
	gzx.position.set(0,2,0);

	ts.scene.add(gxy);
	ts.scene.add(gzx);
	ts.scene.add(gyz);

	var material = new THREE.LineBasicMaterial({color:0xFFFFFF});
	var geometry = new THREE.Geometry();
	geometry.vertices.push(new THREE.Vector3(x,0,-2));
	var y=funcxy(x);
	geometry.vertices.push(new THREE.Vector3(x,y,-2));
	geometry.vertices.push(new THREE.Vector3(-2,y,-2));
	var z=funcyz(y);
	geometry.vertices.push(new THREE.Vector3(-2,y,z));
	geometry.vertices.push(new THREE.Vector3(-2,2,z));
	geometry.vertices.push(new THREE.Vector3(x,2,z));
	geometry.vertices.push(new THREE.Vector3(0,2,z));
	line=new THREE.Line(geometry,material);
	ts.scene.add(line);

	var mx=new THREE.MeshBasicMaterial({color:0xFF0000,transparent:true,opacity:0.4});
	var my=new THREE.MeshBasicMaterial({color:0x0000FF,transparent:true,opacity:0.4});
	var mz=new THREE.MeshBasicMaterial({color:0xFF00FF,transparent:true,opacity:0.4});
	mx.side = THREE.DoubleSide;
	my.side = THREE.DoubleSide;
	mz.side = THREE.DoubleSide;


	px=new THREE.Mesh(gsikaku,mx);
	py=new THREE.Mesh(gsikaku,my);
	px2=new THREE.Mesh(gsikaku,mx);
	py2=new THREE.Mesh(gsikaku,my);
	py.rotation.z=Math.PI*0.5;
	py.rotation.y=Math.PI*0.5;
	py2.rotation.z=Math.PI*0.5;
	py2.rotation.y=Math.PI*0.5;
	pz=new THREE.Mesh(gsikaku,mz);
	pz2=new THREE.Mesh(gsikaku,mz);
	pz.rotation.x=Math.PI*0.5;
	pz.rotation.z=Math.PI*0.5;
	pz2.rotation.x=Math.PI*0.5;
	pz2.rotation.z=Math.PI*0.5;

	px.position.set(x,0,-1.98);
	px2.position.set(x,0,-2.02);
	py.position.set(-1.98,y,0);
	py2.position.set(-2.02,y,0);
	pz.position.set(0,1.98,z);
	pz2.position.set(0,2.02,z);

	ts.scene.add(px);
	ts.scene.add(py);
	ts.scene.add(pz);
	ts.scene.add(px2);
	ts.scene.add(py2);
	ts.scene.add(pz2);


	requestAnimationFrame(graphWrite);
    ts.controlsUpdate();
	ts.draw();
	x +=0.01;
	if( x> 2) {
		x=-2;
	}
}
var x=-2;

function make_graph2D(gfunc,c) {
	var i;
	var material = new THREE.LineBasicMaterial(c);
	var geometry = new THREE.Geometry();
	for( i=-2+0.02; i<= 2; i += 0.02) {
		geometry.vertices.push(new THREE.Vector3(i,gfunc(i),0));
	}
	return new THREE.Line(geometry,material);
}

var fnox=1;
var fnoy=1;
function Func(funcNo,aa,x) {
	switch( funcNo ) {
	case 1:
		return aa*x;
		break;
	case 2:
		return aa/ x;
		break;
	case 3:
		return aa*x*x;
		break;
	case 4:
		return Math.sqrt(Math.abs(x/ aa));
		break;
	case 5:
		return Math.sin(aa*x);
		break;
	case 6:
		return Math.asin(x)/aa;
		break;
	case 7:
		return Math.exp(aa*x);
		break;
	case 8:
		return Math.log(Math.abs(x))/ aa;
		break;
	}
}
function timesString(a,x) {
	if( a==1 ) {
		return x;
	} else if( a==-1 ) {
		return "-"+x;
	} else if( a==0 ) {
		return "0";
	} else {
		return a+x;
	}
}
function divString(a,x) {
	if( a==0 ) {
		return "未定義";
	}
	if( x==1 ) {
		return a;
	}
	if( x==-1 ) {
		return "-"+a;
	}
	return '<table cellpadding="2" cellspacing="0" style="display:inline-table"><tbody><tr class="eq"><td align="center" nowrap="nowrap">'+a+'<hr noshade="noshade" size="1">'+x+'</td></tr></tbody></table>';
}

function Funcstring(funcNo,aa,x) {
	switch( funcNo ) {
	case 1:
		return timesString(aa,x);
		break;
	case 2:
		return divString(aa,x);
		break;
	case 3:
		return timesString(aa,x)+"<sup>2</sup>";
		break;
	case 4:
		return "√(|"+divString(x,aa)+ "|)";
		break;
	case 5:
		return "sin("+timesString(aa,x)+")";
		break;
	case 6:
		return divString("arcsin("+x+")",aa);
		break;
	case 7:
		return "exp("+timesString(aa,x)+")";
		break;
	case 8:
		return "log("+divString("|"+x+"|", aa)+")";
		break;
	}
}
var a=1;
var b=1;
function changeA_fc() {
	var elements = document.getElementsByName('a_f_value');
	a *=10;
	a=Math.round(a);
	a = a/ 10;
	var i;
	for(i=0; i<elements.length ; i++ ) {
		elements.item(i).innerHTML=a;
	}
	$('#yfx').html(Funcstring(fnox,a,'<span class="aka">x</span>'));
	$('#zgfx').html(Funcstring(fnoy,b,"("+Funcstring(fnox,a,'<span class="aka">x</span>')+")"));
}
function changeB_fc() {
	var elements = document.getElementsByName('b_f_value');
	b *=10;
	b=Math.round(b);
	b = b/ 10;
	var i;
	for(i=0; i<elements.length ; i++ ) {
		elements.item(i).innerHTML=b;
	}
	$('#zgy').html(Funcstring(fnoy,b,'<span class="ao">y</span>'));
	$('#zgfx').html(Funcstring(fnoy,b,"("+Funcstring(fnox,a,'<span class="aka">x</span>')+")"));
}

function upA_fc() {
	a +=0.1;
	if( a>5 ) { a=5; }
	changeA_fc();
}
function downA_fc() {
	a-=0.1;
	if( a<-5 ) { a=-5;}
	changeA_fc();
}
function upB_fc() {
	b +=0.1;
	if( b>5 ) { b=5; }
	changeB_fc();
}
function downB_fc() {
	b-=0.1;
	if( b<-5 ) { b=-5;}
	changeB_fc();
}
function changefuncxy(i) {
	fnox=Number($(i).val());
	$('#yfx').html(Funcstring(fnox,a,'<span class="aka">x</span>'));
	$('#zgfx').html(Funcstring(fnoy,b,"("+Funcstring(fnox,a,'<span class="aka">x</span>')+")"));
}
function changefuncyz(i) {
	fnoy=Number($(i).val());
	$('#zgy').html(Funcstring(fnoy,b,'<span class="ao">y</span>'));
	$('#zgfx').html(Funcstring(fnoy,b,"("+Funcstring(fnox,a,'<span class="aka">x</span>')+")"));
}
