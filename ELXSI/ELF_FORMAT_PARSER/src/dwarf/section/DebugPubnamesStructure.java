/***********************************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: DebugPubnamesStructure
 * @version: 1.0
 * Date: June 2009 
 * @author Debabrata Hazra
 * Description: Create Data Structure of .debug_pubnames Section from ELF file.
 ***********************************************************************************/

package dwarf.section;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import read.elf.ReadHeader;
import dwarf.internal.ByteToString;

public class DebugPubnamesStructure {

	private int pubnames_header_length;
	private short pubnames_header_version;
	private int pubnames_header_offset;
	private int pubnames_header_areaSize;

	private int pubnames_value_offset;
	private String pubnames_value_funcName;			//Store the Function name

	private BufferedWriter writer = null;

	public DebugPubnamesStructure() {
		pubnames_header_length=0;		
		try {			
			writer = new BufferedWriter(new FileWriter(new File(".debug_pubnames.txt")));
		} catch (IOException e) {}	
	}

	/**
	 * Read all Headers of .debug_pubnames Section from ELF file and
	 * Create a Data Structure in .debug_pubnames.txt file.
	 * @param debug_pubnames
	 * @throws IOException
	 */
	public void readHeader(byte[] debug_pubnames) throws IOException{

		for(int i=0; i <debug_pubnames.length; i+=pubnames_header_length+4){

			pubnames_header_length = Integer.parseInt(ByteToString.byte2StringArray(debug_pubnames, i, 4, ReadHeader.EI_DATA), 16); 
			pubnames_header_version = Short.parseShort(ByteToString.byte2StringArray(debug_pubnames, i+4, 2, ReadHeader.EI_DATA), 16);
			pubnames_header_offset = Integer.parseInt(ByteToString.byte2StringArray(debug_pubnames, i+6, 4, ReadHeader.EI_DATA), 16);
			pubnames_header_areaSize = Integer.parseInt(ByteToString.byte2StringArray(debug_pubnames, i+10, 4, ReadHeader.EI_DATA), 16);

			writer.write(pubnames_header_length + " $ " + pubnames_header_version + " $ " + pubnames_header_offset +
					" $ " + pubnames_header_areaSize + " $ ");

			//Read all the function information and create the Data Structure in .debug_pubnames.txt file.
			readFunctionInformation(debug_pubnames,pubnames_header_length,i,i+14);
		}
		writer.close();
	}

	/**
	 * Read Function names and all the details regarding the function from .debug_punmaes Section and
	 * Create a Data Structure in .debug_pubnames.txt file.
	 * @param debug_pubnames
	 * @param length
	 * @param startPoint
	 * @param index
	 * @throws IOException
	 */
	private void readFunctionInformation(byte[] debug_pubnames,	int length, int startPoint, int index) throws IOException {

		for(int i=index; i<(startPoint + length - 4);){
			pubnames_value_offset = Integer.parseInt(ByteToString.byte2StringArray(debug_pubnames, i, 4, ReadHeader.EI_DATA), 16);

			//Read Function Name from debug_pubnames array.			
			pubnames_value_funcName = new String();
			int j=i+4;
			for(; debug_pubnames[j]!=0 ; j++){
				pubnames_value_funcName += (char) debug_pubnames[j];
			}
			i=j+1;
			writer.write(pubnames_value_offset + " $ " + pubnames_value_funcName + " $ ");
		}
		writer.newLine();
	}

}
