package com.odcgroup.workbench.core.internal.repository.maven;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.workbench.core.OfsCore;

import de.visualrules.core.objectfactory.AbstractObjectFactory;

public class ModelCustomizerObjectFactory extends AbstractObjectFactory {

	public ModelCustomizerObjectFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object createObject(String objectId) throws CoreException {
		if ("de.visualrules.artifact.maven.util.IModelCustomizer".equals(objectId)) {
			return new PomModelCustomizer();
		}
		throw new CoreException(new Status(IStatus.ERROR, OfsCore.PLUGIN_ID, "Factory not registered for object id:"
				+ objectId));
	}

}
