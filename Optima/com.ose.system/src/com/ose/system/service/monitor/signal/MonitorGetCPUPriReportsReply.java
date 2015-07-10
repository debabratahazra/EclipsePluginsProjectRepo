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
import com.ose.system.service.monitor.MonitorCPUPriReport;

public class MonitorGetCPUPriReportsReply extends Signal
{
   public static final int SIG_NO = 39273;

   public int status;                    // U32
   public boolean enabled;               // U32
   public short euId;                    // U16
   public MonitorCPUPriReport[] reports; // MonitorCPUPriReport[]

   public MonitorGetCPUPriReportsReply()
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
      reportsCount = in.readS32();
      reports = new MonitorCPUPriReport[reportsCount];
      for (int i = 0; i < reportsCount; i++)
      {
         MonitorCPUPriReport report = new MonitorCPUPriReport();
         report.tick = in.readS32();
         report.ntick = in.readS32();
         report.interval = in.readS32();
         report.sumInterrupt = in.readS32();
         report.sumBackground = in.readS32();
         report.reserved0 = in.readS32();
         report.sumPrioritized = in.readS32Array(32);
         reports[i] = report;
      }
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      int reportsCount;

      reportsCount = ((reports != null) ? reports.length : 0);
      out.writeS32(status);
      out.writeBoolean(enabled);
      out.writeS16(euId);
      out.writeS16((short) 0); // Unused U16 reserved0
      out.writeS32(0); // Unused U32 reserved1
      out.writeS32(reportsCount);
      for (int i = 0; i < reportsCount; i++)
      {
         MonitorCPUPriReport report = reports[i];
         out.writeS32(report.tick);
         out.writeS32(report.ntick);
         out.writeS32(report.interval);
         out.writeS32(report.sumInterrupt);
         out.writeS32(report.sumBackground);
         out.writeS32(report.reserved0);
         if ((report.sumPrioritized == null) ||
             (report.sumPrioritized.length != 32))
         {
            throw new IOException("Invalid MonitorCPUPriReport.sumPrioritized " +
                                  "in MonitorGetCPUPriReportsReply.");
         }
         out.writeS32Array(report.sumPrioritized);
      }
   }
}
