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

public class MonitorTracepointGetTracepointsReply extends Signal
{

   public static final int SIG_NO = 40742;

   public int id; // U32
   public long address; // U64
   public int scopeType; // U32
   public int scopeId; // U32
   public int enabled; // U32
   public int passCount; // U32
   public int tracepointType; // U32
   public int byteCodeLength; // U32
   public byte[] byteCode; // U8[]

   public MonitorTracepointGetTracepointsReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      id = in.readS32();
      address = in.readS64();
      scopeType = in.readS32();
      scopeId = in.readS32();
      enabled = in.readS32();
      passCount = in.readS32();
      tracepointType = in.readS32();
      in.readS32(); // Unused U32 reserved0
      in.readS32(); // Unused U32 reserved1
      in.readS32(); // Unused U32 reserved2
      byteCodeLength = in.readS32();
      byteCode = in.readS8Array(byteCodeLength);
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(id);
      out.writeS64(address);
      out.writeS32(scopeType);
      out.writeS32(scopeId);
      out.writeS32(enabled);
      out.writeS32(passCount);
      out.writeS32(tracepointType);
      out.writeS32(0); // Unused U32 reserved0
      out.writeS32(0); // Unused U32 reserved1
      out.writeS32(0); // Unused U32 reserved2
      out.writeS32(byteCodeLength);
      out.writeS8Array(byteCode);
   }

}