package com.odcgroup.iris.generator;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Test ResourceField object
 *
 * @author sjunejo
 *
 */
public class ResourceFieldTest {

	@Test
	public void testResourceFieldWithNull() {
		ResourceField rField = new ResourceField("Something", "ANYTHING", null, null, "", "", true, false, "", false, false, "");
		assertNotNull(rField.getJoinedTo());
		assertNotNull(rField.getPropertyGroup());
	}
}
