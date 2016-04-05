package com.example.bongplayer2.Listener;

import com.example.bongplayer2.base.IDataChangeListener;
import com.example.bongplayer2.fragment.BaseFragment;

public class FragmentIDataChangeListener implements IDataChangeListener {
	
	private BaseFragment baseFragment;
	public FragmentIDataChangeListener(BaseFragment paramBaseFragment)
	{
		this.baseFragment = paramBaseFragment;
	}
	@Override
	public void changed() {
		// TODO Auto-generated method stub
		baseFragment.onDataChanged();
	}

}
