package jp.ac.u_ryukyu.phys.spherical;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class SphericalActivity extends Activity {
	SphericalSurfaceView view3d;
	static String menu_string[]= {"無","静","動"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spherical);
		LinearLayout main=(LinearLayout)findViewById(R.id.mainscreen);
        view3d=new SphericalSurfaceView(this);
        main.addView(view3d,new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT));
    	findViewById(R.id.btResetScreen).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view3d.resetView();
			}
		});	
    	Button btr = (Button) findViewById(R.id.vecr);
        btr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { 
            	((Button)v).setText(menu_string[view3d.vrstateChange()]); 	
            }
        });
     	Button bttheta = (Button) findViewById(R.id.vectheta);
        bttheta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { 	
            	((Button)v).setText(menu_string[view3d.vthetastateChange()]); 
            }
        });
     	Button btphi = (Button) findViewById(R.id.vecphi);
        btphi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { 	
            	((Button)v).setText(menu_string[view3d.vphistateChange()]); 
            }
        });
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		//繝｡繝九Η繝ｼ繧､繝ｳ繝輔Ξ繝ｼ繧ｿ繝ｼ繧貞叙蠕�
    	MenuInflater inflater = getMenuInflater();
    	//xml縺ｮ繝ｪ繧ｽ繝ｼ繧ｹ繝輔ぃ繧､繝ｫ繧剃ｽｿ逕ｨ縺励※繝｡繝九Η繝ｼ縺ｫ繧｢繧､繝�Β繧定ｿｽ蜉�
    	inflater.inflate(R.menu.menu, menu);
    	//縺ｧ縺阪◆繧液rue繧定ｿ斐☆
    	return true;
		
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.res0 :
			view3d.setReso(8,10,3);
			break;
		case R.id.res1:
			view3d.setReso(14,16,5);
			break;
		case R.id.res2:
			view3d.setReso(20,24,8);
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

}
