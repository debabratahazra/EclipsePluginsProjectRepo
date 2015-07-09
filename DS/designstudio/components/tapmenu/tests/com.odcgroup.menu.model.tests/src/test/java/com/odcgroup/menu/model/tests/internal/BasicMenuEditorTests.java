package com.odcgroup.menu.model.tests.internal;

import static com.odcgroup.workbench.core.tests.util.TestTankResourcesTestUtil.loadTestTankResourceAsString;
import static org.eclipse.xtext.junit4.ui.util.IResourcesSetupUtil.waitForAutoBuild;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.ui.AbstractEditorTest;
import org.eclipse.xtext.junit4.ui.util.IResourcesSetupUtil;
import org.eclipse.xtext.junit4.ui.util.JavaProjectSetupUtil;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.menu.editor.provider.MenuAdapterFactoryContentProvider;
import com.odcgroup.menu.editor.provider.MenuDecoratingLabelProvider;
import com.odcgroup.menu.editor.ui.MenuEditor;
import com.odcgroup.menu.model.Enabled;
import com.odcgroup.menu.model.MenuInjectorProvider;
import com.odcgroup.menu.model.MenuItem;
import com.odcgroup.menu.model.MenuPackage;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.ProjectInitializer;

/**
 * @author pkk
 */
@SuppressWarnings("all")
@RunWith(XtextRunner.class)
@InjectWith(MenuInjectorProvider.class)
public class BasicMenuEditorTests extends AbstractEditorTest {
	
	private final String TEST_PROJECT = "default-models";
	  
	private static String MENU_MODEL = "/menu/Default/gen/MenuGen.menu";
	private static String PAGEFLOW_MODEL = "/pageflow/ds3742/Ds3742.pageflow";
	
	private static String STANDARD_MENU_EDITOR_ID = "com.odcgroup.menu.editor.ui.MenuEditorID";

	@Before
	@Override
	public void setUp() {
		try {
			super.setUp();
			createJavaProjectWithXtextNature();
			waitForAutoBuild();
		} catch (Throwable ex) {
			throw Exceptions.sneakyThrow(ex);
		}
	}
	  
	@Override
	protected String getEditorId() {
		// this should be the id of the XText editor
	  return "com.odcgroup.menu.model.Menu";
	}
	  
	private void createJavaProjectWithXtextNature() {
		try {
			IJavaProject createJavaProject = JavaProjectSetupUtil.createJavaProject(TEST_PROJECT);
			final IProject project = createJavaProject.getProject();
			IResourcesSetupUtil.addNature(project, XtextProjectHelper.NATURE_ID);
			// this runnable  is necessary to initialize all old OfsXXX data structures.
			// It also add the OFS nature to the project
			IWorkspaceRunnable op = new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException {
					for (ProjectInitializer initializer : OfsCore.getProjectInitializers(project, false)) {
						initializer.updateConfiguration(project, null);
					}
				}
			};
			ResourcesPlugin.getWorkspace().run(op, null);
		} catch (Throwable ex) {
			throw Exceptions.sneakyThrow(ex);
		}
	}
	
	private IFile createTestFile(final String modelRelativePath) {
		try {
			String contents = loadTestTankResourceAsString(this.getClass(), modelRelativePath);
			return IResourcesSetupUtil.createFile((this.TEST_PROJECT + modelRelativePath), contents);
		} catch (Throwable ex) {
			throw Exceptions.sneakyThrow(ex);
		}
	}
	
	private IEditorPart openEditor(IFile file, String editorId) throws PartInitException {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
				new FileEditorInput(file), editorId);
	}
		
	@Test
	public void testMenuEditor() throws PartInitException {
		
		createTestFile(PAGEFLOW_MODEL);
		IFile menufile = createTestFile(MENU_MODEL);
		Assert.assertTrue(menufile.exists());
		IEditorPart ep = openEditor(menufile, STANDARD_MENU_EDITOR_ID);
		Assert.assertNotNull("Menu Editor failed to open", ep);
		Assert.assertTrue("Expected instanceof MenuEditor, but was "+ep.getClass().getName(),
				ep instanceof MenuEditor);
		MenuEditor editor = (MenuEditor) ep;
		
		Viewer viewer = editor.getViewer();
		Assert.assertTrue("Expected to be an instance of TreeViewer, but it was " + (viewer!=null?viewer.getClass():"null"), viewer instanceof TreeViewer);
		TreeViewer treeViewer = (TreeViewer) viewer;
		TreeItem item = treeViewer.getTree().getTopItem();
		treeViewer.setSelection(new StructuredSelection(item.getData()), true);
		treeViewer.setExpandedState(item.getData(), true);
		
		Assert.assertTrue(treeViewer.getLabelProvider() instanceof MenuDecoratingLabelProvider);
		Assert.assertTrue(treeViewer.getContentProvider() instanceof MenuAdapterFactoryContentProvider);
		
		AdapterFactoryEditingDomain domain = (AdapterFactoryEditingDomain) editor.getEditingDomain();
		Collection<?> childdesc = domain.getNewChildDescriptors(item.getData(), null);
		Assert.assertTrue(childdesc.size() == 1);
		Object obj = childdesc.iterator().next();
		Assert.assertTrue(obj instanceof CommandParameter);
		CommandParameter cp = (CommandParameter) obj;
		Assert.assertTrue(cp.getValue() instanceof MenuItem);
		
		TreeItem slevel = item.getItem(0);
		Assert.assertNotNull(slevel);
		Assert.assertTrue(slevel.getItemCount() == 7);
		treeViewer.setSelection(new StructuredSelection(slevel.getItem(6).getData()), true);
		
		DoubleClickEvent event = new DoubleClickEvent(treeViewer, treeViewer.getSelection());
		IDoubleClickListener listener = new com.odcgroup.menu.editor.ui.MenuItemDoubleClickListener();
		listener.doubleClick(event);
		
		IEditorPart aeditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		Assert.assertTrue("Ds3742.pageflow".equals(aeditor.getTitle()));
	}
    
	@Test
	public void test5197MenuEnableProperty() throws PartInitException {
		IFile menufile = createTestFile(MENU_MODEL);
		Assert.assertTrue(menufile.exists());
		IEditorPart ep = openEditor(menufile, STANDARD_MENU_EDITOR_ID);
		Assert.assertNotNull("Menu Editor failed to open", ep);
		Assert.assertTrue("Expected instanceof MenuEditor, but was " + ep.getClass().getName(),
				ep instanceof MenuEditor);
		MenuEditor editor = (MenuEditor) ep;
		Viewer viewer = editor.getViewer();
		Assert.assertTrue("Expected to be an instance of TreeViewer, but it was " + (viewer!=null?viewer.getClass():"null"), viewer instanceof TreeViewer);
		TreeViewer treeViewer = (TreeViewer) viewer;
		TreeItem item = treeViewer.getTree().getTopItem();
		treeViewer.setSelection(new StructuredSelection(item.getData()), true);
		treeViewer.setExpandedState(item.getData(), true);
		AdapterFactoryEditingDomain domain = (AdapterFactoryEditingDomain) editor.getEditingDomain();
		Collection<?> childdesc = domain.getNewChildDescriptors(item.getData(), null);
		Assert.assertTrue(childdesc.size() == 1);
		Object obj = childdesc.iterator().next();
		Assert.assertTrue(obj instanceof CommandParameter);
		CommandParameter cp = (CommandParameter) obj;
		Assert.assertTrue(cp.getValue() instanceof MenuItem);
		MenuItem menuitem = (MenuItem) cp.getValue();
		final EAttribute attr = MenuPackage.eINSTANCE.getMenuItem_Enabled();
		if (editor != null) {
			EditingDomain editingDomain = editor.getEditingDomain();
			Command cmd = SetCommand.create(editingDomain, menuitem, attr, Enabled.FALSE);
			if (cmd != null) {
				editingDomain.getCommandStack().execute(cmd);
			}
			editor.getEditorSite().getPage().closeEditor(editor, false);
		}
		Assert.assertEquals(Enabled.FALSE, menuitem.getEnabled());
	}
}
