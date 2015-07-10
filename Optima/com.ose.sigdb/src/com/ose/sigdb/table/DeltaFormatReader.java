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

import com.ose.sigdb.CVariable;
import com.ose.sigdb.ctypes.CArray;
import com.ose.sigdb.ctypes.CInt;
import com.ose.sigdb.ctypes.CIntType;
import com.ose.sigdb.ctypes.CPrimType;
import com.ose.sigdb.ctypes.CType;
import com.ose.sigdb.ctypes.ComplexType;
import com.ose.sigdb.io.ObjectFile;
import com.ose.sigdb.util.Int;

/**
 * This class implements a signal table reader that reads the "Delta Format" as
 * outputed by sigdb.
 * 
 * @see com.ose.sigdb.table.SignalTableReader
 * @see com.ose.sigdb.io.ObjectFile
 */
public class DeltaFormatReader implements SignalTableReader
{
   private CPrimType signoType;

   private OseSymbols oseSymbols;

   private String fileName;

   private ObjectFile objectFile;

   private CPrimType cuint;

   /**
    * This is used to check if we may have a variable sized array at the end of
    * the signal. If we have that the lastType should be a CArray of size 1
    * after that a signal has been read.
    */
   private CType lastType;

   /**
    * @param file
    * @param name
    *           the file name of the object file to read.
    */
   public DeltaFormatReader(ObjectFile objectFile)
   {
      this.objectFile = objectFile;
      this.fileName = objectFile.getFile().getPath();
      lastType = null;
   }

   private void addComplexType(SignalDescriptionTable signalTable,
         VariableDescriptionTable varDescTable,
         SignalNameDescriptionTable sigTable, int sigVarIndex, int i, CType type)
         throws IOException
   {
      lastType = null;
      int size = varDescTable.getSize(sigVarIndex);

      Int currentVarDescIndex = new Int(sigVarIndex + 1);

      addSubMembers((ComplexType) type, currentVarDescIndex, varDescTable, 0,
            size);

      if ((lastType != null) && (lastType instanceof CArray)
            && (((CArray) lastType).getArraySize() == 1)
            && lastType.isPrimitive())
      {
         ((CArray) lastType).setVariableSize(true);
      }

      signalTable.addDescription(new SignalDescription(sigTable.getNumber(i),
            sigTable.getName(i), type));
   }

   private void addPrimType(SignalDescriptionTable signalTable,
         SignalNameDescriptionTable sigTable, int i, CType type)
         throws IOException
   {
      if ((signoType == null) && (type instanceof CIntType))
      {
         signoType = (CPrimType) type;
      }
      signalTable.addDescription(new SignalDescription(sigTable.getNumber(i),
            sigTable.getName(i), type));
   }

   /**
    * 
    * @param parent
    * @param currentVarDescIndex
    * @param varDescTable
    * @param startOffset
    * @param size
    * @return
    * 
    * @throws IOException
    */
   private Int addSubMembers(ComplexType parent, Int currentVarDescIndex,
         VariableDescriptionTable varDescTable, int startOffset, int size)
         throws IOException
   {
      int offset = 0;
      int prevSize;
      boolean isLast = false;
      boolean readOnly;

      if ((startOffset == 0) &&
          (oseSymbols.getMajorVersion() <= 2) &&
          (oseSymbols.getMinorVersion() == 0))
      {
         if (signoType == null)
         {
            if (cuint == null)
            {
               cuint = new CInt(true, 4, objectFile.getEndian());
            }
            signoType = cuint;
         }
         CVariable signoVar = new CVariable("signo", signoType, null,
               parent.getName());

         signoVar.setReadOnly(true);
         readOnly = false;
         parent.addMember(signoVar, 0);
         prevSize = signoType.getSize();
      }
      else if (startOffset == 0)
      {
         readOnly = true; // This will make first entry (signo) read only
         prevSize = 0;
      }
      else
      {
         readOnly = false;
         prevSize = 0;
      }

      Int varDescIndex = currentVarDescIndex;
      int psize = startOffset + offset + prevSize;
      
      while ((psize <= size) && !isLast)
      {
         CVariable variable;
         CType member = varDescTable.getType(varDescIndex.intValue());
         offset = varDescTable.getOffset(varDescIndex.intValue());

         if ((member instanceof CPrimType) || (member instanceof CArray))
         {
            boolean haveSteped = false;
            CType m = member;

            while (m instanceof CArray)
            {
               m = ((CArray) m).getSubType();
            }

            if ((signoType == null) && (m instanceof CIntType))
            {
               signoType = (CPrimType) m;
            }

            if ((m != member) && (m instanceof ComplexType))
            {
               int varDesc = varDescIndex.intValue();
               Int newIndex = new Int(varDescTable.getMisc(varDesc));
               Int nextIndex =
                  addSubMembers(
                     (ComplexType) m,
                     newIndex,
                     varDescTable,
                     startOffset + offset,
                     size);

               isLast = varDescTable.isLast(varDescIndex.intValue());
               // is FLAGS_US_LAST set ?
               if (nextIndex.intValue() > varDescIndex.intValue())
               {
                  varDescIndex = nextIndex;
               }
               else
               {
                  varDescIndex.inc();
               } // Get next entry.
               haveSteped = true;
            }

            variable = new CVariable(varDescTable.getName(varDescIndex
                  .intValue()), member, null, parent.getName());

            variable.setReadOnly(readOnly);
            readOnly = false;
            parent.addMember(variable, offset);

            if (!haveSteped)
            {
               isLast = varDescTable.isLast(varDescIndex.intValue());
               // is FLAGS_US_LAST set ?
               varDescIndex.inc(); // Get next entry.
            }

         }
         else if (member instanceof ComplexType)
         {
            int varDesc = varDescIndex.intValue();
            Int newIndex = new Int(varDescTable.getMisc(varDesc));

            Int nextIndex =
               addSubMembers(
                  (ComplexType) member,
                  newIndex,
                  varDescTable,
                  startOffset + offset,
                  size);

            parent.addMember(
               new CVariable(
                  varDescTable.getName(varDesc),
                  member,
                  null,
                  parent.getName()),
               offset);
            isLast = varDescTable.isLast(varDescIndex.intValue());
            // is FLAGS_US_LAST set ?
            if (nextIndex.intValue() > varDescIndex.intValue())
            {
               varDescIndex = nextIndex;
            }
            else
            {
               varDescIndex.inc();
            } // Get next entry.
            readOnly = false;
         }
         else
         {
            throw new RuntimeException("Unsupported type in confsig table");
         }
         
         prevSize = member.getSize();
         psize = startOffset + offset + prevSize;
         lastType = member;
      }
      return varDescIndex; // Return the next index.
   }

   /**
    * Get the file name of the object file.
    * 
    * @return object file name.
    */
   public String getName()
   {
      return fileName;
   }

   /**
    * @param suffix
    *           The name of the current suffix or null for the first suffix.
    * @return the next signal table suffix or null if no more signal tables.
    */
   public String getNextSuffix(String suffix)
   {
      return objectFile.getNextOseSymbolSuffix(suffix);
   }

   /**
    * @param table
    * 
    * @throws IOException
    */
   public SignalDescriptionTable read(String suffix) throws IOException
   {
      SignalDescriptionTable signalTable = null;

      verifyObjectFile();
      int endian = objectFile.getEndian();

      if (objectFile.findOseSymbolSuffix(suffix))
      {
         signalTable = new SignalDescriptionTable();
         oseSymbols = new OseSymbols(objectFile, suffix);

         if (oseSymbols.supportedVersion())
         {
            VariableDescriptionTable variableDescriptionTable = new VariableDescriptionTable(
                  objectFile, oseSymbols);
            SignalNameDescriptionTable signalDescriptionTable = new SignalNameDescriptionTable(
                  objectFile, oseSymbols, variableDescriptionTable);

            int sigVarIndex = 0;

            // for (int i = 0; (sigVarIndex = signalDescriptionTable
            // .getVarIndex(i)) >= 0; i++)
            for (int i = 0; sigVarIndex >= 0; i++)
            {
               CType type = variableDescriptionTable.getType(sigVarIndex);

               if (type instanceof ComplexType)
               {
                  addComplexType(signalTable, variableDescriptionTable,
                        signalDescriptionTable, sigVarIndex, i, type);

               }
               else
               {
                  addPrimType(signalTable, signalDescriptionTable, i, type);
               }

               sigVarIndex = signalDescriptionTable.getVarIndex(i + 1);
            }

            if (signoType == null)
            {
               // This should only happen if no normal signals are found
               if (cuint == null)
               {
                  cuint = new CInt(true, 4, endian);
               }
               signoType = cuint;
            }
         }
         else
         {
            throw new IOException("Unsupported version of the signal table.");
         }
      }

      return signalTable;
   }

   private boolean verifyObjectFile() throws IOException
   {

      if (objectFile == null)
      {
         throw new Error("No object file present.");
      }

      if (!objectFile.supportedFileType())
      {
         throw new IOException("The file " + fileName
               + " is not a supported file type.");
      }

      if (!objectFile.supportedVersion())
      {
         throw new IOException("Unsupported version of the signal table.");
      }

      return true;
   }
}
