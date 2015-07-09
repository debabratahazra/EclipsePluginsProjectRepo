package com.odcgroup.ocs.server.external.ui.builder.internal.mapping;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * @author yandenmatten
 */
public class GenTargetMapperTest {
	
	@Test
	public void testGetTarget() {
		IFile existingFile = Mockito.mock(IFile.class);
		Mockito.when(existingFile.exists()).thenReturn(true);
		
		IProject project = Mockito.mock(IProject.class);
		Mockito.when(project.getFile(Matchers.<String>any())).thenReturn(existingFile);
		
		GenTargetMapper genTargetMapper = new GenTargetMapper(null, project);
		
		Assert.assertNull("Should be non mapped as not starting with target/classes", genTargetMapper.getSourceFolder("target/file.class"));
		Assert.assertNull("Should be non mapped as not starting with target/classes", genTargetMapper.getSourceFolder("target/api/domain/file.class"));
		Assert.assertNotNull("Should be mapped as starting with target/classes", genTargetMapper.getSourceFolder("target/classes/file.class"));
	}
}
