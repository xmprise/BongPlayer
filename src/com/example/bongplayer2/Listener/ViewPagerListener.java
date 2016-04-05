package com.example.bongplayer2.Listener;

import com.actionbarsherlock.app.ActionBar;
import com.example.bongplayer2.BongPlayerExplorer;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.util.Log;
import com.example.bongplayer2.fragment.BaseFragment;
public final class ViewPagerListener extends ViewPager.SimpleOnPageChangeListener{
	private BongPlayerExplorer bongPlayerExplorer;
	
	private final String LOGTAG = "BongPlayerExplorer ViewPagerListener";
	public ViewPagerListener(BongPlayerExplorer paramBongPlayerExplorer)
	{
		bongPlayerExplorer = paramBongPlayerExplorer;
	}

	//수정 필요
	public final void onPageScrollStateChanged(int paramInt)
	{
		ViewPager viewPager = bongPlayerExplorer.getViewPager();
		
		Log.d(LOGTAG, "onPageScrollStateChanged");
		Log.d(LOGTAG, "onPageScrollStateChanged paramInt>>"+ paramInt);

		if (paramInt != 0)
		{
			viewPager.requestDisallowInterceptTouchEvent(true);
		}
		else
		{
			viewPager.requestDisallowInterceptTouchEvent(false);
		}
	}

	public final void onPageSelected(int paramInt)
	{
		bongPlayerExplorer.getSupportActionBar().setSelectedNavigationItem(paramInt);
		//bongPlayerExplorer.baseFragment = (BaseFragment)bongPlayerExplorer.getFragmentPagerAdapter().getItem(paramInt);
		//	    BongPlayerExplorer.a(this.a, BongPlayerExplorer.a(this.a, paramInt));
		bongPlayerExplorer.getSharedPreferences().edit().putInt("last_tab", paramInt).commit();
	}
}
