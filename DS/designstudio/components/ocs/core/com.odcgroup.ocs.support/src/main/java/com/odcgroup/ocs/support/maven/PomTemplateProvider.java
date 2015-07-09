package com.odcgroup.ocs.support.maven;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

import com.odcgroup.ocs.support.OCSPluginActivator;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.helper.CoreExceptionHelper;
import com.odcgroup.workbench.core.repository.maven.IPomTemplatesProvider;

public class PomTemplateProvider implements IPomTemplatesProvider {

	@Override
	public String getModelsPomTemplate() throws CoreException {
		return getPomTemplate("models-pom.xml");	
	}

	@Override
	public String getModelGenPomTemplate() throws CoreException {
		return getPomTemplate("gen-pom.xml");
	}
	
	private String getPomTemplate(String templateName) throws CoreException {
		InputStream stream = null;
		try {
			URL pomUrl = OCSPluginActivator.getDefault().getBundle().getResource("templates/" + templateName);
			stream = pomUrl.openStream();
			return IOUtils.toString(stream);
		} catch (IOException e) {
			throw CoreExceptionHelper.createCoreException(IStatus.ERROR, OfsCore.PLUGIN_ID, 
					"Unable to get a model pom", e);
		} finally {
			IOUtils.closeQuietly(stream);
		}
	}


}
