package jp.ac.u_ryukyu.phys.SurfaceCharge;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SurfaceChargeActivity extends Activity {
	SurfaceChargeSurfaceView view3d;
	SurfaceChargeGraphView view;
	TextView labelx;
	TextView labelz;
	TextView labelr;
	TextView labeln;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintegral);
        LinearLayout main=(LinearLayout)findViewById(R.id.mainscreen);
        
        
        view3d=new SurfaceChargeSurfaceView(this);
        //view3d.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT));
       // main.addView(view3d,new LinearLayout.LayoutParams(100,100));
        main.addView(view3d,new LinearLayout.LayoutParams(100,100));
        //view3d.setMinimumHeight(h);
        //view3d.setMinimumWidth(h);
        view=new SurfaceChargeGraphView(this,view3d);
        main.addView(view,new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
//        main.addView(view,new LinearLayout.LayoutParams(w-h,h));
        // サイズはviewのonSizeChangeで調整する。
    
		labelz=(TextView)findViewById(R.id.textZ);
		((SeekBar)findViewById(R.id.sbZ)).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				view.setZ(0.1f*progress);
				String s="Z=";
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
		
		labeln=(TextView)findViewById(R.id.textN);
		((SeekBar)findViewById(R.id.sbN)).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				view.setN(progress+5);
				String s="N=";
				s += String.valueOf(progress+5);
				if( s.length()>5) {
					s=s.substring(0, 5);
				}
				labeln.setText(s);
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
		labelr=(TextView)findViewById(R.id.textR);
		((SeekBar)findViewById(R.id.sbR)).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				view.setR(progress*0.1f+0.2f);
				String s="R=";
				s += String.valueOf(progress*0.1f+0.2f);
				if( s.length()>5) {
					s=s.substring(0, 5);
				}
				labelr.setText(s);
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
	
		view.setZ(0f);
		view.setR(1f);
		view.setN(10);
        
    }
}
