package com.odcgroup.page.ui.properties.table.controls;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;


/**
 * @author pkk
 * @param <E> 
 *
 */
public interface ITypeCombo<E> {
	
	/**
	 * @param value
	 */
	public void select(String value);
	
	/**
	 * @return Object
	 */
	public E getSelectedValue();
	
	/**
	 * @param listener
	 */
	public void addSelectionListener(SelectionListener listener);
	
	/**
	 * @param listener
	 */
	public void removeSelectionListener(SelectionListener listener);
	
	/**
	 * @param enabled
	 */
	public void setEnabled(boolean enabled);
	
	/**
	 * @return enablement
	 */
	public boolean isEnabled();
	
	/**
	 * @return Combo
	 */
	public Combo getCombo();

}
