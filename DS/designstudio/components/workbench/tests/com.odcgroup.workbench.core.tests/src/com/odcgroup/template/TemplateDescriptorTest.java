package com.odcgroup.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.workbench.core.OfsCore;

/**
 * @author yan
 */
public class TemplateDescriptorTest {

	@Test
	public void testDescriptorCase1() throws IOException {
		TemplateDescriptor descriptor = getTemplateDescriptor("resources/templates/descriptors/case1");
		Assert.assertEquals("case1", descriptor.getName());
		Assert.assertEquals("Wrong template description", "Case 1 template, no parameter", descriptor.getDescription());
		Assert.assertEquals("Shouldn't have an autowire module pom defined", "", descriptor.getAutowireModulePom());
	}
	
	@Test
	public void testDescriptorCase2() throws IOException {
		TemplateDescriptor descriptor = getTemplateDescriptor("resources/templates/descriptors/case2");
		Assert.assertEquals("case2", descriptor.getName());
		Assert.assertEquals("Wrong template description", "Case 2 template, no parameter, but with autowire", descriptor.getDescription());
		Assert.assertEquals("Should have an autowire module pom defined", "custo/pom.xml", descriptor.getAutowireModulePom());
	}
	
	@Test
	public void testDescriptorCase3() throws IOException {
		TemplateDescriptor descriptor = getTemplateDescriptor("resources/templates/descriptors/case3");
		Assert.assertEquals("case3", descriptor.getName());
		Assert.assertEquals("Wrong template description", "Case 3 template, with unordered parameters", descriptor.getDescription());
		testParameters(descriptor);
	}

	@Test
	public void testDescriptorCase4() throws IOException {
		TemplateDescriptor descriptor = getTemplateDescriptor("resources/templates/descriptors/case4");
		Assert.assertEquals("case4", descriptor.getName());
		Assert.assertEquals("Wrong template description", "Case 4 template, with ordered parameters", descriptor.getDescription());

		testParameters(descriptor);
		
		// Test parameters order
		Assert.assertEquals("Wrong order", "first", descriptor.getParameters().get(0).getName());
		Assert.assertEquals("Wrong order", "second", descriptor.getParameters().get(1).getName());
		Assert.assertEquals("Wrong order", "third", descriptor.getParameters().get(2).getName());
	}
	
	@Test
	public void testDescriptorCase5() throws IOException {
		TemplateDescriptor descriptor = getTemplateDescriptor("resources/templates/descriptors/case5");
		Assert.assertEquals("case5", descriptor.getName());
		Assert.assertEquals("Wrong template description", "Case 5 template, with ordered parameters", descriptor.getDescription());

		testParameters(descriptor);
		
		// Test parameters order
		Assert.assertEquals("Wrong order", "third", descriptor.getParameters().get(0).getName());
		Assert.assertEquals("Wrong order", "second", descriptor.getParameters().get(1).getName());
		Assert.assertEquals("Wrong order", "first", descriptor.getParameters().get(2).getName());
	}

	@Test
	public void testDescriptorCase6() throws IOException {
		TemplateDescriptor descriptor = getTemplateDescriptor("resources/templates/descriptors/case6");
		Assert.assertEquals("case6", descriptor.getName());
		Assert.assertEquals("Wrong template description", "Case 6 template, with partially ordered parameters", descriptor.getDescription());

		// Test parameters order
		// Forced order
		Assert.assertEquals("Wrong order", "third", descriptor.getParameters().get(0).getName());
		Assert.assertEquals("Wrong order", "second", descriptor.getParameters().get(1).getName());
		// Alphabetical order
		Assert.assertEquals("Wrong order", "fifth", descriptor.getParameters().get(2).getName());
		Assert.assertEquals("Wrong order", "first", descriptor.getParameters().get(3).getName());
		Assert.assertEquals("Wrong order", "forth", descriptor.getParameters().get(4).getName());
	}
	
	private TemplateDescriptor getTemplateDescriptor(String path) throws IOException {
		URL url = FileLocator.find(OfsCore.getDefault().getBundle(),
                new Path(path), null);
		File template = new File(FileLocator.toFileURL(url).getPath(), TemplateCreator.TEMPLATE_DESCRIPTOR_PROPERTIES);
		InputStream is = null;
		try {
			is = new FileInputStream(template);
			return TemplateCreator.instance().getTemplateDescriptor(template, is, new ArrayList<String>());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * @param descriptor
	 */
	private void testParameters(TemplateDescriptor descriptor) {
		Assert.assertEquals("Wrong number of parameters", 3, descriptor.getParameters().size());
		for (TemplateParameterDescriptor parameter : descriptor.getParameters()) {
			if (parameter.getName().equals("first")) {
				Assert.assertEquals("Wrong default value", "First default value", parameter.getDefaultValue());
				Assert.assertEquals("Wrong description", "First description", parameter.getDescription());
			} else if (parameter.getName().equals("second")) {
				Assert.assertEquals("Wrong default value", "", parameter.getDefaultValue());
				Assert.assertEquals("Wrong description", "Second description (no default value, no mandatory parameter)", parameter.getDescription());
			} else if (parameter.getName().equals("third")) {
				Assert.assertEquals("Wrong default value", "", parameter.getDefaultValue());
				Assert.assertEquals("Wrong description", "Third description (no default value, not mandatory)", parameter.getDescription());
			} else {
				Assert.fail("Shouldn't have other parameter");
			}
		}
	}
	
}
