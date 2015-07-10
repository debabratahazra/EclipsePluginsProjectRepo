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

public abstract class PmProgramInfoReplySignal extends Signal
{

   public int status;            // U32
   public String install_handle; // char[32]
   public int progpid;           // U32
   public int domain;            // U32
   public int main_block;        // U32
   public int main_process;      // U32
   public long pool_base;        // U64
   public long pool_size;        // U64
   public int uid;               // U32
   public int state;             // U32
   // Only available in OSE 5.2 and later:
   public boolean extended_info; // Pseudo
   public int segpid;            // U32
   public int stk_poolid;        // U32
   public int sig_poolid;        // U32
   public long sig_pool_base;    // U64
   public long sig_pool_size;    // U64
   public int heap;              // U32

   public PmProgramInfoReplySignal(int sigNo)
   {
      super(sigNo);
   }

   protected abstract void read(SignalInputStream in) throws IOException;

   protected abstract void write(SignalOutputStream out) throws IOException;
}
