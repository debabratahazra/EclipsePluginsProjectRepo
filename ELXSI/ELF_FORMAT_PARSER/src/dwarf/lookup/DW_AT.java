/********************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: DW_AT
 * @version: 1.0
 * Date: June 2009 
 * @author Debabrata Hazra
 * Description: Look up Table of Attribute encodings
 ********************************************************/

package dwarf.lookup;

public class DW_AT {

	public DW_AT() {		
	}

	/**
	 * Look up table for Attribute
	 * @param value
	 * @return ATTRIBUTE name
	 */
	public String dw_at_lookup_table(int value){

		switch(value){
		case 0x01:
			return "DW_AT_sibling";
		case 0x02:
			return "DW_AT_location";
		case 0x03:
			return "DW_AT_name";
		case 0x09:
			return "DW_AT_ordering";
		case 0x0b:
			return "DW_AT_byte_size";
		case 0x0c:
			return "DW_AT_bit_offset";
		case 0x0d:
			return "DW_AT_bit_size";
		case 0x10:
			return "DW_AT_stmt_list";
		case 0x11:
			return "DW_AT_low_pc";
		case 0x12:
			return "DW_AT_high_pc";
		case 0x13:
			return "DW_AT_language";
		case 0x15:
			return "DW_discr";
		case 0x16:
			return "DW_AT_discr_value";
		case 0x17:
			return "DW_AT_visibility";
		case 0x18:
			return "DW_AT_import";
		case 0x19:
			return "DW_AT_string_length";
		case 0x1a:
			return "DW_AT_common_reference";
		case 0x1b:
			return "DW_AT_comp_dir";
		case 0x1c:
			return "DW_AT_const_value";
		case 0x1d:
			return "DW_AT_containing_type";
		case 0x1e:
			return "DW_AT_default_value";
		case 0x20:
			return "DW_AT_inline";
		case 0x21:
			return "DW_AT_is_optional";
		case 0x22:
			return "DW_AT_lower_bound";
		case 0x25:
			return "DW_AT_producer";
		case 0x27:
			return "DW_AT_prototyped";
		case 0x2a:
			return "DW_AT_return_addr";
		case 0x2c:
			return "DW_AT_start_scope";
		case 0x2e:
			return "DW_AT_stride_size";
		case 0x2f:
			return "DW_AT_upper_bound";
		case 0x31:
			return "DW_AT_abstract_origin";
		case 0x32:
			return "DW_AT_accessibility";
		case 0x33:
			return "DW_AT_address_class";
		case 0x34:
			return "DW_AT_artificial";
		case 0x35:
			return "DW_AT_base_types";
		case 0x36:
			return "DW_AT_calling_convention";
		case 0x37:
			return "DW_AT_count";
		case 0x38:
			return "DW_AT_data_member_location";
		case 0x39:
			return "DW_AT_decl_column";
		case 0x3a:
			return "DW_AT_decl_file";
		case 0x3b:
			return "DW_AT_decl_line";
		case 0x3c:
			return "DW_AT_declaration";
		case 0x3d:
			return "DW_AT_decr_list";
		case 0x3e:
			return "DW_AT_encoding";
		case 0x3f:
			return "DW_AT_external";
		case 0x40:
			return "DW_AT_frame_base";
		case 0x41:
			return "DW_AT_friend";
		case 0x42:
			return "DW_AT_identifier_case";
		case 0x43:
			return "DW_AT_macro_info";
		case 0x44:
			return "DW_AT_namelist_item";
		case 0x45:
			return "DW_AT_priority";
		case 0x46:
			return "DW_AT_segment";
		case 0x47:
			return "DW_AT_specification";
		case 0x48:
			return "DW_AT_static_link";
		case 0x49:
			return "DW_AT_type";
		case 0x4a:
			return "DW_AT_use_location";
		case 0x4b:
			return "DW_AT_variable_parameter";
		case 0x4c:
			return "DW_AT_virtuality";
		case 0x4d:
			return "DW_AT_vtable_elem_location";
		case 0x2000:
			return "DW_AT_lo_user";
		case 0x3fff:
			return "DW_AT_hi_user";
		default:
			return null;			
		}
	}

}
