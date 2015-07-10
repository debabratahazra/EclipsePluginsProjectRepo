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

public class MonitorGetCPUProcessReportsEnabledReply extends Signal
{
   public static final int SIG_NO = 39259;

   public boolean enabled;    // U32
   public int interval;       // U32
   public int maxReports;     // U32
   public int maxProcsReport; // U32
   public int sec;            // U32
   public int sectick;        // U32
   public int secntick;       // U32

   public MonitorGetCPUProcessReportsEnabledReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      enabled = in.readBoolean();
      interval = in.readS32();
      if (getSigSize() >= 24)
      {
         maxReports = in.readS32();
         maxProcsReport = in.readS32();
         in.readS32(); // Unused U32 reserved0
      }
      if (getSigSize() >= 36)
      {
         sec = in.readS32();
         sectick = in.readS32();
         secntick = in.readS32();
      }
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeBoolean(enabled);
      out.writeS32(interval);
      out.writeS32(maxReports);
      out.writeS32(maxProcsReport);
      out.writeS32(0); // Unused U32 reserved0
      out.writeS32(sec);
      out.writeS32(sectick);
      out.writeS32(secntick);
   }
}
