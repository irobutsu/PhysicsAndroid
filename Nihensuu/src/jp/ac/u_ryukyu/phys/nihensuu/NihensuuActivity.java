package jp.ac.u_ryukyu.phys.nihensuu;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;


public class NihensuuActivity extends Activity {
	Nihensuu3DView view3d;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nihensuu);
		LinearLayout main=(LinearLayout)findViewById(R.id.ll);   
		view3d=new Nihensuu3DView(this);
		main.addView(view3d,new LinearLayout.LayoutParams(50,50));

		findViewById(R.id.buttondownx2).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view3d.downx2();
				((TextView) findViewById(R.id.textViewx2)).setText(view3d.getCX2String());
			}
		});	
		findViewById(R.id.buttonupx2).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view3d.upx2();
				((TextView) findViewById(R.id.textViewx2)).setText(view3d.getCX2String());
			}
		});	
		findViewById(R.id.buttondownx1).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view3d.downx1();
				((TextView) findViewById(R.id.textViewx1)).setText(view3d.getCX1String());
			}
		});	
		findViewById(R.id.buttonupx1).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view3d.upx1();
				((TextView) findViewById(R.id.textViewx1)).setText(view3d.getCX1String());
			}
		});	
		findViewById(R.id.buttondowny2).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view3d.downy2();
				((TextView) findViewById(R.id.textViewy2)).setText(view3d.getCY2String());
			}
		});	
		findViewById(R.id.buttonupy2).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view3d.upy2();
				((TextView) findViewById(R.id.textViewy2)).setText(view3d.getCY2String());
			}
		});	
		findViewById(R.id.buttondowny1).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view3d.downy1();
				((TextView) findViewById(R.id.textViewy1)).setText(view3d.getCY1String());
			}
		});	
		findViewById(R.id.buttonupy1).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view3d.upy1();
				((TextView) findViewById(R.id.textViewy1)).setText(view3d.getCY1String());
			}
		});	
		findViewById(R.id.buttondownxy).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view3d.downxy();
				((TextView) findViewById(R.id.textViewxy)).setText(view3d.getCXYString());
			}
		});	
		findViewById(R.id.buttonupxy).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view3d.upxy();
				((TextView) findViewById(R.id.textViewxy)).setText(view3d.getCXYString());
			}
		});	
		findViewById(R.id.buttondown0).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view3d.down0();
				((TextView) findViewById(R.id.textView0)).setText(view3d.getC0String());
			}
		});	
		findViewById(R.id.buttonup0).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				view3d.up0();
				((TextView) findViewById(R.id.textView0)).setText(view3d.getC0String());
			}
		});	
	}
}
