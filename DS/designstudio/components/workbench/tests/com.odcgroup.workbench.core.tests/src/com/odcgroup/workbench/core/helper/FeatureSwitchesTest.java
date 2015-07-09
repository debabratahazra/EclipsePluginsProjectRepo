package com.odcgroup.workbench.core.helper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.odcgroup.workbench.core.helper.FeatureSwitches.Feature;

/**
 * Unit test for FeatureEnablement.
 *
 * @author Michael Vorburger
 */
public class FeatureSwitchesTest {

	@Test public void testIntegerFeatureSwitchDefaultValue() {
		Feature<Integer> someIntegerBasedFeature = new Feature<Integer>("ds.someIntegerBasedFeature", 10);
		assertEquals(10, (int) someIntegerBasedFeature.get());
	}

	@Test public void testIntegerFeatureSwitchSystemPropertyOverride() {
		final String ANOTHER_INTEGER_BASED_FEATURE = "ds.anotherIntegerBasedFeature";
		System.setProperty(ANOTHER_INTEGER_BASED_FEATURE, "69");
		Feature<Integer> anotherIntegerBasedFeature = new Feature<Integer>(ANOTHER_INTEGER_BASED_FEATURE, 10);
		assertEquals(69, (int) anotherIntegerBasedFeature.get());
	}

	@Test public void testIntegerFeatureSwitchSystemBadPropertyOverride() {
		final String YET_ANOTHER_INTEGER_BASED_FEATURE = "ds.yetIntegerBasedFeature";
		System.setProperty(YET_ANOTHER_INTEGER_BASED_FEATURE, "yuyu");
		Feature<Integer> yetAnotherIntegerBasedFeature = new Feature<Integer>(YET_ANOTHER_INTEGER_BASED_FEATURE, 10);
		assertEquals(10, (int) yetAnotherIntegerBasedFeature.get());
	}

	@Test public void testBooleanFeatureSwitchDefaultValue() {
		Feature<Boolean> someBooleanBasedFeature = new Feature<Boolean>("ds.someBooleanBasedFeature", true);
		assertEquals(true, someBooleanBasedFeature.get());
	}

	@Test public void testBooleanFeatureSwitchSystemPropertyOverride() {
		final String ANOTHER_BOOLEAN_BASED_FEATURE = "ds.anotherBooleanBasedFeature";
		System.setProperty(ANOTHER_BOOLEAN_BASED_FEATURE, "true");
		Feature<Boolean> anotherBooleanBasedFeature = new Feature<Boolean>(ANOTHER_BOOLEAN_BASED_FEATURE, false);
		assertEquals(true, anotherBooleanBasedFeature.get());
	}

	@Test public void testBooleanFeatureSwitchSystemBadPropertyOverride() {
		final String YET_ANOTHER_BOOLEAN_BASED_FEATURE = "ds.yetBooleanBasedFeature";
		System.setProperty(YET_ANOTHER_BOOLEAN_BASED_FEATURE, "yuyu");
		Feature<Boolean> yetAnotherBooleanBasedFeature = new Feature<Boolean>(YET_ANOTHER_BOOLEAN_BASED_FEATURE, true);
		assertEquals(true, yetAnotherBooleanBasedFeature.get());
	}

	@Test public void testStringFeatureSwitchDefaultValue() {
		Feature<String> someStringBasedFeature = new Feature<String>("ds.someStringBasedFeature", "Hello");
		assertEquals("Hello", someStringBasedFeature.get());
	}

	@Test public void testStringFeatureSwitchSystemPropertyOverride() {
		final String ANOTHER_String_BASED_FEATURE = "ds.anotherStringBasedFeature";
		System.setProperty(ANOTHER_String_BASED_FEATURE, "XYZ");
		Feature<String> anotherStringBasedFeature = new Feature<String>(ANOTHER_String_BASED_FEATURE, "ABC");
		assertEquals("XYZ", anotherStringBasedFeature.get());
	}

	@Test public void testStringFeatureSwitchSystemBadPropertyOverride() {
		final String YET_ANOTHER_String_BASED_FEATURE = "ds.yetStringBasedFeature";
		System.setProperty(YET_ANOTHER_String_BASED_FEATURE, "");
		Feature<String> yetAnotherStringBasedFeature = new Feature<String>(YET_ANOTHER_String_BASED_FEATURE, "yuyu");
		assertEquals("yuyu", yetAnotherStringBasedFeature.get());
	}
}
