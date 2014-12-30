/*****************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: ReadDebugFrame
 * @version: 1.0
 * Date: June 2009 
 * @author Debabrata Hazra
 * Description: Read .debug_frame Section from ELF file format.
 *****************************************************************/

package read.dwarf;

import java.io.IOException;
import java.io.RandomAccessFile;

import read.elf.ReadHeader;
import read.elf.ReadSection;

public class ReadDebugFrame {
	
	private int f_offset_addr;
	private int size;
	private byte[] debug_frame;
	
	
	/**
	 * Constructor:
	 * initialized the Data Member of this class.
	 */
	public ReadDebugFrame() {
		int index = getDebugFrameIndex();
		f_offset_addr = ReadSection.sh_offset[index];
		size = ReadSection.sh_size[index];
		debug_frame = new byte[size];
	}

	/*****************************************************************
	 * Get the Section Header Index Number of .debug_frame Section  
	 * @return index
	 *****************************************************************/
	private int getDebugFrameIndex() {
		for(int index=0;index<ReadHeader.elf_shnum;index++){
			if(ReadSection.sh_str_table[index].contains(".debug_frame")){
				return index;
			}
		}
		return 0;
	}
	
	/*******************************************************************
	 * Fetch whole .debug_frame section from ELF file 
	 * into debug_frame variable.
	 * @param filename
	 * @throws IOException
	 *******************************************************************/
	public void readDebugFrameSection(String filename) throws IOException{
		RandomAccessFile fpoint = new RandomAccessFile(filename, "r");
		fpoint.seek(f_offset_addr);
		int index=0;
		while(index<size){
			debug_frame[index] = fpoint.readByte();
			index++;			
		}
		fpoint.close();
	}
	
}
