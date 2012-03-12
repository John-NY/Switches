package automation.client.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PandoraActivity extends Activity {
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pandora_layout);
        
	    Toast.makeText(this, "This is the Pandora tab!", Toast.LENGTH_SHORT).show();
    }
    
	public void onbtnPlayClick(View view) {
		Toast.makeText(this, "onbtnPlayClick clicked!", Toast.LENGTH_SHORT).show();
	}
	
	public void onbtnQuitClick(View view) {
		Toast.makeText(this, "onbtnQuitClick clicked!", Toast.LENGTH_SHORT).show();
	}
}