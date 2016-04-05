package com.example.bongplayer2.Utils;

import android.database.Cursor;
import android.provider.MediaStore.Video;

public class CursorUtils {

	public static final String VIDEO_ID = Video.VideoColumns._ID;
	public static final String VIDEO_PATH = Video.VideoColumns.DATA;
	public static final String VIDEO_NAME = Video.VideoColumns.DISPLAY_NAME;
	public static final String VIDEO_SIZE = Video.VideoColumns.SIZE;
	public static final String VIDEO_TIME = Video.VideoColumns.DURATION;
	public static final String VIDEO_RESOLUTION = Video.VideoColumns.RESOLUTION;
	public static final String VIDEO_LNG = Video.VideoColumns.LANGUAGE;
	public static final String VIDEO_PARENT_PATH = "replace("+VIDEO_PATH+", "+VIDEO_NAME+",'')";
	
	public static final String UNIQ_VIDEO_PARENT_PATH = "distinct replace("+VIDEO_PATH+", "+VIDEO_NAME+",'')";
	
	public static final String[] proj2 = {UNIQ_VIDEO_PARENT_PATH, VIDEO_ID, VIDEO_PATH,VIDEO_NAME,VIDEO_SIZE,VIDEO_TIME,VIDEO_RESOLUTION,VIDEO_RESOLUTION};
	
	public static String getColumeValue(Cursor cursor, String cname)
	{
		return cursor.getString(cursor.getColumnIndex(cname));
	}

	public static String folderPathToFolderName(Cursor cursor)
	{
		String folderPath = cursor.getString(0); // folder Path

		String folderPathSplit[] = folderPath.split("/", 0);
		
		return folderPathSplit[folderPathSplit.length-1];
	}
}
