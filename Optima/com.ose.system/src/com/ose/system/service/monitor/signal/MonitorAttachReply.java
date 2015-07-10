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

public class MonitorAttachReply extends Signal
{
   public static final int SIG_NO = 39110;

   public int status;             // U32
   public int processCacheCount;  // U32
   // Only available in OSE 5.3 and later:
   public boolean extendedInfo;   // Pseudo
   public int[] notifySignals;    // U32[]
   public int[] interceptSignals; // U32[]
   public int[] traceSignals;     // U32[]

   public MonitorAttachReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      status = in.readS32();
      processCacheCount = in.readS32();
      // Only available in OSE 5.3 and later:
      if (getSigSize() > 12)
      {
         int notifyOffset;    // U32
         int notifyCount;     // U32
         int interceptOffset; // U32
         int interceptCount;  // U32
         int traceOffset;     // U32
         int traceCount;      // U32

         in.readS32(); // Unused U32 reserved0
         in.readS32(); // Unused U32 reserved1
         in.readS32(); // Unused U32 reserved2
         notifyOffset = in.readS32();
         notifyCount = in.readS32();
         interceptOffset = in.readS32();
         interceptCount = in.readS32();
         traceOffset = in.readS32();
         traceCount = in.readS32();
         in.skipBytes(4 * notifyOffset);
         notifySignals = in.readS32Array(notifyCount);
         in.skipBytes(4 * (interceptOffset - notifyOffset - notifyCount));
         interceptSignals = in.readS32Array(interceptCount);
         in.skipBytes(4 * (traceOffset - interceptOffset - interceptCount));
         traceSignals = in.readS32Array(traceCount);
      }
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(status);
      out.writeS32(processCacheCount);
      // Only available in OSE 5.3 and later:
      if (extendedInfo)
      {
         int notifyOffset;    // U32
         int notifyCount;     // U32
         int interceptOffset; // U32
         int interceptCount;  // U32
         int traceOffset;     // U32
         int traceCount;      // U32

         out.writeS32(0); // Unused U32 reserved0
         out.writeS32(0); // Unused U32 reserved1
         out.writeS32(0); // Unused U32 reserved2
         notifyOffset = 0;
         notifyCount = ((notifySignals != null) ? notifySignals.length : 0);
         interceptOffset = notifyCount;
         interceptCount = ((interceptSignals != null) ? interceptSignals.length : 0);
         traceOffset = interceptOffset + interceptCount;
         traceCount = ((traceSignals != null) ? traceSignals.length : 0);
         out.writeS32(notifyOffset);
         out.writeS32(notifyCount);
         out.writeS32(interceptOffset);
         out.writeS32(interceptCount);
         out.writeS32(traceOffset);
         out.writeS32(traceCount);
         if (notifyCount > 0)
         {
            out.writeS32Array(notifySignals);
         }
         if (interceptCount > 0)
         {
            out.writeS32Array(interceptSignals);
         }
         if (traceCount > 0)
         {
            out.writeS32Array(traceSignals);
         }
      }
   }
}
