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

import java.util.Map;
import com.ose.system.Target;

public class UserProfilerEditorInput extends ProfilerEditorInput
{
   private final Map pidToNameMap;
   private final int maxValuesReport;
   private final int reportNumber;
   
   public UserProfilerEditorInput(Target target, 
                                  int integrationInterval,
                                  int maxReports,
                                  int maxValuesReport,
                                  int reportNumber,
                                  Map pidToNameMap,
                                  long timestamp)
   {
      super(target, integrationInterval, maxReports, timestamp);
      this.maxValuesReport = maxValuesReport;
      this.reportNumber = reportNumber;
      this.pidToNameMap = pidToNameMap;
   }

   public String getName()
   {
      if (reportNumber == HeapProfilerEditor.HEAP_USER_REPORT_NUMBER)
      {   
         return "Turnover Heap Usage / Process";
      }
      else if (reportNumber == AccumulatedHeapProcessProfilerEditor.ACCUMULATED_HEAP_PROCESS_USER_REPORT_NUMBER)
      {
         return "Accumulated Heap Usage / Process";
      }
      else if (reportNumber == AccumulatedHeapProfilerEditor.ACCUMULATED_HEAP_USER_REPORT_NUMBER)
      {
         return "Accumulated Heap Usage / Heap";
      }
      else
      {
         return "Custom Profiling Type " + reportNumber;
      }
   }

   public int getMaxValuesReport()
   {
      return this.maxValuesReport;
   }

   public int getReportNumber()
   {
      return this.reportNumber;
   }

   public Map getPidToNameMap()
   {
      return pidToNameMap;
   }
   
   public boolean equals(Object obj)
   {
      if (obj instanceof UserProfilerEditorInput)
      {
         UserProfilerEditorInput that = (UserProfilerEditorInput)obj;         
         
         return super.equals(that) 
            && getMaxValuesReport() == that.getMaxValuesReport()
            && getReportNumber() == that.getReportNumber();
      }
      return false;
   }
   
   public int hashCode()
   {
      int result = super.hashCode();
      
      result = 37*result + getMaxValuesReport();
      result = 37*result + getReportNumber();
      return result;
   }   
}
