package com.example.waketest1;

import java.util.Timer;
import java.util.TimerTask;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;


public class MainActivity extends ActionBarActivity {

	Timer mTimer;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ToggleButton tb = (ToggleButton)findViewById(R.id.toggleButton1);
        tb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					startService(new Intent(MainActivity.this, MyWakeService.class));
				}
				else {
					stopService(new Intent(MainActivity.this, MyWakeService.class));
				}
			}
		});
    }

    @SuppressLint("HandlerLeak")
	@Override
    protected void onResume() {
        final ToggleButton tb = (ToggleButton)findViewById(R.id.toggleButton1);
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
            	tb.setChecked(MyWakeService.isStarted());
            }
        };
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				handler.obtainMessage().sendToTarget();
			}
		}, 0, 1000);
    	super.onResume();
    }
    
    @Override
    protected void onPause() {
    	mTimer.cancel();
    	mTimer.purge();
    	super.onPause();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
