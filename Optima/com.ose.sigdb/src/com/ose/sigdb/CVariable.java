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

import com.ose.sigdb.ctypes.CArray;
import com.ose.sigdb.ctypes.CChar;
import com.ose.sigdb.ctypes.CType;
import com.ose.sigdb.ctypes.CTypeDef;
import com.ose.sigdb.ctypes.ComplexType;
import com.ose.sigdb.table.SignalDescription;
import com.ose.sigdb.util.Format;

/**
 * This class models a C variable. The variable may be part of a struct or
 * union.
 * 
 * @see com.ose.sigdb.ctypes.CType;
 * @see com.ose.sigdb.Member;
 */
public class CVariable
{

   private String name;

   private CType type;

   private String format;

   private boolean readOnly;

   private String parentName;

   /**
    * This constructro is used to create a new variable from a old variable with
    * a new type.
    * 
    * @param v
    * @param newType
    */
   public CVariable(CVariable v, CType newType)
   {
      init(v.name, newType, v.format, v.parentName);
   }

   /**
    * @param name
    * @param type
    */
   public CVariable(String name, CType type)
   {
      init(name, type, null, null);
   }

   /**
    * @param name
    * @param type
    * @param format
    */
   public CVariable(String name, CType type, String format)
   {
      init(name, type, format, null);
   }

   /**
    * @param name
    * @param type
    * @param format
    * @param parentName
    */
   public CVariable(String name, CType type, String format, String parentName)
   {
      init(name, type, format, parentName);
   }

   /**
    * Find a sub variable by its name. The name must be complete inclusive any
    * array indexes. The variable is returned as a {@link Member, Member} to
    * preserve the offset from the start of the search.
    * 
    * @param name
    *           fully qualified member name (e.g. "nisse[2].pelle.orvar").
    * @return The found variable as a member or null if not found.
    * 
    * @throws MalformedVariableName
    */
   public Member findMember(String name)
   {
      return findSubVariable(name, 0);
   }

   /**
    * @param vname
    * @param offset
    * @return
    * 
    * @throws MalformedVariableName
    */
   public Member findSubVariable(String vname, int offset)
   {
      String reducedName = vname;

      if (reducedName.startsWith(name))
      {
         reducedName = reducedName.substring(name.length()).trim();
         CType type = this.type;

         if (type instanceof CTypeDef)
            type = ((CTypeDef) type).getSubType();

         if (type instanceof CArray)
         {
            CArray array = (CArray) type;

            if (reducedName.length() == 0)
            {
               return new Member(this, offset);
            }
            else if (reducedName.startsWith("["))
            {
               int index = reducedName.indexOf(']');

               if (index > 1)
               {
                  int arrayIndex;

                  try
                  {
                     arrayIndex = Integer.decode(
                           reducedName.substring(1, index).trim()).intValue();
                  }
                  catch (NumberFormatException nfe)
                  {
                     throw new RuntimeException("Array index is not a number.");
                  }
                  if ((arrayIndex < 0) || (arrayIndex >= array.getArraySize()))
                     throw new RuntimeException("Array index out of bounds: "
                           + arrayIndex);
                  reducedName = reducedName.substring(index + 1).trim();
                  if (reducedName.length() == 0)
                  {
                     // Home at last...
                     CVariable arrayentry = new CVariable(vname,
                           ((CArray) type).getSubType(), null, name);
                     arrayentry.setReadOnly(readOnly);
                     return new Member(arrayentry, offset + arrayIndex
                           * array.getElementSize());
                  }
                  else
                  {
                     CType subType = array.getSubType();

                     if (subType instanceof ComplexType)
                     {
                        if (reducedName.startsWith("."))
                        {
                           return ((ComplexType) subType).findSubVariable(
                                 reducedName.substring(1), offset + arrayIndex
                                       * array.getElementSize());
                        }
                        else
                           return null;
                     }
                     else
                        return null;
                  }
               }
               else
                  throw new RuntimeException(
                        "Missing \']\' in array expression.");
            }
            else
               return null;
         }
         else if (type.isPrimitive())
         {
            if (reducedName.length() == 0)
               return new Member(this, offset);
            else
               return null;
         }
         else if (type instanceof ComplexType)
         {
            if (reducedName.startsWith("."))
               return ((ComplexType) type).findSubVariable(reducedName
                     .substring(1), offset);
            else
               return null;
         }
         else
            throw new Error(
                  "Hmm... type is not an array, struct, union, or primitive: "
                        + type);
      }
      else
         return null;
   }

   /**
    * @return
    */
   public String getFormat()
   {
      return format;
   }

   /**
    * @return
    */
   public String getName()
   {
      return name;
   }

   /**
    * @return
    */
   public int getSize()
   {
      return type.getSize();
   }

   /**
    * @return
    */
   public CType getType()
   {
      return type;
   }

   /**
    * @param name
    * @param type
    * @param format
    * @param parentName
    */
   private void init(String name, CType type, String format, String parentName)
   {
      if (name == null || type == null)
      {
         throw new RuntimeException("name and type must be not null");
      }

      if (format != null && !type.isPrimitive())
      {
         throw new RuntimeException(
               "Only support format strings on primitive types.");
      }

      this.name = name;
      this.type = type;
      this.format = format;
      this.parentName = parentName;
   }

   /**
    * @return
    */
   public boolean isReadOnly()
   {
      return readOnly;
   }

   /**
    * @param readOnly
    */
   public void setReadOnly(boolean readOnly)
   {
      this.readOnly = readOnly;
   }

   public String toString(byte[] value, int offset, int endian)
   {
      return toString(value, offset, endian, null);
   }
   
   public String toString(byte[] value, int offset, int endian, String format)
   {
      String result = null;

      if (type instanceof CArray)
      {
         if (((CArray) type).getSubType() instanceof CChar)
         {
            if (format == null)
               format = "%s";
            try
            {
               Format f = new Format(format);

               if (f.getFormatChar() == 's')
                  result = getName() + " = " + type.toString(value, offset, endian, format);
            }
            catch (IllegalArgumentException e)
            {
               e.printStackTrace();
            }
         }

         if (result == null)
         {
            result = "";
            int elements = ((CArray) type).getArraySize();

            for (int i = 0; i < elements; i++)
            {
               String s = ((CArray) type).toString(value, offset, endian, format);

               if (s.endsWith(SignalDescription.truncatedMessage))
               {
                  result += s;
                  break;
               }
               else
                  result += s;
            }
         }
      }
      else if (type instanceof ComplexType)
      {
         result = "\n";
         result += ((ComplexType) type).toString(value, offset, endian, format);
      }
      else
         result = "    " + getName() + " = " + type.toString(value, offset, endian, format);

      return result;
   }

}
