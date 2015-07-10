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

import java.util.Map;
import com.ose.system.Target;

public class CPUBlockProfilerEditorInput extends ProfilerEditorInput
{
   private final short executionUnit;
   private final Map bidToNameMap;
   private final int maxValuesReport;

   public CPUBlockProfilerEditorInput(Target target,
                                      short executionUnit,
                                      int integrationInterval,
                                      int maxReports,
                                      int maxValuesReport,
                                      Map bidToNameMap,
                                      long timestamp)
   {
      super(target, integrationInterval, maxReports, timestamp);
      this.executionUnit = executionUnit;
      this.maxValuesReport = maxValuesReport;
      this.bidToNameMap = bidToNameMap;
   }

   public String getName()
   {
      return "CPU Usage / Block";
   }

   public int getNumExecutionUnits()
   {
      return getTarget().getNumExecutionUnits() & 0xFFFF;
   }

   public short getExecutionUnit()
   {
      return executionUnit;
   }

   public int getMaxValuesReport()
   {
      return maxValuesReport;
   }
   
   public Map getBidToNameMap()
   {
      return bidToNameMap;
   }
   
   public boolean equals(Object obj)
   {
      if (obj instanceof CPUBlockProfilerEditorInput)
      {
         CPUBlockProfilerEditorInput that = (CPUBlockProfilerEditorInput)obj;         
         
         return super.equals(that) 
            && (getExecutionUnit() == that.getExecutionUnit())
            && (getMaxValuesReport() == that.getMaxValuesReport());
      }
      return false;
   }
   
   public int hashCode()
   {
      int result = super.hashCode();
      
      result = 37 * result + getExecutionUnit() + getMaxValuesReport();
      return result;
   }
}
