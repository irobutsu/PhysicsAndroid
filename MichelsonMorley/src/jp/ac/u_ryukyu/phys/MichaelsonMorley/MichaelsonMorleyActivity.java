package jp.ac.u_ryukyu.phys.MichaelsonMorley;


import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.widget.SeekBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import jp.ac.u_ryukyu.phys.MichaelsonMorley.R;

public class MichaelsonMorleyActivity extends Activity {
	MichaelsonMorleyView view;
	TextView labelV;
	Bitmap bitmap;
	protected ToggleButton tbL;
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
		case R.id.ttPF:
			view.setTTFlg(true);
			view.setPtoF(true);
			view.setLFlg(true);
			tbL.setChecked(true);
			break;
		case R.id.ttFP:
			view.setTTFlg(true);
			view.setPtoF(false);
			view.setLFlg(true);
			tbL.setChecked(true);
			break;
		case R.id.ttNone:
			view.setTTFlg(false);
		}
		return super.onMenuItemSelected(featureId, item);
	}

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        view=new MichaelsonMorleyView(this);

        LinearLayout l=(LinearLayout)findViewById(R.id.ll);    	
        l.addView(view,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

        ((ToggleButton)findViewById(R.id.tbE)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
        	@Override
        	public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
        		view.setEFlg(isChecked);
		}
        });


	tbL=(ToggleButton)findViewById(R.id.tbL);
	tbL.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
		@Override
		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
			view.setLFlg(isChecked);
		}
	});	
	
	((ToggleButton)findViewById(R.id.tbStop)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
  		@Override
  		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
  			if( isChecked ) {
  				view.timerStart();
  			} else {
  				view.timerStop();
  			}
  		}
  	});
	labelV=(TextView)findViewById(R.id.tvV);
	((SeekBar)findViewById(R.id.sbV)).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			view.setV(0.01f*progress);
			String s="地球の速度は光速の";
			s += String.valueOf(progress);
			s += "％";
			labelV.setText(s);
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
				
		}
	});
	
	findViewById(R.id.btRestart).setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			view.restart();
		}
	});
	
    ((RadioGroup) findViewById(R.id.radioGroup1)).setOnCheckedChangeListener(
    		new RadioGroup.OnCheckedChangeListener() {
        @Override
		public void onCheckedChanged(RadioGroup group, int checkedId) { 
        	if( checkedId == R.id.rdPoint) {
        		view.setWFlg(false);
        	} else {
        		view.setWFlg(true);
        	}
        }
    });

	
	
	
	Resources r = getResources();
	bitmap=BitmapFactory.decodeResource(r, R.drawable.background);
	view.setBitmap(bitmap);
    }

}