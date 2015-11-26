package jp.ac.u_ryukyu.phys.bhthru;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class BHthruActivity extends Activity {
	TextView labelS,labelD,labelL,labelAngle;
	BHthruView view;
	BHthruSubView subview;
	protected Bitmap bitmap;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    	view=new BHthruView(this);
    	subview=new BHthruSubView(this,view);
    	
    	LinearLayout l=(LinearLayout)findViewById(R.id.main);  
    	l.addView(view,new LinearLayout.LayoutParams(100,100));
    	l.addView(subview,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
    	
    	labelAngle=(TextView)findViewById(R.id.textViewAngle);
		((SeekBar)findViewById(R.id.sbA)).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				
				subview.setAngle(progress);
				String s="角度 ";
				s += String.valueOf(progress);
				labelAngle.setText(s);
				subview.postInvalidate();
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

    	labelS=(TextView)findViewById(R.id.textViewS);
		((SeekBar)findViewById(R.id.sbS)).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				view.setAA(0.1f*(1+progress));
				subview.setAA(0.1f*(1+progress));
				String s="シュワルツシュルト半径 ";
				s += String.valueOf(0.1f*(1+progress));
				labelS.setText(s);
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

    	labelD=(TextView)findViewById(R.id.textViewD);
		((SeekBar)findViewById(R.id.sbD)).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				view.setDD((3+progress));
				subview.setDD((3+progress));
				String s="視点までの距離 ";
				s += String.valueOf((3+progress));
				labelD.setText(s);
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

    	labelL=(TextView)findViewById(R.id.textViewL);
		((SeekBar)findViewById(R.id.sbL)).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				view.setLL((2+progress));
				subview.setLL((2+progress));
				String s="壁までの距離 ";
				s += String.valueOf((2+progress));
				labelL.setText(s);
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
		Resources r = getResources();
		bitmap=BitmapFactory.decodeResource(r, R.drawable.dosei);
		view.setBitmap(bitmap);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
