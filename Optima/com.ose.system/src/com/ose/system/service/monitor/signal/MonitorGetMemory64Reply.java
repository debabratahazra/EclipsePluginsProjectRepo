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

public class MonitorGetMemory64Reply extends Signal
{
   public static final int SIG_NO = 40830;

   public int pid;       // U32
   public long address;   // U64
   public byte[] data;   // U8[]

   public MonitorGetMemory64Reply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int dataCount; // U32

      pid = in.readS32();
      address = in.readS64();
      dataCount = in.readS32();
      in.readS32(); // Unused U32 reserved0
      in.readS64(); // Unused U64 reserved1
      data = in.readS8Array(dataCount);
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(pid);
      out.writeS64(address);
      out.writeS32(data.length);
      out.writeS32(0); // Unused U32 reserved0
      out.writeS64(0); // Unused U64 reserved1
      out.writeS8Array(data);
   }
}
