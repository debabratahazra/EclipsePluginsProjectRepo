
package com.tel.autosysframework.intro;

import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.actions.ActionFactory;  
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;  

import com.tel.autosysframework.run.RunAutosysProject;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private IWorkbenchWindow window = null;
	private IWorkbenchAction newaction = null;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.application.ActionBarAdvisor#makeActions(org.eclipse.ui.IWorkbenchWindow)
	 */
	protected void makeActions(IWorkbenchWindow window) {
		this.window = window;
		newaction = ActionFactory.NEW_WIZARD_DROP_DOWN.create(window);
	}

	@Override
	protected void fillCoolBar(ICoolBarManager coolBar) {
		IToolBarManager toolbar =
			new ToolBarManager(coolBar.getStyle() | SWT.BOTTOM);
		coolBar.add(toolbar);
		toolbar.add(ActionFactory.NEW_WIZARD_DROP_DOWN.create(window));
		toolbar.add(ActionFactory.SAVE.create(window));
		toolbar.add(ActionFactory.SAVE_ALL.create(window));
		coolBar.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		toolbar.add(ActionFactory.CUT.create(window));
		toolbar.add(ActionFactory.COPY.create(window));
		toolbar.add(ActionFactory.PASTE.create(window));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.application.ActionBarAdvisor#fillMenuBar(org.eclipse.jface.action.IMenuManager)
	 */
	protected void fillMenuBar(IMenuManager menuBar) {/*

		// Menu Sample
		MenuManager manager = new MenuManager("&File", "com.tel.autosysframework.autosys");
		newaction.setText("&New");
		manager.add(newaction);
		newaction = ActionFactory.SAVE.create(window);
		manager.add(newaction);
		newaction = ActionFactory.SAVE_ALL.create(window);
		manager.add(newaction);
		newaction = ActionFactory.SAVE_AS.create(window);
		manager.add(newaction);
		newaction = ActionFactory.IMPORT.create(window);
		manager.add(newaction);
		newaction = ActionFactory.CLOSE_ALL.create(window);
		manager.add(newaction);
		newaction = ActionFactory.CLOSE.create(window);
		manager.add(newaction);
		newaction = ActionFactory.QUIT.create(window);
		manager.add(newaction);
		menuBar.add(manager);

		manager = new MenuManager("&Edit", "edit");
		newaction = ActionFactory.CUT.create(window);
		manager.add(newaction);
		newaction = ActionFactory.COPY.create(window);
		manager.add(newaction);
		newaction = ActionFactory.PASTE.create(window);
		manager.add(newaction);
		newaction = ActionFactory.UNDO.create(window);
		manager.add(newaction);
		newaction = ActionFactory.REDO.create(window);
		manager.add(newaction);
		menuBar.add(manager);
		
		manager = new MenuManager("&View", "view");
		menuBar.add(manager);
		
		manager = new MenuManager("&Design", "design");
		menuBar.add(manager);
		
		manager = new MenuManager("&Run", "org.eclipse.ui.run");
		menuBar.add(manager);

	*/}

}
