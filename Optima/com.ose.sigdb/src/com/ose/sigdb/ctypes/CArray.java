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
 * This class represent a C array. Note that multi dimension arrays are created
 * by creating arrays of arrays.
 */
public class CArray extends CType
{
   /** The type of the elements in the array. */
   private CType type;

   /** Number of elements in the array. */
   private int elements;

   /** The size of a single element (sizeof the type + pading). */
   private int elementSize;
   
   private boolean isVariableSize;

   /**
    * @param type
    * @param elements
    * @param elementSize
    */
   public CArray(CType type, int elements, int elementSize)
   {
      super(type.getName() + "[" + elements + "]", type.getDefaultEndian());
      this.type = type;
      this.elements = elements;
      this.elementSize = elementSize;
      isVariableSize = false;
   }
   
   /**
    * @param isVariableSize
    */
   public void setVariableSize(boolean isVariableSize)
   {
      this.isVariableSize = isVariableSize;
   }

   /**
    * @param string
    * @param data
    * @param offset
    */
   public void assign(String string, byte[] data, int offset)
   {
      if (isCString())
      {
         char[] chars = string.toCharArray();

         for (int i = 0; i < chars.length; i++)
         {
            data[offset + i] = (byte) (chars[i] & 0xff);
         }
         data[offset + chars.length] = (byte) 0;
      }
      else
      {
         throw new Error("This array is not a C string.");
      }
   }

   /**
    * Return the size of the array in number of elements.
    * 
    * @return
    */
   public final int getArraySize()
   {
      return elements;
   }

   /**
    * @return
    */
   public int getElementSize()
   {
      return elementSize;
   }

   /**
    * @return
    */
   public final CType getElementType()
   {
      return type;
   }

   public String getAsciiString(byte[] data, int offset, int endian)
   {
      StringBuffer buffer = new StringBuffer();
      
      int size = getSize();
      if(!isVariableSize)
      {
         for(int i = offset; i < offset + size && i < data.length; i++)
         {
            appendValidCharacter(buffer, (char) data[i]); 
         }
      }
      else
      {
         for(int i = offset; i < data.length; i++)
         {
            appendValidCharacter(buffer, (char) data[i]);
         }
      }
      
      return buffer.toString();
   }
   
   private void appendValidCharacter(StringBuffer buffer, char character)
   {
      if(character >= 0x20 && character < 0x7f)
      {
         buffer.append(character);
      }
      else if(character == 0x9 || character == 0xa || character == 0xd)
      {
         // TAB, LF, CR
         buffer.append(character);
      }
      else
      {
         buffer.append(".");
      }
   }
   
   /**
    * @return
    */
   public int getSize()
   {
      return elements * elementSize;
   }

   /**
    * @return
    */
   public CType getSubType()
   {
      return getElementType();
   }

   public boolean isComplex()
   {
      return !isPrimitive();
   }

   /**
    * @return
    */
   public final boolean isCString()
   {
      return (type instanceof CChar)
            && ((CChar) type).getDefaultFormat().getFormatChar() == 'c';
   }

   /**
    * @return
    */
   public boolean isPrimitive()
   {
      return type.isPrimitive();
   }

   public String toString()
   {
      return getName();
   }
   
   public String toString(byte[] value, int offset, int endian)
   {
      return toString(value, offset, endian, null);
   }
   
   /**
    * @param data
    * @param offset
    * @param endian
    * @param format
    * @return
    */
   public String toString(byte[] data, int offset, int endian, String format)
   {
      StringBuffer buffer = new StringBuffer();

      if (isCString())
      {
         buffer.append(getAsciiString(data, offset, endian));
      }
      else
      {
         if(!isVariableSize) {
            for(int i = offset; i < elements; i += elementSize)
            {
               buffer.append(type.toString(data, i, endian, format));
            }
         }
         else
         {
            for(int i = 0; i < data.length; i += elementSize)
            {
               buffer.append(type.toString(data, i, endian, format));
               buffer.append(", ");
            }
         }  
      }

      return buffer.toString();
   }
}
