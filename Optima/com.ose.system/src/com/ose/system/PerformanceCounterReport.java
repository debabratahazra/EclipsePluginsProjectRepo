/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2011 by Enea Software AB.
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

import com.ose.system.service.monitor.MonitorCounterReport;
import com.ose.system.service.monitor.MonitorCounterReportExt;

/**
 * This class represents a performance counter report.
 * A PerformanceCounterReport object is immutable and its
 * content is a snapshot at the time of the notifying call.
 *
 * @see com.ose.system.PerformanceCounterReports#getReports()
 */
public class PerformanceCounterReport
{
   private final long pc;

   private final int pid;

   /**
    * Create a new performance counter report object.
    *
    * @param report  the internal performance counter report object.
    */
   PerformanceCounterReport(MonitorCounterReport report)
   {
      pc = report.pc;
      if (report instanceof MonitorCounterReportExt)
      {
         pid = ((MonitorCounterReportExt) report).pid;
      }
      else
      {
         pid = 0;
      }
   }

   /**
    * Create a new performance counter report object.
    *
    * @param pc   the program counter.
    * @param pid  the process ID.
    */
   PerformanceCounterReport(long pc, int pid)
   {
      this.pc = pc;
      this.pid = pid;
   }

   /**
    * Create a new performance counter report object for off-line usage.
    *
    * @param pc   the program counter.
    * @param pid  the process ID.
    * @return a new performance counter report object for off-line usage.
    */
   public static PerformanceCounterReport getInstance(long pc, int pid)
   {
      return new PerformanceCounterReport(pc, pid);
   }

   /**
    * Return the program counter (PC) at the time when this performance counter
    * report was created.
    *
    * @return the program counter (PC) at the time when this performance counter
    * report was created.
    */
   public long getPC()
   {
      return pc;
   }

   /**
    * Return the ID of the executing process at the time when this performance
    * counter report was created.
    *
    * @return the ID of the executing process at the time when this performance
    * counter report was created.
    */
   public int getPid()
   {
      return pid;
   }
}
