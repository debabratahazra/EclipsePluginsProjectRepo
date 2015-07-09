package com.odcgroup.page.ui.properties.matrix;

import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;
import com.odcgroup.page.ui.properties.AbstractPropertySourceAdapter;

/**
 * @author atr
 */
public class MatrixModelReferencePropertySourceAdapter extends AbstractPropertySourceAdapter {

	@Override
	public Object getPropertyValue() {
		return MatrixHelper.getMatrix(getWidget()).getModelReference();
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
	public MatrixModelReferencePropertySourceAdapter(Property property, CommandStack commandStack) {
		super(property, commandStack);
	}

}
