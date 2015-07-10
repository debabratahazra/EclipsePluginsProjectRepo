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
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

class ElfFile
{
   // e_ident field indices
   static final int EI_MAG0 = 0;
   static final int EI_MAG1 = 1;
   static final int EI_MAG2 = 2;
   static final int EI_MAG3 = 3;
   static final int EI_CLASS = 4;
   static final int EI_DATA = 5;
   static final int EI_VERSION = 6;
   static final int EI_OSABI = 7;
   static final int EI_ABIVERSION = 8;
   static final int EI_PAD = 9;
   static final int EI_NIDENT = 16;

   // e_ident[EI_CLASS]
   static final byte ELFCLASS32 = 1;
   static final byte ELFCLASS64 = 2;

   // e_ident[EI_DATA]
   static final byte ELFDATA2LSB = 1;
   static final byte ELFDATA2MSB = 2;

   // e_type
   static final int ET_NONE = 0;
   static final int ET_REL = 1;
   static final int ET_EXEC = 2;
   static final int ET_DYN = 3;
   static final int ET_CORE = 4;
   static final int ET_LOOS = 0xFE00;
   static final int ET_HIOS = 0xFEFF;
   static final int ET_LOPROC = 0xFF00;
   static final int ET_HIPROC = 0xFFFF;

   // e_machine
   static final int EM_NONE = 0;
   static final int EM_386 = 3;
   static final int EM_MIPS = 8;
   static final int EM_PPC = 20;
   static final int EM_PPC64 = 21;
   static final int EM_ARM = 40;

   // e_version
   static final int EV_CURRENT = 1;

   // sh_type
   static final int SHT_PROGBITS = 1;
   static final int SHT_SYMTAB = 2;
   static final int SHT_STRTAB = 3;
   static final int SHT_RELA = 4;
   static final int SHT_HASH = 5;
   static final int SHT_DYNAMIC = 6;
   static final int SHT_NOTE = 7;
   static final int SHT_NOBITS = 8;
   static final int SHT_REL = 9;
   static final int SHT_SHLIB = 10;
   static final int SHT_DYNSYM = 11;

   // st_info
   static final int STT_NOTYPE = 0;
   static final int STT_OBJECT = 1;
   static final int STT_FUNC = 2;
   static final int STT_SECTION = 3;
   static final int STT_FILE = 4;
   static final int STT_GNU_IFUNC = 10;
   static final int STT_ARM_TFUNC = 13;
   static final int STT_LOPROC = 13;
   static final int STT_HIPROC = 14;

   private final String path;
   private RandomAccessFile file;
   private boolean isOpen;
   private boolean isBigEndian = true;
   private boolean is64Bit = false;
   private ElfHeader header;
   private ElfSectionHeader[] sectionHeaders;

   ElfFile(String path)
   {
      this.path = path;
   }

   void open() throws IOException
   {
      if (file != null)
      {
         throw new IOException("File " + path + " is already open.");
      }

      file = new RandomAccessFile(path, "r");

      readHeader();
      readSectionHeaders();

      isOpen = true;
   }

   void close() throws IOException
   {
      if (file == null)
      {
         throw new IOException("File " + path
               + " cannot be closed, because it is not open.");
      }

      file.close();
      isOpen = false;
      file = null;
      header = null;
      sectionHeaders = null;
   }

   boolean isOpen()
   {
      return isOpen;
   }

   String getPath()
   {
      return path;
   }

   boolean is64Bit()
   {
      return is64Bit;
   }

   ElfSectionHeader getSection(String sectionName)
   {
      for (int i = 0; i < sectionHeaders.length; i++)
      {
         if (sectionHeaders[i].getName().equals(sectionName))
         {
            return sectionHeaders[i];
         }
      }

      throw new IllegalArgumentException("Section " + sectionName + " not found.");
   }

   ByteBuffer getSectionBuffer(String sectionName) throws IOException
   {
      ElfSectionHeader debugLineSectionHeader = null;
      for (int i = 0; i < sectionHeaders.length; i++)
      {
          if (sectionHeaders[i].getName().equals(sectionName))
          {
              debugLineSectionHeader = sectionHeaders[i];
              break;
          }
      }

      return mapSectionToBuffer(debugLineSectionHeader);
   }

   List readSymbols(ElfSectionHeader textSection) throws IOException
   {
      // Collect section headers for all symbol table sections.
      List symbolTableSectionHeaders = new LinkedList();
      for (int i = 0; i < sectionHeaders.length; i++)
      {
         if (sectionHeaders[i].isSymbolTable())
         {
            symbolTableSectionHeaders.add(sectionHeaders[i]);
         }
      }

      // Create symbol objects for all symbols in all symbol table sections.
      List symbols = new ArrayList();
      for (Iterator iter = symbolTableSectionHeaders.iterator(); iter.hasNext();)
      {
         ElfSectionHeader sectionHeader = (ElfSectionHeader) iter.next();

         // Seek to the start of the symbol table section.
         file.seek(sectionHeader.getOffset());

         // FIXME: Potential 64-bit issue?
         long entryCount = sectionHeader.getSize() / sectionHeader.getEntrySize();

         // Read the symbol table entries
         List entries = new LinkedList();
         for (int i = 0; i < entryCount; i++)
         {
            ElfSymbolTableEntry entry = new ElfSymbolTableEntry();
            entry.readFields(header, this);
            entries.add(entry);
         }

         // Get the header of the section that contains the symbol names.
         ElfSectionHeader symbolNames = sectionHeaders[(int) sectionHeader.getLink()];

         // For each symbol table entry that is a function, create a symbol.
         for (Iterator entryIter = entries.iterator(); entryIter.hasNext();)
         {
            ElfSymbolTableEntry entry = (ElfSymbolTableEntry) entryIter.next();

            // If the symbol table entry represents a function,
            // create a symbol and add it to the list of symbols.
            if (isFunction(entry) || isTypelessFunction(entry, textSection))
            {
               // Read the name from the string section.
               String name = readStringFromStringSection(symbolNames, entry.getNameIndex());
               if (isArmSpecialSymbol(entry, name) || isMipsSpecialSymbol(name))
               {
                  continue;
               }
               Symbol symbol = new Symbol(name, entry.getValue(), entry.getSize(), true);
               symbols.add(symbol);
            }
         }
      }

      // Sort the symbols to enable binary search.
      Collections.sort(symbols);

      return symbols;
   }

   byte readByte() throws IOException
   {
      return file.readByte();
   }

   int readUnsignedShort() throws IOException
   {
      if (isBigEndian)
      {
         return file.readUnsignedShort();
      }
      else
      {
         return file.readUnsignedByte() | (file.readUnsignedByte() << 8);
      }
   }

   int readInt(RandomAccessFile f) throws IOException
   {
      if (isBigEndian)
      {
         return f.readInt();
      }
      else
      {
         return (file.readUnsignedByte() | (file.readUnsignedByte() << 8)
               | (file.readUnsignedByte() << 16) | (file.readUnsignedByte() << 24));
      }
   }

   long readUnsignedInt() throws IOException
   {
      if (isBigEndian)
      {
         return (long) file.readInt() & 0xFFFFFFFFL;
      }
      else
      {
         long value = (file.readUnsignedByte() | (file.readUnsignedByte() << 8)
               | (file.readUnsignedByte() << 16) | (file.readUnsignedByte() << 24));
         return value & 0xFFFFFFFFL;
      }
   }

   long readLong() throws IOException
   {
      if (isBigEndian)
      {
         return file.readLong();
      }
      else
      {
         return (file.readUnsignedByte() | (file.readUnsignedByte() << 8)
               | (file.readUnsignedByte() << 16) | (file.readUnsignedByte() << 24)
               | (file.readUnsignedByte() << 32) | (file.readUnsignedByte() << 40)
               | (file.readUnsignedByte() << 48) | (file.readUnsignedByte() << 56));
      }
   }

   int readHalf() throws IOException
   {
      return readUnsignedShort();
   }

   long readWord() throws IOException
   {
      return readUnsignedInt();
   }

   long readAddr32() throws IOException
   {
      return readUnsignedInt();
   }

   long readAddr64() throws IOException
   {
      return file.readLong();
   }

   long readOff32() throws IOException
   {
      return readUnsignedInt();
   }

   long readOff64() throws IOException
   {
      return file.readLong();
   }

   long readXword() throws IOException
   {
      return file.readLong();
   }

   void readBytes(byte[] bytes) throws IOException
   {
      file.readFully(bytes);
   }

   private void readHeader() throws IOException
   {
      // Seek to the beginning of the file.
      file.seek(0);

      // Read the ELF header based on the ELF identifier.
      header = new ElfHeader(readElfIdent());
      header.readFields(this);

      is64Bit = header.is64Bit();
   }

   private void readSectionHeaders() throws IOException
   {
      sectionHeaders =
         new ElfSectionHeader[header.getSectionHeaderCount()];

      // Seek to the section header offset.
      file.seek(header.getSectionHeaderOffset());

      // Read the section headers.
      for (int i = 0; i < sectionHeaders.length; i++)
      {
         ElfSectionHeader sectionHeader = new ElfSectionHeader();
         sectionHeader.readFields(header, this);
         sectionHeaders[i] = sectionHeader;
      }

      // Get the header of the section that contains the section names.
      ElfSectionHeader sectionNames =
         sectionHeaders[header.getSectionHeaderStringIndex()];

      // Retrieve the names of all the sections from the string section.
      for (int i = 0; i < sectionHeaders.length; i++)
      {
         // Read the name from the string section.
         String name = readStringFromStringSection(sectionNames,
               sectionHeaders[i].getNameIndex());
         sectionHeaders[i].setName(name);
      }
   }

   private String readStringFromStringSection(ElfSectionHeader stringSection,
         long stringOffset) throws IOException
   {
      // Seek to the first byte of the string.
      file.seek(stringSection.getOffset() + stringOffset);

      // Count the bytes up until the null terminator.
      int nameLength = 0;
      byte b = file.readByte();
      while (b != 0)
      {
         nameLength++;
         b = file.readByte();
      }

      if (nameLength > 0)
      {
         // Go back to the first byte of the section name.
         file.seek(stringSection.getOffset() + stringOffset);

         // Read the name string as bytes and convert to String.
         byte[] nameBytes = new byte[nameLength];
         file.readFully(nameBytes);
         return new String(nameBytes, "ISO8859-1");
      }
      else
      {
         // The string is empty.
         return "";
      }
   }

   private byte[] readElfIdent() throws IOException
   {
      byte[] e_ident = new byte[EI_NIDENT];

      // Read ELF identifier array.
      file.readFully(e_ident);

      // Make sure the file is an ELF, by checking the magic number.
      if (e_ident[EI_MAG0] != (byte) 0x7f
            || e_ident[EI_MAG1] != (byte) 'E'
            || e_ident[EI_MAG2] != (byte) 'L'
            || e_ident[EI_MAG3] != (byte) 'F'
      )
      {
         file.close();
         throw new IOException("File " + path + " is not an ELF file.");
      }

      // Adapt to the endian order in the file.
      if (e_ident[EI_DATA] == ELFDATA2LSB)
      {
         isBigEndian = false;
      }

      return e_ident;
   }

   private ByteBuffer mapSectionToBuffer(ElfSectionHeader sectionHeader) throws IOException
   {
      FileChannel channel = file.getChannel();

      // Map the specified section from the file into memory space.
      MappedByteBuffer buffer = channel.map(MapMode.READ_ONLY,
            sectionHeader.getOffset(), sectionHeader.getSize());

      // Make sure the correct endian order is used.
      if (!isBigEndian)
      {
         buffer.order(ByteOrder.LITTLE_ENDIAN);
      }

      return buffer;
   }

   private boolean isFunction(ElfSymbolTableEntry entry)
   {
      int info = entry.getInfo() & 0xF;
      return (info == STT_FUNC) || (info == STT_GNU_IFUNC) ||
             ((header.getMachine() == EM_ARM) && (info == STT_ARM_TFUNC));
   }

   /*
    * Ad hoc check for typeless assembly functions where the
    * programmer forgot to tag the assembly function with STT_FUNC.
    */
   private boolean isTypelessFunction(ElfSymbolTableEntry entry,
                                      ElfSectionHeader textSection)
   {
      if (((entry.getInfo() & 0xF) == STT_NOTYPE) && (entry.getNameIndex() > 0))
      {
         int sectionIndex = entry.getSectionHeaderIndex();
         if ((sectionIndex >= 0) && (sectionIndex < sectionHeaders.length))
         {
            long value = entry.getValue();
            if ((sectionHeaders[sectionIndex] == textSection) &&
                UnsignedArithmetics.isGreaterThanOrEqualTo(value,
                      textSection.getAddress()) &&
                UnsignedArithmetics.isLessThan(value,
                      textSection.getAddress() + textSection.getSize()))
            {
               return true;
            }
         }
      }

      return false;
   }

   private boolean isArmSpecialSymbol(ElfSymbolTableEntry entry, String name)
   {
      return (header.getMachine() == EM_ARM) && (entry.getInfo() == 0) &&
             (name.equals("$a") || name.equals("$t") || name.equals("$d") ||
              name.equals("$m") || name.equals("$f") || name.equals("$p"));
   }

   private boolean isMipsSpecialSymbol(String name)
   {
      return (header.getMachine() == EM_MIPS) && name.startsWith("$L");
   }
}
