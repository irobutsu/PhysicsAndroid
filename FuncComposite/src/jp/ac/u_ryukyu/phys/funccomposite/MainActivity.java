package jp.ac.u_ryukyu.phys.funccomposite;



import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity {
	
	FuncComposite3DView view3d;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        view3d=new FuncComposite3DView(this);
    	LinearLayout l=(LinearLayout)findViewById(R.id.ll);    	
		l.addView(view3d,new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// アイテムを追加します
		adapter.add(getString(R.string.f_ax));
		adapter.add(getString(R.string.f_aoverx));
		adapter.add(getString(R.string.f_axtwo));
		adapter.add(getString(R.string.f_sqrtxovera));
		adapter.add(getString(R.string.f_sinax));
		adapter.add(getString(R.string.f_arcsinxovera));
		adapter.add(getString(R.string.f_expax));
		adapter.add(getString(R.string.f_logxovera));
		Spinner spinnerFx = (Spinner) findViewById(R.id.setFx);
		// アダプターを設定します
		spinnerFx.setAdapter(adapter);
		// スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
		spinnerFx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View vw,
					int position, long id) {
				Spinner s = (Spinner) parent;
				int k=s.getSelectedItemPosition();
				setFunctionFx(k); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		
		ArrayAdapter<String> adaptery = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		adaptery.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// アイテムを追加します
		adaptery.add(getString(R.string.g_ay));
		adaptery.add(getString(R.string.g_aovery));
		adaptery.add(getString(R.string.g_aytwo));
		adaptery.add(getString(R.string.g_sqrtyovera));
		adaptery.add(getString(R.string.g_sinay));
		adaptery.add(getString(R.string.g_arcsinyovera));
		adaptery.add(getString(R.string.g_expay));
		adaptery.add(getString(R.string.g_logyovera));
		Spinner spinnerGy = (Spinner) findViewById(R.id.setGy);
		// アダプターを設定します
		spinnerGy.setAdapter(adaptery);
		// スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
		spinnerGy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View vw,
					int position, long id) {
				Spinner s = (Spinner) parent;
				int k=s.getSelectedItemPosition();
				setFunctionGy(k); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		findViewById(R.id.buttonUpA).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				a += 0.1;
				if (a > 5) {
					a = 5;
				}
				changeA();
			}
		});		
		findViewById(R.id.buttonDownA).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				a -= 0.1;
				if (a < -5) {
					a = -5;
				}
				changeA();
			}
		});		
		
		findViewById(R.id.buttonUpB).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				b += 0.1;
				if (b > 5) {
					b = 5;
				}
				changeB();
			}
		});		
		findViewById(R.id.buttonDownB).setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				b -= 0.1;
				if (b < -5) {
					b = -5;
				}
				changeB();
			}
		});		
		view3d.start();
    }
    int funcFx=0;
	int funcGy=0;
	void setFunctionFx(int f) {
		funcFx=f;
		((TextView)findViewById(R.id.TextViewFx)).setText("y="+FuncString(funcFx,String.valueOf(a),"x"));
		view3d.setfnoF(funcFx,a);
		setFunctionFG();
	}
	void setFunctionGy(int f) {
		funcGy=f;
		((TextView)findViewById(R.id.TextViewGy)).setText("z="+FuncString(funcGy,String.valueOf(b),"y"));
		view3d.setfnoG(funcGy,b);
		setFunctionFG();
	}
	void setFunctionFG() {
		((TextView)findViewById(R.id.textViewGF)).setText("z="+FuncString(funcGy,String.valueOf(b),"("+FuncString(funcFx,String.valueOf(a),"x")+")"));
	}
    float a=1;
    float b=1;
   
    void changeA() {
    	a *=10;
    	a=Math.round(a);
    	a = a/ 10;
    	String s="a="+String.valueOf(a);
    	((TextView)findViewById(R.id.TextViewA)).setText(s);
    	setFunctionFx(funcFx);
    }
    void changeB() {
    	b *=10;
    	b=Math.round(b);
    	b = b/ 10;
    	String s="b="+String.valueOf(b);
    	((TextView)findViewById(R.id.TextViewB)).setText(s);
    	setFunctionGy(funcGy);
    }
    String FuncString(int funcNo,String aa,String x) {
    	switch( funcNo ) {
    	case 0:
    		return timesString(aa,x);
    	case 1:
    		return divString(aa,x);
    	case 2:
    		return timesString(aa,x)+"^2";
    	case 3:
    		return "√(|"+divString(x,aa)+ "|)";
    	case 4:
    		return "sin("+timesString(aa,x)+")";
    	case 5:
    		return divString("arcsin("+x+")",aa);
    	case 6:
    		return "exp("+timesString(aa,x)+")";
    	case 7:
    		return "log("+divString("|"+x+"|", aa)+")";
    	}
    	return "";
    }
    String timesString(String as,String xs) {   	
    	if( as.equals("1.0")) {
    		return xs;
    	} else if( as.equals("-1.0")) {
    		return "-"+xs;
    	}
    	return as+"*"+xs;
    }
    String divString(String as,String xs) {
    	if( xs.equals("1.0")) {
    		return as;
    	} else if( xs.equals("-1.0")) {
    		return "-"+as;
    	}
    	return as + "/" +xs;
    }
    
    
}
