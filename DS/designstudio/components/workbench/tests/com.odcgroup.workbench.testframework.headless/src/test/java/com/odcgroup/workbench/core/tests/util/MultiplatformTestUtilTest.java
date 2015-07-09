package com.odcgroup.workbench.core.tests.util;

import org.junit.Test;

public class MultiplatformTestUtilTest {

	@Test
	public void testAssertEqualsIgnoringEOL() {
		MultiplatformTestUtil.assertEqualsIgnoringEOL("NOK", "ja", "ja");
		MultiplatformTestUtil.assertEqualsIgnoringEOL("NOK", "ho\r\n", "ho\n");
		MultiplatformTestUtil.assertEqualsIgnoringEOL("NOK", "ho\r\n", "ho\r");
	}

}
