/*******************************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: ElfParser
 * @version: 1.0
 * Date: June 2009 
 * @author Santanu Guchhait
 * Description: This file defines the function and class 
 * required to open the ELF file and store the allocatable 
 * section details in the data structure required for parsing the opcodes
 *******************************************************************************/


package elf.disassembly;

import java.io.IOException;
import java.io.RandomAccessFile;

import read.elf.ReadHeader;
import read.elf.ReadSection;


public class ElfParser 
{
	public RandomAccessFile seeker = null;
	private int sectionsize,temp = 0;
	private static int MAX_ROW_COUNT = 25;
	private long [][] Code_Array = new long[MAX_ROW_COUNT][];

	
	/********************************************************************
	 * @param filename	
	 * Description    - Constructor for the class ELF_PARSER.
	 *********************************************************************/
	public ElfParser(String filename) throws IOException 
	{
		seeker = new RandomAccessFile(filename, "r");


		for (int i1=1;i1<(ReadHeader.elf_shnum);i1++)
		{
			if(((ReadSection.sh_flags[i1])& 0x2)!=0)
			{
				sectionsize=ReadSection.sh_size[i1];
				if (temp < sectionsize)
				{
					temp = sectionsize;
				}
			}
		}
		Code_Array = new long [MAX_ROW_COUNT][temp+1]; //temp=max. section size
	}
	
	
	/********************************************************************	
	 * Parameter      - No parameter
	 * Return type    - No return 
	 * Description    - Read and Store ELF data into an Array.
	 *********************************************************************/
	public void  elfReading() throws IOException 
	{
		for (int i=1;i<(ReadHeader.elf_shnum);i++)
		{
			if(((ReadSection.sh_flags[i])& 0x2)!=0)
			{	
				sectionsize=ReadSection.sh_size[i];
				seeker.seek(ReadSection.sh_offset[i]);
				for(int j=1;j<=sectionsize;j++)
				{
					Code_Array[i][j]=seeker.readByte();
				}

			}

		}	
	}
}





