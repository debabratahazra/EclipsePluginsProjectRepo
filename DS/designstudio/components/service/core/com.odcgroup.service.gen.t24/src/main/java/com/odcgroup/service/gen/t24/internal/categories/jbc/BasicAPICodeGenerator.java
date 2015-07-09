package com.odcgroup.service.gen.t24.internal.categories.jbc;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.service.gen.t24.AbstractComponentCodeGenerator2;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;
import com.odcgroup.service.gen.t24.internal.generator.BasicApiGenerator;
import com.odcgroup.service.gen.t24.internal.generator.JBCCustomizedTypeSchemaGenerator;
import com.odcgroup.service.gen.t24.internal.generator.JBCCustomizedTypeStructureGenerator;
import com.odcgroup.service.gen.t24.internal.generator.JBCGetMetaDataAPIGenerator;
import com.odcgroup.service.gen.t24.internal.generator.ServiceGenerator;
import com.odcgroup.service.gen.t24.internal.generator.TemplateOutsideJarLoader;

public class BasicAPICodeGenerator extends AbstractComponentCodeGenerator2 {

	Logger logger = LoggerFactory.getLogger(BasicAPICodeGenerator.class);

	@Override
	protected List<ServiceGenerator> getServiceGenerator() throws LoadTemplateException {
		List<ServiceGenerator> generators = new ArrayList<ServiceGenerator>();
		generators.add(new BasicApiGenerator(new TemplateOutsideJarLoader()));
		generators.add(new JBCGetMetaDataAPIGenerator(new TemplateOutsideJarLoader()));
		generators.add(new JBCCustomizedTypeSchemaGenerator(new TemplateOutsideJarLoader()));
		generators.add(new JBCCustomizedTypeStructureGenerator(new TemplateOutsideJarLoader()));
		return generators;
	}
}
