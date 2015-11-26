package jp.ac.u_ryukyu.phys.ElectricField3D;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ToggleButton;
import android.widget.CompoundButton;
import android.view.View;

public class ElectricField3DActivity extends Activity {


	DenbaView view;
	Denba3DView view3d;
	TextView labelQ1;
	TextView labelQ2;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		view3d=new Denba3DView(this);

		LinearLayout main=(LinearLayout)findViewById(R.id.ll);    	
		main.addView(view3d,new LinearLayout.LayoutParams(50,50));
		view=new DenbaView(this,view3d);
		main.addView(view,new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,android.view.ViewGroup.LayoutParams.FILL_PARENT));



		((ToggleButton)findViewById(R.id.tbE)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				view.setEFlg(isChecked);
				view.invalidate();
			}
		});
		((ToggleButton)findViewById(R.id.tbV)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				view.setVFlg(isChecked);
				view.invalidate();
			}
		});
		((ToggleButton)findViewById(R.id.tbF)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				view.setFFlg(isChecked);
				view.invalidate();
			}
		});
		((ToggleButton)findViewById(R.id.tbY)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				view.setYFlg(isChecked);
				view.invalidate();
			}
		});
		((ToggleButton)findViewById(R.id.tbS)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				view.setSFlg(isChecked);
				view.invalidate();
			}
		});
		((ToggleButton)findViewById(R.id.tbY1)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				view.setY1Flg(isChecked);
				view.invalidate();
			}
		});
		((ToggleButton)findViewById(R.id.tbY2)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				view.setY2Flg(isChecked);
				view.invalidate();
			}
		});
		labelQ1=((TextView)findViewById(R.id.Q1Text));
		labelQ1.setTextColor(Color.RED);
		((Button)findViewById(R.id.Q1Up)).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) { 				
				String s=getString(R.string.q1_equal);
				s += String.valueOf(view.addQ1());
				labelQ1.setText(s);
				view.invalidate();
			}
		});		
		((Button)findViewById(R.id.Q1Down)).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) { 				
				String s=getString(R.string.q1_equal);
				s += String.valueOf(view.subQ1());
				labelQ1.setText(s);
				view.invalidate();
			}
		});		
		labelQ2=((TextView)findViewById(R.id.Q2Text));
		labelQ2.setTextColor(Color.BLUE);
		((Button)findViewById(R.id.Q2Up)).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) { 				
				String s=getString(R.string.q2_equal); 
				s += String.valueOf(view.addQ2());
				labelQ2.setText(s);
				view.invalidate();
			}
		});		
		((Button)findViewById(R.id.Q2Down)).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) { 				
				String s=getString(R.string.q2_equal);
				s += String.valueOf(view.subQ2());
				labelQ2.setText(s);
				labelQ2.setTextColor(Color.BLUE);
				view.invalidate();
			}
		});
		((Button)findViewById(R.id.btResetScreen)).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) { view3d.resetView(); }
		});	
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
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.mode0:
			view3d.setMode(0);
			return true;
		case R.id.mode1:
			view3d.setMode(1);
			return true;
		case R.id.mode2:
			view3d.setMode(2);
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}
} 