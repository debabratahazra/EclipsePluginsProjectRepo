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

public abstract class PmProgramInfoRequestSignal extends Signal
{
   protected PmProgramInfoRequestSignal(int sigNo)
   {
      super(sigNo);
   }

   public int progpid; // U32

   protected void read(SignalInputStream in) throws IOException
   {
      in.readS32(); // Unused U32 status
      in.readString(32); // Unused char[32] install_handle
      progpid = in.readS32();
      in.readS32(); // Unused U32 domain
      in.readS32(); // Unused U32 main_block
      in.readS32(); // Unused U32 main_process
      in.readS32(); // Unused U32 pool_base
      in.readS32(); // Unused U32 pool_size
      in.readS32(); // Unused U32 uid
      in.readS32(); // Unused U32 state
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(0); // Unused U32 status
      out.writeString("dummy", 32); // Unused char[32] install_handle
      out.writeS32(progpid);
      out.writeS32(0); // Unused U32 domain
      out.writeS32(0); // Unused U32 main_block
      out.writeS32(0); // Unused U32 main_process
      out.writeS32(0); // Unused U32 pool_base
      out.writeS32(0); // Unused U32 pool_size
      out.writeS32(0); // Unused U32 uid
      out.writeS32(0); // Unused U32 state
   }
}
