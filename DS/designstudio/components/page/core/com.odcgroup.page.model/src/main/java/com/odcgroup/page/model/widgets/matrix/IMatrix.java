package com.odcgroup.page.model.widgets.matrix;

import java.util.List;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.widgets.table.ITableKeepFilter;
import com.odcgroup.page.model.widgets.table.IWidgetAdapter;

/**
 * interface for the Matrix widget
 *
 * @author pkk
 *
 */
public interface IMatrix extends IWidgetAdapter {
	
	/**
	 * to display or not the last row aggregations of the matrix
	 * 
	 * @return
	 */
	boolean displayLastRow();

	/**
	 * to display or not the last column aggregations of the matrix
	 * 
	 * @return
	 */
	boolean displayLastColumn();
	
	/**
	 * to display or not the matrix aggregations
	 * @return
	 */
	boolean displayLastCell();
	
	/**
	 * description of the matrix widget
	 * @return
	 */
	String getDescription();	

	/**
	 * @param keepFilter
	 */
	void add(ITableKeepFilter keepFilter);	
	
	/**
	 * @param keepFilter
	 */
	void remove(ITableKeepFilter keepFilter);

	/**
	 * @param extra
	 */
	void add(IMatrixExtra extra);
	
	/**
	 * @param extra
	 */
	void remove(IMatrixExtra extra);
	
	/**
	 * @return
	 */
	boolean isDumpModelEnabled();
	
	/**
	 * @return the property css class
	 */
	String getCssClass();
	
	/**
	 * @return the unique matrix identifier
	 */
	String getID();		

	/**
	 * @return
	 */
	String getModelReference();

	/**
	 * This property reflects how multiple filters are connected
	 * 
	 * @return the property keep-filter-logic
	 */
	Property getKeepFilterLogic();

	/**
	 * @return a list of defined keep filters, 
	 * or an empty list if none have been defined.
	 */
	List<ITableKeepFilter> getKeepFilters();
	
	/**
	 * @return a list of defined Extra, or an empty list if none have been
	 *         defined.
	 */
	List<IMatrixExtra> getExtras();
	
	/**
	 * @return matrix group representing the x-axis
	 */
	IMatrixAxis getXAxis();
	
	/**
	 * @return matrix group representing the y-axis
	 */
	IMatrixAxis getYAxis();
	
	/**
	 * @return
	 */
	IMatrixCell getLastCell();
	
	/**
	 * @return
	 */
	IMatrixCell getLastColumn();
	
	/**
	 * @return
	 */
	IMatrixCell getLastRow();
	
	/**
	 * @return
	 */
	IMatrixExtraColumn getExtraColumn();
	
	/**
	 * @return
	 */
	IMatrixContentCell getMatrixCell();

}
