package jp.ac.phys.u_ryukyu.superConductor;




import jp.ac.phys.u_ryukyu.superConductor.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.ProgressBar;

public class SuperConductorActivity extends Activity {
	SuperConductorView view;
	Button bt;
	TextView textv;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    	LinearLayout main=(LinearLayout)findViewById(R.id.ll);   
		
		
		view=new SuperConductorView(this);
		main.addView(view,new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,android.view.ViewGroup.LayoutParams.FILL_PARENT));
		textv=((TextView)findViewById(R.id.textView));
		bt=((Button)findViewById(R.id.btSuper));
		bt.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) { 
				if( view.toggleSuper() ) {
					textv.setText("超伝導状態");
					bt.setText("暖める");
				} else {
					textv.setText("常伝導状態");
					bt.setText("冷やす");
				} 
			}
		});	
	
    }
}