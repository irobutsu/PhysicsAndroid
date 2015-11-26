package jp.ac.u_ryukyu.phys.gsense;



import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

public class GsenseActivity extends Activity implements SensorEventListener{
	GsenseSurfaceView view3d;
	GsenseGraphView view;
	TextView labelx;
	TextView labelz;
	TextView values;
	SensorManager manager;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
    	values = (TextView)findViewById(R.id.value_id);
		manager = (SensorManager)getSystemService(SENSOR_SERVICE);
		
        setContentView(R.layout.main);
        LinearLayout main=(LinearLayout)findViewById(R.id.mainscreen);
        
        
        view3d=new GsenseSurfaceView(this);
        //view3d.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT));
       // main.addView(view3d,new LinearLayout.LayoutParams(100,100));
        main.addView(view3d,new LinearLayout.LayoutParams(10,10));
        //view3d.setMinimumHeight(h);
        //view3d.setMinimumWidth(h);
        view=new GsenseGraphView(this,view3d);
        main.addView(view,new LinearLayout.LayoutParams(10,10));
//        main.addView(view,new LinearLayout.LayoutParams(w-h,h));
        // サイズはviewのonSizeChangeで調整する。
    

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
//	  	((ToggleButton)findViewById(R.id.tba)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
//	  		@Override
//	  		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
//	  				view3d.setAFlg(isChecked);
//	  				view.setAFlg(isChecked);
//	  		}
//	  	});  	
	  	((ToggleButton)findViewById(R.id.tbx)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
	  		@Override
	  		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
	  				view3d.setXFlg(isChecked);
	  				view.setXFlg(isChecked);
	  		}
	  	});  	
	  	((ToggleButton)findViewById(R.id.tby)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
	  		@Override
	  		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
	  			view3d.setYFlg(isChecked);
	  			view.setYFlg(isChecked);
	  		}
	  	});  	
	 	((ToggleButton)findViewById(R.id.tbz)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
	  		@Override
	  		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
	  				view3d.setZFlg(isChecked);
	  				view.setZFlg(isChecked);
	  		}
	  	});
		findViewById(R.id.btTurn).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.turn();
			}
		});	
    }

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		// Listenerの登録解除
		manager.unregisterListener(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// Listenerの登録
		List<Sensor> sensors = manager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if(sensors.size() > 0) {
			Sensor s = sensors.get(0);
			manager.registerListener(this, s, SensorManager.SENSOR_DELAY_UI);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			float x=event.values[SensorManager.DATA_X];
			float y=event.values[SensorManager.DATA_Y];
			float z=event.values[SensorManager.DATA_Z];
			view.setG(x,y,z);
		}
	}
}