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

package com.ose.sigdb.table;

import java.io.IOException;

import com.ose.sigdb.ctypes.CArray;
import com.ose.sigdb.ctypes.CPrimType;
import com.ose.sigdb.ctypes.CStruct;
import com.ose.sigdb.ctypes.CType;
import com.ose.sigdb.ctypes.CUnion;
import com.ose.sigdb.ctypes.CShort;
import com.ose.sigdb.ctypes.CChar;
import com.ose.sigdb.ctypes.CDouble;
import com.ose.sigdb.ctypes.CEnum;
import com.ose.sigdb.ctypes.CFloat;
import com.ose.sigdb.ctypes.CInt;
import com.ose.sigdb.ctypes.CLong;
import com.ose.sigdb.ctypes.CLongFloat;
import com.ose.sigdb.ctypes.CLongLong;
import com.ose.sigdb.ctypes.CPointer;
import com.ose.sigdb.io.ObjectFile;

public class VariableDescriptionTable
{
   final static int ENTRY_SIZE_V21 = 20;

   final static int NAME_V21 = 0;

   final static int SIZE_V21 = 4;

   final static int OFFSET_V21 = 6;

   final static int INDEX_V21 = 8;

   final static int MISC_V21 = 10;

   final static int TYPE_V21 = 12;

   final static int FLAGS_V21 = 14;

   final static int INDICES_V21 = 16;

   final static int ENTRY_SIZE_V20 = 16;

   final static int NAME_V20 = 0;

   final static int SIZE_V20 = 2;

   final static int OFFSET_V20 = 4;

   final static int INDEX_V20 = 6;

   final static int MISC_V20 = 8;

   final static int TYPE_V20 = 10;

   final static int FLAGS_V20 = 12;

   final static int INDICES_V20 = 14;

   final static int T_ENUM = 0;

   final static int T_CHAR = 1;

   final static int T_U_CHAR = 2;

   final static int T_SHORT = 3;

   final static int T_U_SHORT = 4;

   final static int T_INT = 5;

   final static int T_U_INT = 6;

   final static int T_LONG = 7;

   final static int T_U_LONG = 8;

   final static int T_FLOAT = 9;

   final static int T_DOUBLE = 10;

   final static int T_LONG_DOUBLE = 11;

   final static int T_POINTER = 12;

   final static int T_STRUCT = 13;

   final static int T_UNION = 14;

   final static int T_VOLATILE = 15;

   final static int T_VOID = 16;

   final static int T_LONG_LONG = 17;

   final static int T_U_LONG_LONG = 18;

   final static int ENUM_SIZE = 8;

   final static int N_A_ = 65535;

   final static int FLAGS_US_FIRST = 0x1;

   final static int FLAGS_US_LAST = 0x2;

   final static int FLAGS_VOID_IN = 0x4;

   final static int FLAGS_VOID_OUT = 0x8;

   final static int FLAGS_USR_IN = 0x10;

   final static int FLAGS_USR_OUT = 0x20;

   final static int FLAGS_STRING = 0x40;

   private int entrySize;

   private int nameIdx;

   private int sizeIdx;

   private int offsetIdx;

   private int indexIdx;

   private int miscIdx;

   private int typeIdx;

   private int flagsIdx;

   private int indicesIdx;

   private OseSymbols oseSymbols;

   private int offset;

   private int size;

   private int endian;

   private int indexPool = -1;

   private int enumPool = -1;

   private String symbolPrefix = "";

   private ObjectFile objectFile;

   private CPrimType cchar;

   private CPrimType cuchar;

   private CPrimType cshort;

   private CPrimType cushort;

   private CPrimType cint;

   private CPrimType cuint;

   private CPrimType clong;

   private CPrimType culong;

   private CPrimType clonglong;

   private CPrimType culonglong;

   private CType cfloat;

   private CType cdouble;

   private CType clongdouble;

   public VariableDescriptionTable(ObjectFile objectFile, OseSymbols symbols)
         throws IOException
   {
      oseSymbols = symbols;
      offset = (int) objectFile.getSymbolValueIndex(symbolPrefix
            + "ose_vardesc_tab", oseSymbols.getSymbolSuffix());
      if (offset <= 0)
      {
         // try a new symbol prefix before giving up.
         offset = (int) objectFile.getSymbolValueIndex("_" + "ose_vardesc_tab",
               oseSymbols.getSymbolSuffix());
         if (offset > 0)
         {
            symbolPrefix = "_";
         }
      }
      size = oseSymbols.getVardescSize();

      if ((offset <= 0) && (size > 0))
      {
         throw new IOException("Corrupt confsig table (no ose_vardesc_tab).");
      }

      endian = objectFile.getEndian();

      if ((oseSymbols.getMajorVersion() > 2)
            || (oseSymbols.getMajorVersion() == 2 && oseSymbols
                  .getMinorVersion() > 0))
      {
         entrySize = ENTRY_SIZE_V21;
         nameIdx = NAME_V21;
         sizeIdx = SIZE_V21;
         offsetIdx = OFFSET_V21;
         indexIdx = INDEX_V21;
         miscIdx = MISC_V21;
         typeIdx = TYPE_V21;
         flagsIdx = FLAGS_V21;
         indicesIdx = INDICES_V21;
      }
      else
      {
         entrySize = ENTRY_SIZE_V20;
         nameIdx = NAME_V20;
         sizeIdx = SIZE_V20;
         offsetIdx = OFFSET_V20;
         indexIdx = INDEX_V20;
         miscIdx = MISC_V20;
         typeIdx = TYPE_V20;
         flagsIdx = FLAGS_V20;
         indicesIdx = INDICES_V20;
      }

      this.objectFile = objectFile;
   }

   /**
    * Get a floating point type
    * 
    * @param typeSize
    * @return
    */
   private final CType createFloatType(int typeSize)
   {
      CType t;

      switch (typeSize)
      {
      case 4:
         t = new CFloat(endian);
         break;
      case 8:
         t = new CDouble(endian);
         break;
      default: // Represent the unhandled float size as a byte array
         t = new CArray(new CLongFloat(), typeSize, 1);
      }
      return t;
   }

   /**
    * @param index
    * @return
    * 
    * @throws IOException
    */
   private CType getEnumType(int index) throws IOException
   {
      int stringPool = oseSymbols.getStringPool();

      if (enumPool < 0)
      {
         enumPool = oseSymbols.getEnumPool();
      }
      if (((enumPool < 0) && (oseSymbols.getEnumdescSize() == 0))
            || ((stringPool < 0) && (oseSymbols.getStringPoolSize() == 0)))
      {
         return new CEnum("<no name>", new String[0], new long[0], 4, endian);
      }
      int count = 0;
      int enumIndex = getMisc(index);
      int value = objectFile.readInt(enumPool + enumIndex * ENUM_SIZE);
      int stringIndex = objectFile
            .readInt(enumPool + enumIndex * ENUM_SIZE + 4);

      while (!((value == 0) && (stringIndex == N_A_)))
      {
         count++;
         int ind = (enumPool + count * ENUM_SIZE) + enumIndex * ENUM_SIZE;

         value = objectFile.readInt(ind);
         stringIndex = objectFile.readInt(ind + 4);
      }
      long[] values = new long[count];
      String[] names = new String[count];

      for (int i = 0; i < count; i++)
      {
         int ind = (enumPool + i * ENUM_SIZE) + enumIndex * ENUM_SIZE;

         stringIndex = objectFile.readInt(ind + 4);
         values[i] = objectFile.readInt(ind);
         names[i] = objectFile.getAsciiString(stringPool + stringIndex, 256);
      }
      CType result = new CEnum(getName(index), names, values, getSize(index),
            endian);

      return result;
   }

   /**
    * @param index
    * @return
    */
   public int getMisc(int index)
   {
      int misc = objectFile.readShort(offset + index * entrySize + miscIdx);

      return misc;
   }

   /**
    * @param index
    * @return
    * 
    * @throws IOException
    */
   public String getName(int index) throws IOException
   {
      int stringPool = oseSymbols.getStringPool();
      int nameIndex;

      if ((oseSymbols.getMajorVersion() == 2)
            && (oseSymbols.getMinorVersion() > 0)
            || (oseSymbols.getMajorVersion() > 2))
      {
         if ((stringPool < 0) && oseSymbols.getStringPoolSize() == 0)
         {
            return "<no name>";
         }
         nameIndex = objectFile.readInt(offset + index * entrySize + nameIdx);
      }
      else
      {
         nameIndex = objectFile.readShort(offset + entrySize * index + nameIdx);
         if (nameIndex < 0)
         {
            nameIndex += 0x10000;
         }
      }

      return objectFile.getAsciiString(stringPool + nameIndex, 128);
   }

   /**
    * @param index
    * @return
    */
   public int getOffset(int index)
   {
      int varOffset = objectFile.readShort(offset + index * entrySize
            + offsetIdx);

      return varOffset;
   }

   /**
    * @param index
    * @return
    */
   public int getSize(int index)
   {
      int varSize;

      varSize = objectFile.readShort(offset + index * entrySize + sizeIdx);
      return varSize;
   }

   /**
    * @param index
    * @return
    * 
    * @throws IOException
    */
   public CType getType(int index) throws IOException
   {
      CType result;
      int type;
      if (offset < 0)
      {
         return new CInt(true, 4, endian);
      }

      type = objectFile.readShort(offset + index * entrySize + typeIdx);
      int typeSize = getSize(index);

      if (type == -1)
         throw new Error("Reading end mark"); // FIXME PATCH!!!
      switch (type & 31)
      {
      case T_ENUM:
         result = getEnumType(index);
         break;
      case T_CHAR:
         if (cchar == null)
         {
            if (!((typeSize == 1) || (typeSize == 2)))
            {
               throw new IOException("Only supports chars of size 1 or 2");
            }
            cchar = new CChar(false, typeSize, endian);
         }
         result = cchar;
         break;
      case T_U_CHAR:
         if (cuchar == null)
         {
            if (!((typeSize == 1) || (typeSize == 2)))
            {
               throw new IOException("Only supports chars of size 1 or 2");
            }
            cuchar = new CChar(true, typeSize, endian);
         }
         result = cuchar;
         break;
      case T_SHORT:
         if (cshort == null)
         {
            if (!((typeSize == 1) || (typeSize == 2) || (typeSize == 4)))
            {
               throw new IOException("Only supports shorts of size 1, 2 or 4");
            }
            cshort = new CShort(false, typeSize, endian);
         }
         result = cshort;
         break;
      case T_U_SHORT:
         if (cushort == null)
         {
            if (!((typeSize == 1) || (typeSize == 2) || (typeSize == 4)))
            {
               throw new IOException("Only supports shorts of size 1, 2 or 4");
            }
            cushort = new CShort(true, typeSize, endian);
         }
         result = cushort;
         break;
      case T_INT:
         if (cint == null)
         {
            if (!((typeSize == 1) || (typeSize == 2) || (typeSize == 4) || (typeSize == 8)))
            {
               throw new IOException("Only supports ints of size 1, 2, 4, or 8");
            }
            cint = new CInt(false, typeSize, endian);
         }
         result = cint;
         break;
      case T_U_INT:
         if (cuint == null)
         {
            if (!((typeSize == 1) || (typeSize == 2) || (typeSize == 4) || (typeSize == 8)))
            {
               throw new IOException("Only supports ints of size 1, 2, 4, or 8");
            }
            cuint = new CInt(true, typeSize, endian);
         }
         result = cuint;
         break;
      case T_LONG:
         if (clong == null)
         {
            if (!((typeSize == 2) || (typeSize == 4) || (typeSize == 8)))
            {
               throw new IOException("Only supports longs of size 2, 4, or 8");
            }
            clong = new CLong(false, typeSize, endian);
         }
         result = clong;
         break;
      case T_U_LONG:
         if (culong == null)
         {
            if (!((typeSize == 2) || (typeSize == 4) || (typeSize == 8)))
            {
               throw new IOException("Only supports longs of size 2, 4, or 8");
            }
            culong = new CLong(true, typeSize, endian);
         }
         result = culong;
         break;
      case T_LONG_LONG:
         if (clonglong == null)
         {
            if (!((typeSize == 2) || (typeSize == 4) || (typeSize == 8)))
            {
               throw new IOException(
                     "Only supports long longs of size 2, 4, or 8");
            }
            clonglong = new CLongLong(false, typeSize, endian);
         }
         result = clonglong;
         break;
      case T_U_LONG_LONG:
         if (culonglong == null)
         {
            if (!((typeSize == 2) || (typeSize == 4) || (typeSize == 8)))
            {
               throw new IOException(
                     "Only supports long longs of size 2, 4, or 8");
            }
            culonglong = new CLongLong(true, typeSize, endian);
         }
         result = culonglong;
         break;
      case T_FLOAT:
         if (cfloat == null)
         {
            cfloat = createFloatType(typeSize);
         }
         result = cfloat;
         break;
      case T_DOUBLE:
         if (cdouble == null)
         {
            cdouble = createFloatType(typeSize);
         }
         result = cdouble;
         break;
      case T_LONG_DOUBLE:
         if (clongdouble == null)
         {
            clongdouble = createFloatType(typeSize);
         }
         result = clongdouble;
         break;
      case T_STRUCT:
      {
         String name = getName(getMisc(index) - 1);

         if (!name.startsWith("struct"))
         {
            name = "struct " + name;
         }
         result = new CStruct(name, endian);
      }
         break;
      case T_UNION:
      {
         String name = getName(getMisc(index) - 1);

         if (!name.startsWith("union"))
         {
            name = "union " + name;
         }
         result = new CUnion(name, endian);
      }
         break;
      case T_POINTER:
         if (!((typeSize == 1) || (typeSize == 2) || (typeSize == 4) || (typeSize == 8)))
         {
            throw new IOException(
                  "Only supports pointers of size 1, 2, 4, or 8");
         }
         result = new CPointer(new CChar(false), typeSize, CType.BIG_ENDIAN);
         break;
      default:
         throw new IOException("Unsupported type " + type);
      }

      int indices = objectFile.readShort(offset + index * entrySize
            + indicesIdx);

      int firstIndex = -1;

      while (indices > 0)
      {
         if (firstIndex < 0)
         {
            firstIndex = objectFile.readShort(offset + index * entrySize
                  + indexIdx);
            if (indexPool < 0)
            {
               indexPool = oseSymbols.getIndexPool();
            }
         }
         indices--;
         int elements = objectFile.readShort(indexPool + (firstIndex + indices)
               * 2);

         if (result instanceof CArray)
         {
            result = new CArray(result, elements, result.getSize());
         }
         else
         {
            result = new CArray(result, elements, getSize(index));
         }
      }

      return result;
   }

   /**
    * @param index
    * @return
    */
   public boolean isLast(int index)
   {
      int flags = objectFile.readShort(offset + index * entrySize + flagsIdx);

      return (flags & FLAGS_US_LAST) > 0;
   }

}
