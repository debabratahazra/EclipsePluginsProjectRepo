/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2008 by Enea Software AB.
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

package com.ose.system.service.tracepoint.signal;

import java.io.IOException;

import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;

public class MonitorTracepointGetAnnotationsRequest extends Signal
{

   public static final int SIG_NO = 40771;

   public int tracepointId; // U32
   public int scopeType; //U32
   public int scopeId; //U32

   public MonitorTracepointGetAnnotationsRequest()
   {
      super(SIG_NO);
   }

   @Override
   protected void read(SignalInputStream in) throws IOException
   {
      tracepointId = in.readS32();
      scopeType = in.readS32();
      scopeId = in.readS32();
      in.readS32(); // Unused U32 reserved0
      in.readS32(); // Unused U32 reserved1
   }

   @Override
   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(tracepointId);
      out.writeS32(scopeType);
      out.writeS32(scopeId);
      out.writeS32(0); // Unused U32 reserved0
      out.writeS32(0); // Unused U32 reserved1

   }

}
