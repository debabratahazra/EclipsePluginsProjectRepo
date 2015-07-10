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

public class MonitorSetUserReportsEnabledRequest extends Signal
{
   public static final int SIG_NO = 39280;

   public int reportNo;        // U32
   public boolean enabled;     // U32
   public int interval;        // U32
   public int maxReports;      // U32
   public int maxValuesReport; // U32

   public MonitorSetUserReportsEnabledRequest()
   {
      super(SIG_NO);
   }

   public MonitorSetUserReportsEnabledRequest(int reportNo,
                                              boolean enabled,
                                              int interval,
                                              int maxReports,
                                              int maxValuesReport)
   {
      super(SIG_NO);
      this.reportNo = reportNo;
      this.enabled = enabled;
      this.interval = interval;
      this.maxReports = maxReports;
      this.maxValuesReport = maxValuesReport;
   }

   protected void read(SignalInputStream in) throws IOException
   {
      reportNo = in.readS32();
      enabled = in.readBoolean();
      interval = in.readS32();
      maxReports = in.readS32();
      maxValuesReport = in.readS32();
      in.readS32(); // Unused U32 reserved0
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(reportNo);
      out.writeBoolean(enabled);
      out.writeS32(interval);
      out.writeS32(maxReports);
      out.writeS32(maxValuesReport);
      out.writeS32(0); // Unused U32 reserved0
   }
}
