package ar.uba.dc.gcmovil.suma;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import ar.uba.dc.gcmovil.suma.R;

public class ActivityInput extends Activity {

	private MyModel model;
	public MyModel getModel()
	{
		return model;
	}
	private MyView view;
	private MyController controller;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Create MVC
        model = new MyModel();
        view = new MyView(this, model);
        controller = new MyController(model, view, this);
        
        // Bind controller to click
        findViewById(R.id.enterId).setOnClickListener(controller);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
}
