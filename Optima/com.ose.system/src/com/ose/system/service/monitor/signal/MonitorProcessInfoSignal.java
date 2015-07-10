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
import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;

public abstract class MonitorProcessInfoSignal extends Signal
{
   public int pid;        // U32
   public int bid;        // U32
   public int sid;        // U32
   public int type;       // U32
   public int entrypoint; // U32
   public int properties; // U32
   public int reserved0;  // U32 The 32 most significant bits of the entrypoint in 64 bit systems (or 0).
   public String name;    // char[]

   public MonitorProcessInfoSignal(int sigNo)
   {
      super(sigNo);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      pid = in.readS32();
      bid = in.readS32();
      sid = in.readS32();
      type = in.readS32();
      entrypoint = in.readS32();
      properties = in.readS32();
      reserved0 = in.readS32(); 
      name = in.readString();
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(pid);
      out.writeS32(bid);
      out.writeS32(sid);
      out.writeS32(type);
      out.writeS32(entrypoint);
      out.writeS32(properties);
      out.writeS32(reserved0); 
      out.writeString((name != null) ? name : "");
   }
}
