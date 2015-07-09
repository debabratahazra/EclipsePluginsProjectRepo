package com.odcgroup.menu.editor.provider;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

/**
 * @author pkk
 *
 */
public class MenuStyledLabelProvider implements IStyledLabelProvider {
	
	private AdapterFactoryLabelProvider provider;
	/**
	 * @param provider
	 */
	public MenuStyledLabelProvider(AdapterFactoryLabelProvider provider) {
		this.provider = provider;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider#getStyledText(java.lang.Object)
	 */
	public StyledString getStyledText(Object element) {	
		return new StyledString(provider.getText(element));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object element) {
		return provider.getImage(element);
	}

	

	public void addListener(ILabelProviderListener listener) {
		provider.addListener(listener);
	}

	public void dispose() {
	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
		provider.removeListener(listener);
	}

}
