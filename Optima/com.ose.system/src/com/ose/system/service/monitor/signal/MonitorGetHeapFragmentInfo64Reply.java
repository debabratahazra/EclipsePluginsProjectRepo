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
import com.ose.system.service.monitor.MonitorHeapFragmentInfo64;

public class MonitorGetHeapFragmentInfo64Reply extends Signal
{
   public static final int SIG_NO = 40828;

   public int id; // U32
   public MonitorHeapFragmentInfo64[] fragments; // MonitorHeapFragmentInfo[]

   public MonitorGetHeapFragmentInfo64Reply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int fragmentsCount; // U32

      id = in.readS32();
      in.readS32(); // Unused U32 reserved0
      fragmentsCount = in.readS32();

      fragments = new MonitorHeapFragmentInfo64[fragmentsCount];
      for (int i = 0; i < fragmentsCount; i++)
      {
         MonitorHeapFragmentInfo64 fragment = new MonitorHeapFragmentInfo64();
         fragment.address = in.readS64();
         fragment.size = in.readS64();
         fragment.usedSize = in.readS64();
         fragment.reqSize = in.readS64();
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
         MonitorHeapFragmentInfo64 fragment = fragments[i];
         out.writeS64(fragment.address);
         out.writeS64(fragment.size);
         out.writeS64(fragment.usedSize);
         out.writeS64(fragment.reqSize);
         out.writeS32(fragment.reserved0);
         out.writeS32(fragment.reserved1);
      }
   }
}
