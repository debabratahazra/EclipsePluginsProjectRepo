/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2007 by Enea Software AB.
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
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;

public class MonitorGetEventActionpointsReply
   extends MonitorEventActionpointInfoSignal
{
   public static final int SIG_NO = 39171;

   public int type; // U32

   public MonitorGetEventActionpointsReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      in.readS32(); // Unused U32 reserved0
      type = in.readS32();
      super.read(in);
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(0); // Unused U32 reserved0
      out.writeS32(type);
      super.write(out);
   }
}
