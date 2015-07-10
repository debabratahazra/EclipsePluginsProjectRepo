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

public class MonitorSetEnvVarRequest extends Signal
{
   public static final int SIG_NO = 39083;

   public int pid;      // U32
   public int pointer;  // U32
   public String name;  // char[]
   public String value; // char[]

   public MonitorSetEnvVarRequest()
   {
      super(SIG_NO);
   }

   public MonitorSetEnvVarRequest(int pid, String name, String value)
   {
      super(SIG_NO);
      this.pid = pid;
      this.name = name;
      this.value = value;
      this.pointer = 0;
   }

   public MonitorSetEnvVarRequest(int pid, String name, int value)
   {
      super(SIG_NO);
      this.pid = pid;
      this.name = name;
      this.value = null;
      this.pointer = value;
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int valueOffset; // U32

      pid = in.readS32();
      pointer = in.readS32();
      in.readS32(); // Unused U32 nameOffset
      valueOffset = in.readS32();
      name = in.readString();
      value = (((valueOffset > 0) && (valueOffset != ~0)) ?
               in.readString() : null);
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(pid);
      if (value != null)
      {
         out.writeS32(0); // U32 pointer
         out.writeS32(0); // U32 nameOffset
         out.writeS32(name.length() + 1); // U32 valueOffset
         out.writeString(name);
         out.writeString(value);
      }
      else
      {
         out.writeS32(pointer);
         out.writeS32(0); // U32 nameOffset
         out.writeS32(~0); // U32 valueOffset
         out.writeString(name);
      }
   }
}
