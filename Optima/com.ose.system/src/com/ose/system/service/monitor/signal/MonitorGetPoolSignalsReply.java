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

public class MonitorGetPoolSignalsReply extends Signal
{
   public static final int SIG_NO = 39054;

   public int pid; // U32
   public MonitorSignalInfo[] signals; // MonitorSignalInfo[]

   public MonitorGetPoolSignalsReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int signalsCount; // U32

      pid = in.readS32();
      in.readS32(); // Unused U32 reserved0
      signalsCount = in.readS32();
      signals = new MonitorSignalInfo[signalsCount];
      for (int i = 0; i < signalsCount; i++)
      {
         MonitorSignalInfo signalInfo = new MonitorSignalInfo();
         signalInfo.number = in.readS32();
         signalInfo.owner = in.readS32();
         signalInfo.sender = in.readS32();
         signalInfo.addressee = in.readS32();
         signalInfo.size = in.readS32();
         signalInfo.bufsize = in.readS32();
         signalInfo.address = in.readS32();
         signalInfo.status = in.readS32();
         signalInfo.reserved0 = in.readS32();
         signalInfo.reserved1 = in.readS32();
         signals[i] = signalInfo;
      }
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      int signalsCount; // U32

      signalsCount = ((signals != null) ? signals.length : 0);
      out.writeS32(pid);
      out.writeS32(0); // Unused U32 reserved0
      out.writeS32(signalsCount);

      for (int i = 0; i < signalsCount; i++)
      {
         MonitorSignalInfo signalInfo = signals[i];
         out.writeS32(signalInfo.number);
         out.writeS32(signalInfo.owner);
         out.writeS32(signalInfo.sender);
         out.writeS32(signalInfo.addressee);
         out.writeS32(signalInfo.size);
         out.writeS32(signalInfo.bufsize);
         out.writeS32(signalInfo.address);
         out.writeS32(signalInfo.status);
         out.writeS32(signalInfo.reserved0);
         out.writeS32(signalInfo.reserved1);
      }
   }
}
