package com.odcgroup.workbench.core.internal.repository.maven;

import org.eclipse.core.runtime.CoreException;

import de.visualrules.core.objectfactory.AbstractObjectFactory;

/**
 * This class provides an implementation of ISettingsCustomizer and is registered
 * through a VisualRules extension point.
 * 
 * @author Kai Kreuzer
 *
 */
public class MavenSettingsCustomizerFactory extends AbstractObjectFactory {

	public MavenSettingsCustomizerFactory() {
	}

	@Override
	public Object createObject(String id) throws CoreException {
		if(id.equals("de.visualrules.artifact.maven.util.ISettingsCustomizer")) {
			return new MavenSettingsCustomizer();
		} else {
			return null;
		}
	}

}
