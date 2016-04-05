package com.example.bongplayer2.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Video;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.example.bongplayer2.R;
import com.example.bongplayer2.VideoActivity;
import com.example.bongplayer2.R.attr;
import com.example.bongplayer2.R.drawable;
import com.example.bongplayer2.R.id;
import com.example.bongplayer2.R.layout;
import com.example.bongplayer2.R.menu;
import com.example.bongplayer2.R.string;
import com.example.bongplayer2.Utils.CursorUtils;
import com.example.bongplayer2.adapter.StreamAdapter;
import com.example.bongplayer2.adapter.StreamCursorAdapter;
import com.example.bongplayer2.bean.FileExplorerItem;
import com.example.bongplayer2.bean.StreamListViewData;
import com.example.bongplayer2.dialog.ListMenuDialog;
import com.google.code.androidsmb.DBAdapter;
import com.google.code.androidsmb.DB_Open;
import com.google.code.androidsmb.ListFiles;
import com.google.code.androidsmb.WiFiDirectActivity;
import com.yixia.zi.preference.APreference;
import com.yixia.zi.provider.Session;
import com.yixia.zi.utils.UIUtils;
import java.util.ArrayList;

public class FragmentStream extends BaseFragment
implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener
{
	private final String LOGTAG ="FragmentStream";
	private Session session;
	private StreamCursorAdapter streamCursorAdapter;
	private StreamAdapter streamAdapter;
	private ListMenuDialog listMenuDialog;
	private FileExplorerItem fileExplorerItem;
	private BaseFragment.EditModeListener fragmentStreamEditListener;
	private StreamCursorAdapter.IconClickListener FragmentStreamIconClickListener = null;
	private final FragmentStreamContentObserver fragmentStreamContentObserver = new FragmentStreamContentObserver(this, new Handler());

	private ListView sambaListView;
	private ListView httpListView;
	
	private ArrayList<StreamListViewData> streamListViewDataArray = new ArrayList<StreamListViewData>();

	private StreamListViewData sambaSection;
	private StreamListViewData httpSection;
	private StreamListViewData sambaAdd;
	private StreamListViewData httpaAdd;

	private static ArrayList<ArrayList<String>> streamURLArray = new ArrayList<ArrayList<String>>();
	private static ArrayList<StreamAdapter> streamAdapterArray;
	
	private DBAdapter database;
	private Cursor cursor;
	private View menu;
	TextView url;
	DB_Open db_open;
	private SQLiteDatabase db;
	
	private Dialog a()
	{
		Log.d(LOGTAG, "Dialog");
		AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext).setIcon(android.R.id.icon);
		View view = LayoutInflater.from(this.mContext).inflate(R.layout.dialog_one_edittext, null);
		EditText editText = (EditText)view.findViewById(R.id.dialog_one_edittext_edittext);
		builder.setTitle(R.string.action_add_group);
		builder.setView(view);
		//builder.setPositiveButton(R.string.dialog_button_ok, new nf(this, editText));
		return setDismissDialog(builder, 2);
	}

	protected void changeMenuMore(int paramInt1, int paramInt2, boolean paramBoolean)
	{
		Log.d(LOGTAG, "changeMenuMore");
		if (this.mMenuMore != null)
		{
			((ImageView)this.mMenuMore.findViewById(2131558533)).setImageResource(UIUtils.getAttrValue(getActivity(), paramInt1).resourceId);
			((TextView)this.mMenuMore.findViewById(2131558534)).setText(paramInt2);
			this.mMenuMore.setEnabled(paramBoolean);
		}
	}

	
	private void setEdit()
	{
		if (streamAdapter == null)
			return;
		else{
			boolean[] itemsIsCheck = streamAdapter.getChecked();

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

	public boolean checkNameExists(int paramInt, String paramString)
	{
		Log.d(LOGTAG, "checkNameExists");
		// Byte code:
		//   0: aload_0
		//   1: invokevirtual 111\011me/abitno/media/explorer/FragmentStreamFolder:getContentResolver\011()Landroid/content/ContentResolver;
		//   4: astore 5
		//   6: getstatic 117\011me/abitno/media/provider/StreamProvider:CONTENT_URI\011Landroid/net/Uri;
		//   9: astore 6
		//   11: iconst_1
		//   12: anewarray 119\011java/lang/String
		//   15: astore 7
		//   17: aload 7
		//   19: iconst_0
		//   20: ldc 207
		//   22: aastore
		//   23: iconst_2
		//   24: anewarray 119\011java/lang/String
		//   27: astore 8
		//   29: aload 8
		//   31: iconst_0
		//   32: new 121\011java/lang/StringBuilder
		//   35: dup
		//   36: invokespecial 122\011java/lang/StringBuilder:<init>\011()V
		//   39: iload_1
		//   40: invokevirtual 210\011java/lang/StringBuilder:append\011(I)Ljava/lang/StringBuilder;
		//   43: invokevirtual 130\011java/lang/StringBuilder:toString\011()Ljava/lang/String;
		//   46: aastore
		//   47: aload 8
		//   49: iconst_1
		//   50: aload_2
		//   51: aastore
		//   52: aload 5
		//   54: aload 6
		//   56: aload 7
		//   58: ldc 212
		//   60: aload 8
		//   62: aconst_null
		//   63: invokevirtual 216\011android/content/ContentResolver:query\011(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
		//   66: astore 9
		//   68: aload 9
		//   70: astore 4
		//   72: aload 4
		//   74: ifnull +57 -> 131
		//   77: aload 4
		//   79: invokeinterface 222 1 0
		//   84: istore 11
		//   86: iload 11
		//   88: ifle +43 -> 131
		//   91: iconst_1
		//   92: istore 10
		//   94: aload 4
		//   96: ifnull +10 -> 106
		//   99: aload 4
		//   101: invokeinterface 225 1 0
		//   106: iload 10
		//   108: ireturn
		//   109: astore_3
		//   110: aconst_null
		//   111: astore 4
		//   113: aload 4
		//   115: ifnull +10 -> 125
		//   118: aload 4
		//   120: invokeinterface 225 1 0
		//   125: aload_3
		//   126: athrow
		//   127: astore_3
		//   128: goto -15 -> 113
		//   131: iconst_0
		//   132: istore 10
		//   134: goto -40 -> 94
		//
		// Exception table:
		//   from\011to\011target\011type
		//   0\01168\011109\011finally
		//   77\01186\011127\011finally
		return true;
	}

	public void deleteCheckedGroup(ArrayList paramArrayList)
	{
		Log.d(LOGTAG, "deleteCheckedGroup");
		//new AlertDialog.Builder(getActivity()).setIcon(17301543).setTitle(2131361867).setMessage(2131361870).setPositiveButton(2131361877, new nh(this, paramArrayList)).setNegativeButton(2131361878, null).show();
	}

	public void deleteGroup(long paramLong)
	{
		Log.d(LOGTAG, "deleteGroup");
		//new AlertDialog.Builder(getActivity()).setIcon(17301543).setTitle(2131361867).setMessage(2131361870).setPositiveButton(2131361877, new ni(this, paramLong)).setNegativeButton(2131361878, null).show();
	}

	public void onActivityCreated(Bundle paramBundle)
	{
		Log.d(LOGTAG, "onActivityCreated");
		super.onActivityCreated(paramBundle);
		//getListView().setOnItemLongClickListener(this);
		
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
			
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				// TODO Auto-generated method stub
				
				StreamListViewData streamListViewData = streamAdapter.getItem(position);
				
				//Log.i(LOGTAG, "Item Click : " + position + " : " + streamListViewData.data);
				final String selectItem = streamListViewData.data;
				if(streamListViewData.type == null){
					
					return false;
				}
				else{
					final String items[] = { "Connect", "Modify", "Delete" };
					new AlertDialog.Builder(getActivity())
			        .setSingleChoiceItems(items, 0, null)
			        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int whichButton) {
			                dialog.dismiss();
			                int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
			                //0 1 ...
			                ArrayList<String> arr = new ArrayList<String>();
			                Cursor c;
			                switch(selectedPosition){
			                	case 0: // Connect
			                					                		
			                		c = db.rawQuery("SELECT url,name,pwd, _user_id FROM db_user WHERE url= '" +selectItem +"'", null);
			                		c.moveToFirst();
			                		c.getCount();
			                		String intentURL = "";
			                		String intentname = "";
			                		String intentpwd = "";
			                		
			                		for(int i=0;i<c.getCount();i++){
			                			arr.add(c.getString(0));
			                			intentURL = c.getString(0);
			                			intentname = c.getString(1);
			                			intentpwd = c.getString(2);
			                			Log.i(LOGTAG, "ITEM : " + c.getString(1) + " : " + c.getString(0));
			                			c.moveToNext();
			                		}
			                		Intent intent = new Intent(getActivity(), ListFiles.class);
			                	    intent.putExtra("IPaddress" , intentURL);
			                	    intent.putExtra("username" , intentname);
			                	    intent.putExtra("password" , intentpwd);
			                	    startActivity(intent);
			                		break;
			                	case 1: // Modify
			                		  AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
			                		     alert.setTitle("SMB Information Modify");
			                		     alert.setMessage("SMB URL :");
			                		        //Setup text area for user input
			                		     final EditText url = new EditText(getActivity());
			                		     alert.setView(url);
			                		     alert.setMessage("SMB UserName :");
			                		        //Setup text area for user input
			                		     final EditText name = new EditText(getActivity());
			                		     alert.setView(name);
			                		     alert.setMessage("SMB UserPassword :");
			                		        //Setup text area for user input
			                		     final EditText pwd = new EditText(getActivity());
			                		     alert.setView(pwd);
			                		        //Setup what happens when the user clicks either buttons
			                		     alert.setPositiveButton("Modify Information", new DialogInterface.OnClickListener() {
			                		    
			                		   @Override
			                		   public void onClick(DialogInterface dialog, int whichbutton) {
			                		    // TODO Auto-generated method stub
			                		    
			                		   }
			                		  });
			                		  alert.show();
			                		break;
			                	case 2: // Delete
			                		db_open.deleteTitle(selectItem);
			                		
			                		c = db.rawQuery("SELECT url, _user_id FROM db_user", null);
			                		c.moveToFirst();
			                		c.getCount();
			                		
			                		
			                		for(int i=0;i<c.getCount();i++){
			                			arr.add(c.getString(0));
			                			sambaAdd = new StreamListViewData();
			                			sambaAdd.data = c.getString(0);
			                			sambaAdd.type = "URL";
			                			sambaAdd.urlType = "Samba";
			                			//streamListViewDataArray.add(sambaAdd);
			                			streamAdapter.add(sambaAdd);
			                			Log.i(LOGTAG, "ITEM : " + c.getString(1) + " : " + c.getString(0));
			                			c.moveToNext();
			                		}
			                		
			                		break;
			                }
			                
			            }
			        })
			        .show();
					
					Log.i("HERE :" ,streamListViewData.data);
				}
				
				return false;
			}
		});
		//getLoaderManager().initLoader(1, null, this);
	}

	public void onAttach(Activity paramActivity)
	{
		Log.d(LOGTAG, "onAttach");
		super.onAttach(paramActivity);
		//paramActivity.getContentResolver().registerContentObserver(StreamProvider.CONTENT_URI, true, fragmentStreamContentObserver);
	}

	public boolean onBackPressed()
	{
		Log.d(LOGTAG, "onBackPressed");
		if (isEditMode())
		{
			fragmentStreamEditListener.onCancelEditClick();
			setEditMode();
			return true;
		}
		else
		{
			return super.onBackPressed();
		}
	}

	public void onCreate(Bundle paramBundle)
	{
		Log.d(LOGTAG, "onCreate");
		super.onCreate(paramBundle);
		//		streamCursorAdapter = new StreamCursorAdapter(getActivity(), FragmentStreamIconClickListener);
		//		fragmentStreamEditListener = streamCursorAdapter.mEditModeListener;
		//		setListAdapter(streamCursorAdapter);

		//		streamAdapter = new StreamAdapter(getActivity(), textViewResourceId, folderInfoList);
		db_open = new DB_Open(getActivity());
		db = db_open.getWritableDatabase();
		
		sambaSection = new StreamListViewData();
		sambaSection.data = "Samba";
		sambaSection.type = "Section";
		streamListViewDataArray.add(sambaSection);
		
		ArrayList<String> arr = new ArrayList<String>();
		Cursor c = db.rawQuery("SELECT url, _user_id FROM db_user", null);
		c.moveToFirst();
		c.getCount();
		
		for(int i=0;i<c.getCount();i++){
			arr.add(c.getString(0));
			sambaAdd = new StreamListViewData();
			sambaAdd.data = c.getString(0);
			sambaAdd.type = "URL";
			sambaAdd.urlType = "Samba";
			streamListViewDataArray.add(sambaAdd);
			//Log.i(LOGTAG, "ITEM : " + c.getString(1) + " : " + c.getString(0));
			c.moveToNext();
		}
		
		sambaAdd = new StreamListViewData();
		sambaAdd.data = "ADD Samba";
		sambaAdd.type = "AddBtn";
		sambaAdd.urlType = "Samba";
		// Here Line in Input SQLite data code.
		streamListViewDataArray.add(sambaAdd);
		
		httpSection = new StreamListViewData();
		httpSection.data = "http";
		httpSection.type = "Section";
		streamListViewDataArray.add(httpSection);

		httpaAdd = new StreamListViewData();
		httpaAdd.data = "ADD http";
		httpaAdd.type = "AddBtn";
		httpaAdd.urlType = "Http";
		streamListViewDataArray.add(httpaAdd);

		streamAdapter = new StreamAdapter(getActivity(), R.layout.file_explorer_stream_section_row, streamListViewDataArray);
		fragmentStreamEditListener = streamAdapter.mEditModeListener;
		setListAdapter(streamAdapter);
		setHasOptionsMenu(true);
		session = new Session(getActivity());

		streamAdapter.createChecked();
		
//		database = new DBAdapter(getActivity());
//		//database.deleteAll();
//		database.open();
//		
//		cursor = database.getAllTitles();
//		
//		@SuppressWarnings("deprecation")
//		ListAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.file_explorer_stream_section_row, cursor,
//				new String[] {cursor.getColumnName(cursor.getColumnIndex("url"))}, new int[] {android.R.id.text1});
		
		//setListAdapter(adapter);
		//Log.i("SQLITE","data : " + cursor.getString(cursor.getColumnIndex("url")));
		//setEmptyData(paramCursor, R.drawable.mylist_empty_tips, R.string.file_explorer_no_data);
		
	}

	public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
	{
		Log.d(LOGTAG, "onCreateView");
		tabName = "stream";

		View view = super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
		changeMenuMore(R.attr.action_add_group, R.string.action_add_group, true);		
		
		return view;
	}

	private class StreamAdapterSetAsyncTask extends AsyncTask<Void, StreamAdapter, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			Log.d(LOGTAG, "streamURLArray size>>"+streamURLArray.size());

			for (ArrayList<String> URL : streamURLArray) {
				//StreamAdapter streamAdapter = new StreamAdapter(getActivity(), R.layout.file_explorer_stream_row, URL);
				streamAdapterArray.add(streamAdapter);
				publishProgress(streamAdapter);
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(StreamAdapter... streamAdapter) {
			int currViewId = 1;
			Log.d(LOGTAG, "onProgressUpdate");
			Log.d(LOGTAG, "streamAdapterArray size>>"+streamAdapterArray.size());
			//            LinearLayout ll = (LinearLayout) findViewById(R.id.sousboissons_linearlayout);

			Log.d(LOGTAG, "onProgressUpdate for");
			//            	TextView sousCategorieTitle = new TextView(getActivity(), null);
			//            	sousCategorieTitle.setText(streamAdapter1.sousCategory);
			//            	sousCategorieTitle.setBackgroundColor(getResources().getColor(R.color.green));
			//            	sousCategorieTitle.setTextSize(19);
			//            	sousCategorieTitle.setTextColor(getResources().getColor(R.color.white));
			//            	sousCategorieTitle.setGravity(Gravity.CENTER);
			//            	LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
			//            	LinearLayout.LayoutParams.WRAP_CONTENT);

			//            	layoutParams.setMargins(0, 0, 0, 0);

			//            	sousCategorieTitle.setLayoutParams(layoutParams);
			//setListAdapter(streamAdapter);
			//            	LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
			//            			LinearLayout.LayoutParams.FILL_PARENT,
			//            			LinearLayout.LayoutParams.FILL_PARENT);
			//            	layoutParams2.setMargins(17, 0, 17, 0);
			//            	listview.setLayoutParams(layoutParams2);
			//
			//            	ll.setPadding(15, 15, 15, 0);
			//            	ll.addView(sousCategorieTitle);
			//            	ll.addView(listview);

			currViewId++;
		}
	}

	public Dialog onCreateDialog(int paramInt)
	{
		Log.d(LOGTAG, "onCreateDialog");
		switch (paramInt)
		{
		default:
			return super.onCreateDialog(paramInt);
		case 1:
			return openDialogSamba();
		case 2:
			return a();
		}
	}

	public Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
	{
		Log.d(LOGTAG, "onCreateLoader");
		String[] proj2 = {"distinct replace("+CursorUtils.VIDEO_PATH+", "+CursorUtils.VIDEO_NAME+",'')", CursorUtils.VIDEO_ID};
		return new CursorLoader(getActivity(), Video.Media.EXTERNAL_CONTENT_URI, proj2, null , null, null);
	}

	public void onLoadFinished(Loader<Cursor> paramLoader, Cursor paramCursor)
	{
		Log.d(LOGTAG, "onLoadFinished");
		if (getActivity() == null)
			return;
		else
		{
			streamCursorAdapter.changeCursor(paramCursor);
			streamAdapter.createChecked();
			setEmptyData(paramCursor, R.drawable.mylist_empty_tips, R.string.file_explorer_no_data);
			//restorePosition("stream_folder_last_selection", "stream_folder_last_scroll_top");
		}
	}

	public void onDetach()
	{
		Log.d(LOGTAG, "onDetach");
		super.onDetach();
		getActivity().getContentResolver().unregisterContentObserver(fragmentStreamContentObserver);
	}

	public void onEditMenuClick(View paramView)
	{
		Log.d(LOGTAG, "onEditMenuClick");
		switch (paramView.getId())
		{
		case 2131558530:
			streamCursorAdapter.toggleChecked();
			updateMenuStatus();
		case 2131558531:
			deleteCheckedGroup(streamCursorAdapter.getCheckedStreamFolder());
		default:
		case 2131558528:
			a().show();
		case 2131558529:
		case 2131558532:
		}
	}

	public void onItemClick(AdapterView paramAdapterView, View paramView, int paramInt, long paramLong)
	{
		Log.d(LOGTAG, "onItemClick");
		switch (paramInt)
		{
		default:
		case 0:
		case 1:
		case 2:
		}
		while (true)
		{
			//			return;
			//			nd localnd = new nd(this, getActivity().getContentResolver());
			//			String[] arrayOfString1 = StreamCursorAdapter.StreamFolderQuery.ONE_FOLDER_PROJECTION;
			//			String[] arrayOfString2 = new String[1];
			//			arrayOfString2[0] = fileExplorerItem._id;
			//			localnd.startQuery(0, null, StreamProvider.CONTENT_URI, arrayOfString1, "parent_id = ? AND is_directory = 0", arrayOfString2, "access_count DESC");
			//			continue;
			//			deleteGroup(fileExplorerItem._id);
			//			continue;
			//			renameGroup(fileExplorerItem.name, fileExplorerItem._id);
		}
	}

	public void onListItemClick(ListView paramListView, View paramView, int paramInt, long paramLong)
	{
		Log.d(LOGTAG, "onListItemClick");
		Log.d(LOGTAG, "paramInt>>>" + paramInt);
		
		if (isEditMode())
		{
			streamAdapter.setChecked(paramInt);
			updateMenuStatus();
		}
		else
		{
			StreamListViewData streamListViewData = streamAdapter.getItem(paramInt);
			String urlType = null;
			String type = null;
			
			if(streamListViewData.urlType == null){
				
				return;
				}
			else{
				urlType = streamListViewData.urlType;
			}
			if(streamListViewData.type == null){
				
				return;}
			else{
				type = streamListViewData.type;
				Log.i("HERE :" ,streamListViewData.data);
			}
			if(type.equals("URL"))
			{
				if(urlType.equals("Samba"))
				{
					
				}
				else if(urlType.equals("Http"))
				{
					
				}
				
			}
			else if(type.equals("AddBtn"))
			{
				if(urlType.equals("Samba"))
				{
					openDialogSamba().show();
				}
				else if(urlType.equals("Http"))
				{
					openDialogHttp().show();
				}
				
			}
		}
	}
		
	
	public void onLoaderReset(Loader paramLoader)
	{
		Log.d(LOGTAG, "onLoaderReset");
		streamCursorAdapter.swapCursor(null);
	}

	public void wifiOnOff(MenuItem paramMenuItem)
	{
		String service = this.mContext.WIFI_SERVICE;
		WifiManager wifi = (WifiManager)this.mContext.getSystemService(service);

		if(!wifi.isWifiEnabled()){
			if(wifi.getWifiState() != WifiManager.WIFI_STATE_ENABLED){
				wifi.setWifiEnabled(true);
				Log.i("DEBUG_TAG", "============wifi WIFI_STATE_ENABLED");
				paramMenuItem.setIcon(R.drawable.action_wifi);
			}
		}else{
			wifi.setWifiEnabled(false);
			Log.i("DEBUG_TAG", "============wifi action_wifi_search");
			paramMenuItem.setIcon(R.drawable.action_wifi_search);
		}
	}
	
	public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
	{
		Log.d(LOGTAG, "onCreateOptionsMenu");
		paramMenuInflater.inflate(R.menu.menu_stream_folder, paramMenu);
		
		String service = this.mContext.WIFI_SERVICE;
		WifiManager wifi = (WifiManager)this.mContext.getSystemService(service);
		
		if(!wifi.isWifiEnabled())
		{
			paramMenu.findItem(R.id.menu_wifi).setIcon(R.drawable.action_wifi_search);
		}
		else
		{
			paramMenu.findItem(R.id.menu_wifi).setIcon(R.drawable.action_wifi);
		}
		
		
		super.onCreateOptionsMenu(paramMenu, paramMenuInflater);
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem)
	{
		Log.d(LOGTAG, "onOptionsItemSelected");
		switch (paramMenuItem.getItemId())
		{
		case R.id.menu_wifi:
			wifiOnOff(paramMenuItem);
			break;
		case R.id.menu_search:
			Intent intent = new Intent(getActivity(), WiFiDirectActivity.class);

			startActivityForResult(intent, 1);
			
			break;
		case R.id.menu_open_url:
			openDialogSamba().show();
			break;
		case R.id.menu_edit:
			if (isEditMode())
			{
				setEditMode();
				fragmentStreamEditListener.onCancelEditClick();
				Log.d(LOGTAG, "onCancelEditClick!");
			}
			else
			{
				if(streamAdapter.getChecked().length != 0)
				{
					setEditMode();
					fragmentStreamEditListener.onEditClick();
					updateMenuStatus();
					Log.d(LOGTAG, "onEditClick!");
				}
			}
			break;
		default:
		}
		return super.onOptionsItemSelected(paramMenuItem);
	}	

	public void onPause()
	{
		Log.d(LOGTAG, "onPause");
		super.onPause();
		saveCurrentPosition("stream_folder_last_selection", "stream_folder_last_scroll_top");
	}

	public void onResume()
	{
		Log.d(LOGTAG, "onResume");
		super.onResume();
	}

	public void renameGroup(String paramString, long paramLong)
	{
		//		Log.d(LOGTAG, "renameGroup");
		//		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setIcon(17301543);
		//		View view = LayoutInflater.from(getActivity()).inflate(2130968605, null);
		//		EditText localEditText = (EditText)view.findViewById(2131558458);
		//		builder.setTitle(paramString);
		//		localEditText.setText(paramString);
		//		builder.setView(view);
		//		builder.setPositiveButton(2131361875, new ng(this, localEditText, paramString, paramLong));
		//		builder.setNegativeButton(2131361876, null);
		//		builder.show();
	}

	public void updateMenuStatus()
	{
		Log.d(LOGTAG, "updateMenuStatus");
		if (streamCursorAdapter == null)
			return;
		else
		{
			boolean[] itemsIsCheck = streamCursorAdapter.getChecked();
			ImageView imageView = (ImageView)this.mMenuDelete.findViewById(2131558530);
			if ((itemsIsCheck != null) && (isEditMode()))
			{
				int i = itemsIsCheck.length;
				int j = 0;
				int k = 0;
				while (true)
				{
					if (j < i)
					{
						if (itemsIsCheck[j] != false)
						{
							imageView.setEnabled(true);
							mMenuDelete.setEnabled(true);
						}
					}
					else
					{
						if (k != itemsIsCheck.length)
							break;
						imageView.setEnabled(false);
						mMenuDelete.setEnabled(false);
						break;
					}
					k++;
					j++;
				}
			}
			imageView.setEnabled(false);
			mMenuDelete.setEnabled(false);
		}
	}

	protected Dialog openDialogSamba()
	{
		Log.d(LOGTAG, "urlOpenDialog");
		return openDialogSamba(null);
	}

	protected Dialog openDialogHttp()
	{
		Log.d(LOGTAG, "urlOpenDialog");
		return openDialogHttp(null);
	}

	protected Dialog openDialogSamba(Bundle paramBundle)
	{
		Log.d(LOGTAG, "urlOpenDialog");

		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setIcon(UIUtils.getAttrValue(getActivity(), R.attr.file_url).resourceId);

		View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_add_samba, null);

		EditText sambaUrlText = (EditText)view.findViewById(R.id.dialog_add_samba_url_edittext);
		EditText sambaName = (EditText)view.findViewById(R.id.dialog_add_samba_name_edittext);
		EditText sambaPwd = (EditText)view.findViewById(R.id.dialog_add_samba_pwd_edittext);

		//		if (paramBundle == null)
		//		{
		//			
		//		}
		//		else
		//		{
		
		sambaUrlText.requestFocus();
		sambaUrlText.setSelectAllOnFocus(true);
		sambaUrlText.setSelection(0, sambaUrlText.getText().length());
		
		//int size = aPreference_url.getAll().size();
		
		((InputMethodManager)getActivity().getSystemService("input_method")).toggleSoftInput(2, 1);
		
		builder.setTitle(R.string.dialog_add_samba_title);
		builder.setView(view);
		builder.setPositiveButton(R.string.dialog_button_ok, new DialogOKOnClickListener(this, sambaUrlText, sambaName, sambaPwd, "Samba"));
		
				
		return setDismissDialog(builder, 1);
		//		}
	}

	protected Dialog openDialogHttp(Bundle paramBundle)
	{
		Log.d(LOGTAG, "urlOpenDialog");

		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setIcon(UIUtils.getAttrValue(getActivity(), R.attr.file_url).resourceId);

		View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_add_http, null);

		EditText httpUrlEditText = (EditText)view.findViewById(R.id.dialog_add_http_url_edittext);
		
		httpUrlEditText.requestFocus();
		httpUrlEditText.setSelectAllOnFocus(true);
		httpUrlEditText.setSelection(0, httpUrlEditText.getText().length());
		
		((InputMethodManager)getActivity().getSystemService("input_method")).toggleSoftInput(2, 1);

		builder.setTitle(R.string.dialog_add_samba_title);
		builder.setView(view);
		builder.setPositiveButton(R.string.dialog_button_ok, new DialogOKOnClickListener(this, httpUrlEditText, null, null, "Http"));

		return setDismissDialog(builder, 1);
	}

		public final class DialogOKOnClickListener
	implements DialogInterface.OnClickListener
	{
		private EditText url;
		private EditText name;
		private EditText pwd;
		private String type;

		public DialogOKOnClickListener(FragmentStream paramFragmentStream, EditText url, EditText name, EditText pwd, String type)
		{

			this.url = url;
			this.name = name;
			this.pwd = pwd;
			this.type = type;
		}

		public final void onClick(DialogInterface paramDialogInterface, int paramInt)
		{
			//streamListViewData.data = url.getText().toString();
			//"smb://"+streamListViewData.data+"/"
			//Log.d(LOGTAG, "sambaUrl>>>"+url.getText().toString() + name.getText().toString() +  pwd.getText().toString());
			db_open.insertTitle(url.getText().toString(), name.getText().toString(), pwd.getText().toString());
			
			
//			database.insertTitle(url.getText().toString(), name.getText().toString(), pwd.getText().toString());
//			cursor.requery();
			StreamListViewData streamListViewData = new StreamListViewData();
			streamListViewData.data = url.getText().toString();
			streamListViewData.type = "URL";
			streamListViewData.urlType = type;
			//Log.i("TYPE", "TYPE :" + type);//Samba
			streamAdapter.add(streamListViewData);
			
			//	    String str = this.a.getText().toString().trim();
			//	    if (!TextUtils.isEmpty(str))
			//	    {
			//	      int i = 0;
			//	      int j = this.b.getSelectedItemPosition();
			//	      if (j >= 0)
			//	        i = ((BaseFragment.Group)this.c.get(j)).id;
			//	      Intent localIntent = new Intent(this.e.getActivity(), VideoPlayActivity.class);
			//	      localIntent.setAction("android.intent.action.SEND");
			//	      localIntent.putExtra("urlName", this.d.getText().toString());
			//	      localIntent.putExtra("groupId", i);
			//	      localIntent.setData(Uri.parse(str));
			//	      this.e.startActivity(localIntent);
			//	    }
			//	    this.e.removeDialog(1);
		}
	}

	public class FragmentStreamContentObserver extends ContentObserver {
		private final String LOGTAG = "MyContentObserver";
		private FragmentStream fragmentStream;
		public FragmentStreamContentObserver(FragmentStream fragmentStream, Handler handler) {
			super(handler);
			// TODO Auto-generated constructor stub

			fragmentStream = fragmentStream;
		}

		public final void onChange(boolean paramBoolean)
		{
			Log.d(LOGTAG, "onChange");
			if (fragmentStream.getActivity() == null)
			{
				Log.d(LOGTAG, "getActivity null");
				return;
			}
			else
			{
				Loader loader = fragmentStream.getLoaderManager().getLoader(2);
				Log.d(LOGTAG, "getActivity not null");
				if (loader != null)
				{
					Log.d(LOGTAG, "loader not null forceLoad");
					loader.forceLoad();
				}
			}
		}

	}

}