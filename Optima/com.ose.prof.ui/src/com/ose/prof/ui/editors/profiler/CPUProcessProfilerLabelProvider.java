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

import com.ose.system.CPUProcessReport;
import com.ose.system.CPUProcessReport.CPUProcessLoad;

public class CPUProcessProfilerLabelProvider extends ProfilerLabelProvider
{
   private final int numExecutionUnits;
   private final short executionUnit;
   private final IdNameMap idNameMap;

   CPUProcessProfilerLabelProvider(int numExecutionUnits,
                                   short executionUnit,
                                   boolean relative,
                                   IdNameMap idNameMap)
   {
      super(relative);
      this.numExecutionUnits = numExecutionUnits;
      this.executionUnit = executionUnit;
      if (idNameMap == null)
      {
         throw new IllegalArgumentException();
      }
      this.idNameMap = idNameMap;
   }
   
   public String getColumnText(Object element, int columnIndex)
   {
      CPUProcessReport report;
      
      if (!(element instanceof CPUProcessReport))
      {
         return null;
      }      
      report = (CPUProcessReport)element;
      
      switch(columnIndex)
      {      
         case ProfilerEditor.COLUMN_TICK:
            return toLongString(report.getTick()) 
                      + ":" + toLongString(report.getNTick());
         case ProfilerEditor.COLUMN_INTERVAL:
            return toLongString(report.getInterval());
         case CPUProcessProfilerEditor.COLUMN_OTHER:
            return toRelativeLongString(report.getSumOther(),
                                        report.getInterval(),
                                        numExecutionUnits,
                                        executionUnit);
         default:
         {
            CPUProcessLoad[] processes = report.getSumProcesses();
            int i = (columnIndex - (CPUProcessProfilerEditor.COLUMN_OTHER + 1)) / 2;
            boolean columnIsID = ((columnIndex - (CPUProcessProfilerEditor.COLUMN_OTHER + 1)) % 2 == 0);

            if (i >= 0 && i < processes.length)
            {
               if (columnIsID)
               {
                  return idNameMap.getName(processes[i].getId());
               }
               else
               {
                  return toRelativeLongString(processes[i].getSum(),
                                              report.getInterval(),
                                              numExecutionUnits,
                                              executionUnit);
               }
            }
            return null;
         }
      }
   }
}
