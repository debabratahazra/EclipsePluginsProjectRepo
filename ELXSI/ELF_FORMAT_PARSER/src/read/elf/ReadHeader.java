/********************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: ReadHeader
 * @version: 1.0
 * Date: June 2009 
 * @author Debabrata Hazra and Santanu Guchhait
 * Description: This file defines the class and functions
 * that are required to store the ELF header information 
 * (identifying the Little Endian or Big Endian alignment)
 ********************************************************/



package read.elf;

import java.io.BufferedInputStream;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import dwarf.internal.ByteToString;


public class ReadHeader {

	private final int ID_INDEX=16;
	private final int SIZE=52;
	public byte[] elf_ident;
	public short elf_type;
	public short elf_machine;
	public int elf_version;
	public int elf_entry;
	public int elf_phoff;
	public static int elf_shoff;
	public int elf_flags;
	public short elf_ehsize;
	public short elf_phentsize;
	public short elf_phnum;
	public static short elf_shentsize;
	public static short elf_shnum;
	public static short elf_shstrndx;	
	public byte[] elfHeader;
	public static byte EI_DATA;		//Data Format in ELF File.

	
	/********************************************************************	
	 * Description    - Constructor for the class ReadElfHeader.
	 *********************************************************************/
	public ReadHeader() {
		elf_ident = new byte[ID_INDEX];
		elfHeader = new byte[SIZE] 	;
	}

	
	/********************************************************************	
	 * parameter      - Filename
	 * Return type    - No return type
	 * Description    - Reads the ELF Header Information and stores them.
	 *********************************************************************/
	public void readElfFileHeader(String filename){

		/**
		 * Read ELF Header and store into elfHeader array.
		 */
		try {
			DataInputStream in = new DataInputStream(new BufferedInputStream(
					new FileInputStream(filename)));
			int i=0;
			do{
				//Store 52 byte Header information.
				elfHeader[i] =  in.readByte();

				//Initialize the elf_ident value.
				if(i>=0 && i<16){
					elf_ident[i] = elfHeader[i]; 
				}
				i++;
			}while (i<52);			    
			EI_DATA = elf_ident[5];
			in.close();
		} catch (FileNotFoundException e) {} 
		catch (IOException e) {}
		catch(Exception e){}

		/**
		 * Identify the EI_DATA [Data Encoding Format]
		 * and
		 * Assign all Data Members of this Class.
		 */
		switch(elf_ident[5]){
		case 0:
			System.out.println("\nInvalid ELF File.");    //Invalid Data Encoding.			
			break;
		case 1:
			setElfData2LSB(elfHeader);
			break;
		case 2:
			setElfData2MSB(elfHeader);
			break;
		default:
			break;
		}

	}

	/********************************************************************	
	 * Parameter      - elfHeader2
	 * Return type    - No return type
	 * Description    - Initializes all the class members.
	 *********************************************************************/
	private void setElfData2LSB(byte[] elfHeader2) {		

		elf_type = Short.valueOf(ByteToString.byte2StringFile(elfHeader2, 16, 2, 1), 16);
		elf_machine = Short.valueOf(ByteToString.byte2StringFile(elfHeader2, 18, 2, 1), 16);
		elf_version = Integer.valueOf(ByteToString.byte2StringFile(elfHeader2, 20, 4, 1), 16);
		elf_entry = Integer.valueOf(ByteToString.byte2StringFile(elfHeader2, 24, 4, 1), 16);
		elf_phoff = Integer.valueOf(ByteToString.byte2StringFile(elfHeader2, 28, 4, 1), 16);
		elf_shoff = Integer.valueOf(ByteToString.byte2StringFile(elfHeader2, 32, 4, 1), 16);
		elf_flags = Integer.valueOf(ByteToString.byte2StringFile(elfHeader2, 36, 4, 1), 16);
		elf_ehsize = Short.valueOf(ByteToString.byte2StringFile(elfHeader2, 40, 2, 1), 16);
		elf_phentsize = Short.valueOf(ByteToString.byte2StringFile(elfHeader2, 42, 2, 1), 16);
		elf_phnum = Short.valueOf(ByteToString.byte2StringFile(elfHeader2, 44, 2, 1), 16);
		elf_shentsize = Short.valueOf(ByteToString.byte2StringFile(elfHeader2, 46, 2, 1), 16);
		elf_shnum = Short.valueOf(ByteToString.byte2StringFile(elfHeader2, 48, 2, 1), 16);
		elf_shstrndx = Short.valueOf(ByteToString.byte2StringFile(elfHeader2, 50, 2, 1), 16);		
	}

	
	/********************************************************************	
	 * Parameter      - elfHeader2
	 * Return type    - No return type
	 * Description    - Initializes all the class members.
	 *********************************************************************/
	private void setElfData2MSB(byte[] elfHeader2) {

		elf_type = Short.valueOf(ByteToString.byte2StringFile(elfHeader2, 16, 2, 2), 16);
		elf_machine = Short.valueOf(ByteToString.byte2StringFile(elfHeader2, 18, 2, 2), 16);
		elf_version = Integer.valueOf(ByteToString.byte2StringFile(elfHeader2, 20, 4, 2), 16);
		elf_entry = Integer.valueOf(ByteToString.byte2StringFile(elfHeader2, 24, 4, 2), 16);
		elf_phoff = Integer.valueOf(ByteToString.byte2StringFile(elfHeader2, 28, 4, 2), 16);
		elf_shoff = Integer.valueOf(ByteToString.byte2StringFile(elfHeader2, 32, 4, 2), 16);
		elf_flags = Integer.valueOf(ByteToString.byte2StringFile(elfHeader2, 36, 4, 2), 16);
		elf_ehsize = Short.valueOf(ByteToString.byte2StringFile(elfHeader2, 40, 2, 2), 16);
		elf_phentsize = Short.valueOf(ByteToString.byte2StringFile(elfHeader2, 42, 2, 2), 16);
		elf_phnum = Short.valueOf(ByteToString.byte2StringFile(elfHeader2, 44, 2, 2), 16);
		elf_shentsize = Short.valueOf(ByteToString.byte2StringFile(elfHeader2, 46, 2, 2), 16);
		elf_shnum = Short.valueOf(ByteToString.byte2StringFile(elfHeader2, 48, 2, 2), 16);
		elf_shstrndx = Short.valueOf(ByteToString.byte2StringFile(elfHeader2, 50, 2, 2), 16);			
	}	
}
