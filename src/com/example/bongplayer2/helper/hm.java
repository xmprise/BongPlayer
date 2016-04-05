package com.example.bongplayer2.helper;

import com.example.bongplayer2.R;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Process;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
//import com.mxtech.app.MXApplication;

public final class hm
{
  public static int a;
  public static float b;
  public static float c;
  public static float d;

  public static float a(float paramFloat)
  {
    return 39.370098F * paramFloat * a;
  }

  public static int a(String paramString)
  {
    int i;
    if ("bad_removal".equals(paramString))
      i = R.string.error_media_bad_removal;
//    while (true)
//    {
//      return i;
//      if ("checking".equals(paramString))
//      {
//        i = R.string.error_media_checking;
//        continue;
//      }
//      if ("mounted_ro".equals(paramString))
//      {
//        i = R.string.error_media_mounted_read_only;
//        continue;
//      }
//      if ("nofs".equals(paramString))
//      {
//        i = R.string.error_media_nofs;
//        continue;
//      }
//      if ("removed".equals(paramString))
//      {
//        i = R.string.error_media_removed;
//        continue;
//      }
//      if ("shared".equals(paramString))
//      {
//        i = R.string.error_media_shared;
//        continue;
//      }
//      if ("unmountable".equals(paramString))
//      {
//        i = R.string.error_media_unmountable;
//        continue;
//      }
//      if ("unmounted".equals(paramString))
//      {
//        i = R.string.error_media_unmounted;
//        continue;
//      }
//      i = R.string.error_media_general;
//    }
    return 1;
  }

  public static void a(Context paramContext)
  {
    DisplayMetrics localDisplayMetrics = paramContext.getResources().getDisplayMetrics();
    a = localDisplayMetrics.densityDpi;
    b = localDisplayMetrics.density;
    c = localDisplayMetrics.scaledDensity;
    d = a(0.003F);
  }

  public static final void a(Window paramWindow)
  {
    paramWindow.requestFeature(1);
    paramWindow.addFlags(1024);
  }

  public static final void a(Window paramWindow, float paramFloat)
  {
    WindowManager.LayoutParams localLayoutParams = paramWindow.getAttributes();
    localLayoutParams.screenBrightness = paramFloat;
    paramWindow.setAttributes(localLayoutParams);
  }

  public static final void a(Window paramWindow, boolean paramBoolean)
  {
    int i;
    float f;
    WindowManager.LayoutParams localLayoutParams;
//    if (paramBoolean)
//    {
//      i = -1;
//      f = i;
//      localLayoutParams = paramWindow.getAttributes();
//      if (localLayoutParams.buttonBrightness != f)
//        break label32;
//    }
//    while (true)
//    {
//      return;
//      i = 0;
//      break;
//      label32: localLayoutParams.buttonBrightness = f;
//      paramWindow.setAttributes(localLayoutParams);
//      Log.i(MXApplication.a, "Turned button backlight " + paramBoolean + " for " + paramWindow);
//    }
  }

  public static float b(float paramFloat)
  {
    return paramFloat / (39.370098F * a);
  }

  public static Location b(Context paramContext)
  {
    int i = 0;
    LocationManager localLocationManager = (LocationManager)paramContext.getSystemService("location");
    Object localObject = null;
    if (localLocationManager == null)
      localObject = null;
//    while (true)
//    {
//      return localObject;
//      int j = Process.myPid();
//      int k = Process.myUid();
//      int m;
//      if (paramContext.checkPermission("android.permission.ACCESS_FINE_LOCATION", j, k) == 0)
//      {
//        m = 1;
//        if (paramContext.checkPermission("android.permission.ACCESS_COARSE_LOCATION", j, k) == 0)
//          i = 1;
//        if (m == 0);
//      }
//      else
//      {
//        try
//        {
//          Location localLocation3 = localLocationManager.getLastKnownLocation("gps");
//          if (localLocation3 != null)
//          {
//            localObject = localLocation3;
//            continue;
//            m = 0;
//          }
//        }
//        catch (IllegalArgumentException localIllegalArgumentException3)
//        {
//        }
//      }
//      if ((m != 0) || (i != 0))
//        try
//        {
//          Location localLocation2 = localLocationManager.getLastKnownLocation("network");
//          if (localLocation2 != null)
//            localObject = localLocation2;
//        }
//        catch (IllegalArgumentException localIllegalArgumentException1)
//        {
//        }
//      if ((Build.VERSION.SDK_INT > 7) && (m != 0));
//      try
//      {
//        Location localLocation1 = localLocationManager.getLastKnownLocation("passive");
//        localObject = localLocation1;
//        if (localObject != null)
//          continue;
//        label154: localObject = null;
//      }
//      catch (IllegalArgumentException localIllegalArgumentException2)
//      {
//        break label154;
//      }
//    }
    return (Location) localObject;
  }

  public static float c(float paramFloat)
  {
    return paramFloat * b;
  }

  public static String c(Context paramContext)
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    if (localTelephonyManager == null);
    for (String str = null; ; str = localTelephonyManager.getNetworkCountryIso())
      return str;
  }

  public static float d(float paramFloat)
  {
    return paramFloat / b;
  }

  public static boolean d(Context paramContext)
  {
    ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
    int i;
    if (localConnectivityManager == null)
      i = 0;
//    while (true)
//    {
//      return i;
//      NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
//      if (localNetworkInfo == null)
//      {
//        i = 0;
//        continue;
//      }
//      if (localNetworkInfo.getType() != 1)
//      {
//        i = 0;
//        continue;
//      }
//      if (!localNetworkInfo.isConnected())
//      {
//        i = 0;
//        continue;
//      }
//      i = 1;
//    }
    return true;
  }

  public static boolean e(Context paramContext)
  {
//    int i = 1;
//    int j = 0;
//    if (Build.VERSION.SDK_INT >= 17)
//    	return false;
//    else
//    {
//      if (Build.VERSION.SDK_INT >= 13)
//      {
//        Configuration localConfiguration = paramContext.getResources().getConfiguration();
//        if (Build.VERSION.SDK_INT < 16)
//          if (localConfiguration.smallestScreenWidthDp < 600);
//        while (true)
//        {
//          j = i;
//          break;
//          i = 0;
//
//          if (localConfiguration.smallestScreenWidthDp >= 720)
//            continue;
//          i = 0;
//        }
//      }
//      if (Build.VERSION.SDK_INT < 11)
//
//      j = i;
//      
//      return true;
//    }
	  return true;
  }
}

