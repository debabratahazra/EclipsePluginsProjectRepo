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

public class SymbolInfo
{
   private final String symbol;
   private final String file;
   private final long line;

   public SymbolInfo(String symbol, String file, long line)
   {
      this.symbol = symbol;
      this.file = file;
      this.line = line;
   }

   public String getSymbol()
   {
      return symbol;
   }

   public String getFile()
   {
      return file;
   }

   public long getLine()
   {
      return line;
   }

   public boolean equals(Object obj)
   {
      if (obj instanceof SymbolInfo)
      {
         SymbolInfo other = (SymbolInfo) obj;
         if (this.symbol.equals(other.symbol) || this.equalsIgnoreDemangling(other))
         {
            // Ignore file and line if one or both of them are missing.
            if ((this.line == -1) &&  (other.line == -1))
            {
               return true;
            }
            else if (this.file.equals("??") && other.file.equals("??"))
            {
               return true;
            }
            else
            {
               return this.file.equals(other.file) && (this.line == other.line);
            }
         }
      }

      return false;
   }

   /*
    * This method compares this symbol name with another symbol name and if one
    * of them is mangled and the other is demangled it determines them to be
    * equal if the the mangled name contains the demangled name or a part of it.
    *
    * The reason for this check is that the Optima ELF parser does not demangle
    * symbol names while the addr2line ELF parser demangles some symbol names
    * even though we instruct it to not demangle symbol names.
    */
   private boolean equalsIgnoreDemangling(SymbolInfo other)
   {
      boolean thisMangled = this.symbol.startsWith("_Z");
      boolean otherMangled = other.symbol.startsWith("_Z");

      if (thisMangled && !otherMangled)
      {
         String otherSymbol = other.symbol;
         if (otherSymbol.startsWith("~") && (otherSymbol.length() > 1))
         {
            // Ignore the '~' in demangled C++ destructors.
            otherSymbol = otherSymbol.substring(1);
         }
         return this.symbol.contains(otherSymbol);
      }
      else if (!thisMangled && otherMangled)
      {
         String thisSymbol = this.symbol;
         if (thisSymbol.startsWith("~") && (thisSymbol.length() > 1))
         {
            // Ignore the '~' in demangled C++ destructors.
            thisSymbol = thisSymbol.substring(1);
         }
         return other.symbol.contains(thisSymbol);
      }
      else
      {
         // Both symbol names are either mangled or demangled and should in that
         // case be completely equal.
         return this.symbol.equals(other.symbol);
      }
   }

   public String toString()
   {
      return "Symbol: " + symbol + "\n" +
             "File: " + file + "\n" +
             "Line: " + line;
   }
}
