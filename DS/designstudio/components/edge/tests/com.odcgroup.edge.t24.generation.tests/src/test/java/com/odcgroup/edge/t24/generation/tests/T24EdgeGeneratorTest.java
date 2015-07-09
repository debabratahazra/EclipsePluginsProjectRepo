package com.odcgroup.edge.t24.generation.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.edge.t24.generation.T24EdgeGenerator;
import com.odcgroup.t24.version.VersionDSLInjectorProvider;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelResourceLookup;
import com.odcgroup.workbench.generation.tests.AbstractDSGenerationTest;

/**
 * Test for T24EdgeGenerator.
 * 
 * This test MUST be run as an OSGi JUnit Plug-In Test.
 * 
 * @author Michael Vorburger, for Simon File.
 */
@RunWith(XtextRunner.class)
@InjectWith(VersionDSLInjectorProvider.class)
@Ignore
public class T24EdgeGeneratorTest extends AbstractDSGenerationTest {

	// TODO FIXME later; this doesn't actually work yet.. I had given up on this in initial and moved on to the simpler EdgeMapperTest instead 
	
	private IOfsProject ofsProject;

	@Before
	public void setUp() {
		ofsProject = createModelAndGenProject();
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	@Test
	public void testDoGenerate() throws Exception {
		// TODO copyResourceInModelsProject() ... from test-tank (or fix test-tank to allow from this project?)
		
		Collection<IOfsModelResource> modelResources = new HashSet<IOfsModelResource>();
		ModelResourceLookup lookup = new ModelResourceLookup(ofsProject, OfsCore.getRegisteredModelNames());
		modelResources.addAll(lookup.getAllOfsModelResources(IOfsProject.SCOPE_ALL - IOfsProject.SCOPE_DEPENDENCY));
		
		T24EdgeGenerator generator = new T24EdgeGenerator();
		IFolder outputFolder = null; // not important for T24EdgeGenerator 
		IProgressMonitor monitor = new NullProgressMonitor();
		List<IStatus> nonOkStatuses = new ArrayList<IStatus>();
		generator.run(monitor, ofsProject.getProject(), modelResources, outputFolder, nonOkStatuses);

// FIXME: Mappers now write to file based on version name, need to bring back a list of projects to test (or search)
		
//		IFile iFile = getGenProject().getFile(T24EdgeGenerator.GEN_IFP_TMP);
//		InputStream is = iFile.getContents(true);
//		String ifpAsString = CharStreams.toString(new InputStreamReader(is, Charsets.UTF_8));
//		Closeables.closeQuietly(is);
//
//		// Just for convenience..
//		// System.out.println(ifpAsString);
//
//		// A virtually empty *.ifp is >500, so first make sure we wrote anything really
//		assertTrue(ifpAsString.length() > 500);
//		
//		// If we wrote ANYTHING of interest, it should be a little bigger
//		assertTrue(ifpAsString.length() > 800);
		
		// TODO check that name of version appears in it?
		// more intelligent assertions TBD...		
	}

}
