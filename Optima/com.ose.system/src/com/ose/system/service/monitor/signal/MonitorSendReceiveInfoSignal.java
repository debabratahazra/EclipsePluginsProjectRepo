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

public abstract class MonitorSendReceiveInfoSignal extends MonitorEventInfoSignal
{
   public int signalNumber;    // U32
   public int signalSender;    // U32
   public int signalAddressee; // U32
   public int signalSize;      // U32
   public int signalAddress;   // U32
   public int signalId;        // U32
   public int lineNumber;      // U32
   public short euId;          // U16
   public int reserved1;       // U32 The 32 most significant bits of the signal buffer address in 64 bit systems (or 0).
   public String fileName;     // char[]
   public byte[] signalData;   // U8[]

   public MonitorSendReceiveInfoSignal(int sigNo)
   {
      super(sigNo);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int fileNameOffset;   // U32
      int signalDataOffset; // U32
      int signalDataCount;  // U32

      super.read(in);
      signalNumber = in.readS32();
      signalSender = in.readS32();
      signalAddressee = in.readS32();
      signalSize = in.readS32();
      signalAddress = in.readS32();
      signalId = in.readS32();
      lineNumber = in.readS32();
      fileNameOffset = in.readS32();
      signalDataOffset = in.readS32();
      signalDataCount = in.readS32();
      euId = in.readS16();
      in.readS16(); // Unused U16 reserved0
      reserved1 = in.readS32();
      if (lineNumber > 0)
      {
         in.skipBytes(fileNameOffset);
         fileName = in.readString();
         in.skipBytes(signalDataOffset - fileName.length() - 1);
      }
      else
      {
         fileName = "";
         in.skipBytes(signalDataOffset);
      }
      signalData = in.readS8Array(signalDataCount);
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      int fileNameLength;  // U32
      int signalDataCount; // U32

      fileNameLength = ((fileName != null) ? (fileName.length() + 1) : 1);
      signalDataCount = ((signalData != null) ? signalData.length : 0);

      super.write(out);
      out.writeS32(signalNumber);
      out.writeS32(signalSender);
      out.writeS32(signalAddressee);
      out.writeS32(signalSize);
      out.writeS32(signalAddress);
      out.writeS32(signalId);
      out.writeS32(lineNumber);
      out.writeS32(0); // U32 fileNameOffset
      out.writeS32(fileNameLength); // U32 signalDataOffset
      out.writeS32(signalDataCount); // U32 signalDataCount
      out.writeS16(euId);
      out.writeS16((short) 0); // Unused U16 reserved0
      out.writeS32(reserved1); 
      out.writeString((fileName != null) ? fileName : "");
      if (signalDataCount > 0)
      {
         out.writeS8Array(signalData);
      }
   }
}
