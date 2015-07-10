/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2007 by Enea Software AB.
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

public class MonitorGetTraceMultipleRequest extends Signal
{
   public static final int SIG_NO = 39211;

   public int timeout; // U32
   public int handle;  // U32

   public MonitorGetTraceMultipleRequest()
   {
      super(SIG_NO);
   }

   public MonitorGetTraceMultipleRequest(int timeout, int handle)
   {
      super(SIG_NO);
      this.timeout = timeout;
      this.handle = handle;
   }

   protected void read(SignalInputStream in) throws IOException
   {
      timeout = in.readS32();
      handle = in.readS32();
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(timeout);
      out.writeS32(handle);
   }
}
