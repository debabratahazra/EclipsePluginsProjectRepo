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

package com.ose.perf.ui.view;

import java.text.DateFormat;
import java.util.Date;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import com.ose.system.Target;
import com.ose.system.ui.SharedImages;

class ProfilerSessionLabelProvider extends LabelProvider
   implements ITableLabelProvider
{
   private static final DateFormat DATE_FORMAT = DateFormat.getDateTimeInstance();

   public String getColumnText(Object obj, int index)
   {
      if (!(obj instanceof ProfilerSession))
      {
         return null;
      }

      ProfilerSession profilerSession = (ProfilerSession) obj;
      switch (index)
      {
         case ProfilerView.COLUMN_PROFILER_SESSION_TARGET:
            return profilerSession.getTarget().toString();
         case ProfilerView.COLUMN_PROFILER_SESSION_EXECUTION_UNIT:
            short executionUnit = profilerSession.getExecutionUnit();
            return (executionUnit == Target.ALL_EXECUTION_UNITS) ? "All"
                  : Short.toString(executionUnit);
         case ProfilerView.COLUMN_PROFILER_SESSION_PERFORMANCE_COUNTER:
            return profilerSession.getPerformanceCounter().getName();
         case ProfilerView.COLUMN_PROFILER_SESSION_PERFORMANCE_COUNTER_VALUE:
            return toUnsignedLongString(profilerSession.getPerformanceCounterValue());
         case ProfilerView.COLUMN_PROFILER_SESSION_TIMESTAMP:
            return DATE_FORMAT.format(new Date(profilerSession.getTimestamp()));
         case ProfilerView.COLUMN_PROFILER_SESSION_REPORTS:
            return toLongString(profilerSession.getNumReports());
         case ProfilerView.COLUMN_PROFILER_SESSION_LOST_REPORTS:
            return toLongString(profilerSession.getNumLostReports());
         default:
            return null;
      }
   }

   public Image getColumnImage(Object obj, int index)
   {
      if ((index == ProfilerView.COLUMN_PROFILER_SESSION_TARGET) &&
          (obj instanceof ProfilerSession))
      {
         ProfilerSession profilerSession = (ProfilerSession) obj;
         return SharedImages.get(profilerSession.isOpen() ?
            SharedImages.IMG_OBJ_TARGET : SharedImages.IMG_OBJ_TARGET_KILLED);
      }
      else
      {
         return null;
      }
   }

   private static String toLongString(int i)
   {
      return Long.toString(i & 0xFFFFFFFFL);
   }

   private static String toUnsignedLongString(long l)
   {
      return (l < 0) ? "0x" + Long.toHexString(l).toUpperCase() : Long.toString(l);
   }
}
