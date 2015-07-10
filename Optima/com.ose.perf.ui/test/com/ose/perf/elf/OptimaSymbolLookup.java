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

import java.io.IOException;

public class OptimaSymbolLookup
{
   private final Elf elf;

   public OptimaSymbolLookup(String file) throws IOException
   {
      elf = new Elf(file);
   }

   public SymbolInfo lookup(long address) throws IOException
   {
      // Look up the symbol from the DWARF2 debug info if available,
      // otherwise from the symbol table.
      Symbol symbol = elf.getDebugSymbol(address);
      if (symbol == null)
      {
         symbol = elf.getSymbol(address);
      }
      String symbolName = (symbol != null) ? symbol.getName() : "??";
      FileLineInfo fileLineInfo = elf.getFileLineInfo(address);
      String file = (fileLineInfo != null) ? fileLineInfo.getFilename() : "??";
      long line = (fileLineInfo != null) ? fileLineInfo.getLine() : -1;
      return new SymbolInfo(symbolName, file, line);
   }
}
