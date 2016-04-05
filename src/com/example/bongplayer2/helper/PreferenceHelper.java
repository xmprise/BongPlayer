package com.example.bongplayer2.helper;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.example.bongplayer2.R;
import com.example.bongplayer2.Listener.pref.ColorPickerChangeListener;
import com.example.bongplayer2.adapter.PreferencesExpanableListAdapter;
import com.yixia.zi.preference.APreference;
import com.yixia.zi.widget.colorpicker.ColorPickerDialog;
import java.util.ArrayList;

public final class PreferenceHelper
{
  protected static final String SELECTION_MEDIA = "_directory like ?";
  private static final String[] a = new String[4];

  static
  {
    a[0] = "_directory";
    a[1] = "_data";
    a[2] = "_id";
    a[3] = "COUNT(_id)";
  }

  public static void clearHistory(Context paramContext, APreference paramAPreference)
  {
    //new AlertDialog.Builder(paramContext).setIcon(17301543).setTitle(2131361867).setMessage(2131361870).setPositiveButton(2131361877, new qb(paramContext, paramAPreference)).setNegativeButton(2131361878, null).show();
  }

  public static void showAbout(Context paramContext)
  {
    paramContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(paramContext.getString(2131362085))));
  }

  public static void showColorPicker(Context paramContext, APreference paramAPreference, String paramString, int paramInt, PreferencesExpanableListAdapter preferencesExpanableListAdapter)
  {
    new ColorPickerDialog(paramContext, R.style.Theme_ZI_Dialog_Translucent, paramAPreference.getInt(paramString, paramInt), new ColorPickerChangeListener(paramAPreference, paramString, preferencesExpanableListAdapter)).show();
  }

  public static void showHiddenFolder(Context paramContext)
  {
//    Cursor localCursor = paramContext.getContentResolver().query(MediaStore.Video.Media.CONTENT_URI, a, "hidden= 1) GROUP BY (_directory", null, "_directory COLLATE NOCASE ASC");
//    if (localCursor != null)
//    {
//      CharSequence[] arrayOfCharSequence = new CharSequence[localCursor.getCount()];
//      String[] arrayOfString = new String[localCursor.getCount()];
//      for (int i = 0; localCursor.moveToNext(); i++)
//      {
//        StringBuilder localStringBuilder = new StringBuilder().append(localCursor.getString(0)).append(" (");
//        Object[] arrayOfObject = new Object[1];
//        arrayOfObject[0] = Integer.valueOf(localCursor.getInt(3));
//        arrayOfCharSequence[i] = (paramContext.getString(2131361823, arrayOfObject) + ")");
//        arrayOfString[i] = localCursor.getString(0);
//      }
//      localCursor.close();
//      ArrayList localArrayList = new ArrayList();
//      new AlertDialog.Builder(paramContext).setIcon(2130837667).setTitle(2131361966).setMultiChoiceItems(arrayOfCharSequence, null, new qd(arrayOfString, localArrayList)).setPositiveButton(2131361877, new qc(localArrayList, paramContext)).setNegativeButton(2131361878, null).show();
//    }
  }

  public static void showLegal(Context paramContext)
  {
    //paramContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("file:///android_asset/info/legal.html"), paramContext, VInfoActivity.class));
  }

  public static void showMannul(Context paramContext)
  {
   // paramContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(paramContext.getString(2131362086))));
  }
}