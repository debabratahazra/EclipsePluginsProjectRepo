package com.zealcore.se.ui.editors;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.part.EditorPart;

import com.zealcore.se.core.IPersistable;
import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.services.IServiceProvider;
import com.zealcore.se.ui.ISynchable;
import com.zealcore.se.ui.internal.FilterRegistry;
import com.zealcore.se.ui.internal.TimeEvent;
import com.zealcore.se.ui.internal.FilterRegistry.IUiFilter;

public final class LogsetEditor extends EditorPart implements ISynchable,
        ISelectionProvider, ISelectionChangedListener, IPersistable {

    private static final String SEPARATOR = "###";

    private static final String TAG_FILTERS = "com.zealcore.sd.browserfilters";

    public static final String EDITOR_ID = "com.zealcore.se.ui.editors.LogsetEditor";

    protected static final long RESOURCE_REFRESH_DELAY = 1000;

    private Composite zealBar;

    private Set<IUiFilter> filters = new LinkedHashSet<IUiFilter>();

    private Composite container;

    private ILogsetBrowser logBrowser;

    @Override
    public void doSave(final IProgressMonitor monitor) {}

    @Override
    public void doSaveAs() {}

    @Override
    public void init(final IEditorSite site, final IEditorInput input)
            throws PartInitException {
        setSite(site);
        if (input instanceof ILogViewInput) {
            setInput(input);
        } else {
            throw new PartInitException(
                    "Input must be of type ILogViewInput. Input= " + input);
        }
        if (getInput().exists()) {
            setPartName(getInput().getName());
            getInput().getTimeCluster().addSynchable(this);
            final String browserId = getInput().getBrowserId();
            logBrowser = BrowserFactory.valueOf(browserId);
            logBrowser.setEditorPart(this);

            setTitleImage(logBrowser.getImageDescriptor().createImage());
            getInput().addPersitable(this);
            getInput().addPersitable(logBrowser);
        } else {
            logBrowser = new NullBrowser("Unable to create browser "
                    + getInput().getBrowserId()
                    + " because EditorInput doesn't exist");
        }
    }

    public ILogViewInput getInput() {
        return (ILogViewInput) getEditorInput();
    }

    @Override
    public boolean isDirty() {
        return false;
    }

    @Override
    public boolean isSaveAsAllowed() {
        return false;
    }

    @Override
    public void createPartControl(final Composite parent) {
        container = parent;
        createZealBar();
        final Composite viewPort = new Composite(parent, SWT.NULL);
        viewPort.setLayout(new FillLayout());
        parent.setLayout(new FormLayout());

        FormData formData = new FormData();
        formData.left = new FormAttachment(0, 0);
        formData.right = new FormAttachment(100, 0);
        getZealBar().setLayoutData(formData);

        formData = new FormData();
        formData.left = new FormAttachment(0, 0);
        formData.right = new FormAttachment(100, 0);
        formData.top = new FormAttachment(getZealBar());
        if (logBrowser instanceof GanttChart
                || logBrowser instanceof EventTimelineBrowser) {
            formData.bottom = new FormAttachment(90, 0);
        } else {
            formData.bottom = new FormAttachment(100, 0);
        }
        viewPort.setLayoutData(formData);

        getSite().setSelectionProvider(this);
        logBrowser.createControl(viewPort);
    }

    protected Composite createZealBar() {
        zealBar = new Composite(container, SWT.NULL);
        zealBar.setLayout(new RowLayout());
        return zealBar;
    }

    protected Composite getZealBar() {
        return zealBar;
    }

    protected IServiceProvider getServiceProvider() {
        return SeCorePlugin.getDefault();
    }

    protected void initState(final IMemento savedState) {
        return;
    }

    protected void saveViewState(final IMemento savedState) {
        return;
    }

    protected void setHelp(final Composite container, final String helpId) {
        if (getSite() == null) {
            return;
        }
        if (getSite().getWorkbenchWindow() == null) {
            return;
        }
        final IWorkbench workbench = getSite().getWorkbenchWindow()
                .getWorkbench();
        if (workbench != null && workbench.getHelpSystem() != null) {
            final IWorkbenchHelpSystem helpSystem = workbench.getHelpSystem();
            helpSystem.setHelp(container, helpId);
        }
    }

    /**
     * Convenience method for setting the current time.
     * 
     * @param time
     *                the new current time
     */
    protected void setCurrentTime(final long time) {
        if (getInput() != null) {
            getInput().getTimeCluster().setCurrentTime(time);
        }
    }

    @Override
    public void dispose() {
        if (getInput() != null) {
            getInput().getTimeCluster().removeSynchable(this);
            getTitleImage().dispose();
        }
        if (logBrowser != null) {
            logBrowser.dispose();
        }
        super.dispose();
    }

    public void selectionChanged(final IWorkbenchPart part,
            final ISelection selection) {}

    /**
     * Sets the filters.
     * 
     * @param filters
     *                the filters
     */
    public void setFilters(final Set<IUiFilter> filters) {
        this.filters = filters;
    }

    /**
     * Gets the filters.
     * 
     * @return the filters
     */
    public Set<IUiFilter> getFilters() {
        return filters;
    }

    @Override
    public void setFocus() {
        logBrowser.setFocus();
    }

    public void synch(final TimeEvent source) {
        logBrowser.synch(source);
    }

    public void refresh() {
        logBrowser.refresh();
    }

    public void addSelectionChangedListener(
            final ISelectionChangedListener listener) {
        logBrowser.addSelectionChangedListener(listener);
    }

    public void removeSelectionChangedListener(
            final ISelectionChangedListener listener) {
        logBrowser.removeSelectionChangedListener(listener);
    }

    public void selectionChanged(final SelectionChangedEvent event) {
        logBrowser.selectionChanged(event);
    }

    public ISelection getSelection() {
        return logBrowser.getSelection();
    }

    public void setSelection(final ISelection selection) {
        logBrowser.setSelection(selection);
    }

    public void init(final IMemento savedState) {
        final String serial = savedState.getString(LogsetEditor.TAG_FILTERS);
        if (serial != null) {
            final Set<IUiFilter> storedFilters = new LinkedHashSet<IUiFilter>();
            for (final String id : serial.split(LogsetEditor.SEPARATOR)) {
                for (final IUiFilter filter : FilterRegistry.getInstance()
                        .getLiveFilters()) {
                    if (filter.getId().equals(id)) {
                        storedFilters.add(filter);
                        break;
                    }
                }
            }
            setFilters(storedFilters);
        }
    }

    public void saveState(final IMemento savedState) {
        final StringBuilder serial = new StringBuilder();
        for (final IUiFilter filter : getFilters()) {
            serial.append(filter.getId() + LogsetEditor.SEPARATOR);
            savedState.putString(LogsetEditor.TAG_FILTERS, serial.toString());
        }
    }

    public ILogsetBrowser getLogsetBrowser() {
        return logBrowser;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object getAdapter(final Class adapter) {
        final ILogsetBrowser browser = getLogsetBrowser();
        if (adapter.isInstance(browser)) {
            return adapter.cast(browser);
        }
        return super.getAdapter(adapter);
    }
}
