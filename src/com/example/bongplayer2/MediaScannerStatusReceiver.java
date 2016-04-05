package com.example.bongplayer2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.yixia.zi.utils.Log;
import com.yixia.zi.utils.ServiceHelper;

public class MediaScannerStatusReceiver extends BroadcastReceiver
{
	private final String LOGTAG = "MediaScannerStatusReceiver";
	private static boolean a = false;
	private static boolean b = false;

	public static boolean isScanning(Context paramContext)
	{
		return ((a) || (ServiceHelper.isServiceRunning(paramContext, "io.vov.vitamio.MediaScannerService")));
	}

	public static boolean isScanningStarted()
	{
		boolean bool = b;
		b = false;
		return bool;
	}

	public void onReceive(Context paramContext, Intent paramIntent)
	{
		String str = paramIntent.getAction();

		Log.i(LOGTAG , "MediaScannerStatusReceiver: "+ str);

		if ("com.yixia.vitamio.action.MEDIA_SCANNER_STARTED".equals(str))
		{
			a = true;
			b = true;
		}
		else if ("com.yixia.vitamio.action.MEDIA_SCANNER_FINISHED".equals(str))
		{
			a = false;
		}
	}
}
