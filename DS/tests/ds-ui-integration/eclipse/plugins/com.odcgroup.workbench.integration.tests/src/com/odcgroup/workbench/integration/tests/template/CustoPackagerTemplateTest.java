package com.odcgroup.workbench.integration.tests.template;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.filter.Filter;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.ocs.packager.launcher.PackagerLauncherHelper;
import com.odcgroup.workbench.integration.tests.AbstractSWTBotTest;
import com.odcgroup.workbench.integration.tests.util.pageobjects.MenuPageObject;
import com.odcgroup.workbench.integration.tests.util.pageobjects.OdysseyNavigatorPageObject;

@RunWith(SWTBotJunit4ClassRunner.class)
public class CustoPackagerTemplateTest {
	
	private static final String CUSTO_PMS_PACKAGER = "custo-pms-packager";

	// PageObjects that are used in these tests
	private static MenuPageObject menu = new MenuPageObject();
	private static OdysseyNavigatorPageObject on = new OdysseyNavigatorPageObject();

	private static boolean oldOpenGeneratedCustoPackageAtTheEnd;
	
	@BeforeClass
	public static void setUp() throws Exception {
		// Avoid opening the windows explorer as it causes
		// file locking problem during the workspace clean up
		oldOpenGeneratedCustoPackageAtTheEnd = PackagerLauncherHelper.getHelper().isOpenGeneratedCustoPackageAtTheEnd();
		PackagerLauncherHelper.getHelper().setOpenGeneratedCustoPackageAtTheEnd(false);
	}

	@AfterClass
	public static void tearDown() throws Exception {
		SWTWorkbenchBot bot = new SWTWorkbenchBot();
		try {
			bot.button("Cancel").click();
		} catch (WidgetNotFoundException e) {
			// Ignore it as it is only to make the other tests working in case of failure
		}
		PackagerLauncherHelper.getHelper().setOpenGeneratedCustoPackageAtTheEnd(oldOpenGeneratedCustoPackageAtTheEnd);
		AbstractSWTBotTest.deleteProjects();
	}


	@Test
	public void createCustoPmsPackager() throws Exception {
		// Create the packager
		menu.createNewTemplateProject(CUSTO_PMS_PACKAGER, "custo");
		IProject packagerProject = ResourcesPlugin.getWorkspace().getRoot().getProject(CUSTO_PMS_PACKAGER);

		// Trick: remove the pms-custo-models (& gen) from the packager module pom
		// (The packager on a full pms-custo-models is tested in another plan)
		removeModelAndGenFromModulePom(packagerProject);
		
		// And launch it (and wait until it is finished
		on.createCustoPackage(CUSTO_PMS_PACKAGER);
		
		// Test if the zip is available
		File[] zipFiles = getGeneratedZips(packagerProject);
		
		Assert.assertEquals("One custo packager zip should be generated", 1, zipFiles.length);
	}
	
	@Test
	public void attemptToCreateCustoPmsPackagerWithWrongParameters() {
		// Given
		SWTWorkbenchBot bot = new SWTWorkbenchBot();
		Map<String,String> params = new HashMap<String,String>();
		
		// When
		params.put("custo-name", "abc def");
		menu.createNewTemplateProject_openWizard();
		menu.createNewTemplateProject_selectAndFillParams(CUSTO_PMS_PACKAGER, params);
		
		// Then
		Assert.assertFalse("Finish button should be disabled", bot.button("Finish").isEnabled());
		
		// When
		params.put("custo-name", "abc-def");
		menu.createNewTemplateProject_selectAndFillParams(CUSTO_PMS_PACKAGER, params);
		
		// Then
		Assert.assertTrue("Finish button should be enabled", bot.button("Finish").isEnabled());
		
		// When
		params.put("custo-name", "abcdef-");
		menu.createNewTemplateProject_selectAndFillParams(CUSTO_PMS_PACKAGER, params);
		
		// Then
		Assert.assertFalse("Finish button should be disabled", bot.button("Finish").isEnabled());
		
		// When
		params.put("custo-name", "1234");
		menu.createNewTemplateProject_selectAndFillParams(CUSTO_PMS_PACKAGER, params);
		
		// Then
		Assert.assertTrue("Finish button should be enabled", bot.button("Finish").isEnabled());
		
		// When
		params.put("custo-name", "-abcdef");
		menu.createNewTemplateProject_selectAndFillParams(CUSTO_PMS_PACKAGER, params);
		
		// Then
		Assert.assertFalse("Finish button should be disabled", bot.button("Finish").isEnabled());
		
		// When
		params.put("custo-name", "ABC");
		menu.createNewTemplateProject_selectAndFillParams(CUSTO_PMS_PACKAGER, params);
		
		// Then
		Assert.assertTrue("Finish button should be enabled", bot.button("Finish").isEnabled());
		
		// When
		params.put("custo-name", "");
		menu.createNewTemplateProject_selectAndFillParams(CUSTO_PMS_PACKAGER, params);
		
		// Then
		Assert.assertFalse("Finish button should be disabled", bot.button("Finish").isEnabled());
		
		// When
		params.put("custo-name", "ABC123");
		menu.createNewTemplateProject_selectAndFillParams(CUSTO_PMS_PACKAGER, params);
		
		// Then
		Assert.assertTrue("Finish button should be enabled", bot.button("Finish").isEnabled());
		
		// Clean up: close the wizard
		bot.button("Cancel").click();
	}

	private void removeModelAndGenFromModulePom(IProject packagerProject) throws IOException, JDOMException, CoreException {
		IFile modulePomIFile = packagerProject.getFile("module-pom/pom.xml");
		File modulePom = modulePomIFile.getLocation().toFile();
		
		Document document = new SAXBuilder().build(modulePom);
		Element root = document.getRootElement();
		Namespace ns = root.getNamespace();
		Element modules = root.getChild("modules", ns);
		
		modules.removeContent(new Filter() {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean matches(Object child) {
				if (child instanceof Element) {
					Element module = (Element)child;
					return (module.getText().endsWith("-models") ||
							module.getText().endsWith("-models-gen"));
				} else {
					return false;
				}
			}
		});
		
		XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(modulePom);
			sortie.output(document, fos);
		} finally {
			IOUtils.closeQuietly(fos);
		}
		
		modulePomIFile.refreshLocal(IResource.DEPTH_ZERO, null);
	}


	/**
	 * @param packagerProject
	 * @return
	 * @throws CoreException 
	 */
	private File[] getGeneratedZips(IProject packagerProject) throws CoreException {
		File[] zipFiles;
		packagerProject.refreshLocal(IResource.DEPTH_INFINITE, null);
		File target = packagerProject.getFile("target").getLocation().toFile();
		zipFiles = target.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".zip");
			}
		});
		return zipFiles;
	}
	
}
