package com.odcgroup.service.model.ui.outline;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.odcgroup.service.model.component.AccessSpecifier;

/**
 * Filter on Access specifier: Private
 */
public class FilterPrivateAccessContribution extends AbstractFilterAccessContribution {

	public FilterPrivateAccessContribution() {
		super("ui.outline.filterPrivateAccess", AccessSpecifier.PRIVATE);
	}

	protected ImageDescriptor getImageDescriptor() {
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, "/icons/private.png");
	}

}
