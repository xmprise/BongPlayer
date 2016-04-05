package com.example.bongplayer2;

import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.util.Log;

public class FragmentMediaScanCompleteListener implements OnScanCompletedListener{

	public final void onScanCompleted(String paramString, Uri paramUri)
	  {
	    Log.i("MangoPlayer[Explorer#Media#Folder]", "Scanned: " + paramString + " -> uri: " + paramUri);
	  }
}
