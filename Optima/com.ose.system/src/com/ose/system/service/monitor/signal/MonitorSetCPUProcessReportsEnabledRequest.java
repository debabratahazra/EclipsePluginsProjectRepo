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

public class MonitorSetCPUProcessReportsEnabledRequest extends Signal
{
   public static final int SIG_NO = 39260;

   public boolean enabled;     // U32
   public int interval;        // U32
   public int maxReports;      // U32
   public int maxProcsReport;  // U32
   public boolean usePriority; // U8
   public byte priority;       // U8
   public short reserved1;     // U16

   public MonitorSetCPUProcessReportsEnabledRequest()
   {
      super(SIG_NO);
   }

   public MonitorSetCPUProcessReportsEnabledRequest(boolean enabled,
                                                    int interval,
                                                    int maxReports,
                                                    int maxProcsReport,
                                                    boolean usePriority,
                                                    byte priority,
                                                    boolean perCoreProfiledProcesses)
   {
      super(SIG_NO);
      this.enabled = enabled;
      this.interval = interval;
      this.maxReports = maxReports;
      this.maxProcsReport = maxProcsReport;
      this.usePriority = usePriority;
      this.priority = priority;
      this.reserved1 = perCoreProfiledProcesses ? (short) 1 : (short) 0;
   }

   protected void read(SignalInputStream in) throws IOException
   {
      enabled = in.readBoolean();
      interval = in.readS32();
      if (getSigSize() > 12)
      {
         maxReports = in.readS32();
         maxProcsReport = in.readS32();
         usePriority = in.readOSBOOLEAN();
         priority = in.readS8();
         reserved1 = in.readS16();
      }
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeBoolean(enabled);
      out.writeS32(interval);
      out.writeS32(maxReports);
      out.writeS32(maxProcsReport);
      out.writeOSBOOLEAN(usePriority);
      out.writeS8(priority);
      out.writeS16(reserved1);
   }
}
