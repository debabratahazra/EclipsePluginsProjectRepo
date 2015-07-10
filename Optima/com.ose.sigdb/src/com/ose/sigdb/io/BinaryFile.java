/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2007 by Enea Software AB.
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

package com.ose.sigdb.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.ose.sigdb.util.EndianConstants;
import com.ose.sigdb.util.StringParser;

/**
 * Implements functions to support sigdb information in binary files. <n>
 * 
 * @see ObjectFile
 */
public class BinaryFile implements ObjectFile, EndianConstants
{
   // Byte position in table ose_symbols when 32-bit pointers are used.

   public final static int OSE_SYMBOLS_MAJVER = 0;

   public final static int OSE_SYMBOLS_MINVER = 2;

   public final static int OSE_SYMBOLS_SIGDESC_P = 4;

   public final static int OSE_SYMBOLS_VARDESC_P = 8;

   public final static int OSE_SYMBOLS_ENUM_POOL_P = 12;

   public final static int OSE_SYMBOLS_STRING_POOL_P = 16;

   public final static int OSE_SYMBOLS_INDEX_POOL_P = 20;

   public final static int OSE_SYMBOLS_SIGDESC_SIZE = 24;

   public final static int OSE_SYMBOLS_VARDESC_SIZE = 28;

   public final static int OSE_SYMBOLS_ENUMDESC_SIZE = 32;

   public final static int OSE_SYMBOLS_STRING_POOL_SIZE = 36;

   public final static int OSE_SYMBOLS_INDEX_POOL_SIZE = 40;

   public final static int OSE_SYMBOLS_MAGIC = 44;

   // Byte position in table ose_symbols when 64-bit pointers are used.

   public final static int OSE_SYMBOLS_MAJVER_64 = 0;

   public final static int OSE_SYMBOLS_MINVER_64 = 2;

   public final static int OSE_SYMBOLS_SIGDESC_P_64 = 8;

   public final static int OSE_SYMBOLS_VARDESC_P_64 = 16;

   public final static int OSE_SYMBOLS_ENUM_POOL_P_64 = 24;

   public final static int OSE_SYMBOLS_STRING_POOL_P_64 = 32;

   public final static int OSE_SYMBOLS_INDEX_POOL_P_64 = 40;

   public final static int OSE_SYMBOLS_SIGDESC_SIZE_64 = 48;

   public final static int OSE_SYMBOLS_VARDESC_SIZE_64 = 52;

   public final static int OSE_SYMBOLS_ENUMDESC_SIZE_64 = 56;

   public final static int OSE_SYMBOLS_STRING_POOL_SIZE_64 = 60;

   public final static int OSE_SYMBOLS_INDEX_POOL_SIZE_64 = 64;

   public final static int OSE_SYMBOLS_MAGIC_64 = 68;

   private File file;

   private byte[] data;

   private int endian = 0;

   private int oseSymbolsIndex = 0; /* Position of ose_symbols */

   private String savedSuffix = "";

   /* If true, we assume 64-bit pointers in struct ose_symbols
    * and the 64-bit versions of the offsets should be used.
    */
   private boolean has64bitPointers = false;

   /**
    * @param symbol -
    *           the symbol with the suffix
    * @param suffix
    *           null for any or string for starting point
    * @return - true if found else false
    */
   public boolean findOseSymbolSuffix(String suffix)
   {
      String pattern;
      int tmp;
      if (oseSymbolsIndex == 0 || !savedSuffix.equals(suffix))
      {
         if (suffix == null)
         {
            pattern = "This is a sigdesc structure (ose_symbols)";
         }
         else
         {
            pattern = "This is a " + suffix + "desc structure (ose_symbols)";
         }
         tmp = findPattern(pattern.getBytes(), 0, -1);
         
         if (tmp == -1)
         { /* oseSymbols not found */
            oseSymbolsIndex = 0;
            return false;
         }
         
         oseSymbolsIndex = tmp - (has64bitPointers ? OSE_SYMBOLS_MAGIC_64 : OSE_SYMBOLS_MAGIC);

         short majver = readShort(oseSymbolsIndex + (has64bitPointers ? OSE_SYMBOLS_MAJVER_64 : OSE_SYMBOLS_MAJVER));
         short minver = readShort(oseSymbolsIndex + (has64bitPointers ? OSE_SYMBOLS_MINVER_64 : OSE_SYMBOLS_MINVER));
         if (majver < 2 || majver > 3)
         {
            oseSymbolsIndex = 0;
            return false;
         }
         if (majver == 2 && (minver < 1 || minver > 2))
         {
            oseSymbolsIndex = 0;
            return false;
         }
         if (majver == 3 && minver != 0)
         {
            oseSymbolsIndex = 0;
            return false;
         }

         if (suffix != null)
         {
            savedSuffix = suffix;
         }
      }
      return true;
   }

   /**
    * Get the offset of the value that the symbol references.
    * 
    * @param pattern
    *           the symbol to search for.
    * @param from
    *           start point of search
    * @param noBytes
    *           how many bytes to search (-1 for all)
    * @return an offset to this symbol or -1 if not found.
    */
   private int findPattern(byte[] pattern, int from, int noBytes)
   {
      int j;
      int result = -1;
      int bar;

      if (from + pattern.length > data.length)
      {
         return result;
      }

      bar = data.length - pattern.length - from;

      if (noBytes != -1)
      {
         if (bar > noBytes)
         {
            bar = noBytes;
         }
      }

      for (int i = from; i < from + bar; i++)
      {
         if (data[i] == pattern[0])
         {
            for (j = 1; j < pattern.length; j++)
            {
               if (data[i + j] != pattern[j])
               {
                  break;
               }
            }
            if (j == pattern.length)
            {
               result = i;
               break;
            }
         }
      }
      return result;
   }

   /**
    * @param offset
    * @param length
    * @return a string read from the object file
    */
   public String getAsciiString(long offset, int maxLength)
   {
      return StringParser.ascii2String(data, (int) offset, maxLength);
   }

   /**
    * Get the endian for this object file.
    * 
    * @see osedbg.api.EndianConstants
    * @return an endian constant.
    */
   public int getEndian()
   {

      if (endian != 0)
      {
         return endian;
      }

      if (oseSymbolsIndex == 0)
      { /* Get any ose_symbols */
         getSymbolValueIndex("ose_symbols", null);
      }

      if (oseSymbolsIndex == 0)
      {
         endian = BIG_ENDIAN;
         return endian; /* Unknown endian */
      }

      byte v = data[oseSymbolsIndex + (has64bitPointers ?
                    OSE_SYMBOLS_MAJVER_64 : OSE_SYMBOLS_MAJVER)];
      if (v == 0)
      {
         /* The most significant byte of the version is 0 => big endian. */
         endian = BIG_ENDIAN;
      }
      else
      {
         endian = LITTLE_ENDIAN;
      }
      return endian;
   }

   public File getFile()
   {
      return file;
   }

   /** @return the name of this object format (e.g. binary). */
   public String getFormatName()
   {

      return "binary";
   }

   public String getNextOseSymbolSuffix(String suffix)
   {
      int tmp;
      int index;
      String nextSuffix;
      String pattern = "This is a sigdesc structure (ose_symbols";
      int strlen = pattern.length();
      if (suffix == null)
      { /* First search search for any ose_symbol* */
         tmp = findPattern(pattern.getBytes(), 0, -1);
         if (tmp == -1)
         {
            return null;
         }
         index = tmp + strlen;
      }
      else
      {
         /* Find suffixed symbol */
         tmp = findPattern((pattern + suffix + ")").getBytes(), 0, -1);

         if (tmp == -1)
         {
            return null;
         }

         /* Find next symbol with any suffix */
         tmp = findPattern(pattern.getBytes(), tmp + strlen, -1);
         if (tmp == -1)
         {
            return null;
         }
         index = tmp + strlen;
      }
      /* Get the the suffix */
      tmp = findPattern(")".getBytes(), index, -1);
      if (tmp == -1)
      { /* Something is wrong !!!! */
         return null;
      }
      if (tmp > index)
      {
         nextSuffix = new String(data, index, tmp - index);
      }
      else
      {
         nextSuffix = new String("");
      }
      return nextSuffix;
   }

   /**
    * Get the offset of the value that the symbol references.
    * 
    * @param symbol
    *           the name of the symbol.
    * @return an offset to the value of this symbol or 0 (zero) if not found.
    */
   public long getSymbolValueIndex(String symbol, String suffix)
   {
      int tmp;
      String pattern;

      if (!findOseSymbolSuffix(suffix))
      { /* ose_symbols not found */
         return -1;
      }

      if (symbol.startsWith("ose_symbols"))
      {
         return oseSymbolsIndex;
      }

      if (suffix == null)
      {
         pattern = "This is a sigdesc structure (" + symbol + ")";
      }
      else
      {
         pattern = "This is a " + suffix + "desc structure (" + symbol + ")";
      }

      tmp = findPattern(pattern.getBytes(), 0, -1);
      if (tmp == -1)
      {
         return -1;
      }

      if (symbol.startsWith("ose_sigdesc_tab"))
      {
         return (tmp - readInt(oseSymbolsIndex + (has64bitPointers ? OSE_SYMBOLS_SIGDESC_SIZE_64 : OSE_SYMBOLS_SIGDESC_SIZE)));
      }
      if (symbol.startsWith("ose_vardesc_tab"))
      {
         return (tmp - readInt(oseSymbolsIndex + (has64bitPointers ? OSE_SYMBOLS_VARDESC_SIZE_64 : OSE_SYMBOLS_VARDESC_SIZE)));
      }
      if (symbol.startsWith("ose_enum_pool"))
      {
         return (tmp - readInt(oseSymbolsIndex + (has64bitPointers ? OSE_SYMBOLS_ENUMDESC_SIZE_64 : OSE_SYMBOLS_ENUMDESC_SIZE)));
      }
      if (symbol.startsWith("ose_index_pool"))
      {
         return (tmp - readInt(oseSymbolsIndex + (has64bitPointers ? OSE_SYMBOLS_INDEX_POOL_SIZE_64 : OSE_SYMBOLS_INDEX_POOL_SIZE)));
      }
      if (symbol.startsWith("ose_string_pool"))
      {
         return (tmp - readInt(oseSymbolsIndex + (has64bitPointers ? OSE_SYMBOLS_STRING_POOL_SIZE_64 : OSE_SYMBOLS_STRING_POOL_SIZE)));
      }
      return -1;
   }

   /**
    * Init a new object file reader. Must be called before any other method is
    * called.
    */
   public void init(File file) throws IOException
   {
      FileInputStream istream = new FileInputStream(file);
      data = new byte[(int) file.length()];
      istream.read(data);

      this.file = file;
   }

   /**
    * Read a byte from the specified offset.
    * 
    * @param offset
    * @return
    */
   public byte readByte(long offset)
   {
      return data[(int) offset];
   }

   /**
    * Read a byte order corrected int from the specified offset.
    * 
    * @param offset
    * @return
    */
   public int readInt(long offset)
   {
      int value;

      if (getEndian() == LITTLE_ENDIAN)
      {
         value = (data[(int) offset + 3] & 0xFF) << 24;
         value |= (data[(int) offset + 2] & 0xFF) << 16;
         value |= (data[(int) offset + 1] & 0xFF) << 8;
         value |= (data[(int) offset] & 0xFF);
      }
      else
      {
         value = (data[(int) offset] & 0xFF) << 24;
         value |= (data[(int) offset + 1] & 0xFF) << 16;
         value |= (data[(int) offset + 2] & 0xFF) << 8;
         value |= (data[(int) offset + 3] & 0xFF);
      }

      return value;
   }

   /**
    * Read a byte order corrected short from the specified offset.
    * 
    * @param offset
    * @return
    */
   public short readShort(long offset)
   {
      short value;

      if (getEndian() == LITTLE_ENDIAN)
      {
         value = (short) ((data[(int) offset + 1] & 0xFF) << 8);
         value |= (data[(int) offset] & 0xFF);
      }
      else
      {
         value = (short) ((data[(int) offset] & 0xFF) << 8);
         value |= (data[(int) offset + 1] & 0xFF);
      }

      return value;
   }

   /**
    * @return true if the file type of this file is suported by this
    *         ObjectFileReader
    */
   public boolean supportedFileType()
   {
      /* Try to find the magic identifier */
      byte[] sigPattern = "This is a sigdesc structure".getBytes();
      byte[] evtPattern = "This is a evtdesc structure".getBytes();

      return (findPattern(sigPattern, 0, -1) > 0) || (findPattern(evtPattern, 0, -1) > 0);
   }

   /** @return true if the version of the object file is supported. */
   public boolean supportedVersion()
   {
      short majver;
      short minver;

      if (oseSymbolsIndex == 0)
      { /* check any structure */
         getSymbolValueIndex("ose_symbols", null);
         if (oseSymbolsIndex == 0)
         {
            getSymbolValueIndex("ose_symbols", "evt");
            if (oseSymbolsIndex == 0)
            {
               /* Try again but assume 64-bit pointers in struct ose_symbols. */
               has64bitPointers = true;
               getSymbolValueIndex("ose_symbols", null);
               if (oseSymbolsIndex == 0)
               {
                  getSymbolValueIndex("ose_symbols", "evt");
                  if (oseSymbolsIndex == 0)
                  {
                     return false;
                  }
               }
            }
         }
      }

      majver = readShort(oseSymbolsIndex + (has64bitPointers ? OSE_SYMBOLS_MAJVER_64 : OSE_SYMBOLS_MAJVER));
      minver = readShort(oseSymbolsIndex + (has64bitPointers ? OSE_SYMBOLS_MINVER_64 : OSE_SYMBOLS_MINVER));

      if (((majver == 2) && (minver == 1)) || ((majver == 3) && (minver == 0)))
      {
         /* 2.1 version may be generated without magic id=>check */
         if (supportedFileType())
         {
            return true;
         }
      }

      return false;
   }

   public boolean is64bit()
   {
      return has64bitPointers;
   }
}
