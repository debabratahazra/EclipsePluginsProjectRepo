/****************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: ReadDebugAbbrev
 * @version: 1.0
 * Date: June 2009 
 * @author Debabrata Hazra
 * Description: Read .debug_abbrev Section from ELF file format.
 ****************************************************************/

package read.dwarf;

import java.io.IOException;

import java.io.RandomAccessFile;

import dwarf.section.DebugAbbrevStructure;

import read.elf.ReadHeader;
import read.elf.ReadSection;

public class ReadDebugAbbrev {

	private int f_offset_addr;
	private int size;
	public static byte[] debug_abbrev;


	/**
	 * Constructor:
	 * initialized the Data Member of this class.
	 */
	public ReadDebugAbbrev() {
		int index = getDebugAbbrevIndex();
		f_offset_addr = ReadSection.sh_offset[index];
		size = ReadSection.sh_size[index];
		debug_abbrev = new byte[size];
	}

	/*****************************************************************
	 * Get the Section Header Index Number of .debug_abbrev Section  
	 * @return index
	 *****************************************************************/
	private int getDebugAbbrevIndex() {
		for(int index=0;index<ReadHeader.elf_shnum;index++){
			if(ReadSection.sh_str_table[index].contains(".debug_abbrev")){
				return index;
			}
		}
		return 0;
	}

	/*******************************************************************
	 * Fetch whole .debug_abbrev section from ELF file 
	 * into debug_abbrev variable and
	 * Create Data Structure in .debug_abbrev.txt file.
	 * @param filename
	 * @throws IOException
	 *******************************************************************/
	public void readDebugAbbrevSection(String filename) throws IOException{
		RandomAccessFile fpoint = new RandomAccessFile(filename, "r");
		fpoint.seek(f_offset_addr);
		int index=0;
		while(index<size){
			debug_abbrev[index] = fpoint.readByte();
			index++;			
		}
		new DebugAbbrevStructure().readCU(debug_abbrev);
		fpoint.close();
	}

}

