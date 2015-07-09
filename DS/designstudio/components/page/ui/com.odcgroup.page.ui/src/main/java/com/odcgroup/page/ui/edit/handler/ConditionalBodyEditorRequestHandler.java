package com.odcgroup.page.ui.edit.handler;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;
import com.odcgroup.page.ui.util.ConditionDialogUtils;
import com.odcgroup.workbench.core.IOfsProject;

public class ConditionalBodyEditorRequestHandler implements WidgetRequestHandler {

	public void handle(IOfsProject ofsProject, CommandStack commandStack,
			Widget widget) {
		Property codeProp = widget.findProperty(PropertyTypeConstants.JAVA_CODE);
		if (codeProp != null) {
			Shell shell = null;
			try {
				shell = new Shell();
				String newValue = ConditionDialogUtils.editCondition(shell, codeProp);
				if (!newValue.equals(codeProp.getValue())) {
					commandStack.execute(new UpdatePropertyCommand(codeProp,
							newValue));
				}
			} finally {
				if (shell != null) {
					shell.dispose();
				}
			}
		}
	}
}
