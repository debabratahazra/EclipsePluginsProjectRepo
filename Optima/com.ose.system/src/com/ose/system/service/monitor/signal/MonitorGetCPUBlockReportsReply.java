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

package com.ose.system.service.monitor.signal;

import java.io.IOException;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;
import com.ose.system.service.monitor.MonitorCPUBlockReport;
import com.ose.system.service.monitor.MonitorCPUBlockReport.MonitorCPUBlock;

public class MonitorGetCPUBlockReportsReply extends Signal
{
   public static final int SIG_NO = 39277;

   public int status;                      // U32
   public boolean enabled;                 // U32
   public short euId;                      // U16
   public MonitorCPUBlockReport[] reports; // MonitorCPUBlockReport[]

   public MonitorGetCPUBlockReportsReply()
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
      in.readS32(); // Unused U32 blocksCount
      reports = new MonitorCPUBlockReport[reportsCount];
      for (int i = 0; i < reportsCount; i++)
      {
         MonitorCPUBlockReport report = new MonitorCPUBlockReport();
         report.tick = in.readS32();
         report.ntick = in.readS32();
         report.interval = in.readS32();
         report.sumOther = in.readS32();
         report.reserved0 = in.readS32();
         report.blocksCount = in.readS32();
         report.blocks = new MonitorCPUBlock[report.blocksCount];
         for (int j = 0; j < report.blocksCount; j++)
         {
            MonitorCPUBlock block = new MonitorCPUBlock();
            block.id = in.readS32();
            block.sum = in.readS32();
            report.blocks[j] = block;
         }
         reports[i] = report;
      }
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      int reportsCount;
      int blocksCount;

      reportsCount = ((reports != null) ? reports.length : 0);
      blocksCount = reportsCount * 3;
      out.writeS32(status);
      out.writeBoolean(enabled);
      out.writeS16(euId);
      out.writeS16((short) 0); // Unused U16 reserved0
      out.writeS32(0); // Unused U32 reserved1
      out.writeS32(0); // Unused U32 reserved2
      out.writeS32(reportsCount);
      for (int i = 0; i < reportsCount; i++)
      {
         blocksCount += reports[i].blocksCount;
      }
      out.writeS32(blocksCount);
      for (int i = 0; i < reportsCount; i++)
      {
         MonitorCPUBlockReport report = reports[i];
         out.writeS32(report.tick);
         out.writeS32(report.ntick);
         out.writeS32(report.interval);
         out.writeS32(report.sumOther);
         out.writeS32(report.reserved0);
         out.writeS32(report.blocksCount);
         for (int j = 0; j < report.blocksCount; j++)
         {
            MonitorCPUBlock block = report.blocks[j];
            out.writeS32(block.id);
            out.writeS32(block.sum);
         }
      }
   }
}
