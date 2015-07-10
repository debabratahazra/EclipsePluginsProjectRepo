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

import com.ose.system.service.monitor.MonitorCPUProcessReport;
import com.ose.system.service.monitor.MonitorCPUProcessReport.MonitorCPUProcess;

/**
 * This class represents an OSE CPU process level load report.
 * A CPUProcessReport object is immutable and its content is a snapshot
 * at the time of the retrieving Target.getCPUProcessReports() call.
 *
 * @see com.ose.system.CPUProcessReports#getReports()
 */
public class CPUProcessReport extends TargetReport
{
   private final int sumOther;
   private final CPUProcessLoad[] sumProcesses;

   /**
    * Create a new CPU process level load report object.
    *
    * @param report  the internal CPU process level load report object.
    */
   CPUProcessReport(MonitorCPUProcessReport report)
   {
      super(report.tick, report.ntick, report.interval);
      this.sumOther = report.sumOther;
      this.sumProcesses = new CPUProcessLoad[report.processesCount];
      for (int i = 0; i < report.processesCount; i++)
      {
         this.sumProcesses[i] = new CPUProcessLoad(report.processes[i]);
      }
   }

   /**
    * Create a new CPU process level load report object.
    *
    * @param tick          the tick.
    * @param ntick         the micro tick.
    * @param interval      the measurement interval.
    * @param sumOther      the other sum.
    * @param sumProcesses  the processes sum.
    */
   CPUProcessReport(int tick,
                    int ntick,
                    int interval,
                    int sumOther,
                    CPUProcessLoad[] sumProcesses)
   {
      super(tick, ntick, interval);
      this.sumOther = sumOther;
      this.sumProcesses = sumProcesses;
   }

   /**
    * Create a new CPU process level load report object for off-line usage.
    *
    * @param tick          the tick.
    * @param ntick         the micro tick.
    * @param interval      the measurement interval.
    * @param sumOther      the other sum.
    * @param sumProcesses  the processes sum.
    * @return a new CPU process level load report object for off-line usage.
    */
   public static CPUProcessReport getInstance(int tick,
                                              int ntick,
                                              int interval,
                                              int sumOther,
                                              CPUProcessLoad[] sumProcesses)
   {
      return new CPUProcessReport(tick, ntick, interval, sumOther, sumProcesses);
   }

   /**
    * Return the time the CPU was occupied with all other processes than those
    * returned by getSumProcesses() (unspecified unit).
    *
    * @return the time the CPU was occupied with all other processes than those
    * returned by getSumProcesses().
    */
   public int getSumOther()
   {
      return sumOther;
   }

   /**
    * Return an array of the time the CPU was occupied per process.
    *
    * Note that some OSE target monitors return all processes that have executed
    * within the measurement interval, while other OSE target monitors return a
    * fixed-size subset containing the most time-consuming processes that have
    * executed within the measurement interval and those processes explicitly
    * set with Target.setCPUProcessReportPoint().
    *
    * @return an array of the time the CPU was occupied per process.
    */
   public CPUProcessLoad[] getSumProcesses()
   {
      return sumProcesses;
   }

   /**
    * This class contains the time the CPU was occupied with a specific process.
    * A CPUProcessLoad object is immutable and its content is a snapshot at the
    * time of the retrieving Target.getCPUProcessReports() call.
    *
    * @see com.ose.system.CPUProcessReport#getSumProcesses()
    */
   public static class CPUProcessLoad
   {
      private final int id;
      private final int sum;

      /**
       * Create a new CPU process load object.
       *
       * @param process  the internal CPU process load object.
       */
      CPUProcessLoad(MonitorCPUProcess process)
      {
         if (process == null)
         {
            throw new NullPointerException();
         }
         this.id = process.id;
         this.sum = process.sum;
      }

      /**
       * Create a new CPU process load object.
       *
       * @param id   the id.
       * @param sum  the sum.
       */
      CPUProcessLoad(int id, int sum)
      {
         this.id = id;
         this.sum = sum;
      }

      /**
       * Create a new CPU process load object for off-line usage.
       *
       * @param id   the id.
       * @param sum  the sum.
       * @return a new CPU process load object for off-line usage.
       */
      public static CPUProcessLoad getInstance(int id, int sum)
      {
         return new CPUProcessLoad(id, sum);
      }

      /**
       * Return the id of the process.
       *
       * @return the id of the process.
       */
      public int getId()
      {
         return id;
      }

      /**
       * Return the time the CPU was occupied with the process
       * (unspecified unit).
       *
       * @return the time the CPU was occupied with the process.
       */
      public int getSum()
      {
         return sum;
      }
   }
}
