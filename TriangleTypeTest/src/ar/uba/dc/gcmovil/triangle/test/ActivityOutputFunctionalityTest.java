package ar.uba.dc.gcmovil.triangle.test;

import junit.framework.TestCase;
import ar.uba.dc.gcmovil.triangle.ActivityOutput;

public class ActivityOutputFunctionalityTest extends TestCase {

	public void testTriangleResult(){
		
		ActivityOutput activityOutput = new ActivityOutput();
		
		assertEquals(ActivityOutput.NO_TRIANGLE, activityOutput.triangleResult(0,0,0));
		assertEquals(ActivityOutput.NO_TRIANGLE, activityOutput.triangleResult(1,0,0));
		assertEquals(ActivityOutput.NO_TRIANGLE, activityOutput.triangleResult(0,1,0));
		assertEquals(ActivityOutput.NO_TRIANGLE, activityOutput.triangleResult(0,0,1));
		assertEquals(ActivityOutput.NO_TRIANGLE, activityOutput.triangleResult(1,2,3));
		assertEquals(ActivityOutput.NO_TRIANGLE, activityOutput.triangleResult(-1,1,1));
		
		assertEquals(ActivityOutput.EQUILATERO, activityOutput.triangleResult(1,1,1));
		
		assertEquals(ActivityOutput.ISOSCELES, activityOutput.triangleResult(2,2,3));
		assertEquals(ActivityOutput.ISOSCELES, activityOutput.triangleResult(2,3,2));
		assertEquals(ActivityOutput.ISOSCELES, activityOutput.triangleResult(3,2,2));
		
		assertEquals(ActivityOutput.ESCALENO, activityOutput.triangleResult(2,4,5));
		assertEquals(ActivityOutput.ESCALENO, activityOutput.triangleResult(5,4,3));
		assertEquals(ActivityOutput.ESCALENO, activityOutput.triangleResult(4,5,2));
		
		
	}

}
