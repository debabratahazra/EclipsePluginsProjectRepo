package com.odcgroup.page.ui.command.matrix;

import com.odcgroup.page.model.widgets.matrix.IConditionalCssClass;
import com.odcgroup.page.model.widgets.matrix.ICssClass;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 *
 * @author pkk
 *
 */
public class RemoveCssConditionCommand extends BaseCommand {
	
	private ICssClass cssClass;
	private IConditionalCssClass condition;
	private int index = -1;
	
	/**
	 * @param cssClass
	 * @param condition
	 */
	public RemoveCssConditionCommand(ICssClass cssClass, IConditionalCssClass condition) {
		this.cssClass = cssClass;
		this.condition = condition;
	}
	


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		if (cssClass != null ) {
			index = cssClass.getSnippet().getContents().indexOf(condition.getSnippet());
			cssClass.removeConditionalCssClass(condition);
		}
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		if (index > -1) {
			cssClass.getSnippet().getContents().add(index, condition.getSnippet());
		}
	}

}
