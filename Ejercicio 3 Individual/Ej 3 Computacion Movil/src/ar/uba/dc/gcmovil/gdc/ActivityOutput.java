package ar.uba.dc.gcmovil.gdc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import ar.uba.dc.gcmovil.gdc.R;

public class ActivityOutput extends Activity {
	
	public static String INVALID_NUMBERS = "NUMEROS INVALIDOS";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_output);
		
		Intent intent = getIntent();
		MyModel myModel = (MyModel) intent.getSerializableExtra(MyController.MODEL);
		
		Integer nro1 = myModel.getNro1();
		Integer nro2 = myModel.getNro2();		
		
		TextView resultView = (TextView) findViewById(R.id.result_text);
						
		resultView.setText(GCDResult(nro1, nro2));
	}

	public String GCDResult(Integer nro1,Integer nro2) {
		
		if(validateNumbers(nro1, nro2)){
			return GCD(nro1, nro2);
		}
		
		return INVALID_NUMBERS;
	}
	
	//Check if numbers are greater tha 0
	private boolean validateNumbers(Integer nro1,Integer nro2){
		
		return (nro1 > 0) && 
				(nro2 > 0);
		
	}

	private String GCD(Integer nro1,Integer nro2){			
		return String.valueOf(gcd(nro1, nro2));
	}
	
	private static int gcd(Integer p, Integer q) {
		if (q == 0) {
			return p;
		}
		return gcd(q, p % q);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_output, menu);
		return true;
	}

}
