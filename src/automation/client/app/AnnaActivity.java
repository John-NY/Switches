package automation.client.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
 
/**
 * A very simple application to handle Voice Recognition intents
 * and display the results
 */
public class AnnaActivity extends Activity
{

	Socket nsocket; //Network Socket
    InputStream nis; //Network Input Stream
    OutputStream nos; //Network Output Stream

    private static final int REQUEST_CODE = 1234;
    private ListView wordsList;
 
    /**
     * Called with the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anna_layout);
 
        Button speakButton = (Button) findViewById(R.id.speakButton);
 
        wordsList = (ListView) findViewById(R.id.list);
 
        // Disable button if no recognition service is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0)
        {
            speakButton.setEnabled(false);
            speakButton.setText("Recognizer not present");
        }

        Toast.makeText(this, "This is the Anna tab!", Toast.LENGTH_SHORT).show();

    }
 
    /**
     * Handle the action of the button being clicked
     */
    public void speakButtonClicked(View v)
    {
        startVoiceRecognitionActivity();
    }
 
    /**
     * Fire an intent to start the voice recognition activity.
     */
    private void startVoiceRecognitionActivity()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice recognition Demo...");
        startActivityForResult(intent, REQUEST_CODE);
    }
 
    /**
     * Handle the results from the voice recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            // Populate the wordsList with the String values the recognition engine thought it heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            wordsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                    matches));
            wordsList.setOnItemClickListener(
            		new OnItemClickListener() {
            			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
            			{
            		    	lstSelectionMade(view); //Toast.makeText(getApplicationContext(), "An item has been selected", Toast.LENGTH_SHORT).show();
            			}
            		});
        }
    }
    
    public void lstSelectionMade(View view) {
    	SendChatToServer(view);
		String sSelect = "A selection \"" + ((TextView) view).getText() + "\" has been made!";
		Toast.makeText(this, sSelect, Toast.LENGTH_SHORT).show();
    }
    

	public void SendChatToServer(View view)
	{
        try
        {
            SocketAddress saddr = new InetSocketAddress("192.168.0.55", 9140);
            nsocket = new Socket();
            nsocket.connect(saddr, 9140); //10 second connection timeout
            if (nsocket.isConnected()) {
            	String cmd = "\"" + ((TextView) view).getText() + "\"";
                nis = nsocket.getInputStream();
                nos = nsocket.getOutputStream();
                nos.write(cmd.getBytes());
            } 
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
	}

}