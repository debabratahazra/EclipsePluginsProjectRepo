package com.odcgroup.aaa.ui.internal.wizard;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.aaa.ui.internal.model.AAAFormatCode;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class AAAFormatTableLabelProvider implements ITableLabelProvider {

	/*
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	/* 
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
	 */
	public String getColumnText(Object element, int columnIndex) {
		AAAFormatCode format = (AAAFormatCode) element;
		if (columnIndex == 0) {
			return format.getCode();
		} else if (columnIndex == 1) {
			return format.getFunction();
		} else {
			return format.getDenom();
		}
	}

	/*
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	public void dispose() {
	}

	/*
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
	 */
	public boolean isLabelProperty(Object element, String property) {
		return true;
	}

	/*
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * 
	 */
	public AAAFormatTableLabelProvider() {
		
	}
	
}
