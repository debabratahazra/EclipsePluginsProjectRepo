package com.odcgroup.mdf.editor.ui.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.metamodel.MdfModelElement;


public class OnePageDialog extends TitleAreaDialog
    implements DialogPageContainer {
    protected final DialogPage page;
    protected final MdfModelElement model;
    private final WidgetFactory WIDGET_FACTORY = new FormWidgetFactory();

    public OnePageDialog(
        Shell parentShell, DialogPage page, MdfModelElement model) {
        super(parentShell);
        this.page = page;
        this.model = model;
        initPage();
    }

    public DialogPage getCurrentPage() {
        return page;
    }

    public void setDirty(boolean dirty) {
    }

    public boolean close() {
        page.dispose();
        return super.close();
    }

    public void updateButtons() {
        Button ok = getButton(IDialogConstants.OK_ID);

        if (ok != null) {
            ok.setEnabled(page.getMessageType() != IMessageProvider.ERROR);
        }
    }

    public void updateMessage() {
        setMessage(page.getMessage(), page.getMessageType());
    }

    public void updateTitleBar() {
        setTitle(page.getTitle());
    }

    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        String name = model.getName();
        newShell.setText(name == null ? "" : name);
    }

    protected Control createDialogArea(Composite parent) {
        page.createControl(parent);
        updateButtons();
        updateMessage();
        updateTitleBar();
        return page.getControl();
    }

    protected void okPressed() {
    	//DS-1349
        page.doSave(model);
        super.okPressed();
    }

    private void initPage() {
        page.setContainer(this);
        page.setEditMode(DialogPage.MODE_EDIT_SIMPLE);
    }

    /**
     * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPageContainer#getWidgetFactory()
     */
    public WidgetFactory getWidgetFactory() {
        return WIDGET_FACTORY;
    }
    
    /** 
     * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPageContainer#dispose()
     * //OCS-26284
     */
    public void dispose() {
    	
    }
    
}
