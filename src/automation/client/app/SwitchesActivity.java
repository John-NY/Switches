package automation.client.app;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class SwitchesActivity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Reusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Do the same for the other tabs
        intent = new Intent().setClass(this, PowerActivity.class);
        spec = tabHost.newTabSpec("power").setIndicator("Power",
                          res.getDrawable(R.drawable.ic_tab_power))
                      .setContent(intent);
        tabHost.addTab(spec);

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, PandoraActivity.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("pandora").setIndicator("Pandora",
                          res.getDrawable(R.drawable.ic_tab_pandora))
                      .setContent(intent);
        tabHost.addTab(spec);

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, AnnaActivity.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("anna").setIndicator("Anna")
                      .setContent(intent);
        tabHost.addTab(spec);

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, ReminderActivity.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("reminder").setIndicator("Reminder")
                      .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
    }

}	

