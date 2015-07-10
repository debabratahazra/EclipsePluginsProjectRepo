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

public class MonitorSetMemoryRequest extends Signal
{
   public static final int SIG_NO = 39103;

   public int pid;     // U32
   public int address; // U32
   public byte[] data; // U8[]

   public MonitorSetMemoryRequest()
   {
      super(SIG_NO);
   }

   public MonitorSetMemoryRequest(int pid, int address, byte[] data)
   {
      super(SIG_NO);
      this.pid = pid;
      this.address = address;
      this.data = data;
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int dataCount; // U32

      pid = in.readS32();
      address = in.readS32();
      dataCount = in.readS32();
      data = in.readS8Array(dataCount);
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(pid);
      out.writeS32(address);
      out.writeS32(data.length);
      out.writeS8Array(data);
   }
}
