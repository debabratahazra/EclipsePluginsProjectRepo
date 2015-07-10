/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2009 by Enea Software AB.
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
import com.ose.system.service.monitor.MonitorHeapBufferSizeInfo;

public class MonitorGetHeapInfoReply extends Signal
{
   public static final int SIG_NO = 39141;

   public int id;             // U32
   public int owner;          // U32
   public int sid;            // U32
   public int size;           // U32
   public int usedSize;       // U32
   public int peakUsedSize;   // U32
   public int reqSize;        // U32
   public int largestFree;    // U32
   public int largeThreshold; // U32
   public int priv;           // U32
   public int shared;         // U32
   public int mallocFailed;   // U32
   public MonitorHeapBufferSizeInfo[] bufferSizes; // MonitorHeapBufferSizeInfo[]

   public MonitorGetHeapInfoReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int bufferSizesCount; // U32

      id = in.readS32();
      owner = in.readS32();
      sid = in.readS32();
      size = in.readS32();
      usedSize = in.readS32();
      peakUsedSize = in.readS32();
      reqSize = in.readS32();
      largestFree = in.readS32();
      largeThreshold = in.readS32();
      priv = in.readS32();
      shared = in.readS32();
      mallocFailed = in.readS32();
      bufferSizesCount = in.readS32();
      in.readS32(); // Unused U32 reserved0
      in.readS32(); // Unused U32 reserved1
      in.readS32(); // Unused U32 reserved2
      in.readS32(); // Unused U32 reserved3

      bufferSizes = new MonitorHeapBufferSizeInfo[bufferSizesCount];
      for (int i = 0; i < bufferSizesCount; i++)
      {
         MonitorHeapBufferSizeInfo bufferSize = new MonitorHeapBufferSizeInfo();
         bufferSize.size = in.readS32();
         bufferSize.allocated = in.readS32();
         bufferSize.free = in.readS32();
         bufferSize.reserved0 = in.readS32();
         bufferSizes[i] = bufferSize;
      }
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      int bufferSizesCount; // U32

      bufferSizesCount = ((bufferSizes != null) ? bufferSizes.length : 0);

      out.writeS32(id);
      out.writeS32(owner);
      out.writeS32(sid);
      out.writeS32(size);
      out.writeS32(usedSize);
      out.writeS32(peakUsedSize);
      out.writeS32(reqSize);
      out.writeS32(largestFree);
      out.writeS32(largeThreshold);
      out.writeS32(priv);
      out.writeS32(shared);
      out.writeS32(mallocFailed);
      out.writeS32(bufferSizesCount);
      out.writeS32(0); // Unused U32 reserved0
      out.writeS32(0); // Unused U32 reserved1
      out.writeS32(0); // Unused U32 reserved2
      out.writeS32(0); // Unused U32 reserved3

      for (int i = 0; i < bufferSizesCount; i++)
      {
         MonitorHeapBufferSizeInfo bufferSize = bufferSizes[i];
         out.writeS32(bufferSize.size);
         out.writeS32(bufferSize.allocated);
         out.writeS32(bufferSize.free);
         out.writeS32(bufferSize.reserved0);
      }
   }
}
