package com.odcgroup.translation.core.initializer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.internal.migration.MessageRepositoryMigration;
import com.odcgroup.translation.core.migration.TranslationMigrationException;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.DefaultModelProjectInitializer;

/**
 * The migration of centralized translation (before DS 3.0.0) into 
 * the models could be done using the standard model migration as 
 * the translation are not a model anymore. This project initializer
 * does the migration as a quick fix on a specific marker.
 * @author atr
 */
public class TranslationModelProjectInitializer extends DefaultModelProjectInitializer {

	private static final String TRANSLATION = ".translation";
	private static final String MESSAGES = "messages_";

	@Override
	public IStatus checkConfiguration(IProject project) {
		MultiStatus status = new MultiStatus(OfsCore.PLUGIN_ID, IStatus.OK, "Errors in configuration", null);

		IOfsProject ofsProject = OfsCore.getOfsProject(project);
		IFolder folder = ofsProject.getProject().getFolder("translation/translations");
		try {
			if (folder.exists()) {
				IResource[] translations = folder.members();
				for (IResource translation : translations) {
					if (translation.getName().startsWith(MESSAGES) &&
							translation.getName().endsWith(TRANSLATION)) {
						status.add(new Status(IStatus.ERROR, OfsCore.PLUGIN_ID, "Translations need to be migrated from the central location into the models"));
						break;
					}
				}
			}
		} catch (CoreException e) {
			// Ignore
		}
		
		return status;
	}
	
	@Override
	public void updateConfiguration(final IProject project, IProgressMonitor monitor) throws CoreException {
		MessageRepositoryMigration migration = new MessageRepositoryMigration();
		IOfsProject ofsProject = OfsCore.getOfsProject(project);
		IFolder folder = ofsProject.getProject().getFolder("translation/translations");
		if(folder.exists()) {
			IResource[] resources = folder.members();
			for (IResource resource: resources) {
				if (resource instanceof IFile &&
						resource.getName().startsWith(MESSAGES) &&
						resource.getName().endsWith(TRANSLATION)) {
					try {
						migration.migrate(ofsProject, (IFile)resource, monitor);
					} catch (TranslationMigrationException e) {
						throw new CoreException(new Status(IStatus.ERROR, TranslationCore.PLUGIN_ID,
				                0, "Unable to migrate the " + resource.getName() + " of " + project.getName(), e));
					}
					
					// Cleanup
					IFile translationFile = (IFile)resource;
					try {
						// Prepare backup folder
						IFolder archivedFolder = project.getFolder("archived");
						if (!archivedFolder.exists()) {
							archivedFolder.create(true, true, monitor);
						}
						IFolder archivedTranslationFolder = archivedFolder.getFolder("translation");
						if (!archivedTranslationFolder.exists()) {
							archivedTranslationFolder.create(true, true, monitor);
						}
						
						// Move the translation file
						translationFile.move(new Path("../../archived/translation/" + translationFile.getName()), true, monitor);
	
						// Clean up the translation folder
						for (IResource cleanUpRes : project.getFolder("translation/translations").members()) {
							cleanUpRes.delete(true, monitor);
						}
						project.getFolder("translation/translations").delete(true, monitor);
						project.getFolder("translation").delete(true, monitor);
					} catch (CoreException e) {
						throw new CoreException(new Status(IStatus.ERROR, TranslationCore.PLUGIN_ID,
				                0, "Unable to cleanup the translations folder of " + project.getName(), e));
					} finally {
						project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
					}
				}
			}
		}
	}
	
	public TranslationModelProjectInitializer() {
		super("translation");
	}

}
