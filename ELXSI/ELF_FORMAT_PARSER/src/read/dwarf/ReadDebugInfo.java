/*****************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: ReadDebugInfo
 * @version: 1.0
 * Date: June 2009 
 * @author Debabrata Hazra
 * Description: Read .debug_info Section from ELF file format.
 ****************************************************************/

package read.dwarf;

import java.io.IOException;

import java.io.RandomAccessFile;

import dwarf.section.DebugInfoStructure;

import read.elf.ReadHeader;
import read.elf.ReadSection;

public class ReadDebugInfo {
	
	private int f_offset_addr;
	private int size;
	private byte[] debug_info;
	
		
	/**
	 * Constructor:
	 * initialized the Data Member of this class.
	 */
	public ReadDebugInfo() {
		int index = getDebugInfoIndex();
		f_offset_addr = ReadSection.sh_offset[index];
		size = ReadSection.sh_size[index];
		debug_info = new byte[size];
	}

	/*****************************************************************
	 * Get the Section Header Index Number of .debug_info Section  
	 * @return index
	 *****************************************************************/
	private int getDebugInfoIndex() {
		for(int index=0;index<ReadHeader.elf_shnum;index++){
			if(ReadSection.sh_str_table[index].contains(".debug_info")){
				return index;
			}
		}
		return 0;
	}
	
	/*******************************************************************
	 * Fetch whole .debug_info section from ELF file 
	 * into debug_info variable and
	 * Create Data Structure in .debug_info.txt file.
	 * @param filename
	 * @throws IOException
	 *******************************************************************/
	public void readDebugInfoSection(String filename) throws IOException{
		RandomAccessFile fpoint = new RandomAccessFile(filename, "r");
		fpoint.seek(f_offset_addr);
		int index=0;
		while(index<size){
			debug_info[index] = fpoint.readByte();
			index++;			
		}	
		new DebugInfoStructure().readHeader(debug_info);
		fpoint.close();
	}	
}
