package jp.ac.u_ryukyu.phys.Flux;



import jp.ac.u_ryukyu.phys.VectorProducts.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;

public class FluxActivity extends Activity {
	
	FluxView view;
	Flux3DView view3d;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        view3d=new Flux3DView(this);
        view=new FluxView(this,view3d);
    	LinearLayout l=(LinearLayout)findViewById(R.id.ll);    	
		l.addView(view,new LinearLayout.LayoutParams(100,100));
		l.addView(view3d,new LinearLayout.LayoutParams(100,100));
    }
}