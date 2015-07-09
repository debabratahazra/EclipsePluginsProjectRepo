package com.odcgroup.workbench.dsl.resources;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.IResourceFactory;

/**
 * This is a resource factory to be extended. We do not use the
 * default Xtext mechanism here (which is an extension point in the UI plugin), because we need this
 * to work for the generator edition, where the UI plugin is not present.
 * See also https://bugs.eclipse.org/bugs/show_bug.cgi?id=323572
 * 
 * @author Kai Kreuzer
 *
 */
public abstract class AbstractDSLResourceFactory implements IResourceFactory {
	
	private IResourceFactory resourceFactory;

	public AbstractDSLResourceFactory() {
		resourceFactory = createResourceFactory();
	}
	
	protected abstract IResourceFactory createResourceFactory();

	@Override
	public Resource createResource(URI uri) {
		return resourceFactory.createResource(uri);
	}
}
