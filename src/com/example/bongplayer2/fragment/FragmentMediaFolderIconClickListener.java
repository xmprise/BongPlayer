package com.example.bongplayer2.fragment;

import android.content.IntentFilter;

import com.example.bongplayer2.dialog.ListMenuDialog;
import com.example.bongplayer2.interfaces.IconClickListener;

public final class FragmentMediaFolderIconClickListener implements IconClickListener{
	private FragmentMediaFolder fragmentMediafolder;
	
	public FragmentMediaFolderIconClickListener(FragmentMediaFolder fragmentMediafolder)
	{
		this.fragmentMediafolder = fragmentMediafolder;
	}

	@Override
	public void onIconClick(/*param~~*/) {
		// TODO Auto-generated method stub
		
		//if(param != null)
		//fragmentMedia.buildAction();
		ListMenuDialog listMenuDialog = new ListMenuDialog(fragmentMediafolder.getActivity(), 1, fragmentMediafolder);
	}
}