package com.odcgroup.workbench.core.resources;

import java.io.ByteArrayInputStream;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.odcgroup.workbench.core.IOfsModelContainer;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.IDependencyManager;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class OfsModelPackageTest extends AbstractDSUnitTest {

	OfsModelPackage modelPackage = null;
	IFile testDomainFile;
	
	@Before
	public void setUp() throws Exception {
		createModelsProject();
		IFolder dummyFolder = getProject().getFolder("domain").getFolder(new Path("dummy"));
		dummyFolder.create(true, true, null);
		testDomainFile = dummyFolder.getFile("test.domain");
		testDomainFile.create(new ByteArrayInputStream("".getBytes()), true, null);
		modelPackage = new OfsModelPackage(URI.createURI("resource:///dummy"), getOfsProject().getModelFolder("domain"), IOfsProject.SCOPE_PROJECT);
	}
	
	@After
	public void tearDown() throws Exception {
		modelPackage = null;
		deleteModelsProjects();
	}
	
	@Test
	public void testGetDependencyModels_DS4280() throws URISyntaxException {
		
		Set<java.net.URI> uris = new HashSet<java.net.URI>();
		uris.add(new java.net.URI("jar:file:/C:/fake/models.jar!/domain/dummy/test.domain"));
		uris.add(testDomainFile.getLocation().toFile().toURI());
		
		Set<IOfsModelContainer> deps = new HashSet<IOfsModelContainer>();
		deps.add(getOfsProject());
		IDependencyManager depMgr = Mockito.mock(IDependencyManager.class);
		Mockito.when(depMgr.getDependencies(getOfsProject())).thenReturn(deps);
		Mockito.when(depMgr.getFileURIs(getOfsProject(), new Path("domain/dummy"))).thenReturn(uris);
		modelPackage.depMgr = depMgr; 
		Collection<IOfsModelResource> depModels = modelPackage.getDependencyModels();
		Assert.assertEquals(1, depModels.size());
		Assert.assertEquals(testDomainFile, depModels.iterator().next().getResource());
	}
}