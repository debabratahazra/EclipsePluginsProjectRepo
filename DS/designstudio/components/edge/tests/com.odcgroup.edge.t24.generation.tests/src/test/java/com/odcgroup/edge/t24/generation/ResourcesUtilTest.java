package com.odcgroup.edge.t24.generation;

import static org.junit.Assert.assertNotNull;

import java.net.URL;

import org.junit.Test;

import com.odcgroup.edge.t24.generation.util.ResourcesUtil;

public class ResourcesUtilTest {

	@Test
	public void testGetResource() {
		URL url = ResourcesUtil.getPlugInResource("/templates/BasicVersionScreenIn3DTemplate.ifp");
		assertNotNull(url);
		//System.out.println(url.toExternalForm());
	}
}
