package com.odcgroup.cdm.generation.custo.ui.wizards;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

import com.odcgroup.cdm.generation.custo.ui.CdmGenerationCustoUICore;
import com.odcgroup.cdm.generation.util.CdmConstants;

/**
 * Main page for importing custom fields.
 * 
 * @author Gary Hayes
 */
public class CustomFieldsImportPage extends WizardNewFileCreationPage {

	/** The file extensions for the domain model. */
	private static final String[] DOMAIN_EXTENSIONS = new String[] { "*." + CdmConstants.MML_FILE_EXTENSION };

	/** The file extensions for the configuration file. */
	private static final String[] CONFIGURATION_EXTENSIONS = new String[] { "*.conf" };

	/** The parent control. */
	private Composite composite;
	
	/** The domain model file editor. */
	private FileFieldEditor domainModelEditor;

	/** The Custom Field configuration file editor. */
	private FileFieldEditor configurationEditor;

	/**
	 * Creates a new ImporterWizardPage.
	 * 
	 * @param selection
	 *            The selection
	 */
	public CustomFieldsImportPage(IStructuredSelection selection) {
		super("Import CDM Custom Fields", selection);
		setTitle("Import CDM Custom Fields");
		setDescription("Import CDM Custom Fields into the Domain Model. This creates a new file. You need to delete the old domain file and rename the new one.");
	}

	/**
	 * Returns the label to display in the file name specification visual component group.
	 * 
	 * @return String The label to display in the file name specification visual component group
	 */
	protected String getNewFileLabel() {
		return "New Domain File Name";
	}

	/**
	 * Creates the additional controls needed by the page.
	 * 
	 * @param parent
	 *            The parent Widget
	 */
	protected void createAdvancedControls(Composite parent) {
		composite = new Composite(parent, SWT.NONE);
		GridData fileSelectionData = new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL);
		composite.setLayoutData(fileSelectionData);

		GridLayout fileSelectionLayout = new GridLayout();
		fileSelectionLayout.numColumns = 3;
		fileSelectionLayout.makeColumnsEqualWidth = false;
		fileSelectionLayout.marginWidth = 0;
		fileSelectionLayout.marginHeight = 0;
		composite.setLayout(fileSelectionLayout);

		domainModelEditor = new FileFieldEditor("domainModelSelect", "Select Domain Model: ", composite);
		domainModelEditor.setFileExtensions(DOMAIN_EXTENSIONS);

		configurationEditor = new FileFieldEditor("configurationSelect", "Select Configuration File: ", composite);
		configurationEditor.setFileExtensions(CONFIGURATION_EXTENSIONS);

		composite.moveAbove(null);
	}

	/**
	 * Validates the linked resource.
	 * 
	 * @return IStatus
	 */
	protected IStatus validateLinkedResource() {
		return new Status(IStatus.OK, CdmGenerationCustoUICore.PLUGIN_ID, IStatus.OK, "", null);
	}

	/**
	 * Creates the link target path if a link target has been specified.
	 */
	protected void createLinkTarget() {
		// Do nothing
	}
	
	/**
	 * Gets the domain model file name.
	 * 
	 * @return String
	 */
	public String getDomainModelFileName() {
		return domainModelEditor.getTextControl(composite).getText();
	}
	
	/**
	 * Gets the cdm configuration file name.
	 * 
	 * @return String
	 */
	public String getConfigurationFileName() {
		return configurationEditor.getTextControl(composite).getText();
	}
}