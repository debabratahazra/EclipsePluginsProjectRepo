/*****************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: ReadDebugLine
 * @version: 1.0
 * Date: June 2009 
 * @author Debabrata Hazra
 * Description: Read .debug_line Section from ELF file format.
 *****************************************************************/

package read.dwarf;

import java.io.IOException;

import java.io.RandomAccessFile;

import dwarf.section.DebugLineStructure;

import read.elf.ReadHeader;
import read.elf.ReadSection;

public class ReadDebugLine {
	
	private int f_offset_addr;
	private int size;
	private byte[] debug_line;
	
	
	/**
	 * Constructor:
	 * initialized the Data Member of this class.
	 */
	public ReadDebugLine() {
		int index = getDebugLineIndex();
		f_offset_addr = ReadSection.sh_offset[index];
		size = ReadSection.sh_size[index];
		debug_line = new byte[size];
	}

	/*****************************************************************
	 * Get the Section Header Index Number of .debug_line Section  
	 * @return index
	 *****************************************************************/
	private int getDebugLineIndex() {
		for(int index=0;index<ReadHeader.elf_shnum;index++){
			if(ReadSection.sh_str_table[index].contains(".debug_line")){
				return index;
			}
		}
		return 0;
	}
	
	/*******************************************************************
	 * Fetch whole .debug_line section from ELF file 
	 * into debug_line variable and
	 * Create the Data Structure in .debug_line.txt file.
	 * @param filename
	 * @throws IOException
	 *******************************************************************/
	public void readDebugLineSection(String filename) throws IOException{
		RandomAccessFile fpoint = new RandomAccessFile(filename, "r");
		fpoint.seek(f_offset_addr);
		int index=0;
		while(index<size){
			debug_line[index] = fpoint.readByte();
			index++;			
		}
		new DebugLineStructure().readHeader(debug_line);
		fpoint.close();
	}
	
}

