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

/** This class represents the C short type. */
public class CShort extends CIntType
{

   /**
    * @param isUnsign
    * @return
    */
   private static String getConstName(boolean isUnsign)
   {
      if (isUnsign)
      {
         return "unsigned short";
      }
      else
      {
         return "short";
      }
   }

   /**
    * @param isUnsign
    * @return
    */
   private static String getDefFormat(boolean isUnsign)
   {
      if (isUnsign)
      {
         return "0x%x";
      }
      else
      {
         return "%d";
      }
   }

   private boolean unsigned;

   /**
    * Create the C short type (unsigned or signed).
    * 
    * @param _unsigned
    *           true if the type is unsigned.
    * @param _size
    *           the size of the short in bytes.
    * @param _defaultEndian
    *           the default endian used by toString.
    */
   public CShort(boolean _unsigned, int _size, int _defaultEndian)
   {
      super(getConstName(_unsigned), _size, _defaultEndian,
            getDefFormat(_unsigned));
      unsigned = _unsigned;
   }

   /**
    * Check if this integer is unsigned.
    * 
    * @return true if type is unsigned otherwise false is returned.
    */
   public boolean isUnsigned()
   {
      return unsigned;
   }
}
