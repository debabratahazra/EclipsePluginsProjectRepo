package com.odcgroup.page.ui.properties.table.controls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 * @author pkk
 * @param <E> 
 *
 */
public abstract class AbstractTypeCombo<E> implements ITypeCombo<E> {
	

	/**		 */
	private Combo combo;
	/**		 */
	private String[] items = null;
	
	/**
	 * @param parent
	 * @param items  
	 */
	public AbstractTypeCombo(Composite parent, String[] items) {
		createCombo(parent);
		setItems(items);
	}	
	
	/**
	 * @param parent
	 */
	public AbstractTypeCombo(Composite parent) {
		createCombo(parent);
	}
	
	/**
	 * @param parent
	 */
	protected void createCombo(Composite parent) {
		combo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
	}
	
	/**
	 * @param items
	 */
	public void setItems(String[] items) {
		this.items = items;
		combo.removeAll();
		combo.setItems(items);
	}
	
	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ITypeCombo#getCombo()
	 */
	public Combo getCombo() {
		return combo;
	}
	
	/**
	 * @param listener
	 */
	public void addSelectionListener(SelectionListener listener) {
		combo.addSelectionListener(listener);
	}
	
	/**
	 * @param listener
	 */
	public void removeSelectionListener(SelectionListener listener) {
		combo.removeSelectionListener(listener);
	}
	
	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ITypeCombo#setEnabled(boolean)
	 */
	public void setEnabled(boolean enabled) {
		combo.setEnabled(enabled);
	}

	/**
	 * @see com.odcgroup.page.ui.properties.table.controls.ITypeCombo#isEnabled()
	 */
	public boolean isEnabled() {
		return combo.isEnabled();
	}
	
	/**
	 * @return item
	 */
	public String getSelectedItem() {
		int index = getCombo().getSelectionIndex();
		if (index == -1) return null;
		return items[index];
	}
	
	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ITypeCombo#select(java.lang.String)
	 */
	public void select(String item) {
		int index = -1;
		for(int i =0;i<items.length;i++) {
			if (items[i].equals(item)) {
				index = i;
			}
		}
		getCombo().select(index);
	}
	

}
