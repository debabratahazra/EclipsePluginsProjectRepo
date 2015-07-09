package com.odcgroup.page.ui.command.matrix;

import com.odcgroup.page.model.widgets.matrix.ICssClass;
import com.odcgroup.page.model.widgets.matrix.IStyleProvider;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 *
 * @author pkk
 *
 */
public class UpdateSpecificCssClassCommand extends BaseCommand {
	
	private IStyleProvider styleProvider;
	private String newClass = null;
	private String oldClass = null;
	
	
	/**
	 * @param contentCell
	 */
	public UpdateSpecificCssClassCommand(IStyleProvider styleProvider, String newClass) {
		this.styleProvider = styleProvider;
		this.newClass = newClass;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		if (styleProvider != null) {
			ICssClass cssClass = styleProvider.getCssClass();
			if (cssClass != null) {
				oldClass = cssClass.getSpecificCssClass().getValue();
				if (!oldClass.equals(newClass)) {
					cssClass.getSpecificCssClass().setValue(newClass);
				}
			}
		}
		super.execute();
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		ICssClass cssClass = styleProvider.getCssClass();
		cssClass.getSpecificCssClass().setValue(oldClass);
	}
	
}
