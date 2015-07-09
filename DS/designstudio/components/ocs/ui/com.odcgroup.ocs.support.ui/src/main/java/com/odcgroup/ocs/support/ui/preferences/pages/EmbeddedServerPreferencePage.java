package com.odcgroup.ocs.support.ui.preferences.pages;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.ocs.support.OCSPluginActivator;
import com.odcgroup.ocs.support.installer.OcsBinariesExtractionFacade;
import com.odcgroup.ocs.support.preferences.OCSRuntimePreference;
import com.odcgroup.ocs.support.ui.OCSSupportUICore;
import com.odcgroup.ocs.support.ui.external.tool.installer.OcsInstallerHelper;
import com.odcgroup.ocs.support.ui.installer.OcsBinariesExtractionUIFacade;
import com.odcgroup.workbench.core.OfsCore;

public class EmbeddedServerPreferencePage extends FieldEditorPreferencePage
        implements IWorkbenchPreferencePage {

	protected static final Logger logger = LoggerFactory.getLogger(EmbeddedServerPreferencePage.class);

	public static final String OCS_INSTALLER = "Update_Installer.jar";

	private FileFieldEditor ocsDistributionEditor;
	private DirectoryFieldEditor ocsHotfixesEditor;
	private DirectoryFieldEditor ocsCustoEditor;
	private Button reloadButton;
	private OcsBooleanFieldEditor autoUpdateMaven;
	private String lastExtractedOcsBinaries;

    public EmbeddedServerPreferencePage() {
        super(GRID);
        setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, OCSPluginActivator.PLUGIN_ID));
        setDescription(OCSSupportUICore.getDefault().getString("EmbeddedServerPreferencePage.distribution")); //$NON-NLS-1$
    }

    protected void createFieldEditors() {
    	createOcsDistributionEditor();
        createOcsHotfixesEditor();
        createCustoEditor();
        createReloadButton();
        createAutoUpdateMaven();
    }

	/**
	 *
	 */
	protected void createAutoUpdateMaven() {
		autoUpdateMaven = new OcsBooleanFieldEditor(
        		com.odcgroup.ocs.support.preferences.OCSRuntimePreference.AUTO_UPDATE_MAVEN,
        		"Automatically configure Maven to use Triple'A Plus binaries in offline",
        		getFieldEditorParent());
        addField(autoUpdateMaven);

        Label autoUpdateMavenDescrption = new Label(getFieldEditorParent(), SWT.NONE);
        autoUpdateMavenDescrption.setText("     (will automatically update the Maven > User Settings > User Settings field)");
    	GridData autoUpdateMavenDescrptionGridData = new GridData(GridData.BEGINNING, SWT.BEGINNING, false, false, 3, 1);
    	autoUpdateMavenDescrption.setLayoutData(autoUpdateMavenDescrptionGridData);
	}

	/**
	 *
	 */
	private void createReloadButton() {
		new Label(getFieldEditorParent(), SWT.NONE);
        new Label(getFieldEditorParent(), SWT.NONE);
        reloadButton = new Button(getFieldEditorParent(), SWT.PUSH);
        reloadButton.setText("Reload...");

        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        reloadButton.setLayoutData(gridData);
        reloadButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				extractOcsBinaries();
				OcsBinariesExtractionUIFacade.instance().updateM2EclipseConfiguration();
				OfsCore.getDependencyManager().resetMavenSettings();

				MessageDialog.openInformation(getShell(), "M2eclipse Properties Update",
						"The m2eclipse User Settings will be updated to use this new " +
						"extraction when the Preferences are saved.");
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}

	/**
	 *
	 */
	private void createCustoEditor() {
		ocsCustoEditor = new OcsDirectoryFieldEditor(
                OCSRuntimePreference.CUSTO_DIR,
                OCSSupportUICore.getDefault().getString("EmbeddedServerPreferencePage.custo.label"), //$NON-NLS-1$
                getFieldEditorParent()) {

        	@Override
        	protected boolean checkPageState() {
				return checkPageValid();
        	}

        };
        addField(ocsCustoEditor);
	}

	/**
	 *
	 */
	private void createOcsHotfixesEditor() {
		ocsHotfixesEditor = new OcsDirectoryFieldEditor(
                OCSRuntimePreference.HOTFIXES_DIR,
                OCSSupportUICore.getDefault().getString("EmbeddedServerPreferencePage.hotfixes.label"), //$NON-NLS-1$
                getFieldEditorParent()) {

        	@Override
        	protected boolean checkPageState() {
				return checkPageValid();
        	}

        };
        addField(ocsHotfixesEditor);
	}

	/**
	 *
	 */
	private void createOcsDistributionEditor() {
		ocsDistributionEditor = new OcsFileFieldEditor(
                OCSRuntimePreference.DISTRIBUTION_PACKAGE,
                OCSSupportUICore.getDefault().getString("EmbeddedServerPreferencePage.distribution.label"), //$NON-NLS-1$
                getFieldEditorParent()) {

        	@Override
        	protected boolean checkPageState() {
				return checkPageValid();
        	}

        };
        ocsDistributionEditor.setFileExtensions(new String[] { "*.zip" }); //$NON-NLS-1$
        ocsDistributionEditor.setEmptyStringAllowed(true);
        ocsDistributionEditor.setValidateStrategy(StringFieldEditor.VALIDATE_ON_KEY_STROKE);
        addField(ocsDistributionEditor);
	}

    public void init(IWorkbench workbench) {
    }

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
		if (event.getProperty().equals(FieldEditor.VALUE)) {
			if (event.getSource() == ocsDistributionEditor) {
				setValid(isValidOcsDistributionFilepath(ocsDistributionEditor.getStringValue()));
			}
		}
	}

	/**
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {
		if (ocsBinariesExtractionRequired()) {
			if (extractOcsBinaries()) {
				boolean updateM2EclipseConfiguration = updateM2EclipseConfiguration();
				OfsCore.getDependencyManager().resetMavenSettings();

				if (!callSuperPerformOk()) {
					return false;
				}

				if(updateM2EclipseConfiguration) {
					MessageDialog.openInformation(getShell(), "M2eclipse Properties Updated",
							"The m2eclipse User Settings have been updated to use the new " +
					"extraction.");
				}
				
				
				return true;
			} else {
				return false;
			}
		} else {
			return callSuperPerformOk();
		}
	}

	/**
	 * @return
	 */
	protected boolean callSuperPerformOk() {
		return super.performOk();
	}

	/**
	 * @return
	 *
	 */
	protected boolean updateM2EclipseConfiguration() {
		if(autoUpdateMaven!=null) {
			autoUpdateMaven.forceValue(true);
		}

		return OcsBinariesExtractionUIFacade.instance().updateM2EclipseConfiguration();
	}

	/**
	 * @return
	 */
	protected boolean ocsBinariesExtractionRequired() {

		String oldOcsDistributionEditor = getPreferenceStore().getString(OCSRuntimePreference.DISTRIBUTION_PACKAGE);
		String newOcsDistributionEditor = "";

		if(ocsDistributionEditor != null) {
			newOcsDistributionEditor = ocsDistributionEditor.getStringValue();
		}

		return !oldOcsDistributionEditor.equals(newOcsDistributionEditor) &&
		!newOcsDistributionEditor.equals(lastExtractedOcsBinaries) && !newOcsDistributionEditor.trim().isEmpty();
	}

	/**
	 * @param newOcsDistributionEditor
	 */
	protected boolean extractOcsBinaries() {
		File extractionDestination = OcsBinariesExtractionFacade.instance().generateNewBinariesDestination();
		if (!extractionDestination.exists() && !extractionDestination.mkdirs()) {
			logger.error("Unable to create this folder: " + extractionDestination);
			return false;
		}

		boolean success = extractOcsBinaries(extractionDestination);

		// In case of failure, remove the (maybe partial) extraction
		if (!success) {
			try {
				FileUtils.deleteDirectory(extractionDestination);
			} catch (IOException e) {
				logger.error("Unable to remove failed extraction");
			}
		}

		return success;
	}

	/**
	 * Extracted in a separate method for testing purpose only
	 * @param extractionDestination
	 * @return {@code true} if the extraction was achieved without error,
	 * {@code false} otherwise
	 */
	protected boolean extractOcsBinaries(File extractionDestination) {
		boolean success = OcsInstallerHelper.extractOcsBinaries(
				getShell(),
				extractionDestination.getAbsolutePath(),
				ocsDistributionEditor.getStringValue(),
				ocsHotfixesEditor.getStringValue(),
				ocsCustoEditor.getStringValue());
		if (success) {
			lastExtractedOcsBinaries = ocsDistributionEditor.getStringValue();
		}
		return success;
	}

	private boolean checkPageValid() {
		boolean distributionReady =
			isValidOcsDistributionFilepath(ocsDistributionEditor.getStringValue()) &&
			isValidOcsHotfixes(ocsHotfixesEditor.getStringValue()) &&
			isValidOcsCusto(ocsCustoEditor.getStringValue());

		reloadButton.setEnabled(distributionReady);

		return distributionReady;
	}

	protected boolean isValidOcsDistributionFilepath(String filepath) {
		if (filepath.trim().isEmpty()) {
			setMessage("Distribution: No Triple'A Plus file has been defined", WARNING);
			setErrorMessage(null);
			return true;
		}

		File distributionZip = new File(filepath);
		if (!distributionZip.exists()) {
			setErrorMessage("Distribution: No Triple'A Plus file found on current path.");
			return false;
		}

		if (FilenameUtils.getName(filepath).contains("source")) {
			setErrorMessage("Distribution: Triple'A Plus Source file selected instead of Triple'A Plus file.");
			return false;
		}

		File distributionInstaller = new File(distributionZip.getParent(), OCS_INSTALLER);
		if (!distributionInstaller.exists()) {
   			String filename = FilenameUtils.getName(distributionInstaller.getAbsolutePath());
			setErrorMessage("Distribution: Missing required " + filename + " in " + distributionZip.getParent());
			return false;
    	}

		setMessage(null);
		setErrorMessage(null);
		return true;
	}

	protected boolean isValidOcsHotfixes(String dir) {
		if (dir.trim().isEmpty()) {
			setErrorMessage(null);
			return true;
		} else if (ocsDistributionEditor.getStringValue().trim().isEmpty()) {
			setErrorMessage("Hotfixes directory: no distribution defined");
			return false;
		}

		File hotfixesDir = new File(dir);
		if (dir.trim().length() <= 3) {
			setErrorMessage("Hotfixes Directory: " + hotfixesDir + " is invalid");
			return false;
		} else if (!hotfixesDir.exists()) {
			setErrorMessage("Hotfixes Directory: " + hotfixesDir + " doesn't exist");
			return false;
    	} else {
			setErrorMessage(null);
			return true;
    	}
	}

	protected boolean isValidOcsCusto(String dir) {
		if (dir.trim().isEmpty()) {
			setErrorMessage(null);
			return true;
		} else if (ocsDistributionEditor.getStringValue().trim().isEmpty()) {
			setErrorMessage("Custo directory: no distribution defined");
			return false;
		}

		File custoDir = new File(dir);
		if (dir.trim().length() <= 3) {
			setErrorMessage("Custo Directory: " + custoDir + " is invalid");
			return false;
		} else if (!custoDir.exists()) {
			setErrorMessage("Custo Directory: " + custoDir + " doesn't exist");
			return false;
    	} else {
			setErrorMessage(null);
			return true;
    	}
	}


}
