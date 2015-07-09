package com.odcgroup.workbench.generation.cartridge.ng;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.generation.GenerationCore;

public class BridgeStatefullCodeGenerator extends BridgeCodeGenerator2 {
	
	private StatefullCodeGenerator generator;
	
	private Map<String, ResourceGenerator2> resourceGenerator = new HashMap<String, ResourceGenerator2>();
	
	private ResourceGenerator2 getResourceGenerator(URI uri) {
		String extension = uri.fileExtension();
		ResourceGenerator2 rg = resourceGenerator.get(extension);
		if (rg == null) {
			rg = langSpecificResourceGenerator2Provider.get(uri);
			resourceGenerator.put(extension,  rg);
		}
		return rg;
	}
	
	@Override
	public void run(IProject project, Collection<IResource> modelResources, IFolder outputFolder, List<IStatus> nonOkStatuses, IProgressMonitor monitor) {	
		try {
			generator.beforeGeneration();
		} catch (CoreException exception) {
			String errorMsg = "Code generation error before generation";
			final IStatus errosStatus = new Status(IStatus.ERROR, GenerationCore.PLUGIN_ID, 0,	errorMsg + ": " + exception.getMessage(), exception);
			nonOkStatuses.add(errosStatus);
		}
		
		try {
			int total = modelResources.size();
			String ofTotal = " of " + total + ")";
			int counter = 0;
			for (IResource resource : modelResources) {
				monitor.subTask("Resource : "+resource.getName() + " (" + ++counter + ofTotal);
				URI standardURI = URI.createPlatformResourceURI(resource.getFullPath().toString(), true);
				if (standardURI != null) { // DS-7346 
					ResourceGenerator2 resourceGenerator = getResourceGenerator(standardURI);
					resourceGenerator.setCodeGenerator2(generator);
					resourceGenerator.setUri(standardURI);
					try {
						resourceGenerator.generate(project, getModelLoader(), outputFolder);
					} catch (CoreException exception) {
						String errorMsg = "Error while executing " + getClass().getName() +" for domain model '" + resource.getName() + "'";
						final IStatus errosStatus = new Status(IStatus.ERROR, GenerationCore.PLUGIN_ID, 0,	errorMsg + ": " + exception.getMessage(), exception);
						nonOkStatuses.add(errosStatus);
					}
				}
				monitor.worked(1);
				if (monitor.isCanceled()){
					break;
				}
				MemoryGuy.giveMeSomeSunshineIfNearOOM(getResourceSet(), generator);
			}
		}
		
		finally {
			try {
				generator.afterGeneration();
			} catch (CoreException exception) {
				String errorMsg = "Code generation error after generation";
				final IStatus errosStatus = new Status(IStatus.ERROR, GenerationCore.PLUGIN_ID, 0,	errorMsg + ": " + exception.getMessage(), exception);
				nonOkStatuses.add(errosStatus);
			}
		}
	}

	@Override
	public void run(IProgressMonitor monitor, IProject modelsProject, Collection<IOfsModelResource> modelResources, IFolder outputFolder, List<IStatus> nonOkStatuses) {
		Collection<IResource> resources = transform(modelResources);
		run(modelsProject, resources, outputFolder, nonOkStatuses, monitor);
	}

	public void setCodeGenerator(StatefullCodeGenerator statefullCodeGenerator) {
		setCodeGenerator2Class(statefullCodeGenerator.getClass());
		generator = statefullCodeGenerator;
	}

}
