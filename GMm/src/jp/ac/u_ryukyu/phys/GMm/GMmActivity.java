package jp.ac.u_ryukyu.phys.GMm;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.ToggleButton;
import android.graphics.BitmapFactory;

public class GMmActivity extends Activity {
	private TextView labelg;
	protected GMmView view;	
	protected Bitmap bitmap;	

	protected void newView(){
		view=new GMmView(this);
	}
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.main);
    	newView();
    	LinearLayout l=(LinearLayout)findViewById(R.id.ll);    	
		l.addView(view,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
		((ToggleButton)findViewById(R.id.cbF)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				view.setFFlg(isChecked);
			}
    	});
    	((ToggleButton)findViewById(R.id.cbA)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				view.setAFlg(isChecked);
			}
    	});
    	((ToggleButton)findViewById(R.id.cbV)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				view.setVFlg(isChecked);
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
	  	Resources r = getResources();
		bitmap=BitmapFactory.decodeResource(r, R.drawable.background);
		view.setBitmap(bitmap); 
		((ToggleButton)findViewById(R.id.tbS)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				view.setSFlg(isChecked);
			}
    	});
			
		findViewById(R.id.button1).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.setCircle();
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
				view.setMom((float)(Math.exp(progress*Math.log(200.0)/100.0)));
				String s="M=";
				s += String.valueOf(Math.exp(progress*Math.log(200.0)/100.0));
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
	}
}
