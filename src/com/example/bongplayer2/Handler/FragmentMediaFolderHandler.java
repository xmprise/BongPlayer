package com.example.bongplayer2.Handler;

import android.os.Handler;
import android.os.Message;
import com.example.bongplayer2.MediaScannerStatusReceiver;
import com.example.bongplayer2.fragment.FragmentMediaFolder;
import com.yixia.zi.utils.FileHelper;

public final class FragmentMediaFolderHandler extends Handler
{
	private FragmentMediaFolder fragmentMediaFolder;
	private boolean isRegisterReceiver;
	public FragmentMediaFolderHandler(FragmentMediaFolder fragmentMediaFolder)
	{
		this.fragmentMediaFolder = fragmentMediaFolder;
	}

	public final void handleMessage(Message paramMessage)
	{
		switch (paramMessage.what)
		{
		default:
		case 0:
			
			if (!FileHelper.sdAvailable())
			{
				//FragmentMediaFolder.a(this.a);
			}
			
			if (MediaScannerStatusReceiver.isScanning(fragmentMediaFolder.getActivity().getApplicationContext()))
			{
				fragmentMediaFolder.setRegisterReceiver(true);
				//fragmentMediaFolder.getHandler().sendEmptyMessageDelayed(0, 1000L);

			}
			else
			{
				fragmentMediaFolder.setRegisterReceiver(false);
				//fragmentMediaFolder.getHandler().removeMessages(0);
			}
			
			break;
		}
	}
}
