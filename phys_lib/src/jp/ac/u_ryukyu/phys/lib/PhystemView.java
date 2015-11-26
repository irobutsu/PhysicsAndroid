package jp.ac.u_ryukyu.phys.lib;



import android.content.Context;
import android.graphics.Canvas;



public abstract class PhystemView extends DraggManageView {

	protected boolean vFlg=true;
	protected boolean aFlg=true;
	protected boolean fFlg=true;


	
	
	
	
	// protected View nearView;
	
	
	public PhystemView(Context context) {
		super(context);	
	}
	

    public void setAFlg(boolean a) { aFlg=a;}
    public void setFFlg(boolean a) { fFlg=a;}    
    public void setVFlg(boolean a) { vFlg=a;}

	

	@Override
	protected void writePrevious(Canvas canvas) {
		super.writePrevious(canvas);
		// ↑これで物体が描かれる。
		int i;
		if( vFlg ) {
			for(i=0; i<objs.size(); i++) {
				objs.get(i).writePV(canvas);
			}
		}		
		if( aFlg ) {
			for(i=0; i<objs.size(); i++) {
				objs.get(i).writePA(canvas);
			}
		}
			
		// これで、「描かれる力」は、このターンで掛かってた力全部。
		if( fFlg ) {
			for(i=0; i<objs.size(); i++) {
				objs.get(i).writeF(canvas);
			}
		}
	}
}
