package ar.uba.dc.gcmovil.suma.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import ar.uba.dc.gcmovil.suma.ActivityOutput;
import ar.uba.dc.gcmovil.suma.R;
import ar.uba.dc.gcmovil.suma.ActivityInput;

@SuppressLint("NewApi")
public class ActivityOutputFunctionalityTest extends ActivityInstrumentationTestCase2<ActivityInput> {

	public ActivityOutputFunctionalityTest() {
		super(ActivityInput.class);
	}

	ActivityInput activityInput;
	private static int TIMEOUT = 10000;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	

	
	public void testSumaResult(){
		
		setActivityInitialTouchMode(false);
		activityInput = getActivity();
		
		ActivityMonitor monitor = getInstrumentation().addMonitor(ActivityOutput.class.getName(), null, false);
	   
		//suma 21
		
		//al agregar un valor de mas de 2 digitos el valor que asocie tiene que ser
		// igual a los dos primeros digitos ingresados y los demas valores descartados
		// ya que no admite mas de dos digitos
		setValue("160");
	    
	    TextView txtValue = (TextView) activityInput.findViewById(R.id.value);	    
	    
	    //Verifico que la longitud sea de 2
	    assertEquals("16", txtValue.getText());

		
		Button sumaButton = (Button) activityInput.findViewById(R.id.enterId);	    

	    
	    TouchUtils.clickView(this, sumaButton);
		setValue("5");
		
	    TouchUtils.clickView(this, sumaButton);
	    
	    Activity outputActivity = (Activity) monitor.waitForActivityWithTimeout(TIMEOUT);
	    

	    //Begin test itself
	    TextView textView = (TextView) outputActivity.findViewById(R.id.result_text);
	    //Verifica que el resultado sea correcto
	    assertEquals("21", textView.getText());
	    
	    outputActivity.finish();
	 

	    Log.i("[TEST]", "Passed.");

	}
	private void setValue(final String value){
		
		activityInput.runOnUiThread(new Runnable() 
	    {
	        public void run() 
	        {
	        	((TextView)activityInput.findViewById(R.id.value)).setText(value);
	        }
	    });  
	}
	
	@Override
	protected void tearDown() {
		activityInput.finish();
	}

}
