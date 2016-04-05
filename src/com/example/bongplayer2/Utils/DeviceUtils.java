package com.example.bongplayer2.Utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import java.lang.reflect.Method;

public class DeviceUtils
{
	public static int getScreenHeight(Activity paramActivity)
	{
		int i = 0;
		int j = 0;
		Display display = paramActivity.getWindowManager().getDefaultDisplay();
		try
		{
			j = ((Integer)Display.class.getMethod("getRawHeight", new Class[0]).invoke(display, new Object[0])).intValue();
			//i = display.getHeight();
			//i = j;
			return j;
		}
		catch (Exception exception)
		{
			i = display.getHeight();
			return i;
		}
		

	}

	public static double getScreenPhysicalSize(Activity paramActivity)
	{
		DisplayMetrics displayMetrics = new DisplayMetrics();
		paramActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		return Math.sqrt(Math.pow(displayMetrics.widthPixels, 2.0D) + Math.pow(displayMetrics.heightPixels, 2.0D)) / (160.0F * displayMetrics.density);
	}

	public static int getScreenWidth(Activity paramActivity)
	{
		int i=0;
		int j=0;
		Display display = paramActivity.getWindowManager().getDefaultDisplay();
		try
		{
			j = ((Integer)Display.class.getMethod("getRawWidth", new Class[0]).invoke(display, new Object[0])).intValue();
			return j;
		}
		catch (Exception localException)
		{
			i = display.getWidth();
			return i;
		}
	}
}