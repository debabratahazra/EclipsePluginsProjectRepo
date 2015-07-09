package com.temenos.t24.tools.eclipse.basic.editors.outline;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.views.IT24ViewItem;

public class OutlineLabelProvider implements ILabelProvider
{

	public OutlineLabelProvider(){
		super();
	}

	public Image getImage(Object element){
		return null;
	}

	public String getText(Object element){
		if (element instanceof IT24ViewItem){
            IT24ViewItem dtdElement = (IT24ViewItem) element;
			String textToShow = dtdElement.getLabel();
			return textToShow;
		}
		return "";
	}

	public boolean isLabelProperty(Object element, String property)
	{
		return false;
	}

    public void addListener(ILabelProviderListener listener) {
    }

    public void dispose() {
    }

    public void removeListener(ILabelProviderListener listener) {
    }
}