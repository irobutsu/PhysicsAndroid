var gs;
var gs2;
var x=6;
$(document).delegate('#page0','pageinit',function(e) {
    gs=new RescaleScreen("canvas1",10,10,0.5);
    gs.setLeft(-1);
    gs.setBottom(-1);
    gs.gobackOriginalSize();
    gs2=new GraphScreen("canvas2",12,20,0.3
	);
    gs2.setLeft(-1);
    gs2.setBottom(-2);
    gs2.gobackOriginalSize();
    gs2.setylabel("S.png");
    gs2.setxlabel("x.png");
    gs.writeGraphs=function() {
		graph1WriteContent();
    }    
    gs2.writeGraphs=function() {
		graph2WriteContent();
    }
});
$(document).delegate('#page0','pageshow',function(e) {
    x=Number($('#slider').val());
    graphWrite();
});
function graphWrite() {
    graph1Write();
    graph2Write();
	if( dxflg ) {
		document.getElementById("answer").innerHTML="S="+S(x);
		document.getElementById("answer2").innerHTML="S+ΔS="+S(x+0.5);
	} else {
		document.getElementById("answer").innerHTML="S="+S(x);
	}
}

function graph2Write() {
    gs2.setScale()
    gs2.clearALL();	
    gs2.writeCoordinate();
    graph2WriteContent();
}
function graph2WriteContent() {
    gs2.setFunction(S);
    gs2.writeFunction("rgba(0,0,0,1)");
	gs2.writeLine(x,0,x,S(x),"rgba(255,0,0,1)");
	if( dxflg ) {
		gs2.writeLine(x+0.5,0,x+0.5,S(x+0.5),"rgba(0,0,255,1)");
	}
}
function S(x) {
	return x*(8-x);
}
function graph1Write() {
    gs.setScale();
    gs.clearALL();	
	gs.writeLine(0,0,x,0,"rgba(0,0,0,1)");
	gs.writeLine(x,0,x,8-x,"rgba(0,0,0,1)");
	gs.writeLine(x,8-x,0,8-x,"rgba(0,0,0,1)");
	gs.writeLine(0,8-x,0,0,"rgba(0,0,0,1)");
	gs.writeRect(0,0,x,8-x,"rgba(255,0,0,0.5)");
	if( dxflg ) {
		gs.writeRect(0,0,x+0.5,7.5-x,"rgba(0,0,255,0.5)");
	}
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
var dxflg=false;
function  ondeltaFlgchange() {
    dxflg=$("#checkboxdeltax").prop("checked");
	graphWrite();
}