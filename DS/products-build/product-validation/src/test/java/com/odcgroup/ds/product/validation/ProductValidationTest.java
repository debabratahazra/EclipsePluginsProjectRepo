package com.odcgroup.ds.product.validation;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import junit.framework.Assert;

import org.jdom2.JDOMException;
import org.junit.Test;

import com.odcgroup.ds.product.validation.ProductValidator.ProductValidationFailed;

public class ProductValidationTest {

	private static final String PLUGIN_BASED_PRODUCT_1 = "/plugin-based-product-1.product";
	private static final String PLUGIN_BASED_PRODUCT_2 = "/plugin-based-product-2.product";
	private static final String TEST_PRODUCT_ZIP = "/test-product.zip";

	@Test
	public void testException() {
		ProductValidator validator = new ProductValidator();
		Assert.assertFalse(validator.isException("plugins/com.odcgroup.mdf_9.0.0.201306100919"));
		Assert.assertFalse(validator.isException("plugins/com.odcgroup.mdf_9.0.0.201306100919.jar"));
		Assert.assertTrue(validator.isException("plugins/org.eclipse.core.resources.win32.x86_3.5.100.v20110423-0524"));
		Assert.assertTrue(validator.isException("plugins/org.eclipse.core.resources.win32.x86_3.5.100.v20110423-0524.jar"));
	}
	
	@Test
	public void testGetPlugins() throws IOException {
		ProductValidator validator = new ProductValidator();
		List<String> includedPlugins = validator.getIncludedPlugins(getResourceAsFile(TEST_PRODUCT_ZIP));
		Assert.assertEquals("Wrong number of plugins detected", 4, includedPlugins.size());
		Assert.assertTrue("Should contains this plugin", includedPlugins.contains("plugins/com.odcgroup.mdf_9.0.0.201306100919/"));
		Assert.assertTrue("Should contains this plugin", includedPlugins.contains("plugins/org.eclipse.equinox.launcher.win32.win32.x86_1.1.100.v20110502/"));
		Assert.assertTrue("Should contains this plugin", includedPlugins.contains("plugins/com.odcgroup.domain.translation_9.0.0.201306100919.jar"));
		Assert.assertTrue("Should contains this plugin", includedPlugins.contains("plugins/org.eclipse.core.resources.win32.x86_3.5.100.v20110423-0524.jar"));
	}
	
	@Test
	public void testGetDeclaredPlugins() throws IOException, JDOMException {
		ProductValidator validator = new ProductValidator();
		List<String> includedPlugins = validator.getDeclaredPlugins(getResourceAsFile(PLUGIN_BASED_PRODUCT_1));
		Assert.assertEquals("Wrong number of plugins detected", 2, includedPlugins.size());
		Assert.assertTrue("Should contains this plugin", includedPlugins.contains("com.odcgroup.mdf"));
		Assert.assertTrue("Should contains this plugin", includedPlugins.contains("com.odcgroup.domain.translation"));
	}
	
	@Test
	public void testValidateOk() throws ProductValidationFailed {
		ProductValidator validator = new ProductValidator();
		validator.validate(getResourceAsFile(PLUGIN_BASED_PRODUCT_1), getResourceAsFile(TEST_PRODUCT_ZIP));
	}
	
	@Test(expected=ProductValidator.ProductValidationFailed.class)
	public void testValidateFailure() throws ProductValidationFailed {
		ProductValidator validator = new ProductValidator();
		validator.validate(getResourceAsFile(PLUGIN_BASED_PRODUCT_2), getResourceAsFile(TEST_PRODUCT_ZIP));
	}
	
	private File getResourceAsFile(String name) {
		try {
			return new File(ProductValidationTest.class.getResource(name).toURI());
		} catch (URISyntaxException e) {
			throw new RuntimeException("Unable to find " + name);
		}
	}

}
