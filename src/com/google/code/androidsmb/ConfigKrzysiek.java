package com.google.code.androidsmb;

public class ConfigKrzysiek{
	
	String URL;
	String path;
	String mime;
	String username;
	String password;
	
	public ConfigKrzysiek(){
		URL = "http://127.0.0.1:7887";
		path = "smb://192.168.49.10:7771/JLAN/Movie/1.mp4";
		mime = "video/mp4";
		username = "jlansrv";
		password = "jlan";
	}
}
