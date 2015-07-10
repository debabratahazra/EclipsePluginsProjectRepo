/* COPYRIGHT-ENEA-SRC-R2 *
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

package com.ose.prof.ui.editors.profiler;

import org.eclipse.jface.viewers.Viewer;

import com.ose.system.CPUReport;

class CPUProfilerSorter extends ProfilerSorter
{   
   public int compare(Viewer viewer, Object e1, Object e2)
   {
      CPUReport report1;
      CPUReport report2;
      
      if (!(e1 instanceof CPUReport) || !(e2 instanceof CPUReport))
      {
         return 0;
      }
      
      report1 = (CPUReport)e1;
      report2 = (CPUReport)e2;
      
      switch (getColumn())
      {
         case ProfilerEditor.COLUMN_TICK:
            return compareUnsignedIntsPair(report1.getTick(), report1.getNTick(),
                                           report2.getTick(), report2.getNTick());
         case ProfilerEditor.COLUMN_INTERVAL:
            return compareUnsignedInts(report1.getInterval(), 
                                       report2.getInterval());
         case CPUProfilerEditor.COLUMN_USAGE:
            return compareUnsignedInts(report1.getSum(), report2.getSum());
         default:
            return 0;
      }
   }
}
