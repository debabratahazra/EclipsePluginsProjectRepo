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

public class MonitorGetRamdumpReply extends Signal
{
   public static final int SIG_NO = 39504;

   public int dumpId;  // U32
   public int offset;  // U32
   public byte[] data; // U8[]

   public MonitorGetRamdumpReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int dataCount; // U32

      dumpId = in.readS32();
      offset = in.readS32();
      dataCount = in.readS32();
      data = in.readS8Array(dataCount);
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(dumpId);
      out.writeS32(offset);
      out.writeS32(data.length);
      out.writeS8Array(data);
   }
}
