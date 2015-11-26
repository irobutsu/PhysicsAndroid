package jp.ac.u_ryukyu.phys.maeno.induction;

//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

    private InductionView view;
    private Induction3DView view3d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout main = (LinearLayout) findViewById(R.id.mainscreen);


        view3d = new Induction3DView(this);

        main.addView(view3d, new LinearLayout.LayoutParams(10, 10));

        view = new InductionView(this, view3d);
        main.addView(view, new LinearLayout.LayoutParams(10, 10));
        findViewById(R.id.btResetScreen).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                view3d.resetView();
            }
        });
        ((ToggleButton) findViewById(R.id.tbStop)).setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    view.start();
                } else {
                    view.stop();
                }
            }
        });
    }
}
