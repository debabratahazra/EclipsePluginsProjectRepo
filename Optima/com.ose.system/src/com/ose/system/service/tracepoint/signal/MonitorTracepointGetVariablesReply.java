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
import java.util.ArrayList;
import java.util.List;

import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;
import com.ose.system.service.tracepoint.MonitorTracepointBufferStatus;

public class MonitorTracepointGetVariablesReply extends Signal
{

   public static final int SIG_NO = 40748;

   public int variableStatus; // U32
   public int id; // U32
   public int padding; // U32
   public long initialValue; // S64

   public MonitorTracepointGetVariablesReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      variableStatus = in.readS32();
      id = in.readS32();
      padding = in.readS32();
      initialValue = in.readS64();
      in.readS32(); // Unused U32 reserved0
      in.readS32(); // Unused U32 reserved1
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(variableStatus);
      out.writeS32(id);
      out.writeS32(padding);
      out.writeS64(initialValue);
      out.writeS32(0); // Unused U32 reserved0
      out.writeS32(0); // Unused U32 reserved1

   }

}