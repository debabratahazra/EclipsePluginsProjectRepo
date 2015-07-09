package com.odcgroup.pageflow.generation.tests.ocs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.odcgroup.pageflow.generation.PageflowGenerationCore;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;

/**
 * @author phanikumark
 *
 */
public class PageflowGenerationTest extends BasePageflowGenerationTest {

	
	private static final String DS5266PAGEFLOW_DIR = "resources/test-tank-models/pageflow/ds5266";
	private static final String DS5246PAGEFLOW_DIR = "resources/test-tank-models/pageflow/subpageflowtests";
	private static final String DS5286PAGEFLOW_DIR = "resources/test-tank-models/pageflow/ds5286";
	private static final String DS5341PAGEFLOW_DIR = "resources/test-tank-models/pageflow/ds5341"; 
	private static final String DS5314PAGEFLOW_DIR = "resources/test-tank-models/pageflow/ds5314"; 
	private static final String DS5379PAGEFLOW_DIR = "resources/test-tank-models/pageflow/ds5379"; 
	private static final String DS5406PAGEFLOW_DIR = "resources/test-tank-models/pageflow/ds5406"; 
	private static final String DS5444PAGEFLOW_DIR = "resources/test-tank-models/pageflow/ds5444"; 
	private static final String ALLSCENARIOS_DIR = "resources/test-tank-models/pageflow/allscenarios"; 
	
	private static final String DS5266_TARGETFLDR = "pageflow/ds5266/";	
	private static final String DS5286_TARGETFLDR = "pageflow/ds5286/";	
	private static final String DS5246_TARGETFLDR = "pageflow/subpageflowtests/";	
	private static final String DS5341_TARGETFLDR = "pageflow/ds5341/";	
	private static final String DS5314_TARGETFLDR = "pageflow/ds5314/";	
	private static final String DS5379_TARGETFLDR = "pageflow/ds5379/";	
	private static final String DS5406_TARGETFLDR = "pageflow/ds5406/";	
	private static final String DS5444_TARGETFLDR = "pageflow/ds5444/";
	private static final String ALLSCENARIOS_TARGETFLDR = "pageflow/allscenarios/";
	
	private static final String ROOT_OCSOUTPUT_DIR = "output";
	private static final String OCSOUTPUT_DIR = ROOT_OCSOUTPUT_DIR+ "/META-INF/config";
	private static final String GEN_DS5246PAGEFLOW = "test-pageflow-Test.xml";
	private static final String GEN_DS5266PAGEFLOW = "StrategyAdministration-pageflow-Allocation.xml";
	private static final String GEN_DS5286PAGEFLOW = "ds5286-pageflow-Parent.xml";
	private static final String GEN_DS5341PAGEFLOW = "portfolio-pageflow-PortfolioAdministration.xml";
	private static final String GEN_DS5314PAGEFLOW = "orderlist-pageflow-OrderListAll.xml";
	private static final String GEN_DS5379PAGEFLOW = "ds5379-pageflow-ParentPf.xml";
	private static final String GEN_DS5406PAGEFLOW = "wui-cdm-pageflow-config.xml";
	private static final String GEN_DS5444PAGEFLOW = "ds5444-pageflow-EditingSessionDS5444.xml";
	private static final String GEN_FROMVSPAGEFLOW = "allscenarios-pageflow-FromViewState.xml";
	private static final String GEN_UC01PAGEFLOW = "allscenarios-pageflow-UseCase1.xml";
	private static final String GEN_UC02PAGEFLOW = "allscenarios-pageflow-UseCase2.xml";
	private static final String GEN_UC03PAGEFLOW = "allscenarios-pageflow-UseCase3.xml";
	private static final String GEN_UC04PAGEFLOW = "allscenarios-pageflow-UseCase4.xml";
	private static final String GEN_UC05PAGEFLOW = "allscenarios-pageflow-UseCase5.xml";
	private static final String GEN_UC06PAGEFLOW = "allscenarios-pageflow-UseCase6.xml";
	private static final String GEN_UC07PAGEFLOW = "allscenarios-pageflow-UseCase7.xml";
	private static final String GEN_UC08PAGEFLOW = "allscenarios-pageflow-UseCase8.xml";
	private static final String GEN_UC09PAGEFLOW = "allscenarios-pageflow-UseCase9.xml";
	private static final String GEN_UC10PAGEFLOW = "allscenarios-pageflow-UseCase10.xml";
	private static final String GEN_UC11PAGEFLOW = "allscenarios-pageflow-UseCase11.xml";
	private static final String GEN_UC12PAGEFLOW = "allscenarios-pageflow-UseCase12.xml";
	private static final String GEN_UC13PAGEFLOW = "allscenarios-pageflow-UseCase13.xml";
	private static final String GEN_UC14PAGEFLOW = "allscenarios-pageflow-UseCase14.xml";
	private static final String GEN_UC15PAGEFLOW = "allscenarios-pageflow-UseCase15.xml";
	private static final String GEN_UC16PAGEFLOW = "allscenarios-pageflow-UseCase16.xml";
	private static final String GEN_UC17PAGEFLOW = "allscenarios-pageflow-UseCase17.xml";
	private static final String GEN_UC18PAGEFLOW = "allscenarios-pageflow-UseCase18.xml";
	private static final String GEN_UC19PAGEFLOW = "allscenarios-pageflow-UseCase19.xml";
	private static final String GEN_UC20PAGEFLOW = "allscenarios-pageflow-UseCase20.xml";
	private static final String GEN_UC21PAGEFLOW = "allscenarios-pageflow-UseCase21.xml";
	private static final String GEN_UC22PAGEFLOW = "allscenarios-pageflow-UseCase22.xml";
	private static final String GEN_UC23PAGEFLOW = "allscenarios-pageflow-UseCase23.xml";
	private static final String GEN_UC24PAGEFLOW = "allscenarios-pageflow-UseCase24.xml";
	private static final String GEN_UC25PAGEFLOW = "allscenarios-pageflow-UseCase25.xml";
	private static final String GEN_UC26PAGEFLOW = "allscenarios-pageflow-UseCase26.xml";
	
	private static final String OCSTEMPLATE_DIR = "resources/templates/pageflow";
	
	private IOfsProject ofsProject = null;
	private IProject project;	

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		ofsProject = createModelsProject();
		project = ofsProject.getProject();
		project.open(null);
		project.getFullPath().append(OCSOUTPUT_DIR);
		project.getFolder(ROOT_OCSOUTPUT_DIR).create(true, true, null);		
	}
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	protected void assertTransformation(URI modelUri, String filename,
			final String... skippedXPaths) throws ModelNotFoundException,
			IOException, InvalidMetamodelVersionException, CoreException,
			SAXException, URISyntaxException {

		File genFile = project.getFolder(OCSOUTPUT_DIR+"/"+filename).getLocation().toFile();
		String generatedXml = FileUtils.readFileToString(genFile);
		
		final URL url = FileLocator.find(
				PageflowGenerationCore.getDefault().getBundle(), 
				new Path(OCSTEMPLATE_DIR + "/" + filename), 
				null);
		final File expectedFile = new File(FileLocator.toFileURL(url).toURI());
		String expectedXml = FileUtils.readFileToString(expectedFile);
		
		assertXml("Unexpected difference with the generation of "
				+ modelUri, generatedXml, expectedXml, skippedXPaths);
	}
	
	/**
	 * DS-5246
	 * 
	 * @throws ModelNotFoundException
	 * @throws InvalidMetamodelVersionException 
	 * @throws IOException 
	 * @throws URISyntaxException 
	 */
	@Test
	public void testSubPageFlowInitTransitionGeneration() throws Exception {
		
		copyPageflowResourcesToModelProject(ofsProject, DS5246PAGEFLOW_DIR, DS5246_TARGETFLDR);
		
		boolean success = runPageflowGeneration(getOfsProject(), ROOT_OCSOUTPUT_DIR);
		Assert.assertTrue(success);

		testGeneration(GEN_DS5246PAGEFLOW, "wf:state", "key");
		
		assertTransformation(
				URI.createURI("resource:///subpageflowtests/Test.pageflow"),
				GEN_DS5246PAGEFLOW,
				"/config[1]/comment()[1]");
	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void testDS5266PageflowGeneration() throws Exception {	

		copyPageflowResourcesToModelProject(ofsProject, DS5246PAGEFLOW_DIR, DS5246_TARGETFLDR);
		copyPageflowResourcesToModelProject(ofsProject, DS5266PAGEFLOW_DIR, DS5266_TARGETFLDR);

		boolean success = runPageflowGeneration(getOfsProject(), ROOT_OCSOUTPUT_DIR);
		Assert.assertTrue(success);

		testGeneration(GEN_DS5266PAGEFLOW, "wf:state", "key");		
		
		assertTransformation(
				URI.createURI("resource:///ds5266/StrategyAdministration.pageflow"),
				GEN_DS5266PAGEFLOW,
				"/config[1]/comment()[1]");

	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void testDS5286PageflowGeneration() throws Exception {	
		
		copyPageflowResourcesToModelProject(ofsProject, DS5286PAGEFLOW_DIR, DS5286_TARGETFLDR);

		boolean success = runPageflowGeneration(getOfsProject(), ROOT_OCSOUTPUT_DIR);
		Assert.assertTrue(success);

		testGeneration(GEN_DS5286PAGEFLOW, "wf:transition", "to-state");		

		assertTransformation(
				URI.createURI("resource:///ds5286/Parent.pageflow"),
				GEN_DS5286PAGEFLOW,
				"/config[1]/comment()[1]");
	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void testDS5341PageflowGeneration() throws Exception {	
		
		copyPageflowResourcesToModelProject(ofsProject, DS5341PAGEFLOW_DIR, DS5341_TARGETFLDR);
		
		boolean success = runPageflowGeneration(getOfsProject(), ROOT_OCSOUTPUT_DIR);
		Assert.assertTrue(success);

		testGeneration(GEN_DS5341PAGEFLOW, "wf:result", "state");

		assertTransformation(
				URI.createURI("resource:///ds5341/PortfolioAdministration.pageflow"),
				GEN_DS5341PAGEFLOW,
				"/config[1]/comment()[1]");
	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void testDS5314PageflowGeneration() throws Exception {	
		
		copyPageflowResourcesToModelProject(ofsProject, DS5314PAGEFLOW_DIR, DS5314_TARGETFLDR);

		boolean success = runPageflowGeneration(getOfsProject(), ROOT_OCSOUTPUT_DIR);
		Assert.assertTrue(success);

		testGeneration(GEN_DS5314PAGEFLOW, "wf:transition", "name");

		assertTransformation(
				URI.createURI("resource:///ds5314/OrderListAll.pageflow"),
				GEN_DS5314PAGEFLOW,
				"/config[1]/comment()[1]");

	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void testDS5379PageflowGeneration() throws Exception {	
		
		copyPageflowResourcesToModelProject(ofsProject, DS5379PAGEFLOW_DIR, DS5379_TARGETFLDR);
		
		boolean success = runPageflowGeneration(getOfsProject(), ROOT_OCSOUTPUT_DIR);
		Assert.assertTrue(success);

		testGeneration(GEN_DS5379PAGEFLOW, "wf:transition", "to-state");

		assertTransformation(
				URI.createURI("resource:///ds5379/ParentPf.pageflow"),
				GEN_DS5379PAGEFLOW,
				"/config[1]/comment()[1]");

	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void testDS5406PageflowGeneration() throws Exception {	

		copyPageflowResourcesToModelProject(ofsProject, DS5406PAGEFLOW_DIR, DS5406_TARGETFLDR);
		
		boolean success = runPageflowGeneration(getOfsProject(), ROOT_OCSOUTPUT_DIR);
		Assert.assertTrue(success);

		testGeneration(GEN_DS5406PAGEFLOW, "wf:transition", "to-state");

		assertTransformation(
				URI.createURI("resource:///ds5406/addCrFlow.pageflow"),
				GEN_DS5406PAGEFLOW,
				"/config[1]/comment()[1]");

	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testDS5444PageflowGeneration() throws Exception {	

		copyPageflowResourcesToModelProject(ofsProject, DS5444PAGEFLOW_DIR, DS5444_TARGETFLDR);
		
		boolean success = runPageflowGeneration(getOfsProject(), ROOT_OCSOUTPUT_DIR);
		Assert.assertTrue(success);

		testGeneration(GEN_DS5444PAGEFLOW, "wf:property", "name");

		assertTransformation(
				URI.createURI("resource:///ds5444/EditingSessionDS5444.pageflow"),
				GEN_DS5444PAGEFLOW,
				"/config[1]/comment()[1]");

	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void testAllScenariosPageflowGeneration() throws Exception {	

		copyPageflowResourcesToModelProject(ofsProject, ALLSCENARIOS_DIR, ALLSCENARIOS_TARGETFLDR);
		
		boolean success = runPageflowGeneration(getOfsProject(), ROOT_OCSOUTPUT_DIR);
		Assert.assertTrue(success);

		testGeneration(GEN_FROMVSPAGEFLOW,"wf:state", "key");
		testGeneration(GEN_FROMVSPAGEFLOW,"wf:result", "state");
		testGeneration(GEN_FROMVSPAGEFLOW,"wf:process", "class-name");

		assertTransformation(
				URI.createURI("resource:///allscenarios/DS5289IsIdempotent.pageflow"),
				GEN_FROMVSPAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC01PAGEFLOW,"wf:transition", "is-idempotent");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/FromViewState.pageflow"),
				GEN_FROMVSPAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC01PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC01PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase1.pageflow"),
				GEN_UC01PAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC02PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC02PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase2.pageflow"),
				GEN_UC02PAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC03PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC03PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase3.pageflow"),
				GEN_UC03PAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC04PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC04PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase4.pageflow"),
				GEN_UC04PAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC05PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC05PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase5.pageflow"),
				GEN_UC05PAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC06PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC06PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase6.pageflow"),
				GEN_UC06PAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC07PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC07PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase7.pageflow"),
				GEN_UC07PAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC08PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC08PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase8.pageflow"),
				GEN_UC08PAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC09PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC09PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase9.pageflow"),
				GEN_UC09PAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC10PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC10PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase10.pageflow"),
				GEN_UC10PAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC11PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC11PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase11.pageflow"),
				GEN_UC11PAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC12PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC12PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase12.pageflow"),
				GEN_UC12PAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC13PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC13PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase13.pageflow"),
				GEN_UC13PAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC14PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC14PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase14.pageflow"),
				GEN_UC14PAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC15PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC15PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase15.pageflow"),
				GEN_UC15PAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC16PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC16PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase16.pageflow"),
				GEN_UC16PAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC17PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC17PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase17.pageflow"),
				GEN_UC17PAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC18PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC18PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase18.pageflow"),
				GEN_UC18PAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC19PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC19PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase19.pageflow"),
				GEN_UC19PAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC20PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC20PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase20.pageflow"),
				GEN_UC20PAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC21PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC21PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase21.pageflow"),
				GEN_UC21PAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC22PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC22PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase22.pageflow"),
				GEN_UC22PAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC23PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC23PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase23.pageflow"),
				GEN_UC23PAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC24PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC24PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase24.pageflow"),
				GEN_UC24PAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC25PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC25PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase25.pageflow"),
				GEN_UC25PAGEFLOW,
				"/config[1]/comment()[1]");

		testGeneration(GEN_UC26PAGEFLOW,"wf:state", "key");
		testGeneration(GEN_UC26PAGEFLOW,"wf:process", "class-name");
		
		assertTransformation(
				URI.createURI("resource:///allscenarios/UseCase26.pageflow"),
				GEN_UC26PAGEFLOW,
				"/config[1]/comment()[1]");

	}
	
	/**
	 * @param genPageflow
	 * @throws ModelNotFoundException
	 * @throws IOException
	 * @throws InvalidMetamodelVersionException
	 * @throws URISyntaxException
	 */
	private void testGeneration(String genPageflow, String element, String attribute) throws 
	ModelNotFoundException, IOException, InvalidMetamodelVersionException, URISyntaxException {
		
		// now validate the generated files
		IFolder fldr = getProject().getFolder(OCSOUTPUT_DIR);
		File dir = fldr.getLocation().toFile();
		Assert.assertTrue(dir.exists() && dir.isDirectory());
		boolean filefound = false;
		for (File file : dir.listFiles()) {
			if (genPageflow.equals(file.getName())) {
				final URL url = FileLocator.find(PageflowGenerationCore
						.getDefault().getBundle(), new Path(OCSTEMPLATE_DIR + "/"
						+ file.getName()), null);
				final File template = new File(FileLocator.toFileURL(url).toURI());
					assertEquals(
							new InputSource(new FileInputStream(template)),
							new InputSource(new FileInputStream(project
									.getFolder(OCSOUTPUT_DIR)
									.getFile(file.getName()).getLocation()
									.toFile())), element, attribute);
				filefound = true;
			}
		}
		if (!filefound) {
			Assert.fail("Generated pageflow "+genPageflow+" not found!");
		}
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest#getOfsProject()
	 */
	public IOfsProject getOfsProject() {
		return ofsProject;
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest#getProject()
	 */
	public IProject getProject() {
		return project;
	}

	
	/**
	 * @param doc1
	 * @param doc2
	 * @param element
	 * @param attribute
	 */
	protected void checkDocuments(Document doc1, Document doc2, String element, String attribute) {
		checkXML(doc1, doc2, element, attribute);	
	}
	
	/**
	 * checks the equality of two documents
	 * 
	 * @param src1
	 * @param src2
	 * @param element
	 * @param attribute
	 */
	protected void assertEquals(InputSource src1, InputSource src2, String element, String attribute) {
		if (src1 != src2) {
			try {
				DocumentBuilder builder = getDocumentBuilderFactory()
						.newDocumentBuilder();
				Document doc1 = builder.parse(src1);
				Document doc2 = builder.parse(src2);
				checkDocuments(doc1, doc2, element, attribute);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void checkDocuments(Document doc1, Document doc2) {		
	}



}
