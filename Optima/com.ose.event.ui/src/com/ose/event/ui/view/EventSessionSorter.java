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

package com.ose.event.ui.view;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import com.ose.system.ui.util.StringUtils;

class EventSessionSorter extends ViewerSorter
{
   private static final int ASCENDING = 1;
   private static final int DESCENDING = -1;

   private int column;
   private int direction;

   EventSessionSorter()
   {
      super();
      reset();
   }

   public void reset()
   {
      column = EventView.COLUMN_EVENT_SESSION_TIMESTAMP;
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
      if (!(e1 instanceof EventSession) && !(e2 instanceof EventSession))
      {
         return 0;
      }

      EventSession eventSession1 = (EventSession) e1;
      EventSession eventSession2 = (EventSession) e2;

      switch (column)
      {
         case EventView.COLUMN_EVENT_SESSION_TARGET:
            return compareStrings(eventSession1.getTarget().toString(),
                                  eventSession2.getTarget().toString());
         case EventView.COLUMN_EVENT_SESSION_SCOPE:
            return compareStrings(
                  StringUtils.toScopeString(eventSession1.getScopeType(),
                                            eventSession1.getScopeId()),
                  StringUtils.toScopeString(eventSession2.getScopeType(),
                                            eventSession2.getScopeId()));
         case EventView.COLUMN_EVENT_SESSION_TIMESTAMP:
            return compareLongs(eventSession1.getTimestamp(),
                                eventSession2.getTimestamp());
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
}
