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
import java.util.ArrayList;
import java.util.List;

import com.ose.sigdb.util.Format;

/**
 * Models a C enum type.
 */
public class CEnum extends CIntType
{

   private String[] labels;

   private long[] values;

   /**
    * @param name
    * @param labels
    * @param values
    * @param size
    * @param defaultEndian
    */
   public CEnum(String name, String[] labels, long[] values, int size,
         int defaultEndian)
   {
      super("enum " + name, size, defaultEndian, "%s");
      this.labels = labels;
      this.values = values;
   }

   /**
    * @param name
    * @return
    */
   public List complete(String name)
   {
      String start = name.trim();
      List matches = new ArrayList();

      if (start.length() == 0)
      {
         // If an empty string all should match.
         for (int i = 0; i < labels.length; i++)
         {
            matches.add(labels[i]);
         }
         return matches;
      }
      else
      {
         for (int i = 0; i < labels.length; i++)
         {
            if (labels[i].startsWith(start))
               matches.add(labels[i]);
         }
      }
      return matches;
   }

   /**
    * @param value
    * @return
    */
   public String getLabel(long value)
   {
      String result = null;

      for (int i = 0; i < values.length; i++)
      {
         if (values[i] == value)
         {
            result = labels[i];
            break;
         }
      }
      if (result == null)
         // No label found for this label so return the integer as a string.
         result = "" + value;
      return result;
   }

   /**
    * @return
    */
   public boolean isUnsigned()
   {
      return false;
   }

   /**
    * @param label
    * @return
    * 
    * @throws java.util.NoSuchElementException
    */
   public long label2number(String label)
         throws java.util.NoSuchElementException
   {
      for (int i = 0; i < values.length; i++)
      {
         if (label.equals(labels[i]))
            return values[i];
      }
      throw new java.util.NoSuchElementException("No such label: " + label);
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
      String f;

      if (format == null)
         f = "%s";
      else
         f = format;

      try
      {
         Format fmt = new Format(f);

         if (fmt.getFormatChar() != 's')
         {
            // if not a string representation use the standard integer toString
            // function.
            return super.toString(value, offset, endian, format);
         }
      }
      catch (java.lang.IllegalArgumentException e)
      {
         // If illagal format use the default string format.
         f = defaultFormat;
      }

      /*
       * DataInputStream allways treat data as big endian, so if little endian
       * we must swap the bytes.
       */
      byte[] correctEndian;

      if (endian == CType.LITTLE_ENDIAN)
      {
         correctEndian = swapEndian(value, offset);
         offset = 0; // reset the offset for the new byte array.
      }
      else
      {
         correctEndian = value;
      }

      DataInputStream s = new DataInputStream(new ByteArrayInputStream(
            correctEndian, offset, size));
      long longValue = 0;

      try
      {
         switch (size)
         {
         case 1:
            longValue = (long) s.readByte();
            break;
         case 2:
            longValue = (long) s.readShort();
            break;
         case 4:
            longValue = (long) s.readInt();
            break;
         case 8:
            longValue = (long) s.readLong();
            break;
         default:
            throw new RuntimeException("Unsupported length of int");
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }

      String svalue = getLabel(longValue);
      String result;

      try
      {
         String fmt = format == null ? "%s" : format;
         result = (new Format(fmt)).form(svalue);
      }
      catch (java.lang.IllegalArgumentException e)
      {
         // An incorrect format string so try with the default one.
         result = (new Format("%s")).form(svalue);
      }

      return result;
   }
}
