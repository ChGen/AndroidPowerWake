package com.example.waketest1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class MyWakeService extends Service {
	WakeLock mWakeLock;
	private static boolean sStarted = false;
	
	public static boolean isStarted() { return sStarted; }
	
	public MyWakeService() {
	}

	@Override
	public void onCreate() {
		super.onCreate();
		sStarted = true;
		PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
		mWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
		        "MyWakelockTag");
		mWakeLock.acquire();
		PowerStatusNotification.notify(this, "CPU locked!", 1);
	}
	
	@Override
	public void onDestroy() {
		sStarted = false;
		mWakeLock.release();
		PowerStatusNotification.cancel(this);
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
