package jp.ac.u_ryukyu.phys.elemagwave;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	ElemagwaveSurfaceView view3d;
	CheckBox cbE,cbB;
	ToggleButton tbS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout main=(LinearLayout)findViewById(R.id.mainscreen);
        view3d=new ElemagwaveSurfaceView(this);
        main.addView(view3d,new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT));
        findViewById(R.id.btResetScreen).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view3d.resetView();
			}
		});	
        tbS=(ToggleButton)findViewById(R.id.tbStop);
	  	tbS.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
	  		@Override
	  		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
	  			if( isChecked ) {
	  				view3d.timerStart();
	  			} else {
	  				view3d.timerStop();
	  			}
	  		}
	  	});
        cbE=(CheckBox)findViewById(R.id.cbE);
		cbE.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) { 
				
					view3d.setrotEFlg(((CheckBox)v).isChecked());
			
			}
	     	});
		  cbB=(CheckBox)findViewById(R.id.cbB);
			cbB.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) { 
					
						view3d.setrotBFlg(((CheckBox)v).isChecked());
				
				}
		     	});
	    	
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
