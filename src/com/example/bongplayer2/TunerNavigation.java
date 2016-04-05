package com.example.bongplayer2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
//import iv;
//import se;

public class TunerNavigation extends DialogPreference
{
//  private final iv a = new iv();
//  private se b;
//
  public TunerNavigation(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
//
//  public TunerNavigation(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
//  {
//    super(paramContext, paramAttributeSet, paramInt);
//  }
//
//  public void onActivityDestroy()
//  {
//    this.a.a();
//    super.onActivityDestroy();
//  }
//
//  public void onClick(DialogInterface paramDialogInterface, int paramInt)
//  {
//    se localse;
//    if ((paramInt == -1) && (this.b.a))
//    {
//      SharedPreferences.Editor localEditor = L.a.edit();
//      this.b.a(localEditor);
//      localse = this.b;
//      if (!localEditor.commit())
//        break label65;
//    }
//    label65: for (boolean bool = false; ; bool = true)
//    {
//      localse.a = bool;
//      super.onClick(paramDialogInterface, paramInt);
//      return;
//    }
//  }
//
//  protected View onCreateDialogView()
//  {
//    ViewGroup localViewGroup = (ViewGroup)super.onCreateDialogView();
//    this.b = new se(null, localViewGroup, null);
//    return localViewGroup;
//  }
}