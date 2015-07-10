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

package com.ose.system.service.pm.signal;

import java.io.IOException;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;

public class PmInterfaceReply extends Signal
{
   public static final int SIG_NO = 36201;

   public int status;     // U32
   public String whatStr; // char[32]
   public int biosHandle; // U32
   public int[] sigs;     // U32[]

   public PmInterfaceReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int sigsCount;

      status = in.readS32();
      whatStr = in.readString(32);
      biosHandle = in.readS32();
      sigsCount = in.readS32();
      sigs = in.readS32Array(sigsCount);
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(status);
      out.writeString(whatStr, 32);
      out.writeS32(biosHandle);
      out.writeS32(sigs.length);
      out.writeS32Array(sigs);
   }
}
