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

/**
 * This class is used to represent a C floating point type that is arbitrary
 * long.
 */
public class CLongFloat extends CIntType
{

   /**
    * @param isUnsign
    * @return
    */
   private static String getConstName(boolean isUnsign)
   {
      return "float byte";
   }

   /**
    * @param isUnsign
    * @return
    */
   private static String getDefFormat(boolean isUnsign)
   {
      return "0x%x";
   }

   /**
    */
   public CLongFloat()
   {
      super(getConstName(true), 1, BIG_ENDIAN, getDefFormat(true));
   }

   /**
    * Check if this integer is unsigned.
    * 
    * @return true if type is unsigned otherwise false is returned.
    */
   public boolean isUnsigned()
   {
      return true;
   }
}
