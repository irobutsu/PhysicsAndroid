package jp.ac.u_ryukyu.phys.Oscillator;




import jp.ac.u_ryukyu.phys.Shindousi.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class OscillatorActivity extends Activity {
	OscillatorView view;
	private TextView labelm;
	private TextView labelk;
	private TextView labelkappa;
	SeekBar sbk;
	SeekBar sbm;
	SeekBar sbkappa;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    	view =new OscillatorView(this);
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
	  	labelm=(TextView)findViewById(R.id.TextViewM);
		sbm=(SeekBar)findViewById(R.id.sbM);
		sbm.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				float m=0.1f*(progress+1);
				view.setMass(m);
				String s="質量=";
				s += String.valueOf(m);
				if( s.length()>9) {
					s=s.substring(0, 9);
				}
				labelm.setText(s);
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
	  	labelk=(TextView)findViewById(R.id.textView2);
			sbk=(SeekBar)findViewById(R.id.sbK);
			sbk.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					float k=0.1f*(progress+1);
					view.setK(k);
					String s="ばね定数=";
					s += String.valueOf(k);
					if( s.length()>9) {
						s=s.substring(0, 9);
					}
					labelk.setText(s);
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
		labelkappa=(TextView)findViewById(R.id.textView1);
		sbkappa=(SeekBar)findViewById(R.id.sbKappa);
		sbkappa.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				float k=0.01f*(progress);
				view.setKappa(k);
				String s="空気抵抗=";
				s += String.valueOf(k);
				if( s.length()>9) {
					s=s.substring(0, 9);
				}
				labelkappa.setText(s);
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
		findViewById(R.id.Mup).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sbm.incrementProgressBy(1);
			}
		});
		findViewById(R.id.MDown).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sbm.incrementProgressBy(-1);
			}
		});
		findViewById(R.id.KUp).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sbk.incrementProgressBy(1);
			}
		});
		findViewById(R.id.KDown).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sbk.incrementProgressBy(-1);
			}
		});
		findViewById(R.id.KappaUp).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sbkappa.incrementProgressBy(1);
			}
		});
		findViewById(R.id.KappaDown).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sbkappa.incrementProgressBy(-1);
			}
		});
		
		findViewById(R.id.tzero).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.TimeRestart();
			}
		});
		
		findViewById(R.id.sol).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.toggleSFlg();
			}
		});

    }
}