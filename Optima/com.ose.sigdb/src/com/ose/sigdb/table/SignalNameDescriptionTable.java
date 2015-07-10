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

import com.ose.sigdb.io.ObjectFile;

class SignalNameDescriptionTable
{
   static final int ENTRY_SIZE = 12;

   static final int NAME = 0;

   static final int NUMBER = 4;

   static final int VAR = 8;

   private OseSymbols oseSymbols;

   private VariableDescriptionTable varDescTable;

   private int offset;

   private int size;

   private String symbolPrefix = "";

   private ObjectFile objectFile;

   /**
    * @param _oseSymbols
    * @param _varDescTable
    * 
    * @throws IOException
    */
   SignalNameDescriptionTable(ObjectFile objectFile, OseSymbols oseSymbols,
         VariableDescriptionTable varDescTable) throws IOException
   {
      this.oseSymbols = oseSymbols;
      this.varDescTable = varDescTable;
      offset = (int) objectFile.getSymbolValueIndex(symbolPrefix
            + "ose_sigdesc_tab", oseSymbols.getSymbolSuffix());
      if (offset <= 0)
      {
         // try a new symbol prefix before giving up.
         offset = (int) objectFile.getSymbolValueIndex("_" + "ose_sigdesc_tab",
               oseSymbols.getSymbolSuffix());
         if (offset > 0)
         {
            symbolPrefix = "_";
         }
      }
      if (offset <= 0)
      {
         throw new IOException("Corrupt confsig table (no ose_sigdesc_tab).");
      }
      size = oseSymbols.getSigdescSize();

      this.objectFile = objectFile;
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

      String name;

      if ((stringPool < 0) && (oseSymbols.getStringPoolSize() == 0))
      {
         return "<" + Integer.toString(getNumber(index)) + ">";
      }

      if (index * ENTRY_SIZE < size)
      {
         int nameIndex = objectFile.readInt(offset + index * ENTRY_SIZE + NAME);

         name = objectFile.getAsciiString(stringPool + nameIndex, 128);
      }
      else
      {
         name = null;
      }

      return name;
   }

   /**
    * @param index
    * @return
    * 
    * @throws IOException
    */
   public int getNumber(int index) throws IOException
   {
      int number = 0;

      if (index * ENTRY_SIZE < size)
      {
         number = objectFile.readInt(offset + index * ENTRY_SIZE + NUMBER);
      }
      else
      {
         throw new RuntimeException("Signal index out of bounds");
      }

      return number;
   }

   /**
    * @param index
    * @return
    * 
    * @throws IOException
    */
   public int getVarIndex(int index) throws IOException
   {
      int varIndex;

      if (index * ENTRY_SIZE < size)
      {
         varIndex = objectFile.readInt(offset + index * ENTRY_SIZE + VAR);
      }
      else
      {
         varIndex = -1;
      }

      return varIndex;
   }

   /**
    * @return
    */
   public String toString()
   {
      StringBuffer result = new StringBuffer(1024);

      try
      {
         int stringPool = oseSymbols.getStringPool();

         for (int pos = 0; pos + ENTRY_SIZE < size; pos += ENTRY_SIZE)
         {
            int nameIndex = objectFile.readInt(offset + pos + NAME);
            String name = objectFile
                  .getAsciiString(stringPool + nameIndex, 128);

            result.append(name);
            result.append(" ");
            result.append(objectFile.readInt(offset + pos + NUMBER));
            result.append(" ");
            result.append(varDescTable.getName(objectFile.readInt(offset + pos
                  + VAR)));
            result.append("\n");
         }
      }
      catch (IOException e)
      {
         e.printStackTrace(System.out);
         result = new StringBuffer(e.toString());
      }

      return result.toString();
   }

}
