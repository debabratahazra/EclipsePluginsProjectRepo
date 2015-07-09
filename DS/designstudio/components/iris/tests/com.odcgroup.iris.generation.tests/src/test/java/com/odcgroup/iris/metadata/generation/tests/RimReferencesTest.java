package com.odcgroup.iris.metadata.generation.tests;

import static org.junit.Assert.assertFalse;

import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.odcgroup.iris.java.generation.IRISJavaGenerator;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.generation.tests.AbstractDSGenerationTest;

/**
 * @author yandenmatten
 */
public class RimReferencesTest extends AbstractDSGenerationTest {

	private static String[] HOTHOUSE_RIM = new String[] {
		"rim/hothouse/BPM.rim",
		"rim/hothouse/Composite.rim",
		"rim/hothouse/CustAcctEdges.rim",
		"rim/hothouse/CustomerEdges.rim",
		"rim/hothouse/Hothouse.rim",
		"rim/hothouse/Menu.rim",
		"rim/hothouse/Profile.rim",
	};
	
	
	@Before
	public void setUp() throws Exception {
		createModelAndGenProject();
		// TODO This should not be necessary.. there should be rim/ folder OOB in a std. new *-models project (RimProjectInitializer creates it) - why does this not work for this test? 
		IFolder folder = getProject().getFolder("rim");
        OfsCore.createFolder(folder);
        copyFromClasspathToModelsProject(HOTHOUSE_RIM);
	}
	
	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}

	@Ignore
	@Test
	public void testRimReferences() throws Throwable {
		activateCartridge(getProject(), IRISJavaGenerator.ID);
		Set<IFolder> outputs = generateAll(); 
		Assert.assertTrue(!outputs.isEmpty());
		
		String javaText = readFileContent(getGenProject(), "src/generated/iris/java/hothouse/Hothouse/rootResourceState.java");
		assertFalse("Java contains factory.getResourceState(\"\")... :-(" + javaText, javaText.contains("factory.getResourceState(\"\")"));
		assertFalse("Java contains \"\"... :-(" + javaText, javaText.contains("\"\""));
		
//		IFolder folder = getOutputFolder(getGenProject(), IRISJavaGenerator.ID);
//		test content of folder

		
		
		
		
		
//		
//		IRISJavaGenerator generator = new IRISJavaGenerator();
//		
//		IFolder outputFolder = getProject().getFolder("output");
//		outputFolder.create(true, true, null);
//		generator.run(getProject(), resources, outputFolder);
//		
//		
//		System.out.println("results here: " + outputFolder.getLocation().toOSString());
//		IFolder javaFolder = outputFolder.getFolder("RestbucksModel");
//		
//		Assert.assertTrue(javaFolder.exists());
//
//		IResource[] files = javaFolder.members();
//		Assert.assertTrue(files.length > 0);
	}

}
