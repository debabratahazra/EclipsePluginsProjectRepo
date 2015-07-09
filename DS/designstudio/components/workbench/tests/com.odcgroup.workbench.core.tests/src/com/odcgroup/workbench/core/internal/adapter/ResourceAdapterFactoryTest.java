package com.odcgroup.workbench.core.internal.adapter;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelFolder;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class ResourceAdapterFactoryTest extends AbstractDSUnitTest {

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyResourceInModelsProject("domain/general/Domain.domain");
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}

	@Test
	public void testGetAdapterList() {
		ResourceAdapterFactory factory = new ResourceAdapterFactory();
		Assert.assertEquals(2, factory.getAdapterList().length);
		Assert.assertTrue(ArrayUtils.contains(factory.getAdapterList(), IOfsProject.class));
		Assert.assertTrue(ArrayUtils.contains(factory.getAdapterList(), IOfsElement.class));
	}

	@Test
	public void testGetAdapter() {
		ResourceAdapterFactory factory = new ResourceAdapterFactory();

		Object adapter = factory.getAdapter(getProject(), IOfsProject.class);
		Assert.assertTrue(adapter instanceof IOfsProject);
		
		adapter = factory.getAdapter(getProject().getFolder("domain"), IOfsElement.class);
		Assert.assertTrue(adapter instanceof IOfsModelFolder);

		adapter = factory.getAdapter(getProject().getFolder("domain/general"), IOfsElement.class);
		Assert.assertTrue(adapter instanceof IOfsModelPackage);

		adapter = factory.getAdapter(getProject().getFile("domain/general/Domain.domain"), IOfsElement.class);
		Assert.assertTrue(adapter instanceof IOfsModelResource);
	}
	
	@Test
	public void testDS3683() throws CoreException {
		IFile sourceFile = getProject().getFile("domain/general/Domain.domain");
		IFolder targetFolder = getProject().getFolder("target/domain/general");
		OfsCore.createFolder(targetFolder);
		IFile targetFile = targetFolder.getFile("Domain.domain");
		targetFile.create(sourceFile.getContents(), true, null);

		ResourceAdapterFactory factory = new ResourceAdapterFactory();
		Object adapter = factory.getAdapter(getProject().getFile("target/domain/general/Domain.domain"), IOfsElement.class);
		Assert.assertNull(adapter);
	}

}
