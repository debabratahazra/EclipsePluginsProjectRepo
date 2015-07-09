package com.odcgroup.ocs.packager.launcher;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.junit.Assert;
import org.junit.Test;

public class PackagerLauncherHelperTest  {
	
	@Test
	public void testConfigureLaunch_usesDefault() {
		// Given
		MockLaunchConfigurationWorkingCopy spyWorkingCopy = new MockLaunchConfigurationWorkingCopy();
		
		Properties packagerProperties = new Properties();
		
		Map<String, Object> defaultProperties = new HashMap<String, Object>();
		defaultProperties.put("attributeBoolean1", true);
		defaultProperties.put("attributeBoolean2", false);
		defaultProperties.put("attributeString", "someString");
		defaultProperties.put("attributeList", Arrays.asList(StringUtils.split("abc,def,ghi",",")));
		
		// When
		PackagerLauncherHelper.getHelper().configureLaunch(spyWorkingCopy, packagerProperties, defaultProperties);
		
		// Then
		Assert.assertTrue(spyWorkingCopy.booleanAttributes.get("attributeBoolean1"));
		Assert.assertFalse(spyWorkingCopy.booleanAttributes.get("attributeBoolean2"));
		Assert.assertEquals("someString", spyWorkingCopy.stringAttributes.get("attributeString"));
		Assert.assertEquals(3, spyWorkingCopy.listAttributes.get("attributeList").size());
		Assert.assertEquals("abc", spyWorkingCopy.listAttributes.get("attributeList").get(0));
		Assert.assertEquals("def", spyWorkingCopy.listAttributes.get("attributeList").get(1));
		Assert.assertEquals("ghi", spyWorkingCopy.listAttributes.get("attributeList").get(2));
	}

	@Test
	public void testConfigureLaunch_overrideDefault() {
		// Given
		MockLaunchConfigurationWorkingCopy spyWorkingCopy = new MockLaunchConfigurationWorkingCopy();
		
		Properties packagerProperties = new Properties();
		packagerProperties.setProperty("attributeBoolean1", "false");
		packagerProperties.setProperty("attributeBoolean2", "true");
		packagerProperties.setProperty("attributeString", "someModifiedString");
		packagerProperties.setProperty("attributeList", "abc*,def*,ghi*");
		packagerProperties.setProperty("attributeNewBoolean1", "true");
		packagerProperties.setProperty("attributeNewBoolean2", "false");
		packagerProperties.setProperty("attributeNewString", "someOtherString");
		packagerProperties.setProperty("attributeNewList", "123,456,789");
		
		Map<String, Object> defaultProperties = new HashMap<String, Object>();
		defaultProperties.put("attributeBoolean1", true);
		defaultProperties.put("attributeBoolean2", false);
		defaultProperties.put("attributeString", "someString");
		defaultProperties.put("attributeList", Arrays.asList(StringUtils.split("abc,def,ghi",",")));
		
		// When
		PackagerLauncherHelper.getHelper().configureLaunch(spyWorkingCopy, packagerProperties, defaultProperties);
		
		// Then
		Assert.assertFalse(spyWorkingCopy.booleanAttributes.get("attributeBoolean1"));
		Assert.assertTrue(spyWorkingCopy.booleanAttributes.get("attributeBoolean2"));
		Assert.assertEquals("someModifiedString", spyWorkingCopy.stringAttributes.get("attributeString"));
		Assert.assertEquals(3, spyWorkingCopy.listAttributes.get("attributeList").size());
		Assert.assertEquals("abc*", spyWorkingCopy.listAttributes.get("attributeList").get(0));
		Assert.assertEquals("def*", spyWorkingCopy.listAttributes.get("attributeList").get(1));
		Assert.assertEquals("ghi*", spyWorkingCopy.listAttributes.get("attributeList").get(2));
		
		Assert.assertTrue(spyWorkingCopy.booleanAttributes.get("attributeNewBoolean1"));
		Assert.assertFalse(spyWorkingCopy.booleanAttributes.get("attributeNewBoolean2"));
		Assert.assertEquals("someOtherString", spyWorkingCopy.stringAttributes.get("attributeNewString"));
		Assert.assertFalse(spyWorkingCopy.listAttributes.containsKey("attributeNewList"));
		Assert.assertEquals("123,456,789", spyWorkingCopy.stringAttributes.get("attributeNewList"));
	}
	
	@Test
	public void testForceZipLocation() throws CoreException {
		// Given
		PackagerLauncherHelper helper = new PackagerLauncherHelper() {
			@Override
			protected String getWorkingCopyAttribute(
					ILaunchConfigurationWorkingCopy workingCopy) {
				return "some values";
			}
			
		};
		ILaunchConfigurationWorkingCopy workingCopy = helper.createWorkingCopy();
		
		// When
		helper.forceZipLocation(workingCopy, "C:\\some\\folder");
		
		// Then
		Assert.assertEquals("-DrepoFolder=C:\\some\\folder some values", workingCopy.getAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, ""));
	}
	
	@Test
	public void testForceZipLocationWithoutExistingVMArgs() throws CoreException {
		// Given
		PackagerLauncherHelper helper = new PackagerLauncherHelper() {
			@Override
			protected String getWorkingCopyAttribute(
					ILaunchConfigurationWorkingCopy workingCopy) {
				return null;
			}
			
		};
		ILaunchConfigurationWorkingCopy workingCopy = helper.createWorkingCopy();
		
		// When
		helper.forceZipLocation(workingCopy, "C:\\some\\folder");
		
		// Then
		Assert.assertEquals("-DrepoFolder=C:\\some\\folder", workingCopy.getAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, ""));
	}
	
}
