/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2006 by Enea Software AB.
 * All rights reserved.
 *
 * This Software is furnished under a software license agreement and
 * may be used only in accordance with the terms of such agreement.
 * Any other use or reproduction is prohibited. No title to and
 * ownership of the Software is hereby transferred.
 *
 * PROPRIETARY NOTICE
 * This Software consists of confidential information.
 * Trade secret law and copyright law protect this Software.
 * The above notice of copyright on this Software does not indicate
 * any actual or intended publication of such Software.
 **************************************************************************
 * COPYRIGHT-END */

package com.ose.pmd.ui.view;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import com.ose.system.DumpInfo;

public class DumpContentProvider implements IStructuredContentProvider
{
   private DumpInfo[] dumps;

   public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
   {
      if (newInput instanceof DumpInfo[])
      {
         dumps = (DumpInfo[]) newInput;
      }
   }

   public void dispose()
   {
      // Nothing to be done.
   }

   public Object[] getElements(Object parent)
   {
      return ((dumps != null) ? dumps : new DumpInfo[0]);
   }
}
