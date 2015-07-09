package com.odcgroup.mdf.editor.ui.dialogs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;

import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.metamodel.MdfModelElement;


public class MdfElementWizard extends BasicNewResourceWizard
    implements INewWizard {
    private final WidgetFactory WIDGET_FACTORY = new FormWidgetFactory();
    private List pages = new ArrayList();
    protected final MdfModelElement model;

    /**
     * Constructor for MdfElementWizard.
     */
    public MdfElementWizard(MdfModelElement model) {
        super();
        setNeedsProgressMonitor(true);
        this.setWindowTitle("MDF Wizard");
        this.model = model;
    }

    /**
     * Adding the page to the wizard.
     */
    public void addPages() {
        DialogPagesFactory factory = new AllPagesFactory();
        factory.addPages(model, pages);

        Iterator it = pages.iterator();

        while (it.hasNext()) {
            DialogPage page = (DialogPage) it.next();
            addPage(new MdfWizardPage(page));
        }
    }

    /**
     * @see org.eclipse.jface.wizard.IWizard#dispose()
     */
    public void dispose() {
        for (int i = 0; i < pages.size(); i++) {
            ((DialogPage) pages.get(i)).dispose();
        }

        super.dispose();
    }

    /**
     * This method is called when 'Finish' button is pressed in the wizard.
     */
    public boolean performFinish() {
        for (int i = 0; i < pages.size(); i++) {
            ((DialogPage) pages.get(i)).doSave(model);
        }

        return true;
    }

    private class MdfWizardPage extends WizardPage
        implements DialogPageContainer {
        private final DialogPage page;

        /**
         * Constructor for SampleNewWizardPage.
         *
         * @param pageName
         */
        public MdfWizardPage(DialogPage page) {
            super(page.getClass().getName());
            this.page = page;
            page.setContainer(this);
            page.setEditMode(DialogPage.MODE_CREATE);
        }

        /**
         * @see com.odcgroup.mdf.editor.ui.wizards.DialogPageContainer#getCurrentPage()
         */
        public DialogPage getCurrentPage() {
            IWizardPage wp = getContainer().getCurrentPage();

            if (wp instanceof MdfWizardPage) {
                return ((MdfWizardPage) wp).page;
            }

            return null;
        }

        /**
         * @see org.eclipse.jface.dialogs.IDialogPage#setDescription(java.lang.String)
         */
        public void setDescription(String description) {
            page.setDescription(description);
        }

        /**
         * @see org.eclipse.jface.dialogs.IDialogPage#getDescription()
         */
        public String getDescription() {
            return page.getDescription();
        }

        /**
         * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPageContainer#setDirty(boolean)
         */
        public void setDirty(boolean dirty) {
        }

        /**
         * @see org.eclipse.jface.dialogs.IDialogPage#getErrorMessage()
         */
        public String getErrorMessage() {
            return page.getErrorMessage();
        }

        /**
         * @see org.eclipse.jface.dialogs.IDialogPage#getImage()
         */
        public Image getImage() {
            Image result = page.getImage();

            if (result == null) {
                result = super.getImage();
            }

            return result;
        }

        /**
         * @see org.eclipse.jface.dialogs.IDialogPage#setImageDescriptor(org.eclipse.jface.resource.ImageDescriptor)
         */
        public void setImageDescriptor(ImageDescriptor image) {
            page.setImageDescriptor(image);
        }

        /**
         * @see org.eclipse.jface.dialogs.IDialogPage#getMessage()
         */
        public String getMessage() {
            return page.getMessage();
        }

        /**
         * @see org.eclipse.jface.wizard.IWizardPage#isPageComplete()
         */
        public boolean isPageComplete() {
            return page.isPageComplete();
        }

        /**
         * @see com.odcgroup.mdf.editor.ui.wizards.DialogPageContainer#getShell()
         */
        public Shell getShell() {
            return getContainer().getShell();
        }

        /**
         * @see org.eclipse.jface.dialogs.IDialogPage#setTitle(java.lang.String)
         */
        public void setTitle(String title) {
            page.setTitle(title);
        }

        /**
         * @see org.eclipse.jface.dialogs.IDialogPage#getTitle()
         */
        public String getTitle() {
            return page.getTitle();
        }

        /**
         * @see org.eclipse.jface.dialogs.IDialogPage#setVisible(boolean)
         */
        public void setVisible(boolean visible) {
            page.setVisible(visible);
        }

        /**
         * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
         */
        public void createControl(Composite parent) {
            page.createControl(parent);
            setControl(page.getControl());
        }

        /**
         * @see org.eclipse.jface.dialogs.IDialogPage#dispose()
         */
        public void dispose() {
            page.dispose();
            super.dispose();
        }

        /**
         * @see org.eclipse.jface.dialogs.IDialogPage#performHelp()
         */
        public void performHelp() {
            page.performHelp();
        }

        /**
         * @see com.odcgroup.mdf.editor.ui.wizards.DialogPageContainer#updateButtons()
         */
        public void updateButtons() {
            getContainer().updateButtons();
        }

        /**
         * @see com.odcgroup.mdf.editor.ui.wizards.DialogPageContainer#updateMessage()
         */
        public void updateMessage() {
            getContainer().updateMessage();
        }

        /**
         * @see com.odcgroup.mdf.editor.ui.wizards.DialogPageContainer#updateTitleBar()
         */
        public void updateTitleBar() {
            getContainer().updateTitleBar();
        }

        /**
         * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPageContainer#getWidgetFactory()
         */
        public WidgetFactory getWidgetFactory() {
            return WIDGET_FACTORY;
        }
    }
}
