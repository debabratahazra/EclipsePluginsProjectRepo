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

public class MonitorGetCounterTypeEnabledReply extends Signal
{
   public static final int SIG_NO = 40784;

   public int status;        // U32
   public boolean enabled;   // U32
   public short euId;        // U16
   public int counterType;   // U32
   public long counterValue; // U64
   public int maxReports;    // U32

   public MonitorGetCounterTypeEnabledReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      status = in.readS32();
      enabled = in.readBoolean();
      euId = in.readS16();
      in.readS16(); // Unused U16 reserved0
      in.readS32(); // Unused U32 reserved1
      counterType = in.readS32();
      counterValue = in.readS64();
      maxReports = in.readS32();
      in.readS32(); // Unused U32 reserved2
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(status);
      out.writeBoolean(enabled);
      out.writeS16(euId);
      out.writeS16((short) 0); // Unused U16 reserved0
      out.writeS32(0); // Unused U32 reserved1
      out.writeS32(counterType);
      out.writeS64(counterValue);
      out.writeS32(maxReports);
      out.writeS32(0); // Unused U32 reserved2
   }
}
