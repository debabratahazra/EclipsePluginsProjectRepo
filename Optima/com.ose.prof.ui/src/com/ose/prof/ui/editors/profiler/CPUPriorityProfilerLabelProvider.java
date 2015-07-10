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

import com.ose.system.CPUPriorityReport;

public class CPUPriorityProfilerLabelProvider extends ProfilerLabelProvider
{
   private final int numExecutionUnits;
   private final short executionUnit;

   CPUPriorityProfilerLabelProvider(int numExecutionUnits,
                                    short executionUnit,
                                    boolean relative)
   {
      super(relative);
      this.numExecutionUnits = numExecutionUnits;
      this.executionUnit = executionUnit;
   }
   
   public String getColumnText(Object element, int columnIndex)
   {
      CPUPriorityReport report;
      
      if (!(element instanceof CPUPriorityReport))
      {
         return null;
      }      
      report = (CPUPriorityReport)element;
      
      switch(columnIndex)
      {      
         case ProfilerEditor.COLUMN_TICK:
            return toLongString(report.getTick()) 
                      + ":" + toLongString(report.getNTick());
         case ProfilerEditor.COLUMN_INTERVAL:
            return toLongString(report.getInterval());
         case CPUPriorityProfilerEditor.COLUMN_INTERRUPT:
            return toRelativeLongString(report.getSumInterrupt(),
                                        report.getInterval(),
                                        numExecutionUnits,
                                        executionUnit);
         case CPUPriorityProfilerEditor.COLUMN_BACKGROUND:
            return toRelativeLongString(report.getSumBackground(),
                                        report.getInterval(),
                                        numExecutionUnits,
                                        executionUnit);
         default:
         {
            int i = columnIndex - CPUPriorityProfilerEditor.COLUMN_PRIORITY_0;
   
            if (i >= 0 && i < report.getSumPrioritized().length)
            {
               return toRelativeLongString(report.getSumPrioritized()[i],
                                           report.getInterval(),
                                           numExecutionUnits,
                                           executionUnit);
            }
            return null;
         }
      }
   }
}
