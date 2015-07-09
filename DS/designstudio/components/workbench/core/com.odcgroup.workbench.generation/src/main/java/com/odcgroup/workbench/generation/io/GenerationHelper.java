package com.odcgroup.workbench.generation.io;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

public class GenerationHelper {

	/** This method loads an XMI model file and returns the model object tree
	 *
	 * @param modelFile the file resource in Eclipse
	 * @param modelName the name of the model (e.g. domain, pageflow, process, etc.)
	 * @return the object tree of the model
	 * @throws CoreException
	 */
	public static EObject loadModel(IFile modelFile, String modelName) throws CoreException {
	    EObject model = null;

	    ResourceSet rs = new ResourceSetImpl();
	    URI uri = URI.createFileURI(modelFile.getLocation().toFile().getAbsolutePath());
	    model = (EObject) rs.getResource(uri, true).getContents().get(0);

	    return model;
	}

}
