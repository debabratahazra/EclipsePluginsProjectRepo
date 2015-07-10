/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2005-2007 by Enea Software AB.
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
import com.ose.system.service.monitor.MonitorSignalInfo;

public class MonitorGetSignalQueueReply extends Signal
{
   public static final int SIG_NO = 39036;

   public int pid;                  // U32
   public MonitorSignalInfo signal; // MonitorSignalInfo

   public MonitorGetSignalQueueReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      pid = in.readS32();
      signal = new MonitorSignalInfo();
      signal.number = in.readS32();
      signal.owner = in.readS32();
      signal.sender = in.readS32();
      signal.addressee = in.readS32();
      signal.size = in.readS32();
      signal.bufsize = in.readS32();
      signal.address = in.readS32();
      signal.status = in.readS32();
      signal.reserved0 = in.readS32();
      signal.reserved1 = in.readS32();
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(pid);
      out.writeS32(signal.number);
      out.writeS32(signal.owner);
      out.writeS32(signal.sender);
      out.writeS32(signal.addressee);
      out.writeS32(signal.size);
      out.writeS32(signal.bufsize);
      out.writeS32(signal.address);
      out.writeS32(signal.status);
      out.writeS32(signal.reserved0);
      out.writeS32(signal.reserved1);
   }
}
