package jp.ac.u_ryukyu.phys.Ampere;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;


public class AmpereActivity extends Activity {
	AmpereView view;
	int charge=0;
	int current=1;
	TextView labelq;
	TextView labeli;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        view=new AmpereView(this);
        view.setQ(charge);
        view.setI(current);
        LinearLayout l=(LinearLayout)findViewById(R.id.ll);    	
		l.addView(view,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
	
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
	  	findViewById(R.id.btCenter).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.centeringI();
			}
		});
			
		findViewById(R.id.btQup).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				charge++;
				if (charge > 3) {
					charge = 3;
				}
				view.setQ(charge);
				labelq.setText(String.valueOf(charge));

			}
		});
		findViewById(R.id.btQdown).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				charge--;
				if (charge < -3) {
					charge = 3;
				}
				view.setQ(charge);
				labelq.setText(String.valueOf(charge));
			}
		});
		

		labelq=(TextView)findViewById(R.id.tCharge);
		findViewById(R.id.btIup).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				current++;
				if (current > 3) {
					current = 3;
				}
				view.setI(current);
				labeli.setText(String.valueOf(current));

			}
		});
		findViewById(R.id.btIdown).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				current--;
				if (current < -3) {
					current = -3;
				}
				view.setI(current);
				labeli.setText(String.valueOf(current));
			}
		});
		labeli=(TextView)findViewById(R.id.tCurrent);
		findViewById(R.id.btEn).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.maruEvt();
			}
		});
		findViewById(R.id.btSankaku).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.sankakuEvt();
			}
		});
		findViewById(R.id.btSikaku).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.sikakuEvt();
			}
		});
		findViewById(R.id.btOugi).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.ougiEvt();
			}
		});
		findViewById(R.id.btStop).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.stopOnce();
			}
		});
    }
}