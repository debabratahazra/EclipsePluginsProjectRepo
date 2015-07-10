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

package com.ose.system.service.monitor.signal;

import java.io.IOException;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;
import com.ose.system.service.monitor.MonitorCPUProcessReport;
import com.ose.system.service.monitor.MonitorCPUProcessReport.MonitorCPUProcess;

public class MonitorGetCPUProcessReportsReply extends Signal
{
   public static final int SIG_NO = 39275;

   public int status;                        // U32
   public boolean enabled;                   // U32
   public short euId;                        // U16
   public MonitorCPUProcessReport[] reports; // MonitorCPUProcessReport[]

   public MonitorGetCPUProcessReportsReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int reportsCount;

      status = in.readS32();
      enabled = in.readBoolean();
      euId = in.readS16();
      in.readS16(); // Unused U16 reserved0
      in.readS32(); // Unused U32 reserved1
      in.readS32(); // Unused U32 reserved2
      reportsCount = in.readS32();
      in.readS32(); // Unused U32 processesCount
      reports = new MonitorCPUProcessReport[reportsCount];
      for (int i = 0; i < reportsCount; i++)
      {
         MonitorCPUProcessReport report = new MonitorCPUProcessReport();
         report.tick = in.readS32();
         report.ntick = in.readS32();
         report.interval = in.readS32();
         report.sumOther = in.readS32();
         report.reserved0 = in.readS32();
         report.processesCount = in.readS32();
         report.processes = new MonitorCPUProcess[report.processesCount];
         for (int j = 0; j < report.processesCount; j++)
         {
            MonitorCPUProcess process = new MonitorCPUProcess();
            process.id = in.readS32();
            process.sum = in.readS32();
            report.processes[j] = process;
         }
         reports[i] = report;
      }
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      int reportsCount;
      int processesCount;

      reportsCount = ((reports != null) ? reports.length : 0);
      processesCount = reportsCount * 3;
      out.writeS32(status);
      out.writeBoolean(enabled);
      out.writeS16(euId);
      out.writeS16((short) 0); // Unused U16 reserved0
      out.writeS32(0); // Unused U32 reserved1
      out.writeS32(0); // Unused U32 reserved2
      out.writeS32(reportsCount);
      for (int i = 0; i < reportsCount; i++)
      {
         processesCount += reports[i].processesCount;
      }
      out.writeS32(processesCount);
      for (int i = 0; i < reportsCount; i++)
      {
         MonitorCPUProcessReport report = reports[i];
         out.writeS32(report.tick);
         out.writeS32(report.ntick);
         out.writeS32(report.interval);
         out.writeS32(report.sumOther);
         out.writeS32(report.reserved0);
         out.writeS32(report.processesCount);
         for (int j = 0; j < report.processesCount; j++)
         {
            MonitorCPUProcess process = report.processes[j];
            out.writeS32(process.id);
            out.writeS32(process.sum);
         }
      }
   }
}
