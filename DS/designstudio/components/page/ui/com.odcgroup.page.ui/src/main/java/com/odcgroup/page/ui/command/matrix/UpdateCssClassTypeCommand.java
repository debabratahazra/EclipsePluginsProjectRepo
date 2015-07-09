package com.odcgroup.page.ui.command.matrix;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.model.snippets.SnippetUtil;
import com.odcgroup.page.model.widgets.matrix.ICssClass;
import com.odcgroup.page.model.widgets.matrix.IStyleProvider;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 *
 * @author pkk
 *
 */
public class UpdateCssClassTypeCommand extends BaseCommand {
	
	private IStyleProvider styleProvider;
	private String newType = null;
	private String oldType = null;
	
	
	/**
	 * @param contentCell
	 */
	public UpdateCssClassTypeCommand(IStyleProvider styleProvider, String type) {
		this.styleProvider = styleProvider;
		this.newType = type;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		if (styleProvider != null) {
			ICssClass cssClass = styleProvider.getCssClass();
			if (cssClass == null) {
				cssClass = SnippetUtil.getSnippetFactory().createCssClass();
				cssClass.getCssClassType().setValue(newType);
				styleProvider.getWidget().getSnippets().add(cssClass.getSnippet());
			} else {
				oldType = cssClass.getCssClassType().getValue();
				if (!oldType.equals(newType)) {
					cssClass.getCssClassType().setValue(newType);
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
		if (StringUtils.isEmpty(oldType)) {
			styleProvider.getWidget().getSnippets().clear();
		} else {
			cssClass.getCssClassType().setValue(oldType);
		}
	}
	
	

}
