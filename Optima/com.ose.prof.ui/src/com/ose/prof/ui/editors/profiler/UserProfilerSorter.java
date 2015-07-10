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

import com.ose.system.UserReport;
import com.ose.system.UserReport.UserReportValue;

class UserProfilerSorter extends ProfilerSorter
{
   public int compare(Viewer viewer, Object e1, Object e2)
   {
      UserReport report1;
      UserReport report2;
      
      if (!(e1 instanceof UserReport) || !(e2 instanceof UserReport))
      {
         return 0;
      }
      
      report1 = (UserReport)e1;
      report2 = (UserReport)e2;
      
      switch (getColumn())
      {
         case ProfilerEditor.COLUMN_TICK:
            return compareUnsignedIntsPair(report1.getTick(), report1.getNTick(),
                                           report2.getTick(), report2.getNTick());
         case ProfilerEditor.COLUMN_INTERVAL:
            return compareUnsignedInts(report1.getInterval(), 
                                       report2.getInterval());
         case UserProfilerEditor.COLUMN_OTHER:
            return getDirection() * (report1.getSumOther() - report2.getSumOther());
         default:
         {   
            UserReportValue[] values1 = report1.getValues();
            UserReportValue[] values2 = report2.getValues();
            int i = (getColumn() - (CPUProcessProfilerEditor.COLUMN_OTHER + 1)) / 2;
            boolean columnIsID = ((getColumn() - (CPUProcessProfilerEditor.COLUMN_OTHER + 1)) % 2 == 0);

            if (i >= 0)
            {
               // FIXME: Handle names when sorting.
               boolean in1 = i < values1.length;
               boolean in2 = i < values2.length;
               
               if (in1 && in2)
               {
                  if (columnIsID)
                  {
                     return compareUnsignedInts(values1[i].getId(), 
                                                values2[i].getId());
                  }
                  else
                  {
                     return getDirection() 
                        * (values1[i].getSum() - values2[i].getSum());
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
