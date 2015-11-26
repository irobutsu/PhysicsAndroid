package jp.ac.u_ryukyu.phys.rutherford;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {
	private TextView labelg,labelR,labelv;
	protected RutherfordView view;	
	protected Bitmap bitmap;	

	protected void newView(){
		view=new RutherfordView(this);
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
	
		findViewById(R.id.btGO).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.startm();
			}
		});	
		findViewById(R.id.clear).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.clearPath();
			}
		});	

		labelg=(TextView)findViewById(R.id.textView2);
		((SeekBar)findViewById(R.id.sbG)).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				view.setQ((float)(5000*(1+progress)));
				String s="Q=";
				s += String.valueOf(1+progress);
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
		labelv=(TextView)findViewById(R.id.textViewV);
		((SeekBar)findViewById(R.id.sbV)).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				view.setV((float)(progress));
				String s="V=";
				s += String.valueOf(progress);
				labelv.setText(s);
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
		labelR=(TextView)findViewById(R.id.textViewR);
		((SeekBar)findViewById(R.id.sbR)).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				view.setR((float)(progress+1));
				String s="R=";
				s += String.valueOf(progress+1);
				labelR.setText(s);
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
		}
		);
    }   

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
