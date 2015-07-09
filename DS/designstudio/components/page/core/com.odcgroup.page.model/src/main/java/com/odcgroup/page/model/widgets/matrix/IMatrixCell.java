package com.odcgroup.page.model.widgets.matrix;

import java.util.List;

import com.odcgroup.page.model.widgets.table.IWidgetAdapter;

/**
 *
 * @author pkk
 *
 */
public interface IMatrixCell extends IWidgetAdapter {
	
	public static final String CELLTYPE_CONTENT = "content-cell";
	public static final String CELLTYPE_LASTCELL = "last-cell";
	public static final String CELLTYPE_LASTROW = "last-row";
	public static final String CELLTYPE_LASTCOLUMN = "last-column";
	public static final String CELLTYPE_EXTRACOLUMN = "extra-column";
	
	
	/**
	 * @return
	 */
	List<IMatrixCellItem> getCellItems();
	
	/**
	 * @param cellItem
	 */
	void removeItem(IMatrixCellItem cellItem);
	
	/**
	 * @param id
	 * @return
	 */
	IMatrixCellItem getCellItem(String ID);
	
	/**
	 * @return
	 */
	String getMatrixCellType();
	
	/**
	 * @return
	 */
	boolean isContentCell();
	
	/**
	 * @return
	 */
	boolean isLastCell();
	
	/**
	 * @return
	 */
	boolean isLastColumn();
	
	/**
	 * @return
	 */
	boolean isLastRow();
	
	/**
	 * @return
	 */
	boolean isExtraColumn();
	
	/**
	 * @return
	 */
	IMatrix getParent();


}
