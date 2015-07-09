package com.odcgroup.translation.generation;

import java.util.Collection;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.osgi.framework.BundleContext;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.workbench.core.IOfsModelResource;

/**
 * @author atr
 */
public interface ITranslationGenerationRuntime {

	/**
	 * @param project
	 * @param object
	 * @return
	 */
	ITranslationKey getTranslationKey(IProject project, ITranslation translation);

	/**
	 * @param project 
	 * @param modelResources
	 * @param properties
	 */
	void generate(IProject project, Collection<IOfsModelResource> modelResources, Map<String, ?> properties) throws CoreException;
	
	/**
	 * @param context
	 */
	void release(BundleContext context);
}
