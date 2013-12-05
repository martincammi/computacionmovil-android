package ar.uba.dc.gcmovil.gdc.test;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import ar.uba.dc.gcmovil.gdc.ActivityInput;
import ar.uba.dc.gcmovil.gdc.ActivityOutput;
import ar.uba.dc.gcmovil.gdc.R;

//Ejecutando cada test en forma individual funcionan.
//Para ejecutar cada uno, cambiar el nombre de "aTest..." a "Test" de a uno por vez.

public class ActivityInputBehaviourTest extends ActivityInstrumentationTestCase2<ActivityInput> {

	private static int TIMEOUT = 10000;
	
	ActivityInput activityInput;
	
	public ActivityInputBehaviourTest() {
		super(ActivityInput.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
//		setActivityInitialTouchMode(false);
//		activityInput = getActivity();
	}
	
	public void testStartActivityInput() {
		
		setActivityInitialTouchMode(false);
		activityInput = getActivity();
		
		// Add a non-blocking monitor for ActivityB
	    ActivityMonitor monitor = 
	    		getInstrumentation().addMonitor(ActivityOutput.class.getName(), null, false);
	    			// arg0: The activity class this monitor is responsible for.
	    			// arg1: A canned result to return if the monitor is hit; can be null.
	    			// arg2: Controls whether the monitor should block the activity start 
	    			//			(returning its canned result)
	    
	    // Find button and click it
	    Button gcdButton = (Button) activityInput.findViewById(R.id.gdcId);	    
	    TouchUtils.clickView(this, gcdButton);
	    
	    // Wait for the monitor to be hit or the timeout to expire
	    Activity startedActivity = (Activity) monitor.waitForActivityWithTimeout(TIMEOUT);
	    
	    // Test whether the return activity is not null
	    assertNotNull("[TEST] Timeout Expired. Test is inconclusive.", startedActivity);

	    assertEquals("[TEST] ActivityB has not been started.", ActivityOutput.class, startedActivity.getClass());
	    
	    // Kill the started activity
	    startedActivity.finish();
	    
	    // Remove idle monitor
	    getInstrumentation().removeMonitor(monitor);
	    
	    Log.i("[TEST]", "Passed.");
	    
	}
	
	public void testActivityInputInvalidNumbers1() {
		
		setActivityInitialTouchMode(false);
		activityInput = getActivity();
		
		ActivityMonitor monitor = getInstrumentation().addMonitor(ActivityOutput.class.getName(), null, false);
	   
		//Invalido
		setNros("0","0");
		
	    Activity outputActivity = startActivityOutput(monitor);

	    //Begin test itself
	    TextView textView = (TextView) outputActivity.findViewById(R.id.result_text);
	    assertEquals(ActivityOutput.INVALID_NUMBERS, textView.getText());
	    
	    outputActivity.finish();    
	    
	    getInstrumentation().removeMonitor(monitor);
	    Log.i("[TEST]", "Passed.");
	    
	}
	
	public void testActivityInputInvalidNumbers2() {
		
		setActivityInitialTouchMode(false);
		activityInput = getActivity();
		
		ActivityMonitor monitor = getInstrumentation().addMonitor(ActivityOutput.class.getName(), null, false);
		
		setNros("0","1");
		
	    Activity outputActivity = startActivityOutput(monitor);

	    //Begin test itself
	    TextView textView = (TextView) outputActivity.findViewById(R.id.result_text);
	    assertEquals(ActivityOutput.INVALID_NUMBERS, textView.getText());
	    
	    outputActivity.finish();
	    activityInput.finish();
	    getInstrumentation().removeMonitor(monitor);
	    
	    Log.i("[TEST]", "Passed.");
	    
	}
	
	public void testActivityInputGCDCaso1() {
		
		setActivityInitialTouchMode(false);
		activityInput = getActivity();
		
		ActivityMonitor monitor2 = getInstrumentation().addMonitor(ActivityOutput.class.getName(), null, false);
	   
		setNros("16","4");
		
	    Activity outputActivity = startActivityOutput(monitor2);

	    //Begin test itself
	    TextView textView = (TextView) outputActivity.findViewById(R.id.result_text);
	    assertEquals("4", textView.getText());
	    
	    outputActivity.finish();
	    
	    getInstrumentation().removeMonitor(monitor2);
	    
	    Log.i("[TEST]", "Passed.");
	    
	}
	
	public void testActivityInputGCDCaso2() {
		
		setActivityInitialTouchMode(false);
		activityInput = getActivity();
		
		ActivityMonitor monitor = getInstrumentation().addMonitor(ActivityOutput.class.getName(), null, false);
	   
		setNros("6","9");
		
	    Activity outputActivity = startActivityOutput(monitor);

	    //Begin test itself
	    TextView textView = (TextView) outputActivity.findViewById(R.id.result_text);
	    assertEquals("3", textView.getText());
	    
	    outputActivity.finish();
	    
	    getInstrumentation().removeMonitor(monitor);
	    
	    Log.i("[TEST]", "Passed.");
	    
	}
		
	public void testActivityInputGCDCaso3() {
		
		setActivityInitialTouchMode(false);
		activityInput = getActivity();
		
		ActivityMonitor monitor = getInstrumentation().addMonitor(ActivityOutput.class.getName(), null, false);
	   
		setNros("1052","52");
		
	    Activity outputActivity = startActivityOutput(monitor);

	    //Begin test itself
	    TextView textView = (TextView) outputActivity.findViewById(R.id.result_text);
	    assertEquals("4", textView.getText());
	    
	    outputActivity.finish();
	    
	    getInstrumentation().removeMonitor(monitor);
	    
	    Log.i("[TEST]", "Passed.");
	    
	}
	
	
	private void setNros(final String nro1, final String nro2){
		
		activityInput.runOnUiThread(new Runnable() 
	    {
	        public void run() 
	        {
	        	((TextView)activityInput.findViewById(R.id.nro1)).setText(nro1);
	        	((TextView)activityInput.findViewById(R.id.nro2)).setText(nro2);	        	
	        }
	    });  
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private ActivityOutput startActivityOutput(ActivityMonitor monitor){
		
	    Button gcdButton = (Button) activityInput.findViewById(R.id.gdcId);	    
	    TouchUtils.clickView(this, gcdButton);
	    
	    Activity startedActivity = (Activity) monitor.waitForActivityWithTimeout(TIMEOUT);
	    
	    return (ActivityOutput) startedActivity; 
	}
	
	@Override
	protected void tearDown() {
		activityInput.finish();
	}
}
