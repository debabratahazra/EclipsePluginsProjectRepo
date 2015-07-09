package com.odcgroup.page.ui.command.matrix;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.matrix.IMatrixAxis;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 *
 * @author pkk
 *
 */
public class UpdateMatrixAxisCommand extends BaseCommand {
	
	private IMatrixAxis matrixAxis;
	private Widget widget;
	String oldValue = "";
	
	/**
	 * @param matrixAxis
	 * @param widget
	 */
	public UpdateMatrixAxisCommand(IMatrixAxis matrixAxis, Widget widget) {
		this.matrixAxis = matrixAxis;
		this.widget = widget;
	}
	
	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		String domainValue = widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
		Property domProp = matrixAxis.getDomainAttribute();
		oldValue = domProp.getValue();
		matrixAxis.getDomainAttribute().setValue(domainValue);
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		matrixAxis.getDomainAttribute().setValue(oldValue);
	}

}
