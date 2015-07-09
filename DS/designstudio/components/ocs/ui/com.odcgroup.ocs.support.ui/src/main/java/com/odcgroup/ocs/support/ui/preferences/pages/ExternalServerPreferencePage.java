package com.odcgroup.ocs.support.ui.preferences.pages;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.ocs.server.preferences.DSServerPreferenceManager;
import com.odcgroup.ocs.support.OCSPluginActivator;
import com.odcgroup.ocs.support.preferences.OCSRuntimePreference;
import com.odcgroup.ocs.support.ui.OCSSupportUICore;
import com.odcgroup.ocs.support.ui.internal.compiler.OCSSchemaResolver;
import com.odcgroup.workbench.core.OfsCore;

public class ExternalServerPreferencePage extends FieldEditorPreferencePage
        implements IWorkbenchPreferencePage {

	protected static final Logger logger = LoggerFactory.getLogger(ExternalServerPreferencePage.class);

	private DirectoryFieldEditor installDirEditor;
	private FileFieldEditor ocsSourcesEditor;
	private BooleanFieldEditor logAllDeployFiles;

    public ExternalServerPreferencePage() {
        super(GRID);
        setPreferenceStore(new ScopedPreferenceStore(new InstanceScope(), OCSPluginActivator.PLUGIN_ID));
        setDescription(OCSSupportUICore.getDefault().getString("ExternalServerPreferencePage.runtime")); //$NON-NLS-1$
    }

    protected void createFieldEditors() {

        String[] versionSegments = OfsCore.getVersionNumber().split("\\.");
        String version = versionSegments[0] + "." + versionSegments[1] + "." + versionSegments[2];

    	createInstallDirEditor(version);
		createOcsSourcesEditor();
		createDeployConsole();
    }

	/**
	 *
	 */
	private void createDeployConsole() {
		Label deployConsole = new Label(getFieldEditorParent(), SWT.NONE);
		deployConsole.setText("Deploy Console:");
		deployConsole.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false, 2, 1));

		logAllDeployFiles = new BooleanFieldEditor(DSServerPreferenceManager.DISPLAY_ALL_DEPLOYED_FILES, "Log all deployed files", getFieldEditorParent());
		getPreferenceStore().setDefault(DSServerPreferenceManager.DISPLAY_ALL_DEPLOYED_FILES, true);
		addField(logAllDeployFiles);
	}

	/**
	 *
	 */
	private void createOcsSourcesEditor() {
		ocsSourcesEditor = new OcsFileFieldEditor(
		        OCSRuntimePreference.OCS_SOURCES_JAR,
		        OCSSupportUICore.getDefault().getString("ExternalServerPreferencePage.sources.label"), //$NON-NLS-1$
		        getFieldEditorParent()) {
        	@Override
        	protected boolean checkPageState() {
				return isPageValid();
        	}
		};
		ocsSourcesEditor.setFileExtensions(new String[] { "*.jar" }); //$NON-NLS-1$
		ocsSourcesEditor.setEmptyStringAllowed(true);
		ocsSourcesEditor.setValidateStrategy(StringFieldEditor.VALIDATE_ON_KEY_STROKE);
		addField(ocsSourcesEditor);
	}

	/**
	 * @param version
	 */
	private void createInstallDirEditor(String version) {
		installDirEditor = new OcsDirectoryFieldEditor(OCSRuntimePreference.INSTALL_DIR,
		OCSSupportUICore.getDefault().getString("ExternalServerPreferencePage.installDir.label"), getFieldEditorParent()) {
        	@Override
        	protected boolean checkPageState() {
				return isPageValid();
        	}
			@Override
			protected void valueChanged() {
				refreshValidState();
				super.valueChanged();
			}
		};
		installDirEditor.setEmptyStringAllowed(true);
		installDirEditor.setValidateStrategy(StringFieldEditor.VALIDATE_ON_KEY_STROKE);
		installDirEditor.setErrorMessage("This must be a valid Triple'A Plus installation directory, e.g. C:\\OCS" + version);
		addField(installDirEditor);
	}

    public void init(IWorkbench workbench) {
    }

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
	}

	/**
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {
		String oldInstallDir = getPreferenceStore().getString(OCSRuntimePreference.INSTALL_DIR);
		String oldOcsSourceJar = getPreferenceStore().getString(OCSRuntimePreference.OCS_SOURCES_JAR);
		
		if (!super.performOk()) {
			return false;
		}

		String newInstallDir = getPreferenceStore().getString(OCSRuntimePreference.INSTALL_DIR);
		String newOcsSourceJar = getPreferenceStore().getString(OCSRuntimePreference.OCS_SOURCES_JAR);

		if (anyPropertiesChanged(oldInstallDir, oldOcsSourceJar, newInstallDir, newOcsSourceJar)) {
			resetSchemaResolver();
		}
		
		if (StringUtils.isNotBlank(newInstallDir) && !newInstallDir.equalsIgnoreCase(oldInstallDir)) {
			
			WorkspaceJob fullBuildJob = new WorkspaceJob("Full build (triggered because of External Server configuration change)") {
	            public boolean belongsTo(Object family) {
	                return ResourcesPlugin.FAMILY_MANUAL_BUILD.equals(family);
	            }
	            public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
	            	ResourcesPlugin.getWorkspace().build(IncrementalProjectBuilder.FULL_BUILD, monitor);
	            	return Status.OK_STATUS;
	            }
			};
			fullBuildJob.setUser(true);
			fullBuildJob.schedule();
		}

		return true;
	}

	/**
	 * for testing purposes
	 */
	public void resetSchemaResolver() {
		OCSSchemaResolver.extractOcsSchemas();
	}

	/**
	 * @param oldInstallDir
	 * @param oldEarDir
	 * @param oldOcsSourceJar
	 * @param newInstallDir
	 * @param newEarDir
	 * @param newOcsSourceJar
	 * @return
	 */
	public boolean anyPropertiesChanged(String oldInstallDir, String oldOcsSourceJar, String newInstallDir,  String newOcsSourceJar) {
		return !oldInstallDir.equals(newInstallDir) || !oldOcsSourceJar.equals(newOcsSourceJar);
	}
	
	protected boolean isPageValid() {
		boolean valid = isValidInstallDir(installDirEditor.getStringValue()) &&
				        isValidSourceJar(ocsSourcesEditor.getStringValue());
		setValid(valid);
		return valid;
	}
	
	/**
	 * @param stringValue
	 * @return
	 */
	private boolean isValidInstallDir(String dir) {
		if (dir.trim().isEmpty()) {
			setErrorMessage(null);
			return true;
		}
		File directory = new File(dir);
		if (!directory.exists() || !directory.isDirectory()) {
			setErrorMessage("Install directory: Invalid value");
			return false;
		} else {
			setErrorMessage(null);
			return true;
		}
	}

	/**
	 * @param stringValue
	 * @return
	 */
	private boolean isValidSourceJar(String filename) {
		if (filename.trim().isEmpty()) {
			setErrorMessage(null);
			return true;
		}
		File file = new File(filename);
		if (!file.exists() || !file.isFile()) {
			setErrorMessage("Triple'A Plus Sources jar: invalid value");
			return false;
		} else {
			setErrorMessage(null);
			return true;
		}
	}
}
