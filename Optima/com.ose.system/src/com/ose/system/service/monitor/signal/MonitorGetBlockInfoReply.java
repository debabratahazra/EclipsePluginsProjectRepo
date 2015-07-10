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

public class MonitorGetBlockInfoReply extends Signal
{
   public static final int SIG_NO = 39015;

   public int bid;             // U32
   public int sid;             // U32
   public int uid;             // U32
   public boolean supervisor;  // U32
   public int signalsAttached; // U32
   public int errorHandler;    // U32
   public int maxSigSize;      // U32
   public int sigPoolId;       // U32
   public int stackPoolId;     // U32
   public short euId;          // U16
   public String name;         // char[]

   public MonitorGetBlockInfoReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      bid = in.readS32();
      sid = in.readS32();
      uid = in.readS32();
      supervisor = in.readBoolean();
      signalsAttached = in.readS32();
      errorHandler = in.readS32();
      maxSigSize = in.readS32();
      sigPoolId = in.readS32();
      stackPoolId = in.readS32();
      euId = in.readS16();
      in.readS16(); // Unused U16 reserved0
      in.readS32(); // Unused U32 reserved1
      name = in.readString();
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(bid);
      out.writeS32(sid);
      out.writeS32(uid);
      out.writeBoolean(supervisor);
      out.writeS32(signalsAttached);
      out.writeS32(errorHandler);
      out.writeS32(maxSigSize);
      out.writeS32(sigPoolId);
      out.writeS32(stackPoolId);
      out.writeS16(euId);
      out.writeS16((short) 0); // Unused U16 reserved0
      out.writeS32(0); // Unused U32 reserved1
      out.writeString(name);
   }
}
