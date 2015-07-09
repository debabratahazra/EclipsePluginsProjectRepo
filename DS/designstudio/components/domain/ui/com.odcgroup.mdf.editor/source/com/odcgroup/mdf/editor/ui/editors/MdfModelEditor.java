package com.odcgroup.mdf.editor.ui.editors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.editor.MdfCore;
import com.odcgroup.mdf.editor.model.MdfMarkerUtils;
import com.odcgroup.mdf.editor.model.MdfProjectRepository;
import com.odcgroup.mdf.editor.model.MdfUtility;
import com.odcgroup.mdf.editor.model.ModelChangedEvent;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.AllPagesFactory;
import com.odcgroup.mdf.editor.ui.dialogs.DialogPage;
import com.odcgroup.mdf.editor.ui.dialogs.DialogPageContainer;
import com.odcgroup.mdf.editor.ui.dialogs.DialogPagesFactory;
import com.odcgroup.mdf.editor.ui.dialogs.EditionSupportFactory;
import com.odcgroup.mdf.editor.ui.dialogs.EditorWidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.TitleAreaPageContainer;
import com.odcgroup.mdf.editor.ui.viewers.MdfOutlinePage;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;


/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class MdfModelEditor extends EditorPart implements PaintListener,
    ISelectionProvider, DialogPageContainer {
    private static final Logger LOGGER = Logger.getLogger(MdfModelEditor.class);
    public final WidgetFactory WIDGET_FACTORY = new EditorWidgetFactory();
    private CTabFolder tabFolder;
    private List pages;
    private MdfModelElement model;
    private MdfOutlinePage outline = null;
    private boolean dirty = false;

    /**
     * Constructor for MdfModelEditor
     */
    public MdfModelEditor() {
        super();
    }

    /**
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    public Object getAdapter(Class required) {
        if (IContentOutlinePage.class.equals(required)) {
            if (outline == null) {
                outline = new MdfOutlinePage(this);
            }

            return outline;
        }

        return super.getAdapter(required);
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
        this.dirty = dirty;
        this.firePropertyChange(PROP_DIRTY);
    }

    /**
     * @see org.eclipse.ui.ISaveablePart#isDirty()
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * @see org.eclipse.ui.IWorkbenchPart#setFocus()
     */
    public void setFocus() {
    }

    /**
     * @see org.eclipse.ui.ISaveablePart#isSaveAsAllowed()
     */
    public boolean isSaveAsAllowed() {
        return false;
    }

    public void setSelection(ISelection selection) {
        Object obj = ((IStructuredSelection) selection).getFirstElement();

        if (obj instanceof MdfModelElement) {
            try {
                // DS-1349
            	EditionSupportFactory.INSTANCE(getEditorSite().getShell()).openModelEditor(
                    (MdfModelElement) obj);
            } catch (PartInitException e) {
                LOGGER.error(e, e);
                MdfCore.openError(getSite().getShell(), e);
            }
        }
    }

    public ISelection getSelection() {
        return new StructuredSelection(model);
    }

    public void addPage(DialogPage page) {
        CTabItem item = new CTabItem(tabFolder, SWT.NULL);
        item.setText(page.getTitle());
        item.setToolTipText(page.getDescription());
        item.setData(page);
        page.setEditMode(DialogPage.MODE_EDIT);

        TitleAreaPageContainer container =
            new TitleAreaPageContainer(this, page);
        item.setControl(container.createControl(tabFolder));
    }

    public void addSelectionChangedListener(ISelectionChangedListener listener) {
    }

    /**
     * @see org.eclipse.ui.IWorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    public void createPartControl(Composite parent) {
        tabFolder = new CTabFolder(parent, SWT.BOTTOM);
        tabFolder.setBackground(WIDGET_FACTORY.getBackgroundColor());
        tabFolder.setForeground(WIDGET_FACTORY.getForegroundColor());
        tabFolder.addPaintListener(this);
    }

    public void dispose() {
        if (outline != null) {
            outline.dispose();
        }

        super.dispose();
    }

    /**
     * @see org.eclipse.ui.ISaveablePart#doSave(org.eclipse.core.runtime.IProgressMonitor)
     */
    public void doSave(IProgressMonitor monitor) {
        Iterator it = pages.iterator();

        while (it.hasNext()) {
            DialogPage page = (DialogPage) it.next();
            String error = page.getErrorMessage();

            if (error != null) {
                MessageDialog.openError(
                    getSite().getShell(), "Error",
                    page.getTitle() + ": " + error);
                return;
            }
        }

        MdfDomainImpl domain = (MdfDomainImpl) MdfUtility.getDomain(model);

        try {
            monitor.beginTask(
                "Saving " + model.getQualifiedName(), pages.size() + 2);

            it = pages.iterator();

            while (it.hasNext()) {
                DialogPage page = (DialogPage) it.next();
                page.doSave(model);
                monitor.worked(1);
            }

            MdfUtility.doSave(
                domain.eResource(), new SubProgressMonitor(monitor, 1));
            monitor.worked(1);

            MdfProjectRepository.fireModelChangedEvent(
                this, model, ModelChangedEvent.ELEMENT_UPDATED,
                new SubProgressMonitor(monitor, 1));
            monitor.worked(1);
        } catch (CoreException e) {
            LOGGER.error(e, e);
            MdfCore.openError(getSite().getShell(), e);
        } finally {
            monitor.done();
        }

        setDirty(false);
    }

    /**
     * @see org.eclipse.ui.ISaveablePart#doSaveAs()
     */
    public void doSaveAs() {
    }

    /**
     * @see org.eclipse.ui.IEditorPart#gotoMarker(org.eclipse.core.resources.IMarker)
     */
    public void gotoMarker(IMarker marker) {
        try {
            if (
                marker.isSubtypeOf(MdfCore.MDF_MARKER) &&
                    (model instanceof MdfDomain)) {
                MdfName name 
                	= MdfNameFactory.createMdfName((String) marker.getAttribute(MdfMarkerUtils.MDF_NAME));
                
                if (name != null) {
                    String domainName = name.getDomain();

                    if ((domainName == null) || (domainName.length() == 0)) {
                        // -- The marker references the domain, let's
                        // continue...
                        return;
                    } else {
                        MdfDomain domain = (MdfDomain) model;
                        String localName = name.getLocalName();

                        int pos = localName.indexOf('#');

                        if (pos != -1) {
                            // -- The marker references a property or a value of
                            // -- an entity in the domain
                            String entityName = localName.substring(0, pos);
                            String propName = localName.substring(pos + 1);
                            MdfEntity entity = domain.getEntity(entityName);

                            if (entity == null)
                                entity = domain.getDataset(entityName);

                            if (entity instanceof MdfClass) {
                                model =
                                    ((MdfClass) entity).getProperty(propName);
                            } else if (entity instanceof MdfEnumeration) {
                                model =
                                    ((MdfEnumeration) entity).getValue(
                                        propName);
                            } else if (entity instanceof MdfDataset) {
                                model =
                                    ((MdfDataset) entity).getProperty(propName);
                            } else {
                                // -- We don't care...
                                return;
                            }
                        } else {
                            // -- The marker references an entity in the domain
                            model = domain.getEntity(localName);

                            if (model == null)
                                model = domain.getDataset(localName);
                        }
                    }

                    setInput(new MdfModelEditorInput(model));
                    setPartName(model.getQualifiedName().toString());
                    setTitleToolTip(model.getQualifiedName().toString());
                }
            }
        } catch (CoreException e) {
            LOGGER.error(e, e);
        }
    }

    /**
     * @see org.eclipse.ui.IEditorPart#init(org.eclipse.ui.IEditorSite,
     *      org.eclipse.ui.IEditorInput)
     */
    public void init(IEditorSite site, IEditorInput editorInput)
        throws PartInitException {
        if (editorInput instanceof MdfModelEditorInput) {
            model = ((MdfModelEditorInput) editorInput).getModel();
        }

        if (model == null) {
            throw new PartInitException("Invalid editor input specified");
        }

        setPartName(model.getQualifiedName().toString());
        setTitleToolTip(model.getQualifiedName().toString());

        setSite(site);
        setInput(editorInput);
    }

    /**
     * @see org.eclipse.swt.events.PaintListener#paintControl(org.eclipse.swt.events.PaintEvent)
     */
    public void paintControl(PaintEvent e) {
        if (pages == null) {
            pages = new ArrayList();

            DialogPagesFactory pfactory = new AllPagesFactory();
            pfactory.addPages(model, pages);

            Iterator it = pages.iterator();

            while (it.hasNext()) {
                DialogPage page = (DialogPage) it.next();
                addPage(page);
            }

            tabFolder.setSelection(0);
        }
    }

    public void removeSelectionChangedListener(
        ISelectionChangedListener listener) {
    }

    public Shell getShell() {
        return getSite().getShell();
    }

    public WidgetFactory getWidgetFactory() {
        return WIDGET_FACTORY;
    }

    public void updateButtons() {
    }

    public void updateMessage() {
    }

    public void updateTitleBar() {
    }
}
