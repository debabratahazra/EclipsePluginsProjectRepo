package com.odcgroup.service.gen.t24.internal.generator;

import org.apache.velocity.Template;

import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;

public interface VelocityTemplateLoader {
	/**
	 * @param pathToTemplate
	 * @return a Velocity {@link Template} instance for the template file path passed in. 
	 */
	Template loadTemplate(String pathToTemplate) throws LoadTemplateException;

}