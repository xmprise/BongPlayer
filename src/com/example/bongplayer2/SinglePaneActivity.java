package com.example.bongplayer2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public abstract class SinglePaneActivity extends BaseActivity
{
	private final String LOGTAG ="SinglePaneActivity";
	private Fragment fragment;

	public Fragment getFragment()
	{
		return fragment;
	}

	protected void onCreate(Bundle paramBundle)
	{
		Log.d(LOGTAG, "onCreate");
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_singlepane_empty);

		String title = getIntent().getStringExtra("title");

		if(title != null)
		{
			Log.d(LOGTAG, "onCreate title not null");
			setTitle(title);

			fragment = onCreatePane();
			Log.d(LOGTAG, "create FragmentMedia");
			fragment.setArguments(intentToFragmentArguments(getIntent()));
			getSupportFragmentManager().beginTransaction().add(R.id.root_container, fragment, "single_pane").commit();
		}
		else
		{
			Log.d(LOGTAG, "onCreate title null");
			title = (String) getTitle();
			fragment = getSupportFragmentManager().findFragmentByTag("single_pane");
		}



	}

	protected abstract Fragment onCreatePane();
}
