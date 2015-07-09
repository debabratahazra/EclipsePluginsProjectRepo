package com.odcgroup.workbench.rcpapp;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.search.internal.ui.OpenSearchDialogAction;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;

import com.odcgroup.workbench.ui.action.ExportProjectAction;
import com.odcgroup.workbench.ui.action.ImportProjectAction;
import com.odcgroup.workbench.ui.action.OpenPreferencesAction;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private IAction newProjectAction;
	private IWorkbenchAction saveAction;
	private IWorkbenchAction saveAllAction;
	private IWorkbenchAction closeAllAction;
	private IWorkbenchAction renameAction;
	private IWorkbenchAction refreshAction;
	private IWorkbenchAction printAction;
	private IAction importProjectAction;
	private IAction exportProjectAction;
	private IWorkbenchAction propertyAction;
	private IWorkbenchAction exitAction;

	private IWorkbenchAction undoAction;
	private IWorkbenchAction redoAction;
	private IWorkbenchAction cutAction;
	private IWorkbenchAction copyAction;
	private IWorkbenchAction pasteAction;
	private IWorkbenchAction deleteAction;
	private IWorkbenchAction selectAllAction;
	
	private IAction searchAction;

	private IWorkbenchAction resetPerspectiveAction;
	private IAction preferencesAction;
	
	private IWorkbenchAction introAction;
	private IWorkbenchAction helpAction;
	private IWorkbenchAction aboutAction;

    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    protected void makeActions(IWorkbenchWindow window) {

    	// File menu actions
    	saveAction = ActionFactory.SAVE.create(window);
		register(saveAction);
		saveAllAction = ActionFactory.SAVE_ALL.create(window);
		register(saveAllAction);
		closeAllAction = ActionFactory.CLOSE_ALL.create(window);
		register(closeAllAction);
		renameAction = ActionFactory.RENAME.create(window);
		register(renameAction);
		refreshAction = ActionFactory.REFRESH.create(window);
        refreshAction.setDisabledImageDescriptor(
        		IDEWorkbenchPlugin.getIDEImageDescriptor("dlcl16/refresh_nav.gif"));//$NON-NLS-1$
        refreshAction.setImageDescriptor(
        		IDEWorkbenchPlugin.getIDEImageDescriptor("elcl16/refresh_nav.gif"));//$NON-NLS-1$       
        refreshAction.setActionDefinitionId("org.eclipse.ui.file.refresh"); //$NON-NLS-1$
		register(refreshAction);
		printAction = ActionFactory.PRINT.create(window);
		register(printAction);
		importProjectAction = new ImportProjectAction(window);
		register(importProjectAction);
		exportProjectAction = new ExportProjectAction();
		exportProjectAction.setText("Export Projects...");
		register(exportProjectAction);
		propertyAction = ActionFactory.PROPERTIES.create( window);
		register(propertyAction);
		exitAction = ActionFactory.QUIT.create(window);
		register(exitAction);
		
		// Edit menu actions
		undoAction = ActionFactory.UNDO.create(window);
		register(undoAction);
		redoAction = ActionFactory.REDO.create(window);
		register(redoAction);
		cutAction = ActionFactory.CUT.create(window);
		register(cutAction);
		copyAction = ActionFactory.COPY.create(window);
		register(copyAction);
		pasteAction = ActionFactory.PASTE.create(window);
		register(pasteAction);
		deleteAction = ActionFactory.DELETE.create(window);
		register(deleteAction);
		selectAllAction = ActionFactory.SELECT_ALL.create(window);
		register(selectAllAction);
		searchAction = new OpenSearchDialogAction();
		
		// Window menu actions
		resetPerspectiveAction = ActionFactory.RESET_PERSPECTIVE.create(window);
		register(resetPerspectiveAction);
		preferencesAction = new OpenPreferencesAction(window);
		register(preferencesAction);
		
		// Help menu actions
		helpAction = ActionFactory.HELP_CONTENTS.create(window);
		register(helpAction);
		aboutAction = ActionFactory.ABOUT.create(window);
		register(aboutAction);
//		introAction = ActionFactory.INTRO.create(window);
//		register(introAction);
    }

    protected void fillMenuBar(IMenuManager menuBar) {
    	
		MenuManager fileMenu = new MenuManager("&File", IWorkbenchActionConstants.M_FILE);
		MenuManager editMenu = new MenuManager("&Edit", IWorkbenchActionConstants.M_EDIT);
		MenuManager windowMenu = new MenuManager("&Window", IWorkbenchActionConstants.M_WINDOW);
		MenuManager helpMenu = new MenuManager("&Help", IWorkbenchActionConstants.M_HELP);

		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(new GroupMarker(IWorkbenchActionConstants.M_PROJECT));
		menuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		menuBar.add(windowMenu);
		menuBar.add(helpMenu);
		
		// File menu
		fileMenu.add(newProjectAction);
		fileMenu.add(new Separator());
		fileMenu.add(closeAllAction);
		fileMenu.add(new Separator());
		fileMenu.add(saveAction);
		fileMenu.add(saveAllAction);
		fileMenu.add(new Separator());
		fileMenu.add(renameAction);
		fileMenu.add(refreshAction);
		fileMenu.add(new Separator());
		fileMenu.add(printAction);
		fileMenu.add(new Separator());
		fileMenu.add(importProjectAction);
		fileMenu.add(exportProjectAction);
		fileMenu.add(new Separator());
		fileMenu.add(propertyAction);
		fileMenu.add(new Separator());
		fileMenu.add(new GroupMarker(IWorkbenchActionConstants.MRU));
		fileMenu.add(exitAction);
		fileMenu.add(new GroupMarker(IWorkbenchActionConstants.FILE_END));
		
		// Edit menu
		editMenu.add(undoAction);
		editMenu.add(redoAction);
		editMenu.add(new Separator());
		editMenu.add(cutAction);
		editMenu.add(copyAction);
		editMenu.add(pasteAction);
		editMenu.add(new Separator());
		editMenu.add(deleteAction);
		editMenu.add(selectAllAction);
		editMenu.add(new Separator());
		editMenu.add(searchAction);

		// Window menu
		windowMenu.add(preferencesAction);
		windowMenu.add(resetPerspectiveAction);
		
		// Help menu
//		helpMenu.add(introAction);
		helpMenu.add(helpAction);
		helpMenu.add(new Separator());
		helpMenu.add(new Separator());
		helpMenu.add(aboutAction);
    }

    protected void fillCoolBar(ICoolBarManager coolBar) {
        IToolBarManager toolbar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
        coolBar.add(new ToolBarContributionItem(toolbar, "main"));
        toolbar.add(newProjectAction);
        toolbar.add(saveAction);
        toolbar.add(printAction);
        toolbar.add(new Separator());
        toolbar.add(searchAction);
    }
}
