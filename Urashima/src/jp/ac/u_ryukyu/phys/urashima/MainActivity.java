package jp.ac.u_ryukyu.phys.urashima;



import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class MainActivity extends Activity {
	UrashimaView view;
	TextView labelV;
	ToggleButton tbL;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        view=new UrashimaView(this);

        LinearLayout l=(LinearLayout)findViewById(R.id.ll);    	
        l.addView(view,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

        ((ToggleButton)findViewById(R.id.tbE)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){
        	@Override
        	public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
        		view.setEFlg(isChecked);
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
        labelV=(TextView)findViewById(R.id.tvV);
        ((SeekBar)findViewById(R.id.sbV)).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

        	@Override
        	public void onProgressChanged(SeekBar seekBar, int progress,
        			boolean fromUser) {
        		view.setV(0.01f*progress);
        		String s="地球の速度は光速の";
        		s += String.valueOf(progress);
        		s += "％";
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}
