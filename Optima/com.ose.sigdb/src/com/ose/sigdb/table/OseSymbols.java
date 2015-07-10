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

package com.ose.sigdb.table;

import java.io.IOException;

import com.ose.sigdb.io.ObjectFile;

class OseSymbols implements SymDescConstants
{
   private int offset;

   private ObjectFile objectFile;

   private int minorVersion; // Initialized by OseSymbols.supportedVersion

   private int majorVersion; // Initialized by OseSymbols.supportedVersion

   /**
    * The symbol prefix for C symbols in files. If symbol look up failes then a
    * new look up will be tryed with a reset symbolPrefix. 68K uses '_' as
    * symbol prefix, ppc does not.
    */
   private String symbolPrefix = "";

   private String symbolSuffix = "";

   public OseSymbols(ObjectFile objectFile, String suffix) throws IOException
   {
      if (!objectFile.supportedFileType())
      {
         throw new RuntimeException("Unsupported file type: " + objectFile);
      }

      this.objectFile = objectFile;

      offset = (int) objectFile.getSymbolValueIndex(symbolPrefix
            + "ose_symbols", suffix);
      if (offset <= 0)
      {
         // try a new symbol prefix before giving up.
         symbolPrefix = "_";
         offset = (int) objectFile.getSymbolValueIndex(symbolPrefix
               + "ose_symbols", suffix);
      }
      if (offset <= 0)
      {
         throw new IOException(
               "Not a valid confsig table in this file (no ose_symbols "
                     + suffix + ").");
      }

      symbolSuffix = suffix;
   }

   /**
    * @return
    */
   public int getEnumdescSize()
   {
      return objectFile.readInt(offset + (objectFile.is64bit() ? OS_ENUMDESC_SIZE_64 : OS_ENUMDESC_SIZE));
   }

   /**
    * @return
    * 
    * @throws IOException
    */
   public int getEnumPool() throws IOException
   {
      int index = (int) objectFile.getSymbolValueIndex(symbolPrefix
            + "ose_enum_pool", symbolSuffix);

      if ((index <= 0 && (getEnumdescSize() > 0)))
      {
         throw new IOException("Corrupt confsig table (no ose_enum_pool).");
      }
      return index;
   }

   /**
    * @return
    * 
    * @throws IOException
    */
   public int getIndexPool() throws IOException
   {
      int index = (int) objectFile.getSymbolValueIndex(symbolPrefix
            + "ose_index_pool", symbolSuffix);

      if (index <= 0)
      {
         throw new IOException("Corrupt confsig table (no ose_index_pool).");
      }
      return index;
   }

   /**
    * @return
    */
   public int getIndexPoolSize()
   {
      return objectFile.readInt(offset + (objectFile.is64bit() ? OS_INDEX_POOL_SIZE_64 : OS_INDEX_POOL_SIZE));
   }

   public int getMajorVersion()
   {
      return majorVersion;
   }

   public int getMinorVersion()
   {
      return minorVersion;
   }

   /**
    * @return
    */
   public int getSigdescSize()
   {
      return objectFile.readInt(offset + (objectFile.is64bit() ? OS_SIGDESC_SIZE_64 : OS_SIGDESC_SIZE));
   }

   /**
    * @return
    * 
    * @throws IOException
    */
   public int getStringPool() throws IOException
   {
      int index = (int) objectFile.getSymbolValueIndex(symbolPrefix
            + "ose_string_pool", symbolSuffix);

      if ((index <= 0) && getStringPoolSize() > 0)
      {
         throw new IOException("Corrupt confsig table (no ose_string_pool).");
      }
      return index;
   }

   /**
    * @return
    */
   public int getStringPoolSize()
   {
      return objectFile.readInt(offset + (objectFile.is64bit() ? OS_STRING_POOL_SIZE_64 : OS_STRING_POOL_SIZE));
   }

   public String getSymbolSuffix()
   {
      return symbolSuffix;
   }

   /**
    * @return
    */
   public int getVardescSize()
   {
      return objectFile.readInt(offset + (objectFile.is64bit() ? OS_VARDESC_SIZE_64 : OS_VARDESC_SIZE));
   }

   /**
    * @param suffix -
    *           null for any or string for starting point
    * @return - if another suffix was found was found
    * @throws IOException
    */
   public String nextSuffix(String suffix)
   {
      return objectFile.getNextOseSymbolSuffix(suffix);
   }

   /**
    * @return
    */
   public boolean supportedVersion()
   {
      boolean supported = false;
      majorVersion = objectFile.readShort(offset + (objectFile.is64bit() ? OS_MAJVER_64 : OS_MAJVER));
      minorVersion = objectFile.readShort(offset + (objectFile.is64bit() ? OS_MINVER_64 : OS_MINVER));
      supported = (majorVersion == 2)
            && ((minorVersion == 0) || (minorVersion == 1));
      if (!objectFile.getFormatName().equals("ELF"))
      {
         supported = supported || (majorVersion == 3) && (minorVersion == 0);
      }
      return supported;
   }
}
