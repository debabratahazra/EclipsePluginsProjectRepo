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

public class PmUninstallLoadModuleRequest extends Signal
{
   public static final int SIG_NO = 36212;

   public String install_handle; // char[32]

   public PmUninstallLoadModuleRequest()
   {
      super(SIG_NO);
   }

   public PmUninstallLoadModuleRequest(String install_handle)
   {
      super(SIG_NO);
      this.install_handle = install_handle;
   }

   protected void read(SignalInputStream in) throws IOException
   {
      in.readS32(); // Unused U32 status
      install_handle = in.readString(32);
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(0); // Unused U32 status
      out.writeString(install_handle, 32);
   }
}
