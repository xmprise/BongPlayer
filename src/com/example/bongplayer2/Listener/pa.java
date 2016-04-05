package com.example.bongplayer2.Listener;

import io.vov.utils.StringUtils;
import android.app.Activity;
import android.media.AudioManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageButton;
import com.yixia.zi.utils.UIUtils;
import com.example.bongplayer2.CommonGestures.TouchListener;
import com.example.bongplayer2.MediaController;
import com.example.bongplayer2.MediaController.MediaPlayerControl;

public final class pa
implements TouchListener
{
	private final String LOGTAG = "TouchListener";
	private MediaController mediaController;
	
	public pa(MediaController paramMediaController)
	{
		mediaController = paramMediaController;
	}

	public final void onDoubleTap()
	{
		Log.d(LOGTAG, "onDoubleTap");
//		MediaController.m(this.a);
	}

	public final void onGestureBegin()
	{
		Log.d(LOGTAG, "onGestureBegin");
		
//		mediaController.a(this.a, mediaController.getVideoActivity().getWindow().getAttributes().screenBrightness);
//		mediaController.a(this.a, mediaController.getAudioManager().getStreamVolume(3));
//		
//		if (mediaController.g(this.a) <= 0.0F)
//			mediaController.a(this.a, 0.5F);
//		
//		if (mediaController.g(this.a) < 0.01F)
//			mediaController.a(this.a, 0.01F);
//		
//		if (mediaController.h(this.a) < 0)
//			mediaController.a(this.a, 0);
		
		mediaController.currentVolume = mediaController.getAudioManager().getStreamVolume(3);
	}

	public final void onGestureEnd()
	{
		Log.d(LOGTAG, "onGestureEnd");
		mediaController.brightnNessView.setVisibility(View.GONE);
	}

	public final void onLeftSlide(float paramFloat)
	{
//		Log.d(LOGTAG, "onLeftSlide");
//		Log.d(LOGTAG, "onLeftSlide paramFloat>>>"+paramFloat);
//		if ((UIUtils.isGingerbread()) && (mediaController.isAutoBrightness(mediaController.getVideoActivity())))
//		{
//			mediaController.stopAutoBrightness(mediaController.getVideoActivity());
//			MediaController.j(this.a);
//		}
//		mediaController.setVolume(paramFloat + 0.01F);
//		mediaController.setScreenBrightness(mediaController.getVideoActivity().getWindow().getAttributes().screenBrightness);
	}

	public final void onLongPress()
	{
		Log.d(LOGTAG, "onLongPress");
		mediaController.playOrPause();
	}

	public final void onRightSlide(float paramFloat)
	{
		Log.d(LOGTAG, "onRightSlide");
		Log.d(LOGTAG, "onRightSlide paramFloat>>>"+paramFloat);
		int volumeValue = (int)(paramFloat * mediaController.maxVolume) + mediaController.currentVolume;
		
		Log.d(LOGTAG, "volumeValue>>>"+volumeValue);
		mediaController.setVolume(volumeValue);
	}

	public final void onScale(float paramFloat, int paramInt)
	{
		Log.d(LOGTAG, "onScale");
		switch (paramInt)
		{
		default:
		case 0: //scale begin
//			MediaController.o(this.a);
//			MediaController.p(this.a).setImageResource(2130837728);
//			mediaController.mediaPlayerControl.toggleVideoMode(MediaController.q(this.a));
			break;
		case 1: // scale
			float f = mediaController.mediaPlayerControl.scale(paramFloat);
			mediaController.setScreenCenterMsg((int)(f * 100.0F) + "%", 500L);
			break;
		}
	}

	public final void onSingleTap()
	{
		Log.d(LOGTAG, "onSingleTap");
		if (mediaController.isShowing())
			mediaController.hide();
		else
		{
			mediaController.show();
		}
		
		if (mediaController.mediaPlayerControl.getBufferPercentage() >= 100)
			mediaController.mediaPlayerControl.removeLoadingView();
	}

	@Override
	public void onCenterSlide(float paramFloat) {
		// TODO Auto-generated method stub
		Log.d(LOGTAG, "onCenterSlide");
		Log.d(LOGTAG, "onCenterSlide paramFloat>>>"+paramFloat);
		Log.d(LOGTAG, "onCenterSlide paramFloat>>>"+(int)paramFloat);
		Log.d(LOGTAG, "onCenterSlide progress>>>"+mediaController.getSeekBar().getProgress());
		
		long seekTime;
		//long seekTime = duration / 1000 * mediaController.seekBar.getProgress() + 1000;
		
		
		if(paramFloat > 0)
			seekTime = mediaController.mediaPlayerControl.getCurrentPosition() + 1000 * 1;
		else
			seekTime = mediaController.mediaPlayerControl.getCurrentPosition() - 1000 * 1;
		
		mediaController.mediaPlayerControl.seekTo(seekTime);
		String time = StringUtils.generateTime(seekTime);
		
		//if(mediaController.mediaPlayerControl.getCurrentDecoder().equals("SW"))
			
		mediaController.setScreenCenterMsg(time, 1500L);
		mediaController.getCurrentPlayTime().setText(time);
		
//		mediaController.seekBar.setProgress(mediaController.seekBar.getProgress() + (int)paramFloat);
//		mediaController.seekBarListener.onProgressChanged(mediaController.seekBar, mediaController.seekBar.getProgress() + 1, true);
		//mediaController.mediaPlayerControl.seekTo(seekTime);
		
		
		
	}
}