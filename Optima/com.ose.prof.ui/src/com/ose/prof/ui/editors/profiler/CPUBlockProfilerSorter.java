/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2008 by Enea Software AB.
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

import com.ose.system.CPUBlockReport;
import com.ose.system.CPUBlockReport.CPUBlockLoad;

public class CPUBlockProfilerSorter extends ProfilerSorter
{
   public int compare(Viewer viewer, Object e1, Object e2)
   {
      CPUBlockReport report1;
      CPUBlockReport report2;
      
      if (!(e1 instanceof CPUBlockReport) || !(e2 instanceof CPUBlockReport))
      {
         return 0;
      }
      
      report1 = (CPUBlockReport)e1;
      report2 = (CPUBlockReport)e2;
      
      switch (getColumn())
      {
         case ProfilerEditor.COLUMN_TICK:
            return compareUnsignedIntsPair(report1.getTick(), report1.getNTick(),
                                           report2.getTick(), report2.getNTick());
         case ProfilerEditor.COLUMN_INTERVAL:
            return compareUnsignedInts(report1.getInterval(), 
                                       report2.getInterval());
         case CPUBlockProfilerEditor.COLUMN_OTHER:
            return compareUnsignedInts(report1.getSumOther(), 
                                       report2.getSumOther());
         default:
         {   
            // FIXME: Handle names when sorting.
            CPUBlockLoad[] blocks1 = report1.getSumBlocks();
            CPUBlockLoad[] blocks2 = report2.getSumBlocks();
            int i = (getColumn() - (CPUBlockProfilerEditor.COLUMN_OTHER + 1)) / 2;
            boolean columnIsID = ((getColumn() - (CPUBlockProfilerEditor.COLUMN_OTHER + 1)) % 2 == 0);

            if (i >= 0)
            {
               boolean in1 = i < blocks1.length;
               boolean in2 = i < blocks2.length;
               
               if (in1 && in2)
               {
                  if (columnIsID)
                  {
                     return compareUnsignedInts(blocks1[i].getId(),
                                                blocks2[i].getId());
                  }
                  else
                  {
                     return compareUnsignedInts(blocks1[i].getSum(),
                                                blocks2[i].getSum());
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
