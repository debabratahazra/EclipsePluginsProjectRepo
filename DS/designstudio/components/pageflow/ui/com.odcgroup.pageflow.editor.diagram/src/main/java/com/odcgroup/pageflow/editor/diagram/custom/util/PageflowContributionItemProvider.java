package com.odcgroup.pageflow.editor.diagram.custom.util;

import org.eclipse.gmf.runtime.common.ui.util.IWorkbenchPartDescriptor;
import org.eclipse.gmf.runtime.diagram.ui.providers.DiagramContributionItemProvider;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IWorkbenchPage;

import com.odcgroup.pageflow.editor.diagram.custom.actions.ActionLabelDisplayAction;
import com.odcgroup.pageflow.editor.diagram.custom.actions.ToggleDescriptionLabelAction;
import com.odcgroup.pageflow.editor.diagram.custom.actions.ToggleIDDisplayAction;
import com.odcgroup.pageflow.editor.diagram.part.Messages;

/**
 * @author pkk
 *
 */
public class PageflowContributionItemProvider extends
		DiagramContributionItemProvider {

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.providers.DiagramContributionItemProvider#createAction(java.lang.String, org.eclipse.gmf.runtime.common.ui.util.IWorkbenchPartDescriptor)
	 */
	protected IAction createAction(String actionId,
			IWorkbenchPartDescriptor partDescriptor) {
		IWorkbenchPage workbenchPage = partDescriptor.getPartPage();
		if (actionId.equals(Messages.DescLabelDisplayToolBarID)) {
			return new ToggleDescriptionLabelAction(workbenchPage);
		} else if (actionId.equals(Messages.ActionLabelDisplayToolBarId)){
			return new ActionLabelDisplayAction(workbenchPage);			
		} else if (actionId.equals(Messages.ToggleIDDisplayToolBarID)){
			return new ToggleIDDisplayAction(workbenchPage);			
		}
		return super.createAction(actionId, partDescriptor);
	}

}
