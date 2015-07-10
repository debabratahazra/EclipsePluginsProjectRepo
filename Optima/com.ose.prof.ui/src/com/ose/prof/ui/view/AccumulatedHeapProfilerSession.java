/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2012 by Enea Software AB.
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

import java.io.IOException;
import com.ose.prof.ui.ProfilerPlugin;
import com.ose.system.Target;

class AccumulatedHeapProfilerSession extends UserProfilerSession
{
   private static final int ACCUMULATED_HEAP_USER_REPORT_NUMBER = 52;

   AccumulatedHeapProfilerSession(Target target,
                                  int integrationInterval,
                                  int maxReports,
                                  int maxValuesReport)
      throws IOException
   {
      super(target,
            ACCUMULATED_HEAP_USER_REPORT_NUMBER,
            integrationInterval,
            maxReports,
            maxValuesReport,
            false);
   }

   public String getProfilerName()
   {
      return "Accumulated Heap Usage / Heap";
   }

   String getProfilerEditorId()
   {
      return ProfilerPlugin.ACCUMULATED_HEAP_PROFILER_EDITOR_ID;
   }
}