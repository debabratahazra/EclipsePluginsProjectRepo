package com.odcgroup.service.gen.t24.internal.categories.java.webservice;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.service.gen.t24.AbstractComponentCodeGenerator2;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;
import com.odcgroup.service.gen.t24.internal.generator.ServiceGenerator;
import com.odcgroup.service.gen.t24.internal.generator.TemplateOutsideJarLoader;

public class JavaWSServiceXMLCodeGenerator extends AbstractComponentCodeGenerator2 {
	Logger logger = LoggerFactory.getLogger(JavaWSServiceXMLCodeGenerator.class);

	@Override
	protected List<ServiceGenerator> getServiceGenerator() throws LoadTemplateException {
		List<ServiceGenerator> generators = new ArrayList<ServiceGenerator>();
		generators.add(new JavaWSServiceXMLGenerator(new TemplateOutsideJarLoader()));
		return generators;
	}
}
