package com.odcgroup.page.ui.properties.table;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.ui.command.table.TableUpdateColumnCountCommand;
import com.odcgroup.page.ui.properties.AbstractPropertySourceAdapter;

/**
 * Specialized PropertySourceAdapter for the property table-column-count of the
 * widget Table/Tree.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableColumnCountPropertySourceAdapter extends AbstractPropertySourceAdapter {

	/**
	 * Creates a new TableColumnCountPropertySourceAdapter.
	 * 
	 * @param property
	 *            The Property
	 * @param commandStack
	 *            The command stack to execute the command
	 */
	public TableColumnCountPropertySourceAdapter(Property property, CommandStack commandStack) {
		super(property, commandStack);
	}

	/**
	 * Sets the value of the property.
	 * 
	 * @param newValue
	 *            The value of the property
	 * 
	 * @see IPropertySource#setPropertyValue(Object, Object)
	 */
	public void setPropertyValue(Object newValue) {
		int columnCount = Integer.parseInt((String) newValue);
		TableUpdateColumnCountCommand upc = new TableUpdateColumnCountCommand(getProperty(), columnCount);
		getCommandStack().execute(upc);
	}

	/*
	 * @see
	 * com.odcgroup.page.ui.properties.PropertySourceAdapter#getPropertyValue()
	 */
	public Object getPropertyValue() {
		return getProperty().getValue();
	}

}