package com.odcgroup.page.ui.command.table;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableColumn;

/**
 * @author amc
 *
 */
public class TableDeleteColumnCommandTest {
	
	@Test
	public void testExecute() throws Exception {
		// given 
		ITable table = mock(ITable.class);
		ITableColumn column = createColumn(table, 0);
		TableDeleteColumnCommand command = new TableDeleteColumnCommand(column);
		// when
		command.execute();
		// then
		verify(table).removeColumn(0);
	}
	
	@Test
	public void testUndo() throws Exception {
		// given 
		ITable table = mock(ITable.class);
		Widget columnWidget = createColumnWidget(table);
		ITableColumn column = createColumn(table, 0);
		TableDeleteColumnCommand command = new TableDeleteColumnCommand(column);
		// when
		command.execute();
		command.undo();
		// then
		verify(table).insertColumnAt(0, columnWidget);
	}

	private Widget createColumnWidget(ITable table) {
		Widget columnWidget = mock(Widget.class);
		when(table.removeColumn(anyInt())).thenReturn(columnWidget);
		return columnWidget;
	}

	/**
	 * If a column to the left of the current column is deleted, its index changes
	 * so the new index must be used for removal
	 */
	@Test
	public void testWhenColumnIndexChangesBeforeExecute() throws Exception {
		// given 
		ITable table = mock(ITable.class);
		ITableColumn column = createColumn(table, 1);
		TableDeleteColumnCommand command = new TableDeleteColumnCommand(column);
		simulateEffectOfColumnZeroDeletion(column);
		// when
		command.execute();
		// then
		verify(table).removeColumn(0);
	}
	
	@Test
	public void testWhenColumnIndexChangesBeforeExecuteThatUndoReaddsCorrectly() throws Exception {
		// given 
		ITable table = mock(ITable.class);
		Widget columnWidget = createColumnWidget(table);
		ITableColumn column = createColumn(table, 1);
		TableDeleteColumnCommand command = new TableDeleteColumnCommand(column);
		simulateEffectOfColumnZeroDeletion(column);
		// when
		command.execute();
		command.undo();
		// then
		verify(table).insertColumnAt(0, columnWidget);
	}

	private void simulateEffectOfColumnZeroDeletion(ITableColumn column) {
		when(column.getColumnIndex()).thenReturn(0);
	}
	
	private ITableColumn createColumn(ITable table, int index) {
		ITableColumn column = mock(ITableColumn.class);
		when(column.getTable()).thenReturn(table);
		when(column.getColumnIndex()).thenReturn(index);
		return column;
	}

}
