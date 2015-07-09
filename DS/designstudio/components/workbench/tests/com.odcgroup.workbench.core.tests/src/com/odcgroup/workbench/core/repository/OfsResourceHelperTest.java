package com.odcgroup.workbench.core.repository;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.odcgroup.workbench.core.IOfsProject;

public class OfsResourceHelperTest {
	
	@Test
	public void testGetOfsProjectWithNullResourceReturnsNull() throws Exception {
		Resource resource = null;
		IOfsProject actualProject = OfsResourceHelper.getOfsProject(resource);	
		Assert.assertNull(actualProject);
	}
	
	@Test
	public void testIsURIofValidModelFolder() throws Exception {
		URI uri = URI.createPlatformResourceURI("test-project/target/test/test.version", false);
		Assert.assertTrue(uri.isPlatformResource());
		Assert.assertFalse(OfsResourceHelper.isURIofValidModelFolder(uri));
		
		uri = ModelURIConverter.toResourceURI(uri);
		Assert.assertFalse(OfsResourceHelper.isURIofValidModelFolder(uri));
	}
	
	@Test
	public void testGetOfsProjectWithNullResourceSetReturnsNull() throws Exception {
		// given 
		Resource resource = Mockito.mock(Resource.class);
		Mockito.when(resource.getResourceSet()).thenReturn(null);
		
		//when
		IOfsProject actualProject = OfsResourceHelper.getOfsProject(resource);		
		
		//then
		Assert.assertNull(actualProject);
	}
	
	@Test
	public void testGetOfsProjectWhenResultSetHasModelURIConverterReturnsProject() throws Exception {
		// given 
		IOfsProject expectedProject = Mockito.mock(IOfsProject.class);
		
		ModelURIConverter converter = Mockito.mock(ModelURIConverter.class);
		Mockito.when(converter.getOfsProject()).thenReturn(expectedProject);
		
		ResourceSet resourceSet = Mockito.mock(ResourceSet.class);
		Mockito.when(resourceSet.getURIConverter()).thenReturn(converter);
		
		Resource resource = Mockito.mock(Resource.class);
		Mockito.when(resource.getResourceSet()).thenReturn(resourceSet);
		
		// when
		IOfsProject actualProject = OfsResourceHelper.getOfsProject(resource);
		
		// then
		Assert.assertEquals(expectedProject, actualProject);
	}
	
}
