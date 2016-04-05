package com.example.bongplayer2.Listener;

import com.example.bongplayer2.MediaController;

import android.view.View;
import android.view.View.OnClickListener;

public final class ViewLockBtnListener implements View.OnClickListener{
	
	private MediaController mediaController;
	public ViewLockBtnListener(MediaController mediaController)
	{
		this.mediaController = mediaController;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		mediaController.hide();
		boolean lock = false;
	    if (!mediaController.isLocked())
	    	lock = true;
	    else
	    	lock = false;  
		mediaController.screenLock(lock);
    	mediaController.show();
	}
}
