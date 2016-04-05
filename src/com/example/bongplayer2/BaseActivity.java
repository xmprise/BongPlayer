package com.example.bongplayer2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
//import com.flurry.android.FlurryAgent;
//import com.umeng.analytics.MobclickAgent;
//import me.abitno.VPlayerApplication;


public abstract class BaseActivity extends SherlockFragmentActivity
{
	private final String LOGTAG ="BaseActivity";
	protected int mTheme;

	public static Intent fragmentArgumentsToIntent(Bundle paramBundle)
	{
		
		Intent intent = new Intent();
        if (paramBundle == null) {
            return intent;
        }
        
        final Uri data = paramBundle.getParcelable("_uri");
        
        if (data != null) {
            intent.setData(data);
        }

        intent.putExtras(paramBundle);
        intent.removeExtra("_uri");
        return intent;
	}

	public static Bundle intentToFragmentArguments(Intent paramIntent)
	{
		 Bundle arguments = new Bundle();
	        if (paramIntent == null) {
	            return arguments;
	        }

	        final Uri data = paramIntent.getData();
	        String folderPath = paramIntent.getStringExtra("folderPath");
	        if (data != null) {
	            arguments.putParcelable("_uri", data);
	        }
	        
	        if (folderPath != null)
	        {
	        	arguments.putString("folderPath", folderPath);
	        }

	        final Bundle extras = paramIntent.getExtras();
	        if (extras != null) {
	            arguments.putAll(paramIntent.getExtras());
	        }

	        return arguments;
	}

	protected void onCreate(Bundle paramBundle)
	{
		//    VPlayerApplication localVPlayerApplication = (VPlayerApplication)getApplication();
		//    if (localVPlayerApplication != null)
		//    {
		//      localVPlayerApplication.setCurrentActivity(this);
		//      setTheme(VPlayerApplication.THEME);
		//    }
		//    this.mTheme = VPlayerApplication.THEME;
		super.onCreate(paramBundle);
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem)
	{
		Log.d(LOGTAG, "onOptionsItemSelected!!");
		Log.d(LOGTAG, "paramMenuItem.getItemId()>>>"+paramMenuItem.getItemId());
		return super.onOptionsItemSelected(paramMenuItem);
		//    boolean bool;
		//    switch (paramMenuItem.getItemId())
		//    {
		//    default:
		//      bool = super.onOptionsItemSelected(paramMenuItem);
		//    case 16908332:
		//    }
		//    while (true)
		//    {
		//      return bool;
		//      if ((this instanceof FileExplorerContainer))
		//      {
		//        bool = false;
		//        continue;
		//      }
		//      NavUtils.navigateUpFromSameTask(this);
		//      bool = true;
		//    }
	}

	public void onPause()
	{
		super.onPause();
		//MobclickAgent.onPause(this);
	}

	public void onResume()
	{
		super.onResume();
		//MobclickAgent.onResume(this);
	}

	public void onStart()
	{
		super.onStart();
		//FlurryAgent.onStartSession(this, "FPWRYE55S1K6XS8PEF5U");
	}

	public void onStop()
	{
		super.onStop();
		//FlurryAgent.onEndSession(this);
	}

	public void setNoCustTheme(Bundle paramBundle)
	{
		super.onCreate(paramBundle);
	}

	public void onBackPressed()
	{
		Log.d(LOGTAG,"onBackPressed!!");
		super.onBackPressed();  
	}
}