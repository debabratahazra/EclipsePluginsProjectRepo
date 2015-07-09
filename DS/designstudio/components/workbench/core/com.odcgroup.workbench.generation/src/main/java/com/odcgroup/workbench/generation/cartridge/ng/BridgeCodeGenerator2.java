package com.odcgroup.workbench.generation.cartridge.ng;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.inject.Inject;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Injector;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.di.ILangSpecificProvider;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.generation.GenerationCore;
import com.odcgroup.workbench.generation.cartridge.CodeGenerator;

/**
 * Utility to "bridge" from the old infrastructure to the new.
 * 
 * NOTE that, as per the doc. in CodeGenerator2, all cartridges will implement
 * both the CodeGenerator as well as the CodeGenerator2 interfaces (for the time
 * being, until we switch), but "in NG mode (FeatureSwitches.newCodeGen), their
 * CodeGenerator.run(IProject, Collection<IOfsModelResource>, IFolder) will
 * never be called anymore." - instead this helper runs, and in turn uses
 * CodeGenerator2.
 * 
 * @author Michael Vorburger
 */
public class BridgeCodeGenerator2 implements CodeGenerator, BridgeCodeGenerator {
	private static final Logger logger = LoggerFactory.getLogger(BridgeCodeGenerator2.class);

	@Inject ILangSpecificProvider<ResourceGenerator2> langSpecificResourceGenerator2Provider;

	@Inject ILangSpecificProvider<Injector> langSpecificInjectorProvider;
	
	private Class<? extends CodeGenerator2> codeGenerator2Class;

	private ModelLoader modelLoader;
	
	protected final ModelLoader getModelLoader() {
		return modelLoader;
	}
	
	@Override
	public void run(IProgressMonitor monitor, IProject modelsProject, Collection<IOfsModelResource> modelResources, IFolder outputFolder, List<IStatus> nonOkStatuses) {
		Collection<IResource> resources = transform(modelResources);
		run(modelsProject, resources, outputFolder, nonOkStatuses, monitor);
	}

	@Override
	public void run(IProject project, Collection<IResource> modelResources, IFolder outputFolder, List<IStatus> nonOkStatuses, IProgressMonitor monitor) {		
		int total = modelResources.size();
		String ofTotal = " of " + total + ")";
		int counter = 0;
		for (IResource resource : modelResources) {
			monitor.subTask("Resource : "+ resource.getName() + " (" + ++counter + ofTotal);
			URI standardURI = URI.createPlatformResourceURI(resource.getFullPath().toString(), true);
			
			if (standardURI != null) { // DS-7346
				ResourceGenerator2 resourceGenerator = langSpecificResourceGenerator2Provider.get(standardURI);
				CodeGenerator2 generator2 = langSpecificInjectorProvider.get(standardURI).getInstance(codeGenerator2Class);
				resourceGenerator.setCodeGenerator2(generator2);
				resourceGenerator.setUri(standardURI);
				
				try {
					resourceGenerator.generate(project, modelLoader, outputFolder);
				} catch (CoreException exception) {
					String errorMsg = "Error while executing " + getClass().getName() +" for model '" + resource.getName() + "'";
					errorMsg += ": "+exception.getMessage();
					final IStatus errorStatus = new Status(IStatus.ERROR, GenerationCore.PLUGIN_ID, 0,	errorMsg, exception);
					nonOkStatuses.add(errorStatus);					
					logger.error(errorStatus.getMessage(), exception);
				}
				MemoryGuy.giveMeSomeSunshineIfNearOOM(getResourceSet(), generator2);
			}
			monitor.worked(1);
			if (monitor.isCanceled())
				return;
		}
		
	}

	/**
	 * Converts proprietary DS resource:// model URI to standard EMF platform:// URIs.
	 * 
	 * @param project the IProject, which will necessarily have to have an IOfsProject - needed to transform resource:// model URIs (which do not contain the project name anymore) 
	 * @param uri the DS resource:// model URI
	 * @return the EMF platform:// URI
	 */
	protected URI convertToStandardURI(IOfsProject modelsOfsProject, URI uri) {
		Preconditions.checkNotNull(uri, "uri == null");
		if (!ModelURIConverter.isModelUri(uri)) {
			return uri;
		}
		return new ModelURIConverter(modelsOfsProject).toPlatformURI(uri);
	}

	public void setCodeGenerator2Class(Class<? extends CodeGenerator2> codeGenerator2Class) {
		this.codeGenerator2Class = codeGenerator2Class;
	}	

	public void setModelLoader(ModelLoader modelLoader) {
		this.modelLoader = modelLoader;
	}
	
	protected ResourceSet getResourceSet() {
		return ((ModelLoaderImpl)getModelLoader()).getResourceSet();
	}
	
	protected Collection<IResource> transform(Collection<IOfsModelResource> mResources) {
		Collection<IResource> resources = new CopyOnWriteArraySet<IResource>();
		for(IOfsModelResource res : mResources) {
			resources.add(res.getResource());
		}
		return resources;
	}

}
