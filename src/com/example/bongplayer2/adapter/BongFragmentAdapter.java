package com.example.bongplayer2.adapter;

import com.example.bongplayer2.BongPlayerExplorer;
import com.example.bongplayer2.fragment.BaseFragment;
import com.example.bongplayer2.fragment.FragmentMediaFolder;
import com.example.bongplayer2.fragment.FragmentStream;
import com.yixia.zi.preference.APreference;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class BongFragmentAdapter extends FragmentPagerAdapter{

	private BongPlayerExplorer bongPlayerExplorer;
	private BaseFragment baseFragment;
	
	public BongFragmentAdapter(BongPlayerExplorer paramFileExplorer, FragmentManager paramFragmentManager)
	{
	    super(paramFragmentManager);
	    this.bongPlayerExplorer = paramFileExplorer;
	    
	}
	 
	@Override
	public Fragment getItem(int num) {
		// TODO Auto-generated method stub		
		
		switch(num)
		{
		case 0:
			baseFragment = new FragmentMediaFolder();
			return baseFragment;
		case 1:
			baseFragment = new FragmentStream();
			return baseFragment;
		case 2:
			//return new FragmentMediaFolder();
		default:
			baseFragment = new FragmentMediaFolder();
			return baseFragment;
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}
}
