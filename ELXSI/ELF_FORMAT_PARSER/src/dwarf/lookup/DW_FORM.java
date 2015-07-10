/*************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: DW_FORM
 * @version: 1.0
 * Date: June 2009 
 * @author Debabrata Hazra
 * Description: Look up Table of Attribute form encodings
 *************************************************************/

package dwarf.lookup;

public class DW_FORM {

	public DW_FORM() {		
	}

	/**
	 * Look up table for FORM values
	 * @param value
	 * @return FORM name
	 */
	public String dw_form_lookup_table(int value){

		switch(value){
		case 0x01:
			return "DW_FORM_addr";
		case 0x03:
			return "DW_FORM_block2";
		case 0x04:
			return "DW_FORM_block4";
		case 0x05:
			return "DW_FORM_data2";
		case 0x06:
			return "DW_FORM_data4";
		case 0x07:
			return "DW_FORM_data8";
		case 0x08:
			return "DW_FORM_string";
		case 0x09:
			return "DW_FORM_block";
		case 0x0a:
			return "DW_FORM_block1";
		case 0x0b:
			return "DW_FORM_data1";
		case 0x0c:
			return "DW_FORM_flag";
		case 0x0d:
			return "DW_FORM_sdata";
		case 0x0e:
			return "DW_FORM_strp";
		case 0x0f:
			return "DW_FORM_udata";
		case 0x10:
			return "DW_FORM_ref_addr";
		case 0x11:
			return "DW_FORM_ref1";
		case 0x12:
			return "DW_FORM_ref2";
		case 0x13:
			return "DW_FORM_ref4";
		case 0x14:
			return "DW_FORM_ref8";
		case 0x15:
			return "DW_FORM_ref_udata";
		case 0x16:
			return "DW_FORM_indirect";
		default:
			return null;			
		}
	}

}
