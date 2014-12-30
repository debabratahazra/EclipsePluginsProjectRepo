/***********************************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: DW_FORM_SIZE
 * @version: 1.0
 * Date: June 2009 
 * @author Debabrata Hazra
 * Description: Return the size value of each DW_FORM_* [Attribute Form Encoding] 
 ***********************************************************************************/


package dwarf.lookup;

import read.elf.ReadHeader;
import dwarf.internal.ByteToString;

public class DW_FORM_SIZE {

	public DW_FORM_SIZE() {		
	}


	/**
	 * Return the pointer size of each DW_FORM_* 
	 * @param form_value
	 * @param debug_info
	 * @param info_index
	 * @return form_size
	 */
	public long getFormSize(int form_value, byte[] debug_info, int info_index){

		long length=0;
		switch(form_value){
		case 0x01:
			//DW_FORM_addr
			return 4;
		case 0x03:
			//DW_FORM_block2
			length = Integer.parseInt
			(ByteToString.byte2StringArray(debug_info, info_index, 2, ReadHeader.EI_DATA), 16);
			return length+2;
		case 0x04:
			//DW_FORM_block4
			length = Long.parseLong
			(ByteToString.byte2StringArray(debug_info, info_index, 4, ReadHeader.EI_DATA), 16);
			return length+4;
		case 0x05:
			//DW_FORM_data2
			return 2;
		case 0x06:
			//DW_FORM_data4
			return 4;
		case 0x07:
			//DW_FORM_data8
			return 8;
		case 0x08:
			//DW_FORM_string
			int i=0;
			for(int j=info_index; debug_info[j]!=0; i++,j++);
			return i+1;			
		case 0x09:
			//DW_FORM_block
			return (debug_info[info_index]+1);
		case 0x0a:
			//DW_FORM_block1
			return (debug_info[info_index]+1);
		case 0x0b:
			//DW_FORM_data1
			return 1;
		case 0x0c:
			//DW_FORM_flag
			return 1;
		case 0x0d:
			//DW_FORM_sdata
			return 1;
		case 0x0e:
			//DW_FORM_strp
			return 4;				//Pointer to .debug_str offset value [Pointer size: 4]
		case 0x0f:
			//DW_FORM_udata
			return 1;
		case 0x10:
			//DW_FORM_ref_addr
			return 4;				//As Target board address is 4-byte.
		case 0x11:
			//DW_FORM_ref1
			return 1;				//Pointer to .debug_abbrev offset value [Pointer size: 1]
		case 0x12:
			//DW_FORM_ref2
			return 2;				//Pointer to .debug_abbrev offset value [Pointer size: 2]
		case 0x13:
			//DW_FORM_ref4
			return 4;				//Pointer to .debug_abbrev offset value [Pointer size: 4]
		case 0x14:
			//DW_FORM_ref8
			return 8;				//Pointer to .debug_abbrev offset value [Pointer size: 8]
		case 0x15:
			//DW_FORM_ref_udata
			return 1;
		case 0x16:
			//DW_FORM_indirect

		default:
			return -1;				//Negative value for invalid value.
		}
	}
}
