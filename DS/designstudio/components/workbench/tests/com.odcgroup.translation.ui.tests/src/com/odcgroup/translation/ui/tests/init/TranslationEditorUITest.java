package com.odcgroup.translation.ui.tests.init;

import java.util.List;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.translation.ui.TranslationUICore;
import com.odcgroup.translation.ui.editor.TranslationEditor;
import com.odcgroup.translation.ui.editor.controls.TranslationTab;
import com.odcgroup.translation.ui.editor.model.ITranslationCollectorProvider;
import com.odcgroup.translation.ui.navigator.TranslationEditorInput;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * @author pkk
 */
public class TranslationEditorUITest extends AbstractDSUnitTest {

    @Before
	public void setUp() throws Exception {
    	createModelsProject();
    }

	@After
	public void tearDown() throws Exception {
        deleteModelsProjects();
    }
	
	/**
	 * test the translationCollector Provider extensions
	 */
	@Test
	public void testTranslationCollectorProviders() {
		List<ITranslationCollectorProvider> providers = TranslationUICore.getTranslationCollectors();
		Assert.assertEquals(false, providers.isEmpty());
	}

	@Test
	public void testTranslationOpenEditorAction() {
		IOfsProject ofsProject = getOfsProject();
		Assert.assertNotNull(ofsProject);

		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IEditorPart editor = null;
		try {
			IEditorInput input = new TranslationEditorInput(ofsProject);
			String editorId = "com.odcgroup.translation.ui.editor.TranslationEditor";
			// open the translation editor for given ofsProject
			editor = window.getActivePage().openEditor(input, editorId, true);
		} catch (PartInitException e) {
		}

		// check the opened editor is TranslationEditor
		Assert.assertNotNull(editor);
		Assert.assertEquals(true, (editor instanceof TranslationEditor));
		TranslationEditor tEditor = (TranslationEditor) editor;
        
		// checkt the translation tabs in the editor
		CTabFolder tabfolder = tEditor.getTabFolder();
		// tabs size == collectors+all
		Assert.assertEquals(tEditor.getTranslationCollectors().size()+1, tabfolder.getItems().length);
		CTabItem tabItem = tabfolder.getSelection();
		Assert.assertEquals(true, (tabItem instanceof TranslationTab));
        //check for the menu tab in the translation viewer.
		Boolean flag = false;
		for (CTabItem ctab:tEditor.getTabFolder().getItems()) {
			if(ctab.getText().contains("Menu")) {
				flag = true;
				break;
			}
		}
		Assert.assertTrue("Menu tab is unavailable",flag);
		// finally close all the editors
		window.getActivePage().closeAllEditors(false);
	}

	/**
	 * DS-3547 - test if the translation tab for VisualRules model exists
	 */
	/*
	@Test
	public void testTranslationEditorTabs() {
		IOfsProject ofsProject = getOfsProject();
		Assert.assertNotNull(ofsProject);
		
		List<ITranslationCollectorProvider> providers = TranslationUICore.getTranslationCollectors();
		Assert.assertEquals(false, providers.isEmpty());

		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IEditorPart editor = null;
		try {
			IEditorInput input = new TranslationEditorInput(ofsProject);
			String editorId = "com.odcgroup.translation.ui.editor.TranslationEditor";
			// open the translation editor for given ofsProject
			editor = window.getActivePage().openEditor(input, editorId, true);
		} catch (PartInitException e) {
		}

		// check the opened editor is TranslationEditor
		Assert.assertNotNull(editor);
		Assert.assertEquals(true, (editor instanceof TranslationEditor));
		TranslationEditor tEditor = (TranslationEditor) editor;
		
		// check if the collectorProvider for rule models exists
		ITranslationCollectorProvider ruleProvider = null;		
		for (ITranslationCollectorProvider provider : providers) {
			if(provider.getDisplayName().equals("Rule")) {
				ruleProvider = provider;
			}
		}
		Assert.assertNotNull(ruleProvider);
		ITranslationCollector collector = ruleProvider.getTranslationCollector(ofsProject);
		Assert.assertNotNull(collector);
		String ruleTranslationCollectorClass = "com.odcgroup.visualrules.integration.ui.translation.RuleTranslationCollector";
		Assert.assertTrue("TranslationCollector for VisualRules found", (collector.getClass().equals(ruleTranslationCollectorClass)));

		// checkt the translation tabs in the editor
		CTabFolder tabfolder = tEditor.getTabFolder();
		Assert.assertEquals(tEditor.getTranslationCollectors().size(), tabfolder.getItems().length);
		CTabItem[] tabs = tabfolder.getItems();
		List<String> tabNames = new ArrayList<String>();
		for (CTabItem tab : tabs) {
			Assert.assertEquals(true, (tab instanceof TranslationTab));
			tabNames.add(tab.getText());
		}
		Assert.assertTrue("Translation tab for VisualRules found", tabNames.contains(ruleProvider.getDisplayName()));		

	}*/

}
