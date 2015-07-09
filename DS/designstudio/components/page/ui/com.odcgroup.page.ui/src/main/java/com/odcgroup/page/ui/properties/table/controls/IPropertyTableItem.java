package com.odcgroup.page.ui.properties.table.controls;

import java.util.List;

import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.ui.IWorkbenchPart;

/**
 *
 * @author pkk
 * @param <E> 
 * @param <T> 
 *
 */
public interface IPropertyTableItem<E, T> {
	
	/**
	 * @param inputElement
	 * @return Object[]
	 */
	E[] getTableElements(List<E> inputElement);
	
	/**
	 * @param element
	 * @param columnIndex
	 * @return String
	 */
	String getPropertyTableColumnText(E element, int columnIndex);
	
	/**
	 * @return ViewerSorter
	 */
	ViewerSorter getTableSorter();
	
	/**
	 * @param input 
	 * @return List
	 */
	List<E> getTableInput(T input);
	
	/**
	 * @return element
	 */
	E getSelection();
	
	/**
	 * @param columns 
	 */
	void configureColumns(List<PropertyColumn> columns);
	
	/**
	 * @param input
	 * @param part
	 */
	void setInput(T input, IWorkbenchPart part);

}
