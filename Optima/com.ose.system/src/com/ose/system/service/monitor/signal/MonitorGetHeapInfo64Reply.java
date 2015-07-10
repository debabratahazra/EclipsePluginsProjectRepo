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
import com.ose.system.service.monitor.MonitorHeapBufferSizeInfo64;

public class MonitorGetHeapInfo64Reply extends Signal
{
   public static final int SIG_NO = 40826;

   public int id;             // U32
   public int owner;          // U32
   public int sid;            // U32
   public long size;           // U64
   public long usedSize;       // U64
   public long peakUsedSize;   // U64
   public long reqSize;        // U64
   public long largestFree;    // U64
   public long largeThreshold; // U64
   public int priv;           // U32
   public int shared;         // U32
   public int mallocFailed;   // U32
   public MonitorHeapBufferSizeInfo64[] bufferSizes; // MonitorHeapBufferSizeInfo[]

   public MonitorGetHeapInfo64Reply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int bufferSizesCount; // U32

      id = in.readS32();
      owner = in.readS32();
      sid = in.readS32();
      size = in.readS64();
      usedSize = in.readS64();
      peakUsedSize = in.readS64();
      reqSize = in.readS64();
      largestFree = in.readS64();
      largeThreshold = in.readS64();
      priv = in.readS32();
      shared = in.readS32();
      mallocFailed = in.readS32();
      bufferSizesCount = in.readS32();
      in.readS32(); // Unused U32 reserved0
      in.readS32(); // Unused U32 reserved1
      in.readS32(); // Unused U32 reserved2
      in.readS32(); // Unused U32 reserved3

      bufferSizes = new MonitorHeapBufferSizeInfo64[bufferSizesCount];
      for (int i = 0; i < bufferSizesCount; i++)
      {
         MonitorHeapBufferSizeInfo64 bufferSize = new MonitorHeapBufferSizeInfo64();
         bufferSize.size = in.readS64();
         bufferSize.allocated = in.readS64();
         bufferSize.free = in.readS64();
         bufferSize.reserved0 = in.readS64();
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
      out.writeS64(size);
      out.writeS64(usedSize);
      out.writeS64(peakUsedSize);
      out.writeS64(reqSize);
      out.writeS64(largestFree);
      out.writeS64(largeThreshold);
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
         MonitorHeapBufferSizeInfo64 bufferSize = bufferSizes[i];
         out.writeS64(bufferSize.size);
         out.writeS64(bufferSize.allocated);
         out.writeS64(bufferSize.free);
         out.writeS64(bufferSize.reserved0);
      }
   }
}
