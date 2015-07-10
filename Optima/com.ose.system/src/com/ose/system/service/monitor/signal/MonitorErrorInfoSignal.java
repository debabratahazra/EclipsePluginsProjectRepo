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

public abstract class MonitorErrorInfoSignal extends MonitorEventInfoSignal
{
   public int from;        // U32
   public boolean exec;    // U32
   public int error;       // U32
   public int extra;       // U32
   public int lineNumber;  // U32
   public short euId;      // U16
   public String fileName; // char[]
   private int reserved1;  // U32 
   public MonitorExtendedErrorInfo monitorExtendedErrorInfo;

   public MonitorErrorInfoSignal(int sigNo)
   {
      super(sigNo);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int fileNameOffset; // U32

      super.read(in);
      from = in.readS32();
      exec = in.readBoolean();
      error = in.readS32();
      extra = in.readS32();
      lineNumber = in.readS32();
      fileNameOffset = in.readS32();
      euId = in.readS16();
      in.readS16(); // Unused U16 reserved0
      reserved1 = in.readS32(); // offset to where Extended Info starts
      int before = in.getReadPosition();
      if(reserved1 < fileNameOffset)
      {
         // read ExtendedErrorInfo first
         monitorExtendedErrorInfo = skipAndReadExtended(reserved1>0,reserved1, in);
         int skipBytes = getBytesToSkip(before, fileNameOffset, in);
         fileName = skipAndReadFileName(lineNumber>0, skipBytes, in);
      }
      else if(fileNameOffset <= reserved1)
      {
         // read Filename first
         fileName = skipAndReadFileName(lineNumber>0, fileNameOffset, in);
         int skipbytes = getBytesToSkip(before, reserved1, in);
         monitorExtendedErrorInfo = skipAndReadExtended(reserved1>0, skipbytes, in);
      }
   }
   
   private int getBytesToSkip(int before, int offset, SignalInputStream in)
   {
      int current = in.getReadPosition();
      int readLength = current - before;
      return offset - readLength;
   }
   
   private MonitorExtendedErrorInfo skipAndReadExtended(boolean hasExtended, int skipBytes, SignalInputStream in) throws IOException
   {
      if(hasExtended)
      {
         in.skipBytes(skipBytes);
         MonitorExtendedErrorInfo extended = new MonitorExtendedErrorInfo();
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

   protected void write(SignalOutputStream out) throws IOException
   {
      super.write(out);
      out.writeS32(from);
      out.writeBoolean(exec);
      out.writeS32(error);
      out.writeS32(extra);
      out.writeS32(lineNumber);
      out.writeS32(0); // U32 fileNameOffset
      out.writeS16(euId);
      out.writeS16((short) 0); // Unused U16 reserved0
      reserved1 = 0;
      if(monitorExtendedErrorInfo != null)
      {
         reserved1 = fileName.length() + 1; 
      }
      out.writeS32(reserved1); // reserved1
      out.writeString((fileName != null) ? fileName : "");
      if(reserved1 > 0)
      {
         monitorExtendedErrorInfo.write(out);         
      }
   }
}
