package com.odcgroup.mdf.editor.ui.views;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.PropertyDialogAction;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;

import com.odcgroup.mdf.editor.MdfCore;
import com.odcgroup.mdf.editor.model.MdfProject;
import com.odcgroup.mdf.editor.model.MdfProjectRepository;
import com.odcgroup.mdf.editor.ui.actions.MdfAction;
import com.odcgroup.mdf.editor.ui.actions.MdfActionGroup;
import com.odcgroup.mdf.editor.ui.actions.MdfActionListener;
import com.odcgroup.mdf.editor.ui.dialogs.EditionSupport;
import com.odcgroup.mdf.editor.ui.dialogs.EditionSupportFactory;
import com.odcgroup.mdf.editor.ui.filters.ClassFilter;
import com.odcgroup.mdf.editor.ui.filters.DatasetFilter;
import com.odcgroup.mdf.editor.ui.filters.EnumFilter;
import com.odcgroup.mdf.editor.ui.filters.MainClassFilter;
import com.odcgroup.mdf.editor.ui.viewers.MdfPropertySheetPage;
import com.odcgroup.mdf.editor.ui.viewers.MdfTreeViewer;
import com.odcgroup.mdf.metamodel.MdfModelElement;


/**
 * The MDF repository view
 *
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini </a>
 * @version 1.0
 */
public class MdfProjectsView extends ViewPart implements MdfActionListener,
    ISelectionProvider, ISelectionChangedListener {
    private static final Logger LOGGER =
        Logger.getLogger(MdfProjectsView.class);
    private ClassFilter classFilter = null;
    private MainClassFilter mainClassFilter = null;
    private DatasetFilter datasetFilter = null;
    private EditionSupport editionSupport;
    private EnumFilter enumFilter = null;
    private final List selectionListeners = new Vector();
    private MdfActionGroup actionGroup = null;
    private MdfTreeViewer viewer;
    private PageBook pageBook = null;
    private PropertyDialogAction propertyDialogAction = null;
    private PropertySheetPage properties = null;

    /**
     * The constructor.
     */
    public MdfProjectsView() {
        super();
        actionGroup = new MdfActionGroup(this);
    }

    /**
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    public Object getAdapter(Class adapter) {
        if (IPropertySheetPage.class == adapter) {
            if (properties == null) {
                properties = new MdfPropertySheetPage();
            }

            return properties;
        } else if (EditionSupport.class == adapter) {
            return getEditionSupport();
        }

        return super.getAdapter(adapter);
    }

    /**
     * Passing the focus request to the viewer's control.
     */
    public void setFocus() {
        pageBook.setFocus();
    }

    public void setSelection(ISelection selection) {
        if (viewer != null) {
            viewer.setSelection(selection, true);
        }
    }

    public ISelection getSelection() {
        return (viewer == null) ? StructuredSelection.EMPTY
                                : viewer.getSelection();
    }

    public void addSelectionChangedListener(ISelectionChangedListener listener) {
        selectionListeners.add(listener);
    }

    /**
     * This is a callback that will allow us to create the viewer and initialize
     * it.
     *
     * @param parent
     *            parent control of the view
     */
    public void createPartControl(final Composite parent) {
        pageBook = new PageBook(parent, SWT.NONE);

        Label label = new Label(pageBook, 16576);
        label.setText("Initializing...");
        viewer = new MdfTreeViewer(pageBook);
        pageBook.showPage(label);
        getSite().setSelectionProvider(this);

        Display display = Display.getCurrent();
        display.asyncExec(
            new Runnable() {
                public void run() {
                    MdfProjectRepository projects =
                        MdfProjectRepository.getInstance();
                    projects.init();
                    viewer.setInput(projects);
                    viewer.expandToLevel(2);
                    viewer.addSelectionChangedListener(MdfProjectsView.this);
                    hookContextMenu();
                    hookOpenHandler();
                    createFiltersAndSorters();
                    contributeToActionBars();
                    pageBook.showPage(viewer.getControl());
                }
            });
    }

    /**
     * @see org.eclipse.ui.IWorkbenchPart#dispose()
     */
    public void dispose() {
        if (properties != null) {
            properties.dispose();
        }

        super.dispose();
    }

    /**
     * @see com.odcgroup.mdf.editor.actions.MdfActionListener#onAction(com.odcgroup.mdf.editor.actions.MdfAction)
     */
    public void onAction(MdfAction action) {
        String id = action.getId();

        if (id.equals(MdfCore.ACTION_HIDE_CLASSES)) {
            if (action.isChecked()) {
                viewer.addFilter(classFilter);
            } else {
                viewer.removeFilter(classFilter);
            }
        } else if (id.equals(MdfCore.ACTION_HIDE_MAIN_CLASSES)) {
            if (action.isChecked()) {
                viewer.addFilter(mainClassFilter);
            } else {
                viewer.removeFilter(mainClassFilter);
            }
        } else if (id.equals(MdfCore.ACTION_HIDE_ENUMS)) {
            if (action.isChecked()) {
                viewer.addFilter(enumFilter);
            } else {
                viewer.removeFilter(enumFilter);
            }
        } else if (id.equals(MdfCore.ACTION_HIDE_DATASETS)) {
            if (action.isChecked()) {
                viewer.addFilter(datasetFilter);
            } else {
                viewer.removeFilter(datasetFilter);
            }
        }
    }

    public void removeSelectionChangedListener(
        ISelectionChangedListener listener) {
        selectionListeners.remove(listener);
    }

    public void selectionChanged(SelectionChangedEvent event) {
        Iterator it = selectionListeners.iterator();

        while (it.hasNext()) {
            ISelectionChangedListener listener =
                (ISelectionChangedListener) it.next();
            listener.selectionChanged(
                new SelectionChangedEvent(this, event.getSelection()));
        }
    }

    private synchronized EditionSupport getEditionSupport() {
        if (editionSupport == null) {
            // DS-1349
            editionSupport = EditionSupportFactory.INSTANCE(getSite().getShell());
        }

        return editionSupport;
    }

    private void contributeToActionBars() {
        MdfActionGroup.setGlobalActionHandler(
            getViewSite().getActionBars(), actionGroup);

        IActionBars bars = getViewSite().getActionBars();
        actionGroup.fillActionBars(bars);
        bars.updateActionBars();
    }

    private void createFiltersAndSorters() {
        classFilter = new ClassFilter();
        mainClassFilter = new MainClassFilter();
        enumFilter = new EnumFilter();
        datasetFilter = new DatasetFilter();
    }

    private void fillContextMenu(IMenuManager menu) {
        MenuManager newMenu = new MenuManager("&New", "new");
        newMenu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

        menu.add(newMenu);
        menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
        menu.add(new Separator());

        IStructuredSelection selection = (IStructuredSelection) getSelection();

        if (selection.size() == 1) {
            Object sel = selection.getFirstElement();

            if (sel instanceof MdfProject) {
                propertyDialogAction.selectionChanged(selection);
                menu.add(propertyDialogAction);
            }
        }
    }

    private void hookContextMenu() {
        propertyDialogAction = new PropertyDialogAction(getViewSite(), viewer);

        MenuManager menuMgr = new MenuManager("#PopupMenu");
        menuMgr.setRemoveAllWhenShown(true);

        menuMgr.addMenuListener(
            new IMenuListener() {
                public void menuAboutToShow(IMenuManager manager) {
                    fillContextMenu(manager);
                }
            });

        Menu menu = menuMgr.createContextMenu(viewer.getControl());
        viewer.getControl().setMenu(menu);
        getSite().registerContextMenu(menuMgr, viewer);
    }

    private void hookOpenHandler() {
        viewer.addOpenListener(
            new IOpenListener() {
                public void open(OpenEvent e) {
                    onOpenSelection();
                }
            });
    }

    private void onOpenSelection() {
        ISelection selection = getSelection();
        Object obj = ((IStructuredSelection) selection).getFirstElement();

        if ((obj instanceof MdfModelElement) && !(obj instanceof MdfProject)) {
            try {
                getEditionSupport().openModelEditor((MdfModelElement) obj);
            } catch (PartInitException e) {
                LOGGER.error(e, e);
            }
        }
    }
}
