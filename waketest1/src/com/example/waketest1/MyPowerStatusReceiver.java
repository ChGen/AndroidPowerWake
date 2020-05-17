package com.example.waketest1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

public class MyPowerStatusReceiver extends BroadcastReceiver {
	public MyPowerStatusReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED))
			context.startService(new Intent(context, MyWakeService.class));
		else if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED))
			context.stopService(new Intent(context, MyWakeService.class));
   }
}
