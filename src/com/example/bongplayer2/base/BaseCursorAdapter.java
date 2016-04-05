package com.example.bongplayer2.base;

import java.util.ArrayList;
import java.util.List;

import com.example.bongplayer2.Utils.CursorUtils;
import com.example.bongplayer2.fragment.BaseFragment;
import com.yixia.zi.preference.APreference;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class BaseCursorAdapter extends CursorAdapter {

	private final String LOGTAG = "BaseCursorAdapter";
	protected APreference mAPreference;
	protected boolean[] mChecked;
	public boolean selectALL = false;
	public BaseFragment.EditModeListener mEditModeListener = new EditModeListener();
	public boolean mIsShowEditMenu;

	public BaseCursorAdapter(Context paramContext, Cursor paramCursor, boolean paramBoolean)
	{
		super(paramContext, paramCursor, false);
		mAPreference = new APreference(paramContext);
	}

	@Override
	public void bindView(View arg0, Context arg1, Cursor arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
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
		if ((getCursor() != null) && (!getCursor().isClosed()))
			mChecked = new boolean[getCursor().getCount()];
	}

	public boolean[] getChecked()
	{
		return mChecked;
	}

	public List getCheckedFiles()
	{
		ArrayList arrayList = new ArrayList();
		
		if ((mChecked != null) && (mCursor != null) && (!mCursor.isClosed()))
		{
			
			for(int i=0; i<mChecked.length;i++)
			{
				if ((mChecked[i] != false) && (this.mCursor.moveToPosition(i)))
					arrayList.add(mCursor.getString(mCursor.getColumnIndex(CursorUtils.VIDEO_PARENT_PATH)));
			}
		}
		return arrayList;
	}

	public List getCheckedMediaFiles()
	{
		ArrayList arrayList = new ArrayList();
		if ((mChecked != null) && (mCursor != null) && (!mCursor.isClosed()))
		{
			for(int i=0; i<mChecked.length;i++)
			{
				if ((mChecked[i] != false) && (this.mCursor.moveToPosition(i)))
					arrayList.add(mCursor.getString(mCursor.getColumnIndex(CursorUtils.VIDEO_PATH)));
			}
		}
		return arrayList;
	}

	public ArrayList getCheckedStreamFolder()
	{
		ArrayList arrayList = new ArrayList();
		if ((mChecked != null) && (mCursor != null) && (!mCursor.isClosed()))
		{
			int i = 0;
			int j = mChecked.length;
			while (i < j)
			{
				if ((mChecked[i] != false) && (mCursor.moveToPosition(i)))
					arrayList.add(Long.valueOf(mCursor.getLong(mCursor.getColumnIndex("_id"))));
				i++;
			}
		}
		return arrayList;
	}

	public void setChecked(int paramInt)
	{
		if (mChecked[paramInt] == false)
		{
			mChecked[paramInt] = true;
		}
		else
		{
			mChecked[paramInt] = false;
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

	public final class EditModeListener
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
