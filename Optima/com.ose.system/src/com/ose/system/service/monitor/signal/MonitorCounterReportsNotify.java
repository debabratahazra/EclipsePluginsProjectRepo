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

package com.ose.system.service.monitor.signal;

import java.io.IOException;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;
import com.ose.system.service.monitor.Monitor;
import com.ose.system.service.monitor.MonitorCounterReport;
import com.ose.system.service.monitor.MonitorCounterReportExt;

public class MonitorCounterReportsNotify extends Signal
{
   public static final int SIG_NO = 40791;

   public short euId;                     // U16
   public int counterType;                // U32
   public MonitorCounterReport[] reports; // MonitorCounterReport[]

   public MonitorCounterReportsNotify()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int counterReportType;
      int reportsCount;

      euId = in.readS16();
      in.readS16(); // Unused U16 reserved0
      counterType = in.readS32();
      counterReportType = in.readS32();
      in.readS32(); // Unused U32 reserved1
      reportsCount = in.readS32();
      if (counterReportType == Monitor.MONITOR_COUNTER_REPORT_EXT)
      {
         reports = new MonitorCounterReportExt[reportsCount];
         for (int i = 0; i < reportsCount; i++)
         {
            MonitorCounterReportExt report = new MonitorCounterReportExt();
            report.pc = in.readS64();
            report.pid = in.readS32();
            report.reserved0 = in.readS32();
            reports[i] = report;
         }
      }
      else
      {
         reports = new MonitorCounterReport[reportsCount];
         for (int i = 0; i < reportsCount; i++)
         {
            MonitorCounterReport report = new MonitorCounterReport();
            report.pc = in.readS64();
            reports[i] = report;
         }
      }
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      int counterReportType;
      int reportsCount;

      if (reports instanceof MonitorCounterReportExt[])
      {
         counterReportType = Monitor.MONITOR_COUNTER_REPORT_EXT;
      }
      else
      {
         counterReportType = Monitor.MONITOR_COUNTER_REPORT;
      }
      reportsCount = ((reports != null) ? reports.length : 0);
      out.writeS16(euId);
      out.writeS16((short) 0); // Unused U16 reserved0
      out.writeS32(counterType);
      out.writeS32(counterReportType);
      out.writeS32(0); // Unused U32 reserved1
      out.writeS32(reportsCount);
      if (counterReportType == Monitor.MONITOR_COUNTER_REPORT_EXT)
      {
         for (int i = 0; i < reportsCount; i++)
         {
            MonitorCounterReportExt report = (MonitorCounterReportExt) reports[i];
            out.writeS64(report.pc);
            out.writeS32(report.pid);
            out.writeS32(report.reserved0);
         }
      }
      else
      {
         for (int i = 0; i < reportsCount; i++)
         {
            MonitorCounterReport report = reports[i];
            out.writeS64(report.pc);
         }
      }
   }
}
