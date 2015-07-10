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

public abstract class PmLoadModuleInfoReplySignal extends Signal
{
   public int status;            // U32
   public String install_handle; // char[32]
   public long entrypoint;       // U64
   public int options;           // U32
   public long text_base;        // U64
   public long text_size;        // U64
   public long data_base;        // U64
   public long data_size;        // U64
   public int no_of_sections;    // U32
   public int no_of_instances;   // U32
   public String file_format;    // char[32]
   public String file_name;      // char[256]

   protected PmLoadModuleInfoReplySignal(int sigNo)
   {
      super(sigNo);
   }

   protected abstract void read(SignalInputStream in) throws IOException;
   
   protected abstract void write(SignalOutputStream out) throws IOException;
}
