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

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import java.math.BigInteger;

import com.ose.sigdb.util.Format;

/**
 * This class represents any c-language integer type. It should be subclassed to
 * handle a specific integer type.
 */
public abstract class CIntType extends CPrimType
{

   /**
    * Create a int of specified size.
    * 
    * @param name
    * @param _size
    * @param _defaultEndian
    * @param _defaultFormat
    */
   public CIntType(String name, int _size, int _defaultEndian,
         String _defaultFormat)
   {
      super(name, _size, _defaultEndian, _defaultFormat);
   }

   /**
    * Insert a value of this integer type to a byte array. The assigned value
    * must fit in this type.
    * 
    * @param value
    *           an object of type java.lang.Number (the "right value")
    * @param data
    *           the byte array there the value will be inserted.
    * @param offset
    *           the offset into the array.
    * @param endian
    *           the byte order to use (BIG_ENDIAN or LITTLE_ENDIAN).
    * @see osedbg.api.EndianConstants
    */
   public void assign(Object value, byte[] data, int offset, int endian)
   {
      Number num = (Number) value;
      long v = num.longValue();

      if (willFit(v))
      {
         if (endian == CType.BIG_ENDIAN)
         {
            for (int i = 0; i < size; i++)
            {
               int shift = 8 * (size - i - 1);

               data[offset + i] = (byte) ((v >> shift) & 0xff);
            }
         }
         else if (endian == CType.LITTLE_ENDIAN)
         {
            for (int i = 0; i < size; i++)
            {
               int shift = 8 * i;

               data[offset + i] = (byte) ((v >> shift) & 0xff);
            }
         }
         else
         {
            throw new Error("Unknown endian.");
         }
      }
      else
      {
         throw new Error("Integer value out of range.");
      }
   }

   /**
    * Read an integer of this type from an byte array. The value is parsed
    * according to this integers charistics and returned as a BigInteger.
    * 
    * @param value
    *           the byte array containing the integer value.
    * @param offset
    *           the index into the array.
    * @param endian
    *           the byte order to use (BIG_ENDIAN or LITTLE_ENDIAN).
    * @return the parsed value as a BigInteger.
    * @see osedbg.api.EndianConstants
    */
   public BigInteger bigIntegerValue(byte[] value, int offset, int endian)
   {
      byte[] correctEndian;
      int index;

      if (endian == CType.LITTLE_ENDIAN)
      {
         correctEndian = swapEndian(value, offset);
         index = 0; // reset the offset for the new byte array.
      }
      else
      {
         correctEndian = value;
         index = offset;
      }
      byte[] bytes;

      if (isUnsigned())
      {
         bytes = new byte[size + 1];
         bytes[0] = (byte) 0;
         System.arraycopy(correctEndian, index, bytes, 1, size);
      }
      else
      {
         bytes = new byte[size];
         System.arraycopy(correctEndian, index, bytes, 0, size);
      }
      return new BigInteger(bytes);
   }

   /**
    * Check if this integer type is unsigned.
    * 
    * @return true if this integer type is unsigned otherwise is returned.
    */
   public abstract boolean isUnsigned();

   /**
    * Read an integer of this type from an byte array. The value is parsed
    * according to this integers charistics and returned as a long. If the value
    * will not fit in a long NumberFormatException is thrown (this differ from
    * how methods in Number classes works since they tuncate the value).
    * 
    * @param value
    *           the byte array containing the integer value.
    * @param offset
    *           the index into the array.
    * @param endian
    *           the byte order to use (BIG_ENDIAN or LITTLE_ENDIAN).
    * @return the parsed value as a long.
    * @see osedbg.api.EndianConstants
    * 
    * @throws NumberFormatException
    */
   public long longValue(byte[] value, int offset, int endian)
         throws NumberFormatException
   {

      /*
       * DataInputStream allways treat data as big endian, so if little endian
       * we must swap the bytes.
       */
      byte[] correctEndian;
      int index;

      if (endian == CType.LITTLE_ENDIAN)
      {
         correctEndian = swapEndian(value, offset);
         index = 0; // reset the offset for the new byte array.
      }
      else
      {
         correctEndian = value;
         index = offset;
      }

      DataInputStream s = new DataInputStream(new ByteArrayInputStream(
            correctEndian, index, size));
      long v = 0;

      try
      {
         if (isUnsigned())
         {
            switch (size)
            {
            case 1:
               v = (long) s.readByte() & 0xffL;
               break;
            case 2:
               v = (long) s.readShort() & 0xffffL;
               break;
            case 4:
               v = (long) s.readInt() & 0xffffffffL;
               break;
            case 8:
               v = s.readLong();
               if (v < 0)
               {
                  throw new NumberFormatException(
                        "Value to large to fit in a long");
               }
               break;
            default:
               throw new RuntimeException("Unsupported length of int");
            }
         }
         else
         {
            switch (size)
            {
            case 1:
               v = s.readByte();
               break;
            case 2:
               v = s.readShort();
               break;
            case 4:
               v = s.readInt();
               break;
            case 8:
               v = s.readLong();
               break;
            default:
               throw new RuntimeException("Unsupported length of int");
            }
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return v;
   }

   /**
    * Get the name of the type.
    * 
    * @return name of the type.
    */
   public String toString()
   {
      return getName();
   }

   /**
    * @param value
    * @param offset
    * @param endian
    * @param format
    * @return
    */
   public String toString(byte[] value, int offset, int endian, String format)
   {
      if (value.length - offset < size)
      {
         return "0";
      }

      String result;
      String form;

      if (format == null)
      {
         form = defaultFormat;
      }
      else
      {
         form = format;
      }

      try
      {
         long v = longValue(value, offset, endian);

         try
         {
            Format f = new Format(form);
            char fc = f.getFormatChar();

            if ((fc == 'c') && (size > 1))
            {
               f.setFormatChar('d');
               result = f.form(v);
            }
            else if (fc == 'c')
            {
               result = f.form((char) v);
            }
            else
            {
               result = f.form(v);
            }
         }
         catch (IllegalArgumentException e)
         {
            // An incorrect format string so try with the default one.
            Format f = new Format(defaultFormat);

            if (f.getFormatChar() == 'c')
            {
               f.setFormatChar('d');
            }
            result = f.form(v);
         }
      }
      catch (NumberFormatException nfe)
      {
         BigInteger v = bigIntegerValue(value, offset, endian);

         try
         {
            Format f = new Format(form);
            char fc = f.getFormatChar();

            switch (fc)
            {
            case 'd':
               result = v.toString();
               break;
            case 'x':
               result = "0x" + v.toString(16).toLowerCase();
               break;
            case 'X':
               result = "0x" + v.toString(16).toUpperCase();
               break;
            case 'o':
               result = "0" + v.toString(8);
               break;
            default:
               result = v.toString();
               break;
            }
         }
         catch (IllegalArgumentException e)
         {
            result = v.toString();
         }
      }

      return result;
   }

   /**
    * Checks if an integer will fit in this type.
    * 
    * @param value
    *           to be checked.
    * @return true if the value may be stored in this integer type.
    */
   public final boolean willFit(long value)
   {
      if (size < 8)
      {
         return (value < (1L << (8 * size))) && (value > (-(1L << (8 * size))));
      }
      else
      {
         return true;
      }
   }
}
