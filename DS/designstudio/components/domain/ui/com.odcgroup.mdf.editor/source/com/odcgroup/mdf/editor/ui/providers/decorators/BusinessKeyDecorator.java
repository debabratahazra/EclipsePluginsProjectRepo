package com.odcgroup.mdf.editor.ui.providers.decorators;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

import com.odcgroup.mdf.editor.MdfCore;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.metamodel.MdfProperty;

public class BusinessKeyDecorator implements ILightweightLabelDecorator{
	
	 private static final ImageDescriptor BUS_KEY;
	 
	 static {
		 BUS_KEY = MdfPlugin.getImageDescriptor(MdfCore.ICON_BKEY);
	 }

	
	/**
	 * @see org.eclipse.jface.viewers.ILightweightLabelDecorator#decorate(java.lang.Object, org.eclipse.jface.viewers.IDecoration)
	 */
	public void decorate(Object element, IDecoration decoration) {
		if (element instanceof MdfProperty) {
			MdfProperty model = (MdfProperty) element;
            if (model.isBusinessKey()) {
            	decoration.addOverlay(BUS_KEY, IDecoration.TOP_RIGHT);            		
            }
        }
		
	}
	
	public void addListener(ILabelProviderListener listener) {		
	}
	
	public void dispose() {		
	}
	
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}
	
	public void removeListener(ILabelProviderListener listener) {		
	}

}
