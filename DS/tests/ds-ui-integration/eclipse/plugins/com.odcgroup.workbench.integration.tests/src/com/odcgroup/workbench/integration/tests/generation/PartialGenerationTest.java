package com.odcgroup.workbench.integration.tests.generation;

import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.MODULES_DEFAULT_PACKAGE;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.Locale;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.workbench.integration.tests.ProjectUtils;
import com.odcgroup.workbench.integration.tests.util.pageobjects.MenuPageObject;
import com.odcgroup.workbench.integration.tests.util.pageobjects.ModuleEditorPageObject;
import com.odcgroup.workbench.integration.tests.util.pageobjects.OdysseyNavigatorPageObject;
import com.odcgroup.workbench.integration.tests.util.pageobjects.XmlEditorPageObject;

/**
 * Tests that generating a single page updates the messages.xml files correctly
 *
 * @author amc
 *
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class PartialGenerationTest {

	private static OdysseyNavigatorPageObject navigator = new OdysseyNavigatorPageObject();
	private String projectName = null;
	
	private static final String MODULE = "Module1";
	private static final String MODULE2 = "Module2";
	
	private static final String NEW_TRANSLATION = "NEW_TRANSLATION";
	private static final String NEW_TRANSLATION_FR = "NEW_TRANSLATION_FR";
	private static final String NEW_TRANSLATION_DE = "NEW_TRANSLATION_DE";
	
	private static final String ADDED_TRANSLATION = "UPDATED_TRANSLATION";
	private static final String ADDED_TRANSLATION_FR = "UPDATED_TRANSLATION_FR";
	private static final String ADDED_TRANSLATION_DE = "UPDATED_TRANSLATION_DE";
	
	@After
	public void tearDown() throws Exception {
		if(projectName != null) {
			ProjectUtils.deleteModelProject(projectName);
			ProjectUtils.getGenProjectName(projectName);
		}
	}
	@Ignore
	@Test
	public void generationFromProjectRootGeneratesMessagesXML() throws Exception {
		projectName = "part-gen-test-from-root-models";
		createProject(projectName);
		
		ModuleEditorPageObject moduleEditor = navigator.createModule(projectName, MODULE);
		createLabelWithTranslation(moduleEditor, NEW_TRANSLATION);
		moduleEditor.save();
		moduleEditor.close();
		navigator.runCodeGeneration(projectName);
		
		waitForGenerationToComplete();
		
		assertMessagesXmlContainsTranslation(projectName, NEW_TRANSLATION);
	}
	@Ignore
	@Test
	public void generationFromModuleGeneratesMessagesXML() throws Exception {
		projectName = "part-gen-test-from-module-models";
		createProject(projectName);
		
		ModuleEditorPageObject editor = navigator.createModule(projectName, MODULE);
		createLabelWithTranslation(editor, NEW_TRANSLATION);
		editor.save();
		editor.close();
		navigator.runCodeGeneration(projectName, MODULES_DEFAULT_PACKAGE, MODULE);
		
		assertMessagesXmlContainsTranslation(projectName, NEW_TRANSLATION);
	}
	@Ignore
	@Test
	public void newTranslationsInExistingModuleAreMergedIntoExistingMessagesXML() throws Exception {
		projectName = "part-gen-test-merge-existing-module-models";
		createProject(projectName);
		
		ModuleEditorPageObject editor = navigator.createModule(projectName, MODULE);
		createLabelWithTranslation(editor, NEW_TRANSLATION);
		editor.save();
		navigator.runCodeGeneration(projectName, MODULES_DEFAULT_PACKAGE, MODULE);

		assertMessagesXmlContainsTranslation(projectName, NEW_TRANSLATION);
		
		editor.setFocus();
		createSecondLabelWithTranslation(editor, ADDED_TRANSLATION);
		editor.save();
		editor.close();
		navigator.runCodeGeneration(projectName, MODULES_DEFAULT_PACKAGE, MODULE);
		
		assertMessagesXmlContainsTranslation(projectName, NEW_TRANSLATION);
		// test that added translation is merged in correctly after partial generation
		assertMessagesXmlContainsTranslation(projectName, ADDED_TRANSLATION);
	}
	
	@Ignore
	@Test
	public void newTranslationsInNewModuleAreMergedIntoExistingMessagesXML() throws Exception {
		projectName = "part-gen-test-merge-new-module-models";
		createProject(projectName);
		
		ModuleEditorPageObject editor = navigator.createModule(projectName, MODULE);
		createLabelWithTranslation(editor, NEW_TRANSLATION);
		editor.save();
		editor.close();
		navigator.runCodeGeneration(projectName, MODULES_DEFAULT_PACKAGE, MODULE);
		
		ModuleEditorPageObject secondEditor = navigator.createModule(projectName, MODULE2);
		createLabelWithTranslation(secondEditor, ADDED_TRANSLATION);
		secondEditor.save();
		secondEditor.close();
		navigator.runCodeGeneration(projectName, MODULES_DEFAULT_PACKAGE, MODULE2);
		
		assertMessagesXmlContainsTranslation(projectName, NEW_TRANSLATION);
		assertMessagesXmlContainsTranslation(projectName, ADDED_TRANSLATION);
	}
	
	@Ignore
	@Test
	public void newTranslationsInNewModuleAreMergedIntoExistingMessagesXMLForMultipleLocales() throws Exception {
		projectName = "part-gen-test-multiple-locales-models";
		createProject(projectName);
		
		ModuleEditorPageObject editor = navigator.createModule(projectName, MODULE);
		createLabelWithTranslation(editor, NEW_TRANSLATION, NEW_TRANSLATION_FR, NEW_TRANSLATION_DE);
		editor.save();
		editor.close();
		navigator.runCodeGeneration(projectName, MODULES_DEFAULT_PACKAGE, MODULE);
		
		ModuleEditorPageObject secondEditor = navigator.createModule(projectName, MODULE2);
		createLabelWithTranslation(secondEditor, ADDED_TRANSLATION, ADDED_TRANSLATION_FR, ADDED_TRANSLATION_DE);
		secondEditor.save();
		secondEditor.close();
		navigator.runCodeGeneration(projectName, MODULES_DEFAULT_PACKAGE, MODULE2);
		
		assertMessagesXmlContainsTranslation(projectName, NEW_TRANSLATION);
		assertMessagesXmlContainsTranslation(projectName, ADDED_TRANSLATION);
		
		assertMessagesXmlContainsTranslation(projectName, Locale.FRENCH, NEW_TRANSLATION_FR);
		assertMessagesXmlContainsTranslation(projectName, Locale.FRENCH, ADDED_TRANSLATION_FR);
		assertMessagesXmlContainsTranslation(projectName, Locale.GERMAN, NEW_TRANSLATION_DE);
		assertMessagesXmlContainsTranslation(projectName, Locale.GERMAN, ADDED_TRANSLATION_DE);
	}

	private void createProject(String projectName) {
		MenuPageObject menu = new MenuPageObject();
		menu.createNewModelProject(projectName);
	}

	private void createLabelWithTranslation(ModuleEditorPageObject moduleEditor, String translation) {
		createLabel(moduleEditor);
		moduleEditor.setTranslationOnSelectedComponentEnglish(translation);
	}
	
	private void createLabelWithTranslation(ModuleEditorPageObject moduleEditor, String englishTranslation, String frenchTranslation, String germanTranslation) {
		createLabel(moduleEditor);
		moduleEditor.setTranslationOnSelectedComponentEnglish(englishTranslation);
		moduleEditor.setTranslationOnSelectedComponentFrench(frenchTranslation);
		moduleEditor.setTranslationOnSelectedComponentGerman(germanTranslation);
	}

	private void createLabel(ModuleEditorPageObject moduleEditor) {
		moduleEditor.addHBox(30, 30);
		moduleEditor.addLabel(35, 35);
	}
	
	private void createSecondLabelWithTranslation(ModuleEditorPageObject moduleEditor, String translation) {
		moduleEditor.addLabel(60, 35);
		moduleEditor.setTranslationOnSelectedComponentEnglish(translation);
	}

	private void waitForGenerationToComplete() throws InterruptedException {
		try {
			Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	private void assertMessagesXmlContainsTranslation(String project, String translation) {
		SWTBotTreeItem messagesXMLNode = navigator.selectNode(new Path(ProjectUtils.getMessagesXmlPath(project)));
		assertNotNull(messagesXMLNode);
		messagesXMLNode.doubleClick();
		assertEditorContainsTranslation(ProjectUtils.getMessagesXmlFileName(), translation);
	}
	
	private void assertMessagesXmlContainsTranslation(String project, Locale locale, String translation) {
		SWTBotTreeItem messagesXMLNode = navigator.selectNode(new Path(ProjectUtils.getMessagesXmlPath(project, locale)));
		assertNotNull(messagesXMLNode);
		messagesXMLNode.doubleClick();
		assertEditorContainsTranslation(ProjectUtils.getMessagesXmlFileName(locale), translation);
	}

	private void assertEditorContainsTranslation(String fileName, String translation) {
		XmlEditorPageObject editor =  new XmlEditorPageObject(fileName);
		String editorText = editor.getText();
		assertTrue(editorText.contains(translation));
		editor.close();
	}
}
