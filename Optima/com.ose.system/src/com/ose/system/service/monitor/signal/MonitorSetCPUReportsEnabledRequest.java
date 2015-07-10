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

public class MonitorSetCPUReportsEnabledRequest extends Signal
{
   public static final int SIG_NO = 39252;

   public boolean enabled; // U32
   public int interval;    // U32
   public int priority;    // U32
   public int maxReports;  // U32

   public MonitorSetCPUReportsEnabledRequest()
   {
      super(SIG_NO);
   }

   public MonitorSetCPUReportsEnabledRequest(boolean enabled,
                                             int interval,
                                             int priority,
                                             int maxReports)
   {
      super(SIG_NO);
      this.enabled = enabled;
      this.interval = interval;
      this.priority = priority;
      this.maxReports = maxReports;
   }

   protected void read(SignalInputStream in) throws IOException
   {
      enabled = in.readBoolean();
      interval = in.readS32();
      priority = in.readS32();
      if (getSigSize() > 16)
      {
         maxReports = in.readS32();
         in.readS32(); // Unused U32 reserved0
      }
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeBoolean(enabled);
      out.writeS32(interval);
      out.writeS32(priority);
      out.writeS32(maxReports);
      out.writeS32(0); // Unused U32 reserved0
   }
}
