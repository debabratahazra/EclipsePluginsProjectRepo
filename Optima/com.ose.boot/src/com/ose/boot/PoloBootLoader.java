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

package com.ose.boot;

import java.io.IOException;

/**
 * This class supports booting of binaries using the OSE POLO boot loader and
 * a HTTP server.
 */
public class PoloBootLoader implements BootLoader
{
   private static final int LONG_TIMEOUT = 60000;

   private ConsoleConnection console;

   public PoloBootLoader(ConsoleConnection console)
   {
      this.console = console;
   }

   public void reset() throws IOException
   {
      // Do not wait for a certain prompt before sending
      // the command since we want to be able to reset a
      // previously booted non-POLO OSE system as well.
      console.sendCommand("shutdown -r");
      console.waitForPrompt(LONG_TIMEOUT);
   }

   public void boot(String host, int port, String file) throws IOException
   {
      console.waitForPrompt();
      console.sendCommand("boot /http/" + host + ":" + port +
                          (file.startsWith("/") ? "" : "/") + file);
      console.expect("Transferring control to image", LONG_TIMEOUT);
   }

   public String getRamlog(boolean all) throws IOException
   {
      console.waitForPrompt();
      if (all)
      {
         console.sendCommand("rld -a");
      }
      else
      {
         console.sendCommand("rld -p");
      }
      return console.waitForPrompt(LONG_TIMEOUT);
   }
}
