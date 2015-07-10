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

class ElfHeader
{
   // The names of these fields are from the ELF specification.
   private final byte[] e_ident;
   private int e_type;
   private int e_machine;
   private long e_version;
   private long e_entry;
   private long e_phoff;
   private long e_shoff;
   private long e_flags;
   private int e_ehsize;
   private int e_phentsize;
   private int e_phentnum;
   private int e_shentsize;
   private int e_shnum;
   private int e_shstrndx;

   ElfHeader(byte[] e_ident)
   {
      this.e_ident = new byte[e_ident.length];
      for (int i = 0; i < e_ident.length; i++)
      {
         this.e_ident[i] = e_ident[i];
      }
   }

   boolean is64Bit()
   {
      return e_ident[ElfFile.EI_CLASS] == ElfFile.ELFCLASS64;
   }

   void readFields(ElfFile file) throws IOException
   {
      e_type = file.readHalf();
      e_machine = file.readHalf();
      e_version = file.readWord();
      e_entry = is64Bit() ? file.readAddr64() : file.readAddr32();
      e_phoff = is64Bit() ? file.readOff64() : file.readOff32();
      e_shoff = is64Bit() ? file.readOff64() : file.readOff32();
      e_flags = file.readWord();
      e_ehsize = file.readHalf();
      e_phentsize = file.readHalf();
      e_phentnum = file.readHalf();
      e_shentsize = file.readHalf();
      e_shnum = file.readHalf();
      e_shstrndx = file.readHalf();
   }

   byte[] getIdent()
   {
      return e_ident;
   }

   int getType()
   {
      return e_type;
   }

   int getMachine()
   {
      return e_machine;
   }

   long getVersion()
   {
      return e_version;
   }

   long getEntry()
   {
      return e_entry;
   }

   long getProgramHeaderOffset()
   {
      return e_phoff;
   }

   long getSectionHeaderOffset()
   {
      return e_shoff;
   }

   long getFlags()
   {
      return e_flags;
   }

   int getElfHeaderSize()
   {
      return e_ehsize;
   }

   int getProgramHeaderEntrySize()
   {
      return e_phentsize;
   }

   int getProgramHeaderEntryCount()
   {
      return e_phentnum;
   }

   int getSectionHeaderEntrySize()
   {
      return e_shentsize;
   }

   int getSectionHeaderCount()
   {
      return e_shnum;
   }

   int getSectionHeaderStringIndex()
   {
      return e_shstrndx;
   }
}
