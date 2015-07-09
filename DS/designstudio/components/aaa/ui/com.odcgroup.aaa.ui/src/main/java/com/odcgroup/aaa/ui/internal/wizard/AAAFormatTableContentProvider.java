package com.odcgroup.aaa.ui.internal.wizard;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.odcgroup.aaa.ui.internal.model.AAAFormatCode;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class AAAFormatTableContentProvider implements IStructuredContentProvider {
	
	private static AAAFormatCode[] formatCodeArray = {};

	/*
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
    @SuppressWarnings("unchecked")
    public Object[] getElements(Object inputElement) {
		return ((List<AAAFormatCode>)inputElement).toArray(formatCodeArray);
	}

	/*
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
	}

	/*
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

}
