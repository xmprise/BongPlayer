package com.example.bongplayer2.Listener;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.provider.MediaStore;
import android.widget.EditText;
import com.yixia.zi.utils.FileHelper;
import java.io.File;

import com.example.bongplayer2.Utils.CursorUtils;
import com.example.bongplayer2.base.IDataChangeListener;
import com.example.bongplayer2.helper.MediaHelper;

public final class RenameOKListener
implements DialogInterface.OnClickListener
{
	private MediaHelper mediaHelper;
	private long mediaID;
	private EditText editText;
	private File file;
	public RenameOKListener(MediaHelper paramMediaHelper, EditText paramEditText, File paramFile, long mediaID)
	{
		mediaHelper = paramMediaHelper;
		editText = paramEditText;
		file = paramFile;
		this.mediaID = mediaID;
	}

	public final void onClick(DialogInterface paramDialogInterface, int paramInt)
	{
		String textValue = editText.getText().toString().trim();

		if ((textValue == null) || (textValue.trim().equals("")) || (textValue.trim().equals(file.getName())))
			return;
		else
		{
			String absolutePath = file.getAbsolutePath(); 
			File parentFile = new File(file.getParent(), textValue.trim());
			//			try
			//			{
			//				if (!parentFile.exists());
			//				Object[] arrayOfObject = new Object[1];
			//				arrayOfObject[0] = parentFile.getName().trim();
			//				MediaHelper.a(localMediaHelper, 2131361835, arrayOfObject);
			//			}
			//			catch (SecurityException localSecurityException)
			//			{
			//				//MediaHelper.a(this.d, 2131361829, new Object[0]);
			//			}

			if (!file.renameTo(parentFile))
			{

				//			if (mediaID < 0L)
				//				mediaID = mediaHelper.getIdByData(this.b.getAbsolutePath());
				if (mediaID >= 0L)
				{
					ContentValues contentValues = new ContentValues();
					contentValues.put("_data", parentFile.getAbsolutePath());
					contentValues.put("title", FileHelper.getFileNameForTitle(textValue));
					mediaHelper.getContext().getContentResolver().update(ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaID), contentValues, null, null);
				}
			}
			
			//			String str3 = parentFile.getAbsolutePath();
			//			MediaHelper.session(this.d).put(str3, MediaHelper.b(this.d).getString(absolutePath, ""));
			//			MediaHelper.session(this.d).put(str3 + ".last", MediaHelper.b(this.d).getFloat(absolutePath + ".last", 7.7F));
			//			MediaHelper.session(this.d).remove(absolutePath);
			//			MediaHelper.session(this.d).remove(absolutePath + ".last");

			//			if (MediaHelper.c(this.d) == null)
			//				continue;
			//			MediaHelper.iDataChangeListener(this.d).changed();
		}
	}
}