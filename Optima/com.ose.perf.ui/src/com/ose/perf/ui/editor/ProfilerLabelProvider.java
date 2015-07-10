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

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import com.ose.perf.ui.editor.nodes.TreeNode;

class ProfilerLabelProvider extends LabelProvider implements ITableLabelProvider
{
   public String getColumnText(Object element, int columnIndex)
   {
      TreeNode node = (TreeNode) element;

      switch (columnIndex)
      {
         case ProfilerEditor.COLUMN_NAME:
            return node.toString();
         case ProfilerEditor.COLUMN_BARS:
            return null;
         case ProfilerEditor.COLUMN_RELATIVE:
            return Double.toString(Math.round(1000.0 * node.getRelativeHitCount())
                  / 10.0) + "%";
         case ProfilerEditor.COLUMN_ABSOLUTE:
            return toUnsignedLongString(node.getHitCount());
         default:
            return null;
      }
   }

   public Image getColumnImage(Object element, int columnIndex)
   {
      if (columnIndex == ProfilerEditor.COLUMN_NAME)
      {
         TreeNode node = (TreeNode) element;
         return node.getImage();
      }
      return null;
   }

   private static String toUnsignedLongString(long l)
   {
      return (l < 0) ? "0x" + Long.toHexString(l).toUpperCase() : Long.toString(l);
   }
}
