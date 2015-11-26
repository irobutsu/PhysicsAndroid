package jp.ac.u_ryukyu.phys.Kepler;


import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.ToggleButton;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class KeplerActivity extends Activity {
	TextView labelg;
	KeplerView view;
	protected Bitmap bitmap;
	protected void newView(){
		view=new KeplerView(this);
	}
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		newView();
    	LinearLayout l=(LinearLayout)findViewById(R.id.ll);    	
		l.addView(view,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
		((CheckBox)findViewById(R.id.cbF)).setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				view.setFFlg(isChecked);
			}
    	});
    	((CheckBox)findViewById(R.id.cbA)).setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				view.setAFlg(isChecked);
			}
    	});
    	((CheckBox)findViewById(R.id.cbV)).setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				view.setVFlg(isChecked);
			}
    	});	
    	((CheckBox)findViewById(R.id.tbG)).setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				view.setGFlg(isChecked);
			}
    	});
    	((CheckBox)findViewById(R.id.cbGV)).setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				view.setGVFlg(isChecked);
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
		
		findViewById(R.id.button1).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.setCircle();
				((ToggleButton) findViewById(R.id.tbStop)).setChecked(true);
			}
		});		
		findViewById(R.id.stopG).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.stopGP();
				((ToggleButton) findViewById(R.id.tbStop)).setChecked(true);
			}
		});	
		findViewById(R.id.brake).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.brake();
			}
		});

		labelg=(TextView)findViewById(R.id.textView2);
		((SeekBar)findViewById(R.id.sbG)).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				view.setMom((float)(Math.exp(progress*Math.log(30.0)/100.0)));
				String s=getString(R.string.Mverm_equal);
				s += String.valueOf(Math.exp(progress*Math.log(30.0)/100.0));
				if( s.length()>9) {
					s=s.substring(0, 9);
				}
				labelg.setText(s);
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
		Resources r = getResources();
		bitmap=BitmapFactory.decodeResource(r, R.drawable.background);
		view.setBitmap(bitmap); 	
	}
}