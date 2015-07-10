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

import com.ose.sigdb.util.EndianConstants;

/**
 * This class models a C-language type. Normaly C type information is extracted
 * during reading of a signal table.
 * 
 * @see osedbg.api.signal.SignalDescriptionTable
 */
public abstract class CType implements EndianConstants
{
   /**
    * Default endian (or byte order). The default endian is normaly the endian
    * of the target or object file which this type is read from.
    * 
    * @see osedbg.api.EndianConstants
    */
   private int defaultEndian;

   private String name;

   /**
    * Initialize common parameters for this type.
    * <p>
    * The type name may contain type modifiers such as unsigned. Examples of
    * type names: "int", unsigned long", "struct Data", "Data".
    * <p>
    * The default endian is used if endian is unspecified during printing and
    * parsning of data of this type. The byte order of the target or object file
    * is normaly used as the default endian.
    * 
    * @param name
    *           the name of this type.
    * @param endian
    *           the default byte order for this type.
    * @see osedbg.api.EndianConstants
    */
   public CType(String name, int endian)
   {
      if (name != null)
      {
         this.name = name;
      }
      else
      {
         this.name = "";
      }

      switch (endian)
      {
      case LITTLE_ENDIAN:
         defaultEndian = LITTLE_ENDIAN;
         break;
      case BIG_ENDIAN:
         defaultEndian = BIG_ENDIAN;
         break;
      default:
         throw new RuntimeException("Endian type " + endian + " is invalid");
      }
   }

   /**
    * Get default endian. The default endian is normaly the endian of the target
    * or object file which this type is read from.
    * 
    * @return default endian constant.
    * @see osedbg.api.EndianConstants
    */
   public int getDefaultEndian()
   {
      return defaultEndian;
   }

   /**
    * Get the name of the type. Normaly the name may contain type modifiers such
    * as unsigned.
    * 
    * @return the name of the type or an empty string.
    */
   public String getName()
   {
      return name;
   }

   /**
    * Get a string representation.
    * 
    * @return the name of this type. public String toString() { return
    *         getName(); }
    * 
    * /** Get the size of a variable instanciated to this type in bytes.
    * @return the size in bytes.
    */
   public abstract int getSize();

   public abstract boolean isComplex();

   /**
    * This function returns true if the type is a native C type.
    * 
    * @retrun true if this type is a native C type (i.e. an int).
    * @return
    */
   public abstract boolean isPrimitive();

   /**
    * Return a string representation of the byte array interpreted as this type.
    * The default endian and format string for this type is used.
    * 
    * @param value
    *           a byte array containing the data to be interpreted.
    * @param offset
    *           an offset into the byte array to start read.
    * @return the string representation.
    */
   public String toString(byte[] value, int offset)
   {
      return toString(value, offset, defaultEndian, null);
   }

   /**
    * Return a string representation of the byte array interpreted as this type.
    * The default format string for this type is used.
    * 
    * @param value
    *           a byte array containing the data to be interpreted.
    * @param offset
    *           an offset into the byte array to start read.
    * @param endian
    *           the byteorder to use.
    * @return the string representation.
    * @see osedbg.api.EndianConstants
    */
   public String toString(byte[] value, int offset, int endian)
   {
      return toString(value, offset, endian, null);
   }

   /**
    * Return a string representation of the byte array interpreted as this type.
    * 
    * @param value
    *           a byte array containing the data to be interpreted.
    * @param offset
    *           an offset into the byte array to start read.
    * @param endian
    *           the byteorder to use.
    * @param format
    *           A C printf style format string that may contain one format char.
    * @return the string representation.
    * @see osedbg.api.EndianConstants
    */
   public abstract String toString(byte[] value, int offset, int endian,
         String format);
}
