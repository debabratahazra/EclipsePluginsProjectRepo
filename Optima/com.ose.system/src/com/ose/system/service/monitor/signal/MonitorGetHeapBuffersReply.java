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
import java.util.ArrayList;
import java.util.List;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;
import com.ose.system.service.monitor.MonitorHeapBufferInfo;

public class MonitorGetHeapBuffersReply extends Signal
{
   public static final int SIG_NO = 39147;

   private static final int HEAP_BUFFER_INFO_SIZE = 40;

   public int id; // U32
   public MonitorHeapBufferInfo[] buffers; // MonitorHeapBufferInfo[]

   public MonitorGetHeapBuffersReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      List buffersList;
      int buffersCount; // U32
      int fileNameCount = 0; // U32
      MonitorHeapBufferInfo prevBuffer = null;

      buffersList = new ArrayList();
      id = in.readS32();
      in.readS32(); // Unused U32 reserved0
      buffersCount = in.readS32();

      while (buffersCount > 0)
      {
         if (fileNameCount == 0)
         {
            MonitorHeapBufferInfo buffer = new MonitorHeapBufferInfo();
            buffer.owner = in.readS32();
            buffer.shared = in.readBoolean();
            buffer.address = in.readS32();
            buffer.size = in.readS32();
            buffer.reqSize = in.readS32();
            fileNameCount = in.readS32();
            buffer.fileName = "";
            buffer.lineNumber = in.readS32();
            buffer.status = in.readS32();
            buffer.reserved0 = in.readS32();
            buffer.reserved1 = in.readS32();
            buffersList.add(buffer);
            prevBuffer = buffer;
            buffersCount--;
         }
         else
         {
            prevBuffer.fileName =
               in.readString(fileNameCount * HEAP_BUFFER_INFO_SIZE);
            buffersCount -= fileNameCount;
            fileNameCount = 0;
         }
      }

      buffers = (MonitorHeapBufferInfo[])
         buffersList.toArray(new MonitorHeapBufferInfo[0]);
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      int buffersCount; // U32
      int fileNameCount = 0; // U32

      buffersCount = ((buffers != null) ? buffers.length : 0);
      for (int i = 0; i < buffersCount; i++)
      {
         MonitorHeapBufferInfo buffer = buffers[i];
         int fileNameLength =
            ((buffer.fileName != null) ? buffer.fileName.length() + 1 : 0);
         if (fileNameLength > 0)
         {
            fileNameCount += fileNameLength / HEAP_BUFFER_INFO_SIZE + 1;
         }
      }

      out.writeS32(id);
      out.writeS32(0); // Unused U32 reserved0
      out.writeS32(buffersCount + fileNameCount);

      for (int i = 0; i < buffersCount; i++)
      {
         MonitorHeapBufferInfo buffer = buffers[i];
         int fileNameLength =
            ((buffer.fileName != null) ? buffer.fileName.length() + 1 : 0);
         fileNameCount = (fileNameLength > 0) ?
            fileNameLength / HEAP_BUFFER_INFO_SIZE + 1 : 0;
         out.writeS32(buffer.owner);
         out.writeBoolean(buffer.shared);
         out.writeS32(buffer.address);
         out.writeS32(buffer.size);
         out.writeS32(buffer.reqSize);
         out.writeS32(fileNameCount);
         out.writeS32(buffer.lineNumber);
         out.writeS32(buffer.status);
         out.writeS32(buffer.reserved0);
         out.writeS32(buffer.reserved1);
         if (fileNameCount > 0)
         {
            out.writeString(buffer.fileName, fileNameCount * HEAP_BUFFER_INFO_SIZE);
         }
      }
   }
}
