package com.example.bongplayer2;

import java.io.IOException;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.ThumbnailUtils;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

public class VideoViewDemo extends Activity {

	private String path = "http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8";
	private String path2 = "/sdcard/Movies/Premium.Rush.2012.1080p.BDRip.x264.AAC-26K.mp4";
	private String path3 = "/sdcard/Movies/[Mnet] 보이스 코리아 시즌2.E03.130308.HDTV.H264.720p-WITH.mp4";
	private VideoView mVideoView;
	private MediaPlayer mp;
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		if (!LibsChecker.checkVitamioLibs(this))
			return;

		setContentView(R.layout.activity_video_view_demo);
//		try {
//			mp = new MediaPlayer(getApplicationContext());
//			mp.setDataSource(path2);
//			mp.setDisplay();
//			mp.prepare();
//			mp.start();
//			mp.
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		mVideoView = (VideoView) findViewById(R.id.surface_view);
		mVideoView.setVideoPath(path3);
		mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_LOW);
		mVideoView.setMediaController(new MediaController(this));
		mVideoView.requestFocus();
		System.out.println("AspectRatio>>>>"+mVideoView.getVideoAspectRatio());
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if (mVideoView != null)
			mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
		super.onConfigurationChanged(newConfig);
	}
}
