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

package com.ose.dbgserver.monitor.signals;

import java.io.IOException;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;

public class MonitorSetRegistersReply extends Signal
{
   public static final int SIG_NO = 39108;

   public int pid;         // U32
   public int status;      // U32
   public int[] registers; // U32[] (array of register ids)

   public MonitorSetRegistersReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int registersCount; // U32

      pid = in.readS32();
      status = in.readS32();
      registersCount = in.readS32();
      registers = in.readS32Array(registersCount);
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(pid);
      out.writeS32(status);
      if (registers != null)
      {
         out.writeS32(registers.length);
         out.writeS32Array(registers);
      }
      else
      {
         out.writeS32(0);
      }
   }
}
