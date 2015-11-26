package jp.ac.phys.u_ryukyu.charges3D;




import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ToggleButton;
import android.widget.ProgressBar;

public class Charges3DActivity extends Activity {
	ChargesView view;
	Charges3DView view3d;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    	LinearLayout main=(LinearLayout)findViewById(R.id.ll);   
    	view3d=new Charges3DView(this);
		main.addView(view3d,new LinearLayout.LayoutParams(50,50));
		
		
		
		view=new ChargesView(this,view3d,(ProgressBar)findViewById(R.id.progressBar1));
		main.addView(view,new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,android.view.ViewGroup.LayoutParams.FILL_PARENT));
	

		((ToggleButton)findViewById(R.id.tbE)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				view.setEFlg(isChecked);
				view.invalidate();
			}
		});
		((ToggleButton)findViewById(R.id.tbV)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				view.setVFlg(isChecked);
				view.invalidate();
			}
		});   
//		
//		((RadioGroup) findViewById(R.id.radioGroup)).setOnCheckedChangeListener(
//				new RadioGroup.OnCheckedChangeListener() {
//					@Override
//					public void onCheckedChanged(RadioGroup group, int checkedId) { 
//						switch( checkedId) {
//						case R.id.pluscharge:
//							view.setQFlg(true);
//							view.setPFlg(true);
//							break;
//						case R.id.minuscharge:
//							view.setQFlg(true);
//							view.setPFlg(false);
//							break;
//						case R.id.rmcharge:
//							view.setQFlg(false);
//						}
//					}
//				});
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    // アイテムを追加します
	    adapter.add(getString(R.string.add_Plus));
	    adapter.add(getString(R.string.add_Minus));
	    adapter.add(getString(R.string.rm_Charge));
	    Spinner spinner = (Spinner) findViewById(R.id.setMode);
	    // アダプターを設定します
	    spinner.setAdapter(adapter);
	    // スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
	    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	    	@Override
	    	public void onItemSelected(AdapterView<?> parent, View vw,
	    			int position, long id) {
	    		Spinner s = (Spinner) parent;
	    		switch( s.getSelectedItemPosition() ) {
	    		case 0:
	    			view.setQFlg(true);
	    			view.setPFlg(true);
	    			break;
	    		case 1:
	    			view.setQFlg(true);
	    			view.setPFlg(false);
	    			break;
	    		case 2:
	    			view.setQFlg(false);
	    		}
	    	}
	    	@Override
	    	public void onNothingSelected(AdapterView<?> arg0) {
	    	}
	    });
	        
		findViewById(R.id.btErase).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.allErase();
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
			return true;
		case R.id.mode1:
			view3d.setMode(1);
			return true;
		case R.id.mode2:
			view3d.setMode(2);
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

}