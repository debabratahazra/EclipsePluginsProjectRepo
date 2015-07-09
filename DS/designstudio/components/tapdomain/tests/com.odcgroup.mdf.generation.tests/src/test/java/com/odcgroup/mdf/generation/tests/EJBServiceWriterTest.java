package com.odcgroup.mdf.generation.tests;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.mdf.generation.ServiceEJBGenerator;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;


public class EJBServiceWriterTest extends AbstractDSUnitTest {

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyResourceInModelsProject("domain/ds3682/DS3682.domain");
	}
	
	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testJavaDescriptorGenerator_DS3682() throws ModelNotFoundException, CoreException, IOException {
		IOfsModelResource modelResource = getOfsProject().getOfsModelResource(URI.createURI("resource:///ds3682/DS3682.domain"));
		
		ServiceEJBGenerator generator = new ServiceEJBGenerator();
		IFolder outputFolder = getProject().getFolder("output");
		outputFolder.create(true, true, null);
		IProgressMonitor monitor = new NullProgressMonitor();
		List<IStatus> nonOkStatuses = new ArrayList<IStatus>();
		generator.run(monitor, getProject(), Collections.singleton(modelResource), outputFolder, nonOkStatuses);
		outputFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
		
		InputStream is = outputFolder.getFile("META-INF/ejb-jar.xml").getContents();
		String contents = IOUtils.toString(is);
		IOUtils.closeQuietly(is);
		contents = StringUtils.deleteWhitespace(contents);
		contents = StringUtils.remove(contents, '\n');
		contents = StringUtils.remove(contents, '\r');
		
		Assert.assertTrue(contents.contains("<method>" +
										"<ejb-name>DS3682_MyDataSetService</ejb-name>" +
										"<method-intf>Home</method-intf>" +
										"<method-name>*</method-name>" +
									"</method>" +
									"<trans-attribute>Supports</trans-attribute>"));
		Assert.assertTrue(contents.contains("<method>" +
										"<ejb-name>DS3682_MyDataSetService</ejb-name>" +
										"<method-intf>Remote</method-intf>" +
										"<method-name>*</method-name>" +
									"</method>" +
									"<trans-attribute>Required</trans-attribute>"));
		Assert.assertTrue(contents.contains("<method>" + 
										"<ejb-name>DS3682_MyDataSetService</ejb-name>" + 
										"<method-intf>Remote</method-intf>" + 
										"<method-name>validateAll</method-name>" +
									"</method>" +
									"<trans-attribute>Supports</trans-attribute>"));
	}
}
