package com.zealcore.se.ui.views;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.zealcore.se.core.model.IDuration;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.ui.SeUiPlugin;
import com.zealcore.se.ui.editors.ILogViewInput;
import com.zealcore.se.ui.internal.TimeEvent;
import com.zealcore.se.ui.search.NamedItemContentProvider;
import com.zealcore.se.ui.search.NamedItemLabelProvider;
import com.zealcore.se.ui.search.SearchResult;

public class SearchResultViewer extends ViewPart {

    public static final String VIEW_ID = "com.zealcore.se.ui.views.SearchResultViewer";

    private ILogViewInput input;

    private TreeViewer viewer;

    public SearchResultViewer() {}

    public void synch(final TimeEvent source) {
    // TODO Show the closest event?
    }

    public ILogViewInput getClusterInput() {
        return input;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IViewSetView#setInput(com.zealcore.se.ui.views.ILogViewInput)
     */
    public void setInput(final ILogViewInput input) {
        this.input = input;
        setTitleToolTip(input.toString());
    }

    @Override
    public void createPartControl(final Composite parent) {
        viewer = new TreeViewer(parent, SWT.SINGLE);
        viewer.setLabelProvider(new NamedItemLabelProvider());
        viewer.setContentProvider(new NamedItemContentProvider());

        viewer.addSelectionChangedListener(new ISelectionChangedListener() {

            public void selectionChanged(final SelectionChangedEvent event) {
                final IStructuredSelection selection = (IStructuredSelection) event
                        .getSelection();
                if (selection.getFirstElement() instanceof ILogEvent) {
                    final ILogEvent abstractLogEvent = (ILogEvent) selection
                            .getFirstElement();
                    if (input != null) {
                        input.getTimeCluster().setCurrentTime(
                                abstractLogEvent.getTs());
                    }
                }

                if (selection.getFirstElement() instanceof IDuration) {
                    final IDuration d = (IDuration) selection.getFirstElement();
                    if (input != null) {
                        input.getTimeCluster().setCurrentTime(d.getStartTime());
                    }
                }

            }

        });

        getViewSite().setSelectionProvider(viewer);
    }

    @Override
    public void setFocus() {}

    public void setSearchResult(final SearchResult result) {
        viewer.setInput(result.getItems());

    }

    public void setQuery(final ISearchQuery query) {
        query.run(null);
        final ISearchResult searchResult = query.getSearchResult();
        if (searchResult instanceof SearchResult) {
            final SearchResult result = (SearchResult) searchResult;
            setSearchResult(result);

        } else {
            SeUiPlugin
                    .logError(
                            "The result returned by the search were incompatible with this viewer",
                            new IllegalArgumentException("Bad Query"));
        }

    }

    public void refresh() {}
    // TODO Save AbstractState and Load AbstractState
}
