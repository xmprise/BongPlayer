package com.example.bongplayer2.adapter;

import io.vov.vitamio.MediaMetadataRetriever;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.provider.MediaStore.Video.Thumbnails;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bongplayer2.R;
import com.example.bongplayer2.Utils.CursorUtils;
import com.example.bongplayer2.base.BaseCursorAdapter;
import com.example.bongplayer2.bean.FileExplorerItem;
import com.example.bongplayer2.bean.FileExplorerRow;
import com.example.bongplayer2.fragment.FragmentMedia;
import com.example.bongplayer2.fragment.FragmentMedia.FragMentMediaIconClickListener;
import com.yixia.zi.preference.APreference;
import com.yixia.zi.utils.ImageFetcher;
import com.yixia.zi.utils.StringHelper;
import com.yixia.zi.widget.cropimage.BitmapManager;
import com.yixia.zi.widget.cropimage.BitmapUtils;

public class MediaCursorAdapter extends BaseCursorAdapter
{
	private final String LOGTAG = "MediaCursorAdapter";
	private Activity activity;
	//private Session session;
	private IconClickListener mediaIconClickListener;
	private MediaCursorIconClickListener mediaCursorIconClickListener = new MediaCursorIconClickListener();
	private final ForegroundColorSpan e = new ForegroundColorSpan(Color.parseColor("#777777"));
	protected ImageFetcher mImageFetcher;
//	private String lastPlayVideoPath;
//	private long lastPlayVideoCurrentTime; 
	
	public MediaCursorAdapter(FragmentMedia paramFragmentMedia, ImageFetcher paramImageFetcher, FragMentMediaIconClickListener paramIconClickListener)
	{
		super(paramFragmentMedia.getActivity(), null, false);
		Log.d(LOGTAG, "MediaCursorAdapter");
		activity = paramFragmentMedia.getActivity();
		mImageFetcher = paramImageFetcher;
		//session = new Session(activity);
		mediaIconClickListener = paramIconClickListener;
	}

	public void bindView(View paramView, Context paramContext, Cursor paramCursor)
	{
		Log.d(LOGTAG, "bindView");
		FileExplorerRow fileExplorerRow = (FileExplorerRow)paramView.getTag();
		
		if (fileExplorerRow == null)
		{
			Log.d(LOGTAG, "bindView fileExplorerRow null");
			fileExplorerRow = new FileExplorerRow(paramView);
			paramView.setTag(fileExplorerRow);
		}
		
		//String title = mCursor.getString(mCursor.getColumnIndex("title"));
		String title = null;
		String str2 = null;
		String path = null;
		
		int duration = 0;
		
		String videoID = mCursor.getString(mCursor.getColumnIndex(CursorUtils.VIDEO_ID));
		String videoPath = mCursor.getString(mCursor.getColumnIndex(CursorUtils.VIDEO_PATH));
		
		fileExplorerRow.name.setText(mCursor.getString(mCursor.getColumnIndex(CursorUtils.VIDEO_NAME)));
		fileExplorerRow.size.setText(StringHelper.generateFileSize(mCursor.getLong(mCursor.getColumnIndex(CursorUtils.VIDEO_SIZE))));		
		fileExplorerRow.duration.setText(StringHelper.generateTime(mCursor.getLong(mCursor.getColumnIndex(CursorUtils.VIDEO_TIME))));
		FileExplorerItem fileExplorerItem = new FileExplorerItem();
		
		fileExplorerItem._id = mCursor.getInt(mCursor.getColumnIndex(CursorUtils.VIDEO_ID));
		fileExplorerItem.data = videoPath;
		fileExplorerItem.name = mCursor.getString(mCursor.getColumnIndex(CursorUtils.VIDEO_NAME));
		
		ImageView imageView = fileExplorerRow.icon;
		//ThumbnailUtils.createVideoThumbnail(getColumeValue(videoListCursor, proj2[2]), android.provider.MediaStore.Video.Thumbnails.MICRO_KIND);
		
		Uri thumbnail = MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{MediaStore.Video.Thumbnails.DATA};
        String where = MediaStore.Video.Thumbnails.VIDEO_ID+"=?";
        String[] whereArgs = new String[]{videoID};
        Cursor thumb = mContext.getContentResolver().query(thumbnail, projection, where, whereArgs, null);
        
        thumb.moveToFirst();
    	
        try{        	
        	String thumbPath = thumb.getString(0);
    		mImageFetcher.loadLocalImage(thumbPath, imageView);
        }
        catch(Exception e)
        {
        	CreateThumbTask createThumbTask = new CreateThumbTask(imageView);
        	createThumbTask.execute();
        }
        thumb.close();
        
        fileExplorerRow.icon.setTag(fileExplorerItem);
		fileExplorerRow.icon.setOnClickListener(mediaCursorIconClickListener);

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
			

			if(mAPreference.getString("Last_Play_Video_Path", "").equals(videoPath))
			{
				long lastPlayVideoCurrentTime = mAPreference.getLong("Last_Play_Video_CurrentTime", 0L);
				fileExplorerRow.name.setTextColor(mContext.getResources().getColor(R.color.listview_item_title_last_play_color));
				if(lastPlayVideoCurrentTime > 0L)
					fileExplorerRow.duration.setText(highlightText(StringHelper.generateTime(lastPlayVideoCurrentTime) ,StringHelper.generateTime(mCursor.getLong(mCursor.getColumnIndex(CursorUtils.VIDEO_TIME)))));
			}
			else
				fileExplorerRow.name.setTextColor(mContext.getResources().getColor(R.color.gray));

			fileExplorerRow.checked.setVisibility(View.GONE);
			//fileExplorerRow.name.setTextColor(mContext.getResources().getColor(UIUtils.getAttrValue(activity, R.attr.listview_item_title_color).resourceId));
		}

		
//		if (title == null)
//		{
//			str2 = "";
//			fileExplorerRow.name.setText(str2);
//			fileExplorerRow.progress.setVisibility(View.GONE);
//			
//			long size = mCursor.getLong(mCursor.getColumnIndex(CursorUtils.VIDEO_SIZE));
//			
//			fileExplorerRow.size.setText(StringHelper.generateFileSize(size));
//			
//			path = mCursor.getString(mCursor.getColumnIndex("_data"));
//			
//			duration = mCursor.getInt(mCursor.getColumnIndex("duration"));
//			
			//float f = b.getFloat(path + ".last", 7.7F);
//			if ((f <= 0.0F) || (f >= 1.0F))
//				break label495;
//			
//			fileExplorerRow.duration.setText(highlightText(StringHelper.generateTime((int)(f * duration)), StringHelper.generateTime(duration)));
//			
//			long id = mCursor.getLong(mCursor.getColumnIndex("_id"));
//			
//			FileExplorerItem localFileExplorerItem = new FileExplorerItem();
//			
//			localFileExplorerItem._id = id;
//			localFileExplorerItem.data = path;
//			localFileExplorerItem.name = str2;
//			
//			fileExplorerRow.icon.setTag(localFileExplorerItem);
//			fileExplorerRow.icon.setOnClickListener(this.d);
//			
//			ImageView imageView = fileExplorerRow.icon;
//			
//			//String thumbnailPath = MediaStore.Video.Thumbnails.getThumbnailPath(mContext.getApplicationContext(), mContext.getContentResolver(), id);
//			
//			//mImageFetcher.loadLocalImage(thumbnailPath, imageView);
//			
//			int position = mCursor.getPosition();
//			if (!mIsShowEditMenu)
//				return;
//			fileExplorerRow.checked.setVisibility(View.VISIBLE);
//			fileExplorerRow.checked.setChecked(mChecked[position]);
//			
//			if (mCursor.getInt(mCursor.getColumnIndex("play_status")) != 1)
//				return;
//			fileExplorerRow.name.setTextColor(mContext.getResources().getColor(UIUtils.getAttrValue(activity, R.attr.listview_item_played_color).resourceId));
//		}
//		else
//		{
//			if (mAPreference.getString("media_last_play", "").equals(path))
//				fileExplorerRow.name.setTextColor(mContext.getResources().getColor(R.color.listview_item_title_last_play_color));
//			str2 = title.trim();
//
//			fileExplorerRow.duration.setText(StringHelper.generateTime(duration));
//			
//			if (fileExplorerRow.checked.getVisibility() == View.GONE)
//				
//			fileExplorerRow.checked.setVisibility(View.GONE);
//			
//			fileExplorerRow.name.setTextColor(mContext.getResources().getColor(UIUtils.getAttrValue(activity, R.attr.listview_item_title_color).resourceId));
//		}
	}

	public CharSequence highlightText(String paramString1, String paramString2)
	{
		SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder(paramString1 + "/" + paramString2);
		localSpannableStringBuilder.setSpan(this.e, 0, paramString1.length(), 33);
		return localSpannableStringBuilder;
	}

	public View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
	{
		Log.d(LOGTAG, "newView");
		return activity.getLayoutInflater().inflate(R.layout.file_explorer_media_row, paramViewGroup, false);
	}

	public abstract interface IconClickListener
	{
		public abstract void onIconClick(FileExplorerItem paramFileExplorerItem);
	}

	public abstract interface MediaQuery
	{
		public static final String ORDER_MEDIA_TITLE = "title_key COLLATE NOCASE ASC";
		public static final String[] PROJECTION = {"_id", "title", "title_key", "_size", "duration", "_data", "available_size", "play_status"};
		public static final String SELECTION = "_directory = ?";
		public static final String[] SIM_PROJECTION = {"_id","_data"};
		public static final String SORT_ORDER = "_directory COLLATE NOCASE ASC";
		public static final int _TOKEN = 2;

	}

	public abstract interface MediaSearch
	{
		public static final String[] PROJECTION = MediaCursorAdapter.MediaQuery.PROJECTION;
		public static final String SELECTION = "_display_name like ?";
		public static final String SORT_ORDER = "title_key COLLATE NOCASE ASC";
		public static final int _TOKEN = 3;
	}

	public final class MediaCursorIconClickListener implements View.OnClickListener
	{
		public final void onClick(View paramView)
		{
			if (mediaIconClickListener != null)
			{
				Log.d(LOGTAG, "mediaIconClickListener != null");
				FileExplorerItem fileExplorerItem = (FileExplorerItem)paramView.getTag();
				mediaIconClickListener.onIconClick(fileExplorerItem);
			}
		}
	}
	
	class CreateThumbTask extends AsyncTask<Void, String, Bitmap>{
        //상속받은 클래스를 사용하려면 반드시 구현해야 하는 메소드로 지정된
        //작업을 마칠때까지 필요한 시간동안 계속해서 실행된다. 끝나지 않고 무한정
        //실행해야 하는 작업은 AsyncTask로 실행하기에 적당하지 않다
        private ImageView imageView;
        private String videoPath;
		public CreateThumbTask(ImageView imageView)
		{
			this.imageView = imageView;
		}
		
		@Override
        protected Bitmap doInBackground(Void... unused){
			
			videoPath = mCursor.getString(mCursor.getColumnIndex(CursorUtils.VIDEO_PATH));
			Bitmap thumbBitmap = mImageFetcher.processBitmap(videoPath, 0);
			
            return thumbBitmap;
        }
        
        //doInBackground() 메소드에서 publishProgress() 메소드를 호출하면
        //onProgressUpdate() 메소드에서 인자로 넘겨준 값을 받게된다
        //(사용자 인터페이스에서 실행되므로 주의)
        @Override
        protected void onProgressUpdate(String... item){
        	
        }
        
        //doInBackground() 메소드의 작업이 완료된 직후 호출됨
        @Override
        protected void onPostExecute(Bitmap thumbBitmap){
        	//mImageFetcher.loadLocalImage(videoPath, imageView);
        	imageView.setImageBitmap(thumbBitmap);
        }
    }
}