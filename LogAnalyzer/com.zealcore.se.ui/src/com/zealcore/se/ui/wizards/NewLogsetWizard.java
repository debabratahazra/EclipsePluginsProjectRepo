package com.zealcore.se.ui.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.zealcore.se.ui.editors.ILogSessionWrapper;

public class NewLogsetWizard extends Wizard implements INewWizard {

    private static final String WINDOW_TITLE = "New Logset";

    private LogsetCreationPage logsetCreationPage;

    public NewLogsetWizard() {
        setWindowTitle(NewLogsetWizard.WINDOW_TITLE);
    }

    @Override
    public boolean performFinish() {
        String name = logsetCreationPage.getLogsetName();

        final ILogSessionWrapper addedLog = logsetCreationPage.getProject()
                .addLog(name);
        return addedLog == null ? false : true;
    }

    @Override
    public void addPages() {
        super.addPages();
        addPage(logsetCreationPage);
    }

    public void init(final IWorkbench workbench,
            final IStructuredSelection selection) {
        logsetCreationPage = new LogsetCreationPage(selection);
    }
}
