package com.example.bongplayer2.dialog;

import java.util.ArrayList;

import com.example.bongplayer2.R;
import com.example.bongplayer2.adapter.ListMenuArrayAdapter;
import com.yixia.zi.utils.DeviceUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ListMenuDialog extends Dialog implements AdapterView.OnItemClickListener{

	private final String LOGTAG = "ListMenuDialog";
	public static final ArrayList<ListMenuItem> MENU_ITEM_MORE_COMMON;
	private ArrayList arrayList;
	private ListView listView;
	private TextView textView;
	private Context context;
	private AdapterView.OnItemClickListener d;
	private boolean f = true;
	private String g;

	
	static
	{
		MENU_ITEM_MORE_COMMON = new ArrayList<ListMenuItem>();
		MENU_ITEM_MORE_COMMON.add(new ListMenuItem(R.string.file_explorer_play, R.drawable.file_explorer_action_play));
	}

	public ListMenuDialog(Context paramContext, int paramInt, AdapterView.OnItemClickListener paramOnItemClickListener)
	{		
		this(paramContext, null, paramInt, paramOnItemClickListener);
		Log.d(LOGTAG, "ListMenuDialog1");
	}

	public ListMenuDialog(Context paramContext, ArrayList paramArrayList, int paramInt, AdapterView.OnItemClickListener paramOnItemClickListener)
	{		
		super(paramContext, paramInt);

		Log.d(LOGTAG, "ListMenuDialog2");

		setCanceledOnTouchOutside(true);

		context = paramContext;
		this.d = paramOnItemClickListener; 
		if ((paramArrayList != null) && (paramArrayList.size() > 0))
		{
			Log.d(LOGTAG, "ListMenuDialog paramArrayList not null");
			arrayList = paramArrayList;
			this.f = false;
		}
		else
		{
					
			Log.d(LOGTAG, "ListMenuDialog paramArrayList null");
			arrayList = new ArrayList(MENU_ITEM_MORE_COMMON);
		}
	}

	protected void onCreate(Bundle paramBundle)
	{
		super.onCreate(paramBundle);
		setContentView(R.layout.menu_list);
		resize();
		textView = ((TextView)findViewById(android.R.id.title));
		listView = ((ListView)findViewById(android.R.id.list));
		listView.setOnItemClickListener(this.d);
		listView.setAdapter(new ListMenuArrayAdapter(context, arrayList));
	}

	@Override
	public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int arg2, long arg3) {
		//TODO Auto-generated method stub
		
		Log.d(LOGTAG,"onItemClick!!!!!!!!!");
		if (this.f)
			switch (arg2 - (arrayList.size() - MENU_ITEM_MORE_COMMON.size()))
			{
			default:
				if (this.d != null)
				{
					paramView.setTag(this.g);
					this.d.onItemClick(paramAdapterView, paramView, arg2, arg3);
				}
				dismiss();
				break;
			case 0:
//				if (context != null)
//					context.startActivity(new Intent(context, VPreferenceActivity.class));
//				cancel();
				break;
			case 1:
//				if (context != null)
//					context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(context.getString(2131362086))));
//				cancel();
				break;
			case 2:
//				if (context != null)
//					((FileExplorerContainer)context).killVPlayerService();
//				Process.killProcess(Process.myPid());
				break;
			case 3:
				if (this.d != null)
				{
					paramView.setTag(this.g);
					this.d.onItemClick(paramAdapterView, paramView, arg2, arg3);
				}
				dismiss();
				break;
			}
		else
		{
//			if (context != null)
//				context.startActivity(new Intent(context, PluginsActivity.class));
//			cancel();
		}
	}

	public void resize()
	{
		Activity activity = (Activity)context;
		int width = DeviceUtils.getScreenWidth(activity);
		int height = DeviceUtils.getScreenHeight(activity);

		double d1 = width;
		double d2 = 0;
		if (width < height)
		{
			d2 = 0.8D;
		}
		else
		{
			d2 = 0.6D;
		}

		int setgWidth = (int)(d2 * d1);
		if (setgWidth > 600)
			setgWidth = 600;
		getWindow().setLayout(setgWidth, -2);
	}

	public void show(int paramInt)
	{
		getWindow().setGravity(paramInt);
		show();
	}

	public void show(int paramInt1, int paramInt2)
	{
		show(context.getString(paramInt1), paramInt2);
	}

	public void show(String paramString, int paramInt)
	{
		Log.d(LOGTAG, "ListMenuDialog show");
		Log.d(LOGTAG, "paramString paramInt>>>"+paramString+"  "+paramInt);
		show(paramInt);

		if ((paramInt == 80) && (textView.getVisibility() != View.GONE))
		{
			Log.d(LOGTAG, "(paramInt == 80) && (textView.getVisibility() != View.GONE)");
			textView.setVisibility(View.GONE);
			findViewById(R.id.line).setVisibility(View.GONE);
		}
		else
		{
			Log.d(LOGTAG, "paramInt != 80");
			if (textView != null)
			{
				Log.d(LOGTAG, "textView != null");
				this.g = paramString;
				textView.setText(paramString);
			}
		}
	}
}
