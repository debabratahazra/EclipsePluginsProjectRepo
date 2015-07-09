package com.odcgroup.t24.menu.editor.provider;

import org.eclipse.jface.viewers.DecoratingStyledCellLabelProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.swt.graphics.Color;

import com.odcgroup.t24.menu.menu.MenuItem;

/**
 * @author pkk
 *
 */
public class MenuDecoratingLabelProvider extends DecoratingStyledCellLabelProvider {
        
	/**
	 * @param provider
	 * @param decorator
	 */
	public MenuDecoratingLabelProvider(IStyledLabelProvider provider, ILabelDecorator decorator) {
		super(provider, decorator, null);
		
	}

	@Override
	public Color getForeground(Object element) {
		if (element instanceof MenuItem) {
			MenuItem item = (MenuItem) element;
			if (item.getEnabled().toString().equals("false")) {
				return new Color(null, 192, 192, 192);
			}
		} 
		return super.getForeground(element);
	}
}
