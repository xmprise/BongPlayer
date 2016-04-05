package com.example.bongplayer2.interfaces;

public interface MediaPlayerControl{
	
	public abstract int getBufferPercentage();

	public abstract long getCurrentPosition();

	public abstract long getDuration();

	public abstract long goBack();

	public abstract long goForward();

	public abstract boolean isPlaying();

	public abstract void next();

	public abstract void pause();

	public abstract void previous();

	public abstract void removeLoadingView();

	public abstract float scale(float paramFloat);

	public abstract void seekTo(long paramLong);

	public abstract void showMenu();

	public abstract void snapshot();

	public abstract void start();

	public abstract void stop();

	public abstract void toggleVideoMode(int paramInt);
	
	public abstract void release();
}
