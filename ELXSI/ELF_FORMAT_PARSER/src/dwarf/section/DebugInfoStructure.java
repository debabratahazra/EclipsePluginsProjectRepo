/*****************************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: DebugInfoStructure
 * @version: 1.0
 * Date: June 2009 
 * @author Debabrata Hazra
 * Description: Create Data Structure of .debug_info Section from ELF file.
 *****************************************************************************/

package dwarf.section;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import dwarf.lookup.DW_FORM_SIZE;
import dwarf.lookup.DW_TAG;

import read.dwarf.ReadDebugAbbrev;
import read.dwarf.ReadDebugStr;
import read.elf.ReadHeader;
import dwarf.internal.ArrayExpend;
import dwarf.internal.ByteToString;
import dwarf.internal.StructUnionHandler;


public class DebugInfoStructure {

	private int info_header_length;	
	private int info_header_abbrevOffset;

	private BufferedWriter writer = null;
	private BufferedWriter s_writer = null;
	private BufferedWriter g_writer = null;

	private static String fileName;
	private static boolean multipleSub;
	private long array_variable_size;
	private String upper_bound = null;

	public static int struct_index, sObject_index, sMember_index;	
	public static boolean structUnionType;
	public static int frame_base;

	static{
		fileName = null;
		multipleSub = false;	
		struct_index = 0;
		sObject_index = 0;
		sMember_index = 0;
		structUnionType = false;
	}


	/**
	 * Constructor
	 * Create Data Structure file: .debug_info.txt
	 * Create Source File info in .source_file.txt 
	 */
	public DebugInfoStructure() {		
		try {			
			writer = new BufferedWriter(new FileWriter(new File(".debug_info.txt")));
			s_writer = new BufferedWriter(new FileWriter(new File(".source_file.txt")));
			g_writer = new BufferedWriter(new FileWriter(new File(".global_variable.txt")));
		} catch (IOException e) {}	
	}


	/**
	 * Read .debug_info section and create a Data Structure.
	 * @param debug_info
	 * @throws IOException
	 */
	public void readHeader(byte[] debug_info) throws IOException{

		for (int index = 0; index < debug_info.length; index+=(info_header_length + 4)) {
			info_header_length = Integer.parseInt(ByteToString.byte2StringArray(debug_info, index, 4, ReadHeader.EI_DATA), 16);			
			info_header_abbrevOffset = Integer.parseInt(ByteToString.byte2StringArray(debug_info, index+6, 4, ReadHeader.EI_DATA), 16);			

			readCompilationUnit(debug_info, info_header_length,info_header_abbrevOffset, 
					ReadDebugAbbrev.debug_abbrev, index, index+11 );
			writer.newLine();
			s_writer.newLine();			
		}	
		writer.close();
		s_writer.close();
		g_writer.close();
		StructUnionHandler.struct_writer.close();
	}



	/**
	 * Read each Compilation Unit (CU) from .debug_info Section 
	 * and Create Data Structure of .debug_info.txt and .source_file.txt file. 
	 * @param debug_info
	 * @param header_length
	 * @param abbrev_offset
	 * @param debug_abbrev
	 * @param info_startPoint
	 * @param info_index
	 * @throws IOException
	 */
	private void readCompilationUnit(byte[] debug_info,	int header_length,int abbrev_offset, 
			byte[] debug_abbrev, int info_startPoint, int info_index) throws IOException {				
		getSourceFiles(debug_info, header_length, abbrev_offset, debug_abbrev, info_startPoint, info_index);
		multipleSub = false;
		int _index = info_index;  
		for (int i = _index; i < (header_length + info_startPoint); i = ++_index) {
			_index = getMethods(debug_info, header_length, abbrev_offset, debug_abbrev, info_startPoint, i);
		}
	}


	/**
	 * Get all the Method(s) details [Method Name, High_pc & Low_pc Value,
	 * all local & parameterized variable name, its location value and size of each variable(s) and
	 * Create a Data Structure in .debug_info.txt file for each CU. 
	 * @param debug_info
	 * @param header_length
	 * @param abbrev_offset
	 * @param debug_abbrev
	 * @param info_startPoint
	 * @param info_index
	 * @return current denig_info index pointer position
	 * @throws IOException
	 */
	private int getMethods(byte[] debug_info, int header_length,
			int abbrev_offset, byte[] debug_abbrev, int info_startPoint, int info_index) throws IOException {

		int i=info_index;
		for(; i<debug_info.length && debug_info[i]!=0 ; i++){
			String tagName = getTagName(debug_info[i], debug_abbrev, abbrev_offset);
			if(tagName.contains("DW_TAG_subprogram")){
				//Method details
				if(multipleSub)		writer.newLine();
				i = setMethodDetails(debug_info, header_length, abbrev_offset, i, info_startPoint, debug_abbrev);							
			}else if(tagName.contains("DW_TAG_variable")){
				//Global Variable details
				i = getGlobalVariable(debug_info, header_length, abbrev_offset, i, info_startPoint, debug_abbrev);				
			}else{
				i += getAttributeEntryLength(debug_info, header_length, abbrev_offset, i, debug_abbrev);
			}
		}	
		return i;
	}




	/**
	 * Get the Global Variable Information and
	 * Create a Data Structure in .global_variable.txt
	 * @param debug_info
	 * @param header_length
	 * @param abbrev_offset
	 * @param info_index
	 * @param info_startPoint
	 * @param debug_abbrev
	 * @return current denig_info index pointer position
	 * @throws IOException
	 */
	private int getGlobalVariable(byte[] debug_info, int header_length,
			int abbrev_offset, int info_index, int info_startPoint, byte[] debug_abbrev) throws IOException {

		int abbrev_pt = getDebugAbbrevPointer(debug_info[info_index], abbrev_offset, debug_abbrev) + 3;
		getAttributeDetails(debug_info, header_length, abbrev_offset, info_index+1,
				info_startPoint, debug_abbrev, abbrev_pt, true, 0);
		info_index += getAttributeEntryLength(debug_info, header_length, abbrev_offset, info_index, debug_abbrev) ;
		return info_index;
	}


	/**
	 * Get the Source File name and Method name with its local & parameterized variable details for
	 * each Method in a particular CU.
	 * @param debug_info
	 * @param header_length
	 * @param abbrev_offset
	 * @param info_index
	 * @param info_startPoint
	 * @param debug_abbrev
	 * @return current denig_info index pointer position
	 * @throws IOException
	 */
	private int setMethodDetails(byte[] debug_info, int header_length,
			int abbrev_offset, int info_index, int info_startPoint, byte[] debug_abbrev) throws IOException {

		multipleSub = true;
		writer.write(getFilename(fileName)	+ " $ ");		
		int abbrev_pt = getDebugAbbrevPointer(debug_info[info_index], abbrev_offset, debug_abbrev);

		info_index = setDebugInfoSubprogram(debug_info, abbrev_offset, info_index, debug_abbrev, abbrev_pt);
		abbrev_pt = getDebugAbbrevPointer(debug_info[info_index], abbrev_offset, debug_abbrev);
		info_index = setDebugInfoVariable(debug_info, header_length, abbrev_offset, info_index, info_startPoint, debug_abbrev, abbrev_pt);
		return info_index;
	}



	/**
	 * Get the Source File Name of current CU.
	 * @param fileName
	 * @return String
	 */
	private String getFilename(String fileName){
		int _index = fileName.lastIndexOf("\\") + 1;		
		String file_name = new String();
		if(_index!=0){
			file_name = fileName.substring(_index, fileName.length());
		}else{
			file_name = fileName;
		}
		return file_name;
	}


	/**
	 * Get the Details of Variable(s) [both Local Variable and Parameterized Variables]  of each methods and 
	 * Create the Data Structure in .debug_info.txt file.
	 * @param debug_info
	 * @param header_length
	 * @param abbrev_offset
	 * @param info_index
	 * @param info_startPoint
	 * @param debug_abbrev
	 * @param abbrev_pt
	 * @return current denig_info index pointer position
	 * @throws IOException
	 */
	private int setDebugInfoVariable(byte[] debug_info, int header_length,
			int abbrev_offset, int info_index, int info_startPoint, byte[] debug_abbrev,	int abbrev_pt) throws IOException {		

		int index = info_index;
		for (; debug_info[index]!=0; index++) {
			switch(debug_abbrev[abbrev_pt+1]){			//Check the TAG attributes.
			case 0x34:
				//DW_TAG_variable
				getAttributeDetails(debug_info, header_length, abbrev_offset, (index+1), info_startPoint, debug_abbrev, (abbrev_pt+3), false, 0);
				StructUnionHandler.setStructUnionInit(structUnionType);
				index += getAttributeEntryLength(debug_info, header_length, abbrev_offset, index, debug_abbrev);
				abbrev_pt = getDebugAbbrevPointer(debug_info[index+1], abbrev_offset, debug_abbrev);
				break;
			case 0x05:
				//DW_TAG_formal_parameter
				getAttributeDetails(debug_info, header_length, abbrev_offset, (index+1), info_startPoint, debug_abbrev, (abbrev_pt+3), false, 0);
				index += getAttributeEntryLength(debug_info, header_length, abbrev_offset, index, debug_abbrev);
				abbrev_pt = getDebugAbbrevPointer(debug_info[index+1], abbrev_offset, debug_abbrev);
				break;			
			default:	
				//Skip the other TAG attributes.
				index += getAttributeEntryLength(debug_info, header_length, abbrev_offset, index, debug_abbrev);
			abbrev_pt = getDebugAbbrevPointer(debug_info[index+1], abbrev_offset, debug_abbrev);
			break;
			}				
		}
		return index;
	}



	/**
	 * Get the Variable Details [Local variable, Global Variable, Pointer & Structure-Union] 
	 * of a particular Local or Parameterized variable.
	 * @param debug_info
	 * @param header_length
	 * @param abbrev_offset
	 * @param info_index
	 * @param info_startPoint
	 * @param debug_abbrev
	 * @param abbrev_pt
	 * @param global
	 * @throws IOException
	 */
	private void getAttributeDetails(byte[] debug_info, int header_length,
			int abbrev_offset, int info_index, int info_startPoint, byte[] debug_abbrev, 
			int abbrev_pt, boolean global, int structType) throws IOException {

		long variable_size = 0;										//Store the size of the variable in Byte
		byte[] location_value = new byte[1];						//Store the value of DW_AT_location.
		byte[] member_location_value = new byte[1];					//Store Structure or Union type variable member location
		String varName = new String();								//Store Variable Name		

		for(; debug_abbrev[abbrev_pt]!=0; abbrev_pt++){
			switch(debug_abbrev[abbrev_pt]){					//Check Attributes names for variables

			//Get the Variable name.
			case 0x03:
				//DW_AT_name
				switch(debug_abbrev[++abbrev_pt]){					//Check the DW_FORM_* values
				case 0x08:
					//DW-FORM-string [String type value in .debug_info section]
					int k = info_index;
					for (; debug_info[k]!=0; k++) {
						varName += (char)debug_info[k];								
					}
					info_index = k+1;
					break;
				case 0x0e:
					//DW_FORM_strp [Pointer value for indicating the .debug_str offset value.] 
					int address = Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index, 4, ReadHeader.EI_DATA), 16);							
					for (k = address; ReadDebugStr.debug_str[k]!=0; k++) {
						varName += (char)ReadDebugStr.debug_str[k];								
					}
					info_index += 4;
					break;
				default:
					//Skip other FORM values.
					info_index+= (int)(new DW_FORM_SIZE().getFormSize(debug_abbrev[abbrev_pt], debug_info, info_index));
				break;				
				}				

				break;

				//Get the variable offset location w.r.t. frame pointer.
			case 0x38:
				//DW_AT_data_member_location			[For Structure type variable only]
			case 0x02:
				//DW_AT_location						[For local or other type variable]		
				int loc_type = debug_abbrev[abbrev_pt];
				switch(debug_abbrev[++abbrev_pt]){		//Check the value type in .debug_info section.
				case 0x03:
					//DW_FORM_block2

					int block_length = Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index, 2, 
							ReadHeader.EI_DATA), 16);
					info_index+= 2;
					if(loc_type==0x38){
						member_location_value = new byte[block_length];					
						for (int i = 0; i < block_length; i++) {
							member_location_value[i]= debug_info[info_index++];
						}
					}else{
						location_value = new byte[block_length];					
						for (int i = 0; i < block_length; i++) {
							location_value[i]= debug_info[info_index++];
						}
					}																				
					break;
				case 0x04:
					//DW_FORM_block4

					block_length = Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index, 4, 
							ReadHeader.EI_DATA), 16);
					info_index+= 4;					
					if(loc_type==0x38){
						member_location_value = new byte[block_length];					
						for (int i = 0; i < block_length; i++) {
							member_location_value[i]= debug_info[info_index++];
						}
					}else{
						location_value = new byte[block_length];					
						for (int i = 0; i < block_length; i++) {
							location_value[i]= debug_info[info_index++];
						}
					}							
					break;
				case 0x09:
					//DW_FORM_block

					block_length = Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index, 1, 
							ReadHeader.EI_DATA), 16);
					info_index++;					
					if(loc_type==0x38){
						member_location_value = new byte[block_length];					
						for (int i = 0; i < block_length; i++) {
							member_location_value[i]= debug_info[info_index++];
						}
					}else{
						location_value = new byte[block_length];					
						for (int i = 0; i < block_length; i++) {
							location_value[i]= debug_info[info_index++];
						}
					}												
					break;
				case 0x0a:
					//DW_FORM_block1

					block_length = Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index, 1, 
							ReadHeader.EI_DATA), 16);
					info_index++;					
					if(loc_type==0x38){
						member_location_value = new byte[block_length];					
						for (int i = 0; i < block_length; i++) {
							member_location_value[i]= debug_info[info_index++];
						}
					}else{
						location_value = new byte[block_length];					
						for (int i = 0; i < block_length; i++) {
							location_value[i]= debug_info[info_index++];
						}
					}							
					break;
				default:
					break;
				}				
				break;					


				//Get the Variable size.
			case 0x49:				
				//DW_AT_type
				variable_size = getDW_AT_type(debug_info, info_startPoint, header_length, info_index,  abbrev_offset, debug_abbrev, abbrev_pt, varName);				
				abbrev_pt++;
				info_index+= (int)(new DW_FORM_SIZE().getFormSize(debug_abbrev[abbrev_pt], debug_info, info_index));
				break;				


			default:
				//Skip other Attributes.
				abbrev_pt++;
			info_index+= (int)(new DW_FORM_SIZE().getFormSize(debug_abbrev[abbrev_pt], debug_info, info_index));
			break;
			}
		}	
		//Set the Structure or Union type variable details.
		setStructureVariableInfo(varName, location_value, member_location_value, variable_size,structType);
		//Set the general type variable details.
		setVariableInfo(getFilename(fileName), varName, location_value, variable_size, global, structType);		
	}





	/**
	 * Set the Structure or Union type variable details informations and
	 * store the value in StructureUnionHandler class.
	 * @param varName
	 * @param location_value
	 * @param member_location_value
	 * @param variable_size
	 * @param structType
	 */
	private void setStructureVariableInfo(String varName,
			byte[] location_value, byte[] member_location_value, long variable_size, int structType) {

		if(structType==0 && variable_size!=0){			
			return;
		}
		if(location_value.length>1){			
			StructUnionHandler.location[0] = (byte)( location_value[1] | 0x80 );
			return;
		}

		if(sMember_index > 0){
			StructUnionHandler.memberName = (String[])ArrayExpend.expend(StructUnionHandler.memberName, sMember_index+1);			
			StructUnionHandler.size = (int[])ArrayExpend.expend(StructUnionHandler.size, sMember_index+1);
		}		

		if(member_location_value.length>1){
			if(sMember_index > 0){
				StructUnionHandler.locationDetails = (int[])ArrayExpend.expend(StructUnionHandler.locationDetails, sMember_index + 1);
			}
			StructUnionHandler.locationDetails[sMember_index]  = member_location_value[1];
		}		

		StructUnionHandler.memberName[sMember_index] = varName;		
		StructUnionHandler.size[sMember_index++] = (int)variable_size;
	}


	/**
	 * Get the variable DW_AT_type attribute and return the
	 * size of that variable.
	 * Also store the Structure or Union name in StructureUnionHandler class.
	 * @param debug_info
	 * @param info_startPoint
	 * @param info_header_length
	 * @param info_index
	 * @param abbrev_offset
	 * @param debug_abbrev
	 * @param abbrev_pt
	 * @param varName
	 * @return variable size
	 * @throws IOException
	 */
	private long getDW_AT_type(byte[] debug_info, int info_startPoint, int info_header_length,
			int info_index, int abbrev_offset,  byte[] debug_abbrev,	int abbrev_pt, String varName) throws IOException {

		String structName = new String();		
		long variable_size=0;
		int refInfoAddress =  Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index, 4, 
				ReadHeader.EI_DATA), 16) + info_startPoint;
		int abbrev_pointer = getDebugAbbrevPointer(debug_info[refInfoAddress], abbrev_offset, debug_abbrev);

		refInfoAddress++;

		switch(debug_abbrev[++abbrev_pointer]){				//Check the Variable's attribute type		

		//For pointer type variable
		case 0x0f:
			//DW_TAG_pointer_type

			//For general type variable
		case 0x24:
			//DW_TAG_base_type
			boolean flag=true;
			for (abbrev_pointer+=2 ; debug_abbrev[abbrev_pointer]!=0 && flag; abbrev_pointer++) {
				switch(debug_abbrev[abbrev_pointer]){
				case 0x0b:
					//DW_AT_byte_size
					switch(debug_abbrev[++abbrev_pointer]){
					case 0x0B:
						//DW_FORM_data1
						variable_size = Long.parseLong(ByteToString.byte2StringArray(debug_info, refInfoAddress, 1, 
								ReadHeader.EI_DATA), 16);									
						break;
					case 0x5:
						//DW_FORM_data2
						variable_size = Long.parseLong(ByteToString.byte2StringArray(debug_info, refInfoAddress, 2, 
								ReadHeader.EI_DATA), 16);									
						break;
					case 0x06:
						//DW_FORM_data4
						variable_size = Long.parseLong(ByteToString.byte2StringArray(debug_info, refInfoAddress, 4, 
								ReadHeader.EI_DATA), 16);									
						break;
					case 0x07:
						//DW_FORM_data8
						variable_size = Long.parseLong(ByteToString.byte2StringArray(debug_info, refInfoAddress, 8, 
								ReadHeader.EI_DATA), 16);									
						break;
					default:
						break;
					}														
					flag=false;
					break;
				default:
					abbrev_pointer++;
				refInfoAddress += (int)(new DW_FORM_SIZE().getFormSize(debug_abbrev[abbrev_pointer], debug_info, refInfoAddress));
				break;
				}	
			}									
			break;					



			//For Union type variable	
		case 0x17:
			//DW_TAG_union_type

			//For Structure type variable
		case 0x13:
			//DW_TAG_structure_type

			//For typedef of Structure or Union type variable
		case 0x16:
			//DW_TAG_typedef
			flag = true;			
			int tagTypeValue = debug_abbrev[abbrev_pointer];				//Get the current DW_TAG_* value
			for (abbrev_pointer+=2 ; debug_abbrev[abbrev_pointer]!=0 && flag; abbrev_pointer++) {
				switch(debug_abbrev[abbrev_pointer]){
				case 0x03:
					//DW_AT_name					[Get the Structure or Union Name]
					switch(debug_abbrev[++abbrev_pointer]){					//Check the DW_FORM_* values
					case 0x08:
						//DW-FORM-string
						int k = refInfoAddress;
						for (; debug_info[k]!=0; k++) {
							structName += (char)debug_info[k];								
						}												
						break;
					case 0x0e:
						//DW_FORM_strp
						int address = Integer.parseInt(ByteToString.byte2StringArray(debug_info, refInfoAddress, 4, ReadHeader.EI_DATA), 16);							
						for (k = address; ReadDebugStr.debug_str[k]!=0; k++) {
							structName += (char)ReadDebugStr.debug_str[k];								
						}
						refInfoAddress += 4;						
						break;
					default:						
						break;
					}	

					flag = false;
					break;					

				default:
					refInfoAddress += (int)(new DW_FORM_SIZE().getFormSize(debug_abbrev[abbrev_pointer], debug_info, refInfoAddress));
				break;
				}
			}

			//Set the Structure or Union type variable details [Member details] in StructUnionHandler class
			setStructureMember(debug_info, info_index, info_startPoint, info_header_length,
					abbrev_offset,refInfoAddress, abbrev_pt, debug_abbrev, tagTypeValue);


			//Set the Source file name and structure or Union name in StructUnionHandler class.
			StructUnionHandler.sourceFilename = getFilename(fileName);
			if(struct_index > 0){
				StructUnionHandler.structName =(String[]) ArrayExpend.expend(StructUnionHandler.structName, struct_index+1);
			}
			StructUnionHandler.structName[struct_index++] = structName;

			if(sObject_index > 0){
				StructUnionHandler.objName = (String[])ArrayExpend.expend(StructUnionHandler.objName, sObject_index+1);
			}
			StructUnionHandler.objName[sObject_index++] = varName;				

			abbrev_pointer++;
			refInfoAddress += (int)(new DW_FORM_SIZE().getFormSize(debug_abbrev[abbrev_pointer], debug_info, refInfoAddress));
			break;			



			//For Array type variable [both for single and multi dimension array]
		case 0x01:
			//DW_TAG_array_type

			int tempInfo_pointer = refInfoAddress - 1;
			abbrev_pointer += 2;
			boolean aFlag = true;
			upper_bound = new String();
			for (int i = abbrev_pointer; i < debug_abbrev.length && aFlag; i++) {
				switch(debug_abbrev[i]){
				case 0x49:
					//DW_AT_type

					//Get the each array element size
					array_variable_size = getDW_AT_type(debug_info, info_startPoint, info_header_length, refInfoAddress,  
							abbrev_offset, debug_abbrev, abbrev_pointer, varName);

					tempInfo_pointer += getAttributeEntryLength(debug_info, info_header_length, abbrev_offset, tempInfo_pointer, debug_abbrev) + 1;

					//Get the total length of an array [both for single and multi dimension array]
					int k;					
					for (int j = 0; j < debug_abbrev.length; j=k) {
						//Each DW_TAG_subrange_type attribute
						j= getDebugAbbrevPointer(debug_info[tempInfo_pointer], abbrev_offset, debug_abbrev);
						tempInfo_pointer++;
						if(debug_info[tempInfo_pointer-1]==0){							
							return -1;
						}
						for (k = j + 3; debug_abbrev[k]!=0; k++) {
							//For individual DW_TAG_subrange_type attribute [for multi-dimension array] 

							switch(debug_abbrev[k]){
							case 0x2F:
								//DW_AT_upper_bound								

								switch(debug_abbrev[++k]){								
								case 0x05:
									//DW_FORM_data2
									upper_bound += ":" +  String.valueOf(Integer.parseInt(ByteToString.byte2StringArray(debug_info, tempInfo_pointer, 2, ReadHeader.EI_DATA), 16));
									tempInfo_pointer+=2;
									break;
								case 0x06:
									//DW_FORM_data4
									upper_bound += ":" + String.valueOf(Integer.parseInt(ByteToString.byte2StringArray(debug_info, tempInfo_pointer, 4, ReadHeader.EI_DATA), 16));
									tempInfo_pointer+= 4;
									break;
								case 0x07:
									//DW_FORM_data8
									upper_bound += ":" + String.valueOf(Integer.parseInt(ByteToString.byte2StringArray(debug_info, tempInfo_pointer, 8, ReadHeader.EI_DATA), 16));
									tempInfo_pointer += 8;
									break;								
								case 0x0b:
									//DW_FORM_data1
									upper_bound += ":" + String.valueOf(Integer.parseInt(ByteToString.byte2StringArray(debug_info, tempInfo_pointer, 1, ReadHeader.EI_DATA), 16));
									tempInfo_pointer ++;
									break;
								default:
									k++;
								tempInfo_pointer+= (int)(new DW_FORM_SIZE().getFormSize(debug_abbrev[i], debug_info, tempInfo_pointer));
								break;								
								}											
								break;


							default:
								//Skip other attributes
								k++;
							tempInfo_pointer += (int)(new DW_FORM_SIZE().getFormSize(debug_abbrev[k], debug_info, tempInfo_pointer));
							break;
							}
						}
					}					
					aFlag = false;
					break;
				default:
					//Skip other attributes.
					i++;
				refInfoAddress += (int)(new DW_FORM_SIZE().getFormSize(debug_abbrev[i], debug_info, refInfoAddress));
				break;
				}
			}
			break;


		default:
			//Do nothing.
			break;
		}
		return variable_size;
	}




	/**
	 * Get the Member details of Structure or Union type variable. 
	 * @param debug_info
	 * @param info_index
	 * @param info_startPoint
	 * @param info_header_length
	 * @param abbrev_offset
	 * @param refInfoAddress
	 * @param abbrev_pt
	 * @param debug_abbrev
	 * @param tagTypeValue
	 * @throws IOException
	 */
	private void setStructureMember(byte[] debug_info, int info_index, int info_startPoint,
			int info_header_length, int abbrev_offset,int refInfoAddress, int abbrev_pt, byte[] debug_abbrev, int tagTypeValue) throws IOException {

		//Check the variable type and get the details of Structure or Union each members' details
		switch(tagTypeValue){

		case 0x13:	
			//DW_TAG_structure_type
		case 0x17:
			//DW_TAG_union_type

			structUnionType = true;
			refInfoAddress =  Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index, 4, 
					ReadHeader.EI_DATA), 16) + info_startPoint;
			refInfoAddress += getAttributeEntryLength(debug_info, info_header_length, abbrev_offset, refInfoAddress, debug_abbrev) + 1;
			abbrev_pt = getDebugAbbrevPointer(debug_info[refInfoAddress], abbrev_offset, debug_abbrev) ;
			for (int i = refInfoAddress; abbrev_pt<debug_abbrev.length-1 && debug_abbrev[abbrev_pt+1]==0x0D && i < debug_info.length; i++) {				
				getAttributeDetails(debug_info,info_header_length,abbrev_offset,i+1,info_startPoint,
						debug_abbrev,abbrev_pt+3, false, tagTypeValue);
				i += getAttributeEntryLength(debug_info, info_header_length, abbrev_offset, i, debug_abbrev);
				abbrev_pt = getDebugAbbrevPointer(debug_info[i+1], abbrev_offset, debug_abbrev) ;
			}	
			break;	


		case 0x16:
			//DW_TAG_typedef

			int tempInfoIndex = Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index, 4, 
					ReadHeader.EI_DATA), 16) + info_startPoint +1;
			int tempAbbrevPointer = getDebugAbbrevPointer(debug_info[tempInfoIndex-1], abbrev_offset, debug_abbrev);
			if(debug_abbrev[tempAbbrevPointer+1]==0x16){
				//Point to DW_TAG_typedef CU section.

				for (int i = tempAbbrevPointer + 3; i < debug_abbrev.length; i++) {
					switch(debug_abbrev[i]){
					case 0x49:
						//DW_AT_type
						info_index = tempInfoIndex;
						//Self method call.
						setStructureMember(debug_info, info_index, info_startPoint, info_header_length,
								abbrev_offset, refInfoAddress, abbrev_pt, debug_abbrev, 0x13);
						return;
					default:
						//Skip other attributes.
						i++;
					tempInfoIndex += (int)(new DW_FORM_SIZE().getFormSize(debug_abbrev[i], debug_info, tempInfoIndex));					
					break;
					}
				}				
			}
			break;
		default:
			//Do Nothing
			break;
		}		
	}


	/**
	 * Create the Local, Parameterized Variable & Global Variable in
	 * .debug_info.txt & .global_variable.txt files respectively.  
	 * @param filename
	 * @param varName
	 * @param location_value
	 * @param variable_size
	 * @param global -> check whether it is Global type variable or local variable.
	 * @param structType -> get the Structure or Union AT number.
	 * @throws IOException
	 */
	private void setVariableInfo(String filename, String varName,
			byte[] location_value, long variable_size, boolean global, int structType) throws IOException {


		if((location_value.length==1 && location_value[0]==0 && global) || (!global && variable_size==0) || structType!=0){
			return;						//Global variable only used here or Structure Variable is used.
		}

		//Write Source Filename in .global_variable.txt file.
		if(global){
			//Write Source File Name.			
			g_writer.write(filename + " $ ");
		}

		//Write the Variable Name
		if(global){
			g_writer.write(varName + " $ ");
		}else{
			writer.write(varName + " $ ");
		}

		//Write DW_AT_location value
		int loc_field_length = location_value.length;	
		switch((int)location_value[0]){
		case 3:
			if(global){				
				g_writer.write(Integer.parseInt(ByteToString.byte2StringArray(location_value, 1, loc_field_length-1, 
						ReadHeader.EI_DATA), 16) + "");
			}else{				
				writer.write(Integer.parseInt(ByteToString.byte2StringArray(location_value, 1, loc_field_length-1, 
						ReadHeader.EI_DATA), 16) + "");
			}				
			break;
		case -111:				//i.e. 0x91 -> DW_OP_fbreg
			if(global){				
				byte loc_val = (byte)(Integer.parseInt(ByteToString.byte2StringArray(location_value, 1, loc_field_length-1, 
						ReadHeader.EI_DATA), 16) | 0x80);				
				g_writer.write(loc_val + "");
			}else{				
				byte loc_val = (byte)( Integer.parseInt(ByteToString.byte2StringArray(location_value, 1, loc_field_length-1, 
						ReadHeader.EI_DATA), 16) | 0x80);
				writer.write( loc_val + "");
			}	
			break;
		default:
			break;
		}

		//Write the Variable Size
		if(global){
			g_writer.write(" $ " + variable_size + " $ ");
			g_writer.newLine();
		}else{
			if(variable_size==-1){		//In case of array type variable
				writer.write(" $ " + array_variable_size + upper_bound + " $ ");				
			}else{						//For general type variable
				writer.write(" $ " + variable_size + " $ ");
			}			
		}		
	}


	/**
	 * Create the Data Structure of .debug_info section [DW_TAG_subprogram] with the following details:
	 * subprogram: Method Name
	 * low_pc: Starting Address
	 * high_pc: Ending Address
	 * in .debug_info.txt file.
	 * @param debug_info
	 * @param abbrev_offset
	 * @param info_index
	 * @param debug_abbrev
	 * @param abbrev_pt
	 * @return info_index
	 * @throws IOException
	 */
	private int setDebugInfoSubprogram(byte[] debug_info, int abbrev_offset,
			int info_index, byte[] debug_abbrev, int abbrev_pt) throws IOException {

		abbrev_pt += 3;				//Point to First DW_AT_* attribute encoded value.
		info_index++;

		String funcName = new String();
		int low_pc_address=0, high_pc_address=0, frame_base = 0;

		for (int i = abbrev_pt; debug_abbrev[i]!=0; i++) {
			switch(debug_abbrev[i]){							//Check the Function Attributes

			//Get the Method Name
			case 0x03:
				//DW_AT_name
				switch(debug_abbrev[i+1]){	
				//Check the DW_FORM_* values
				case 0x08:
					//String value.
					int k = info_index;
					for (; debug_info[k]!=0; k++) {
						funcName += (char)debug_info[k];								
					}
					info_index = k+1;
					break;

				case 0x0e:
					//String pointer address.
					int address = Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index, 4, ReadHeader.EI_DATA), 16);							
					for (k = address; ReadDebugStr.debug_str[k]!=0; k++) {
						funcName += (char)ReadDebugStr.debug_str[k];								
					}
					info_index += 4;
					break;

				default:
					break;
				}					
				i++;
				break;


				//Get the low-pc value for each method
			case 0x11:
				//DW_AT_low_pc
				switch(debug_abbrev[++i]){

				case 0x01:
					//DW_FORM_addr
					low_pc_address = Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index, 4, ReadHeader.EI_DATA), 16);							
					break;

				default:
					break;
				}	
				info_index += 4;	
				break;


				//Get the high-pc value for each method
			case 0x12:
				//DW_AT_high_pc
				switch(debug_abbrev[++i]){

				case 0x01:
					//DW_FORM_addr
					high_pc_address = Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index, 4, ReadHeader.EI_DATA), 16);							
					break;

				default:
					break;
				}
				info_index += 4;	
				break;

				//Get the frame pointer value for each method.
			case 0x40:
				//DW_AT_frame_base
				switch(debug_abbrev[++i]){
				case 0x03:
					//DW_FORM_block2
					int length = Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index, 2, ReadHeader.EI_DATA), 16);
					frame_base = Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index+2, length, ReadHeader.EI_DATA), 16);
					info_index += (2+length);
					break;
				case 0x04:
					//DW_FORM_block4
					length = Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index, 4, ReadHeader.EI_DATA), 16);
					frame_base = Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index+4, length, ReadHeader.EI_DATA), 16);
					info_index += (4+length);
					break;
				case 0x05:
					//DW_FORM_data2
					frame_base = Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index, 2, ReadHeader.EI_DATA), 16);
					info_index+=2;
					break;
				case 0x06:
					//DW_FORM_data4
					frame_base = Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index, 4, ReadHeader.EI_DATA), 16);
					info_index+= 4;
					break;
				case 0x07:
					//DW_FORM_data8
					frame_base = Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index, 8, ReadHeader.EI_DATA), 16);
					info_index += 8;
					break;
				case 0x09:
					//DW_FORM_block
					length = Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index, 1, ReadHeader.EI_DATA), 16);
					frame_base = Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index+1, length, ReadHeader.EI_DATA), 16);
					info_index += (1+length);
					break;
				case 0x0a:
					//DW_FORM_block1
					length = Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index, 1, ReadHeader.EI_DATA), 16);
					frame_base = Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index+1, length, ReadHeader.EI_DATA), 16);
					info_index += (1+length);
					break;
				case 0x0b:
					//DW_FORM_data1
					frame_base = Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index, 1, ReadHeader.EI_DATA), 16);
					info_index ++;
					break;
				default:
					i++;
				info_index+= (int)(new DW_FORM_SIZE().getFormSize(debug_abbrev[i], debug_info, info_index));
				break;
				}
				break;

			default:
				//Skip other attributes
				i++;
			info_index+= (int)(new DW_FORM_SIZE().getFormSize(debug_abbrev[i], debug_info, info_index));
			break;
			}
		}
		DebugInfoStructure.frame_base = frame_base;		
		writer.write(funcName + " $ " + low_pc_address + " $ " + high_pc_address + " $ " + frame_base + " $ ");
		return info_index;		
	}


	/**
	 * Return the current debug_abbrev array index number
	 * corresponding to the given debug_info entry index number.
	 * @param info_index_number
	 * @param abbrev_offset
	 * @param debug_abbrev
	 * @return index_position
	 */
	private int getDebugAbbrevPointer(byte info_index_number, int abbrev_offset,
			byte[] debug_abbrev) {

		for(int i=abbrev_offset; i<debug_abbrev.length; i++){
			if(info_index_number==debug_abbrev[i]){
				return i;
			}else{
				for (int j = i; j < debug_abbrev.length; j++, i++) {
					if(debug_abbrev[j]==0 && debug_abbrev[j+1]==0){
						i++;
						break;
					}
				}				
			}
		}		
		return 0;
	}


	/**
	 * Get source file name with its absolute windows file path and
	 * Create a Data Structure in .source_file.txt file. 
	 * @param debug_info
	 * @param info_header_length
	 * @param abbrev_offset
	 * @param debug_abbrev
	 * @param info_startPoint
	 * @param index
	 * @throws IOException 
	 */
	private void getSourceFiles(byte[] debug_info, int info_header_length,
			int abbrev_offset, byte[] debug_abbrev, int info_startPoint, int info_index) throws IOException {

		String tagName = new String();
		for(int i=info_index; i<debug_info.length; i++){
			tagName = getTagName(debug_info[info_index], debug_abbrev, abbrev_offset);
			if(tagName.contains("DW_TAG_compile_unit")){				
				int info_length = getAttributeEntryLength(debug_info, info_header_length, abbrev_offset, 
						info_index, debug_abbrev);
				getSourceFileDetails(debug_info, i, info_length, debug_abbrev, abbrev_offset);
				break;
			}else{
				i += getAttributeEntryLength(debug_info, info_header_length, abbrev_offset, 
						i, debug_abbrev);				
			}
		}		
	}


	/**
	 * Create the Data Structure of Source filename with its absolute file path
	 * and its Memory location [low_pc and high_pc] in .source_file.txt file.
	 * @param debug_info
	 * @param info_index
	 * @param info_length
	 * @param debug_abbrev
	 * @param abbrev_offset
	 * @throws IOException
	 */
	private void getSourceFileDetails(byte[] debug_info, int info_index, int info_length, byte[] debug_abbrev, int abbrev_offset) throws IOException {

		String dirName = new String();
		fileName = new String();
		int low_pc_address=0, high_pc_address=0;

		for(int i=abbrev_offset; debug_abbrev[i]!=0; i++){
			if(debug_abbrev[i]==debug_info[info_index]){				//Index number Matched.
				i+=3;		//i -> Indicate the first Attribute Value.
				info_index++;

				for (int j = i; debug_abbrev[j]!=0; j++) {
					switch(debug_abbrev[j]){						//Check the DW_AT_* values from compile_unit TAG.

					//Get the absolute path of the Elf file location
					case 0x1b:
						//DW_AT_comp_dir
						switch(debug_abbrev[j+1]){					//Check the DW_FORM_* values

						case 0x08:
							//String value.
							int k = info_index;
							for (; debug_info[k]!=0; k++) {
								dirName += (char)debug_info[k];								
							}
							info_index = k+1;
							break;

						case 0x0e:
							//String pointer address.
							int address = Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index, 4, ReadHeader.EI_DATA), 16);							
							for (k = address; ReadDebugStr.debug_str[k]!=0; k++) {
								dirName += (char)ReadDebugStr.debug_str[k];								
							}
							info_index += 4;
							break;

						default:
							break;
						}						
						j++;
						break;


						//Get the Elf file name with relative path 
					case 0x03:
						//DW_AT_name
						switch(debug_abbrev[j+1]){					//Check the DW_FORM_* values

						case 0x08:
							//String value.
							int k = info_index;
							for (; debug_info[k]!=0; k++) {
								fileName += (char)debug_info[k];								
							}							
							fileName = fileName.replace("/", "\\");
							info_index=k+1;
							break;

						case 0x0e:
							//String pointer address.
							int address = Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index, 4, ReadHeader.EI_DATA), 16);							
							for (k = address; ReadDebugStr.debug_str[k]!=0; k++) {
								fileName += (char)ReadDebugStr.debug_str[k];								
							}
							info_index+=4;
							break;

						default:
							break;
						}						
						j++;
						break;


						//Get the low-pc value
					case 0x11:
						//DW_AT_low_pc
						switch(debug_abbrev[++j]){
						case 0x01:
							//DW_FORM_addr
							low_pc_address = Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index, 4, ReadHeader.EI_DATA), 16);							
							break;
						default:
							break;
						}	
						info_index += 4;						
						break;


						//Get the high-pc value
					case 0x12:
						//DW_AT_high_pc
						switch(debug_abbrev[++j]){
						case 0x01:
							//DW_FORM_addr
							high_pc_address = Integer.parseInt(ByteToString.byte2StringArray(debug_info, info_index, 4, ReadHeader.EI_DATA), 16);							
							break;
						default:
							break;
						}
						info_index += 4;						
						break;


					default:
						//Skip other attributes.
						j++;
					info_index+= (int)(new DW_FORM_SIZE().getFormSize(debug_abbrev[j], debug_info, info_index));						
					break;
					}						
				}
				break;					
			}
		}

		//Get the actual file name with its absolute path value
		int _index = dirName.lastIndexOf("\\");
		dirName = dirName.substring(0, _index);
		_index = fileName.indexOf("\\");
		if(_index != -1){
			fileName = fileName.substring(_index, fileName.length());
		}		
		//Create the data structure in .source_file.txt file
		s_writer.write(dirName + fileName + " $ " + low_pc_address + " $ " + high_pc_address);
	}


	/**
	 * Return TAG Name corresponding to the .debug_abbrev index number 
	 * with the help of .debug_abbrev offset address.
	 * @param info_abbrev_index
	 * @param debug_abbrev
	 * @param abbrev_offset
	 * @return String
	 */
	private String getTagName(byte info_abbrev_index, byte[] debug_abbrev, int abbrev_offset) {

		for(int i=abbrev_offset; i<debug_abbrev.length; i++){
			if(info_abbrev_index==debug_abbrev[i]){				
				return (new DW_TAG().dw_tag_lookup_table(debug_abbrev[i+1]));
			}else{
				for(int j=i;j<debug_abbrev.length-1;j++,i++){
					if(debug_abbrev[j]==0 && debug_abbrev[j+1]==0){
						i++; break;
					}
				}	
			}					
		}
		return null;
	}



	/**
	 * Return the length of each small Compilation Unit 
	 * w.r.t. current Abbrev_index number of .debug_info section.  
	 * @param debug_info
	 * @param info_header_length
	 * @param abbrev_offset
	 * @param info_pt
	 * @param debug_abbrev
	 * @return length
	 */
	private int getAttributeEntryLength(byte[] debug_info, int info_header_length, int abbrev_offset, int info_pt, byte[] debug_abbrev){

		int length=0;
		for(int j=abbrev_offset; j<(debug_abbrev.length-1); j++ ){

			if(debug_info[info_pt]==debug_abbrev[j]){									//Index number matched.				
				j+=4;
				for(int k=j;k<debug_abbrev.length; k++){
					if(debug_abbrev[k-1]==0 && debug_abbrev[k]==0){						
						return length;						
					}else{
						length+= new DW_FORM_SIZE().getFormSize(debug_abbrev[k], debug_info, (info_pt+1 + length));
						k++;
					}
				}

			}else{
				//Jump to the next debug_abbrev index entry point.				
				for(int k=j;k<debug_abbrev.length-1;k++,j++){
					if(debug_abbrev[k]==0 && debug_abbrev[k+1]==0){
						j++; break;
					}
				}					
			}
		}		
		return 0;
	}
}
