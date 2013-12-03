package ar.uba.dc.gcmovil.gdc.test;

import junit.framework.TestCase;
import ar.uba.dc.gcmovil.gdc.ActivityOutput;

public class ActivityOutputFunctionalityTest extends TestCase {

	public void testGCDResult(){
		
		ActivityOutput activityOutput = new ActivityOutput();
		
		assertEquals(ActivityOutput.INVALID_NUMBERS, activityOutput.GCDResult(0,0));
		assertEquals(ActivityOutput.INVALID_NUMBERS, activityOutput.GCDResult(1,0));
		assertEquals(ActivityOutput.INVALID_NUMBERS, activityOutput.GCDResult(0,1));
		
		assertEquals("4", activityOutput.GCDResult(16,4));		
		assertEquals("3", activityOutput.GCDResult(6,9));
		assertEquals("15", activityOutput.GCDResult(15,60));
		assertEquals("15", activityOutput.GCDResult(60,15));
		assertEquals("5", activityOutput.GCDResult(15,65));
		assertEquals("4", activityOutput.GCDResult(1052,52));		
	}

}
