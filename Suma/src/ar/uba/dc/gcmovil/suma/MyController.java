package ar.uba.dc.gcmovil.suma;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class MyController implements OnClickListener {
	
	public static String MODEL = " ar.uba.dc.gcmovil.suma.MyController.model";
	
	private final MyView myView;
	private final MyModel myModel;
	private final Activity myActivity;
	
    public MyController(MyModel m, MyView v, Activity activity) {
    	myModel = m;
    	myView = v;
    	myActivity = activity;
    }
    
	public void onClick(View v) {
		
		if (!myModel.getIsSetValue1())
		{
			myModel.setValue1(formatInteger(myView.getValue1().getText().toString()));
			myView.setButtonText("Enter 2");
		}
		else
		{
			myModel.setValue2(formatInteger(myView.getValue2().getText().toString()));
			
			Intent intent = new Intent(myActivity, ActivityOutput.class);
			intent.putExtra(MODEL, myModel);
			myActivity.startActivity(intent);
		}
	}
	
	private Integer formatInteger(String value){
		
		if(value == null || "".equals(value)){
			return Integer.valueOf(0);
		}
		
		return Integer.parseInt(value);
	}
	
	
}
