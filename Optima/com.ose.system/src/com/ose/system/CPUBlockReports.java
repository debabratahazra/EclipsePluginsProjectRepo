/* COPYRIGHT-ENEA-SRC-R1 *
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

package com.ose.system;

import com.ose.system.service.monitor.MonitorCPUBlockReport;

/**
 * This class contains information about OSE CPU block level load reports.
 * A CPUBlockReports object is immutable and its content is a snapshot at
 * the time of the retrieving call.
 *
 * @see com.ose.system.Target#getCPUBlockReports(IProgressMonitor, short, int, int)
 */
public class CPUBlockReports extends TargetReports
{
   private final short executionUnit;

   /**
    * Create a new CPU block level load reports object.
    *
    * @param target   the target.
    * @param enabled  the enablement status.
    * @param euId     the execution unit from where the CPU block level load
    * reports were retrieved from.
    * @param reports  the internal CPU block level load report objects.
    */
   CPUBlockReports(Target target, boolean enabled, short euId, MonitorCPUBlockReport[] reports)
   {
      super(target, enabled);

      if (reports == null)
      {
         throw new NullPointerException();
      }
      this.executionUnit = euId;
      this.reports = new CPUBlockReport[reports.length];
      for (int i = 0; i < reports.length; i++)
      {
         this.reports[i] = new CPUBlockReport(reports[i]);
      }
   }

   /**
    * Return the execution unit from where the CPU block level load reports were
    * retrieved from or Target.ALL_EXECUTION_UNITS.
    *
    * @return the execution unit from where the CPU block level load reports
    * were retrieved from or Target.ALL_EXECUTION_UNITS.
    * @see com.ose.system.Target#ALL_EXECUTION_UNITS
    */
   public short getExecutionUnit()
   {
      return executionUnit;
   }
}
