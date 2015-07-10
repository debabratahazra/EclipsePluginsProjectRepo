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
 * This class models a C typedef of another type.
 */
public class CTypeDef extends CType
{

   private CType type;

   /**
    * @param name
    * @param cType
    * @param endian
    */
   public CTypeDef(String name, CType cType, int endian)
   {
      super(name, endian);
      this.type = cType;
   }

   /**
    * @return
    */
   public final int getSize()
   {
      return type.getSize();
   }

   /**
    * Get the type that this typedef is an alias for. If the encapsulated type
    * also is a typedef the root type is returned.
    * 
    * @return the encapsulated type.
    */
   public CType getSubType()
   {
      CType _type = type;

      if (_type instanceof CTypeDef)
      {
         _type = ((CTypeDef) _type).getSubType();
      }
      return _type;
   }

   public boolean isComplex()
   {
      return !isPrimitive();
   }

   /**
    * @return
    */
   public boolean isPrimitive()
   {
      return type.isPrimitive();
   }

   /**
    * @param data
    * @param offset
    * @param endian
    * @param format
    * @return
    */
   public final String toString(byte[] data, int offset, int endian,
         String format)
   {
      return type.toString(data, offset, endian, format);
   }
}
