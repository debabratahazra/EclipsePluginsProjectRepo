/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2011 by Enea Software AB.
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

package com.ose.perf.ui.editor;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import com.ose.perf.ui.editor.nodes.TreeNode;

class ProfilerContentProvider implements ITreeContentProvider
{
   public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
   {
   }

   public void dispose()
   {
   }

   public Object[] getElements(Object inputElement)
   {
      return ((TreeNode) inputElement).getChildren();
   }

   public Object[] getChildren(Object parentElement)
   {
      return ((TreeNode) parentElement).getChildren();
   }

   public Object getParent(Object element)
   {
      return ((TreeNode) element).getParent();
   }

   public boolean hasChildren(Object element)
   {
      return ((TreeNode) element).hasChildren();
   }
}
