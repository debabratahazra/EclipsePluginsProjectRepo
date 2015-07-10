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

package com.ose.sigdb;


/**
 * Object representation of a signal attribute
 */
public class SignalAttribute extends Attribute
{
   private CVariable variable;

   private int offset;

   /**
    * Constructs a new SingalAttribute
    * 
    * @param member
    *           description of the attribute.
    * @param data
    *           the signal data
    * @param byteOrder
    *           the data byte order
    * @see #com.ose.sigdb.util.EndianConstants#BIG_ENDIAN
    * @see #com.ose.sigdb.util.EndianConstants#LITTLE_ENDIAN
    */
   public SignalAttribute(Member member, byte[] data, int byteOrder)
   {
      variable = member.getVariable();
      type = variable.getType();
      offset = member.getOffset();
      value = type.toString(data, member.getOffset(), byteOrder, variable.getFormat());
   }

   /**
    * Gets this attribute's name.
    * 
    * @return the attribute's name.
    */
   public String getName()
   {
      return variable.getName();
   }

   /**
    * Gets the offset for this attribute in the data array.
    * 
    * @return the offset.
    */
   public int getOffset()
   {
      return offset;
   }

   /**
    * Gets the size of this attribute in bytes.
    * 
    * @return number of bytes this attribute use in the data array.
    */
   public int getSize()
   {
      return variable.getSize();
   }

   /**
    * Gets the value of this attribute as a string. The representation is
    * dependent of the toString implementation in the CType.
    * 
    * @see #com.ose.sigdb.ctypes.CType.toString(byte[], int, int).
    * @return the attribute value.
    */
   public String getValue()
   {
      return value;
   }
   
   public String toString()
   {
      StringBuffer buffer = new StringBuffer();
      
      buffer.append(getType() + " " + getName() + " = " + getValue());
      
      return buffer.toString();
   }
}
