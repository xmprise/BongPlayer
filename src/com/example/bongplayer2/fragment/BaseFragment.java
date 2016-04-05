package com.example.bongplayer2.fragment;

import java.io.File;
import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;

import com.actionbarsherlock.app.SherlockListFragment;
import com.example.bongplayer2.R;
import com.example.bongplayer2.VideoActivity;
import com.example.bongplayer2.Listener.BaseFragmentListener;
import com.example.bongplayer2.Listener.FragmentIDataChangeListener;
import com.example.bongplayer2.R.anim;
import com.example.bongplayer2.R.drawable;
import com.example.bongplayer2.R.id;
import com.example.bongplayer2.R.layout;
import com.example.bongplayer2.R.string;
import com.example.bongplayer2.dialog.ListMenuItem;
import com.example.bongplayer2.helper.MediaHelper;
import com.yixia.zi.preference.APreference;
import com.yixia.zi.utils.ImageFetcher;
import com.yixia.zi.utils.Media;
import com.yixia.zi.utils.StringUtils;
import com.yixia.zi.utils.UIUtils;

public class BaseFragment extends SherlockListFragment implements AdapterView.OnItemLongClickListener{
	protected static final ArrayList<ListMenuItem> MENU_ITEM_FILE;
	protected static final ArrayList<ListMenuItem> MENU_ITEM_FOLDER;
	protected static final ArrayList<ListMenuItem> MENU_ITEM_MEDIA;
	protected static final ArrayList<ListMenuItem> MENU_ITEM_MEDIA_FOLDER;
	protected static final ArrayList<ListMenuItem> MENU_ITEM_STREAM;
	protected static final ArrayList<ListMenuItem> MENU_ITEM_STREAM_FOLDER;
	private FragmentIDataChangeListener fragmentIDataChangeListener = new FragmentIDataChangeListener(this);
	private BaseFragmentListener baseFragmentListener = new BaseFragmentListener(this);
	protected APreference mAPreference;
	protected Context mContext;
	protected View mEditMenuView;
	protected ImageFetcher mImageFetcher;
	protected View mInstruction;
	protected MediaHelper mMediaHelper;
	protected View mMenuDelete;
	protected View mMenuLine;
	protected View mMenuMore;
	protected View mMenuSelectAll;
	protected SharedPreferences mSharedPreferences;
	protected String tabName = "";

	static
	{
		MENU_ITEM_MEDIA = new ArrayList<ListMenuItem>();
		MENU_ITEM_STREAM_FOLDER = new ArrayList<ListMenuItem>();
		MENU_ITEM_STREAM = new ArrayList<ListMenuItem>();
		MENU_ITEM_FOLDER = new ArrayList<ListMenuItem>();
		MENU_ITEM_FILE = new ArrayList<ListMenuItem>();
		MENU_ITEM_MEDIA_FOLDER = new ArrayList<ListMenuItem>();

		MENU_ITEM_MEDIA_FOLDER.add(new ListMenuItem(R.string.file_explorer_play_all, R.drawable.file_explorer_action_play));
		MENU_ITEM_MEDIA_FOLDER.add(new ListMenuItem(R.string.file_meta_delete, R.drawable.file_explorer_action_remove));
		MENU_ITEM_MEDIA_FOLDER.add(new ListMenuItem(R.string.file_meta_rename, R.drawable.action_edit_light));		

		//MENU_ITEM_MEDIA.add(new ListMenuItem(R.string.file_explorer_play, R.drawable.file_explorer_action_play));
		MENU_ITEM_MEDIA.add(new ListMenuItem(R.string.file_explorer_hw_decoder, R.drawable.file_explorer_action_info));
		MENU_ITEM_MEDIA.add(new ListMenuItem(R.string.file_explorer_sw_decoder, R.drawable.file_explorer_action_info));
		MENU_ITEM_MEDIA.add(new ListMenuItem(R.string.file_explorer_play_from_start, R.drawable.file_explorer_action_replay));
		MENU_ITEM_MEDIA.add(new ListMenuItem(R.string.file_explorer_loop_file, R.drawable.file_explorer_action_loop));
		MENU_ITEM_MEDIA.add(new ListMenuItem(R.string.file_meta_delete,  R.drawable.file_explorer_action_remove));
		MENU_ITEM_MEDIA.add(new ListMenuItem(R.string.file_meta_rename, R.drawable.action_edit_light));
		MENU_ITEM_MEDIA.add(new ListMenuItem(R.string.file_meta_details, R.drawable.file_explorer_action_info));
		

		MENU_ITEM_STREAM_FOLDER.add(new ListMenuItem(R.string.file_explorer_play_all, R.drawable.file_explorer_action_play));
		MENU_ITEM_STREAM_FOLDER.add(new ListMenuItem(R.string.file_meta_delete, R.drawable.file_explorer_action_remove));
		MENU_ITEM_STREAM_FOLDER.add(new ListMenuItem(R.string.file_meta_rename, R.drawable.action_edit_light));

		MENU_ITEM_STREAM.add(new ListMenuItem(R.string.file_explorer_play, R.drawable.file_explorer_action_play));
		MENU_ITEM_STREAM.add(new ListMenuItem(R.string.file_explorer_play_from_start, R.drawable.file_explorer_action_replay));
		MENU_ITEM_STREAM.add(new ListMenuItem(R.string.file_explorer_loop_file, R.drawable.file_explorer_action_loop));
		MENU_ITEM_STREAM.add(new ListMenuItem(R.string.file_meta_delete, R.drawable.file_explorer_action_remove));
		MENU_ITEM_STREAM.add(new ListMenuItem(R.string.file_meta_modify, R.drawable.action_edit_light));
		MENU_ITEM_STREAM.add(new ListMenuItem(R.string.file_meta_details, R.drawable.file_explorer_action_info));

		MENU_ITEM_FOLDER.add(new ListMenuItem(R.string.file_explorer_play_all, R.drawable.file_explorer_action_play));
		MENU_ITEM_FOLDER.add(new ListMenuItem(R.string.file_meta_delete, R.drawable.file_explorer_action_remove));

		MENU_ITEM_FILE.add(new ListMenuItem(R.string.file_explorer_play_from_start, R.drawable.file_explorer_action_replay));
		MENU_ITEM_FILE.add(new ListMenuItem(R.string.file_explorer_loop_file, R.drawable.file_explorer_action_loop));
		MENU_ITEM_FILE.add(new ListMenuItem(R.string.file_meta_delete, R.drawable.file_explorer_action_remove));
		MENU_ITEM_FILE.add(new ListMenuItem(R.string.file_meta_rename, R.drawable.action_edit_light));
	}

	public void onCreate(Bundle paramBundle)
	{
		super.onCreate(paramBundle);
		this.mMediaHelper = new MediaHelper(getActivity(), fragmentIDataChangeListener);
		mContext = getActivity();
		if (this.mAPreference == null)
			this.mAPreference = new APreference(this.mContext);
		if (mSharedPreferences == null)
			mSharedPreferences = mContext.getSharedPreferences("MangoPlayer", 0);
		mImageFetcher = UIUtils.getImageFetcher(getActivity());
		mImageFetcher.setLoadingImage(R.drawable.thumbback);
	}

	public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
	{
		ViewGroup viewGroup = null;

		if(tabName.equals("stream"))
		{
			viewGroup = (ViewGroup)paramLayoutInflater.inflate(R.layout.stream_explorer, paramViewGroup, false);
		}
		else
			viewGroup = (ViewGroup)paramLayoutInflater.inflate(R.layout.file_explorer, paramViewGroup, false);


		mEditMenuView = viewGroup.findViewById(R.id.edit_menu);
		mInstruction = viewGroup.findViewById(R.id.instruction);
		if (mEditMenuView != null)
		{
			mMenuSelectAll = mEditMenuView.findViewById(R.id.select_all);
			mMenuSelectAll.setOnClickListener(baseFragmentListener);
			mMenuDelete = mEditMenuView.findViewById(R.id.delete);
			mMenuDelete.setOnClickListener(baseFragmentListener);
			mMenuDelete.setEnabled(false);
			//			mMenuMore = mEditMenuView.findViewById(R.id.action_more);
			//			mMenuMore.setOnClickListener(baseFragmentListener);
			//			mMenuMore.setEnabled(false);
			mMenuLine = mEditMenuView.findViewById(R.id.view_line);
		}
		return viewGroup;
	}

	public boolean onItemLongClick(AdapterView paramAdapterView, View paramView, int paramInt, long paramLong)
	{
		View localView1 = paramView.findViewById(2131558491);
		if (localView1 != null)
			localView1.performClick();
		while (true)
		{
			return false;
			//View localView2 = paramView.findViewById(2131558492);
			//	      if (localView2 == null)
			//	        continue;
			//	      localView2.performClick();
		}
	}

	protected void appendDetail(StringBuffer paramStringBuffer, int paramInt, String paramString)
	{
		if (!StringUtils.isBlank(paramString))
		{
			paramStringBuffer.append(getActivity().getString(paramInt));
			paramStringBuffer.append(" " + paramString);
			paramStringBuffer.append("\n");
		}
	}

	protected boolean isEditMode()
	{
		if (mEditMenuView != null);
		{
			if(mEditMenuView.isShown())
				return true;
			else 
				return false;
		}
	}

	protected final void saveCurrentPosition(String paramString1, String paramString2)
	{
		//		int i = 0;
		//		int j = getListView().getFirstVisiblePosition();
		//		View localView = getListView().getChildAt(0);
		//		if (localView == null);
		//		while (true)
		//		{
		//			putSharedInt(paramString2, i);
		//			putSharedInt(paramString1, j);
		//			return;
		//			i = localView.getTop();
		//		}
	}

	protected AlertDialog setDismissDialog(AlertDialog.Builder paramBuilder, int paramInt)
	{
		paramBuilder.setNegativeButton(R.string.dialog_button_cancel, new DialogCancelListener());
		AlertDialog alertDialog = paramBuilder.create();
		alertDialog.setOnDismissListener(new DialogDismissListener(this, paramInt));
		return alertDialog;
	}

	public void removeDialog(int paramInt)
	{
		if (getActivity() != null)
			getActivity().removeDialog(paramInt);
	}

	protected void setEditMode()
	{
		if (isEditMode())
			mEditMenuView.setVisibility(View.GONE);
		else
		{
			mEditMenuView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_in));
			mEditMenuView.setVisibility(View.VISIBLE);
		}
	}

	protected void setEmptyData(Cursor paramCursor, int paramInt1, int paramInt2)
	{
		if ((paramCursor == null) || (paramCursor.getCount() == 0))
			setInstruction(paramInt1, paramInt2);
		else
			setListData();
	}

	protected void setInstruction(int paramInt1, int paramInt2)
	{
		if (this.mInstruction != null)
		{
			this.mInstruction.setVisibility(0);
			if (getView() != null)
				getListView().setVisibility(8);
			ImageView localImageView = (ImageView)mInstruction.findViewById(2131558505);
			TextView localTextView = (TextView)mInstruction.findViewById(2131558506);
			if (paramInt2 != 0)
				localTextView.setText(paramInt2);
			if (paramInt1 != 0)
				localImageView.setImageResource(paramInt1);
		}
	}

	public void onDataChanged()
	{
	}

	protected void setListData()
	{
		getListView().setVisibility(View.VISIBLE);
		mInstruction.setVisibility(View.GONE);
	}

	public Dialog onCreateDialog(int paramInt)
	{
		return null;
	}

	public Dialog onCreateDialog(int paramInt, Bundle paramBundle)
	{
		return null;
	}

	public void showDialog(int paramInt)
	{
		if (getActivity() != null)
			getActivity().showDialog(paramInt);
	}

	public void showDialog(int paramInt, Bundle paramBundle)
	{
		if (getActivity() != null)
			getActivity().showDialog(paramInt, paramBundle);
	}

	public void showLongToast(int paramInt)
	{
		//ToastHelper.showToast(getActivity(), 1, paramInt);
	}

	public void showToast(int paramInt)
	{
		//ToastHelper.showToast(getActivity(), 0, paramInt);
	}

	public void showToast(int paramInt, Object[] paramArrayOfObject)
	{
		//ToastHelper.showToast(getActivity(), getActivity().getString(paramInt, paramArrayOfObject));
	}

	protected void deleteDir(File paramFile)
	{
		//this.mMediaHelper.deleteDir(paramFile);
	}

	protected void deleteFile(File paramFile)
	{

		File[] file = new File[1];
		file[0] = paramFile;
		this.mMediaHelper.deleteFile(file);
	}

	protected void renameFile(File paramFile, long paramLong)
	{
		this.mMediaHelper.renameFile(paramFile, paramLong);
	}
	
	protected void renameFolder(File paramFolderFile, String folderPath)
	{
		this.mMediaHelper.renameFolderFile(paramFolderFile, folderPath);
	}


	public void startPlayer(File paramFile, boolean paramBoolean, int paramInt)
	{
		//startPlayer(FileHelper.getCanonical(paramFile), paramBoolean, paramInt);
	}

	public void startPlayer(String paramString1, String paramString2, boolean paramBoolean, int paramInt)
	{
		startPlayer(paramString1, paramString2, paramBoolean, true, paramInt, -1);
	}

	public void startPlayer(String paramString1, String paramString2, boolean paramBoolean, int paramInt1, int paramInt2)
	{
		startPlayer(paramString1, paramString2, paramBoolean, true, paramInt1, paramInt2);
	}

	public void startPlayer(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2)
	{
		if (TextUtils.isEmpty(paramString1))
			return;
		else
		{
			Intent intent = new Intent(getActivity(), VideoActivity.class);

			if ((Media.isNative(paramString1)) && (!Uri.decode(paramString1).equals(paramString1)))
				paramString1 = Uri.encode(paramString1);

			intent.setData(Uri.parse(paramString1));

			intent.putExtra("fromStart", paramBoolean1);
			intent.putExtra("savePosition", true);
			intent.putExtra("saveUri", paramBoolean2);
			intent.putExtra("startPosition", -1.0F);
			intent.putExtra("loopCount", paramInt1);
			if (paramString2 != null)
				intent.putExtra("displayName", paramString2);

			intent.putExtra("parentId", paramInt2);

			startActivityForResult(intent, 1);
		}
	}

	public void startPlayer(String paramString, boolean paramBoolean, int paramInt)
	{
		startPlayer(paramString, null, paramBoolean, paramInt);
	}

	public void onEditMenuClick(View paramView)
	{
	}

	public boolean onBackPressed()
	{
		return false;
	}
	
	@Override
    public void onActivityResult(int actionCode, int resultCode, Intent intent) {
    // TODO Auto-generated method stub
		super.onActivityResult(actionCode, resultCode, intent);
    }

	public abstract interface EditModeListener
	{
		public abstract void onCancelEditClick();

		public abstract void onEditClick();
	}

	public final class DialogCancelListener
	implements DialogInterface.OnClickListener
	{

		public final void onClick(DialogInterface paramDialogInterface, int paramInt)
		{
			paramDialogInterface.dismiss();
			removeDialog(paramInt);
		}
	}

	public final class DialogDismissListener
	implements DialogInterface.OnDismissListener
	{
		int paramInt=0;
		public DialogDismissListener(BaseFragment paramBaseFragment, int paramInt)
		{
			this.paramInt = paramInt;
		}

		public final void onDismiss(DialogInterface paramDialogInterface)
		{
			paramDialogInterface.dismiss();
			removeDialog(paramInt);
		}
	}

	public class Group
	{
		public int id;
		public String title;

		public Group(BaseFragment paramBaseFragment, int paramInt, String paramString)
		{
			this.id = paramInt;
			this.title = paramString;
		}

		public String toString()
		{
			return this.title;
		}
	}
}
