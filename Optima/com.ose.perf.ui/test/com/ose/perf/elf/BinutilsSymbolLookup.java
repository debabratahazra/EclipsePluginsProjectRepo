/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2012 by Enea Software AB.
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

package com.ose.perf.elf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class BinutilsSymbolLookup
{
   private final boolean signExtendAddresses;
   private final Process addr2line;
   private final BufferedWriter stdin;
   private final BufferedReader stdout;
   private String lastAddress;
   private String lastSymbol;
   private String lastFileAndLine;

   /*
    * The format of the addr2line output:
    * $ addr2line -s -f -e hello.elf
    * 08048442
    * main
    * hello.c:39
    */

   public BinutilsSymbolLookup(String command, String file) throws IOException
   {
      String[] cmdarray = new String[] {command, "-s", "-f", "-e", file};
      signExtendAddresses = command.toLowerCase().contains("mips");
      addr2line = Runtime.getRuntime().exec(cmdarray);
      stdin = new BufferedWriter(new OutputStreamWriter(addr2line.getOutputStream()));
      stdout = new BufferedReader(new InputStreamReader(addr2line.getInputStream()));
   }

   private void getOutput(String address) throws IOException
   {
      if (!address.equals(lastAddress))
      {
         stdin.write(address + "\n");
         stdin.flush();
         lastSymbol = stdout.readLine();
         lastFileAndLine = stdout.readLine();
         lastAddress = address;
      }
   }

   private String getSymbol(long address) throws IOException
   {
      getOutput(Long.toHexString(address));
      return lastSymbol;
   }

   private String getFileAndLine(long address) throws IOException
   {
      getOutput(Long.toHexString(address));
      return lastFileAndLine;
   }

   private String getFile(long address) throws IOException
   {
      String file = "??";
      String fileAndLine = getFileAndLine(address);
      int index1, index2;
      if ((fileAndLine != null) && (index1 = fileAndLine.lastIndexOf(':')) != -1)
      {
         // We do this because addr2line in Cygwin produces
         // <cygdrive/pathtoexc/C:/pathtofile:##>
         index2 = fileAndLine.indexOf(':');
         if (index1 == index2)
         {
            index2 = 0;
         }
         else
         {
            index2--;
         }
         file = fileAndLine.substring(index2, index1);
      }
      return file;
   }

   private long getLine(long address) throws IOException
   {
      // We try to get the nearest match since the symbol may not exactly align
      // with debug info. In C line number 0 is invalid, line starts at 1 for
      // file, we use this for validation.

      for (int i = 0; i <= 20; i += 4, address += i)
      {
         String fileAndLine = getFileAndLine(address);
         if (fileAndLine != null)
         {
            int colon = fileAndLine.lastIndexOf(':');
            String line = fileAndLine.substring(colon + 1);
            if (!line.startsWith("0"))
            {
               try
               {
                  return Long.parseLong(line);
               }
               catch (Exception e)
               {
                  return -1;
               }
            }
         }
      }

      return -1;
   }

   private long signExtendAddress(long address)
   {
      if (signExtendAddresses && ((address & 0x80000000L) != 0))
      {
         address = address | 0xFFFFFFFF00000000L;
      }
      return address;
   }

   public SymbolInfo lookup(long address) throws IOException
   {
      address = signExtendAddress(address);
      String symbol = getSymbol(address);
      String file = getFile(address);
      long line = getLine(address);
      return new SymbolInfo(symbol, file, line);
   }

   public void dispose()
   {
      try
      {
         stdout.close();
         stdin.close();
         addr2line.getErrorStream().close();
      } catch (IOException ignore) {}
      addr2line.destroy();
   }
}
