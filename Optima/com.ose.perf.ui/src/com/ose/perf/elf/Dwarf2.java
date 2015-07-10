/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2011 by Enea Software AB.
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

package com.ose.perf.elf;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import com.ose.perf.ui.ProfilerPlugin;

class Dwarf2
{
   // Abbreviation forms
   private static final int DW_FORM_addr = 0x01;
   private static final int DW_FORM_block2 = 0x03;
   private static final int DW_FORM_block4 = 0x04;
   private static final int DW_FORM_data2 = 0x05;
   private static final int DW_FORM_data4 = 0x06;
   private static final int DW_FORM_data8 = 0x07;
   private static final int DW_FORM_string = 0x08;
   private static final int DW_FORM_block = 0x09;
   private static final int DW_FORM_block1 = 0x0a;
   private static final int DW_FORM_data1 = 0x0b;
   private static final int DW_FORM_flag = 0x0c;
   private static final int DW_FORM_sdata = 0x0d;
   private static final int DW_FORM_strp = 0x0e;
   private static final int DW_FORM_udata = 0x0f;
   private static final int DW_FORM_ref_addr = 0x10;
   private static final int DW_FORM_ref1 = 0x11;
   private static final int DW_FORM_ref2 = 0x12;
   private static final int DW_FORM_ref4 = 0x13;
   private static final int DW_FORM_ref8 = 0x14;
   private static final int DW_FORM_ref_udata = 0x15;
   private static final int DW_FORM_indirect = 0x16;

   // Attributes
   private static final int DW_AT_name = 0x03;
   private static final int DW_AT_stmt_list = 0x10;
   private static final int DW_AT_low_pc = 0x11;
   private static final int DW_AT_high_pc = 0x12;
   private static final int DW_AT_comp_dir = 0x1b;
   private static final int DW_AT_abstract_origin = 0x31;
   private static final int DW_AT_specification = 0x47;
   private static final int DW_AT_ranges = 0x55;
   private static final int DW_AT_MIPS_linkage_name = 0x2007;

   // Tags
   private static final int DW_TAG_entry_point = 0x03;
   private static final int DW_TAG_inlined_subroutine = 0x1d;
   private static final int DW_TAG_subprogram = 0x2e;

   // Standard opcodes
   private static final int DW_LNS_extended_op = 0;
   private static final int DW_LNS_copy = 1;
   private static final int DW_LNS_advance_pc = 2;
   private static final int DW_LNS_advance_line = 3;
   private static final int DW_LNS_set_file = 4;
   private static final int DW_LNS_set_column = 5;
   private static final int DW_LNS_negate_stmt = 6;
   private static final int DW_LNS_set_basic_block = 7;
   private static final int DW_LNS_const_add_pc = 8;
   private static final int DW_LNS_fixed_advance_pc = 9;

   // Extended opcodes
   private static final int DW_LNE_end_sequence = 1;
   private static final int DW_LNE_set_address = 2;
   private static final int DW_LNE_define_file = 3;
   private static final int DW_LNE_set_discriminator = 4;

   // Indicates that addresses should be treated as 64 bit addresses.
   private boolean addressesAre64Bit;

   private List<CompilationUnit> units = new ArrayList<CompilationUnit>();

   Dwarf2(boolean addressesAre64Bit)
   {
      this.addressesAre64Bit = addressesAre64Bit;
   }

   void parseCompilationUnits(ByteBuffer debugInfoSectionBuffer,
                              ByteBuffer debugAbbrevSectionBuffer,
                              ByteBuffer debugStrSectionBuffer,
                              ByteBuffer debugRangesSectionBuffer)
      throws InvalidDwarf2FormatError
   {
      Dwarf2Buffer section = new Dwarf2Buffer(debugInfoSectionBuffer);
      long unitPos = 0;

      // Iterate over all compilation units in the .debug_info section.
      while (section.hasMoreData())
      {
         List<AbbrevationInfo> abbrevList;
         AbbrevationInfo abbrev;
         AttrInfo attrRet = new AttrInfo();
         CompilationUnit unit = new CompilationUnit();
         unit.startPos = section.position();
         long lowPc = 0;
         long highPc = 0;
         long offsetSize;
         long length = section.getUWord();

         if (length == 0xFFFFFFFFL)
         {
            offsetSize = 8;
            length = section.getUWord64();
         }
         else if (length == 0)
         {
            offsetSize = 8;
            length = section.getUWord();
         }
         else
         {
            offsetSize = 4;
         }

         unitPos = section.position();
         int version = section.getUHalf();
         long abbrevNumber;
         long abbrevOffset;
         if (offsetSize == 4)
         {
            abbrevOffset = section.getUWord();
         }
         else
         {
            abbrevOffset = section.getUWord64();
         }
         short addrSize = section.getUByte();

         // Read the abbreviations for this compilation unit into a table.
         abbrevList = readAbbrevationTable(debugAbbrevSectionBuffer, abbrevOffset);

         abbrevNumber = section.getULeb128();
         abbrev = lookupAbbreviation(abbrevNumber, abbrevList);

         unit.version = version;
         unit.addrSize = addrSize;
         unit.offsetSize = offsetSize;
         unit.abbrevs = abbrevList;

         for (int i = 0; i < abbrev.numAttrs; i++)
         {
            AttrInfo attr = abbrev.attrs.get(i);
            readAttribute(section, debugStrSectionBuffer, attr, attrRet, unit);

            switch ((int) attrRet.name)
            {
               case DW_AT_stmt_list:
                  unit.stmtList = 1;
                  unit.lineOffset = attrRet.val;
                  break;
               case DW_AT_name:
                  unit.name = attrRet.str;
                  break;
               case DW_AT_low_pc:
                  lowPc = attrRet.val;
                  unit.baseAddress = lowPc;
                  break;
               case DW_AT_high_pc:
                  highPc = attrRet.val;
                  break;
               case DW_AT_ranges:
                  readRangeList(unit, unit.arrangeList,
                        debugRangesSectionBuffer, attrRet.val);
                  break;
               case DW_AT_comp_dir:
                  String compDir = attrRet.str;
                  unit.compDir = compDir;
                  break;
               default:
                  break;
            }
         }

         if (highPc != 0)
         {
            addArrange(unit, unit.arrangeList, lowPc, highPc);
         }
         if (section.hasMoreData())
         {
            scanCompUnitForSymbols(section, debugStrSectionBuffer,
                  debugRangesSectionBuffer, unit);
         }
         units.add(unit);
         section.setPosition(unitPos + length);
      }
   }

   Symbol getSymbol(long addressToFind)
   {
      Symbol symbol = null;
      FunctionInfo funcInfo = null;

      for (Iterator i = units.iterator(); i.hasNext();)
      {
         CompilationUnit unit = (CompilationUnit) i.next();

         if (compUnitContainsAddress(unit, addressToFind))
         {
            funcInfo = lookupAddressInFuncTable(unit, addressToFind);
            if (funcInfo != null)
            {
               break;
            }
         }
      }

      if (funcInfo != null)
      {
         symbol = new Symbol(funcInfo.name, funcInfo.current.low,
               (funcInfo.current.high - funcInfo.current.low), true);
      }

      return symbol;
   }

   FileLineInfo getFileLineInfo(ByteBuffer sectionBuffer, long addressToFind)
      throws InvalidDwarf2FormatError
   {
      Dwarf2Buffer section = new Dwarf2Buffer(sectionBuffer);
      FileLineInfo fileLineInfo = null;

      // Iterate over the compilation unit statement programs.
      while (section.hasMoreData())
      {
         // Get the statement program.
         StatementProgram program = new StatementProgram(section);

         // Interpret the statement program until it ends
         // or the address is found.
         fileLineInfo = interpretStatementProgram(program, addressToFind);
         if (fileLineInfo != null)
         {
            break;
         }
      }

      return fileLineInfo;
   }

   /*
    * In DWARF 2, the description of the debugging information is stored
    * in a separate .debug_abbrev section.
    */
   private List<AbbrevationInfo> readAbbrevationTable(
         ByteBuffer abbrevSectionBuffer, long abbrevOffset)
   {
      abbrevSectionBuffer.mark();
      abbrevSectionBuffer.position(abbrevSectionBuffer.position()
            + (int) abbrevOffset);
      Dwarf2Buffer section = new Dwarf2Buffer(abbrevSectionBuffer);
      long abbrevNumber = section.getULeb128();
      List<AbbrevationInfo> abbrevList = new ArrayList<AbbrevationInfo>();

      while (abbrevNumber != 0)
      {
         AbbrevationInfo abbrev = new AbbrevationInfo(abbrevNumber);
         abbrev.tag = section.getULeb128();
         abbrev.hasChildren = section.getSByte();
         long abbrevName = section.getULeb128();
         long abbrevForm = section.getULeb128();
         while (abbrevName != 0)
         {
            AttrInfo attrInfo = new AttrInfo(abbrevName, abbrevForm);
            abbrev.attrs.add(attrInfo);
            abbrevName = section.getULeb128();
            abbrevForm = section.getULeb128();
         }
         abbrev.numAttrs = abbrev.attrs.size();
         abbrevList.add(abbrev);
         abbrevNumber = section.getULeb128();
      }

      abbrevSectionBuffer.reset();
      return abbrevList;
   }

   private AbbrevationInfo lookupAbbreviation(long abbrevNumber,
         List<AbbrevationInfo> abbrevList)
   {
      for (Iterator<AbbrevationInfo> i = abbrevList.iterator(); i.hasNext();)
      {
         AbbrevationInfo abbrev = i.next();
         if (abbrev.number == abbrevNumber)
         {
            return abbrev;
         }
      }

      return null;
   }

   private void readAttribute(Dwarf2Buffer section, ByteBuffer strSection,
         AttrInfo attr, AttrInfo attrRet, CompilationUnit unit)
   {
      attrRet.name = attr.name;
      readAttribute(section, strSection, attrRet, attr.form, unit);
   }

   private AttrInfo readAttribute(Dwarf2Buffer section, ByteBuffer strSection,
         AttrInfo attrRet, long form, CompilationUnit unit)
   {
      DwarfBlock block = new DwarfBlock();
      attrRet.form = form;

      switch ((int) form)
      {
         case DW_FORM_ref_addr:
         {
            if (unit.version == 3)
            {
               if (unit.offsetSize == 4)
               {
                  attrRet.val = section.getUWord();
               }
               else
               {
                  attrRet.val = section.getUWord64();
               }
            }
            break;
         }
         case DW_FORM_addr:
         {
            attrRet.val = readAddress(section, unit);
            break;
         }
         case DW_FORM_block2:
         {
            block.size = section.getUHalf();
            block.data = section.getNBytes(block.size);
            attrRet.block = block;
            break;
         }
         case DW_FORM_block4:
         {
            block.size = section.getUWord();
            block.data = section.getNBytes(block.size);
            attrRet.block = block;
            break;
         }
         case DW_FORM_data2:
            attrRet.val = section.getUHalf();
            break;
         case DW_FORM_data4:
            attrRet.val = section.getUWord();
            break;
         case DW_FORM_data8:
            attrRet.val = section.getUWord64();
            break;
         case DW_FORM_string:
            attrRet.str = section.getString();
            break;
         case DW_FORM_strp:
            long offset;
            if (unit.offsetSize == 4)
            {
               offset = section.getUWord();
            }
            else
            {
               offset = section.getUWord64();
            }
            strSection.position(strSection.position() + (int) offset);
            Dwarf2Buffer strBuffer = new Dwarf2Buffer(strSection);
            attrRet.str = strBuffer.getString();
            strSection.position(0);
            break;
         case DW_FORM_block:
            block.size = section.getULeb128();
            block.data = section.getNBytes(block.size);
            attrRet.block = block;
            break;
         case DW_FORM_block1:
            block.size = section.getUByte();
            block.data = section.getNBytes(block.size);
            attrRet.block = block;
            break;
         case DW_FORM_data1:
            attrRet.val = section.getUByte();
            break;
         case DW_FORM_flag:
            attrRet.val = section.getUByte();
            break;
         case DW_FORM_sdata:
            attrRet.sval = section.getSLeb128();
            break;
         case DW_FORM_udata:
            attrRet.val = section.getULeb128();
            break;
         case DW_FORM_ref1:
            attrRet.val = section.getUByte();
            break;
         case DW_FORM_ref2:
            attrRet.val = section.getUHalf();
            break;
         case DW_FORM_ref4:
            attrRet.val = section.getUWord();
            break;
         case DW_FORM_ref8:
            attrRet.val = section.getUWord64();
            break;
         case DW_FORM_ref_udata:
            attrRet.val = section.getULeb128();
            break;
         case DW_FORM_indirect:
            form = section.getULeb128();
            attrRet = readAttribute(section, strSection, attrRet, form, unit);
            break;
         default:
            break;
      }

      return attrRet;
   }

   private void readRangeList(CompilationUnit unit, List arrangeList,
         ByteBuffer rangeBuffer, long offset)
   {
      long baseAddr = unit.baseAddress;
      rangeBuffer.mark();
      rangeBuffer.position(rangeBuffer.position() + (int) offset);
      Dwarf2Buffer strBuffer = new Dwarf2Buffer(rangeBuffer);

      for (;;)
      {
         long lowPc = readAddress(strBuffer, unit);
         long highPc = readAddress(strBuffer, unit);
         if (lowPc == 0 && highPc == 0)
         {
            break;
         }
         if (lowPc == -1 && highPc == -1)
         {
            baseAddr = highPc;
         }
         else
         {
            addArrange(unit, arrangeList, baseAddr + lowPc, baseAddr + highPc);
         }
      }

      rangeBuffer.reset();
   }

   private void addArrange(CompilationUnit unit, List arrangeList, long lowPc,
         long highPc)
   {
      for (Iterator i = arrangeList.iterator(); i.hasNext();)
      {
         Arrange arr = (Arrange) i.next();
         if (lowPc == arr.high)
         {
            arr.high = highPc;
            return;
         }
         if (highPc == arr.low)
         {
            arr.low = lowPc;
            return;
         }
      }

      Arrange arr = new Arrange();
      arr.low = lowPc;
      arr.high = highPc;
      arrangeList.add(arr);
   }

   private void scanCompUnitForSymbols(Dwarf2Buffer section,
                                       ByteBuffer debugStrSectionBuffer,
                                       ByteBuffer debugRangesSectionBuffer,
                                       CompilationUnit unit)
   {
      int nestingLevel = 1;

      while (nestingLevel != 0)
      {
         long lowPc = 0;
         long highPc = 0;
         AttrInfo attrRet = new AttrInfo();
         FunctionInfo func = null;
         long abbrevNumber = section.getULeb128();

         if (abbrevNumber == 0)
         {
            nestingLevel--;
            continue;
         }

         AbbrevationInfo abbrev = lookupAbbreviation(abbrevNumber, unit.abbrevs);
         if ((abbrev.tag == DW_TAG_subprogram)
               || (abbrev.tag == DW_TAG_entry_point)
               || (abbrev.tag == DW_TAG_inlined_subroutine))
         {
            func = new FunctionInfo();
            func.tag = (int) abbrev.tag;
         }

         for (int i = 0; i < abbrev.numAttrs; i++)
         {
            AttrInfo attr = abbrev.attrs.get(i);
            readAttribute(section, debugStrSectionBuffer, attr, attrRet, unit);

            if (func != null)
            {
               switch ((int) attrRet.name)
               {
                  case DW_AT_abstract_origin:
                     long pos = section.position();
                     String name = findAbstractInstanceName(unit, section,
                           debugStrSectionBuffer, attrRet);
                     func.name = name;
                     section.setPosition(pos);
                     break;
                  case DW_AT_name:
                     func.name = attrRet.str;
                     break;
                  case DW_AT_MIPS_linkage_name:
                     func.name = attrRet.str;
                     break;
                  case DW_AT_low_pc:
                     lowPc = attrRet.val;
                     break;
                  case DW_AT_high_pc:
                     highPc = attrRet.val;
                     break;
                  case DW_AT_ranges:
                     readRangeList(unit, func.arrList, debugRangesSectionBuffer,
                           attrRet.val);
                     break;
                  default:
                     break;
               }
            }
         }

         if (func != null && highPc != 0)
         {
            addArrange(unit, func.arrList, lowPc, highPc);
         }
         if (func != null && func.name != null)
         {
            unit.funcList.add(func);
         }
         if (abbrev.hasChildren != 0)
         {
            nestingLevel++;
         }
      }
   }

   private boolean compUnitContainsAddress(CompilationUnit unit, long address)
   {
      for (Iterator i = unit.arrangeList.iterator(); i.hasNext();)
      {
         Arrange arr = (Arrange) i.next();
         if (address > arr.low && address < arr.high)
         {
            return true;
         }
      }

      return false;
   }

   private FunctionInfo lookupAddressInFuncTable(CompilationUnit unit,
         long address)
   {
      FunctionInfo bestFit = null;

      for (Iterator i = unit.funcList.iterator(); i.hasNext();)
      {
         FunctionInfo func = (FunctionInfo) i.next();
         if (func.arrList != null)
         {
            for (Iterator j = func.arrList.iterator(); j.hasNext();)
            {
               Arrange arr = (Arrange) j.next();
               if (address >= arr.low && address < arr.high)
               {
                  if ((bestFit == null)
                        || ((arr.high - arr.low) < (bestFit.current.high - bestFit.current.low)))
                  {
                     bestFit = func;
                     if (bestFit.current == null)
                     {
                        bestFit.current = new Arrange();
                     }
                     bestFit.current.high = arr.high;
                     bestFit.current.low = arr.low;
                  }
               }
            }
         }
      }

      return bestFit;
   }

   private long readAddress(Dwarf2Buffer section, CompilationUnit unit)
   {
      switch (unit.addrSize)
      {
         case 8:
            return section.getUWord64();
         case 4:
            return section.getUWord();
         case 2:
            return section.getULeb128();
         default:
            // Some error case...
            return 0;
      }
   }

   private String findAbstractInstanceName(CompilationUnit unit,
         Dwarf2Buffer section, ByteBuffer debugStrSectionBuffer, AttrInfo attr)
   {
      long abbrevNumber = 0;
      long dieRef = attr.val;
      String name = null;
      AttrInfo attrRet = new AttrInfo();

      if (attr.form == DW_FORM_ref_addr)
      {
         if (dieRef == 0)
         {
            return null;
         }
         section.setPosition(section.position() + dieRef);
      }
      else
      {
         section.setPosition(unit.startPos + dieRef);
      }
      abbrevNumber = section.getULeb128();
      AbbrevationInfo abbrev = lookupAbbreviation(abbrevNumber, unit.abbrevs);

      for (int i = 0; i < abbrev.numAttrs; i++)
      {
         AttrInfo attr1 = abbrev.attrs.get(i);
         readAttribute(section, debugStrSectionBuffer, attr1, attrRet, unit);

         switch ((int) attrRet.name)
         {
            case DW_AT_name:
               if (name == null)
               {
                  name = attrRet.str;
               }
               break;
            case DW_AT_specification:
               name = findAbstractInstanceName(unit, section,
                     debugStrSectionBuffer, attrRet);
               break;
            case DW_AT_MIPS_linkage_name:
               name = attrRet.str;
               break;
            default:
               break;
         }
      }

      return name;
   }

   private FileLineInfo checkAddress(StatementProgram program,
         long lowerAddress,
         long upperAddress,
         long addressToFind)
   {
      // Check if the address has been found.
      if (UnsignedArithmetics.isLessThanOrEqualTo(lowerAddress, addressToFind)
            && UnsignedArithmetics.isGreaterThan(upperAddress, addressToFind))
      {
         StateMachineRegisters snapshot = program.getRegistersSnapshot();
         FileEntry fileEntry = (FileEntry) program.getFiles().get(
               (int)(snapshot.getFile() - 1));

         String directory = null;
         if (fileEntry.getDirectoryIndex() != 0)
         {
            directory = (String) program.getIncludeDirectories().get(
                  (int)(fileEntry.getDirectoryIndex() - 1));
         }

         FileLineInfo info = new FileLineInfo(addressToFind, directory,
               fileEntry.getFilename(), snapshot.getLine());

         return info;
      }
      else
      {
         return null;
      }
   }

   private void interpretEndSequence(StatementProgram program,
         StateMachineRegisters registers)
   {
      // Set the end_sequence register of the state machine to "true" and
      // append a row to the matrix using the current values of the state
      // machine registers. Then reset the registers to the initial values.

      // Every statement program sequence must end with an end sequence
      // instruction which creates a row whose address is that of the
      // byte after the last target machine instruction of the sequence.

      registers.setIsEndSequence(true);

      registers.reset(program.isDefaultIsStatement());

      program.setRegistersSnapshot(registers.getSnapshot());
   }

   private void interpretSetAddress(StatementProgram program,
         StateMachineRegisters registers)
   {
      // Takes a single relocatable address as an operand. The size of the
      // operand is the size appropriate to hold an address on the target
      // machine. Set the address register to the value given by the
      // relocatable address.

      // All the other statement program opcodes that affect the address
      // register add a delta to it. This instruction stores a relocatable
      // value into it instead.

      long address;

      if (addressesAre64Bit)
      {
         address = program.getProgramBuffer().get64BitAddress();
      }
      else
      {
         address = program.getProgramBuffer().get32BitAddress();
      }

      registers.setAddress(address);
   }

   private void interpretDefineFile(StatementProgram program,
         StateMachineRegisters registers)
   {
      // Takes 4 arguments. The first is a null terminated string
      // containing a source file name. The second is an unsigned
      // LEB128 number representing the directory index of the
      // directory in which the file was found. The third is an
      // unsigned LEB128 number representing the time of last
      // modification of the file. The time and length fields may
      // contain LEB128(0) if the information is not available.

      // The directory index represents an entry in the include
      // directories section of the statement program prologue.
      // The index is LEB128(0) if the file was found in the
      // current directory of the compilation, LEB128(1) if it was
      // found in the first directory in the include directories
      // section, and so on. The directory index is ignored for
      // file names that represent full path names.

      // The files are numbered, starting at 1, in the order in
      // which they appear; the names in the prologue come before
      // names defined by the define file instruction. These
      // numbers are used in the file register of the state machine.

      Dwarf2Buffer buffer = program.getProgramBuffer();

      FileEntry fileEntry = new FileEntry();
      fileEntry.setFilename(buffer.getString());
      fileEntry.setDirectoryIndex(buffer.getULeb128());
      /* long lastModified = */ buffer.getULeb128();
      /* long length = */ buffer.getULeb128();

      program.addFile(fileEntry);
   }

   private void interpretExtendedOp(StatementProgram program,
         StateMachineRegisters registers)
   {
      Dwarf2Buffer buffer = program.getProgramBuffer();

      /* long length = */ buffer.getULeb128();
      short subOpcode = buffer.getUByte();

      switch (subOpcode)
      {
         case DW_LNE_end_sequence:
            interpretEndSequence(program, registers);
            break;
         case DW_LNE_set_address:
            interpretSetAddress(program, registers);
            break;
         case DW_LNE_define_file:
            interpretDefineFile(program, registers);
            break;
         case DW_LNE_set_discriminator:
            buffer.getULeb128();
            break;
         default:
            // This should not happen. Pretend it did not happen.
            break;
      }
   }

   private void interpretCopy(StatementProgram program,
         StateMachineRegisters registers,
         long addressToFind)
   {
      // Takes no arguments. Append a row to the matrix using the current
      // values of the state machine registers. Then set the basic_block
      // register to "false".

      program.setRegistersSnapshot(registers.getSnapshot());

      registers.setIsBasicBlock(false);
   }

   private FileLineInfo interpretAdvancePC(StatementProgram program,
         StateMachineRegisters registers,
         long addressToFind)
   {
      // Takes a single unsigned LEB128 operand, multiplies it by the
      // minimum_instruction_length field of the prologue, and adds the
      // result to the address register of the state machine.
      long delta = program.getProgramBuffer().getULeb128()
         * program.getMinimumInstructionLength();

      FileLineInfo fileLineInfo = checkAddress(program,
                                               registers.getAddress(),
                                               registers.getAddress() + delta,
                                               addressToFind);
      registers.addToAddress(delta);

      return fileLineInfo;
   }

   private void interpretAdvanceLine(StatementProgram program,
         StateMachineRegisters registers)
   {
      // Takes a single signed LEB128 operand and adds that value to
      // the line register of the state machine.
      long delta = program.getProgramBuffer().getSLeb128();
      registers.addToLine(delta);
   }

   private void interpretSetFile(StatementProgram program,
         StateMachineRegisters registers)
   {
      // Takes a single unsigned LEB128 operand and stores it in the file
      // register of the state machine.
      registers.setFile(program.getProgramBuffer().getULeb128());
   }

   private void interpretSetColumn(StatementProgram program,
         StateMachineRegisters registers)
   {
      // Takes a single unsigned LEB128 operand and stores it in the column
      // register of the state machine.
      registers.setColumn(program.getProgramBuffer().getULeb128());
   }

   private void interpretNegateStmt(StatementProgram program,
         StateMachineRegisters registers)
   {
      // Takes no arguments. Set the is_stmt register of the state machine
      // to the logical negation of its current value.
      registers.toggleIsStatement();
   }

   private void interpretSetBasicBlock(StatementProgram program,
         StateMachineRegisters registers)
   {
      // Takes no arguments. Set the basic_block register of the state
      // machine to "true".
      registers.setIsBasicBlock(true);
   }

   private FileLineInfo interpretConstAddPC(StatementProgram program,
         StateMachineRegisters registers, long addressToFind)
   {
      // Takes no arguments. Add to the address register of the state
      // machine the address increment value corresponding to special
      // opcode 255.
      long delta = ((255 - program.getOpcodeBase()) / program.getLineRange())
            * program.getMinimumInstructionLength();

      FileLineInfo fileLineInfo = checkAddress(program,
            registers.getAddress(),
            registers.getAddress() + delta,
            addressToFind);

      registers.addToAddress(delta);

      return fileLineInfo;
   }

   private FileLineInfo interpretFixedAdvancePC(StatementProgram program,
         StateMachineRegisters registers, long addressToFind)
   {
      // Takes a single uhalf operand. Add to the address register of
      // the state machine the value of the (unencoded) operand. This is
      // the only extended opcode that takes an argument that is not
      // a variable length number.
      long delta = program.getProgramBuffer().getUHalf();

      FileLineInfo fileLineInfo = checkAddress(program,
            registers.getAddress(),
            registers.getAddress() + delta,
            addressToFind);

      registers.addToAddress(delta);

      return fileLineInfo;
   }

   private FileLineInfo interpretSpecialOpcode(StatementProgram program,
         StateMachineRegisters registers,
         long addressToFind, short opcode)
   {
      // The spec says you are supposed to do this for special opcodes.
      short decodedOpcode = (short) (opcode - program.getOpcodeBase());
      long lineDelta = program.getLineBase()
         + (int) (decodedOpcode % program.getLineRange());
      long addressDelta = (decodedOpcode / program.getLineRange())
         * program.getMinimumInstructionLength();

      FileLineInfo fileLineInfo = checkAddress(program,
                                        registers.getAddress(),
                                        registers.getAddress() + addressDelta,
                                        addressToFind);

      registers.addToLine(lineDelta);
      registers.addToAddress(addressDelta);

      program.setRegistersSnapshot(registers.getSnapshot());

      return fileLineInfo;
   }

   private FileLineInfo interpretStatementProgram(StatementProgram program,
         long addressToFind)
   {
      FileLineInfo fileLineInfo = null;
      StateMachineRegisters registers;

      registers = new StateMachineRegisters(program.isDefaultIsStatement());

      program.setRegistersSnapshot(registers.getSnapshot());

      while (program.getProgramBuffer().hasMoreData())
      {
         short opcode = program.getProgramBuffer().getUByte();

         switch (opcode)
         {
            case DW_LNS_extended_op:
               interpretExtendedOp(program, registers);
               break;
            case DW_LNS_copy:
               interpretCopy(program, registers, addressToFind);
               break;
            case DW_LNS_advance_pc:
               fileLineInfo = interpretAdvancePC(program, registers,
                     addressToFind);
               break;
            case DW_LNS_advance_line:
               interpretAdvanceLine(program, registers);
               break;
            case DW_LNS_set_file:
               interpretSetFile(program, registers);
               break;
            case DW_LNS_set_column:
               interpretSetColumn(program, registers);
               break;
            case DW_LNS_negate_stmt:
               interpretNegateStmt(program, registers);
               break;
            case DW_LNS_set_basic_block:
               interpretSetBasicBlock(program, registers);
               break;
            case DW_LNS_const_add_pc:
               fileLineInfo = interpretConstAddPC(program, registers,
                     addressToFind);
               break;
            case DW_LNS_fixed_advance_pc:
               fileLineInfo = interpretFixedAdvancePC(program, registers,
                     addressToFind);
               break;
            default:
               fileLineInfo = interpretSpecialOpcode(program, registers,
                     addressToFind, opcode);
               break;
         }

         // If file/line info for address was found, break out of loop
         if (fileLineInfo != null)
         {
            break;
         }
      }

      return fileLineInfo;
   }

   private class StateMachineRegisters implements Cloneable
   {
      private long address;
      private long file;
      private long line;
      private long column;
      private boolean isStatement;
      private boolean isBasicBlock;
      private boolean isEndSequence;

      private long baseAddress;

      StateMachineRegisters(boolean defaultIsStatement)
      {
         reset(defaultIsStatement);
      }

      void reset(boolean defaultIsStatement)
      {
         address = 0;
         file = 1;
         line = 1;
         column = 0;
         isStatement = defaultIsStatement;
         isBasicBlock = false;
         isEndSequence = false;
      }

      StateMachineRegisters getSnapshot()
      {
         // We don't need a deep copy.
         StateMachineRegisters snapshot = null;
         try
         {
            snapshot = (StateMachineRegisters) this.clone();
         }
         catch (CloneNotSupportedException e)
         {
            // This really should not happen.
            ProfilerPlugin.log(e);
            e.printStackTrace();
         }
         return snapshot;
      }

      long getAddress()
      {
         return address;
      }

      void setAddress(long address)
      {
         this.address = address;
         baseAddress = address;
      }

      void addToAddress(long delta)
      {
         address += delta;
      }

      long getBaseAddress()
      {
         return baseAddress;
      }

      long getFile()
      {
         return file;
      }

      void setFile(long fileIndex)
      {
         file = fileIndex;
      }

      long getLine()
      {
         return line;
      }

      void addToLine(long delta)
      {
         line += delta;
      }

      long getColumn()
      {
         return column;
      }

      void setColumn(long column)
      {
         this.column = column;
      }

      boolean isStatement()
      {
         return isStatement;
      }

      void toggleIsStatement()
      {
         isStatement = !isStatement;
      }

      boolean isBasicBlock()
      {
         return isBasicBlock;
      }

      void setIsBasicBlock(boolean isBasicBlock)
      {
         this.isBasicBlock = isBasicBlock;
      }

      boolean isEndSequence()
      {
         return isEndSequence;
      }

      void setIsEndSequence(boolean isEndSequence)
      {
         this.isEndSequence = isEndSequence;
      }
   }

   private class StatementProgram
   {
      // Prologue header fields
      private long totalLength;
      private int version;
      private long prologueLength;
      private short minimumInstructionLength;
      private boolean defaultIsStatement;
      private byte lineBase;
      private short lineRange;
      private short opcodeBase;
      private short[] standardOpcodeLengths;
      private final List includeDirectories = new ArrayList();
      private final List files = new ArrayList();

      private Dwarf2Buffer programBuffer;
      private int programLength;

      private StateMachineRegisters registersSnapshot;

      StatementProgram(Dwarf2Buffer buffer) throws InvalidDwarf2FormatError
      {
         readPrologue(buffer);

         // Extract entire statement program as a separate buffer.
         programBuffer = buffer.getSubBuffer(programLength);
      }

      long getTotalLength()
      {
         return totalLength;
      }

      int getVersion()
      {
         return version;
      }

      long getPrologueLength()
      {
         return prologueLength;
      }

      short getMinimumInstructionLength()
      {
         return minimumInstructionLength;
      }

      boolean isDefaultIsStatement()
      {
         return defaultIsStatement;
      }

      byte getLineBase()
      {
         return lineBase;
      }

      short getLineRange()
      {
         return lineRange;
      }

      short getOpcodeBase()
      {
         return opcodeBase;
      }

      short[] getStandardOpcodeLengths()
      {
         return standardOpcodeLengths;
      }

      List getIncludeDirectories()
      {
         return includeDirectories;
      }

      void addIncludeDirectory(String directory)
      {
         includeDirectories.add(directory);
      }

      List getFiles()
      {
         return files;
      }

      void addFile(FileEntry file)
      {
         files.add(file);
      }

      Dwarf2Buffer getProgramBuffer()
      {
         return programBuffer;
      }

      int getProgramLength()
      {
         return programLength;
      }

      StateMachineRegisters getRegistersSnapshot()
      {
         return registersSnapshot;
      }

      void setRegistersSnapshot(StateMachineRegisters snapshot)
      {
         registersSnapshot = snapshot;
      }

      private void readIncludeDirectories(Dwarf2Buffer buffer)
      {
         // Read include directories.
         String includePath = buffer.getString();
         while (includePath.length() > 0)
         {
            addIncludeDirectory(includePath);
            includePath = buffer.getString();
         }
      }

      private void readFileEntries(Dwarf2Buffer buffer)
      {
         // Read file entries
         String filename = buffer.getString();
         while (filename.length() > 0)
         {
            FileEntry fileEntry = new FileEntry();
            fileEntry.setFilename(filename);
            fileEntry.setDirectoryIndex(buffer.getULeb128());
            /* long lastModified = */ buffer.getULeb128();
            /* long length = */ buffer.getULeb128();
            addFile(fileEntry);
            filename = buffer.getString();
         }
      }

      private void readStandardOpcodeLengths(Dwarf2Buffer buffer)
      {
         standardOpcodeLengths = new short[opcodeBase];
         for (int i = 1; i < opcodeBase; i++)
         {
            standardOpcodeLengths[i] = buffer.getUByte();
         }
      }

      private void readPrologue(Dwarf2Buffer buffer)
         throws InvalidDwarf2FormatError
      {
         long marker64Bit = buffer.peekUWord();
         boolean is64BitFormat = (marker64Bit == 0xFFFFFFFFL);

         if (is64BitFormat)
         {
            buffer.getUWord();
         }

         // Read statement program prologue fields.
         if (is64BitFormat)
         {
            totalLength = buffer.getUWord64();
         }
         else
         {
            totalLength = buffer.getUWord();
         }
         long startOfPrologue = buffer.position();

         version = buffer.getUHalf();

         if (is64BitFormat)
         {
            prologueLength = buffer.getUWord64();
         }
         else
         {
            prologueLength = buffer.getUWord();
         }

         minimumInstructionLength = buffer.getUByte();
         defaultIsStatement = buffer.getBoolean();
         lineBase = buffer.getSByte();
         lineRange = buffer.getUByte();
         opcodeBase = buffer.getUByte();

         // Make sure the Dwarf version is 2.
         if (version != 2)
         {
            throw new InvalidDwarf2FormatError();
         }

         readStandardOpcodeLengths(buffer);

         readIncludeDirectories(buffer);

         readFileEntries(buffer);

         long endOfPrologue = buffer.position();

         // Calculate the length of the statement program data.
         programLength = (int)(totalLength
               - (endOfPrologue - startOfPrologue));
      }
   }

   private class FileEntry
   {
      private String filename;
      private long directoryIndex;

      String getFilename()
      {
         return filename;
      }

      void setFilename(String filename)
      {
         this.filename = filename;
      }

      long getDirectoryIndex()
      {
         return directoryIndex;
      }

      void setDirectoryIndex(long directoryIndex)
      {
         this.directoryIndex = directoryIndex;
      }
   }

   private class CompilationUnit
   {
      List<AbbrevationInfo> abbrevs;
      int version;
      int stmtList;
      short addrSize;
      long offsetSize;
      long lineOffset;
      long baseAddress;
      long startPos;
      String name;
      String compDir;
      List<Arrange> arrangeList = new ArrayList<Arrange>();
      List<FunctionInfo> funcList = new ArrayList<FunctionInfo>();
   }

   private class AttrInfo
   {
      long name;
      long form;
      long val;
      long sval;
      String str;
      DwarfBlock block;

      AttrInfo()
      {
      }

      AttrInfo(long name, long form)
      {
         this.name = name;
         this.form = form;
      }
   }

   private class DwarfBlock
   {
      long size;
      String data;
   }

   private class AbbrevationInfo
   {
      // Number identifying the abbreviation.
      long number;
      // DWARF tag.
      long tag;
      // Boolean.
      int hasChildren;
      // Number of attributes.
      int numAttrs;
      // An array of attribute descriptions.
      List<AttrInfo> attrs = new ArrayList<AttrInfo>();

      AbbrevationInfo(long number)
      {
         this.number = number;
      }
   }

   private class Arrange
   {
      long low;
      long high;
   }

   private class FunctionInfo
   {
      String name;
      int tag;
      Arrange current;
      List<Arrange> arrList = new ArrayList<Arrange>();
   }
}
