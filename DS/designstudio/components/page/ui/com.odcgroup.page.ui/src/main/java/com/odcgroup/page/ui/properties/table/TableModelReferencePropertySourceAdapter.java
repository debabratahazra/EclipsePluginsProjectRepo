package com.odcgroup.page.ui.properties.table;

import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;
import com.odcgroup.page.ui.properties.AbstractPropertySourceAdapter;

/**
 * @author atr
 */
public class TableModelReferencePropertySourceAdapter extends AbstractPropertySourceAdapter {

	@Override
	public Object getPropertyValue() {
		return TableHelper.getTable(getWidget()).getModelReference();
	}

	@Override
	public void setPropertyValue(Object newValue) {
		String s = newValue != null ? String.valueOf(newValue) : "";
		getCommandStack().execute(new UpdatePropertyCommand(getProperty(), s.trim()));
	}

	/**
	 * Creates a new ViewReferencePropertySourceAdapter.
	 * 
	 * @param property
	 *            The Property
	 * @param commandStack
	 *            The command stack to execute the command
	 */
	public TableModelReferencePropertySourceAdapter(Property property, CommandStack commandStack) {
		super(property, commandStack);
	}

}
