package jp.ac.u_ryukyu.phys.jiba;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class JibaActivity extends Activity {
	JibaView view;
	TextView labelQ1;
	TextView labelQ2;
	protected void newView() {
		view=new JibaView(this);
	}
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        newView();
    	LinearLayout l=(LinearLayout)findViewById(R.id.ll);    	
		l.addView(view,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
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
		findViewById(R.id.Q1Up).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				String s = getString(R.string.q1_equal);
				s += String.valueOf(view.addQ1());
				labelQ1.setText(s);
				view.invalidate();
			}
		});		
		findViewById(R.id.Q1Down).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				String s = getString(R.string.q1_equal);
				s += String.valueOf(view.subQ1());
				labelQ1.setText(s);
				view.invalidate();
			}
		});		
		labelQ2=((TextView)findViewById(R.id.Q2Text));
		labelQ2.setTextColor(Color.BLUE);
		findViewById(R.id.Q2Up).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				String s = getString(R.string.q2_equal);
				s += String.valueOf(view.addQ2());
				labelQ2.setText(s);
				view.invalidate();
			}
		});		
		findViewById(R.id.Q2Down).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				String s = getString(R.string.q2_equal);
				s += String.valueOf(view.subQ2());
				labelQ2.setText(s);
				labelQ2.setTextColor(Color.BLUE);
				view.invalidate();
			}
		});	
    }
}