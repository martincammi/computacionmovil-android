package ar.uba.dc.gcmovil.triangle.test;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import ar.uba.dc.gcmovil.triangle.ActivityInput;
import ar.uba.dc.gcmovil.triangle.R;

public class ActivityInputLayoutTest extends ActivityInstrumentationTestCase2<ActivityInput> {

	private ActivityInput activity;
	
	public ActivityInputLayoutTest() {
		super(ActivityInput.class);
	}
	
	@Override 
	protected void setUp() throws Exception {  
		super.setUp();
		setActivityInitialTouchMode(false);
		activity = getActivity(); 
		
//		Intent intent = new Intent(getInstrumentation().getTargetContext(), ActivityInput.class); 
//		startActivity(intent, null, null); 
//		activity = getActivity();
	} 

	public void testPosition() {

		// Get button and message
		final View triangleView = activity.findViewById(R.id.triangleId);
		final View side1View = activity.findViewById(R.id.side1);
		final View side2View = activity.findViewById(R.id.side2);
		final View side3View = activity.findViewById(R.id.side3);

		// Get positions on screen
		int[] location_side1 = new int[2];
		int[] location_side2 = new int[2];
		int[] location_side3 = new int[2];
		int[] location_triangle = new int[2];

		side1View.getLocationOnScreen(location_side1);
		side2View.getLocationOnScreen(location_side2);
		side3View.getLocationOnScreen(location_side3);
		triangleView.getLocationOnScreen(location_triangle);
		
		// Get button's height
		int heightSide1 = side1View.getHeight();
		int heightSide2 = side2View.getHeight();
		int heightSide3 = side3View.getHeight();

		assertTrue("[TEST]. Side2 is NOT below Side1.", location_side2[1] >= location_side1[1] + heightSide1);
		assertTrue("[TEST]. Side3 is NOT below Side2.", location_side3[1] >= location_side2[1] + heightSide2);
		assertTrue("[TEST]. Button triangle is NOT below Side3.", location_triangle[1] >= location_side3[1] + heightSide3);
		
		Log.i("[TEST] testPosition Passed.", "Layouts OK");		
	}
	
	
}
