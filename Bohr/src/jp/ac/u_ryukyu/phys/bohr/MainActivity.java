package jp.ac.u_ryukyu.phys.bohr;

import android.os.Bundle;
import android.app.Activity;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	Bohr3DView view3d;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		view3d=new Bohr3DView(this);

		LinearLayout main=(LinearLayout)findViewById(R.id.ll);    	
		main.addView(view3d,new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT));
		((ToggleButton)findViewById(R.id.tbParticle)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				view3d.setPFlg(isChecked);
				view3d.invalidate();
			}
		});
		((ToggleButton)findViewById(R.id.tbWave)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				view3d.setWFlg(isChecked);
				view3d.invalidate();
			}
		});
		((CheckBox)findViewById(R.id.cb1s)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				view3d.set1SFlg(isChecked);
				view3d.invalidate();
			}
		});
		((CheckBox)findViewById(R.id.cb2s)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				view3d.set2SFlg(isChecked);
				view3d.invalidate();
			}
		});
		((CheckBox)findViewById(R.id.cb3s)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				view3d.set3SFlg(isChecked);
				view3d.invalidate();
			}
		});
	}
}
