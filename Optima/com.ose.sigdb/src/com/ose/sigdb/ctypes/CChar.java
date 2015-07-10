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

package com.ose.sigdb.ctypes;

/** This class represents the C char type. */
public class CChar extends CIntType
{
   /**
    * @param isUnsign
    * @return
    */
   private static String getConstName(boolean isUnsign)
   {
      if (isUnsign)
      {
         return "unsigned char";
      }
      else
      {
         return "char";
      }
   }

   private boolean unsigned;

   /**
    * Create a 8 bit char type (unsigned or signed).
    * 
    * @param _unsigned
    *           true if the type is unsigned.
    */
   public CChar(boolean _unsigned)
   {
      // Only one byte chars are supported.
      super(getConstName(_unsigned), 1, CType.BIG_ENDIAN, "%c");
      unsigned = _unsigned;
   }

   /**
    * Create a 8 bit char type (unsigned or signed).
    * 
    * @param _unsigned
    *           true if the type is unsigned.
    * @param typeSize
    * @param endian
    */
   public CChar(boolean _unsigned, int typeSize, int endian)
   {
      // Only one byte chars are supported.
      super(getConstName(_unsigned), typeSize, endian, "%c");
      unsigned = _unsigned;
   }

   /**
    * Create a 8 bit char type (unsigned or signed).
    * 
    * @param _unsigned
    * @param format
    *           is used as default format string at out put.
    */
   public CChar(boolean _unsigned, String format)
   {
      // Only one byte chars are supported.
      super(getConstName(_unsigned), 1, CType.BIG_ENDIAN, format);
      unsigned = _unsigned;
   }

   /**
    * @return
    */
   public boolean isUnsigned()
   {
      return unsigned;
   }
}
