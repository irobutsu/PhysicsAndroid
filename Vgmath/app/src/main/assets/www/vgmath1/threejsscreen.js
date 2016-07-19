// JavaScript Document
// three.jsを使って3d図を描くキャンバスです。
var ThreejsScreen=function(canvas_string,hw,adjustW) {
	// hwは縦÷横の数字。
    canvas = document.getElementById(canvas_string);
	this.canvas=canvas;
	if( hw === undefined ) {
		hw=1;
	}
	if( adjustW === undefined ) {
		adjustW=0.5;
	}
	if( adjustW > 0 ) {
		// 画面サイズにあわせてcanvasサイズを変える。
		// 画面横幅のadjustW倍になる。ただし、縦が苦しい
		// 場合はもっと縮む。
		// この機能を使わない場合はadjustWに負の値をセット。

		var wx=document.body.clientWidth*adjustW;
		var hy=document.body.clientHeight*0.7;
		if( hy<=0 ) { hy=wx;}
		if( hy > wx*hw ) {
			canvas.width=wx;
			canvas.height=wx*hw;
		} else {
			canvas.height=hy;
			canvas.width=hy/hw;	
		}
	}
	var param={ canvas:canvas,antialias:true };
	var useWebGLflg;
	if (window.WebGLRenderingContext && ( canvas.getContext( 'webgl' ) || canvas.getContext( 'experimental-webgl' ) ) ) {
		this.renderer = new THREE.WebGLRenderer(param);
		useWebGLflg=true;
	} else {
		this.renderer = new THREE.CanvasRenderer(param);
		useWebGLflg=false;
	}
	if(!this.renderer ) {
		alert("three.jsの初期化に失敗しました。");
	}
	this.renderer.setSize(canvas.width,canvas.height);
	//this.renderer.domElement.style.position = 'absolute'; 
	this.setClearColor(0x8FFFFF,1.0);

	this.scene = new THREE.Scene();
	this.light = new THREE.DirectionalLight(0xFFFFFF, 1.0, 0);
	this.light.position.set( 100, -100, 100 );
	this.alight=new THREE.AmbientLight(0x333333);
	this.scene.add(this.light);                   
	this.scene.add(this.alight);
	this.camera=new THREE.PerspectiveCamera(45,canvas.width/canvas.height,1,10000);
	this.camera.position.set(50,40,0);
	this.camera.up.set(0,0,1);
	this.camera.lookAt({x:0,y:0,z:0});
	this.controls = new THREE.TrackballControls(this.camera,canvas);
    this.controls.rotateSpeed = 0.5;
	this.useWebGL=function() { return useWebGLflg;};
};

ThreejsScreen.prototype={
	setClearColor:function(c,n){this.renderer.setClearColor(c,n);},
	draw:function(){
		this.renderer.clear();
		this.renderer.render(this.scene,this.camera);
	},
	render:function() {
		this.renderer.render(this.scene, this.camera);
	},
	controlsUpdate:function() { this.controls.update();},
	setLightPosition:function(xx,yy,zz) {
		this.light.position.set(xx,yy,zz);
	},
	setLightColor:function(rr,gg,bb) { 
		this.light.color.r=rr;
		this.light.color.g=gg;
		this.light.color.b=bb;
	},
	setALightColor:function(rr,gg,bb) { 
		this.alight.color.r=rr;
		this.alight.color.g=gg;
		this.alight.color.b=bb;
	},
	add:function(obj) { this.scene.add(obj);},
	setCameraPosition:function(x,y,z) { this.camera.position.set(x,y,z);},
	setCameraTarget:function(x,y,z) { this.controls.target=new THREE.Vector3(x,y,z);}
};

