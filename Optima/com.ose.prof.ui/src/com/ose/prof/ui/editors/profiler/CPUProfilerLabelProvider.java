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

import com.ose.system.CPUReport;

public class CPUProfilerLabelProvider extends ProfilerLabelProvider
{
   private final int numExecutionUnits;
   private final short executionUnit;

   CPUProfilerLabelProvider(int numExecutionUnits,
                            short executionUnit,
                            boolean relative)
   {
      super(relative);
      this.numExecutionUnits = numExecutionUnits;
      this.executionUnit = executionUnit;
   }
   
   public String getColumnText(Object element, int columnIndex)
   {
      CPUReport report;
      
      if (!(element instanceof CPUReport))
      {
         return null;
      }      
      report = (CPUReport)element;
      
      switch(columnIndex)
      {
         case ProfilerEditor.COLUMN_TICK:
            return toLongString(report.getTick()) 
                      + ":" + toLongString(report.getNTick());
         case ProfilerEditor.COLUMN_INTERVAL:
            return toLongString(report.getInterval());
         case CPUProfilerEditor.COLUMN_USAGE:
            return toRelativeLongString(report.getSum(),
                                        report.getInterval(),
                                        numExecutionUnits,
                                        executionUnit);
         default:
            return null;
      }
   }
}
