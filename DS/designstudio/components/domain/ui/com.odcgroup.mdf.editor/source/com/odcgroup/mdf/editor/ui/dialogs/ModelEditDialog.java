package com.odcgroup.mdf.editor.ui.dialogs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.metamodel.MdfModelElement;


public class ModelEditDialog extends TitleAreaDialog
    implements DialogPageContainer {
    private final MdfModelElement model;
    private final WidgetFactory WIDGET_FACTORY = new FormWidgetFactory();
    private CTabFolder tabFolder;
    private List pages = new ArrayList();
    private final int editMode;
	private DialogPage explicitPage;

    public ModelEditDialog(Shell parentShell, MdfModelElement model) {
        this(parentShell, model, false);
    }

    public ModelEditDialog(Shell parentShell, MdfModelElement model, boolean newModel) {
        super(parentShell);
        this.model = model;
        editMode = (newModel ? DialogPage.MODE_CREATE : DialogPage.MODE_EDIT_SIMPLE);
    }

    //DS-8063 test fix for build conflict
    protected void setExplicitPage(DialogPage page) {
    	explicitPage = page;
    }
    
    public DialogPage getCurrentPage() {
        int i = tabFolder.getSelectionIndex();

        if (i == -1) {
            return null;
        } else {
            return (DialogPage) tabFolder.getItem(i).getData();
        }
    }

    public void setDirty(boolean dirty) {
    }

    public void addPage(DialogPage page) {
        CTabItem item = new CTabItem(tabFolder, SWT.NULL);
        item.setText(page.getTitle());
        item.setToolTipText(page.getDescription());
        item.setData(page);

        page.setContainer(this);
        page.setEditMode(editMode);
        page.createControl(tabFolder);
        item.setControl(page.getControl());
    }

    public boolean close() {
        Iterator it = pages.iterator();

        while (it.hasNext()) {
            DialogPage page = (DialogPage) it.next();
            page.dispose();
        }

        return super.close();
    }

    public void updateButtons() {
        Button ok = getButton(IDialogConstants.OK_ID);

        if (ok != null) {
            Iterator it = pages.iterator();

            while (it.hasNext()) {
                DialogPage page = (DialogPage) it.next();
                int message = page.getMessageType();

                if (message == IMessageProvider.ERROR) {
                    ok.setEnabled(false);
                    return;
                }
            }

            ok.setEnabled(true);
        }
    }

    public void updateMessage() {
        setMessage(
            getCurrentPage().getMessage(), getCurrentPage().getMessageType());
    }

    public void updateTitleBar() {
        setTitle(getCurrentPage().getTitle());
    }

    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        String name = model.getName();
        newShell.setText(name == null ? "" : name);
    }

    protected Control createDialogArea(Composite parent) {
        tabFolder = new CTabFolder(parent, SWT.TOP);
        tabFolder.setBackground(WIDGET_FACTORY.getBackgroundColor());
        tabFolder.setForeground(WIDGET_FACTORY.getForegroundColor());

        DialogPagesFactory pfactory = new AllPagesFactory();
        pfactory.addPages(model, pages);
        
        if(explicitPage!=null){
        	testPages();
        }

        Iterator it = pages.iterator();
        
        while (it.hasNext()) {
            DialogPage page = (DialogPage) it.next();
            addPage(page);
        }

        tabFolder.addSelectionListener(
            new SelectionAdapter() {
                public void widgetSelected(SelectionEvent e) {
                    updateMessage();
                    updateTitleBar();
                }
            });

        tabFolder.setSelection(0);
        updateButtons();
        updateMessage();
        updateTitleBar();
        return tabFolder;
    }

	private void testPages() {
		int index = -1;
		for (Object obj : pages) {
			if(obj.getClass().getSimpleName().equals("T24MdfDocPage")) {
				index = pages.lastIndexOf(obj);
			}
		} 
		if(index >= 0) {
			pages.remove(index);
			pages.add(explicitPage);
		}
	}

    protected void okPressed() {
        Iterator it = pages.iterator();

        while (it.hasNext()) {
            DialogPage page = (DialogPage) it.next();
            page.doSave(model);
        }

        super.okPressed();
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
