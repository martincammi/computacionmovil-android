package ar.uba.dc.gcmovil.triangle;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import ar.uba.dc.gcmovil.triangle.R;

public class MyView extends View {

	private final Activity activity;
	private final MyModel model;
	
	private EditText side1;
	private EditText side2;
	private EditText side3;
	
	
	public MyView(Activity a, MyModel m) {
		super(a, null);
		
		activity = a;
		model = m;
		
        activity.setContentView(R.layout.activity_main);
        
        side1 = (EditText) activity.findViewById(R.id.side1);
        side2 = (EditText) activity.findViewById(R.id.side2);
        side3 = (EditText) activity.findViewById(R.id.side3);
	}
	
	public void setTriangle() {
		side1.setText(model.getSide1().toString());
		side2.setText(model.getSide2().toString());
		side3.setText(model.getSide3().toString());
	}

	public TextView getSide1() {
		return side1;
	}

	public void setSide1(EditText side1) {
		this.side1 = side1;
	}

	public TextView getSide2() {
		return side2;
	}

	public void setSide2(EditText side2) {
		this.side2 = side2;
	}

	public TextView getSide3() {
		return side3;
	}

	public void setSide3(EditText side3) {
		this.side3 = side3;
	}
	
	
	
}
