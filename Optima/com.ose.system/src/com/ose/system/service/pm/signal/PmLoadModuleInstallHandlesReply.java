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

public class PmLoadModuleInstallHandlesReply extends Signal
{
   public static final int SIG_NO = 36215;

   public int status;               // U32
   public String[] install_handles; // char[][]

   public PmLoadModuleInstallHandlesReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int install_handles_no_of_strings; // U32

      status = in.readS32();
      install_handles_no_of_strings = in.readS32();
      in.readS32(); // Unused U32 install_handles_size
      install_handles = new String[install_handles_no_of_strings];
      for (int i = 0; i < install_handles_no_of_strings; i++)
      {
         install_handles[i] = in.readString();
      }
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      int install_handles_no_of_strings; // U32
      int install_handles_size = 0; // U32

      install_handles_no_of_strings =
         ((install_handles != null) ? install_handles.length : 0);
      out.writeS32(status);
      out.writeS32(install_handles_no_of_strings);
      for (int i = 0; i < install_handles_no_of_strings; i++)
      {
         install_handles_size += install_handles[i].length() + 1;
      }
      out.writeS32(install_handles_size);
      for (int i = 0; i < install_handles_no_of_strings; i++)
      {
         out.writeString(install_handles[i]);
      }
   }
}
