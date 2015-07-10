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
import com.ose.system.ui.util.ExtendedSignalInfo;

public class SignalSorter extends ViewerSorter
{
   private static final int ASCENDING = 1;
   private static final int DESCENDING = -1;

   private int column;
   private int direction;

   SignalSorter()
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
      if (!(e1 instanceof ExtendedSignalInfo) &&
          !(e2 instanceof ExtendedSignalInfo))
      {
         return 0;
      }

      ExtendedSignalInfo sig1 = (ExtendedSignalInfo) e1;
      ExtendedSignalInfo sig2 = (ExtendedSignalInfo) e2;

      switch (column)
      {
         case PoolView.COLUMN_SIGNAL_BROKEN_BUFFER:
            return compareBooleans(sig1.isEndmarkBroken(),
                                   sig2.isEndmarkBroken());
         case PoolView.COLUMN_SIGNAL_NUMBER:
            return compareUnsignedInts(sig1.getSignalInfo().getSigNo(),
                                       sig2.getSignalInfo().getSigNo());
         case PoolView.COLUMN_SIGNAL_OWNER_ID:
            return compareUnsignedInts(sig1.getSignalInfo().getOwner(),
                                       sig2.getSignalInfo().getOwner());
         case PoolView.COLUMN_SIGNAL_OWNER_NAME:
            return compareStrings(sig1.getOwnerName(), sig2.getOwnerName());
         case PoolView.COLUMN_SIGNAL_SENDER_ID:
            return compareUnsignedInts(sig1.getSignalInfo().getSender(),
                                       sig2.getSignalInfo().getSender());
         case PoolView.COLUMN_SIGNAL_SENDER_NAME:
            return compareStrings(sig1.getSenderName(), sig2.getSenderName());
         case PoolView.COLUMN_SIGNAL_ADDRESSEE_ID:
            return compareUnsignedInts(sig1.getSignalInfo().getAddressee(),
                                       sig2.getSignalInfo().getAddressee());
         case PoolView.COLUMN_SIGNAL_ADDRESSEE_NAME:
            return compareStrings(sig1.getAddresseeName(),
                                  sig2.getAddresseeName());
         case PoolView.COLUMN_SIGNAL_SIZE:
            return compareUnsignedInts(sig1.getSignalInfo().getSize(),
                                       sig2.getSignalInfo().getSize());
         case PoolView.COLUMN_SIGNAL_BUFFER_SIZE:
            return compareUnsignedInts(sig1.getSignalInfo().getBufferSize(),
                                       sig2.getSignalInfo().getBufferSize());
         case PoolView.COLUMN_SIGNAL_ADDRESS:
            return compareUnsignedInts(sig1.getSignalInfo().getAddress(),
                                       sig2.getSignalInfo().getAddress());
         case PoolView.COLUMN_SIGNAL_STATUS:
            return compareUnsignedInts(sig1.getSignalInfo().getStatus(),
                                       sig2.getSignalInfo().getStatus());
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
