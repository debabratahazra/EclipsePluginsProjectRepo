/*****************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: ReadSectionHeader
 * @version: 1.0
 * Date: June 2009 
 * @author Debabrata Hazra
 * Description: Read each Sections from ELF Header.
 *****************************************************/

package read.elf;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.RandomAccessFile;

import dwarf.internal.ByteToString;

public class ReadSection {

	private int[] sh_name;				//Section Name
	private int[] sh_type;				//Section Type
	public static int[] sh_flags;		//Flag Value
	public static int[] sh_addr;		//Section starting address in memory location
	public static int[] sh_offset;		//Section offset address in Elf file 
	public static int[] sh_size;		//Section length
	private int[] sh_link;
	private int[] sh_info;
	private int[] sh_addralign;
	private int[] sh_entsize;

	private byte EI_DATA;
	public static int elf_shoff;		//Section Header Table's file Offset.
	private short elf_shentsize;		//Each Section Header's size in bytes.
	private short elf_shnum;			//Number of Section Headers.
	private short elf_shstrndx;			//Section Header index number for String table information.

	private static RandomAccessFile fpoint = null;
	public static String []sh_str_table;			//Store Section String table information
	private int str_table_offset;					//Store Section string table offest value of Elf file


	/************************************************************************
	 * Initialize Data Member & the Section Header basic Information 
	 * @param filename
	 * @throws FileNotFoundException
	 ************************************************************************/
	public ReadSection(String filename) throws FileNotFoundException {
		fpoint = new RandomAccessFile(filename, "r");

		this.EI_DATA = ReadHeader.EI_DATA;
		elf_shoff = ReadHeader.elf_shoff;
		this.elf_shentsize = ReadHeader.elf_shentsize;
		this.elf_shnum = ReadHeader.elf_shnum;
		this.elf_shstrndx = ReadHeader.elf_shstrndx;

		sh_name = new int[elf_shnum];
		sh_type = new int[elf_shnum];
		sh_flags = new int[elf_shnum];
		sh_addr = new int[elf_shnum];
		sh_offset = new int[elf_shnum];
		sh_size = new int[elf_shnum];
		sh_link = new int[elf_shnum];
		sh_info = new int[elf_shnum];
		sh_addralign = new int[elf_shnum];
		sh_entsize = new int[elf_shnum];
		sh_str_table = new String[elf_shnum];

	}

	/****************************************************
	 * Set the file pointer of ELF file 
	 * to each Section Header starting index point. 
	 * @throws IOException
	 ****************************************************/
	private void setIndex() throws IOException{
		fpoint.seek(elf_shoff);
	}


	/***********************************************************
	 * Read all Section Header Information from ELF file.
	 * @throws Exception
	 ***********************************************************/
	public void readSectionHeaders() throws Exception{

		int index = 0;
		while(index < this.elf_shnum ){

			byte[] sectionHeader = new byte[elf_shentsize];
			setIndex();
			for(int i=0;i<elf_shentsize;i++){
				sectionHeader[i] = fpoint.readByte();	
				fpoint.seek(++elf_shoff);
			}

			switch(EI_DATA){
			case 0:
				System.out.println("\nInvalid ELF File.");    //Invalid Data Encoding in Elf file.
				break;
			case 1:
				setElfData2LSB(sectionHeader, index);
				break;
			case 2:
				setElfData2MSB(sectionHeader, index);
				break;
			default:
				break;
			}
			index++;
		}
		str_table_offset = sh_offset[elf_shstrndx];
		readStringTable();
	}


	/*******************************************
	 * Data Member assign in Data2LSB format.
	 * @param sectionHeader
	 * @param index
	 *******************************************/
	private void setElfData2LSB(byte[] sectionHeader, int index) {

		sh_name[index] = Integer.valueOf(ByteToString.byte2StringFile(sectionHeader, 0, 4, 1), 16);
		sh_type[index] = Integer.valueOf(ByteToString.byte2StringFile(sectionHeader, 4, 4, 1), 16);
		sh_flags[index] = Integer.valueOf(ByteToString.byte2StringFile(sectionHeader, 8, 4, 1), 16);
		sh_addr[index] = Integer.valueOf(ByteToString.byte2StringFile(sectionHeader, 12, 4, 1), 16);
		sh_offset[index] = Integer.valueOf(ByteToString.byte2StringFile(sectionHeader, 16, 4, 1), 16);
		sh_size[index] = Integer.valueOf(ByteToString.byte2StringFile(sectionHeader, 20, 4, 1), 16);
		sh_link[index] = Integer.valueOf(ByteToString.byte2StringFile(sectionHeader, 24, 4, 1), 16);
		sh_info[index] = Integer.valueOf(ByteToString.byte2StringFile(sectionHeader, 28, 4, 1), 16);
		sh_addralign[index] = Integer.valueOf(ByteToString.byte2StringFile(sectionHeader, 32, 4, 1), 16);
		sh_entsize[index] = Integer.valueOf(ByteToString.byte2StringFile(sectionHeader, 36, 4, 1), 16);

	}


	/**********************************************
	 * Data Member assign in Data2MSB format.
	 * @param sectionHeader
	 * @param index
	 **********************************************/
	private void setElfData2MSB(byte[] sectionHeader, int index) {

		sh_name[index] = Integer.valueOf(ByteToString.byte2StringFile(sectionHeader, 0, 4, 2), 16);
		sh_type[index] = Integer.valueOf(ByteToString.byte2StringFile(sectionHeader, 4, 4, 2), 16);
		sh_flags[index] = Integer.valueOf(ByteToString.byte2StringFile(sectionHeader, 8, 4,2), 16);
		sh_addr[index] = Integer.valueOf(ByteToString.byte2StringFile(sectionHeader, 12, 4, 2), 16);
		sh_offset[index] = Integer.valueOf(ByteToString.byte2StringFile(sectionHeader, 16, 4, 2), 16);
		sh_size[index] = Integer.valueOf(ByteToString.byte2StringFile(sectionHeader, 20, 4, 2), 16);
		sh_link[index] = Integer.valueOf(ByteToString.byte2StringFile(sectionHeader, 24, 4, 2), 16);
		sh_info[index] = Integer.valueOf(ByteToString.byte2StringFile(sectionHeader, 28, 4, 2), 16);
		sh_addralign[index] = Integer.valueOf(ByteToString.byte2StringFile(sectionHeader, 32, 4, 2), 16);
		sh_entsize[index] = Integer.valueOf(ByteToString.byte2StringFile(sectionHeader, 36, 4, 2), 16);

	}	

	/*************************************************
	 * Assign the Name of each Section Elements
	 * from String Table
	 * @throws IOException
	 ************************************************/
	private void readStringTable() throws IOException{		
		int index = 0;		
		byte fChar=0;
		while(index < this.elf_shnum ){
			int pos = str_table_offset + sh_name[index] ;
			fpoint.seek(pos);
			sh_str_table[index] = new String();
			while((fChar = fpoint.readByte())!=0){
				sh_str_table[index] += String.valueOf((char)fChar);	
				fpoint.seek(++pos);
			}			
			index++;
		}		
	}
}
