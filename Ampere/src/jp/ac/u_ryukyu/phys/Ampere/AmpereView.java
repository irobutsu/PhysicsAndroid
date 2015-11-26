package jp.ac.u_ryukyu.phys.Ampere;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.FloatMath;
import jp.ac.u_ryukyu.phys.lib.DraggManageView;
import jp.ac.u_ryukyu.phys.lib.DraggManager;
import jp.ac.u_ryukyu.phys.lib.MovingObject;
import jp.ac.u_ryukyu.phys.lib.Vec2;

import java.util.ArrayList;


public class AmpereView extends DraggManageView {


	@Override
	protected void setSituation() {
		// TODO Auto-generated method stub

	}

	private int V[][];
	
	
	private Charge q1;
	private TestCharge tc;
	private Current i1;
	





	private ArrayList<Float> ppx;
	private ArrayList<Float> ppy;
	private ArrayList<Float> stopx;
	private ArrayList<Float> stopy;
	private boolean nowAnime;
	private int nowp;

	public AmpereView(Context context) {
		super(context);
		
		wallMg=0f;
		ppx=new ArrayList<Float>();
		ppy=new ArrayList<Float>();
		nowp=0;
		nowAnime=true;


		tc=new TestCharge(1,20,Color.argb(100,100,100,100),new Vec2(100,100),this);

	

		q1=new Charge(0,30,Color.RED,new Vec2(60,100));
		i1=new Current(1,30,Color.rgb(0,0,100),new Vec2(100,60));

		q1.setDraggManager(0);
		i1.setDraggManager(0);
		tc.setDraggManager(0);
		addObj(tc); 
		addObj(q1);
		addObj(i1);
	}
	float hankei;
	float minihankei;
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		hankei=h/25;
		if( hankei < 15) {
			hankei=15;
		}
		minihankei=hankei/2;
		if( minihankei < 8 ) {
			minihankei=8;
		}
		q1.setR(hankei);
		i1.setR(hankei);
		tc.setR(minihankei);
		centeringI();
		sikakuEvt();
		V=new int[(int)wx/4+1][];
		int i;

		for(i=0; i<=(int)wx/4 ; i++ ) {
			V[i]=new int[(int)hy/2+1];
		}
	}

	public void sankakuEvt(){
		float cx=wx/4;
		float cy=hy/2;
		float ww=hy/4;
		ppx.clear();
		ppy.clear();
		ppx.add(cx);
		ppy.add(cy-ww);
		ppx.add(cx-ww);
		ppy.add(cy+ww);
		ppx.add(cx+ww);
		ppy.add(cy+ww);
		tousokuundouka();
		animeGO();
	}

	public void sikakuEvt(){
		

		ppx.clear();
		ppy.clear();

		float cx=wx/4;
		float cy=hy/2;
		float ww=hy/4;
		
		
		
		ppx.add(cx+ww);
		ppy.add(cy-ww);
		ppx.add(cx-ww);
		ppy.add(cy-ww);
		ppx.add(cx-ww);
		ppy.add(cy+ww);
		ppx.add(cx+ww);
		ppy.add(cy+ww);

		tousokuundouka();
		animeGO();
	}

	public void maruEvt(){
		int i;
		float cx=wx/4;
		float cy=hy/2;
		float ww=hy/4;
		ppx.clear();
		ppy.clear();
		for(i=0; i<200 ; i++ ) {
			ppx.add((float)(cx+ww*Math.sin(0.01*Math.PI*i)));
			ppy.add((float)(cy+ww*Math.cos(0.01*Math.PI*i)));
		}
		tousokuundouka();
		animeGO();
	}

	public void ougiEvt(){
		int i;
		int cx=(int)wx/4;
		int cy=(int)hy/2;
		int ww=(int)hy/4;
		ppx.clear();
		ppy.clear();

		for(i=0; i<51 ; i++ ) {
			ppx.add((float)(cx+ww*Math.sin(0.01*Math.PI*(i))));
			ppy.add((float)(cy+ww*Math.cos(0.01*Math.PI*(i))));
		}

		for(i=0; i<51 ; i++ ) {
			ppx.add((float)(cx+ww*0.5*Math.sin(0.01*Math.PI*(50-i))));
			ppy.add((float)(cy+ww*0.5*Math.cos(0.01*Math.PI*(50-i))));
		}

		tousokuundouka();
		animeGO();
	}


	public void makePath() {
		
		tousokuundouka();
		animeGO();
	}

	public void tousokuundouka(){
		
		// 行程の中に、速度0があったらそれを取り除く。

		int i;

		i=0;
		while(i< ppx.size()-1 ) {
			if( ppx.get(i+1)-ppx.get(i)==0 && ppy.get(i+1)-ppy.get(i)==0 ) {
				ppx.remove(i+1);
				ppy.remove(i+1);
			} else {
				i++;
			}
		}
		if( ppx.get(0)-ppx.get(ppx.size()-1)==0 && ppy.get(0)-ppy.get(ppy.size()-1)==0 ) {
			ppx.remove(0);
			ppy.remove(0);
		}


		// まず全行程の距離を計算
		float len=0;

		ArrayList<Float> lens = new ArrayList<Float>();


		float nx;
		float ny;

		for( i=0 ; i < ppx.size()-1 ; i++ ) {
			nx=ppx.get(i)-ppx.get(i+1);
			ny=ppy.get(i)-ppy.get(i+1);
			len += Math.sqrt(nx*nx+ny*ny);
			lens.add(len);
		}
		nx=ppx.get(0)-ppx.get(ppx.size()-1);
		ny=ppy.get(0)-ppy.get(ppx.size()-1);

		len+=Math.sqrt(nx*nx+ny*ny);
		lens.add(len);

		// lens.get(0)が(ppx,ppy)[0]から(ppx,ppy)[1]までの長さ。
		// lens[l-1]が(ppx,ppy)[l-1]から(ppx,ppy)[0]までの長さ

		//float steplen=len*0.0025f;
		// これが1stepの長さ。len/400
		int w=(int)(wx/2);
		float steplen=len/w;
		
		stopx=new ArrayList<Float>();
		stopy=new ArrayList<Float>();

		stopx.add(ppx.get(0));
		stopy.add(ppy.get(0));

		i=0;
		
		int k;
		for( k=1;  k < w ; k++) {
			while( lens.get(i) < steplen*k ) {
				i++;
			}
			float ratio;
			if( i== 0 ) {
				ratio=(steplen*k)/(lens.get(0));
			} else {
				ratio=(steplen*k- lens.get(i - 1))/(lens.get(i)- lens.get(i - 1));
			}
			if( i == ppx.size()-1 ) {
				stopx.add(((ppx.get(i))+((ppx.get(0)-ppx.get(i)))*ratio));
				stopy.add(((ppy.get(i))+((ppy.get(0)-ppy.get(i)))*ratio));
			} else {
				stopx.add(((ppx.get(i))+((ppx.get(i+1)-ppx.get(i)))*ratio));
				stopy.add(((ppy.get(i))+((ppy.get(i+1)-ppy.get(i)))*ratio));
			}
		}
	}



	private void drawYajirusi(Canvas graphics,int c,float XX,float YY,float angle,int len,int w){ 

		int x1= (int)((-w*Math.sin(angle) / 2 ));
		int y1= (int)((-w*Math.cos(angle) / 2 ));
		int x2= (int)((len*Math.cos(angle) / 2 ));
		int y2= (int)((-len*Math.sin(angle) / 2 ));
		int xl= (int)((len*Math.cos(angle)));
		int yl= (int)((-len*Math.sin(angle)));
		Paint p=new Paint();
		p.setStrokeWidth(2);
		p.setColor(c);

		graphics.drawLine(XX, YY, XX+xl, YY+yl, p);

		p.setStrokeWidth(1);

		Path pt=new Path();

		pt.moveTo(XX+xl,YY+yl);
		pt.lineTo(XX + x2 + x1,YY + y2 + y1);
		pt.lineTo(XX + x2-x1,YY + y2-y1);
		pt.lineTo(XX+xl,YY+yl);
		pt.close();
		p.setStyle(Paint.Style.FILL);
		graphics.drawPath(pt, p);

	}

	boolean rewriteFlg=true;
	@Override
	protected void drawBackGround(Canvas graphics){
		// if( !nowAnime ) return;
		int w=(int)wx/2;
		int h=(int)hy;
		int i;
		int XX, YY;

		int x1=(int)q1.X();
		int x3=(int)i1.X();
		int y1=(int)q1.Y();
		int y3=(int)i1.Y();
		int Q1= q1.Q();
		int l1= i1.Q();

		if( rewriteFlg && !q1.isDragged() && !i1.isDragged() ) {
			int R;
			int I;


			int DX1[]=new int[w/2+1];
			int SQX1[]=new int[w/2+1];
			int SQX3[]=new int[w/2+1];


			int DY1[]=new int[h/2+1];
			int SQY1[]=new int[h/2+1];
			int SQY3[]=new int[h/2+1];


			for( XX = 0 ; XX <= w/2  ; XX++ ) {
				DX1[XX]=2*XX-x1;
				SQX1[XX]=(2*XX-x1)*(2*XX-x1);
				SQX3[XX]=(2*XX-x3)*(2*XX-x3);
			}

			for( YY = 0 ; YY <= h/2  ; YY++ ) {
				DY1[YY]=2*YY-y1;
				SQY1[YY]=(2*YY-y1)*(2*YY-y1);
				SQY3[YY]=(2*YY-y3)*(2*YY-y3);
			}


			for( XX=0; XX <= w/2  ; XX++ ) {
				for( YY = 0 ; YY <= h/2  ; YY++ ) {
					switch( Q1 ) {
					case 0:
						R=1;
						I=0;
						break;
					case 1:
						R= (DX1[XX]);
						I= (DY1[YY]);
						break;
					case 2:
						R= (SQX1[XX]-SQY1[YY]);
						I= (DX1[XX]*DY1[YY]*2);
						break;
					case 3:
						R= ((DX1[XX])*SQX1[XX]-3*(DX1[XX])*SQY1[YY]);
						I= (3*SQX1[XX]*(DY1[YY])-SQY1[YY]*(DY1[YY]));
						break;
					case -1:
						R= (DX1[XX]);
						I= (y1-2*YY);
						break;
					case -2:
						R= (SQX1[XX]-SQY1[YY]);
						I= (-2*(DX1[XX])*DY1[YY]);
						break;
					case -3:
						R= ((DX1[XX])*SQX1[XX]-3*(DX1[XX])*SQY1[YY]);
						I= (-3*SQX1[XX]*DY1[YY]+SQY1[YY]*(DY1[YY]));
						break;
					default:
						R=0; I=0;
						break;
					}

					float RRR=R;
					float III=I;

					float NR;
					float Acos;
					float Asin;
					float A=1.0f;

					if( l1 !=0 ) {
						float A1=(SQX3[XX] + SQY3[YY]);
						if( A1 != 0 ) {
							switch( l1 ) {
							case 1:
								A=A1;
								break;
							case 2:
								A=A1*A1;
								break;
							case 3:
								A=A1*A1*A1;
								break;
							case -1:
								A=1.0f/A1;
								break;
							case -2:
								A=1.0f/(A1*A1);
								break;
							case -3:
								A=1.0f/(A1*A1*A1);
								break;
							}
						}
						A = (float)(0.5*Math.log(A));
						Acos=FloatMath.cos(A);
						Asin=FloatMath.sin(A);

						NR=RRR*Acos-III*Asin;
						III=RRR*Asin+III*Acos;
						RRR=NR;
					} else {
					}
					float v= (float)Math.atan2(RRR,III);

					v *= 5.0 ;
					v /= Math.PI ;
					V[XX][YY] = (int)(Math.floor(v)) ;
				}
			}
			rewriteFlg=false;
		}
		Paint p=new Paint();
		p.setColor(Color.CYAN);
		p.setStyle(Paint.Style.FILL);
		graphics.drawRect(0 , 0 , w , h,p) ;
		p.setColor(Color.WHITE);
		graphics.drawRect(w,0,wx,h,p);
		p.setColor(Color.MAGENTA);
		p.setStyle(Paint.Style.STROKE);
		p.setStrokeWidth(4);
		Path pt=new Path();
		if( ppx.size()>0) {		
			pt.moveTo(ppx.get(0),ppy.get(0));
			for( i=1 ; i<ppx.size() ; i++ ) {
				pt.lineTo(ppx.get(i),ppy.get(i));
			}
			pt.close();
			graphics.drawPath(pt,p);
		}
		p.setColor(Color.BLUE);
		p.setStyle(Paint.Style.FILL);
		for( XX = 0 ; XX < w/2 ; XX++ ) {
			for( YY = 0 ; YY < h/2 ; YY++ ) {
				if( !q1.isNear(2*XX,2*YY) && !i1.isNear(2*XX,2*YY)) {
					if( V[XX ][YY] != V[XX][YY + 1] ) {

						graphics.drawRect(2*XX , 2*YY , 2*XX+2 , 2*YY+2,p) ;

					} else if( V[XX][YY] != V[XX + 1][YY] ) {

						graphics.drawRect(2*XX , 2*YY , 2*XX+2 , 2*YY+2,p) ;

					} else if( V[XX][YY] != V[XX + 1][YY+1] ) {

						graphics.drawRect(2*XX , 2*YY , 2*XX+2 , 2*YY+2,p) ;

					}
				}
			}
		}
		YY=150;
		XX=250;

		float r1= 1.0f / ((2*XX-q1.X())*(2*XX-q1.X()) + (2*YY-q1.Y())*(2*YY-q1.Y()) );
		float xxx= q1.Q()*1000*(2*XX-q1.X())*r1 ;
		float yyy= q1.Q()*1000*(2*YY-q1.Y())*r1 ;
		float rsq= 1.0f / ((XX-i1.X())*(XX-i1.X()) + (2*YY-i1.Y())*(2*YY-i1.Y()) );
		float yyy2= -i1.Q()*1000*(2*XX-i1.X())*rsq ;
		float xxx2= i1.Q()*1000*(2*YY-i1.Y())*rsq ;
		graphics.drawLine(w,h/2,2*w,h/2,p);

		p.setColor(Color.BLACK);
		p.setStyle(Paint.Style.STROKE);
		float tatebai=50/hy;
		float vx,vy,vs;
		if( ppx.size()>1) {

			r1 = 1.0f / ((ppx.get(0)-q1.X())*(ppx.get(0)-q1.X()) + (ppy.get(0)-q1.Y())*(ppy.get(0)-q1.Y()) );
			xxx = q1.Q()*1000*(ppx.get(0)-q1.X())*r1 ;
			yyy = q1.Q()*1000*(ppy.get(0)-q1.Y())*r1 ;
			rsq = 1.0f / ((ppx.get(0)-i1.X())*(ppx.get(0)-i1.X()) + (ppy.get(0)-i1.Y())*(ppy.get(0)-i1.Y()) );
			yyy2 = -i1.Q()*1000*(ppx.get(0)-i1.X())*rsq ;
			xxx2 = i1.Q()*1000*(ppy.get(0)-i1.Y())*rsq ;
			vx=ppx.get(1)-ppx.get(0);
			vy=ppy.get(1)-ppy.get(0);
			vs=tatebai*FloatMath.sqrt( vx*vx +vy*vy);

			pt.reset();
			pt.moveTo(w,h/2-(int)(((xxx+xxx2)*vx+(yyy+yyy2)*vy)/vs));

			for( i=1 ; i<w ; i++ ) {
				r1 = 1.0f / ((stopx.get(i)-q1.X())*(stopx.get(i)-q1.X()) + (stopy.get(i)-q1.Y())*(stopy.get(i)-q1.Y()) );
				xxx = q1.Q()*1000*(stopx.get(i)-q1.X())*r1 ;
				yyy = q1.Q()*1000*(stopy.get(i)-q1.Y())*r1 ;
				rsq = 1.0f / ((stopx.get(i)-i1.X())*(stopx.get(i)-i1.X()) + (stopy.get(i)-i1.Y())*(stopy.get(i)-i1.Y()) );
				yyy2 = -i1.Q()*1000*(stopx.get(i)-i1.X())*rsq ;
				xxx2 = i1.Q()*1000*(stopy.get(i)-i1.Y())*rsq ;
				int nextp=i+1;
				if(nextp == w ) {
					nextp=0; 
				}
				vx=stopx.get(nextp)-stopx.get(i);
				vy=stopy.get(nextp)-stopy.get(i);
				vs=tatebai*FloatMath.sqrt( vx*vx +vy*vy);

				pt.lineTo(w+i,
						h/2-(int)(((xxx+xxx2)*vx+(yyy+yyy2)*vy)/vs));
			}
			graphics.drawPath(pt, p);
		}
		if( drawPt != null ) {
			p.setColor(Color.MAGENTA);
			graphics.drawPath(drawPt,p);
		}
		
		int nextp;
		if( nowAnime ) {
			tc.setX((stopx.get(nowp)));
			tc.setY((stopy.get(nowp)));
			r1 = 1.0f / ((stopx.get(nowp)-q1.X())*(stopx.get(nowp)-q1.X()) + (stopy.get(nowp)-q1.Y())*(stopy.get(nowp)-q1.Y()) );
			xxx = q1.Q()*1000*(stopx.get(nowp)-q1.X())*r1 ;
			yyy = q1.Q()*1000*(stopy.get(nowp)-q1.Y())*r1 ;
			rsq = 1.0f / ((stopx.get(nowp)-i1.X())*(stopx.get(nowp)-i1.X()) + (stopy.get(nowp)-i1.Y())*(stopy.get(nowp)-i1.Y()) );
			yyy2 = -i1.Q()*1000*(stopx.get(nowp)-i1.X())*rsq ;
			xxx2 = i1.Q()*1000*(stopy.get(nowp)-i1.Y())*rsq ;
			drawYajirusi(graphics,Color.BLUE,stopx.get(nowp),stopy.get(nowp),(float)Math.atan2(-yyy-yyy2, xxx+xxx2),(int)(Math.sqrt((xxx + xxx2 )*(xxx + xxx2 ) 
												+ (yyy + yyy2 )*(yyy + yyy2 ))/(tatebai)),16);
			
			nextp=nowp+w/100;
			if(nextp >= stopx.size() ) {
				nextp=0; 
			}
			vx=stopx.get(nextp)-stopx.get(nowp);
			vy=stopy.get(nextp)-stopy.get(nowp);
			float v=tatebai*FloatMath.sqrt( vx*vx +vy*vy);
			float vtheta=(float)Math.atan2(-vy,vx);
			float vp=((xxx+xxx2)*vx+(yyy+yyy2)*vy)/v;

			//	yjv.set(stopx.get(nowp),stopy.get(nowp),vtheta,(int)(v*10),8);
			// drawYajirusi(graphics,Color.RED,stopx.get(nowp),stopy.get(nowp),vtheta,(int)(v*40),16);
			drawYajirusi(graphics,Color.rgb(0, 0, 128),stopx.get(nowp),stopy.get(nowp),vtheta,(int)(vp),16);
			//	yj2.set(stopx.get(nowp),stopy.get(nowp),vtheta,(int)(vp),8);
			
			drawYajirusi(graphics,Color.rgb(0, 0, 128), w+(w*nowp)/(stopx.size()),h/2,(float)(0.5*Math.PI),(int)vp,16);
			
			p.setColor(Color.argb(100,255, 255, 255));
			graphics.drawLine(stopx.get(nowp), stopy.get(nowp), i1.X(), i1.Y(), p);
			int pastp=nowp;
			int c;
			for( c=240 ; c>0; c-=15) {
				pastp--;
				if( pastp<0) { pastp=stopx.size()-1; }
				p.setColor(Color.argb(100,c,c,c));
				graphics.drawLine(stopx.get(pastp), stopy.get(pastp), i1.X(), i1.Y(), p);
			}
			
			
			
			nowp=nextp;
			drawPt=null;
		} else {
			ppx.add(tc.X());
			ppy.add(tc.Y());
			if( drawPt != null ) {
				drawPt.reset();
			}
			drawPt=new Path();

			drawPt.moveTo(ppx.get(0),ppy.get(0));
			for(i=1 ; i<ppx.size() ; i++ ) {
				drawPt.lineTo(ppx.get(i),ppy.get(i));
			}
		} 
		
	}
	@Override
	protected void calcNext() {

	
	}
	Path drawPt;

	@Override
	public boolean ClickOthers(Vec2 m) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void writeSubView(Canvas canvas) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void prepareForNext() {
		
		if( q1.isDragged() || i1.isDragged() ) {
			rewriteFlg=true;
		}
		super.prepareForNext();
	}
	@Override
	protected void calcNextEach(MovingObject m) {
		
	}

	public void setQ(int charge) {
		q1.setQ(charge);
		rewriteFlg=true;
	}

	public void setI(int current) {
		i1.setQ(current);
		rewriteFlg=true;
	}
	public void centeringI() {
		i1.setPos(new Vec2(wx/4,hy/2));	
		rewriteFlg=true;
	}

	public void stopOnce() {
		animeOFF();
		ppx.clear();
		ppy.clear();
		nowp=0;
	}
	public void animeGO() {
		DraggManager.dragObjList.remove(tc);
		nowAnime=true;
	}
	public void animeOFF() {
		DraggManager.dragObjList.add(tc);
		nowAnime=false;
	}
	

}


