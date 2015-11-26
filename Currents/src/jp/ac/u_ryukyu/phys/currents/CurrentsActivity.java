package jp.ac.u_ryukyu.phys.currents;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CurrentsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currents);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_currents, menu);
        return true;
    }
}
