package com.example.bongplayer2.Listener;

import com.example.bongplayer2.fragment.PreferenceFragment;

import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;

public final class PreferenceFragmentExpandListener implements ExpandableListView.OnGroupExpandListener
{
	private PreferenceFragment preferenceFragment;
	
	public PreferenceFragmentExpandListener(PreferenceFragment paramVPreferenceFragment)
	{
		preferenceFragment = paramVPreferenceFragment;
	}

	public final void onGroupExpand(int paramInt)
	{
		int i = 0;
		int groupCnt = preferenceFragment.preferencesExpanableListAdapter.getGroupCount();
		
//		while (i < j)
//		{
//			if (paramInt != i)
//				PreferenceFragment.b(this.a).collapseGroup(i);
//			i++;
//		}
	}
}