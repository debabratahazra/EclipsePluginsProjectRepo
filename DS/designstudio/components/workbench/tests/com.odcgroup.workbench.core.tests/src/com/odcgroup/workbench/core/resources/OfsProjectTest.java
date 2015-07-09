package com.odcgroup.workbench.core.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;
import com.odcgroup.workbench.testframework.headless.TestFrameworkHeadlessCore;

public class OfsProjectTest extends AbstractDSUnitTest {

	@Before
	public void setUp() throws Exception {
		createModelsProject();
	}
	
	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	@Test
	public void testModelResourceChangeOutsideModelFolder_DS3683() throws CoreException, URISyntaxException, IOException {
		IFolder targetFolder = getProject().getFolder("target/general");
		OfsCore.createFolder(targetFolder);

		URL url = FileLocator.find(TestFrameworkHeadlessCore.getDefault().getBundle(), new Path(RESOURCES_FOLDER + "/domain/general/Domain.domain"), null);
        File domainFile = new File(FileLocator.toFileURL(url).toURI());
        IFile fakeDomainFile = targetFolder.getFile("Domain.domain");
        fakeDomainFile.create(new FileInputStream(domainFile), true, null);
		try {
	        OfsCore.getOfsProject(getProject()).getOfsModelResource(URI.createURI("resource:///general/Domain.domain"));
			Assert.fail("Resource was found, but it should not exist!");
		} catch (ModelNotFoundException e) {
			// this is expected!
		}
	}
}
