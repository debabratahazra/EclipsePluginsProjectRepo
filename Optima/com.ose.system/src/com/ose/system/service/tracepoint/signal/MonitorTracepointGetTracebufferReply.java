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

public class MonitorTracepointGetTracebufferReply extends Signal
{

   public static final int SIG_NO = 40766;

   public int snapshotId; // U32
   public int pid; // U32
   public int core; // U32
   public long timeStamp; // U64
   public int errorCode; // U32
   public int eCodeExtra; // U32
   public int sigLeft; // U32
   public int eCodeExtraHi;// U32
   public int tracepointId;// U32
   public int dataLength; // U32
   public byte[] traceData;// U8[]

   public MonitorTracepointGetTracebufferReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      snapshotId = in.readS32();
      pid = in.readS32();
      core = in.readS32();
      timeStamp = in.readS64();
      errorCode = in.readS32();
      eCodeExtra = in.readS32();
      sigLeft = in.readS32();
      eCodeExtraHi = in.readS32();
      in.readS16(); // Unused U16 reserved0
      in.readS16(); // Unused U16 reserved1
      tracepointId = in.readS32();
      dataLength = in.readS32();
      traceData = in.readS8Array(dataLength);
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(snapshotId);
      out.writeS32(pid);
      out.writeS32(core);
      out.writeS64(timeStamp);
      out.writeS32(errorCode);
      out.writeS32(eCodeExtra);
      out.writeS32(sigLeft);
      out.writeS32(eCodeExtraHi);
      out.writeS16((short) 0); // Unused U16 reserved0
      out.writeS16((short) 0); // Unused U16 reserved1
      out.writeS32(tracepointId);
      out.writeS32(dataLength);
      out.writeS8Array(traceData);
   }

}