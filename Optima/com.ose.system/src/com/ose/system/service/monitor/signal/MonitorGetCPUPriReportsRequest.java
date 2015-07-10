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

public class MonitorGetCPUPriReportsRequest extends Signal
{
   public static final int SIG_NO = 39272;

   public short euId; // U16
   public int tick;   // U32
   public int ntick;  // U32

   public MonitorGetCPUPriReportsRequest()
   {
      super(SIG_NO);
   }

   public MonitorGetCPUPriReportsRequest(short euId, int tick, int ntick)
   {
      super(SIG_NO);
      this.euId = euId;
      this.tick = tick;
      this.ntick = ntick;
   }

   protected void read(SignalInputStream in) throws IOException
   {
      euId = (short) in.readS32();
      tick = in.readS32();
      ntick = in.readS32();
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(0xFFFF & euId);
      out.writeS32(tick);
      out.writeS32(ntick);
   }
}
