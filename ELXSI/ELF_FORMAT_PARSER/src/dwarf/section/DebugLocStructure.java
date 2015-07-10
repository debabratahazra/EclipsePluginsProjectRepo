/***************************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: DebugLocStructure
 * @version: 1.0
 * Date: June 2009 
 * @author Debabrata Hazra
 * Description: Create Data Structure of .debug_loc Section from ELF file.
 **************************************************************************/

package dwarf.section;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import dwarf.lookup.DW_OP;

import read.elf.ReadHeader;
import dwarf.internal.ByteToString;


public class DebugLocStructure {

	private int loc_header_start_offset;
	private int loc_header_end_offset;
	private short loc_header_op_length;


	private BufferedWriter writer = null;

	/**
	 * Constructor, Create .debug_loc text file.
	 */
	public DebugLocStructure() {							  
		try {			
			writer = new BufferedWriter(new FileWriter(new File(".debug_loc.txt")));
		} catch (IOException e) {}		
	}


	/**
	 * Read each Header of the .debug_loc Section from ELF file and
	 * Create Data Structure in .debug_loc.txt file.
	 * @param debug_loc
	 * @throws IOException
	 */
	public void readHeader(byte[] debug_loc) throws IOException{

		for (int i = 0, index=0; i < debug_loc.length; ) {
			loc_header_start_offset = Integer.parseInt(ByteToString.byte2StringArray(debug_loc, i, 4, ReadHeader.EI_DATA), 16);
			loc_header_end_offset = Integer.parseInt(ByteToString.byte2StringArray(debug_loc, i+4, 4, ReadHeader.EI_DATA), 16);

			if(loc_header_start_offset==0 && loc_header_end_offset==0){
				i+=8;
				continue;
			}

			if(loc_header_start_offset==0){
				loc_header_start_offset = DebugArangesStructure.base_address[index];
				loc_header_end_offset += loc_header_start_offset;
				index++;
			}else{
				loc_header_start_offset += DebugArangesStructure.base_address[index-1];
				loc_header_end_offset += DebugArangesStructure.base_address[index-1];
			}

			loc_header_op_length = Short.parseShort(ByteToString.byte2StringArray(debug_loc, i+8, 2, ReadHeader.EI_DATA), 16);

			writer.write(loc_header_start_offset + " $ " + loc_header_end_offset + " $ " + loc_header_op_length + " $ ");

			readExpression(debug_loc, loc_header_op_length,  i+10 );

			i+=(loc_header_op_length+10);
		}
		writer.close();
	}


	/**
	 * Read Expression and create the Data structure in .debug_loc.txt file.
	 * @param debug_loc
	 * @param length	 
	 * @param index
	 * @throws IOException
	 */
	private void readExpression(byte[] debug_loc, int length, int index) throws IOException{

		switch(length){
		case 1:
			String expression = new DW_OP().dw_op_lookup_table(debug_loc[index]);
			if(expression==null) return;
			writer.write(expression);
			break;
		case 2:
			expression = new DW_OP().dw_op_lookup_table(debug_loc[index]);
			if(expression==null) return;
			int val = debug_loc[index+1];
			writer.write(expression + " $ " + val) ;
			break;
		case 3:
			expression = new DW_OP().dw_op_lookup_table(debug_loc[index]);
			if(expression==null) return;
			val = debug_loc[index+1];
			int val2 = debug_loc[index+2];
			writer.write(expression + " $ " + val + " $ " + val2 ) ;
			break;
		default:
			break;			
		}
		writer.newLine();
	}


}
