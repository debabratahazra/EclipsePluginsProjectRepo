package com.odcgroup.page.ui.properties.table;

import java.util.List;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableAggregate;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.model.widgets.table.ITableColumnItem;
import com.odcgroup.page.model.widgets.table.ITableSort;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;
import com.odcgroup.page.ui.properties.AbstractPropertySourceAdapter;

public class TableColumnAttributePropertySourceAdapter extends AbstractPropertySourceAdapter {

	/**
	 * @param property
	 * @param commandStack
	 */
	public TableColumnAttributePropertySourceAdapter(Property property,
			CommandStack commandStack) {
		super(property, commandStack);
	}
	
	/**
	 * @return the actual value of the underlying property
	 */
	protected String doGetPropertyValue() {
		String s = getProperty().getValue();
		return s == null ? "" : s;
	}

	@Override
	public Object getPropertyValue() {
        String s = getProperty().getValue();
        return s == null ? "" : s;
	}

	@Override
	public void setPropertyValue(Object newValue) {
		Widget widget = getWidget();
		String oldVal = doGetPropertyValue();
        CompoundCommand cc = new CompoundCommand("Change Column Name");
        String newVal = newValue != null ? String.valueOf(newValue) : "";
        cc.add(new UpdatePropertyCommand(getProperty(), newVal));
        if(widget.getTypeName().equals(WidgetTypeConstants.TABLE_COLUMN)){
        ITableColumn column = TableHelper.getTableColumn(widget);
        // update the references
		if (column.isComputed()) {
			// column items
			ITable table = column.getTable();
			List<ITableColumn> cols = table.getColumns();
			for (ITableColumn col : cols) {
				List<ITableColumnItem> items =  col.getItems();
				for (ITableColumnItem item : items) {
					if(item.getColumn().equals(oldVal)) {
						Property colp = item.getWidget().findProperty("item-column");
						cc.add(new UpdatePropertyCommand(colp, newVal));
					}
				}
			}
			// table sorts
			List<ITableSort> sorts = column.getTable().getSorts();
			for (ITableSort sort : sorts) {
				if(sort.getColumnName().equals(oldVal)) {
					Property colp = sort.getWidget().findProperty("sort-column-name");
					cc.add(new UpdatePropertyCommand(colp, newVal));
				}
			}
			
			// table aggregates
			List<ITableAggregate> aggs = column.getTable().getAggregatedColumns();
			for (ITableAggregate agg : aggs) {
				if (agg.getColumnName().equals(oldVal)) {
					Property colp = agg.getWidget().findProperty("aggregate-column-name");
					cc.add(new UpdatePropertyCommand(colp, newVal));
				}
			}
		} 
        }	
        getCommandStack().execute(cc);
	}

}
 