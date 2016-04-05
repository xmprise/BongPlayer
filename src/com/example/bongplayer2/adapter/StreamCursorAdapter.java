package com.example.bongplayer2.adapter;

import com.example.bongplayer2.base.BaseCursorAdapter;
import com.example.bongplayer2.bean.FileExplorerItem;
import com.example.bongplayer2.bean.FileExplorerRow;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;


public class StreamCursorAdapter extends BaseCursorAdapter
{
  private Activity a;
  private IconClickListener b;
  //private View.OnClickListener c = new np(this);

  public StreamCursorAdapter(Activity paramActivity, StreamCursorAdapter.IconClickListener paramIconClickListener)
  {
    super(paramActivity, null, false);
    this.a = paramActivity;
    this.b = paramIconClickListener;
  }

  public void bindView(View paramView, Context paramContext, Cursor paramCursor)
  {
//    FileExplorerRow localFileExplorerRow = (FileExplorerRow)paramView.getTag();
//    if (localFileExplorerRow == null)
//    {
//      localFileExplorerRow = new FileExplorerRow(paramView);
//      paramView.setTag(localFileExplorerRow);
//    }
//    long l = this.mCursor.getLong(this.mCursor.getColumnIndex("_id"));
//    String str1 = this.mCursor.getString(this.mCursor.getColumnIndex("title"));
//    Context localContext = this.mContext;
//    Object[] arrayOfObject = new Object[1];
//    arrayOfObject[0] = Integer.valueOf(this.mCursor.getInt(this.mCursor.getColumnIndex("url_count")));
//    String str2 = localContext.getString(2131361822, arrayOfObject);
//    FileExplorerItem localFileExplorerItem = new FileExplorerItem();
//    localFileExplorerItem._id = l;
//    localFileExplorerItem.name = str1;
//    localFileExplorerRow.name.setText(str1);
//    localFileExplorerRow.size.setText(str2);
//    //localFileExplorerRow.iconWrapper.setOnClickListener(this.c);
//    localFileExplorerRow.iconWrapper.setTag(localFileExplorerItem);
//    int i = this.mCursor.getPosition();
//    if (this.mIsShowEditMenu)
//    {
//      localFileExplorerRow.checked.setVisibility(0);
//      localFileExplorerRow.checked.setChecked(this.mChecked[i]);
//    }
//    else
//    {
//      if (localFileExplorerRow.checked.getVisibility() != 8)
//        localFileExplorerRow.checked.setVisibility(8);
//    }
  }

  public View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    return this.a.getLayoutInflater().inflate(2130968611, paramViewGroup, false);
  }
  
  public abstract interface IconClickListener
  {
    public abstract void onIconClick(FileExplorerItem paramFileExplorerItem);
  }
}