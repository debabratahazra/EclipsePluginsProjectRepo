package com.odcgroup.service.gen.t24.internal.categories.cpp.service;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.service.gen.t24.AbstractComponentCodeGenerator2;
import com.odcgroup.service.gen.t24.internal.cartridges.cpp.CppServiceAPIGenerator;
import com.odcgroup.service.gen.t24.internal.generator.ServiceGenerator;
import com.odcgroup.service.gen.t24.internal.generator.TemplateOutsideJarLoader;

public class CppServiceAPICodeGenerator extends AbstractComponentCodeGenerator2 {

	@Override
	protected List<ServiceGenerator> getServiceGenerator() {
		List<ServiceGenerator> generators = new ArrayList<ServiceGenerator>();
		generators.add(new CppServiceAPIGenerator(new TemplateOutsideJarLoader()));
		return generators;
	}
}
