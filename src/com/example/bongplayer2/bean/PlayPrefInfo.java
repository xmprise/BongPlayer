package com.example.bongplayer2.bean;

import android.content.Context;

import com.example.bongplayer2.R;
import com.yixia.zi.preference.APreference;

public class PlayPrefInfo {
	private String fileName;
	private boolean isCountinu;
	private boolean isNextPlay;
	private String screenSize;
	private float screenRatio;
	private int brightness;
	private int seekInterval;
	private String decoder;
	
	private String subFont;
	private int subFontSize;
	private int subColor;
	private boolean isSubShow;
	private String subEncoding;
	private int subPosition;
	private int subTime;
	
	private boolean isRePlay;
	private boolean isLoop;
	
	private APreference apreference;
	public PlayPrefInfo(APreference apreference , Context context)
	{
		this.apreference = apreference;
		
//		fileName = 
		isCountinu = Boolean.valueOf(apreference.getString(context.getString(R.string.pref_key_play_continu), "false"));
		isNextPlay = Boolean.valueOf(apreference.getString(context.getString(R.string.pref_key_play_next), "false"));
		screenSize = apreference.getString(context.getString(R.string.pref_key_video_size), "100");
		screenRatio = Float.valueOf(apreference.getString(context.getString(R.string.pref_key_screen_ratio), "1.78"));
		brightness = Integer.valueOf(apreference.getString(context.getString(R.string.pref_key_brightness), "10"));
		seekInterval = Integer.valueOf(apreference.getString(context.getString(R.string.pref_key_micro_seek_duration), "5"));
		subFont = apreference.getString(context.getString(R.string.pref_key_sub_font), "Default");
		subFontSize = Integer.valueOf(apreference.getString(context.getString(R.string.pref_key_sub_size), "15"));
		
		subColor = Integer.valueOf(apreference.getString(context.getString(R.string.pref_key_sub_color), "-65536"));
		isSubShow = Boolean.valueOf(apreference.getString(context.getString(R.string.pref_key_sub_shown), "true"));
		subEncoding = apreference.getString(context.getString(R.string.pref_key_sub_encoding), "Auto Detect");
		subPosition = Integer.valueOf(apreference.getString(context.getString(R.string.pref_key_sub_position), "1"));
		subTime = Integer.valueOf(apreference.getString(context.getString(R.string.pref_key_sub_time), "1"));
	}
	
	public void savePref(APreference apreference)
	{
		
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public boolean isCountinu() {
		return isCountinu;
	}
	public void setCountinu(boolean isCountinu) {
		this.isCountinu = isCountinu;
	}
	public boolean isNextPlay() {
		return isNextPlay;
	}
	public void setNextPlay(boolean isNextPlay) {
		this.isNextPlay = isNextPlay;
	}
	public String getScreenSize() {
		return screenSize;
	}
	public void setScreenSize(String screenSize) {
		this.screenSize = screenSize;
	}
	public float getScreenRatio() {
		return screenRatio;
	}
	public void setScreenRatio(float screenRatio) {
		this.screenRatio = screenRatio;
	}
	public int getBrightness() {
		return brightness;
	}
	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}
	public int getSeekInterval() {
		return seekInterval;
	}
	public void setSeekInterval(int seekInterval) {
		this.seekInterval = seekInterval;
	}
	public String getDecoder() {
		return decoder;
	}
	public void setDecoder(String decoder) {
		this.decoder = decoder;
	}
	public String getSubFont() {
		return subFont;
	}
	public void setSubFont(String subFont) {
		this.subFont = subFont;
	}
	public int getSubFontSize() {
		return subFontSize;
	}
	public void setSubFontSize(int subFontSize) {
		this.subFontSize = subFontSize;
	}
	public int getSubColor() {
		return subColor;
	}
	public void setSubColor(int subColor) {
		this.subColor = subColor;
	}
	public boolean isSubShow() {
		return isSubShow;
	}
	public void setSubShow(boolean isSubShow) {
		this.isSubShow = isSubShow;
	}
	public String getSubEncoding() {
		return subEncoding;
	}
	public void setSubEncoding(String subEncoding) {
		this.subEncoding = subEncoding;
	}
	public int getSubPosition() {
		return subPosition;
	}
	public void setSubPosition(int subPosition) {
		this.subPosition = subPosition;
	}
	public int getSubTime() {
		return subTime;
	}
	public void setSubTime(int subTime) {
		this.subTime = subTime;
	}

	public boolean isRePlay() {
		return isRePlay;
	}

	public void setRePlay(boolean isRePlay) {
		this.isRePlay = isRePlay;
	}

	public boolean isLoop() {
		return isLoop;
	}

	public void setLoop(boolean isLoop) {
		this.isLoop = isLoop;
	}
	
}
