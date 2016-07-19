var N;

function  init() {
    N=64;
    
    tm_start();
}

function writeCanvas(gc2) {
    N=Num.get();
    int w=canvas2.getWidth();
    
    int Time=(int)t;
    int time=Time%(w-150);
    int n=(Time*N/(w-150))%N;
    
    var v=V.get();
    var a=((double)A.get())*0.01f;
    
    v=v/200;
    a=a/200;
    
    var h=canvas2.Height;
    var i;
    
    gc2.setColor(Color.WHITE);
    gc2.fillRect(0,0,w,h);

    gc2.setColor(Color.BLACK);
    gc2.drawLine(150,0,150,h);

    gc2.setColor(Color.GRAY);
    gc2.drawLine(time+150,0,time+150,h);

    gc2.setColor(Color.RED);

	    
    var y=0;
    var ny;
    for(i=0; i<w-150 ; i++ ) {
	var x=i;
	ny = v*x + a*x*x/2;
	gc2.drawLine(150+i,h-30-(int)y,150+i+1,h-30-(int)ny);
	y=ny;
    }

    y=0;
    var yy=0;
    var xx=0.0;
    var  xx2=1.0;
    var  vv=0.0;
    gc2.setColor(Color.BLACK);
    for( i=0 ; i<N ; i++ ) {
	var x= (i*(w-150))/N;
	var x2=(i+1)*(w-150)/N;
	ny = y + ((v+a*x)*(x2-x));
	gc2.drawLine((int)x+150,h-30-(int)y,(int)x2+150,h-30-(int)ny);
	if( i ==n ) {
	    yy=y;
	    xx=x;
	    xx2=x2;
	    vv=v+a*x;
	}
	y=ny;
    }

    int x0=35;
    int y0=h-130;

    gc2.setColor(Color.BLACK);
    gc2.fillRect(x0-5,y0,5,105);
    gc2.fillRect(x0+100,y0,5,105);
    gc2.fillRect(x0-5,y0+100,110,5);

    gc2.setColor(Color.GRAY);
    gc2.fillRect(x0-15,y0-60,80,10);
    gc2.fillRect(x0+40,y0-50,20,5);
    
    gc2.setColor(Color.CYAN);
    var www=(8.0*(v+a*(n*w)/N));
    var ww=0;
    if( www >0.0 && www < 1.0 ) {
	gc2.fillRect(x0+50,y0-45,1,145);
    } else {
	ww=(int)www;
	gc2.fillRect(x0+50-ww,y0-45,ww*2,145);
    }
    
    var ii = (int)(time-xx);
    var yyy = (int)(1.0*( yy + ((double)ii)*vv));
    
    if( yyy > 101 ) {
	gc2.fillRect(x0,y0-1,100,101);
	gc2.fillRect(x0-5-ww,y0-2,110+2*ww,2);
	gc2.fillRect(x0-5-ww,y0-2,ww,h-y0+2);
	gc2.fillRect(x0+105,y0-2,ww,h-y0+2);
    } else {
	gc2.fillRect(x0,y0+100-yyy,100,yyy);
    }
    gc2.setColor(Color.GRAY);
    gc2.drawLine(140,y0+100-yyy,time+150,y0+100-yyy);
}


function writeCanvas(Graphics gc){
    N=Num.get();
    
    var w=canvas.Width;
    
    var Time=Math.floor(t);
    var time=Time%(w-150);
    var n=(Time*N/(w-150))%N;

    var ww=w/N;
    var h=canvas.getHeight();

    gc.setColor(Color.YELLOW);
    gc.fillRect(0,0,w,h);
    
    gc.setColor(Color.BLACK);
    gc.drawLine(150,0,150,h);
}




	var hh;
	var i;
	var z;
	var v=V.get();
	var a=((double)A.get())*0.01f;


	gc.setColor(Color.BLUE);

	gc.drawLine(150,h-(int)v,w,h-(int)(v+a*(w-150)));
	    

	gc.setColor(Color.CYAN);
	for( i=0 ; i<n ; i++ ) {
	    hh=(int)(v+a*(i*(w-150)/N));
	    gc.fillRect(150+(w-150)*i/N,h-hh,(w-150)*(i+1)/N-(w-150)*i/N,hh);
	}

	gc.setColor(Color.BLACK);
	for( i=0 ; i<N ; i++ ) {
	    hh=(int)(v+a*(i*(w-150)/N));
	    gc.drawRect(150+(w-150)*i/N,h-hh,(w-150)*(i+1)/N-(w-150)*i/N,hh);
	}


	gc.setColor(Color.MAGENTA);	

	hh=(int)(v+a*(n*(w-150)/N));

	gc.fillRect(150+(w-150)*n/N,h-hh,time-(w-150)*n/N,hh);

	canvas2.repaint();
    }


