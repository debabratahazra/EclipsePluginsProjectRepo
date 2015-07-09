package com.odcgroup.service.gen.t24.internal.generator;

import java.io.File;
import java.net.URL;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.service.gen.t24.ServiceGenerationT24Core;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;

/**
 * This implementation of {@link VelocityTemplateLoader} is used for testing.
 * 
 * @author jannadani
 * 
 */
public class TemplateOutsideJarLoader implements VelocityTemplateLoader {

	private static Logger logger = LoggerFactory.getLogger(TemplateOutsideJarLoader.class);

	public TemplateOutsideJarLoader() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.temenos.t24.modelgen.VelocityTemplateLoader#loadTemplate(java.lang
	 * .String)
	 */
	public Template loadTemplate(String template) throws LoadTemplateException {
		try {
			return initializeVelocity(template);
		} catch (Exception e) {
			logger.error("Cannot locate Velocity template: "+ template, e);
			throw new LoadTemplateException("Cannot locate Velocity template: "+ template, e);
		}
	}

	private Template initializeVelocity(String template) throws Exception {
		try {
			Plugin serviceActivator = ServiceGenerationT24Core.getDefault();
			if (null == serviceActivator)
				throw new Exception("generatorActivator is null");
			Bundle uiGeneratorBundle = serviceActivator.getBundle();
			Properties velocityProperties = new Properties();
			
			// find the location on local file system where Bundle is located
			// add this to velocity properties
			Path templatesPath = new Path("/resources");
			URL templatesURL = FileLocator.find(uiGeneratorBundle, templatesPath, null);
			if (null == templatesURL)
				throw new Exception("templatesURL is null for: " + "templates",	null);

			URL tempURL = FileLocator.toFileURL(templatesURL);
			String velocityPathValue = new File(tempURL.toURI()).toString();

			// use the properties to initialize velocity
			String velocityPathKey = "file.resource.loader.path";
			velocityProperties.put(velocityPathKey, velocityPathValue);
			Velocity.init(velocityProperties);
			return Velocity.getTemplate(template);
		}
		catch (Exception e) {
			throw new Exception(e);
		}
	}
}
