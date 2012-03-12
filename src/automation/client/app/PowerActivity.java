package automation.client.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import android.app.Activity;
import android.os.Bundle;
//import android.view.View;
import android.view.View;
import android.widget.Toast;

public class PowerActivity extends Activity {
    Socket nsocket; //Network Socket
    InputStream nis; //Network Input Stream
    OutputStream nos; //Network Output Stream

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.power_layout);
        
		Toast.makeText(this, "This is the power view", Toast.LENGTH_SHORT).show();
    }
    
	public void onButton1Click(View view) {
		Toast.makeText(this, "onButton1Click clicked!", Toast.LENGTH_SHORT).show();
        try
        {
            SocketAddress saddr = new InetSocketAddress("192.168.0.55", 9140);
            nsocket = new Socket();
            nsocket.connect(saddr, 9140); //10 second connection timeout
            if (nsocket.isConnected()) {
            	String cmd = "\"node3 off\"";
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
	
	public void onButton2Click(View view) {
		Toast.makeText(this, "onButton2Click clicked!", Toast.LENGTH_SHORT).show();
	}
}
