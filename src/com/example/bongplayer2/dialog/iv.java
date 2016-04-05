package com.example.bongplayer2.dialog;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.bongplayer2.interfaces.iw;

public final class iv
  implements DialogInterface.OnDismissListener
{
  private List a = new ArrayList();
  private iw b;

  public void a()
  {
    Iterator localIterator1 = this.a.iterator();
    Iterator localIterator2;
//    if (!localIterator1.hasNext())
//    {
//      if (this.b == null)
//        break label93;
//      localIterator2 = this.a.iterator();
//      label36: if (localIterator2.hasNext())
//        break label63;
//    }
//    while (true)
//    {
//      return;
//      ((DialogInterface)localIterator1.next()).dismiss();
//      break;
//      label63: DialogInterface localDialogInterface = (DialogInterface)localIterator2.next();
//      localIterator2.remove();
//      this.b.b(this, localDialogInterface);
//      break label36;
//      label93: this.a.clear();
//    }
  }

  public void a(DialogInterface paramDialogInterface)
  {
    this.a.add(paramDialogInterface);
    if (this.b != null)
      this.b.a(this, paramDialogInterface);
  }

  public void a(iw paramiw)
  {
    this.b = paramiw;
  }

  public boolean a(Class paramClass)
  {
    Iterator localIterator = this.a.iterator();
//    if (!localIterator.hasNext());
//    for (int i = 0; ; i = 1)
//    {
//      return i;
//      if (!paramClass.isInstance((DialogInterface)localIterator.next()))
//        break;
//    }
    return true;
  }

  public int b()
  {
    return this.a.size();
  }

  public DialogInterface b(Class paramClass)
  {
    Iterator localIterator = this.a.iterator();
    DialogInterface localDialogInterface = null;
    if (!localIterator.hasNext())
      localDialogInterface = null;
//    while (true)
//    {
//      return localDialogInterface;
//      localDialogInterface = (DialogInterface)localIterator.next();
//      if (!paramClass.isInstance(localDialogInterface))
//        break;
//    }
    
    return localDialogInterface;
  }

  public void b(DialogInterface paramDialogInterface)
  {
    this.a.remove(paramDialogInterface);
    if (this.b != null)
      this.b.b(this, paramDialogInterface);
  }

  public boolean c(DialogInterface paramDialogInterface)
  {
    return this.a.contains(paramDialogInterface);
  }

  public void onDismiss(DialogInterface paramDialogInterface)
  {
    b(paramDialogInterface);
  }
}