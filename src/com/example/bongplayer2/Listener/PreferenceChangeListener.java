package com.example.bongplayer2.Listener;

import com.example.bongplayer2.VideoActivity;
import com.yixia.zi.preference.APreference.OnPreferenceChangedListener;

public class PreferenceChangeListener implements OnPreferenceChangedListener
{
	private VideoActivity videoActivity;
	public PreferenceChangeListener(VideoActivity videoActivity)
	{
		this.videoActivity = videoActivity;
	}

	public final void onPreferenceChanged()
	{
		//videoActivity.get.sendEmptyMessage(5);
	}
}
