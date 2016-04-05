package com.example.bongplayer2.Listener;

import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import com.yixia.zi.utils.DeviceUtils;
import com.example.bongplayer2.CommonGestures;
import com.example.bongplayer2.CommonGestures.TouchListener;

public class qw extends GestureDetector.SimpleOnGestureListener{

	private final String LOGTAG = "";
	private CommonGestures commonGestures;

	public qw(CommonGestures paramCommonGestures)
	{
		commonGestures = paramCommonGestures;
	}

	public final void onLongPress(MotionEvent paramMotionEvent)
	{
		if ((commonGestures.touchListener != null) && (commonGestures.a))
			commonGestures.touchListener.onLongPress();
	}

	public final boolean onSingleTapConfirmed(MotionEvent paramMotionEvent)
	{
		if (commonGestures.touchListener != null)
			commonGestures.touchListener.onSingleTap();
		return true;
	}
}
