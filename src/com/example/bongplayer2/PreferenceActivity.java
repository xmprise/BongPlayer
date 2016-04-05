package com.example.bongplayer2;

import com.actionbarsherlock.view.MenuItem;
import com.example.bongplayer2.fragment.PreferenceFragment;
import com.yixia.zi.utils.UIUtils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class PreferenceActivity extends BaseActivity
{
	private final String LOGTAG ="PreferenceActivity";
	private View fragmentPreference;
	private View fragmentPreferenceCategory;
	private PreferenceFragment preferenceFragment;
	private boolean isNotTablet;
	protected View mPreferenceArrow;

	@SuppressLint("NewApi")
	public void onBackPressed()
	{
		Log.d(LOGTAG,"onBackPressed");

		if ((isNotTablet) && (fragmentPreference.getVisibility() == View.VISIBLE))
		{

			Log.d(LOGTAG,"onBackPressed1 ");
			fragmentPreferenceCategory.setVisibility(View.VISIBLE);
			fragmentPreference.setVisibility(View.GONE);
			setTitle(R.string.preferences_title);

			if (!UIUtils.hasHoneycomb())

				recreate();

		}
		else
		{
			Log.d(LOGTAG,"onBackPressed2 ");
			//			Intent intent = getIntent();
			finish();
			//			startActivity(intent);
		}
		super.onBackPressed();
	}

	protected void onCreate(Bundle paramBundle)
	{
		Log.d(LOGTAG,"onCreate");

		super.onCreate(paramBundle);

		setContentView(R.layout.preference);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		fragmentPreference = findViewById(R.id.fragment_preference);
		fragmentPreferenceCategory = findViewById(R.id.fragment_preference_category);

		mPreferenceArrow = findViewById(R.id.preference_arrow);

		if (!UIUtils.isTablet(this))
		{
	
			return ;
		}
		else
		{
			isNotTablet = false;
		}
		
		if (isNotTablet)
			fragmentPreference.setVisibility(View.GONE);

		preferenceFragment = ((PreferenceFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_preference));
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem)
	{
		Log.d(LOGTAG,"onOptionsItemSelected");
		
		switch (paramMenuItem.getItemId())
		{
		default:
		case android.R.id.home:
			onBackPressed();
			break;
		}
		return super.onOptionsItemSelected(paramMenuItem);
	}

	public void switchCategory(int paramInt)
	{
		Log.d(LOGTAG,"switchCategory");
		preferenceFragment.changePosition(paramInt);

		if (isNotTablet)
		{
			fragmentPreferenceCategory.setVisibility(View.GONE);
			fragmentPreference.setVisibility(View.VISIBLE);
		}
	}
}