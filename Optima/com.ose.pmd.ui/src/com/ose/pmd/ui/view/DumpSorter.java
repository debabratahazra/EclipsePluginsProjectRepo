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

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import com.ose.system.DumpInfo;

public class DumpSorter extends ViewerSorter
{
   private static final int ASCENDING = 1;
   private static final int DESCENDING = -1;

   private int column;
   private int direction;

   DumpSorter()
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
      if (!(e1 instanceof DumpInfo) && !(e2 instanceof DumpInfo))
      {
         return 0;
      }

      DumpInfo dump1 = (DumpInfo) e1;
      DumpInfo dump2 = (DumpInfo) e2;

      switch (column)
      {
         case DumpView.COLUMN_DUMP_ID:
            return compareInts(dump1.getId(), dump2.getId());
         case DumpView.COLUMN_DUMP_SIZE:
            return compareLongs(dump1.getSize(), dump2.getSize());
         case DumpView.COLUMN_DUMP_TIMESTAMP:
            return compareLongsPair(dump1.getSeconds(), dump1.getMicroSeconds(),
                                    dump2.getSeconds(), dump2.getMicroSeconds());
         default:
            return 0;
      }
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

   private int compareLongs(long l1, long l2)
   {
      int result = (l1 < l2) ? -1 : ((l1 > l2) ? 1 : 0);
      if (direction == DESCENDING)
      {
         result *= DESCENDING;
      }
      return result;
   }

   private int compareLongsPair(long l11, long l12, long l21, long l22)
   {
      int result;

      if (l11 < l21)
      {
         result = -1;
      }
      else if (l11 > l21)
      {
         result = 1;
      }
      else
      {
         result = (l12 < l22) ? -1 : ((l12 > l22) ? 1 : 0);
      }

      if (direction == DESCENDING)
      {
         result *= DESCENDING;
      }
      return result;
   }
}
