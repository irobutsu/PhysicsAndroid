package jp.ac.u_ryukyu.phys.RelativisiticTrain;

import android.app.Activity;
import android.graphics.Bitmap;
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

public class RelativisticTrainActivity extends Activity {

   
        RelativisticTrainView view;
    	TextView labelV;
    	Bitmap bitmap;
    	/** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main);
            
            view = new RelativisticTrainView(this);
        
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
    	((ToggleButton)findViewById(R.id.tbT)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
      		@Override
      		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
      			view.changeshiftFlg(isChecked);
      		}
      	});
    	labelV=(TextView)findViewById(R.id.tvV);
    	((SeekBar)findViewById(R.id.sbV)).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

    		@Override
    		public void onProgressChanged(SeekBar seekBar, int progress,
    				boolean fromUser) {
    			view.setV(0.01f*progress);
    			String s=getString(R.string.v_equal);
    			s += String.valueOf(progress);
    			s += getString(R.string.percent);
    			labelV.setText(s);
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
    	
    	findViewById(R.id.btRestart).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				view.restart();
			}
		});
    	   }
}