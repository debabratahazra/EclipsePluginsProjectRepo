package com.zealcore.se.ui.editors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorSite;

import com.zealcore.se.core.IFilter;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.services.IServiceProvider;
import com.zealcore.se.ui.ITimeCluster;
import com.zealcore.se.ui.internal.FilterRegistry.IUiFilter;

public abstract class AbstractLogsetBrowser implements ILogsetBrowser {

    private LogsetEditor editor;

//    private boolean isLog = true;

    public final void setEditorPart(final LogsetEditor editor) {
        this.editor = editor;

    }

//    public boolean isLogarithmic() {
//        return isLog;
//    }

    public final ILogViewInput getInput() {
        return editor.getInput();
    }

    protected IServiceProvider getServiceProvider() {
        return editor.getServiceProvider();
    }

    public IEditorSite getSite() {
        return editor.getEditorSite();
    }

    protected Logset getLogset() {
        return Logset.valueOf(getInput().getLog().getId());
    }

    protected Composite getZealBar() {
        return editor.getZealBar();
    }

    protected String getTitleToolTip() {
        return editor.getTitleToolTip();
    }

    protected String getTitle() {
        return editor.getTitle();
    }

    protected void setCurrentTime(final long ts) {
        editor.setCurrentTime(ts);

    }

    public void dispose() {}

    protected final void setHelp(final Composite drawingArea,
            final String helpId) {
        editor.setHelp(drawingArea, helpId);
    }

    /**
     * Gets the filters.
     * 
     * @return the filters
     */
    protected Set<IUiFilter> getFilters() {
        return editor.getFilters();
    }

    /**
     * Filters a collection.
     * 
     * @param filterSet
     *                the filter set
     * @param collection
     *                the collection
     * 
     * @return a new filtered list
     */
    @SuppressWarnings("unchecked")
    protected <T extends IObject> List<T> filterCollection(
            final Collection<T> collection,
            final Set<? extends IFilter<IObject>> filterSet) {
        if (collection == null) {
            return Collections.EMPTY_LIST;
        }
        final List<T> visible = new ArrayList<T>(collection.size());
        if (filterSet == null) {
            visible.addAll(collection);
            return visible;
        }
        for (final T e : collection) {
            if (this.passesFilters(e, filterSet)) {
                visible.add(e);
            }
        }
        return visible;
    }

    /**
     * Checks if element passes filters.
     * 
     * @param element
     *                the element
     * @param filterSet
     *                the filter set
     * 
     * @return true, if passes filters
     */
    private <T extends IObject> boolean passesFilters(final T element,
            final Set<? extends IFilter<IObject>> filterSet) {
        for (final IFilter<IObject> f : filterSet) {
            if (!f.filter(element)) {
                return false;
            }
        }
        return true;
    }

    private final Set<ISelectionChangedListener> selectionListeners = new HashSet<ISelectionChangedListener>();

    private IStructuredSelection selection;

    public final void addSelectionChangedListener(
            final ISelectionChangedListener listener) {
        selectionListeners.add(listener);

    }

    public final IStructuredSelection getSelection() {
        return selection == null ? StructuredSelection.EMPTY : selection;
    }

    public final void removeSelectionChangedListener(
            final ISelectionChangedListener listener) {
        selectionListeners.add(listener);

    }

    public final void setSelection(final ISelection selection) {

        if (getSite().getSelectionProvider() == null) {
            getSite().setSelectionProvider(this);
        }
        this.selection = (IStructuredSelection) selection;

        final SelectionChangedEvent event = new SelectionChangedEvent(this,
                getSelection());

        for (final ISelectionChangedListener l : selectionListeners) {
            if (l != this) {
                l.selectionChanged(event);
            }
        }
    }

    protected final void setSelection(final Object o) {
        Object object = o;
        if (object == null) {
            // Empty selection
            object = new Object();
        }
        final StructuredSelection sel = new StructuredSelection(object);
        this.setSelection(sel);
    }

    public void selectionChanged(final SelectionChangedEvent event) {

    }

    /**
     * 
     * @return the time cluster time and ensures that it is within the bounds.
     */
    protected long getVisibleTime() {
        final ITimeCluster cluster = getInput().getTimeCluster();
        final long currentTime = cluster.getCurrentTime();
        // Get the current time - ensure within bounds
        long time = currentTime > cluster.getMax() ? cluster.getMax()
                : currentTime;
        time = time < cluster.getMin() ? cluster.getMin() : time;
        return time;
    }

    public void gotoNextLikeSelected() {}

    public void gotoPreviousLikeSelected() {}

}
