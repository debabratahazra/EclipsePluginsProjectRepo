
package com.odcgroup.tap.mdf.generation.xls;

import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;


public class TAPExcelCore {

	
	public static WritableCellFormat getDefaultCellFormat() {
		 
		 WritableFont courier10font = new WritableFont(WritableFont.COURIER, 10, WritableFont.NO_BOLD, true); 
		 WritableCellFormat courier10format = new WritableCellFormat  (courier10font); 
		
		return courier10format;
	}
	
	public static WritableCellFormat getTitleCellFormat() {
		WritableFont courier12Boldfont = new WritableFont(WritableFont.COURIER, 12, WritableFont.BOLD, true); 
		 WritableCellFormat courier12Boldformat = new WritableCellFormat  (courier12Boldfont); 
		 return courier12Boldformat;
	}
	
	
	
}
