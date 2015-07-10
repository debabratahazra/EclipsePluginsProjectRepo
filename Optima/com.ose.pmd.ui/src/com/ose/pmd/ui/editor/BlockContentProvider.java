/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2006-2007 by Enea Software AB.
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

package com.ose.pmd.ui.editor;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import com.ose.pmd.dump.AbstractBlock;

public class BlockContentProvider implements IStructuredContentProvider
{
   private AbstractBlock[] blocks;

   public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
   {
      if (newInput instanceof AbstractBlock[])
      {
         blocks = (AbstractBlock[]) newInput;
      }
   }

   public void dispose()
   {
      // Nothing to be done.
   }

   public Object[] getElements(Object parent)
   {
      return ((blocks != null) ? blocks : new AbstractBlock[0]);
   }
}