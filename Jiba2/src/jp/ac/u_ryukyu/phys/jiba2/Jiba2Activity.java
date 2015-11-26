package jp.ac.u_ryukyu.phys.jiba2;

import jp.ac.u_ryukyu.phys.jiba2.R;
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

public class Jiba2Activity extends Activity {
	Jiba2View view;
	TextView labelQ1;
	TextView labelQ2;
	TextView labelM1;
	TextView labelEB;
	protected void newView() {
		view=new Jiba2View(this);
	}
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        newView();
    	LinearLayout l=(LinearLayout)findViewById(R.id.ll);    	
		l.addView(view,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
	
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
		labelM1=((TextView)findViewById(R.id.M1Text));
		labelM1.setTextColor(Color.CYAN);
		((Button)findViewById(R.id.M1Up)).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) { 				
				String s=getString(R.string.m1_equal);
				s += String.valueOf(view.addM());
				labelM1.setText(s);
				view.invalidate();
			}
		});		
		((Button)findViewById(R.id.M1Down)).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) { 				
				String s=getString(R.string.m1_equal);
				s += String.valueOf(view.subM());
				labelM1.setText(s);
				view.invalidate();
			}
		});		
		labelEB=((TextView)findViewById(R.id.EBText));
		labelEB.setTextColor(Color.WHITE);
		((Button)findViewById(R.id.EBUp)).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) { 				
				String s=getString(R.string.eB_equal);
				int B=view.addB();
				s += String.valueOf(B);
				if( B>0 ) {
					s += "↓";
				}
				if( B<0 ) {
					s += "↑";
				}
				labelEB.setText(s);
				view.invalidate();
			}
		});		
		((Button)findViewById(R.id.EBDown)).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) { 				
				String s=getString(R.string.eB_equal);
				int B=view.subB();
				s += String.valueOf(B);
				if( B>0 ) {
					s += "↓";
				}
				if( B<0 ) {
					s += "↑";
				}
				labelEB.setText(s);
				view.invalidate();
			}
		});		
    }
}