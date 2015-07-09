package com.odcgroup.t24.version.importer.ui.wizard;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * This class define a wizard page for the importation
 * 
 * @author atr
 * 
 */
class VersionTranslationImportWizardPage extends WizardPage {

	/** The import OCS Translation text constant. */
	private static final String IMPORT_EXCEL_TRANSLATION_TEXT = "Import an existing excel translations file";

	/** The import OCS Translation title constant. */
	private static final String IMPORT_EXCEL_TRANSLATION_TITLE = "Import Versions Translations from Excel";

	/** The parent Composite. */
	private Composite composite;

	/** The file path to the selected file text field */
	private FileFieldEditor filePathField;

	/**
	 * Validate the data.
	 */
	private void validateData() {

		if (StringUtils.isEmpty(getFilePathField().getText())) {
			setPageComplete(false);
			return;
		}

		File f = new File(getFilePathField().getText());
		if (!f.exists()) {
			setPageComplete(false);
			return;
		}

		setPageComplete(true);
	}

	/**
	 * Gets the textfield of the file path of the file to import
	 * 
	 * @return String The location of the file to import
	 */
	private Text getFilePathField() {
		return filePathField.getTextControl(composite);
	}
	

	@Override
	public void createControl(Composite parent) {
		composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));

		filePathField = new FileFieldEditor("File", "File to Import", composite);
		filePathField.setFileExtensions(new String[] { "*.xls" });
		filePathField.getTextControl(composite).addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				validateData();
			}

		});
		
		setControl(composite);

		setPageComplete(false);
	}


	public String getFilename() {
		return getFilePathField().getText();
	}
	
	/**
	 * Constructor
	 * 
	 * @param pagename
	 *            The page name
	 */
	public VersionTranslationImportWizardPage(String pagename) {
		super(pagename);
		setTitle(IMPORT_EXCEL_TRANSLATION_TITLE);
		setDescription(IMPORT_EXCEL_TRANSLATION_TEXT);
	}

}
