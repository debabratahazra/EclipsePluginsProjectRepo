/*******************************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: DebugAbbrevStructure
 * @version: 1.0
 * Date: June 2009 
 * @author Debabrata Hazra
 * Description: Create Data Structure of .debug_abbrev Section from ELF file.
 *******************************************************************************/

package dwarf.section;


import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import dwarf.lookup.DW_AT;
import dwarf.lookup.DW_FORM;
import dwarf.lookup.DW_TAG;

import read.elf.ReadHeader;
import dwarf.internal.ByteToString;

public class DebugAbbrevStructure {

	private byte abbrev_cu_index;
	private String abbrev_cu_tag;			//DW_TAG_*
	private byte abbrev_cu_child;
	private String abbrev_cu_attribute;		//DW_AT_*
	private String abbrev_cu_form;			//DW_FORM_*

	private BufferedWriter writer = null;

	public DebugAbbrevStructure() {		
		try {			
			writer = new BufferedWriter(new FileWriter(new File(".debug_abbrev.txt")));
		} catch (IOException e) {}	
	}


	/**
	 * Read .debug_abbrev Section [Compilation Unit (CU)] from ELF file and
	 * Create Data Structure in .debug_abbrev.txt file.
	 * @param debug_abbrev
	 * @throws IOException
	 */
	public void readCU(byte[] debug_abbrev) throws IOException{
		int lastIndex;
		for (int i = 0; i < debug_abbrev.length-1; ) {
			abbrev_cu_index = Byte.parseByte(ByteToString.byte2StringArray(debug_abbrev, i, 1, ReadHeader.EI_DATA), 16);
			if(abbrev_cu_index==0){				//Start new CU.
				i++; continue;
			}

			abbrev_cu_tag = new DW_TAG().dw_tag_lookup_table((int)
					Byte.parseByte(ByteToString.byte2StringArray(debug_abbrev, i+1, 1, ReadHeader.EI_DATA), 16));
			abbrev_cu_child = Byte.parseByte(ByteToString.byte2StringArray(debug_abbrev, i+2, 1, ReadHeader.EI_DATA), 16);

			writer.write(abbrev_cu_index + " $ " + abbrev_cu_tag + " $ " + abbrev_cu_child + " $ ");

			lastIndex = readAttributeForm(debug_abbrev, i, i+3);
			i=lastIndex+2;
		}
		writer.close();
	}


	/**
	 * Read Attribute and Form of each Compilation Unit in .debug_abbrev Section and
	 * Create Data Structure in .debug_abbrev.txt file.
	 * @param debug_abbrev
	 * @param baseIndex
	 * @param index
	 * @return i
	 * @throws IOException
	 */
	private int readAttributeForm(byte[] debug_abbrev, int baseIndex, int index) throws IOException {
		int i = index;
		int value_attr, value_form;
		for (; i < debug_abbrev.length; i+=2) {
			value_attr = Byte.parseByte(ByteToString.byte2StringArray(debug_abbrev, i, 1, ReadHeader.EI_DATA), 16);
			value_form = Byte.parseByte(ByteToString.byte2StringArray(debug_abbrev, i+1, 1, ReadHeader.EI_DATA), 16);
			if(value_attr==0 && value_form==0) break;
			abbrev_cu_attribute = new DW_AT().dw_at_lookup_table((int)value_attr);
			abbrev_cu_form = new DW_FORM().dw_form_lookup_table((int)value_form);

			writer.write(abbrev_cu_attribute + " $ "  + abbrev_cu_form + " $ ");
		}		
		writer.newLine();		
		return i;
	}

}
