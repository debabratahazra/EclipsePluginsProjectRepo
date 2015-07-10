package com.zealcore.se.rcp;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.ide.IDEActionFactory;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.registry.ActionSetRegistry;
import org.eclipse.ui.internal.registry.IActionSetDescriptor;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

    // actions
    private IAction exitAction;

    private IAction importAction;

    private IAction exportAction;

    private IAction helpContentsAction;

    private IAction helpSearchAction;

    private IAction helpAboutAction;

    private IAction resetPerspectiveAction;

    private IAction preferencesAction;

    // contributions
    private IContributionItem perspectiveContributions;

    private IContributionItem viewContributions;

    private IContributionItem wizardContributions;

    private IWorkbenchAction switchWorkspaceAction;

    public ApplicationActionBarAdvisor(final IActionBarConfigurer configurer) {
        super(configurer);
    }

    @Override
    protected void makeActions(final IWorkbenchWindow window) {
        this.switchWorkspaceAction = IDEActionFactory.OPEN_WORKSPACE
                .create(window);
        register(this.switchWorkspaceAction);
        this.importAction = ActionFactory.IMPORT.create(window);
        register(this.importAction);
        this.exportAction = ActionFactory.EXPORT.create(window);
        register(this.exportAction);
        this.exitAction = ActionFactory.QUIT.create(window);
        register(this.exitAction);

        this.wizardContributions = ContributionItemFactory.NEW_WIZARD_SHORTLIST
                .create(window);

        // window menu
        this.perspectiveContributions = ContributionItemFactory.PERSPECTIVES_SHORTLIST
                .create(window);
        this.viewContributions = ContributionItemFactory.VIEWS_SHORTLIST
                .create(window);
        this.resetPerspectiveAction = ActionFactory.RESET_PERSPECTIVE
                .create(window);
        this.preferencesAction = ActionFactory.PREFERENCES.create(window);

        // help menu
        this.helpContentsAction = ActionFactory.HELP_CONTENTS.create(window);
        this.helpContentsAction
                .setActionDefinitionId("org.eclipse.ui.help.dynamicHelp");
        this.helpSearchAction = ActionFactory.HELP_SEARCH.create(window);
        this.helpAboutAction = ActionFactory.ABOUT.create(window);

        register(ActionFactory.SAVE.create(window));
        register(ActionFactory.SAVE_ALL.create(window));
        register(ActionFactory.SAVE_AS.create(window));

        removeExtraneousActions();
    }

    @Override
    protected void fillCoolBar(final ICoolBarManager coolBar) {
        coolBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
    }

    @Override
    protected void fillMenuBar(final IMenuManager menuBar) {
        menuBar.removeAll();

        createFileMenu(menuBar);
        createNavigateMenu(menuBar);
        menuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
        createWindowMenu(menuBar);
        createHelpMenu(menuBar);
    }

    private void createNavigateMenu(final IMenuManager menuBar) {
        MenuManager menu = new MenuManager(
                IDEWorkbenchMessages.Workbench_navigate,
                IWorkbenchActionConstants.M_NAVIGATE);
        menu.add(new GroupMarker(IWorkbenchActionConstants.NAV_START));
        menu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));

        MenuManager goToSubMenu = new MenuManager(
                IDEWorkbenchMessages.Workbench_goTo,
                IWorkbenchActionConstants.GO_TO);
        menu.add(goToSubMenu);
        menuBar.add(menu);
    }

    private void createHelpMenu(final IMenuManager menuBar) {
        final MenuManager helpMenu = new MenuManager("&Help",
                IWorkbenchActionConstants.M_HELP);
        menuBar.add(helpMenu);
        helpMenu.add(this.helpContentsAction);
        helpMenu.add(this.helpSearchAction);
        helpMenu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
        helpMenu.add(new Separator());
        helpMenu.add(new Separator());
        helpMenu.add(this.helpAboutAction);
    }

    private void createWindowMenu(final IMenuManager menuBar) {
        final MenuManager windowMenu = new MenuManager("&Window",
                IWorkbenchActionConstants.M_WINDOW);
        final MenuManager openPerspectiveMenu = new MenuManager(
                "Open Perspective");
        openPerspectiveMenu.add(this.perspectiveContributions);
        windowMenu.add(openPerspectiveMenu);

        final MenuManager showViewMenu = new MenuManager("Show View");
        showViewMenu.add(this.viewContributions);
        windowMenu.add(showViewMenu);
        windowMenu.add(new Separator());
        windowMenu.add(this.resetPerspectiveAction);
        windowMenu.add(new Separator());
        windowMenu.add(this.preferencesAction);
        menuBar.add(windowMenu);
    }

    private void createFileMenu(final IMenuManager menuBar) {
        final MenuManager fileMenu = new MenuManager("&File",
                IWorkbenchActionConstants.M_FILE);
        final MenuManager newMenu = new MenuManager("New", "new");
        newMenu.add(this.wizardContributions);
        fileMenu.add(newMenu);
        fileMenu.add(new Separator());
        fileMenu.add(this.switchWorkspaceAction);
        fileMenu.add(new Separator());
        fileMenu.add(this.importAction);
        fileMenu.add(this.exportAction);
        fileMenu.add(new Separator());
        fileMenu.add(this.exitAction);
        menuBar.add(fileMenu);
    }

    private void removeExtraneousActions() {

        ActionSetRegistry reg = WorkbenchPlugin.getDefault()
                .getActionSetRegistry();

        // removing gotoLastPosition message
        removeStandardAction(reg,
                "org.eclipse.ui.edit.text.actionSet.navigation");

        // Removing "Convert Line Delimiters To" menu
        removeStandardAction(reg,
                "org.eclipse.ui.edit.text.actionSet.convertLineDelimitersTo");

        // Removing "Open File" menu
        removeStandardAction(reg, "org.eclipse.ui.actionSet.openFiles");
    }

    private void removeStandardAction(final ActionSetRegistry reg,
            final String actionSetId) {

        IActionSetDescriptor[] actionSets = reg.getActionSets();

        for (int i = 0; i < actionSets.length; i++) {
            if (!actionSets[i].getId().equals(actionSetId)) {
                continue;
            }
            IExtension ext = actionSets[i].getConfigurationElement()
                    .getDeclaringExtension();

            reg.removeExtension(ext, new Object[] { actionSets[i] });

        }
    }
}
