package com.odcgroup.page.ui.command.matrix;

import com.odcgroup.page.model.widgets.matrix.IConditionalCssClass;
import com.odcgroup.page.model.widgets.matrix.ICssClass;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 *
 * @author pkk
 *
 */
public class InsertConditionalCssClassCommand extends BaseCommand {
	
	private ICssClass cssClass;
	private IConditionalCssClass newCondition;
	
	/**
	 * @param cssClass
	 * @param newCondition
	 */
	public InsertConditionalCssClassCommand(ICssClass cssClass, IConditionalCssClass newCondition) {
		this.cssClass = cssClass;
		this.newCondition = newCondition;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		if (cssClass != null && newCondition != null) {
			cssClass.getSnippet().getContents().add(newCondition.getSnippet());
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		if (cssClass != null) {
			cssClass.getSnippet().getContents().clear();
		}
	}

}
