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
import java.io.IOException;

/** This interface defines a common interface to classes that implements
    support for different object file formats. It does not support all
    aspects of object files.
*/
public interface ObjectFile
{

   /**
    * Init a new object file reader. Must be called before
    * any other method is called. 
    */
   public void init(File file) throws IOException;
   
   public File getFile();
   
   /** @return true if the file type of this file is suported by this ObjectFileReader */
   public boolean supportedFileType();

   /** @return true if the version of the object file is supported. */
   public boolean supportedVersion();

   /** @return the name of this object format (e.g. ELF). */
   public String getFormatName();

   /**
    * @param offset
    * @param length
    * @return a string read from the object file 
    */
   public String getAsciiString(long offset, int length);

   /**
    * Get the endian for this object file.
    *   @see osedbg.api.EndianConstants
    *   @return an endian constant.
    */
   public int getEndian();

   /**
    * Read a byte order corrected int from the specified offset. 
    * @param offset
    * @return
    */
   public int readInt(long offset);

   /**
    * Read a byte order corrected short from the specified offset. 
    * @param offset
    * @return
    */
   public short readShort(long offset);

   /**
    * Read a byte from the specified offset. 
    * @param offset
    * @return
    */
   public byte readByte(long offset);

   /**
    * Get the offset of the value that the symbol references.
    *   @param symbol the name of the symbol.
    *   @param suffix the suffix of the symbol.
    *   @return an offset to the value of this symbol or 0 (zero) if not found.
    */
   public long getSymbolValueIndex(String symbol, String suffix);

   /**
    * @param symbol - the symbol with the suffix
    * @param suffix null for any or string for starting point
    * @return - string of next suffix 
    */
   public String getNextOseSymbolSuffix(String suffix);

   /**
    * @param symbol - the symbol with the suffix
    * @param suffix null for any or string for starting point
    * @return - true if found else false 
    */
   public boolean findOseSymbolSuffix(String suffix);   

   /**
    * @return - true if a 64-bit ABI is used with 64-bit pointers
    */
   public boolean is64bit();
}
