var w=400;
var h=400;
var t=0;
var nowDrag=null;
var func;
var mode;
var a=1.0;
var graphics;
var canvas;


function init(canvas_string) {    
    canvas = document.getElementById(canvas_string);
    graphics=canvas.getContext("2d");
	var w=document.body.clientWidth*0.9;
    var h=window.innerHeight*0.6;
	
	if( w> h ) {
		w=h;
	} else {
		h=w;
	}
	canvas.width=w;
	canvas.height=w;
	var sc=canvas.width/400;
	graphics.scale(sc,sc);
    reset();
}

function reset() {
    updatedydx();
    if( mode== 2) {
	plotf();
    } else if( mode==1 ) {
	anime_go();
    }
}

function updatedydx() {
    var i;
    var j;
    
    graphics.clearRect(0,0,w,h);
    graphics.strokeStyle="rgb(0,0,0)";
    graphics.beginPath();
    graphics.moveTo(200,0);
    graphics.lineTo(200,w-1);
    graphics.moveTo(0,200);
    graphics.lineTo(w-1,200);
    graphics.stroke();

    graphics.strokeStyle="rgb(0,200,200)";
    graphics.beginPath();
    graphics.moveTo(100,0);
    graphics.lineTo(100,w-1);
    graphics.moveTo(300,0);
    graphics.lineTo(300,w-1);
    graphics.moveTo(0,100);
    graphics.lineTo(w-1,100);
    graphics.moveTo(0,300);
    graphics.lineTo(w-1,300);
    graphics.stroke();
    
    for ( i=0 ; i< 20 ; i++ ) {
	for ( j=0 ; j< 20 ; j++ ) {
	    var xx,yy;
	    xx=i*20+10;
	    yy=j*20+10;
	    graphics.strokeStyle="rgb(128,128,128)";
		graphics.beginPath();
	    graphics.arc(xx,yy,10,0,2*Math.PI,true);
// （xx,yy）は画面上の座標。実際の座標は、(0.01*(xx-200),0.01*(200-yy))になる。
		graphics.stroke();
	    var diff=difff(0.01*(xx-200),0.01*(200-yy));
	    var r=Math.sqrt(1+diff*diff);
	    var yyy=(10.0*diff/r+0.5);
	    var xxx=(10.0*Math.sqrt(r*r-diff*diff)/r);
	    
	    graphics.beginPath();
	    graphics.moveTo(xx+xxx,yy-yyy);
	    graphics.lineTo(xx-xxx,yy+yyy);
	    graphics.stroke();
	}
    }		    
}



var i, k;
var kk;
var kkk;
var C;
var xxx;

var anime_End;

function anime_go() {
    i=0;
    k=-65536;
    anime_End=false;
    anime();
}

var px,py;

function anime() {
    var ppx,ppy;
    
	if( anime_End ) {
		return;
    }
    graphics.strokeStyle="rgb(255,0,0)";
    switch(val) {
    case 0:
		if( k==-65536 ) {
	    	k=40;
	    	i=0;
		}
		if( i==0 ) {
			px=0;
			py=k;
	    	i++;
		} else {
	    	graphics.beginPath();
	    	graphics.moveTo(px,py);
	    	px=i;
	    	py=k-(a*i);
	    	graphics.lineTo(px,py);
	    	graphics.stroke();
	    	i+=3;
	    	if( py<0 || i>= 400 ) {
				i=0;
			k += 40;
			if( k-(a*400) > 400 ) {
		    	anime_End=true;
			}
	    }
	}
	break;
    case 1:
	if( k==-65536 ) {
	    k=40;
		i=0;
	  
	}
	if( i==0 ) {
		kk=200.0*a;
		if( k-kk > 0) {
			i=0;
			py=k-kk;
		} else {
			i=200-Math.sqrt(200*k/a);
			py=0;
		}
	    px=i;
	   
		if( py >400 ) {
			anime_End=true;
		}
	    i+=2;
	} else {
	    ppx=px; ppy=py;
		i+=2;
		px=i;
		py=k-(a*(2.0-0.01*i)*(2.0-0.01*i)*50.0);
		if( i> 400 || (py<0 && ppy<0 ) ) {
		    i=0;
		    k += 40;
		}
		graphics.beginPath();
		graphics.moveTo(ppx,ppy);
		graphics.lineTo(px,py);
		graphics.stroke();
	}
	break;
    case 2:
	if( k==-65536 ) {
	    kk=Math.exp(-a*2.0);
	    i=0;
	    k=40;
	}
	if( i==0 ) {
	    px=0;
	    py=k*kk+200;
	    i+=2;
	} else {
	    ppx=px; ppy=py;
	    px=i;
	    py=200+(k*Math.exp(a*0.01*(i-200)));
	    i+=2;
	    if( i>400 || (py>400 && ppy>400 ) || (py<0 && ppy<0 ) ) {
		i=0;
		if( k>0 ) {
		    k=-k;
		} else {
		    k = -k +40;
		}
		ppx=-65536;
		if( k*kk > 200 ) {
		    anime_End=true;
		}
	    }
	    if( ppx != -65536 && ( !(ppy >400 && py>400) || !(ppy<0 && py<0)) ){
		graphics.beginPath();
		graphics.moveTo(ppx,ppy);
		graphics.lineTo(px,py);
		graphics.stroke();
	    }
	}
	break;
    case 3:
	if( k==-65536 ) {
	    i=400;
	    k=-200;
	    kk=1.0;
	}
	C=0.01*(200-k);
	if( i==400 ) {
	    px=200-(kk*100.0*Math.exp((-2.0-C)/a));
	    py=400;
	    i-=4;
	} else {
	    ppx=px;ppy=py;
	    py=i;
	    px=200-(kk*100.0*Math.exp((0.01*(200-i)-C)/a));
	    i-=4;
	    if( i<-1 || (px<0 && ppx<0 ) || (px>400&&ppx>400) ) {
		i=400;
		k +=40;
		ppx=-65536;
		if( k>=400 ) {
		    if( kk>0 ) {
			kk=-1.0;
			k=-200;
		    } else {
			anime_End=true;
		    }
		}
	    }
	    if( ppx != -65536 ) {
		graphics.beginPath();
		graphics.moveTo(ppx,ppy);
		graphics.lineTo(px,py);
		graphics.stroke();
	    }
	}
	break;
	case 4:
	if( k==-65536 ) {
	    i=400; k=-200; kk=1.0;
	}
	C=0.01*(200-k);
	if( i==400 && kk==1.0 ) {
	    px=200+(100.0*a/(C+2.0));
	    py=400;
	    i-=4;
	} else if( i==0 && kk== -1.0) {
	    px=200+(100.0*a/(C-2.0));
	    py=0;
	    i+=4;
	} else if( kk==1.0 ) {
	    ppx=px;ppy=py;
	    py=i;
	    px=200+(100.0*a/(C-0.01*(200-i)));
	    i-=4;
	    if( i<0 || ppx >400 ) {
		k +=40;
		i=400;
		if( k >= 800 ) { i=0; k=-200; kk=-1.0; }
	    }
	    if( ppx != -65536 ) {
		graphics.beginPath();
		graphics.moveTo(ppx,ppy);
		graphics.lineTo(px,py);
		graphics.stroke();
	    }
	} else {
	    ppx=px;ppy=py;
	    py=i;
	    px=200+(100.0*a/(C-0.01*(200-i)));
	    i += 4;
	    if( i>400 || ppx <0 ) {
		k +=40;
		i=0;
		if( k>= 800 ) { anime_End=true;}
	    }
	    if( ppx != -65536 ) {
		graphics.beginPath();
		graphics.moveTo(ppx,ppy);
		graphics.lineTo(px,py);
		graphics.stroke();
	    }
	}
	break;
    case 5:
	if( k==-65536 ) {
	    i=0; k=-4;
	}
	if( i==0 ) {
		ppx=200;ppy=200;
	} else {
		ppx=px; ppy=py;
	}
	
	if( k<5 ) {
		px=200+3*i;
	 	py=200+50*k*Math.pow((0.03*i),a);
	} else {
		px=200-3*i;
		py=200-50*(k-9)*Math.pow((0.03*i),a);
	}
	graphics.beginPath();
	graphics.moveTo(ppx,ppy);
	graphics.lineTo(px,py);
	graphics.stroke();
	i+=1;
	if( 3*i > 200 || (py<0 && ppy<0 ) || (py>400 && ppy>400) ) {
		i=0;
		k+=1;
		if( k> 13 ) {
			anime_End=true;
		}
	}
	break;
    case 6:
	if( k==-65536 ) {
	    k=20;
	    i=0;
	}
	if( i==0 ) {
	    px=200+k;
	    py=200;
	    i+=3;
	} else {
	    ppx=px;
	    ppy=py;
	    px=200+k*Math.cos(Math.PI/180.0*i);
	    py=200+k*a*Math.sin(Math.PI/180.0*i);
	    i+=3;
	    graphics.beginPath();
	    graphics.moveTo(ppx,ppy);
	    graphics.lineTo(px,py);
	    graphics.stroke();
	    if( i>360 ) {
		k+=40;
		i=0;
		if( k> 550 ) {anime_End=true;}
	    }
	}
	break;
    case 7:
	if( k==-65536 ) {
	    k=40; i=0; kk=1.0;
	}
	if( kk==1.0 ) {
	    if( i==0 ) {
		px=0; py=200-(100.0*Math.sqrt(4.0*a+0.0001*k*k));
		i+=2;
	    } else {
		do {
		    ppx=px; ppy=py;
		    px=i;
		    py=200-(100.0*Math.sqrt(a*(0.0001*(i-200)*(i-200))+0.0001*k*k));
		    i+=2;
		    if( i>400 ) {
			k+=40;
			i=0;
			if( k>=200 ) {
			    kk=2.0; k=40;
			}
			ppx=-65536;
			ppy=200;
		    }
		} while( (py<0 && ppy<0) );
		if( ppx != -65536) {
		    graphics.beginPath();
		    graphics.moveTo(ppx,ppy);
		    graphics.lineTo(px,py);
		    graphics.stroke();
		}
	    }
	} else if( kk==2.0 ) {
	    if( i==0 ) {
		px=0; py=200+(100.0*Math.sqrt(4.0*a+0.0001*k*k));
		i+=2;
	    } else {
		do {
		    ppx=px; ppy=py;
		    px=i;
		    py=200+(100.0*Math.sqrt(a*(0.0001*(i-200)*(i-200))+0.0001*k*k));
		    i+=2;
		    if( i>400 ) {
			k+=40;
			i=0;
			if( k>=200 ) {
			    kk=3.0; k=0;
			}
			ppx=-65536;
			ppy=200;
		    }
		} while( (py>400 && ppy>400) );
		if( ppx != -65536) {
		    graphics.beginPath();
		    graphics.moveTo(ppx,ppy);
		    graphics.lineTo(px,py);
		    graphics.stroke();
		}
	    }
	} else if( kk==3.0 ) {
	    if( i==0 ) {
		py=0; px=200+(100.0*Math.sqrt(4.0/a+0.0001*k*k));
		i+=2;
	    } else {
		do {
		    ppx=px; ppy=py;
		    py=i;
		    px=200+(100.0*Math.sqrt((0.0001*(i-200)*(i-200))/a+0.0001*k*k));
		    i+=2;
		    if( i>400 ) {
			k+=40;
			i=0;
			if( k>=200 ) {
			    kk=4.0; k=0;
			}
			ppy=-65536;
			ppx=200;
		    }
		} while( (px>400 && ppx>400) );
		if( ppy != -65536) {
		    graphics.beginPath();
		    graphics.moveTo(ppx,ppy);
		    graphics.lineTo(px,py);
		    graphics.stroke();
		}
	    }
	} else {
	    if( i==0 ) {
		py=0; px=200-(100.0*Math.sqrt(4.0/a+0.0001*k*k));
		i+=2;
	    } else {
		do {
		    ppx=px; ppy=py;
		    py=i;
		    px=200-(100.0*Math.sqrt((0.0001*(i-200)*(i-200))/a+0.0001*k*k));
		    i+=2;
		    if( i>400 ) {
			k+=40;
			i=0;
			if( k>=200 ) {
			    anime_End=true;
			}
			ppy=-65536;
			ppx=200;
		    }
		} while( (px<0 && ppx<0) );
		if( ppy != -65536) {
		    graphics.beginPath();
		    graphics.moveTo(ppx,ppy);
		    graphics.lineTo(px,py);
		    graphics.stroke();
		}
	    }
	}
	break;
    case 8:
	if( k==-65536 ) {
	    k=0;
	    i=0;
	}
	if( i==0 ) {
	    px=0;
	    py=200-((k-200)*Math.exp(2*a));
	    i++;
	} else {
	    ppx=px;
	    ppy=py;
	    do {
		px=i;
		py=200-((k-200)*Math.exp(0.5*a*(0.0001*(i-200)*(i-200))));
		i++;
		if( i>399 ) {
		    k+=20;
		    i=0;
		    ppx=-65536;
		    py=200;
		    if( k>399 ) {
			anime_End=true;
		    }
		}
	    } while ( (py<0 && ppy<0 ) || (py>400 && ppy>400 ));
	    if( ppx != -65536 ) {
		graphics.beginPath();
		graphics.moveTo(ppx,ppy);
		graphics.lineTo(px,py);
		graphics.stroke();
	    }
	}
	break;
    case 9:
	if( k==-65536 ) {
	    k=0;i=0;
	}
	if( i==0 ) {
	    px=0;
	    py=200-((k-200)*Math.exp(-2*a));
	    i++;
	} else {
	    ppx=px;	ppy=py;
	    do {
		px=i;
		py=200-((k-200)*Math.exp(-0.5*a*(0.0001*(i-200)*(i-200))));
		i++;
		if( i>399 ) {
		    k+=20;
		    i=0;
		    ppx=-65536;
		    if( k>399 ) { anime_End=true;}
		}
	    } while ( (py<0 && ppy<0 ) || (py>400 && ppy>400 ));
	    if( ppx != -65536 ) {
		graphics.beginPath();
		graphics.moveTo(ppx,ppy);
		graphics.lineTo(px,py);
		graphics.stroke();
	    }
	}
	break;
    case 10:
	if( k==-65536 ) {
	    k=40; i=0;
	}
	if( i==0 ) {
	    px=0; py=k+(50*Math.cos(4.0*a)/a);
	    i+=2;
	} else {
	    ppx=px; ppy=py;
	    px=i; py=k+(50*Math.cos(2.0*a*(2.0-0.01*i))/a);
	    i+=2;
	    if( i>400 ) {
		k+=40;
		i=0;
		ppx=-65536;
		if( k>400 ) { anime_End=true;}
	    }
	    if( ppx != -65536) {
		graphics.beginPath();
		graphics.moveTo(ppx,ppy);
		graphics.lineTo(px,py);
		graphics.stroke();
	    }
	}	    
	break;
    case 11:
	if( k==-65536 ){
	    k=-400; i=0;
	}
	if( i==0 ) {
	    px=0; py=200-(100*(a+0.01*k*Math.exp(-2.0)));
	    i+=2;
	} else {
	    do {
		ppx=px; ppy=py;
		px=i; py = 200-(100*(-a*(0.01*(i-200)+1.0)+0.01*k*Math.exp(0.01*(i-200))));
		i+=4;
	    } while( i<=400 &&
		     ((ppy <0 && py <0 ) || (ppy>400 && py>400 ))
		     );   
	    if( i>400 ) {
		k += 80;
		i=0;
		ppx = -65536;
		if( k>400 ) { anime_End=true;}
	    }
		if( ppx != -65536 ) {
			graphics.beginPath();
			graphics.moveTo(ppx,ppy);
			graphics.lineTo(px,py);
			graphics.stroke();
	    }
	}
	break;
    }
	setTimeout("anime()",25);
}

function plotf() {
    graphics.strokeStyle="rgb(255,0,0)";
    switch(val) {
    case 0:
	for( k=0 ; k-(a*400) <= 400 ; k += 20 ) {
	    graphics.beginPath();
	    graphics.moveTo(0,k);
	    graphics.lineTo(400,k-(a*400));
	    graphics.stroke();
	} 
	break;
    case 1:
	kk=(200.0*a);
	for( k=20 ; k-kk <= 400 ; k += 20 ) {
	    graphics.beginPath();
	    graphics.moveTo(0,k-kk);
	    for( i=1 ; i<400 ; i++ ) {
		graphics.lineTo(i,k-(a*(2.0-0.01*i)*(2.0-0.01*i)*50.0));
	    }
	    graphics.stroke();
	} 
	break;
    case 2:
	kk=Math.exp(-a*2.0);
	graphics.beginPath();
	graphics.moveTo(0,200);
	graphics.lineTo(400,200);
	graphics.stroke();

	for( k=20 ; (k*kk)<=200 ; k += 20 ) {
	    graphics.beginPath();
	    graphics.moveTo(0,-k*kk+200);
	    for( i=1 ; i< 400 ; i++ ) {
		graphics.lineTo(i,200-(k*Math.exp(a*0.01*(i-200))));
	    }
	    graphics.stroke();
	}
	for( k=20 ; (k*kk)<=200 ; k += 20 ) {
	    graphics.beginPath();
	    graphics.moveTo(0,k*kk+200);
	    for( i=1 ; i< 400 ; i++ ) {
		graphics.lineTo(i,200+(k*Math.exp(a*0.01*(i-200))));
	    }
	    graphics.stroke();
	}
	break;
    case 3:
	for( k=-200 ; k<400 ; k += 20 ) {
	    C=0.01*(200-k);
	    graphics.beginPath();
	    graphics.moveTo(200-(100.0*Math.exp((-2.0-C)/a)),400);
	    for(i=399; i>0 ; i-- ) {
		xxx=200-(100.0*Math.exp((0.01*(200-i)-C)/a));
		graphics.lineTo(xxx,i);
		if( xxx <0 ) {
		    break;
		}
	    }
	    graphics.stroke();
	    graphics.beginPath();
	    graphics.moveTo(200+(100.0*Math.exp((-2.0-C)/a)),400);
	    for(i=399; i>0 ; i-- ) {
		xxx=200+(100.0*Math.exp((0.01*(200-i)-C)/a));
		graphics.lineTo(xxx,i);
		if( xxx > 400 ) {
		    break;
		}
	    }
	    graphics.stroke();
	}
	break;
    case 4:
	for( k=-400 ; k<800 ; k += 20 ) {
	    C=0.01*(200-k);
	    graphics.beginPath();
	    graphics.moveTo(200+(100.0*a/(C+2.0)),400);
	    for(i=399; i>k ; i-- ) {
		xxx=200+(100.0*a/(C-0.01*(200-i)));
		graphics.lineTo(xxx,i);
		if( xxx > 400 ) {
		    break;
		}
	    }
	    graphics.stroke();
	    graphics.beginPath();
	    graphics.moveTo(200+(100.0*a/(C-2.0)),0);
	    for(i=1; i<k ; i++) {
		xxx=200+(100.0*a/(C-0.01*(200-i)));
		graphics.lineTo(xxx,i);
		if( xxx <0 ) {
		    break;
		}
	    }
	    graphics.stroke();
	}
	break;
    case 5:
	for( k=-75 ; k < 90 ; k+=15 ) {
	    C=Math.tan(k*Math.PI/180);
	    graphics.beginPath();
	    if( a == 1.0 ) {
			graphics.moveTo(0,200+(-C*200));
			for(i=1 ; i< 400 ; i++ ) {
			    graphics.lineTo(i,200+(C*(i-200)));
			}
	    } else {
			graphics.moveTo(0,200+(100.0*C*Math.pow(2.0,a)));
			for(i=1; i<200 ; i++ ) {
		    	graphics.lineTo(i,200+(100.0*C*Math.pow(0.01*(200-i),a)));
			}
			graphics.lineTo(200,0);
			for(i=201; i<400 ; i++ ) {
		    	graphics.lineTo(i,200+(100.0*C*Math.pow(0.01*(i-200),a)));
			}
	    }
	    graphics.stroke();
	}
	break;
    case 6:
	for( k=20 ; k<=550 ; k += 40){
	    px=200+k;
	    py=200;
	    for( i=1 ; i<360 ; i+=3 ) {
		ppx=px;
		ppy=py;
		px=200+k*Math.cos(Math.PI/180.0*i);
		py=200+k*a*Math.sin(Math.PI/180.0*i);
		graphics.beginPath();
		graphics.moveTo(ppx,ppy);
		graphics.lineTo(px,py);
		graphics.stroke();
	    }
	}
	break;
    case 7:
	for( k=40 ; k<200 ; k+=40) {
	    graphics.beginPath();
	    graphics.moveTo(0,200-(100.0*Math.sqrt(4.0*a+0.0001*k*k)));
	    for( i=1; i<400 ; i++ ) {
		graphics.lineTo(i,200-(100.0*Math.sqrt(a*(0.0001*(i-200)*(i-200))+0.0001*k*k)));
	    }
	    graphics.stroke();
	}
	for( k=40 ; k<200 ; k+=40) {
	    graphics.beginPath();
	    graphics.moveTo(0,200+(100.0*Math.sqrt(4.0*a+0.0001*k*k)));
	    for( i=1; i<400 ; i++ ) {
		graphics.lineTo(i,200+(100.0*Math.sqrt(a*(0.0001*(i-200)*(i-200))+0.0001*k*k)));
	    }
	    graphics.stroke();
	}
	for( k=0 ; k<200 ; k+=40) {
	    graphics.beginPath();
	    graphics.moveTo(200-(100.0*Math.sqrt(4.0/a+0.0001*k*k)),0);
	    for( i=1; i<400 ; i++ ) {
		graphics.lineTo(200-(100.0*Math.sqrt((0.0001*(i-200)*(i-200))/a+0.0001*k*k)),i);
	    }
	    graphics.stroke();
	}
	for( k=0 ; k<200 ; k+=40) {
	    graphics.beginPath();
	    graphics.moveTo(200+(100.0*Math.sqrt(4.0/a+0.0001*k*k)),0);
	    for( i=1; i<400 ; i++ ) {
		graphics.lineTo(200+(100.0*Math.sqrt((0.0001*(i-200)*(i-200))/a+0.0001*k*k)),i);
	    }
	    graphics.stroke();
	}
	break;
    case 8:
	for( k=0 ; k<400 ; k+=20) {
	    graphics.beginPath();
	    graphics.moveTo(0,200-((k-200)*Math.exp(2*a)));
	    for( i=1; i<400 ; i++ ) {
		graphics.lineTo(i,200-((k-200)*Math.exp(0.5*a*(0.0001*(i-200)*(i-200)))));
	    }
	    graphics.stroke();
	}
	break;
    case 9:
	for( k=-200 ; k<600 ; k+=40) {
	    graphics.beginPath();
	    graphics.moveTo(0,200-((k-200)*Math.exp(-2*a)));
	    for( i=1; i<400 ; i++ ) {
		graphics.lineTo(i,200-((k-200)*Math.exp(-0.5*a*(0.0001*(i-200)*(i-200)))));
	    }
	    graphics.stroke();
	} 
	break;
    case 10:
	for( k=-40 ; k <= 440 ; k += 40 ) {
	    graphics.beginPath();
	    graphics.moveTo(0,k+(50*Math.cos(a*4.0)/a));
	    for( i=1 ; i<400 ; i++ ) {
		graphics.lineTo(i,k+(50*Math.cos(2.0*a*(2.0-0.01*i))/a));
	    }
	    graphics.stroke();
	} 
	break;
    case 11:
		for( k=-400; k<=400 ; k+=80 ) {
			graphics.beginPath();
			graphics.moveTo(0,200-(100*(a+0.01*k*Math.exp(-2.0))));
			for( i=1 ; i<400 ; i++ ) {
				graphics.lineTo(i,200-(100*(-a*(0.01*(i-200)+1.0)+0.01*k*Math.exp(0.01*(i-200)))));
			}
			graphics.stroke();
		}
		break;
	case 12:
		for( k=-1.95; k<=2 ; k+=0.1 ) {
			var C=(1-k)/k;
			graphics.beginPath();
			graphics.moveTo(0,200-100/(1+C*Math.exp(a*2.0)));
			for( i=1 ; i<400 ; i++ ) {
				var yyy=200-100/(1+C*Math.exp(-a*0.01*(i-200)));
				if( yyy< -200 ) break;
				graphics.lineTo(i,yyy);
			}
			graphics.stroke();
		}
		break;
    }
    return ;
    }

function changeMode(selector_str) {
	var s=$(selector_str).val();
	if( s == "diffonly" ) { 
		mode =0;
	} else if( s=="solanime" ) {
		mode=1;
	} else if( s=="solution" ) {
		mode=2;
	} else {
		mode=-1;
	}
	reset();
}
	
function changefunc(selector_str) {
	val=Number($(selector_str).val());
	reset();
}


var val=0;

function difff(xx,yy) {
    switch(val) {
    case 0:
	return a;
	break;
    case 1:
	return a*xx;
	break;
    case 2:
	return a*yy;
	break;
    case 3:
	return a/xx;
	break;
    case 4:
	return a/(xx*xx);
	break;
    case 5:
	return a*yy/xx;
	break;
    case 6:
	return -a*xx/yy;
	break;
    case 7:
	return a*xx/yy;
	break;
    case 8:
	return a*xx*yy;
	break;
    case 9:
	return -a*xx*yy;
	break;
    case 10:
	return Math.sin(2.0*a*xx);
    case 11:
	return a*xx+yy;
	case 12:
		return a*yy*(1-yy);
    }	    
    return 0;
}
