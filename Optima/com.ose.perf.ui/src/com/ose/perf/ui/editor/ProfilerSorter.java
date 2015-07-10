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

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import com.ose.perf.ui.editor.nodes.TreeNode;

class ProfilerSorter extends ViewerSorter
{
   private static final int ASCENDING = 1;
   private static final int DESCENDING = -1;

   private int column;
   private int direction;

   ProfilerSorter()
   {
      super();
      reset();
   }

   public void reset()
   {
      column = ProfilerEditor.COLUMN_ABSOLUTE;
      direction = DESCENDING;
   }

   public void setColumn(int column)
   {
      if (this.column == column)
      {
         // Same column, toggle sort direction.
         direction *= DESCENDING;
      }
      else
      {
         // New column, reset sort direction.
         this.column = column;
         direction = (column == ProfilerEditor.COLUMN_NAME) ? ASCENDING : DESCENDING;
      }
   }

   public int compare(Viewer viewer, Object e1, Object e2)
   {
      if (!(e1 instanceof TreeNode) || !(e2 instanceof TreeNode))
      {
         return 0;
      }

      TreeNode node1 = (TreeNode) e1;
      TreeNode node2 = (TreeNode) e2;

      switch (column)
      {
         case ProfilerEditor.COLUMN_NAME:
            return compareStrings(node1.toString(), node2.toString());
         case ProfilerEditor.COLUMN_BARS:
            // Fall through.
         case ProfilerEditor.COLUMN_RELATIVE:
            // Fall through.
         case ProfilerEditor.COLUMN_ABSOLUTE:
            return compareUnsignedLongs(node1.getHitCount(), node2.getHitCount());
         default:
            return 0;
      }
   }

   private int compareStrings(String s1, String s2)
   {
      int result = s1.compareTo(s2);
      if (direction == DESCENDING)
      {
         result *= DESCENDING;
      }
      return result;
   }

   private int compareUnsignedLongs(long l1, long l2)
   {
      int result;
      l1 += Long.MIN_VALUE;
      l2 += Long.MIN_VALUE;

      result = (l1 < l2) ? -1 : ((l1 > l2) ? 1 : 0);
      if (direction == DESCENDING)
      {
         result *= DESCENDING;
      }
      return result;
   }
}
