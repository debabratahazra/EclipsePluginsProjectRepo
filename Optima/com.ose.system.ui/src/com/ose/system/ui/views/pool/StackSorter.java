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

package com.ose.system.ui.views.pool;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import com.ose.system.ui.util.ExtendedStackInfo;

public class StackSorter extends ViewerSorter
{
   private static final int ASCENDING = 1;
   private static final int DESCENDING = -1;

   private int column;
   private int direction;

   StackSorter()
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

   public int compare(Viewer viewer, Object e1, Object e2)
   {
      if (!(e1 instanceof ExtendedStackInfo) &&
          !(e2 instanceof ExtendedStackInfo))
      {
         return 0;
      }

      ExtendedStackInfo stack1 = (ExtendedStackInfo) e1;
      ExtendedStackInfo stack2 = (ExtendedStackInfo) e2;

      switch (column)
      {
         case PoolView.COLUMN_STACK_OWNER_ID:
            return compareUnsignedInts(stack1.getStackInfo().getPid(),
                                       stack2.getStackInfo().getPid());
         case PoolView.COLUMN_STACK_OWNER_NAME:
            return compareStrings(stack1.getOwnerName(), stack2.getOwnerName());
         case PoolView.COLUMN_STACK_ADDRESS:
            return compareUnsignedInts(stack1.getStackInfo().getAddress(),
                                       stack2.getStackInfo().getAddress());
         case PoolView.COLUMN_STACK_SIZE:
            return compareUnsignedInts(stack1.getStackInfo().getSize(),
                                       stack2.getStackInfo().getSize());
         case PoolView.COLUMN_STACK_BUFFER_SIZE:
            return compareUnsignedInts(stack1.getStackInfo().getBufferSize(),
                                       stack2.getStackInfo().getBufferSize());
         case PoolView.COLUMN_STACK_USED:
            return compareUnsignedInts(stack1.getStackInfo().getUsed(),
                                       stack2.getStackInfo().getUsed());
         case PoolView.COLUMN_STACK_RELATIVE_USED:
            return compareDoubles((double) stack1.getStackInfo().getUsed() /
                                  (double) stack1.getStackInfo().getSize(),
                                  (double) stack2.getStackInfo().getUsed() /
                                  (double) stack2.getStackInfo().getSize());
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

   private int compareUnsignedInts(int i1, int i2)
   {
      int result;
      long l1 = 0xFFFFFFFFL & i1;
      long l2 = 0xFFFFFFFFL & i2;

      result = (l1 < l2) ? -1 : ((l1 > l2) ? 1 : 0);
      if (direction == DESCENDING)
      {
         result *= DESCENDING;
      }
      return result;
   }

   private int compareDoubles(double d1, double d2)
   {
      int result = (d1 < d2) ? -1 : ((d1 > d2) ? 1 : 0);
      if (direction == DESCENDING)
      {
         result *= DESCENDING;
      }
      return result;
   }
}
