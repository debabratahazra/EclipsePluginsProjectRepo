package com.zealcore.se.ui.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.SEProperty;

// TODO Move this to a more general package
public class NamedItemContentProvider implements ITreeContentProvider {

    private static int range;

    private static int totalLogs;

    public static int getTotalLogs() {
        return totalLogs;
    }

    public static void setTotalLogs(final int totalLogs) {
        NamedItemContentProvider.totalLogs = totalLogs;
    }

    public static int getRange() {
        return range;
    }

    public static void setRange(final int range) {
        NamedItemContentProvider.range = range;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {}

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface
     * .viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    public void inputChanged(final Viewer viewer, final Object oldInput,
            final Object newInput) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.
     * Object)
     */
    public Object[] getChildren(final Object parentElement) {
    	if(parentElement instanceof SearchResult){
    		return new LargeResultWrapper(getChildren2(parentElement)).wrap(true);
    	}
        return new LargeResultWrapper(getChildren2(parentElement)).wrap(false);
    }

    @SuppressWarnings("unchecked")
    private Object[] getChildren2(final Object parentElement) {
        if (parentElement == null) {
            return new Object[0];
        }

        if (parentElement instanceof IWorkbenchAdapter) {
            final IWorkbenchAdapter adapter = (IWorkbenchAdapter) parentElement;
            return adapter.getChildren(parentElement);

        }

        if (parentElement instanceof SEProperty) {
            final SEProperty prop = (SEProperty) parentElement;
            return getChildren(prop.getData());
        }

        if (parentElement instanceof IObject) {
            final IObject named = (IObject) parentElement;
            final List<SEProperty> propertyAnnotations = named
                    .getZPropertyAnnotations();
            return getChildren(propertyAnnotations);

        }

        if (parentElement.getClass().isArray()) {
            return (Object[]) parentElement;
        }

        if (parentElement instanceof Iterable) {
            final Iterable col = (Iterable) parentElement;
            final Collection<Object> objects = new ArrayList<Object>();
            for (final Object object : col) {
                objects.add(object);
            }
            return objects.toArray();
        }

        if (parentElement instanceof SearchResult) {
            final SearchResult result = (SearchResult) parentElement;
            final List items = result.getItems();
            Collections.reverse(items);
            return items.toArray();
        }

        return new Object[0];
    }

    @SuppressWarnings("unchecked")
    private Object[] getChildren(final List<SEProperty> propertyAnnotations) {

        final List objects = new ArrayList();
        for (final SEProperty property : propertyAnnotations) {
            if (property.isDirect()) {
                objects.addAll(Arrays.asList(getChildren(property)));
            } else {
                objects.add(property);
            }
        }
        return objects.toArray();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object
     * )
     */
    public Object getParent(final Object element) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.
     * Object)
     */
    public boolean hasChildren(final Object element) {
        return getChildren(element).length > 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java
     * .lang.Object)
     */
    public Object[] getElements(final Object inputElement) {
        return getChildren(inputElement);
    }
}
