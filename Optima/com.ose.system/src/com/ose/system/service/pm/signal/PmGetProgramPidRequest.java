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

public class PmGetProgramPidRequest extends Signal
{
   public static final int SIG_NO = 36240;

   public int pid; // U32

   public PmGetProgramPidRequest()
   {
      super(SIG_NO);
   }

   public PmGetProgramPidRequest(int pid)
   {
      super(SIG_NO);
      this.pid = pid;
   }

   protected void read(SignalInputStream in) throws IOException
   {
      in.readS32(); // Unused U32 status
      pid = in.readS32();
      in.readS32(); // Unused U32 progpid
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(0); // Unused U32 status
      out.writeS32(pid);
      out.writeS32(0); // Unused U32 progpid
   }
}
