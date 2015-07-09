package com.odcgroup.page.model.widgets;

import java.util.List;

import com.odcgroup.page.model.Widget;

/**
 * @author atr
 * @since DS 1.40.0
 */
public interface Grid {

	int getPosition(int rowIndex, int columnIndex);
	
	int getColumnWidth(int columnIndex);
	
	void setColumnWidth(int columnIndex, int width);
	
	void setColumnCssClass(int columnIndex, String value);

	String getColumnCssClass(int columnIndex);
	
	Widget getCellWidgetAt(int rowIndex, int columnIndex);

	List<List<Widget>> setColumnCount(int newColumnCount);
	
	int getColumnCount();

	GridColumn getColumn(int columnIndex);

	List<List<Widget>> setRowCount(int newRowCount);

	int getRowCount();

	GridRow getRow(int rowIndex);

	int insertColumn();

	void insertColumnAt(int columnIndex);

	void insertColumnAt(int columnIndex, List<Widget> cells);

	void insertColumnRight(int columnIndex);

	void insertColumnLeft(int columnIndex);

	int insertRow();

	void insertRowAt(int rowIndex);

	void insertRowAt(int rowIndex, List<Widget> cells);

	void insertRowAbove(int rowIndex);

	void insertRowBelow(int rowIndex);

	List<Widget> removeColumn(int columnIndex);

	List<Widget> removeRow(int rowIndex);

	void moveCellDown(int rowIndex, int columnIndex);

	void moveCellLeft(int rowIndex, int columnIndex);

	void moveCellRight(int rowIndex, int columnIndex);
	
	void moveCellUp(int rowIndex, int columnIndex);

	void moveColumnLeft(int columnIndex);
	
	void moveColumnRight(int columnIndex);

	void moveRowDown(int rowIndex);

	void moveRowUp(int rowIndex);


}
