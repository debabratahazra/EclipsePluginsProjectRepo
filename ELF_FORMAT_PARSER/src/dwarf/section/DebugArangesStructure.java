/********************************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: DebugArangesStructure
 * @version: 1.0
 * Date: June 2009 
 * @author Debabrata Hazra
 * Description: Create Data Structure of .debug_aranges Section from ELF file.
 ********************************************************************************/

package dwarf.section;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import read.elf.ReadHeader;
import dwarf.internal.ArrayExpend;
import dwarf.internal.ByteToString;

public class DebugArangesStructure {

	/**
	 * Header Information of .debug_aranges Section.
	 */
	private int aranges_header_len;			//Header Length.	
	private short aranges_header_ver;		//DWARG version.
	private int aranges_header_offset;		//Offset.
	private byte aranges_header_psize;		//Pointer size.
	private byte aranges_header_segsize;	//Segment size.

	private int aranges_address_value;		//Address value from Address Range Description.
	private int aranges_address_length;		//Length from Address Range Description.

	public static int[] base_address; 

	private BufferedWriter writer = null;

	public DebugArangesStructure() {
		base_address = new int[1];
		try {			
			writer = new BufferedWriter(new FileWriter(new File(".debug_aranges.txt")));
		} catch (IOException e) {}		
	}


	/**
	 * Read each Header of .debug_aranges Section from ELF file and
	 * Create Data Structure in .debug_aranges.txt file. 
	 * @param debug_aranges
	 * @throws IOException
	 */
	public void readHeader(byte[] debug_aranges) throws IOException{

		aranges_header_len=0;
		for(int i=0, address_range = 1; i <debug_aranges.length; i+=aranges_header_len+4, address_range++){

			aranges_header_len = Integer.parseInt(ByteToString.byte2StringArray(debug_aranges, i, 4, ReadHeader.EI_DATA), 16); 
			aranges_header_ver = Short.parseShort(ByteToString.byte2StringArray(debug_aranges, i+4, 2, ReadHeader.EI_DATA), 16);
			aranges_header_offset = Integer.parseInt(ByteToString.byte2StringArray(debug_aranges, i+6, 4, ReadHeader.EI_DATA), 16);
			aranges_header_psize = Byte.parseByte(ByteToString.byte2StringArray(debug_aranges, i+10, 1, ReadHeader.EI_DATA), 16);
			aranges_header_segsize = Byte.parseByte(ByteToString.byte2StringArray(debug_aranges, i+11, 1, ReadHeader.EI_DATA), 16);

			writer.write(aranges_header_len + " $ " + aranges_header_ver + " $ " + aranges_header_offset +
					" $ " + aranges_header_psize + " $ " + aranges_header_segsize + " $ ");

			readAddressRange(debug_aranges,aranges_header_len,i,i+16, address_range);
		}
		writer.close();
	}


	/**
	 * Read each Address Range value of each Header of .debug_aranges Section and
	 * Write it in .debug_aranges.txt file.
	 * @param debug_aranges
	 * @param length
	 * @param startPoint
	 * @param index
	 * @param address_range
	 * @throws IOException
	 */
	private void readAddressRange(byte[] debug_aranges,int length,int startPoint, int index, int address_range) throws IOException{

		base_address = (int[]) ArrayExpend.expend(base_address, address_range);		//Expand the array size.
		for(int i=index; (i)< (startPoint + length-4); i+=8){						//-4 => Last 8 Byte is reserved for 0-0
			aranges_address_value = Integer.parseInt(ByteToString.byte2StringArray(debug_aranges, i, 4, ReadHeader.EI_DATA), 16);
			base_address[address_range-1] = aranges_address_value; 

			aranges_address_length = Integer.parseInt(ByteToString.byte2StringArray(debug_aranges, i+4, 4, ReadHeader.EI_DATA), 16);

			writer.write(aranges_address_value + " $ " + aranges_address_length);			
		}
		writer.newLine();		
	}

}
