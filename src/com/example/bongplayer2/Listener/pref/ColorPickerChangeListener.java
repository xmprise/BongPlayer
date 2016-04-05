package com.example.bongplayer2.Listener.pref;

import com.example.bongplayer2.adapter.PreferencesExpanableListAdapter;
import com.yixia.zi.preference.APreference;
import com.yixia.zi.widget.colorpicker.ColorPickerDialog.OnColorChangedListener;

public final class ColorPickerChangeListener
implements OnColorChangedListener
{
	private APreference aPreference;
	private String key;
	private PreferencesExpanableListAdapter preferencesExpanableListAdapter;
	
	public ColorPickerChangeListener(APreference paramAPreference, String paramString, PreferencesExpanableListAdapter preferencesExpanableListAdapter)
	{
		this.aPreference = paramAPreference;
		this.key = paramString;
		this.preferencesExpanableListAdapter = preferencesExpanableListAdapter;
	}

	public final void onColorChanged(int paramInt)
	{
		aPreference.put(key, paramInt);
		preferencesExpanableListAdapter.groupSelectColor.setBackgroundColor(paramInt);
		preferencesExpanableListAdapter.notifyDataSetChanged();
	}
}