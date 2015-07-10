/* COPYRIGHT-ENEA-SRC-R2 *
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

public class MonitorSetSysParamRequest extends Signal
{
   public static final int SIG_NO = 39091;

   public boolean reconfigure; // U8
   public String name;         // char[]
   public String value;        // char[]

   public MonitorSetSysParamRequest()
   {
      super(SIG_NO);
   }

   public MonitorSetSysParamRequest(boolean reconfigure, String name, String value)
   {
      super(SIG_NO);
      this.reconfigure = reconfigure;
      this.name = name;
      this.value = value;
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int valueOffset; // U32

      reconfigure = in.readOSBOOLEAN();
      in.readS8();  // Unused U8 reserved0
      in.readS16(); // Unused U16 reserved1
      in.readS32(); // Unused U32 reserved2
      in.readS32(); // Unused U32 reserved3
      in.readS32(); // Unused U32 nameOffset
      valueOffset = in.readS32();
      name = in.readString();
      value = (((valueOffset > 0) && (valueOffset != ~0)) ?
               in.readString() : null);
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeOSBOOLEAN(reconfigure);
      out.writeS8((byte) 0);   // Unused U8 reserved0
      out.writeS16((short) 0); // Unused U16 reserved1
      out.writeS32(0);         // Unused U32 reserved2
      out.writeS32(0);         // Unused U32 reserved3
      if (value != null)
      {
         out.writeS32(0); // U32 nameOffset
         out.writeS32(name.length() + 1); // U32 valueOffset
         out.writeString(name);
         out.writeString(value);
      }
      else
      {
         out.writeS32(0); // U32 nameOffset
         out.writeS32(~0); // U32 valueOffset
         out.writeString(name);
      }
   }
}
