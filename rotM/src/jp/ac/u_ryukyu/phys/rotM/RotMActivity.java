package jp.ac.u_ryukyu.phys.rotM;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class RotMActivity extends Activity {
	RotMSurfaceView view3d;
	TextView labelM;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rot_m);
        LinearLayout main=(LinearLayout)findViewById(R.id.mainscreen);
        view3d=new RotMSurfaceView(this);
        main.addView(view3d,new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT));
    	findViewById(R.id.btResetScreen).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view3d.resetView();
			}
		});	
    	labelM=(TextView)findViewById(R.id.textDelM);
    	((SeekBar)findViewById(R.id.sbDelM)).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

    		@Override
    		public void onProgressChanged(SeekBar seekBar, int progress,
    				boolean fromUser) {
    			view3d.setDelM(progress-2);
    			String s="∂M/∂x=";
    			s += String.valueOf(progress-2);
    			labelM.setText(s);
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
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_rot_m, menu);
        return true;
    }
}
