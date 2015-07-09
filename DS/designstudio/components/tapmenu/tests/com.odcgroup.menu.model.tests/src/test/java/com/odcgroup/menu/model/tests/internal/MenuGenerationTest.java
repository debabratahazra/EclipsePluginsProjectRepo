package com.odcgroup.menu.model.tests.internal;

import static com.odcgroup.workbench.core.tests.util.TestTankResourcesTestUtil.loadTestTankResourceAsString;
import static org.eclipse.xtext.junit4.ui.util.IResourcesSetupUtil.waitForAutoBuild;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.ui.AbstractWorkbenchTest;
import org.eclipse.xtext.junit4.ui.util.JavaProjectSetupUtil;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.eclipse.xtext.junit4.validation.ValidationTestHelper;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import com.odcgroup.menu.generation.MenuGenerator;
import com.odcgroup.menu.model.MenuInjectorProvider;
import com.odcgroup.menu.model.MenuRoot;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoaderImpl;
import com.odcgroup.workbench.generation.cartridge.ng.SimplerEclipseResourceFileSystemAccess2;

/**
 * @author pkk
 */
@SuppressWarnings("restriction")
@RunWith(XtextRunner.class)
@InjectWith(MenuInjectorProvider.class)
public class MenuGenerationTest extends AbstractWorkbenchTest {
	
	private static String TEST_PROJECT = "project-models";
	private static String TEST_PROJECT_GEN = TEST_PROJECT + "-gen";
	
	private static String MENU_MODEL_1 = "/menu/Default/gen/MenuGen.menu";
	private static String MENU_MODEL_1_GENERATED = "oams-profiles/Default/activity/gen/menu/Menu.xml";
	private static String MENU_MODEL_1_EXPECTED_PAGEFLOW_URI = "/activity/aaa/pageflow/start/returnanalysis-pageflow-Return/Return";
	
	private static String MENU_MODEL_2 = "/menu/RelationShipManager/aaa/MenuGen.menu";
	private static String MENU_MODEL_2_GENERATED = "oams-profiles/RelationShipManager/activity/aaa/menu/Menu.xml";

	private static String MENU_MODEL_3 = "/menu/Default/gen/TestMenuShortcut.menu";
	private static String MENU_MODEL_3_GENERATED = "oams-profiles/Default/activity/gen/menu/Menu.xml";
	
	@Inject
	private ParseHelper<MenuRoot> parseHelper;
	@Inject
	private ValidationTestHelper validationHelper;
	@Inject 
	private MenuGenerator generator;
	
	private IProject modelGenProject;
	
	@Before
	@Override
	public void setUp() {
		try {
			super.setUp();
			IJavaProject createJavaProject = JavaProjectSetupUtil.createJavaProject(TEST_PROJECT_GEN);
			modelGenProject = createJavaProject.getProject();
			waitForAutoBuild();
		} catch (Throwable ex) {
			throw Exceptions.sneakyThrow(ex);
		}
	}
	
	@Test
	public void testMenuGenerationforTAP() throws Exception {
		
		IFolder outputFolder = doGeneration(MENU_MODEL_1);
		IFile genfile = outputFolder.getFile(MENU_MODEL_1_GENERATED);
		assertTrue(genfile.exists());	

		Document document = fetchGenDocument(genfile);
		assertNotNull(document);
		NodeList nodes = document.getElementsByTagName("menu");
		assertTrue(nodes.getLength() == 9);
		Element root = document.getDocumentElement();
		NamedNodeMap attributes = root.getAttributes();
		String rootname = attributes.getNamedItem("name").getNodeValue();
		assertTrue("menu.aaa.activity".equals(rootname));
		assertTrue(root.getChildNodes().getLength() == 3);
		attributes = nodes.item(7).getAttributes();
		String uri = attributes.getNamedItem("uri").getNodeValue();
		assertTrue(MENU_MODEL_1_EXPECTED_PAGEFLOW_URI.equals(uri));	
	}
    
   /**
    * test the menu folder generation under profilefoldername/activity/
    */
	@Test
	public void testDS5491ProfileMenuGenerationFolder() throws Exception { 
		
		IFolder outputFolder = doGeneration(MENU_MODEL_2);
		IFile genfile = outputFolder.getFile(MENU_MODEL_2_GENERATED);
		
		//Assert.assert generated file exist
		assertTrue(genfile.exists());
		//Assert.assert generated file location.
		assertEquals(MENU_MODEL_2_GENERATED, genfile.getFullPath().removeFirstSegments(3).toString());
	}
	
	@Test
	public void testMenuGenerationforShortcut() throws Exception {
		
		IFolder outputFolder = doGeneration(MENU_MODEL_3);
		IFile genfile = outputFolder.getFile(MENU_MODEL_3_GENERATED);
		assertTrue(genfile.exists());	
		
		Document document = fetchGenDocument(genfile);
		assertNotNull(document);
		NodeList nodes = document.getElementsByTagName("menu");
		assertTrue(nodes.getLength() == 3);
		Element root = document.getDocumentElement();
		NamedNodeMap attributes = root.getAttributes();
		String rootname = attributes.getNamedItem("name").getNodeValue();
		assertTrue("TestMenuShortcut".equals(rootname));
		assertTrue(root.getChildNodes().getLength() == 3);
		attributes = nodes.item(2).getAttributes();
		String shortcut = attributes.getNamedItem("keyboard-shortcut").getNodeValue();
		String expectedShortcut = "Ctrl+Alt+F";
		assertTrue(expectedShortcut.equals(shortcut));
	}	
	
	@Test
	public void testMenuGenerationforDisplayChildTabs() throws Exception {
		
		IFolder outputFolder = doGeneration(MENU_MODEL_3);
		IFile genfile = outputFolder.getFile(MENU_MODEL_3_GENERATED);
		assertTrue(genfile.exists());	
		
		Document document = fetchGenDocument(genfile);
		assertNotNull(document);
		NodeList nodes = document.getElementsByTagName("menu");
		assertTrue(nodes.getLength() == 3);
		Element root = document.getDocumentElement();
		NamedNodeMap attributes = root.getAttributes();
		String rootname = attributes.getNamedItem("name").getNodeValue();
		assertTrue("TestMenuShortcut".equals(rootname));
		assertTrue(root.getChildNodes().getLength() == 3);
		attributes = nodes.item(2).getAttributes();
		String displayChildTab = attributes.getNamedItem("children-displayed-as-tabs").getNodeValue();
		String expectedDisplayChildTab = "false";
		assertTrue(expectedDisplayChildTab.equals(displayChildTab));
	}
	
	
	protected IFolder doGeneration(String modelName) throws Exception {
		String content = loadTestTankResourceAsString(this.getClass(), modelName);
		MenuRoot menuRoot = parseHelper.parse(content);
		validationHelper.assertNoErrors(menuRoot);

		// replace the _syntetic: URI by a real URI so that the generator with work properly
		Resource resource = menuRoot.eResource();
		URI inputURI = URI.createURI("platform:/resource/"+TEST_PROJECT+modelName); 
		resource.setURI(inputURI);
		
		// define a model loader
		ResourceSet rs = menuRoot.eResource().getResourceSet();
		ModelLoaderImpl modelLoader = new ModelLoaderImpl(rs);
		
		// Create output folder.
		IFolder outputFolder = modelGenProject.getFolder("src/wui_block");
		outputFolder.create(true, true, null);
		IFileSystemAccess fsa = getFileSystemAccess(modelGenProject, outputFolder);
		
		// generate artifacts
		generator.doGenerate(inputURI, modelLoader, fsa);
		
		outputFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
		return outputFolder;
	}
	
	protected IFileSystemAccess getFileSystemAccess(IProject modelsProject, IFolder outputFolder) {
		SimplerEclipseResourceFileSystemAccess2 fsa = new SimplerEclipseResourceFileSystemAccess2();
		fsa.setProject(outputFolder.getProject());
		fsa.setOutputPath(outputFolder.getProjectRelativePath().toString());
		fsa.getOutputConfigurations().get(IFileSystemAccess.DEFAULT_OUTPUT).setCreateOutputDirectory(true);
		fsa.setMonitor(new NullProgressMonitor());
		return fsa;
	}
	
	protected Document fetchGenDocument(IFile xmlfile) throws Exception {
		DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();
		factory.setIgnoringElementContentWhitespace(true);
	    DocumentBuilder parser = factory.newDocumentBuilder();
	    Document doc = parser.parse(xmlfile.getContents());
		return doc;
	}
	

}
