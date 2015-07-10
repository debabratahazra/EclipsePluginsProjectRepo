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

public abstract class MonitorUserInfoSignal extends MonitorEventInfoSignal
{
   public int from;         // U32
   public int eventNumber;  // U32
   public int eventSize;    // U32
   public int lineNumber;   // U32
   public short euId;       // U16
   public String fileName;  // char[]
   public byte[] eventData; // U8[]

   public MonitorUserInfoSignal(int sigNo)
   {
      super(sigNo);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int fileNameOffset;  // U32
      int eventDataOffset; // U32
      int eventDataCount;  // U32

      super.read(in);
      from = in.readS32();
      eventNumber = in.readS32();
      eventSize = in.readS32();
      lineNumber = in.readS32();
      fileNameOffset = in.readS32();
      eventDataOffset = in.readS32();
      eventDataCount = in.readS32();
      euId = in.readS16();
      in.readS16(); // Unused U16 reserved0
      if (lineNumber > 0)
      {
         in.skipBytes(fileNameOffset);
         fileName = in.readString();
         in.skipBytes(eventDataOffset - fileName.length() - 1);
      }
      else
      {
         fileName = "";
         in.skipBytes(eventDataOffset);
      }
      eventData = in.readS8Array(eventDataCount);
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      int fileNameLength; // U32
      int eventDataCount; // U32

      fileNameLength = ((fileName != null) ? (fileName.length() + 1) : 1);
      eventDataCount = ((eventData != null) ? eventData.length : 0);

      super.write(out);
      out.writeS32(from);
      out.writeS32(eventNumber);
      out.writeS32(eventSize);
      out.writeS32(lineNumber);
      out.writeS32(0); // U32 fileNameOffset
      out.writeS32(fileNameLength); // U32 eventDataOffset
      out.writeS32(eventDataCount); // U32 eventDataCount
      out.writeS16(euId);
      out.writeS16((short) 0); // Unused U16 reserved0
      out.writeString((fileName != null) ? fileName : "");
      if (eventDataCount > 0)
      {
         out.writeS8Array(eventData);
      }
   }
}
