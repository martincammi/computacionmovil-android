package ar.uba.dc.gcmovil.suma;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import ar.uba.dc.gcmovil.suma.R;

public class ActivityOutput extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_output);
		
		Intent intent = getIntent();
		MyModel myModel = (MyModel) intent.getSerializableExtra(MyController.MODEL);
		
		Integer value1 = myModel.getValue1();
		Integer value2 = myModel.getValue2();
		
		TextView resultView = (TextView) findViewById(R.id.result_text);
		
		
		String resultado = ((Integer)(value1 + value2)).toString();
		resultView.setText(resultado);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_output, menu);
		return true;
	}

}
