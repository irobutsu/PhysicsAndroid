package jp.ac.u_ryukyu.phys.floatingball;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity  implements SensorEventListener{
	boolean useG;
	private SensorManager sensorManager;
	FloatingBallView view;
	Button reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        view= new FloatingBallView(this);
        LinearLayout l=(LinearLayout)findViewById(R.id.ll);    	
		l.addView(view,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		reset=(Button)findViewById(R.id.reset);
		reset.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) { view.reset(); }
		});
  
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
		checkDownWord();

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
    public boolean onMenuItemSelected(int featureId,MenuItem item) {
    	switch (item.getItemId()) {
		case R.id.rot0:
			rotState=0;
			break;
		case R.id.rot90:
			rotState=1;
			break;
		case R.id.rot180:
			rotState=2;
			break;
		case R.id.rot270:
			rotState=3;
			break;
		}
		return super.onMenuItemSelected(featureId, item);

    }

    int rotState;
	@Override
	public void onSensorChanged(SensorEvent event) {
		Sensor sensor = event.sensor;
		if(sensor.getType()==Sensor.TYPE_ACCELEROMETER ) {
			switch(rotState) {
			case 0:
				view.setA(event.values[0], event.values[1],event.values[2]);
				break;
			case 1:
				view.setA(-event.values[1], event.values[0],event.values[2]);
				break;
			case 2:
				view.setA(-event.values[0], -event.values[1],event.values[2]);
				break;
			case 3:
				view.setA(event.values[1], -event.values[0],event.values[2]);
				break;
			}
		}
	}


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
}
