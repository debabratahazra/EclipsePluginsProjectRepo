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

import com.ose.system.Target;

public class CPUPriorityProfilerEditorInput extends ProfilerEditorInput
{
   private final short executionUnit;

   public CPUPriorityProfilerEditorInput(Target target, 
                                         short executionUnit,
                                         int integrationInterval,
                                         int maxReports,
                                         long timestamp)
   {
      super(target, integrationInterval, maxReports, timestamp);
      this.executionUnit = executionUnit;
   }

   public String getName()
   {
      return "CPU Usage / Priority";
   }

   public int getNumExecutionUnits()
   {
      return getTarget().getNumExecutionUnits() & 0xFFFF;
   }

   public short getExecutionUnit()
   {
      return executionUnit;
   }

   public boolean equals(Object obj)
   {
      if (obj instanceof CPUPriorityProfilerEditorInput)
      {
         CPUPriorityProfilerEditorInput that = (CPUPriorityProfilerEditorInput) obj;
         return super.equals(obj) && (getExecutionUnit() == that.getExecutionUnit());
      }
      return false;
   }

   public int hashCode()
   {
      int result = super.hashCode();

      result = 37 * result + getExecutionUnit();
      return result;
   }
}
