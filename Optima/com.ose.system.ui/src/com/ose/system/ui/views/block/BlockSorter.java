/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2005-2007 by Enea Software AB.
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

package com.ose.system.ui.views.block;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import com.ose.system.BlockInfo;

public class BlockSorter extends ViewerSorter
{
   private static final int ASCENDING = 1;
   private static final int DESCENDING = -1;

   private int column;
   private int direction;

   BlockSorter()
   {
      super();
      reset();
   }

   public void reset()
   {
      column = 0;
      direction = ASCENDING;
   }

   public void sortByColumn(int column)
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
         direction = ASCENDING;
      }
   }

   // TODO: Add supervisor, numSignalsAttached, and errorHandler when supported.
   public int compare(Viewer viewer, Object e1, Object e2)
   {
      if (!(e1 instanceof BlockInfo) && !(e2 instanceof BlockInfo))
      {
         return 0;
      }

      BlockInfo b1 = (BlockInfo) e1;
      BlockInfo b2 = (BlockInfo) e2;

      switch (column)
      {
         case BlockView.COLUMN_NAME:
            return compareStrings(b1.getName(), b2.getName());
         case BlockView.COLUMN_BID:
            return compareInts(b1.getId(), b2.getId());
         case BlockView.COLUMN_SID:
            return compareInts(b1.getSid(), b2.getSid());
         case BlockView.COLUMN_UID:
            return compareInts(b1.getUid(), b2.getUid());
         case BlockView.COLUMN_MAX_SIGNAL_SIZE:
            return compareInts(b1.getMaxSignalSize(), b2.getMaxSignalSize());
         case BlockView.COLUMN_SIGNAL_POOL_ID:
            return compareInts(b1.getSignalPoolId(), b2.getSignalPoolId());
         case BlockView.COLUMN_STACK_POOL_ID:
            return compareInts(b1.getStackPoolId(), b2.getStackPoolId());
         case BlockView.COLUMN_EXECUTION_UNIT:
            return compareShorts(b1.getExecutionUnit(), b2.getExecutionUnit());
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

   private int compareInts(int i1, int i2)
   {
      int result = (i1 < i2) ? -1 : ((i1 > i2) ? 1 : 0);
      if (direction == DESCENDING)
      {
         result *= DESCENDING;
      }
      return result;
   }

   private int compareShorts(short s1, short s2)
   {
      int result = (s1 < s2) ? -1 : ((s1 > s2) ? 1 : 0);
      if (direction == DESCENDING)
      {
         result *= DESCENDING;
      }
      return result;
   }
}
