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

import com.ose.system.UserReport;
import com.ose.system.UserReport.MaxMinUserReportValue;
import com.ose.system.UserReport.UserReportValue;


public class UserProfilerLabelProvider extends ProfilerLabelProvider
{
   private final IdNameMap idNameMap;
   private boolean maxMin;

   UserProfilerLabelProvider(IdNameMap idNameMap)
   {
      super(false);
      if (idNameMap == null)
      {
         throw new IllegalArgumentException();
      }
      this.idNameMap = idNameMap;
   }

   void setRelative(boolean relative)
   {
      throw new UnsupportedOperationException(); 
   }
   
   void setMaxMin(boolean maxMin)
   {
      this.maxMin = maxMin;
   }
   
   public String getColumnText(Object element, int columnIndex)
   {
      UserReport report;
      
      if (!(element instanceof UserReport))
      {
         return null;
      }      
      report = (UserReport)element;
      
      switch(columnIndex)
      {      
         case ProfilerEditor.COLUMN_TICK:
            return toLongString(report.getTick()) 
                      + ":" + toLongString(report.getNTick());
         case ProfilerEditor.COLUMN_INTERVAL:
            return toLongString(report.getInterval());
         case UserProfilerEditor.COLUMN_OTHER:
         {
            String s = toString(report.getSumOther());
            
            if (maxMin)
            {
               s += "[" + toString(report.getMinOther()) 
                     + ","  + toString(report.getMaxOther()) + "]";
            }
            return s;
         }
         default:
         {
            UserReportValue[] values = report.getValues();
            int i = (columnIndex - (UserProfilerEditor.COLUMN_OTHER + 1)) / 2;
            boolean columnIsID = ((columnIndex - (UserProfilerEditor.COLUMN_OTHER + 1)) % 2 == 0);

            if (i >= 0 && i < values.length)
            {
               if (columnIsID)
               {
                  return idNameMap.getName(values[i].getId());
               }
               else
               {
                  String s = toString(values[i].getSum());

                  if (maxMin)
                  {
                     MaxMinUserReportValue value = (MaxMinUserReportValue)values[i];

                     s += "[" + toString(value.getMin())
                           + "," + toString(value.getMax()) + "]";
                  }
                  return s;
               }
            }
            return null;
         }
      }
   }
   
   private String toString(int i)
   {
      return Integer.toString(i);
   }
}
