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

import java.util.Iterator;

import com.ose.sigdb.CVariable;
import com.ose.sigdb.Member;

/**
 * A C struct modulation.
 */
public class CStruct extends ComplexType
{

   /**
    * Create a new C struct. The struct is not complete after creation, instead
    * addMember should be called. The type name should start with the key word
    * struct.
    * 
    * @param typeName
    *           the complete type name (e.g. "struct Signal").
    * @param endian
    *           the default byte order.
    * @see osedbg.api.signal.ComplexType#addMember
    */
   public CStruct(String typeName, int endian)
   {
      super(typeName, endian);
   }

   /**
    * Get the size of this struct. Any padding at the end of this struct is
    * ignored.
    * 
    * @return the size occupied by a variable of this struct.
    */
   public int getSize()
   {
      int size;

      Member m = getLastMember();

      if (m != null)
         size = m.getOffset() + m.getSize();
      else
      {
         size = 0;
      }

      return size;
   }

   /**
    * Get the type name.
    * 
    * @return the complete type name including "union".
    */
//   public String toString()
//   {
//      return getName();
//   }

   /**
    * Create a string representation of a variable length array in this struct.
    * 
    * If the last element is an array with one element the array is treated as
    * an array of variable size by this method. If that array is a char array
    * it's by default treated as a string.
    * <p>
    * The array size is calculated from the length parameter and the type size.
    * <p>
    * <code>
    * Example:
    *   struct Name {
    *     SIGSELECT signo;
    *     char name[1];
    *   };
    * </code> If
    * the signal above has sigsize() == 10 and sizeof(struct Name) == 5
    * toStringVarArray should be called with length == 10 and the contents of
    * name is returned.
    * 
    * @param data
    *           the byte array of the raw data.
    * @param offset
    *           into the data there this struct starts.
    * @param length
    *           number of bytes actual used for this variabel.
    * @param endian
    *           the byte order to use.
    * @param format
    *           the C printf style format string.
    * @return a string representation of the last array.
    */
   public String toStringVarArray(byte[] data, int offset, int length,
         int endian, String format)
   {
      String result = "";
      Member lastMember = getLastMember();
      CType t = lastMember.getVariable().getType();

      if (t instanceof CArray)
      {
         CArray a = (CArray) t;

         if (a.getArraySize() == 1)
         {
            for (Iterator i = getMemberIterator(); i.hasNext();)
            {
               Member m = (Member) i.next();

               if (m == lastMember)
               {

                  /*
                   * Replace the last member with a new array member with
                   * adjusted size.
                   */
                  CArray tempArray = new CArray(a.getElementType(), (length - m
                        .getOffset())
                        / a.getElementSize(), a.getElementSize());
                  CVariable tempVariable = new CVariable(m.getVariable(),
                        tempArray);

                  m = new Member(tempVariable, m.getOffset());
               }
               result += m.getVariable().toString(data, offset + m.getOffset(), endian) + "\n";
            }
         }
         else
            result = toString(data, offset, endian, format);
      }
      else
         result = toString(data, offset, endian, format);

      return result;
   }
}
