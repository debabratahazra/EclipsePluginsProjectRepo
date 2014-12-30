/******************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: ReadDebugStr
 * @version: 1.0
 * Date: June 2009 
 * @author Debabrata Hazra
 * Description: Read .debug_str Section from ELF file format.
 *****************************************************************/

package read.dwarf;

import java.io.IOException;
import java.io.RandomAccessFile;

import read.elf.ReadHeader;
import read.elf.ReadSection;

public class ReadDebugStr {

	private int f_offset_addr;
	private int size;
	public static byte[] debug_str;


	/**
	 * Constructor:
	 * initialized the Data Member of this class.
	 */
	public ReadDebugStr() {
		int index = getDebugStrIndex();
		f_offset_addr = ReadSection.sh_offset[index];
		size = ReadSection.sh_size[index];
		debug_str = new byte[size];
	}

	/*****************************************************************
	 * Get the Section Header Index Number of .debug_str Section  
	 * @return index
	 *****************************************************************/
	private int getDebugStrIndex() {
		for(int index=0;index<ReadHeader.elf_shnum;index++){
			if(ReadSection.sh_str_table[index].contains(".debug_str")){
				return index;
			}
		}
		return 0;
	}

	/*******************************************************************
	 * Fetch whole .debug_str section from ELF file 
	 * into debug_str variable.
	 * @param filename
	 * @throws IOException
	 *******************************************************************/
	public void readDebugStrSection(String filename) throws IOException{
		RandomAccessFile fpoint = new RandomAccessFile(filename, "r");
		fpoint.seek(f_offset_addr);
		int index=0;
		while(index<size){
			debug_str[index] = fpoint.readByte();
			index++;			
		}	
		fpoint.close();
	}

}
