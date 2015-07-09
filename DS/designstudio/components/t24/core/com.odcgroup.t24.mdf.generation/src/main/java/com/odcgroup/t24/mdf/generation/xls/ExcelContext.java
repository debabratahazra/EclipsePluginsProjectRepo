package com.odcgroup.t24.mdf.generation.xls;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import jxl.Cell;
import jxl.CellView;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.mdf.MessageHandler;


public class ExcelContext {
	
	/**
	 * The main sheet
	 */
	private class DomainInfo {
		
		private String domainName;
		private int nextSheetNumber = 0;
		private Map<String/*QFN*/, SheetInfo> sheets = new HashMap<String, SheetInfo>();
		
		public final SheetInfo createSheet(String qualifiedName) {
			SheetInfo sheet = getSheet(qualifiedName);
			if (workBook.getSheet(sheet.getName()) == null) {
				workBook.createSheet(sheet.getName(), sheet.getIndex());
			}
			return sheet;
		}
		
		public final String getFilename() {
			return getName().toLowerCase() + ".xls";
		}
		
		public final String getName() {
			return this.domainName;
		}
		
		public SheetInfo getSheet(String qualifiedName) {
			SheetInfo sheet = sheets.get(qualifiedName);
			if (sheet == null) {
				sheet = new SheetInfo(qualifiedName, nextSheetNumber++);
				sheets.put(qualifiedName, sheet);
			}
			return sheet;
		}

		public DomainInfo(String name) {
			this.domainName = name;
		}

	}

	/**
	 * Additional sheets 
	 */
	private class SheetInfo {
		private final int index;
		private String name;
		private final String qualifiedName;
		int column = 0;
		int row = 0;

		private String getCompactName(String value) {
			if (value.length() < SHEET_NAME_LIMIT) {
				return value;
			}
			return value.substring(0, SHEET_NAME_LIMIT - 6) + "..." + (index);
		}

		public final int getIndex() {
			return this.index;
		}
		
		public final String getName() {
			if (name == null) {
				int pos = qualifiedName.indexOf(':');
				if (pos != -1) {
					name = getCompactName(qualifiedName.substring(pos+1));
				} else {
					name = getCompactName(qualifiedName);
				}
			}
			return name;
		}
		
		public SheetInfo(String qualifiedName, int index) {
			this.qualifiedName = qualifiedName;
			this.index = index;
		}
	}

	private SheetInfo activeSheet;

	private WritableCellFormat courier10format = null;

	private WritableCellFormat courier10Wrapformat = null;

	private WritableCellFormat courier12Boldformat = null;
	
	private DomainInfo activeDomain;

	private Map<String /*Domain*/, DomainInfo> domains = new HashMap<String, DomainInfo>();

	private Map<String /*QFN*/, String /* description */> mainClasses = new HashMap<String, String>();
	
	private MessageHandler messageHandler; 

	private final int SHEET_NAME_LIMIT = 31;
	
	private WritableCellFormat underlineformat = null;
	
	private WritableWorkbook workBook;


	private DomainInfo getDomainInfo(String qualifiedName) {
		String domain = StringUtils.substringBefore(qualifiedName, ":");
		DomainInfo info = domains.get(domain);
		if (info == null) {
			info = new DomainInfo(domain);
			domains.put(domain, info);
		}
		return info;
	}

	private void sheetAutoFitColumns(WritableSheet sheet) {
		//sheet.getSettings().setDefaultColumnWidth(50);
		for (int i = 0; i < sheet.getColumns(); i++) {
			CellView cv = new CellView();
			cv.setAutosize(true);
			sheet.setColumnView(i, cv);
		}
	}
	
	private void sheetAutoFitColumns2(WritableSheet sheet, int fromColumn, int toColumn) {
		int lastColumn = Math.min(sheet.getColumns(), toColumn+1);
		if (lastColumn < 0) lastColumn = sheet.getColumns();
		for (int i = fromColumn; i < lastColumn; i++) {
			Cell[] cells = sheet.getColumn(i);

			int longestStrLen = -1;

			/* Find the widest cell in the column. */
			for (int j = 0; j < cells.length; j++) {
				String str = cells[j].getContents();
				if (str.length() > longestStrLen) {
					if (!str.isEmpty()) {
						longestStrLen = str.trim().length();
					}
				}
			}

			/* If not found, skip the column. */
			if (longestStrLen != -1) {

				/* If wider than the max width, crop width */
				if (longestStrLen > 255)
					longestStrLen = 255;
	
				CellView cv = new CellView();//sheet.getColumnView(i);
				/* Every character is 256 units wide, so scale it. */
				cv.setSize(longestStrLen*246);
				//cv.setSize((longestStrLen + /* (longestStrLen/2) */+(longestStrLen / 4)) * 256);
				//cv.setAutosize(true);
				sheet.setColumnView(i, cv);
			}
		}
	}

	protected final String getDomain(String qualifiedName) {
		return StringUtils.substringBefore(qualifiedName, ":");
	}
	
	public void createColumns(String[] names) throws RowsExceededException, WriteException {
		activeSheet.column = 0;
		for (String name : names) {
			write(activeSheet.column++, activeSheet.row, name, getTitleCellFormat());
		}
	}

	public final void createSheet(String qualifiedName) {
		activeSheet = getDomainInfo(qualifiedName).createSheet(qualifiedName);
	}

	public final void error(String message, Exception ex) {
		this.messageHandler.error(message, ex);
	}

	public final WritableSheet getActiveSheet() {
		return workBook.getSheet(activeSheet.getName());
	}

	public WritableCellFormat getDefaultCellFormat() throws WriteException {
		if (courier10format == null) {
			WritableFont courier10font = new WritableFont(WritableFont.ARIAL,
					WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD, false);
			courier10format = new WritableCellFormat(courier10font);
			courier10format.setVerticalAlignment(VerticalAlignment.TOP);
		}
		return courier10format;
	}

	public final String getDomain() {
		return this.activeDomain.getName();
	}

	// Excel files per domain will be located in doc directory
	public String getExcelPath(String domainName) {
		StringBuffer b = new StringBuffer();
		b.append("Domain").append(File.separatorChar);
		b.append(getFileName(domainName));
		return b.toString();
	}

	public final String getFileName(String qualifiedName) {
		return getDomainInfo(qualifiedName).getFilename();
	}

	public WritableCellFormat getItalicAndWrapCellFormat() throws WriteException {
		if (courier10Wrapformat == null) {
			WritableFont courier10font = new WritableFont(WritableFont.COURIER,
					10, WritableFont.NO_BOLD, true);
			courier10Wrapformat = new WritableCellFormat(courier10font);
			courier10Wrapformat.setVerticalAlignment(VerticalAlignment.TOP);
			courier10Wrapformat.setWrap(true);
		}
		return courier10Wrapformat;
	}

	@SuppressWarnings("unchecked")
	public final Map<String, String> getMainClasses() {
		return Collections.unmodifiableMap(mainClasses);
	}

	public WritableCellFormat getTitleCellFormat() throws WriteException {
		if (courier12Boldformat == null) {
			WritableFont courier12Boldfont = new WritableFont(
					WritableFont.COURIER, 12, WritableFont.BOLD, true);
			courier12Boldformat = new WritableCellFormat(courier12Boldfont);
			courier12Boldformat.setVerticalAlignment(VerticalAlignment.TOP);
		}
		return courier12Boldformat;
	}
	
	public WritableCellFormat getUnderlineCellFormat() throws WriteException {
		if (underlineformat == null) {
			WritableFont underlineFont = new WritableFont(WritableFont.ARIAL,
					WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD,
					false, UnderlineStyle.SINGLE, Colour.BLUE);
			underlineformat = new WritableCellFormat(underlineFont);
			underlineformat.setVerticalAlignment(VerticalAlignment.TOP);
		}
		return underlineformat;
	}

	public final void info(String message) {
		this.messageHandler.info(message);
		
	}

	public void setActiveSheet(String qualifiedName) {
		this.activeSheet = getDomainInfo(qualifiedName).getSheet(qualifiedName);
	}

	public final void setColumn(int column) {
		this.activeSheet.column = column;
	}

	public final void setMainClass(String qualifiedName, String description) {
		mainClasses.put(qualifiedName, description);
	}

	public final void setMessageHandler(MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}
	
	public final void setRow(int row) {
		this.activeSheet.row = row;
	}

	public final void setWorkbook(WritableWorkbook wb, String domain) {
		this.workBook = wb;
		this.activeDomain = new DomainInfo(domain);
		domains.put(domain, activeDomain);
		courier10format = null;
		courier10Wrapformat = null;
		courier12Boldformat = null;
		underlineformat = null;
	}
	
	public void setColumnWidth(int column, int width) {
		getActiveSheet().getColumnView(column).setDimension(width);
	}

	public void updateColumnsSize() {
		sheetAutoFitColumns(getActiveSheet());
	}
	
	public void updateColumnsSize2(int fromColumn, int toColumn) {
		sheetAutoFitColumns2(getActiveSheet(), fromColumn, toColumn);
	}
	
	public void setComment(int column, String comment, double width, double height) {
		WritableCell cell = getActiveSheet().getWritableCell(column, activeSheet.row);
        WritableCellFeatures cf = cell.getWritableCellFeatures();
        if (cf == null) {
			WritableCellFeatures wcf = new WritableCellFeatures();
	        wcf.setComment(comment, width, height);
	        cell.setCellFeatures(wcf);
        }
	}

	public void setComment(int column, String comment) {
		WritableCell cell = getActiveSheet().getWritableCell(column, activeSheet.row);
        WritableCellFeatures cf = cell.getWritableCellFeatures();
        if (cf == null) {
			WritableCellFeatures wcf = new WritableCellFeatures();
	        wcf.setComment(comment);
	        cell.setCellFeatures(wcf);
        }
	}

	public void write(int column, int row, String value, WritableCellFormat format) throws RowsExceededException, WriteException {
		if (value == null)
			value = "";
		Label label = null;
		if (format == null) {
			label = new Label(column, row, value, getDefaultCellFormat());
		} else {
			label = new Label(column, row, value, format);
		}
		getActiveSheet().addCell(label);
	}

	public void write(String value) throws RowsExceededException, WriteException {
		write(activeSheet.column++, activeSheet.row, value, null);
	}
	
	public void write(String value, WritableCellFormat format) throws RowsExceededException, WriteException {
		write(activeSheet.column++, activeSheet.row, value, format);
	}

	public void writeFileHyperLink(String qualifiedName, String label) throws RowsExceededException, WriteException {
		DomainInfo info = getDomainInfo(qualifiedName);
		SheetInfo sheet = info.getSheet(qualifiedName);
		String link = "HYPERLINK(\"[.\\" + info.getFilename() + "]" + sheet.getName() + "!A1\", \"" + label + "\")";
		Formula f = new Formula(activeSheet.column++, activeSheet.row, link, getUnderlineCellFormat());
		getActiveSheet().addCell(f);
	}

	public void writeln() throws RowsExceededException, WriteException {
		activeSheet.column = 0;
		activeSheet.row++;
	}

	public void writeln(String value) throws RowsExceededException, WriteException {
		writeln(value, null);
	}
	

	public void writeln(String value, WritableCellFormat format) throws RowsExceededException, WriteException {
		write(value, format);
		writeln();
	}

	public void writeSheetHyperlink(String qualifiedName, String label) throws RowsExceededException, WriteException {
		DomainInfo info = getDomainInfo(qualifiedName);
		SheetInfo sheet = info.getSheet(qualifiedName);
		String link = "HYPERLINK(\"#" + sheet.getName() + "!A1\", \"" + label + "\")";
		Formula f = new Formula(activeSheet.column++, activeSheet.row, link, getUnderlineCellFormat());
		getActiveSheet().addCell(f);
	}

	public ExcelContext() {
	}

}
