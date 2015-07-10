/**
 * 
 */
package com.zealcore.se.ui.search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.swt.widgets.Display;

import com.zealcore.se.core.ISearchAdapter;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.ifw.SearchQuery;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.ui.SeUiPlugin;

public final class UISearchQuery implements ISearchQuery {

	private final ISearchAdapter adapter;

	private SearchResult result;

	private final Logset logset;

	private SearchQuery.SearchInfo searchInfo;

	private UISearchQuery uiSearchQuery;

	private List<IObject> data = null;

	public UISearchQuery(final Logset logset, final ISearchAdapter adapter) {
		this.logset = logset;
		this.adapter = adapter;
		this.searchInfo = null;
		this.uiSearchQuery = this;
	}

	public UISearchQuery(final Logset logset, final ISearchAdapter adapter,
			final SearchQuery.SearchInfo searchInfo) {

		this.logset = logset;
		this.adapter = adapter;
		this.searchInfo = searchInfo;
		this.uiSearchQuery = this;
	}

	public boolean canRerun() {
		return true;
	}

	public boolean canRunInBackground() {
		return true;
	}

	public String getLabel() {
		return "Search for " + adapter.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.search.ui.ISearchQuery#getSearchResult()
	 */
	public ISearchResult getSearchResult() {
		if (result == null) {
			data = new LinkedList<IObject>();
			result = new SearchResult(uiSearchQuery, data);
		}
		return result;
	}

	public IStatus run(final IProgressMonitor monitor) {
		monitor.beginTask("Operation in Progress...", IProgressMonitor.UNKNOWN);
		performSearch(monitor);
		monitor.done();
		return Status.OK_STATUS;
	}

	private void performSearch(final IProgressMonitor monitor) {

		long syncKey = logset.getLock();
		if (syncKey == -1) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					MessageDialog
					.openWarning(
						Display.getDefault().getActiveShell(),
						"Warning",
						"Another Log Analyzer activity is currently running.To continue preempt the currently running task or wait for running task to finish its execution");
				}
			});
			return;
		}

		SearchQuery searchQuery = new SearchQuery(adapter, SeUiPlugin
				.getDefault().getPreferenceStore().getInt("search_hits"),
				searchInfo);

		if (logset == null) {
			throw new IllegalStateException("logset not set");
		}

		try {
			long currentTime = logset.getCurrentTime();
			logset.setCurrentTime(0);
			if (logset.canSkipEvents() && (searchInfo != null)) {
				logset.setNoOfEventsToSkip(searchInfo.getCurrentHit()
						.getILogEventSkipCount());
				searchQuery.setSkipEventsRequired(true);
			}
			logset.addQuery(searchQuery, monitor);
			logset.setCurrentTime(currentTime);
			logset.setNoOfEventsToSkip(0);
			data = searchQuery.getResults();
			searchInfo = searchQuery.getSearchInfo();
			if (result == null) {
				result = new SearchResult(uiSearchQuery, data);
			} else {
				result.setData(new ArrayList<Object>(data));
				result.notifySearchResultListener();
			}
		} finally {
			logset.releaseLock(syncKey);
			logset.removeQuery(searchQuery);
		}
	}

	public Logset getLogset() {
		return logset;
	}

	public SearchQuery.SearchInfo getSearchInfo() {
		return searchInfo;
	}
}
