package com.odcgroup.service.gen.t24.internal.generator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;

import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;

/**
 * This implementation intended to be deployed in the executable jar, and the template paths 
 * are in relation to the top level of the jar. It reads the file as a string and then uses 
 * Velocity's {@link StringResourceRepository} and {@link StringResourceLoader} 
 * to transform this into a {@link Template} instance. 
 * @author jannadani
 *
 */
public class TemplateEmbeddedInJarLoader implements VelocityTemplateLoader {

	private static VelocityEngine engine;
	
	static {
		try {
			engine = getVelocityEngine();
		} catch (Exception e) {
			throw new RuntimeException(e);	// TODO use our own exception?
		} 
	}

	/* (non-Javadoc)
	 * @see com.temenos.t24.modelgen.VelocityTemplateLoader#loadTemplate(java.lang.String)
	 */
	public Template loadTemplate(String template) throws LoadTemplateException {
		// leading "/" means relative to top level of containing JAR
		String pathToTemplate = "/" + template;
		try {			
			
			String myTemplateBody = getTemplateBody(pathToTemplate);	

			StringResourceRepository repository = StringResourceLoader.getRepository();
			repository.putStringResource(pathToTemplate, myTemplateBody);
			
			return engine.getTemplate(pathToTemplate);			
		} catch (Exception e) {
			throw new LoadTemplateException("Cannot locate Velocity template: " + pathToTemplate);
		}
	}
	
	static String getTemplateBody(String pathToTemplate) throws IOException {
		InputStream inStream = TemplateEmbeddedInJarLoader.class.getResourceAsStream(pathToTemplate);
		StringBuilder stringBuilder = new StringBuilder();
		InputStreamReader streamReader = new InputStreamReader(inStream);
		BufferedReader bufferedReader = new BufferedReader(streamReader);
			 
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append('\n');
		}
		bufferedReader.close();
		return stringBuilder.toString();
	}
	
	static VelocityEngine getVelocityEngine() throws Exception {
		Properties p = new Properties();
		
		p.setProperty("resource.loader", "string");
		p.setProperty("string.resource.loader.class", "org.apache.velocity.runtime.resource.loader.StringResourceLoader");
		VelocityEngine engine = new VelocityEngine();
		engine.init(p);
		return engine;
	}	
}
