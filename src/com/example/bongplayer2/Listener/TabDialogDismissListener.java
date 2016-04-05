package com.example.bongplayer2.Listener;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import com.yixia.zi.preference.APreference;
import com.example.bongplayer2.dialog.TabDialog;

public final class TabDialogDismissListener
implements DialogInterface.OnDismissListener
{
	private TabDialog tabDialog;
	public TabDialogDismissListener(TabDialog paramTabDialog)
	{
		tabDialog = paramTabDialog;
	}

	public final void onDismiss(DialogInterface paramDialogInterface)
	{
		((Activity)tabDialog.getCtx()).setRequestedOrientation(tabDialog.getaPreference().getInt("vplayer_screen_orientation", 0));
	}
}