package com.odcgroup.t24.server.external.ui;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.wizards.IWizardDescriptor;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.t24.server.external.model.internal.ExternalT24Server;
import com.odcgroup.t24.server.external.nature.T24ExternalServerNature;
import com.odcgroup.t24.server.external.util.ServerPropertiesHelper;
import com.odcgroup.workbench.core.OfsCore;

/**
 * Action that add new embedded server
 */
public class AddT24ExternalServerAction extends Action {

	private static String EQUALS_CR = "=";	
	private static Logger logger = LoggerFactory.getLogger(AddT24ExternalServerAction.class);
	private IProject serverProject;
	
	public AddT24ExternalServerAction() {
		setText("Add New T24 Server...");
	}

	@Override
	public void run() {
		IWizardDescriptor descriptor = PlatformUI.getWorkbench()
		   	.getNewWizardRegistry().findWizard("com.odcgroup.t24.server.properties.wizard");
		IWorkbenchWizard createWizard = null;
		
		if (descriptor != null) {
			try {
				 createWizard = descriptor.createWizard();
			} catch (CoreException e) {
				e.printStackTrace();
			}
			WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), createWizard);
			if (dialog.open() == WizardDialog.OK) {
				createProject((BasicNewProjectResourceWizard) createWizard);
			}
		} else {
			logger.error("Unable to find the new project wizard");
		}
	}

	/**
	 * @param wizard
	 */
	public void createProject(BasicNewProjectResourceWizard wizard) {
		IProject project = wizard.getNewProject();
		if(project == null) {
			project = getServerProject();	// JUnit test case.
		}
		try {
			IFolder folder = project.getFolder(ExternalT24Server.CONFIG);
			if(!folder.exists())
			folder.create(true, true, new NullProgressMonitor());
			
			IFile file = folder.getFile(ExternalT24Server.SERVER_PROPERTIES);
			//attach file content with delimiter
			String expected = writeToFile();
			//file text
			InputStream source = new ByteArrayInputStream(expected.getBytes());
			if(!file.exists())
			file.create(source, true, null);
			try {
				source.close();
			} catch (IOException e) {
			}
			OfsCore.createFolder(folder);
			OfsCore.addNature(project, T24ExternalServerNature.NATURE_ID);
			project.refreshLocal(IResource.DEPTH_ZERO, null);
			
			if (file != null && file.exists()) {
				IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				if (window != null) {
					IWorkbenchPage activePage = window.getActivePage();
					if (activePage != null)
						activePage.openEditor(new FileEditorInput(file),
								"com.odcgroup.t24.server.properties.ui.ServerFormEditor");
				}
			}
		} catch (CoreException e) {
			logger.error("Unable to properly initialized the server project due to: " + e.getMessage(), e);
		} 
	}
	
	public IProject getServerProject() {
		return serverProject;
	}
	
	public void setServerProject(IProject serverProject) {
		this.serverProject = serverProject;
	}
	
	/**
	 * Method to write default values to server.properties file with comments
	 * 
	 * @return String
	 */
	private String writeToFile() {
		StringWriter stringWriter = new StringWriter();
		for (String key : ServerPropertiesHelper.getAllKeys()) {
			stringWriter.append(Messages.getString(key));
			stringWriter.append(IOUtils.LINE_SEPARATOR);
			stringWriter.append(key);
			stringWriter.append(EQUALS_CR);
			if (ServerPropertiesHelper.OSTYPE.equalsIgnoreCase(key)) {
				stringWriter.append(ServerPropertiesHelper.DEFAULT_OSTYPE);
			} else if (ServerPropertiesHelper.PROTOCOL_KEY.equalsIgnoreCase(key)) {
				stringWriter.append(ServerPropertiesHelper.DEFAULT_PROTOCOL);
			}else if (ServerPropertiesHelper.T24_CATALOGSERVICE_VERSION.equalsIgnoreCase(key)) {
				stringWriter.append(ServerPropertiesHelper.DEFAULT_T24_CATALOGSERVICE_VERSION);
			}
			try {
				if(key.equals("deployed.projects")){
					stringWriter.append(addDeplyedProjectsName());
				}
			} catch (CoreException e) {
				logger.error("Unable to get Project Nature from workspace", e.getMessage());
			}
			stringWriter.append(IOUtils.LINE_SEPARATOR);
			stringWriter.append(IOUtils.LINE_SEPARATOR);
		}
		return stringWriter.toString();
	}

	/**
	 * -models-gen projects will be added automatically during creating Server project 
	 * @throws CoreException 
	 */
	public static String addDeplyedProjectsName() throws CoreException {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		String projectsName = "";
		String JAVA_NATURE = "org.eclipse.jdt.core.javanature";
		String OFS_NATURE = "com.odcgroup.workbench.generation.OfsTechnicalNature";
		for (IProject iProject : projects) {
			if(iProject.hasNature(JAVA_NATURE) && !iProject.hasNature(OFS_NATURE)) {
				projectsName = projectsName + iProject.getName() + ",";
			}
		}
		if(projectsName.length() > 1) {
			return projectsName.substring(0, projectsName.length()-1);
		}
		return "";
	}
}
