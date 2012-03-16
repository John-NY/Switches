package automation.client.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PandoraActivity extends Activity {

	Socket nsocket; //Network Socket
    InputStream nis; //Network Input Stream
    OutputStream nos; //Network Output Stream

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pandora_layout);
        
	    Toast.makeText(this, "This is the Pandora tab!", Toast.LENGTH_SHORT).show();
    }

	public void onbtnPlayClick(View view) {
		SendCharToServer("p");
		Toast.makeText(this, "play/pause", Toast.LENGTH_SHORT).show();
	}

	public void onbtnNextClick(View view) {
		SendCharToServer("n");
		Toast.makeText(this, "next song", Toast.LENGTH_SHORT).show();
	}

	public void onbtnVolDownClick(View view) {
		SendCharToServer("(");
	}

	public void onbtnVolUpClick(View view) {
		SendCharToServer(")");
	}
	
	public void onbtnChangeStationClick(View view) {
		Toast.makeText(this, "Ask Anna: Change Station [name]", Toast.LENGTH_SHORT).show();
		Toast.makeText(this, "Catalyst, Chill, Dubstep, Ether, House, Kick, Noise, Opera, Orbital, Trance", Toast.LENGTH_SHORT).show();
	}
	
	public void onbtnBanClick(View view) {
		SendCharToServer("-");
		Toast.makeText(this, "You will not hear this song again.", Toast.LENGTH_SHORT).show();
	}

	public void onbtnLoveClick(View view) {
		SendCharToServer("+");
		Toast.makeText(this, "Your love of this song is now documented.", Toast.LENGTH_SHORT).show();
	}

	public void onbtnQuitClick(View view) {
		SendCharToServer("q");
		Toast.makeText(this, "Server halted.  Pandora will not restart.", Toast.LENGTH_SHORT).show();
	}

	public void SendCharToServer(String sendchar)
	{
        try
        {
            SocketAddress saddr = new InetSocketAddress("192.168.0.55", 9140);
            nsocket = new Socket();
            nsocket.connect(saddr, 9140); //10 second connection timeout
            if (nsocket.isConnected()) {
            	String cmd = "\"pandora " + sendchar + "\"";
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