package com.example.bongplayer2.Listener;

import com.example.bongplayer2.fragment.BaseFragment;

import android.view.View;
import android.view.View.OnClickListener;

public final class BaseFragmentListener implements View.OnClickListener {

	private BaseFragment baseFragment;
	
	public BaseFragmentListener(BaseFragment baseFragment)
	{
		this.baseFragment = baseFragment;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		baseFragment.onEditMenuClick(v);
	}

}
