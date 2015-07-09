package com.odcgroup.translation.generation.ui.internal.wizard;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationModelVisitor;
import com.odcgroup.translation.core.ITranslationModelVisitorHandler;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.core.translation.BaseTranslation;
import com.odcgroup.translation.core.util.LanguageUtils;
import com.odcgroup.translation.generation.ITranslationKey;
import com.odcgroup.translation.generation.TranslationGenerationCore;
import com.odcgroup.translation.generation.xls.IColumnProvider;
import com.odcgroup.translation.ui.TranslationUICore;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;

/**
 * Import the translation from an excel file
 * 
 * @author atr
 */
public class XLSImporter {
	
	private static Logger logger = LoggerFactory.getLogger(BaseTranslation.class);
	
	private IProject project;
	private Iterable<IColumnProvider> providers;

	/** The excel workbook. */
	private HSSFWorkbook hssfworkbook = null;

	private int updatedTranslation;
	
	private List<Locale> locales = new ArrayList<Locale>();	
	
	private Map<String, String[]> messages = new TreeMap<String, String[]>();
	
	/**/
	private Set<Resource> modifiedResources = new HashSet<Resource>();

	/**
	 * @param sheet
	 * @return an error status
	 */
	private void readMessages(HSSFSheet sheet, String filename) throws CoreException {

		// read project-name
		HSSFCell cell = sheet.getRow(0).getCell((short)1);
		String projectName = cell.getRichStringCellValue().toString();
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		if (project == null) { 
			String message = "Cannot find the project ["+projectName+"] in the current workspace";  //$NON-NLS-1$
			IStatus status = new Status(Status.ERROR, TranslationUICore.PLUGIN_ID, IStatus.OK, message, null);
			throw new CoreException(status);
		}
		
		// read the languages
		int numberOfLanguages = sheet.getRow(1).getPhysicalNumberOfCells() - 1;
		
		locales = new ArrayList<Locale>();
		short columnIndex = 1;
		for (short lx = 0; lx < numberOfLanguages; lx++) {
			boolean boolStatus = true;
			cell = sheet.getRow(1).getCell(columnIndex);
			String language = StringUtils.trim(cell.getRichStringCellValue().toString());
			for(IColumnProvider provider : providers) {
				if(provider.getHeader().equals(language) || language.endsWith("HTML")) {
					boolStatus = false;
					break;
				}
			}
			if(!boolStatus) {
				columnIndex++;
				continue;
			}
			
			Locale locale = LanguageUtils.getLanguage(language, Locale.ENGLISH);
			if (locale == null) {
				String message = "The language [" + language + "] defined in line[2], column[" + (columnIndex + 1) //$NON-NLS-1$
						+ "], is not recognized."; //$NON-NLS-1$
				IStatus status = new Status(Status.ERROR,
						TranslationUICore.PLUGIN_ID, IStatus.OK, message, null);
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

			// read the message key
			cell = row.getCell((short)0);
			String key = cell.getRichStringCellValue().toString();
			if (StringUtils.isNotEmpty(key)) {
				
				String[] texts = new String[numberOfLanguages];
				messages.put(key, texts);

				// read message for each language
				columnIndex = 1;
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
					texts[localeIndex] = text; 
				}
			}
		}

	}
	
	private void importMessages() throws CoreException {
		
		ITranslationManager tm = TranslationCore.getTranslationManager(project);

		IOfsProject ofsProject = OfsCore.getOfsProject(project);
		
		Set<String> modelNames = new HashSet<String>(Arrays.asList(OfsCore.getRegisteredModelNames()));
		modelNames.add("mml");  //$NON-NLS-1$
		
		Set<IOfsModelResource> ofsResources = ofsProject.getModelResourceSet().getAllOfsModelResources(
				modelNames.toArray(new String[]{}), IOfsProject.SCOPE_PROJECT);

		for (IOfsModelResource res : ofsResources) {
			try {
				List<EObject> eObjList = res.getEMFModel();
				if (eObjList.size() > 0) {
					EObject model = eObjList.get(0);
					ITranslationModelVisitor visitor = tm.getTranslationModelVisitor(model);
					if (visitor != null) {
						visitor.visit(new ITranslationModelVisitorHandler() {
							public void notify(ITranslation translation) {
								ITranslationKey key = TranslationGenerationCore.getTranslationKey(project, translation);
								// DS-4024 Skip translation without key (the key is not generated because it has never been translated)
								if (key != null) {
									for (ITranslationKind kind : key.getTranslationKinds()) {
										String msgkey = key.getKey(kind);
										if (msgkey != null) {
											for (Locale locale : locales) {
												if (messages.containsKey(msgkey)) {
													int localeIndex = locales.indexOf(locale);
													String[] texts = messages.get(msgkey);
													String newText = texts[localeIndex];
													if (StringUtils.isNotEmpty(newText)) {
														try {
															String oldText = translation.getText(kind, locale);
															if (!StringUtils.equals(newText, oldText)) {
																if (!translation.isProtected()) {
																	translation.setText(kind, locale, newText);
																	updatedTranslation++;
																	EObject owner = (EObject)translation.getOwner();
																	modifiedResources.add(owner.eResource());
																}
															}
														} catch (TranslationException ex) {
															String message = "Cannot change the translation for key:["+msgkey+"] kind:["+kind+"] locale:["+locale+"] text:["+newText+"]";  //$NON-NLS-1$
															TranslationUICore.getDefault().logError(message, ex);
														}
													}
												}
											}
										}
									}
								}
							}
						});
					}
				}
			} catch (IOException ex) {
				IStatus status = new Status(IStatus.ERROR, TranslationUICore.PLUGIN_ID,
						"Error while collecting translations for model " + res.getURI(), ex); //$NON-NLS-1$
				throw new CoreException(status);
			} catch (InvalidMetamodelVersionException ex) {
				IStatus status = new Status(IStatus.ERROR, TranslationUICore.PLUGIN_ID,
						"Error while collecting translations for model " + res.getURI(), ex);  //$NON-NLS-1$
				throw new CoreException(status);
			}
		}
		
		for (Resource resource : modifiedResources) {
			try {
				logger.trace("Saving modifications of " + resource.getURI()); //$NON-NLS-1$
				resource.save(null);
			} catch (IOException ex) {
				IStatus status = new Status(IStatus.ERROR, TranslationUICore.PLUGIN_ID,
						"Unable to save " + resource.getURI() + resource.getURI(), ex);  //$NON-NLS-1$
				throw new CoreException(status);
			}
		}
		
		
		TranslationUICore.getDefault().logInfo("Total Translation Updated: " + updatedTranslation, null);
		
		if (modifiedResources.size() > 0) {
			project.refreshLocal(IResource.DEPTH_INFINITE, null);
		}
		
	}

	/**
	 * Perform the importation of the excel file
	 * 
	 * @return an error status
	 */
	public void importTranslations(String filename) throws CoreException {
		IStatus status = Status.OK_STATUS;
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filename));
			hssfworkbook = new HSSFWorkbook(fs);
			HSSFSheet sheet = hssfworkbook.getSheetAt(0);
			readMessages(sheet, filename);
			importMessages();
		} catch (IOException ex) {
			String message = "Error opening the excel file : " + filename + " ";
			status = new Status(Status.ERROR, TranslationUICore.PLUGIN_ID, IStatus.OK, message, ex);
			throw new CoreException(status);
		}
	}


	/**
	 * Constructor
	 * 
	 * @param holder
	 *            The message repository to store the data
	 */
	public XLSImporter() {
		providers = TranslationGenerationCore.getColumnProviders();
	}
	
}
