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

public class MonitorConnectReply extends Signal
{
   public static final int SIG_NO = 39009;

   public int status;         // U32
   public int tickLength;     // U32
   public int tick;           // U32
   public int ntick;          // U32
   public int ntickFrequency; // U32
   public int cpuClass;       // U32
   public int cpuType;        // U32
   public int errorHandler;   // U32
   public short euCount;      // U16
   public int featuresCount;  // U32
   public short[] features;   // U16[]

   public MonitorConnectReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      status = in.readS32();
      tickLength = in.readS32();
      tick = in.readS32();
      ntick = in.readS32();
      ntickFrequency = in.readS32();
      cpuClass = in.readS32();
      cpuType = in.readS32();
      errorHandler = in.readS32();
      euCount = in.readS16();
      in.readS16(); // Unused U16 reserved0
      in.readS32(); // Unused U32 reserved1
      in.readS32(); // Unused U32 reserved2
      in.readS32(); // Unused U32 reserved3
      in.readS32(); // Unused U32 reserved4
      in.readS32(); // Unused U32 reserved5
      in.readS32(); // Unused U32 reserved6
      in.readS32(); // Unused U32 reserved7
      featuresCount = in.readS32();
      features = in.readS16Array(featuresCount);
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(status);
      out.writeS32(tickLength);
      out.writeS32(tick);
      out.writeS32(ntick);
      out.writeS32(ntickFrequency);
      out.writeS32(cpuClass);
      out.writeS32(cpuType);
      out.writeS32(errorHandler);
      out.writeS16(euCount);
      out.writeS16((short) 0); // Unused U16 reserved0
      out.writeS32(0); // Unused U32 reserved1
      out.writeS32(0); // Unused U32 reserved2
      out.writeS32(0); // Unused U32 reserved3
      out.writeS32(0); // Unused U32 reserved4
      out.writeS32(0); // Unused U32 reserved5
      out.writeS32(0); // Unused U32 reserved6
      out.writeS32(0); // Unused U32 reserved7
      out.writeS32(featuresCount);
      out.writeS16Array(features);
   }
}
