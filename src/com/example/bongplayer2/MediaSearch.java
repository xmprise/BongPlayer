package com.example.bongplayer2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.example.bongplayer2.Utils.CursorUtils;
import com.example.bongplayer2.bean.VideoFileInfo;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore.Video;
import android.util.Log;

public class MediaSearch {
	private Cursor entireVideoCursor = null;

	private ContentResolver resolver;
	private static ArrayList<FolderInfo> folderInfoList = null; 
	private static HashMap<String, ArrayList<VideoFileInfo>> videoFileInfoMap = null;
	
	private final String LOCATION_EXTERNAL = "external"; // private final? public static final?
	private final String LOCATION_INTERNAL = "internal";
	
	private final String VIDEO_ID = Video.VideoColumns._ID;
	private final String VIDEO_PATH = Video.VideoColumns.DATA;
	private final String VIDEO_NAME = Video.VideoColumns.DISPLAY_NAME;
	private final String VIDEO_SIZE = Video.VideoColumns.SIZE;
	private final String VIDEO_TIME = Video.VideoColumns.DURATION;
	private final String VIDEO_RESOLUTION = Video.VideoColumns.RESOLUTION;
	private final String VIDEO_LNG = Video.VideoColumns.LANGUAGE;
	private final String VIDEO_PARENT_PATH = "replace("+VIDEO_PATH+", "+VIDEO_NAME+",'')";
	
	public MediaSearch(ContentResolver resolver) {
		this.resolver = resolver;
	}
		
	public void loadVideoInfo()
	{
		Uri[] uri = new Uri[] { Video.Media.EXTERNAL_CONTENT_URI, Video.Media.INTERNAL_CONTENT_URI };
		
		String[] proj = {"distinct replace("+VIDEO_PATH+", "+VIDEO_NAME+",'')", VIDEO_PATH};
		ArrayList<String> videoPathInfoList = new ArrayList<String>(); 
		String[] selectionArgs = new String[1];
		
		
		selectionArgs[0] = ("/storage/emulated/0/Movies/");
		//entireVideoCursor = resolver.query(uri[0], proj, null, null, null);
		entireVideoCursor = resolver.query(uri[0], CursorUtils.proj2, CursorUtils.VIDEO_PARENT_PATH+" = ?", selectionArgs, null);
		//entireVideoCursor = resolver.query(uri[0], CursorUtils.proj2, null, null, null);
		entireVideoCursor.moveToFirst();
		
		if (entireVideoCursor != null && entireVideoCursor.moveToFirst()) {	
			do {
				videoPathInfoList.add(entireVideoCursor.getString(0));
				Log.d("mediasearch", "entireVideoCursor.getString(1)>>>>"+entireVideoCursor.getString(1));
			} while (entireVideoCursor.moveToNext());
		}
		Log.d("videoPathInfoList.Count",
				"count>>"+ videoPathInfoList.size());
		
		ArrayList<String> uniqVideoPath = new ArrayList<String>(new HashSet<String>(videoPathInfoList));
		Log.d("uniq",""+uniqVideoPath);

		//String where = VIDEO_PATH + " like ?";
		
		folderInfoList = new ArrayList<FolderInfo>();
		videoFileInfoMap = new HashMap<String, ArrayList<VideoFileInfo>>();
		
		for(int index=0; index<uniqVideoPath.size();index++)
		{
			String[] proj2 = {VIDEO_ID, VIDEO_PARENT_PATH, VIDEO_PATH, VIDEO_NAME, VIDEO_SIZE, VIDEO_TIME, VIDEO_RESOLUTION, VIDEO_LNG};
			//String whereArgs[] = {uniq.get(index)+"%"};
			Cursor videoListCursor = resolver.query(Video.Media.EXTERNAL_CONTENT_URI, proj2, null, null, null);
			
			ArrayList<VideoFileInfo> videoFileInfoList = new ArrayList<VideoFileInfo>();
			FolderInfo folderInfo = new FolderInfo();
			
			int count = 0;
			
			if(videoListCursor != null && videoListCursor.getCount() > 0) {
				videoListCursor.moveToFirst();
			    while(!videoListCursor.isAfterLast()) {
			    	
			    	VideoFileInfo videoFileInfo = new VideoFileInfo();
			    	if(uniqVideoPath.get(index).equals(getColumeValue(videoListCursor, proj2[1]))) 
			    	{
				    	videoFileInfo.setVideoPath(getColumeValue(videoListCursor, proj2[2]));
				    	videoFileInfo.setVideoName(getColumeValue(videoListCursor, proj2[3]));
				    	videoFileInfo.setVideoSize(getColumeValue(videoListCursor, proj2[4]));
				    	videoFileInfo.setVideoTime(getColumeValue(videoListCursor, proj2[5]));
				    	videoFileInfo.setVideoResolution(getColumeValue(videoListCursor, proj2[6]));
				    	//videoFileInfo.setVideoThumnail(ThumbnailUtils.createVideoThumbnail(getColumeValue(videoListCursor, proj2[2]), android.provider.MediaStore.Video.Thumbnails.MICRO_KIND));
				    	
				    	System.out.println("2"+getColumeValue(videoListCursor, proj2[2]));
				    	System.out.println("3"+getColumeValue(videoListCursor, proj2[3]));
				    	System.out.println("4"+getColumeValue(videoListCursor, proj2[4]));
				    	System.out.println("5"+getColumeValue(videoListCursor, proj2[5]));
				    	System.out.println("6"+getColumeValue(videoListCursor, proj2[6]));
			    	
			    		count++;
			    	}
			    	videoFileInfoList.add(videoFileInfo);
			    	videoListCursor.moveToNext();
			    }
			}
			String folderName[] = uniqVideoPath.get(index).split("/", 0);
			//folderInfo.setFolderName(folderName);
			folderInfo.setFolderName(folderName[folderName.length-1]);
			folderInfo.setFolderPath(uniqVideoPath.get(index));
			folderInfo.setVideoNum(count);
			folderInfoList.add(folderInfo);
			
			videoFileInfoMap.put(uniqVideoPath.get(index), videoFileInfoList);
			
			videoListCursor.close();
		}
		
		entireVideoCursor.close();
	}
	
	public String getColumeValue(Cursor cursor, String cname)
	{
		return cursor.getString(cursor.getColumnIndex(cname));
	}


	public static ArrayList<FolderInfo> getFolderInfoList() {
		return folderInfoList;
	}
	
	public static ArrayList<VideoFileInfo> getVideoFileInfo(String key){
		
		return videoFileInfoMap.get(key);
	}

	public static HashMap<String, ArrayList<VideoFileInfo>> getVideoFileInfoMap() {
		return videoFileInfoMap;
	}
	
	
}

