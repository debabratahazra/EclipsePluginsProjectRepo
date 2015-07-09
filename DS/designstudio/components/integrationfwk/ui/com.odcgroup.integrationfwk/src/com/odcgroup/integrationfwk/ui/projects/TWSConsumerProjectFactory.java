package com.odcgroup.integrationfwk.ui.projects;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;

import com.odcgroup.integrationfwk.ui.TWSConsumerPluginException;
import com.odcgroup.integrationfwk.ui.natures.TWSConsumerNature;
import com.odcgroup.integrationfwk.ui.utils.StringConstants;

public class TWSConsumerProjectFactory {

	private static final String[] FOLDER_NAMES = {
			// "src" + File.separator + "com" + File.separator + "temenos"
			// + File.separator + "tws",
			StringConstants.EVENT_FOLDER_NAME,
			StringConstants.FLOW_FOLDER_NAME, StringConstants.LOG_FOLDER_NAME,
			StringConstants.SCHEMA_FOLDER_NAME };

	/**
	 * Adds a nature to a project.
	 * 
	 * @param progressMonitor
	 * @param project
	 * @param nature
	 * @throws CoreException
	 */

	private void addNatureToProject(IProgressMonitor progressMonitor,
			IProject project, String nature) throws CoreException {
		IProjectDescription newProjectDescription = project.getDescription();
		String[] oldNatureIds = newProjectDescription.getNatureIds();
		String[] newNatureIds = new String[oldNatureIds.length + 1];
		System.arraycopy(oldNatureIds, 0, newNatureIds, 0, oldNatureIds.length);
		newNatureIds[newNatureIds.length - 1] = nature;
		newProjectDescription.setNatureIds(newNatureIds);
		project.setDescription(newProjectDescription, new SubProgressMonitor(
				progressMonitor, 10));

	}

	/**
	 * Creates a single folder.
	 * 
	 * @param folder
	 * @throws CoreException
	 */
	// TODO: Candidate for refactoring to a common fancitionality
	private void createFolder(IFolder folder) throws CoreException {
		IContainer parent = folder.getParent();
		if (parent instanceof IFolder) {
			createFolder((IFolder) parent);
		}
		if (!folder.exists()) {
			folder.create(false, true, null);
		}
	}

	/**
	 * Creates all project folders.
	 * 
	 * @param project
	 * @throws CoreException
	 */
	// TODO: Candidate for refactoring to a common fancitionality
	private void createFolders(IProject project) throws CoreException {
		final String[] folderNames = FOLDER_NAMES;
		for (String folderName : folderNames) {
			IPath folderPath = new Path(folderName);
			IFolder folder = project.getFolder(folderPath);
			createFolder(folder);
		}
	}

	/**
	 * 
	 * Creates a new TWS project.
	 * 
	 * @param projectName
	 *            the name of the project
	 * @param location
	 *            the project location
	 * @return the project created
	 * @throws CoreException
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws TWSPluginException
	 */
	public TWSConsumerProject createProject(String projectName)
			throws CoreException, IOException, URISyntaxException,
			TWSConsumerPluginException {
		return createProject(projectName, null);
	}

	/**
	 * 
	 * Creates a new TWS project at custom location if provided.
	 * 
	 * @param projectName
	 *            the name of the project
	 * @param customLocationURI
	 *            the project location
	 * @return the project created
	 * @throws CoreException
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws TWSPluginException
	 */
	public TWSConsumerProject createProject(String projectName,
			URI customLocationURI) throws CoreException, IOException,
			URISyntaxException, TWSConsumerPluginException {

		IProgressMonitor progressMonitor = new NullProgressMonitor();

		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName);
		if (project.exists()) {
			project.delete(true, true, new SubProgressMonitor(progressMonitor,
					10));
		}
		IProjectDescription projectDescription = ResourcesPlugin.getWorkspace()
				.newProjectDescription(projectName);
		if (customLocationURI != null) {
			projectDescription.setLocationURI(customLocationURI);
		}

		project.create(projectDescription, new SubProgressMonitor(
				progressMonitor, 10));
		project.open(new SubProgressMonitor(progressMonitor, 10));
		addNatureToProject(progressMonitor, project,
				TWSConsumerNature.NATURE_ID);

		createFolders(project);

		// something is not quite right with the refresh as the files dont
		// appear in the project view unless refreshed...
		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		TWSConsumerProject twsProject = null;
		twsProject = new TWSConsumerProject(project);

		return twsProject;
	}

	/**
	 * Factory method which will helps to delete the given project.
	 * 
	 * @param projectToBeDeleted
	 *            - instance of {@link TWSConsumerProject}
	 * @return true if successfully deleted, false otherwise.
	 * @throws CoreException
	 */
	public boolean deleteProject(TWSConsumerProject projectToBeDeleted)
			throws CoreException {
		if (projectToBeDeleted == null) {
			return false;
		}
		IProject project = projectToBeDeleted.getProject();
		if (project.exists()) {
			project.delete(true, true, new NullProgressMonitor());
		}
		return true;
	}

}
