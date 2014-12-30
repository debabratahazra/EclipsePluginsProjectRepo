/*****************************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: DebugLineStructure
 * @version: 1.0
 * Date: June 2009 
 * @author Debabrata Hazra
 * Description: Create Data Structure of .debug_line Section from ELF file.
 *****************************************************************************/

package dwarf.section;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import read.elf.ReadHeader;
import dwarf.internal.ArrayExpend;
import dwarf.internal.ByteToString;

public class DebugLineStructure {

	private int line_header_length;

	private byte line_header_minInstLen;
	private byte line_header_initValStmt;
	private byte line_header_lineBase;
	private byte line_header_lineRange;
	private byte line_header_OpBase;

	private int line_set_address;
	private int line_set_lineNumber;

	private BufferedWriter writer = null;

	/**
	 * Constructor: Create .debug_line data structure file.
	 */
	public DebugLineStructure() {
		try {			
			writer = new BufferedWriter(new FileWriter(new File(".debug_line.txt")));
		} catch (IOException e) {}
	}


	/**
	 * Read each Header of .debug_line section from ELF file.
	 * @param debug_line
	 * @throws IOException
	 */
	public void readHeader(byte[] debug_line) throws IOException{
		int index=0,j=0;
		for(; index<debug_line.length; index=j){

			line_header_length = Integer.parseInt(ByteToString.byte2StringArray(debug_line, index, 4, ReadHeader.EI_DATA), 16);
			index+=4;

			for(j=index; j<(index+line_header_length); ){

				line_header_minInstLen = Byte.parseByte(ByteToString.byte2StringArray(debug_line, j+6, 1, ReadHeader.EI_DATA), 16);
				line_header_initValStmt = Byte.parseByte(ByteToString.byte2StringArray(debug_line, j+7, 1, ReadHeader.EI_DATA), 16);
				line_header_lineBase = (byte)Long.parseLong(ByteToString.byte2StringArray(debug_line, j+8, 1, ReadHeader.EI_DATA), 16);
				line_header_lineRange = Byte.parseByte(ByteToString.byte2StringArray(debug_line, j+9, 1, ReadHeader.EI_DATA), 16);
				line_header_OpBase = Byte.parseByte(ByteToString.byte2StringArray(debug_line, j+10, 1, ReadHeader.EI_DATA), 16);

				j+= 11 + (line_header_OpBase -1) ;			//Skip opcode argument value list from Data Structure.

				//Get Directory Name.
				int number = 0;
				String[] dirName = new String[1];
				while(debug_line[j]!=0){
					dirName = (String[]) ArrayExpend.expend(dirName, number+1);
					dirName[number] = "";
					for(int k=j; debug_line[k]!=0 ; j++){
						dirName[number] += (char)debug_line[k++];
					}
					number++; j++;
				}
				j++;

				//Get Source/Header File Name.
				String[] fileName = new String[1];
				number=0;
				while(debug_line[j]!=0){
					fileName = (String[]) ArrayExpend.expend(fileName, number+1);
					fileName[number]="";
					for(int k=j; debug_line[k]!=0; j++){
						fileName[number] += (char)debug_line[k++];
					}
					number++;

					int dirEntry = debug_line[++j];

					j+=3;			//Skip Time-Size information.	

					if(fileName[number-1].toUpperCase().contains(".C") || 
							fileName[number-1].toUpperCase().contains(".S")
							|| fileName[number-1].toUpperCase().contains(".ASM")){
						if(dirEntry !=0 &&  dirName[dirEntry-1]!=null){
							writer.write(dirName[dirEntry-1] + "\\" + fileName[number-1]);
						}else{
							writer.write(fileName[number-1]);
						}

					}

				}
				j+=2;		//Skip Null Termination.

				int length = readStatement(debug_line, j);
				j= length;

				//Write the last Standard Opcode information with dummy value.
				writer.write(" $ " + "0" + " $ " + line_set_address + " $ " + line_set_lineNumber);
				writer.newLine();
			}
		}
		writer.close();
	}


	/**
	 * Read Statement instruction from header of .debug_line section
	 * @param debug_line
	 * @param startIndex
	 * @return length of this section
	 * @throws IOException
	 */
	private int readStatement(byte[] debug_line, int startIndex) throws IOException {

		for(int i=startIndex; i< debug_line.length;){
			int len = readExtendedOpcode(debug_line, i);
			if(len==1){							//End of Sequence.
				return (i+2);
			}
			i+= ++len;

			if(debug_line[i]>= 0x13 && debug_line[i]<=0x1A){	
				line_set_lineNumber=1;
				writer.write(" $ " + "2");
			}else{
				len = readFileEntryLineNumber(debug_line, i);
				i+= len;
			}	

			len = readSpecialOpcode(debug_line, i);
			i=len;
		}		
		return 0;
	}



	/**
	 * Read Special opcode from .debug_line section
	 * @param debug_line
	 * @param i
	 * @return length of this section
	 * @throws IOException
	 */
	private int readSpecialOpcode(byte[] debug_line, int i) throws IOException {
		int opcode=0;
		while(debug_line[i]!=0){
			opcode = Integer.parseInt(ByteToString.byte2StringArray(debug_line, i, 1, ReadHeader.EI_DATA), 16);
			if(opcode > 9){
				//Read Special Opcode.
				opcode -= line_header_OpBase;				
				writer.write(" $ " + opcode);
				readLineAddressInformation(opcode);
				i++;
			}else{
				//Read Standard Opcode.
				int len = readStandardOpcode(debug_line, i, opcode);
				i+= len;
			}				
		}
		return i+1;
	}



	/**
	 * Read Line information and
	 * Address information from Special Opcode.
	 * @param opcode
	 * @throws IOException
	 */
	private void readLineAddressInformation(int opcode) throws IOException {

		int incrementAddress, incrementLine;
		incrementAddress = opcode / line_header_lineRange ;
		incrementLine = line_header_lineBase + (opcode % line_header_lineRange);

		writer.write(" $ " + (getAddress() + (incrementAddress * line_header_minInstLen)));
		writer.write(" $ " + (getLineNumber() + incrementLine));	

		updateLineAddress(incrementAddress, incrementLine);
	}


	/**
	 * Update the current address value and line number
	 * @param incrementAddress
	 * @param incrementLine
	 */
	private void updateLineAddress(int incrementAddress, int incrementLine) {

		line_set_address = getAddress() + (incrementAddress) * line_header_minInstLen;		
		line_set_lineNumber = getLineNumber() + incrementLine;
	}


	/**
	 * Read Standard Opcode from .debug_line section.
	 * @param debug_line
	 * @param i
	 * @param opcode
	 * @return length of this section
	 * @throws IOException
	 */
	private int readStandardOpcode(byte[] debug_line, int i, int opcode) throws IOException {

		switch(opcode){
		case 1:
			//DW_LNS_copy

			return 1;
		case 2:
			//DW_LNS_advance_pc			

			updateLineAddress(debug_line[i+1],0);
			//Omit Standard Opcode Information
			//writer.write(" $ " + debug_line[i] + " $ " + line_set_address + " $ " + line_set_lineNumber);			
			return 2;
		case 3:
			//DW_LNS_advance_line

			updateLineAddress(0, debug_line[i+1]);
			//Omit Standard Opcode Information
			//writer.write(" $ " + debug_line[i] + " $ " + line_set_address + " $ " + line_set_lineNumber);
			return 2;
		case 4:
			//DW_LNS_set_file

			return 2;
		case 5:
			//DW_LNS_set_column

			return 2;
		case 6:
			//DW_LNS_negate_stmt

			if(line_header_initValStmt==1){
				line_header_initValStmt = 0;
			}else{
				line_header_initValStmt = 1;
			}
			return 1;
		case 7:
			//DW_LNS_set_basic_block

			return 1;
		case 8:
			//DW_LNS_const_add_pc

			int address = (int) ((255 - line_header_OpBase) / line_header_lineRange);
			updateLineAddress(address,0);
			//Omit Standard Opcode Information
			//writer.write(" $ " + debug_line[i] + " $ " + line_set_address + " $ " + line_set_lineNumber);
			return 1;
		case 9:
			//DW_LNS_fixed_advance_pc

			address = Integer.parseInt(ByteToString.byte2StringArray(debug_line, i+1, 2, ReadHeader.EI_DATA), 16);
			updateLineAddress(address,0);
			//Omit Standard Opcode Information
			//writer.write(" $ " + debug_line[i] + " $ " + line_set_address + " $ " + line_set_lineNumber);
			return 3;
		default:
			return 1;		
		}		
	}


	/**
	 * Read Extended Opcode from .debug_line section.
	 * @param debug_line
	 * @param index
	 * @return length of this section
	 * @throws IOException
	 */
	private byte readExtendedOpcode(byte[] debug_line, int index) throws IOException {
		byte length, extOpNumber;
		length =  Byte.parseByte(ByteToString.byte2StringArray(debug_line, index, 1, ReadHeader.EI_DATA), 16);
		extOpNumber =  Byte.parseByte(ByteToString.byte2StringArray(debug_line, index+1, 1, ReadHeader.EI_DATA), 16);
		switch(extOpNumber){
		case 1:
			//DW_LNE_end_sequence						
			index+=2;
			//line_set_address = 0;
			break;
		case 2:
			//DW_LNE_set_address
			line_set_address = Integer.parseInt(ByteToString.byte2StringArray(debug_line, index+2, 4, ReadHeader.EI_DATA), 16);
			index+=6;
			writer.write(" $ " + line_set_address);
			break;
		case 3:			
			//DW_LNE_define_file			
			index+=2;
			int i=index;
			for(; i< (index+length) ; i++){

			}

			break;
		default:
			break;
		}
		return length;
	}


	/**
	 * Read the First entry point of each source file(s).
	 * @param debug_line
	 * @param index
	 * @return length of this section
	 * @throws IOException
	 */
	private byte readFileEntryLineNumber(byte[] debug_line, int index) throws IOException {

		byte length, lineInc;
		length = Byte.parseByte(ByteToString.byte2StringArray(debug_line, index, 1, ReadHeader.EI_DATA), 16);
		if(length==1){
			writer.write(" $ " + "1");
			line_set_lineNumber=1;
			return length;			
		}
		lineInc = Byte.parseByte(ByteToString.byte2StringArray(debug_line, index+1, 1, ReadHeader.EI_DATA), 16);
		line_set_lineNumber = (int) (lineInc + Byte.parseByte(ByteToString.byte2StringArray(debug_line, index+2, 1, ReadHeader.EI_DATA), 16));
		writer.write(" $ " + line_set_lineNumber);
		return length;		
	}

	/**
	 * Get the current Memory Address.
	 * @return address
	 */
	private int getAddress(){
		return line_set_address;
	}

	/**
	 * Get the current line number from source file.
	 * @return line number
	 */
	private int getLineNumber(){
		return line_set_lineNumber;
	}
}
