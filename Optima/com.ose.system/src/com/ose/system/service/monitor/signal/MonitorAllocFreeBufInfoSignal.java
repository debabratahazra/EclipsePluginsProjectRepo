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

public abstract class MonitorAllocFreeBufInfoSignal extends MonitorEventInfoSignal
{
   public int from;          // U32
   public int signalNumber;  // U32
   public int signalSize;    // U32
   public int signalAddress; // U32
   public int signalId;      // U32
   public int lineNumber;    // U32
   public short euId;        // U16
   public String fileName;   // char[]
   private short reserved0;   // U16
   public MonitorExtendedAllocFreeBufInfo monitorExtendedAllocFreeBufInfo;

   public MonitorAllocFreeBufInfoSignal(int sigNo)
   {
      super(sigNo);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int fileNameOffset; // U32

      super.read(in);
      from = in.readS32();
      signalNumber = in.readS32();
      signalSize = in.readS32();
      signalAddress = in.readS32();
      signalId = in.readS32();
      lineNumber = in.readS32();
      fileNameOffset = in.readS32();
      euId = in.readS16();
      reserved0 = in.readS16();
      int before = in.getReadPosition();
      if(reserved0 < fileNameOffset)
      {
         // read ExtendedErrorInfo first
         monitorExtendedAllocFreeBufInfo = skipAndReadExtended(reserved0 > 0, reserved0, in);
         int skipBytes = getBytesToSkip(before, fileNameOffset, in);
         fileName = skipAndReadFileName(lineNumber>0, skipBytes, in);
      }
      else if(fileNameOffset <= reserved0)
      {
         // read Filename first
         fileName = skipAndReadFileName(lineNumber>0, fileNameOffset, in);
         int skipbytes = getBytesToSkip(before, reserved0, in);
         monitorExtendedAllocFreeBufInfo = skipAndReadExtended(reserved0>0, skipbytes, in);
      }
   }
   
   private MonitorExtendedAllocFreeBufInfo skipAndReadExtended(boolean hasExtended, int skipBytes, SignalInputStream in) throws IOException
   {
      if(hasExtended)
      {
         in.skipBytes(skipBytes);
         MonitorExtendedAllocFreeBufInfo extended = new MonitorExtendedAllocFreeBufInfo();
         extended.read(in);
         return extended;
      }
      return null;
   }
   
   private String skipAndReadFileName(boolean hasFileName, int skipBytes, SignalInputStream in) throws IOException
   {
      if (hasFileName)
      {
         in.skipBytes(skipBytes);
         return in.readString();
      }
      return "";
   }
   
   private int getBytesToSkip(int before, int offset, SignalInputStream in)
   {
      int current = in.getReadPosition();
      int readLength = current - before;
      return offset - readLength;
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      super.write(out);
      out.writeS32(from);
      out.writeS32(signalNumber);
      out.writeS32(signalSize);
      out.writeS32(signalAddress);
      out.writeS32(signalId);
      out.writeS32(lineNumber);
      out.writeS32(0); // U32 fileNameOffset
      out.writeS16(euId);
      reserved0 = 0;
      if(monitorExtendedAllocFreeBufInfo != null)
      {
         reserved0 = (short) (fileName.length() + 1); 
      }
      out.writeS16(reserved0); 
      out.writeString((fileName != null) ? fileName : "");
      if(reserved0 > 0)
      {
         monitorExtendedAllocFreeBufInfo.write(out);         
      }
   }
}
