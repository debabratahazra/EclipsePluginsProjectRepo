package com.odcgroup.service.model.ui.outline;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.odcgroup.service.model.component.AccessSpecifier;

/**
 * Filter on Access specifier: Public
 */
public class FilterPublicAccessContribution extends AbstractFilterAccessContribution {

	public FilterPublicAccessContribution() {
		super("ui.outline.filterPublicAccess", AccessSpecifier.PUBLIC);
	}

	protected ImageDescriptor getImageDescriptor() {
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, "/icons/public.png");
	}

}
