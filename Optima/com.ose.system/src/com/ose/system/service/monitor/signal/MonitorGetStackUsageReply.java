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
import com.ose.system.service.monitor.MonitorStackInfo;

public class MonitorGetStackUsageReply extends Signal
{
   public static final int SIG_NO = 39026;

   public int pid;                     // U32
   public MonitorStackInfo main;       // MonitorStackInfo
   public MonitorStackInfo supervisor; // MonitorStackInfo

   public MonitorGetStackUsageReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      pid = in.readS32();
      main = new MonitorStackInfo();
      main.address = in.readS32();
      main.size = in.readS32();
      main.used = in.readS32();
      main.bufsize = in.readS32();
      main.reserved1 = in.readS32();
      main.reserved2 = in.readS32();
      supervisor = new MonitorStackInfo();
      supervisor.address = in.readS32();
      supervisor.size = in.readS32();
      supervisor.used = in.readS32();
      supervisor.bufsize = in.readS32();
      supervisor.reserved1 = in.readS32();
      supervisor.reserved2 = in.readS32();
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(pid);
      out.writeS32(main.address);
      out.writeS32(main.size);
      out.writeS32(main.used);
      out.writeS32(main.bufsize);
      out.writeS32(main.reserved1);
      out.writeS32(main.reserved2);
      out.writeS32(supervisor.address);
      out.writeS32(supervisor.size);
      out.writeS32(supervisor.used);
      out.writeS32(supervisor.bufsize);
      out.writeS32(supervisor.reserved1);
      out.writeS32(supervisor.reserved2);
   }
}
