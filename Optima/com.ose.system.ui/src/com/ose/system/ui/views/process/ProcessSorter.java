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

package com.ose.system.ui.views.process;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import com.ose.system.ProcessInfo;
import com.ose.system.ui.util.StringUtils;

public class ProcessSorter extends ViewerSorter
{
   private static final int ASCENDING = 1;
   private static final int DESCENDING = -1;

   private int column;
   private int direction;

   ProcessSorter()
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

   // TODO: Add sid, creator, supervisor, supervisorStackSize, timeSlice,
   // vector, numSignalsAttached, and errorHandler when supported.
   public int compare(Viewer viewer, Object e1, Object e2)
   {
      if (!(e1 instanceof ProcessInfo) && !(e2 instanceof ProcessInfo))
      {
         return 0;
      }

      ProcessInfo p1 = (ProcessInfo) e1;
      ProcessInfo p2 = (ProcessInfo) e2;

      switch (column)
      {
         case ProcessView.COLUMN_NAME:
            return compareStrings(p1.getName(), p2.getName());
         case ProcessView.COLUMN_ID:
            return compareInts(p1.getId(), p2.getId());
         case ProcessView.COLUMN_BID:
            return compareInts(p1.getBid(), p2.getBid());
         case ProcessView.COLUMN_UID:
            return compareInts(p1.getUid(), p2.getUid());
         case ProcessView.COLUMN_TYPE:
            return compareStrings(
                  StringUtils.toProcessTypeString(p1.getType()),
                  StringUtils.toProcessTypeString(p2.getType()));
         case ProcessView.COLUMN_STATE:
            return compareStrings(
                  StringUtils.toProcessStateString(p1.getState()),
                  StringUtils.toProcessStateString(p2.getState()));
         case ProcessView.COLUMN_PRIORITY:
            return compareInts(p1.getPriority(), p2.getPriority());
         case ProcessView.COLUMN_NUM_SIGNALS_IN_QUEUE:
            return compareInts(p1.getNumSignalsInQueue(), p2.getNumSignalsInQueue());
         case ProcessView.COLUMN_SIGSELECT_COUNT:
            int[] p1Sigselect = p1.getSigselect();
            int[] p2Sigselect = p2.getSigselect();
            return compareInts(
                  (p1Sigselect.length > 0) ? p1Sigselect[0] : Integer.MIN_VALUE,
                  (p2Sigselect.length > 0) ? p2Sigselect[0] : Integer.MIN_VALUE);
         case ProcessView.COLUMN_ENTRYPOINT:
            return compareInts(p1.getEntrypoint(), p2.getEntrypoint());
         case ProcessView.COLUMN_SEMAPHORE_VALUE:
            return compareInts(p1.getSemaphoreValue(), p2.getSemaphoreValue());
         case ProcessView.COLUMN_NUM_SIGNALS_OWNED:
            return compareInts(p1.getNumSignalsOwned(), p2.getNumSignalsOwned());
         case ProcessView.COLUMN_FILE_NAME:
            return compareStrings(p1.getFileName(), p2.getFileName());
         case ProcessView.COLUMN_LINE_NUMBER:
            return compareInts(p1.getLineNumber(), p2.getLineNumber());
         case ProcessView.COLUMN_STACK_SIZE:
            return compareInts(p1.getStackSize(), p2.getStackSize());
         case ProcessView.COLUMN_EXECUTION_UNIT:
            return compareShorts(p1.getExecutionUnit(), p2.getExecutionUnit());
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
