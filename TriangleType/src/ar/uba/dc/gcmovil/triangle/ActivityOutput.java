package ar.uba.dc.gcmovil.triangle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import ar.uba.dc.gcmovil.triangle.R;

public class ActivityOutput extends Activity {
	
	public static String ISOSCELES = "ISOSCELES";
	public static String ESCALENO = "ESCALENO";
	public static String EQUILATERO = "EQUILATERO";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_output);
		
		Intent intent = getIntent();
		MyModel myModel = (MyModel) intent.getSerializableExtra(MyController.MODEL);
		
		TextView resultView = (TextView) findViewById(R.id.result_text);
		
		if(validateTriangle(myModel.getSide1(), myModel.getSide2(), myModel.getSide3())){
			resultView.setText("Su triángulo es: " + triangleType(myModel.getSide1(), myModel.getSide2(), myModel.getSide3()));
		}else{
			resultView.setText("Los datos ingresados no corresponden a un triángulo válido");
		}
	}
	
	private boolean validateTriangle(Integer side1,Integer side2, Integer side3){
		
		return (side1 + side2 > side3) && 
				(side1 + side3 > side2) && 
				(side2 + side3 > side1);
		
	}

	private String triangleType(Integer side1,Integer side2, Integer side3){
		
		if(side1 == side2 && side2 == side3){
			return EQUILATERO;
		}else if(side1 == side2 || side1 == side3 || side2 == side3){
			return ISOSCELES;
		}
		
		return ESCALENO;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_output, menu);
		return true;
	}

}
