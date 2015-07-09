package com.odcgroup.t24.mdf.generation.xls;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.mdf.OutputStreamFactory;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEnumeration;


public class T24Domain {
	
	private static String GROUP_SEPARTOR = "__";	
	
	private MdfDomain domain;
	
	private String getQualifiedName() {
		return domain.getQualifiedName().getQualifiedName();
	}
	
	private boolean isMainClass(MdfClass c) {
		return ! StringUtils.contains(c.getName(), GROUP_SEPARTOR);
	}
	
	@SuppressWarnings("unchecked")
	private List<T24Class> getClasses() {
		List<T24Class> classes = new ArrayList<T24Class>();
		for (MdfClass c : (List<MdfClass>)domain.getClasses()) {
			if (isMainClass(c)) {
				classes.add(new T24Class(c));
			}
		}
		return classes;
	}
	
	@SuppressWarnings("unchecked")
	private List<T24Enumeration> getEnumerations() {
		List<T24Enumeration> enumerations = new ArrayList<T24Enumeration>();
		for (MdfEnumeration e : (List<MdfEnumeration>)domain.getEnumerations()) {
			enumerations.add(new T24Enumeration(e));
		}
		return enumerations;
	}
	
	
	private void writeMainSheet(ExcelContext ctx) throws RowsExceededException, WriteException {
		ctx.createSheet(getQualifiedName());
		ctx.createColumns(new String[] {"Application", "Description"});
		ctx.writeln();
	}

	private void writeMainSheetContent(ExcelContext ctx) throws RowsExceededException, WriteException {
		ctx.setActiveSheet(getQualifiedName());
		ctx.setRow(1);
		for (T24Class clazz : getClasses()) {
			ctx.writeSheetHyperlink(clazz.getQualifiedName(), clazz.getName());

			String desc = clazz.getDescription();
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
	}

	private void writeClasses(ExcelContext ctx) throws RowsExceededException, WriteException {
		for (T24Class clazz : getClasses()) {
			clazz.writeTo(ctx);
		}
	}

	private void writeEnumerations(ExcelContext ctx) throws RowsExceededException, WriteException {
		for (T24Enumeration enumeration : getEnumerations()) {
			enumeration.writeTo(ctx);
		}
	}

	private void writeOtherSheets(ExcelContext ctx) throws RowsExceededException, WriteException {
		for (T24Class clazz : getClasses()) {
			ctx.createSheet(clazz.getQualifiedName());
		}
		for (T24Enumeration enumeration : getEnumerations()) {
			ctx.createSheet(enumeration.getQualifiedName());
		}
		writeClasses(ctx);
		writeEnumerations(ctx);
	}

	public void writeTo(ExcelContext ctx, OutputStreamFactory factory) throws RowsExceededException, WriteException, IOException {
		
		String domainName = domain.getName();
		String path = ctx.getExcelPath(domainName);
		ctx.info("Writing Excel file for domain [" + domain.getName() + "] to path [" + path + "]");

		OutputStream excelStream = factory.openStream(path);
		WritableWorkbook wb = null;
		
		try {
			WorkbookSettings settings = new WorkbookSettings();
			wb = Workbook.createWorkbook(excelStream, settings);
			ctx.setWorkbook(wb, domainName);
		
			writeMainSheet(ctx);
			writeOtherSheets(ctx);
			writeMainSheetContent(ctx);
			ctx.updateColumnsSize();
			
		} finally {
			
			if (wb != null) {
				wb.write();
				wb.close();
			}
			
			if (excelStream != null) {
				excelStream.close();
			}
			
			ctx.info("Done writing Excel file for domain [" + domainName + "]");
		}
	}
	
	public T24Domain(MdfDomain domain) {
		this.domain = domain;
	}
}
