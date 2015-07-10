/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2011 by Enea Software AB.
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
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public class Elf
{
   private final ElfFile elfFile;
   private final long textAddress;
   private final long relocatedTextAddress;
   private final List symbols;
   private final Dwarf2 dwarf2;
   private final ByteBuffer debugLineSection;
   private final ByteBuffer debugInfoSection;
   private final ByteBuffer debugAbbrevSection;
   private final ByteBuffer debugStrSection;
   private final ByteBuffer debugRangesSection;

   private Elf(String path, Long relocatedTextAddress) throws IOException
   {
      elfFile = new ElfFile(path);
      elfFile.open();
      ElfSectionHeader textSection = elfFile.getSection(".text");
      textAddress = textSection.getAddress();
      this.relocatedTextAddress = (relocatedTextAddress != null) ?
         relocatedTextAddress.longValue() : textAddress;
      symbols = elfFile.readSymbols(textSection);
      debugLineSection = elfFile.getSectionBuffer(".debug_line");
      debugInfoSection = elfFile.getSectionBuffer(".debug_info");
      debugAbbrevSection = elfFile.getSectionBuffer(".debug_abbrev");
      debugStrSection = elfFile.getSectionBuffer(".debug_str");
      debugRangesSection = elfFile.getSectionBuffer(".debug_ranges");
      dwarf2 = new Dwarf2(elfFile.is64Bit());
      dwarf2.parseCompilationUnits(debugInfoSection,
                                   debugAbbrevSection,
                                   debugStrSection,
                                   debugRangesSection);
      elfFile.close();
   }

   public Elf(String path) throws IOException
   {
      this(path, null);
   }

   public Elf(String path, long relocatedTextAddress) throws IOException
   {
      this(path, new Long(relocatedTextAddress));
   }

   public String getPath()
   {
      return elfFile.getPath();
   }

   /**
    * Return the function symbol for the given address or null if there is none.
    *
    * This method looks up a symbol in the symbol table in an ELF file. The
    * symbol table is almost always available in an ELF file. See the
    * getDebugSymbol() method for an alternative way of looking up a symbol.
    *
    * @param address  the address to look up the function symbol for.
    * @return the function symbol for the given address or null if there is none.
    * @see #getDebugSymbol(long)
    */
   public Symbol getSymbol(long address)
   {
      address = unrelocateAddress(address);

      if (symbols.isEmpty())
      {
         return null;
      }

      // Bail out if the address is outside of the symbol range of this file.
      Symbol firstSymbol = (Symbol) symbols.get(0);
      Symbol lastSymbol = (Symbol) symbols.get(symbols.size() - 1);
      if (UnsignedArithmetics.isLessThan(address, firstSymbol.getAddress()) ||
          UnsignedArithmetics.isGreaterThan(address, lastSymbol.getAddress() +
                lastSymbol.getSize()))
      {
         return null;
      }

      // Search for a symbol that contains the address.
      int symbolIndex = Collections.binarySearch(symbols, new Long(address));

      if (symbolIndex < 0)
      {
         // The address ended up between symbols. Could be an asm function.
         // Pick the closest symbol with a lower address.
         symbolIndex = -symbolIndex - 2;
      }

      // Make sure that the symbol index is not out of bounds.
      if (symbolIndex >= 0 && symbolIndex < symbols.size())
      {
         return (Symbol) symbols.get(symbolIndex);
      }
      else
      {
         return null;
      }
   }

   /**
    * Return the function symbol for the given address or null if there is none.
    *
    * This method looks up a symbol in the DWARF2 debug info sections of an ELF
    * file. DWARF2 debug info is only available in ELF files that are compiled
    * with debug info (e.g. -g). See the getSymbol() method for an alternative
    * way of looking up a symbol.
    *
    * This method is needed when looking up symbols for inlined functions, which
    * getSymbol() does not handle.
    *
    * @param address  the address to look up the function symbol for.
    * @return the function symbol for the given address or null if there is none.
    * @see #getSymbol(long)
    */
   public Symbol getDebugSymbol(long address)
   {
      address = unrelocateAddress(address);
      return dwarf2.getSymbol(address);
   }

   /**
    * Return the file & line info for the given address or null if there is none.
    *
    * This method looks up file & line info for an address in the DWARF2 debug
    * info sections of an ELF file. DWARF2 debug info is only available in ELF
    * files that are compiled with debug info (e.g. -g).
    *
    * @param address  the address to look up file & line info for.
    * @return the file & line info for the given address or null if there is none.
    * @throws IOException  if an I/O exception occurred.
    */
   public FileLineInfo getFileLineInfo(long address) throws IOException
   {
      address = unrelocateAddress(address);
      FileLineInfo info = dwarf2.getFileLineInfo(debugLineSection, address);
      debugLineSection.rewind();
      return info;
   }

   private long unrelocateAddress(long address)
   {
      long unrelocatedAddress = address - relocatedTextAddress + textAddress;
      return unrelocatedAddress;
   }
}
