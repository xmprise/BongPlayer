package com.example.bongplayer2;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ExternalStorageMonitor extends BroadcastReceiver{
	
	public final void onReceive(Context paramContext, Intent paramIntent)
	{
		Log.i("SDcardReceiver: %s", paramIntent.getAction());
	}
}
