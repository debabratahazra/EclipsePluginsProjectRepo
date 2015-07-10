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

import com.ose.system.CPUPriorityReport;

class CPUPriorityProfilerSorter extends ProfilerSorter
{
   public int compare(Viewer viewer, Object e1, Object e2)
   {
      CPUPriorityReport report1;
      CPUPriorityReport report2;
      
      if (!(e1 instanceof CPUPriorityReport) || !(e2 instanceof CPUPriorityReport))
      {
         return 0;
      }
      
      report1 = (CPUPriorityReport)e1;
      report2 = (CPUPriorityReport)e2;
      
      switch (getColumn())
      {
         case ProfilerEditor.COLUMN_TICK:
            return compareUnsignedIntsPair(report1.getTick(), report1.getNTick(),
                                           report2.getTick(), report2.getNTick());
         case ProfilerEditor.COLUMN_INTERVAL:
            return compareUnsignedInts(report1.getInterval(), 
                                       report2.getInterval());
         case CPUPriorityProfilerEditor.COLUMN_INTERRUPT:
            return compareUnsignedInts(report1.getSumInterrupt(), 
                                       report2.getSumInterrupt());
         case CPUPriorityProfilerEditor.COLUMN_BACKGROUND:
            return compareUnsignedInts(report1.getSumBackground(), 
                                       report2.getSumBackground());

         default:
         {   
            int i = getColumn() - CPUPriorityProfilerEditor.COLUMN_PRIORITY_0;
         
            if (i >= 0) 
            {
               boolean in1 = i < report1.getSumPrioritized().length;
               boolean in2 = i < report2.getSumPrioritized().length;
               
               if (in1 && in2)
               {
                  return compareUnsignedInts(report1.getSumPrioritized()[i], 
                                             report2.getSumPrioritized()[i]);                  
               }
               else if (in1)
               {
                  return 1 * getDirection();
               }
               else if (in2)
               {
                  return -1 * getDirection();
               }
               else
               {
                  return 0;
               }
            }
            return 0;
         }  
      }
   }
}
