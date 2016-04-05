package com.example.bongplayer2;

import java.util.Map;

import com.appunite.ffmpeg.FFmpegStreamInfo;

public interface PlayerFunctionInterface {

	public abstract int getCurrentPosition();

	public abstract int getDuration();

	public abstract int getVideoHeight();

	public abstract int getVideoWidth();

	public abstract boolean isPlaying();

	public abstract void pause();

	public abstract void release();

	public abstract void reset();

	public abstract float scale(float paramFloat);

	public abstract void seekTo(long paramLong);

	public abstract void setDataSource();
	
	public abstract void setDataSource(String url, Map<String, String> dictionary,
			FFmpegStreamInfo videoStream, FFmpegStreamInfo audioStream,
			FFmpegStreamInfo subtitlesStream);

	public abstract void snapshot();

	public abstract void start();

	public abstract void stop();

	public abstract void toggleVideoMode(int paramInt);
}
