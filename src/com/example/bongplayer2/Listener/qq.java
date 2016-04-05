package com.example.bongplayer2.Listener;

import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import com.example.bongplayer2.CommonGestures;
import com.example.bongplayer2.CommonGestures.TouchListener;

public final class qq
implements ScaleGestureDetector.OnScaleGestureListener
{
	private final String LOGTAG = "";
	private CommonGestures commonGestures;

	public qq(CommonGestures paramCommonGestures)
	{
		commonGestures = paramCommonGestures;
	}

	public final boolean onScale(ScaleGestureDetector paramScaleGestureDetector)
	{
		if ((commonGestures.touchListener != null) && (commonGestures.a))
			commonGestures.touchListener.onScale(paramScaleGestureDetector.getScaleFactor(), 1);
		return true;
	}

	public final boolean onScaleBegin(ScaleGestureDetector paramScaleGestureDetector)
	{
		if ((commonGestures.touchListener != null) && (commonGestures.a))
			commonGestures.touchListener.onScale(0.0F, 0);
		return true;
	}

	public final void onScaleEnd(ScaleGestureDetector paramScaleGestureDetector)
	{
		if ((commonGestures.touchListener != null) && (commonGestures.a))
			commonGestures.touchListener.onScale(0.0F, 2);
	}
}