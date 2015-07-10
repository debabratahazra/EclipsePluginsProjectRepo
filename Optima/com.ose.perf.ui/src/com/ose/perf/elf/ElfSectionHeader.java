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

class ElfSectionHeader
{
   // The names of these fields come from the ELF specification.
   private long sh_name;
   private long sh_type;
   private long sh_flags;
   private long sh_addr;
   private long sh_offset;
   private long sh_size;
   private long sh_link;
   private long sh_info;
   private long sh_addralign;
   private long sh_entsize;

   private String name = "";

   void readFields(ElfHeader elfHeader, ElfFile file) throws IOException
   {
      sh_name = file.readWord();
      sh_type = file.readWord();
      sh_flags = elfHeader.is64Bit() ? file.readXword() : file.readWord();
      sh_addr = elfHeader.is64Bit() ? file.readAddr64() : file.readAddr32();
      sh_offset = elfHeader.is64Bit() ? file.readOff64() : file.readOff32();
      sh_size = elfHeader.is64Bit() ? file.readXword() : file.readWord();
      sh_link = file.readWord();
      sh_info = file.readWord();
      sh_addralign = elfHeader.is64Bit() ? file.readXword() : file.readWord();
      sh_entsize = elfHeader.is64Bit() ? file.readXword() : file.readWord();
   }

   String getName()
   {
      return name;
   }

   void setName(String name)
   {
      this.name = name;
   }

   boolean isSymbolTable()
   {
      return sh_type == ElfFile.SHT_SYMTAB;
   }

   long getNameIndex()
   {
      return sh_name;
   }

   long getType()
   {
      return sh_type;
   }

   long getFlags()
   {
      return sh_flags;
   }

   long getAddress()
   {
      return sh_addr;
   }

   long getOffset()
   {
      return sh_offset;
   }

   long getSize()
   {
      return sh_size;
   }

   long getLink()
   {
      return sh_link;
   }

   long getInfo()
   {
      return sh_info;
   }

   long getAddressAlignment()
   {
      return sh_addralign;
   }

   long getEntrySize()
   {
      return sh_entsize;
   }
}
