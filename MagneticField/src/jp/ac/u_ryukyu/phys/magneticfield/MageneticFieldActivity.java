package jp.ac.u_ryukyu.phys.magneticfield;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MageneticFieldActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magenetic_field);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_magenetic_field, menu);
        return true;
    }
}