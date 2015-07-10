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

import com.ose.system.CPUBlockReport;
import com.ose.system.CPUBlockReport.CPUBlockLoad;

public class CPUBlockProfilerLabelProvider extends ProfilerLabelProvider
{
   private final int numExecutionUnits;
   private final short executionUnit;
   private final IdNameMap idNameMap;

   CPUBlockProfilerLabelProvider(int numExecutionUnits,
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
      CPUBlockReport report;
      
      if (!(element instanceof CPUBlockReport))
      {
         return null;
      }      
      report = (CPUBlockReport)element;
      
      switch(columnIndex)
      {      
         case ProfilerEditor.COLUMN_TICK:
            return toLongString(report.getTick()) 
                      + ":" + toLongString(report.getNTick());
         case ProfilerEditor.COLUMN_INTERVAL:
            return toLongString(report.getInterval());
         case CPUBlockProfilerEditor.COLUMN_OTHER:
            return toRelativeLongString(report.getSumOther(),
                                        report.getInterval(),
                                        numExecutionUnits,
                                        executionUnit);
         default:
         {
            CPUBlockLoad[] blocks = report.getSumBlocks();
            int i = (columnIndex - (CPUBlockProfilerEditor.COLUMN_OTHER + 1)) / 2;
            boolean columnIsID = ((columnIndex - (CPUBlockProfilerEditor.COLUMN_OTHER + 1)) % 2 == 0);

            if (i >= 0 && i < blocks.length)
            {
               if (columnIsID)
               {
                  return idNameMap.getName(blocks[i].getId());
               }
               else
               {
                  return toRelativeLongString(blocks[i].getSum(),
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
