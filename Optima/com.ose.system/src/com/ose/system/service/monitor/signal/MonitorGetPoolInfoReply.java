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
import com.ose.system.service.monitor.MonitorPoolFragmentInfo;
import com.ose.system.service.monitor.MonitorPoolBufferSizeInfo;

public class MonitorGetPoolInfoReply extends Signal
{
   public static final int SIG_NO = 39051;

   public int pid;   // U32
   public int sid;   // U32
   public int total; // U32
   public int free;  // U32
   public byte stackAlignment;        // U8
   public byte stackAdmSize;          // U8
   public byte stackInternalAdmSize;  // U8
   public byte signalAlignment;       // U8
   public byte signalAdmSize;         // U8
   public byte signalInternalAdmSize; // U8
   public MonitorPoolFragmentInfo[] fragments;     // MonitorPoolFragmentInfo[]
   public MonitorPoolBufferSizeInfo[] stackSizes;  // MonitorPoolBufferSizeInfo[]
   public MonitorPoolBufferSizeInfo[] signalSizes; // MonitorPoolBufferSizeInfo[]

   public MonitorGetPoolInfoReply()
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
      total = in.readS32();
      free = in.readS32();
      in.readS32(); // Unused U32 fragmentsOffset
      fragmentsCount = in.readS32();
      in.readS32(); // Unused U32 stackSizesOffset
      stackSizesCount = in.readS32();
      in.readS32(); // Unused U32 signalSizesOffset
      signalSizesCount = in.readS32();
      stackAlignment = in.readS8();
      stackAdmSize = in.readS8();
      stackInternalAdmSize = in.readS8();
      signalAlignment = in.readS8();
      signalAdmSize = in.readS8();
      signalInternalAdmSize = in.readS8();
      in.readS8(); // Unused U8 reserved0
      in.readS8(); // Unused U8 reserved1
      in.readS32(); // Unused U32 reserved2

      fragments = new MonitorPoolFragmentInfo[fragmentsCount];
      for (int i = 0; i < fragmentsCount; i++)
      {
         MonitorPoolFragmentInfo fragment = new MonitorPoolFragmentInfo();
         fragment.address = in.readS32();
         fragment.size = in.readS32();
         fragment.stacks = in.readS32();
         fragment.signals = in.readS32();
         fragment.reserved0 = in.readS32();
         fragment.reserved1 = in.readS32();
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
      out.writeS32(total);
      out.writeS32(free);
      out.writeS32(0); // U32 fragmentsOffset
      out.writeS32(fragmentsCount);
      out.writeS32(fragmentsCount); // U32 stackSizesOffset
      out.writeS32(stackSizesCount);
      out.writeS32(fragmentsCount + stackSizesCount); // U32 signalSizesOffset
      out.writeS32(signalSizesCount);
      out.writeS8(stackAlignment);
      out.writeS8(stackAdmSize);
      out.writeS8(stackInternalAdmSize);
      out.writeS8(signalAlignment);
      out.writeS8(signalAdmSize);
      out.writeS8(signalInternalAdmSize);
      out.writeS8((byte) 0); // Unused U8 reserved0
      out.writeS8((byte) 0); // Unused U8 reserved1
      out.writeS32(0); // Unused U32 reserved2

      for (int i = 0; i < fragmentsCount; i++)
      {
         MonitorPoolFragmentInfo fragment = fragments[i];
         out.writeS32(fragment.address);
         out.writeS32(fragment.size);
         out.writeS32(fragment.stacks);
         out.writeS32(fragment.signals);
         out.writeS32(fragment.reserved0);
         out.writeS32(fragment.reserved1);
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
