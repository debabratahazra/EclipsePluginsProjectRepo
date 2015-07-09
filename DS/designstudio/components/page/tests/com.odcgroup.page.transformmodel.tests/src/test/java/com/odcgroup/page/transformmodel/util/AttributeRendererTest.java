package com.odcgroup.page.transformmodel.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author amc
 *
 */
public class AttributeRendererTest {

	@Test
	public void testAttributeRenderedAppendsNameValuePairToStringBuilder() throws Exception {
		// given 
		StringBuilder builder = new StringBuilder();
		final String NAME = "TESTNAME";
		final String VALUE = "TESTVALUE";
		AttributeRenderer renderer = AttributeRenderer.getInstance(builder);
		final String EXPECTED = " "+NAME+"=\""+VALUE+"\"";
		// when
		renderer.render(NAME, VALUE);
		// then
		Assert.assertEquals(EXPECTED, builder.toString());
	}
	
}
