package com.odcgroup.t24.server.external.ui.tests.builder;

import java.io.File;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.odcgroup.t24.server.external.ui.T24ServerUIExternalCore;
import com.odcgroup.t24.server.external.ui.builder.T24DeployBuilder;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;


public class T24DeployBuilderTest extends AbstractDSUnitTest{

	private T24DeployBuilder deployBuilder;
	IProject project;	

	@Before
	public void setUp() throws Exception {
    	IOfsProject ofsProject = createModelsProject();
    	// create output folder for pageflow gen
		project = ofsProject.getProject();
		project.open(null);
		deployBuilder = new T24DeployBuilder();
	}
	
	@After
	protected void tearDown() {
		deleteModelsProjects();
	}
	
	public void _testIsTargetFileUpdateOfSource_ReturnsFalse_IfTargetIsNewerThatSource() throws Exception {
		
		IFolder folder = project.getFolder(".gen");
		folder.create(true, true, new NullProgressMonitor());
		
		final URL url = FileLocator.find(T24ServerUIExternalCore
				.getDefault().getBundle(), new Path("src/test/resources" + "/"
				+ "sample.xml"), null);
		final File sample = new File(FileLocator.toFileURL(url).toURI());
		File destFile = new File(folder.getLocation() + "\\sample.xml");
		if(!destFile.exists()){
			destFile.createNewFile();
		}
		FileUtils.copyFile(sample, destFile);
		
		
		deployBuilder.build(IncrementalProjectBuilder.FULL_BUILD, null, new NullProgressMonitor());

		Assert.assertFalse(FileUtils.contentEquals(sample, destFile));
		
	}

}

