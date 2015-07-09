package com.odcgroup.service.model.ui.outline;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.odcgroup.service.model.component.AccessSpecifier;

/**
 * Filter on Access specifier: Module
 */
public class FilterModuleAccessContribution extends AbstractFilterAccessContribution {

	public FilterModuleAccessContribution() {
		super("ui.outline.filterModuleAccess", AccessSpecifier.MODULE);
	}

	protected ImageDescriptor getImageDescriptor() {
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, "/icons/module.png");
	}

}
