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
 * 
 */
public class CPointer extends CIntType
{

   private CType type;

   /**
    * @param _type
    * @param _size
    * @param _defaultEndian
    */
   public CPointer(CType _type, int _size, int _defaultEndian)
   {
      super(_type.getName() + "*", _size, _defaultEndian, "0x%x");
      type = _type;
   }

   /**
    * @return
    */
   public CType dereferense()
   {
      return type;
   }

   /**
    * @return
    */
   public boolean isUnsigned()
   {
      return true;
   }
}
