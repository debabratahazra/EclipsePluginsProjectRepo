package com.temenos.t24.tools.eclipse.basic.wizards.t24unit;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

/**
 * Wizard helps in creating the T24 unit test codes
 * 
 * @author ssethupathi
 * 
 */
public class T24UnitTestGenerationWizard extends Wizard implements INewWizard {

    private T24UnitTestGenerationPage page;
    private IStructuredSelection selection;

    public T24UnitTestGenerationWizard() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean performFinish() {
        String fileName = page.getTestFileName();
        String fileDirName = page.getTestDirName();
        T24UnitCodeCreator.getInstance().generateCode(fileDirName, fileName);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.selection = selection;
    }

    /**
     * {@inheritDoc}
     */
    public void addPages() {
        setWindowTitle("T24Unit test Creation Wizard");
        page = new T24UnitTestGenerationPage(selection);
        addPage(page);
    }
}
