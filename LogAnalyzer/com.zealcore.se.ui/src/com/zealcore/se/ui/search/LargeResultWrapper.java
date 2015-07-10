/**
 * 
 */
package com.zealcore.se.ui.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.zealcore.se.core.ifw.SearchQuery;
import com.zealcore.se.ui.IconManager;

class LargeResultWrapper implements IWorkbenchAdapter {

	static final int MAX = 100;

	private String label;

	private static String separator = " ... ";

	private Object[] children;

	public LargeResultWrapper(final Object[] children) {
		this.children = children;
	}

	Object[] wrap(boolean search) {
		NamedItemContentProvider.setRange(findRange(children.length));
		NamedItemContentProvider.setTotalLogs(children.length);
		return wrap(0, children, search);
	}

	private Object[] wrap(final int depth, final Object[] content, boolean search) {

		if (content.length <= MAX) {
			return content;
		}

		String label2 = "";
		int count = 0;
		List<Object> stuff = new ArrayList<Object>();

		int offsetValue = 0;
		SearchResult searchResult = SearchResult.getInstance();
		if(search && searchResult != null){
			//Only for Search Result
			int result = searchResult.getItems().size();
			if(result > 0){
				SearchQuery.SearchInfo info = ((UISearchQuery)searchResult.getQuery()).getSearchInfo();
				offsetValue = info.getCurrentHit().getTotalEventCount() - result + 1;
			}
		}

		for ( int start = 0; start < content.length ; start += MAX) {
			int end = Math.min(content.length, start + MAX);

			if (depth > 0) {
				int tempRange = NamedItemContentProvider.getRange();
				int lLabel = tempRange * count;
				int rLabel = Math.min(NamedItemContentProvider.getTotalLogs(),
						(tempRange * (count + 1)));
				label2 = (offsetValue + lLabel) + separator + (offsetValue + rLabel - 1);
				count++;
			} else {
				label2 = (offsetValue + start) + separator + (offsetValue + end - 1);
			}

			LargeResultWrapper parent = new LargeResultWrapper(Arrays.asList(content).subList(start, end).toArray());
			stuff.add(parent);
			parent.setLabel(label2);
		}

		if (stuff.size() > MAX) {
			return wrap(depth + 1, stuff.toArray(), search);
		}

		return stuff.toArray();
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public Object[] getChildren(final Object o) {
		return children;
	}

	public ImageDescriptor getImageDescriptor(final Object object) {
		return IconManager.getImageDescriptor(IconManager.ARRAY_PARTITION);
	}

	public String getLabel(final Object o) {
		return label;
	}

	public Object getParent(final Object o) {
		return null;
	}

	private static int findRange(final int len) {
		int result = 1;
		int length = len;
		while (length > 10) {
			result *= 10;
			length /= 10;
		}
		return result;
	}

}
