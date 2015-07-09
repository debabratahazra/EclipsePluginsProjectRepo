package com.odcgroup.mdf.editor.ui.dialogs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;

import com.odcgroup.mdf.ecore.MdfModelElementImpl;
import com.odcgroup.mdf.editor.MdfCore;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.security.MdfPermission;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.security.DSAuthorizationResult;
import com.odcgroup.workbench.security.SecurityCore;
import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;
import com.odcgroup.workbench.ui.help.OfsHelpHelper;


/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class MdfPropertySheetPage implements IPropertySheetPage,
        DialogPageContainer {

    private final EditingDomain domain;
    private final WidgetFactory WIDGET_FACTORY = new EditorWidgetFactory();
    private CTabFolder tabFolder;
    private IWorkbenchPart workbenchPart;
    private List<DialogPage> pages = new ArrayList<DialogPage>();
    private MdfModelElement model;
    private boolean dirty = false;
    private boolean readOnly = false;

    public MdfPropertySheetPage(EditingDomain domain) {
        this.domain = domain;
    }

    /**
     * @see org.eclipse.ui.part.IPage#setActionBars(org.eclipse.ui.IActionBars)
     */
    public void setActionBars(IActionBars actionBars) {
    	actionBars.getToolBarManager().add(OfsHelpHelper.createHelpAction(IOfsHelpContextIds.DOMAIN_PROPERTIES));
    }

    /**
     * @see org.eclipse.ui.part.IPage#getControl()
     */
    public Control getControl() {
        return tabFolder;
    }

    /**
     * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPageContainer#getCurrentPage()
     */
    public DialogPage getCurrentPage() {
        int i = tabFolder.getSelectionIndex();

        if (i == -1) {
            return null;
        } else {
            return (DialogPage) tabFolder.getItem(i).getData();
        }
    }

    /**
     * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPageContainer#setDirty(boolean)
     */
    public void setDirty(boolean dirty) {
    	MdfModelElementImpl obj =(MdfModelElementImpl)model; 
    	if (!domain.isReadOnly(obj.eResource())) {
    		this.dirty = dirty;
    	} else {
    		this.dirty = false;
    	}
    }

    /**
     * @return
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * @see org.eclipse.ui.part.IPage#setFocus()
     */
    public void setFocus() {
        tabFolder.setFocus();
    }

    /**
     * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPageContainer#getShell()
     */
    public Shell getShell() {
        return workbenchPart.getSite().getShell();
    }

    /**
     * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPageContainer#getWidgetFactory()
     */
    public WidgetFactory getWidgetFactory() {
        return WIDGET_FACTORY;
    }

    /**
     * @see org.eclipse.ui.part.IPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        tabFolder = new CTabFolder(parent, SWT.BOTTOM);
        tabFolder.setBackground(WIDGET_FACTORY.getBackgroundColor());
        tabFolder.setForeground(WIDGET_FACTORY.getForegroundColor());
        setVisible(tabFolder, false);

        tabFolder.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                updateMessage();
                updateTitleBar();
                if (isDirty()) {
                	savePages();
                }
            }
        });
    }

    /**
     * @see org.eclipse.ui.part.IPage#dispose()
     */
    public void dispose() {
        disposePages();
        tabFolder.dispose();
        if(WIDGET_FACTORY !=null){
        	WIDGET_FACTORY.dispose();
        }
    }

    /**
     * 
     */
    public void refresh() {
        CTabItem tab = tabFolder.getSelection();
        Class selectedPage = (tab != null) ? tab.getData().getClass() : null;

        disposePages();
        addPages(selectedPage);
    }

    /**
     * @see org.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.IWorkbenchPart,
     *      org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IWorkbenchPart part, ISelection selection) {
        workbenchPart = part;
        savePages();

        Object newsel = ((IStructuredSelection) selection).getFirstElement();
        if (newsel != model) {
            model = null;
            if (newsel instanceof MdfModelElement && newsel instanceof EObject) {
                model = (MdfModelElement) newsel;
            }
            refresh();
        }
        if (newsel instanceof MdfModelElementImpl) {
        	MdfModelElementImpl obj =(MdfModelElementImpl)newsel;

        	// make the properties pages disabled for dependency models
        	
			if (domain.isReadOnly(obj.eResource()) || isReadOnly() || !hasEditPermissions(obj)) {
		        for (DialogPage page : pages) {
		            // check for the page can be disabled
		            if(page.getControl()!=null && !page.isAlwaysEnabled()) {
		            	page.setEditMode(DialogPage.MODE_READ_ONLY);
		            }
		        }
			}			
        }
    }

    // DS-1773 
	/**
	 * @param obj
	 * @return
	 */
	private boolean hasEditPermissions(MdfModelElementImpl obj) {
    	URIConverter converter = domain.getResourceSet().getURIConverter();
    	if(converter instanceof ModelURIConverter) {
    		ModelURIConverter modelUriConverter = (ModelURIConverter) converter;
    		IProject project = modelUriConverter.getOfsProject().getProject();
    		if(obj.eResource()==null) return false;
    		URI uri = obj.eResource().getURI().appendFragment(obj.eResource().getURIFragment(obj));
        	if(SecurityCore.getAuthorizationManager().permissionGranted(project, uri, MdfPermission.PROPERTY_SHEET_EDIT, null)==DSAuthorizationResult.REJECTED) {
        		return false;
        	}
    	}
    	return true;
	}

	/**
     * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPageContainer#updateButtons()
     */
    public void updateButtons() {
    }

    /**
     * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPageContainer#updateMessage()
     */
    public void updateMessage() {
        CTabItem[] items = tabFolder.getItems();

        for (int i = 0; i < items.length; i++) {
            DialogPage page = (DialogPage) items[i].getData();
            switch (page.getMessageType()) {
                case IMessageProvider.ERROR:
                    items[i].setImage(MdfPlugin.getDefault().getImage(
                            MdfCore.ICON_ERROR));
                    break;

                case IMessageProvider.WARNING:
                    items[i].setImage(MdfPlugin.getDefault().getImage(
                            MdfCore.ICON_WARNING));
                    break;

                default:
                    items[i].setImage(null);
            }
        }
    }

    /**
     * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPageContainer#updateTitleBar()
     */
    public void updateTitleBar() {
    }

    /**
     * creates a tab item for each of the dialog page
     * 
     * @param page
     * @return
     */
    private CTabItem addPage(DialogPage page) {
        CTabItem item = new CTabItem(tabFolder, SWT.NULL);
        item.setText(page.getTitle());
        item.setToolTipText(page.getDescription());
        item.setData(page);
        page.setEditMode(DialogPage.MODE_EDIT_SIMPLE);

        TitleAreaPageContainer container = new TitleAreaPageContainer(this,
                page);
        item.setControl(container.createControl(tabFolder));

        return item;
    }

    /**
     * add tabs to the tabbed pane
     * 
     * @param selectedPage
     */
    private void addPages(Class selectedPage) {
        if (model != null) {
            DialogPagesFactory pfactory = new AllPagesFactory();
            pfactory.addPages(model, pages);

            boolean selected = false;
            for (DialogPage page : pages) {
                CTabItem item = addPage(page);
                if (page.getClass() == selectedPage) {
                    tabFolder.setSelection(item);
                    selected = true;
                }
            }

            if (!selected) {
                tabFolder.setSelection(0);
            }

            setVisible(tabFolder, !pages.isEmpty());
            updateButtons();
            updateMessage();
            updateTitleBar();
        } else {
            setVisible(tabFolder, false);
        }
    }

    /**
     * dispose all the tabs and associated dialog pages
     */
    private void disposePages() {
        setVisible(tabFolder, false);

        CTabItem[] items = tabFolder.getItems();

        for (int i = 0; i < items.length; i++) {
            items[i].dispose();
        }

        Iterator it = pages.iterator();

        while (it.hasNext()) {
            DialogPage page = (DialogPage) it.next();
            page.dispose();
        }

        pages.clear();
    }

    /**
     * @param control
     * @param visible
     */
    private void setVisible(Control control, boolean visible) {
        if (!control.isDisposed()) control.setVisible(visible);
    }

    /**
     * call save on all the dialog pages in the tabbed pane
     */
    public void savePages() {
        if ((model != null) && dirty) {
            domain.getCommandStack().execute(
                    new ChangeCommand((Notifier) model) {

                        public String getLabel() {
                            return "Editing";
                        }

                        protected void doExecute() {
                            for (DialogPage page : pages) {
                                page.doSave(model);
                            }
                            dirty = false;
                        }

                        public Collection<?> getAffectedObjects() {
                            return Collections.singleton(notifier);
                        }

                    });

        }
    }

	/**
	 * @return
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

}
