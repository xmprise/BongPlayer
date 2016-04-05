package com.example.bongplayer2.Listener;

import io.vov.utils.StringUtils;

import com.example.bongplayer2.MediaController;
import com.example.bongplayer2.MediaController.MediaPlayerControl;

import android.media.AudioManager;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public final class SeekBarListener implements SeekBar.OnSeekBarChangeListener{

	private String LOGTAG = "SeekBarListener";

	private boolean keepStopPlaying = false;
	private MediaController mediaController;

	private AudioManager audioManger;
	private Handler handler;
	public SeekBarListener(MediaController mediaController)
	{
		this.mediaController = mediaController;
		audioManger = mediaController.getAudioManager();
		handler = mediaController.getHandler();
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		Log.d(LOGTAG, "fromUser>>>"+fromUser);
		Log.d(LOGTAG, "progress>>>>"+progress);
		
		if(!fromUser)
			return;
		else
		{
			long duration = mediaController.mediaPlayerControl.getDuration();
			long seekTime = duration / 1000 * progress;

			String time = StringUtils.generateTime(seekTime);
			if(mediaController.isShowing())
				mediaController.mediaPlayerControl.seekTo(seekTime);
			mediaController.setScreenCenterMsg(time, 1500L);
			mediaController.getCurrentPlayTime().setText(time);
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		//MediaController.b(this.b, true);

		mediaController.show(3600000);

		handler.removeMessages(2);
		Log.d(LOGTAG, "onStartTrackingTouch");
		
		if (!mediaController.mediaPlayerControl.isPlaying())
			keepStopPlaying = true;
		else
		{
			keepStopPlaying = false;
		}
		
		if (mediaController.isShowing())
		{
			audioManger.setStreamMute(3, true);
			if (keepStopPlaying)
			{
				mediaController.mediaPlayerControl.start();

			}
		}
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
		Log.d(LOGTAG, "onStopTrackingTouch");
		if (!mediaController.isShowing())
			mediaController.mediaPlayerControl.seekTo((int)mediaController.mediaPlayerControl.getDuration() * seekBar.getProgress() / 1000L);
		else
		{
			mediaController.getCurrentPlayTimeCenter().setVisibility(View.GONE); // 화면 중앙에 표시되는 플레이 시간

			mediaController.show(3000);

			handler.removeMessages(2);
			audioManger.setStreamMute(3, false);
			//MediaController.b(this.b, false);
			handler.sendEmptyMessageDelayed(2, 1000L);
			if(keepStopPlaying)
			{
				mediaController.mediaPlayerControl.pause();
			}
		}
	}
}
