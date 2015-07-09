package com.odcgroup.t24.version.translation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.core.util.LanguageUtils;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelURIConverter;

/**
 * Import translations from an excel file back to versions/fields
 */
public class VersionTranslationsExcelImporter {
	
	private static String PLUGIN_ID = "com.odcgroup.t24.version.model";
	
	private static Logger logger = LoggerFactory.getLogger(VersionTranslationsExcelImporter.class);
	
	private IProject project;

	/** The excel workbook. */
	private HSSFWorkbook workbook = null;

	private int updatedTranslation;
	
	private List<Locale> locales = new ArrayList<Locale>();	
	
	private int numberOfLanguages = 0;
	
	private Map<String, URI> uriCache = new HashMap<String, URI>();
	
	private Map<URI, VersionData> versionDataMap = new LinkedHashMap<URI, VersionData>();

	private class MessageData {
		private final String name;
		private int starIndex = -1;
		private final String translationType;
		private final String[] messages;
		public final void setMessage(int index, String message) {
			messages[index] = message;
		}
		public final String getMessage(int index) {
			return messages[index];
		}
		public final String getTranslationType() {
			return translationType;
		}
		public final String getName() {
			return name;
		}
		public final int getStarIndex() {
			return starIndex;
		}
		public MessageData(int numberOfMessages, String translationType, String name) {
			this.translationType = translationType;
			this.messages = new String[numberOfMessages];
			this.name = name;
		}
	}
	
	private class VersionData {
		private List<MessageData> data = new ArrayList<MessageData>(0);
		private List<MessageData> fields = new ArrayList<MessageData>(0);
	}
	
	
	private String buildIdentifier(String versionName, String fieldName) {
		StringBuilder id = new StringBuilder();
		id.append("version/");
		id.append(versionName.replace(".", "_"));
		id.append(".version");
		if (StringUtils.isNotBlank(fieldName)) {
			id.append("#");
			id.append(fieldName);
		}
		return id.toString();
	}
	
	
	/**
	 * @param sheet
	 * @return an error status
	 */
	private void readMessages(String filename) throws CoreException {

		
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filename));
			workbook = new HSSFWorkbook(fs);
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			// read project-name
			HSSFCell cell = sheet.getRow(0).getCell((short)1);
			String projectName = cell.getRichStringCellValue().toString();
			project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			if (project == null) { 
				String message = "Cannot find the project ["+projectName+"] in the current workspace";  //$NON-NLS-1$
				IStatus status = new Status(Status.ERROR, PLUGIN_ID, IStatus.OK, message, null);
				throw new CoreException(status);
			}
			
			// number of distinct languages
			numberOfLanguages = sheet.getRow(1).getPhysicalNumberOfCells() - 3;
			
			// decode locale from languages
			locales = new ArrayList<Locale>();
			short columnIndex = 3;
			for (short lx = 0; lx < numberOfLanguages; lx++) {
				boolean boolStatus = true;
				cell = sheet.getRow(1).getCell(columnIndex);
				String language = StringUtils.trim(cell.getRichStringCellValue().toString());
				if(!boolStatus) {
					columnIndex++;
					continue;
				}
				
				Locale locale = LanguageUtils.getLanguage(language, Locale.ENGLISH);
				if (locale == null) {
					String message = "The language [" + language + "] defined in line[2], column[" + (columnIndex + 1) //$NON-NLS-1$
							+ "], cannot be recognized."; //$NON-NLS-1$
					IStatus status = new Status(Status.ERROR, PLUGIN_ID, IStatus.OK, message, null);
					throw new CoreException(status);
				}
				locales.add(locale);
				
				columnIndex++;
			}
	
			// read all the messages into the message table
			int numberOfRows = sheet.getPhysicalNumberOfRows();
			for (int rowIndex = 2; rowIndex < numberOfRows; rowIndex++) {
	
				// get the row
				HSSFRow row = sheet.getRow(rowIndex);
	
				cell = null;
	
				// read the version name
				cell = row.getCell((short)0);
				String versionName = cell.getRichStringCellValue().toString();
				if (StringUtils.isBlank(versionName)) {
					// ignore this line, and continue
					continue;
				}
					
				// read the field name (empty when translation is related to the version itself)
				cell = row.getCell((short)1);
				String fieldName = cell.getRichStringCellValue().toString();
	
				// read the translations type (this should be the display name of a ITranslationKind) 
				cell = row.getCell((short)2);
				String translationType = cell.getRichStringCellValue().toString();
				if (StringUtils.isBlank(translationType)) {
					// ignore this line, and continue
					continue;
				}
	
				// get resource model URI from version name/field name
				// whenever the fieldname is * (star), a numeric suffix is added , as we can have multiple
				// star fields for the save version.
				String identifier = buildIdentifier(versionName, fieldName);
				URI uri = uriCache.get(identifier);
				if (uri == null) {
					uri = ModelURIConverter.createModelURI(identifier);
					uriCache.put(identifier, uri);
				}
				
				// version
				VersionData versionData = versionDataMap.get(uri.trimFragment());
				if (versionData == null) {
					versionData = new VersionData();
					versionDataMap.put(uri.trimFragment(), versionData);
				}

	
				// version or field name
				String name = null;
				
				List<MessageData> translations = null;
				if (uri.hasFragment()) {
					name = fieldName;
					translations = versionData.fields;
				} else {
					name = versionName;
					translations = versionData.data;
				}
	
				// read message for each language
				columnIndex = 3;
				MessageData data = new MessageData(numberOfLanguages, translationType, name);
				for (int localeIndex = 0; localeIndex < numberOfLanguages; localeIndex++) {
					HSSFCell cellValue = row.getCell((short) columnIndex++);
					String text = "";  //$NON-NLS-1$
					if (cellValue != null) {
						int cellType = cellValue.getCellType();
						// DS-1543
						if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
							double d = cellValue.getNumericCellValue();
							text = new Double(d).intValue() + "";  //$NON-NLS-1$
						} else {
							text = cellValue.getRichStringCellValue().getString();
						}
					}
					data.setMessage(localeIndex, text); 
				}
				
				translations.add(data);
	
			}
			
			// we can have multiple field with the same name '*' star, so we define a star index 
			// (zero based) for each version having one ore more 'star' fields
			// compute the correct index for all fields with name '*'
			for (VersionData vd : versionDataMap.values()) {
				int fx = 0, index = 0, step = 0;
				List<MessageData> fdList = vd.fields;
				while (fx < fdList.size()) {
					MessageData fd = fdList.get(fx);
					if ("*".equals(fd.name)) {
						fd.starIndex = index;
						if (step > 0) index++;
						step = 1 - step;
					} else {
						fd.starIndex = -1;
					}
					fx++;
				}
				
			}
			
		} catch (IOException ex) {
			String message = "Error opening the excel file : " + filename + " ";
			IStatus status = new Status(Status.ERROR, PLUGIN_ID, IStatus.OK, message, ex);
			throw new CoreException(status);

		} finally {
			workbook = null;
		}
	
	}
	
	private Map<String, ITranslationKind> kindMap = new HashMap<String, ITranslationKind>();

	private void updateTranslations(MessageData data, ITranslation translation) {
		
		String translationType = data.getTranslationType();
		ITranslationKind kind = kindMap.get(translationType);
		if (kind == null) {
			for (ITranslationKind type : translation.getAllKinds()) {
				if (translationType.equalsIgnoreCase(translation.getDisplayName(type))) {
					kindMap.put(translationType, type);
					kind = type;
					break;
				}
			}
		}
	
		if (kind != null) {

			for (int lx = 0; lx < numberOfLanguages; lx++) {
				Locale locale = locales.get(lx);
				String newText = data.getMessage(lx);
				if (StringUtils.isNotBlank(newText)) {
					try {
						if (! translation.isProtected()) {
							String oldText = translation.getText(kind, locale);
							if (!newText.equals(oldText)) {
								translation.setText(kind, locale, newText);
								updatedTranslation++;
							}
						}
					} catch (TranslationException ex) {
						logger.error("Error when reading translation", ex);
					}
				}
			}	
			
		} else {
			// TODO error
		}

	}
	
	
	private Field lookupField(Version version, String fieldName) {
		for (Field field : version.getFields()) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		return null;
	}
	
	private Field lookupStarField(Version version, int starIndex) {
		int index = 0;
		for (Field field : version.getFields()) {
			if (field.getName().equals("*")) {
				if (index++ == starIndex) {
					return field;
				}
			}
		}
		return null;
	}
	
	private void updatesModels() throws CoreException {
		
		ITranslationManager tm = TranslationCore.getTranslationManager(project);

		IOfsProject ofsProject = OfsCore.getOfsProject(project);		
		
		for (URI uri : versionDataMap.keySet()) {
			IOfsModelResource resource = null;
			Version version = null;
			try {
				resource = ofsProject.getOfsModelResource(uri);
				version = (Version) resource.getEMFModel().get(0);
				ITranslation translation = tm.getTranslation(version);	
				VersionData versionData = versionDataMap.get(uri);
				for (MessageData data : versionData.data) {
					updateTranslations(data, translation);
				}
				
				// fields
				for (MessageData data : versionData.fields) {
					Field field = null;
					if (data.getStarIndex() !=-1) { 
						field = lookupStarField(version, data.getStarIndex());
					} else {
						field = lookupField(version, data.getName());
					}
					if (field != null) {
						translation = tm.getTranslation(field);
						updateTranslations(data, translation);
					}
				}
				
			} catch (ModelNotFoundException ex) {
				IStatus status = new Status(IStatus.ERROR, PLUGIN_ID, "An error occured when loading " + uri, ex);  //$NON-NLS-1$
				logger.error(status.getMessage(), ex);
				throw new CoreException(status);
			} catch (IOException ex) {
				IStatus status = new Status(IStatus.ERROR, PLUGIN_ID, "An error occured when loading model from " + resource.getURI(), ex);  //$NON-NLS-1$
				logger.error(status.getMessage(), ex);
				throw new CoreException(status);
			} catch (InvalidMetamodelVersionException ex) {
				IStatus status = new Status(IStatus.ERROR, PLUGIN_ID, "An error occured when loading model from" + resource.getURI(), ex);  //$NON-NLS-1$
				logger.error(status.getMessage(), ex);
				throw new CoreException(status);
			} finally {
				if (resource != null) {
					try {
						if (this.updatedTranslation > 0) {
							logger.trace("Saving modifications of " + resource.getURI()); //$NON-NLS-1$
							version.eResource().save(null);
						}
					} catch (IOException ex) {
						IStatus status = new Status(IStatus.ERROR, PLUGIN_ID, "An error occured when saving " + resource.getURI(), ex);  //$NON-NLS-1$
						logger.error(status.getMessage(), ex);
						throw new CoreException(status);
					} finally {
						resource.unload();
					}
				}
			}
			
		}

	}

	/**
	 * Perform the importation of the excel file
	 * 
	 * @return an error status
	 */
	public void importTranslations(String filename) throws CoreException {
		
		readMessages(filename);
		
		class MyWorkspaceJob extends WorkspaceJob {
			public MyWorkspaceJob() {
				super("Import Verions Translations");
			}
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				try {
					updatesModels();
				} finally {
					logger.info("Import Versions Translations: #updates: " + updatedTranslation);
				}
				return Status.OK_STATUS;
			}
		};
		MyWorkspaceJob job = new MyWorkspaceJob();
		job.setRule(OfsCore.getOfsProject(project).getModelFolder("version").getResource());
		job.schedule();
		try {
			job.join();
		} catch (InterruptedException ex) {
			logger.error(ex.getMessage(),ex);
		}
	}


	/**
	 * Constructor
	 * 
	 * @param holder
	 *            The message repository to store the data
	 */
	public VersionTranslationsExcelImporter() {
	}
	
}
