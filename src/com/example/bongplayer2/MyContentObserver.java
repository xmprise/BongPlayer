package com.example.bongplayer2;

import com.example.bongplayer2.fragment.FragmentMedia;

import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

public class MyContentObserver extends ContentObserver {
	private final String LOGTAG = "MyContentObserver";
	private FragmentMedia fragmentMedia;
	public MyContentObserver(FragmentMedia paramFragmentMedia, Handler handler) {
		super(handler);
		// TODO Auto-generated constructor stub
		
		fragmentMedia = paramFragmentMedia;
	}

	public final void onChange(boolean paramBoolean)
	{
		Log.d(LOGTAG, "onChange");
		if (fragmentMedia.getActivity() == null)
		{
			Log.d(LOGTAG, "getActivity null");
			return;
		}
		else
		{
			Loader<Cursor> loader = fragmentMedia.getLoaderManager().getLoader(2);
			Log.d(LOGTAG, "getActivity not null");
			if (loader != null)
			{
				Log.d(LOGTAG, "loader not null forceLoad");
				loader.forceLoad();
			}
		}
	}

}
