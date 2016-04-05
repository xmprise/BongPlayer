package com.example.bongplayer2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Messenger;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.example.bongplayer2.Listener.ViewPagerListener;
import com.example.bongplayer2.adapter.BongFragmentAdapter;
import com.example.bongplayer2.fragment.BaseFragment;
import com.example.bongplayer2.fragment.FragmentMediaFolder;
import com.yixia.zi.preference.APreference;

public class BongPlayerExplorer extends BaseActivity implements ActionBar.TabListener{

	private final String LOGTAG = "BongPlayerExplorer";

	private FragmentMediaFolder fragMentMediaFolder;
	private FragmentPagerAdapter fragmentPagerAdapter = new BongFragmentAdapter(this, getSupportFragmentManager()); ;
	public BaseFragment baseFragment;

	private ViewPager viewPager;
	private ViewPager.OnPageChangeListener viewPagerListener = new ViewPagerListener(this);
	private SharedPreferences sharedPreferences;
	public boolean mCreated = false;
	private boolean k;
	private boolean l;
	private Messenger i = null;	
	//private ServiceConnection m = new mo(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d(LOGTAG, "onCreate");

		if(!isFinishing())
		{
			Log.d(LOGTAG, "not isFinishing");
			sharedPreferences = getSharedPreferences("MangoPlayer", 0);

			setContentView(R.layout.fragment_pager);
			viewPager = (ViewPager)findViewById(R.id.pager);
			viewPager.setAdapter(fragmentPagerAdapter);
			viewPager.setOnPageChangeListener(viewPagerListener);

			viewPager.setPageMarginDrawable(R.drawable.grey_border);
			viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.page_margin_width));

			ActionBar actionBar = getSupportActionBar();
			actionBar.setHomeButtonEnabled(false);
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			actionBar.addTab(actionBar.newTab().setText(R.string.local_media_tab).setTabListener(this));
			actionBar.addTab(actionBar.newTab().setText(R.string.local_stream_tab).setTabListener(this));
			//actionBar.addTab(actionBar.newTab().setText("").setTabListener(this));

			if (savedInstanceState != null) {
				actionBar.setSelectedNavigationItem(savedInstanceState.getInt("last_tab", 0));
			}

			viewPager.setCurrentItem(sharedPreferences.getInt("last_tab", 0));
			
			//viewPager.setCurrentItem(0);
			//bindService(new Intent(this, RemoteVPlayerService.class), this.m, 1);
			k = true;
			//startService(new Intent(this, UpdateService.class));
			mCreated = true;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		Log.d(LOGTAG, "onCreateOptionsMenu!!!");

		super.onCreateOptionsMenu(menu);
		getSupportMenuInflater().inflate(R.menu.menu_local, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem)
	{
		Log.d(LOGTAG, "onOptionsItemSelected!!");
		Log.d(LOGTAG, "paramMenuItem.getItemId()>>>"+paramMenuItem.getItemId());
		boolean bool = true;

		switch(paramMenuItem.getItemId())
		{
		default:
			bool = super.onOptionsItemSelected(paramMenuItem);
			break;
		case R.id.menu_preferences:
			startActivity(new Intent(this, PreferenceActivity.class));
			break;
		case R.id.menu_quit:
			killVPlayerService();
			//Process.killProcess(Process.myPid());
			break;
		}
		return bool;
	}

	public boolean onPrepareOptionsMenu(Menu paramMenu)
	{
		return super.onPrepareOptionsMenu(paramMenu);
	}

	protected void onRestoreInstanceState(Bundle paramBundle)
	{
		super.onRestoreInstanceState(paramBundle);
		if (fragMentMediaFolder == null)
			fragMentMediaFolder = ((FragmentMediaFolder)getSupportFragmentManager().getFragment(paramBundle, "media_fragment"));
		//		if (this.b == null)
		//			this.b = ((FragmentStreamFolder)getSupportFragmentManager().getFragment(paramBundle, "stream_fragment"));
		//		if (this.c == null)
		//			this.c = ((FragmentFile)getSupportFragmentManager().getFragment(paramBundle, "file_fragment"));
	}

	protected void onSaveInstanceState(Bundle paramBundle)
	{
		super.onSaveInstanceState(paramBundle);
		if (fragMentMediaFolder != null)
			getSupportFragmentManager().putFragment(paramBundle, "media_fragment", fragMentMediaFolder);
		//		if (this.b != null)
		//			getSupportFragmentManager().putFragment(paramBundle, "stream_fragment", this.b);
		//		if (this.c != null)
		//			getSupportFragmentManager().putFragment(paramBundle, "file_fragment", this.c);
	}

	// when tab click, view pager change 
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		if (viewPager.getCurrentItem() != tab.getPosition())
			viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	public void killVPlayerService()
	{

	}

	public void onBackPressed()
	{	
		Log.d(LOGTAG, "onBackPressed");
		if((baseFragment != null) && (baseFragment.onBackPressed()))
			return;
		else
			finish();
	}

	public void onDestroy()
	{
		super.onDestroy();
	}

	public void onResume()
	{
		super.onResume();
//		if (mTheme != VPlayerApplication.THEME)
//		{
//			if (!UIUtils.hasHoneycomb())
//				break label58;
//			recreate();
//		}
//		if(true)
//		{
//			if ((mCreated) && (this.k) && (this.l));
//			try
//			{
//				i.send(Message.obtain(null, 4));
//				Intent localIntent = getIntent();
//				finish();
//				startActivity(localIntent);
//			}
//			catch (RemoteException localRemoteException)
//			{
//				Log.e("VPlayer[Explorer]", "getVPlayerMessage", localRemoteException);
//			}
//		}
	}

	public ViewPager getViewPager() {
		return viewPager;
	}

	public void setViewPager(ViewPager viewPager) {
		this.viewPager = viewPager;
	}

	public SharedPreferences getSharedPreferences() {
		return sharedPreferences;
	}

	public FragmentPagerAdapter getFragmentPagerAdapter() {
		return fragmentPagerAdapter;
	}
}
