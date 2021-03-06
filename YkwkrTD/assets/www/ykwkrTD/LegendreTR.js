var gs;
var gs2;
var gs3;
var a=0;
var x=1;
var px0flg=false;
var px1flg=false;
var fimg=new Image();
var tildefimg=new Image("tildef.img");
var xpximg=new Image();
var animeCount=0;
$(document).delegate('#page1','pageinit',function(e) {
    gs=new GraphScreen("canvas1",6,5,0.45);
    gs.setLeft(-3);
    gs.setBottom(-1);
    gs.gobackOriginalSize();
    gs.setxlabel("x.png");
    gs.setylabel("f.png");
    gs2=new GraphScreen("canvas2",6,5,0.45);
    gs2.setLeft(-3);
    gs2.setBottom(-1);
    gs2.gobackOriginalSize();
    gs2.setylabel("f.png");
    gs2.setxlabel("px.png");
    gs.writeGraphs=function() {
		graph1WriteContent();
    }    
    gs2.writeGraphs=function() {
		graph2WriteContent();
    }
});
$(document).delegate('#page1','pageinit',function(e) {
    a=Number($('#slider').val());
    graphWrite();
});
function graphWrite() {
    graph1Write();
    graph2Write();
    if( animeCount >= 0 ) {
		animeCount++;
		if( animeCount >100 ) {
			animeCount=0;
		}
		setTimeout("graphWrite();",50);
    }
}

$(document).delegate('#page3','pageinit',function(e) {
    tildefimg.src="tildef.png";
    fimg.src="f.png";
    xpximg.src="xpx.png";
    gs3=new GraphScreen("canvas3",6,5,0.45);
    gs3.setLeft(-3);
    gs3.setBottom(-2);
    gs3.gobackOriginalSize();
    gs3.setxlabel("x.png");
    gs3.setylabel("f.png");
    gs3.writeGraphs=function() {
		graph3WriteContent();
    }
    gs4=new GraphScreen("canvas4",6,5,0.45);
    gs4.setW(6);
    gs4.setLeft(-3);
    gs4.setH(5);
    gs4.setBottom(-2);
    gs4.gobackOriginalSize();
    gs4.setxlabel("px.png");
    gs4.setylabel("ftildef.png");
    gs4.writeGraphs=function() {
		graph4Write();
    }
});

$(document).delegate('#page3','pageinit',function(e) {
    a=Number($('#slider_a').val());
    x=Number($('#slider_x').val());
    graphWrite3();
});
function graphWrite3() {
    graph3Write();
    graph4Write();
}
function graph4Write() {
	gs4.setScale();
	gs4.clearALL();	
	gs4.writeCoordinate();
	graph4WriteContent();
}
function graph4WriteContent() {
    gs4.setFunction(tildef);
    gs4.writeFunction("rgba(0,0,255,0.5)");
    gs4.setFunction(fp);
    gs4.writeFunction("rgba(255,0,0,0.5)");
    gs4.writePoint(2*(x-a),-(x-a)*(x+a),0.05,"rgba(0,0,255,1)");
}
function graph3Write() {
    gs3.setScale();
    gs3.clearALL();	
    gs3.writeCoordinate();
    graph3WriteContent();
}

function graph3WriteContent() {
    gs3.setFunction(f);
    gs3.writeFunction("rgba(255,0,0,0.5)");
    gs3.writeLinear(2*(x-a),-(x-a)*(x+a),"rgba(128,128,0,1)");
    gs3.writePoint(0,-(x-a)*(x+a),0.05,"rgba(0,0,255,1)");
    gs3.ctx.drawImage(tildefimg,-0.2,-(x-a)*(x+a),0.204,0.404);
    gs3.writeLine(0,(x-a)*(x-a),x,(x-a)*(x-a),"rgba(120,120,120,0.5)");
    gs3.writePoint(0,(x-a)*(x-a),0.05,"rgba(60,60,60,1)");
    gs3.ctx.drawImage(fimg,-0.2,(x-a)*(x-a),0.176,0.320);
    gs3.ctx.strokeStyle="rgba(120,120,120,0.5)";
    gs3.ctx.beginPath();
    gs3.ctx.moveTo(0,(x-a)*(x-a));
    gs3.ctx.quadraticCurveTo(-0.5,-(x-a)*a,0,-(x-a)*(x+a))
    gs3.ctx.stroke();
    gs3.ctx.drawImage(xpximg,-1.2,-(x-a)*a,0.768,0.224);
}
function graph2Write() {
    gs2.setScale()
    gs2.clearALL();	
    gs2.writeCoordinate();
    graph2WriteContent();
}
function graph2WriteContent() {
    gs2.setFunction(fp);
    gs2.writeFunction("rgba(255,0,0,0.5)");
    if(px0flg ) {
		gs2.writePoint(0,0,0.05,"rgba(128,0,128,1)");
    }
    if(px1flg ) {
		gs2.writePoint(1,0.25,0.05,"rgba(0,128,128,1)");
    }
    if( animeCount >= 0 ) {
		var xx;
		if( animeCount > 50 ) {
			xx=(75-animeCount)/12.5
		} else {
			xx=(animeCount-25)/12.5;
		}
		gs2.writePoint(xx,xx*xx*0.25,0.05,"rgba(128,128,0,1)");	
    }
}
function graph1Write() {
    gs.setScale();
    gs.clearALL();	
    gs.writeCoordinate();
    graph1WriteContent();
}

function graph1WriteContent() {
    gs.setFunction(f);
    gs.writeFunction("rgba(255,0,0,0.5)");
    if(px0flg ) {
		gs.writeLinear(0,0,"rgba(128,0,128,1)");	
		gs.writePoint(a,0,0.05,"rgba(128,0,128,1)");
    }
    if(px1flg ) {
		gs.writeLinear(1,-a-0.25,"rgba(0,128,128,1)");
		gs.writePoint(Number(a)+0.5,0.25,0.05,"rgba(0,128,128,1)");
    }
    if( animeCount >= 0 ) {
		var xx;
		if( animeCount > 50 ) {
			xx=(75-animeCount)/12.5;
		} else {
			xx=(animeCount-25)/12.5;
		}
		var yy=xx*xx*0.25;
		gs.writePoint(a+xx*0.5,yy,0.05,"rgba(128,128,0,1)");
		gs.writeLinear(xx,-xx*(a+xx*0.25),"rgba(128,128,0,1)");
    }
}
$(document).delegate('#page4','pageinit',function(e) {
    tildefimg.src="tildef.png";
    fimg.src="f.png";
    xpximg.src="xpx.png";
    gs5=new GraphScreen("canvas5",6,5,0.45);
    gs5.setLeft(-1);
    gs5.setBottom(-1);
    gs5.gobackOriginalSize();
    gs5.setxlabel("x.png");
    gs5.setylabel("f.png");
    gs5.writeGraphs=function() {
		graph5Write();
    }
    gs6=new GraphScreen("canvas6",6,5,0.45);
    gs6.setLeft(-3);
    gs6.setBottom(-2);
    gs6.gobackOriginalSize();
    gs6.setxlabel("px.png");
    gs6.setylabel("ftildef.png");
    gs6.writeGraphs=function() {
		graph6Write();
    }
});
$(document).delegate('#page4','pageinit',function(e) {
    animeCount=0;
    graphWrite4();
});
function graphWrite4() {
    graph5Write();
    graph6Write();
    if( animeCount >= 0 ) {
		animeCount++;
		if( animeCount >100 ) {
			animeCount=0;
		}
		setTimeout("graphWrite4();",100);
    }
}
function graph5Write() {
    gs5.setScale();
    gs5.clearALL();	
    gs5.writeCoordinate();
	graph5WriteContent();
}
function graph5WriteContent() {
    gs5.setFunction(oref);
    gs5.writeFunction("rgba(255,0,0,0.5)");
    if( animeCount >= 0 ) {
		var xx;
		var fwdFlg;
		var count;
		if( animeCount > 50 ) {
			fwdFlg=false;
			count=100-animeCount;
		} else {
			fwdFlg=true;
			count=animeCount;
		}
		
		if( count >30 ) {
			xx=(count-10)/20;
		} else if( count >=20 ) {
			xx=1;
		}else {
			xx=count/20;
		}
		var yy=oref(xx);
		gs5.writePoint(xx,yy,0.05,"rgba(128,128,0,1)");
		if( count >=20 && count <= 30 ) {
			gs5.writeLinear((count-20)/10,1-(count-20)/10,"rgba(128,128,0,1)");
			gs5.writePoint(0,1-(count-20)/10,0.05,"rgba(0,0,255,1)");
		} else {
			if( count <20 ) {
				gs5.writeLinear(2*xx-2,-xx*xx+2,"rgba(128,128,0,1)");
				gs5.writePoint(0,-xx*xx+2,0.05,"rgba(0,0,255,1)");
			} else {
				gs5.writeLinear(xx,-0.5*xx*xx+0.5,"rgba(128,128,0,1)");
				gs5.writePoint(0,-0.5*xx*xx+0.5,0.05,"rgba(0,0,255,1)");
			}
		}
    }
}
function graph6Write() {
    gs6.setScale();
    gs6.clearALL();	
    gs6.writeCoordinate();
	graph6WriteContent();
}
function graph6WriteContent() {
    gs6.setFunction(oretildef);
    gs6.writeFunction("rgba(0,0,255,0.5)");
    gs6.setFunction(oretildefpx);
    gs6.writeFunction("rgba(255,0,0,0.5)");
    if( animeCount >= 0 ) {
		var xx;
		var fwdFlg;
		var count;
		if( animeCount > 50 ) {
			fwdFlg=false;
			count=100-animeCount;
		} else {
			fwdFlg=true;
			count=animeCount;
		}
		
		if( count >30 ) {
			xx=(count-10)/20;
		} else if( count >=20 && count <= 30 ) {
			xx=1;
		}else {
			xx=count/20;	
		}
		if( count <20 ) {
			gs6.writePoint(2*xx-2,-xx*xx+2,0.05,"rgba(0,0,255,1)")
			gs6.writePoint(2*xx-2,xx*xx-2*xx+2,0.05,"rgba(128,128,0,1)")
		} else if( count <= 30 ) {
			gs6.writePoint((count-20)/10,(30-count)/10,0.05,"rgba(0,0,255,1)");
			gs6.writePoint((count-20)/10,1,0.05,"rgba(128,128,0,1)")
		} else {
			gs6.writePoint(xx,-0.5*xx*xx+0.5,0.05,"rgba(0,0,255,1)");
			gs6.writePoint(xx,0.5*xx*xx+0.5,0.05,"rgba(128,128,0,1)");
		}
    }
}
function oretildefpx(x) {
	if( x>0 ) {
		if( x<1 ) {
			return 1;
		} else {
			return 0.5*x*x+0.5;
		}
    } else {
		return 0.25*x*x+1;
    }
}
function oretildef(x) {
    if( x>0 ) {
		if( x<1 ) {
			return 1-x;
		} else {
			return -0.5*x*x+0.5;
		}
    } else {
		return -0.25*x*x-x+1;
    }
}
function oref(x) {
    if( x<1 ) {
		return x*x-2*x+2;
    } else {
		return 0.5*x*x+0.5;
    }
}



function tildef(x) {
    return -0.25*x*x-a*x;
}
function f(x) {
    return (x-a)*(x-a);
}
function fp(x) {
    return x*x*0.25;
}

function onanime() {
    if( $("#animeon").prop("checked") ) {
		animeCount=0;	
    } else {
		animeCount=-1;
    }
    graphWrite();
}
function  onpx0change() {
    px0flg=$("#checkboxpxzero_0").prop("checked");
    if( animeCount <0 ) {
		graphWrite();	
    }
}
function onpx1change() {
    px1flg=$("#checkboxpxone_0").prop("checked");
    if( animeCount <0 ) {
		graphWrite();
    }
}

$(document).delegate('#page5','pageinit',function(e) {
    gs7=new GraphScreen("canvas7",6,5,0.45);
    gs7.setLeft(-1);
    gs7.setBottom(-1);
    gs7.gobackOriginalSize();
    gs7.setxlabel("x.png");
    gs7.setylabel("f.png");
    gs7.writeGraphs=function() {
		graph7Write();
    }
    gs8=new GraphScreen("canvas8",6,5,0.45);
    gs8.setLeft(-3);
    gs8.setBottom(-1);
    gs8.gobackOriginalSize();
    gs8.setxlabel("px.png");
    gs8.setylabel("ftildef.png");
    gs8.writeGraphs=function() {
		graph8Write();
    }
});
$(document).delegate('#page5','pageinit',function(e) {
    animeCount=0;
    graphWrite5();
});
function graphWrite5() {
    graph7Write();
    graph8Write();
    if( animeCount >= 0 ) {
		animeCount++;
		if( animeCount >100 ) {
			animeCount=0;
		}
		setTimeout("graphWrite5();",100);
    }
}

function ouf(x) {
	if( x<3 && x>1 ) {
		return ((x-2)*(x-2)-1)*((x-2)*(x-2)-1)/2+0.5;
		// ��ʬ�� 4*(((x-2)*(x-2)-1)*(x-2)
	} else if( x<1 ){
		return 2*(x-1)*(x-1)+0.5;
	} else {
		return 2*(x-3)*(x-3)+0.5;
	}
}

function graph7Write() {
    gs7.setScale();
    gs7.clearALL();	
    gs7.writeCoordinate();
	graph7WriteContent();
}
function graph7WriteContent() {
    gs7.setFunction(ouf);
    gs7.writeFunction("rgba(255,0,0,0.5)");
    if( animeCount >= 0 ) {
		var xx;
		var fwdFlg;
		var count;
		if( animeCount > 50 ) {
			fwdFlg=false;
			count=100-animeCount;
		} else {
			fwdFlg=true;
			count=animeCount;
		}
		
		if( count <20 ) {
			xx=count/20.0;
		} else if( count <=30 ) {
			xx=(count-20)/5.0+1;
		}else {
			xx=(count-30)/20.0+3;
		}
		var yy=ouf(xx);
		
		gs7.writePoint(xx,yy,0.05,"rgba(128,128,0,1)");
		if( count >=20 && count <= 30 ) {
			gs7.writePoint(xx,0.5,0.05,"rgba(128,128,0,0.6)");
			gs7.writeLinear(0,0.5,"rgba(128,128,0,1)");
			gs7.writePoint(0,0.5,0.05,"rgba(0,0,255,0.4)");
		} else if( count <20 ) {
			var p=-2*xx*xx+2.5;
			gs7.writeLinear(4*(xx-1),p,"rgba(128,128,0,1)");
			gs7.writePoint(0,p,0.05,"rgba(0,0,255,1)");
		} else {
			var p=-2*xx*xx+18.5;
			gs7.writeLinear(4*(xx-3),p,"rgba(128,128,0,1)");
			gs7.writePoint(0,p,0.05,"rgba(0,0,255,1)");
		}
    }
}
function graph8Write() {
    gs8.setScale();
    gs8.clearALL();	
    gs8.writeCoordinate();
	graph8WriteContent();
}
function outildef(x) {
	return x*x/8+0.5;
}
function outildefpx(x) {
	if( x <0 ) {
		return -x*x/8-x+0.5;
	} else {
		return -x*x/8-3*x+0.5;
	}
}


function graph8WriteContent() {
    gs8.setFunction(outildef);
    gs8.writeFunction("rgba(255,0,0,0.5)");
    gs8.setFunction(outildefpx);
    gs8.writeFunction("rgba(0,0,255,0.5)");
    if( animeCount >= 0 ) {
		var xx;
		var fwdFlg;
		var count;
		if( animeCount > 50 ) {
			fwdFlg=false;
			count=100-animeCount;
		} else {
			fwdFlg=true;
			count=animeCount;
		}
		if( count <20 ) {
			xx=count/20.0;
		} else if( count <=30 ) {
			xx=(count-20)/5.0+1;
		}else {
			xx=(count-30)/20.0+3;
		}
		var yy=ouf(xx);

		if( count <20 ) {
			gs8.writePoint(4*(xx-1),2*(xx-1)*(xx-1)+0.5,0.05,"rgba(128,128,0,1)")
			gs8.writePoint(4*(xx-1),-2*xx*xx+2.5,0.05,"rgba(0,0,255,1)")
		} else if( count <= 30 ) {
			gs8.writePoint(0,0.5,0.05,"rgba(0,0,255,1)");
			gs8.writePoint(0,0.5,0.05,"rgba(128,128,0,1)")
		} else {
			gs8.writePoint(4*(xx-3),2*(xx-3)*(xx-3)+0.5,0.05,"rgba(128,128,0,1)")
			gs8.writePoint(4*(xx-3),-2*xx*xx+18.5,0.05,"rgba(0,0,255,1)")
		}
    }
}
