package com.odcgroup.t24.mdf.generation.xls;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import jxl.Workbook;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.odcgroup.mdf.OutputStreamFactory;


public class T24DataModel {
	
	public void write(ExcelContext ctx, OutputStreamFactory factory) throws RowsExceededException, WriteException, IOException {

		final String name = "_Data_Model_Entities";
		String path = ctx.getExcelPath(name);
		ctx.info("Writing Excel file [" + name + "] to path [" + path + "]");

		OutputStream excelStream = null;
		WritableWorkbook wb = null;
		
		try {
			excelStream = factory.openStream(path);
			wb = Workbook.createWorkbook(excelStream);
			ctx.setWorkbook(wb, name);
			
			Map<String, String> mainClasses = ctx.getMainClasses();
			List<String> list =new ArrayList<String>(mainClasses.keySet());
			Collections.sort(list);
			
			// header
			ctx.createSheet("Applications");
			ctx.createColumns(new String[] {"Application", "Description"});
			ctx.writeln();
			
			// list of applications
			for (String qfn : list) {
				ctx.writeFileHyperLink(qfn, StringUtils.substringAfter(qfn, ":"));
				String desc = mainClasses.get(qfn);
				if (desc == null) desc = "";
				desc = desc.trim();
				// content of a cell is limited to 32767 characters.
				final int MAX_LEN = Short.MAX_VALUE;
				if (desc.length() > MAX_LEN) {
					String chunk = desc.substring(0, MAX_LEN-1);
					ctx.write(chunk, ctx.getItalicAndWrapCellFormat());
					desc = desc.substring(MAX_LEN); // ignore this
				} else {
					ctx.write(desc, ctx.getItalicAndWrapCellFormat());
				}
				ctx.writeln();
			}
			ctx.updateColumnsSize2(0,1);

		} finally {
			if (wb != null) {
				wb.write();
				try {
					wb.close();
				} catch (WriteException ex) {
					ctx.error(ex.getLocalizedMessage(), ex);
				}
			}
			if (excelStream != null) {
				excelStream.close();
			}
			ctx.info("Done writing Excel file [" + name + "]");
		}

	}

}
