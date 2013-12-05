package ar.uba.dc.gcmovil.triangle.test;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ar.uba.dc.gcmovil.triangle.ActivityInput;
import ar.uba.dc.gcmovil.triangle.ActivityOutput;
import ar.uba.dc.gcmovil.triangle.R;

//Ejecutando cada test en forma individual funcionan.
public class ActivityInputBehaviourTest extends ActivityInstrumentationTestCase2<ActivityInput> {

	private static int TIMEOUT = 10000;
	
	ActivityInput activityInput;
	
	public ActivityInputBehaviourTest() {
		super(ActivityInput.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		activityInput = getActivity();
	}
	
	public void testStartActivityInput() {
		
		// Add a non-blocking monitor for ActivityB
	    ActivityMonitor monitor = 
	    		getInstrumentation().addMonitor(ActivityOutput.class.getName(), null, false);
	    			// arg0: The activity class this monitor is responsible for.
	    			// arg1: A canned result to return if the monitor is hit; can be null.
	    			// arg2: Controls whether the monitor should block the activity start 
	    			//			(returning its canned result)
	    
	    // Find button and click it
	    Button triangleButton = (Button) activityInput.findViewById(R.id.triangleId);	    
	    TouchUtils.clickView(this, triangleButton);
	    
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
	
	public void atestActivityInputAllTriangle() {
		
		//setActivityInitialTouchMode(false);
		//activityInput = getActivity();
		
		ActivityMonitor monitor = getInstrumentation().addMonitor(ActivityOutput.class.getName(), null, false);
	   
		//Invalido
		setSides("0","0","0");
		
	    Activity outputActivity = startActivityOutput(monitor);

	    //Begin test itself
	    TextView textView = (TextView) outputActivity.findViewById(R.id.result_text);
	    assertEquals(ActivityOutput.NO_TRIANGLE, textView.getText());
	    activityInput.findViewById(R.id.side1);
	    outputActivity.finish();
	 

	    //Equilatero
	    
	    setActivityInitialTouchMode(false);
		activityInput = getActivity();
		
		setSides("1","1","1");
		
	    outputActivity = startActivityOutput(monitor);

	    //Begin test itself
	    textView = (TextView) outputActivity.findViewById(R.id.result_text);
	    assertEquals(ActivityOutput.EQUILATERO, textView.getText());
	    
	    outputActivity.finish();

	    //Isoceles
	    //Escaleno
	    
	    
	    getInstrumentation().removeMonitor(monitor);
	    Log.i("[TEST]", "Passed.");
	    
	}
	
	public void testActivityInputInvalidTriangle() {
		
		setActivityInitialTouchMode(false);
		activityInput = getActivity();
		
		ActivityMonitor monitor = getInstrumentation().addMonitor(ActivityOutput.class.getName(), null, false);
		
		setSides("1","0","1");
		
	    Activity outputActivity = startActivityOutput(monitor);

	    //Begin test itself
	    TextView textView = (TextView) outputActivity.findViewById(R.id.result_text);
	    assertEquals(ActivityOutput.NO_TRIANGLE, textView.getText());
	    
	    outputActivity.finish();
	    activityInput.finish();
	    getInstrumentation().removeMonitor(monitor);
	    
	    Log.i("[TEST]", "Passed.");
	    
	}
	
	public void testActivityInputTrianguloEquilatero() {
		
		setActivityInitialTouchMode(false);
		activityInput = getActivity();
		
		ActivityMonitor monitor2 = getInstrumentation().addMonitor(ActivityOutput.class.getName(), null, false);
	   
		setSides("1","1","1");
		
	    Activity outputActivity = startActivityOutput(monitor2);

	    //Begin test itself
	    TextView textView = (TextView) outputActivity.findViewById(R.id.result_text);
	    assertEquals(ActivityOutput.EQUILATERO, textView.getText());
	    
	    outputActivity.finish();
	    
	    getInstrumentation().removeMonitor(monitor2);
	    
	    Log.i("[TEST]", "Passed.");
	    
	}
	
	public void testActivityInputTrianguloIsoceles() {
		
		ActivityMonitor monitor = getInstrumentation().addMonitor(ActivityOutput.class.getName(), null, false);
	   
		setSides("2","2","3");
		
	    Activity outputActivity = startActivityOutput(monitor);

	    //Begin test itself
	    TextView textView = (TextView) outputActivity.findViewById(R.id.result_text);
	    assertEquals(ActivityOutput.ISOSCELES, textView.getText());
	    
	    outputActivity.finish();
	    
	    getInstrumentation().removeMonitor(monitor);
	    
	    Log.i("[TEST]", "Passed.");
	    
	}
		
	public void testActivityInputTrianguloEscaleno() {
		
		ActivityMonitor monitor = getInstrumentation().addMonitor(ActivityOutput.class.getName(), null, false);
	   
		setSides("2","4","5");
		
	    Activity outputActivity = startActivityOutput(monitor);

	    //Begin test itself
	    TextView textView = (TextView) outputActivity.findViewById(R.id.result_text);
	    assertEquals(ActivityOutput.ESCALENO, textView.getText());
	    
	    outputActivity.finish();
	    
	    getInstrumentation().removeMonitor(monitor);
	    
	    Log.i("[TEST]", "Passed.");
	    
	}
	
	//Este es el problema de porque no corren bien los tests:
	//Runs the specified action on the UI thread. If the current thread is the UI thread, 
	//then the action is executed immediately. If the current thread is not the UI thread, 
	//the action is posted to the event queue of the UI thread.
	//Esto es el porque agregamos el Sleep
	private void setSides(final String side1, final String side2, final String side3) {
		
		activityInput.runOnUiThread(new Runnable() 
	    {
	        public void run() 
	        {
	        	((EditText)activityInput.findViewById(R.id.side1)).setText(side1);
	        	((EditText)activityInput.findViewById(R.id.side2)).setText(side2);
	        	((EditText)activityInput.findViewById(R.id.side3)).setText(side3);
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
		
	    Button triangleButton = (Button) activityInput.findViewById(R.id.triangleId);	    
	    TouchUtils.clickView(this, triangleButton);
	    
	    Activity startedActivity = (Activity) monitor.waitForActivityWithTimeout(TIMEOUT);
	    
	    return (ActivityOutput) startedActivity; 
	}
	
	@Override
	protected void tearDown() {
		activityInput.finish();
	}
}
