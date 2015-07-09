package com.odcgroup.t24.applicationimport.serializer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.t24.applicationimport.ApplicationIntrospectionActivator;
import com.odcgroup.t24.applicationimport.ApplicationsImportProblemsException;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.workbench.core.OfsCore;

/**
 * Utility to efficiently save a list of domains.
 */
public class MdfDomainSerializer {
	private static final Logger logger = LoggerFactory.getLogger(MdfDomainSerializer.class);

	public static void serialize(final Collection<MdfDomain> domainList, final IProject project, final ResourceSet resourceSet, IProgressMonitor monitor,final IFolder selectedFolder) throws CoreException {

		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				ApplicationsImportProblemsException problems = new ApplicationsImportProblemsException();
				monitor.beginTask("Writing",domainList.size()*2);
				for (final MdfDomain domain : domainList) {
					if (monitor.isCanceled())
						break;
					
					final String dir = T24Aspect.getModule(domain);
					IFolder folder = null;
					if(selectedFolder != null){
					    folder = project.getProject().getFolder(selectedFolder.getProjectRelativePath()+"/"+dir);
					} else {
					    folder = project.getProject().getFolder("domain/"+"/" + dir);
					}
					if (!folder.exists()) {
						OfsCore.createFolder(folder);
					}
					final String name = domain.getName();
					if (name == null || name.trim().isEmpty())
						problems.addProblem("Ouch, found a weird domain with no name: " + domain.toString());
					final IFile file = folder.getFile(name + ".domain");
					final InputStream initialContents = new ByteArrayInputStream(new byte[0]);
					if (!file.exists()) {
						file.create(initialContents, false, new NullProgressMonitor());
					}
					monitor.subTask(file.getFullPath().toString());

					MdfDomainImpl domainImpl = (MdfDomainImpl)domain;
					Resource resource = domainImpl.eResource();
					if (resource == null) {
						resource = resourceSet.getResource(URI.createPlatformResourceURI(file.getFullPath().toString(), true), true);
					}
					if (domain.getMetamodelVersion() == null) {
						domainImpl.setMetamodelVersion(OfsCore.getCurrentMetaModelVersion("domain"));
					}
					resource.getContents().add(domainImpl);
					try {
						resource.save(null);
						// Intentionally catching ANY Throwable, not just IOException...
					} catch (Throwable e) {
						logger.error("Error when saving {}", resource.getURI(), e);
						problems.addProblem(name, e);
					}
					
					monitor.worked(1);
					
					
				}
				if (!problems.getProblems().isEmpty()) {
					IStatus status = new Status(IStatus.ERROR, ApplicationIntrospectionActivator.PLUGIN_ID, problems.toString(), problems);
					throw new CoreException(status);
				}
			}
		};
		
		monitor.beginTask("Writing " + domainList.size() + " Domain files...", domainList.size());
		ResourcesPlugin.getWorkspace().run(runnable, monitor);
		monitor.done();
	}

	public static void serializeHelpText(final Collection<MdfDomain> classDomainList, IProgressMonitor monitor) throws CoreException {
		final Map<Object, Object> saveOptions = new HashMap<Object, Object>();
		saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED, Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
		ApplicationsImportProblemsException problems = new ApplicationsImportProblemsException();
		monitor.beginTask("Writing",classDomainList.size()*2);
		
		for (final MdfDomain mdfDomain : classDomainList) {
			if (monitor.isCanceled())
				break;
			String name = mdfDomain.getName();
			try {
				EObject eObject = (EObject) mdfDomain;
				Resource resource = eObject.eResource();
				try {
					if (resource!=null && !eObject.eIsProxy()) {
						resource.save(saveOptions);	
						// Intentionally catching ANY Throwable, not just IOException...
					}
				} catch (Throwable e) {
					logger.error("Error when saving {}", resource.getURI(), e);
					problems.addProblem(name, e);
				}
			} catch (Throwable e) {
					problems.addProblem(name, e);
			}
			monitor.worked(1);
		}
		if (!problems.getProblems().isEmpty()) {
			IStatus status = new Status(IStatus.ERROR, ApplicationIntrospectionActivator.PLUGIN_ID, problems.toString(), problems);
			throw new CoreException(status);
		}
		
		monitor.beginTask("Writing " + classDomainList.size() + " Class files...", classDomainList.size());
		monitor.done();
	}
	
}
