package com.example.bongplayer2;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import com.yixia.zi.utils.DeviceUtils;

public class HWDecodingVideoView extends SurfaceView
{
	public static final int VIDEO_LAYOUT_ORIGIN = 0;
	public static final int VIDEO_LAYOUT_SCALE = 1;
	public static final int VIDEO_LAYOUT_SCALE_ZOOM = 4;
	public static final int VIDEO_LAYOUT_STRETCH = 2;
	public static final int VIDEO_LAYOUT_ZOOM = 3;
	private Activity videoActivity;
	private SurfaceHolder surfaceHolder;
	private int xPoint;
	private int yPoint;
	private int videoMode = 1; 
	private SurfaceHolder.Callback surfaceHolderCallback = new pv();
	private SurfaceCallback surfaceCallback; 
	public int mVideoHeight; 

	public HWDecodingVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(surfaceHolderCallback);
	}

	@SuppressWarnings("deprecation")
	public void initialize(Activity paramActivity, SurfaceCallback paramSurfaceCallback, boolean paramBoolean)
	{
		videoActivity = paramActivity;

		surfaceCallback = paramSurfaceCallback;

		if (surfaceHolder == null)
			surfaceHolder = getHolder();

		if (paramBoolean)
			getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		else
			getHolder().setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
	}

	public void setVideoLayout(int videoMode, float prefScreenRatio, int videoWidth, int videoHeight, float videoAspectRatio)
	{
		this.videoMode = videoMode;

		ViewGroup.LayoutParams layoutParams1 = getLayoutParams();

			
		int deviceScreenWidth = DeviceUtils.getScreenWidth(videoActivity);
		int deviceScreenHeight = DeviceUtils.getScreenHeight(videoActivity);

		float deviceRatio = deviceScreenWidth / deviceScreenHeight;

		
		if (prefScreenRatio <= 0.01F)
			prefScreenRatio = videoAspectRatio;

		yPoint = videoHeight;
		xPoint = videoWidth;
		int n = 0;

		
		if ((this.videoMode == VIDEO_LAYOUT_ORIGIN) && (xPoint < deviceScreenWidth) && (yPoint < deviceScreenHeight))
		{
			Log.d("setVideoLayout", "VIDEO_LAYOUT_ORIGIN");
			layoutParams1.width = ((int)(prefScreenRatio * yPoint));
			layoutParams1.height = ((int)(xPoint / prefScreenRatio));
		}
		else if(this.videoMode == VIDEO_LAYOUT_ZOOM)
		{
			Log.d("setVideoLayout", "VIDEO_LAYOUT_ZOOM");
			if (deviceRatio > prefScreenRatio) //
			{
				layoutParams1.width = deviceScreenWidth;
				layoutParams1.height = deviceScreenHeight;
			}
			else
			{
				layoutParams1.width = (int)prefScreenRatio * deviceScreenHeight;
				layoutParams1.height = (int)(deviceScreenWidth / prefScreenRatio);
			}
		}
		else if(this.videoMode == VIDEO_LAYOUT_STRETCH)
		{
			Log.d("setVideoLayout", "VIDEO_LAYOUT_STRETCH");
			layoutParams1.width = deviceScreenWidth;
			layoutParams1.height = deviceScreenHeight;
		}
		else if ((this.videoMode == VIDEO_LAYOUT_SCALE_ZOOM) && (this.mVideoHeight > 0))
		{
			Log.d("setVideoLayout", "VIDEO_LAYOUT_SCALE_ZOOM");
			Log.d("setVideoLayout", "this mVideoHeight" + this.mVideoHeight);

			layoutParams1.width = ((int)(prefScreenRatio * this.mVideoHeight));
			layoutParams1.height = this.mVideoHeight;
			
			Log.d("setVideoLayout", "this layoutParams1.width>>> " + layoutParams1.width);
			Log.d("setVideoLayout", "this layoutParams1.height>>> " + layoutParams1.height);
		}

		this.mVideoHeight = layoutParams1.height;

		setLayoutParams(layoutParams1);
		getHolder().setFixedSize(xPoint, yPoint);

		//		Object[] arrayOfObject = new Object[10];
		//		arrayOfObject[0] = Integer.valueOf(videoWidth);
		//		arrayOfObject[1] = Integer.valueOf(videoHeight);
		//		arrayOfObject[2] = Float.valueOf(videoAspectRatio);
		//		arrayOfObject[3] = Integer.valueOf(xPoint);
		//		arrayOfObject[4] = Integer.valueOf(yPoint);
		//		arrayOfObject[5] = Integer.valueOf(layoutParams1.width);
		//		arrayOfObject[6] = Integer.valueOf(layoutParams1.height);
		//		arrayOfObject[7] = Integer.valueOf(screenWidth);
		//		arrayOfObject[8] = Integer.valueOf(screenHeight);
		//		arrayOfObject[9] = Float.valueOf(f1);		
		//		Log.d("VIDEO: %dx%dx%f, Surface: %dx%d, LP: %dx%d, Window: %dx%dx%f", arrayOfObject);
		}

	public final class pv implements SurfaceHolder.Callback
	{
		public final void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3)
		{
			paramSurfaceHolder.setKeepScreenOn(true);

			if (surfaceCallback != null)
				surfaceCallback.onSurfaceChanged(paramSurfaceHolder, paramInt1, paramInt2, paramInt3);
		}

		public final void surfaceCreated(SurfaceHolder paramSurfaceHolder)
		{
			// VideoView.a(this.a, paramSurfaceHolder);
			if (surfaceCallback != null)
				surfaceCallback.onSurfaceCreated(paramSurfaceHolder);
		}

		public final void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
		{
			if (surfaceCallback != null)
				surfaceCallback.onSurfaceDestroyed(paramSurfaceHolder);
		}
	}

	public abstract interface SurfaceCallback
	{
		public abstract void onSurfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3);

		public abstract void onSurfaceCreated(SurfaceHolder paramSurfaceHolder);

		public abstract void onSurfaceDestroyed(SurfaceHolder paramSurfaceHolder);
	}
}
