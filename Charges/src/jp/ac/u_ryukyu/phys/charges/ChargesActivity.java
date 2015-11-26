package jp.ac.u_ryukyu.phys.charges;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class ChargesActivity extends Activity {

	ChargesView view;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charges);
    	LinearLayout main=(LinearLayout)findViewById(R.id.ll);   
   
		
		
		view=new ChargesView(this,(ProgressBar)findViewById(R.id.progressBar1));
		main.addView(view,new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,android.view.ViewGroup.LayoutParams.FILL_PARENT));
	

		((ToggleButton)findViewById(R.id.tbE)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				view.setEFlg(isChecked);
				view.invalidate();
			}
		});

	        
		findViewById(R.id.btErase).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.allErase();
			}
		});	
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

}
