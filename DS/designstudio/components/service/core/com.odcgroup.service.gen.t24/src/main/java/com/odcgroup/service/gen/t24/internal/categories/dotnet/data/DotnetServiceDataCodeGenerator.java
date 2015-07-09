package com.odcgroup.service.gen.t24.internal.categories.dotnet.data;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.service.gen.t24.AbstractComponentCodeGenerator2;
import com.odcgroup.service.gen.t24.internal.generator.ServiceGenerator;
import com.odcgroup.service.gen.t24.internal.generator.TemplateOutsideJarLoader;

public class DotnetServiceDataCodeGenerator extends AbstractComponentCodeGenerator2 {

	@Override
	protected List<ServiceGenerator> getServiceGenerator() {
		List<ServiceGenerator> generators = new ArrayList<ServiceGenerator>();
		generators.add(new DotNetServiceDataGenerator(new TemplateOutsideJarLoader()));
		return generators;
	}
}
