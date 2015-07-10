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

package com.ose.sigdb.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** This class handles an signed in as it was an unsigned 32 bit int. */

public class U32 implements EndianConstants
{
   /**
    * The smallest value of type <code>int</code>.
    * 
    * @since P2.1.0.0
    */
   public static final int MIN_VALUE = 0x0;

   /**
    * The largest value of type <code>int</code>.
    * 
    * @since P2.1.0.0
    */
   public static final long MAX_VALUE = 0xffffffffL;

   // private static Format hexFormat = new Format("0x%X");
   private final static char[] hexTokens = { '0', '1', '2', '3', '4', '5', '6',
         '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

   private final static char[] buffer = new char[10];

   private static Format addrFormat = new Format("%8X");

   private static Format byteFormat = new Format("%X");

   /** the string strBuffer is used by hexDumpToList. */
   private static StringBuffer strBuffer = new StringBuffer(100);

   public static long get(int v)
   {
      // And what is wrong with (long)v & 0xffffffffL, Sven?
      // if(v < 0) return v + 0x100000000l;
      // else return v;

      return (long) v & 0xffffffffL;
   }

   public static int getInt(long v)
   {
      // What!?!! This is plain wrong....
      // if(v > 0x7fffffffL)
      // return (int)-(v & 0x7fffffffL);
      // else
      // return (int)v;

      return (int) v;
   }

   public static final String hexDump(String linePrefix, byte[] arr,
         int lineSize, boolean ascii)
   {
      return hexDump(linePrefix, 0, arr, lineSize, ascii, 1, BIG_ENDIAN);
   }

   public static final String hexDump(String linePrefix, int startAddr,
         byte[] arr, int lineSize, boolean ascii)
   {
      return hexDump(linePrefix, startAddr, arr, lineSize, ascii, 1, BIG_ENDIAN);
   }

   public static final String hexDump(String linePrefix, int startAddr,
         byte[] arr, int lineSize, boolean ascii, int wordSize, int endian)
   {
      List strings = hexDumpToList(linePrefix, startAddr, arr, lineSize, ascii,
            wordSize, endian);
      String res = "";
      for (Iterator i = strings.iterator(); i.hasNext();)
      {
         res += i.next();
         if (i.hasNext())
            res += "\n";
      }
      return res;
   }

   /**
    * Create a hexdump from a byte array.
    * 
    * @param linePrefix
    *           is a string that is prepended to each line.
    * @param startAddress
    *           is the address offset there the byte array starts at.
    * @param arr
    *           the byte array to be hex-dumped.
    * @param lineSize
    *           number of bytes for each line.
    * @param wordSize
    *           nuber of bytes per word (1, 2, 4, or 8).
    * @param endian
    *           constant used when wordSize > 1.
    * @return a list of strings.
    * @see #BIG_ENDIAN
    * @see #LITTLE_ENDIAN
    */
   public static final java.util.List hexDumpToList(String linePrefix,
         int startAddr, byte[] arr, int lineSize, boolean ascii, int wordSize,
         int endian)
   {
      ArrayList resList = new ArrayList();
      int pos = 0;
      int length = arr.length;
      while (pos < length)
      {
         int start = pos;
         synchronized (strBuffer)
         {
            strBuffer.setLength(0);
            strBuffer.append(linePrefix + toString(addrFormat, startAddr + pos)
                  + ":");
            do
            {
               strBuffer.append(' ');
               if (endian == BIG_ENDIAN)
               {
                  do
                  {
                     int v = arr[pos] < 0 ? 256 + arr[pos] : arr[pos];
                     strBuffer.append((v <= 0xf ? "0" : "")
                           + toString(byteFormat, v));
                     pos++;
                  } while (pos < length && pos % wordSize != 0);
               }
               else if (endian == LITTLE_ENDIAN)
               {
                  int count = (pos + wordSize <= length) ? wordSize - 1
                        : length - pos;
                  while (count >= 0)
                  {
                     int v = arr[pos + count] < 0 ? 256 + arr[pos + count]
                           : arr[pos + count];
                     strBuffer.append((v <= 0xf ? "0" : "")
                           + toString(byteFormat, v));
                     count--;
                  }
                  pos += wordSize;
               }
               else
                  throw new Error("Unsupported endian: " + endian);
            } while (pos < length && pos % lineSize != 0);
            if (ascii)
            {
               strBuffer.append(' ');
               for (int i = start; i < pos; i++)
               {
                  byte b = arr[i];
                  char c;
                  c = (b >= 0x20 && b <= 0x7e) ? (char) b : '.';
                  strBuffer.append(c);
               }
            }
            resList.add(strBuffer.toString());
         }
      }
      return resList;
   }

   public static final int parse(String string) throws NumberFormatException
   {
      int base;

      if (string.startsWith("0x"))
      {
         string = string.substring(2);
         base = 16;
      }
      else
         base = 10;

      long value = Long.parseLong(string, base);
      if ((value < MIN_VALUE) || (value > MAX_VALUE))
      {
         throw new NumberFormatException("Value is out of range.");
      }

      return (int) value;
   }

   public static String toDecString(int _value)
   {
      String result;

      if (_value < 0)
      {
         // Treat the int as unsigned and convert to a long.
         result = Long.toString(get(_value));
      }
      else
      {
         result = Integer.toString(_value);
      }
      return result;
   }

   private static final String toString(Format format, int _value)
   {
      return format.form(get(_value));
   }

   public static String toString(int _value)
   {
      int i = 9;
      do
      {
         buffer[i] = hexTokens[_value & 15];
         _value >>>= 4;
         i--;
      } while (_value != 0);
      buffer[i--] = 'x';
      buffer[i] = '0';
      return new String(buffer, i, 10 - i);
   }

   private int value; /* The unsigned value represented as a signed value. */

   /** Treat a signed integer as an unsigned 32 bit integer. */
   public U32(int _value)
   {
      value = _value;
   }

   /**
    * Converts a string to an unsigned integer. If the string starts with 0x the
    * string is treated as a hexadicimal value.
    * 
    * @throws java.lang.NumberFormatException.
    */
   public U32(String string) throws NumberFormatException
   {
      value = parse(string);
   }

   /**
    * Returns the value of this Integer as a byte.
    * 
    * @since P2.1.0.0
    */
   public byte byteValue()
   {
      return (byte) value;
   }

   /**
    * Returns the value of this Integer as a double.
    * 
    * @return the <code>int</code> value represented by this object is
    *         converted to type <code>double</code> and the result of the
    *         conversion is returned.
    * @since P2.1.0.0
    */
   public double doubleValue()
   {
      return (double) get();
   }

   /**
    * Returns the value of this Integer as a float.
    * 
    * @return the <code>int</code> value represented by this object is
    *         converted to type <code>float</code> and the result of the
    *         conversion is returned.
    * @since P2.1.0.0
    */
   public float floatValue()
   {
      return (float) get();
   }

   public final long get()
   {
      return get(value);
   }

   public int getInt()
   {
      return value;
   }

   /**
    * Returns the value of this Integer as an int.
    * 
    * @return the <code>int</code> value represented by this object.
    * @since P2.1.0.0
    */
   public int intValue()
   {
      return (int) value;
   }

   /**
    * Returns the value of this Integer as a long.
    * 
    * @return the <code>int</code> value represented by this object that is
    *         converted to type <code>long</code> and the result of the
    *         conversion is returned.
    * @since P2.1.0.0
    */
   public long longValue()
   {
      return get();
   }

   /** Change the value of this integer. */
   public void set(int _value)
   {
      value = _value;
   }

   /**
    * Returns the value of this Integer as a short.
    * 
    * @since P2.1.0.0
    */
   public short shortValue()
   {
      return (short) value;
   }

   public String toDecString()
   {
      return toDecString(value);
   }

   public String toString()
   {
      return toString(value);
   }
}
