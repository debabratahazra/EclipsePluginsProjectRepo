/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2007 by Enea Software AB.
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

package com.ose.prof.ui.view;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

class ProfilerSessionSorter extends ViewerSorter
{
   private static final int ASCENDING = 1;
   private static final int DESCENDING = -1;

   private int column;
   private int direction;

   ProfilerSessionSorter()
   {
      super();
      reset();
   }

   public void reset()
   {
      column = ProfilerView.COLUMN_PROFILER_SESSION_TIMESTAMP;
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
      if (!(e1 instanceof ProfilerSession) && !(e2 instanceof ProfilerSession))
      {
         return 0;
      }

      ProfilerSession profilerSession1 = (ProfilerSession) e1;
      ProfilerSession profilerSession2 = (ProfilerSession) e2;

      switch (column)
      {
         case ProfilerView.COLUMN_PROFILER_SESSION_TARGET:
            return compareStrings(profilerSession1.getTarget().toString(),
                                  profilerSession2.getTarget().toString());
         case ProfilerView.COLUMN_PROFILER_SESSION_TYPE:
            return compareStrings(profilerSession1.getProfilerName(),
                                  profilerSession2.getProfilerName());
         case ProfilerView.COLUMN_PROFILER_SESSION_EXECUTION_UNIT:
            return compareShorts(profilerSession1.getExecutionUnit(),
                                 profilerSession2.getExecutionUnit());
         case ProfilerView.COLUMN_PROFILER_SESSION_TIMESTAMP:
            return compareLongs(profilerSession1.getTimestamp(),
                                profilerSession2.getTimestamp());
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

   private int compareLongs(long l1, long l2)
   {
      int result = (l1 < l2) ? -1 : ((l1 > l2) ? 1 : 0);
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
