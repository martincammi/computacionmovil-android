package ar.uba.dc.gcmovil.suma.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.util.Log;
import android.widget.Button;
import ar.uba.dc.gcmovil.suma.ActivityInput;
import ar.uba.dc.gcmovil.suma.ActivityOutput;
import ar.uba.dc.gcmovil.suma.R;

//Ejecutando cada test en forma individual funcionan.
@SuppressLint("NewApi")
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
	    Button enterButton = (Button) activityInput.findViewById(R.id.enterId);	    
	    TouchUtils.clickView(this, enterButton);
	    TouchUtils.clickView(this, enterButton);
	    
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
	
	
	@Override
	protected void tearDown() {
		activityInput.finish();
	}
}
