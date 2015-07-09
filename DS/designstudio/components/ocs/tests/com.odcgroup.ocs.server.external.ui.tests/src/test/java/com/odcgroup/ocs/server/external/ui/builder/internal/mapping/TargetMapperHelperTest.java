package com.odcgroup.ocs.server.external.ui.builder.internal.mapping;

import org.eclipse.core.resources.IProject;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.odcgroup.ocs.server.external.builder.internal.mapping.TargetMapper;

public class TargetMapperHelperTest {

	private static final String TEST_NON_GEN_PROJECT = "test-project";
	private static final String TEST_GEN_PROJECT = TEST_NON_GEN_PROJECT + TargetMapperHelper.GEN_SUFFIX;

	@Test
	public void testGetTargetMapperReturnsGeneratedTargetMapperWhenProjectNameEndsWithGen() throws Exception {
		IProject effectiveProject = Mockito.mock(IProject.class);
		Mockito.when(effectiveProject.getName()).thenReturn(TEST_GEN_PROJECT);

		TargetMapper targetMapper = TargetMapperHelper.getTargetMapper(effectiveProject );

		Assert.assertTrue(targetMapper instanceof GenTargetMapper);
	}

	@Test
	public void testGetTargetMapperReturnsNonGeneratedTargetMapperWhenProjectNameEndsWithGen() throws Exception {
		IProject effectiveProject = Mockito.mock(IProject.class);
		Mockito.when(effectiveProject.getName()).thenReturn(TEST_NON_GEN_PROJECT);

		TargetMapper targetMapper = TargetMapperHelper.getTargetMapper(effectiveProject );

		Assert.assertTrue(targetMapper instanceof NonGenTargetMapper);
	}

}
