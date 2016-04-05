package com.example.bongplayer2.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Video;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.example.bongplayer2.ExternalStorageMonitor;
import com.example.bongplayer2.FolderInfo;
import com.example.bongplayer2.MediaDetailActivity;
import com.example.bongplayer2.MediaSearch;
import com.example.bongplayer2.R;
import com.example.bongplayer2.Handler.FragmentMediaFolderHandler;
import com.example.bongplayer2.Utils.CursorUtils;
import com.example.bongplayer2.adapter.MediaFolderCursorAdapter;
import com.example.bongplayer2.adapter.MediaInfoAdapter;
import com.example.bongplayer2.bean.FileExplorerItem;
import com.example.bongplayer2.bean.VideoFileInfo;
import com.example.bongplayer2.dialog.ListMenuDialog;
import com.yixia.zi.utils.UIUtils;

public class FragmentMediaFolder extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener{

	private final String LOGTAG = "FragmentMediaFolder";
	private static final IntentFilter intentFilter;
	private ArrayList<FolderInfo> folderInfoList;
	private static HashMap<String, ArrayList<VideoFileInfo>> videoFileInfoMap = null;
	private static MediaSearch ms;
	private MediaInfoAdapter fileInfoAdapter;
	private ListMenuDialog listMenuDialog;
	private Menu menu;
	private BaseFragment.EditModeListener editModeListener;
	private MediaFolderCursorAdapter mediaFolderCursorAdapter;
	private FragMentMediaFolderIconClickListener fragMentMediaFolderIconClickListener = new FragMentMediaFolderIconClickListener(this);
	private Handler handler = new FragmentMediaFolderHandler(this);
	private boolean isRegisterReceiver;
	private ExternalStorageMonitor fragmentMediaBroadcastReceiver;
	private final String rootDir = Environment.getExternalStorageDirectory().getPath();
	private final CustomContentObserver customContentObserver = new CustomContentObserver(this, new Handler());

	private MediaScannerConnection msc;
	private MediaScannerConnectionClient mScanClient;
	private final String VIDEO_ID = Video.VideoColumns._ID;
	private final String VIDEO_PATH = Video.VideoColumns.DATA;
	private final String VIDEO_NAME = Video.VideoColumns.DISPLAY_NAME;
	private final String VIDEO_SIZE = Video.VideoColumns.SIZE;
	private final String VIDEO_TIME = Video.VideoColumns.DURATION;
	private final String VIDEO_RESOLUTION = Video.VideoColumns.RESOLUTION;
	private final String VIDEO_LNG = Video.VideoColumns.LANGUAGE;
	private final String VIDEO_PARENT_PATH = "replace("+VIDEO_PATH+", "+VIDEO_NAME+",'')";
	private FileExplorerItem fileExplorerItem;

	static
	{
		intentFilter= new IntentFilter("android.intent.action.MEDIA_MOUNTED");
		intentFilter.addAction("android.intent.action.MEDIA_EJECT");
		intentFilter.addAction("android.intent.action.MEDIA_REMOVED");
		intentFilter.addAction("android.intent.action.MEDIA_UNMOUNTED");
		intentFilter.addAction("android.intent.action.MEDIA_UNMOUNTABLE");
		intentFilter.addDataScheme("file");
	}


	private void setEdit()
	{
		if (mediaFolderCursorAdapter == null)
			return;
		else{
			boolean[] itemsIsCheck = mediaFolderCursorAdapter.getChecked();

			if ((itemsIsCheck != null) && (isEditMode()))
			{
				int checkCnt = itemsIsCheck.length;

				mMenuDelete.setEnabled(false);

				for(int i=0;i<checkCnt;i++)
				{
					if(itemsIsCheck[i] != false)
					{
						mMenuDelete.setEnabled(true);
						break;
					}	
				}
			}
		}
	}

	public void onConfigurationChanged(Configuration paramConfiguration)
	{
		super.onConfigurationChanged(paramConfiguration);
		if ((listMenuDialog != null) && (listMenuDialog.isShowing()))
			listMenuDialog.resize();
	}

	public void onCreate(Bundle paramBundle)
	{
		super.onCreate(paramBundle);
		//		ms = new MediaSearch(getActivity().getContentResolver());
		//		ms.loadVideoInfo();
		//		folderInfoList = ms.getFolderInfoList();
		//		fileInfoAdapter = new MediaInfoAdapter(this.getActivity(), R.layout.row, folderInfoList);
		//		setListAdapter(fileInfoAdapter);


		mediaFolderCursorAdapter = new MediaFolderCursorAdapter(this, fragMentMediaFolderIconClickListener);
		editModeListener = mediaFolderCursorAdapter.mEditModeListener;
		setListAdapter(mediaFolderCursorAdapter);
		setHasOptionsMenu(true); // fragment callback option
	}

	private void broadcastReceiverRegister()
	{
		if (!isRegisterReceiver)
		{
			fragmentMediaBroadcastReceiver = new ExternalStorageMonitor();
			getActivity().registerReceiver(fragmentMediaBroadcastReceiver, intentFilter);
			isRegisterReceiver = true;
		}
		else
		{
			try
			{
				if (fragmentMediaBroadcastReceiver != null)
					getActivity().unregisterReceiver(fragmentMediaBroadcastReceiver);

				isRegisterReceiver = false;
			}
			catch (IllegalArgumentException localIllegalArgumentException)
			{

			}
		}
	}



	@SuppressWarnings("unchecked")
	public void onActivityCreated(Bundle paramBundle)
	{
		super.onActivityCreated(paramBundle);
		getListView().setOnItemLongClickListener(this);
		Log.d(LOGTAG, "onActivityCreated");
		LoaderManager lm = getLoaderManager();
		lm.initLoader(1, null, this); 
	}

	
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) 
	{
		Log.d(LOGTAG, "onCreateLoader");
		//String[] proj = MediaFolderCursorAdapter.MediaFolderQuery.PROJECTION;
		String selection = "group by ";
		String[] proj2 = {CursorUtils.UNIQ_VIDEO_PARENT_PATH + "as uniq_path", VIDEO_ID};
		return new CursorLoader(getActivity(), Video.Media.EXTERNAL_CONTENT_URI, proj2, "_id > 0) GROUP BY (uniq_path", null, null);
	}

	
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) 
	{
		Log.d(LOGTAG, "onLoadFinished");
		if ((getActivity() == null) || (cursor == null))
		{
			Log.d(LOGTAG, "paramCursor null");
		}
		else
		{
			mediaFolderCursorAdapter.changeCursor(cursor); // mCursor replace this paramCursor 
			Log.d(LOGTAG, "onLoadFinished changeCursor");
			mediaFolderCursorAdapter.createChecked();
			setEmptyData(cursor, 0, 0);

			//			if (!MediaScannerStatusReceiver.isScanning(mContext.getApplicationContext()))
			//				restorePosition("media_folder_last_selection", "media_folder_last_scroll_top");
			//			if (mAPreference.getBoolean(getString(2131362071), true))
			//			{
			//				if (this.i == null)
			//					this.i = new ArrayList();
			//				else
			//				{
			//					if (paramCursor == null)
			//						
			//					paramCursor.moveToFirst();
			//					
			//					while (!paramCursor.isAfterLast())
			//					{
			//						String str = paramCursor.getString(paramCursor.getColumnIndex("_directory"));
			//						FileObserverScan localFileObserverScan = new FileObserverScan(getActivity(), str, 768); 
			//						localFileObserverScan.startWatching();
			//						this.i.add(localFileObserverScan);
			//						paramCursor.moveToNext();
			//					}
			//					
			//					Iterator localIterator = this.i.iterator();
			//					while (localIterator.hasNext())
			//						((FileObserverScan)localIterator.next()).stopWatching();
			//					this.i.clear();
			//				}
			//			}
		}
	}

	public void onLoaderReset(Loader<Cursor> arg0) 
	{
		mediaFolderCursorAdapter.swapCursor(null);
	}

	public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
	{
		Log.d(LOGTAG, "onCreateOptionsMenu");
		paramMenuInflater.inflate(R.menu.menu_media_folder, paramMenu);
		menu = paramMenu;

		//		MenuItem menuItem = paramMenu.findItem(R.id.menu_search); // search Icon
		//		
		//		if ((menuItem != null) && (UIUtils.hasHoneycomb()))
		//		{
		//			SearchView searchView = (SearchView)menuItem.getActionView();
		//			if (searchView != null)
		//				searchView.setSearchableInfo(((SearchManager)mContext.getSystemService("search")).getSearchableInfo(getActivity().getComponentName()));
		//		}
		super.onCreateOptionsMenu(paramMenu, paramMenuInflater);
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem)
	{

		Log.d(LOGTAG, "onOptionsItemSelected!");

		boolean bool = true;
		switch (paramMenuItem.getItemId())
		{
		default:
			bool = super.onOptionsItemSelected(paramMenuItem);
			break;
		case R.id.menu_edit:
			Log.d(LOGTAG, "menu_edit!");
			if (isEditMode())
			{
				setEditMode();
				editModeListener.onCancelEditClick();
				Log.d(LOGTAG, "onCancelEditClick!");
			}
			else
			{
				setEditMode();
				editModeListener.onEditClick();
				Log.d(LOGTAG, "onEditClick!");
				setEdit();
			}
			break;
		case R.id.menu_refresh:
			Log.d(LOGTAG, "menu_refresh!");
			//			if (!MediaScannerStatusReceiver.isScanning(mContext.getApplicationContext()))
			//			{
			//				handler.sendEmptyMessage(0);
			//				Context context = mContext.getApplicationContext();
			//
			//				MediaScannerConnection.scanDirectories(context, rootDir, new FragmentMediaScanCompleteListener());
			//			}
			Context context = mContext.getApplicationContext();
			mScanClient = new MediaScannerConnectionClient() {

				public void onMediaScannerConnected() {
					Log.i("CS", "onMediaScannerConnected");
					File file = Environment
							.getExternalStorageDirectory(); 

					File[] fileNames = file.listFiles();

					if (fileNames != null) {
						for (int i = 0; i < fileNames.length; i++) 
							msc.scanFile(fileNames[i].getAbsolutePath(), null);
					}
				}

				@Override
				public void onScanCompleted(String path, Uri uri) {
					Log.i("OUT", "onScanCompleted(" + path + ", " + uri.toString()
							+ ")"); 
				}
			};
			final int sdkVer = Integer.parseInt(Build.VERSION.SDK);

			if (sdkVer > Build.VERSION_CODES.GINGERBREAD) {
				
				System.out.println("sdkVer > Build.VERSION_CODES.GINGERBREAD");
				msc = new MediaScannerConnection(context, mScanClient);
				msc.connect();
			} else {
				
				context.getApplicationContext().sendBroadcast(
						new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
				System.out.println("sdkVer > Build.VERSION_CODES.GINGERBREAD else");
			}

			break;
		case R.id.menu_search:
			Log.d(LOGTAG, "menu_search!");
			//			onSearchRequested();
			break;
		}
		return bool;
	}

	public boolean onSearchRequested()
	{
		if (!UIUtils.hasHoneycomb())
		{
			Bundle localBundle = new Bundle();
			localBundle.putInt("SearchType", 1);
			getActivity().startSearch(null, false, localBundle, false);
		}
		return true;
	}

	public void onListItemClick(ListView paramListView, View paramView, int paramInt, long paramLong)
	{
		if (isEditMode())
		{
			mediaFolderCursorAdapter.setChecked(paramInt);
			setEdit();
		}
		else
		{
			Cursor cursor = (Cursor)mediaFolderCursorAdapter.getItem(paramInt);
			Intent intent = new Intent(getActivity(), MediaDetailActivity.class);
			//intent.setData(ScheduleHelper.buildMediaDirectoryUri(folderPath)); 
			intent.setData(MediaStore.Video.Media.EXTERNAL_CONTENT_URI); 
			intent.putExtra("title", CursorUtils.folderPathToFolderName(cursor));
			Log.d(LOGTAG,"onListItemClick cursor.getString(0)>>"+cursor.getString(0));
			intent.putExtra("folderPath",cursor.getString(0));
			startActivity(intent);
		}
	}


	public void onEditMenuClick(View paramView)
	{
		switch (paramView.getId())
		{
		case R.id.iv_action_delete:
			mediaFolderCursorAdapter.toggleChecked();
			setEdit();
			break;
		case R.id.view_line:
			break;
		case R.id.select_all:
			mediaFolderCursorAdapter.toggleChecked();
			setEdit();
			break;
		case R.id.delete:
			mMediaHelper.deleteFolder(mediaFolderCursorAdapter.getCheckedFiles());
			break;
		default:
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int id, long arg3) {
		// TODO Auto-generated method stub
		//		String str1 = fileExplorerItem.name;
		//		String str2 = fileExplorerItem.data;

		String folderPath = fileExplorerItem.data;
		File file = new File(folderPath);

		if (!file.exists())
			showLongToast(R.string.file_explorer_not_exists);
		else
		{
			if (!file.canRead())
				showLongToast(R.string.file_explorer_cannot_read);
			else
				switch (id)
				{
				default:
					break;
				case 0:
					startPlayer(file, false, 1);
					break;
				case 1:
					deleteFile(file);
					break;
				case 2:
					renameFolder(file, folderPath);
					break;
				}
		}
	}

	public void onAttach(Activity paramActivity)
	{
		super.onAttach(paramActivity);
		paramActivity.getContentResolver().registerContentObserver(Video.Media.EXTERNAL_CONTENT_URI, true, customContentObserver);
	}

	public void onDetach()
	{
		super.onDetach();
		getActivity().getContentResolver().unregisterContentObserver(customContentObserver);
	}

	@Override
	public boolean onBackPressed()
	{
		Log.d(LOGTAG, "onBackPressed");
		if (isEditMode())
		{
			Log.d(LOGTAG, "onBackPressed isEditMode");
			setEditMode();
			editModeListener.onCancelEditClick();
			return true;
		}
		else
		{
			Log.d(LOGTAG, "onBackPressed notEditMode");
			return super.onBackPressed();
		}
	}

	public abstract interface Callbacks
	{
		public abstract boolean onFolderSelected(String paramString);
	}

	public final class FragMentMediaFolderIconClickListener
	implements MediaFolderCursorAdapter.IconClickListener
	{
		private FragmentMediaFolder fragmentMediaFolder;
		public FragMentMediaFolderIconClickListener(FragmentMediaFolder paramFragmentMediaFolder)
		{
			fragmentMediaFolder = paramFragmentMediaFolder;
		}

		

		public final void onIconClick(FileExplorerItem fileExplorerItem)
		{
			Log.d(LOGTAG, "onIconClick");
			
			if (fileExplorerItem != null)
			{
				Log.d(LOGTAG, "FragMentMediaFolderIconClickListener onIconClick");
				buildAction(fileExplorerItem);
			}
		}
	}

	public void buildAction(FileExplorerItem paramFileExplorerItem)
	{
		Log.d(LOGTAG, "buildAction");

		if (listMenuDialog == null)
			listMenuDialog = new ListMenuDialog(getActivity(), MENU_ITEM_MEDIA_FOLDER, R.style.ListDialog, this);
		fileExplorerItem = paramFileExplorerItem;
		listMenuDialog.show(paramFileExplorerItem.folerName, 17);
	}
	
	public final class CustomContentObserver extends ContentObserver
	{
		public CustomContentObserver(FragmentMediaFolder paramFragmentMediaFolder, Handler paramHandler)
		{
			super(paramHandler);
		}

		public final void onChange(boolean paramBoolean)
		{
			if (getActivity() == null)
				return;
			else
			{
				Loader<Cursor> loader = getLoaderManager().getLoader(1);
				if (loader == null)
					return;
				loader.forceLoad();
			}
		}
	}

	public void onPause()
	{
		super.onPause();
		broadcastReceiverRegister();
		saveCurrentPosition("media_folder_last_selection", "media_folder_last_scroll_top");
	}

	public void onResume()
	{
		super.onResume();
		handler.sendEmptyMessageDelayed(0, 1000L);
		broadcastReceiverRegister();
	}

	public Handler getHandler() {
		return handler;
	}

	public boolean isRegisterReceiver() {
		return isRegisterReceiver;
	}

	public void setRegisterReceiver(boolean isRegisterReceiver) {
		this.isRegisterReceiver = isRegisterReceiver;
	}

}
