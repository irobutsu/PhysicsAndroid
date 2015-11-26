package jp.ac.u_ryukyu.phys.VectorPotential;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class VectorPotentialActivity extends Activity {
	private VectorPotentialSurfaceView view3d;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        LinearLayout main=(LinearLayout)findViewById(R.id.mainscreen);
        view3d=new VectorPotentialSurfaceView(this);
        main.addView(view3d,new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT));
//      	((ToggleButton)findViewById(R.id.tbStop)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
//	  		@Override
//	  		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
//	  			if( isChecked ) {
//	  				view3d.start();
//	  			} else {
//	  				view3d.stop();
//	  			}
//	  		}
//	  	});
	  	
	  	((ToggleButton)findViewById(R.id.tbB)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
	  		@Override
	  		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
	  				view3d.setBFlg(isChecked);
	  		}
	  	});  	
	  	((ToggleButton)findViewById(R.id.tbA)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
	  		@Override
	  		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
	  				view3d.setAFlg(isChecked);
	  		}
	  	});  	
	  	((ToggleButton)findViewById(R.id.tbrotAx)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
	  		@Override
	  		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
	  				view3d.setrotAxFlg(isChecked);
	  		}
	  	});
	  	((ToggleButton)findViewById(R.id.tbrotAy)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
	  		@Override
	  		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
	  				view3d.setrotAyFlg(isChecked);
	  		}
	  	});  	
	  	((ToggleButton)findViewById(R.id.tbrotAz)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
	  		@Override
	  		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
	  				view3d.setrotAzFlg(isChecked);
	  		}
	  	});  	
	  	findViewById(R.id.btResetScreen).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view3d.resetView();
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
		case R.id.mode0:
			view3d.setMode(0);
			break;
		case R.id.mode1:
			view3d.setMode(1);
			break;
		case R.id.mode2:
			view3d.setMode(2);
			break;
		case R.id.mode3:
			view3d.setMode(3);
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
}