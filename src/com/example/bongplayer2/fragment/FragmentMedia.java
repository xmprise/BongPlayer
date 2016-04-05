package com.example.bongplayer2.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.example.bongplayer2.BaseActivity;
import com.example.bongplayer2.MyContentObserver;
import com.example.bongplayer2.R;
import com.example.bongplayer2.VideoActivity;
import com.example.bongplayer2.R.id;
import com.example.bongplayer2.R.menu;
import com.example.bongplayer2.R.style;
import com.example.bongplayer2.Utils.CursorUtils;
import com.example.bongplayer2.adapter.MediaCursorAdapter;
import com.example.bongplayer2.bean.FileExplorerItem;
import com.example.bongplayer2.dialog.ListMenuDialog;
import com.yixia.zi.preference.APreference;
import com.yixia.zi.utils.Media;
import com.yixia.zi.utils.StringHelper;
import com.yixia.zi.widget.dialog.CommonAlertDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FragmentMedia extends BaseFragment
implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener
{
	private final String LOGTAG = "FragmentMedia";
	private String data;

	private MediaCursorAdapter mediaCursorAdapter;
	private BaseFragment.EditModeListener editModeListener;
	private int loaderID;
	private String folderPath;
	private ListMenuDialog listMenuDialog;
	private FileExplorerItem fileExplorerItem;
	private final MyContentObserver contentObserver = new MyContentObserver(this, new Handler()); 
	private FragMentMediaIconClickListener fragMentMediaIconClickListener = new FragMentMediaIconClickListener();
	private ArrayList<String> videoPaths = new ArrayList<String>();

	// Fragment 사이 데이터 교환할 때 쓰는 더 좋은 방법인데 지금은 사용하기 힘듬
	public FragmentMedia newInstance(String value){
		FragmentMedia fragmentMedia = new FragmentMedia();
		data = value;
		return fragmentMedia;
	}

	private void setEdit()
	{
		if (mediaCursorAdapter == null)
			return;
		else{
			boolean[] itemsIsCheck = mediaCursorAdapter.getChecked();

			if ((itemsIsCheck != null) && (isEditMode()))
			{
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
	}

	public void buildAction(FileExplorerItem paramFileExplorerItem)
	{
		Log.d(LOGTAG, "buildAction");

		if (listMenuDialog == null)
			listMenuDialog = new ListMenuDialog(getActivity(), MENU_ITEM_MEDIA, R.style.ListDialog, this);
		fileExplorerItem = paramFileExplorerItem;
		listMenuDialog.show(paramFileExplorerItem.name, 17);
	}

	public void onActivityCreated(Bundle paramBundle)
	{
		Log.d(LOGTAG, "onActivityCreated");
		super.onActivityCreated(paramBundle);
		getListView().setOnItemLongClickListener(this);
	}
	
	public void onAttach(Activity paramActivity)
	{
		Log.d(LOGTAG, "onAttach");
		super.onAttach(paramActivity);
		paramActivity.getContentResolver().registerContentObserver(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, true, contentObserver);
	}

	public boolean onBackPressed()
	{
		Log.d(LOGTAG, "onBackPressed");
		if (isEditMode())
		{
			Log.d(LOGTAG, "onBackPressed isEditMode");
			editModeListener.onCancelEditClick();
			setEditMode();
			return true;
		}
		else
		{
			Log.d(LOGTAG, "onBackPressed notEditMode");
			return super.onBackPressed();
		}
	}

	public void onCreate(Bundle paramBundle)
	{
		Log.d(LOGTAG, "onCreate");
		super.onCreate(paramBundle);
		reloadFromArguments(getArguments());
		setHasOptionsMenu(true);
	}

	public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
	{
		Log.d(LOGTAG, "onCreateOptionsMenu");
		paramMenuInflater.inflate(R.menu.menu_media, paramMenu);
		super.onCreateOptionsMenu(paramMenu, paramMenuInflater);
	}

	public void onDestroy()
	{
		Log.d(LOGTAG, "onDestroy");
		super.onDestroy();
		if (mImageFetcher != null)
			mImageFetcher.closeCache();
	}

	public void onDetach()
	{
		Log.d(LOGTAG, "onDetach");
		super.onDetach();
		getActivity().getContentResolver().unregisterContentObserver(contentObserver);
	}

	public void onEditMenuClick(View paramView)
	{
		Log.d(LOGTAG,"onEditMenuClick");
		Log.d(LOGTAG,"getID>>>>"+ paramView.getId());

		switch (paramView.getId())
		{
		default:
			break;
		case R.id.select_all:
			Log.d(LOGTAG, "onEditMenuClick select_all");
			mediaCursorAdapter.toggleChecked();
			setEdit();
			break;
		case R.id.delete:
			Log.d(LOGTAG, "onEditMenuClick delete");
			//mMediaHelper.deleteFile(mediaCursorAdapter.getCheckedMediaFiles());
			break;
		}
	}

	public void onItemClick(AdapterView paramAdapterView, View paramView, int paramInt, long paramLong)
	{
		Log.d(LOGTAG, "onItemClick");
		String mediaName = fileExplorerItem.name;
		String mediaPath = fileExplorerItem.data;

		long mediaID = fileExplorerItem._id;

		File file = new File(mediaPath);
		
		if (!file.exists())
			showLongToast(R.string.file_explorer_not_exists);
		else
		{
			if (!file.canRead())
				showLongToast(R.string.file_explorer_cannot_read);
			else
				switch (paramInt)
				{
				default:
					break;
//				case 0:
//					//startPlayer(file, false, 1);
//					break;
				case 0:
					break;
				case 1:
					break;
				case 2:
					String str4 = null;
					Cursor cursor3 = mediaCursorAdapter.getCursor();
					if ((cursor3 != null) && (cursor3.moveToFirst()))
					{
						//str4 = createPLS(str1, cursor3, cursor3.getColumnIndex("_data"), cursor3.getColumnIndex("_id"), l);
					}
					APreference localAPreference = new APreference(this.mContext);
					if ((str4 != null) && (localAPreference.getBoolean("MangoPlayer_automatic_next", false)))
						startPlayer(str4, true, 1);
					else
						startPlayer(file, true, 1);
					break;
				case 3:
					startPlayer(file, true, 0);
					break;
				case 4:
					deleteFile(file);
					break;
				case 5:
					renameFile(file, mediaID);
					break;
				case 6:
					
					Cursor videoDetailCursor = mContext.getContentResolver().query(ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaID), null, null, null, null);
					StringBuffer stringBuffer = new StringBuffer();
					try{
					if ((videoDetailCursor.getCount() == 0) || (!videoDetailCursor.moveToFirst()))
					{
						showLongToast(R.string.file_explorer_not_exists);
					}
					
					if (videoDetailCursor != null)
					{
						appendDetail(stringBuffer, R.string.video_detail_directory, videoDetailCursor.getString(videoDetailCursor.getColumnIndex("_data")));
						appendDetail(stringBuffer, R.string.video_detail_size, StringHelper.generateFileSize(videoDetailCursor.getLong(videoDetailCursor.getColumnIndex("_size"))));
						appendDetail(stringBuffer, R.string.video_detail_duration, StringHelper.generateTime(videoDetailCursor.getLong(videoDetailCursor.getColumnIndex("duration"))));
						appendDetail(stringBuffer, R.string.video_detail_artist, videoDetailCursor.getString(videoDetailCursor.getColumnIndex("artist")));
						appendDetail(stringBuffer, R.string.video_detail_album, videoDetailCursor.getString(videoDetailCursor.getColumnIndex("album")));
						appendDetail(stringBuffer, R.string.video_detail_resolution, videoDetailCursor.getString(videoDetailCursor.getColumnIndex("width")) + " x " + videoDetailCursor.getString(videoDetailCursor.getColumnIndex("height")));
						appendDetail(stringBuffer, R.string.video_detail_description, videoDetailCursor.getString(videoDetailCursor.getColumnIndex("description")));
						appendDetail(stringBuffer, R.string.video_detail_language, videoDetailCursor.getString(videoDetailCursor.getColumnIndex("language")));
					}
					
					String str3 = stringBuffer.toString();
					new CommonAlertDialog(this.mContext, R.style.ListDialog, R.string.file_meta_details, R.drawable.divider, str3, R.string.dialog_button_ok, 0, null).show();
					}
					catch(Exception e)
					{
						
					}
					finally
					{
						if (videoDetailCursor != null)
							videoDetailCursor.close();
					}
				}
		}
	}

	public void onListItemClick(ListView paramListView, View paramView, int paramInt, long id)
	{
		Log.d(LOGTAG, "onListItemClick");
		String str1 = null;

		if (isEditMode())
		{
			Log.d(LOGTAG, "onListItemClick isEditMode");
			mediaCursorAdapter.setChecked(paramInt);
			setEdit();
		}
		else
		{
			Log.d(LOGTAG, "onListItemClick notEditMode");
			
			int count = mediaCursorAdapter.getCount();
			for(int i=0; i<count; i++)
			{
				Cursor cursor2 = (Cursor)mediaCursorAdapter.getItem(i);
				videoPaths.add(cursor2.getString(cursor2.getColumnIndex(CursorUtils.VIDEO_PATH)));
			}
			
			Cursor cursor = (Cursor)mediaCursorAdapter.getItem(paramInt);
			
			//int videoID = cursor.getInt(cursor.getInt(cursor.getColumnIndex(CursorUtils.VIDEO_ID)));
			String videoPath = cursor.getString(cursor.getColumnIndex(CursorUtils.VIDEO_PATH));
			File file = new File(videoPath);

			Intent intent = new Intent(getActivity(), VideoActivity.class);

			
			Bundle bundle = new Bundle();
			bundle.putStringArrayList("videoPaths", videoPaths);
			intent.putExtra("videoPath", videoPath);
			intent.putExtras(bundle);
			startActivityForResult(intent, 1);

			if (!file.exists())
			{
				Log.d(LOGTAG, "FILE NOT EXISTS " + videoPath);
				showLongToast(R.string.file_explorer_not_exists);

				//Custom Content Provider Update 함
				mContext.getContentResolver().delete(ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id), null, null);
			}
			else if (!file.canRead())
			{
				Log.d(LOGTAG, "FILE NOT READABLE " + videoPath);
				showLongToast(R.string.file_explorer_cannot_read);
			}
			else
			{
				if (new APreference(getActivity()).getBoolean("pref_key_play_next", false))
				{
					//str1 = createPLS(file.getName(), cursor, i, j, k);
				}

				if (str1 != null)
					startPlayer(str1, false, 1);
				else
					startPlayer(file, false, 1);
			}
		}
	}
	
	@Override
    public void onActivityResult(int actionCode, int resultCode, Intent intent) {
    // TODO Auto-generated method stub
		super.onActivityResult(actionCode, resultCode, intent);
		Log.d("Level1", "onActivityResult");
		switch(resultCode)
		{
			case 0:
				mediaCursorAdapter.notifyDataSetChanged();
		}
		
    }

	public Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
	{
		Log.d(LOGTAG, "onCreateLoader");
		Log.d(LOGTAG, "onCreateLoader paramInt>>>"+paramInt);
		CursorLoader cursorLoader = null;

		if (paramInt == 2)
		{
			Log.d(LOGTAG, "onCreateLoader paramInt 2");
			String[] proj = MediaCursorAdapter.MediaQuery.PROJECTION;
			String[] proj2 = CursorUtils.proj2;
			FragmentActivity fragmentActivity = getActivity();

			//Uri uri = MediaStore.Video.Media.CONTENT_URI;
			Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

			String[] selectionArgs = new String[1];

			selectionArgs[0] = folderPath;
			cursorLoader = new CursorLoader(fragmentActivity, uri, proj2,  CursorUtils.UNIQ_VIDEO_PARENT_PATH+"= ?", selectionArgs, null);
		}
		else
		{
			if (paramInt == 3)
			{
				Log.d(LOGTAG, "onCreateLoader paramInt 3");
				String[] proj = MediaCursorAdapter.MediaSearch.PROJECTION;
				String[] proj2 = CursorUtils.proj2;
				FragmentActivity fragmentActivity = getActivity();
				//				Uri uri = MediaStore.Video.Media.CONTENT_URI;
				Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				String[] selectionArgs = new String[1];

				selectionArgs[0] =folderPath;
				Log.d(LOGTAG, "onCreateLoader folderPath>>>>"+folderPath);
				cursorLoader = new CursorLoader(fragmentActivity, uri, proj2, CursorUtils.VIDEO_PARENT_PATH+" = ?", selectionArgs, null);
			}
		}
		return cursorLoader;
	}

	public void onLoadFinished(Loader<Cursor> paramLoader, Cursor paramCursor)
	{
		Log.d(LOGTAG, "onLoadFinished");

		if (getActivity() == null)
		{
			Log.d(LOGTAG, "onLoadFinished null");
			return;
		}
		else
		{
			Log.d(LOGTAG, "onLoadFinished not null");
			int loaderID = paramLoader.getId();

			if ((loaderID == 2) || (loaderID == 3))
			{
				Log.d(LOGTAG, "onLoadFinished loaderID == 2 || 3");
				mediaCursorAdapter.changeCursor(paramCursor);
				mediaCursorAdapter.createChecked();
			}
			setEmptyData(paramCursor, 0, 0);
			//restorePosition("media_last_selection" + folderPath, "media_last_scroll_top" + folderPath);
		}
	}

	public void onLoaderReset(Loader paramLoader)
	{
		Log.d(LOGTAG, "onLoaderReset");
		mediaCursorAdapter.swapCursor(null);
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem)
	{
		Log.d(LOGTAG, "onOptionsItemSelected");

		switch (paramMenuItem.getItemId())
		{
		default:
		case R.id.menu_edit:
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
		}


		return super.onOptionsItemSelected(paramMenuItem);
	}

	public void onPause()
	{
		Log.d(LOGTAG, "onPause");
		super.onPause();
		//saveCurrentPosition("media_last_selection" + this.d, "media_last_scroll_top" + this.d);
	}

	protected void reloadFromArguments(Bundle paramBundle)
	{
		Log.d(LOGTAG, "reloadFromArguments");

		//arrayListAdapter로는 힘듬 ㅠㅠ ㅅㅂ

		//		ArrayList<VideoFileInfo> videoFileInfoList;
		//		videoFileInfoList = new ArrayList<VideoFileInfo>();
		//		VideoFileInfo aa = new VideoFileInfo();
		//		aa.setVideoName("zzz");
		//		videoFileInfoList.add(aa);
		//		//videoFileInfoList = MediaSearch.getVideoFileInfo(key);
		//		System.out.println("123123");
		//		MediaFileInfoAdapter bb = new MediaFileInfoAdapter(getActivity(), R.layout.media_info_row, videoFileInfoList);
		//		System.out.println("22222");
		//		setListAdapter(bb);

		setListAdapter(null);

		Uri uri = BaseActivity.fragmentArgumentsToIntent(paramBundle).getData(); // Cursor Uri 가져오기
		folderPath = BaseActivity.fragmentArgumentsToIntent(paramBundle).getStringExtra("folderPath");
		Log.d(LOGTAG, "reloadFromArguments folderPath>>>"+folderPath);
		if (uri == null)
			return;
		else
			Log.d(LOGTAG, "uri.getPathSegments()>>>"+uri.getPathSegments());

		List list;
		loaderID = 3;
		//		if (ScheduleHelper.isSearchUri(uri))
		//		{
		//			loaderID = 3;
		//			list = uri.getPathSegments();
		//		}

		//		for (folderPath = ((String)list.get(-1 + list.size())); ; folderPath = ScheduleHelper.getMediaDirectory(uri))
		//		{
		mediaCursorAdapter = new MediaCursorAdapter(this, mImageFetcher, fragMentMediaIconClickListener);
		editModeListener = mediaCursorAdapter.mEditModeListener;
		setListAdapter(mediaCursorAdapter);
		getLoaderManager().restartLoader(loaderID, paramBundle, this);
		loaderID = 2;
		Log.d(LOGTAG, "reloadFromArguments end");
		//		}
	}

	public final class FragMentMediaIconClickListener implements MediaCursorAdapter.IconClickListener
	{
		@Override
		public final void onIconClick(FileExplorerItem paramFileExplorerItem) {
			// TODO Auto-generated method stub
			if (paramFileExplorerItem != null)
			{
				Log.d(LOGTAG, "FragMentMediaIconClickListener onIconClick");
				buildAction(paramFileExplorerItem);
			}
		}
	}
}