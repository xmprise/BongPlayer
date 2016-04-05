package com.example.bongplayer2.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.bongplayer2.R;
import com.example.bongplayer2.Listener.DeleteFileOKListener;
import com.example.bongplayer2.Listener.DeleteFolderOKListener;
import com.example.bongplayer2.Listener.RenameFolderOKListener;
import com.example.bongplayer2.Listener.RenameOKListener;
import com.example.bongplayer2.base.IDataChangeListener;

public class MediaHelper
{
	protected static final String SELECTION_MEDIA = "_directory = ?";
	private Context context;
	//	private Session session;
	private IDataChangeListener iDataChangeListener;

	public MediaHelper(Context context)
	{
		this.context = context;
		//		this.session = new Session(context);
	}

	public MediaHelper(Context context, IDataChangeListener iDataChangeListener)
	{
		this.context = context;
		//		this.session = new Session(context);
		this.iDataChangeListener = iDataChangeListener;
	}

	public static int checkRootDirectroy(ContentResolver paramContentResolver, Context paramContext)
	{
		//		try
		//		{
		//			Uri localUri = StreamProvider.CONTENT_URI;
		//			String[] arrayOfString = new String[1];
		//			arrayOfString[0] = "_id";
		//			Cursor localCursor2 = paramContentResolver.query(localUri, arrayOfString, "parent_id = -1", null, null);
		//			localCursor1 = localCursor2;
		//			if (localCursor1 == null);
		//		}
		//		finally
		//		{
		//			try
		//			{
		//				int j;
		//				if (localCursor1.moveToNext())
		//					j = localCursor1.getInt(localCursor1.getColumnIndex("_id"));
		//				for (int i = j; ; i = 1)
		//				{
		//					if (localCursor1 != null)
		//						localCursor1.close();
		//					return i;
		//					ContentValues localContentValues = new ContentValues();
		//					localContentValues.put("title", paramContext.getString(2131361885));
		//					localContentValues.put("is_directory", "1");
		//					localContentValues.put("modify_time", Long.valueOf(System.currentTimeMillis()));
		//					localContentValues.put("access_count", Integer.valueOf(0));
		//					localContentValues.put("parent_id", Integer.valueOf(-1));
		//					paramContentResolver.insert(StreamProvider.CONTENT_URI, localContentValues);
		//				}
		//				localObject1 = finally;
		//				Cursor localCursor1 = null;
		//				label166: if (localCursor1 != null)
		//					localCursor1.close();
		//				throw localObject1;
		//			}
		//			finally
		//			{
		//				break label166;
		//			}
		//		}
		return 1;
	}

	public void deleteCheckedGroup(ArrayList paramArrayList)
	{
		//		new AlertDialog.Builder(context).setIcon(17301543).setTitle(2131361867).setMessage(2131361870).setPositiveButton(2131361877, new ob(this, paramArrayList)).setNegativeButton(2131361878, null).show();
	}

	public void deleteDir(File paramFile)
	{
		//		if ((paramFile == null) || (context == null));
		//		while (true)
		//		{
		//			return;
		//			new AlertDialog.Builder(context).setIcon(17301543).setTitle(2131361867).setMessage(2131361870).setPositiveButton(2131361877, new nx(this, paramFile)).setNegativeButton(2131361878, null).show();
		//		}
	}

	public void deleteFile(Collection paramCollection)
	{
		if (paramCollection != null)
		{
			String[] deleteFileArray = new String[paramCollection.size()];
			paramCollection.toArray(deleteFileArray);
			deleteFile(deleteFileArray);
		}
	}

	public void deleteFile(File[] deleteFileArray)
	{
		if ((deleteFileArray == null) || (deleteFileArray.length == 0) || (context == null))
			return;
		else
		{
			new AlertDialog.Builder(context).setIcon(R.drawable.warning).setTitle("삭제").setMessage("파일을 정말 삭제하시겠습니까?").setPositiveButton("확인", new DeleteFileOKListener(this, deleteFileArray)).setNegativeButton("취소", null).show();
		}
	}

	public void deleteFile(String[] deleteFileArray)
	{
		if ((deleteFileArray == null) || (deleteFileArray.length == 0) || (context == null))
			return;
		else
		{
			ArrayList arrayList = new ArrayList();
			int i = deleteFileArray.length;

			for (int j = 0; j < i; j++)
				arrayList.add(new File(deleteFileArray[j]));

			File[] arrayOfFile = new File[arrayList.size()];
			arrayList.toArray(arrayOfFile);

			deleteFile(arrayOfFile);
		}
	}

	public void deleteFileNoConfim(File paramFile)
	{
		//    String str = paramFile.getAbsolutePath();
		//    if ((!paramFile.isDirectory()) && (paramFile.delete()))
		//    {
		//      long l = getIdByData(paramFile.getAbsolutePath());
		//      if (l >= 0L)
		//        context.getContentResolver().delete(ContentUris.withAppendedId(MediaStore.Video.Media.CONTENT_URI, l), null, null);
		//      context.remove(str);
		//      context.remove(str + ".last");
		//    }
	}

	public void deleteFolder(Collection paramCollection)
	{
		if (paramCollection != null)
		{
			String[] deleteFolderArray = new String[paramCollection.size()];
			paramCollection.toArray(deleteFolderArray);
			deleteFolder(deleteFolderArray);
		}
	}

	public void deleteFolder(String[] deleteFolderArray)
	{
		if ((deleteFolderArray == null) || (deleteFolderArray.length == 0) || (context == null))
			return;

		Object[] temp;
		String str = null;

		if (deleteFolderArray.length == 1)
		{
			temp = new Object[1];
			temp[0] = deleteFolderArray[0];

			str = context.getString(R.string.dialog_text_confirm_delete_folder, temp);
		}
		else
			str = context.getString(R.string.dialog_text_confirm_delete_folder);

		new AlertDialog.Builder(context).setIcon(android.R.drawable.ic_dialog_alert).setTitle("폴더삭제").setMessage(str).setPositiveButton("확인", new DeleteFolderOKListener(this, deleteFolderArray)).setNegativeButton("취소", null).show();
	}

	public long getIdByData(String paramString)
	{
		return 1;
	}

	public void hideFolder(Collection paramCollection)
	{
		if (paramCollection != null)
		{
			String[] arrayOfString = new String[paramCollection.size()];
			paramCollection.toArray(arrayOfString);
			hideFolder(arrayOfString);
		}
	}

	public void hideFolder(String[] paramArrayOfString)
	{
		//		if ((paramArrayOfString == null) || (paramArrayOfString.length == 0) || (context == null));
		//		while (true)
		//		{
		//			return;
		//			new AlertDialog.Builder(context).setIcon(17301543).setTitle(2131361868).setMessage(2131361872).setPositiveButton(2131361877, new nz(this, paramArrayOfString)).setNegativeButton(2131361878, null).show();
		//		}
	}

	public void renameFile(File paramFile, long mediaID)
	{
		if (context == null)
			return;
		else
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(context).setIcon(android.R.drawable.ic_dialog_alert);
			View view = LayoutInflater.from(context).inflate(R.layout.dialog_one_edittext, null);
			EditText editText = (EditText)view.findViewById(R.id.dialog_one_edittext_edittext);

			builder.setTitle(paramFile.getName().trim());
			editText.setText(paramFile.getName());

			builder.setView(view);
			builder.setPositiveButton(R.string.dialog_button_ok, new RenameOKListener(this, editText, paramFile, mediaID));
			builder.setNegativeButton(R.string.dialog_button_cancel, null);
			builder.show();
		}
	}
	
	public void renameFolderFile(File paramFile, String folderPath)
	{
		if (context == null)
			return;
		else
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(context).setIcon(android.R.drawable.ic_dialog_alert);
			View view = LayoutInflater.from(context).inflate(R.layout.dialog_one_edittext, null);
			EditText editText = (EditText)view.findViewById(R.id.dialog_one_edittext_edittext);

			builder.setTitle(paramFile.getName().trim());
			editText.setText(paramFile.getName());

			builder.setView(view);
			builder.setPositiveButton(R.string.dialog_button_ok, new RenameFolderOKListener(this, editText, paramFile, folderPath));
			builder.setNegativeButton(R.string.dialog_button_cancel, null);
			builder.show();
		}
	}

	public Context getContext() {
		return context;
	}

	public IDataChangeListener getiDataChangeListener() {
		return iDataChangeListener;
	}

}
