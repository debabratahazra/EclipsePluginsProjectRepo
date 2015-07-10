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

class ElfSymbolTableEntry
{
   // These fields have names from the ELF specification.
   private long st_name;
   private long st_value;
   private long st_size;
   private byte st_info;
   private byte st_other;
   private int st_shndx;

   void readFields(ElfHeader elfHeader, ElfFile file) throws IOException
   {
      if (elfHeader.is64Bit())
      {
         st_name = file.readWord();
         st_info = file.readByte();
         st_other = file.readByte();
         st_shndx = file.readHalf();
         st_value = file.readAddr64();
         st_size = file.readXword();
      }
      else
      {
         st_name = file.readWord();
         st_value = file.readAddr32();
         st_size = file.readWord();
         st_info = file.readByte();
         st_other = file.readByte();
         st_shndx = file.readHalf();
      }
   }

   long getNameIndex()
   {
      return st_name;
   }

   long getValue()
   {
      return st_value;
   }

   long getSize()
   {
      return st_size;
   }

   byte getInfo()
   {
      return st_info;
   }

   byte getOther()
   {
      return st_other;
   }

   int getSectionHeaderIndex()
   {
      return st_shndx;
   }
}
