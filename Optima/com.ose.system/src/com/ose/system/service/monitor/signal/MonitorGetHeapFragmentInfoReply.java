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
import com.ose.system.service.monitor.MonitorHeapFragmentInfo;

public class MonitorGetHeapFragmentInfoReply extends Signal
{
   public static final int SIG_NO = 39144;

   public int id; // U32
   public MonitorHeapFragmentInfo[] fragments; // MonitorHeapFragmentInfo[]

   public MonitorGetHeapFragmentInfoReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int fragmentsCount; // U32

      id = in.readS32();
      in.readS32(); // Unused U32 reserved0
      fragmentsCount = in.readS32();

      fragments = new MonitorHeapFragmentInfo[fragmentsCount];
      for (int i = 0; i < fragmentsCount; i++)
      {
         MonitorHeapFragmentInfo fragment = new MonitorHeapFragmentInfo();
         fragment.address = in.readS32();
         fragment.size = in.readS32();
         fragment.usedSize = in.readS32();
         fragment.reqSize = in.readS32();
         fragment.reserved0 = in.readS32();
         fragment.reserved1 = in.readS32();
         fragments[i] = fragment;
      }
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      int fragmentsCount; // U32

      fragmentsCount = ((fragments != null) ? fragments.length : 0);

      out.writeS32(id);
      out.writeS32(0); // Unused U32 reserved0
      out.writeS32(fragmentsCount);

      for (int i = 0; i < fragmentsCount; i++)
      {
         MonitorHeapFragmentInfo fragment = fragments[i];
         out.writeS32(fragment.address);
         out.writeS32(fragment.size);
         out.writeS32(fragment.usedSize);
         out.writeS32(fragment.reqSize);
         out.writeS32(fragment.reserved0);
         out.writeS32(fragment.reserved1);
      }
   }
}
