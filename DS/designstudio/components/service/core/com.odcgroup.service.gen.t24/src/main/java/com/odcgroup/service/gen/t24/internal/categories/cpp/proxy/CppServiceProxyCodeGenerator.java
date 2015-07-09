package com.odcgroup.service.gen.t24.internal.categories.cpp.proxy;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.service.gen.t24.AbstractComponentCodeGenerator2;
import com.odcgroup.service.gen.t24.internal.cartridges.cpp.CppServiceProxyGenerator;
import com.odcgroup.service.gen.t24.internal.generator.ServiceGenerator;
import com.odcgroup.service.gen.t24.internal.generator.TemplateOutsideJarLoader;

public class CppServiceProxyCodeGenerator extends AbstractComponentCodeGenerator2 {

	@Override
	protected List<ServiceGenerator> getServiceGenerator() {
		List<ServiceGenerator> generators = new ArrayList<ServiceGenerator>();
		generators.add(new CppServiceProxyGenerator(new TemplateOutsideJarLoader()));
		return generators;
	}

}