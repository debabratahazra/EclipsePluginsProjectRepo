/* COPYRIGHT-ENEA-SRC-R1 *
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

package com.ose.system.service.monitor.signal;

import java.io.IOException;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;
import com.ose.system.service.monitor.MonitorUserReport;
import com.ose.system.service.monitor.MonitorUserReport.MonitorMaxMinUserReportValue;
import com.ose.system.service.monitor.MonitorUserReport.MonitorUserReportValue;

public class MonitorGetUserReportsReply extends Signal
{
   public static final int SIG_NO = 39283;

   public int status;                  // U32
   public int reportNo;                // U32
   public boolean enabled;             // U32
   public boolean continuous;          // U8
   public boolean maxmin;              // U8
   public boolean multiple;            // U8
   public MonitorUserReport[] reports; // MonitorUserReport[]

   public MonitorGetUserReportsReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int reportsCount;

      status = in.readS32();
      reportNo = in.readS32();
      enabled = in.readBoolean();
      continuous = in.readOSBOOLEAN();
      maxmin = in.readOSBOOLEAN();
      multiple = in.readOSBOOLEAN();
      in.readOSBOOLEAN(); // Unused U8 reserved0
      in.readS32(); // Unused U32 reserved1
      reportsCount = in.readS32();
      in.readS32(); // Unused U32 reportValuesCount
      reports = new MonitorUserReport[reportsCount];
      for (int i = 0; i < reportsCount; i++)
      {
         MonitorUserReport report = new MonitorUserReport();
         report.tick = in.readS32();
         report.ntick = in.readS32();
         report.interval = in.readS32();
         report.sumOther = in.readS32();
         if (maxmin)
         {
            report.maxOther = in.readS32();
            report.minOther = in.readS32();
         }
         in.readS32(); // Unused U32 reserved0
         report.reportValuesCount = in.readS32();
         report.reportValues = new MonitorUserReportValue[report.reportValuesCount];
         for (int j = 0; j < report.reportValuesCount; j++)
         {
            MonitorUserReportValue reportValue = (maxmin ?
                  new MonitorMaxMinUserReportValue() :
                  new MonitorUserReportValue());
            reportValue.id = in.readS32();
            reportValue.sum = in.readS32();
            if (maxmin)
            {
               MonitorMaxMinUserReportValue maxMinReportValue =
                  (MonitorMaxMinUserReportValue) reportValue;
               maxMinReportValue.max = in.readS32();
               maxMinReportValue.min = in.readS32();
            }
            report.reportValues[j] = reportValue;
         }
         reports[i] = report;
      }
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      int reportsCount;
      int reportValuesCount;

      reportsCount = ((reports != null) ? reports.length : 0);
      out.writeS32(status);
      out.writeS32(reportNo);
      out.writeBoolean(enabled);
      out.writeOSBOOLEAN(continuous);
      out.writeOSBOOLEAN(maxmin);
      out.writeOSBOOLEAN(multiple);
      out.writeOSBOOLEAN(false); // Unused U8 reserved0
      out.writeS32(0); // Unused U32 reserved1
      out.writeS32(reportsCount);
      reportValuesCount = (maxmin ? reportsCount * 4 : reportsCount * 3);
      for (int i = 0; i < reportsCount; i++)
      {
         reportValuesCount += (maxmin ? reports[i].reportValuesCount * 2 :
                               reports[i].reportValuesCount);
      }
      out.writeS32(reportValuesCount);
      for (int i = 0; i < reportsCount; i++)
      {
         MonitorUserReport report = reports[i];
         out.writeS32(report.tick);
         out.writeS32(report.ntick);
         out.writeS32(report.interval);
         out.writeS32(report.sumOther);
         if (maxmin)
         {
            out.writeS32(report.maxOther);
            out.writeS32(report.minOther);
         }
         out.writeS32(0); // Unused U32 reserved0
         out.writeS32(report.reportValuesCount);
         for (int j = 0; j < report.reportValuesCount; j++)
         {
            MonitorUserReportValue reportValue = report.reportValues[j];
            out.writeS32(reportValue.id);
            out.writeS32(reportValue.sum);
            if (maxmin)
            {
               MonitorMaxMinUserReportValue maxMinReportValue =
                  (MonitorMaxMinUserReportValue) reportValue;
               out.writeS32(maxMinReportValue.max);
               out.writeS32(maxMinReportValue.min);
            }
         }
      }
   }
}
