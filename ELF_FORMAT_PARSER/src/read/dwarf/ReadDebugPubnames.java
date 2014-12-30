/*********************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: ReadDebugPubnames
 * @version: 1.0
 * Date: June 2009 
 * @author Debabrata Hazra
 * Description: Read .debug_pubnames Section from ELF file format.
 ********************************************************************/

package read.dwarf;

import java.io.IOException;

import java.io.RandomAccessFile;

import dwarf.section.DebugPubnamesStructure;

import read.elf.ReadHeader;
import read.elf.ReadSection;

public class ReadDebugPubnames {
	
	private int f_offset_addr;
	private int size;
	private byte[] debug_pubnames;
	
	
	/**
	 * Constructor:
	 * initialized the Data Member of this class.
	 */
	public ReadDebugPubnames() {
		int index = getDebugPubnamesIndex();
		f_offset_addr = ReadSection.sh_offset[index];
		size = ReadSection.sh_size[index];
		debug_pubnames = new byte[size];
	}

	/*****************************************************************
	 * Get the Section Header Index Number of .debug_pubnames Section  
	 * @return index
	 *****************************************************************/
	private int getDebugPubnamesIndex() {
		for(int index=0;index<ReadHeader.elf_shnum;index++){
			if(ReadSection.sh_str_table[index].contains(".debug_pubnames")){
				return index;
			}
		}
		return 0;
	}
	
	/*******************************************************************
	 * Fetch whole .debug_pubnames section from ELF file 
	 * into debug_pubnames variable and
	 * Create a Data Structure in .debug_pubnames.txt file.
	 * @param filename
	 * @throws IOException
	 *******************************************************************/
	public void readDebugPubnamesSection(String filename) throws IOException{
		RandomAccessFile fpoint = new RandomAccessFile(filename, "r");
		fpoint.seek(f_offset_addr);
		int index=0;
		while(index<size){
			debug_pubnames[index] = fpoint.readByte();
			index++;			
		}
		new DebugPubnamesStructure().readHeader(debug_pubnames);
		fpoint.close();
	}
	
}

