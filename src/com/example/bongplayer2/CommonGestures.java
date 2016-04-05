package com.example.bongplayer2;

import android.app.Activity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.example.bongplayer2.Listener.qm;
import com.example.bongplayer2.Listener.qq;
import com.example.bongplayer2.Listener.qw;

public class CommonGestures
{
	public final String LOGTAG = "CommonGestures";
	public static final int SCALE_STATE_BEGIN = 0;
	public static final int SCALE_STATE_END = 2;
	public static final int SCALE_STATE_SCALEING = 1;
	public boolean a;
	public GestureDetector b;
	public GestureDetector c;
	public ScaleGestureDetector scaleGestureDetector;
	public Activity videoActivity;
	public TouchListener touchListener;

	public CommonGestures(Activity paramActivity)
	{
		videoActivity = paramActivity;
		this.b = new GestureDetector(videoActivity, new qm(this));
		this.c = new GestureDetector(videoActivity, new qw(this));
		scaleGestureDetector = new ScaleGestureDetector(videoActivity, new qq(this));
	}

	public boolean onTouchEvent(MotionEvent paramMotionEvent)
	{
		Log.d(LOGTAG, "onTouchEvent");

		boolean bool1 = false;

		if (touchListener == null)
			return bool1;
		else
		{
			if (this.c.onTouchEvent(paramMotionEvent))
				bool1 = true;
			else if (paramMotionEvent.getPointerCount() > 1)
			{
				try
				{
					if (scaleGestureDetector != null)
					{
						boolean bool2 = scaleGestureDetector.onTouchEvent(paramMotionEvent);
						if (bool2)
							bool1 = true;
					}
				}
				catch (Exception exception)
				{
					exception.printStackTrace();
				}
			}
			else if (this.b.onTouchEvent(paramMotionEvent))
				bool1 = true;
			else
				switch (0xFF & paramMotionEvent.getAction())
				{
				default:
					break;
				case 1:
					touchListener.onGestureEnd();
				}
		}
		return true;
	}

	public void setTouchListener(CommonGestures.TouchListener paramTouchListener, boolean paramBoolean)
	{
		touchListener = paramTouchListener;
		this.a = paramBoolean;
	}

	public abstract interface TouchListener
	{
		public abstract void onDoubleTap();

		public abstract void onGestureBegin();

		public abstract void onGestureEnd();

		public abstract void onLeftSlide(float paramFloat);

		public abstract void onLongPress();

		public abstract void onRightSlide(float paramFloat);

		public abstract void onScale(float paramFloat, int paramInt);

		public abstract void onSingleTap();
		
		public abstract void onCenterSlide(float paramFloat);
	}
}