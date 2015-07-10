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

import com.ose.system.service.monitor.MonitorCPUPriReport;

/**
 * This class represents an OSE CPU priority level load report.
 * A CPUPriorityReport object is immutable and its content is a snapshot
 * at the time of the retrieving Target.getCPUPriorityReports() call.
 *
 * @see com.ose.system.CPUPriorityReports#getReports()
 */
public class CPUPriorityReport extends TargetReport
{
   private final int sumInterrupt;
   private final int sumBackground;
   private final int[] sumPrioritized;

   /**
    * Create a new CPU priority level load report object.
    *
    * @param report  the internal CPU priority level load report object.
    */
   CPUPriorityReport(MonitorCPUPriReport report)
   {
      super(report.tick, report.ntick, report.interval);
      this.sumInterrupt = report.sumInterrupt;
      this.sumBackground = report.sumBackground;
      this.sumPrioritized = report.sumPrioritized;
   }

   /**
    * Create a new CPU priority level load report object.
    *
    * @param tick            the tick.
    * @param ntick           the micro tick.
    * @param interval        the measurement interval.
    * @param sumInterrupt    the interrupt sum.
    * @param sumBackground   the background sum.
    * @param sumPrioritized  the prioritized sum.
    */
   CPUPriorityReport(int tick,
                     int ntick,
                     int interval,
                     int sumInterrupt,
                     int sumBackground,
                     int[] sumPrioritized)
   {
      super(tick, ntick, interval);
      this.sumInterrupt = sumInterrupt;
      this.sumBackground = sumBackground;
      this.sumPrioritized = sumPrioritized;
   }

   /**
    * Create a new CPU priority level load report object for off-line usage.
    *
    * @param tick            the tick.
    * @param ntick           the micro tick.
    * @param interval        the measurement interval.
    * @param sumInterrupt    the interrupt sum.
    * @param sumBackground   the background sum.
    * @param sumPrioritized  the prioritized sum.
    * @return a new CPU priority level load report object for off-line usage.
    */
   public static CPUPriorityReport getInstance(int tick,
                                               int ntick,
                                               int interval,
                                               int sumInterrupt,
                                               int sumBackground,
                                               int[] sumPrioritized)
   {
      return new CPUPriorityReport(tick,
                                   ntick,
                                   interval,
                                   sumInterrupt,
                                   sumBackground,
                                   sumPrioritized);
   }

   /**
    * Return the time the CPU was occupied with interrupt processes
    * (unspecified unit).
    *
    * @return the time the CPU was occupied with interrupt processes.
    */
   public int getSumInterrupt()
   {
      return sumInterrupt;
   }

   /**
    * Return the time the CPU was occupied with background processes
    * (unspecified unit).
    *
    * @return the time the CPU was occupied with background processes.
    */
   public int getSumBackground()
   {
      return sumBackground;
   }

   /**
    * Return the time the CPU was occupied with prioritized processes
    * (unspecified unit). The indices of the returned array corresponds
    * to the priority levels.
    *
    * @return the time the CPU was occupied with prioritized processes.
    */
   public int[] getSumPrioritized()
   {
      return sumPrioritized;
   }
}
