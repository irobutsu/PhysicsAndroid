package jp.ac.u_ryukyu.phys.xva;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import jp.ac.u_ryukyu.phys.xva.XvaView;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;



public class XvaActivity extends Activity implements SensorEventListener {
	CheckBox cbU;
	ToggleButton tbS;
	boolean useG=false;
	TextView labelg,labele;
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
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		view.timerStop();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		view.timerStop();
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.rot0:
			setRotState(0);
			break;
		case R.id.rot90:
			setRotState(1);
			break;
		case R.id.rot180:
			setRotState(2);
			break;
		case R.id.rot270:
			setRotState(3);
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	XvaView view;
	Bitmap bitmap;
	private int rotState=0;
	private SensorManager sensorManager;
	

//	private Button orButton;
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
    	tbS=(ToggleButton)findViewById(R.id.tbStop);
	  	tbS.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
	  		@Override
	  		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
	  			if( isChecked ) {
	  				view.timerStart();
	  			} else {
	  				view.timerStop();
	  			}
	  		}
	  	});
			
		cbU=(CheckBox)findViewById(R.id.cbU);
		cbU.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) { 
				if( useG ) {
					view.setUFlg(((CheckBox)v).isChecked());
					if( ((CheckBox)v).isChecked() ) {
						showGDialog();
					}
				} else {
					v.setEnabled(false);
				}
			}
	     	});

		labelg=(TextView)findViewById(R.id.textView2);
		((SeekBar)findViewById(R.id.sbG)).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,	boolean fromUser) {
				view.setG(progress-20f);
				String s=getString(R.string.g_equal);
				s += String.valueOf(progress-20);
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
	    	
		labele=(TextView)findViewById(R.id.textView3);
		((SeekBar)findViewById(R.id.sbE)).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				view.setE(0.01f*progress);
				String s=getString(R.string.e_equal);
				s += String.valueOf(0.01f*progress);
				labele.setText(s);
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
//		orButton = (Button)findViewById(R.id.btnG);
//	    	
//		orButton.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) { 
//				setRotState(getRotState() + 1);
//				if( getRotState() >3) {
//					setRotState(0);
//	    		}
//				switch(getRotState()) {
//				case 0:
//					((Button)v).setText(R.string.angle_add_0);
//					break;
//	    		case 1:
//	    			((Button)v).setText(R.string.angle_add_90);
//	    			break;
//	    		case 2:
//	    			((Button)v).setText(R.string.angle_add_180);
//	    			break;
//	    		case 3:
//	    			((Button)v).setText(R.string.angle_add_270);
//	    			break;
//	    		}
//			}
//	    });

	    	
		Resources r = getResources();
		bitmap=BitmapFactory.decodeResource(r, R.drawable.background);
		view.setBitmap(bitmap);
		
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		
		// 加速度センサー
		List<Sensor> sensors = sensorManager
				.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if (sensors.size() > 0) {
			useG=true;
			sensorManager.registerListener(this, sensors.get(0),SensorManager.SENSOR_DELAY_NORMAL);
		} else {
			useG=false;
		}
		cbU.setEnabled(useG);		
		checkDownWord();
	}
	boolean firstcall=true;
	public void showGDialog() {
		if( firstcall) {
			AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);	
			alertDialog.setTitle(R.string.cautiong);
			alertDialog.setMessage(R.string.alertg);
			final Dialog dialog=alertDialog.show();
			Handler dismisser=new Handler();
			Runnable r=new Runnable() {
				@Override
				public void run() {
					dialog.dismiss();
				}
			};
			dismisser.postDelayed(r,3000);
		}
		firstcall=false;
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();	
		view.setUFlg(cbU.isChecked());
		if( tbS.isChecked()) {
			view.timerStart();
		} else {
			view.timerStop();
		}
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSensorChanged(SensorEvent event) {
		Sensor sensor = event.sensor;
		if(sensor.getType()==Sensor.TYPE_ACCELEROMETER ) {
			switch(getRotState()) {
			case 0:
				view.setA(event.values[0], event.values[1]);
				break;
			case 1:
				view.setA(-event.values[1], event.values[0]);
				break;
			case 2:
				view.setA(-event.values[0], -event.values[1]);
				break;
			case 3:
				view.setA(event.values[1], -event.values[0]);
				break;
			}
		}
	}

	protected void newView() {
		view=new XvaView(this);
	}
	public int getRotState() {
		return rotState;
	}

	public void setRotState(int rotState) {
		this.rotState = rotState;
	}
	public void checkDownWord(){
		rotState=0;
		AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);	
		alertDialog.setTitle(R.string.cautiongcheck);
		alertDialog.setMessage(R.string.alertgcheck);
		final Dialog dialog2=alertDialog.show();
		Handler dismisser=new Handler();
		Runnable r=new Runnable() {
			@Override
			public void run() {
				dialog2.dismiss();
				float ax=view.getAx();
				float ay=view.getAy();
				if( Math.abs(ax)>Math.abs(ay) ) {
					if( ax>0) {
						rotState=3;
					} else {
						rotState=1;
					}
				} else {
					if( ay>0) {
						rotState=0;
					} else {
						rotState=2;
					}
				}
				view.checkDone();
			}
		};
		dismisser.postDelayed(r,5000);
	
	}
} 
