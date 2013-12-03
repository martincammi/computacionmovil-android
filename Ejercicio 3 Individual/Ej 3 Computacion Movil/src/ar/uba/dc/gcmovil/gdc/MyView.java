package ar.uba.dc.gcmovil.gdc;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import ar.uba.dc.gcmovil.gdc.R;

public class MyView extends View {

	private final Activity activity;
	private final MyModel model;
	
	private EditText nro1;
	private EditText nro2;

	
	public MyView(Activity a, MyModel m) {
		super(a, null);
		
		activity = a;
		model = m;
		
        activity.setContentView(R.layout.activity_main);
        
        nro1 = (EditText) activity.findViewById(R.id.nro1);
        nro2 = (EditText) activity.findViewById(R.id.nro2);
	}
	
	public void setGDC() {
		nro1.setText(model.getNro1().toString());
		nro2.setText(model.getNro2().toString());
	}

	public TextView getNro1() {
		return nro1;
	}

	public void setNro1(EditText nro1) {
		this.nro1 = nro1;
	}

	public TextView getNro2() {
		return nro2;
	}

	public void setNro2(EditText nro2) {
		this.nro2 = nro2;
	}


	
}
