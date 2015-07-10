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

public class PmCreateProgramRequest extends Signal
{
   public static final int SIG_NO = 36222;

   public int domain;            // U32
   public String install_handle; // char[32]
   public String[] conf;         // U32, U32, char[] ("<name>=<value>\0...")

   public PmCreateProgramRequest()
   {
      super(SIG_NO);
   }

   public PmCreateProgramRequest(int domain, String install_handle, String[] conf)
   {
      super(SIG_NO);
      this.domain = domain;
      this.install_handle = install_handle;
      this.conf = conf;
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int conf_no_of_strings; // U32
      int conf_size; // U32

      domain = in.readS32();
      install_handle = in.readString(32);
      conf_no_of_strings = in.readS32();
      conf_size = in.readS32();
      if (conf_size > 0)
      {
         conf = new String[conf_no_of_strings];
         for (int i = 0; i < conf.length; i++)
         {
            conf[i] = in.readString();
         }
      }
      else
      {
         conf = null;
      }
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(domain);
      out.writeString(install_handle, 32);
      if (conf == null)
      {
         out.writeS32(0); // U32 conf_no_of_strings
         out.writeS32(0); // U32 conf_size
         out.writeS32(0); // char[] conf
      }
      else
      {
         int size = 0;

         out.writeS32(conf.length);
         for (int i = 0; i < conf.length; i++)
         {
            size += conf[i].length() + 1;
         }
         out.writeS32(size);
         for (int i = 0; i < conf.length; i++)
         {
            out.writeString(conf[i]);
         }
         if (conf.length == 0)
         {
            // Kludge for handling an over pedantic sizeof check in PM.
            out.writeS32(0);
         }
      }
   }
}
