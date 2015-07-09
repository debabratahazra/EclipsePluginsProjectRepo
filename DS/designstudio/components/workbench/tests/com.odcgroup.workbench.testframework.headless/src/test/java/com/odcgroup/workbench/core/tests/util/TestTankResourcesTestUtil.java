package com.odcgroup.workbench.core.tests.util;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.EMFPlugin;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.odcgroup.workbench.testframework.headless.TestFrameworkHeadlessCore;

public final class TestTankResourcesTestUtil {
	
	private static final String TEST_TANK_ROOT_FOLDER = "/resources/test-tank-models";

	public static String loadTestTankResourceAsString(Class<?> testClass, String relativePath) throws IOException {
		return loadTestTankResourceAsString(testClass, relativePath, Charsets.UTF_8);
	}
	
	public static String loadTestTankResourceAsString(Class<?> testClass, String relativePath, Charset charset) throws IOException {
		final String resourcePath = TEST_TANK_ROOT_FOLDER + relativePath;
		URL resourceURL = (EMFPlugin.IS_ECLIPSE_RUNNING)
			? FileLocator.find(TestFrameworkHeadlessCore.getDefault().getBundle(), new Path(resourcePath), null)
			: testClass.getResource(resourcePath);
		return Resources.toString(resourceURL, charset);
	}
}
