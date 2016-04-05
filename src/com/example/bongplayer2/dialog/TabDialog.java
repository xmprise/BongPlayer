package com.example.bongplayer2.dialog;

import java.util.HashMap;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.example.bongplayer2.R;
import com.example.bongplayer2.Listener.TabDialogDismissListener;
import com.yixia.zi.preference.APreference;

public class TabDialog extends Dialog {
	private final String LOGTAG = "TabDialog";

	private static final int[] tabID;
	private Context context;
	private LinearLayout tabDialogLinearLayout;
	//	private PlayerService playerService;
	private APreference aPreference;
	private int f = -1;

	static
	{
		tabID = new int[3];
		tabID[0] = R.id.tab_video;
		tabID[1] = R.id.tab_audio;
		tabID[2] = R.id.tab_subtitle;
	}

	public TabDialog(Context paramContext, int paramInt/*, VPlayerService paramVPlayerService*/)
	{
		super(paramContext, paramInt);

		context = paramContext;
		//		playerService = paramVPlayerService;
		aPreference = new APreference(context);
		setOnDismissListener(new TabDialogDismissListener(this));
		setContentView(R.layout.dialog_tabs);
		tabDialogLinearLayout = ((LinearLayout)findViewById(R.id.box));
		tabBindingListener();
		selectTab(aPreference.getInt("last_tab", R.id.tab_video));
		setCanceledOnTouchOutside(true);
	}

	private void tabBindingListener()
	{
		int i = tabID.length;
		for (int j = 0; j < i; j++)
			findViewById(tabID[j]).setOnClickListener(new tabClickListener(this));
	}

	private void selectTab(int viewID)
	{
		Log.d(LOGTAG, "aaaaaaaaa");

		tabDialogLinearLayout.removeAllViews();

		int i = tabID.length;
		for (int j = 0; j < i; j++)
			findViewById(tabID[j]).setBackgroundResource(R.drawable.tab_bg);

		ViewGroup localViewGroup;
		HashMap localHashMap;
		String[] arrayOfString;
		int m;

		switch (viewID)
		{
		case R.id.tab_video_image:
		case R.id.tab_audio_image:
		default:
			selectVideoTab();
		case R.id.tab_video:
			Log.d(LOGTAG, "tab_video");
			selectVideoTab();
			aPreference.put("last_tab", viewID);
			break;
		case R.id.tab_audio:
			Log.d(LOGTAG, "tab_audio");
			//			findViewById(2131558462).setBackgroundResource(2130837798);
			//			localViewGroup = (ViewGroup)View.inflate(this.b, 2130968604, null);
			//			a(localViewGroup, 2131558455, "vplayer_stereo_left", (float)aPreference.getDouble("vplayer_stereo_left", 1.0D));
			//			a(localViewGroup, 2131558456, "vplayer_stereo_right", (float)aPreference.getDouble("vplayer_stereo_right", 1.0D));
			//			localHashMap = playerService.getAudioTrackMap();
			//			if (localHashMap != null)
			//			{
			//				arrayOfString = new String[localHashMap.size()];
			//				localHashMap.keySet().toArray(arrayOfString);
			//				Arrays.sort(arrayOfString);
			//				m = 0;
			//				label211: if (m >= arrayOfString.length)
			//					break label703;
			//				int n = ((Integer)localHashMap.get(arrayOfString[m])).intValue();
			//				if ((n != playerService.getLastAudioTrack()) && (n != playerService.getAudioTrack()))
			//					break label351;
			//			}
			break;
		case R.id.tab_subtitle:
			Log.d(LOGTAG, "tab_subtitle");
			break;
		}
		//		while (true)
		//		{
		//			Spinner localSpinner = (Spinner)localViewGroup.findViewById(2131558457);
		//			ArrayAdapter localArrayAdapter = new ArrayAdapter(this.b, 2130968636, arrayOfString);
		//			localArrayAdapter.setDropDownViewResource(2130968635);
		//			localSpinner.setAdapter(localArrayAdapter);
		//			localSpinner.setSelection(m);
		//			localSpinner.setOnItemSelectedListener(new qr(this, localHashMap, arrayOfString));
		//			this.c.addView(localViewGroup, new LinearLayout.LayoutParams(-1, -1));
		//			break;
		//			label351: m++;
		//			break label211;
		//			findViewById(2131558464).setBackgroundResource(2130837798);
		//			View localView = View.inflate(this.b, 2130968607, null);
		//			a(localView, 2131558472, 2131492880, 2131492881, "vplayer_text_encoding", "auto");
		//			a(localView, 2131558471, 2131492876, 2131492877, "vplayer_text_style", Integer.valueOf(1));
		//			a(localView, 2131558470, 2131492878, 2131492879, "vplayer_text_font", Integer.valueOf(0));
		//			a(localView, 2131558468, aPreference.getInt("vplayer_text_color", -1), "vplayer_text_color");
		//			a(localView, 2131558476, aPreference.getInt("vplayer_sub_shadow_color", -16777216), "vplayer_sub_shadow_color");
		//			a(localView, 2131558467, "vplayer_sub_enabled", aPreference.getBoolean("vplayer_sub_enabled", true));
		//			a(localView, 2131558475, "vplayer_sub_shadow_enabled", aPreference.getBoolean("vplayer_sub_shadow_enabled", true));
		//			a(localView);
		//			SeekBar localSeekBar1 = (SeekBar)localView.findViewById(2131558474);
		//			int k = DeviceUtils.getScreenHeight((Activity)this.b);
		//			localSeekBar1.setProgress((int)(localSeekBar1.getMax() * aPreference.getDouble("vplayer_sub_position", 10.0D) / k));
		//			localSeekBar1.setOnSeekBarChangeListener(new qt(this, k));
		//			SeekBar localSeekBar2 = (SeekBar)localView.findViewById(2131558473);
		//			localSeekBar2.setProgress((int)aPreference.getDouble("vplayer_sub_size", 18.0D));
		//			localSeekBar2.setOnSeekBarChangeListener(new qu(this));
		//			SeekBar localSeekBar3 = (SeekBar)localView.findViewById(2131558477);
		//			localSeekBar3.setProgress((int)aPreference.getDouble("vplayer_sub_shadow_radius", 2.0D));
		//			localSeekBar3.setOnSeekBarChangeListener(new qv(this));
		//			this.c.addView(localView, new LinearLayout.LayoutParams(-1, -1));
		//			break;
		//			label703: m = -1;
		//		}
	}

	private void a(View paramView)
	{
//		int i = 0;
//		HashMap localHashMap = this.d.getSubTrackMap();
//		if (this.d.getUri() == null);
//		String str1;
//		boolean bool;
//		do
//		{
//			return;
//			str1 = this.d.getUri().getPath();
//			bool = str1.startsWith(Environment.getExternalStorageDirectory().getPath());
//		}
//		while ((localHashMap == null) && (!bool));
//		ArrayList localArrayList = new ArrayList();
//		this.f = -1;
//		if (localHashMap != null)
//		{
//			String[] arrayOfString = new String[localHashMap.size()];
//			localHashMap.keySet().toArray(arrayOfString);
//			Arrays.sort(arrayOfString);
//			int k = arrayOfString.length;
//			int m = 0;
//			if (m < k)
//			{
//				String str3 = arrayOfString[m];
//				Object localObject = localHashMap.get(str3);
//				if ((localObject instanceof Integer))
//					localArrayList.add(new qy(str3, ((Integer)localObject).intValue()));
//				while (true)
//				{
//					m++;
//					break;
//					if (!(localObject instanceof String))
//						continue;
//					localArrayList.add(new qy(str3, (String)localObject));
//				}
//			}
//		}
//		if (bool)
//		{
//			String str2 = this.d.getLastSubTrack();
//			if (!TextUtils.isEmpty(str2))
//			{
//				File localFile = new File(str2);
//				if ((localFile.exists()) && (localFile.canRead()))
//				{
//					localArrayList.add(0, new qy(localFile.getName(), str2));
//					this.f = 0;
//				}
//			}
//			if (localArrayList.size() == 0)
//			{
//				localArrayList.add(new qy(this.b.getString(2131361995), -1));
//				this.f = 0;
//			}
//			localArrayList.add(new qy(this.b.getString(2131361864), -1));
//		}
//		int j;
//		if ((this.f == -1) && (localHashMap != null))
//			j = this.d.getLastSubTrackId();
//		while (true)
//		{
//			if (i < localArrayList.size())
//			{
//				qy localqy = (qy)localArrayList.get(i);
//				if (((j != -1) && (localqy.a == j)) || ((localqy.a != -1) && (this.d.getSubLocation() == 0) && (localqy.equals(Integer.valueOf(this.d.getSubTrack())))) || ((!TextUtils.isEmpty(localqy.b)) && (this.d.getSubLocation() == 1) && (localqy.equals(this.d.getSubPath()))))
//					this.f = i;
//			}
//			else
//			{
//				Spinner localSpinner = (Spinner)paramView.findViewById(2131558469);
//				ArrayAdapter localArrayAdapter = new ArrayAdapter(this.b, 2130968636, localArrayList);
//				localArrayAdapter.setDropDownViewResource(2130968635);
//				localSpinner.setAdapter(localArrayAdapter);
//				localSpinner.setSelection(this.f);
//				localSpinner.setOnItemSelectedListener(new qw(this, localArrayList, bool, str1, localSpinner));
//				break;
//			}
//			i++;
//		}
	}

	private void a(View paramView, int paramInt1, int paramInt2, int paramInt3, String paramString, Object paramObject)
	{
//		Spinner localSpinner = (Spinner)paramView.findViewById(paramInt1);
//		ArrayAdapter localArrayAdapter = ArrayAdapter.createFromResource(this.b, paramInt2, 2130968636);
//		localArrayAdapter.setDropDownViewResource(2130968635);
//		String[] arrayOfString = this.b.getResources().getStringArray(paramInt3);
//		localSpinner.setAdapter(localArrayAdapter);
//		String str = this.e.getString(paramString, String.valueOf(paramObject));
//		int i = 0;
//		if (i < arrayOfString.length)
//			if (!arrayOfString[i].equals(str));
//		while (true)
//		{
//			localSpinner.setSelection(i);
//			localSpinner.setOnItemSelectedListener(new qn(this, paramString, arrayOfString));
//			return;
//			i++;
//			break;
//			i = 0;
//		}
	}

	private void a(View paramView, int paramInt1, int paramInt2, String paramString)
	{
//		ColorPanelView localColorPanelView = (ColorPanelView)paramView.findViewById(paramInt1);
//		localColorPanelView.setColor(paramInt2);
//		localColorPanelView.setOnClickListener(new ql(this, paramInt2, paramString, localColorPanelView));
	}

	private void a(View paramView, int paramInt, String paramString, float paramFloat)
	{
//		SeekBar localSeekBar = (SeekBar)paramView.findViewById(paramInt);
//		int i = localSeekBar.getMax();
//		localSeekBar.setProgress((int)(paramFloat * i));
//		localSeekBar.setOnSeekBarChangeListener(new qs(this, paramString, i));
	}

	private void a(View paramView, int paramInt, String paramString, boolean paramBoolean)
	{
//		CheckBox localCheckBox = (CheckBox)paramView.findViewById(paramInt);
//		localCheckBox.setChecked(paramBoolean);
//		localCheckBox.setOnCheckedChangeListener(new qo(this, paramString, paramInt));
	}

	private void selectVideoTab()
	{
//		findViewById(R.id.tab_video).setBackgroundResource(R.drawable.tab_bg_selected);
//		View view = View.inflate(context, R.layout.dialog_video, null);
//		
//		a(view, 2131558480, 2131492865, 2131492866, "vplayer_video_quality", Integer.valueOf(0));
//		a(view, 2131558481, 2131492867, 2131492868, "vplayer_aspect_ratio", Float.valueOf(0.0F));
//		a(view, 2131558482, 2131492871, 2131492872, "vplayer_screen_orientation", Integer.valueOf(0));
//		a(view, 2131558483, "vplayer_prefer_hw", aPreference.getBoolean("vplayer_prefer_hw", false));
//		a(view, 2131558484, "vplayer_deinterlace", aPreference.getBoolean("vplayer_deinterlace", false));
//		a(view, 2131558485, "vplayer_background_playback", aPreference.getBoolean("vplayer_background_playback", false));
//		SeekBar localSeekBar = (SeekBar)view.findViewById(2131558486);
//		localSeekBar.setMax(32);
//		localSeekBar.setProgress(aPreference.getInt("vplayer_buffer_size", 0) / 1024 / 256);
//		localSeekBar.setOnSeekBarChangeListener(new qq(this));
//		this.c.addView(view, new LinearLayout.LayoutParams(-1, -1));
	}

	public LinearLayout getTabDialogLinearLayout() {
		return tabDialogLinearLayout;
	}

	public APreference getaPreference() {
		return aPreference;
	}

	public Context getCtx()
	{
		return context;
	}

	public final class tabClickListener
	implements View.OnClickListener
	{
		public tabClickListener(TabDialog paramTabDialog)
		{
		}

		public final void onClick(View paramView)
		{
			Log.d(LOGTAG, "tab Click!!");
			selectTab(paramView.getId());
		}
	}
}
