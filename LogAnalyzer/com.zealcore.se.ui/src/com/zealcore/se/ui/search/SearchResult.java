package com.zealcore.se.ui.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.search.ui.ISearchResultListener;
import org.eclipse.search.ui.SearchResultEvent;

import com.zealcore.se.core.ifw.SearchQuery;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.ui.SeUiPlugin;

public class SearchResult implements ISearchResult {

    private List<Object> data;

    private final ISearchQuery query;
    
    private static SearchResult searchResultInstance;
    
    private List<ISearchResultListener> listeners;

    public SearchResult(final ISearchQuery query, final Collection<? super IObject> inputData) {
        this.data = new ArrayList<Object>(inputData);
        this.query = query;
        searchResultInstance = this;
        listeners = new ArrayList<ISearchResultListener>();
    }

    public void addListener(final ISearchResultListener l) {
        synchronized (listeners) {
            listeners.add(l);
        }
    }
    
    public static SearchResult getInstance(){
    	return searchResultInstance;
    }

    public ImageDescriptor getImageDescriptor() {
        return SeUiPlugin.imageDescriptorFromPlugin(SeUiPlugin.PLUGIN_ID, "icons/old_logfilesmall.gif");
    }

    public String getLabel() {
    	String label;
    	if(this.data.size() > 0){
    	SearchQuery.SearchInfo info = ((UISearchQuery)query).getSearchInfo();
    		label = this.query.getLabel() + " ( "  
			+ (info.getCurrentHit().getTotalEventCount() - this.data.size() + 1) 
			+ " to " + info.getCurrentHit().getTotalEventCount()
			+ " out of " + info.getTotalEvents()
			+ " )"; 
    	}else{
    		label = this.query.getLabel() + " (Not Found)";
    	}
    	
    	return label; 
    }
    
    
    //Get the current search result page range.
    public String getMenuLabel(){
    	long start ;
    	long end ;
    	String separator;
    	if(this.data.size() > 0){
            SearchQuery.SearchInfo info = ((UISearchQuery)query).getSearchInfo();
            start = info.getCurrentHit().getTotalEventCount() - this.data.size() + 1; 
            end = Long.valueOf(info.getCurrentHit().getTotalEventCount());
            separator = "...";
    	}else{
    		return null;
    	}
    	return (""+ start + separator + end);
    }

    public ISearchQuery getQuery() {
        return this.query;
    }

    public String getTooltip() {
        return "Search";
    }

    public void removeListener(final ISearchResultListener l) {
        synchronized (listeners) {
            listeners.remove(l);
        }
    }

    public List<Object> getItems() {
        return this.data;

    }

    @Override
    public String toString() {
        return getLabel();
    }

    
    public void setData(final List<Object> data) {
        this.data = data;
    }

    public void notifySearchResultListener() {
        if (listeners == null) {
            return;
        }
        synchronized (listeners) {
            SearchResultEvent event = new LogEventSearchResultEvent(this);
            for (Iterator<ISearchResultListener> iterator = listeners.iterator(); iterator.hasNext();) {
                ISearchResultListener type = (ISearchResultListener) iterator.next();
                
                type.searchResultChanged(event);
            }
		}
    }

    private class LogEventSearchResultEvent extends SearchResultEvent {

        private static final long serialVersionUID = 1L;

        protected LogEventSearchResultEvent(ISearchResult searchResult) {
            super(searchResult);
        }
    }
}
