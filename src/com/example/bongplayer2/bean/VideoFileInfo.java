package com.example.bongplayer2.bean;

import android.graphics.Bitmap;

public class VideoFileInfo {
	private String videoName;
	private String videoPath;
	private String videoTime;
	private Bitmap videoThumnail;
	private String videoSize;
	private String videoResolution;
	private String videoLNG;
	
	public String getVideoSize() {
		return videoSize;
	}
	public void setVideoSize(String videoSize) {
		this.videoSize = videoSize;
	}
	public String getVideoResolution() {
		return videoResolution;
	}
	public void setVideoResolution(String videoResolution) {
		this.videoResolution = videoResolution;
	}
	public String getVideoLNG() {
		return videoLNG;
	}
	public void setVideoLNG(String videoLNG) {
		this.videoLNG = videoLNG;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public String getVideoPath() {
		return videoPath;
	}
	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}
	public String getVideoTime() {
		return videoTime;
	}
	public void setVideoTime(String videoTime) {
		this.videoTime = videoTime;
	}
	public Bitmap getVideoThumnail() {
		return videoThumnail;
	}
	public void setVideoThumnail(Bitmap videoThumnail) {
		this.videoThumnail = videoThumnail;
	}
	
	
}

