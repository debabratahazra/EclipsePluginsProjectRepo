package com.odcgroup.headless.internal;

import org.eclipse.xtext.resource.IResourceFactory;

import com.odcgroup.menu.model.MenuStandaloneSetup;
import com.odcgroup.workbench.dsl.resources.AbstractDSLResourceFactory;

/**
 * This is a simply resource factory to be used for files with "menu" extension. We do not use the
 * default Xtext mechanism here (which is an extension point in the UI plugin), because we need this
 * to work for the generator edition, where the UI plugin is not present.
 * See also https://bugs.eclipse.org/bugs/show_bug.cgi?id=323572
 * 
 * @author Kai Kreuzer
 *
 */
public class MenuResourceFactory extends AbstractDSLResourceFactory {

	@Override
	protected IResourceFactory createResourceFactory() {
		return MenuStandaloneSetup.getInjector().getInstance(IResourceFactory.class);
	}

}
