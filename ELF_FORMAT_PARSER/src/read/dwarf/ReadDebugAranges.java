/********************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: ReadDebugAranges
 * @version: 1.0
 * Date: June 2009 
 * @author Debabrata Hazra
 * Description: Read .debug_aranges Section from ELF file format.
 *******************************************************************/

package read.dwarf;

import java.io.IOException;

import java.io.RandomAccessFile;

import dwarf.section.DebugArangesStructure;

import read.elf.ReadHeader;
import read.elf.ReadSection;

public class ReadDebugAranges {
	
	private int f_offset_addr;
	private int size;
	public static byte[] debug_aranges;
		
	/**
	 * Constructor:
	 * initialized the Data Member of this class.
	 */
	public ReadDebugAranges() {		
		int index = getDebugArangesIndex();
		f_offset_addr = ReadSection.sh_offset[index];
		size = ReadSection.sh_size[index];
		debug_aranges = new byte[size];
	}
	
	
	/*****************************************************************
	 * Get the Section Header Index Number of .debug_aranges Section  
	 * @return index
	 *****************************************************************/
	private int getDebugArangesIndex() {
		for(int index=0;index<ReadHeader.elf_shnum;index++){
			if(ReadSection.sh_str_table[index].contains(".debug_aranges")){
				return index;
			}
		}
		return 0;
	}
	
	/*******************************************************************
	 * Fetch whole .debug_aranges section from ELF file 
	 * into debug_aranges variable and 
	 * Create a Data Structure in .debug_aranges.txt file.
	 * @param filename
	 * @throws IOException
	 *******************************************************************/
	public void readDebugArangesSection(String filename) throws IOException{		
		RandomAccessFile fpoint = new RandomAccessFile(filename, "r");
		fpoint.seek(f_offset_addr);
		int index=0;
		while(index<size){
			debug_aranges[index] = fpoint.readByte();
			index++;			
		}
		new DebugArangesStructure().readHeader(debug_aranges);
		fpoint.close();		
	}	
}
