package jp.ac.u_ryukyu.phys.BiotSavart;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.SeekBar.OnSeekBarChangeListener;

@SuppressLint("NewApi")
public class BiotSavartActivity extends Activity {
	BiotSavartSurfaceView view3d;
	BiotSavartGraphView view;
	TextView labelx;
	TextView labelz;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        LinearLayout main=(LinearLayout)findViewById(R.id.mainscreen);
        
        
        view3d=new BiotSavartSurfaceView(this);
        //view3d.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT));
       // main.addView(view3d,new LinearLayout.LayoutParams(100,100));
        main.addView(view3d,new LinearLayout.LayoutParams(10,10));
        //view3d.setMinimumHeight(h);
        //view3d.setMinimumWidth(h);
        view=new BiotSavartGraphView(this,view3d);
        main.addView(view,new LinearLayout.LayoutParams(10,10));
//        main.addView(view,new LinearLayout.LayoutParams(w-h,h));
        // サイズはviewのonSizeChangeで調整する。
    

		labelz=(TextView)findViewById(R.id.textZ);
		((SeekBar)findViewById(R.id.sbZ)).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				view.setZ(0.1f*progress);
				String s=getString(R.string.z_);
				s += String.valueOf(progress*0.1f);
				if( s.length()>5) {
					s=s.substring(0, 5);
				}
				labelz.setText(s);
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
	  	((ToggleButton)findViewById(R.id.tbStop)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
	  		@Override
	  		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
	  			if( isChecked ) {
	  				view.start();
	  			} else {
	  				view.stop();
	  			}
	  		}
	  	});
	  	
	  	((ToggleButton)findViewById(R.id.tbx)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
	  		@Override
	  		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
	  				view3d.setXFlg(isChecked);
	  		}
	  	});  	
	  	((ToggleButton)findViewById(R.id.tby)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
	  		@Override
	  		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
	  				view3d.setYFlg(isChecked);
	  		}
	  	});  	
	 	((ToggleButton)findViewById(R.id.tbz)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
	  		@Override
	  		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
	  				view3d.setZFlg(isChecked);
	  		}
	  	});
	 	((ToggleButton)findViewById(R.id.tba)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
	  		@Override
	  		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
	  				view3d.setAFlg(isChecked);
	  		}
	  	});
		findViewById(R.id.btResetScreen).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view3d.resetView();
			}
		});	
		labelx=(TextView)findViewById(R.id.textX);
		((SeekBar)findViewById(R.id.sbX)).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				view.setX(0.1f*progress);
				String s=getString(R.string.x_);
				s += String.valueOf(progress*0.1f);
				if( s.length()>5) {
					s=s.substring(0, 5);
				}
				labelx.setText(s);
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