package jp.ac.u_ryukyu.phys.Divergence;






import jp.ac.u_ryukyu.phys.Gauss.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class GaussActivity extends Activity {
	TextView labelEx0,labelExx,labelExy;
	TextView labelEy0,labelEyx,labelEyy;
	GaussView view;
	GaussGraphView graphview;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);	
        labelEx0=((TextView)findViewById(R.id.Ex0));
		findViewById(R.id.Ex0Up).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.upEx0();
				String s = String.valueOf(view.getEx0());
				labelEx0.setText(s);
				view.invalidate();
			}
		});		
		findViewById(R.id.Ex0Down).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.downEx0();
				String s = String.valueOf(view.getEx0());
				labelEx0.setText(s);
				view.invalidate();
			}
		});		
		labelExx=((TextView)findViewById(R.id.Exx));
		findViewById(R.id.ExxUp).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.upExx();
				String s = String.valueOf(view.getExx());
				labelExx.setText(s);
				view.invalidate();
			}
		});		
		findViewById(R.id.ExxDown).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.downExx();
				String s = String.valueOf(view.getExx());
				labelExx.setText(s);
				view.invalidate();
			}
		});		
		
		labelExy=((TextView)findViewById(R.id.Exy));
		findViewById(R.id.ExyUp).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.upExy();
				String s = String.valueOf(view.getExy());
				labelExy.setText(s);
				view.invalidate();
			}
		});		
		findViewById(R.id.ExyDown).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.downExy();
				String s = String.valueOf(view.getExy());
				labelExy.setText(s);
				view.invalidate();
			}
		});		
		
		labelEy0=((TextView)findViewById(R.id.Ey0));
		findViewById(R.id.Ey0Up).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.upEy0();
				String s = String.valueOf(view.getEy0());
				labelEy0.setText(s);
				view.invalidate();
			}
		});		
		findViewById(R.id.Ey0Down).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.downEy0();
				String s = String.valueOf(view.getEy0());
				labelEy0.setText(s);
				view.invalidate();
			}
		});		
		labelEyx=((TextView)findViewById(R.id.Eyx));
		findViewById(R.id.EyxUp).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.upEyx();
				String s = String.valueOf(view.getEyx());
				labelEyx.setText(s);
				view.invalidate();
			}
		});		
		findViewById(R.id.EyxDown).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.downEyx();
				String s = String.valueOf(view.getEyx());
				labelEyx.setText(s);
				view.invalidate();
			}
		});		
		
		labelEyy=((TextView)findViewById(R.id.Eyy));
		findViewById(R.id.EyyUp).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.upEyy();
				String s = String.valueOf(view.getEyy());
				labelEyy.setText(s);
				view.invalidate();
			}
		});		
		findViewById(R.id.EyyDown).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.downEyy();
				String s = String.valueOf(view.getEyy());
				labelEyy.setText(s);
				view.invalidate();
			}
		});		
		findViewById(R.id.btErase).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view.allErase();
			}
		});	
		((SeekBar)findViewById(R.id.sbB)).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				view.setbairitsu(progress);
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
		graphview =new GaussGraphView(this);
		view =new GaussView(this,(TextView)findViewById(R.id.divE),graphview);
    	LinearLayout l=(LinearLayout)findViewById(R.id.ll);    	
		l.addView(graphview,new LinearLayout.LayoutParams(100, 100));
		l.addView(view,new LinearLayout.LayoutParams(100,100));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// アイテムを追加します
		adapter.add(getString(R.string.move_frame));
		adapter.add(getString(R.string.div_plus));
		adapter.add(getString(R.string.div_minus));
		Spinner spinner = (Spinner) findViewById(R.id.setMode);
		// アダプターを設定します
		spinner.setAdapter(adapter);
		// スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View vw,
					int position, long id) {
				Spinner s = (Spinner) parent;
				view.clickMode(s.getSelectedItemPosition() );
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
//	    ((RadioGroup) findViewById(R.id.rg)).setOnCheckedChangeListener(
//	    		new RadioGroup.OnCheckedChangeListener() {
//	        @Override
//			public void onCheckedChanged(RadioGroup group, int checkedId) { 
//	        	switch( checkedId) {
//	        	case R.id.frame:
//	        		view.clickMode(0);
//	        		break;
//	        	case R.id.divSp:
//	        		view.clickMode(1);
//	        		break;
//	        	case R.id.rotSp:
//	        		view.clickMode(2);
//	        		break;
//	        	case R.id.divSm:
//	        		view.clickMode(3);
//	        		break;
//	        	case R.id.rotSm:
//	        		view.clickMode(4);
//	        	}
//	        }
//	    });
    }
}
