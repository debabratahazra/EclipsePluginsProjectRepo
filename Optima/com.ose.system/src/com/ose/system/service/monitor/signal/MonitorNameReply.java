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

public class MonitorNameReply extends Signal
{
   public static final int SIG_NO = 39007;

   public String systemName;  // char[]
   public String monitorName; // char[]

   public MonitorNameReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      in.readS32(); // Unused U32 reserved0
      in.readS32(); // Unused U32 reserved1
      in.readS32(); // Unused U32 reserved2
      in.readS32(); // Unused U32 systemNameOffset
      in.readS32(); // Unused U32 monitorNameOffset
      systemName = in.readString();
      monitorName = in.readString();
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(0); // Unused U32 reserved0
      out.writeS32(0); // Unused U32 reserved1
      out.writeS32(0); // Unused U32 reserved2
      out.writeS32(0); // U32 systemNameOffset
      out.writeS32(systemName.length() + 1); // U32 monitorNameOffset
      out.writeString(systemName);
      out.writeString(monitorName);
   }
}
