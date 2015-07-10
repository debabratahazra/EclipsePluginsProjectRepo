/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2008 by Enea Software AB.
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
package com.ose.system.service.tracepoint.signal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;
import com.ose.system.service.tracepoint.MonitorTracepointBufferStatus;

public class MonitorTracepointGetStatusReply extends Signal
{

   public static final int SIG_NO = 40761;

   public int status; // U32
   public int traceStatus; // U32
   public int cores; // U32
   public MonitorTracepointBufferStatus[] perCoreStatus;

   public MonitorTracepointGetStatusReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      status = in.readS32();
      traceStatus = in.readS32();
      in.readS32(); // Unused U32 reserved0
      in.readS32(); // Unused U32 reserved1
      cores = in.readS32();

      perCoreStatus = new MonitorTracepointBufferStatus[cores];
      MonitorTracepointBufferStatus bufferStatus;
      if (cores > 0)
      {
         List<MonitorTracepointBufferStatus> coresList = new ArrayList<MonitorTracepointBufferStatus>(
               cores);
         for (int i = 0; i < cores; i++)
         {
            bufferStatus = new MonitorTracepointBufferStatus();
            bufferStatus.frames = in.readS64();
            bufferStatus.created = in.readS64();
            bufferStatus.size = in.readS64();
            bufferStatus.free = in.readS64();
            bufferStatus.bufferType = in.readS32();
            in.readS32(); // reserved0
            perCoreStatus[i] = bufferStatus;
         }
      }
      else
      {
         perCoreStatus = null;
      }

   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(status);
      out.writeS32(traceStatus);
      out.writeS32(0); // Unused U32 reserved0
      out.writeS32(0); // Unused U32 reserved1
      out.writeS32(cores);

      if (cores != 0)
      {
         for (int i = 0; i < cores; i++)
         {
            MonitorTracepointBufferStatus status = perCoreStatus[i];

            out.writeS64(status.frames);
            out.writeS64(status.created);
            out.writeS64(status.size);
            out.writeS64(status.free);
            out.writeS32(status.bufferType);
            out.writeS32(0); // reserved0
         }
      }
   }
}
