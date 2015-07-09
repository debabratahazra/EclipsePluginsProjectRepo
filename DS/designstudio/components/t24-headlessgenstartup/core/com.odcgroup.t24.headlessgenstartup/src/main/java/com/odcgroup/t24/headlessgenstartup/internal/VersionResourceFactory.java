package com.odcgroup.t24.headlessgenstartup.internal;

import org.eclipse.xtext.resource.IResourceFactory;

import com.odcgroup.t24.version.VersionDSLStandaloneSetup;
import com.odcgroup.workbench.dsl.resources.AbstractDSLResourceFactory;

/**
 * This is a simply resource factory to be used for files with "version" extension. We do not use the
 * default Xtext mechanism here (which is an extension point in the UI plugin), because we need this
 * to work for the generator edition, where the UI plugin is not present.
 * See also https://bugs.eclipse.org/bugs/show_bug.cgi?id=323572
 * 
 * @author Kai Kreuzer
 *
 */
public class VersionResourceFactory extends AbstractDSLResourceFactory {
		
	@Override
	protected IResourceFactory createResourceFactory() {
		return VersionDSLStandaloneSetup.getInjector().getInstance(IResourceFactory.class);
	}

}
