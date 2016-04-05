package com.example.bongplayer2.Listener;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.bongplayer2.CommonGestures;
import com.yixia.zi.utils.DeviceUtils;

public class qm extends GestureDetector.SimpleOnGestureListener{

	private final String LOGTAG = "qm";
	private boolean touchStart = false;
	private CommonGestures commonGestures;

	public qm(CommonGestures paramCommonGestures)
	{
		commonGestures = paramCommonGestures;
	}

	public final boolean onDoubleTap(MotionEvent paramMotionEvent)
	{
		if ((commonGestures.touchListener != null) && (commonGestures.a))
			commonGestures.touchListener.onDoubleTap();
		return super.onDoubleTap(paramMotionEvent);
	}

	public final boolean onDown(MotionEvent paramMotionEvent)
	{
		touchStart = true;
		return super.onDown(paramMotionEvent);
	}

	public final boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float distanceX, float distanceY)
	{
		float x;
		float y;
		int screenWidth;
		int screenHeight;

		Log.d(LOGTAG, "paramMotionEvent1.getX()>>"+paramMotionEvent1.getX());
		Log.d(LOGTAG, "paramMotionEvent1.getY()>>"+paramMotionEvent1.getY());
		Log.d(LOGTAG, "paramMotionEvent2.getX()>>"+paramMotionEvent2.getX());
		Log.d(LOGTAG, "paramMotionEvent2.getY()>>"+paramMotionEvent2.getY());
		Log.d(LOGTAG, "paramMotionEvent2.getX(0)>>"+paramMotionEvent2.getX(0));
		Log.d(LOGTAG, "paramMotionEvent2.getY(0)>>"+paramMotionEvent2.getY(0));
		
		if ((commonGestures.touchListener != null) && (commonGestures.a) && (paramMotionEvent1 != null) && (paramMotionEvent2 != null))
		{
			if (touchStart)
			{
				commonGestures.touchListener.onGestureBegin();
				touchStart = false;
			}

			x = paramMotionEvent1.getX();
			y = paramMotionEvent1.getY();

			screenWidth = DeviceUtils.getScreenWidth(commonGestures.videoActivity);
			screenHeight = DeviceUtils.getScreenHeight(commonGestures.videoActivity);

			if (2.0F * Math.abs(paramMotionEvent2.getY(0) - y) > Math.abs(paramMotionEvent2.getX(0) - x))
			{
				if (x <= 4.0D * screenWidth / 5.0D)
				{
					if (x < screenWidth / 5.0D)
					{
						commonGestures.touchListener.onLeftSlide((y - paramMotionEvent2.getY(0)) / screenHeight);
						Log.d(LOGTAG, "onLeftSlide");
					}
				}
				else
				{
					commonGestures.touchListener.onRightSlide((y - paramMotionEvent2.getY(0)) / screenHeight);
					Log.d(LOGTAG, "onRightSlide");
				}
			}
			else
			{
				commonGestures.touchListener.onCenterSlide((paramMotionEvent2.getX(0) - x) / screenWidth * 3);
				Log.d(LOGTAG, "onCenterSlide");
			}
		}

		return super.onScroll(paramMotionEvent1, paramMotionEvent2, distanceX, distanceY);

	}
}
