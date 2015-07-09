package com.odcgroup.t24.version.translation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationPreferences;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.core.util.LanguageUtils;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.generation.GenerationCore;

public class VersionTranslationExcelWriter {

	private class RowData {
		
		private final String[] data;
		
		public final void setValue(int column, String value) {
			data[column] = value;
		}
		
		public void writeTo(HSSFRow row, short column) {
			for (int cx = 0; cx < data.length; cx++) {
				write(row, column++, data[cx]);
			}
			
		}
		public RowData(int columnCount) {
			data = new String[columnCount];
		}
	}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VersionTranslationExcelWriter.class);

	private final int MAX_ROW_COUNT = 32768;
	private final IProject project;
	private final IContainer targetContainer;

	private List<Locale> locales;
	private ITranslationManager manager;
	private HSSFCellStyle style;
	private HSSFWorkbook workBook;
	private int fileCount = 0;
	private int rowCount = 0;
	
	private void closeWorkbook() throws IOException {

		// autosize ON for all columns
		HSSFSheet sheet = getFirstSheet();
		short column = 0;
		sheet.autoSizeColumn(column++);
		sheet.autoSizeColumn(column++);
		sheet.autoSizeColumn(column++);
		for (Locale locale : getLocales()) {
			sheet.autoSizeColumn(column++);
		}

		/*// Save the workbook
		//FileOutputStream outputStream = FileUtils.openOutputStream(getFile());
		workBook.write(outputStream);
		IOUtils.closeQuietly(outputStream);
		
		// discard this workbook
		this.workBook = null;
		this.style = null;*/
		
		//OfsCore.getOfsProject(project).clearCache();

	}
	
	private void fetchData(String version, String field, ITranslation translation, List<RowData> rows) {
		for (ITranslationKind kind : translation.getAllKinds()) {
			short column = 0;
			RowData row = new RowData(3+getLocales().size());
			row.setValue(column++, version);
			row.setValue(column++, field);
			row.setValue(column++, translation.getDisplayName(kind));
			fetchTranslations(translation, kind, row, column);
			rows.add(row);
		}
	}

	private void fetchTranslations(ITranslation translation, ITranslationKind kind, RowData row, short column) {
		for (Locale locale : getLocales()) {
			String message = "";
			try {
				if (!translation.isInherited(kind, locale)) {
					message = translation.getText(kind, locale);
				}
			} catch (TranslationException ex) {
				String msg = "Error when reading translation ["+translation.getDisplayName(kind)+"] for "+translation.getOwner();
				//LOGGER.error(msg, ex);
			} finally {
				row.setValue(column++, message);
			}
		}
	}
	
	private File getFile() throws IOException {
		File targetFolder = targetContainer.getLocation().toFile();
		StringBuilder filename = new StringBuilder();
		filename.append(targetFolder.getCanonicalPath());
		filename.append("\\Version");
		filename.append("\\versions");
		if (fileCount > 0) {
			filename.append(fileCount);
		}
		fileCount++;
		filename.append(".xls");
		return new File(filename.toString());
	}
	
	
	private HSSFSheet getFirstSheet() throws IOException {
		HSSFSheet sheet = null;
		HSSFWorkbook wb = getWorkbook();
		if (wb.getNumberOfSheets() == 0) {
			sheet = wb.createSheet("Sheet1");
			writeHeader(sheet);
		} else {
			sheet = wb.getSheetAt(0);
		}
		return sheet;
	}
	
	private List<Locale> getLocales() {
		if (locales == null) {
			locales = new ArrayList<Locale>();
			ITranslationPreferences preferences = getTranslationManager().getPreferences();
			locales.add(preferences.getDefaultLocale());
			locales.addAll(preferences.getAdditionalLocales());
		}
		return locales;
	}

	private HSSFWorkbook getWorkbook() throws IOException {		
		if (this.workBook == null) {
			// create a new workbook
			this.workBook = new HSSFWorkbook();
			// init default column and create the cell style
			style = workBook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
			this.rowCount = 0;
		}
		return workBook;
	}
	
	private void write(HSSFRow row, short column, String value) {
		write(row, column, value, null);
	}
	
	private void write(HSSFRow row, short column, String value, HSSFCellStyle style) {
		HSSFCell cell = row.createCell(column);
		cell.setCellValue(new HSSFRichTextString(value));
		if (style != null) {
			cell.setCellStyle(style);
		}
	}
	
	private void write(Version version) throws IOException {

		List<RowData> rows = new ArrayList<RowData>();

		// fetch version's translations
		String versionName = version.getT24Name();
		ITranslation translation = getTranslationManager().getTranslation(version);
		fetchData(versionName, "", translation, rows);

		// fetch translations for each fields
		for (Field field : version.getFields()) {
			translation = getTranslationManager().getTranslation(field);
			fetchData(versionName,field.getName(), translation, rows);
		}
			
		// if the current sheet is full, then close current workbook. 
		if (rowCount + rows.size() > MAX_ROW_COUNT) {
			closeWorkbook();
		}
		
		// write data to the first sheet (automatically created if needed)
		HSSFSheet sheet = getFirstSheet();
		for (RowData rowData : rows) {
			HSSFRow row = sheet.createRow(rowCount++);
			rowData.writeTo(row, (short)0);
		}

	}

	private void writeHeader(HSSFSheet sheet) {
		
		this.rowCount = 0;
		short column = 0;

		// first row
		HSSFRow row = sheet.createRow(rowCount++);
		write(row, column++, "Project Name", style);
		write(row, column++, project.getName());

		// Second row
		column = 0;
		row = sheet.createRow(rowCount++);
		write(row, column++, "Version", style);
		write(row, column++, "Field", style);
		write(row, column++, "Type", style);

		// write locales 
		for (Locale locale : getLocales()) {
			String displayStr = LanguageUtils.getShortDisplayString(locale,	Locale.ENGLISH);
			write(row, column++, displayStr, style);
		}

		assert rowCount == 2;
		
	}

	protected final ITranslationManager getTranslationManager() {
		if (manager == null) {
			manager = TranslationCore.getTranslationManager(this.project);
		}
		return manager;
	}

	/**
	 * @param modelResources
	 * @throws CoreException
	 */
	public void write(Collection<IOfsModelResource> modelResources) throws CoreException {
		
		IOfsModelResource resource = null;
		
		try {
			
			for (Iterator<IOfsModelResource> iter = modelResources.iterator(); iter.hasNext(); ) {
				try {
					resource = iter.next();
					write((Version) resource.getEMFModel().get(0));
				} finally {
					if (resource != null) {
						resource.unload();
					}
				}
			}
		
		} catch (IOException ex) {
			String message = "Error while generating translations for version model '" + resource.getName() + "'";
			throw new CoreException(new Status(IStatus.ERROR, GenerationCore.PLUGIN_ID, 0, message, ex));
		
		} catch (InvalidMetamodelVersionException ex) {
			String message = "Error while generating translations for version model '" + resource.getName() + "'";
			throw new CoreException(new Status(IStatus.ERROR, GenerationCore.PLUGIN_ID, 0, message, ex));
		
		} finally {
			try {
				closeWorkbook();
			} catch (IOException ex) {
				throw new CoreException(new Status(IStatus.ERROR, GenerationCore.PLUGIN_ID, 0, "Error", ex));
			} 
		}
	}

	public VersionTranslationExcelWriter(IProject project, IContainer targetContainer) {
		this.project = project;
		this.targetContainer = targetContainer;
	}

}
