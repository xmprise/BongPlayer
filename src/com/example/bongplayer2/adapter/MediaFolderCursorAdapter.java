package com.example.bongplayer2.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore.Video;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bongplayer2.R;
import com.example.bongplayer2.Utils.CursorUtils;
import com.example.bongplayer2.base.BaseCursorAdapter;
import com.example.bongplayer2.bean.FileExplorerItem;
import com.example.bongplayer2.bean.FileExplorerRow;
import com.example.bongplayer2.fragment.FragmentMediaFolder;
import com.yixia.zi.preference.APreference;

public class MediaFolderCursorAdapter extends BaseCursorAdapter {

	private final String LOGTAG = "MediaFolderCursorAdapter";
	private Activity activity;
	private IconClickListener iconClickListener;
	private MeidaFolderCursorIconClickListener meidaFolderCursorIconClickListener = new MeidaFolderCursorIconClickListener();
	private String lastPlayVideoPath;
	public MediaFolderCursorAdapter(FragmentMediaFolder paramFragmentMediaFolder, MediaFolderCursorAdapter.IconClickListener paramIconClickListener)
	{
		super(paramFragmentMediaFolder.getActivity(), null, false);
		activity = paramFragmentMediaFolder.getActivity();
		iconClickListener = paramIconClickListener;
		lastPlayVideoPath = mAPreference.getString("", "");
		
		Log.d(LOGTAG, "MediaFolderCursorAdapter");
	}

	//this paramView is newView function return view;
	public void bindView(View paramView, Context paramContext, Cursor paramCursor)
	{
		Log.d(LOGTAG, "bindView");
		FileExplorerRow fileExplorerRow = (FileExplorerRow)paramView.getTag();

		if (fileExplorerRow == null)
		{
			fileExplorerRow = new FileExplorerRow(paramView);
			paramView.setTag(fileExplorerRow);
		}
		
		String folderPath = mCursor.getString(0); // folder Path
		
		String folderPathSplit[] = folderPath.split("/", 0);

		String[] proj2 = {CursorUtils.VIDEO_PARENT_PATH, CursorUtils.VIDEO_PATH, CursorUtils.VIDEO_NAME, CursorUtils.VIDEO_SIZE, CursorUtils.VIDEO_TIME, CursorUtils.VIDEO_RESOLUTION, CursorUtils.VIDEO_LNG};

		//int i = mCursor.getInt(mCursor.getColumnIndex("video_count"));
		//int j = mCursor.getInt(mCursor.getColumnIndex("play_status"));

		//Log.d(LOGTAG, "bindView i>>>>"+i);
		//Log.d(LOGTAG, "bindView j>>>>"+j);
		
		fileExplorerRow.progress.setVisibility(View.GONE);

		fileExplorerRow.name.setText(folderPathSplit[folderPathSplit.length-1]); // folderName set

		FileExplorerItem fileExplorerItem = new FileExplorerItem();
		
		fileExplorerItem.data = folderPath;
		fileExplorerItem.folerName = folderPathSplit[folderPathSplit.length-1];
		
		TextView sizeTextView = fileExplorerRow.size;
		Context context = mContext;
		
		//폴더별 비디오 갯수 카운팅 커서
		Uri[] uri = new Uri[] { Video.Media.EXTERNAL_CONTENT_URI, Video.Media.INTERNAL_CONTENT_URI };
		String[] videoCountProj = {CursorUtils.VIDEO_PARENT_PATH};
		String videoCountSelection = CursorUtils.VIDEO_PARENT_PATH+"= ?";
		String videoCountSelectionArgs[] = {mCursor.getString(0)};
		Cursor cursor = mContext.getContentResolver().query(uri[0], videoCountProj, videoCountSelection, videoCountSelectionArgs, null);

		Object[] arrayOfObject = new Object[1];
		arrayOfObject[0] = cursor.getCount();

		sizeTextView.setText(context.getString(R.string.file_explorer_videos, arrayOfObject));
		sizeTextView.setTextColor(Color.parseColor("#0000ff"));
		
		fileExplorerRow.icon.setTag(fileExplorerItem);
		fileExplorerRow.icon.setOnClickListener(meidaFolderCursorIconClickListener);

		if (fileExplorerRow.progress.getVisibility() != View.GONE)
			fileExplorerRow.progress.setVisibility(View.GONE);

		int position = mCursor.getPosition();

		if (mIsShowEditMenu)
		{
			Log.d(LOGTAG, "mIsShowEditMenu>>>"+ mIsShowEditMenu);
			fileExplorerRow.checked.setVisibility(View.VISIBLE);
			fileExplorerRow.checked.setChecked(mChecked[position]);
			//if (j != i)
			//fileExplorerRow.name.setTextColor(mContext.getResources().getColor(UIUtils.getAttrValue(activity, R.attr.listview_item_title_color).resourceId));
		}
		else
		{
			Log.d(LOGTAG, "mIsShowEditMenu>>>"+ mIsShowEditMenu);
			//			String str2 = mAPreference.getString("media_last_play", "");
			//			 
			//			int m = str2.lastIndexOf("/");
			//			
			//			if ((m != -1) && (str1.equals(str2.substring(0, m))))
			//				fileExplorerRow.name.setTextColor(mContext.getResources().getColor(R.color.listview_item_title_last_play_color));

			fileExplorerRow.checked.setVisibility(View.GONE);
			//fileExplorerRow.name.setTextColor(mContext.getResources().getColor(UIUtils.getAttrValue(activity, R.attr.listview_item_title_color).resourceId));
		}
	}

	public View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
	{
		return activity.getLayoutInflater().inflate(R.layout.file_explorer_media_folder_row, paramViewGroup, false);
	}

	public final class MeidaFolderCursorIconClickListener
	implements View.OnClickListener
	{
		public final void onClick(View paramView)
		{
			if (iconClickListener != null)
			{
				FileExplorerItem fileExplorerItem = (FileExplorerItem)paramView.getTag();
				iconClickListener.onIconClick(fileExplorerItem);
			}
		}
	}

	public abstract interface IconClickListener
	{
		public abstract void onIconClick(FileExplorerItem fileExplorerItem);
	}

	public abstract interface MediaFolderQuery
	{
		public static final String PLAY_STATUS = "play_status";
		public static final String SELECTION = "hidden= 0) GROUP BY (_directory";
		public static final String SORT_ORDER = "_directory_name COLLATE NOCASE ASC";
		public static final String VIDEO_COUNT = "video_count";
		public static final int _TOKEN = 1;

		public static final String[] PROJECTION = {"_id", "_directory", "_directory_name", "COUNT(_id) as video_count", "COUNT(play_status) as play_status"};
		public static final String[] SIM_PROJECTION = {"_id","_data"};
	}
}
