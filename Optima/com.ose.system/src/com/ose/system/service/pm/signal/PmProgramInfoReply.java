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
package com.ose.system.service.pm.signal;

import java.io.IOException;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;

public class PmProgramInfoReply extends PmProgramInfoReplySignal
{
   public static final int SIG_NO = 36235;
   
   public PmProgramInfoReply()
   {
      super(SIG_NO);
   }
   
   protected void read(SignalInputStream in) throws IOException
   {
      status = in.readS32();
      install_handle = in.readString(32);
      progpid = in.readS32();
      domain = in.readS32();
      main_block = in.readS32();
      main_process = in.readS32();
      pool_base = in.readS32();
      pool_size = in.readS32();
      uid = in.readS32();
      state = in.readS32();
      // Only available in OSE 5.2 and later:
      if (getSigSize() > 72)
      {
         extended_info = true;
         segpid = in.readS32();
         stk_poolid = in.readS32();
         sig_poolid = in.readS32();
         sig_pool_base = in.readS32();
         sig_pool_size = in.readS32();
         heap = in.readS32();
      }
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(status);
      out.writeString(install_handle, 32);
      out.writeS32(progpid);
      out.writeS32(domain);
      out.writeS32(main_block);
      out.writeS32(main_process);
      out.writeS32(PmUtils.longToInt(pool_base));
      out.writeS32(PmUtils.longToInt(pool_size));
      out.writeS32(uid);
      out.writeS32(state);
      // Only available in OSE 5.2 and later:
      if (extended_info)
      {
         out.writeS32(segpid);
         out.writeS32(stk_poolid);
         out.writeS32(sig_poolid);
         out.writeS32(PmUtils.longToInt(sig_pool_base));
         out.writeS32(PmUtils.longToInt(sig_pool_size));
         out.writeS32(heap);
      }
   }
}
