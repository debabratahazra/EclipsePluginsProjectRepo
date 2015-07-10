package sample.rcp;

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

    private IWorkbenchAction iExitAction;
	private IWorkbenchAction iSaveAction;
	private IWorkbenchAction iAboutAction;
	private IWorkbenchAction iOpenNewWindowAction;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    protected void makeActions(IWorkbenchWindow window) {
    	iExitAction = ActionFactory.QUIT.create(window);
    	register(iExitAction);
    	iSaveAction = ActionFactory.SAVE.create(window);
    	register(iSaveAction);
    	iAboutAction = ActionFactory.ABOUT.create(window);
    	register(iAboutAction);
    	iOpenNewWindowAction = ActionFactory.OPEN_NEW_WINDOW.create(window);
    	register(iOpenNewWindowAction);
    }

    protected void fillMenuBar(IMenuManager menuBar) {
    	MenuManager fileMenu = new MenuManager("&File", IWorkbenchActionConstants.M_FILE);
    	menuBar.add(fileMenu);
    	MenuManager helpMenu = new MenuManager("&Help", IWorkbenchActionConstants.M_HELP);
    	menuBar.add(helpMenu);
    	
    	// Added in File menu
    	fileMenu.add(iOpenNewWindowAction);
    	fileMenu.add(iSaveAction);
    	fileMenu.add(iExitAction);
    	
    	// Added in Help Menu
    	helpMenu.add(iAboutAction);
    }
    
    @Override
    protected void fillCoolBar(ICoolBarManager coolBar) {
    	super.fillCoolBar(coolBar);
    	IToolBarManager toolbarManager = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
    	coolBar.add(toolbarManager);
    	
    	// Add action in toolbar
    	toolbarManager.add(iOpenNewWindowAction);
    	toolbarManager.add(iSaveAction);
    	toolbarManager.add(iExitAction);
    	toolbarManager.add(new Separator());
    	toolbarManager.add(iAboutAction);
    }
    
}
