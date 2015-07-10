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

import com.ose.sigdb.util.Format;

/**
 * 
 */
public abstract class CPrimType extends CType
{

   protected int size;

   protected String defaultFormat;

   /**
    * @param name
    * @param size
    * @param defaultEndian
    * @param defaultFormat
    */
   public CPrimType(String name, int size, int defaultEndian,
         String defaultFormat)
   {
      super(name, defaultEndian);

      if (!(size <= 8 && size >= 1))
      {
         throw new RuntimeException("Invalid size for prim c type: " + size);
      }

      if (!(defaultFormat.length() > 0))
      {
         throw new RuntimeException("No default format specified");
      }

      this.size = size;
      this.defaultFormat = defaultFormat;
   }

   /**
    * @param value
    * @param data
    * @param offset
    * @param endian
    */
   abstract public void assign(Object value, byte[] data, int offset, int endian);

   /**
    * @return
    */
   public Format getDefaultFormat()
   {
      return new Format(defaultFormat);
   }

   /**
    * @return
    */
   public int getSize()
   {
      return size;
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
      return true;
   }

   /**
    * Swap the byte order in the byte array starting at offset.
    * 
    * @param data
    *           the byte array containing the bytes to swap.
    * @param offset
    *           the offset into the byte array there to start swap.
    * @return a new allocated byte array with the converted data.
    */
   protected final byte[] swapEndian(byte[] data, int offset)
   {
      byte[] result = new byte[size];

      for (int i = 0; i < size; i++)
      {
         result[i] = data[offset + size - 1 - i];
      }
      return result;
   }

   /**
    * @param value
    * @param offset
    * @param endian
    * @return
    */
   public String toString(byte[] value, int offset, int endian)
   {
      return toString(value, offset, endian, defaultFormat);
   }
}
