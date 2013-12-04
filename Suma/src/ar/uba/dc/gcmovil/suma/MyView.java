package ar.uba.dc.gcmovil.suma;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ar.uba.dc.gcmovil.suma.R;

public class MyView extends View {

	private final Activity activity;
	private final MyModel model;
	
	private EditText value1;
	private EditText value2;
	
	
	public MyView(Activity a, MyModel m) {
		super(a, null);
		
		activity = a;
		model = m;
		
        activity.setContentView(R.layout.activity_main);
        
        value1 = (EditText) activity.findViewById(R.id.value);
        value2 = value1;
	}
	
	public TextView getValue1() {
		return value1;
	}

	public void setButtonText(String text)
	{
		Button enterX = (Button) activity.findViewById(R.id.enterId);
		enterX.setText(text);
	}
	
	public void setValue1(EditText value1) {
		this.value1 = value1;
	}

	public TextView getValue2() {
		return value2;
	}

	public void setValue2(EditText value2) {
		this.value2 = value2;
	}
	
	
}
