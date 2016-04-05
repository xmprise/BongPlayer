package com.example.bongplayer2.Listener;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.provider.MediaStore.Video;

import java.io.File;

import com.example.bongplayer2.R;
import com.example.bongplayer2.Utils.CursorUtils;
import com.example.bongplayer2.base.IDataChangeListener;
import com.example.bongplayer2.helper.MediaHelper;

public final class DeleteFileOKListener
implements DialogInterface.OnClickListener
{
	private File[] deleteFileArray; 
	private MediaHelper mediaHelper;		

	public DeleteFileOKListener(MediaHelper mediaHelper, File[] deleteFileArray)
	{
		this.deleteFileArray = deleteFileArray;
		this.mediaHelper = mediaHelper;
	}

	public final void onClick(DialogInterface paramDialogInterface, int paramInt)
	{
		try
		{
			ContentResolver contentResolver = mediaHelper.getContext().getContentResolver();

			for(File file : deleteFileArray)
			{
				String str = file.getAbsolutePath();
				if ((!file.isDirectory()) && (file.delete()))
				{
					long mediaID = mediaHelper.getIdByData(file.getAbsolutePath());

					if (mediaID >= 0L)
						contentResolver.delete(ContentUris.withAppendedId(Video.Media.EXTERNAL_CONTENT_URI, mediaID), null, null);
//					session.remove(str);
//					session.remove(str + ".last");
				}
			}
			
			if (mediaHelper.getiDataChangeListener() != null)
				mediaHelper.getiDataChangeListener().changed();

		}
		catch (Exception localException)
		{
			//MediaHelper.a(this.b, R.string.file_explorer_delete_failed, new Object[0]);
		}
	}
}