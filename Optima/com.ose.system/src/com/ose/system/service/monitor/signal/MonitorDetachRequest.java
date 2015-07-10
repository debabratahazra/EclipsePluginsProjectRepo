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

public class MonitorDetachRequest extends Signal
{
   public static final int SIG_NO = 39111;

   public boolean resume; // U32

   public MonitorDetachRequest()
   {
      super(SIG_NO);
   }

   public MonitorDetachRequest(boolean resume)
   {
      super(SIG_NO);
      this.resume = resume;
   }

   protected void read(SignalInputStream in) throws IOException
   {
      resume = in.readBoolean();
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeBoolean(resume);
   }
}
