/**************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: DW_TAG
 * @version: 1.0
 * Date: June 2009 
 * @author Debabrata Hazra
 * Description: Look up Table of Tag encodings
 **************************************************/

package dwarf.lookup;

public class DW_TAG {

	public DW_TAG() {		
	}

	/**
	 * Look up table for DW_TAG_*
	 * @param value
	 * @return TAG name
	 */
	public String dw_tag_lookup_table(int value){

		switch(value){
		case 0x01:
			return "DW_TAG_array_type";
		case 0x02:
			return "DW_TAG_class_type";
		case 0x03:
			return "DW_TAG_entry_point";
		case 0x04:
			return "DW_TAG_enumeration_type";
		case 0x05:
			return "DW_TAG_formal_parameter";
		case 0x08:
			return "DW_TAG_imported_declaration";
		case 0x0a:
			return "DW_TAG_label";
		case 0x0b:
			return "DW_TAG_lexical_block";
		case 0x0d:
			return "DW_TAG_member";
		case 0x0f:
			return "DW_TAG_pointer_type";
		case 0x10:
			return "DW_TAG_reference_type";
		case 0x11:
			return "DW_TAG_compile_unit";
		case 0x12:
			return "DW_TAG_string_type";
		case 0x13:
			return "DW_TAG_structure_type";
		case 0x15:
			return "DW_TAG_subroutine_type";
		case 0x16:
			return "DW_TAG_typedef";
		case 0x17:
			return "DW_TAG_union_type";
		case 0x18:
			return "DW_TAG_unspecified_parameters";
		case 0x19:
			return "DW_TAG_variant";
		case 0x1a:
			return "DW_TAG_common_block";
		case 0x1b:
			return "DW_TAG_common_inclusion";
		case 0x1c:
			return "DW_TAG_inheritance";
		case 0x1d:
			return "DW_TAG_inlined_subroutine";
		case 0x1e:
			return "DW_TAG_module";
		case 0x1f:
			return "DW_TAG_ptr_to_member_type";
		case 0x20:
			return "DW_TAG_set_type";
		case 0x21:
			return "DW_TAG_subrange_type";
		case 0x22:
			return "DW_TAG_with_stmt";
		case 0x23:
			return "DW_TAG_access_declaration";
		case 0x24:
			return "DW_TAG_base_type";
		case 0x25:
			return "DW_TAG_catch_block";
		case 0x26:
			return "DW_TAG_const_type";
		case 0x27:
			return "DW_TAG_constant";
		case 0x28:
			return "DW_TAG_enumerator";
		case 0x29:
			return "DW_TAG_file_type";
		case 0x2a:
			return "DW_TAG_friend";
		case 0x2b:
			return "DW_TAG_namelist";
		case 0x2c:
			return "DW_TAG_namelist_item";
		case 0x2d:
			return "DW_TAG_packed_type";
		case 0x2e:
			return "DW_TAG_subprogram";
		case 0x2f:
			return "DW_TAG_template_type_param";
		case 0x30:
			return "DW_TAG_template_value_param";
		case 0x31:
			return "DW_TAG_thrown_type";
		case 0x32:
			return "DW_TAG_try_block";
		case 0x33:
			return "DW_TAG_variant_part";
		case 0x34:
			return "DW_TAG_variable";
		case 0x35:
			return "DW_TAG_volatile_type";
		case 0x4080:
			return "DW_TAG_lo_user";
		case 0xffff:
			return "DW_TAG_hi_user";
		default:
			return null;		
		}
	}

}
