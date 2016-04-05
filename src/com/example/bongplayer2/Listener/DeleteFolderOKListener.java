package com.example.bongplayer2.Listener;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.provider.MediaStore.Video;

import com.example.bongplayer2.Utils.CursorUtils;
import com.example.bongplayer2.base.IDataChangeListener;
import com.example.bongplayer2.helper.MediaHelper;

public class DeleteFolderOKListener implements DialogInterface.OnClickListener {

	private String[] deleteFolderArray; 
	private MediaHelper mediaHelper;		

	public DeleteFolderOKListener(MediaHelper mediaHelper, String[] deleteFolderArray)
	{

		this.deleteFolderArray = deleteFolderArray;
		this.mediaHelper = mediaHelper;
	}

	public final void onClick(DialogInterface paramDialogInterface, int paramInt)
	{
		try
		{
			ContentResolver contentResolver = mediaHelper.getContext().getContentResolver();
			
			for (String str : deleteFolderArray)
			{
				Uri uri = Video.Media.EXTERNAL_CONTENT_URI;
				String[] tempString = new String[1];
				tempString[0] = str;
				contentResolver.delete(uri, CursorUtils.VIDEO_PARENT_PATH+" = ?", tempString);
			}
			if (mediaHelper.getiDataChangeListener() != null)
				mediaHelper.getiDataChangeListener().changed();
			return;
		}
		catch (Exception localException)
		{
			//MediaHelper.a(this.b, 2131361828, new Object[0]);
		}
	}
}
