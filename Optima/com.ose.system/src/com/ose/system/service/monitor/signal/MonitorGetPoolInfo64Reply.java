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
import com.ose.system.service.monitor.MonitorPoolBufferSizeInfo;
import com.ose.system.service.monitor.MonitorPoolFragmentInfo64;

public class MonitorGetPoolInfo64Reply extends Signal
{
   public static final int SIG_NO = 40824;

   public int pid;   // U32
   public int sid;   // U32
   public long total; // U64
   public long free;  // U64
   public short stackAlignment;        // U16
   public short stackAdmSize;          // U16
   public short stackInternalAdmSize;  // U16
   public short signalAlignment;       // U16
   public short signalAdmSize;         // U16
   public short signalInternalAdmSize; // U16
   public MonitorPoolFragmentInfo64[] fragments;     // MonitorPoolFragmentInfo[]
   public MonitorPoolBufferSizeInfo[] stackSizes;  // MonitorPoolBufferSizeInfo[]
   public MonitorPoolBufferSizeInfo[] signalSizes; // MonitorPoolBufferSizeInfo[]

   public MonitorGetPoolInfo64Reply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int fragmentsCount;   // U32
      int stackSizesCount;  // U32
      int signalSizesCount; // U32

      pid = in.readS32();
      sid = in.readS32();
      in.readS32(); // Unused U32 reserved0
      total = in.readS64();
      free = in.readS64();
      in.readS32(); // Unused U32 fragmentsOffset
      fragmentsCount = in.readS32();
      in.readS32(); // Unused U32 stackSizesOffset
      stackSizesCount = in.readS32();
      in.readS32(); // Unused U32 signalSizesOffset
      signalSizesCount = in.readS32();
      stackAlignment = in.readS16();
      stackAdmSize = in.readS16();
      stackInternalAdmSize = in.readS16();
      signalAlignment = in.readS16();
      signalAdmSize = in.readS16();
      signalInternalAdmSize = in.readS16();
      in.readS32(); // Unused U32 reserved1
      in.readS64(); // Unused U64 reserved2
      in.readS64(); // Unused U64 reserved3
      in.readS64(); // Unused U64 reserved4

      fragments = new MonitorPoolFragmentInfo64[fragmentsCount];
      for (int i = 0; i < fragmentsCount; i++)
      {
         MonitorPoolFragmentInfo64 fragment = new MonitorPoolFragmentInfo64();
         fragment.address = in.readS64();
         fragment.size = in.readS64();
         fragment.stacks = in.readS64();
         fragment.signals = in.readS64();
         fragment.reserved0 = in.readS64();
         fragment.reserved1 = in.readS64();
         fragments[i] = fragment;
      }

      stackSizes = new MonitorPoolBufferSizeInfo[stackSizesCount];
      for (int i = 0; i < stackSizesCount; i++)
      {
         MonitorPoolBufferSizeInfo stackSize = new MonitorPoolBufferSizeInfo();
         stackSize.size = in.readS32();
         stackSize.allocated = in.readS32();
         stackSize.free = in.readS32();
         stackSize.reserved0 = in.readS32();
         stackSizes[i] = stackSize;
      }

      signalSizes = new MonitorPoolBufferSizeInfo[signalSizesCount];
      for (int i = 0; i < signalSizesCount; i++)
      {
         MonitorPoolBufferSizeInfo signalSize = new MonitorPoolBufferSizeInfo();
         signalSize.size = in.readS32();
         signalSize.allocated = in.readS32();
         signalSize.free = in.readS32();
         signalSize.reserved0 = in.readS32();
         signalSizes[i] = signalSize;
      }
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      int fragmentsCount;   // U32
      int stackSizesCount;  // U32
      int signalSizesCount; // U32

      fragmentsCount = ((fragments != null) ? fragments.length : 0);
      stackSizesCount = ((stackSizes != null) ? stackSizes.length : 0);
      signalSizesCount = ((signalSizes != null) ? signalSizes.length : 0);

      out.writeS32(pid);
      out.writeS32(sid);
      out.writeS32(0); // Unused U32 reserved0
      out.writeS64(total);
      out.writeS64(free);
      out.writeS32(0); // U32 fragmentsOffset
      out.writeS32(fragmentsCount);
      out.writeS32(fragmentsCount); // U32 stackSizesOffset
      out.writeS32(stackSizesCount);
      out.writeS32(fragmentsCount + stackSizesCount); // U32 signalSizesOffset
      out.writeS32(signalSizesCount);
      out.writeS16(stackAlignment);
      out.writeS16(stackAdmSize);
      out.writeS16(stackInternalAdmSize);
      out.writeS16(signalAlignment);
      out.writeS16(signalAdmSize);
      out.writeS16(signalInternalAdmSize);
      out.writeS32(0); // Unused U32 reserved1
      out.writeS64(0); // Unused U64 reserved2
      out.writeS64(0); // Unused U64 reserved3
      out.writeS64(0); // Unused U64 reserved4

      for (int i = 0; i < fragmentsCount; i++)
      {
         MonitorPoolFragmentInfo64 fragment = fragments[i];
         out.writeS64(fragment.address);
         out.writeS64(fragment.size);
         out.writeS64(fragment.stacks);
         out.writeS64(fragment.signals);
         out.writeS64(fragment.reserved0);
         out.writeS64(fragment.reserved1);
      }

      for (int i = 0; i < stackSizesCount; i++)
      {
         MonitorPoolBufferSizeInfo stackSize = stackSizes[i];
         out.writeS32(stackSize.size);
         out.writeS32(stackSize.allocated);
         out.writeS32(stackSize.free);
         out.writeS32(stackSize.reserved0);
      }

      for (int i = 0; i < signalSizesCount; i++)
      {
         MonitorPoolBufferSizeInfo signalSize = signalSizes[i];
         out.writeS32(signalSize.size);
         out.writeS32(signalSize.allocated);
         out.writeS32(signalSize.free);
         out.writeS32(signalSize.reserved0);
      }
   }
}
