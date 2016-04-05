//package com.example.bongplayer2.helper;
//
//import android.net.Uri;
//import android.net.Uri.Builder;
//import io.vov.vitamio.provider.MediaStore.Video.Media;
//import java.util.List;
//import me.abitno.media.provider.StreamProvider;
//
//public class ScheduleHelper
//{
//  public static Uri buildLocalMediaUri()
//  {
//    return MediaStore.Video.Media.CONTENT_URI.buildUpon().appendPath("local").build();
//  }
//
//  public static Uri buildMediaDirectoryUri(String paramString)
//  {
//    return MediaStore.Video.Media.CONTENT_URI.buildUpon().appendPath(paramString).build();
//  }
//
//  public static Uri buildRemoteMediaUri()
//  {
//    return MediaStore.Video.Media.CONTENT_URI.buildUpon().appendPath("remote").build();
//  }
//
//  public static Uri buildSearchUri(String paramString)
//  {
//    return MediaStore.Video.Media.CONTENT_URI.buildUpon().appendPath("search").appendPath(paramString).build();
//  }
//
//  public static Uri buildStreamDirectoryUri(String paramString)
//  {
//    return StreamProvider.CONTENT_URI.buildUpon().appendPath(paramString).build();
//  }
//
//  public static String getMediaDirectory(Uri paramUri)
//  {
//    return (String)paramUri.getPathSegments().get(2);
//  }
//
//  public static String getStreamDirectoryString(Uri paramUri)
//  {
//    return (String)paramUri.getPathSegments().get(1);
//  }
//
//  public static boolean isSearchUri(Uri paramUri)
//  {
//    List localList = paramUri.getPathSegments();
//    if ((localList.size() >= 3) && ("search".equals(localList.get(2))));
//    for (boolean bool = true; ; bool = false)
//      return bool;
//  }
//}
