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
import android.widget.ImageButton;
import android.widget.Toast;

public class PowerActivity extends Activity {
	Socket nsocket; //Network Socket
    InputStream nis; //Network Input Stream
    OutputStream nos; //Network Output Stream

    private Boolean btnState1 = false;
    private Boolean btnState2 = false;
    private Boolean btnState3 = false;
    private Boolean btnState4 = false;

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.power_layout);
        
		Toast.makeText(this, "This is the power view", Toast.LENGTH_SHORT).show();
    }
    
	public void onButton1Click(View view) {
		ImageButton btnSw1 = (ImageButton)findViewById(R.id.btnSw1);
		btnState1 = SendButtonToggleToServer(btnSw1,btnState1,"printer");
	}
	
	public void onButton2Click(View view) {
		ImageButton btnSw2 = (ImageButton)findViewById(R.id.btnSw2);
		btnState2 = SendButtonToggleToServer(btnSw2,btnState2,"node3");
	}

	public void onButton4Click(View view) {
		ImageButton btnSw4 = (ImageButton)findViewById(R.id.btnSw4);
		btnState4 = SendButtonToggleToServer(btnSw4,btnState4,"A3");
	}
	
	public void onButton3Click(View view) {
		ImageButton btnSw3 = (ImageButton)findViewById(R.id.btnSw3);
		btnState3 = SendButtonToggleToServer(btnSw3,btnState3,"A2");
	}
	
	public Boolean SendButtonToggleToServer(ImageButton btnSw, Boolean btnState, String strNode)
	{
		String strState; 
		if (btnState) { 
			strState = "off"; 
		} else { 
			strState = "on"; 
		}
        try
        {
            SocketAddress saddr = new InetSocketAddress("192.168.0.55", 9001);
            nsocket = new Socket();
            nsocket.connect(saddr, 9001); //10 second connection timeout
            if (nsocket.isConnected()) {
            	String cmd = "\"" + strNode + " " + strState + "\"";
                nis = nsocket.getInputStream();
                nos = nsocket.getOutputStream();
                nos.write(cmd.getBytes());
        		btnState = ButtonPress(btnSw,btnState,strNode);
            } 
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
		return btnState;
	}

	public Boolean ButtonPress(ImageButton btnSw, Boolean btnState, String strNode) {
		if (btnState) 
		{
			btnState = false;
			btnSw.setImageResource(R.drawable.off);
			Toast.makeText(this, strNode + " off!", Toast.LENGTH_SHORT).show();
		} else {
			btnState = true;
			btnSw.setImageResource(R.drawable.on);
			Toast.makeText(this, strNode + " on!", Toast.LENGTH_SHORT).show();
		}
		return btnState;
	}
	
}
