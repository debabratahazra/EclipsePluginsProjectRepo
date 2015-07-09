package com.odcgroup.t24.mdf.generation.tests;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.mdf.OutputStreamFactory;
import com.odcgroup.mdf.build.FileOutputStreamFactory;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.utils.ConsoleMessageHandler;
import com.odcgroup.t24.mdf.generation.xls.T24ExcelWriter;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * @author atr
 */
public class T24ExcelDocGeneratorTest extends AbstractDSUnitTest {

	@Before
	public void setUp() {
		createModelsProject();
		copyResourceInModelsProject("domain/ds6070/AA_Account.domain");
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}

	@Test
	public void testDomainGeneration() throws CoreException, ModelNotFoundException, IOException, InvalidMetamodelVersionException { 
    	
		final String OUTPUT_FOLDER = "Documentation";
		getProject().getFolder(OUTPUT_FOLDER).create(true, true, null);
		
    	URI modelURI = URI.createURI("resource:///ds6070/AA_Account.domain");
    	IOfsModelResource modelResource = getOfsProject().getOfsModelResource(modelURI);

		String p = getProject().getFolder(OUTPUT_FOLDER).getLocation().toString();
    	OutputStreamFactory factory = new FileOutputStreamFactory(new File(p));
    	
		T24ExcelWriter writer = new T24ExcelWriter();
		writer.open();
		writer.setMessageHandler(new ConsoleMessageHandler());
		MdfDomain domain = (MdfDomain)modelResource.getEMFModel().get(0);
		writer.write(domain, Collections.<MdfDomain>emptyList(), factory);
		writer.close();
		
		// verify that we have 1 files in there: 
		File xlsFile = new File(p + "/domain/aa_account.xls");
		Assert.assertTrue("File do not exist : " + xlsFile.getAbsolutePath(), xlsFile.exists());
	}


}
