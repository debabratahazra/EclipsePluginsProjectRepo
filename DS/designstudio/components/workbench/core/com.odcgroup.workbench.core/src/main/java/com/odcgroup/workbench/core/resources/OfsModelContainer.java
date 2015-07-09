package com.odcgroup.workbench.core.resources;

import com.odcgroup.workbench.core.IContainerIdentifier;
import com.odcgroup.workbench.core.IOfsModelContainer;

/**
 * This is the basic implementation of an IOfsModelContainer
 *
 * @author Kai Kreuzer
 *
 */
public class OfsModelContainer implements IOfsModelContainer {

	private IContainerIdentifier identifier = null;

	public OfsModelContainer() {
	}

	public OfsModelContainer(IContainerIdentifier identifier) {
		this.identifier = identifier;
	}

	public IContainerIdentifier getIdentifier() {
		return identifier;
	}

	public String getName() {
		return (getIdentifier()!=null) ? getIdentifier().getName() : null;
	}

}
