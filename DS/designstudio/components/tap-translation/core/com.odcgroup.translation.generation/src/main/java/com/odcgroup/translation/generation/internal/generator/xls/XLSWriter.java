package com.odcgroup.translation.generation.internal.generator.xls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.odcgroup.translation.core.util.LanguageUtils;
import com.odcgroup.translation.generation.TranslationGenerationCore;
import com.odcgroup.translation.generation.xls.IColumnProvider;

/**
 * @author atr
 */
class XLSWriter {

	private String projectName;
	private Iterable<String[]> messages;
	private List<Locale> locales;

	/**
	 * @param file
	 * @throws IOException
	 * @throws CoreException
	 */
	public void write(IFile file) throws IOException, CoreException {
		
		Iterable<IColumnProvider> providers = TranslationGenerationCore.getColumnProviders();
		
		// init default column and create the cell style
		HSSFWorkbook workBook = new HSSFWorkbook();
		HSSFCellStyle style = workBook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		// sheet
		HSSFSheet sheet = workBook.createSheet("Sheet1");
		int rowIndex = 0;

		/*
		 * FIRST ROW ($A0=Project Name, $A1=<name>
		 */
		HSSFRow row0 = sheet.createRow(rowIndex++);
		HSSFCell msgRepositoryCell = row0.createCell((short)0);
		msgRepositoryCell.setCellValue(new HSSFRichTextString("Project Name"));
		msgRepositoryCell.setCellStyle(style);
		HSSFCell msgRepositoryName = row0.createCell((short) 1);
		msgRepositoryName.setCellValue(new HSSFRichTextString(this.projectName));

		/*
		 * SECOND ROW ($B0=key, $B1=WidgetId, $B2=def-locale, $B3=locale,...$Bn=locale
		 */
		HSSFRow row1 = sheet.createRow(rowIndex++);
		HSSFCell keyCell = row1.createCell((short) 0);
		keyCell.setCellValue(new HSSFRichTextString("Key"));
		keyCell.setCellStyle(style);

		short colIndex = 1;

		// add additional columns after the key column
		for(IColumnProvider provider : providers) {
			if(provider.isBeforeTranslations()) {
				HSSFCell widgetIdCell = row1.createCell(colIndex++);
				widgetIdCell.setCellValue(new HSSFRichTextString(provider.getHeader()));
				widgetIdCell.setCellStyle(style);
			}
		}

		// write locales
		for (Locale locale : this.locales) {
			String displayStr = LanguageUtils.getShortDisplayString(locale,Locale.ENGLISH);
			HSSFCell languageCell = row1.createCell((short) colIndex++);
			languageCell.setCellValue(new HSSFRichTextString(displayStr));
			languageCell.setCellStyle(style);
		}

		// add additional columns after the translation columns
		for(IColumnProvider provider : providers) {
			if(!provider.isBeforeTranslations()) {
				HSSFCell widgetIdCell = row1.createCell(colIndex++);
				widgetIdCell.setCellValue(new HSSFRichTextString(provider.getHeader()));
				widgetIdCell.setCellStyle(style);
			}
		}
		
		for (Locale locale : this.locales) {
			String displayStr = LanguageUtils.getShortDisplayString(locale,Locale.ENGLISH);
			HSSFCell languageCell = row1.createCell((short) colIndex++);
			languageCell.setCellValue(new HSSFRichTextString(displayStr + " HTML"));
			languageCell.setCellStyle(style);
		}

		/*
		 * OTHER ROWS (translations)
		 */
		for (String[] rowContent : messages) {
			HSSFRow row = sheet.createRow(rowIndex++);
			colIndex = 0;
			for (String text : rowContent) {
				if (null == text) {
					text = "";
				}
				row.createCell(colIndex++).setCellValue(new HSSFRichTextString(text));
			}
		}

		// Save to file
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		workBook.write(out);
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		if (!file.exists()) {
			file.create(in, true, new NullProgressMonitor());
			file.setDerived(true, null);
		} else {
			file.setContents(in, IResource.FORCE, new NullProgressMonitor());
			file.setDerived(true, null);
		}
	}

	/**
	 * @param projectName
	 * @param messages
	 * @param locales
	 */
	public XLSWriter(String projectName, Iterable<String[]> messages, List<Locale> locales) {
		this.projectName = projectName;
		this.messages = messages;
		this.locales = locales;
	}

}
