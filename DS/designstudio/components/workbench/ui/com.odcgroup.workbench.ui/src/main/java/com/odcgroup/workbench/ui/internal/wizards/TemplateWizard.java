package com.odcgroup.workbench.ui.internal.wizards;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.odcgroup.template.TemplateDescriptor;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.template.DSTemplateCreator;

public class TemplateWizard extends Wizard implements INewWizard {

	TemplateSelectionPage templateSelectionPage;
	
	public TemplateWizard() {
		setWindowTitle("New Design Studio Template Projects");
		
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setNeedsProgressMonitor(true);
	}
	
	public void addPages() {
		templateSelectionPage = new TemplateSelectionPage();
		addPage(templateSelectionPage);
		
	}

	@Override
	public boolean performFinish() {
		final List<String> projectsName = templateSelectionPage.getSelectedTemplateDescriptor().getProjectsName(
				templateSelectionPage.getParameters());

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		final IPath rootPath = root.getLocation();
		
		// Check if projects don't already exist
		StringBuffer existingProjectsMessage = new StringBuffer();
		final List<IProject> existingProjects = new LinkedList<IProject>();
		final List<File> existingFolders = new LinkedList<File>();
		for (final String projectName: projectsName) {
			if (root.getProject(projectName).exists()) {
				existingProjects.add(root.getProject(projectName));
				existingProjectsMessage.append(projectName);
				existingProjectsMessage.append(", ");
			} else if (new File(rootPath.toFile(), projectName).exists()) {
				// This detect folders with a name that only differs by the case of projects
				// that will be extracted.
				File[] files = rootPath.toFile().listFiles(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						return projectName.equalsIgnoreCase(name);
					}
				});
				for (File file : files) {
					existingFolders.add(file);
					existingProjectsMessage.append(projectName);
					existingProjectsMessage.append(", ");
				}
			}
		}
		
		if (existingProjectsMessage.length() != 0) {
			if (!MessageDialog.openConfirm(getShell(), "Confirm replacement", 
					"The following project(s)/folder(s) already exists in the workspace: " + 
					StringUtils.removeEnd(existingProjectsMessage.toString(), ", ") + "\n" +
					"Are you sure you want to replaced them?")) {
				return false;
			}
		}
		
		final TemplateDescriptor selectedTemplate = templateSelectionPage.getSelectedTemplateDescriptor(); 
		final Map<String, String> parameters = templateSelectionPage.getParameters();

		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				monitor.beginTask("Extracting template contents...", existingProjects.size() + existingFolders.size() + 1 + projectsName.size());
					
				for (IProject project: existingProjects) {
					if (project.exists()) {
						try {
							monitor.setTaskName("Removing " + project.getName());
							project.delete(true, null);
							monitor.worked(1);
						} catch (CoreException e) {
							throw new InvocationTargetException(new Exception("Unable to remove the project named " + project.getName() + 
									"\n(Caused by: " + e.getMessage() + ")"));
						}
					}
				}

				for (File folder : existingFolders) {
					IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(folder.getName());
			        try {
						monitor.setTaskName("Removing " + project.getName());
						project.create(null);
						project.open(null);
						project.delete(true, null);
						monitor.worked(1);
					} catch (CoreException e) {
						// Ignore
					}
				}

				
				// Extract the template
				try {
					monitor.setTaskName("Extract the template");
					DSTemplateCreator.createTemplate(
							selectedTemplate, 
							parameters, 
							rootPath.toFile());
					monitor.worked(1);

					if (monitor.isCanceled()) { 
						throw new InterruptedException();
					}
				} catch (InterruptedException e) {
					throw e;
				} catch (Exception e) {
					throw new InvocationTargetException(new Exception("The " + 
							templateSelectionPage.getSelectedTemplateDescriptor().getName() + 
							" template cannot be extracted to " + 
							rootPath.toFile().getAbsolutePath() + 
							"(Caused by: " + e.getMessage() + ")"));
				}
				
				// Create the projects in the workspace
				for (String projectName: projectsName) {
					try {
						monitor.setTaskName("Creating " + projectName + "...");
						IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
				        if (!project.exists()) { 
				        	project.create(null);
				        }
				        if (!project.isOpen()) {
				        	project.open(null);
				        }
				        monitor.worked(1);
					} catch (CoreException e) {
						throw new InvocationTargetException(new Exception("The " + 
								projectName + 
								" project cannot be created. (Caused by: " +
								e.getMessage() + ")"));
					}
					if (monitor.isCanceled()) { 
						throw new InterruptedException();
					}
				}
				monitor.done();
			}
		};

		try {
			getContainer().run(true, true, op);
		} catch (InvocationTargetException e) {
			MessageDialog.openError(getShell(), "Unable to extract template", 
					e.getTargetException().getMessage());
			return true;
		} catch (InterruptedException e) {
			return false;
		}

		return true;
	}

}
