package jp.ac.u_ryukyu.phys.sphericaldiv;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	SphericalDivSurfaceView view3d;
	static String menu_string[]= {"無","静","動"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LinearLayout main=(LinearLayout)findViewById(R.id.mainscreen);
        view3d=new SphericalDivSurfaceView(this);
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
    	Button btnorth = (Button) findViewById(R.id.btNorth);
        btnorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { 	
            	view3d.toNorth(); 
            }
        });	
        Button btsouth = (Button) findViewById(R.id.btSouth);
        btsouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { 	
            	view3d.toSouth(); 
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		//メニューインフレーターを取得
    	MenuInflater inflater = getMenuInflater();
    	//xmlのリソースファイルを使用してメニューにアイテムを追加
    	inflater.inflate(R.menu.menu, menu);
    	//できたらtrueを返す
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
