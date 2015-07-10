/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2009 by Enea Software AB.
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

package com.ose.system.ui.views.heap;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import com.ose.system.ui.util.ExtendedHeapBufferInfo;

public class HeapBufferSorter extends ViewerSorter
{
   private static final int ASCENDING = 1;
   private static final int DESCENDING = -1;

   private int column;
   private int direction;

   HeapBufferSorter()
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
      if (!(e1 instanceof ExtendedHeapBufferInfo) &&
          !(e2 instanceof ExtendedHeapBufferInfo))
      {
         return 0;
      }

      ExtendedHeapBufferInfo buf1 = (ExtendedHeapBufferInfo) e1;
      ExtendedHeapBufferInfo buf2 = (ExtendedHeapBufferInfo) e2;

      switch (column)
      {
         case HeapView.COLUMN_BROKEN_BUFFER:
            return compareBooleans(buf1.isEndmarkBroken(),
                                   buf2.isEndmarkBroken());
         case HeapView.COLUMN_OWNER_ID:
            return compareUnsignedInts(buf1.getHeapBufferInfo().getOwner(),
                                       buf2.getHeapBufferInfo().getOwner());
         case HeapView.COLUMN_OWNER_NAME:
            return compareStrings(buf1.getOwnerName(), buf2.getOwnerName());
         case HeapView.COLUMN_SHARED:
            return compareBooleans(buf1.getHeapBufferInfo().isShared(),
                                   buf2.getHeapBufferInfo().isShared());
         case HeapView.COLUMN_REQUESTED_SIZE:
            return compareUnsignedInts(buf1.getHeapBufferInfo().getRequestedSize(),
                                       buf2.getHeapBufferInfo().getRequestedSize());
         case HeapView.COLUMN_ACTUAL_SIZE:
            return compareUnsignedInts(buf1.getHeapBufferInfo().getSize(),
                                       buf2.getHeapBufferInfo().getSize());
         case HeapView.COLUMN_ADDRESS:
            return compareUnsignedInts(buf1.getHeapBufferInfo().getAddress(),
                                       buf2.getHeapBufferInfo().getAddress());
         case HeapView.COLUMN_FILE_NAME:
            return compareStrings(buf1.getHeapBufferInfo().getFileName(),
                                  buf2.getHeapBufferInfo().getFileName());
         case HeapView.COLUMN_LINE_NUMBER:
            return compareUnsignedInts(buf1.getHeapBufferInfo().getLineNumber(),
                                       buf2.getHeapBufferInfo().getLineNumber());
         case HeapView.COLUMN_STATUS:
            return compareUnsignedInts(buf1.getHeapBufferInfo().getStatus(),
                                       buf2.getHeapBufferInfo().getStatus());
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

   private int compareBooleans(boolean b1, boolean b2)
   {
      int result = (!b1 && b2) ? -1 : ((b1 && !b2) ? 1 : 0);
      if (direction == DESCENDING)
      {
         result *= DESCENDING;
      }
      return result;
   }
}
