package com.example.bongplayer2.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.bongplayer2.R;
import com.example.bongplayer2.TunerTabLayout;
import com.example.bongplayer2.interfaces.rt;
import com.example.bongplayer2.interfaces.ru;
import com.example.bongplayer2.interfaces.tv;
import com.example.bongplayer2.interfaces.uu;

public final class TunerDialog extends AlertDialog
implements ru, tv
{
	public static int a;
	private rt b;
	private TunerTabLayout tunerTabLayout;
	private View[] tunerTabViewArray = new View[2];
	private TunerScreen tunerScreen;
	private TunerSearch tunerSearch;
	private TunerTouch tunerTouch;
	private TunerText tunerText;
	private TunerPlace tunerPlace;
	private sk j;
	public static int preTabIndex;
	
	public TunerDialog(Context paramContext, rt paramrt, uu paramuu, int tabIndex)
	{
		super(paramContext);
		this.b = paramrt;
		View view = getLayoutInflater().inflate(R.layout.tuner, null);

		setView(view);
		
		tunerTabLayout = ((TunerTabLayout)view.findViewById(R.id.tabList));
		tunerTabViewArray[0] = view.findViewById(R.id.screenPane);
		tunerTabViewArray[1] = view.findViewById(R.id.subtitleTextPane);
//		tunerTabViewArray[1] = view.findViewById(R.id.dragPane);
//		tunerTabViewArray[2] = view.findViewById(R.id.navigationPane);
//		tunerTabViewArray[4] = view.findViewById(R.id.subtitleLayoutPane);		

		tunerScreen = new TunerScreen(this, (ViewGroup)tunerTabViewArray[0], this.b, paramuu);
		tunerText = new TunerText(this, (ViewGroup)tunerTabViewArray[1], this.b, paramuu);
		
//		tunerTouch = new TunerTouch(this, (ViewGroup)tunerTabViewArray[1], this, this.b, paramuu);
//		tunerSearch = new TunerSearch(this, (ViewGroup)tunerTabViewArray[2], this.b);
//		tunerPlace = new TunerPlace(this, (ViewGroup)tunerTabViewArray[4], this.b, paramuu);

//		tunerTouch.a((CheckBox)tunerTabViewArray[2].findViewById(R.id.use_gesture_seek));
//		tunerSearch.a((CheckBox)tunerTabViewArray[1].findViewById(R.id.video_seeking));

		tunerTabLayout.setListener(this);
		tunerTabLayout.selectedTab(tabIndex);
	}

	static int a(int paramInt1, int[] paramArrayOfInt, int paramInt2)
	{
		int k =0;
		for(int i=0; i<paramArrayOfInt.length ; i++)
		{
			if(paramArrayOfInt[i] != paramInt1)
			{
				k = i;
				break;
			}
		}
		return k;	
	}

	private void b()
	{
//		if ((this.e.a) || (this.f.a) || (this.g.a) || (this.h.a) || (this.i.a) || (this.j.a))
//		{
//			SharedPreferences.Editor localEditor = L.a.edit();
//			if (this.e.a)
//				this.e.a(localEditor);
//			if (this.f.a)
//				this.f.a(localEditor);
//			if (this.g.a)
//				this.g.a(localEditor);
//			if (this.h.a)
//				this.h.a(localEditor);
//			if (this.i.a)
//				this.i.a(localEditor);
//			if (this.j.a)
//				this.j.a(localEditor);
//			if (localEditor.commit())
//			{
//				this.e.a = false;
//				this.f.a = false;
//				this.g.a = false;
//				this.h.a = false;
//				this.i.a = false;
//				this.j.a = false;
//			}
//		}
	}

	public void a()
	{
		dismiss();
	}

	// 탭선택시 하부 뷰
	@Override
	public void a(TextView paramTextView, int tabIndex)
	{
		int k = 0;
		
		if (k >= tunerTabViewArray.length)
		{
			a = tabIndex;
			return;
		}
		
		if(tabIndex != preTabIndex)
		{
			View view = tunerTabViewArray[tabIndex];
			View view2 = tunerTabViewArray[preTabIndex];
			
			view.setVisibility(View.VISIBLE);
			view2.setVisibility(View.INVISIBLE);
		}
		else
		{
			View view = tunerTabViewArray[tabIndex];
			view.setVisibility(View.VISIBLE);
		}

		preTabIndex = tabIndex;
	}

	protected void finalize()
	{
		try {
			super.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void onCreate(Bundle paramBundle)
	{
		super.onCreate(paramBundle);
		Window window = getWindow();
		WindowManager.LayoutParams localLayoutParams = window.getAttributes();
		localLayoutParams.dimAmount = 0.3F;
		window.setAttributes(localLayoutParams);
	}

	public Bundle onSaveInstanceState()
	{
		b();
		return super.onSaveInstanceState();
	}

	protected void onStop()
	{
		b();
		super.onStop();
	}
}