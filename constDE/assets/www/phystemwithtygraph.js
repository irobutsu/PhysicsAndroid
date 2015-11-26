var PhystemWithTYGraph=function(canvas_string,graphcanvas_string,ww,w1,hh,adjustW,left,bottom,gleft) {
	Phystem.call(this,canvas_string,ww,hh,adjustW*ww/(ww+w1),left,bottom);
	var graphleft=(gleft == undefined ) ? -1: gleft;
	this.graph=new RescaleCanvas(graphcanvas_string,w1,hh,adjustW*w1/(ww+w1),graphleft,bottom);
	this.graph.writeCoordinate();
	this.gy0=0;
	this.t=0;
}

PhystemWithTYGraph.prototype=Object.create(Phystem.prototype);
PhystemWithTYGraph.prototype.constructor=PhystemWithTYGraph;

PhystemWithTYGraph.prototype.graphRestart=function(x0,v0) {
	this.t=0;
	this.dyObjs[0].p.y=x0;
	this.dyObjs[0].v.y=v0;
	this.gy0=this.dyObjs[0].p.y;
	this.graph.clearALL();
	this.graph.writeCoordinate();
};

PhystemWithTYGraph.prototype.writeContents=function() {
	Phystem.prototype.writeContents.call(this);
	this.graph.writeLine(this.t,this.gy0,this.t+this.stepT*this.drawStep,this.dyObjs[0].p.y,"rgb(255,0,0)");
	this.t+=this.stepT*this.drawStep;
	this.gy0=this.dyObjs[0].p.y;
};
