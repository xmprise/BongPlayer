package com.example.bongplayer2.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.bongplayer2.FolderInfo;
import com.example.bongplayer2.R;
import com.example.bongplayer2.base.BaseCursorAdapter.EditModeListener;
import com.example.bongplayer2.bean.FileExplorerRow;
import com.example.bongplayer2.bean.StreamListViewData;
import com.example.bongplayer2.fragment.BaseFragment;

import android.content.Context;
import android.content.res.TypedArray;

public class StreamAdapter extends ArrayAdapter<StreamListViewData> {

	private final String LOGTAG ="StreamAdapter";

	private ArrayList<StreamListViewData> streamListViewDataArray;
	private ArrayList<Section> sections = new ArrayList<Section>();
	private static int TYPE_SECTION_HEADER = 1;

	private Context context;
	private int Resid;
	private LayoutInflater inflater = null;
	private LayoutInflater vi = null;
	private ArrayList infoList = null;
	private ViewHolder viewHolder;

	private int sambaBtnIndex;
	private int httpBtnIndex;
	private int ftpBtnIndex;

	private HashMap<Integer, Integer> positionMap = new HashMap<Integer, Integer>(); 
	
	protected boolean[] mChecked;
	public boolean selectALL = false;
	//public BaseFragment.EditModeListener mEditModeListener = new EditModeListener();
	public boolean mIsShowEditMenu;
	public BaseFragment.EditModeListener mEditModeListener = new StreamEditModeListener();
	
	private int urlDataCount = 0;

	public StreamAdapter(Context context, int textViewResourceId, ArrayList<StreamListViewData> streamListViewDataArray) {
		super(context, textViewResourceId, streamListViewDataArray);

		Log.d(LOGTAG, "StreamAdapter");

		this.Resid = textViewResourceId;
		this.streamListViewDataArray = streamListViewDataArray;
		//this.inflater = LayoutInflater.from(context);
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;

	}

	public void addSection(String caption, Adapter adapter) {
		sections.add(new Section(caption, adapter));
	}

	public void setResid(int resid) {
		this.Resid = resid;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public StreamListViewData getItem(int position) {

		return streamListViewDataArray.get(position);
	}

	public int getCount() {		

		return streamListViewDataArray.size();
	}

	public boolean areAllItemsSelectable() {
		return (false);
	}

	public boolean isEnabled(int position) {
		return (getItemViewType(position) != TYPE_SECTION_HEADER);
	}

	public int getItemViewType(int position) {
		return 0;
	}


	public int getViewTypeCount() {
		return 3;
	}


	@Override
	public void add(StreamListViewData streamListViewData)
	{
		String type = streamListViewData.type;
		String urlType = streamListViewData.urlType;
		int addIndex=0;

		if(urlType.equals("Samba"))
		{
			Log.i("HERE", "HERE");
			addIndex = sambaBtnIndex; 
		}
		else if(urlType.equals("Http"))
		{
			addIndex = httpBtnIndex;

		}
		else if(urlType.equals("Ftp"))
		{
			addIndex = ftpBtnIndex;

		}

		Log.d(LOGTAG,"addIndex>>>>"+addIndex);

		streamListViewDataArray.add(addIndex, streamListViewData);
		
		notifyDataSetChanged();
		
		createChecked();
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = null;
		Log.d(LOGTAG, "getView");
		Log.d(LOGTAG, "position>>>>"+position);
		
		if(position == 0 )
			urlDataCount = 0;
		
		Log.d(LOGTAG, "urlDataCount>>>"+urlDataCount);
		
		StreamListViewData streamListViewData = streamListViewDataArray.get(position);  

		String type = streamListViewData.type;
		String urlType = streamListViewData.urlType;

		Log.d(LOGTAG,"type>>>>"+type);
		Log.d(LOGTAG,"urlType>>>>"+urlType);

		if(type.equals("Section"))
		{
			view = inflater.inflate(R.layout.file_explorer_stream_section_row, null);
			Log.d(LOGTAG,"Section view");
		}
		else if(type.equals("AddBtn"))
		{
			view = inflater.inflate(R.layout.file_explorer_stream_addbtn_row, null);
			Log.d(LOGTAG,"AddBtn view");
		}
		else if(type.equals("URL"))
		{
			view = inflater.inflate(R.layout.file_explorer_stream_url_row, null);
			Log.d(LOGTAG,"URL view");
		}

		if(streamListViewData != null) {
			if(type.equals("Section")) {

				TextView sectionName;                    
				sectionName = (TextView) view.findViewById(R.id.section_name);
				sectionName.setText(streamListViewData.data);

			} else if(type.equals("AddBtn")) {
				if(urlType.equals("Samba"))
				{
					sambaBtnIndex = position;
					Log.d(LOGTAG, "sambaBtnIndex>>>"+sambaBtnIndex);
				}
				else if(urlType.equals("Http"))
				{
					httpBtnIndex = position;
					Log.d(LOGTAG, "httpBtnIndex>>>"+httpBtnIndex);
				}
				else if(urlType.equals("Ftp"))
				{
					ftpBtnIndex = position;
					Log.d(LOGTAG, "ftpBtnIndex>>>"+ftpBtnIndex);
				}

				TextView addBtnName;
				ImageView addBtnImage;
				addBtnName = (TextView) view.findViewById(R.id.file_name);
				addBtnImage = (ImageView) view.findViewById(R.id.file_icon);
				addBtnName.setText(streamListViewData.data);

			} else if(type.equals("URL")) {
				
				FileExplorerRow fileExplorerRow = (FileExplorerRow)view.getTag();

				if (fileExplorerRow == null)
				{
					fileExplorerRow = new FileExplorerRow(view);
					view.setTag(fileExplorerRow);
				}
				
				TextView url;
				ImageView urlImage;
				url = (TextView) view.findViewById(R.id.file_name);
				urlImage = (ImageView) view.findViewById(R.id.file_icon);
				url.setText(streamListViewData.data);
				
				positionMap.put(position, urlDataCount);
				urlDataCount++;
				
				if (mIsShowEditMenu)
				{
					Log.d(LOGTAG, "mIsShowEditMenu>>>"+ mIsShowEditMenu);
					fileExplorerRow.checked.setVisibility(View.VISIBLE);
					fileExplorerRow.checked.setChecked(mChecked[positionMap.get(position)]);
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
		}

		//		if (v == null) {
		//			Log.d(LOGTAG, "getView v == null");
		//			LayoutInflater vi = (LayoutInflater) context
		//					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//			v = vi.inflate(Resid, null);
		//
		//			viewHolder = new ViewHolder();
		//			viewHolder.streamURL = (TextView)v.findViewById(R.id.file_name);
		//			v.setTag(viewHolder);
		//		}
		return view;
	}


	public void clearChecked()
	{
		if ((mChecked != null) && (mChecked.length > 0))
		{
			int i = mChecked.length;
			for (int j = 0; j < i; j++)
				mChecked[j] = false;
		}
		notifyDataSetChanged();
	}

	public void createChecked()
	{
		if (streamListViewDataArray != null)
		{
			int count = getUrlDataCount();
			mChecked = new boolean[count];
		}
	}
	
	public int getUrlDataCount()
	{
		int count =0;
		for(StreamListViewData data : streamListViewDataArray)
		{
			if(data.type.equals("URL"))
			{
				count++;
			}
		}
		
		return count;
	}

	public boolean[] getChecked()
	{
		return mChecked;
	}

	public ArrayList getCheckedStream()
	{
		ArrayList arrayList = new ArrayList();
		//		if ((mChecked != null) && (mCursor != null) && (!mCursor.isClosed()))
		//		{
		//			int i = 0;
		//			int j = mChecked.length;
		//			while (i < j)
		//			{
		//				if ((mChecked[i] != false) && (mCursor.moveToPosition(i)))
		//					arrayList.add(Long.valueOf(mCursor.getLong(mCursor.getColumnIndex("_id"))));
		//				i++;
		//			}
		//		}
		return arrayList;
	}

	public void setChecked(int position)
	{
		if (mChecked[positionMap.get(position)] == false)
		{
			mChecked[positionMap.get(position)] = true;
		}
		else
		{
			mChecked[positionMap.get(position)] = false;
		}
		notifyDataSetChanged();
	}

	public void toggleChecked()
	{
		if ((mChecked != null) && (mChecked.length > 0) && selectALL != true)
		{

			for(int i=0;i<mChecked.length;i++)
			{
				mChecked[i] = true;
			}
			selectALL = true;
		}
		else
		{
			for(int i=0;i<mChecked.length;i++)
			{
				mChecked[i] = false;
			}
			selectALL = false;
		}

		notifyDataSetChanged();
	}

	//	public final class EditModeListener
	//	implements BaseFragment.EditModeListener
	//	{
	//		public final void onCancelEditClick()
	//		{
	//			Log.d(LOGTAG, "onCancelEditClick");
	//			mIsShowEditMenu = false;
	//			clearChecked();
	//			notifyDataSetChanged();
	//		}
	//
	//		public final void onEditClick()
	//		{
	//			Log.d(LOGTAG, "onEditClick");
	//			mIsShowEditMenu = true;
	//			notifyDataSetChanged();
	//		}
	//	}


	class ViewHolder
	{
		TextView streamURL;
	}

	class Section {
		String caption;
		Adapter adapter;

		Section(String caption, Adapter adapter) {
			this.caption = caption;
			this.adapter = adapter;
		}
	}

	public final class StreamEditModeListener
	implements BaseFragment.EditModeListener
	{
		public final void onCancelEditClick()
		{
			Log.d(LOGTAG, "onCancelEditClick");
			mIsShowEditMenu = false;
			clearChecked();
			notifyDataSetChanged();
		}

		public final void onEditClick()
		{
			Log.d(LOGTAG, "onEditClick");
			mIsShowEditMenu = true;
			notifyDataSetChanged();
		}
	}
}

