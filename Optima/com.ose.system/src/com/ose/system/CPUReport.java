/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2006 by Enea Software AB.
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

import com.ose.system.service.monitor.MonitorCPUReport;

/**
 * This class represents an OSE CPU load report. A CPUReport object is
 * immutable and its content is a snapshot at the time of the retrieving
 * Target.getCPUReports() call.
 *
 * @see com.ose.system.CPUReports#getReports()
 */
public class CPUReport extends TargetReport
{
   private final int sum;

   /**
    * Create a new CPU load report object.
    *
    * @param report  the internal CPU load report object.
    */
   CPUReport(MonitorCPUReport report)
   {
      super(report.tick, report.ntick, report.interval);
      this.sum = report.sum;
   }

   /**
    * Create a new CPU load report object.
    *
    * @param tick      the tick.
    * @param ntick     the micro tick.
    * @param interval  the measurement interval.
    * @param sum       the sum.
    */
   CPUReport(int tick, int ntick, int interval, int sum)
   {
      super(tick, ntick, interval);
      this.sum = sum;
   }

   /**
    * Create a new CPU load report object for off-line usage.
    *
    * @param tick      the tick.
    * @param ntick     the micro tick.
    * @param interval  the measurement interval.
    * @param sum       the sum.
    * @return a new CPU load report object for off-line usage.
    */
   public static CPUReport getInstance(int tick, int ntick, int interval, int sum)
   {
      return new CPUReport(tick, ntick, interval, sum);
   }

   /**
    * Return the time the CPU was occupied (unspecified unit).
    *
    * @return the time the CPU was occupied.
    */
   public int getSum()
   {
      return sum;
   }
}
