/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2007 by Enea Software AB.
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
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;

public abstract class MonitorBindInfoSignal extends MonitorEventInfoSignal
{
   public int from;        // U32
   public short fromEuId;  // U16
   public short toEuId;    // U16
   public int lineNumber;  // U32
   public String fileName; // char[]

   public MonitorBindInfoSignal(int sigNo)
   {
      super(sigNo);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int fileNameOffset; // U32

      super.read(in);
      in.readS32(); // Unused U32 reserved0
      from = in.readS32();
      fromEuId = in.readS16();
      toEuId = in.readS16();
      lineNumber = in.readS32();
      fileNameOffset = in.readS32();
      in.readS32(); // Unused U32 reserved1
      if (lineNumber > 0)
      {
         in.skipBytes(fileNameOffset);
         fileName = in.readString();
      }
      else
      {
         fileName = "";
      }
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      super.write(out);
      out.writeS32(0); // Unused U32 reserved0
      out.writeS32(from);
      out.writeS16(fromEuId);
      out.writeS16(toEuId);
      out.writeS32(lineNumber);
      out.writeS32(0); // U32 fileNameOffset
      out.writeS32(0); // Unused U32 reserved1
      out.writeString((fileName != null) ? fileName : "");
   }
}
