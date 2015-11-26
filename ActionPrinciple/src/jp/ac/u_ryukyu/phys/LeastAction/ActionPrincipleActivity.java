package jp.ac.u_ryukyu.phys.LeastAction;



import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ActionPrincipleActivity extends Activity {
	private ActionPrincipleView view;
	SeekBar sbk;
	SeekBar sbkappa;
	private TextView labelg;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    	view =new ActionPrincipleView(this);
    	LinearLayout l=(LinearLayout)findViewById(R.id.ll);    	
		l.addView(view,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
	
    	labelg=(TextView)findViewById(R.id.textView2);
		((SeekBar)findViewById(R.id.sbG)).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,	boolean fromUser) {
				view.setG(progress/10f);
				String s=getString(R.string.g_equal);
				s += String.valueOf(progress/10f);
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
    	
        ((RadioGroup) findViewById(R.id.radioGroup1)).setOnCheckedChangeListener(
        		new RadioGroup.OnCheckedChangeListener() {
            @Override
    		public void onCheckedChanged(RadioGroup group, int checkedId) { 
            	if( checkedId == R.id.rbInitial) {
            		view.setMode(0);
            	} else if( checkedId==R.id.rbAction) {
            		view.setMode(1);
            	} else {
            		view.setMode(2);
            	}
            }
        });

    	
    }
}