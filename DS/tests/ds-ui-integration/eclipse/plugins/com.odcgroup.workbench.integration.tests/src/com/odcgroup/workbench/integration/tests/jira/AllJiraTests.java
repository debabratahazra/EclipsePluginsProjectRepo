package com.odcgroup.workbench.integration.tests.jira;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.io.IOException;
import java.util.Date;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.MySWTBotTreeItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.ui.IEditorPart;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.dsl.resources.AbstractDSLResource;
import com.odcgroup.workbench.integration.tests.util.pageobjects.MenuPageObject;
import com.odcgroup.workbench.integration.tests.util.pageobjects.OdysseyNavigatorPageObject;
import com.odcgroup.workbench.integration.tests.util.pageobjects.PackageExplorerPageObject;
import com.odcgroup.workbench.ui.helper.OfsEditorUtil;
import com.odcgroup.workbench.tap.validation.ValidationUtil;


@RunWith(SWTBotJunit4ClassRunner.class)
public class AllJiraTests {

	private static final String BUSINESS_MODELS_SVN_URL = "https://lausvn.oams.com/svn/devel/products/OCS/tags/5.1.0/business-models/";
	private static final String CDM_MODELS_SVN_URL = BUSINESS_MODELS_SVN_URL + "cdm/cdm-wui-models";
	private static final String PMS_MODELS_SVN_URL = BUSINESS_MODELS_SVN_URL + "pms/pms-models";
	private static final String PMS_MODELS_GEN_SVN_URL = BUSINESS_MODELS_SVN_URL + "pms/pms-models-gen";

	private static SWTWorkbenchBot bot = new SWTWorkbenchBot();

	// PageObjects that are used in these tests
	private static MenuPageObject menu = new MenuPageObject();
	private static OdysseyNavigatorPageObject on = new OdysseyNavigatorPageObject();
	private static PackageExplorerPageObject pe = new PackageExplorerPageObject();
	
	@Before
	public void setUp() throws Exception {
		SWTBotPreferences.TIMEOUT = 60000;
	}

	@After
	public void tearDown() throws Exception {
		for(IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			project.delete(true, null);
		}
	}
	
	@Test
	public void doSVNCheckout_DS3591andDS3525() throws CoreException {
		menu.checkoutProjectFromSVN(CDM_MODELS_SVN_URL);

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("cdm-wui-models");

	    // test DS-3591
		long startTime = new Date().getTime();
		project.build(IncrementalProjectBuilder.FULL_BUILD, null);
		try {
			Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		long endTime = new Date().getTime();
		assertTrue(String.format("Checkout took %.2f s", (endTime-startTime)/1000f), endTime < startTime + 20000L);
		
		// now test DS-3525
		IMarker[] vrMarkers = project.findMarkers("de.visualrules.validation.validationmarker", true, IResource.DEPTH_INFINITE);
		assertEquals(0, vrMarkers.length);
		project.delete(true, null);
	}
	@Test
	@Ignore
	public void validateModelsOptionAvailableInPackageExplorerOdysseyMenu_DS3513() throws Exception {
		menu.createNewModelProject("ds3513-models");
		menu.openPerspective("Java");
		SWTBotTreeItem peNode = pe.selectNode(new Path("ds3513-models"));
		peNode.contextMenu("Validate Models").click();
		
		menu.openPerspective("Temenos (default)");
		
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("ds3513-models");
		project.delete(true, null);
		
		IProject projectGen = ResourcesPlugin.getWorkspace().getRoot().getProject("ds3513-models-gen");
		projectGen.delete(true, null);
		
	}
	
	@Test
	@Ignore
	public void createRuleOnNonRuleProject_DS3814() throws CoreException {
		menu.checkoutProjectFromSVN(PMS_MODELS_SVN_URL);
		on.customizeModel(new Path("ds3814-custo-models/Domains/datasets/formats/valuation/valuation"));
		SWTBotEditor domainEditor = bot.editorByTitle("valuation.domain");
		SWTBotTree editorTree = domainEditor.bot().tree();
		SWTBotTreeItem item = editorTree.getTreeItem("AAADSValuation");
		MySWTBotTreeItem node = new MySWTBotTreeItem(item.getNode("ValuationDetailledList").widget);
		node.contextMenu("New", 2).click();
		SWTBotShell dialog = bot.shell("Cannot create rule");
		dialog.bot().button("No").click();
		domainEditor.close();
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("ds3814-custo-models");
		project.delete(true, null);
	}

	@Test
	public void openEditorMoreThanOnce_DS4336() throws CoreException {
		menu.checkoutProjectFromSVN(PMS_MODELS_SVN_URL);
		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("pms-models");

		Runnable runnable = new Runnable() {
			public void run() {
				URI uri = URI.createURI("resource:///datasets/entities/domain.domain");
				IEditorPart editor1 = OfsEditorUtil.openEditor(OfsCore.getOfsProject(project), uri);
				assertNotNull(editor1);
				IEditorPart editor2 = OfsEditorUtil.openEditor(OfsCore.getOfsProject(project), uri);
				assertEquals(editor1, editor2);
			}
		};

		Display.getDefault().syncExec(runnable);

		project.delete(true, null);
		ResourcesPlugin.getWorkspace().getRoot().getProject("pms-models-gen").delete(true, null);
	}
	
	@Test
	@Ignore // Ignored due to DS-4813
	public void removalOfParseResult_DS4352() throws ModelNotFoundException, CoreException, IOException, InvalidMetamodelVersionException {
		menu.checkoutProjectFromSVN(PMS_MODELS_SVN_URL);
		menu.checkoutProjectFromSVN(PMS_MODELS_GEN_SVN_URL);
		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("pms-models");

		// let's load a resource
		URI uri = URI.createURI("resource:///datasets/entities/domain.domain");
		IOfsModelResource modelResource = OfsCore.getOfsProject(project).getOfsModelResource(uri);
		
		// make sure we do not have errors on the resource
		IStatus result = ValidationUtil.validate(modelResource, null, false);
		assertNotNull(result);
		assertTrue("domain.domain file has errors: " + result.getMessage(), result.isOK());
		
		// now make sure that the loaded resource does not contain the parse result anymore
		AbstractDSLResource resource = (AbstractDSLResource) modelResource.getEMFModel().get(0).eResource();
		assertNull(resource.getParseResult());
		
		project.delete(true, null);
		ResourcesPlugin.getWorkspace().getRoot().getProject("pms-models-gen").delete(true, null);
	}
	
	@Test
	@Ignore
	public void docGenerationOnCdm() throws CoreException {
		menu.checkoutProjectFromSVN(CDM_MODELS_SVN_URL);
		on.runDocGeneration("cdm-wui-models");
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("cdm-wui-models");
		IFolder docuFolder = project.getFolder("Documentation/Doc");
		docuFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
		assertTrue(docuFolder.getFile("Domain_cdmapp.xls").exists());
		assertTrue(docuFolder.getFile("Translation.xls").exists());
	}

	@Test
	public void linkEditorButtonSelected_DS4597() {
		SWTBotView navigatorView = bot.viewByTitle("DS Navigator");
		final SWTBotToolbarButton linkButton = navigatorView.toolbarButton("Link with Editor");
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				assertTrue("Link with Editor button is not selected!", linkButton.widget.getSelection());
			}
		});
	}
	
}
