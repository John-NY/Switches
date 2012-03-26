package automation.client.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class ReminderActivity extends Activity {

	Socket nsocket; //Network Socket
    InputStream nis; //Network Input Stream
    OutputStream nos; //Network Output Stream

    private TextView mDateDisplay;
    private TextView mTimeDisplay;
    private Button mPickDate;
    private Button mPickTime;
    private TextView mReminderText;

    private int mYear;
    private int mMonth;
    private int mDay;

    private int mHour;
    private int mMinute;

    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;
        
    /**
     * Called with the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_layout);

        // capture our View elements
        mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
        mPickDate = (Button) findViewById(R.id.pickDate);
        mTimeDisplay = (TextView) findViewById(R.id.timeDisplay);
        mPickTime = (Button) findViewById(R.id.pickTime);
        mReminderText = (TextView) findViewById(R.id.reminderText);

        // add a click listener to the button
        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        // add a click listener to the button
        mPickTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });

        // get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // display the current date
        updateDisplay();

        Toast.makeText(this, "This is the Reminder tab!", Toast.LENGTH_SHORT).show();

    }
    
    @Override
    protected Dialog onCreateDialog(int id) {        
        switch (id) {
	        case DATE_DIALOG_ID:
	            return new DatePickerDialog(this,
	                        mDateSetListener,
	                        mYear, mMonth, mDay);
	        case TIME_DIALOG_ID:
	            return new TimePickerDialog(this,
	                    	mTimeSetListener, 
	                    	mHour, mMinute, false);
        }
        return null;
    }
    
    // updates the date we display in the TextView
    private void updateDisplay() {
        mDateDisplay.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mMonth + 1).append("-")
                    .append(mDay).append("-")
                    .append(mYear).append(" "));
        mTimeDisplay.setText(
                new StringBuilder()
                        .append(pad(mHour)).append(":")
                        .append(pad(mMinute)));

    }

	private static String pad(int c) {
	    if (c >= 10)
	        return String.valueOf(c);
	    else
	        return "0" + String.valueOf(c);
	}
//
//    public void onClick(View v) {
//        showDialog(DATE_DIALOG_ID);
//    }
    
    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };
    // the callback received when the user "sets" the time in the dialog
	private TimePickerDialog.OnTimeSetListener mTimeSetListener =
			new TimePickerDialog.OnTimeSetListener() {
	        	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	        		mHour = hourOfDay;
	        		mMinute = minute;
	            	updateDisplay();
	        	}
	    	};
    /**
     * Handle the action of the button being clicked
     */
    public void reminderButtonClicked(View v)
    {
    	String strCmdStr = mTimeDisplay.getText() + " " +  mDateDisplay.getText() + " , " + mReminderText.getText();
        Toast.makeText(this, strCmdStr, Toast.LENGTH_SHORT).show();
		SendStringToServer(strCmdStr);
    }
    
	public void SendStringToServer(String sendstring)
	{
        try
        {
            SocketAddress saddr = new InetSocketAddress("192.168.0.55", 9140);
            nsocket = new Socket();
            nsocket.connect(saddr, 9140); //10 second connection timeout
            if (nsocket.isConnected()) {
            	String cmd = "\"reminder " + sendstring + "\"";
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
//        catch (IOException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } 
//	}

}
