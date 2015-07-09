package com.odcgroup.page.ui.properties.table;

import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.ui.properties.AbstractPropertySourceAdapter;

/**
 * A PropertySourceAdapter for the property column-based-on-group.
 * 
 * @author atr
 */
public class TableColumnBasedOnGroupPropertySourceAdapter extends AbstractPropertySourceAdapter {

	@Override
	public Object getPropertyValue() {
		return TableHelper.getTableColumn(getWidget()).getBasedOnGroup();
	}

	@Override
	public void setPropertyValue(Object newValue) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Creates a new TableColumnBasedOnGroupPropertySourceAdapter.
	 * 
	 * @param property
	 *            The Property
	 * @param commandStack
	 *            The command stack to execute the command
	 */
	public TableColumnBasedOnGroupPropertySourceAdapter(Property property, CommandStack commandStack) {
		super(property, commandStack);
	}	

}
