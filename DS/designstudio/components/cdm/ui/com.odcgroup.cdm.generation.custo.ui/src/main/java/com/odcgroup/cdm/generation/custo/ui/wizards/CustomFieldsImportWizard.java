package com.odcgroup.cdm.generation.custo.ui.wizards;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.odcgroup.cdm.generation.custo.generator.CustomFieldsReader;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelURIConverter;

/**
 * Imports the custom fields into the Domain Model.
 * 
 * @author Gary Hayes
 */
public class CustomFieldsImportWizard extends Wizard implements IImportWizard {

	/** The wizard page. */
	private CustomFieldsImportPage mainPage;

	/**
	 * Executed when the Wizard is finished.
	 * 
	 * @return boolean True if it succeeded
	 */
	public boolean performFinish() {
		
		final String oldDomainFilename = mainPage.getDomainModelFileName();
		final String ConfigurationFilename = mainPage.getConfigurationFileName();
		final IPath containerPath = mainPage.getContainerFullPath();
		final IPath newFilePath = containerPath.append(mainPage.getFileName());
		final String currentProjectName = containerPath.segment(0);

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(currentProjectName);
		final IOfsProject ofsProject = OfsCore.getOfsProject(project);
		
		final Exception[] exception = {null};
		
		WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
			public void execute(IProgressMonitor monitor) {
				try {
					int pos = oldDomainFilename.indexOf(currentProjectName);
					if (pos < 0) {
						MessageDialog.openError(getContainer().getShell(), "Import Custom Fields Error",
								"The source domain must be in the selected project ["+currentProjectName+"]");
						exception[0] = new Exception();
						return;
					}
					IPath oldDomainPath = new Path(oldDomainFilename.substring(pos)).removeFirstSegments(2);
					URI sourceURI = ModelURIConverter.createModelURI(oldDomainPath.toPortableString());
					URI newURI = URI.createPlatformResourceURI(newFilePath.toPortableString(), true);
	
					IOfsModelResource ofsDomain = null;
					MdfDomainImpl domain = null;
					MdfDomainImpl newDomain = null;
					try {
						// load source domain
						ofsDomain = ofsProject.getOfsModelResource(sourceURI);
						domain = (MdfDomainImpl)ofsDomain.getEMFModel().get(0);

						// copy the domain
						newDomain = (MdfDomainImpl)EcoreUtil.copy(domain);

						// import custom fields into the new domain
						CustomFieldsReader reader = new CustomFieldsReader(ConfigurationFilename);
						List<String> messages = reader.importFields(newDomain);
						if (messages.size() > 0) {
							displayMessages(messages);
						}

					} catch (ModelNotFoundException ex) {
						MessageDialog.openError(getContainer().getShell(), "Import Custom Fields Error",
								"Unable to load the domain ["+sourceURI+"]");
						exception[0] = ex;
					} catch (IOException ex) {
						MessageDialog.openError(getContainer().getShell(), "Import Custom Fields Error",
								"Unable to copy the domain to ["+newURI+"]");
						exception[0] = ex;
					} catch (InvalidMetamodelVersionException ex) {
						MessageDialog.openError(getContainer().getShell(), "Import Custom Fields Error",
								"Unable to copy the domain to ["+newURI+"]");
						exception[0] = ex;
					}

					// save the new domain
					Resource res = ofsProject.getModelResourceSet().createResource(newURI);
					res.getContents().add(newDomain);
					try {
						res.save(Collections.EMPTY_MAP);
					} catch (IOException ex) {
						MessageDialog.openError(getContainer().getShell(), "Import Custom Fields Error",
								"Unable to save the new domain in ["+newURI+"]");
						exception[0] = ex;
					}

				} finally {
					monitor.done();
				}
			}
		};

		try {
			// This runs the options, and shows progress.
			getContainer().run(false, false, operation);
		} catch (Exception ex) {
			MessageDialog.openError(getContainer().getShell(), "Import Custom Fields Error",
					"Error: " + ex.getMessage());
			return false;
		}
		
		if (exception[0] != null) {
			return false;
		}
		
		return true;
	}

	/**
	 * @param filePath
	 * @return
	 */
	protected IFile createFileHandle(IPath filePath) {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(filePath);
	}
	
	/**
	 * Initialize the ImporterWizard.
	 * 
	 * @param workbench
	 *            The Workbench
	 * @param selection
	 *            The selection
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		mainPage = new CustomFieldsImportPage(selection);
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adds the ImporterWizardPage to the set of Wizard pages.
	 */
	public void addPages() {
		super.addPages();
		addPage(mainPage);
	}

	/**
	 * Displays the messages that the import generated.
	 * 
	 * @param messages
	 *            The messages
	 */
	private void displayMessages(final List<String> messages) {
		Display display = PlatformUI.getWorkbench().getDisplay();
		display.syncExec(new Runnable() {
			public void run() {
				String m = "";
				for (String s : messages) {
					m += s;
					m += "\n";
				}

				Dialog d = new MessageDialog(null, "Import Custom Fields", null, m, MessageDialog.INFORMATION,
						new String[] { "OK" }, 0);
				d.open();
			}
		});
	}
}
