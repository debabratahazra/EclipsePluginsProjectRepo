package com.odcgroup.workbench.core.internal.repository.maven.helper;

import org.eclipse.core.runtime.CoreException;

import de.visualrules.core.objectfactory.AbstractObjectFactory;

public class TestMavenSettingsCustomizerFactory extends AbstractObjectFactory {

	public TestMavenSettingsCustomizerFactory() {
	}

	@Override
	public Object createObject(String id) throws CoreException {
		if(id.equals("de.visualrules.artifact.maven.util.ISettingsCustomizer")) {
			return new TestMavenSettingsCustomizer();
		} else {
			return null;
		}
	}

}
