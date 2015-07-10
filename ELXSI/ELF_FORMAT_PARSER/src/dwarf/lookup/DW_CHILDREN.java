/******************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: DW_CHILDREN
 * @version: 1.0
 * Date: June 2009 
 * @author Debabrata Hazra
 * Description: Look up Table of Child Determination encodings
 *****************************************************************/


package dwarf.lookup;

public class DW_CHILDREN {

	public DW_CHILDREN() {		
	}

	/**
	 * Look up table for CHILDREN
	 * @param value
	 * @return CHILDREN availability
	 */
	public String dw_children_lookup_table(int value){

		switch(value){
		case 0x0:
			return "DW_CHILDREN_no";
		case 0x1:
			return "DW_CHILDREN_yes";
		default:
			return null;		
		}
	}

}
