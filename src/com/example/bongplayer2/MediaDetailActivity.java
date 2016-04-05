package com.example.bongplayer2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;

import com.actionbarsherlock.app.ActionBar;
import com.example.bongplayer2.fragment.FragmentMedia;

public class MediaDetailActivity extends SinglePaneActivity {
		
	  private final String LOGTAG = "MediaDetailActivity";
	  private FragmentMedia fragmentMedia;

	  public void onBackPressed()
	  {
		Log.d(LOGTAG, "onBackPressed"); 
		
	    if ((fragmentMedia != null) && (fragmentMedia.onBackPressed()));
	    	finish();
	  }

	  protected void onCreate(Bundle paramBundle)
	  {
		Log.d(LOGTAG, "onCreate");
	    super.onCreate(paramBundle);
	    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	  }

	  protected Fragment onCreatePane()
	  {
		Log.d(LOGTAG, "onCreatePane");
		fragmentMedia = new FragmentMedia();
	    return fragmentMedia;
	  }
}
