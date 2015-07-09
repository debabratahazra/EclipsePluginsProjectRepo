package com.odcgroup.menu.editor.decorators;

import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

import com.odcgroup.menu.model.MenuItem;

/**
 * @author pkk
 *
 */
public class MenuEnabledDecorator implements ILightweightLabelDecorator {

	public void decorate(Object element, IDecoration decoration) {
		if (element instanceof MenuItem) {
			MenuItem item  = (MenuItem) element;
			if(item.getEnabled().toString().equals("conditional")) {
				decoration.addSuffix("  (?)");
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
