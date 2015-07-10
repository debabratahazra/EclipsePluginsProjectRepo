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

import com.ose.system.CPUProcessReport;
import com.ose.system.CPUProcessReport.CPUProcessLoad;

class CPUProcessProfilerSorter extends ProfilerSorter
{
   public int compare(Viewer viewer, Object e1, Object e2)
   {
      CPUProcessReport report1;
      CPUProcessReport report2;
      
      if (!(e1 instanceof CPUProcessReport) || !(e2 instanceof CPUProcessReport))
      {
         return 0;
      }
      
      report1 = (CPUProcessReport)e1;
      report2 = (CPUProcessReport)e2;
      
      switch (getColumn())
      {
         case ProfilerEditor.COLUMN_TICK:
            return compareUnsignedIntsPair(report1.getTick(), report1.getNTick(),
                                           report2.getTick(), report2.getNTick());
         case ProfilerEditor.COLUMN_INTERVAL:
            return compareUnsignedInts(report1.getInterval(), 
                                       report2.getInterval());
         case CPUProcessProfilerEditor.COLUMN_OTHER:
            return compareUnsignedInts(report1.getSumOther(), 
                                       report2.getSumOther());
         default:
         {   
            // FIXME: Handle names when sorting.
            CPUProcessLoad[] processes1 = report1.getSumProcesses();
            CPUProcessLoad[] processes2 = report2.getSumProcesses();
            int i = (getColumn() - (CPUProcessProfilerEditor.COLUMN_OTHER + 1)) / 2;
            boolean columnIsID = ((getColumn() - (CPUProcessProfilerEditor.COLUMN_OTHER + 1)) % 2 == 0);

            if (i >= 0)
            {
               boolean in1 = i < processes1.length;
               boolean in2 = i < processes2.length;
               
               if (in1 && in2)
               {
                  if (columnIsID)
                  {
                     return compareUnsignedInts(processes1[i].getId(),
                                                processes2[i].getId());
                  }
                  else
                  {
                     return compareUnsignedInts(processes1[i].getSum(),
                                                processes2[i].getSum());
                  }
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
