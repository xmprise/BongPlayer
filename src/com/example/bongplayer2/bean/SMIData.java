package com.example.bongplayer2.bean;

public class SMIData {
	long time;
	String text;
	
	public SMIData(long time, String text) {
		this.time = time;
		this.text = text;
	}
	
	public long gettime() {
		return time;
	}
	
	public String gettext() {
		return text;
	}
}