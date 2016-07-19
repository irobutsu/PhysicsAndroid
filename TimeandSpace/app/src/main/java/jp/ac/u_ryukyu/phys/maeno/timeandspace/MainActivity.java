package jp.ac.u_ryukyu.phys.maeno.timeandspace;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.apache.cordova.DroidGap;

public class MainActivity extends DroidGap {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.loadUrl("file:///android_asset/www/index.html");
    }
}
