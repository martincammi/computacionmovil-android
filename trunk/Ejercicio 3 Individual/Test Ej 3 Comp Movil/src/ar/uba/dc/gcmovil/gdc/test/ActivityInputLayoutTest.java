package ar.uba.dc.gcmovil.gdc.test;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import ar.uba.dc.gcmovil.gdc.ActivityInput;
import ar.uba.dc.gcmovil.gdc.R;

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
		final View gcdView = activity.findViewById(R.id.gdcId);
		final View nro1View = activity.findViewById(R.id.nro1);
		final View nro2View = activity.findViewById(R.id.nro2);		

		// Get positions on screen
		int[] location_nro1 = new int[2];
		int[] location_nro2 = new int[2];		
		int[] location_gdc = new int[2];

		nro1View.getLocationOnScreen(location_nro1);
		nro2View.getLocationOnScreen(location_nro2);		
		gcdView.getLocationOnScreen(location_gdc);
		
		// Get button's height and padding
		int widthNro1 = nro1View.getWidth();
		int heightNro1 = nro1View.getHeight();
		int paddingLeftGDC = gcdView.getPaddingLeft();				
		int paddingRightGDC = gcdView.getPaddingRight();

		assertTrue("[TEST]. Nro1 is NOT left Nro2.", location_nro2[0] >= location_nro1[0] + widthNro1);
		assertTrue("[TEST]. Button GCD is NOT below the numbers.", location_gdc[1] >= location_nro1[1] + heightNro1);
		assertTrue("[TEST]. Button GCD is NOT Centered.", paddingLeftGDC == paddingRightGDC);
		
		Log.i("[TEST] testPosition Passed.", "Layouts OK");		
	}
	
	
}
