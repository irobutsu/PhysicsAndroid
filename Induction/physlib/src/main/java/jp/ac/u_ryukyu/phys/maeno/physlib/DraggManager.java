package jp.ac.u_ryukyu.phys.maeno.physlib;

import android.view.MotionEvent;

import java.util.ArrayList;

public class DraggManager {
	protected int draggingPointerID=-1;
	// このDraggManagerがくっついている物体はどのポインタにドラッグされているか。
	protected Vec2 shiftV;
	// 物体の中点から、ドラッグされている点までのベクトル
	protected Vec2 d[];
	
	protected int dcountMax=10;
	// マウスの軌跡を何個まで保存しておくか。初期値はアニメーション10枚分、ということ。
	// これを0にしておくと軌跡を貯めない。
	
	
//	public int getDcountMax() {
//		return dcountMax;
//	}
//
//	public void setDcountMax(int dcMax) {
//		dcountMax = dcMax;
//	}


	protected boolean tamattaFlg;
	// マウス軌跡がdcountMax個たまると真になる。
	protected int dcount;
	protected int dcNow;
	// ↑この二つは、ためているマウス軌跡のためのカウンタ。
	protected Vec2 tmpPos=Vec2.zero; // ドラッグ途中の「一時的な座標」
	protected void setTmpPos(Vec2 p) {tmpPos=p;}
	
	static public ArrayList<MovingObject> dragObjList;
	static int pointerCount = 0;
	// 今なんこのポインタがあるか。マルチタッチなので、ポインタが一個とは限らない。
	
	public static int getPointerCount() {
		return pointerCount;
	}

	public DraggManager(MovingObject which) {
		this(which,10);
	}

	public DraggManager(MovingObject which,int dcM) {
		dcountMax=dcM;
		if( dcM != 0 ) {
			d=new Vec2[dcountMax];
		}
		if( dragObjList==null) {
			dragObjList = new ArrayList<>();
		}
		dragObjList.add(which);
	}
				
	static public void remove(MovingObject o) {
		if( dragObjList==null) {
			return;
		}
		int j;
		for(j=0; j<dragObjList.size(); j++ ) {
			if(dragObjList.get(j) == o ) {
				dragObjList.remove(j);
			}
		}
	}
	
	public static boolean manageDragg(MotionEvent event,DraggManageView view,float x0,float y0,float x1,float y1){
		pointerCount = event.getPointerCount();
		boolean handled=false;
		int j;


		int index=(event.getAction()&MotionEvent.ACTION_POINTER_ID_MASK)>>
        	MotionEvent.ACTION_POINTER_ID_SHIFT;
		int ptID=event.getPointerId(index);
		Vec2 mouseP=new Vec2(event.getX(index),event.getY(index));

		if( dragObjList == null) {
			// ドラッグすべきものは何もないので即帰る。
			view.ClickOthers(mouseP);
			return false;
		}

		switch ( event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			for(j=0; j<dragObjList.size(); j++ ) {
				if( dragObjList.get(j).isCanDrag()
						&& dragObjList.get(j).isCatched(mouseP) ) {
					int k;
					boolean draggOther=false;
					for(k=0; k<dragObjList.size() ; k++) {
						if( j!=k && dragObjList.get(j).isDraggedBy(ptID) ) {
							// object kは既にドラッグされている、というフラグを立てる。
							draggOther=true;
						}
    				}
					if( !draggOther ) {
						// ovject jは、まだドラッグされていない。
						// object jが、ポインタiによるドラッグが開始された。
						Vec2 p=dragObjList.get(j).Pos();
						dragObjList.get(j).startDragg(ptID,
								mouseP.Diff(p));
						// 指がつかんだ場所と、物体の重心との差を知らせておく。
						// shiftV=mouseP-Pos()
    					handled=true;
    					dragObjList.get(j).setTmpPos(p);
    					// 現在の物体位置を入れておく。
    				}
    			}
    		}
			
			if( !handled ) {
				// どの物体も押されてない 
				view.ClickOthers(mouseP);
    		}
			break;
    	case MotionEvent.ACTION_MOVE:
    		// ACTION_MOVEはポインタそれぞれについて一個ずつではなく、一挙に来るみたい。
    		// というわけで、ACTION_MOVEが来た時は全てのドラッグ可能な物体について処理を実行。
    		for(j=0;j<dragObjList.size(); j++) {
    			MovingObject obj=dragObjList.get(j);
    			int ID=obj.whoDragg();
    			int idx=event.findPointerIndex(ID);
    			// idxが負の時は、今回来たデータの中にこの物体はない、ということ。
    			if(idx>=0) {
    				mouseP=new Vec2(event.getX(idx),event.getY(idx));
    				Vec2 pt=mouseP.Diff(obj.shift()); // shift分を引いて、「物体の中心位置」に調整する。
    				obj.setTmpPos(obj.PositionInRect(x0,y0,x1,y1,pt));
    				// 動かせない位置まで行っていたらちゃんと戻した上で、その位置を覚えておく。
    				handled=true;
    			}
    		}
    		break;
    	case MotionEvent.ACTION_UP:
    	case MotionEvent.ACTION_POINTER_UP:
    		//タッチが離されたときの動作
    		for(j=0;j<dragObjList.size(); j++) {
    			if( dragObjList.get(j).isDraggedBy(ptID) ) {
    				dragObjList.get(j).releaseDragg();
    				handled=true;
    			}
    		}
    		break;
    	}
		return handled;
	}

	public boolean tamatteru() { return tamattaFlg; }
	public boolean startDragg(int i) {
		return startDragg(i,new Vec2(0f,0f));
	}
	
	// ptIDのIDのポインタによるドラッグ開始。sは、「ポイントされた位置」-「自分の原点」のベクトル
	public boolean startDragg(int ptID,Vec2 s) {
		if( dcountMax != 0 ) {
			d=new Vec2[dcountMax];
		}
		// d[]は、マウスの軌跡をストアしておく配列。
		draggingPointerID=ptID;
		tamattaFlg=false;
		dcount=0;
		shiftV=s;
		return true;
	}

	public static boolean someIsDragged() {
		if( dragObjList == null) {
			// ドラッグすべきものは何もないので即帰る。
			return false;
		}
		int j;
		for(j=0; j<dragObjList.size(); j++ ) {
			if( dragObjList.get(j).isDragged() ) {
				return true;
			}
		}
		return false;
	}
	public boolean isDragged() { return draggingPointerID != -1;}
	public boolean isDraggedBy(int ptID) { return draggingPointerID==ptID;}
	public int whoDragg(){ return draggingPointerID;}
	public boolean releaseDragg() {
		draggingPointerID=-1;
		return true;
	}
	public void pushPath() {
		pushPath(tmpPos);
	}
	public void pushPath(Vec2 p) {
		if( dcountMax == 0 ) {
			return;
		}
		d[dcount]=p;
		dcNow=dcount;
		dcount++;
		if( dcount >= dcountMax) {
			// d[]が一杯になるまでたまった。
			dcount=0;
			// のでカウンタを元に戻す。
			tamattaFlg=true;
		}
	}
	public Vec2 shift(){ 
		if( shiftV==null ) {
			shiftV=new Vec2(0f,0f);
		}
		return shiftV;
	}
	
	public Vec2 calcMidA(float st) {
		Vec2 vv;
		// dcountは「一番古いデータ」を示している。
		// dcount-1が「一番新しいデータ」である。
		int dcMid;
		dcMid=dcount-dcountMax/2;
		if(dcMid<0){
			dcMid +=dcountMax;
		}
		int dcMidM1=dcMid-1;
		if( dcMidM1 <0 ) {
			dcMidM1 +=dcountMax;		
		}	
		 
		int dcMidM2=dcMid-2;
		if( dcMidM2 <0 ) {
			dcMidM2 +=dcountMax;		
		}	
		int dcMidP1=dcMid+1;
		if( dcMidP1 >=dcountMax ) {
			dcMidP1 -=dcountMax;		
		}	  
		int dcMidP2=dcMid+2;
		if( dcMidP2 >=dcountMax ) {
			dcMidP2 -=dcountMax;		
		}
		vv=(d[dcNow].Sum(d[dcount]).Diff((d[dcMid]).Prod(2f))).Prod(2f);
		vv.add( (d[dcMidM2].Sum(d[dcMid]).Diff(d[dcMidM1].Prod(2f))));
		vv.add( (d[dcMid].Sum(d[dcMidP2]).Diff(d[dcMidP1].Prod(2f))));
		 
		vv.div((dcountMax/2+2)*st);	
		return vv;
	}
	public Vec2 calcMidV(float st) {
		if( dcountMax == 0) {
			return Vec2.zero;
			// データを貯めない設定の時は、0を返す。
		}
		Vec2 vv;
		// dcountは「一番古いデータ」を示している。
		// dcount-1が「一番新しいデータ」である。
		int dcMid;
		dcMid=dcount-dcountMax/2;
		if(dcMid<0){
			dcMid +=dcountMax;
		}
		// dcMidは「中間の値」
		 
		int dcMidM1=dcMid-1;
		if( dcMidM1 <0 ) {
			dcMidM1 +=dcountMax;		
		}	
		 
		int dcMidM2=dcMid-2;
		if( dcMidM2 <0 ) {
			dcMidM2 +=dcountMax;		
		}	 
		int dcMidP1=dcMid+1;
		if( dcMidP1 >=dcountMax ) {
			dcMidP1 -=dcountMax;		
		}	 
		int dcMidP2=dcMid+2;
		if( dcMidP2 >=dcountMax ) {
			dcMidP2 -=dcountMax;		
		}
		 
		vv=(d[dcNow]).Diff(d[dcount]);
		vv.div((dcountMax*st));
		return vv;
	}
	public Vec2 calcMid() {
		if( dcountMax ==0 ) {
			return new Vec2(tmpPos);
			// データを貯めない設定の時は、現在の位置
			// そのものを返す。
		}
		int dcMid;
		dcMid=dcount-dcountMax/2;
		if(dcMid<0){
			dcMid +=dcountMax;
		}
		
		return d[dcMid];
	}
}
