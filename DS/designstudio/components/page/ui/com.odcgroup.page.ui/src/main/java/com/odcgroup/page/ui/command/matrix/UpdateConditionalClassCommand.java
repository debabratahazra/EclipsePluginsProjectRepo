package com.odcgroup.page.ui.command.matrix;

import com.odcgroup.page.model.widgets.matrix.IConditionalCssClass;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 *
 * @author pkk
 *
 */
public class UpdateConditionalClassCommand extends BaseCommand {
	
	private IConditionalCssClass condition;
	private String name = null;
	private String code = null;
	private String result = null;
	
	private String oldName = null;
	private String oldCode = null;
	private String oldResult = null;
	
	/**
	 * @param IConditionalCssClass
	 * @param name
	 * @param code
	 * @param result
	 */
	public UpdateConditionalClassCommand(IConditionalCssClass condition, String name, String code, String result) {
		this.condition = condition;
		this.name = name;
		this.code = code;
		this.result = result;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		if (condition != null) {
			oldName = condition.getName().getValue();
			if (!oldName.equals(name)) {
				condition.getName().setValue(name);
			} else {
				oldName = null;
			}
			oldCode = condition.getCondition().getValue();
			if (!oldCode.equals(code)) {
				condition.getCondition().setValue(code);
			} else {
				oldCode = null;
			}
			
			oldResult = condition.getResult().getValue();
			if (!oldResult.equals(result)) {
				condition.getResult().setValue(result);
			} else {
				oldResult = null;
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		if (condition != null) {
			if (oldName != null) {
				condition.getName().setValue(oldName);
			}
			if (oldCode != null) {
				condition.getCondition().setValue(oldCode);
			}
			if (oldResult != null) {
				condition.getResult().setValue(oldResult);
			}
		}
	}

}
