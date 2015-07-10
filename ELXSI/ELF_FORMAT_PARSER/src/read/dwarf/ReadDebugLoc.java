/*****************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: ReadDebugLoc
 * @version: 1.0
 * Date: June 2009 
 * @author Debabrata Hazra
 * Description: Read .debug_loc Section from ELF file format.
 *****************************************************************/

package read.dwarf;

import java.io.IOException;

import java.io.RandomAccessFile;

import dwarf.section.DebugLocStructure;

import read.elf.ReadHeader;
import read.elf.ReadSection;

public class ReadDebugLoc {
	
	private int f_offset_addr;
	private int size;
	private byte[] debug_loc;
	
	
	/**
	 * Constructor:
	 * initialized the Data Member of this class.
	 */
	public ReadDebugLoc() {
		int index = getDebugLocIndex();
		f_offset_addr = ReadSection.sh_offset[index];
		size = ReadSection.sh_size[index];
		debug_loc = new byte[size];
	}

	/*****************************************************************
	 * Get the Section Header Index Number of .debug_loc Section  
	 * @return index
	 *****************************************************************/
	private int getDebugLocIndex() {
		for(int index=0;index<ReadHeader.elf_shnum;index++){
			if(ReadSection.sh_str_table[index].contains(".debug_loc")){
				return index;
			}
		}
		return 0;
	}
	
	/*******************************************************************
	 * Fetch whole .debug_loc section from ELF file 
	 * into debug_loc variable and
	 * Create Data Structure in .debug_loc.txt file.
	 * @param filename
	 * @throws IOException
	 *******************************************************************/
	public void readDebugLocSection(String filename) throws IOException{
		RandomAccessFile fpoint = new RandomAccessFile(filename, "r");
		fpoint.seek(f_offset_addr);
		int index=0;
		while(index<size){
			debug_loc[index] = fpoint.readByte();
			index++;			
		}	
		new DebugLocStructure().readHeader(debug_loc);
		fpoint.close();
	}
	
}
