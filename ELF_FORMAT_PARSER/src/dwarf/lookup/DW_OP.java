/*****************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: DW_OP
 * @version: 1.0
 * Date: June 2009 
 * @author Debabrata Hazra
 * Description: Look up Table of Location Operation encodings
 *****************************************************************/

package dwarf.lookup;

public class DW_OP {

	public DW_OP() {		
	}


	/**
	 * Look up Table for DW_OP_* [Debug Location Operation] Encoding.
	 * @param op_code
	 * @return OP name
	 */
	public String dw_op_lookup_table(int op_code){

		if( op_code <= 0x4f && op_code >= 0x30){
			return ("DW_OP_lit" + (op_code - 0x30)); 
		}else if(op_code <= 0x6f && op_code >= 0x50){
			return ("DW_OP_reg" + (op_code - 0x50));
		}else if(op_code <= 0x8f && op_code >= 0x70){
			return ("DW_OP_breg" + (op_code - 0x70));
		}

		switch(op_code){
		case 0x03:
			return "DW_OP_addr";	
		case 0x06:
			return "DW_OP_deref"; 
		case 0x08:
			return "DW_OP_const1u";
		case 0x09:
			return "DW_OP_const1s";
		case 0x0a:
			return "DW_OP_const2u";
		case 0x0b:
			return "DW_OP_const2s";
		case 0x0c:
			return "DW_OP_const4u";
		case 0x0d:
			return "DW_OP_const4s";
		case 0x0e:
			return "DW_OP_const8u";
		case 0x0f:
			return "DW_OP_const8s";
		case 0x10:
			return "DW_OP_constu";
		case 0x11:
			return "DW_OP_consts";
		case 0x12:
			return "DW_OP_dup";
		case 0x13:
			return "DW_OP_drop";
		case 0x14:
			return "DW_OP_over";
		case 0x15:
			return "DW_OP_pick";
		case 0x16:
			return "DW_OP_swap";
		case 0x17:
			return "DW_OP_rot";
		case 0x18:
			return "DW_OP_xderef";
		case 0x19:
			return "DW_OP_abs";
		case 0x1a:
			return "DW_OP_and";
		case 0x1b:
			return "DW_OP_div";
		case 0x1c:
			return "DW_OP_minus";
		case 0x1d:
			return "DW_OP_mod";
		case 0x1e:
			return "DW_OP_mul";
		case 0x1f:
			return "DW_OP_neg";
		case 0x20:
			return "DW_OP_not";
		case 0x21:
			return "DW_OP_or";
		case 0x22:
			return "DW_OP_plus";
		case 0x23:
			return "DW_OP_plus_uconst";
		case 0x24:
			return "DW_OP_shl";
		case 0x25:
			return "DW_OP_shr";
		case 0x26:
			return "DW_OP_shra";						
		case 0x27:
			return "DW_OP_xor";
		case 0x2f:
			return "DW_OP_skip";
		case 0x28:
			return "DW_OP_bra";
		case 0x29:
			return "DW_OP_eq";
		case 0x2a:
			return "DW_OP_ge";
		case 0x2b:
			return "DW_OP_gt";
		case 0x2c:
			return "DW_OP_le";
		case 0x2d:
			return "DW_OP_lt";
		case 0x2e:
			return "DW_OP_ne";
		case 0x90:
			return "DW_OP_regx";
		case 0x91:
			return "DW_OP_fbreg";
		case 0x92:
			return "DW_OP_bregx";
		case 0x93:
			return "DW_OP_piece";
		case 0x94:
			return "DW_OP_deref_size";
		case 0x95:
			return "DW_OP_xderef_size";
		case 0x96:
			return "DW_OP_nop";
		case 0xe0:
			return "DW_OP_lo_user";
		case 0xff:
			return "DW_OP_hi_user";
		default:
			return null;			
		}		
	}

}
